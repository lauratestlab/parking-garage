package com.example.parkinglot.service;

import com.example.parkinglot.dto.PaymentMethodDTO;
import com.example.parkinglot.entity.PaymentMethod;
import com.example.parkinglot.repo.PaymentMethodRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.parkinglot.mapper.PaymentMethodMapper;

import java.util.List;

@Service
@Transactional
public class PaymentMethodServiceImpl implements PaymentMethodService{

    private final Logger logger = LoggerFactory.getLogger(PaymentMethodServiceImpl.class);

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }


    @Override
    public List<PaymentMethodDTO> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = (List<PaymentMethod>) paymentMethodRepository.findAll();
        return paymentMethods.stream()
                .map(PaymentMethodMapper::mapToDTO)
                .toList();
    }

    @Override
    public PaymentMethodDTO getPaymentMethod(Long paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId)
                .map(PaymentMethodMapper::mapToDTO)
                .orElse(null);
    }

    @Override
    public PaymentMethodDTO createPaymentMethod(PaymentMethodDTO paymentMethodDTO) {
//        new PaymentMethod(expirationDate,
//                ccv, fullName, deliveryStreet, deliveryCity, deliveryState, deliveryZip, card_number, paymentMethodId)
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setExpirationDate(paymentMethodDTO.expirationDate());
        paymentMethod.setCcv(paymentMethodDTO.ccv());
        paymentMethod.setFullName(paymentMethodDTO.fullName());
        paymentMethod.setDeliveryStreet(paymentMethodDTO.deliveryStreet());
        paymentMethod.setDeliveryCity(paymentMethodDTO.deliveryCity());
        paymentMethod.setDeliveryState(paymentMethodDTO.deliveryState());
        paymentMethod.setDeliveryZip(paymentMethodDTO.deliveryZip());
        paymentMethod.setCard_number(paymentMethodDTO.card_number());

        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return PaymentMethodMapper.mapToDTO(paymentMethod);

    }
}

