package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Handelsnamenvestiging;
import nl.ritense.demo.repository.HandelsnamenvestigingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Handelsnamenvestiging}.
 */
@RestController
@RequestMapping("/api/handelsnamenvestigings")
@Transactional
public class HandelsnamenvestigingResource {

    private final Logger log = LoggerFactory.getLogger(HandelsnamenvestigingResource.class);

    private static final String ENTITY_NAME = "handelsnamenvestiging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HandelsnamenvestigingRepository handelsnamenvestigingRepository;

    public HandelsnamenvestigingResource(HandelsnamenvestigingRepository handelsnamenvestigingRepository) {
        this.handelsnamenvestigingRepository = handelsnamenvestigingRepository;
    }

    /**
     * {@code POST  /handelsnamenvestigings} : Create a new handelsnamenvestiging.
     *
     * @param handelsnamenvestiging the handelsnamenvestiging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new handelsnamenvestiging, or with status {@code 400 (Bad Request)} if the handelsnamenvestiging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Handelsnamenvestiging> createHandelsnamenvestiging(@RequestBody Handelsnamenvestiging handelsnamenvestiging)
        throws URISyntaxException {
        log.debug("REST request to save Handelsnamenvestiging : {}", handelsnamenvestiging);
        if (handelsnamenvestiging.getId() != null) {
            throw new BadRequestAlertException("A new handelsnamenvestiging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        handelsnamenvestiging = handelsnamenvestigingRepository.save(handelsnamenvestiging);
        return ResponseEntity.created(new URI("/api/handelsnamenvestigings/" + handelsnamenvestiging.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, handelsnamenvestiging.getId().toString()))
            .body(handelsnamenvestiging);
    }

    /**
     * {@code PUT  /handelsnamenvestigings/:id} : Updates an existing handelsnamenvestiging.
     *
     * @param id the id of the handelsnamenvestiging to save.
     * @param handelsnamenvestiging the handelsnamenvestiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated handelsnamenvestiging,
     * or with status {@code 400 (Bad Request)} if the handelsnamenvestiging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the handelsnamenvestiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Handelsnamenvestiging> updateHandelsnamenvestiging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Handelsnamenvestiging handelsnamenvestiging
    ) throws URISyntaxException {
        log.debug("REST request to update Handelsnamenvestiging : {}, {}", id, handelsnamenvestiging);
        if (handelsnamenvestiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, handelsnamenvestiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!handelsnamenvestigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        handelsnamenvestiging = handelsnamenvestigingRepository.save(handelsnamenvestiging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, handelsnamenvestiging.getId().toString()))
            .body(handelsnamenvestiging);
    }

    /**
     * {@code PATCH  /handelsnamenvestigings/:id} : Partial updates given fields of an existing handelsnamenvestiging, field will ignore if it is null
     *
     * @param id the id of the handelsnamenvestiging to save.
     * @param handelsnamenvestiging the handelsnamenvestiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated handelsnamenvestiging,
     * or with status {@code 400 (Bad Request)} if the handelsnamenvestiging is not valid,
     * or with status {@code 404 (Not Found)} if the handelsnamenvestiging is not found,
     * or with status {@code 500 (Internal Server Error)} if the handelsnamenvestiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Handelsnamenvestiging> partialUpdateHandelsnamenvestiging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Handelsnamenvestiging handelsnamenvestiging
    ) throws URISyntaxException {
        log.debug("REST request to partial update Handelsnamenvestiging partially : {}, {}", id, handelsnamenvestiging);
        if (handelsnamenvestiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, handelsnamenvestiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!handelsnamenvestigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Handelsnamenvestiging> result = handelsnamenvestigingRepository
            .findById(handelsnamenvestiging.getId())
            .map(existingHandelsnamenvestiging -> {
                if (handelsnamenvestiging.getHandelsnaam() != null) {
                    existingHandelsnamenvestiging.setHandelsnaam(handelsnamenvestiging.getHandelsnaam());
                }
                if (handelsnamenvestiging.getVerkortenaam() != null) {
                    existingHandelsnamenvestiging.setVerkortenaam(handelsnamenvestiging.getVerkortenaam());
                }
                if (handelsnamenvestiging.getVolgorde() != null) {
                    existingHandelsnamenvestiging.setVolgorde(handelsnamenvestiging.getVolgorde());
                }

                return existingHandelsnamenvestiging;
            })
            .map(handelsnamenvestigingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, handelsnamenvestiging.getId().toString())
        );
    }

    /**
     * {@code GET  /handelsnamenvestigings} : get all the handelsnamenvestigings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of handelsnamenvestigings in body.
     */
    @GetMapping("")
    public List<Handelsnamenvestiging> getAllHandelsnamenvestigings() {
        log.debug("REST request to get all Handelsnamenvestigings");
        return handelsnamenvestigingRepository.findAll();
    }

    /**
     * {@code GET  /handelsnamenvestigings/:id} : get the "id" handelsnamenvestiging.
     *
     * @param id the id of the handelsnamenvestiging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the handelsnamenvestiging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Handelsnamenvestiging> getHandelsnamenvestiging(@PathVariable("id") Long id) {
        log.debug("REST request to get Handelsnamenvestiging : {}", id);
        Optional<Handelsnamenvestiging> handelsnamenvestiging = handelsnamenvestigingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(handelsnamenvestiging);
    }

    /**
     * {@code DELETE  /handelsnamenvestigings/:id} : delete the "id" handelsnamenvestiging.
     *
     * @param id the id of the handelsnamenvestiging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHandelsnamenvestiging(@PathVariable("id") Long id) {
        log.debug("REST request to delete Handelsnamenvestiging : {}", id);
        handelsnamenvestigingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
