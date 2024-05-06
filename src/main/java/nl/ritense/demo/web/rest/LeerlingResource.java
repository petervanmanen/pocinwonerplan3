package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Leerling;
import nl.ritense.demo.repository.LeerlingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Leerling}.
 */
@RestController
@RequestMapping("/api/leerlings")
@Transactional
public class LeerlingResource {

    private final Logger log = LoggerFactory.getLogger(LeerlingResource.class);

    private static final String ENTITY_NAME = "leerling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeerlingRepository leerlingRepository;

    public LeerlingResource(LeerlingRepository leerlingRepository) {
        this.leerlingRepository = leerlingRepository;
    }

    /**
     * {@code POST  /leerlings} : Create a new leerling.
     *
     * @param leerling the leerling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leerling, or with status {@code 400 (Bad Request)} if the leerling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Leerling> createLeerling(@Valid @RequestBody Leerling leerling) throws URISyntaxException {
        log.debug("REST request to save Leerling : {}", leerling);
        if (leerling.getId() != null) {
            throw new BadRequestAlertException("A new leerling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        leerling = leerlingRepository.save(leerling);
        return ResponseEntity.created(new URI("/api/leerlings/" + leerling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, leerling.getId().toString()))
            .body(leerling);
    }

    /**
     * {@code PUT  /leerlings/:id} : Updates an existing leerling.
     *
     * @param id the id of the leerling to save.
     * @param leerling the leerling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leerling,
     * or with status {@code 400 (Bad Request)} if the leerling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leerling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Leerling> updateLeerling(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Leerling leerling
    ) throws URISyntaxException {
        log.debug("REST request to update Leerling : {}, {}", id, leerling);
        if (leerling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leerling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leerlingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        leerling = leerlingRepository.save(leerling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leerling.getId().toString()))
            .body(leerling);
    }

    /**
     * {@code PATCH  /leerlings/:id} : Partial updates given fields of an existing leerling, field will ignore if it is null
     *
     * @param id the id of the leerling to save.
     * @param leerling the leerling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leerling,
     * or with status {@code 400 (Bad Request)} if the leerling is not valid,
     * or with status {@code 404 (Not Found)} if the leerling is not found,
     * or with status {@code 500 (Internal Server Error)} if the leerling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Leerling> partialUpdateLeerling(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Leerling leerling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Leerling partially : {}, {}", id, leerling);
        if (leerling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leerling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leerlingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Leerling> result = leerlingRepository
            .findById(leerling.getId())
            .map(existingLeerling -> {
                if (leerling.getKwetsbarejongere() != null) {
                    existingLeerling.setKwetsbarejongere(leerling.getKwetsbarejongere());
                }

                return existingLeerling;
            })
            .map(leerlingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leerling.getId().toString())
        );
    }

    /**
     * {@code GET  /leerlings} : get all the leerlings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leerlings in body.
     */
    @GetMapping("")
    public List<Leerling> getAllLeerlings() {
        log.debug("REST request to get all Leerlings");
        return leerlingRepository.findAll();
    }

    /**
     * {@code GET  /leerlings/:id} : get the "id" leerling.
     *
     * @param id the id of the leerling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leerling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leerling> getLeerling(@PathVariable("id") Long id) {
        log.debug("REST request to get Leerling : {}", id);
        Optional<Leerling> leerling = leerlingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(leerling);
    }

    /**
     * {@code DELETE  /leerlings/:id} : delete the "id" leerling.
     *
     * @param id the id of the leerling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeerling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Leerling : {}", id);
        leerlingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
