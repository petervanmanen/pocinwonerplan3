package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Producttype;
import nl.ritense.demo.repository.ProducttypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Producttype}.
 */
@RestController
@RequestMapping("/api/producttypes")
@Transactional
public class ProducttypeResource {

    private final Logger log = LoggerFactory.getLogger(ProducttypeResource.class);

    private static final String ENTITY_NAME = "producttype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProducttypeRepository producttypeRepository;

    public ProducttypeResource(ProducttypeRepository producttypeRepository) {
        this.producttypeRepository = producttypeRepository;
    }

    /**
     * {@code POST  /producttypes} : Create a new producttype.
     *
     * @param producttype the producttype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new producttype, or with status {@code 400 (Bad Request)} if the producttype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Producttype> createProducttype(@Valid @RequestBody Producttype producttype) throws URISyntaxException {
        log.debug("REST request to save Producttype : {}", producttype);
        if (producttype.getId() != null) {
            throw new BadRequestAlertException("A new producttype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        producttype = producttypeRepository.save(producttype);
        return ResponseEntity.created(new URI("/api/producttypes/" + producttype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, producttype.getId().toString()))
            .body(producttype);
    }

    /**
     * {@code PUT  /producttypes/:id} : Updates an existing producttype.
     *
     * @param id the id of the producttype to save.
     * @param producttype the producttype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producttype,
     * or with status {@code 400 (Bad Request)} if the producttype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the producttype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producttype> updateProducttype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Producttype producttype
    ) throws URISyntaxException {
        log.debug("REST request to update Producttype : {}, {}", id, producttype);
        if (producttype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, producttype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!producttypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        producttype = producttypeRepository.save(producttype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, producttype.getId().toString()))
            .body(producttype);
    }

    /**
     * {@code PATCH  /producttypes/:id} : Partial updates given fields of an existing producttype, field will ignore if it is null
     *
     * @param id the id of the producttype to save.
     * @param producttype the producttype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producttype,
     * or with status {@code 400 (Bad Request)} if the producttype is not valid,
     * or with status {@code 404 (Not Found)} if the producttype is not found,
     * or with status {@code 500 (Internal Server Error)} if the producttype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Producttype> partialUpdateProducttype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Producttype producttype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Producttype partially : {}, {}", id, producttype);
        if (producttype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, producttype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!producttypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Producttype> result = producttypeRepository
            .findById(producttype.getId())
            .map(existingProducttype -> {
                if (producttype.getOmschrijving() != null) {
                    existingProducttype.setOmschrijving(producttype.getOmschrijving());
                }

                return existingProducttype;
            })
            .map(producttypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, producttype.getId().toString())
        );
    }

    /**
     * {@code GET  /producttypes} : get all the producttypes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of producttypes in body.
     */
    @GetMapping("")
    public List<Producttype> getAllProducttypes(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftproductzaak-is-null".equals(filter)) {
            log.debug("REST request to get all Producttypes where heeftproductZaak is null");
            return StreamSupport.stream(producttypeRepository.findAll().spliterator(), false)
                .filter(producttype -> producttype.getHeeftproductZaak() == null)
                .toList();
        }

        if ("heeftzaaktype-is-null".equals(filter)) {
            log.debug("REST request to get all Producttypes where heeftZaaktype is null");
            return StreamSupport.stream(producttypeRepository.findAll().spliterator(), false)
                .filter(producttype -> producttype.getHeeftZaaktype() == null)
                .toList();
        }
        log.debug("REST request to get all Producttypes");
        return producttypeRepository.findAll();
    }

    /**
     * {@code GET  /producttypes/:id} : get the "id" producttype.
     *
     * @param id the id of the producttype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the producttype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producttype> getProducttype(@PathVariable("id") Long id) {
        log.debug("REST request to get Producttype : {}", id);
        Optional<Producttype> producttype = producttypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(producttype);
    }

    /**
     * {@code DELETE  /producttypes/:id} : delete the "id" producttype.
     *
     * @param id the id of the producttype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducttype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Producttype : {}", id);
        producttypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
