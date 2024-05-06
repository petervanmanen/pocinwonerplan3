package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Drainageput;
import nl.ritense.demo.repository.DrainageputRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Drainageput}.
 */
@RestController
@RequestMapping("/api/drainageputs")
@Transactional
public class DrainageputResource {

    private final Logger log = LoggerFactory.getLogger(DrainageputResource.class);

    private static final String ENTITY_NAME = "drainageput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrainageputRepository drainageputRepository;

    public DrainageputResource(DrainageputRepository drainageputRepository) {
        this.drainageputRepository = drainageputRepository;
    }

    /**
     * {@code POST  /drainageputs} : Create a new drainageput.
     *
     * @param drainageput the drainageput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drainageput, or with status {@code 400 (Bad Request)} if the drainageput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Drainageput> createDrainageput(@RequestBody Drainageput drainageput) throws URISyntaxException {
        log.debug("REST request to save Drainageput : {}", drainageput);
        if (drainageput.getId() != null) {
            throw new BadRequestAlertException("A new drainageput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        drainageput = drainageputRepository.save(drainageput);
        return ResponseEntity.created(new URI("/api/drainageputs/" + drainageput.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, drainageput.getId().toString()))
            .body(drainageput);
    }

    /**
     * {@code PUT  /drainageputs/:id} : Updates an existing drainageput.
     *
     * @param id the id of the drainageput to save.
     * @param drainageput the drainageput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drainageput,
     * or with status {@code 400 (Bad Request)} if the drainageput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drainageput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Drainageput> updateDrainageput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Drainageput drainageput
    ) throws URISyntaxException {
        log.debug("REST request to update Drainageput : {}, {}", id, drainageput);
        if (drainageput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, drainageput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drainageputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        drainageput = drainageputRepository.save(drainageput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, drainageput.getId().toString()))
            .body(drainageput);
    }

    /**
     * {@code PATCH  /drainageputs/:id} : Partial updates given fields of an existing drainageput, field will ignore if it is null
     *
     * @param id the id of the drainageput to save.
     * @param drainageput the drainageput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drainageput,
     * or with status {@code 400 (Bad Request)} if the drainageput is not valid,
     * or with status {@code 404 (Not Found)} if the drainageput is not found,
     * or with status {@code 500 (Internal Server Error)} if the drainageput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Drainageput> partialUpdateDrainageput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Drainageput drainageput
    ) throws URISyntaxException {
        log.debug("REST request to partial update Drainageput partially : {}, {}", id, drainageput);
        if (drainageput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, drainageput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drainageputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Drainageput> result = drainageputRepository
            .findById(drainageput.getId())
            .map(existingDrainageput -> {
                if (drainageput.getRisicogebied() != null) {
                    existingDrainageput.setRisicogebied(drainageput.getRisicogebied());
                }
                if (drainageput.getType() != null) {
                    existingDrainageput.setType(drainageput.getType());
                }

                return existingDrainageput;
            })
            .map(drainageputRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, drainageput.getId().toString())
        );
    }

    /**
     * {@code GET  /drainageputs} : get all the drainageputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drainageputs in body.
     */
    @GetMapping("")
    public List<Drainageput> getAllDrainageputs() {
        log.debug("REST request to get all Drainageputs");
        return drainageputRepository.findAll();
    }

    /**
     * {@code GET  /drainageputs/:id} : get the "id" drainageput.
     *
     * @param id the id of the drainageput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drainageput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Drainageput> getDrainageput(@PathVariable("id") Long id) {
        log.debug("REST request to get Drainageput : {}", id);
        Optional<Drainageput> drainageput = drainageputRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(drainageput);
    }

    /**
     * {@code DELETE  /drainageputs/:id} : delete the "id" drainageput.
     *
     * @param id the id of the drainageput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrainageput(@PathVariable("id") Long id) {
        log.debug("REST request to delete Drainageput : {}", id);
        drainageputRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
