package com.example.parkinglot.service;

import com.example.parkinglot.dto.SpotDTO;
import com.example.parkinglot.entity.Spot;
import com.example.parkinglot.repo.SpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class     SpotService {

    @Autowired
    private SpotRepository spotRepository;


    public List<SpotDTO> getSpotsWithLimitedFields() {
        List<Object[]> spots = spotRepository.findAllSpotsWithLimitedFields();
        return spots.stream().map(spot -> new SpotDTO((Long) spot[0], (String) spot[1], (Long) spot[2])) // Assuming floorId is Long
                .collect(Collectors.toList());
    }
    }




