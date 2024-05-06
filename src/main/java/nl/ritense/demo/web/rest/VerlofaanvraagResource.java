package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verlofaanvraag;
import nl.ritense.demo.repository.VerlofaanvraagRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verlofaanvraag}.
 */
@RestController
@RequestMapping("/api/verlofaanvraags")
@Transactional
public class VerlofaanvraagResource {

    private final Logger log = LoggerFactory.getLogger(VerlofaanvraagResource.class);

    private static final String ENTITY_NAME = "verlofaanvraag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerlofaanvraagRepository verlofaanvraagRepository;

    public VerlofaanvraagResource(VerlofaanvraagRepository verlofaanvraagRepository) {
        this.verlofaanvraagRepository = verlofaanvraagRepository;
    }

    /**
     * {@code POST  /verlofaanvraags} : Create a new verlofaanvraag.
     *
     * @param verlofaanvraag the verlofaanvraag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verlofaanvraag, or with status {@code 400 (Bad Request)} if the verlofaanvraag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verlofaanvraag> createVerlofaanvraag(@RequestBody Verlofaanvraag verlofaanvraag) throws URISyntaxException {
        log.debug("REST request to save Verlofaanvraag : {}", verlofaanvraag);
        if (verlofaanvraag.getId() != null) {
            throw new BadRequestAlertException("A new verlofaanvraag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verlofaanvraag = verlofaanvraagRepository.save(verlofaanvraag);
        return ResponseEntity.created(new URI("/api/verlofaanvraags/" + verlofaanvraag.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verlofaanvraag.getId().toString()))
            .body(verlofaanvraag);
    }

    /**
     * {@code PUT  /verlofaanvraags/:id} : Updates an existing verlofaanvraag.
     *
     * @param id the id of the verlofaanvraag to save.
     * @param verlofaanvraag the verlofaanvraag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verlofaanvraag,
     * or with status {@code 400 (Bad Request)} if the verlofaanvraag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verlofaanvraag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verlofaanvraag> updateVerlofaanvraag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verlofaanvraag verlofaanvraag
    ) throws URISyntaxException {
        log.debug("REST request to update Verlofaanvraag : {}, {}", id, verlofaanvraag);
        if (verlofaanvraag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verlofaanvraag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verlofaanvraagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verlofaanvraag = verlofaanvraagRepository.save(verlofaanvraag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verlofaanvraag.getId().toString()))
            .body(verlofaanvraag);
    }

    /**
     * {@code PATCH  /verlofaanvraags/:id} : Partial updates given fields of an existing verlofaanvraag, field will ignore if it is null
     *
     * @param id the id of the verlofaanvraag to save.
     * @param verlofaanvraag the verlofaanvraag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verlofaanvraag,
     * or with status {@code 400 (Bad Request)} if the verlofaanvraag is not valid,
     * or with status {@code 404 (Not Found)} if the verlofaanvraag is not found,
     * or with status {@code 500 (Internal Server Error)} if the verlofaanvraag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verlofaanvraag> partialUpdateVerlofaanvraag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verlofaanvraag verlofaanvraag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verlofaanvraag partially : {}, {}", id, verlofaanvraag);
        if (verlofaanvraag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verlofaanvraag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verlofaanvraagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verlofaanvraag> result = verlofaanvraagRepository
            .findById(verlofaanvraag.getId())
            .map(existingVerlofaanvraag -> {
                if (verlofaanvraag.getDatumstart() != null) {
                    existingVerlofaanvraag.setDatumstart(verlofaanvraag.getDatumstart());
                }
                if (verlofaanvraag.getDatumtot() != null) {
                    existingVerlofaanvraag.setDatumtot(verlofaanvraag.getDatumtot());
                }
                if (verlofaanvraag.getSoortverlof() != null) {
                    existingVerlofaanvraag.setSoortverlof(verlofaanvraag.getSoortverlof());
                }

                return existingVerlofaanvraag;
            })
            .map(verlofaanvraagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verlofaanvraag.getId().toString())
        );
    }

    /**
     * {@code GET  /verlofaanvraags} : get all the verlofaanvraags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verlofaanvraags in body.
     */
    @GetMapping("")
    public List<Verlofaanvraag> getAllVerlofaanvraags() {
        log.debug("REST request to get all Verlofaanvraags");
        return verlofaanvraagRepository.findAll();
    }

    /**
     * {@code GET  /verlofaanvraags/:id} : get the "id" verlofaanvraag.
     *
     * @param id the id of the verlofaanvraag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verlofaanvraag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verlofaanvraag> getVerlofaanvraag(@PathVariable("id") Long id) {
        log.debug("REST request to get Verlofaanvraag : {}", id);
        Optional<Verlofaanvraag> verlofaanvraag = verlofaanvraagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verlofaanvraag);
    }

    /**
     * {@code DELETE  /verlofaanvraags/:id} : delete the "id" verlofaanvraag.
     *
     * @param id the id of the verlofaanvraag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerlofaanvraag(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verlofaanvraag : {}", id);
        verlofaanvraagRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
