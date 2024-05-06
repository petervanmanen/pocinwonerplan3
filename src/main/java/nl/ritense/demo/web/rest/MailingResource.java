package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Mailing;
import nl.ritense.demo.repository.MailingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Mailing}.
 */
@RestController
@RequestMapping("/api/mailings")
@Transactional
public class MailingResource {

    private final Logger log = LoggerFactory.getLogger(MailingResource.class);

    private static final String ENTITY_NAME = "mailing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MailingRepository mailingRepository;

    public MailingResource(MailingRepository mailingRepository) {
        this.mailingRepository = mailingRepository;
    }

    /**
     * {@code POST  /mailings} : Create a new mailing.
     *
     * @param mailing the mailing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mailing, or with status {@code 400 (Bad Request)} if the mailing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Mailing> createMailing(@RequestBody Mailing mailing) throws URISyntaxException {
        log.debug("REST request to save Mailing : {}", mailing);
        if (mailing.getId() != null) {
            throw new BadRequestAlertException("A new mailing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mailing = mailingRepository.save(mailing);
        return ResponseEntity.created(new URI("/api/mailings/" + mailing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, mailing.getId().toString()))
            .body(mailing);
    }

    /**
     * {@code PUT  /mailings/:id} : Updates an existing mailing.
     *
     * @param id the id of the mailing to save.
     * @param mailing the mailing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mailing,
     * or with status {@code 400 (Bad Request)} if the mailing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mailing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mailing> updateMailing(@PathVariable(value = "id", required = false) final Long id, @RequestBody Mailing mailing)
        throws URISyntaxException {
        log.debug("REST request to update Mailing : {}, {}", id, mailing);
        if (mailing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mailing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mailingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mailing = mailingRepository.save(mailing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mailing.getId().toString()))
            .body(mailing);
    }

    /**
     * {@code PATCH  /mailings/:id} : Partial updates given fields of an existing mailing, field will ignore if it is null
     *
     * @param id the id of the mailing to save.
     * @param mailing the mailing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mailing,
     * or with status {@code 400 (Bad Request)} if the mailing is not valid,
     * or with status {@code 404 (Not Found)} if the mailing is not found,
     * or with status {@code 500 (Internal Server Error)} if the mailing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mailing> partialUpdateMailing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Mailing mailing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mailing partially : {}, {}", id, mailing);
        if (mailing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mailing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mailingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mailing> result = mailingRepository
            .findById(mailing.getId())
            .map(existingMailing -> {
                if (mailing.getDatum() != null) {
                    existingMailing.setDatum(mailing.getDatum());
                }
                if (mailing.getNaam() != null) {
                    existingMailing.setNaam(mailing.getNaam());
                }
                if (mailing.getOmschrijving() != null) {
                    existingMailing.setOmschrijving(mailing.getOmschrijving());
                }

                return existingMailing;
            })
            .map(mailingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mailing.getId().toString())
        );
    }

    /**
     * {@code GET  /mailings} : get all the mailings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mailings in body.
     */
    @GetMapping("")
    public List<Mailing> getAllMailings(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Mailings");
        if (eagerload) {
            return mailingRepository.findAllWithEagerRelationships();
        } else {
            return mailingRepository.findAll();
        }
    }

    /**
     * {@code GET  /mailings/:id} : get the "id" mailing.
     *
     * @param id the id of the mailing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mailing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mailing> getMailing(@PathVariable("id") Long id) {
        log.debug("REST request to get Mailing : {}", id);
        Optional<Mailing> mailing = mailingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(mailing);
    }

    /**
     * {@code DELETE  /mailings/:id} : delete the "id" mailing.
     *
     * @param id the id of the mailing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMailing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Mailing : {}", id);
        mailingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
