package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Reservation;
import com.example.parkinglot.entity.User;
import com.example.parkinglot.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

    @Query("SELECT COUNT(res) FROM Reservation res JOIN res.car c WHERE LOWER(c.color) = LOWER(:color)")
    long countByCarColor(String color);


    @Query("SELECT SUM(reservation.price) FROM Reservation reservation WHERE reservation.status = :status AND reservation.startTime BETWEEN :start AND :end")
    Optional<BigDecimal> revenue(Status status, LocalDateTime start, LocalDateTime end);

    Optional<Reservation> findOneByConfirmationCodeAndStatus(String confirmationCode, Status status);
}