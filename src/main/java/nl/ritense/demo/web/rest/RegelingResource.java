package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Regeling;
import nl.ritense.demo.repository.RegelingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Regeling}.
 */
@RestController
@RequestMapping("/api/regelings")
@Transactional
public class RegelingResource {

    private final Logger log = LoggerFactory.getLogger(RegelingResource.class);

    private static final String ENTITY_NAME = "regeling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegelingRepository regelingRepository;

    public RegelingResource(RegelingRepository regelingRepository) {
        this.regelingRepository = regelingRepository;
    }

    /**
     * {@code POST  /regelings} : Create a new regeling.
     *
     * @param regeling the regeling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regeling, or with status {@code 400 (Bad Request)} if the regeling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Regeling> createRegeling(@RequestBody Regeling regeling) throws URISyntaxException {
        log.debug("REST request to save Regeling : {}", regeling);
        if (regeling.getId() != null) {
            throw new BadRequestAlertException("A new regeling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        regeling = regelingRepository.save(regeling);
        return ResponseEntity.created(new URI("/api/regelings/" + regeling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, regeling.getId().toString()))
            .body(regeling);
    }

    /**
     * {@code PUT  /regelings/:id} : Updates an existing regeling.
     *
     * @param id the id of the regeling to save.
     * @param regeling the regeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regeling,
     * or with status {@code 400 (Bad Request)} if the regeling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Regeling> updateRegeling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Regeling regeling
    ) throws URISyntaxException {
        log.debug("REST request to update Regeling : {}, {}", id, regeling);
        if (regeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        regeling = regelingRepository.save(regeling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regeling.getId().toString()))
            .body(regeling);
    }

    /**
     * {@code PATCH  /regelings/:id} : Partial updates given fields of an existing regeling, field will ignore if it is null
     *
     * @param id the id of the regeling to save.
     * @param regeling the regeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regeling,
     * or with status {@code 400 (Bad Request)} if the regeling is not valid,
     * or with status {@code 404 (Not Found)} if the regeling is not found,
     * or with status {@code 500 (Internal Server Error)} if the regeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Regeling> partialUpdateRegeling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Regeling regeling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Regeling partially : {}, {}", id, regeling);
        if (regeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Regeling> result = regelingRepository
            .findById(regeling.getId())
            .map(existingRegeling -> {
                if (regeling.getDatumeinde() != null) {
                    existingRegeling.setDatumeinde(regeling.getDatumeinde());
                }
                if (regeling.getDatumstart() != null) {
                    existingRegeling.setDatumstart(regeling.getDatumstart());
                }
                if (regeling.getDatumtoekenning() != null) {
                    existingRegeling.setDatumtoekenning(regeling.getDatumtoekenning());
                }
                if (regeling.getOmschrijving() != null) {
                    existingRegeling.setOmschrijving(regeling.getOmschrijving());
                }

                return existingRegeling;
            })
            .map(regelingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regeling.getId().toString())
        );
    }

    /**
     * {@code GET  /regelings} : get all the regelings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regelings in body.
     */
    @GetMapping("")
    public List<Regeling> getAllRegelings() {
        log.debug("REST request to get all Regelings");
        return regelingRepository.findAll();
    }

    /**
     * {@code GET  /regelings/:id} : get the "id" regeling.
     *
     * @param id the id of the regeling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regeling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Regeling> getRegeling(@PathVariable("id") Long id) {
        log.debug("REST request to get Regeling : {}", id);
        Optional<Regeling> regeling = regelingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regeling);
    }

    /**
     * {@code DELETE  /regelings/:id} : delete the "id" regeling.
     *
     * @param id the id of the regeling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegeling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Regeling : {}", id);
        regelingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
