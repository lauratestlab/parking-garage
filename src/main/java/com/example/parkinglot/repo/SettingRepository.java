package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Car;
import com.example.parkinglot.entity.Setting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {

    Optional<Setting> findByKey(String key);
}
