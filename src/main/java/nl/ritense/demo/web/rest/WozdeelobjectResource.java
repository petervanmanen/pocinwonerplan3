package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Wozdeelobject;
import nl.ritense.demo.repository.WozdeelobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Wozdeelobject}.
 */
@RestController
@RequestMapping("/api/wozdeelobjects")
@Transactional
public class WozdeelobjectResource {

    private final Logger log = LoggerFactory.getLogger(WozdeelobjectResource.class);

    private static final String ENTITY_NAME = "wozdeelobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WozdeelobjectRepository wozdeelobjectRepository;

    public WozdeelobjectResource(WozdeelobjectRepository wozdeelobjectRepository) {
        this.wozdeelobjectRepository = wozdeelobjectRepository;
    }

    /**
     * {@code POST  /wozdeelobjects} : Create a new wozdeelobject.
     *
     * @param wozdeelobject the wozdeelobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wozdeelobject, or with status {@code 400 (Bad Request)} if the wozdeelobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Wozdeelobject> createWozdeelobject(@RequestBody Wozdeelobject wozdeelobject) throws URISyntaxException {
        log.debug("REST request to save Wozdeelobject : {}", wozdeelobject);
        if (wozdeelobject.getId() != null) {
            throw new BadRequestAlertException("A new wozdeelobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        wozdeelobject = wozdeelobjectRepository.save(wozdeelobject);
        return ResponseEntity.created(new URI("/api/wozdeelobjects/" + wozdeelobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, wozdeelobject.getId().toString()))
            .body(wozdeelobject);
    }

    /**
     * {@code PUT  /wozdeelobjects/:id} : Updates an existing wozdeelobject.
     *
     * @param id the id of the wozdeelobject to save.
     * @param wozdeelobject the wozdeelobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozdeelobject,
     * or with status {@code 400 (Bad Request)} if the wozdeelobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wozdeelobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Wozdeelobject> updateWozdeelobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozdeelobject wozdeelobject
    ) throws URISyntaxException {
        log.debug("REST request to update Wozdeelobject : {}, {}", id, wozdeelobject);
        if (wozdeelobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozdeelobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozdeelobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        wozdeelobject = wozdeelobjectRepository.save(wozdeelobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozdeelobject.getId().toString()))
            .body(wozdeelobject);
    }

    /**
     * {@code PATCH  /wozdeelobjects/:id} : Partial updates given fields of an existing wozdeelobject, field will ignore if it is null
     *
     * @param id the id of the wozdeelobject to save.
     * @param wozdeelobject the wozdeelobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozdeelobject,
     * or with status {@code 400 (Bad Request)} if the wozdeelobject is not valid,
     * or with status {@code 404 (Not Found)} if the wozdeelobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the wozdeelobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wozdeelobject> partialUpdateWozdeelobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozdeelobject wozdeelobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wozdeelobject partially : {}, {}", id, wozdeelobject);
        if (wozdeelobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozdeelobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozdeelobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wozdeelobject> result = wozdeelobjectRepository
            .findById(wozdeelobject.getId())
            .map(existingWozdeelobject -> {
                if (wozdeelobject.getCodewozdeelobject() != null) {
                    existingWozdeelobject.setCodewozdeelobject(wozdeelobject.getCodewozdeelobject());
                }
                if (wozdeelobject.getDatumbegingeldigheiddeelobject() != null) {
                    existingWozdeelobject.setDatumbegingeldigheiddeelobject(wozdeelobject.getDatumbegingeldigheiddeelobject());
                }
                if (wozdeelobject.getDatumeindegeldigheiddeelobject() != null) {
                    existingWozdeelobject.setDatumeindegeldigheiddeelobject(wozdeelobject.getDatumeindegeldigheiddeelobject());
                }
                if (wozdeelobject.getStatuswozdeelobject() != null) {
                    existingWozdeelobject.setStatuswozdeelobject(wozdeelobject.getStatuswozdeelobject());
                }
                if (wozdeelobject.getWozdeelobjectnummer() != null) {
                    existingWozdeelobject.setWozdeelobjectnummer(wozdeelobject.getWozdeelobjectnummer());
                }

                return existingWozdeelobject;
            })
            .map(wozdeelobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozdeelobject.getId().toString())
        );
    }

    /**
     * {@code GET  /wozdeelobjects} : get all the wozdeelobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wozdeelobjects in body.
     */
    @GetMapping("")
    public List<Wozdeelobject> getAllWozdeelobjects() {
        log.debug("REST request to get all Wozdeelobjects");
        return wozdeelobjectRepository.findAll();
    }

    /**
     * {@code GET  /wozdeelobjects/:id} : get the "id" wozdeelobject.
     *
     * @param id the id of the wozdeelobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wozdeelobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wozdeelobject> getWozdeelobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Wozdeelobject : {}", id);
        Optional<Wozdeelobject> wozdeelobject = wozdeelobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wozdeelobject);
    }

    /**
     * {@code DELETE  /wozdeelobjects/:id} : delete the "id" wozdeelobject.
     *
     * @param id the id of the wozdeelobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWozdeelobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Wozdeelobject : {}", id);
        wozdeelobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
