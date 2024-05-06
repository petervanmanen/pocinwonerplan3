package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Instructieregel;
import nl.ritense.demo.repository.InstructieregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Instructieregel}.
 */
@RestController
@RequestMapping("/api/instructieregels")
@Transactional
public class InstructieregelResource {

    private final Logger log = LoggerFactory.getLogger(InstructieregelResource.class);

    private static final String ENTITY_NAME = "instructieregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstructieregelRepository instructieregelRepository;

    public InstructieregelResource(InstructieregelRepository instructieregelRepository) {
        this.instructieregelRepository = instructieregelRepository;
    }

    /**
     * {@code POST  /instructieregels} : Create a new instructieregel.
     *
     * @param instructieregel the instructieregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instructieregel, or with status {@code 400 (Bad Request)} if the instructieregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Instructieregel> createInstructieregel(@RequestBody Instructieregel instructieregel) throws URISyntaxException {
        log.debug("REST request to save Instructieregel : {}", instructieregel);
        if (instructieregel.getId() != null) {
            throw new BadRequestAlertException("A new instructieregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        instructieregel = instructieregelRepository.save(instructieregel);
        return ResponseEntity.created(new URI("/api/instructieregels/" + instructieregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, instructieregel.getId().toString()))
            .body(instructieregel);
    }

    /**
     * {@code PUT  /instructieregels/:id} : Updates an existing instructieregel.
     *
     * @param id the id of the instructieregel to save.
     * @param instructieregel the instructieregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instructieregel,
     * or with status {@code 400 (Bad Request)} if the instructieregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instructieregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Instructieregel> updateInstructieregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Instructieregel instructieregel
    ) throws URISyntaxException {
        log.debug("REST request to update Instructieregel : {}, {}", id, instructieregel);
        if (instructieregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instructieregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instructieregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        instructieregel = instructieregelRepository.save(instructieregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, instructieregel.getId().toString()))
            .body(instructieregel);
    }

    /**
     * {@code PATCH  /instructieregels/:id} : Partial updates given fields of an existing instructieregel, field will ignore if it is null
     *
     * @param id the id of the instructieregel to save.
     * @param instructieregel the instructieregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instructieregel,
     * or with status {@code 400 (Bad Request)} if the instructieregel is not valid,
     * or with status {@code 404 (Not Found)} if the instructieregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the instructieregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Instructieregel> partialUpdateInstructieregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Instructieregel instructieregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Instructieregel partially : {}, {}", id, instructieregel);
        if (instructieregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, instructieregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!instructieregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Instructieregel> result = instructieregelRepository
            .findById(instructieregel.getId())
            .map(existingInstructieregel -> {
                if (instructieregel.getInstructieregelinstrument() != null) {
                    existingInstructieregel.setInstructieregelinstrument(instructieregel.getInstructieregelinstrument());
                }
                if (instructieregel.getInstructieregeltaakuitoefening() != null) {
                    existingInstructieregel.setInstructieregeltaakuitoefening(instructieregel.getInstructieregeltaakuitoefening());
                }

                return existingInstructieregel;
            })
            .map(instructieregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, instructieregel.getId().toString())
        );
    }

    /**
     * {@code GET  /instructieregels} : get all the instructieregels.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instructieregels in body.
     */
    @GetMapping("")
    public List<Instructieregel> getAllInstructieregels(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Instructieregels");
        if (eagerload) {
            return instructieregelRepository.findAllWithEagerRelationships();
        } else {
            return instructieregelRepository.findAll();
        }
    }

    /**
     * {@code GET  /instructieregels/:id} : get the "id" instructieregel.
     *
     * @param id the id of the instructieregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instructieregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Instructieregel> getInstructieregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Instructieregel : {}", id);
        Optional<Instructieregel> instructieregel = instructieregelRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(instructieregel);
    }

    /**
     * {@code DELETE  /instructieregels/:id} : delete the "id" instructieregel.
     *
     * @param id the id of the instructieregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructieregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Instructieregel : {}", id);
        instructieregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
