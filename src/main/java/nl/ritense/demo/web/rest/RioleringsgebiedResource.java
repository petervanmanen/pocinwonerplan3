package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Rioleringsgebied;
import nl.ritense.demo.repository.RioleringsgebiedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Rioleringsgebied}.
 */
@RestController
@RequestMapping("/api/rioleringsgebieds")
@Transactional
public class RioleringsgebiedResource {

    private final Logger log = LoggerFactory.getLogger(RioleringsgebiedResource.class);

    private static final String ENTITY_NAME = "rioleringsgebied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RioleringsgebiedRepository rioleringsgebiedRepository;

    public RioleringsgebiedResource(RioleringsgebiedRepository rioleringsgebiedRepository) {
        this.rioleringsgebiedRepository = rioleringsgebiedRepository;
    }

    /**
     * {@code POST  /rioleringsgebieds} : Create a new rioleringsgebied.
     *
     * @param rioleringsgebied the rioleringsgebied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rioleringsgebied, or with status {@code 400 (Bad Request)} if the rioleringsgebied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Rioleringsgebied> createRioleringsgebied(@RequestBody Rioleringsgebied rioleringsgebied)
        throws URISyntaxException {
        log.debug("REST request to save Rioleringsgebied : {}", rioleringsgebied);
        if (rioleringsgebied.getId() != null) {
            throw new BadRequestAlertException("A new rioleringsgebied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rioleringsgebied = rioleringsgebiedRepository.save(rioleringsgebied);
        return ResponseEntity.created(new URI("/api/rioleringsgebieds/" + rioleringsgebied.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rioleringsgebied.getId().toString()))
            .body(rioleringsgebied);
    }

    /**
     * {@code PUT  /rioleringsgebieds/:id} : Updates an existing rioleringsgebied.
     *
     * @param id the id of the rioleringsgebied to save.
     * @param rioleringsgebied the rioleringsgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rioleringsgebied,
     * or with status {@code 400 (Bad Request)} if the rioleringsgebied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rioleringsgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rioleringsgebied> updateRioleringsgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rioleringsgebied rioleringsgebied
    ) throws URISyntaxException {
        log.debug("REST request to update Rioleringsgebied : {}, {}", id, rioleringsgebied);
        if (rioleringsgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rioleringsgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rioleringsgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rioleringsgebied = rioleringsgebiedRepository.save(rioleringsgebied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rioleringsgebied.getId().toString()))
            .body(rioleringsgebied);
    }

    /**
     * {@code PATCH  /rioleringsgebieds/:id} : Partial updates given fields of an existing rioleringsgebied, field will ignore if it is null
     *
     * @param id the id of the rioleringsgebied to save.
     * @param rioleringsgebied the rioleringsgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rioleringsgebied,
     * or with status {@code 400 (Bad Request)} if the rioleringsgebied is not valid,
     * or with status {@code 404 (Not Found)} if the rioleringsgebied is not found,
     * or with status {@code 500 (Internal Server Error)} if the rioleringsgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rioleringsgebied> partialUpdateRioleringsgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rioleringsgebied rioleringsgebied
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rioleringsgebied partially : {}, {}", id, rioleringsgebied);
        if (rioleringsgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rioleringsgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rioleringsgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rioleringsgebied> result = rioleringsgebiedRepository
            .findById(rioleringsgebied.getId())
            .map(existingRioleringsgebied -> {
                if (rioleringsgebied.getRioleringsgebied() != null) {
                    existingRioleringsgebied.setRioleringsgebied(rioleringsgebied.getRioleringsgebied());
                }
                if (rioleringsgebied.getZuiveringsgebied() != null) {
                    existingRioleringsgebied.setZuiveringsgebied(rioleringsgebied.getZuiveringsgebied());
                }

                return existingRioleringsgebied;
            })
            .map(rioleringsgebiedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rioleringsgebied.getId().toString())
        );
    }

    /**
     * {@code GET  /rioleringsgebieds} : get all the rioleringsgebieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rioleringsgebieds in body.
     */
    @GetMapping("")
    public List<Rioleringsgebied> getAllRioleringsgebieds() {
        log.debug("REST request to get all Rioleringsgebieds");
        return rioleringsgebiedRepository.findAll();
    }

    /**
     * {@code GET  /rioleringsgebieds/:id} : get the "id" rioleringsgebied.
     *
     * @param id the id of the rioleringsgebied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rioleringsgebied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rioleringsgebied> getRioleringsgebied(@PathVariable("id") Long id) {
        log.debug("REST request to get Rioleringsgebied : {}", id);
        Optional<Rioleringsgebied> rioleringsgebied = rioleringsgebiedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rioleringsgebied);
    }

    /**
     * {@code DELETE  /rioleringsgebieds/:id} : delete the "id" rioleringsgebied.
     *
     * @param id the id of the rioleringsgebied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRioleringsgebied(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rioleringsgebied : {}", id);
        rioleringsgebiedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
