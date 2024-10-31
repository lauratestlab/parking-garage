package com.example.parkinglot.service;

import com.example.parkinglot.entity.Price;
import com.example.parkinglot.repo.GarageRepository;
import com.example.parkinglot.repo.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    PriceRepository priceRepository;

    @Mock
    GarageRepository garageRepository;

    PriceService priceService;

    @BeforeEach
    void setUp() {
        priceService = new PriceService(priceRepository, garageRepository);
    }

    @Test
    void getPriceFor8Hours() {
        long maxDuration = 24L;
        long duration = 8L;

        var price = new Price();
        price.setPrice(new BigDecimal("12.00"));
        price.setDuration(24L);

        when(priceRepository.findFirstByDurationGreaterThanEqualOrderByDurationAsc(duration)).thenReturn(Optional.of(price));

        BigDecimal result = priceService.getPrice(duration, maxDuration, false);
        assertThat(result).isEqualTo("12.00");
    }

    @Test
    void getPriceFor24ours() {
        long maxDuration = 24L;
        long duration = 24L;

        var price = new Price();
        price.setPrice(new BigDecimal("12.00"));
        price.setDuration(24L);

        when(priceRepository.findFirstByDurationGreaterThanEqualOrderByDurationAsc(duration)).thenReturn(Optional.of(price));

        BigDecimal result = priceService.getPrice(duration, maxDuration, false);
        assertThat(result).isEqualTo("12.00");
    }

    @Test
    void getPriceFor26ours() {
        long maxDuration = 24L;
        long duration = 26L;

        var price = new Price();
        price.setPrice(new BigDecimal("12.00"));
        price.setDuration(24L);

        when(priceRepository.findFirstByDurationGreaterThanEqualOrderByDurationAsc(maxDuration)).thenReturn(Optional.of(price));

        BigDecimal result = priceService.getPrice(duration, maxDuration, false);
        assertThat(result).isEqualTo("24.00");
    }

    @Test
    void getPriceFor49ours() {
        long maxDuration = 24L;
        long duration = 49L;

        var price = new Price();
        price.setPrice(new BigDecimal("12.00"));
        price.setDuration(24L);

        when(priceRepository.findFirstByDurationGreaterThanEqualOrderByDurationAsc(maxDuration)).thenReturn(Optional.of(price));

        BigDecimal result = priceService.getPrice(duration, maxDuration, false);
        assertThat(result).isEqualTo("36.00");
    }
}