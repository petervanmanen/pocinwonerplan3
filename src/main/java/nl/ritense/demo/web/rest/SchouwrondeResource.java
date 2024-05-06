package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Schouwronde;
import nl.ritense.demo.repository.SchouwrondeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Schouwronde}.
 */
@RestController
@RequestMapping("/api/schouwrondes")
@Transactional
public class SchouwrondeResource {

    private final Logger log = LoggerFactory.getLogger(SchouwrondeResource.class);

    private static final String ENTITY_NAME = "schouwronde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchouwrondeRepository schouwrondeRepository;

    public SchouwrondeResource(SchouwrondeRepository schouwrondeRepository) {
        this.schouwrondeRepository = schouwrondeRepository;
    }

    /**
     * {@code POST  /schouwrondes} : Create a new schouwronde.
     *
     * @param schouwronde the schouwronde to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schouwronde, or with status {@code 400 (Bad Request)} if the schouwronde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Schouwronde> createSchouwronde(@RequestBody Schouwronde schouwronde) throws URISyntaxException {
        log.debug("REST request to save Schouwronde : {}", schouwronde);
        if (schouwronde.getId() != null) {
            throw new BadRequestAlertException("A new schouwronde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        schouwronde = schouwrondeRepository.save(schouwronde);
        return ResponseEntity.created(new URI("/api/schouwrondes/" + schouwronde.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, schouwronde.getId().toString()))
            .body(schouwronde);
    }

    /**
     * {@code PUT  /schouwrondes/:id} : Updates an existing schouwronde.
     *
     * @param id the id of the schouwronde to save.
     * @param schouwronde the schouwronde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schouwronde,
     * or with status {@code 400 (Bad Request)} if the schouwronde is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schouwronde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Schouwronde> updateSchouwronde(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Schouwronde schouwronde
    ) throws URISyntaxException {
        log.debug("REST request to update Schouwronde : {}, {}", id, schouwronde);
        if (schouwronde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schouwronde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schouwrondeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        schouwronde = schouwrondeRepository.save(schouwronde);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schouwronde.getId().toString()))
            .body(schouwronde);
    }

    /**
     * {@code PATCH  /schouwrondes/:id} : Partial updates given fields of an existing schouwronde, field will ignore if it is null
     *
     * @param id the id of the schouwronde to save.
     * @param schouwronde the schouwronde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schouwronde,
     * or with status {@code 400 (Bad Request)} if the schouwronde is not valid,
     * or with status {@code 404 (Not Found)} if the schouwronde is not found,
     * or with status {@code 500 (Internal Server Error)} if the schouwronde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Schouwronde> partialUpdateSchouwronde(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Schouwronde schouwronde
    ) throws URISyntaxException {
        log.debug("REST request to partial update Schouwronde partially : {}, {}", id, schouwronde);
        if (schouwronde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schouwronde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schouwrondeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Schouwronde> result = schouwrondeRepository.findById(schouwronde.getId()).map(schouwrondeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schouwronde.getId().toString())
        );
    }

    /**
     * {@code GET  /schouwrondes} : get all the schouwrondes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schouwrondes in body.
     */
    @GetMapping("")
    public List<Schouwronde> getAllSchouwrondes() {
        log.debug("REST request to get all Schouwrondes");
        return schouwrondeRepository.findAll();
    }

    /**
     * {@code GET  /schouwrondes/:id} : get the "id" schouwronde.
     *
     * @param id the id of the schouwronde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schouwronde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Schouwronde> getSchouwronde(@PathVariable("id") Long id) {
        log.debug("REST request to get Schouwronde : {}", id);
        Optional<Schouwronde> schouwronde = schouwrondeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(schouwronde);
    }

    /**
     * {@code DELETE  /schouwrondes/:id} : delete the "id" schouwronde.
     *
     * @param id the id of the schouwronde to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchouwronde(@PathVariable("id") Long id) {
        log.debug("REST request to delete Schouwronde : {}", id);
        schouwrondeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
