package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verlof;
import nl.ritense.demo.repository.VerlofRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verlof}.
 */
@RestController
@RequestMapping("/api/verlofs")
@Transactional
public class VerlofResource {

    private final Logger log = LoggerFactory.getLogger(VerlofResource.class);

    private static final String ENTITY_NAME = "verlof";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerlofRepository verlofRepository;

    public VerlofResource(VerlofRepository verlofRepository) {
        this.verlofRepository = verlofRepository;
    }

    /**
     * {@code POST  /verlofs} : Create a new verlof.
     *
     * @param verlof the verlof to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verlof, or with status {@code 400 (Bad Request)} if the verlof has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verlof> createVerlof(@RequestBody Verlof verlof) throws URISyntaxException {
        log.debug("REST request to save Verlof : {}", verlof);
        if (verlof.getId() != null) {
            throw new BadRequestAlertException("A new verlof cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verlof = verlofRepository.save(verlof);
        return ResponseEntity.created(new URI("/api/verlofs/" + verlof.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verlof.getId().toString()))
            .body(verlof);
    }

    /**
     * {@code PUT  /verlofs/:id} : Updates an existing verlof.
     *
     * @param id the id of the verlof to save.
     * @param verlof the verlof to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verlof,
     * or with status {@code 400 (Bad Request)} if the verlof is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verlof couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verlof> updateVerlof(@PathVariable(value = "id", required = false) final Long id, @RequestBody Verlof verlof)
        throws URISyntaxException {
        log.debug("REST request to update Verlof : {}, {}", id, verlof);
        if (verlof.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verlof.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verlofRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verlof = verlofRepository.save(verlof);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verlof.getId().toString()))
            .body(verlof);
    }

    /**
     * {@code PATCH  /verlofs/:id} : Partial updates given fields of an existing verlof, field will ignore if it is null
     *
     * @param id the id of the verlof to save.
     * @param verlof the verlof to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verlof,
     * or with status {@code 400 (Bad Request)} if the verlof is not valid,
     * or with status {@code 404 (Not Found)} if the verlof is not found,
     * or with status {@code 500 (Internal Server Error)} if the verlof couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verlof> partialUpdateVerlof(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verlof verlof
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verlof partially : {}, {}", id, verlof);
        if (verlof.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verlof.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verlofRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verlof> result = verlofRepository
            .findById(verlof.getId())
            .map(existingVerlof -> {
                if (verlof.getDatumaanvraag() != null) {
                    existingVerlof.setDatumaanvraag(verlof.getDatumaanvraag());
                }
                if (verlof.getDatumtijdeinde() != null) {
                    existingVerlof.setDatumtijdeinde(verlof.getDatumtijdeinde());
                }
                if (verlof.getDatumtijdstart() != null) {
                    existingVerlof.setDatumtijdstart(verlof.getDatumtijdstart());
                }
                if (verlof.getDatumtoekenning() != null) {
                    existingVerlof.setDatumtoekenning(verlof.getDatumtoekenning());
                }
                if (verlof.getGoedgekeurd() != null) {
                    existingVerlof.setGoedgekeurd(verlof.getGoedgekeurd());
                }

                return existingVerlof;
            })
            .map(verlofRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verlof.getId().toString())
        );
    }

    /**
     * {@code GET  /verlofs} : get all the verlofs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verlofs in body.
     */
    @GetMapping("")
    public List<Verlof> getAllVerlofs() {
        log.debug("REST request to get all Verlofs");
        return verlofRepository.findAll();
    }

    /**
     * {@code GET  /verlofs/:id} : get the "id" verlof.
     *
     * @param id the id of the verlof to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verlof, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verlof> getVerlof(@PathVariable("id") Long id) {
        log.debug("REST request to get Verlof : {}", id);
        Optional<Verlof> verlof = verlofRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verlof);
    }

    /**
     * {@code DELETE  /verlofs/:id} : delete the "id" verlof.
     *
     * @param id the id of the verlof to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerlof(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verlof : {}", id);
        verlofRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
