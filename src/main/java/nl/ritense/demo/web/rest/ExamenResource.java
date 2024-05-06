package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Examen;
import nl.ritense.demo.repository.ExamenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Examen}.
 */
@RestController
@RequestMapping("/api/examen")
@Transactional
public class ExamenResource {

    private final Logger log = LoggerFactory.getLogger(ExamenResource.class);

    private static final String ENTITY_NAME = "examen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamenRepository examenRepository;

    public ExamenResource(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    /**
     * {@code POST  /examen} : Create a new examen.
     *
     * @param examen the examen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examen, or with status {@code 400 (Bad Request)} if the examen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Examen> createExamen(@Valid @RequestBody Examen examen) throws URISyntaxException {
        log.debug("REST request to save Examen : {}", examen);
        if (examen.getId() != null) {
            throw new BadRequestAlertException("A new examen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        examen = examenRepository.save(examen);
        return ResponseEntity.created(new URI("/api/examen/" + examen.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, examen.getId().toString()))
            .body(examen);
    }

    /**
     * {@code PUT  /examen/:id} : Updates an existing examen.
     *
     * @param id the id of the examen to save.
     * @param examen the examen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examen,
     * or with status {@code 400 (Bad Request)} if the examen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Examen> updateExamen(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Examen examen
    ) throws URISyntaxException {
        log.debug("REST request to update Examen : {}, {}", id, examen);
        if (examen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        examen = examenRepository.save(examen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examen.getId().toString()))
            .body(examen);
    }

    /**
     * {@code PATCH  /examen/:id} : Partial updates given fields of an existing examen, field will ignore if it is null
     *
     * @param id the id of the examen to save.
     * @param examen the examen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examen,
     * or with status {@code 400 (Bad Request)} if the examen is not valid,
     * or with status {@code 404 (Not Found)} if the examen is not found,
     * or with status {@code 500 (Internal Server Error)} if the examen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Examen> partialUpdateExamen(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Examen examen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Examen partially : {}, {}", id, examen);
        if (examen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Examen> result = examenRepository.findById(examen.getId()).map(examenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examen.getId().toString())
        );
    }

    /**
     * {@code GET  /examen} : get all the examen.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examen in body.
     */
    @GetMapping("")
    public List<Examen> getAllExamen() {
        log.debug("REST request to get all Examen");
        return examenRepository.findAll();
    }

    /**
     * {@code GET  /examen/:id} : get the "id" examen.
     *
     * @param id the id of the examen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Examen> getExamen(@PathVariable("id") Long id) {
        log.debug("REST request to get Examen : {}", id);
        Optional<Examen> examen = examenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(examen);
    }

    /**
     * {@code DELETE  /examen/:id} : delete the "id" examen.
     *
     * @param id the id of the examen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable("id") Long id) {
        log.debug("REST request to delete Examen : {}", id);
        examenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
