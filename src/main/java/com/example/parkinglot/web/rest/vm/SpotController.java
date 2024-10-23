package com.example.parkinglot.web.rest.vm;

import com.example.parkinglot.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spots")
public class SpotController {

    private final SpotService spotService;


    @Autowired
    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/spots/count")
    public long getSpotCount() {
        return spotService.getSpotCount();
    }
    @GetMapping("/spots/id")
    public Long getSpotId() {

        return spotService.getSpotId();
    }
    @GetMapping("/spots/name")
    public String getSpotName() {
        return spotService.getSpotByName();
    }
}
