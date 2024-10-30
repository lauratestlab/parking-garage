package com.example.parkinglot.web.rest;

import com.example.parkinglot.repo.FloorRepository;
import com.example.parkinglot.service.FloorService;
import com.example.parkinglot.dto.FloorDTO;
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


@RestController
@RequestMapping("/api/admin/floors")
public class FloorResource {

    private static final Logger LOG = LoggerFactory.getLogger(FloorResource.class);

    private static final String ENTITY_NAME = "floor";

    @Value("${spring.application.name}")
    private String applicationName;

    private final FloorService floorService;

    private final FloorRepository floorRepository;

    public FloorResource(FloorService floorService, FloorRepository floorRepository) {
        this.floorService = floorService;
        this.floorRepository = floorRepository;
    }

    @PostMapping("")
    public ResponseEntity<FloorDTO> createFloor(@Valid @RequestBody FloorDTO floorDTO) throws URISyntaxException {
        LOG.debug("REST request to save Floor : {}", floorDTO);
        if (floorDTO.id() != null) {
            throw new BadRequestAlertException("A new floor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        floorDTO = floorService.save(floorDTO);
        return ResponseEntity.created(new URI("/api/floors/" + floorDTO.id()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, floorDTO.id().toString()))
                .body(floorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FloorDTO> updateFloor(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody FloorDTO floorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Floor : {}, {}", id, floorDTO);
        if (floorDTO.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floorDTO.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        floorDTO = floorService.update(floorDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, floorDTO.id().toString()))
                .body(floorDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FloorDTO> partialUpdateFloor(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody FloorDTO floorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Floor partially : {}, {}", id, floorDTO);
        if (floorDTO.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floorDTO.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FloorDTO> result = floorService.partialUpdate(floorDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, floorDTO.id().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<FloorDTO>> getAllFloors(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Floors");
        Page<FloorDTO> page = floorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloorDTO> getFloor(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Floor : {}", id);
        Optional<FloorDTO> floorDTO = floorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(floorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFloor(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Floor : {}", id);
        floorService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
