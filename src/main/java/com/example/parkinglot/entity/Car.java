package com.example.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;
    //private Long userId;
    private String model;
    private String make;
    private String color;
    private String registration;

    // Many Cars can belong to one User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Foreign key column in Car table
    private User user;

}
