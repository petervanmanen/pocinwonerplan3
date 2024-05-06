package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Productsoort;
import nl.ritense.demo.repository.ProductsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Productsoort}.
 */
@RestController
@RequestMapping("/api/productsoorts")
@Transactional
public class ProductsoortResource {

    private final Logger log = LoggerFactory.getLogger(ProductsoortResource.class);

    private static final String ENTITY_NAME = "productsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductsoortRepository productsoortRepository;

    public ProductsoortResource(ProductsoortRepository productsoortRepository) {
        this.productsoortRepository = productsoortRepository;
    }

    /**
     * {@code POST  /productsoorts} : Create a new productsoort.
     *
     * @param productsoort the productsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productsoort, or with status {@code 400 (Bad Request)} if the productsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Productsoort> createProductsoort(@Valid @RequestBody Productsoort productsoort) throws URISyntaxException {
        log.debug("REST request to save Productsoort : {}", productsoort);
        if (productsoort.getId() != null) {
            throw new BadRequestAlertException("A new productsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        productsoort = productsoortRepository.save(productsoort);
        return ResponseEntity.created(new URI("/api/productsoorts/" + productsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, productsoort.getId().toString()))
            .body(productsoort);
    }

    /**
     * {@code PUT  /productsoorts/:id} : Updates an existing productsoort.
     *
     * @param id the id of the productsoort to save.
     * @param productsoort the productsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productsoort,
     * or with status {@code 400 (Bad Request)} if the productsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Productsoort> updateProductsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Productsoort productsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Productsoort : {}, {}", id, productsoort);
        if (productsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        productsoort = productsoortRepository.save(productsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productsoort.getId().toString()))
            .body(productsoort);
    }

    /**
     * {@code PATCH  /productsoorts/:id} : Partial updates given fields of an existing productsoort, field will ignore if it is null
     *
     * @param id the id of the productsoort to save.
     * @param productsoort the productsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productsoort,
     * or with status {@code 400 (Bad Request)} if the productsoort is not valid,
     * or with status {@code 404 (Not Found)} if the productsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the productsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Productsoort> partialUpdateProductsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Productsoort productsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Productsoort partially : {}, {}", id, productsoort);
        if (productsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Productsoort> result = productsoortRepository
            .findById(productsoort.getId())
            .map(existingProductsoort -> {
                if (productsoort.getCode() != null) {
                    existingProductsoort.setCode(productsoort.getCode());
                }
                if (productsoort.getOmschrijving() != null) {
                    existingProductsoort.setOmschrijving(productsoort.getOmschrijving());
                }
                if (productsoort.getTarief() != null) {
                    existingProductsoort.setTarief(productsoort.getTarief());
                }
                if (productsoort.getTariefperiode() != null) {
                    existingProductsoort.setTariefperiode(productsoort.getTariefperiode());
                }

                return existingProductsoort;
            })
            .map(productsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /productsoorts} : get all the productsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productsoorts in body.
     */
    @GetMapping("")
    public List<Productsoort> getAllProductsoorts() {
        log.debug("REST request to get all Productsoorts");
        return productsoortRepository.findAll();
    }

    /**
     * {@code GET  /productsoorts/:id} : get the "id" productsoort.
     *
     * @param id the id of the productsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Productsoort> getProductsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Productsoort : {}", id);
        Optional<Productsoort> productsoort = productsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productsoort);
    }

    /**
     * {@code DELETE  /productsoorts/:id} : delete the "id" productsoort.
     *
     * @param id the id of the productsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Productsoort : {}", id);
        productsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
