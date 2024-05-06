package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Boring;
import nl.ritense.demo.repository.BoringRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Boring}.
 */
@RestController
@RequestMapping("/api/borings")
@Transactional
public class BoringResource {

    private final Logger log = LoggerFactory.getLogger(BoringResource.class);

    private static final String ENTITY_NAME = "boring";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoringRepository boringRepository;

    public BoringResource(BoringRepository boringRepository) {
        this.boringRepository = boringRepository;
    }

    /**
     * {@code POST  /borings} : Create a new boring.
     *
     * @param boring the boring to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boring, or with status {@code 400 (Bad Request)} if the boring has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Boring> createBoring(@RequestBody Boring boring) throws URISyntaxException {
        log.debug("REST request to save Boring : {}", boring);
        if (boring.getId() != null) {
            throw new BadRequestAlertException("A new boring cannot already have an ID", ENTITY_NAME, "idexists");
        }
        boring = boringRepository.save(boring);
        return ResponseEntity.created(new URI("/api/borings/" + boring.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, boring.getId().toString()))
            .body(boring);
    }

    /**
     * {@code PUT  /borings/:id} : Updates an existing boring.
     *
     * @param id the id of the boring to save.
     * @param boring the boring to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boring,
     * or with status {@code 400 (Bad Request)} if the boring is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boring couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Boring> updateBoring(@PathVariable(value = "id", required = false) final Long id, @RequestBody Boring boring)
        throws URISyntaxException {
        log.debug("REST request to update Boring : {}, {}", id, boring);
        if (boring.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boring.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        boring = boringRepository.save(boring);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, boring.getId().toString()))
            .body(boring);
    }

    /**
     * {@code PATCH  /borings/:id} : Partial updates given fields of an existing boring, field will ignore if it is null
     *
     * @param id the id of the boring to save.
     * @param boring the boring to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boring,
     * or with status {@code 400 (Bad Request)} if the boring is not valid,
     * or with status {@code 404 (Not Found)} if the boring is not found,
     * or with status {@code 500 (Internal Server Error)} if the boring couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Boring> partialUpdateBoring(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Boring boring
    ) throws URISyntaxException {
        log.debug("REST request to partial update Boring partially : {}, {}", id, boring);
        if (boring.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boring.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Boring> result = boringRepository.findById(boring.getId()).map(boringRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, boring.getId().toString())
        );
    }

    /**
     * {@code GET  /borings} : get all the borings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of borings in body.
     */
    @GetMapping("")
    public List<Boring> getAllBorings() {
        log.debug("REST request to get all Borings");
        return boringRepository.findAll();
    }

    /**
     * {@code GET  /borings/:id} : get the "id" boring.
     *
     * @param id the id of the boring to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boring, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Boring> getBoring(@PathVariable("id") Long id) {
        log.debug("REST request to get Boring : {}", id);
        Optional<Boring> boring = boringRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(boring);
    }

    /**
     * {@code DELETE  /borings/:id} : delete the "id" boring.
     *
     * @param id the id of the boring to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoring(@PathVariable("id") Long id) {
        log.debug("REST request to delete Boring : {}", id);
        boringRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
