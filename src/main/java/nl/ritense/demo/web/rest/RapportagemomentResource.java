package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Rapportagemoment;
import nl.ritense.demo.repository.RapportagemomentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Rapportagemoment}.
 */
@RestController
@RequestMapping("/api/rapportagemoments")
@Transactional
public class RapportagemomentResource {

    private final Logger log = LoggerFactory.getLogger(RapportagemomentResource.class);

    private static final String ENTITY_NAME = "rapportagemoment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RapportagemomentRepository rapportagemomentRepository;

    public RapportagemomentResource(RapportagemomentRepository rapportagemomentRepository) {
        this.rapportagemomentRepository = rapportagemomentRepository;
    }

    /**
     * {@code POST  /rapportagemoments} : Create a new rapportagemoment.
     *
     * @param rapportagemoment the rapportagemoment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rapportagemoment, or with status {@code 400 (Bad Request)} if the rapportagemoment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Rapportagemoment> createRapportagemoment(@RequestBody Rapportagemoment rapportagemoment)
        throws URISyntaxException {
        log.debug("REST request to save Rapportagemoment : {}", rapportagemoment);
        if (rapportagemoment.getId() != null) {
            throw new BadRequestAlertException("A new rapportagemoment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rapportagemoment = rapportagemomentRepository.save(rapportagemoment);
        return ResponseEntity.created(new URI("/api/rapportagemoments/" + rapportagemoment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rapportagemoment.getId().toString()))
            .body(rapportagemoment);
    }

    /**
     * {@code PUT  /rapportagemoments/:id} : Updates an existing rapportagemoment.
     *
     * @param id the id of the rapportagemoment to save.
     * @param rapportagemoment the rapportagemoment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rapportagemoment,
     * or with status {@code 400 (Bad Request)} if the rapportagemoment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rapportagemoment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rapportagemoment> updateRapportagemoment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rapportagemoment rapportagemoment
    ) throws URISyntaxException {
        log.debug("REST request to update Rapportagemoment : {}, {}", id, rapportagemoment);
        if (rapportagemoment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rapportagemoment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rapportagemomentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rapportagemoment = rapportagemomentRepository.save(rapportagemoment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rapportagemoment.getId().toString()))
            .body(rapportagemoment);
    }

    /**
     * {@code PATCH  /rapportagemoments/:id} : Partial updates given fields of an existing rapportagemoment, field will ignore if it is null
     *
     * @param id the id of the rapportagemoment to save.
     * @param rapportagemoment the rapportagemoment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rapportagemoment,
     * or with status {@code 400 (Bad Request)} if the rapportagemoment is not valid,
     * or with status {@code 404 (Not Found)} if the rapportagemoment is not found,
     * or with status {@code 500 (Internal Server Error)} if the rapportagemoment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rapportagemoment> partialUpdateRapportagemoment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rapportagemoment rapportagemoment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rapportagemoment partially : {}, {}", id, rapportagemoment);
        if (rapportagemoment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rapportagemoment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rapportagemomentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rapportagemoment> result = rapportagemomentRepository
            .findById(rapportagemoment.getId())
            .map(existingRapportagemoment -> {
                if (rapportagemoment.getDatum() != null) {
                    existingRapportagemoment.setDatum(rapportagemoment.getDatum());
                }
                if (rapportagemoment.getNaam() != null) {
                    existingRapportagemoment.setNaam(rapportagemoment.getNaam());
                }
                if (rapportagemoment.getOmschrijving() != null) {
                    existingRapportagemoment.setOmschrijving(rapportagemoment.getOmschrijving());
                }
                if (rapportagemoment.getTermijn() != null) {
                    existingRapportagemoment.setTermijn(rapportagemoment.getTermijn());
                }

                return existingRapportagemoment;
            })
            .map(rapportagemomentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rapportagemoment.getId().toString())
        );
    }

    /**
     * {@code GET  /rapportagemoments} : get all the rapportagemoments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rapportagemoments in body.
     */
    @GetMapping("")
    public List<Rapportagemoment> getAllRapportagemoments() {
        log.debug("REST request to get all Rapportagemoments");
        return rapportagemomentRepository.findAll();
    }

    /**
     * {@code GET  /rapportagemoments/:id} : get the "id" rapportagemoment.
     *
     * @param id the id of the rapportagemoment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rapportagemoment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rapportagemoment> getRapportagemoment(@PathVariable("id") Long id) {
        log.debug("REST request to get Rapportagemoment : {}", id);
        Optional<Rapportagemoment> rapportagemoment = rapportagemomentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rapportagemoment);
    }

    /**
     * {@code DELETE  /rapportagemoments/:id} : delete the "id" rapportagemoment.
     *
     * @param id the id of the rapportagemoment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRapportagemoment(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rapportagemoment : {}", id);
        rapportagemomentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
