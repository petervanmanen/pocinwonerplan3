package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Belijning;
import nl.ritense.demo.repository.BelijningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Belijning}.
 */
@RestController
@RequestMapping("/api/belijnings")
@Transactional
public class BelijningResource {

    private final Logger log = LoggerFactory.getLogger(BelijningResource.class);

    private static final String ENTITY_NAME = "belijning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BelijningRepository belijningRepository;

    public BelijningResource(BelijningRepository belijningRepository) {
        this.belijningRepository = belijningRepository;
    }

    /**
     * {@code POST  /belijnings} : Create a new belijning.
     *
     * @param belijning the belijning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new belijning, or with status {@code 400 (Bad Request)} if the belijning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Belijning> createBelijning(@RequestBody Belijning belijning) throws URISyntaxException {
        log.debug("REST request to save Belijning : {}", belijning);
        if (belijning.getId() != null) {
            throw new BadRequestAlertException("A new belijning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        belijning = belijningRepository.save(belijning);
        return ResponseEntity.created(new URI("/api/belijnings/" + belijning.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, belijning.getId().toString()))
            .body(belijning);
    }

    /**
     * {@code PUT  /belijnings/:id} : Updates an existing belijning.
     *
     * @param id the id of the belijning to save.
     * @param belijning the belijning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated belijning,
     * or with status {@code 400 (Bad Request)} if the belijning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the belijning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Belijning> updateBelijning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Belijning belijning
    ) throws URISyntaxException {
        log.debug("REST request to update Belijning : {}, {}", id, belijning);
        if (belijning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, belijning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!belijningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        belijning = belijningRepository.save(belijning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, belijning.getId().toString()))
            .body(belijning);
    }

    /**
     * {@code PATCH  /belijnings/:id} : Partial updates given fields of an existing belijning, field will ignore if it is null
     *
     * @param id the id of the belijning to save.
     * @param belijning the belijning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated belijning,
     * or with status {@code 400 (Bad Request)} if the belijning is not valid,
     * or with status {@code 404 (Not Found)} if the belijning is not found,
     * or with status {@code 500 (Internal Server Error)} if the belijning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Belijning> partialUpdateBelijning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Belijning belijning
    ) throws URISyntaxException {
        log.debug("REST request to partial update Belijning partially : {}, {}", id, belijning);
        if (belijning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, belijning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!belijningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Belijning> result = belijningRepository
            .findById(belijning.getId())
            .map(existingBelijning -> {
                if (belijning.getNaam() != null) {
                    existingBelijning.setNaam(belijning.getNaam());
                }

                return existingBelijning;
            })
            .map(belijningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, belijning.getId().toString())
        );
    }

    /**
     * {@code GET  /belijnings} : get all the belijnings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of belijnings in body.
     */
    @GetMapping("")
    public List<Belijning> getAllBelijnings() {
        log.debug("REST request to get all Belijnings");
        return belijningRepository.findAll();
    }

    /**
     * {@code GET  /belijnings/:id} : get the "id" belijning.
     *
     * @param id the id of the belijning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the belijning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Belijning> getBelijning(@PathVariable("id") Long id) {
        log.debug("REST request to get Belijning : {}", id);
        Optional<Belijning> belijning = belijningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(belijning);
    }

    /**
     * {@code DELETE  /belijnings/:id} : delete the "id" belijning.
     *
     * @param id the id of the belijning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBelijning(@PathVariable("id") Long id) {
        log.debug("REST request to delete Belijning : {}", id);
        belijningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
