package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Behandeling;
import nl.ritense.demo.repository.BehandelingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Behandeling}.
 */
@RestController
@RequestMapping("/api/behandelings")
@Transactional
public class BehandelingResource {

    private final Logger log = LoggerFactory.getLogger(BehandelingResource.class);

    private static final String ENTITY_NAME = "behandeling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BehandelingRepository behandelingRepository;

    public BehandelingResource(BehandelingRepository behandelingRepository) {
        this.behandelingRepository = behandelingRepository;
    }

    /**
     * {@code POST  /behandelings} : Create a new behandeling.
     *
     * @param behandeling the behandeling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new behandeling, or with status {@code 400 (Bad Request)} if the behandeling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Behandeling> createBehandeling(@RequestBody Behandeling behandeling) throws URISyntaxException {
        log.debug("REST request to save Behandeling : {}", behandeling);
        if (behandeling.getId() != null) {
            throw new BadRequestAlertException("A new behandeling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        behandeling = behandelingRepository.save(behandeling);
        return ResponseEntity.created(new URI("/api/behandelings/" + behandeling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, behandeling.getId().toString()))
            .body(behandeling);
    }

    /**
     * {@code PUT  /behandelings/:id} : Updates an existing behandeling.
     *
     * @param id the id of the behandeling to save.
     * @param behandeling the behandeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated behandeling,
     * or with status {@code 400 (Bad Request)} if the behandeling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the behandeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Behandeling> updateBehandeling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Behandeling behandeling
    ) throws URISyntaxException {
        log.debug("REST request to update Behandeling : {}, {}", id, behandeling);
        if (behandeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, behandeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!behandelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        behandeling = behandelingRepository.save(behandeling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, behandeling.getId().toString()))
            .body(behandeling);
    }

    /**
     * {@code PATCH  /behandelings/:id} : Partial updates given fields of an existing behandeling, field will ignore if it is null
     *
     * @param id the id of the behandeling to save.
     * @param behandeling the behandeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated behandeling,
     * or with status {@code 400 (Bad Request)} if the behandeling is not valid,
     * or with status {@code 404 (Not Found)} if the behandeling is not found,
     * or with status {@code 500 (Internal Server Error)} if the behandeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Behandeling> partialUpdateBehandeling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Behandeling behandeling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Behandeling partially : {}, {}", id, behandeling);
        if (behandeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, behandeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!behandelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Behandeling> result = behandelingRepository
            .findById(behandeling.getId())
            .map(existingBehandeling -> {
                if (behandeling.getDatumeinde() != null) {
                    existingBehandeling.setDatumeinde(behandeling.getDatumeinde());
                }
                if (behandeling.getDatumstart() != null) {
                    existingBehandeling.setDatumstart(behandeling.getDatumstart());
                }
                if (behandeling.getToelichting() != null) {
                    existingBehandeling.setToelichting(behandeling.getToelichting());
                }

                return existingBehandeling;
            })
            .map(behandelingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, behandeling.getId().toString())
        );
    }

    /**
     * {@code GET  /behandelings} : get all the behandelings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of behandelings in body.
     */
    @GetMapping("")
    public List<Behandeling> getAllBehandelings() {
        log.debug("REST request to get all Behandelings");
        return behandelingRepository.findAll();
    }

    /**
     * {@code GET  /behandelings/:id} : get the "id" behandeling.
     *
     * @param id the id of the behandeling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the behandeling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Behandeling> getBehandeling(@PathVariable("id") Long id) {
        log.debug("REST request to get Behandeling : {}", id);
        Optional<Behandeling> behandeling = behandelingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(behandeling);
    }

    /**
     * {@code DELETE  /behandelings/:id} : delete the "id" behandeling.
     *
     * @param id the id of the behandeling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBehandeling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Behandeling : {}", id);
        behandelingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
