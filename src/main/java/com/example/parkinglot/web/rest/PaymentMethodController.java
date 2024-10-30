package com.example.parkinglot.web.rest;

import com.example.parkinglot.dto.PaymentMethodDTO;
import com.example.parkinglot.service.PaymentMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
@CrossOrigin
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public List<PaymentMethodDTO> retrieveAllPaymentMethods() {
        return this.paymentMethodService.getAllPaymentMethods();
    }


    @GetMapping("/{paymentMethodId}")
    public PaymentMethodDTO getPaymentMethodById(@PathVariable Long paymentMethodId) {
        return paymentMethodService.getPaymentMethod(paymentMethodId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodDTO addPaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO) {
        return paymentMethodService.createPaymentMethod(paymentMethodDTO);
    }
}
