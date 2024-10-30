//package com.example.parkinglot.web.rest;
//
//import com.example.parkinglot.service.ReportService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@RestController
//@RequestMapping("/api/admin/settings")
//public class settingResource {
//
//    private final ReportService reportService;
//
//    public settingResource(ReportService reportService) {
//        this.reportService = reportService;
//    }
//
//    @GetMapping("/price")
//    public Long getNumberOfAvailableSpots(@RequestParam(required = false) LocalDateTime start, @RequestParam(required = false) LocalDateTime end) {
//        LocalDateTime now = LocalDateTime.now();
//        if (start == null) {
//            start = now;
//        }
//        if (end == null) {
//            end =start;
//        }
//
//        return reportService.getNumberOfAvailableSpots(start, end);
//
//    }
//
//    @GetMapping("/price")
//    public BigDecimal getRevenue(@RequestParam LocalDate start, @RequestParam LocalDate end) {
//        return reportService.getRevenue(start, end);
//    }
//
//
//}
