package com.example.parkinglot.mapper;

import com.example.parkinglot.entity.Floor;
import com.example.parkinglot.dto.FloorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Floor} and its DTO {@link FloorDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloorMapper extends EntityMapper<FloorDTO, Floor> {}
