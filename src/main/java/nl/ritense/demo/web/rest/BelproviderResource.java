package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Belprovider;
import nl.ritense.demo.repository.BelproviderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Belprovider}.
 */
@RestController
@RequestMapping("/api/belproviders")
@Transactional
public class BelproviderResource {

    private final Logger log = LoggerFactory.getLogger(BelproviderResource.class);

    private static final String ENTITY_NAME = "belprovider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BelproviderRepository belproviderRepository;

    public BelproviderResource(BelproviderRepository belproviderRepository) {
        this.belproviderRepository = belproviderRepository;
    }

    /**
     * {@code POST  /belproviders} : Create a new belprovider.
     *
     * @param belprovider the belprovider to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new belprovider, or with status {@code 400 (Bad Request)} if the belprovider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Belprovider> createBelprovider(@RequestBody Belprovider belprovider) throws URISyntaxException {
        log.debug("REST request to save Belprovider : {}", belprovider);
        if (belprovider.getId() != null) {
            throw new BadRequestAlertException("A new belprovider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        belprovider = belproviderRepository.save(belprovider);
        return ResponseEntity.created(new URI("/api/belproviders/" + belprovider.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, belprovider.getId().toString()))
            .body(belprovider);
    }

    /**
     * {@code PUT  /belproviders/:id} : Updates an existing belprovider.
     *
     * @param id the id of the belprovider to save.
     * @param belprovider the belprovider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated belprovider,
     * or with status {@code 400 (Bad Request)} if the belprovider is not valid,
     * or with status {@code 500 (Internal Server Error)} if the belprovider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Belprovider> updateBelprovider(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Belprovider belprovider
    ) throws URISyntaxException {
        log.debug("REST request to update Belprovider : {}, {}", id, belprovider);
        if (belprovider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, belprovider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!belproviderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        belprovider = belproviderRepository.save(belprovider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, belprovider.getId().toString()))
            .body(belprovider);
    }

    /**
     * {@code PATCH  /belproviders/:id} : Partial updates given fields of an existing belprovider, field will ignore if it is null
     *
     * @param id the id of the belprovider to save.
     * @param belprovider the belprovider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated belprovider,
     * or with status {@code 400 (Bad Request)} if the belprovider is not valid,
     * or with status {@code 404 (Not Found)} if the belprovider is not found,
     * or with status {@code 500 (Internal Server Error)} if the belprovider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Belprovider> partialUpdateBelprovider(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Belprovider belprovider
    ) throws URISyntaxException {
        log.debug("REST request to partial update Belprovider partially : {}, {}", id, belprovider);
        if (belprovider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, belprovider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!belproviderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Belprovider> result = belproviderRepository
            .findById(belprovider.getId())
            .map(existingBelprovider -> {
                if (belprovider.getCode() != null) {
                    existingBelprovider.setCode(belprovider.getCode());
                }

                return existingBelprovider;
            })
            .map(belproviderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, belprovider.getId().toString())
        );
    }

    /**
     * {@code GET  /belproviders} : get all the belproviders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of belproviders in body.
     */
    @GetMapping("")
    public List<Belprovider> getAllBelproviders() {
        log.debug("REST request to get all Belproviders");
        return belproviderRepository.findAll();
    }

    /**
     * {@code GET  /belproviders/:id} : get the "id" belprovider.
     *
     * @param id the id of the belprovider to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the belprovider, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Belprovider> getBelprovider(@PathVariable("id") Long id) {
        log.debug("REST request to get Belprovider : {}", id);
        Optional<Belprovider> belprovider = belproviderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(belprovider);
    }

    /**
     * {@code DELETE  /belproviders/:id} : delete the "id" belprovider.
     *
     * @param id the id of the belprovider to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBelprovider(@PathVariable("id") Long id) {
        log.debug("REST request to delete Belprovider : {}", id);
        belproviderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
