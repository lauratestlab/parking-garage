package com.example.parkinglot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "model", length = 25, nullable = false)
    private String model;

    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "make", length = 25, nullable = false)
    private String make;

    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "color", length = 25, nullable = false)
    private String color;

    @NotNull
    @Size(min = 1)
    @Column(name = "registration", nullable = false)
    private String registration;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

}
