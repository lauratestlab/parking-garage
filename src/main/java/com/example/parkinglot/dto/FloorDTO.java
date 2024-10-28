package com.example.parkinglot.dto;

import java.util.List;

public class FloorDTO {
  private Long id;
  private String name;
  private List<SpotDTO> spots;

  public FloorDTO(Long floorId, String name, List<SpotDTO> spots) {
    this.id = floorId;
    this.name = name;
    this.spots = spots;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<SpotDTO> getSpots() {
    return spots;
  }

  public void setSpots(List<SpotDTO> spots) {
    this.spots = spots;
  }
}
