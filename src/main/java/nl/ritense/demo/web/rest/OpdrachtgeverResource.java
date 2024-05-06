package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Opdrachtgever;
import nl.ritense.demo.repository.OpdrachtgeverRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Opdrachtgever}.
 */
@RestController
@RequestMapping("/api/opdrachtgevers")
@Transactional
public class OpdrachtgeverResource {

    private final Logger log = LoggerFactory.getLogger(OpdrachtgeverResource.class);

    private static final String ENTITY_NAME = "opdrachtgever";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpdrachtgeverRepository opdrachtgeverRepository;

    public OpdrachtgeverResource(OpdrachtgeverRepository opdrachtgeverRepository) {
        this.opdrachtgeverRepository = opdrachtgeverRepository;
    }

    /**
     * {@code POST  /opdrachtgevers} : Create a new opdrachtgever.
     *
     * @param opdrachtgever the opdrachtgever to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opdrachtgever, or with status {@code 400 (Bad Request)} if the opdrachtgever has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Opdrachtgever> createOpdrachtgever(@Valid @RequestBody Opdrachtgever opdrachtgever) throws URISyntaxException {
        log.debug("REST request to save Opdrachtgever : {}", opdrachtgever);
        if (opdrachtgever.getId() != null) {
            throw new BadRequestAlertException("A new opdrachtgever cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opdrachtgever = opdrachtgeverRepository.save(opdrachtgever);
        return ResponseEntity.created(new URI("/api/opdrachtgevers/" + opdrachtgever.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, opdrachtgever.getId().toString()))
            .body(opdrachtgever);
    }

    /**
     * {@code PUT  /opdrachtgevers/:id} : Updates an existing opdrachtgever.
     *
     * @param id the id of the opdrachtgever to save.
     * @param opdrachtgever the opdrachtgever to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opdrachtgever,
     * or with status {@code 400 (Bad Request)} if the opdrachtgever is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opdrachtgever couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Opdrachtgever> updateOpdrachtgever(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Opdrachtgever opdrachtgever
    ) throws URISyntaxException {
        log.debug("REST request to update Opdrachtgever : {}, {}", id, opdrachtgever);
        if (opdrachtgever.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opdrachtgever.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opdrachtgeverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        opdrachtgever = opdrachtgeverRepository.save(opdrachtgever);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opdrachtgever.getId().toString()))
            .body(opdrachtgever);
    }

    /**
     * {@code PATCH  /opdrachtgevers/:id} : Partial updates given fields of an existing opdrachtgever, field will ignore if it is null
     *
     * @param id the id of the opdrachtgever to save.
     * @param opdrachtgever the opdrachtgever to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opdrachtgever,
     * or with status {@code 400 (Bad Request)} if the opdrachtgever is not valid,
     * or with status {@code 404 (Not Found)} if the opdrachtgever is not found,
     * or with status {@code 500 (Internal Server Error)} if the opdrachtgever couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Opdrachtgever> partialUpdateOpdrachtgever(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Opdrachtgever opdrachtgever
    ) throws URISyntaxException {
        log.debug("REST request to partial update Opdrachtgever partially : {}, {}", id, opdrachtgever);
        if (opdrachtgever.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opdrachtgever.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opdrachtgeverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Opdrachtgever> result = opdrachtgeverRepository
            .findById(opdrachtgever.getId())
            .map(existingOpdrachtgever -> {
                if (opdrachtgever.getClustercode() != null) {
                    existingOpdrachtgever.setClustercode(opdrachtgever.getClustercode());
                }
                if (opdrachtgever.getClusteromschrijving() != null) {
                    existingOpdrachtgever.setClusteromschrijving(opdrachtgever.getClusteromschrijving());
                }
                if (opdrachtgever.getNaam() != null) {
                    existingOpdrachtgever.setNaam(opdrachtgever.getNaam());
                }
                if (opdrachtgever.getNummer() != null) {
                    existingOpdrachtgever.setNummer(opdrachtgever.getNummer());
                }
                if (opdrachtgever.getOmschrijving() != null) {
                    existingOpdrachtgever.setOmschrijving(opdrachtgever.getOmschrijving());
                }

                return existingOpdrachtgever;
            })
            .map(opdrachtgeverRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opdrachtgever.getId().toString())
        );
    }

    /**
     * {@code GET  /opdrachtgevers} : get all the opdrachtgevers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opdrachtgevers in body.
     */
    @GetMapping("")
    public List<Opdrachtgever> getAllOpdrachtgevers() {
        log.debug("REST request to get all Opdrachtgevers");
        return opdrachtgeverRepository.findAll();
    }

    /**
     * {@code GET  /opdrachtgevers/:id} : get the "id" opdrachtgever.
     *
     * @param id the id of the opdrachtgever to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opdrachtgever, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Opdrachtgever> getOpdrachtgever(@PathVariable("id") Long id) {
        log.debug("REST request to get Opdrachtgever : {}", id);
        Optional<Opdrachtgever> opdrachtgever = opdrachtgeverRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(opdrachtgever);
    }

    /**
     * {@code DELETE  /opdrachtgevers/:id} : delete the "id" opdrachtgever.
     *
     * @param id the id of the opdrachtgever to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpdrachtgever(@PathVariable("id") Long id) {
        log.debug("REST request to delete Opdrachtgever : {}", id);
        opdrachtgeverRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
