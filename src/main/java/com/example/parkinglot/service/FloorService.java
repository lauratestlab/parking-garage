package com.example.parkinglot.service;

import com.example.parkinglot.config.Constants;
import com.example.parkinglot.entity.Floor;
import com.example.parkinglot.repo.FloorRepository;
import com.example.parkinglot.dto.FloorDTO;
import com.example.parkinglot.mapper.FloorMapper;
import java.util.Optional;

import com.example.parkinglot.repo.GarageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.example.parkinglot.entity.Floor}.
 */
@Service
@Transactional
public class FloorService {

    private static final Logger LOG = LoggerFactory.getLogger(FloorService.class);

    private final FloorRepository floorRepository;

    private final FloorMapper floorMapper;
    private final GarageRepository garageRepository;

    public FloorService(FloorRepository floorRepository, FloorMapper floorMapper, GarageRepository garageRepository) {
        this.floorRepository = floorRepository;
        this.floorMapper = floorMapper;
        this.garageRepository = garageRepository;
    }

    /**
     * Save a floor.
     *
     * @param floorDTO the entity to save.
     * @return the persisted entity.
     */
    public FloorDTO save(FloorDTO floorDTO) {
        LOG.debug("Request to save Floor : {}", floorDTO);
        Floor floor = floorMapper.toEntity(floorDTO);
        floor.setGarage(garageRepository.getReferenceById(Constants.DEFAULT_GARAGE_ID));
        floor = floorRepository.save(floor);
        return floorMapper.toDto(floor);
    }

    /**
     * Update a floor.
     *
     * @param floorDTO the entity to save.
     * @return the persisted entity.
     */
    public FloorDTO update(FloorDTO floorDTO) {
        LOG.debug("Request to update Floor : {}", floorDTO);
        Floor floor = floorMapper.toEntity(floorDTO);
        floor = floorRepository.save(floor);
        return floorMapper.toDto(floor);
    }

    /**
     * Partially update a floor.
     *
     * @param floorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FloorDTO> partialUpdate(FloorDTO floorDTO) {
        LOG.debug("Request to partially update Floor : {}", floorDTO);

        return floorRepository
            .findById(floorDTO.id())
            .map(existingFloor -> {
                floorMapper.partialUpdate(existingFloor, floorDTO);

                return existingFloor;
            })
            .map(floorRepository::save)
            .map(floorMapper::toDto);
    }

    /**
     * Get all the floors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FloorDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Floors");
        return floorRepository.findAll(pageable).map(floorMapper::toDto);
    }

    /**
     * Get one floor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FloorDTO> findOne(Long id) {
        LOG.debug("Request to get Floor : {}", id);
        return floorRepository.findById(id).map(floorMapper::toDto);
    }

    /**
     * Delete the floor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Floor : {}", id);
        floorRepository.deleteById(id);
    }
}
