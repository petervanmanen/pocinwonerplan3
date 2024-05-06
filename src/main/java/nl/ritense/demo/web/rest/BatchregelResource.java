package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Batchregel;
import nl.ritense.demo.repository.BatchregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Batchregel}.
 */
@RestController
@RequestMapping("/api/batchregels")
@Transactional
public class BatchregelResource {

    private final Logger log = LoggerFactory.getLogger(BatchregelResource.class);

    private static final String ENTITY_NAME = "batchregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatchregelRepository batchregelRepository;

    public BatchregelResource(BatchregelRepository batchregelRepository) {
        this.batchregelRepository = batchregelRepository;
    }

    /**
     * {@code POST  /batchregels} : Create a new batchregel.
     *
     * @param batchregel the batchregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batchregel, or with status {@code 400 (Bad Request)} if the batchregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Batchregel> createBatchregel(@RequestBody Batchregel batchregel) throws URISyntaxException {
        log.debug("REST request to save Batchregel : {}", batchregel);
        if (batchregel.getId() != null) {
            throw new BadRequestAlertException("A new batchregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        batchregel = batchregelRepository.save(batchregel);
        return ResponseEntity.created(new URI("/api/batchregels/" + batchregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, batchregel.getId().toString()))
            .body(batchregel);
    }

    /**
     * {@code PUT  /batchregels/:id} : Updates an existing batchregel.
     *
     * @param id the id of the batchregel to save.
     * @param batchregel the batchregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchregel,
     * or with status {@code 400 (Bad Request)} if the batchregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batchregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Batchregel> updateBatchregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Batchregel batchregel
    ) throws URISyntaxException {
        log.debug("REST request to update Batchregel : {}, {}", id, batchregel);
        if (batchregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        batchregel = batchregelRepository.save(batchregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, batchregel.getId().toString()))
            .body(batchregel);
    }

    /**
     * {@code PATCH  /batchregels/:id} : Partial updates given fields of an existing batchregel, field will ignore if it is null
     *
     * @param id the id of the batchregel to save.
     * @param batchregel the batchregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchregel,
     * or with status {@code 400 (Bad Request)} if the batchregel is not valid,
     * or with status {@code 404 (Not Found)} if the batchregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the batchregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Batchregel> partialUpdateBatchregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Batchregel batchregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Batchregel partially : {}, {}", id, batchregel);
        if (batchregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batchregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batchregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Batchregel> result = batchregelRepository
            .findById(batchregel.getId())
            .map(existingBatchregel -> {
                if (batchregel.getBedrag() != null) {
                    existingBatchregel.setBedrag(batchregel.getBedrag());
                }
                if (batchregel.getDatumbetaling() != null) {
                    existingBatchregel.setDatumbetaling(batchregel.getDatumbetaling());
                }
                if (batchregel.getOmschrijving() != null) {
                    existingBatchregel.setOmschrijving(batchregel.getOmschrijving());
                }
                if (batchregel.getRekeningnaar() != null) {
                    existingBatchregel.setRekeningnaar(batchregel.getRekeningnaar());
                }
                if (batchregel.getRekeningvan() != null) {
                    existingBatchregel.setRekeningvan(batchregel.getRekeningvan());
                }

                return existingBatchregel;
            })
            .map(batchregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, batchregel.getId().toString())
        );
    }

    /**
     * {@code GET  /batchregels} : get all the batchregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batchregels in body.
     */
    @GetMapping("")
    public List<Batchregel> getAllBatchregels() {
        log.debug("REST request to get all Batchregels");
        return batchregelRepository.findAll();
    }

    /**
     * {@code GET  /batchregels/:id} : get the "id" batchregel.
     *
     * @param id the id of the batchregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batchregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Batchregel> getBatchregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Batchregel : {}", id);
        Optional<Batchregel> batchregel = batchregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(batchregel);
    }

    /**
     * {@code DELETE  /batchregels/:id} : delete the "id" batchregel.
     *
     * @param id the id of the batchregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatchregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Batchregel : {}", id);
        batchregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
