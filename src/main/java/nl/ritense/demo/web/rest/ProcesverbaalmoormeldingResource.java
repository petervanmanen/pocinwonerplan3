package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Procesverbaalmoormelding;
import nl.ritense.demo.repository.ProcesverbaalmoormeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Procesverbaalmoormelding}.
 */
@RestController
@RequestMapping("/api/procesverbaalmoormeldings")
@Transactional
public class ProcesverbaalmoormeldingResource {

    private final Logger log = LoggerFactory.getLogger(ProcesverbaalmoormeldingResource.class);

    private static final String ENTITY_NAME = "procesverbaalmoormelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcesverbaalmoormeldingRepository procesverbaalmoormeldingRepository;

    public ProcesverbaalmoormeldingResource(ProcesverbaalmoormeldingRepository procesverbaalmoormeldingRepository) {
        this.procesverbaalmoormeldingRepository = procesverbaalmoormeldingRepository;
    }

    /**
     * {@code POST  /procesverbaalmoormeldings} : Create a new procesverbaalmoormelding.
     *
     * @param procesverbaalmoormelding the procesverbaalmoormelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procesverbaalmoormelding, or with status {@code 400 (Bad Request)} if the procesverbaalmoormelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Procesverbaalmoormelding> createProcesverbaalmoormelding(
        @RequestBody Procesverbaalmoormelding procesverbaalmoormelding
    ) throws URISyntaxException {
        log.debug("REST request to save Procesverbaalmoormelding : {}", procesverbaalmoormelding);
        if (procesverbaalmoormelding.getId() != null) {
            throw new BadRequestAlertException("A new procesverbaalmoormelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        procesverbaalmoormelding = procesverbaalmoormeldingRepository.save(procesverbaalmoormelding);
        return ResponseEntity.created(new URI("/api/procesverbaalmoormeldings/" + procesverbaalmoormelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, procesverbaalmoormelding.getId().toString()))
            .body(procesverbaalmoormelding);
    }

    /**
     * {@code PUT  /procesverbaalmoormeldings/:id} : Updates an existing procesverbaalmoormelding.
     *
     * @param id the id of the procesverbaalmoormelding to save.
     * @param procesverbaalmoormelding the procesverbaalmoormelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procesverbaalmoormelding,
     * or with status {@code 400 (Bad Request)} if the procesverbaalmoormelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procesverbaalmoormelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Procesverbaalmoormelding> updateProcesverbaalmoormelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Procesverbaalmoormelding procesverbaalmoormelding
    ) throws URISyntaxException {
        log.debug("REST request to update Procesverbaalmoormelding : {}, {}", id, procesverbaalmoormelding);
        if (procesverbaalmoormelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procesverbaalmoormelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procesverbaalmoormeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        procesverbaalmoormelding = procesverbaalmoormeldingRepository.save(procesverbaalmoormelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procesverbaalmoormelding.getId().toString()))
            .body(procesverbaalmoormelding);
    }

    /**
     * {@code PATCH  /procesverbaalmoormeldings/:id} : Partial updates given fields of an existing procesverbaalmoormelding, field will ignore if it is null
     *
     * @param id the id of the procesverbaalmoormelding to save.
     * @param procesverbaalmoormelding the procesverbaalmoormelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procesverbaalmoormelding,
     * or with status {@code 400 (Bad Request)} if the procesverbaalmoormelding is not valid,
     * or with status {@code 404 (Not Found)} if the procesverbaalmoormelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the procesverbaalmoormelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Procesverbaalmoormelding> partialUpdateProcesverbaalmoormelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Procesverbaalmoormelding procesverbaalmoormelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Procesverbaalmoormelding partially : {}, {}", id, procesverbaalmoormelding);
        if (procesverbaalmoormelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procesverbaalmoormelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procesverbaalmoormeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Procesverbaalmoormelding> result = procesverbaalmoormeldingRepository
            .findById(procesverbaalmoormelding.getId())
            .map(existingProcesverbaalmoormelding -> {
                if (procesverbaalmoormelding.getDatum() != null) {
                    existingProcesverbaalmoormelding.setDatum(procesverbaalmoormelding.getDatum());
                }
                if (procesverbaalmoormelding.getGoedkeuring() != null) {
                    existingProcesverbaalmoormelding.setGoedkeuring(procesverbaalmoormelding.getGoedkeuring());
                }
                if (procesverbaalmoormelding.getOpmerkingen() != null) {
                    existingProcesverbaalmoormelding.setOpmerkingen(procesverbaalmoormelding.getOpmerkingen());
                }

                return existingProcesverbaalmoormelding;
            })
            .map(procesverbaalmoormeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procesverbaalmoormelding.getId().toString())
        );
    }

    /**
     * {@code GET  /procesverbaalmoormeldings} : get all the procesverbaalmoormeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procesverbaalmoormeldings in body.
     */
    @GetMapping("")
    public List<Procesverbaalmoormelding> getAllProcesverbaalmoormeldings() {
        log.debug("REST request to get all Procesverbaalmoormeldings");
        return procesverbaalmoormeldingRepository.findAll();
    }

    /**
     * {@code GET  /procesverbaalmoormeldings/:id} : get the "id" procesverbaalmoormelding.
     *
     * @param id the id of the procesverbaalmoormelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procesverbaalmoormelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Procesverbaalmoormelding> getProcesverbaalmoormelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Procesverbaalmoormelding : {}", id);
        Optional<Procesverbaalmoormelding> procesverbaalmoormelding = procesverbaalmoormeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(procesverbaalmoormelding);
    }

    /**
     * {@code DELETE  /procesverbaalmoormeldings/:id} : delete the "id" procesverbaalmoormelding.
     *
     * @param id the id of the procesverbaalmoormelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcesverbaalmoormelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Procesverbaalmoormelding : {}", id);
        procesverbaalmoormeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
