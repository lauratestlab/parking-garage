package com.example.parkinglot.web.rest;

import com.example.parkinglot.dto.ReservationDTO;
import com.example.parkinglot.service.ReservationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reservation")
public class RegistrationResource {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationResource.class);

    private final ReservationService reservationService;

    public RegistrationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        LOG.debug("REST request to create new reservation");
        if (!isDateAcceptable(reservationDTO.startTime(), reservationDTO.endTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time must be after start time and start time must be in future");
        }
        return reservationService.createReservation(reservationDTO);
    }

    private boolean isDateAcceptable(LocalDateTime start, LocalDateTime end) {
        return start.isAfter(LocalDateTime.now()) && end.isAfter(start);
    }
}
