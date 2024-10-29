package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Spot;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    default Optional<Spot> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Spot> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Spot> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(value = "select spot from Spot spot left join fetch spot.floor", countQuery = "select count(spot) from Spot spot")
    Page<Spot> findAllWithToOneRelationships(Pageable pageable);

    @Query("select spot from Spot spot left join fetch spot.floor")
    List<Spot> findAllWithToOneRelationships();

    @Query("select spot from Spot spot left join fetch spot.floor where spot.id =:id")
    Optional<Spot> findOneWithToOneRelationships(@Param("id") Long id);
}
