package com.example.parkinglot.mapper;

import com.example.parkinglot.dto.ReservationInfoDTO;
import com.example.parkinglot.entity.Reservation;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationInfoMapper extends EntityMapper<ReservationInfoDTO, Reservation> {

    default ReservationInfoDTO toDto(boolean success, String message, Reservation reservation) {
        return toDto(success, message, false, reservation);
    }

    default ReservationInfoDTO toDto(boolean success, String message, boolean overstay, Reservation reservation) {
        return new ReservationInfoDTO(
                success,
                overstay,
                message,
                reservation.getConfirmationCode(),
                reservation.getUser() == null ? null : reservation.getUser().getId(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getSpot() == null ? null : reservation.getSpot().getId(),
                reservation.getCar() == null ? null : reservation.getCar().getId(),
                reservation.getPrice()
        );
    }

}
