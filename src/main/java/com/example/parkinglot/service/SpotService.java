package com.example.parkinglot.service;

import com.example.parkinglot.repo.SpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotService {

    @Autowired
    private SpotRepository spotRepository;


    public long getSpotCount() {

        return spotRepository.count();
    }
    public Long getSpotId() {

        return spotRepository.findAll().get(0).getSpotId();

    }
    public String getSpotName() {
        return spotRepository.findAll().get(0).getName();
    }
}

