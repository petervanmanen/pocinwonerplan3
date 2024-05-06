package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vastgoedcontract;
import nl.ritense.demo.repository.VastgoedcontractRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vastgoedcontract}.
 */
@RestController
@RequestMapping("/api/vastgoedcontracts")
@Transactional
public class VastgoedcontractResource {

    private final Logger log = LoggerFactory.getLogger(VastgoedcontractResource.class);

    private static final String ENTITY_NAME = "vastgoedcontract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VastgoedcontractRepository vastgoedcontractRepository;

    public VastgoedcontractResource(VastgoedcontractRepository vastgoedcontractRepository) {
        this.vastgoedcontractRepository = vastgoedcontractRepository;
    }

    /**
     * {@code POST  /vastgoedcontracts} : Create a new vastgoedcontract.
     *
     * @param vastgoedcontract the vastgoedcontract to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vastgoedcontract, or with status {@code 400 (Bad Request)} if the vastgoedcontract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vastgoedcontract> createVastgoedcontract(@RequestBody Vastgoedcontract vastgoedcontract)
        throws URISyntaxException {
        log.debug("REST request to save Vastgoedcontract : {}", vastgoedcontract);
        if (vastgoedcontract.getId() != null) {
            throw new BadRequestAlertException("A new vastgoedcontract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vastgoedcontract = vastgoedcontractRepository.save(vastgoedcontract);
        return ResponseEntity.created(new URI("/api/vastgoedcontracts/" + vastgoedcontract.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vastgoedcontract.getId().toString()))
            .body(vastgoedcontract);
    }

    /**
     * {@code PUT  /vastgoedcontracts/:id} : Updates an existing vastgoedcontract.
     *
     * @param id the id of the vastgoedcontract to save.
     * @param vastgoedcontract the vastgoedcontract to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vastgoedcontract,
     * or with status {@code 400 (Bad Request)} if the vastgoedcontract is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vastgoedcontract couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vastgoedcontract> updateVastgoedcontract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vastgoedcontract vastgoedcontract
    ) throws URISyntaxException {
        log.debug("REST request to update Vastgoedcontract : {}, {}", id, vastgoedcontract);
        if (vastgoedcontract.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vastgoedcontract.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vastgoedcontractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vastgoedcontract = vastgoedcontractRepository.save(vastgoedcontract);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vastgoedcontract.getId().toString()))
            .body(vastgoedcontract);
    }

    /**
     * {@code PATCH  /vastgoedcontracts/:id} : Partial updates given fields of an existing vastgoedcontract, field will ignore if it is null
     *
     * @param id the id of the vastgoedcontract to save.
     * @param vastgoedcontract the vastgoedcontract to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vastgoedcontract,
     * or with status {@code 400 (Bad Request)} if the vastgoedcontract is not valid,
     * or with status {@code 404 (Not Found)} if the vastgoedcontract is not found,
     * or with status {@code 500 (Internal Server Error)} if the vastgoedcontract couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vastgoedcontract> partialUpdateVastgoedcontract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vastgoedcontract vastgoedcontract
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vastgoedcontract partially : {}, {}", id, vastgoedcontract);
        if (vastgoedcontract.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vastgoedcontract.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vastgoedcontractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vastgoedcontract> result = vastgoedcontractRepository
            .findById(vastgoedcontract.getId())
            .map(existingVastgoedcontract -> {
                if (vastgoedcontract.getBeschrijving() != null) {
                    existingVastgoedcontract.setBeschrijving(vastgoedcontract.getBeschrijving());
                }
                if (vastgoedcontract.getDatumeinde() != null) {
                    existingVastgoedcontract.setDatumeinde(vastgoedcontract.getDatumeinde());
                }
                if (vastgoedcontract.getDatumstart() != null) {
                    existingVastgoedcontract.setDatumstart(vastgoedcontract.getDatumstart());
                }
                if (vastgoedcontract.getIdentificatie() != null) {
                    existingVastgoedcontract.setIdentificatie(vastgoedcontract.getIdentificatie());
                }
                if (vastgoedcontract.getMaandbedrag() != null) {
                    existingVastgoedcontract.setMaandbedrag(vastgoedcontract.getMaandbedrag());
                }
                if (vastgoedcontract.getOpzegtermijn() != null) {
                    existingVastgoedcontract.setOpzegtermijn(vastgoedcontract.getOpzegtermijn());
                }
                if (vastgoedcontract.getStatus() != null) {
                    existingVastgoedcontract.setStatus(vastgoedcontract.getStatus());
                }
                if (vastgoedcontract.getType() != null) {
                    existingVastgoedcontract.setType(vastgoedcontract.getType());
                }

                return existingVastgoedcontract;
            })
            .map(vastgoedcontractRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vastgoedcontract.getId().toString())
        );
    }

    /**
     * {@code GET  /vastgoedcontracts} : get all the vastgoedcontracts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vastgoedcontracts in body.
     */
    @GetMapping("")
    public List<Vastgoedcontract> getAllVastgoedcontracts() {
        log.debug("REST request to get all Vastgoedcontracts");
        return vastgoedcontractRepository.findAll();
    }

    /**
     * {@code GET  /vastgoedcontracts/:id} : get the "id" vastgoedcontract.
     *
     * @param id the id of the vastgoedcontract to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vastgoedcontract, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vastgoedcontract> getVastgoedcontract(@PathVariable("id") Long id) {
        log.debug("REST request to get Vastgoedcontract : {}", id);
        Optional<Vastgoedcontract> vastgoedcontract = vastgoedcontractRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vastgoedcontract);
    }

    /**
     * {@code DELETE  /vastgoedcontracts/:id} : delete the "id" vastgoedcontract.
     *
     * @param id the id of the vastgoedcontract to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVastgoedcontract(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vastgoedcontract : {}", id);
        vastgoedcontractRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
