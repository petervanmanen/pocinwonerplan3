package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aanvraagofmelding;
import nl.ritense.demo.repository.AanvraagofmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanvraagofmelding}.
 */
@RestController
@RequestMapping("/api/aanvraagofmeldings")
@Transactional
public class AanvraagofmeldingResource {

    private final Logger log = LoggerFactory.getLogger(AanvraagofmeldingResource.class);

    private static final String ENTITY_NAME = "aanvraagofmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanvraagofmeldingRepository aanvraagofmeldingRepository;

    public AanvraagofmeldingResource(AanvraagofmeldingRepository aanvraagofmeldingRepository) {
        this.aanvraagofmeldingRepository = aanvraagofmeldingRepository;
    }

    /**
     * {@code POST  /aanvraagofmeldings} : Create a new aanvraagofmelding.
     *
     * @param aanvraagofmelding the aanvraagofmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanvraagofmelding, or with status {@code 400 (Bad Request)} if the aanvraagofmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanvraagofmelding> createAanvraagofmelding(@RequestBody Aanvraagofmelding aanvraagofmelding)
        throws URISyntaxException {
        log.debug("REST request to save Aanvraagofmelding : {}", aanvraagofmelding);
        if (aanvraagofmelding.getId() != null) {
            throw new BadRequestAlertException("A new aanvraagofmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanvraagofmelding = aanvraagofmeldingRepository.save(aanvraagofmelding);
        return ResponseEntity.created(new URI("/api/aanvraagofmeldings/" + aanvraagofmelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanvraagofmelding.getId().toString()))
            .body(aanvraagofmelding);
    }

    /**
     * {@code PUT  /aanvraagofmeldings/:id} : Updates an existing aanvraagofmelding.
     *
     * @param id the id of the aanvraagofmelding to save.
     * @param aanvraagofmelding the aanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the aanvraagofmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aanvraagofmelding> updateAanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraagofmelding aanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Aanvraagofmelding : {}, {}", id, aanvraagofmelding);
        if (aanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aanvraagofmelding = aanvraagofmeldingRepository.save(aanvraagofmelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraagofmelding.getId().toString()))
            .body(aanvraagofmelding);
    }

    /**
     * {@code PATCH  /aanvraagofmeldings/:id} : Partial updates given fields of an existing aanvraagofmelding, field will ignore if it is null
     *
     * @param id the id of the aanvraagofmelding to save.
     * @param aanvraagofmelding the aanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the aanvraagofmelding is not valid,
     * or with status {@code 404 (Not Found)} if the aanvraagofmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the aanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aanvraagofmelding> partialUpdateAanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraagofmelding aanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aanvraagofmelding partially : {}, {}", id, aanvraagofmelding);
        if (aanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aanvraagofmelding> result = aanvraagofmeldingRepository
            .findById(aanvraagofmelding.getId())
            .map(existingAanvraagofmelding -> {
                if (aanvraagofmelding.getDatum() != null) {
                    existingAanvraagofmelding.setDatum(aanvraagofmelding.getDatum());
                }
                if (aanvraagofmelding.getOpmerkingen() != null) {
                    existingAanvraagofmelding.setOpmerkingen(aanvraagofmelding.getOpmerkingen());
                }
                if (aanvraagofmelding.getReden() != null) {
                    existingAanvraagofmelding.setReden(aanvraagofmelding.getReden());
                }
                if (aanvraagofmelding.getSoortverzuimofaanvraag() != null) {
                    existingAanvraagofmelding.setSoortverzuimofaanvraag(aanvraagofmelding.getSoortverzuimofaanvraag());
                }

                return existingAanvraagofmelding;
            })
            .map(aanvraagofmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraagofmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /aanvraagofmeldings} : get all the aanvraagofmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanvraagofmeldings in body.
     */
    @GetMapping("")
    public List<Aanvraagofmelding> getAllAanvraagofmeldings() {
        log.debug("REST request to get all Aanvraagofmeldings");
        return aanvraagofmeldingRepository.findAll();
    }

    /**
     * {@code GET  /aanvraagofmeldings/:id} : get the "id" aanvraagofmelding.
     *
     * @param id the id of the aanvraagofmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanvraagofmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanvraagofmelding> getAanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanvraagofmelding : {}", id);
        Optional<Aanvraagofmelding> aanvraagofmelding = aanvraagofmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanvraagofmelding);
    }

    /**
     * {@code DELETE  /aanvraagofmeldings/:id} : delete the "id" aanvraagofmelding.
     *
     * @param id the id of the aanvraagofmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanvraagofmelding : {}", id);
        aanvraagofmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
