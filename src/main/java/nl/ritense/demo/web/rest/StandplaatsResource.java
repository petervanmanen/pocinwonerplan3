package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Standplaats;
import nl.ritense.demo.repository.StandplaatsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Standplaats}.
 */
@RestController
@RequestMapping("/api/standplaats")
@Transactional
public class StandplaatsResource {

    private final Logger log = LoggerFactory.getLogger(StandplaatsResource.class);

    private static final String ENTITY_NAME = "standplaats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StandplaatsRepository standplaatsRepository;

    public StandplaatsResource(StandplaatsRepository standplaatsRepository) {
        this.standplaatsRepository = standplaatsRepository;
    }

    /**
     * {@code POST  /standplaats} : Create a new standplaats.
     *
     * @param standplaats the standplaats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new standplaats, or with status {@code 400 (Bad Request)} if the standplaats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Standplaats> createStandplaats(@RequestBody Standplaats standplaats) throws URISyntaxException {
        log.debug("REST request to save Standplaats : {}", standplaats);
        if (standplaats.getId() != null) {
            throw new BadRequestAlertException("A new standplaats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        standplaats = standplaatsRepository.save(standplaats);
        return ResponseEntity.created(new URI("/api/standplaats/" + standplaats.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, standplaats.getId().toString()))
            .body(standplaats);
    }

    /**
     * {@code PUT  /standplaats/:id} : Updates an existing standplaats.
     *
     * @param id the id of the standplaats to save.
     * @param standplaats the standplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standplaats,
     * or with status {@code 400 (Bad Request)} if the standplaats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the standplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Standplaats> updateStandplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Standplaats standplaats
    ) throws URISyntaxException {
        log.debug("REST request to update Standplaats : {}, {}", id, standplaats);
        if (standplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        standplaats = standplaatsRepository.save(standplaats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standplaats.getId().toString()))
            .body(standplaats);
    }

    /**
     * {@code PATCH  /standplaats/:id} : Partial updates given fields of an existing standplaats, field will ignore if it is null
     *
     * @param id the id of the standplaats to save.
     * @param standplaats the standplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standplaats,
     * or with status {@code 400 (Bad Request)} if the standplaats is not valid,
     * or with status {@code 404 (Not Found)} if the standplaats is not found,
     * or with status {@code 500 (Internal Server Error)} if the standplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Standplaats> partialUpdateStandplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Standplaats standplaats
    ) throws URISyntaxException {
        log.debug("REST request to partial update Standplaats partially : {}, {}", id, standplaats);
        if (standplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Standplaats> result = standplaatsRepository
            .findById(standplaats.getId())
            .map(existingStandplaats -> {
                if (standplaats.getAdres() != null) {
                    existingStandplaats.setAdres(standplaats.getAdres());
                }
                if (standplaats.getBeschrijving() != null) {
                    existingStandplaats.setBeschrijving(standplaats.getBeschrijving());
                }
                if (standplaats.getNaaminstelling() != null) {
                    existingStandplaats.setNaaminstelling(standplaats.getNaaminstelling());
                }

                return existingStandplaats;
            })
            .map(standplaatsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standplaats.getId().toString())
        );
    }

    /**
     * {@code GET  /standplaats} : get all the standplaats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of standplaats in body.
     */
    @GetMapping("")
    public List<Standplaats> getAllStandplaats() {
        log.debug("REST request to get all Standplaats");
        return standplaatsRepository.findAll();
    }

    /**
     * {@code GET  /standplaats/:id} : get the "id" standplaats.
     *
     * @param id the id of the standplaats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the standplaats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Standplaats> getStandplaats(@PathVariable("id") Long id) {
        log.debug("REST request to get Standplaats : {}", id);
        Optional<Standplaats> standplaats = standplaatsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(standplaats);
    }

    /**
     * {@code DELETE  /standplaats/:id} : delete the "id" standplaats.
     *
     * @param id the id of the standplaats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandplaats(@PathVariable("id") Long id) {
        log.debug("REST request to delete Standplaats : {}", id);
        standplaatsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
