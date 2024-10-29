package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Reservation;
import com.example.parkinglot.entity.User;
import com.example.parkinglot.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

    @Query("SELECT COUNT(res) FROM Reservation res JOIN res.car c WHERE LOWER(c.color) = LOWER(:color)")
    long countByCarColor(String color);


    @Query("SELECT EXISTS(" +
            " SELECT spot " +
            " FROM Spot spot LEFT JOIN Reservation reservation ON spot = reservation.spot " +
            "   AND reservation.startTime < :endTime AND reservation.endTime > :startTime " +
            "   AND reservation.status = :status" +
            " WHERE reservation IS NULL" +
            ")"
    )
    boolean existsAvailableSpots(LocalDateTime startTime, LocalDateTime endTime, Status status);


    @Query("SELECT SUM(reservation.price) FROM Reservation reservation WHERE reservation.status = :status AND reservation.startTime BETWEEN :start AND :end")
    Optional<BigDecimal> revenue(Status status, LocalDateTime start, LocalDateTime end);

    Optional<Reservation> findOneByConfirmationCode(String confirmationCode);
}