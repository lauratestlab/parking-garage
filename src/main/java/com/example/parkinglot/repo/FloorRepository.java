package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Floor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {}
