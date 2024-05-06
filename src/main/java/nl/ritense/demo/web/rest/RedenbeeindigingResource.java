package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Redenbeeindiging;
import nl.ritense.demo.repository.RedenbeeindigingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Redenbeeindiging}.
 */
@RestController
@RequestMapping("/api/redenbeeindigings")
@Transactional
public class RedenbeeindigingResource {

    private final Logger log = LoggerFactory.getLogger(RedenbeeindigingResource.class);

    private static final String ENTITY_NAME = "redenbeeindiging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RedenbeeindigingRepository redenbeeindigingRepository;

    public RedenbeeindigingResource(RedenbeeindigingRepository redenbeeindigingRepository) {
        this.redenbeeindigingRepository = redenbeeindigingRepository;
    }

    /**
     * {@code POST  /redenbeeindigings} : Create a new redenbeeindiging.
     *
     * @param redenbeeindiging the redenbeeindiging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new redenbeeindiging, or with status {@code 400 (Bad Request)} if the redenbeeindiging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Redenbeeindiging> createRedenbeeindiging(@RequestBody Redenbeeindiging redenbeeindiging)
        throws URISyntaxException {
        log.debug("REST request to save Redenbeeindiging : {}", redenbeeindiging);
        if (redenbeeindiging.getId() != null) {
            throw new BadRequestAlertException("A new redenbeeindiging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        redenbeeindiging = redenbeeindigingRepository.save(redenbeeindiging);
        return ResponseEntity.created(new URI("/api/redenbeeindigings/" + redenbeeindiging.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, redenbeeindiging.getId().toString()))
            .body(redenbeeindiging);
    }

    /**
     * {@code PUT  /redenbeeindigings/:id} : Updates an existing redenbeeindiging.
     *
     * @param id the id of the redenbeeindiging to save.
     * @param redenbeeindiging the redenbeeindiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redenbeeindiging,
     * or with status {@code 400 (Bad Request)} if the redenbeeindiging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the redenbeeindiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Redenbeeindiging> updateRedenbeeindiging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Redenbeeindiging redenbeeindiging
    ) throws URISyntaxException {
        log.debug("REST request to update Redenbeeindiging : {}, {}", id, redenbeeindiging);
        if (redenbeeindiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redenbeeindiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redenbeeindigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        redenbeeindiging = redenbeeindigingRepository.save(redenbeeindiging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, redenbeeindiging.getId().toString()))
            .body(redenbeeindiging);
    }

    /**
     * {@code PATCH  /redenbeeindigings/:id} : Partial updates given fields of an existing redenbeeindiging, field will ignore if it is null
     *
     * @param id the id of the redenbeeindiging to save.
     * @param redenbeeindiging the redenbeeindiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redenbeeindiging,
     * or with status {@code 400 (Bad Request)} if the redenbeeindiging is not valid,
     * or with status {@code 404 (Not Found)} if the redenbeeindiging is not found,
     * or with status {@code 500 (Internal Server Error)} if the redenbeeindiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Redenbeeindiging> partialUpdateRedenbeeindiging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Redenbeeindiging redenbeeindiging
    ) throws URISyntaxException {
        log.debug("REST request to partial update Redenbeeindiging partially : {}, {}", id, redenbeeindiging);
        if (redenbeeindiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redenbeeindiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redenbeeindigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Redenbeeindiging> result = redenbeeindigingRepository
            .findById(redenbeeindiging.getId())
            .map(existingRedenbeeindiging -> {
                if (redenbeeindiging.getNaam() != null) {
                    existingRedenbeeindiging.setNaam(redenbeeindiging.getNaam());
                }
                if (redenbeeindiging.getOmschrijving() != null) {
                    existingRedenbeeindiging.setOmschrijving(redenbeeindiging.getOmschrijving());
                }

                return existingRedenbeeindiging;
            })
            .map(redenbeeindigingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, redenbeeindiging.getId().toString())
        );
    }

    /**
     * {@code GET  /redenbeeindigings} : get all the redenbeeindigings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of redenbeeindigings in body.
     */
    @GetMapping("")
    public List<Redenbeeindiging> getAllRedenbeeindigings() {
        log.debug("REST request to get all Redenbeeindigings");
        return redenbeeindigingRepository.findAll();
    }

    /**
     * {@code GET  /redenbeeindigings/:id} : get the "id" redenbeeindiging.
     *
     * @param id the id of the redenbeeindiging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the redenbeeindiging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Redenbeeindiging> getRedenbeeindiging(@PathVariable("id") Long id) {
        log.debug("REST request to get Redenbeeindiging : {}", id);
        Optional<Redenbeeindiging> redenbeeindiging = redenbeeindigingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(redenbeeindiging);
    }

    /**
     * {@code DELETE  /redenbeeindigings/:id} : delete the "id" redenbeeindiging.
     *
     * @param id the id of the redenbeeindiging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRedenbeeindiging(@PathVariable("id") Long id) {
        log.debug("REST request to delete Redenbeeindiging : {}", id);
        redenbeeindigingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
