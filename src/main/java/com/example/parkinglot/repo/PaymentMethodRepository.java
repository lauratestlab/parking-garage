package com.example.parkinglot.repo;

import com.example.parkinglot.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findPaymentMethodByPaymentMethodId(Long id);
}






