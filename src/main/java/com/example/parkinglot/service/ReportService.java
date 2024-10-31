package com.example.parkinglot.service;

import com.example.parkinglot.enums.Status;
import com.example.parkinglot.repo.CriteriaBasedRepository;
import com.example.parkinglot.repo.ReservationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ReportService {


    private final CriteriaBasedRepository criteriaBasedRepository;
    private final ReservationRepository reservationRepository;
    private final PriceService priceService;


    public ReportService(CriteriaBasedRepository criteriaBasedRepository, ReservationRepository reservationRepository, PriceService priceService) {
        this.criteriaBasedRepository = criteriaBasedRepository;
        this.reservationRepository = reservationRepository;
        this.priceService = priceService;
    }


    public Long getNumberOfAvailableSpots(LocalDateTime start, LocalDateTime end) {
        Long maxDuration = priceService.maxDuration();
        return criteriaBasedRepository.numberOfAvailableSports(start, end, maxDuration);
    }

    public BigDecimal getRevenue(LocalDate start, LocalDate end) {
        return reservationRepository.revenue(Status.COMPLETED, start.atStartOfDay(),  LocalDateTime.of(end, LocalTime.MAX)).orElse(BigDecimal.ZERO);
    }
}
