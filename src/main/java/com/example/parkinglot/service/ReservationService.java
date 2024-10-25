package com.example.parkinglot.service;


import com.example.parkinglot.dto.ReservationDTO;
import com.example.parkinglot.dto.ReservationInfoDTO;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.enums.Status;
import com.example.parkinglot.exception.CarNotFoundException;
import com.example.parkinglot.exception.NoSpotsAvailableException;
import com.example.parkinglot.exception.NotAllowedTimeException;
import com.example.parkinglot.exception.PaymentMethodNotFoundException;
import com.example.parkinglot.mapper.PaymentMethodMapper;
import com.example.parkinglot.repo.*;
import com.example.parkinglot.security.RandomUtil;
import com.example.parkinglot.security.SecurityUtils;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

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

    public ReservationService(UserRepository userRepository, PaymentService paymentService, ReservationRepository reservationRepository, CarService carService, CarRepository carRepository, PaymentMethodRepository paymentMethodRepository, PaymentMethodMapper paymentMethodMapper, ReportRepository reportRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.reservationRepository = reservationRepository;
        this.carService = carService;
        this.carRepository = carRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodMapper = paymentMethodMapper;
        this.reportRepository = reportRepository;
        this.mailService = mailService;
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
        reservation.setStatus(Status.ACTIVE);
        reservation.setConfirmationCode(RandomUtil.generateRandomAlphanumericString());

        reservationRepository.save(reservation);

        mailService.sendOrderInfoMail(user, reservation.getConfirmationCode());

        return new ReservationInfoDTO(true, "");
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
            return carService.createCar(reservationDTO.car());
        } else {
            return carRepository.getOneByCarId(reservationDTO.carId())
                    .orElseThrow(() -> new CarNotFoundException("Car with id " + reservationDTO.carId() + " was not found in the database"));
        }
    }
}
