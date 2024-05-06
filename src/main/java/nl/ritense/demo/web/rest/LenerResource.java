package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Lener;
import nl.ritense.demo.repository.LenerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Lener}.
 */
@RestController
@RequestMapping("/api/leners")
@Transactional
public class LenerResource {

    private final Logger log = LoggerFactory.getLogger(LenerResource.class);

    private static final String ENTITY_NAME = "lener";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LenerRepository lenerRepository;

    public LenerResource(LenerRepository lenerRepository) {
        this.lenerRepository = lenerRepository;
    }

    /**
     * {@code POST  /leners} : Create a new lener.
     *
     * @param lener the lener to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lener, or with status {@code 400 (Bad Request)} if the lener has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Lener> createLener(@Valid @RequestBody Lener lener) throws URISyntaxException {
        log.debug("REST request to save Lener : {}", lener);
        if (lener.getId() != null) {
            throw new BadRequestAlertException("A new lener cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lener = lenerRepository.save(lener);
        return ResponseEntity.created(new URI("/api/leners/" + lener.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lener.getId().toString()))
            .body(lener);
    }

    /**
     * {@code PUT  /leners/:id} : Updates an existing lener.
     *
     * @param id the id of the lener to save.
     * @param lener the lener to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lener,
     * or with status {@code 400 (Bad Request)} if the lener is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lener couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Lener> updateLener(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Lener lener)
        throws URISyntaxException {
        log.debug("REST request to update Lener : {}, {}", id, lener);
        if (lener.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lener.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lenerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lener = lenerRepository.save(lener);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lener.getId().toString()))
            .body(lener);
    }

    /**
     * {@code PATCH  /leners/:id} : Partial updates given fields of an existing lener, field will ignore if it is null
     *
     * @param id the id of the lener to save.
     * @param lener the lener to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lener,
     * or with status {@code 400 (Bad Request)} if the lener is not valid,
     * or with status {@code 404 (Not Found)} if the lener is not found,
     * or with status {@code 500 (Internal Server Error)} if the lener couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Lener> partialUpdateLener(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Lener lener
    ) throws URISyntaxException {
        log.debug("REST request to partial update Lener partially : {}, {}", id, lener);
        if (lener.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lener.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lenerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Lener> result = lenerRepository
            .findById(lener.getId())
            .map(existingLener -> {
                if (lener.getOpmerkingen() != null) {
                    existingLener.setOpmerkingen(lener.getOpmerkingen());
                }

                return existingLener;
            })
            .map(lenerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lener.getId().toString())
        );
    }

    /**
     * {@code GET  /leners} : get all the leners.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leners in body.
     */
    @GetMapping("")
    public List<Lener> getAllLeners(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Leners");
        if (eagerload) {
            return lenerRepository.findAllWithEagerRelationships();
        } else {
            return lenerRepository.findAll();
        }
    }

    /**
     * {@code GET  /leners/:id} : get the "id" lener.
     *
     * @param id the id of the lener to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lener, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lener> getLener(@PathVariable("id") Long id) {
        log.debug("REST request to get Lener : {}", id);
        Optional<Lener> lener = lenerRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(lener);
    }

    /**
     * {@code DELETE  /leners/:id} : delete the "id" lener.
     *
     * @param id the id of the lener to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLener(@PathVariable("id") Long id) {
        log.debug("REST request to delete Lener : {}", id);
        lenerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
