package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Locatieaanduidingadreswozobject;
import nl.ritense.demo.repository.LocatieaanduidingadreswozobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Locatieaanduidingadreswozobject}.
 */
@RestController
@RequestMapping("/api/locatieaanduidingadreswozobjects")
@Transactional
public class LocatieaanduidingadreswozobjectResource {

    private final Logger log = LoggerFactory.getLogger(LocatieaanduidingadreswozobjectResource.class);

    private static final String ENTITY_NAME = "locatieaanduidingadreswozobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocatieaanduidingadreswozobjectRepository locatieaanduidingadreswozobjectRepository;

    public LocatieaanduidingadreswozobjectResource(LocatieaanduidingadreswozobjectRepository locatieaanduidingadreswozobjectRepository) {
        this.locatieaanduidingadreswozobjectRepository = locatieaanduidingadreswozobjectRepository;
    }

    /**
     * {@code POST  /locatieaanduidingadreswozobjects} : Create a new locatieaanduidingadreswozobject.
     *
     * @param locatieaanduidingadreswozobject the locatieaanduidingadreswozobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locatieaanduidingadreswozobject, or with status {@code 400 (Bad Request)} if the locatieaanduidingadreswozobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Locatieaanduidingadreswozobject> createLocatieaanduidingadreswozobject(
        @RequestBody Locatieaanduidingadreswozobject locatieaanduidingadreswozobject
    ) throws URISyntaxException {
        log.debug("REST request to save Locatieaanduidingadreswozobject : {}", locatieaanduidingadreswozobject);
        if (locatieaanduidingadreswozobject.getId() != null) {
            throw new BadRequestAlertException("A new locatieaanduidingadreswozobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        locatieaanduidingadreswozobject = locatieaanduidingadreswozobjectRepository.save(locatieaanduidingadreswozobject);
        return ResponseEntity.created(new URI("/api/locatieaanduidingadreswozobjects/" + locatieaanduidingadreswozobject.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    locatieaanduidingadreswozobject.getId().toString()
                )
            )
            .body(locatieaanduidingadreswozobject);
    }

    /**
     * {@code PUT  /locatieaanduidingadreswozobjects/:id} : Updates an existing locatieaanduidingadreswozobject.
     *
     * @param id the id of the locatieaanduidingadreswozobject to save.
     * @param locatieaanduidingadreswozobject the locatieaanduidingadreswozobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatieaanduidingadreswozobject,
     * or with status {@code 400 (Bad Request)} if the locatieaanduidingadreswozobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locatieaanduidingadreswozobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Locatieaanduidingadreswozobject> updateLocatieaanduidingadreswozobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Locatieaanduidingadreswozobject locatieaanduidingadreswozobject
    ) throws URISyntaxException {
        log.debug("REST request to update Locatieaanduidingadreswozobject : {}, {}", id, locatieaanduidingadreswozobject);
        if (locatieaanduidingadreswozobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatieaanduidingadreswozobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatieaanduidingadreswozobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locatieaanduidingadreswozobject = locatieaanduidingadreswozobjectRepository.save(locatieaanduidingadreswozobject);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatieaanduidingadreswozobject.getId().toString())
            )
            .body(locatieaanduidingadreswozobject);
    }

    /**
     * {@code PATCH  /locatieaanduidingadreswozobjects/:id} : Partial updates given fields of an existing locatieaanduidingadreswozobject, field will ignore if it is null
     *
     * @param id the id of the locatieaanduidingadreswozobject to save.
     * @param locatieaanduidingadreswozobject the locatieaanduidingadreswozobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatieaanduidingadreswozobject,
     * or with status {@code 400 (Bad Request)} if the locatieaanduidingadreswozobject is not valid,
     * or with status {@code 404 (Not Found)} if the locatieaanduidingadreswozobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the locatieaanduidingadreswozobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Locatieaanduidingadreswozobject> partialUpdateLocatieaanduidingadreswozobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Locatieaanduidingadreswozobject locatieaanduidingadreswozobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Locatieaanduidingadreswozobject partially : {}, {}", id, locatieaanduidingadreswozobject);
        if (locatieaanduidingadreswozobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatieaanduidingadreswozobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatieaanduidingadreswozobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Locatieaanduidingadreswozobject> result = locatieaanduidingadreswozobjectRepository
            .findById(locatieaanduidingadreswozobject.getId())
            .map(existingLocatieaanduidingadreswozobject -> {
                if (locatieaanduidingadreswozobject.getLocatieomschrijving() != null) {
                    existingLocatieaanduidingadreswozobject.setLocatieomschrijving(
                        locatieaanduidingadreswozobject.getLocatieomschrijving()
                    );
                }

                return existingLocatieaanduidingadreswozobject;
            })
            .map(locatieaanduidingadreswozobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatieaanduidingadreswozobject.getId().toString())
        );
    }

    /**
     * {@code GET  /locatieaanduidingadreswozobjects} : get all the locatieaanduidingadreswozobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locatieaanduidingadreswozobjects in body.
     */
    @GetMapping("")
    public List<Locatieaanduidingadreswozobject> getAllLocatieaanduidingadreswozobjects() {
        log.debug("REST request to get all Locatieaanduidingadreswozobjects");
        return locatieaanduidingadreswozobjectRepository.findAll();
    }

    /**
     * {@code GET  /locatieaanduidingadreswozobjects/:id} : get the "id" locatieaanduidingadreswozobject.
     *
     * @param id the id of the locatieaanduidingadreswozobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locatieaanduidingadreswozobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Locatieaanduidingadreswozobject> getLocatieaanduidingadreswozobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Locatieaanduidingadreswozobject : {}", id);
        Optional<Locatieaanduidingadreswozobject> locatieaanduidingadreswozobject = locatieaanduidingadreswozobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locatieaanduidingadreswozobject);
    }

    /**
     * {@code DELETE  /locatieaanduidingadreswozobjects/:id} : delete the "id" locatieaanduidingadreswozobject.
     *
     * @param id the id of the locatieaanduidingadreswozobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocatieaanduidingadreswozobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Locatieaanduidingadreswozobject : {}", id);
        locatieaanduidingadreswozobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
