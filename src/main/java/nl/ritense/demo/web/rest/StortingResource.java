package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Storting;
import nl.ritense.demo.repository.StortingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Storting}.
 */
@RestController
@RequestMapping("/api/stortings")
@Transactional
public class StortingResource {

    private final Logger log = LoggerFactory.getLogger(StortingResource.class);

    private static final String ENTITY_NAME = "storting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StortingRepository stortingRepository;

    public StortingResource(StortingRepository stortingRepository) {
        this.stortingRepository = stortingRepository;
    }

    /**
     * {@code POST  /stortings} : Create a new storting.
     *
     * @param storting the storting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storting, or with status {@code 400 (Bad Request)} if the storting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Storting> createStorting(@RequestBody Storting storting) throws URISyntaxException {
        log.debug("REST request to save Storting : {}", storting);
        if (storting.getId() != null) {
            throw new BadRequestAlertException("A new storting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        storting = stortingRepository.save(storting);
        return ResponseEntity.created(new URI("/api/stortings/" + storting.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, storting.getId().toString()))
            .body(storting);
    }

    /**
     * {@code PUT  /stortings/:id} : Updates an existing storting.
     *
     * @param id the id of the storting to save.
     * @param storting the storting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storting,
     * or with status {@code 400 (Bad Request)} if the storting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Storting> updateStorting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Storting storting
    ) throws URISyntaxException {
        log.debug("REST request to update Storting : {}, {}", id, storting);
        if (storting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stortingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        storting = stortingRepository.save(storting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, storting.getId().toString()))
            .body(storting);
    }

    /**
     * {@code PATCH  /stortings/:id} : Partial updates given fields of an existing storting, field will ignore if it is null
     *
     * @param id the id of the storting to save.
     * @param storting the storting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storting,
     * or with status {@code 400 (Bad Request)} if the storting is not valid,
     * or with status {@code 404 (Not Found)} if the storting is not found,
     * or with status {@code 500 (Internal Server Error)} if the storting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Storting> partialUpdateStorting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Storting storting
    ) throws URISyntaxException {
        log.debug("REST request to partial update Storting partially : {}, {}", id, storting);
        if (storting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stortingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Storting> result = stortingRepository
            .findById(storting.getId())
            .map(existingStorting -> {
                if (storting.getDatumtijd() != null) {
                    existingStorting.setDatumtijd(storting.getDatumtijd());
                }
                if (storting.getGewicht() != null) {
                    existingStorting.setGewicht(storting.getGewicht());
                }

                return existingStorting;
            })
            .map(stortingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, storting.getId().toString())
        );
    }

    /**
     * {@code GET  /stortings} : get all the stortings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stortings in body.
     */
    @GetMapping("")
    public List<Storting> getAllStortings(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Stortings");
        if (eagerload) {
            return stortingRepository.findAllWithEagerRelationships();
        } else {
            return stortingRepository.findAll();
        }
    }

    /**
     * {@code GET  /stortings/:id} : get the "id" storting.
     *
     * @param id the id of the storting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Storting> getStorting(@PathVariable("id") Long id) {
        log.debug("REST request to get Storting : {}", id);
        Optional<Storting> storting = stortingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(storting);
    }

    /**
     * {@code DELETE  /stortings/:id} : delete the "id" storting.
     *
     * @param id the id of the storting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStorting(@PathVariable("id") Long id) {
        log.debug("REST request to delete Storting : {}", id);
        stortingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
