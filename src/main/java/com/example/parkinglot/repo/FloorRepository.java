package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Floor;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface FloorRepository extends CrudRepository<Floor, Long> {
  Optional<Floor> findById(Long floorId);


}








