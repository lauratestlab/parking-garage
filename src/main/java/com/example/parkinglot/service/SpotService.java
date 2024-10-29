package com.example.parkinglot.service;

import com.example.parkinglot.entity.Spot;
import com.example.parkinglot.repo.SpotRepository;
import com.example.parkinglot.dto.SpotDTO;
import com.example.parkinglot.mapper.SpotMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpotService {

    private static final Logger LOG = LoggerFactory.getLogger(SpotService.class);

    private final SpotRepository spotRepository;

    private final SpotMapper spotMapper;

    public SpotService(SpotRepository spotRepository, SpotMapper spotMapper) {
        this.spotRepository = spotRepository;
        this.spotMapper = spotMapper;
    }

    /**
     * Save a spot.
     *
     * @param spotDTO the entity to save.
     * @return the persisted entity.
     */
    public SpotDTO save(SpotDTO spotDTO) {
        LOG.debug("Request to save Spot : {}", spotDTO);
        Spot spot = spotMapper.toEntity(spotDTO);
        spot = spotRepository.save(spot);
        return spotMapper.toDto(spot);
    }

    /**
     * Update a spot.
     *
     * @param spotDTO the entity to save.
     * @return the persisted entity.
     */
    public SpotDTO update(SpotDTO spotDTO) {
        LOG.debug("Request to update Spot : {}", spotDTO);
        Spot spot = spotMapper.toEntity(spotDTO);
        spot = spotRepository.save(spot);
        return spotMapper.toDto(spot);
    }

    /**
     * Partially update a spot.
     *
     * @param spotDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SpotDTO> partialUpdate(SpotDTO spotDTO) {
        LOG.debug("Request to partially update Spot : {}", spotDTO);

        return spotRepository
            .findById(spotDTO.id())
            .map(existingSpot -> {
                spotMapper.partialUpdate(existingSpot, spotDTO);

                return existingSpot;
            })
            .map(spotRepository::save)
            .map(spotMapper::toDto);
    }

    /**
     * Get all the spots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SpotDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Spots");
        return spotRepository.findAll(pageable).map(spotMapper::toDto);
    }

    /**
     * Get all the spots with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SpotDTO> findAllWithEagerRelationships(Pageable pageable) {
        return spotRepository.findAllWithEagerRelationships(pageable).map(spotMapper::toDto);
    }

    /**
     * Get one spot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SpotDTO> findOne(Long id) {
        LOG.debug("Request to get Spot : {}", id);
        return spotRepository.findOneWithEagerRelationships(id).map(spotMapper::toDto);
    }

    /**
     * Delete the spot by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Spot : {}", id);
        spotRepository.deleteById(id);
    }
}
