package com.example.parkinglot.web.rest;

import com.example.parkinglot.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/availableSpots")
    public Long getNumberOfAvailableSpots(@RequestParam(required = false) LocalDateTime start, @RequestParam(required = false) LocalDateTime end) {
        LocalDateTime now = LocalDateTime.now();
        if (start == null) {
            start = now;
        }
        if (end == null) {
            end =start;
        }

        return reportService.getNumberOfAvailableSpots(start, end);

    }

    @GetMapping("/revenue")
    public BigDecimal getRevenue(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return reportService.getRevenue(start, end);
    }
}
