package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overiggebouwdobject;
import nl.ritense.demo.repository.OveriggebouwdobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overiggebouwdobject}.
 */
@RestController
@RequestMapping("/api/overiggebouwdobjects")
@Transactional
public class OveriggebouwdobjectResource {

    private final Logger log = LoggerFactory.getLogger(OveriggebouwdobjectResource.class);

    private static final String ENTITY_NAME = "overiggebouwdobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OveriggebouwdobjectRepository overiggebouwdobjectRepository;

    public OveriggebouwdobjectResource(OveriggebouwdobjectRepository overiggebouwdobjectRepository) {
        this.overiggebouwdobjectRepository = overiggebouwdobjectRepository;
    }

    /**
     * {@code POST  /overiggebouwdobjects} : Create a new overiggebouwdobject.
     *
     * @param overiggebouwdobject the overiggebouwdobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overiggebouwdobject, or with status {@code 400 (Bad Request)} if the overiggebouwdobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overiggebouwdobject> createOveriggebouwdobject(@RequestBody Overiggebouwdobject overiggebouwdobject)
        throws URISyntaxException {
        log.debug("REST request to save Overiggebouwdobject : {}", overiggebouwdobject);
        if (overiggebouwdobject.getId() != null) {
            throw new BadRequestAlertException("A new overiggebouwdobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        overiggebouwdobject = overiggebouwdobjectRepository.save(overiggebouwdobject);
        return ResponseEntity.created(new URI("/api/overiggebouwdobjects/" + overiggebouwdobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, overiggebouwdobject.getId().toString()))
            .body(overiggebouwdobject);
    }

    /**
     * {@code PUT  /overiggebouwdobjects/:id} : Updates an existing overiggebouwdobject.
     *
     * @param id the id of the overiggebouwdobject to save.
     * @param overiggebouwdobject the overiggebouwdobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overiggebouwdobject,
     * or with status {@code 400 (Bad Request)} if the overiggebouwdobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overiggebouwdobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overiggebouwdobject> updateOveriggebouwdobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overiggebouwdobject overiggebouwdobject
    ) throws URISyntaxException {
        log.debug("REST request to update Overiggebouwdobject : {}, {}", id, overiggebouwdobject);
        if (overiggebouwdobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overiggebouwdobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overiggebouwdobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overiggebouwdobject = overiggebouwdobjectRepository.save(overiggebouwdobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overiggebouwdobject.getId().toString()))
            .body(overiggebouwdobject);
    }

    /**
     * {@code PATCH  /overiggebouwdobjects/:id} : Partial updates given fields of an existing overiggebouwdobject, field will ignore if it is null
     *
     * @param id the id of the overiggebouwdobject to save.
     * @param overiggebouwdobject the overiggebouwdobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overiggebouwdobject,
     * or with status {@code 400 (Bad Request)} if the overiggebouwdobject is not valid,
     * or with status {@code 404 (Not Found)} if the overiggebouwdobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the overiggebouwdobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overiggebouwdobject> partialUpdateOveriggebouwdobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overiggebouwdobject overiggebouwdobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Overiggebouwdobject partially : {}, {}", id, overiggebouwdobject);
        if (overiggebouwdobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overiggebouwdobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overiggebouwdobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overiggebouwdobject> result = overiggebouwdobjectRepository
            .findById(overiggebouwdobject.getId())
            .map(existingOveriggebouwdobject -> {
                if (overiggebouwdobject.getBouwjaar() != null) {
                    existingOveriggebouwdobject.setBouwjaar(overiggebouwdobject.getBouwjaar());
                }
                if (overiggebouwdobject.getIndicatieplanobject() != null) {
                    existingOveriggebouwdobject.setIndicatieplanobject(overiggebouwdobject.getIndicatieplanobject());
                }
                if (overiggebouwdobject.getOveriggebouwdobjectidentificatie() != null) {
                    existingOveriggebouwdobject.setOveriggebouwdobjectidentificatie(
                        overiggebouwdobject.getOveriggebouwdobjectidentificatie()
                    );
                }

                return existingOveriggebouwdobject;
            })
            .map(overiggebouwdobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overiggebouwdobject.getId().toString())
        );
    }

    /**
     * {@code GET  /overiggebouwdobjects} : get all the overiggebouwdobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overiggebouwdobjects in body.
     */
    @GetMapping("")
    public List<Overiggebouwdobject> getAllOveriggebouwdobjects() {
        log.debug("REST request to get all Overiggebouwdobjects");
        return overiggebouwdobjectRepository.findAll();
    }

    /**
     * {@code GET  /overiggebouwdobjects/:id} : get the "id" overiggebouwdobject.
     *
     * @param id the id of the overiggebouwdobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overiggebouwdobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overiggebouwdobject> getOveriggebouwdobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Overiggebouwdobject : {}", id);
        Optional<Overiggebouwdobject> overiggebouwdobject = overiggebouwdobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overiggebouwdobject);
    }

    /**
     * {@code DELETE  /overiggebouwdobjects/:id} : delete the "id" overiggebouwdobject.
     *
     * @param id the id of the overiggebouwdobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOveriggebouwdobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overiggebouwdobject : {}", id);
        overiggebouwdobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
