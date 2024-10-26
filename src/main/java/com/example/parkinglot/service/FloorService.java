package com.example.parkinglot.service;

import com.example.parkinglot.dto.SpotDTO;
import com.example.parkinglot.entity.Spot;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.parkinglot.dto.FloorDTO;
import com.example.parkinglot.entity.Floor;
import com.example.parkinglot.repo.FloorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FloorService {

    private FloorRepository floorRepository;

    @Autowired
    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    @Transactional
    public Floor updateFloorName(Long floorId, int newName) {
        Optional<Floor> optFloor = floorRepository.findById(floorId);
        if (optFloor.isPresent()) {
            Floor floor = optFloor.get();
            floor.setName(newName);
            return floorRepository.save(floor);
        }
        return null;
    }

    public Optional<Floor> getFloorById(Long floorId) {
        Optional<Floor> floor = floorRepository.findById(floorId);
        floor.ifPresent(f -> System.out.println("Floor found: " + f));
        return floor;

    }

    public Optional<FloorDTO> getFloorDTOById(Long floorId) {
        return floorRepository.findById(floorId)
                .map(this::convertToDTO);
    }

    private FloorDTO convertToDTO(Floor floor) {
        List<SpotDTO> spotDTOs = floor.getSpots().stream()
                .map(this::convertSpotToDTO)
                .collect(Collectors.toList());

        return new FloorDTO(floor.getFloorId(), floor.getName(), spotDTOs);
    }

    private SpotDTO convertSpotToDTO(Spot spot) {
        return new SpotDTO(spot.getSpotId(), spot.getName(), spot.getFloor().getFloorId());
    }





  //begining od L's code. Not used int the controller
//  private FloorDTO convertToDto(Floor floor) {
//        Long spotId = floor.getSpots().isEmpty() ? null : floor.getSpots().get(0).getSpotId();
//        return new FloorDTO(floor.getFloorId(), floor.getName(), spotId);
//    }

  
    private Floor convertToEntity(FloorDTO floorDTO) {
        Floor floor = new Floor();
        floor.setFloorId(floorDTO.getFloorId());
        floor.setName(floorDTO.getName());
        return floor;
    }

//    public FloorDTO saveFloor(FloorDTO floorDTO) {
//      Floor floor = convertToEntity(floorDTO);
//      Floor savedFloor = floorRepository.save(floor);
//      return convertToDto(savedFloor);
//    }
    //    public List<FloorDTO> getAllFloors() {
    //     return floorRepository.findAll().stream()
    //             .map(this::convertToDto)
    //             .collect(Collectors.toList());
    // }

    //end of L's code.
}