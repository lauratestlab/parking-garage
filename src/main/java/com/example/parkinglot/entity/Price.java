package com.example.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name= "prices")
public class Price {

    @Id
    private int duration;

    private BigDecimal price;
}
