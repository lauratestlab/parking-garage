package com.example.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;

@Entity
@Data
@Table(name= "prices")
public class Price {

    @Id
    private int duration;

    private BigInteger price;
}
