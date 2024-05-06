package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Disciplinairemaatregel;
import nl.ritense.demo.repository.DisciplinairemaatregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Disciplinairemaatregel}.
 */
@RestController
@RequestMapping("/api/disciplinairemaatregels")
@Transactional
public class DisciplinairemaatregelResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinairemaatregelResource.class);

    private static final String ENTITY_NAME = "disciplinairemaatregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplinairemaatregelRepository disciplinairemaatregelRepository;

    public DisciplinairemaatregelResource(DisciplinairemaatregelRepository disciplinairemaatregelRepository) {
        this.disciplinairemaatregelRepository = disciplinairemaatregelRepository;
    }

    /**
     * {@code POST  /disciplinairemaatregels} : Create a new disciplinairemaatregel.
     *
     * @param disciplinairemaatregel the disciplinairemaatregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplinairemaatregel, or with status {@code 400 (Bad Request)} if the disciplinairemaatregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Disciplinairemaatregel> createDisciplinairemaatregel(@RequestBody Disciplinairemaatregel disciplinairemaatregel)
        throws URISyntaxException {
        log.debug("REST request to save Disciplinairemaatregel : {}", disciplinairemaatregel);
        if (disciplinairemaatregel.getId() != null) {
            throw new BadRequestAlertException("A new disciplinairemaatregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        disciplinairemaatregel = disciplinairemaatregelRepository.save(disciplinairemaatregel);
        return ResponseEntity.created(new URI("/api/disciplinairemaatregels/" + disciplinairemaatregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, disciplinairemaatregel.getId().toString()))
            .body(disciplinairemaatregel);
    }

    /**
     * {@code PUT  /disciplinairemaatregels/:id} : Updates an existing disciplinairemaatregel.
     *
     * @param id the id of the disciplinairemaatregel to save.
     * @param disciplinairemaatregel the disciplinairemaatregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinairemaatregel,
     * or with status {@code 400 (Bad Request)} if the disciplinairemaatregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplinairemaatregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Disciplinairemaatregel> updateDisciplinairemaatregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Disciplinairemaatregel disciplinairemaatregel
    ) throws URISyntaxException {
        log.debug("REST request to update Disciplinairemaatregel : {}, {}", id, disciplinairemaatregel);
        if (disciplinairemaatregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplinairemaatregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinairemaatregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        disciplinairemaatregel = disciplinairemaatregelRepository.save(disciplinairemaatregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, disciplinairemaatregel.getId().toString()))
            .body(disciplinairemaatregel);
    }

    /**
     * {@code PATCH  /disciplinairemaatregels/:id} : Partial updates given fields of an existing disciplinairemaatregel, field will ignore if it is null
     *
     * @param id the id of the disciplinairemaatregel to save.
     * @param disciplinairemaatregel the disciplinairemaatregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinairemaatregel,
     * or with status {@code 400 (Bad Request)} if the disciplinairemaatregel is not valid,
     * or with status {@code 404 (Not Found)} if the disciplinairemaatregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the disciplinairemaatregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Disciplinairemaatregel> partialUpdateDisciplinairemaatregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Disciplinairemaatregel disciplinairemaatregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Disciplinairemaatregel partially : {}, {}", id, disciplinairemaatregel);
        if (disciplinairemaatregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplinairemaatregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinairemaatregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Disciplinairemaatregel> result = disciplinairemaatregelRepository
            .findById(disciplinairemaatregel.getId())
            .map(existingDisciplinairemaatregel -> {
                if (disciplinairemaatregel.getDatumgeconstateerd() != null) {
                    existingDisciplinairemaatregel.setDatumgeconstateerd(disciplinairemaatregel.getDatumgeconstateerd());
                }
                if (disciplinairemaatregel.getDatumopgelegd() != null) {
                    existingDisciplinairemaatregel.setDatumopgelegd(disciplinairemaatregel.getDatumopgelegd());
                }
                if (disciplinairemaatregel.getOmschrijving() != null) {
                    existingDisciplinairemaatregel.setOmschrijving(disciplinairemaatregel.getOmschrijving());
                }
                if (disciplinairemaatregel.getReden() != null) {
                    existingDisciplinairemaatregel.setReden(disciplinairemaatregel.getReden());
                }

                return existingDisciplinairemaatregel;
            })
            .map(disciplinairemaatregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, disciplinairemaatregel.getId().toString())
        );
    }

    /**
     * {@code GET  /disciplinairemaatregels} : get all the disciplinairemaatregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplinairemaatregels in body.
     */
    @GetMapping("")
    public List<Disciplinairemaatregel> getAllDisciplinairemaatregels() {
        log.debug("REST request to get all Disciplinairemaatregels");
        return disciplinairemaatregelRepository.findAll();
    }

    /**
     * {@code GET  /disciplinairemaatregels/:id} : get the "id" disciplinairemaatregel.
     *
     * @param id the id of the disciplinairemaatregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplinairemaatregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Disciplinairemaatregel> getDisciplinairemaatregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Disciplinairemaatregel : {}", id);
        Optional<Disciplinairemaatregel> disciplinairemaatregel = disciplinairemaatregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disciplinairemaatregel);
    }

    /**
     * {@code DELETE  /disciplinairemaatregels/:id} : delete the "id" disciplinairemaatregel.
     *
     * @param id the id of the disciplinairemaatregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplinairemaatregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Disciplinairemaatregel : {}", id);
        disciplinairemaatregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
