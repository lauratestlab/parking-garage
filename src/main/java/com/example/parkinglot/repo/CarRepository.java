package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Car;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("select car from Car car where car.user.login = ?#{authentication.name}")
    List<Car> findByUserIsCurrentUser();

    default Optional<Car> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Car> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Car> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query("select car from Car car where car.id = :id and car.user.login = ?#{authentication.name}")
    Optional<Car> findByUserIsCurrentUserAndId(@Param("id") Long id);

    @Query(value = "select car from Car car left join fetch car.user", countQuery = "select count(car) from Car car")
    Page<Car> findAllWithToOneRelationships(Pageable pageable);

    @Query("select car from Car car left join fetch car.user")
    List<Car> findAllWithToOneRelationships();

    @Query("select car from Car car left join fetch car.user where car.id =:id")
    Optional<Car> findOneWithToOneRelationships(@Param("id") Long id);
}
