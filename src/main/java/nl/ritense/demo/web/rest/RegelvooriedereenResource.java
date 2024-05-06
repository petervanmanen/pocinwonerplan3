package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Regelvooriedereen;
import nl.ritense.demo.repository.RegelvooriedereenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Regelvooriedereen}.
 */
@RestController
@RequestMapping("/api/regelvooriedereens")
@Transactional
public class RegelvooriedereenResource {

    private final Logger log = LoggerFactory.getLogger(RegelvooriedereenResource.class);

    private static final String ENTITY_NAME = "regelvooriedereen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegelvooriedereenRepository regelvooriedereenRepository;

    public RegelvooriedereenResource(RegelvooriedereenRepository regelvooriedereenRepository) {
        this.regelvooriedereenRepository = regelvooriedereenRepository;
    }

    /**
     * {@code POST  /regelvooriedereens} : Create a new regelvooriedereen.
     *
     * @param regelvooriedereen the regelvooriedereen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regelvooriedereen, or with status {@code 400 (Bad Request)} if the regelvooriedereen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Regelvooriedereen> createRegelvooriedereen(@RequestBody Regelvooriedereen regelvooriedereen)
        throws URISyntaxException {
        log.debug("REST request to save Regelvooriedereen : {}", regelvooriedereen);
        if (regelvooriedereen.getId() != null) {
            throw new BadRequestAlertException("A new regelvooriedereen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        regelvooriedereen = regelvooriedereenRepository.save(regelvooriedereen);
        return ResponseEntity.created(new URI("/api/regelvooriedereens/" + regelvooriedereen.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, regelvooriedereen.getId().toString()))
            .body(regelvooriedereen);
    }

    /**
     * {@code PUT  /regelvooriedereens/:id} : Updates an existing regelvooriedereen.
     *
     * @param id the id of the regelvooriedereen to save.
     * @param regelvooriedereen the regelvooriedereen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regelvooriedereen,
     * or with status {@code 400 (Bad Request)} if the regelvooriedereen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regelvooriedereen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Regelvooriedereen> updateRegelvooriedereen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Regelvooriedereen regelvooriedereen
    ) throws URISyntaxException {
        log.debug("REST request to update Regelvooriedereen : {}, {}", id, regelvooriedereen);
        if (regelvooriedereen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regelvooriedereen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regelvooriedereenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        regelvooriedereen = regelvooriedereenRepository.save(regelvooriedereen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regelvooriedereen.getId().toString()))
            .body(regelvooriedereen);
    }

    /**
     * {@code PATCH  /regelvooriedereens/:id} : Partial updates given fields of an existing regelvooriedereen, field will ignore if it is null
     *
     * @param id the id of the regelvooriedereen to save.
     * @param regelvooriedereen the regelvooriedereen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regelvooriedereen,
     * or with status {@code 400 (Bad Request)} if the regelvooriedereen is not valid,
     * or with status {@code 404 (Not Found)} if the regelvooriedereen is not found,
     * or with status {@code 500 (Internal Server Error)} if the regelvooriedereen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Regelvooriedereen> partialUpdateRegelvooriedereen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Regelvooriedereen regelvooriedereen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Regelvooriedereen partially : {}, {}", id, regelvooriedereen);
        if (regelvooriedereen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regelvooriedereen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regelvooriedereenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Regelvooriedereen> result = regelvooriedereenRepository
            .findById(regelvooriedereen.getId())
            .map(existingRegelvooriedereen -> {
                if (regelvooriedereen.getActiviteitregelkwalificatie() != null) {
                    existingRegelvooriedereen.setActiviteitregelkwalificatie(regelvooriedereen.getActiviteitregelkwalificatie());
                }

                return existingRegelvooriedereen;
            })
            .map(regelvooriedereenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regelvooriedereen.getId().toString())
        );
    }

    /**
     * {@code GET  /regelvooriedereens} : get all the regelvooriedereens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regelvooriedereens in body.
     */
    @GetMapping("")
    public List<Regelvooriedereen> getAllRegelvooriedereens() {
        log.debug("REST request to get all Regelvooriedereens");
        return regelvooriedereenRepository.findAll();
    }

    /**
     * {@code GET  /regelvooriedereens/:id} : get the "id" regelvooriedereen.
     *
     * @param id the id of the regelvooriedereen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regelvooriedereen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Regelvooriedereen> getRegelvooriedereen(@PathVariable("id") Long id) {
        log.debug("REST request to get Regelvooriedereen : {}", id);
        Optional<Regelvooriedereen> regelvooriedereen = regelvooriedereenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regelvooriedereen);
    }

    /**
     * {@code DELETE  /regelvooriedereens/:id} : delete the "id" regelvooriedereen.
     *
     * @param id the id of the regelvooriedereen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegelvooriedereen(@PathVariable("id") Long id) {
        log.debug("REST request to delete Regelvooriedereen : {}", id);
        regelvooriedereenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
