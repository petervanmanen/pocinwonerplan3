package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Rondleiding;
import nl.ritense.demo.repository.RondleidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Rondleiding}.
 */
@RestController
@RequestMapping("/api/rondleidings")
@Transactional
public class RondleidingResource {

    private final Logger log = LoggerFactory.getLogger(RondleidingResource.class);

    private static final String ENTITY_NAME = "rondleiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RondleidingRepository rondleidingRepository;

    public RondleidingResource(RondleidingRepository rondleidingRepository) {
        this.rondleidingRepository = rondleidingRepository;
    }

    /**
     * {@code POST  /rondleidings} : Create a new rondleiding.
     *
     * @param rondleiding the rondleiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rondleiding, or with status {@code 400 (Bad Request)} if the rondleiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Rondleiding> createRondleiding(@RequestBody Rondleiding rondleiding) throws URISyntaxException {
        log.debug("REST request to save Rondleiding : {}", rondleiding);
        if (rondleiding.getId() != null) {
            throw new BadRequestAlertException("A new rondleiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rondleiding = rondleidingRepository.save(rondleiding);
        return ResponseEntity.created(new URI("/api/rondleidings/" + rondleiding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rondleiding.getId().toString()))
            .body(rondleiding);
    }

    /**
     * {@code PUT  /rondleidings/:id} : Updates an existing rondleiding.
     *
     * @param id the id of the rondleiding to save.
     * @param rondleiding the rondleiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rondleiding,
     * or with status {@code 400 (Bad Request)} if the rondleiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rondleiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rondleiding> updateRondleiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rondleiding rondleiding
    ) throws URISyntaxException {
        log.debug("REST request to update Rondleiding : {}, {}", id, rondleiding);
        if (rondleiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rondleiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rondleidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rondleiding = rondleidingRepository.save(rondleiding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rondleiding.getId().toString()))
            .body(rondleiding);
    }

    /**
     * {@code PATCH  /rondleidings/:id} : Partial updates given fields of an existing rondleiding, field will ignore if it is null
     *
     * @param id the id of the rondleiding to save.
     * @param rondleiding the rondleiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rondleiding,
     * or with status {@code 400 (Bad Request)} if the rondleiding is not valid,
     * or with status {@code 404 (Not Found)} if the rondleiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the rondleiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rondleiding> partialUpdateRondleiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rondleiding rondleiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rondleiding partially : {}, {}", id, rondleiding);
        if (rondleiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rondleiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rondleidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rondleiding> result = rondleidingRepository
            .findById(rondleiding.getId())
            .map(existingRondleiding -> {
                if (rondleiding.getEindtijd() != null) {
                    existingRondleiding.setEindtijd(rondleiding.getEindtijd());
                }
                if (rondleiding.getNaam() != null) {
                    existingRondleiding.setNaam(rondleiding.getNaam());
                }
                if (rondleiding.getOmschrijving() != null) {
                    existingRondleiding.setOmschrijving(rondleiding.getOmschrijving());
                }
                if (rondleiding.getStarttijd() != null) {
                    existingRondleiding.setStarttijd(rondleiding.getStarttijd());
                }

                return existingRondleiding;
            })
            .map(rondleidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rondleiding.getId().toString())
        );
    }

    /**
     * {@code GET  /rondleidings} : get all the rondleidings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rondleidings in body.
     */
    @GetMapping("")
    public List<Rondleiding> getAllRondleidings() {
        log.debug("REST request to get all Rondleidings");
        return rondleidingRepository.findAll();
    }

    /**
     * {@code GET  /rondleidings/:id} : get the "id" rondleiding.
     *
     * @param id the id of the rondleiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rondleiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rondleiding> getRondleiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Rondleiding : {}", id);
        Optional<Rondleiding> rondleiding = rondleidingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rondleiding);
    }

    /**
     * {@code DELETE  /rondleidings/:id} : delete the "id" rondleiding.
     *
     * @param id the id of the rondleiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRondleiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rondleiding : {}", id);
        rondleidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
