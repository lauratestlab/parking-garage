package com.example.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Data
@Table(name= "prices")
public class Price {

    @Id
    private long duration;

    private BigDecimal price;
}
