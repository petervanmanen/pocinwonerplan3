package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Afspraakstatus;
import nl.ritense.demo.repository.AfspraakstatusRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Afspraakstatus}.
 */
@RestController
@RequestMapping("/api/afspraakstatuses")
@Transactional
public class AfspraakstatusResource {

    private final Logger log = LoggerFactory.getLogger(AfspraakstatusResource.class);

    private static final String ENTITY_NAME = "afspraakstatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AfspraakstatusRepository afspraakstatusRepository;

    public AfspraakstatusResource(AfspraakstatusRepository afspraakstatusRepository) {
        this.afspraakstatusRepository = afspraakstatusRepository;
    }

    /**
     * {@code POST  /afspraakstatuses} : Create a new afspraakstatus.
     *
     * @param afspraakstatus the afspraakstatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new afspraakstatus, or with status {@code 400 (Bad Request)} if the afspraakstatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Afspraakstatus> createAfspraakstatus(@Valid @RequestBody Afspraakstatus afspraakstatus)
        throws URISyntaxException {
        log.debug("REST request to save Afspraakstatus : {}", afspraakstatus);
        if (afspraakstatus.getId() != null) {
            throw new BadRequestAlertException("A new afspraakstatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        afspraakstatus = afspraakstatusRepository.save(afspraakstatus);
        return ResponseEntity.created(new URI("/api/afspraakstatuses/" + afspraakstatus.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, afspraakstatus.getId().toString()))
            .body(afspraakstatus);
    }

    /**
     * {@code PUT  /afspraakstatuses/:id} : Updates an existing afspraakstatus.
     *
     * @param id the id of the afspraakstatus to save.
     * @param afspraakstatus the afspraakstatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afspraakstatus,
     * or with status {@code 400 (Bad Request)} if the afspraakstatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the afspraakstatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Afspraakstatus> updateAfspraakstatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Afspraakstatus afspraakstatus
    ) throws URISyntaxException {
        log.debug("REST request to update Afspraakstatus : {}, {}", id, afspraakstatus);
        if (afspraakstatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afspraakstatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afspraakstatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        afspraakstatus = afspraakstatusRepository.save(afspraakstatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, afspraakstatus.getId().toString()))
            .body(afspraakstatus);
    }

    /**
     * {@code PATCH  /afspraakstatuses/:id} : Partial updates given fields of an existing afspraakstatus, field will ignore if it is null
     *
     * @param id the id of the afspraakstatus to save.
     * @param afspraakstatus the afspraakstatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afspraakstatus,
     * or with status {@code 400 (Bad Request)} if the afspraakstatus is not valid,
     * or with status {@code 404 (Not Found)} if the afspraakstatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the afspraakstatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Afspraakstatus> partialUpdateAfspraakstatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Afspraakstatus afspraakstatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update Afspraakstatus partially : {}, {}", id, afspraakstatus);
        if (afspraakstatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afspraakstatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afspraakstatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Afspraakstatus> result = afspraakstatusRepository
            .findById(afspraakstatus.getId())
            .map(existingAfspraakstatus -> {
                if (afspraakstatus.getStatus() != null) {
                    existingAfspraakstatus.setStatus(afspraakstatus.getStatus());
                }

                return existingAfspraakstatus;
            })
            .map(afspraakstatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, afspraakstatus.getId().toString())
        );
    }

    /**
     * {@code GET  /afspraakstatuses} : get all the afspraakstatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of afspraakstatuses in body.
     */
    @GetMapping("")
    public List<Afspraakstatus> getAllAfspraakstatuses() {
        log.debug("REST request to get all Afspraakstatuses");
        return afspraakstatusRepository.findAll();
    }

    /**
     * {@code GET  /afspraakstatuses/:id} : get the "id" afspraakstatus.
     *
     * @param id the id of the afspraakstatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the afspraakstatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Afspraakstatus> getAfspraakstatus(@PathVariable("id") Long id) {
        log.debug("REST request to get Afspraakstatus : {}", id);
        Optional<Afspraakstatus> afspraakstatus = afspraakstatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(afspraakstatus);
    }

    /**
     * {@code DELETE  /afspraakstatuses/:id} : delete the "id" afspraakstatus.
     *
     * @param id the id of the afspraakstatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAfspraakstatus(@PathVariable("id") Long id) {
        log.debug("REST request to delete Afspraakstatus : {}", id);
        afspraakstatusRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
