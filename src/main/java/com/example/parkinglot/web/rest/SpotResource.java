package com.example.parkinglot.web.rest;

import com.example.parkinglot.repo.SpotRepository;
import com.example.parkinglot.service.SpotService;
import com.example.parkinglot.dto.SpotDTO;
import com.example.parkinglot.web.rest.errors.BadRequestAlertException;
import com.example.parkinglot.web.util.HeaderUtil;
import com.example.parkinglot.web.util.PaginationUtil;
import com.example.parkinglot.web.util.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.example.parkinglot.entity.Spot}.
 */
@RestController
@RequestMapping("/api/admin/spots")
public class SpotResource {

    private static final Logger LOG = LoggerFactory.getLogger(SpotResource.class);

    private static final String ENTITY_NAME = "spot";

    @Value("${spring.application.name}")
    private String applicationName;

    private final SpotService spotService;

    private final SpotRepository spotRepository;

    public SpotResource(SpotService spotService, SpotRepository spotRepository) {
        this.spotService = spotService;
        this.spotRepository = spotRepository;
    }

    /**
     * {@code POST  /spots} : Create a new spot.
     *
     * @param spotDTO the spotDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spotDTO, or with status {@code 400 (Bad Request)} if the spot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SpotDTO> createSpot(@Valid @RequestBody SpotDTO spotDTO) throws URISyntaxException {
        LOG.debug("REST request to save Spot : {}", spotDTO);
        if (spotDTO.id() != null) {
            throw new BadRequestAlertException("A new spot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        spotDTO = spotService.save(spotDTO);
        return ResponseEntity.created(new URI("/api/spots/" + spotDTO.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, spotDTO.id().toString()))
            .body(spotDTO);
    }

    /**
     * {@code PUT  /spots/:id} : Updates an existing spot.
     *
     * @param id the id of the spotDTO to save.
     * @param spotDTO the spotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spotDTO,
     * or with status {@code 400 (Bad Request)} if the spotDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SpotDTO> updateSpot(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SpotDTO spotDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Spot : {}, {}", id, spotDTO);
        if (spotDTO. id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spotDTO.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        spotDTO = spotService.update(spotDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, spotDTO.id().toString()))
            .body(spotDTO);
    }

    /**
     * {@code PATCH  /spots/:id} : Partial updates given fields of an existing spot, field will ignore if it is null
     *
     * @param id the id of the spotDTO to save.
     * @param spotDTO the spotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spotDTO,
     * or with status {@code 400 (Bad Request)} if the spotDTO is not valid,
     * or with status {@code 404 (Not Found)} if the spotDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the spotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpotDTO> partialUpdateSpot(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SpotDTO spotDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Spot partially : {}, {}", id, spotDTO);
        if (spotDTO.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spotDTO.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpotDTO> result = spotService.partialUpdate(spotDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, spotDTO.id().toString())
        );
    }

    /**
     * {@code GET  /spots} : get all the spots.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spots in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SpotDTO>> getAllSpots(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Spots");
        Page<SpotDTO> page;
        if (eagerload) {
            page = spotService.findAllWithEagerRelationships(pageable);
        } else {
            page = spotService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /spots/:id} : get the "id" spot.
     *
     * @param id the id of the spotDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spotDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SpotDTO> getSpot(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Spot : {}", id);
        Optional<SpotDTO> spotDTO = spotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spotDTO);
    }

    /**
     * {@code DELETE  /spots/:id} : delete the "id" spot.
     *
     * @param id the id of the spotDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpot(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Spot : {}", id);
        spotService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
