package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Programma;
import nl.ritense.demo.repository.ProgrammaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Programma}.
 */
@RestController
@RequestMapping("/api/programmas")
@Transactional
public class ProgrammaResource {

    private final Logger log = LoggerFactory.getLogger(ProgrammaResource.class);

    private static final String ENTITY_NAME = "programma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgrammaRepository programmaRepository;

    public ProgrammaResource(ProgrammaRepository programmaRepository) {
        this.programmaRepository = programmaRepository;
    }

    /**
     * {@code POST  /programmas} : Create a new programma.
     *
     * @param programma the programma to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programma, or with status {@code 400 (Bad Request)} if the programma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Programma> createProgramma(@Valid @RequestBody Programma programma) throws URISyntaxException {
        log.debug("REST request to save Programma : {}", programma);
        if (programma.getId() != null) {
            throw new BadRequestAlertException("A new programma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        programma = programmaRepository.save(programma);
        return ResponseEntity.created(new URI("/api/programmas/" + programma.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, programma.getId().toString()))
            .body(programma);
    }

    /**
     * {@code PUT  /programmas/:id} : Updates an existing programma.
     *
     * @param id the id of the programma to save.
     * @param programma the programma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programma,
     * or with status {@code 400 (Bad Request)} if the programma is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Programma> updateProgramma(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Programma programma
    ) throws URISyntaxException {
        log.debug("REST request to update Programma : {}, {}", id, programma);
        if (programma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programma.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programmaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        programma = programmaRepository.save(programma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, programma.getId().toString()))
            .body(programma);
    }

    /**
     * {@code PATCH  /programmas/:id} : Partial updates given fields of an existing programma, field will ignore if it is null
     *
     * @param id the id of the programma to save.
     * @param programma the programma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programma,
     * or with status {@code 400 (Bad Request)} if the programma is not valid,
     * or with status {@code 404 (Not Found)} if the programma is not found,
     * or with status {@code 500 (Internal Server Error)} if the programma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Programma> partialUpdateProgramma(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Programma programma
    ) throws URISyntaxException {
        log.debug("REST request to partial update Programma partially : {}, {}", id, programma);
        if (programma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programma.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programmaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Programma> result = programmaRepository
            .findById(programma.getId())
            .map(existingProgramma -> {
                if (programma.getNaam() != null) {
                    existingProgramma.setNaam(programma.getNaam());
                }

                return existingProgramma;
            })
            .map(programmaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, programma.getId().toString())
        );
    }

    /**
     * {@code GET  /programmas} : get all the programmas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programmas in body.
     */
    @GetMapping("")
    public List<Programma> getAllProgrammas(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Programmas");
        if (eagerload) {
            return programmaRepository.findAllWithEagerRelationships();
        } else {
            return programmaRepository.findAll();
        }
    }

    /**
     * {@code GET  /programmas/:id} : get the "id" programma.
     *
     * @param id the id of the programma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Programma> getProgramma(@PathVariable("id") Long id) {
        log.debug("REST request to get Programma : {}", id);
        Optional<Programma> programma = programmaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(programma);
    }

    /**
     * {@code DELETE  /programmas/:id} : delete the "id" programma.
     *
     * @param id the id of the programma to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramma(@PathVariable("id") Long id) {
        log.debug("REST request to delete Programma : {}", id);
        programmaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
