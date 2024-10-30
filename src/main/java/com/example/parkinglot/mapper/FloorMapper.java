package com.example.parkinglot.mapper;

import com.example.parkinglot.dto.SpotDTO;
import com.example.parkinglot.entity.Floor;
import com.example.parkinglot.dto.FloorDTO;
import com.example.parkinglot.entity.Spot;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Floor} and its DTO {@link FloorDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloorMapper extends EntityMapper<FloorDTO, Floor> {}
