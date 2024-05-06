package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Erfgoedobject;
import nl.ritense.demo.repository.ErfgoedobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Erfgoedobject}.
 */
@RestController
@RequestMapping("/api/erfgoedobjects")
@Transactional
public class ErfgoedobjectResource {

    private final Logger log = LoggerFactory.getLogger(ErfgoedobjectResource.class);

    private static final String ENTITY_NAME = "erfgoedobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ErfgoedobjectRepository erfgoedobjectRepository;

    public ErfgoedobjectResource(ErfgoedobjectRepository erfgoedobjectRepository) {
        this.erfgoedobjectRepository = erfgoedobjectRepository;
    }

    /**
     * {@code POST  /erfgoedobjects} : Create a new erfgoedobject.
     *
     * @param erfgoedobject the erfgoedobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new erfgoedobject, or with status {@code 400 (Bad Request)} if the erfgoedobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Erfgoedobject> createErfgoedobject(@RequestBody Erfgoedobject erfgoedobject) throws URISyntaxException {
        log.debug("REST request to save Erfgoedobject : {}", erfgoedobject);
        if (erfgoedobject.getId() != null) {
            throw new BadRequestAlertException("A new erfgoedobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        erfgoedobject = erfgoedobjectRepository.save(erfgoedobject);
        return ResponseEntity.created(new URI("/api/erfgoedobjects/" + erfgoedobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, erfgoedobject.getId().toString()))
            .body(erfgoedobject);
    }

    /**
     * {@code PUT  /erfgoedobjects/:id} : Updates an existing erfgoedobject.
     *
     * @param id the id of the erfgoedobject to save.
     * @param erfgoedobject the erfgoedobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated erfgoedobject,
     * or with status {@code 400 (Bad Request)} if the erfgoedobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the erfgoedobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Erfgoedobject> updateErfgoedobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Erfgoedobject erfgoedobject
    ) throws URISyntaxException {
        log.debug("REST request to update Erfgoedobject : {}, {}", id, erfgoedobject);
        if (erfgoedobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, erfgoedobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!erfgoedobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        erfgoedobject = erfgoedobjectRepository.save(erfgoedobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, erfgoedobject.getId().toString()))
            .body(erfgoedobject);
    }

    /**
     * {@code PATCH  /erfgoedobjects/:id} : Partial updates given fields of an existing erfgoedobject, field will ignore if it is null
     *
     * @param id the id of the erfgoedobject to save.
     * @param erfgoedobject the erfgoedobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated erfgoedobject,
     * or with status {@code 400 (Bad Request)} if the erfgoedobject is not valid,
     * or with status {@code 404 (Not Found)} if the erfgoedobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the erfgoedobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Erfgoedobject> partialUpdateErfgoedobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Erfgoedobject erfgoedobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Erfgoedobject partially : {}, {}", id, erfgoedobject);
        if (erfgoedobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, erfgoedobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!erfgoedobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Erfgoedobject> result = erfgoedobjectRepository
            .findById(erfgoedobject.getId())
            .map(existingErfgoedobject -> {
                if (erfgoedobject.getDateringtot() != null) {
                    existingErfgoedobject.setDateringtot(erfgoedobject.getDateringtot());
                }
                if (erfgoedobject.getDateringvanaf() != null) {
                    existingErfgoedobject.setDateringvanaf(erfgoedobject.getDateringvanaf());
                }
                if (erfgoedobject.getOmschrijving() != null) {
                    existingErfgoedobject.setOmschrijving(erfgoedobject.getOmschrijving());
                }
                if (erfgoedobject.getTitel() != null) {
                    existingErfgoedobject.setTitel(erfgoedobject.getTitel());
                }

                return existingErfgoedobject;
            })
            .map(erfgoedobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, erfgoedobject.getId().toString())
        );
    }

    /**
     * {@code GET  /erfgoedobjects} : get all the erfgoedobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of erfgoedobjects in body.
     */
    @GetMapping("")
    public List<Erfgoedobject> getAllErfgoedobjects() {
        log.debug("REST request to get all Erfgoedobjects");
        return erfgoedobjectRepository.findAll();
    }

    /**
     * {@code GET  /erfgoedobjects/:id} : get the "id" erfgoedobject.
     *
     * @param id the id of the erfgoedobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the erfgoedobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Erfgoedobject> getErfgoedobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Erfgoedobject : {}", id);
        Optional<Erfgoedobject> erfgoedobject = erfgoedobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(erfgoedobject);
    }

    /**
     * {@code DELETE  /erfgoedobjects/:id} : delete the "id" erfgoedobject.
     *
     * @param id the id of the erfgoedobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteErfgoedobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Erfgoedobject : {}", id);
        erfgoedobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
