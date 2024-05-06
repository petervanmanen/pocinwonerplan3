package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bevoegdgezag;
import nl.ritense.demo.repository.BevoegdgezagRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bevoegdgezag}.
 */
@RestController
@RequestMapping("/api/bevoegdgezags")
@Transactional
public class BevoegdgezagResource {

    private final Logger log = LoggerFactory.getLogger(BevoegdgezagResource.class);

    private static final String ENTITY_NAME = "bevoegdgezag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BevoegdgezagRepository bevoegdgezagRepository;

    public BevoegdgezagResource(BevoegdgezagRepository bevoegdgezagRepository) {
        this.bevoegdgezagRepository = bevoegdgezagRepository;
    }

    /**
     * {@code POST  /bevoegdgezags} : Create a new bevoegdgezag.
     *
     * @param bevoegdgezag the bevoegdgezag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bevoegdgezag, or with status {@code 400 (Bad Request)} if the bevoegdgezag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bevoegdgezag> createBevoegdgezag(@RequestBody Bevoegdgezag bevoegdgezag) throws URISyntaxException {
        log.debug("REST request to save Bevoegdgezag : {}", bevoegdgezag);
        if (bevoegdgezag.getId() != null) {
            throw new BadRequestAlertException("A new bevoegdgezag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bevoegdgezag = bevoegdgezagRepository.save(bevoegdgezag);
        return ResponseEntity.created(new URI("/api/bevoegdgezags/" + bevoegdgezag.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bevoegdgezag.getId().toString()))
            .body(bevoegdgezag);
    }

    /**
     * {@code PUT  /bevoegdgezags/:id} : Updates an existing bevoegdgezag.
     *
     * @param id the id of the bevoegdgezag to save.
     * @param bevoegdgezag the bevoegdgezag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bevoegdgezag,
     * or with status {@code 400 (Bad Request)} if the bevoegdgezag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bevoegdgezag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bevoegdgezag> updateBevoegdgezag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bevoegdgezag bevoegdgezag
    ) throws URISyntaxException {
        log.debug("REST request to update Bevoegdgezag : {}, {}", id, bevoegdgezag);
        if (bevoegdgezag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bevoegdgezag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bevoegdgezagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bevoegdgezag = bevoegdgezagRepository.save(bevoegdgezag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bevoegdgezag.getId().toString()))
            .body(bevoegdgezag);
    }

    /**
     * {@code PATCH  /bevoegdgezags/:id} : Partial updates given fields of an existing bevoegdgezag, field will ignore if it is null
     *
     * @param id the id of the bevoegdgezag to save.
     * @param bevoegdgezag the bevoegdgezag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bevoegdgezag,
     * or with status {@code 400 (Bad Request)} if the bevoegdgezag is not valid,
     * or with status {@code 404 (Not Found)} if the bevoegdgezag is not found,
     * or with status {@code 500 (Internal Server Error)} if the bevoegdgezag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bevoegdgezag> partialUpdateBevoegdgezag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bevoegdgezag bevoegdgezag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bevoegdgezag partially : {}, {}", id, bevoegdgezag);
        if (bevoegdgezag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bevoegdgezag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bevoegdgezagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bevoegdgezag> result = bevoegdgezagRepository
            .findById(bevoegdgezag.getId())
            .map(existingBevoegdgezag -> {
                if (bevoegdgezag.getNaam() != null) {
                    existingBevoegdgezag.setNaam(bevoegdgezag.getNaam());
                }

                return existingBevoegdgezag;
            })
            .map(bevoegdgezagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bevoegdgezag.getId().toString())
        );
    }

    /**
     * {@code GET  /bevoegdgezags} : get all the bevoegdgezags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bevoegdgezags in body.
     */
    @GetMapping("")
    public List<Bevoegdgezag> getAllBevoegdgezags() {
        log.debug("REST request to get all Bevoegdgezags");
        return bevoegdgezagRepository.findAll();
    }

    /**
     * {@code GET  /bevoegdgezags/:id} : get the "id" bevoegdgezag.
     *
     * @param id the id of the bevoegdgezag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bevoegdgezag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bevoegdgezag> getBevoegdgezag(@PathVariable("id") Long id) {
        log.debug("REST request to get Bevoegdgezag : {}", id);
        Optional<Bevoegdgezag> bevoegdgezag = bevoegdgezagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bevoegdgezag);
    }

    /**
     * {@code DELETE  /bevoegdgezags/:id} : delete the "id" bevoegdgezag.
     *
     * @param id the id of the bevoegdgezag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBevoegdgezag(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bevoegdgezag : {}", id);
        bevoegdgezagRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
