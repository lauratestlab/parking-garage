package com.example.parkinglot.mapper;

import com.example.parkinglot.dto.ReservationDTO;
import com.example.parkinglot.entity.Reservation;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {

    ReservationDTO toDto(Reservation reservation);

}
