package com.example.parkinglot.service;


import com.example.parkinglot.dto.*;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.enums.Status;
import com.example.parkinglot.exception.ReservationNotFoundException;
import com.example.parkinglot.exception.UserNotFoundException;
import com.example.parkinglot.mapper.ReservationInfoMapper;
import com.example.parkinglot.repo.CriteriaBasedRepository;
import com.example.parkinglot.repo.ReservationRepository;
import com.example.parkinglot.repo.SpotRepository;
import com.example.parkinglot.repo.UserRepository;
import com.example.parkinglot.security.RandomUtil;
import com.example.parkinglot.security.SecurityUtils;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final ReservationRepository reservationRepository;
    private final CarService carService;
    private final CriteriaBasedRepository criteriaBasedRepository;
    private final MailService mailService;
    private final PriceService priceService;
    private final SpotRepository spotRepository;
    private final ReservationInfoMapper reservationInfoMapper;

    public ReservationService(UserRepository userRepository, PaymentService paymentService, ReservationRepository reservationRepository, CarService carService, CriteriaBasedRepository criteriaBasedRepository, MailService mailService, PriceService priceService, SpotRepository spotRepository, ReservationInfoMapper reservationInfoMapper) {
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.reservationRepository = reservationRepository;
        this.carService = carService;
        this.criteriaBasedRepository = criteriaBasedRepository;
        this.mailService = mailService;
        this.priceService = priceService;
        this.spotRepository = spotRepository;
        this.reservationInfoMapper = reservationInfoMapper;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ReservationInfoDTO createReservation(@Valid ReservationDTO reservationDTO) {
        Long maxDuration = priceService.maxDuration();
        Duration duration = Duration.between(reservationDTO.startTime(), reservationDTO.endTime());
//        checkReservationDuration(duration, maxDuration);

        String currentUserLogin = SecurityUtils.getCurrentUserLogin().orElse("");

        User user = userRepository.findOneByLogin(currentUserLogin).orElse(null);

        PaymentMethod paymentMethod = paymentService.processReservationPayment(reservationDTO, user);

        if (reservationDTO.saveCar() && user != null) {
            reservationDTO.car().setUser(new UserDTO(user));
        }
        Car car = carService.findOrSaveCar(reservationDTO.carId(), reservationDTO.car());

        Reservation reservation = new Reservation();

        Spot spot;
        try {
            spot = criteriaBasedRepository.findFirstAvailableSpot(reservationDTO.startTime(), reservationDTO.endTime(), maxDuration);
        } catch (NoResultException e) {
            return reservationInfoMapper.toDto(false, "No spots available", reservation);
        }

        BigDecimal price = priceService.getPrice(duration.toSeconds() / 3600,  maxDuration, reservation.getUser() != null);

        reservation.setPrice(price);

        reservation.setStartTime(reservationDTO.startTime());
        reservation.setEndTime(reservationDTO.endTime());
        reservation.setUser(user);
        reservation.setSpot(spot);
        reservation.setCar(car);
        reservation.setStatus(Status.ORDERED);

        reservation.setConfirmationCode(RandomUtil.generateRandomAlphanumericString());

        reservationRepository.save(reservation);

        if (user == null) {
            user = new User();
            user.setEmail(reservationDTO.email());
        }
        mailService.sendOrderInfoMail(user, reservation.getConfirmationCode());

        String message = "Spot: " + spot.getName() + "; Flor: " + spot.getFloor().getName();
        return reservationInfoMapper.toDto(true, message, reservation);
    }

    public ReservationInfoDTO startReservation(@Valid ReservationStartDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalDateTime.now());

        Long maxDurationInHours = priceService.maxDuration();

        User user = null;
        if (reservationDTO.email() != null) {
            user = userRepository.findOneByEmailIgnoreCase(reservationDTO.email()).orElseThrow(() -> new UserNotFoundException("Couldn't find a member with the given email: " + reservationDTO.email()));
        }

        Spot spot;
        try {
            spot = criteriaBasedRepository.findFirstAvailableSpot(reservation.getStartTime(), maxDurationInHours);
        } catch (NoResultException e) {
            return reservationInfoMapper.toDto(false, "No spots available", reservation);
        }
        Car car = carService.findOrSaveCar(null, new CarDTO(null, reservationDTO.model(), reservationDTO.make(), reservationDTO.color(), reservationDTO.registration(), null));
        reservation.setSpot(spot);

        reservation.setUser(user);
        reservation.setCar(car);
        reservation.setStatus(Status.STARTED);
        reservation.setConfirmationCode(RandomUtil.generateRandomAlphanumericString());

        reservationRepository.save(reservation);

        spot.setCar(car);
        spotRepository.save(spot);

        if (user != null) {
            mailService.sendOrderInfoMail(user, reservation.getConfirmationCode());
        }

        String message = "Spot: " + spot.getName() + "; Flor: " + spot.getFloor().getName();
        return reservationInfoMapper.toDto(true, message, reservation);
    }

    public ReservationInfoDTO closeReservation(@Valid ReservationCompletionDTO reservationCompletionDTO) {

        Reservation reservation = reservationRepository.findOneByConfirmationCodeAndStatus(reservationCompletionDTO.confirmationCode(), Status.STARTED)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation: " + reservationCompletionDTO.confirmationCode() + " not found"));
        reservation.setStatus(Status.COMPLETED);
        reservation.setEndTime(LocalDateTime.now());

        Duration duration = Duration.between(reservation.getEndTime(), reservation.getStartTime());
        long durationSeconds = duration.toSeconds();
        long maxDuration = priceService.maxDuration();

        // If it was not prepaid
        if (BigDecimal.ZERO.compareTo(reservation.getPrice()) == 0) {

            BigDecimal price = priceService.getPrice(durationSeconds / 3600, maxDuration, reservation.getUser() != null);

            reservation.setPrice(price);
        }

        reservation = reservationRepository.save(reservation);

        var spot = reservation.getSpot();
        spot.setCar(null);
        spotRepository.save(spot);

        String message = "";
        return reservationInfoMapper.toDto(true, message, durationSeconds > maxDuration * 3600, reservation);
    }

    public ReservationInfoDTO startReservation(@Valid ReservationCompletionDTO reservationCompletionDTO) {

        Reservation reservation = reservationRepository.findOneByConfirmationCodeAndStatus(reservationCompletionDTO.confirmationCode(), Status.ORDERED)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation: " + reservationCompletionDTO.confirmationCode() + " not found"));

        LocalDateTime startTime = reservation.getStartTime();
        if (LocalDateTime.now().isBefore(startTime)) {
            throw new RuntimeException("It's not your reservation time yet");
        }

        reservation.setStatus(Status.STARTED);

        reservation = reservationRepository.save(reservation);

        var spot = reservation.getSpot();
        spot.setCar(reservation.getCar());
        spotRepository.save(spot);

        String message = "Spot: " + spot.getName() + "; Flor: " + spot.getFloor().getName();
        return reservationInfoMapper.toDto(true, message, reservation);
    }

    public List<ReservationDTO2> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationDTO2::new) // Assuming ReservationDTO2 has a constructor that takes Reservation
                .collect(Collectors.toList());
    }


    public List<ReservationDTO2> findReservationsForCurrentUser() {
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            User user = userRepository.findByLogin(currentUserLogin.get())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            List<Reservation> reservations = reservationRepository.findByUser(user);
            return reservations.stream()
                    .map(reservation -> new ReservationDTO2(reservation)) // convert to DTO
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

//    public long countCarsByColor(String color) {
//        List<ReservationDTO> reservations = findAllReservations();
//        return reservations.stream()
//                .filter(reservation -> reservation.car() != null &&
//                        reservation.car().color().equalsIgnoreCase(color))
//                .count();
//    }
    public long countCarsByColor(String color) {
        return reservationRepository.countByCarColor(color);
    }


//    private void checkReservationDuration(Duration between, Long maxDuration) {
//        Duration duration = Duration.ofHours(maxDuration);
//        if (between.getSeconds() > duration.toSeconds()) {
//            throw new NotAllowedTimeException("You can't reserve more than for " + maxDuration + " hours");
//        }
//
//    }



}
