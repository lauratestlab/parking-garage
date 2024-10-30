package com.example.parkinglot.service;

import com.example.parkinglot.config.Constants;
import com.example.parkinglot.entity.Price;
import com.example.parkinglot.repo.GarageRepository;
import com.example.parkinglot.repo.PriceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceRepository priceRepository;
    private final GarageRepository garageRepository;

    public PriceService(PriceRepository priceRepository, GarageRepository garageRepository) {
        this.priceRepository = priceRepository;
        this.garageRepository = garageRepository;
    }

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

    public BigDecimal maxDuration(long hours, boolean applyDiscount) {
        BigDecimal price = priceRepository.findFirstByDurationGreaterThanEqualOrderByDurationAsc(hours)
                .orElseThrow(RuntimeException::new)
                .getPrice();

        if (applyDiscount) {
            BigDecimal discount = garageRepository.memberDiscount(Constants.DEFAULT_GARAGE_ID)
                    .orElseThrow(RuntimeException::new);
            price = price.multiply(new BigDecimal("100").subtract(discount));
        }

        return price;
    }

    public BigDecimal getPrice(long hours, boolean applyDiscount) {
        BigDecimal price = priceRepository.findFirstByDurationGreaterThanEqualOrderByDurationAsc(hours)
                .orElseThrow(RuntimeException::new)
                .getPrice();

        if (applyDiscount) {
            BigDecimal discount = garageRepository.memberDiscount(Constants.DEFAULT_GARAGE_ID)
                    .orElseThrow(RuntimeException::new);
            price = price.multiply(new BigDecimal("100").subtract(discount));
        }

        return price;
    }
}
