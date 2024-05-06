package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Productgroep;
import nl.ritense.demo.repository.ProductgroepRepository;
import nl.ritense.demo.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link nl.ritense.demo.domain.Productgroep}.
 */
@RestController
@RequestMapping("/api/productgroeps")
@Transactional
public class ProductgroepResource {

    private final Logger log = LoggerFactory.getLogger(ProductgroepResource.class);

    private static final String ENTITY_NAME = "productgroep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductgroepRepository productgroepRepository;

    public ProductgroepResource(ProductgroepRepository productgroepRepository) {
        this.productgroepRepository = productgroepRepository;
    }

    /**
     * {@code POST  /productgroeps} : Create a new productgroep.
     *
     * @param productgroep the productgroep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productgroep, or with status {@code 400 (Bad Request)} if the productgroep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Productgroep> createProductgroep(@Valid @RequestBody Productgroep productgroep) throws URISyntaxException {
        log.debug("REST request to save Productgroep : {}", productgroep);
        if (productgroep.getId() != null) {
            throw new BadRequestAlertException("A new productgroep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        productgroep = productgroepRepository.save(productgroep);
        return ResponseEntity.created(new URI("/api/productgroeps/" + productgroep.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, productgroep.getId().toString()))
            .body(productgroep);
    }

    /**
     * {@code PUT  /productgroeps/:id} : Updates an existing productgroep.
     *
     * @param id the id of the productgroep to save.
     * @param productgroep the productgroep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productgroep,
     * or with status {@code 400 (Bad Request)} if the productgroep is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productgroep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Productgroep> updateProductgroep(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Productgroep productgroep
    ) throws URISyntaxException {
        log.debug("REST request to update Productgroep : {}, {}", id, productgroep);
        if (productgroep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productgroep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productgroepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        productgroep = productgroepRepository.save(productgroep);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productgroep.getId().toString()))
            .body(productgroep);
    }

    /**
     * {@code PATCH  /productgroeps/:id} : Partial updates given fields of an existing productgroep, field will ignore if it is null
     *
     * @param id the id of the productgroep to save.
     * @param productgroep the productgroep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productgroep,
     * or with status {@code 400 (Bad Request)} if the productgroep is not valid,
     * or with status {@code 404 (Not Found)} if the productgroep is not found,
     * or with status {@code 500 (Internal Server Error)} if the productgroep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Productgroep> partialUpdateProductgroep(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Productgroep productgroep
    ) throws URISyntaxException {
        log.debug("REST request to partial update Productgroep partially : {}, {}", id, productgroep);
        if (productgroep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productgroep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productgroepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Productgroep> result = productgroepRepository
            .findById(productgroep.getId())
            .map(existingProductgroep -> {
                if (productgroep.getBeslisboom() != null) {
                    existingProductgroep.setBeslisboom(productgroep.getBeslisboom());
                }
                if (productgroep.getCode() != null) {
                    existingProductgroep.setCode(productgroep.getCode());
                }
                if (productgroep.getOmschrijving() != null) {
                    existingProductgroep.setOmschrijving(productgroep.getOmschrijving());
                }

                return existingProductgroep;
            })
            .map(productgroepRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productgroep.getId().toString())
        );
    }

    /**
     * {@code GET  /productgroeps} : get all the productgroeps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productgroeps in body.
     */
    @GetMapping("")
    public List<Productgroep> getAllProductgroeps() {
        log.debug("REST request to get all Productgroeps");
        return productgroepRepository.findAll();
    }

    /**
     * {@code GET  /productgroeps/:id} : get the "id" productgroep.
     *
     * @param id the id of the productgroep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productgroep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Productgroep> getProductgroep(@PathVariable("id") Long id) {
        log.debug("REST request to get Productgroep : {}", id);
        Optional<Productgroep> productgroep = productgroepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productgroep);
    }

    /**
     * {@code DELETE  /productgroeps/:id} : delete the "id" productgroep.
     *
     * @param id the id of the productgroep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductgroep(@PathVariable("id") Long id) {
        log.debug("REST request to delete Productgroep : {}", id);
        productgroepRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
