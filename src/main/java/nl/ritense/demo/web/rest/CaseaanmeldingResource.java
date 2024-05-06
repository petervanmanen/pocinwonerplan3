package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Caseaanmelding;
import nl.ritense.demo.repository.CaseaanmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Caseaanmelding}.
 */
@RestController
@RequestMapping("/api/caseaanmeldings")
@Transactional
public class CaseaanmeldingResource {

    private final Logger log = LoggerFactory.getLogger(CaseaanmeldingResource.class);

    private static final String ENTITY_NAME = "caseaanmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaseaanmeldingRepository caseaanmeldingRepository;

    public CaseaanmeldingResource(CaseaanmeldingRepository caseaanmeldingRepository) {
        this.caseaanmeldingRepository = caseaanmeldingRepository;
    }

    /**
     * {@code POST  /caseaanmeldings} : Create a new caseaanmelding.
     *
     * @param caseaanmelding the caseaanmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caseaanmelding, or with status {@code 400 (Bad Request)} if the caseaanmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Caseaanmelding> createCaseaanmelding(@RequestBody Caseaanmelding caseaanmelding) throws URISyntaxException {
        log.debug("REST request to save Caseaanmelding : {}", caseaanmelding);
        if (caseaanmelding.getId() != null) {
            throw new BadRequestAlertException("A new caseaanmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        caseaanmelding = caseaanmeldingRepository.save(caseaanmelding);
        return ResponseEntity.created(new URI("/api/caseaanmeldings/" + caseaanmelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, caseaanmelding.getId().toString()))
            .body(caseaanmelding);
    }

    /**
     * {@code PUT  /caseaanmeldings/:id} : Updates an existing caseaanmelding.
     *
     * @param id the id of the caseaanmelding to save.
     * @param caseaanmelding the caseaanmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caseaanmelding,
     * or with status {@code 400 (Bad Request)} if the caseaanmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caseaanmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Caseaanmelding> updateCaseaanmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Caseaanmelding caseaanmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Caseaanmelding : {}, {}", id, caseaanmelding);
        if (caseaanmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caseaanmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caseaanmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        caseaanmelding = caseaanmeldingRepository.save(caseaanmelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, caseaanmelding.getId().toString()))
            .body(caseaanmelding);
    }

    /**
     * {@code PATCH  /caseaanmeldings/:id} : Partial updates given fields of an existing caseaanmelding, field will ignore if it is null
     *
     * @param id the id of the caseaanmelding to save.
     * @param caseaanmelding the caseaanmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caseaanmelding,
     * or with status {@code 400 (Bad Request)} if the caseaanmelding is not valid,
     * or with status {@code 404 (Not Found)} if the caseaanmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the caseaanmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Caseaanmelding> partialUpdateCaseaanmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Caseaanmelding caseaanmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Caseaanmelding partially : {}, {}", id, caseaanmelding);
        if (caseaanmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caseaanmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caseaanmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Caseaanmelding> result = caseaanmeldingRepository
            .findById(caseaanmelding.getId())
            .map(existingCaseaanmelding -> {
                if (caseaanmelding.getDatum() != null) {
                    existingCaseaanmelding.setDatum(caseaanmelding.getDatum());
                }

                return existingCaseaanmelding;
            })
            .map(caseaanmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, caseaanmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /caseaanmeldings} : get all the caseaanmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caseaanmeldings in body.
     */
    @GetMapping("")
    public List<Caseaanmelding> getAllCaseaanmeldings() {
        log.debug("REST request to get all Caseaanmeldings");
        return caseaanmeldingRepository.findAll();
    }

    /**
     * {@code GET  /caseaanmeldings/:id} : get the "id" caseaanmelding.
     *
     * @param id the id of the caseaanmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caseaanmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Caseaanmelding> getCaseaanmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Caseaanmelding : {}", id);
        Optional<Caseaanmelding> caseaanmelding = caseaanmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(caseaanmelding);
    }

    /**
     * {@code DELETE  /caseaanmeldings/:id} : delete the "id" caseaanmelding.
     *
     * @param id the id of the caseaanmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaseaanmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Caseaanmelding : {}", id);
        caseaanmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
