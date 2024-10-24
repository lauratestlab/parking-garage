package com.example.parkinglot.repo;

import com.example.parkinglot.entity.Spot;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;

public interface ReportRepository {
    Long numberOfAvailableSports(LocalDateTime startTime, LocalDateTime endTime) throws NoResultException;

    Spot findFirstAvailableSpot(LocalDateTime startTime, LocalDateTime endTime);
}
