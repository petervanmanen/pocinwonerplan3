package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vreemdeling;
import nl.ritense.demo.repository.VreemdelingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vreemdeling}.
 */
@RestController
@RequestMapping("/api/vreemdelings")
@Transactional
public class VreemdelingResource {

    private final Logger log = LoggerFactory.getLogger(VreemdelingResource.class);

    private static final String ENTITY_NAME = "vreemdeling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VreemdelingRepository vreemdelingRepository;

    public VreemdelingResource(VreemdelingRepository vreemdelingRepository) {
        this.vreemdelingRepository = vreemdelingRepository;
    }

    /**
     * {@code POST  /vreemdelings} : Create a new vreemdeling.
     *
     * @param vreemdeling the vreemdeling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vreemdeling, or with status {@code 400 (Bad Request)} if the vreemdeling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vreemdeling> createVreemdeling(@Valid @RequestBody Vreemdeling vreemdeling) throws URISyntaxException {
        log.debug("REST request to save Vreemdeling : {}", vreemdeling);
        if (vreemdeling.getId() != null) {
            throw new BadRequestAlertException("A new vreemdeling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vreemdeling = vreemdelingRepository.save(vreemdeling);
        return ResponseEntity.created(new URI("/api/vreemdelings/" + vreemdeling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vreemdeling.getId().toString()))
            .body(vreemdeling);
    }

    /**
     * {@code PUT  /vreemdelings/:id} : Updates an existing vreemdeling.
     *
     * @param id the id of the vreemdeling to save.
     * @param vreemdeling the vreemdeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vreemdeling,
     * or with status {@code 400 (Bad Request)} if the vreemdeling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vreemdeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vreemdeling> updateVreemdeling(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vreemdeling vreemdeling
    ) throws URISyntaxException {
        log.debug("REST request to update Vreemdeling : {}, {}", id, vreemdeling);
        if (vreemdeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vreemdeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vreemdelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vreemdeling = vreemdelingRepository.save(vreemdeling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vreemdeling.getId().toString()))
            .body(vreemdeling);
    }

    /**
     * {@code PATCH  /vreemdelings/:id} : Partial updates given fields of an existing vreemdeling, field will ignore if it is null
     *
     * @param id the id of the vreemdeling to save.
     * @param vreemdeling the vreemdeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vreemdeling,
     * or with status {@code 400 (Bad Request)} if the vreemdeling is not valid,
     * or with status {@code 404 (Not Found)} if the vreemdeling is not found,
     * or with status {@code 500 (Internal Server Error)} if the vreemdeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vreemdeling> partialUpdateVreemdeling(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vreemdeling vreemdeling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vreemdeling partially : {}, {}", id, vreemdeling);
        if (vreemdeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vreemdeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vreemdelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vreemdeling> result = vreemdelingRepository
            .findById(vreemdeling.getId())
            .map(existingVreemdeling -> {
                if (vreemdeling.getSociaalreferent() != null) {
                    existingVreemdeling.setSociaalreferent(vreemdeling.getSociaalreferent());
                }
                if (vreemdeling.getVnummer() != null) {
                    existingVreemdeling.setVnummer(vreemdeling.getVnummer());
                }

                return existingVreemdeling;
            })
            .map(vreemdelingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vreemdeling.getId().toString())
        );
    }

    /**
     * {@code GET  /vreemdelings} : get all the vreemdelings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vreemdelings in body.
     */
    @GetMapping("")
    public List<Vreemdeling> getAllVreemdelings() {
        log.debug("REST request to get all Vreemdelings");
        return vreemdelingRepository.findAll();
    }

    /**
     * {@code GET  /vreemdelings/:id} : get the "id" vreemdeling.
     *
     * @param id the id of the vreemdeling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vreemdeling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vreemdeling> getVreemdeling(@PathVariable("id") Long id) {
        log.debug("REST request to get Vreemdeling : {}", id);
        Optional<Vreemdeling> vreemdeling = vreemdelingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vreemdeling);
    }

    /**
     * {@code DELETE  /vreemdelings/:id} : delete the "id" vreemdeling.
     *
     * @param id the id of the vreemdeling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVreemdeling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vreemdeling : {}", id);
        vreemdelingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
