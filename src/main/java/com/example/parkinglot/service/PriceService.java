package com.example.parkinglot.service;

import com.example.parkinglot.entity.Price;
import com.example.parkinglot.repo.PriceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Transactional
    public Price updatePriceByDurationAndValue(int duration, BigDecimal newPrice) {
        Optional<Price> optPrice = priceRepository.findByDuration(duration);
        if (optPrice.isPresent()) {
            Price price = optPrice.get();
            price.setPrice(newPrice);
            return priceRepository.save(price);
        }
        return null;
    }
}
