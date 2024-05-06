package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Openbareactiviteit;
import nl.ritense.demo.repository.OpenbareactiviteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Openbareactiviteit}.
 */
@RestController
@RequestMapping("/api/openbareactiviteits")
@Transactional
public class OpenbareactiviteitResource {

    private final Logger log = LoggerFactory.getLogger(OpenbareactiviteitResource.class);

    private static final String ENTITY_NAME = "openbareactiviteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpenbareactiviteitRepository openbareactiviteitRepository;

    public OpenbareactiviteitResource(OpenbareactiviteitRepository openbareactiviteitRepository) {
        this.openbareactiviteitRepository = openbareactiviteitRepository;
    }

    /**
     * {@code POST  /openbareactiviteits} : Create a new openbareactiviteit.
     *
     * @param openbareactiviteit the openbareactiviteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new openbareactiviteit, or with status {@code 400 (Bad Request)} if the openbareactiviteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Openbareactiviteit> createOpenbareactiviteit(@RequestBody Openbareactiviteit openbareactiviteit)
        throws URISyntaxException {
        log.debug("REST request to save Openbareactiviteit : {}", openbareactiviteit);
        if (openbareactiviteit.getId() != null) {
            throw new BadRequestAlertException("A new openbareactiviteit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        openbareactiviteit = openbareactiviteitRepository.save(openbareactiviteit);
        return ResponseEntity.created(new URI("/api/openbareactiviteits/" + openbareactiviteit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, openbareactiviteit.getId().toString()))
            .body(openbareactiviteit);
    }

    /**
     * {@code PUT  /openbareactiviteits/:id} : Updates an existing openbareactiviteit.
     *
     * @param id the id of the openbareactiviteit to save.
     * @param openbareactiviteit the openbareactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated openbareactiviteit,
     * or with status {@code 400 (Bad Request)} if the openbareactiviteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the openbareactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Openbareactiviteit> updateOpenbareactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Openbareactiviteit openbareactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to update Openbareactiviteit : {}, {}", id, openbareactiviteit);
        if (openbareactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, openbareactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!openbareactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        openbareactiviteit = openbareactiviteitRepository.save(openbareactiviteit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, openbareactiviteit.getId().toString()))
            .body(openbareactiviteit);
    }

    /**
     * {@code PATCH  /openbareactiviteits/:id} : Partial updates given fields of an existing openbareactiviteit, field will ignore if it is null
     *
     * @param id the id of the openbareactiviteit to save.
     * @param openbareactiviteit the openbareactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated openbareactiviteit,
     * or with status {@code 400 (Bad Request)} if the openbareactiviteit is not valid,
     * or with status {@code 404 (Not Found)} if the openbareactiviteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the openbareactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Openbareactiviteit> partialUpdateOpenbareactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Openbareactiviteit openbareactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Openbareactiviteit partially : {}, {}", id, openbareactiviteit);
        if (openbareactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, openbareactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!openbareactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Openbareactiviteit> result = openbareactiviteitRepository
            .findById(openbareactiviteit.getId())
            .map(existingOpenbareactiviteit -> {
                if (openbareactiviteit.getDatumeinde() != null) {
                    existingOpenbareactiviteit.setDatumeinde(openbareactiviteit.getDatumeinde());
                }
                if (openbareactiviteit.getDatumstart() != null) {
                    existingOpenbareactiviteit.setDatumstart(openbareactiviteit.getDatumstart());
                }
                if (openbareactiviteit.getEvenmentnaam() != null) {
                    existingOpenbareactiviteit.setEvenmentnaam(openbareactiviteit.getEvenmentnaam());
                }
                if (openbareactiviteit.getLocatieomschrijving() != null) {
                    existingOpenbareactiviteit.setLocatieomschrijving(openbareactiviteit.getLocatieomschrijving());
                }
                if (openbareactiviteit.getStatus() != null) {
                    existingOpenbareactiviteit.setStatus(openbareactiviteit.getStatus());
                }

                return existingOpenbareactiviteit;
            })
            .map(openbareactiviteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, openbareactiviteit.getId().toString())
        );
    }

    /**
     * {@code GET  /openbareactiviteits} : get all the openbareactiviteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of openbareactiviteits in body.
     */
    @GetMapping("")
    public List<Openbareactiviteit> getAllOpenbareactiviteits() {
        log.debug("REST request to get all Openbareactiviteits");
        return openbareactiviteitRepository.findAll();
    }

    /**
     * {@code GET  /openbareactiviteits/:id} : get the "id" openbareactiviteit.
     *
     * @param id the id of the openbareactiviteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the openbareactiviteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Openbareactiviteit> getOpenbareactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Openbareactiviteit : {}", id);
        Optional<Openbareactiviteit> openbareactiviteit = openbareactiviteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(openbareactiviteit);
    }

    /**
     * {@code DELETE  /openbareactiviteits/:id} : delete the "id" openbareactiviteit.
     *
     * @param id the id of the openbareactiviteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpenbareactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Openbareactiviteit : {}", id);
        openbareactiviteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
