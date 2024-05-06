package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Locatieaanduidingwozobject;
import nl.ritense.demo.repository.LocatieaanduidingwozobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Locatieaanduidingwozobject}.
 */
@RestController
@RequestMapping("/api/locatieaanduidingwozobjects")
@Transactional
public class LocatieaanduidingwozobjectResource {

    private final Logger log = LoggerFactory.getLogger(LocatieaanduidingwozobjectResource.class);

    private static final String ENTITY_NAME = "locatieaanduidingwozobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocatieaanduidingwozobjectRepository locatieaanduidingwozobjectRepository;

    public LocatieaanduidingwozobjectResource(LocatieaanduidingwozobjectRepository locatieaanduidingwozobjectRepository) {
        this.locatieaanduidingwozobjectRepository = locatieaanduidingwozobjectRepository;
    }

    /**
     * {@code POST  /locatieaanduidingwozobjects} : Create a new locatieaanduidingwozobject.
     *
     * @param locatieaanduidingwozobject the locatieaanduidingwozobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locatieaanduidingwozobject, or with status {@code 400 (Bad Request)} if the locatieaanduidingwozobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Locatieaanduidingwozobject> createLocatieaanduidingwozobject(
        @Valid @RequestBody Locatieaanduidingwozobject locatieaanduidingwozobject
    ) throws URISyntaxException {
        log.debug("REST request to save Locatieaanduidingwozobject : {}", locatieaanduidingwozobject);
        if (locatieaanduidingwozobject.getId() != null) {
            throw new BadRequestAlertException("A new locatieaanduidingwozobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        locatieaanduidingwozobject = locatieaanduidingwozobjectRepository.save(locatieaanduidingwozobject);
        return ResponseEntity.created(new URI("/api/locatieaanduidingwozobjects/" + locatieaanduidingwozobject.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, locatieaanduidingwozobject.getId().toString())
            )
            .body(locatieaanduidingwozobject);
    }

    /**
     * {@code PUT  /locatieaanduidingwozobjects/:id} : Updates an existing locatieaanduidingwozobject.
     *
     * @param id the id of the locatieaanduidingwozobject to save.
     * @param locatieaanduidingwozobject the locatieaanduidingwozobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatieaanduidingwozobject,
     * or with status {@code 400 (Bad Request)} if the locatieaanduidingwozobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locatieaanduidingwozobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Locatieaanduidingwozobject> updateLocatieaanduidingwozobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Locatieaanduidingwozobject locatieaanduidingwozobject
    ) throws URISyntaxException {
        log.debug("REST request to update Locatieaanduidingwozobject : {}, {}", id, locatieaanduidingwozobject);
        if (locatieaanduidingwozobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatieaanduidingwozobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatieaanduidingwozobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locatieaanduidingwozobject = locatieaanduidingwozobjectRepository.save(locatieaanduidingwozobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatieaanduidingwozobject.getId().toString()))
            .body(locatieaanduidingwozobject);
    }

    /**
     * {@code PATCH  /locatieaanduidingwozobjects/:id} : Partial updates given fields of an existing locatieaanduidingwozobject, field will ignore if it is null
     *
     * @param id the id of the locatieaanduidingwozobject to save.
     * @param locatieaanduidingwozobject the locatieaanduidingwozobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatieaanduidingwozobject,
     * or with status {@code 400 (Bad Request)} if the locatieaanduidingwozobject is not valid,
     * or with status {@code 404 (Not Found)} if the locatieaanduidingwozobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the locatieaanduidingwozobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Locatieaanduidingwozobject> partialUpdateLocatieaanduidingwozobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Locatieaanduidingwozobject locatieaanduidingwozobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Locatieaanduidingwozobject partially : {}, {}", id, locatieaanduidingwozobject);
        if (locatieaanduidingwozobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatieaanduidingwozobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatieaanduidingwozobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Locatieaanduidingwozobject> result = locatieaanduidingwozobjectRepository
            .findById(locatieaanduidingwozobject.getId())
            .map(existingLocatieaanduidingwozobject -> {
                if (locatieaanduidingwozobject.getDatumbegingeldigheid() != null) {
                    existingLocatieaanduidingwozobject.setDatumbegingeldigheid(locatieaanduidingwozobject.getDatumbegingeldigheid());
                }
                if (locatieaanduidingwozobject.getDatumeindegeldigheid() != null) {
                    existingLocatieaanduidingwozobject.setDatumeindegeldigheid(locatieaanduidingwozobject.getDatumeindegeldigheid());
                }
                if (locatieaanduidingwozobject.getLocatieomschrijving() != null) {
                    existingLocatieaanduidingwozobject.setLocatieomschrijving(locatieaanduidingwozobject.getLocatieomschrijving());
                }
                if (locatieaanduidingwozobject.getPrimair() != null) {
                    existingLocatieaanduidingwozobject.setPrimair(locatieaanduidingwozobject.getPrimair());
                }

                return existingLocatieaanduidingwozobject;
            })
            .map(locatieaanduidingwozobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatieaanduidingwozobject.getId().toString())
        );
    }

    /**
     * {@code GET  /locatieaanduidingwozobjects} : get all the locatieaanduidingwozobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locatieaanduidingwozobjects in body.
     */
    @GetMapping("")
    public List<Locatieaanduidingwozobject> getAllLocatieaanduidingwozobjects() {
        log.debug("REST request to get all Locatieaanduidingwozobjects");
        return locatieaanduidingwozobjectRepository.findAll();
    }

    /**
     * {@code GET  /locatieaanduidingwozobjects/:id} : get the "id" locatieaanduidingwozobject.
     *
     * @param id the id of the locatieaanduidingwozobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locatieaanduidingwozobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Locatieaanduidingwozobject> getLocatieaanduidingwozobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Locatieaanduidingwozobject : {}", id);
        Optional<Locatieaanduidingwozobject> locatieaanduidingwozobject = locatieaanduidingwozobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locatieaanduidingwozobject);
    }

    /**
     * {@code DELETE  /locatieaanduidingwozobjects/:id} : delete the "id" locatieaanduidingwozobject.
     *
     * @param id the id of the locatieaanduidingwozobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocatieaanduidingwozobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Locatieaanduidingwozobject : {}", id);
        locatieaanduidingwozobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
