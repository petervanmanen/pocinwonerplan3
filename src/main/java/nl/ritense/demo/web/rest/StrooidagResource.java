package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Strooidag;
import nl.ritense.demo.repository.StrooidagRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Strooidag}.
 */
@RestController
@RequestMapping("/api/strooidags")
@Transactional
public class StrooidagResource {

    private final Logger log = LoggerFactory.getLogger(StrooidagResource.class);

    private static final String ENTITY_NAME = "strooidag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrooidagRepository strooidagRepository;

    public StrooidagResource(StrooidagRepository strooidagRepository) {
        this.strooidagRepository = strooidagRepository;
    }

    /**
     * {@code POST  /strooidags} : Create a new strooidag.
     *
     * @param strooidag the strooidag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strooidag, or with status {@code 400 (Bad Request)} if the strooidag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Strooidag> createStrooidag(@RequestBody Strooidag strooidag) throws URISyntaxException {
        log.debug("REST request to save Strooidag : {}", strooidag);
        if (strooidag.getId() != null) {
            throw new BadRequestAlertException("A new strooidag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        strooidag = strooidagRepository.save(strooidag);
        return ResponseEntity.created(new URI("/api/strooidags/" + strooidag.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, strooidag.getId().toString()))
            .body(strooidag);
    }

    /**
     * {@code PUT  /strooidags/:id} : Updates an existing strooidag.
     *
     * @param id the id of the strooidag to save.
     * @param strooidag the strooidag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strooidag,
     * or with status {@code 400 (Bad Request)} if the strooidag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strooidag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Strooidag> updateStrooidag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Strooidag strooidag
    ) throws URISyntaxException {
        log.debug("REST request to update Strooidag : {}, {}", id, strooidag);
        if (strooidag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strooidag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strooidagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        strooidag = strooidagRepository.save(strooidag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, strooidag.getId().toString()))
            .body(strooidag);
    }

    /**
     * {@code PATCH  /strooidags/:id} : Partial updates given fields of an existing strooidag, field will ignore if it is null
     *
     * @param id the id of the strooidag to save.
     * @param strooidag the strooidag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strooidag,
     * or with status {@code 400 (Bad Request)} if the strooidag is not valid,
     * or with status {@code 404 (Not Found)} if the strooidag is not found,
     * or with status {@code 500 (Internal Server Error)} if the strooidag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Strooidag> partialUpdateStrooidag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Strooidag strooidag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Strooidag partially : {}, {}", id, strooidag);
        if (strooidag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strooidag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strooidagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Strooidag> result = strooidagRepository
            .findById(strooidag.getId())
            .map(existingStrooidag -> {
                if (strooidag.getDatum() != null) {
                    existingStrooidag.setDatum(strooidag.getDatum());
                }
                if (strooidag.getMaximumtemperatuur() != null) {
                    existingStrooidag.setMaximumtemperatuur(strooidag.getMaximumtemperatuur());
                }
                if (strooidag.getMinimumtemperatuur() != null) {
                    existingStrooidag.setMinimumtemperatuur(strooidag.getMinimumtemperatuur());
                }
                if (strooidag.getTijdmaximumtemperatuur() != null) {
                    existingStrooidag.setTijdmaximumtemperatuur(strooidag.getTijdmaximumtemperatuur());
                }
                if (strooidag.getTijdminimumtemperatuur() != null) {
                    existingStrooidag.setTijdminimumtemperatuur(strooidag.getTijdminimumtemperatuur());
                }

                return existingStrooidag;
            })
            .map(strooidagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, strooidag.getId().toString())
        );
    }

    /**
     * {@code GET  /strooidags} : get all the strooidags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strooidags in body.
     */
    @GetMapping("")
    public List<Strooidag> getAllStrooidags() {
        log.debug("REST request to get all Strooidags");
        return strooidagRepository.findAll();
    }

    /**
     * {@code GET  /strooidags/:id} : get the "id" strooidag.
     *
     * @param id the id of the strooidag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strooidag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Strooidag> getStrooidag(@PathVariable("id") Long id) {
        log.debug("REST request to get Strooidag : {}", id);
        Optional<Strooidag> strooidag = strooidagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(strooidag);
    }

    /**
     * {@code DELETE  /strooidags/:id} : delete the "id" strooidag.
     *
     * @param id the id of the strooidag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrooidag(@PathVariable("id") Long id) {
        log.debug("REST request to delete Strooidag : {}", id);
        strooidagRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
