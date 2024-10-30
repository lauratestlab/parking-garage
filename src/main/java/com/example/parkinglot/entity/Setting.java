package com.example.parkinglot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@Table(name = "settings")
@NoArgsConstructor
public class Setting {

    @Id
    @NotNull
    private String key;

    private String value;
}
