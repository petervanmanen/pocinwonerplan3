package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Crowmelding;
import nl.ritense.demo.repository.CrowmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Crowmelding}.
 */
@RestController
@RequestMapping("/api/crowmeldings")
@Transactional
public class CrowmeldingResource {

    private final Logger log = LoggerFactory.getLogger(CrowmeldingResource.class);

    private static final String ENTITY_NAME = "crowmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrowmeldingRepository crowmeldingRepository;

    public CrowmeldingResource(CrowmeldingRepository crowmeldingRepository) {
        this.crowmeldingRepository = crowmeldingRepository;
    }

    /**
     * {@code POST  /crowmeldings} : Create a new crowmelding.
     *
     * @param crowmelding the crowmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crowmelding, or with status {@code 400 (Bad Request)} if the crowmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Crowmelding> createCrowmelding(@RequestBody Crowmelding crowmelding) throws URISyntaxException {
        log.debug("REST request to save Crowmelding : {}", crowmelding);
        if (crowmelding.getId() != null) {
            throw new BadRequestAlertException("A new crowmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        crowmelding = crowmeldingRepository.save(crowmelding);
        return ResponseEntity.created(new URI("/api/crowmeldings/" + crowmelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, crowmelding.getId().toString()))
            .body(crowmelding);
    }

    /**
     * {@code PUT  /crowmeldings/:id} : Updates an existing crowmelding.
     *
     * @param id the id of the crowmelding to save.
     * @param crowmelding the crowmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crowmelding,
     * or with status {@code 400 (Bad Request)} if the crowmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crowmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Crowmelding> updateCrowmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Crowmelding crowmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Crowmelding : {}, {}", id, crowmelding);
        if (crowmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crowmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crowmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        crowmelding = crowmeldingRepository.save(crowmelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, crowmelding.getId().toString()))
            .body(crowmelding);
    }

    /**
     * {@code PATCH  /crowmeldings/:id} : Partial updates given fields of an existing crowmelding, field will ignore if it is null
     *
     * @param id the id of the crowmelding to save.
     * @param crowmelding the crowmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crowmelding,
     * or with status {@code 400 (Bad Request)} if the crowmelding is not valid,
     * or with status {@code 404 (Not Found)} if the crowmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the crowmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Crowmelding> partialUpdateCrowmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Crowmelding crowmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Crowmelding partially : {}, {}", id, crowmelding);
        if (crowmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crowmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crowmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Crowmelding> result = crowmeldingRepository
            .findById(crowmelding.getId())
            .map(existingCrowmelding -> {
                if (crowmelding.getKwaliteitsniveau() != null) {
                    existingCrowmelding.setKwaliteitsniveau(crowmelding.getKwaliteitsniveau());
                }

                return existingCrowmelding;
            })
            .map(crowmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, crowmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /crowmeldings} : get all the crowmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crowmeldings in body.
     */
    @GetMapping("")
    public List<Crowmelding> getAllCrowmeldings() {
        log.debug("REST request to get all Crowmeldings");
        return crowmeldingRepository.findAll();
    }

    /**
     * {@code GET  /crowmeldings/:id} : get the "id" crowmelding.
     *
     * @param id the id of the crowmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crowmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Crowmelding> getCrowmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Crowmelding : {}", id);
        Optional<Crowmelding> crowmelding = crowmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(crowmelding);
    }

    /**
     * {@code DELETE  /crowmeldings/:id} : delete the "id" crowmelding.
     *
     * @param id the id of the crowmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrowmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Crowmelding : {}", id);
        crowmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
