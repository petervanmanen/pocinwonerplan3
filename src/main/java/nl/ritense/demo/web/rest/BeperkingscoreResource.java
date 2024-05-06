package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beperkingscore;
import nl.ritense.demo.repository.BeperkingscoreRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beperkingscore}.
 */
@RestController
@RequestMapping("/api/beperkingscores")
@Transactional
public class BeperkingscoreResource {

    private final Logger log = LoggerFactory.getLogger(BeperkingscoreResource.class);

    private static final String ENTITY_NAME = "beperkingscore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeperkingscoreRepository beperkingscoreRepository;

    public BeperkingscoreResource(BeperkingscoreRepository beperkingscoreRepository) {
        this.beperkingscoreRepository = beperkingscoreRepository;
    }

    /**
     * {@code POST  /beperkingscores} : Create a new beperkingscore.
     *
     * @param beperkingscore the beperkingscore to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beperkingscore, or with status {@code 400 (Bad Request)} if the beperkingscore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beperkingscore> createBeperkingscore(@RequestBody Beperkingscore beperkingscore) throws URISyntaxException {
        log.debug("REST request to save Beperkingscore : {}", beperkingscore);
        if (beperkingscore.getId() != null) {
            throw new BadRequestAlertException("A new beperkingscore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beperkingscore = beperkingscoreRepository.save(beperkingscore);
        return ResponseEntity.created(new URI("/api/beperkingscores/" + beperkingscore.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beperkingscore.getId().toString()))
            .body(beperkingscore);
    }

    /**
     * {@code PUT  /beperkingscores/:id} : Updates an existing beperkingscore.
     *
     * @param id the id of the beperkingscore to save.
     * @param beperkingscore the beperkingscore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperkingscore,
     * or with status {@code 400 (Bad Request)} if the beperkingscore is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beperkingscore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beperkingscore> updateBeperkingscore(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beperkingscore beperkingscore
    ) throws URISyntaxException {
        log.debug("REST request to update Beperkingscore : {}, {}", id, beperkingscore);
        if (beperkingscore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperkingscore.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingscoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beperkingscore = beperkingscoreRepository.save(beperkingscore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperkingscore.getId().toString()))
            .body(beperkingscore);
    }

    /**
     * {@code PATCH  /beperkingscores/:id} : Partial updates given fields of an existing beperkingscore, field will ignore if it is null
     *
     * @param id the id of the beperkingscore to save.
     * @param beperkingscore the beperkingscore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperkingscore,
     * or with status {@code 400 (Bad Request)} if the beperkingscore is not valid,
     * or with status {@code 404 (Not Found)} if the beperkingscore is not found,
     * or with status {@code 500 (Internal Server Error)} if the beperkingscore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beperkingscore> partialUpdateBeperkingscore(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beperkingscore beperkingscore
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beperkingscore partially : {}, {}", id, beperkingscore);
        if (beperkingscore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperkingscore.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingscoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beperkingscore> result = beperkingscoreRepository
            .findById(beperkingscore.getId())
            .map(existingBeperkingscore -> {
                if (beperkingscore.getCommentaar() != null) {
                    existingBeperkingscore.setCommentaar(beperkingscore.getCommentaar());
                }
                if (beperkingscore.getScore() != null) {
                    existingBeperkingscore.setScore(beperkingscore.getScore());
                }
                if (beperkingscore.getWet() != null) {
                    existingBeperkingscore.setWet(beperkingscore.getWet());
                }

                return existingBeperkingscore;
            })
            .map(beperkingscoreRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperkingscore.getId().toString())
        );
    }

    /**
     * {@code GET  /beperkingscores} : get all the beperkingscores.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beperkingscores in body.
     */
    @GetMapping("")
    public List<Beperkingscore> getAllBeperkingscores() {
        log.debug("REST request to get all Beperkingscores");
        return beperkingscoreRepository.findAll();
    }

    /**
     * {@code GET  /beperkingscores/:id} : get the "id" beperkingscore.
     *
     * @param id the id of the beperkingscore to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beperkingscore, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beperkingscore> getBeperkingscore(@PathVariable("id") Long id) {
        log.debug("REST request to get Beperkingscore : {}", id);
        Optional<Beperkingscore> beperkingscore = beperkingscoreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beperkingscore);
    }

    /**
     * {@code DELETE  /beperkingscores/:id} : delete the "id" beperkingscore.
     *
     * @param id the id of the beperkingscore to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeperkingscore(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beperkingscore : {}", id);
        beperkingscoreRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
