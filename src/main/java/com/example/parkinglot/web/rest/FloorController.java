package com.example.parkinglot.web.rest;

import com.example.parkinglot.dto.FloorDTO;
import com.example.parkinglot.entity.Floor;
import com.example.parkinglot.repo.FloorRepository;
import com.example.parkinglot.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/floors")
public class FloorController {

    private final FloorService floorService;
    private final FloorRepository floorRepository;


    @Autowired
    public FloorController(FloorService floorService, FloorRepository floorRepository) {
        this.floorService = floorService;
        this.floorRepository = floorRepository;
    }

    @PutMapping("/update/{floorId}")
    public ResponseEntity<Floor> updateFloorName(
            @PathVariable("floorId") Long floorId,
            @RequestParam("name") int newName) {
        Floor updatedFloor = floorService.updateFloorName(floorId, newName);
        if (updatedFloor != null) {
            return ResponseEntity.ok(updatedFloor);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{floorId}")
    public ResponseEntity<FloorDTO> getFloorById(@PathVariable("floorId") Long floorId) {
        Optional<FloorDTO> floorDTO = floorService.getFloorDTOById(floorId);
        return floorDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/add")
    public ResponseEntity<Floor> addFloor(@RequestParam("name") int name) {
        Floor floor = new Floor();
        floor.setName(name);  // Set the floor name from the request parameter
        Floor createdFloor = floorService.addFloor(floor);
        return new ResponseEntity<>(createdFloor, HttpStatus.CREATED);
    }



    @DeleteMapping("/{floorId}")
    public ResponseEntity<Void> deleteFloor(@PathVariable("floorId") Long floorId) {
        floorService.deleteFloor(floorId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/{floorId}")
//    public ResponseEntity<Floor> getFloorById(@PathVariable("floorId") Long floorId) {
//        Optional<Floor> floor = floorService.getFloorById(floorId);
//        return floor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

// below for debugging puposes. circular reference preference spot ---> floors
//    @GetMapping("/{floorId}")
//    public ResponseEntity<Floor> getFloorById(@PathVariable("floorId") Long floorId) {
//        try {
//            Optional<Floor> floor = floorService.getFloorById(floorId);
//            return floor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//        } catch (Exception e) {
//            e.printStackTrace(); // Or use logger for a production app
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }



}
