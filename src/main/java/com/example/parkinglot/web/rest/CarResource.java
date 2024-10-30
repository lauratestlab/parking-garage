package com.example.parkinglot.web.rest;

import com.example.parkinglot.dto.CarDTO;
import com.example.parkinglot.repo.CarRepository;
import com.example.parkinglot.security.SecurityUtils;
import com.example.parkinglot.service.CarService;
import com.example.parkinglot.web.rest.errors.BadRequestAlertException;
import com.example.parkinglot.web.util.HeaderUtil;
import com.example.parkinglot.web.util.PaginationUtil;
import com.example.parkinglot.web.util.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarResource.class);

    private static final String ENTITY_NAME = "car";

     @Value("${spring.application.name}")
    private String applicationName;

    private final CarService carService;

    private final CarRepository carRepository;

    public CarResource(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
    }

    @PostMapping("")
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO carDTO) throws URISyntaxException {
        LOG.debug("REST request to save Car : {}", carDTO);
        if (carDTO.id() != null) {
            throw new BadRequestAlertException("A new car cannot already have an ID", ENTITY_NAME, "idexists");
        }
        carDTO = carService.save(carDTO);
        return ResponseEntity.created(new URI("/api/cars/" + carDTO.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, carDTO.id().toString()))
            .body(carDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody CarDTO carDTO)
        throws URISyntaxException {
        LOG.debug("REST request to update Car : {}, {}", id, carDTO);
        if (carDTO.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carDTO.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        carDTO = carService.update(carDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, carDTO.id().toString()))
            .body(carDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarDTO> partialUpdateCar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CarDTO carDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Car partially : {}, {}", id, carDTO);
        if (carDTO.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carDTO.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarDTO> result = carService.partialUpdate(carDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, carDTO.id().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<CarDTO>> getAllCars(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Cars");
        Page<CarDTO> page;
        if (SecurityUtils.hasCurrentUserThisAuthority("ROLE_ADMIN")) {
            //Page<CarDTO> page;
            if (eagerload) {
                page = carService.findAllWithEagerRelationships(pageable);
            } else {
                page = carService.findAll(pageable);
            }
        } else {
//            Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
//            Page<CarDTO> page;
//            if (eagerload) {
//                page = carService.findAllWithEagerRelationshipsByUser(pageable, user);
//            } else {
//                page = carService.findAllByUser(pageable, user);
//            }
            Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
            if (currentUserLogin.isPresent()) {
                String userLogin = currentUserLogin.get();
                page = eagerload ? carService.findAllWithEagerRelationshipsByUser(pageable, userLogin)
                        : carService.findAllByUser(pageable, userLogin);
            } else {

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
            }
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCar(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Car : {}", id);
        Optional<CarDTO> carDTO = carService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carDTO);
    }

    /**
     * {@code DELETE  /cars/:id} : delete the "id" car.
     *
     * @param id the id of the carDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Car : {}", id);
        carService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
