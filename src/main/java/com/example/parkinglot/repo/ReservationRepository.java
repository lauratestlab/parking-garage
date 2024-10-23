package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Reservation;
import com.example.parkinglot.enums.Status;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

@NamedQueries({
        @NamedQuery(name="existsAvailableSpots", query="SELECT EXISTS(" +
                " SELECT spot " +
                " FROM Spot spot LEFT JOIN Reservation reservation ON spot = reservation.spot" +
                " WHERE reservation.startTime between :startTime and :endTime AND reservation.endTime = :endTime AND reservation.status = :status)")
})
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsAvailableSpots(LocalDateTime startTime, LocalDateTime endTime);
}


