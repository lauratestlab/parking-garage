package com.example.parkinglot.web.rest;

import com.example.parkinglot.dto.ReservationCompletionDTO;
import com.example.parkinglot.dto.ReservationDTO;
import com.example.parkinglot.dto.ReservationInfoDTO;
import com.example.parkinglot.dto.ReservationStartDTO;
import com.example.parkinglot.service.ReservationService;
import com.example.parkinglot.service.util.BarcodeUtils;
import com.google.zxing.WriterException;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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

    @PostMapping("/start")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationInfoDTO startParking(@Valid @RequestBody ReservationStartDTO reservationDTO) {
        LOG.debug("REST request to register customer at the counter");
        return reservationService.startReservation(reservationDTO);
    }

    @PostMapping("/complete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ReservationInfoDTO completeParking(@Valid @RequestBody ReservationCompletionDTO reservationDTO) {
        LOG.debug("REST request to accept payment at the exit");
        return reservationService.closeReservation(reservationDTO);
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

    @GetMapping
    @PermitAll // Or @PreAuthorize("hasRole('ADMIN')")
    public List<ReservationDTO> getAllReservations() {
        LOG.debug("REST request to get all reservations");
        return reservationService.findAllReservations();
    }

    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getMyReservations() {
        LOG.debug("REST request to get reservations for current user");
        return reservationService.findReservationsForCurrentUser();
    }
    @GetMapping("/countByColor/{color}")
    public long countCarsByColor(@PathVariable String color) {
        LOG.debug("REST request to count cars by color: {}", color);
        return reservationService.countCarsByColor(color);
    }



}
