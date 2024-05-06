package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Plank;
import nl.ritense.demo.repository.PlankRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Plank}.
 */
@RestController
@RequestMapping("/api/planks")
@Transactional
public class PlankResource {

    private final Logger log = LoggerFactory.getLogger(PlankResource.class);

    private static final String ENTITY_NAME = "plank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlankRepository plankRepository;

    public PlankResource(PlankRepository plankRepository) {
        this.plankRepository = plankRepository;
    }

    /**
     * {@code POST  /planks} : Create a new plank.
     *
     * @param plank the plank to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plank, or with status {@code 400 (Bad Request)} if the plank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Plank> createPlank(@Valid @RequestBody Plank plank) throws URISyntaxException {
        log.debug("REST request to save Plank : {}", plank);
        if (plank.getId() != null) {
            throw new BadRequestAlertException("A new plank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        plank = plankRepository.save(plank);
        return ResponseEntity.created(new URI("/api/planks/" + plank.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, plank.getId().toString()))
            .body(plank);
    }

    /**
     * {@code PUT  /planks/:id} : Updates an existing plank.
     *
     * @param id the id of the plank to save.
     * @param plank the plank to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plank,
     * or with status {@code 400 (Bad Request)} if the plank is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plank couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Plank> updatePlank(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Plank plank)
        throws URISyntaxException {
        log.debug("REST request to update Plank : {}, {}", id, plank);
        if (plank.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plank.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        plank = plankRepository.save(plank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plank.getId().toString()))
            .body(plank);
    }

    /**
     * {@code PATCH  /planks/:id} : Partial updates given fields of an existing plank, field will ignore if it is null
     *
     * @param id the id of the plank to save.
     * @param plank the plank to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plank,
     * or with status {@code 400 (Bad Request)} if the plank is not valid,
     * or with status {@code 404 (Not Found)} if the plank is not found,
     * or with status {@code 500 (Internal Server Error)} if the plank couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Plank> partialUpdatePlank(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Plank plank
    ) throws URISyntaxException {
        log.debug("REST request to partial update Plank partially : {}, {}", id, plank);
        if (plank.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plank.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plankRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Plank> result = plankRepository
            .findById(plank.getId())
            .map(existingPlank -> {
                if (plank.getPlanknummer() != null) {
                    existingPlank.setPlanknummer(plank.getPlanknummer());
                }

                return existingPlank;
            })
            .map(plankRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plank.getId().toString())
        );
    }

    /**
     * {@code GET  /planks} : get all the planks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planks in body.
     */
    @GetMapping("")
    public List<Plank> getAllPlanks() {
        log.debug("REST request to get all Planks");
        return plankRepository.findAll();
    }

    /**
     * {@code GET  /planks/:id} : get the "id" plank.
     *
     * @param id the id of the plank to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plank, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Plank> getPlank(@PathVariable("id") Long id) {
        log.debug("REST request to get Plank : {}", id);
        Optional<Plank> plank = plankRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plank);
    }

    /**
     * {@code DELETE  /planks/:id} : delete the "id" plank.
     *
     * @param id the id of the plank to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlank(@PathVariable("id") Long id) {
        log.debug("REST request to delete Plank : {}", id);
        plankRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
