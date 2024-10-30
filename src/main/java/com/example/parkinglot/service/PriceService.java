package com.example.parkinglot.service;

import com.example.parkinglot.config.Constants;
import com.example.parkinglot.entity.Price;
import com.example.parkinglot.repo.GarageRepository;
import com.example.parkinglot.repo.PriceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.max;
import static org.apache.commons.lang3.ObjectUtils.min;

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

    public Long maxDuration() {
        return priceRepository.maxDuration()
                .orElseThrow(() -> new RuntimeException("Prices are not set"));
    }

    public BigDecimal getPrice(long hours, long maxDuration, boolean applyDiscount) {

        BigDecimal price = priceRepository.findFirstByDurationGreaterThanEqualOrderByDurationAsc(min(hours, maxDuration))
                .orElseThrow(RuntimeException::new)
                .getPrice();

        if (applyDiscount) {
            BigDecimal discount = garageRepository.memberDiscount(Constants.DEFAULT_GARAGE_ID)
                    .orElseThrow(RuntimeException::new);
            price = price.multiply(new BigDecimal("100").subtract(discount).divide(new BigDecimal("100")));
        }

        return price;
    }
}
