package com.example.parkinglot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.parkinglot.dto.FloorDTO;
import com.example.parkinglot.entity.Floor;
import com.example.parkinglot.repo.FloorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FloorSevrice {

  @Autowired 
  private FloorRepository floorRepository;
  
     private FloorDTO convertToDto(Floor floor) {
        Long spotId = floor.getSpots().isEmpty() ? null : floor.getSpots().get(0).getSpotId();
        return new FloorDTO(floor.getFloorId(), floor.getName(), spotId);
    }

  
    private Floor convertToEntity(FloorDTO floorDTO) {
        Floor floor = new Floor();
        floor.setFloorId(floorDTO.getFloorId());
        floor.setName(floorDTO.getName());
        return floor;
    }

    public FloorDTO saveFloor(FloorDTO floorDTO) {
      Floor floor = convertToEntity(floorDTO);
      Floor savedFloor = floorRepository.save(floor);
      return convertToDto(savedFloor);
    }
    //    public List<FloorDTO> getAllFloors() {
    //     return floorRepository.findAll().stream()
    //             .map(this::convertToDto)
    //             .collect(Collectors.toList());
    // }
}