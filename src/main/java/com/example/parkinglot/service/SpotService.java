package com.example.parkinglot.service;

import com.example.parkinglot.dto.SpotDTO;
import com.example.parkinglot.entity.Spot;
import com.example.parkinglot.repo.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class  SpotService {

    @Autowired
    private SpotRepository spotRepository;


    public List<SpotDTO> getSpotsWithLimitedFields(Pageable pageable) {

        Page<Spot> spots = spotRepository.findAll(pageable);
//        List<Object[]> spots = spotRepository.findAllSpotsWithLimitedFields(pageable);
        return spots.stream().map(spot -> new SpotDTO(spot.getId(), spot.getName(), spot.getFloor().getName(), spot.getFloor().getId())) // Assuming floorId is Long
                .collect(Collectors.toList());
    }

    public Optional<SpotDTO> getSpotDTOById(Long id) {
        return spotRepository.findById(id)
                .map(spot -> new SpotDTO(spot.getId(), spot.getName(), spot.getFloor().getName(), spot.getFloor().getId()));
    }

    public void deleteSpot(Long id) {
        spotRepository.deleteById(id);
    }
}




