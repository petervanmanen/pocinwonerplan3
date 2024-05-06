package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ambacht;
import nl.ritense.demo.repository.AmbachtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ambacht}.
 */
@RestController
@RequestMapping("/api/ambachts")
@Transactional
public class AmbachtResource {

    private final Logger log = LoggerFactory.getLogger(AmbachtResource.class);

    private static final String ENTITY_NAME = "ambacht";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmbachtRepository ambachtRepository;

    public AmbachtResource(AmbachtRepository ambachtRepository) {
        this.ambachtRepository = ambachtRepository;
    }

    /**
     * {@code POST  /ambachts} : Create a new ambacht.
     *
     * @param ambacht the ambacht to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ambacht, or with status {@code 400 (Bad Request)} if the ambacht has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ambacht> createAmbacht(@RequestBody Ambacht ambacht) throws URISyntaxException {
        log.debug("REST request to save Ambacht : {}", ambacht);
        if (ambacht.getId() != null) {
            throw new BadRequestAlertException("A new ambacht cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ambacht = ambachtRepository.save(ambacht);
        return ResponseEntity.created(new URI("/api/ambachts/" + ambacht.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ambacht.getId().toString()))
            .body(ambacht);
    }

    /**
     * {@code PUT  /ambachts/:id} : Updates an existing ambacht.
     *
     * @param id the id of the ambacht to save.
     * @param ambacht the ambacht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ambacht,
     * or with status {@code 400 (Bad Request)} if the ambacht is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ambacht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ambacht> updateAmbacht(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ambacht ambacht)
        throws URISyntaxException {
        log.debug("REST request to update Ambacht : {}, {}", id, ambacht);
        if (ambacht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ambacht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ambachtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ambacht = ambachtRepository.save(ambacht);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ambacht.getId().toString()))
            .body(ambacht);
    }

    /**
     * {@code PATCH  /ambachts/:id} : Partial updates given fields of an existing ambacht, field will ignore if it is null
     *
     * @param id the id of the ambacht to save.
     * @param ambacht the ambacht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ambacht,
     * or with status {@code 400 (Bad Request)} if the ambacht is not valid,
     * or with status {@code 404 (Not Found)} if the ambacht is not found,
     * or with status {@code 500 (Internal Server Error)} if the ambacht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ambacht> partialUpdateAmbacht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ambacht ambacht
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ambacht partially : {}, {}", id, ambacht);
        if (ambacht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ambacht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ambachtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ambacht> result = ambachtRepository
            .findById(ambacht.getId())
            .map(existingAmbacht -> {
                if (ambacht.getAmbachtsoort() != null) {
                    existingAmbacht.setAmbachtsoort(ambacht.getAmbachtsoort());
                }
                if (ambacht.getJaarambachttot() != null) {
                    existingAmbacht.setJaarambachttot(ambacht.getJaarambachttot());
                }
                if (ambacht.getJaarambachtvanaf() != null) {
                    existingAmbacht.setJaarambachtvanaf(ambacht.getJaarambachtvanaf());
                }

                return existingAmbacht;
            })
            .map(ambachtRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ambacht.getId().toString())
        );
    }

    /**
     * {@code GET  /ambachts} : get all the ambachts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ambachts in body.
     */
    @GetMapping("")
    public List<Ambacht> getAllAmbachts() {
        log.debug("REST request to get all Ambachts");
        return ambachtRepository.findAll();
    }

    /**
     * {@code GET  /ambachts/:id} : get the "id" ambacht.
     *
     * @param id the id of the ambacht to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ambacht, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ambacht> getAmbacht(@PathVariable("id") Long id) {
        log.debug("REST request to get Ambacht : {}", id);
        Optional<Ambacht> ambacht = ambachtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ambacht);
    }

    /**
     * {@code DELETE  /ambachts/:id} : delete the "id" ambacht.
     *
     * @param id the id of the ambacht to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmbacht(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ambacht : {}", id);
        ambachtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
