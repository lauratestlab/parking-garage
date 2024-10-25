package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Floor;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface FloorRepository extends CrudRepository<Floor, Long> {
  List<Floor> findByFloorID(Long floorId);


}








