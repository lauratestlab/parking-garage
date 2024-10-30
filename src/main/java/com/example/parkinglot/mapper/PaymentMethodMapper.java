package com.example.parkinglot.mapper;

import com.example.parkinglot.dto.PaymentMethodDTO;
import com.example.parkinglot.entity.PaymentMethod;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodDTO paymentMethodToPaymentMethodDTO(PaymentMethod paymentMethod);

    PaymentMethod paymentMethodDTOToPaymentMethod(PaymentMethodDTO paymentMethod);

    List<PaymentMethodDTO> paymentMethodsToPaymentMethodDTOs(List<PaymentMethod> paymentMethods);

    List<PaymentMethod> paymentMethodDTOsToPaymentMethods(List<PaymentMethodDTO> paymentMethods);

}
