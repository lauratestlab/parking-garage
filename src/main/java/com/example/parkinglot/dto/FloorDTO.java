package com.example.parkinglot.dto;

import java.util.List;

public class FloorDTO {
  private Long floorId;
  private int name;
  private List<SpotDTO> spots;

  public FloorDTO(Long floorId, int name, List<SpotDTO> spots) {
    this.floorId = floorId;
    this.name = name;
    this.spots = spots;
  }

  public Long getFloorId() {
    return floorId;
  }

  public void setFloorId(Long floorId) {
    this.floorId = floorId;
  }

  public int getName() {
    return name;
  }

  public void setName(int name) {
    this.name = name;
  }

  public List<SpotDTO> getSpots() {
    return spots;
  }

  public void setSpots(List<SpotDTO> spots) {
    this.spots = spots;
  }
}
