package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Omzetgroep;
import nl.ritense.demo.repository.OmzetgroepRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Omzetgroep}.
 */
@RestController
@RequestMapping("/api/omzetgroeps")
@Transactional
public class OmzetgroepResource {

    private final Logger log = LoggerFactory.getLogger(OmzetgroepResource.class);

    private static final String ENTITY_NAME = "omzetgroep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OmzetgroepRepository omzetgroepRepository;

    public OmzetgroepResource(OmzetgroepRepository omzetgroepRepository) {
        this.omzetgroepRepository = omzetgroepRepository;
    }

    /**
     * {@code POST  /omzetgroeps} : Create a new omzetgroep.
     *
     * @param omzetgroep the omzetgroep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new omzetgroep, or with status {@code 400 (Bad Request)} if the omzetgroep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Omzetgroep> createOmzetgroep(@RequestBody Omzetgroep omzetgroep) throws URISyntaxException {
        log.debug("REST request to save Omzetgroep : {}", omzetgroep);
        if (omzetgroep.getId() != null) {
            throw new BadRequestAlertException("A new omzetgroep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        omzetgroep = omzetgroepRepository.save(omzetgroep);
        return ResponseEntity.created(new URI("/api/omzetgroeps/" + omzetgroep.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, omzetgroep.getId().toString()))
            .body(omzetgroep);
    }

    /**
     * {@code PUT  /omzetgroeps/:id} : Updates an existing omzetgroep.
     *
     * @param id the id of the omzetgroep to save.
     * @param omzetgroep the omzetgroep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omzetgroep,
     * or with status {@code 400 (Bad Request)} if the omzetgroep is not valid,
     * or with status {@code 500 (Internal Server Error)} if the omzetgroep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Omzetgroep> updateOmzetgroep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Omzetgroep omzetgroep
    ) throws URISyntaxException {
        log.debug("REST request to update Omzetgroep : {}, {}", id, omzetgroep);
        if (omzetgroep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omzetgroep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omzetgroepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        omzetgroep = omzetgroepRepository.save(omzetgroep);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omzetgroep.getId().toString()))
            .body(omzetgroep);
    }

    /**
     * {@code PATCH  /omzetgroeps/:id} : Partial updates given fields of an existing omzetgroep, field will ignore if it is null
     *
     * @param id the id of the omzetgroep to save.
     * @param omzetgroep the omzetgroep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omzetgroep,
     * or with status {@code 400 (Bad Request)} if the omzetgroep is not valid,
     * or with status {@code 404 (Not Found)} if the omzetgroep is not found,
     * or with status {@code 500 (Internal Server Error)} if the omzetgroep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Omzetgroep> partialUpdateOmzetgroep(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Omzetgroep omzetgroep
    ) throws URISyntaxException {
        log.debug("REST request to partial update Omzetgroep partially : {}, {}", id, omzetgroep);
        if (omzetgroep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omzetgroep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omzetgroepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Omzetgroep> result = omzetgroepRepository
            .findById(omzetgroep.getId())
            .map(existingOmzetgroep -> {
                if (omzetgroep.getNaam() != null) {
                    existingOmzetgroep.setNaam(omzetgroep.getNaam());
                }
                if (omzetgroep.getOmschrijving() != null) {
                    existingOmzetgroep.setOmschrijving(omzetgroep.getOmschrijving());
                }

                return existingOmzetgroep;
            })
            .map(omzetgroepRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omzetgroep.getId().toString())
        );
    }

    /**
     * {@code GET  /omzetgroeps} : get all the omzetgroeps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of omzetgroeps in body.
     */
    @GetMapping("")
    public List<Omzetgroep> getAllOmzetgroeps() {
        log.debug("REST request to get all Omzetgroeps");
        return omzetgroepRepository.findAll();
    }

    /**
     * {@code GET  /omzetgroeps/:id} : get the "id" omzetgroep.
     *
     * @param id the id of the omzetgroep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the omzetgroep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Omzetgroep> getOmzetgroep(@PathVariable("id") Long id) {
        log.debug("REST request to get Omzetgroep : {}", id);
        Optional<Omzetgroep> omzetgroep = omzetgroepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(omzetgroep);
    }

    /**
     * {@code DELETE  /omzetgroeps/:id} : delete the "id" omzetgroep.
     *
     * @param id the id of the omzetgroep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOmzetgroep(@PathVariable("id") Long id) {
        log.debug("REST request to delete Omzetgroep : {}", id);
        omzetgroepRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
