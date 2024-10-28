package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    Optional<Spot> getOneBySpotId(Long aLong);

    List<Spot> findByName(String name);

    //        @Query("SELECT s.spotId, s.name, s.floor FROM Spot s")
//        List<Object[]> findAllSpotsWithLimitedFields();
    @Query("SELECT s.spotId, s.name, s.floor.id FROM Spot s")
    List<Object[]> findAllSpotsWithLimitedFields();
}






