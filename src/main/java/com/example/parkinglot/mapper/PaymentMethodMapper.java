package com.example.parkinglot.mapper;

import com.example.parkinglot.dto.PaymentMethodDTO;
import com.example.parkinglot.entity.PaymentMethod;

public class PaymentMethodMapper {
    public static PaymentMethodDTO mapToDTO(PaymentMethod paymentMethod) {
        return new PaymentMethodDTO(
                paymentMethod.getExpirationDate(),
                paymentMethod.getCcv(),
                paymentMethod.getFullName(),
                paymentMethod.getDeliveryStreet(),
                paymentMethod.getDeliveryCity(),
                paymentMethod.getDeliveryState(),
                paymentMethod.getDeliveryZip(),
                paymentMethod.getCard_number(),
                paymentMethod.getPaymentMethodId()
        );
    }
}
