package com.example.parkinglot.service;


import com.example.parkinglot.dto.ReservationDTO;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.exception.CarNotFoundException;
import com.example.parkinglot.exception.PaymentMethodNotFoundException;
import com.example.parkinglot.exception.SpotNotFoundException;
import com.example.parkinglot.repo.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReservationService {
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public ReservationService(UserRepository userRepository, SpotRepository spotRepository, ReservationRepository reservationRepository, CarRepository carRepository, PaymentMethodRepository paymentMethodRepository) {
        this.userRepository = userRepository;
        this.spotRepository = spotRepository;
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public ReservationDTO createReservation(@Valid ReservationDTO reservationDTO) {
        User user = userRepository.getReferenceById(reservationDTO.userId());
        Spot spot = spotRepository.getOneBySpotId(reservationDTO.spotId())
                .orElseThrow(() -> new SpotNotFoundException("Spot with id " + reservationDTO.spotId() + " was not found in the database"));
        Car car = carRepository.getOneByCarId(reservationDTO.carId())
                .orElseThrow(() -> new CarNotFoundException("Car with id " + reservationDTO.carId() + " was not found in the database"));
        PaymentMethod paymentMethod = paymentMethodRepository.findPaymentMethodByPaymentMethodId(reservationDTO.paymentMethodId())
                .orElseThrow(() -> new PaymentMethodNotFoundException("Payment method with id " + reservationDTO.paymentMethodId() + " was not found in the database"));

        

        Reservation reservation = new Reservation();
        reservation.setStartTime(reservationDTO.startTime());
        reservation.setEndTime(reservationDTO.endTime());
        reservation.setUser(user);
        reservation.setSpot(spot);
        reservation.setCar(car);
        reservation.setPaymentMethod(paymentMethod);

        reservationRepository.save(reservation);

        return reservationDTO;
    }


}
