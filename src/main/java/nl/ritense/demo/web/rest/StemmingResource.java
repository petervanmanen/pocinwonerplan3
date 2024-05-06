package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Stemming;
import nl.ritense.demo.repository.StemmingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Stemming}.
 */
@RestController
@RequestMapping("/api/stemmings")
@Transactional
public class StemmingResource {

    private final Logger log = LoggerFactory.getLogger(StemmingResource.class);

    private static final String ENTITY_NAME = "stemming";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StemmingRepository stemmingRepository;

    public StemmingResource(StemmingRepository stemmingRepository) {
        this.stemmingRepository = stemmingRepository;
    }

    /**
     * {@code POST  /stemmings} : Create a new stemming.
     *
     * @param stemming the stemming to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stemming, or with status {@code 400 (Bad Request)} if the stemming has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Stemming> createStemming(@RequestBody Stemming stemming) throws URISyntaxException {
        log.debug("REST request to save Stemming : {}", stemming);
        if (stemming.getId() != null) {
            throw new BadRequestAlertException("A new stemming cannot already have an ID", ENTITY_NAME, "idexists");
        }
        stemming = stemmingRepository.save(stemming);
        return ResponseEntity.created(new URI("/api/stemmings/" + stemming.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, stemming.getId().toString()))
            .body(stemming);
    }

    /**
     * {@code PUT  /stemmings/:id} : Updates an existing stemming.
     *
     * @param id the id of the stemming to save.
     * @param stemming the stemming to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stemming,
     * or with status {@code 400 (Bad Request)} if the stemming is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stemming couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stemming> updateStemming(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Stemming stemming
    ) throws URISyntaxException {
        log.debug("REST request to update Stemming : {}, {}", id, stemming);
        if (stemming.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stemming.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stemmingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        stemming = stemmingRepository.save(stemming);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stemming.getId().toString()))
            .body(stemming);
    }

    /**
     * {@code PATCH  /stemmings/:id} : Partial updates given fields of an existing stemming, field will ignore if it is null
     *
     * @param id the id of the stemming to save.
     * @param stemming the stemming to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stemming,
     * or with status {@code 400 (Bad Request)} if the stemming is not valid,
     * or with status {@code 404 (Not Found)} if the stemming is not found,
     * or with status {@code 500 (Internal Server Error)} if the stemming couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Stemming> partialUpdateStemming(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Stemming stemming
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stemming partially : {}, {}", id, stemming);
        if (stemming.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stemming.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stemmingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Stemming> result = stemmingRepository
            .findById(stemming.getId())
            .map(existingStemming -> {
                if (stemming.getResultaat() != null) {
                    existingStemming.setResultaat(stemming.getResultaat());
                }
                if (stemming.getStemmingstype() != null) {
                    existingStemming.setStemmingstype(stemming.getStemmingstype());
                }

                return existingStemming;
            })
            .map(stemmingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stemming.getId().toString())
        );
    }

    /**
     * {@code GET  /stemmings} : get all the stemmings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stemmings in body.
     */
    @GetMapping("")
    public List<Stemming> getAllStemmings() {
        log.debug("REST request to get all Stemmings");
        return stemmingRepository.findAll();
    }

    /**
     * {@code GET  /stemmings/:id} : get the "id" stemming.
     *
     * @param id the id of the stemming to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stemming, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stemming> getStemming(@PathVariable("id") Long id) {
        log.debug("REST request to get Stemming : {}", id);
        Optional<Stemming> stemming = stemmingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stemming);
    }

    /**
     * {@code DELETE  /stemmings/:id} : delete the "id" stemming.
     *
     * @param id the id of the stemming to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStemming(@PathVariable("id") Long id) {
        log.debug("REST request to delete Stemming : {}", id);
        stemmingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
