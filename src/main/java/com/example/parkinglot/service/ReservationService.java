package com.example.parkinglot.service;


import com.example.parkinglot.config.Constants;
import com.example.parkinglot.dto.*;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.enums.Status;
import com.example.parkinglot.exception.CarNotFoundException;
import com.example.parkinglot.exception.PaymentMethodNotFoundException;
import com.example.parkinglot.exception.ReservationNotFoundException;
import com.example.parkinglot.exception.UserNotFoundException;
import com.example.parkinglot.mapper.CarMapper;
import com.example.parkinglot.mapper.PaymentMethodMapper;
import com.example.parkinglot.mapper.ReservationInfoMapper;
import com.example.parkinglot.mapper.ReservationMapper;
import com.example.parkinglot.repo.*;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final ReservationRepository reservationRepository;
    private final CarService carService;
    private final CarRepository carRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;
    private final ReportRepository reportRepository;
    private final MailService mailService;
    private final CarMapper carMapper;
    private final PriceService priceService;
    private final ReservationMapper reservationMapper;
    private final SpotRepository spotRepository;
    private final ReservationInfoMapper reservationInfoMapper;

    public ReservationService(UserRepository userRepository, PaymentService paymentService, ReservationRepository reservationRepository, CarService carService, CarRepository carRepository, PaymentMethodRepository paymentMethodRepository, PaymentMethodMapper paymentMethodMapper, ReportRepository reportRepository, MailService mailService, CarMapper carMapper, PriceService priceService, ReservationMapper reservationMapper, SpotRepository spotRepository, ReservationInfoMapper reservationInfoMapper) {
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.reservationRepository = reservationRepository;
        this.carService = carService;
        this.carRepository = carRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodMapper = paymentMethodMapper;
        this.reportRepository = reportRepository;
        this.mailService = mailService;
        this.carMapper = carMapper;
        this.priceService = priceService;
        this.reservationMapper = reservationMapper;
        this.spotRepository = spotRepository;
        this.reservationInfoMapper = reservationInfoMapper;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ReservationInfoDTO createReservation(@Valid ReservationDTO reservationDTO) {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM);

        User user = userRepository.findOneByLogin(currentUserLogin).orElse(null);

        PaymentMethod paymentMethod = processPayment(reservationDTO);
        paymentService.processPayment(paymentMethod);

        if (reservationDTO.saveCreditCard()) {
            paymentMethod.setUser(user);
            paymentMethodRepository.save(paymentMethod);
        }

        Reservation reservation = new Reservation();

        Spot spot;
        try {
            spot = reportRepository.findFirstAvailableSpot(reservationDTO.startTime(), reservationDTO.endTime(), priceService.maxDuration());
        } catch (NoResultException e) {
            return reservationInfoMapper.toDto(false, "No spots available", reservation);
        }
        Car car = registerCar(reservationDTO.carId(), reservationDTO.car());

        reservation.setStartTime(reservationDTO.startTime());
        reservation.setEndTime(reservationDTO.endTime());
        reservation.setUser(user);
        reservation.setSpot(spot);
        reservation.setCar(car);
        reservation.setStatus(Status.ORDERED);
        reservation.setConfirmationCode(RandomUtil.generateRandomAlphanumericString());

        reservationRepository.save(reservation);

        mailService.sendOrderInfoMail(user, reservation.getConfirmationCode());

        String message = "Spot: " + spot.getName() + "; Flor: " + spot.getFloor().getName();
        return reservationInfoMapper.toDto(true, message, reservation);
    }

    public ReservationInfoDTO startReservation(@Valid ReservationStartDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalDateTime.now());

        Long maxDurationInHours = priceService.maxDuration();

        Spot spot;
        try {
            spot = reportRepository.findFirstAvailableSpot(reservation.getStartTime(), maxDurationInHours);
        } catch (NoResultException e) {
            return reservationInfoMapper.toDto(false, "No spots available", reservation);
        }
        Car car = registerCar(null, new CarDTO(null, reservationDTO.model(), reservationDTO.make(), reservationDTO.color(), reservationDTO.registration(), null));
        reservation.setSpot(spot);

        User user = null;
        if (reservationDTO.email() != null) {
            user = userRepository.findOneByEmailIgnoreCase(reservationDTO.email()).orElseThrow(() -> new UserNotFoundException("Couldn't find a member with the given email: " + reservationDTO.email()));
        }

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
        long hours = duration.toHours();

        Long maxDuration = priceService.maxDuration();
        BigDecimal price = priceService.getPrice(hours,  maxDuration, reservation.getUser() != null);

        reservation.setPrice(price);

        reservation = reservationRepository.save(reservation);

        String message = "";
        return reservationInfoMapper.toDto(true, message, hours > maxDuration, reservation);
    }

    private PaymentMethod processPayment(ReservationDTO reservationDTO) {
        if (Objects.nonNull(reservationDTO.paymentMethod())) {
            return paymentMethodMapper.paymentMethodDTOToPaymentMethod(reservationDTO.paymentMethod());
        } else {
            return paymentMethodRepository.findPaymentMethodById(reservationDTO.paymentMethodId())
                    .orElseThrow(() -> new PaymentMethodNotFoundException("Payment method with id " + reservationDTO.paymentMethodId() + " was not found in the database"));
        }
    }

    private Car registerCar(Long carId, CarDTO car) {
        if (car != null) {
            return carMapper.toEntity(carService.save(car));
        } else {
            return carRepository.findByUserIsCurrentUserAndId(carId)
                    .orElseThrow(() -> new CarNotFoundException("Car with id " + carId + " was not found in the database"));
        }
    }

    public List<ReservationDTO> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(reservationMapper::toDto) // Assuming ReservationDTO2 has a constructor that takes Reservation
                .collect(Collectors.toList());
    }


    public List<ReservationDTO> findReservationsForCurrentUser() {
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            User user = userRepository.findByLogin(currentUserLogin.get())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            List<Reservation> reservations = reservationRepository.findByUser(user);
            return reservations.stream()
                    .map(reservationMapper::toDto) // convert to DTO
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




}
