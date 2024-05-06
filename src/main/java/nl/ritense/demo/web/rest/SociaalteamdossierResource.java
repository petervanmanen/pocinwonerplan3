package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sociaalteamdossier;
import nl.ritense.demo.repository.SociaalteamdossierRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sociaalteamdossier}.
 */
@RestController
@RequestMapping("/api/sociaalteamdossiers")
@Transactional
public class SociaalteamdossierResource {

    private final Logger log = LoggerFactory.getLogger(SociaalteamdossierResource.class);

    private static final String ENTITY_NAME = "sociaalteamdossier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SociaalteamdossierRepository sociaalteamdossierRepository;

    public SociaalteamdossierResource(SociaalteamdossierRepository sociaalteamdossierRepository) {
        this.sociaalteamdossierRepository = sociaalteamdossierRepository;
    }

    /**
     * {@code POST  /sociaalteamdossiers} : Create a new sociaalteamdossier.
     *
     * @param sociaalteamdossier the sociaalteamdossier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sociaalteamdossier, or with status {@code 400 (Bad Request)} if the sociaalteamdossier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sociaalteamdossier> createSociaalteamdossier(@Valid @RequestBody Sociaalteamdossier sociaalteamdossier)
        throws URISyntaxException {
        log.debug("REST request to save Sociaalteamdossier : {}", sociaalteamdossier);
        if (sociaalteamdossier.getId() != null) {
            throw new BadRequestAlertException("A new sociaalteamdossier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sociaalteamdossier = sociaalteamdossierRepository.save(sociaalteamdossier);
        return ResponseEntity.created(new URI("/api/sociaalteamdossiers/" + sociaalteamdossier.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sociaalteamdossier.getId().toString()))
            .body(sociaalteamdossier);
    }

    /**
     * {@code PUT  /sociaalteamdossiers/:id} : Updates an existing sociaalteamdossier.
     *
     * @param id the id of the sociaalteamdossier to save.
     * @param sociaalteamdossier the sociaalteamdossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sociaalteamdossier,
     * or with status {@code 400 (Bad Request)} if the sociaalteamdossier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sociaalteamdossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sociaalteamdossier> updateSociaalteamdossier(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sociaalteamdossier sociaalteamdossier
    ) throws URISyntaxException {
        log.debug("REST request to update Sociaalteamdossier : {}, {}", id, sociaalteamdossier);
        if (sociaalteamdossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sociaalteamdossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sociaalteamdossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sociaalteamdossier = sociaalteamdossierRepository.save(sociaalteamdossier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sociaalteamdossier.getId().toString()))
            .body(sociaalteamdossier);
    }

    /**
     * {@code PATCH  /sociaalteamdossiers/:id} : Partial updates given fields of an existing sociaalteamdossier, field will ignore if it is null
     *
     * @param id the id of the sociaalteamdossier to save.
     * @param sociaalteamdossier the sociaalteamdossier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sociaalteamdossier,
     * or with status {@code 400 (Bad Request)} if the sociaalteamdossier is not valid,
     * or with status {@code 404 (Not Found)} if the sociaalteamdossier is not found,
     * or with status {@code 500 (Internal Server Error)} if the sociaalteamdossier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sociaalteamdossier> partialUpdateSociaalteamdossier(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sociaalteamdossier sociaalteamdossier
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sociaalteamdossier partially : {}, {}", id, sociaalteamdossier);
        if (sociaalteamdossier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sociaalteamdossier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sociaalteamdossierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sociaalteamdossier> result = sociaalteamdossierRepository
            .findById(sociaalteamdossier.getId())
            .map(existingSociaalteamdossier -> {
                if (sociaalteamdossier.getDatumeinde() != null) {
                    existingSociaalteamdossier.setDatumeinde(sociaalteamdossier.getDatumeinde());
                }
                if (sociaalteamdossier.getDatumstart() != null) {
                    existingSociaalteamdossier.setDatumstart(sociaalteamdossier.getDatumstart());
                }
                if (sociaalteamdossier.getDatumvaststelling() != null) {
                    existingSociaalteamdossier.setDatumvaststelling(sociaalteamdossier.getDatumvaststelling());
                }
                if (sociaalteamdossier.getOmschrijving() != null) {
                    existingSociaalteamdossier.setOmschrijving(sociaalteamdossier.getOmschrijving());
                }
                if (sociaalteamdossier.getStatus() != null) {
                    existingSociaalteamdossier.setStatus(sociaalteamdossier.getStatus());
                }

                return existingSociaalteamdossier;
            })
            .map(sociaalteamdossierRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sociaalteamdossier.getId().toString())
        );
    }

    /**
     * {@code GET  /sociaalteamdossiers} : get all the sociaalteamdossiers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sociaalteamdossiers in body.
     */
    @GetMapping("")
    public List<Sociaalteamdossier> getAllSociaalteamdossiers() {
        log.debug("REST request to get all Sociaalteamdossiers");
        return sociaalteamdossierRepository.findAll();
    }

    /**
     * {@code GET  /sociaalteamdossiers/:id} : get the "id" sociaalteamdossier.
     *
     * @param id the id of the sociaalteamdossier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sociaalteamdossier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sociaalteamdossier> getSociaalteamdossier(@PathVariable("id") Long id) {
        log.debug("REST request to get Sociaalteamdossier : {}", id);
        Optional<Sociaalteamdossier> sociaalteamdossier = sociaalteamdossierRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sociaalteamdossier);
    }

    /**
     * {@code DELETE  /sociaalteamdossiers/:id} : delete the "id" sociaalteamdossier.
     *
     * @param id the id of the sociaalteamdossier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSociaalteamdossier(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sociaalteamdossier : {}", id);
        sociaalteamdossierRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
