package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beoordeling;
import nl.ritense.demo.repository.BeoordelingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beoordeling}.
 */
@RestController
@RequestMapping("/api/beoordelings")
@Transactional
public class BeoordelingResource {

    private final Logger log = LoggerFactory.getLogger(BeoordelingResource.class);

    private static final String ENTITY_NAME = "beoordeling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeoordelingRepository beoordelingRepository;

    public BeoordelingResource(BeoordelingRepository beoordelingRepository) {
        this.beoordelingRepository = beoordelingRepository;
    }

    /**
     * {@code POST  /beoordelings} : Create a new beoordeling.
     *
     * @param beoordeling the beoordeling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beoordeling, or with status {@code 400 (Bad Request)} if the beoordeling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beoordeling> createBeoordeling(@RequestBody Beoordeling beoordeling) throws URISyntaxException {
        log.debug("REST request to save Beoordeling : {}", beoordeling);
        if (beoordeling.getId() != null) {
            throw new BadRequestAlertException("A new beoordeling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beoordeling = beoordelingRepository.save(beoordeling);
        return ResponseEntity.created(new URI("/api/beoordelings/" + beoordeling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beoordeling.getId().toString()))
            .body(beoordeling);
    }

    /**
     * {@code PUT  /beoordelings/:id} : Updates an existing beoordeling.
     *
     * @param id the id of the beoordeling to save.
     * @param beoordeling the beoordeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beoordeling,
     * or with status {@code 400 (Bad Request)} if the beoordeling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beoordeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beoordeling> updateBeoordeling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beoordeling beoordeling
    ) throws URISyntaxException {
        log.debug("REST request to update Beoordeling : {}, {}", id, beoordeling);
        if (beoordeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beoordeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beoordelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beoordeling = beoordelingRepository.save(beoordeling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beoordeling.getId().toString()))
            .body(beoordeling);
    }

    /**
     * {@code PATCH  /beoordelings/:id} : Partial updates given fields of an existing beoordeling, field will ignore if it is null
     *
     * @param id the id of the beoordeling to save.
     * @param beoordeling the beoordeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beoordeling,
     * or with status {@code 400 (Bad Request)} if the beoordeling is not valid,
     * or with status {@code 404 (Not Found)} if the beoordeling is not found,
     * or with status {@code 500 (Internal Server Error)} if the beoordeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beoordeling> partialUpdateBeoordeling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beoordeling beoordeling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beoordeling partially : {}, {}", id, beoordeling);
        if (beoordeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beoordeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beoordelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beoordeling> result = beoordelingRepository
            .findById(beoordeling.getId())
            .map(existingBeoordeling -> {
                if (beoordeling.getDatum() != null) {
                    existingBeoordeling.setDatum(beoordeling.getDatum());
                }
                if (beoordeling.getOmschrijving() != null) {
                    existingBeoordeling.setOmschrijving(beoordeling.getOmschrijving());
                }
                if (beoordeling.getOordeel() != null) {
                    existingBeoordeling.setOordeel(beoordeling.getOordeel());
                }

                return existingBeoordeling;
            })
            .map(beoordelingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beoordeling.getId().toString())
        );
    }

    /**
     * {@code GET  /beoordelings} : get all the beoordelings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beoordelings in body.
     */
    @GetMapping("")
    public List<Beoordeling> getAllBeoordelings() {
        log.debug("REST request to get all Beoordelings");
        return beoordelingRepository.findAll();
    }

    /**
     * {@code GET  /beoordelings/:id} : get the "id" beoordeling.
     *
     * @param id the id of the beoordeling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beoordeling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beoordeling> getBeoordeling(@PathVariable("id") Long id) {
        log.debug("REST request to get Beoordeling : {}", id);
        Optional<Beoordeling> beoordeling = beoordelingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beoordeling);
    }

    /**
     * {@code DELETE  /beoordelings/:id} : delete the "id" beoordeling.
     *
     * @param id the id of the beoordeling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeoordeling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beoordeling : {}", id);
        beoordelingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
