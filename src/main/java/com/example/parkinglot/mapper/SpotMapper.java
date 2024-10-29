package com.example.parkinglot.mapper;

import com.example.parkinglot.entity.Floor;
import com.example.parkinglot.entity.Spot;
import com.example.parkinglot.dto.FloorDTO;
import com.example.parkinglot.dto.SpotDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Spot} and its DTO {@link SpotDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpotMapper extends EntityMapper<SpotDTO, Spot> {
    @Mapping(target = "floor", source = "floor", qualifiedByName = "floorName")
    SpotDTO toDto(Spot s);

    @Named("floorName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    FloorDTO toDtoFloorName(Floor floor);
}
