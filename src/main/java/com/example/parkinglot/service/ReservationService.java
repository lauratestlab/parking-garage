package com.example.parkinglot.service;


import com.example.parkinglot.dto.ReservationCompletionDTO;
import com.example.parkinglot.dto.ReservationDTO;
import com.example.parkinglot.dto.ReservationInfoDTO;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.enums.Status;
import com.example.parkinglot.exception.*;
import com.example.parkinglot.mapper.CarMapper;
import com.example.parkinglot.mapper.PaymentMethodMapper;
import com.example.parkinglot.mapper.ReservationMapper;
import com.example.parkinglot.repo.*;
import com.example.parkinglot.security.RandomUtil;
import com.example.parkinglot.security.SecurityUtils;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public ReservationService(UserRepository userRepository, PaymentService paymentService, ReservationRepository reservationRepository, CarService carService, CarRepository carRepository, PaymentMethodRepository paymentMethodRepository, PaymentMethodMapper paymentMethodMapper, ReportRepository reportRepository, MailService mailService, CarMapper carMapper, PriceService priceService, ReservationMapper reservationMapper) {
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
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ReservationInfoDTO createReservation(@Valid ReservationDTO reservationDTO) {
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();

        User user = userRepository.getReferenceById(reservationDTO.userId());

        PaymentMethod paymentMethod = processPayment(reservationDTO);
        paymentService.processPayment(paymentMethod);

        if (currentUserLogin.isPresent() && reservationDTO.saveCreditCard()) {
            paymentMethod.setUser(user);
            paymentMethodRepository.save(paymentMethod);
        }

        Spot spot;
        try {
            spot = reportRepository.findFirstAvailableSpot(reservationDTO.startTime(), reservationDTO.endTime());
        } catch (NoResultException e) {
            return new ReservationInfoDTO(false, "No spots available");
        }

        Reservation reservation = new Reservation();
        reservation.setStartTime(reservationDTO.startTime());
        reservation.setEndTime(reservationDTO.endTime());
        reservation.setUser(user);
        reservation.setSpot(spot);
        reservation.setCar(registerCar(reservationDTO));
        reservation.setStatus(Status.ORDERED);
        reservation.setConfirmationCode(RandomUtil.generateRandomAlphanumericString());

        reservationRepository.save(reservation);

        mailService.sendOrderInfoMail(user, reservation.getConfirmationCode());

        return new ReservationInfoDTO(true, "");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ReservationInfoDTO startReservation(@Valid ReservationDTO reservationDTO) {
        Spot spot;
        try {
            spot = reportRepository.findFirstAvailableSpot(reservationDTO.startTime(), reservationDTO.endTime());
        } catch (NoResultException e) {
            return new ReservationInfoDTO(false, "No spots available");
        }

        User user = null;
        if (reservationDTO.email() != null) {
            user = userRepository.findOneByEmailIgnoreCase(reservationDTO.email()).orElseThrow(() -> new UserNotFoundException("Couldn't find a member with the given email: " + reservationDTO.email()));
        }

        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalDateTime.now());
        reservation.setSpot(spot);
        reservation.setCar(registerCar(reservationDTO));
        reservation.setStatus(Status.STARTED);
        reservation.setConfirmationCode(RandomUtil.generateRandomAlphanumericString());

        reservationRepository.save(reservation);

        if (user != null) {
            mailService.sendOrderInfoMail(user, reservation.getConfirmationCode());
        }

        return new ReservationInfoDTO(true, "");
    }

    public ReservationDTO closeReservation(@Valid ReservationCompletionDTO reservationCompletionDTO) {

        Reservation reservation = reservationRepository.findOneByConfirmationCode(reservationCompletionDTO.confirmationCode())
                .orElseThrow(() -> new ReservationNotFoundException("Reservation: " + reservationCompletionDTO.confirmationCode() + " not found"));
        reservation.setStatus(Status.COMPLETED);
        reservation.setEndTime(LocalDateTime.now());

        Duration duration = Duration.between(reservation.getEndTime(), reservation.getStartTime());
        long hours = duration.toHours();
        BigDecimal price = priceService.getPrice(hours);

        reservation.setPrice(price);

        reservation = reservationRepository.save(reservation);

        return reservationMapper.toDto(reservation);
    }

    private PaymentMethod processPayment(ReservationDTO reservationDTO) {
        if (Objects.nonNull(reservationDTO.paymentMethod())) {
            return paymentMethodMapper.paymentMethodDTOToPaymentMethod(reservationDTO.paymentMethod());
        } else {
            return paymentMethodRepository.findPaymentMethodByPaymentMethodId(reservationDTO.paymentMethodId())
                    .orElseThrow(() -> new PaymentMethodNotFoundException("Payment method with id " + reservationDTO.paymentMethodId() + " was not found in the database"));
        }

    }

    private Car registerCar(ReservationDTO reservationDTO) {
        if (reservationDTO.car() != null) {
            return carMapper.toEntity(carService.save(reservationDTO.car()));
        } else {
            return carRepository.findByUserIsCurrentUserAndId(reservationDTO.carId())
                    .orElseThrow(() -> new CarNotFoundException("Car with id " + reservationDTO.carId() + " was not found in the database"));
        }
    }

    public List<ReservationDTO> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationDTO::new) // Assuming ReservationDTO2 has a constructor that takes Reservation
                .collect(Collectors.toList());
    }


    public List<ReservationDTO> findReservationsForCurrentUser() {
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isPresent()) {
            User user = userRepository.findByLogin(currentUserLogin.get())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            List<Reservation> reservations = reservationRepository.findByUser(user);
            return reservations.stream()
                    .map(reservation -> new ReservationDTO(reservation)) // convert to DTO
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
