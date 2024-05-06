package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Epackage;
import nl.ritense.demo.repository.EpackageRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Epackage}.
 */
@RestController
@RequestMapping("/api/epackages")
@Transactional
public class EpackageResource {

    private final Logger log = LoggerFactory.getLogger(EpackageResource.class);

    private static final String ENTITY_NAME = "epackage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EpackageRepository epackageRepository;

    public EpackageResource(EpackageRepository epackageRepository) {
        this.epackageRepository = epackageRepository;
    }

    /**
     * {@code POST  /epackages} : Create a new epackage.
     *
     * @param epackage the epackage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new epackage, or with status {@code 400 (Bad Request)} if the epackage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Epackage> createEpackage(@RequestBody Epackage epackage) throws URISyntaxException {
        log.debug("REST request to save Epackage : {}", epackage);
        if (epackage.getId() != null) {
            throw new BadRequestAlertException("A new epackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        epackage = epackageRepository.save(epackage);
        return ResponseEntity.created(new URI("/api/epackages/" + epackage.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, epackage.getId().toString()))
            .body(epackage);
    }

    /**
     * {@code PUT  /epackages/:id} : Updates an existing epackage.
     *
     * @param id the id of the epackage to save.
     * @param epackage the epackage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epackage,
     * or with status {@code 400 (Bad Request)} if the epackage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the epackage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Epackage> updateEpackage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Epackage epackage
    ) throws URISyntaxException {
        log.debug("REST request to update Epackage : {}, {}", id, epackage);
        if (epackage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epackage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!epackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        epackage = epackageRepository.save(epackage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, epackage.getId().toString()))
            .body(epackage);
    }

    /**
     * {@code PATCH  /epackages/:id} : Partial updates given fields of an existing epackage, field will ignore if it is null
     *
     * @param id the id of the epackage to save.
     * @param epackage the epackage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated epackage,
     * or with status {@code 400 (Bad Request)} if the epackage is not valid,
     * or with status {@code 404 (Not Found)} if the epackage is not found,
     * or with status {@code 500 (Internal Server Error)} if the epackage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Epackage> partialUpdateEpackage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Epackage epackage
    ) throws URISyntaxException {
        log.debug("REST request to partial update Epackage partially : {}, {}", id, epackage);
        if (epackage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, epackage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!epackageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Epackage> result = epackageRepository
            .findById(epackage.getId())
            .map(existingEpackage -> {
                if (epackage.getNaam() != null) {
                    existingEpackage.setNaam(epackage.getNaam());
                }
                if (epackage.getProces() != null) {
                    existingEpackage.setProces(epackage.getProces());
                }
                if (epackage.getProject() != null) {
                    existingEpackage.setProject(epackage.getProject());
                }
                if (epackage.getStatus() != null) {
                    existingEpackage.setStatus(epackage.getStatus());
                }
                if (epackage.getToelichting() != null) {
                    existingEpackage.setToelichting(epackage.getToelichting());
                }

                return existingEpackage;
            })
            .map(epackageRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, epackage.getId().toString())
        );
    }

    /**
     * {@code GET  /epackages} : get all the epackages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of epackages in body.
     */
    @GetMapping("")
    public List<Epackage> getAllEpackages() {
        log.debug("REST request to get all Epackages");
        return epackageRepository.findAll();
    }

    /**
     * {@code GET  /epackages/:id} : get the "id" epackage.
     *
     * @param id the id of the epackage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the epackage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Epackage> getEpackage(@PathVariable("id") Long id) {
        log.debug("REST request to get Epackage : {}", id);
        Optional<Epackage> epackage = epackageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(epackage);
    }

    /**
     * {@code DELETE  /epackages/:id} : delete the "id" epackage.
     *
     * @param id the id of the epackage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpackage(@PathVariable("id") Long id) {
        log.debug("REST request to delete Epackage : {}", id);
        epackageRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
