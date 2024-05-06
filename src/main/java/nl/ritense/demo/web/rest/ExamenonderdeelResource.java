package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Examenonderdeel;
import nl.ritense.demo.repository.ExamenonderdeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Examenonderdeel}.
 */
@RestController
@RequestMapping("/api/examenonderdeels")
@Transactional
public class ExamenonderdeelResource {

    private final Logger log = LoggerFactory.getLogger(ExamenonderdeelResource.class);

    private static final String ENTITY_NAME = "examenonderdeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamenonderdeelRepository examenonderdeelRepository;

    public ExamenonderdeelResource(ExamenonderdeelRepository examenonderdeelRepository) {
        this.examenonderdeelRepository = examenonderdeelRepository;
    }

    /**
     * {@code POST  /examenonderdeels} : Create a new examenonderdeel.
     *
     * @param examenonderdeel the examenonderdeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examenonderdeel, or with status {@code 400 (Bad Request)} if the examenonderdeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Examenonderdeel> createExamenonderdeel(@RequestBody Examenonderdeel examenonderdeel) throws URISyntaxException {
        log.debug("REST request to save Examenonderdeel : {}", examenonderdeel);
        if (examenonderdeel.getId() != null) {
            throw new BadRequestAlertException("A new examenonderdeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        examenonderdeel = examenonderdeelRepository.save(examenonderdeel);
        return ResponseEntity.created(new URI("/api/examenonderdeels/" + examenonderdeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, examenonderdeel.getId().toString()))
            .body(examenonderdeel);
    }

    /**
     * {@code PUT  /examenonderdeels/:id} : Updates an existing examenonderdeel.
     *
     * @param id the id of the examenonderdeel to save.
     * @param examenonderdeel the examenonderdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examenonderdeel,
     * or with status {@code 400 (Bad Request)} if the examenonderdeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examenonderdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Examenonderdeel> updateExamenonderdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Examenonderdeel examenonderdeel
    ) throws URISyntaxException {
        log.debug("REST request to update Examenonderdeel : {}, {}", id, examenonderdeel);
        if (examenonderdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examenonderdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examenonderdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        examenonderdeel = examenonderdeelRepository.save(examenonderdeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examenonderdeel.getId().toString()))
            .body(examenonderdeel);
    }

    /**
     * {@code PATCH  /examenonderdeels/:id} : Partial updates given fields of an existing examenonderdeel, field will ignore if it is null
     *
     * @param id the id of the examenonderdeel to save.
     * @param examenonderdeel the examenonderdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examenonderdeel,
     * or with status {@code 400 (Bad Request)} if the examenonderdeel is not valid,
     * or with status {@code 404 (Not Found)} if the examenonderdeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the examenonderdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Examenonderdeel> partialUpdateExamenonderdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Examenonderdeel examenonderdeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Examenonderdeel partially : {}, {}", id, examenonderdeel);
        if (examenonderdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examenonderdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examenonderdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Examenonderdeel> result = examenonderdeelRepository.findById(examenonderdeel.getId()).map(examenonderdeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examenonderdeel.getId().toString())
        );
    }

    /**
     * {@code GET  /examenonderdeels} : get all the examenonderdeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examenonderdeels in body.
     */
    @GetMapping("")
    public List<Examenonderdeel> getAllExamenonderdeels() {
        log.debug("REST request to get all Examenonderdeels");
        return examenonderdeelRepository.findAll();
    }

    /**
     * {@code GET  /examenonderdeels/:id} : get the "id" examenonderdeel.
     *
     * @param id the id of the examenonderdeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examenonderdeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Examenonderdeel> getExamenonderdeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Examenonderdeel : {}", id);
        Optional<Examenonderdeel> examenonderdeel = examenonderdeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(examenonderdeel);
    }

    /**
     * {@code DELETE  /examenonderdeels/:id} : delete the "id" examenonderdeel.
     *
     * @param id the id of the examenonderdeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamenonderdeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Examenonderdeel : {}", id);
        examenonderdeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
