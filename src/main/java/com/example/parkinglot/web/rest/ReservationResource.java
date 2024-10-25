package com.example.parkinglot.web.rest;

import com.example.parkinglot.dto.ReservationDTO;
import com.example.parkinglot.dto.ReservationInfoDTO;
import com.example.parkinglot.service.ReservationService;
import com.example.parkinglot.service.util.BarcodeUtils;
import com.google.zxing.WriterException;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reservation")
public class ReservationResource {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationResource.class);

    private final ReservationService reservationService;

    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationInfoDTO createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        LOG.debug("REST request to create new reservation");
        if (!isDateAcceptable(reservationDTO.startTime(), reservationDTO.endTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time must be after start time and start time must be in future");
        }
        return reservationService.createReservation(reservationDTO);
    }

    @GetMapping(value = "/qr/{confirmationCode}", produces = MediaType.IMAGE_JPEG_VALUE)
    @PermitAll
    public byte[] getReservationQR(@PathVariable String confirmationCode) {
        LOG.debug("REST request to generate qr code");
        try {
            return BarcodeUtils.generateQRCode(confirmationCode);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isDateAcceptable(LocalDateTime start, LocalDateTime end) {
        return start.isAfter(LocalDateTime.now()) && end.isAfter(start);
    }
}
