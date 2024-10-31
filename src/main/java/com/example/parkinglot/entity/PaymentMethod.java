package com.example.parkinglot.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

@Entity
@Data
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long id;

    @NotNull
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$"
            , message = "Must be formatted MM/YY")
    @Column(name = "expiration_date", length = 5, nullable = false)
    private String expirationDate;

    @NotNull
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    @Column(name = "ccv", nullable = false)
    private int ccv;

    @NotNull
    @CreditCardNumber(message="Not a valid credit card number")
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @NotNull
    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotBlank(message="Street is required")
    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @NotBlank(message="City is required")
    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank(message="State is required")
    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @NotBlank(message="Zip code is required")
    @NotNull
    @Column(name = "zip", nullable = false)
    private String zip;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;
}
