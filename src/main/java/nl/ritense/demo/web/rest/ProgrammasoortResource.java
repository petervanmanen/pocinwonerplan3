package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Programmasoort;
import nl.ritense.demo.repository.ProgrammasoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Programmasoort}.
 */
@RestController
@RequestMapping("/api/programmasoorts")
@Transactional
public class ProgrammasoortResource {

    private final Logger log = LoggerFactory.getLogger(ProgrammasoortResource.class);

    private static final String ENTITY_NAME = "programmasoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgrammasoortRepository programmasoortRepository;

    public ProgrammasoortResource(ProgrammasoortRepository programmasoortRepository) {
        this.programmasoortRepository = programmasoortRepository;
    }

    /**
     * {@code POST  /programmasoorts} : Create a new programmasoort.
     *
     * @param programmasoort the programmasoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programmasoort, or with status {@code 400 (Bad Request)} if the programmasoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Programmasoort> createProgrammasoort(@RequestBody Programmasoort programmasoort) throws URISyntaxException {
        log.debug("REST request to save Programmasoort : {}", programmasoort);
        if (programmasoort.getId() != null) {
            throw new BadRequestAlertException("A new programmasoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        programmasoort = programmasoortRepository.save(programmasoort);
        return ResponseEntity.created(new URI("/api/programmasoorts/" + programmasoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, programmasoort.getId().toString()))
            .body(programmasoort);
    }

    /**
     * {@code PUT  /programmasoorts/:id} : Updates an existing programmasoort.
     *
     * @param id the id of the programmasoort to save.
     * @param programmasoort the programmasoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programmasoort,
     * or with status {@code 400 (Bad Request)} if the programmasoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programmasoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Programmasoort> updateProgrammasoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Programmasoort programmasoort
    ) throws URISyntaxException {
        log.debug("REST request to update Programmasoort : {}, {}", id, programmasoort);
        if (programmasoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programmasoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programmasoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        programmasoort = programmasoortRepository.save(programmasoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, programmasoort.getId().toString()))
            .body(programmasoort);
    }

    /**
     * {@code PATCH  /programmasoorts/:id} : Partial updates given fields of an existing programmasoort, field will ignore if it is null
     *
     * @param id the id of the programmasoort to save.
     * @param programmasoort the programmasoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programmasoort,
     * or with status {@code 400 (Bad Request)} if the programmasoort is not valid,
     * or with status {@code 404 (Not Found)} if the programmasoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the programmasoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Programmasoort> partialUpdateProgrammasoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Programmasoort programmasoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Programmasoort partially : {}, {}", id, programmasoort);
        if (programmasoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programmasoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programmasoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Programmasoort> result = programmasoortRepository
            .findById(programmasoort.getId())
            .map(existingProgrammasoort -> {
                if (programmasoort.getNaam() != null) {
                    existingProgrammasoort.setNaam(programmasoort.getNaam());
                }
                if (programmasoort.getOmschrijving() != null) {
                    existingProgrammasoort.setOmschrijving(programmasoort.getOmschrijving());
                }

                return existingProgrammasoort;
            })
            .map(programmasoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, programmasoort.getId().toString())
        );
    }

    /**
     * {@code GET  /programmasoorts} : get all the programmasoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programmasoorts in body.
     */
    @GetMapping("")
    public List<Programmasoort> getAllProgrammasoorts() {
        log.debug("REST request to get all Programmasoorts");
        return programmasoortRepository.findAll();
    }

    /**
     * {@code GET  /programmasoorts/:id} : get the "id" programmasoort.
     *
     * @param id the id of the programmasoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programmasoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Programmasoort> getProgrammasoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Programmasoort : {}", id);
        Optional<Programmasoort> programmasoort = programmasoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(programmasoort);
    }

    /**
     * {@code DELETE  /programmasoorts/:id} : delete the "id" programmasoort.
     *
     * @param id the id of the programmasoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgrammasoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Programmasoort : {}", id);
        programmasoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
