package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Offerteaanvraag;
import nl.ritense.demo.repository.OfferteaanvraagRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Offerteaanvraag}.
 */
@RestController
@RequestMapping("/api/offerteaanvraags")
@Transactional
public class OfferteaanvraagResource {

    private final Logger log = LoggerFactory.getLogger(OfferteaanvraagResource.class);

    private static final String ENTITY_NAME = "offerteaanvraag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferteaanvraagRepository offerteaanvraagRepository;

    public OfferteaanvraagResource(OfferteaanvraagRepository offerteaanvraagRepository) {
        this.offerteaanvraagRepository = offerteaanvraagRepository;
    }

    /**
     * {@code POST  /offerteaanvraags} : Create a new offerteaanvraag.
     *
     * @param offerteaanvraag the offerteaanvraag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerteaanvraag, or with status {@code 400 (Bad Request)} if the offerteaanvraag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Offerteaanvraag> createOfferteaanvraag(@RequestBody Offerteaanvraag offerteaanvraag) throws URISyntaxException {
        log.debug("REST request to save Offerteaanvraag : {}", offerteaanvraag);
        if (offerteaanvraag.getId() != null) {
            throw new BadRequestAlertException("A new offerteaanvraag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        offerteaanvraag = offerteaanvraagRepository.save(offerteaanvraag);
        return ResponseEntity.created(new URI("/api/offerteaanvraags/" + offerteaanvraag.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, offerteaanvraag.getId().toString()))
            .body(offerteaanvraag);
    }

    /**
     * {@code PUT  /offerteaanvraags/:id} : Updates an existing offerteaanvraag.
     *
     * @param id the id of the offerteaanvraag to save.
     * @param offerteaanvraag the offerteaanvraag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerteaanvraag,
     * or with status {@code 400 (Bad Request)} if the offerteaanvraag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerteaanvraag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Offerteaanvraag> updateOfferteaanvraag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Offerteaanvraag offerteaanvraag
    ) throws URISyntaxException {
        log.debug("REST request to update Offerteaanvraag : {}, {}", id, offerteaanvraag);
        if (offerteaanvraag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerteaanvraag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerteaanvraagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        offerteaanvraag = offerteaanvraagRepository.save(offerteaanvraag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, offerteaanvraag.getId().toString()))
            .body(offerteaanvraag);
    }

    /**
     * {@code PATCH  /offerteaanvraags/:id} : Partial updates given fields of an existing offerteaanvraag, field will ignore if it is null
     *
     * @param id the id of the offerteaanvraag to save.
     * @param offerteaanvraag the offerteaanvraag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerteaanvraag,
     * or with status {@code 400 (Bad Request)} if the offerteaanvraag is not valid,
     * or with status {@code 404 (Not Found)} if the offerteaanvraag is not found,
     * or with status {@code 500 (Internal Server Error)} if the offerteaanvraag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Offerteaanvraag> partialUpdateOfferteaanvraag(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Offerteaanvraag offerteaanvraag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Offerteaanvraag partially : {}, {}", id, offerteaanvraag);
        if (offerteaanvraag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerteaanvraag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerteaanvraagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Offerteaanvraag> result = offerteaanvraagRepository
            .findById(offerteaanvraag.getId())
            .map(existingOfferteaanvraag -> {
                if (offerteaanvraag.getDatumaanvraag() != null) {
                    existingOfferteaanvraag.setDatumaanvraag(offerteaanvraag.getDatumaanvraag());
                }
                if (offerteaanvraag.getDatumsluiting() != null) {
                    existingOfferteaanvraag.setDatumsluiting(offerteaanvraag.getDatumsluiting());
                }
                if (offerteaanvraag.getNaam() != null) {
                    existingOfferteaanvraag.setNaam(offerteaanvraag.getNaam());
                }
                if (offerteaanvraag.getOmschrijving() != null) {
                    existingOfferteaanvraag.setOmschrijving(offerteaanvraag.getOmschrijving());
                }

                return existingOfferteaanvraag;
            })
            .map(offerteaanvraagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, offerteaanvraag.getId().toString())
        );
    }

    /**
     * {@code GET  /offerteaanvraags} : get all the offerteaanvraags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offerteaanvraags in body.
     */
    @GetMapping("")
    public List<Offerteaanvraag> getAllOfferteaanvraags() {
        log.debug("REST request to get all Offerteaanvraags");
        return offerteaanvraagRepository.findAll();
    }

    /**
     * {@code GET  /offerteaanvraags/:id} : get the "id" offerteaanvraag.
     *
     * @param id the id of the offerteaanvraag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerteaanvraag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Offerteaanvraag> getOfferteaanvraag(@PathVariable("id") Long id) {
        log.debug("REST request to get Offerteaanvraag : {}", id);
        Optional<Offerteaanvraag> offerteaanvraag = offerteaanvraagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(offerteaanvraag);
    }

    /**
     * {@code DELETE  /offerteaanvraags/:id} : delete the "id" offerteaanvraag.
     *
     * @param id the id of the offerteaanvraag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOfferteaanvraag(@PathVariable("id") Long id) {
        log.debug("REST request to delete Offerteaanvraag : {}", id);
        offerteaanvraagRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
