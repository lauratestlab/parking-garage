package com.example.parkinglot.dto;

public class SpotDTO {
    private Long spotId;
    private String name;
    private Long floorId; // Assuming you return the floorId

    public SpotDTO(Long spotId, String name, Long floorId) {
        this.spotId = spotId;
        this.name = name;
        this.floorId = floorId;
    }

    // Getters and setters


    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }
}


