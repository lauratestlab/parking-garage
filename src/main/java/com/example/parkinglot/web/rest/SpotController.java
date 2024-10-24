package com.example.parkinglot.web.rest;

import com.example.parkinglot.dto.SpotDTO;
import com.example.parkinglot.entity.Spot;
import com.example.parkinglot.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/spots")
public class SpotController {

    private final SpotService spotService;


    @Autowired
    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }



    @GetMapping("/limited")
    public List<SpotDTO> getAllSpotsWithLimitedFields() {
        return spotService.getSpotsWithLimitedFields();
    }
//    @GetMapping("/all")
//    public List<Spot> getAllSpots() {
//        return spotService.getSpots();
//    }

//    @GetMapping("/name/{name}")
//    public List<Spot> getSpotByName(@PathVariable String name) {
//        return spotService.getSpotName(name);
//    }
//    @GetMapping("/all")
//    public List<Spot> getAllSpots() {
//        return spotService.getSpots();
    }

//    @GetMapping("/spots/count")
//    public long getSpotCount() {
//        return spotService.getSpotCount();
//    }
//    @GetMapping("/spots/id")
//    public Long getSpotId() {
//
//        return spotService.getSpotId();
//    }
//    @GetMapping("/spots/name/{name}")
//    public List<Spot> getSpotName(@PathVariable String name) {
//        return spotService.getSpotName(name);
//    }
//}
