package com.example.parkinglot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name= "spots")
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "floor_id")
    @NotNull
    @JsonIgnoreProperties(value = { "spots" }, allowSetters = true)
    private Floor floor;

}
