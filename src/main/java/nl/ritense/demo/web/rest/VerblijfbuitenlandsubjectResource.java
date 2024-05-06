package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfbuitenlandsubject;
import nl.ritense.demo.repository.VerblijfbuitenlandsubjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verblijfbuitenlandsubject}.
 */
@RestController
@RequestMapping("/api/verblijfbuitenlandsubjects")
@Transactional
public class VerblijfbuitenlandsubjectResource {

    private final Logger log = LoggerFactory.getLogger(VerblijfbuitenlandsubjectResource.class);

    private static final String ENTITY_NAME = "verblijfbuitenlandsubject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerblijfbuitenlandsubjectRepository verblijfbuitenlandsubjectRepository;

    public VerblijfbuitenlandsubjectResource(VerblijfbuitenlandsubjectRepository verblijfbuitenlandsubjectRepository) {
        this.verblijfbuitenlandsubjectRepository = verblijfbuitenlandsubjectRepository;
    }

    /**
     * {@code POST  /verblijfbuitenlandsubjects} : Create a new verblijfbuitenlandsubject.
     *
     * @param verblijfbuitenlandsubject the verblijfbuitenlandsubject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verblijfbuitenlandsubject, or with status {@code 400 (Bad Request)} if the verblijfbuitenlandsubject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verblijfbuitenlandsubject> createVerblijfbuitenlandsubject(
        @RequestBody Verblijfbuitenlandsubject verblijfbuitenlandsubject
    ) throws URISyntaxException {
        log.debug("REST request to save Verblijfbuitenlandsubject : {}", verblijfbuitenlandsubject);
        if (verblijfbuitenlandsubject.getId() != null) {
            throw new BadRequestAlertException("A new verblijfbuitenlandsubject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verblijfbuitenlandsubject = verblijfbuitenlandsubjectRepository.save(verblijfbuitenlandsubject);
        return ResponseEntity.created(new URI("/api/verblijfbuitenlandsubjects/" + verblijfbuitenlandsubject.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verblijfbuitenlandsubject.getId().toString())
            )
            .body(verblijfbuitenlandsubject);
    }

    /**
     * {@code PUT  /verblijfbuitenlandsubjects/:id} : Updates an existing verblijfbuitenlandsubject.
     *
     * @param id the id of the verblijfbuitenlandsubject to save.
     * @param verblijfbuitenlandsubject the verblijfbuitenlandsubject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfbuitenlandsubject,
     * or with status {@code 400 (Bad Request)} if the verblijfbuitenlandsubject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verblijfbuitenlandsubject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verblijfbuitenlandsubject> updateVerblijfbuitenlandsubject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfbuitenlandsubject verblijfbuitenlandsubject
    ) throws URISyntaxException {
        log.debug("REST request to update Verblijfbuitenlandsubject : {}, {}", id, verblijfbuitenlandsubject);
        if (verblijfbuitenlandsubject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfbuitenlandsubject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfbuitenlandsubjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verblijfbuitenlandsubject = verblijfbuitenlandsubjectRepository.save(verblijfbuitenlandsubject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfbuitenlandsubject.getId().toString()))
            .body(verblijfbuitenlandsubject);
    }

    /**
     * {@code PATCH  /verblijfbuitenlandsubjects/:id} : Partial updates given fields of an existing verblijfbuitenlandsubject, field will ignore if it is null
     *
     * @param id the id of the verblijfbuitenlandsubject to save.
     * @param verblijfbuitenlandsubject the verblijfbuitenlandsubject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfbuitenlandsubject,
     * or with status {@code 400 (Bad Request)} if the verblijfbuitenlandsubject is not valid,
     * or with status {@code 404 (Not Found)} if the verblijfbuitenlandsubject is not found,
     * or with status {@code 500 (Internal Server Error)} if the verblijfbuitenlandsubject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verblijfbuitenlandsubject> partialUpdateVerblijfbuitenlandsubject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfbuitenlandsubject verblijfbuitenlandsubject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verblijfbuitenlandsubject partially : {}, {}", id, verblijfbuitenlandsubject);
        if (verblijfbuitenlandsubject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfbuitenlandsubject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfbuitenlandsubjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verblijfbuitenlandsubject> result = verblijfbuitenlandsubjectRepository
            .findById(verblijfbuitenlandsubject.getId())
            .map(existingVerblijfbuitenlandsubject -> {
                if (verblijfbuitenlandsubject.getAdresbuitenland1() != null) {
                    existingVerblijfbuitenlandsubject.setAdresbuitenland1(verblijfbuitenlandsubject.getAdresbuitenland1());
                }
                if (verblijfbuitenlandsubject.getAdresbuitenland2() != null) {
                    existingVerblijfbuitenlandsubject.setAdresbuitenland2(verblijfbuitenlandsubject.getAdresbuitenland2());
                }
                if (verblijfbuitenlandsubject.getAdresbuitenland3() != null) {
                    existingVerblijfbuitenlandsubject.setAdresbuitenland3(verblijfbuitenlandsubject.getAdresbuitenland3());
                }
                if (verblijfbuitenlandsubject.getLandverblijfadres() != null) {
                    existingVerblijfbuitenlandsubject.setLandverblijfadres(verblijfbuitenlandsubject.getLandverblijfadres());
                }

                return existingVerblijfbuitenlandsubject;
            })
            .map(verblijfbuitenlandsubjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfbuitenlandsubject.getId().toString())
        );
    }

    /**
     * {@code GET  /verblijfbuitenlandsubjects} : get all the verblijfbuitenlandsubjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verblijfbuitenlandsubjects in body.
     */
    @GetMapping("")
    public List<Verblijfbuitenlandsubject> getAllVerblijfbuitenlandsubjects() {
        log.debug("REST request to get all Verblijfbuitenlandsubjects");
        return verblijfbuitenlandsubjectRepository.findAll();
    }

    /**
     * {@code GET  /verblijfbuitenlandsubjects/:id} : get the "id" verblijfbuitenlandsubject.
     *
     * @param id the id of the verblijfbuitenlandsubject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verblijfbuitenlandsubject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verblijfbuitenlandsubject> getVerblijfbuitenlandsubject(@PathVariable("id") Long id) {
        log.debug("REST request to get Verblijfbuitenlandsubject : {}", id);
        Optional<Verblijfbuitenlandsubject> verblijfbuitenlandsubject = verblijfbuitenlandsubjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verblijfbuitenlandsubject);
    }

    /**
     * {@code DELETE  /verblijfbuitenlandsubjects/:id} : delete the "id" verblijfbuitenlandsubject.
     *
     * @param id the id of the verblijfbuitenlandsubject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerblijfbuitenlandsubject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verblijfbuitenlandsubject : {}", id);
        verblijfbuitenlandsubjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
