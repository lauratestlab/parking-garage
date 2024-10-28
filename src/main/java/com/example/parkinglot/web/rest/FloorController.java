package com.example.parkinglot.web.rest;

import com.example.parkinglot.dto.AdminUserDTO;
import com.example.parkinglot.dto.FloorDTO;
import com.example.parkinglot.entity.Floor;
import com.example.parkinglot.security.AuthoritiesConstants;
import com.example.parkinglot.service.FloorService;
import com.example.parkinglot.web.util.PaginationUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class FloorController {

    private static final Logger LOG = LoggerFactory.getLogger(FloorController.class);

    private final FloorService floorService;


    @Autowired
    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{floorId}")
    public ResponseEntity<Floor> updateFloorName(
            @PathVariable("floorId") Long floorId,
            @RequestParam("name") String newName) {
        Floor updatedFloor = floorService.updateFloorName(floorId, newName);
        if (updatedFloor != null) {
            return ResponseEntity.ok(updatedFloor);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/floors")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<FloorDTO>> getAllUsers(Pageable pageable) {
        LOG.debug("REST request to get all Floors");

        final Page<FloorDTO> page = floorService.getAllFloors(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/{floorId}")
    public ResponseEntity<FloorDTO> getFloorById(@PathVariable("floorId") Long floorId) {
        Optional<FloorDTO> floorDTO = floorService.getFloorDTOById(floorId);
        return floorDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/floors")
    public ResponseEntity<Floor> addFloor(@Valid @RequestBody FloorDTO floorDTO) {
        Floor createdFloor = floorService.addFloor(floorDTO);
        return new ResponseEntity<>(createdFloor, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
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
