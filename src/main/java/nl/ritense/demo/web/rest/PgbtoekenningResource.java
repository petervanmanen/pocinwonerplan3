package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Pgbtoekenning;
import nl.ritense.demo.repository.PgbtoekenningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Pgbtoekenning}.
 */
@RestController
@RequestMapping("/api/pgbtoekennings")
@Transactional
public class PgbtoekenningResource {

    private final Logger log = LoggerFactory.getLogger(PgbtoekenningResource.class);

    private static final String ENTITY_NAME = "pgbtoekenning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PgbtoekenningRepository pgbtoekenningRepository;

    public PgbtoekenningResource(PgbtoekenningRepository pgbtoekenningRepository) {
        this.pgbtoekenningRepository = pgbtoekenningRepository;
    }

    /**
     * {@code POST  /pgbtoekennings} : Create a new pgbtoekenning.
     *
     * @param pgbtoekenning the pgbtoekenning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pgbtoekenning, or with status {@code 400 (Bad Request)} if the pgbtoekenning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pgbtoekenning> createPgbtoekenning(@RequestBody Pgbtoekenning pgbtoekenning) throws URISyntaxException {
        log.debug("REST request to save Pgbtoekenning : {}", pgbtoekenning);
        if (pgbtoekenning.getId() != null) {
            throw new BadRequestAlertException("A new pgbtoekenning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pgbtoekenning = pgbtoekenningRepository.save(pgbtoekenning);
        return ResponseEntity.created(new URI("/api/pgbtoekennings/" + pgbtoekenning.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, pgbtoekenning.getId().toString()))
            .body(pgbtoekenning);
    }

    /**
     * {@code PUT  /pgbtoekennings/:id} : Updates an existing pgbtoekenning.
     *
     * @param id the id of the pgbtoekenning to save.
     * @param pgbtoekenning the pgbtoekenning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgbtoekenning,
     * or with status {@code 400 (Bad Request)} if the pgbtoekenning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pgbtoekenning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pgbtoekenning> updatePgbtoekenning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Pgbtoekenning pgbtoekenning
    ) throws URISyntaxException {
        log.debug("REST request to update Pgbtoekenning : {}, {}", id, pgbtoekenning);
        if (pgbtoekenning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pgbtoekenning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pgbtoekenningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pgbtoekenning = pgbtoekenningRepository.save(pgbtoekenning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pgbtoekenning.getId().toString()))
            .body(pgbtoekenning);
    }

    /**
     * {@code PATCH  /pgbtoekennings/:id} : Partial updates given fields of an existing pgbtoekenning, field will ignore if it is null
     *
     * @param id the id of the pgbtoekenning to save.
     * @param pgbtoekenning the pgbtoekenning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pgbtoekenning,
     * or with status {@code 400 (Bad Request)} if the pgbtoekenning is not valid,
     * or with status {@code 404 (Not Found)} if the pgbtoekenning is not found,
     * or with status {@code 500 (Internal Server Error)} if the pgbtoekenning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pgbtoekenning> partialUpdatePgbtoekenning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Pgbtoekenning pgbtoekenning
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pgbtoekenning partially : {}, {}", id, pgbtoekenning);
        if (pgbtoekenning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pgbtoekenning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pgbtoekenningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pgbtoekenning> result = pgbtoekenningRepository
            .findById(pgbtoekenning.getId())
            .map(existingPgbtoekenning -> {
                if (pgbtoekenning.getBudget() != null) {
                    existingPgbtoekenning.setBudget(pgbtoekenning.getBudget());
                }
                if (pgbtoekenning.getDatumeinde() != null) {
                    existingPgbtoekenning.setDatumeinde(pgbtoekenning.getDatumeinde());
                }
                if (pgbtoekenning.getDatumtoekenning() != null) {
                    existingPgbtoekenning.setDatumtoekenning(pgbtoekenning.getDatumtoekenning());
                }

                return existingPgbtoekenning;
            })
            .map(pgbtoekenningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pgbtoekenning.getId().toString())
        );
    }

    /**
     * {@code GET  /pgbtoekennings} : get all the pgbtoekennings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pgbtoekennings in body.
     */
    @GetMapping("")
    public List<Pgbtoekenning> getAllPgbtoekennings() {
        log.debug("REST request to get all Pgbtoekennings");
        return pgbtoekenningRepository.findAll();
    }

    /**
     * {@code GET  /pgbtoekennings/:id} : get the "id" pgbtoekenning.
     *
     * @param id the id of the pgbtoekenning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pgbtoekenning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pgbtoekenning> getPgbtoekenning(@PathVariable("id") Long id) {
        log.debug("REST request to get Pgbtoekenning : {}", id);
        Optional<Pgbtoekenning> pgbtoekenning = pgbtoekenningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pgbtoekenning);
    }

    /**
     * {@code DELETE  /pgbtoekennings/:id} : delete the "id" pgbtoekenning.
     *
     * @param id the id of the pgbtoekenning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePgbtoekenning(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pgbtoekenning : {}", id);
        pgbtoekenningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
