package com.example.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name= "spots")
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spotId;

    private String name;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;
}
