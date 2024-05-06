package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vastgoedcontractregel;
import nl.ritense.demo.repository.VastgoedcontractregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vastgoedcontractregel}.
 */
@RestController
@RequestMapping("/api/vastgoedcontractregels")
@Transactional
public class VastgoedcontractregelResource {

    private final Logger log = LoggerFactory.getLogger(VastgoedcontractregelResource.class);

    private static final String ENTITY_NAME = "vastgoedcontractregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VastgoedcontractregelRepository vastgoedcontractregelRepository;

    public VastgoedcontractregelResource(VastgoedcontractregelRepository vastgoedcontractregelRepository) {
        this.vastgoedcontractregelRepository = vastgoedcontractregelRepository;
    }

    /**
     * {@code POST  /vastgoedcontractregels} : Create a new vastgoedcontractregel.
     *
     * @param vastgoedcontractregel the vastgoedcontractregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vastgoedcontractregel, or with status {@code 400 (Bad Request)} if the vastgoedcontractregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vastgoedcontractregel> createVastgoedcontractregel(@RequestBody Vastgoedcontractregel vastgoedcontractregel)
        throws URISyntaxException {
        log.debug("REST request to save Vastgoedcontractregel : {}", vastgoedcontractregel);
        if (vastgoedcontractregel.getId() != null) {
            throw new BadRequestAlertException("A new vastgoedcontractregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vastgoedcontractregel = vastgoedcontractregelRepository.save(vastgoedcontractregel);
        return ResponseEntity.created(new URI("/api/vastgoedcontractregels/" + vastgoedcontractregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vastgoedcontractregel.getId().toString()))
            .body(vastgoedcontractregel);
    }

    /**
     * {@code PUT  /vastgoedcontractregels/:id} : Updates an existing vastgoedcontractregel.
     *
     * @param id the id of the vastgoedcontractregel to save.
     * @param vastgoedcontractregel the vastgoedcontractregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vastgoedcontractregel,
     * or with status {@code 400 (Bad Request)} if the vastgoedcontractregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vastgoedcontractregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vastgoedcontractregel> updateVastgoedcontractregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vastgoedcontractregel vastgoedcontractregel
    ) throws URISyntaxException {
        log.debug("REST request to update Vastgoedcontractregel : {}, {}", id, vastgoedcontractregel);
        if (vastgoedcontractregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vastgoedcontractregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vastgoedcontractregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vastgoedcontractregel = vastgoedcontractregelRepository.save(vastgoedcontractregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vastgoedcontractregel.getId().toString()))
            .body(vastgoedcontractregel);
    }

    /**
     * {@code PATCH  /vastgoedcontractregels/:id} : Partial updates given fields of an existing vastgoedcontractregel, field will ignore if it is null
     *
     * @param id the id of the vastgoedcontractregel to save.
     * @param vastgoedcontractregel the vastgoedcontractregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vastgoedcontractregel,
     * or with status {@code 400 (Bad Request)} if the vastgoedcontractregel is not valid,
     * or with status {@code 404 (Not Found)} if the vastgoedcontractregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the vastgoedcontractregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vastgoedcontractregel> partialUpdateVastgoedcontractregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vastgoedcontractregel vastgoedcontractregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vastgoedcontractregel partially : {}, {}", id, vastgoedcontractregel);
        if (vastgoedcontractregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vastgoedcontractregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vastgoedcontractregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vastgoedcontractregel> result = vastgoedcontractregelRepository
            .findById(vastgoedcontractregel.getId())
            .map(existingVastgoedcontractregel -> {
                if (vastgoedcontractregel.getBedrag() != null) {
                    existingVastgoedcontractregel.setBedrag(vastgoedcontractregel.getBedrag());
                }
                if (vastgoedcontractregel.getDatumeinde() != null) {
                    existingVastgoedcontractregel.setDatumeinde(vastgoedcontractregel.getDatumeinde());
                }
                if (vastgoedcontractregel.getDatumstart() != null) {
                    existingVastgoedcontractregel.setDatumstart(vastgoedcontractregel.getDatumstart());
                }
                if (vastgoedcontractregel.getFrequentie() != null) {
                    existingVastgoedcontractregel.setFrequentie(vastgoedcontractregel.getFrequentie());
                }
                if (vastgoedcontractregel.getIdentificatie() != null) {
                    existingVastgoedcontractregel.setIdentificatie(vastgoedcontractregel.getIdentificatie());
                }
                if (vastgoedcontractregel.getOmschrijving() != null) {
                    existingVastgoedcontractregel.setOmschrijving(vastgoedcontractregel.getOmschrijving());
                }
                if (vastgoedcontractregel.getStatus() != null) {
                    existingVastgoedcontractregel.setStatus(vastgoedcontractregel.getStatus());
                }
                if (vastgoedcontractregel.getType() != null) {
                    existingVastgoedcontractregel.setType(vastgoedcontractregel.getType());
                }

                return existingVastgoedcontractregel;
            })
            .map(vastgoedcontractregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vastgoedcontractregel.getId().toString())
        );
    }

    /**
     * {@code GET  /vastgoedcontractregels} : get all the vastgoedcontractregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vastgoedcontractregels in body.
     */
    @GetMapping("")
    public List<Vastgoedcontractregel> getAllVastgoedcontractregels() {
        log.debug("REST request to get all Vastgoedcontractregels");
        return vastgoedcontractregelRepository.findAll();
    }

    /**
     * {@code GET  /vastgoedcontractregels/:id} : get the "id" vastgoedcontractregel.
     *
     * @param id the id of the vastgoedcontractregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vastgoedcontractregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vastgoedcontractregel> getVastgoedcontractregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Vastgoedcontractregel : {}", id);
        Optional<Vastgoedcontractregel> vastgoedcontractregel = vastgoedcontractregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vastgoedcontractregel);
    }

    /**
     * {@code DELETE  /vastgoedcontractregels/:id} : delete the "id" vastgoedcontractregel.
     *
     * @param id the id of the vastgoedcontractregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVastgoedcontractregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vastgoedcontractregel : {}", id);
        vastgoedcontractregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
