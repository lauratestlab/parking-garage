package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Price;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findAll();

    Optional<Price> findByDuration(int duration);

    Optional<Price> findFirstByDurationGreaterThanEqualOrderByDurationAsc(long duration);

    @Query("SELECT MAX(p.duration) FROM Price p")
    Optional<Long> maxDuration();

}
