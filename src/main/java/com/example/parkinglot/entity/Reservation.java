package com.example.parkinglot.entity;

import com.example.parkinglot.enums.Status;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "reservations")
public class Reservation extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Timestamp
    private LocalDateTime startTime;

    @Timestamp
    private LocalDateTime endTime;

    private BigDecimal price = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @Enumerated(EnumType.STRING)
    private Status status = Status.STARTED;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private String confirmationCode;

    @Override
    public Long getId() {
        return id;
    }
}
