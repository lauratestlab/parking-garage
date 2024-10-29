//package com.example.parkinglot.web.rest;
//
//import com.example.parkinglot.dto.FloorDTO;
//import com.example.parkinglot.dto.SpotDTO;
//import com.example.parkinglot.entity.Spot;
//import com.example.parkinglot.service.SpotService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/admin/spots")
//public class SpotController {
//
//    private final SpotService spotService;
//
//
//    @Autowired
//    public SpotController(SpotService spotService) {
//        this.spotService = spotService;
//    }
//
//
//
//    @GetMapping
//    public List<SpotDTO> getAllSpotsWithLimitedFields(Pageable pageable) {
//        return spotService.getSpotsWithLimitedFields(pageable);
//    }
//
//
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
//    @GetMapping("/{id}")
//    public ResponseEntity<SpotDTO> getFloorById(@PathVariable("id") Long id) {
//        Optional<SpotDTO> spotDTO = spotService.getSpotDTOById(id);
//        return spotDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteFloor(@PathVariable("id") Long id) {
//        spotService.deleteSpot(id);
//        return ResponseEntity.noContent().build();
//    }
//
////    @GetMapping("/all")
////    public List<Spot> getAllSpots() {
////        return spotService.getSpots();
////    }
//
////    @GetMapping("/name/{name}")
////    public List<Spot> getSpotByName(@PathVariable String name) {
////        return spotService.getSpotName(name);
////    }
////    @GetMapping("/all")
////    public List<Spot> getAllSpots() {
////        return spotService.getSpots();
//    }
//
////    @GetMapping("/spots/count")
////    public long getSpotCount() {
////        return spotService.getSpotCount();
////    }
////    @GetMapping("/spots/id")
////    public Long getSpotId() {
////
////        return spotService.getSpotId();
////    }
////    @GetMapping("/spots/name/{name}")
////    public List<Spot> getSpotName(@PathVariable String name) {
////        return spotService.getSpotName(name);
////    }
////}
