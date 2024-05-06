package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Opschortingzaak;
import nl.ritense.demo.repository.OpschortingzaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Opschortingzaak}.
 */
@RestController
@RequestMapping("/api/opschortingzaaks")
@Transactional
public class OpschortingzaakResource {

    private final Logger log = LoggerFactory.getLogger(OpschortingzaakResource.class);

    private static final String ENTITY_NAME = "opschortingzaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpschortingzaakRepository opschortingzaakRepository;

    public OpschortingzaakResource(OpschortingzaakRepository opschortingzaakRepository) {
        this.opschortingzaakRepository = opschortingzaakRepository;
    }

    /**
     * {@code POST  /opschortingzaaks} : Create a new opschortingzaak.
     *
     * @param opschortingzaak the opschortingzaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opschortingzaak, or with status {@code 400 (Bad Request)} if the opschortingzaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Opschortingzaak> createOpschortingzaak(@RequestBody Opschortingzaak opschortingzaak) throws URISyntaxException {
        log.debug("REST request to save Opschortingzaak : {}", opschortingzaak);
        if (opschortingzaak.getId() != null) {
            throw new BadRequestAlertException("A new opschortingzaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opschortingzaak = opschortingzaakRepository.save(opschortingzaak);
        return ResponseEntity.created(new URI("/api/opschortingzaaks/" + opschortingzaak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, opschortingzaak.getId().toString()))
            .body(opschortingzaak);
    }

    /**
     * {@code PUT  /opschortingzaaks/:id} : Updates an existing opschortingzaak.
     *
     * @param id the id of the opschortingzaak to save.
     * @param opschortingzaak the opschortingzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opschortingzaak,
     * or with status {@code 400 (Bad Request)} if the opschortingzaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opschortingzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Opschortingzaak> updateOpschortingzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Opschortingzaak opschortingzaak
    ) throws URISyntaxException {
        log.debug("REST request to update Opschortingzaak : {}, {}", id, opschortingzaak);
        if (opschortingzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opschortingzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opschortingzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        opschortingzaak = opschortingzaakRepository.save(opschortingzaak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opschortingzaak.getId().toString()))
            .body(opschortingzaak);
    }

    /**
     * {@code PATCH  /opschortingzaaks/:id} : Partial updates given fields of an existing opschortingzaak, field will ignore if it is null
     *
     * @param id the id of the opschortingzaak to save.
     * @param opschortingzaak the opschortingzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opschortingzaak,
     * or with status {@code 400 (Bad Request)} if the opschortingzaak is not valid,
     * or with status {@code 404 (Not Found)} if the opschortingzaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the opschortingzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Opschortingzaak> partialUpdateOpschortingzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Opschortingzaak opschortingzaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Opschortingzaak partially : {}, {}", id, opschortingzaak);
        if (opschortingzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opschortingzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opschortingzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Opschortingzaak> result = opschortingzaakRepository
            .findById(opschortingzaak.getId())
            .map(existingOpschortingzaak -> {
                if (opschortingzaak.getIndicatieopschorting() != null) {
                    existingOpschortingzaak.setIndicatieopschorting(opschortingzaak.getIndicatieopschorting());
                }
                if (opschortingzaak.getRedenopschorting() != null) {
                    existingOpschortingzaak.setRedenopschorting(opschortingzaak.getRedenopschorting());
                }

                return existingOpschortingzaak;
            })
            .map(opschortingzaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opschortingzaak.getId().toString())
        );
    }

    /**
     * {@code GET  /opschortingzaaks} : get all the opschortingzaaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opschortingzaaks in body.
     */
    @GetMapping("")
    public List<Opschortingzaak> getAllOpschortingzaaks() {
        log.debug("REST request to get all Opschortingzaaks");
        return opschortingzaakRepository.findAll();
    }

    /**
     * {@code GET  /opschortingzaaks/:id} : get the "id" opschortingzaak.
     *
     * @param id the id of the opschortingzaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opschortingzaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Opschortingzaak> getOpschortingzaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Opschortingzaak : {}", id);
        Optional<Opschortingzaak> opschortingzaak = opschortingzaakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(opschortingzaak);
    }

    /**
     * {@code DELETE  /opschortingzaaks/:id} : delete the "id" opschortingzaak.
     *
     * @param id the id of the opschortingzaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpschortingzaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Opschortingzaak : {}", id);
        opschortingzaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
