package com.example.parkinglot.dto;

public class FloorDTO {
  private Long floorId;
  private int name;
  private Long spotId;

  public FloorDTO(Long floorId, int name, Long spotId) {
    this.floorId = floorId;
    this.name = name;
    this.spotId = spotId;
  }

  public Long getFloorId() {
    return floorId;
  }

  public void setFloorId() {
    this.floorId = floorId;
  }

  public int getName() {
    return name;
  }

  public void setName() {
    this.name = name;
  }

  public Long getSpotId() {
    return spotId;
  }

  public void setSpotId() {
    this.floorId = spotId;
  }
}
