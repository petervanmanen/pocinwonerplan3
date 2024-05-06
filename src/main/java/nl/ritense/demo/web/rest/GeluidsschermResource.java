package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Geluidsscherm;
import nl.ritense.demo.repository.GeluidsschermRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Geluidsscherm}.
 */
@RestController
@RequestMapping("/api/geluidsscherms")
@Transactional
public class GeluidsschermResource {

    private final Logger log = LoggerFactory.getLogger(GeluidsschermResource.class);

    private static final String ENTITY_NAME = "geluidsscherm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeluidsschermRepository geluidsschermRepository;

    public GeluidsschermResource(GeluidsschermRepository geluidsschermRepository) {
        this.geluidsschermRepository = geluidsschermRepository;
    }

    /**
     * {@code POST  /geluidsscherms} : Create a new geluidsscherm.
     *
     * @param geluidsscherm the geluidsscherm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geluidsscherm, or with status {@code 400 (Bad Request)} if the geluidsscherm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Geluidsscherm> createGeluidsscherm(@RequestBody Geluidsscherm geluidsscherm) throws URISyntaxException {
        log.debug("REST request to save Geluidsscherm : {}", geluidsscherm);
        if (geluidsscherm.getId() != null) {
            throw new BadRequestAlertException("A new geluidsscherm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        geluidsscherm = geluidsschermRepository.save(geluidsscherm);
        return ResponseEntity.created(new URI("/api/geluidsscherms/" + geluidsscherm.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, geluidsscherm.getId().toString()))
            .body(geluidsscherm);
    }

    /**
     * {@code PUT  /geluidsscherms/:id} : Updates an existing geluidsscherm.
     *
     * @param id the id of the geluidsscherm to save.
     * @param geluidsscherm the geluidsscherm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geluidsscherm,
     * or with status {@code 400 (Bad Request)} if the geluidsscherm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geluidsscherm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Geluidsscherm> updateGeluidsscherm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Geluidsscherm geluidsscherm
    ) throws URISyntaxException {
        log.debug("REST request to update Geluidsscherm : {}, {}", id, geluidsscherm);
        if (geluidsscherm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geluidsscherm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geluidsschermRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        geluidsscherm = geluidsschermRepository.save(geluidsscherm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geluidsscherm.getId().toString()))
            .body(geluidsscherm);
    }

    /**
     * {@code PATCH  /geluidsscherms/:id} : Partial updates given fields of an existing geluidsscherm, field will ignore if it is null
     *
     * @param id the id of the geluidsscherm to save.
     * @param geluidsscherm the geluidsscherm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geluidsscherm,
     * or with status {@code 400 (Bad Request)} if the geluidsscherm is not valid,
     * or with status {@code 404 (Not Found)} if the geluidsscherm is not found,
     * or with status {@code 500 (Internal Server Error)} if the geluidsscherm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Geluidsscherm> partialUpdateGeluidsscherm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Geluidsscherm geluidsscherm
    ) throws URISyntaxException {
        log.debug("REST request to partial update Geluidsscherm partially : {}, {}", id, geluidsscherm);
        if (geluidsscherm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geluidsscherm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geluidsschermRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Geluidsscherm> result = geluidsschermRepository
            .findById(geluidsscherm.getId())
            .map(existingGeluidsscherm -> {
                if (geluidsscherm.getAantaldeuren() != null) {
                    existingGeluidsscherm.setAantaldeuren(geluidsscherm.getAantaldeuren());
                }
                if (geluidsscherm.getAantalpanelen() != null) {
                    existingGeluidsscherm.setAantalpanelen(geluidsscherm.getAantalpanelen());
                }
                if (geluidsscherm.getType() != null) {
                    existingGeluidsscherm.setType(geluidsscherm.getType());
                }

                return existingGeluidsscherm;
            })
            .map(geluidsschermRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geluidsscherm.getId().toString())
        );
    }

    /**
     * {@code GET  /geluidsscherms} : get all the geluidsscherms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geluidsscherms in body.
     */
    @GetMapping("")
    public List<Geluidsscherm> getAllGeluidsscherms() {
        log.debug("REST request to get all Geluidsscherms");
        return geluidsschermRepository.findAll();
    }

    /**
     * {@code GET  /geluidsscherms/:id} : get the "id" geluidsscherm.
     *
     * @param id the id of the geluidsscherm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geluidsscherm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Geluidsscherm> getGeluidsscherm(@PathVariable("id") Long id) {
        log.debug("REST request to get Geluidsscherm : {}", id);
        Optional<Geluidsscherm> geluidsscherm = geluidsschermRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(geluidsscherm);
    }

    /**
     * {@code DELETE  /geluidsscherms/:id} : delete the "id" geluidsscherm.
     *
     * @param id the id of the geluidsscherm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeluidsscherm(@PathVariable("id") Long id) {
        log.debug("REST request to delete Geluidsscherm : {}", id);
        geluidsschermRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
