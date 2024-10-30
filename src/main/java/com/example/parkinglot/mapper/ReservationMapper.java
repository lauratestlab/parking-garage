package com.example.parkinglot.mapper;

import com.example.parkinglot.dto.ReservationDTO;
import com.example.parkinglot.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {

    @Mapping(target = "userId", source = "reservation.user.id")
    @Mapping(target = "spotId", source = "reservation.spot.id")
    @Mapping(target = "carId", source = "reservation.car.id")
    ReservationDTO toDto(Reservation reservation);

}
