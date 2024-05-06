package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bijzonderheid;
import nl.ritense.demo.repository.BijzonderheidRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bijzonderheid}.
 */
@RestController
@RequestMapping("/api/bijzonderheids")
@Transactional
public class BijzonderheidResource {

    private final Logger log = LoggerFactory.getLogger(BijzonderheidResource.class);

    private static final String ENTITY_NAME = "bijzonderheid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BijzonderheidRepository bijzonderheidRepository;

    public BijzonderheidResource(BijzonderheidRepository bijzonderheidRepository) {
        this.bijzonderheidRepository = bijzonderheidRepository;
    }

    /**
     * {@code POST  /bijzonderheids} : Create a new bijzonderheid.
     *
     * @param bijzonderheid the bijzonderheid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bijzonderheid, or with status {@code 400 (Bad Request)} if the bijzonderheid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bijzonderheid> createBijzonderheid(@RequestBody Bijzonderheid bijzonderheid) throws URISyntaxException {
        log.debug("REST request to save Bijzonderheid : {}", bijzonderheid);
        if (bijzonderheid.getId() != null) {
            throw new BadRequestAlertException("A new bijzonderheid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bijzonderheid = bijzonderheidRepository.save(bijzonderheid);
        return ResponseEntity.created(new URI("/api/bijzonderheids/" + bijzonderheid.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bijzonderheid.getId().toString()))
            .body(bijzonderheid);
    }

    /**
     * {@code PUT  /bijzonderheids/:id} : Updates an existing bijzonderheid.
     *
     * @param id the id of the bijzonderheid to save.
     * @param bijzonderheid the bijzonderheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bijzonderheid,
     * or with status {@code 400 (Bad Request)} if the bijzonderheid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bijzonderheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bijzonderheid> updateBijzonderheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bijzonderheid bijzonderheid
    ) throws URISyntaxException {
        log.debug("REST request to update Bijzonderheid : {}, {}", id, bijzonderheid);
        if (bijzonderheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bijzonderheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bijzonderheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bijzonderheid = bijzonderheidRepository.save(bijzonderheid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bijzonderheid.getId().toString()))
            .body(bijzonderheid);
    }

    /**
     * {@code PATCH  /bijzonderheids/:id} : Partial updates given fields of an existing bijzonderheid, field will ignore if it is null
     *
     * @param id the id of the bijzonderheid to save.
     * @param bijzonderheid the bijzonderheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bijzonderheid,
     * or with status {@code 400 (Bad Request)} if the bijzonderheid is not valid,
     * or with status {@code 404 (Not Found)} if the bijzonderheid is not found,
     * or with status {@code 500 (Internal Server Error)} if the bijzonderheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bijzonderheid> partialUpdateBijzonderheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bijzonderheid bijzonderheid
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bijzonderheid partially : {}, {}", id, bijzonderheid);
        if (bijzonderheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bijzonderheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bijzonderheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bijzonderheid> result = bijzonderheidRepository
            .findById(bijzonderheid.getId())
            .map(existingBijzonderheid -> {
                if (bijzonderheid.getOmschrijving() != null) {
                    existingBijzonderheid.setOmschrijving(bijzonderheid.getOmschrijving());
                }

                return existingBijzonderheid;
            })
            .map(bijzonderheidRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bijzonderheid.getId().toString())
        );
    }

    /**
     * {@code GET  /bijzonderheids} : get all the bijzonderheids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bijzonderheids in body.
     */
    @GetMapping("")
    public List<Bijzonderheid> getAllBijzonderheids() {
        log.debug("REST request to get all Bijzonderheids");
        return bijzonderheidRepository.findAll();
    }

    /**
     * {@code GET  /bijzonderheids/:id} : get the "id" bijzonderheid.
     *
     * @param id the id of the bijzonderheid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bijzonderheid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bijzonderheid> getBijzonderheid(@PathVariable("id") Long id) {
        log.debug("REST request to get Bijzonderheid : {}", id);
        Optional<Bijzonderheid> bijzonderheid = bijzonderheidRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bijzonderheid);
    }

    /**
     * {@code DELETE  /bijzonderheids/:id} : delete the "id" bijzonderheid.
     *
     * @param id the id of the bijzonderheid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBijzonderheid(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bijzonderheid : {}", id);
        bijzonderheidRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
