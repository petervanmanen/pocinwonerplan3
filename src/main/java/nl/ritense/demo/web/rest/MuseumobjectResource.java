package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Museumobject;
import nl.ritense.demo.repository.MuseumobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Museumobject}.
 */
@RestController
@RequestMapping("/api/museumobjects")
@Transactional
public class MuseumobjectResource {

    private final Logger log = LoggerFactory.getLogger(MuseumobjectResource.class);

    private static final String ENTITY_NAME = "museumobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MuseumobjectRepository museumobjectRepository;

    public MuseumobjectResource(MuseumobjectRepository museumobjectRepository) {
        this.museumobjectRepository = museumobjectRepository;
    }

    /**
     * {@code POST  /museumobjects} : Create a new museumobject.
     *
     * @param museumobject the museumobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new museumobject, or with status {@code 400 (Bad Request)} if the museumobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Museumobject> createMuseumobject(@RequestBody Museumobject museumobject) throws URISyntaxException {
        log.debug("REST request to save Museumobject : {}", museumobject);
        if (museumobject.getId() != null) {
            throw new BadRequestAlertException("A new museumobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        museumobject = museumobjectRepository.save(museumobject);
        return ResponseEntity.created(new URI("/api/museumobjects/" + museumobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, museumobject.getId().toString()))
            .body(museumobject);
    }

    /**
     * {@code PUT  /museumobjects/:id} : Updates an existing museumobject.
     *
     * @param id the id of the museumobject to save.
     * @param museumobject the museumobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated museumobject,
     * or with status {@code 400 (Bad Request)} if the museumobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the museumobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Museumobject> updateMuseumobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Museumobject museumobject
    ) throws URISyntaxException {
        log.debug("REST request to update Museumobject : {}, {}", id, museumobject);
        if (museumobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, museumobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!museumobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        museumobject = museumobjectRepository.save(museumobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, museumobject.getId().toString()))
            .body(museumobject);
    }

    /**
     * {@code PATCH  /museumobjects/:id} : Partial updates given fields of an existing museumobject, field will ignore if it is null
     *
     * @param id the id of the museumobject to save.
     * @param museumobject the museumobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated museumobject,
     * or with status {@code 400 (Bad Request)} if the museumobject is not valid,
     * or with status {@code 404 (Not Found)} if the museumobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the museumobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Museumobject> partialUpdateMuseumobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Museumobject museumobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Museumobject partially : {}, {}", id, museumobject);
        if (museumobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, museumobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!museumobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Museumobject> result = museumobjectRepository
            .findById(museumobject.getId())
            .map(existingMuseumobject -> {
                if (museumobject.getAfmeting() != null) {
                    existingMuseumobject.setAfmeting(museumobject.getAfmeting());
                }
                if (museumobject.getBezittot() != null) {
                    existingMuseumobject.setBezittot(museumobject.getBezittot());
                }
                if (museumobject.getBezitvanaf() != null) {
                    existingMuseumobject.setBezitvanaf(museumobject.getBezitvanaf());
                }
                if (museumobject.getMedium() != null) {
                    existingMuseumobject.setMedium(museumobject.getMedium());
                }
                if (museumobject.getVerkrijging() != null) {
                    existingMuseumobject.setVerkrijging(museumobject.getVerkrijging());
                }

                return existingMuseumobject;
            })
            .map(museumobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, museumobject.getId().toString())
        );
    }

    /**
     * {@code GET  /museumobjects} : get all the museumobjects.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of museumobjects in body.
     */
    @GetMapping("")
    public List<Museumobject> getAllMuseumobjects(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Museumobjects");
        if (eagerload) {
            return museumobjectRepository.findAllWithEagerRelationships();
        } else {
            return museumobjectRepository.findAll();
        }
    }

    /**
     * {@code GET  /museumobjects/:id} : get the "id" museumobject.
     *
     * @param id the id of the museumobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the museumobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Museumobject> getMuseumobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Museumobject : {}", id);
        Optional<Museumobject> museumobject = museumobjectRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(museumobject);
    }

    /**
     * {@code DELETE  /museumobjects/:id} : delete the "id" museumobject.
     *
     * @param id the id of the museumobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMuseumobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Museumobject : {}", id);
        museumobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
