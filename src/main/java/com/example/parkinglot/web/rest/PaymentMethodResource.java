package com.example.parkinglot.web.rest;

import com.example.parkinglot.repo.PaymentMethodRepository;
import com.example.parkinglot.service.PaymentMethodService;
import com.example.parkinglot.dto.PaymentMethodDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodResource {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentMethodResource.class);

    private static final String ENTITY_NAME = "paymentMethod";

    @Value("${spring.application.name}")
    private String applicationName;

    private final PaymentMethodService paymentMethodService;

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodResource(PaymentMethodService paymentMethodService, PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodService = paymentMethodService;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    /**
     * {@code POST  /payment-methods} : Create a new paymentMethod.
     *
     * @param paymentMethodDTO the paymentMethodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentMethodDTO, or with status {@code 400 (Bad Request)} if the paymentMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PaymentMethodDTO> createPaymentMethod(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PaymentMethod : {}", paymentMethodDTO);
        if (paymentMethodDTO.id() != null) {
            throw new BadRequestAlertException("A new paymentMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paymentMethodDTO = paymentMethodService.save(paymentMethodDTO);
        return ResponseEntity.created(new URI("/api/payment-methods/" + paymentMethodDTO.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, paymentMethodDTO.id().toString()))
            .body(paymentMethodDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentMethodDTO paymentMethodDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PaymentMethod : {}, {}", id, paymentMethodDTO);
        if (paymentMethodDTO.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentMethodDTO.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paymentMethodDTO = paymentMethodService.update(paymentMethodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentMethodDTO.id().toString()))
            .body(paymentMethodDTO);
    }

    /**
     * {@code PATCH  /payment-methods/:id} : Partial updates given fields of an existing paymentMethod, field will ignore if it is null
     *
     * @param id the id of the paymentMethodDTO to save.
     * @param paymentMethodDTO the paymentMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentMethodDTO,
     * or with status {@code 400 (Bad Request)} if the paymentMethodDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentMethodDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentMethodDTO> partialUpdatePaymentMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentMethodDTO paymentMethodDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PaymentMethod partially : {}, {}", id, paymentMethodDTO);
        if (paymentMethodDTO.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentMethodDTO.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentMethodDTO> result = paymentMethodService.partialUpdate(paymentMethodDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentMethodDTO.id().toString())
        );
    }

    /**
     * {@code GET  /payment-methods} : get all the paymentMethods.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentMethods in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PaymentMethodDTO>> getAllPaymentMethods(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of PaymentMethods");
        Page<PaymentMethodDTO> page;
        if (eagerload) {
            page = paymentMethodService.findAllWithEagerRelationships(pageable);
        } else {
            page = paymentMethodService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-methods/:id} : get the "id" paymentMethod.
     *
     * @param id the id of the paymentMethodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentMethodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethod(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PaymentMethod : {}", id);
        Optional<PaymentMethodDTO> paymentMethodDTO = paymentMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentMethodDTO);
    }

    /**
     * {@code DELETE  /payment-methods/:id} : delete the "id" paymentMethod.
     *
     * @param id the id of the paymentMethodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PaymentMethod : {}", id);
        paymentMethodService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
