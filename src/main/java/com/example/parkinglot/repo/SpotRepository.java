package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Spot;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface spotReposiotry extends CrudRepository<Spot, Long> {
    Optional<Spot> getOneBySpotId(Long aLong);
}




