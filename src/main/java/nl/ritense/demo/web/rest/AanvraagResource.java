package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aanvraag;
import nl.ritense.demo.repository.AanvraagRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanvraag}.
 */
@RestController
@RequestMapping("/api/aanvraags")
@Transactional
public class AanvraagResource {

    private final Logger log = LoggerFactory.getLogger(AanvraagResource.class);

    private static final String ENTITY_NAME = "aanvraag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanvraagRepository aanvraagRepository;

    public AanvraagResource(AanvraagRepository aanvraagRepository) {
        this.aanvraagRepository = aanvraagRepository;
    }

    /**
     * {@code POST  /aanvraags} : Create a new aanvraag.
     *
     * @param aanvraag the aanvraag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanvraag, or with status {@code 400 (Bad Request)} if the aanvraag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanvraag> createAanvraag(@RequestBody Aanvraag aanvraag) throws URISyntaxException {
        log.debug("REST request to save Aanvraag : {}", aanvraag);
        if (aanvraag.getId() != null) {
            throw new BadRequestAlertException("A new aanvraag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanvraag = aanvraagRepository.save(aanvraag);
        return ResponseEntity.created(new URI("/api/aanvraags/" + aanvraag.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanvraag.getId().toString()))
            .body(aanvraag);
    }

    /**
     * {@code PUT  /aanvraags/:id} : Updates an existing aanvraag.
     *
     * @param id the id of the aanvraag to save.
     * @param aanvraag the aanvraag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraag,
     * or with status {@code 400 (Bad Request)} if the aanvraag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aanvraag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aanvraag> updateAanvraag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraag aanvraag
    ) throws URISyntaxException {
        log.debug("REST request to update Aanvraag : {}, {}", id, aanvraag);
        if (aanvraag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aanvraag = aanvraagRepository.save(aanvraag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraag.getId().toString()))
            .body(aanvraag);
    }

    /**
     * {@code PATCH  /aanvraags/:id} : Partial updates given fields of an existing aanvraag, field will ignore if it is null
     *
     * @param id the id of the aanvraag to save.
     * @param aanvraag the aanvraag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraag,
     * or with status {@code 400 (Bad Request)} if the aanvraag is not valid,
     * or with status {@code 404 (Not Found)} if the aanvraag is not found,
     * or with status {@code 500 (Internal Server Error)} if the aanvraag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aanvraag> partialUpdateAanvraag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraag aanvraag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aanvraag partially : {}, {}", id, aanvraag);
        if (aanvraag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aanvraag> result = aanvraagRepository
            .findById(aanvraag.getId())
            .map(existingAanvraag -> {
                if (aanvraag.getDatumtijd() != null) {
                    existingAanvraag.setDatumtijd(aanvraag.getDatumtijd());
                }

                return existingAanvraag;
            })
            .map(aanvraagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraag.getId().toString())
        );
    }

    /**
     * {@code GET  /aanvraags} : get all the aanvraags.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanvraags in body.
     */
    @GetMapping("")
    public List<Aanvraag> getAllAanvraags(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Aanvraags");
        if (eagerload) {
            return aanvraagRepository.findAllWithEagerRelationships();
        } else {
            return aanvraagRepository.findAll();
        }
    }

    /**
     * {@code GET  /aanvraags/:id} : get the "id" aanvraag.
     *
     * @param id the id of the aanvraag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanvraag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanvraag> getAanvraag(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanvraag : {}", id);
        Optional<Aanvraag> aanvraag = aanvraagRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(aanvraag);
    }

    /**
     * {@code DELETE  /aanvraags/:id} : delete the "id" aanvraag.
     *
     * @param id the id of the aanvraag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanvraag(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanvraag : {}", id);
        aanvraagRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
