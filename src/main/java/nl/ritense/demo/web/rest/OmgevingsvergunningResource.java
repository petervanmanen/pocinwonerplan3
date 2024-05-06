package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Omgevingsvergunning;
import nl.ritense.demo.repository.OmgevingsvergunningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Omgevingsvergunning}.
 */
@RestController
@RequestMapping("/api/omgevingsvergunnings")
@Transactional
public class OmgevingsvergunningResource {

    private final Logger log = LoggerFactory.getLogger(OmgevingsvergunningResource.class);

    private static final String ENTITY_NAME = "omgevingsvergunning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OmgevingsvergunningRepository omgevingsvergunningRepository;

    public OmgevingsvergunningResource(OmgevingsvergunningRepository omgevingsvergunningRepository) {
        this.omgevingsvergunningRepository = omgevingsvergunningRepository;
    }

    /**
     * {@code POST  /omgevingsvergunnings} : Create a new omgevingsvergunning.
     *
     * @param omgevingsvergunning the omgevingsvergunning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new omgevingsvergunning, or with status {@code 400 (Bad Request)} if the omgevingsvergunning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Omgevingsvergunning> createOmgevingsvergunning(@RequestBody Omgevingsvergunning omgevingsvergunning)
        throws URISyntaxException {
        log.debug("REST request to save Omgevingsvergunning : {}", omgevingsvergunning);
        if (omgevingsvergunning.getId() != null) {
            throw new BadRequestAlertException("A new omgevingsvergunning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        omgevingsvergunning = omgevingsvergunningRepository.save(omgevingsvergunning);
        return ResponseEntity.created(new URI("/api/omgevingsvergunnings/" + omgevingsvergunning.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, omgevingsvergunning.getId().toString()))
            .body(omgevingsvergunning);
    }

    /**
     * {@code PUT  /omgevingsvergunnings/:id} : Updates an existing omgevingsvergunning.
     *
     * @param id the id of the omgevingsvergunning to save.
     * @param omgevingsvergunning the omgevingsvergunning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omgevingsvergunning,
     * or with status {@code 400 (Bad Request)} if the omgevingsvergunning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the omgevingsvergunning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Omgevingsvergunning> updateOmgevingsvergunning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Omgevingsvergunning omgevingsvergunning
    ) throws URISyntaxException {
        log.debug("REST request to update Omgevingsvergunning : {}, {}", id, omgevingsvergunning);
        if (omgevingsvergunning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omgevingsvergunning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omgevingsvergunningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        omgevingsvergunning = omgevingsvergunningRepository.save(omgevingsvergunning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omgevingsvergunning.getId().toString()))
            .body(omgevingsvergunning);
    }

    /**
     * {@code PATCH  /omgevingsvergunnings/:id} : Partial updates given fields of an existing omgevingsvergunning, field will ignore if it is null
     *
     * @param id the id of the omgevingsvergunning to save.
     * @param omgevingsvergunning the omgevingsvergunning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omgevingsvergunning,
     * or with status {@code 400 (Bad Request)} if the omgevingsvergunning is not valid,
     * or with status {@code 404 (Not Found)} if the omgevingsvergunning is not found,
     * or with status {@code 500 (Internal Server Error)} if the omgevingsvergunning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Omgevingsvergunning> partialUpdateOmgevingsvergunning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Omgevingsvergunning omgevingsvergunning
    ) throws URISyntaxException {
        log.debug("REST request to partial update Omgevingsvergunning partially : {}, {}", id, omgevingsvergunning);
        if (omgevingsvergunning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omgevingsvergunning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omgevingsvergunningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Omgevingsvergunning> result = omgevingsvergunningRepository
            .findById(omgevingsvergunning.getId())
            .map(omgevingsvergunningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omgevingsvergunning.getId().toString())
        );
    }

    /**
     * {@code GET  /omgevingsvergunnings} : get all the omgevingsvergunnings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of omgevingsvergunnings in body.
     */
    @GetMapping("")
    public List<Omgevingsvergunning> getAllOmgevingsvergunnings() {
        log.debug("REST request to get all Omgevingsvergunnings");
        return omgevingsvergunningRepository.findAll();
    }

    /**
     * {@code GET  /omgevingsvergunnings/:id} : get the "id" omgevingsvergunning.
     *
     * @param id the id of the omgevingsvergunning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the omgevingsvergunning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Omgevingsvergunning> getOmgevingsvergunning(@PathVariable("id") Long id) {
        log.debug("REST request to get Omgevingsvergunning : {}", id);
        Optional<Omgevingsvergunning> omgevingsvergunning = omgevingsvergunningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(omgevingsvergunning);
    }

    /**
     * {@code DELETE  /omgevingsvergunnings/:id} : delete the "id" omgevingsvergunning.
     *
     * @param id the id of the omgevingsvergunning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOmgevingsvergunning(@PathVariable("id") Long id) {
        log.debug("REST request to delete Omgevingsvergunning : {}", id);
        omgevingsvergunningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
