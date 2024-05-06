package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Leverancier;
import nl.ritense.demo.repository.LeverancierRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Leverancier}.
 */
@RestController
@RequestMapping("/api/leveranciers")
@Transactional
public class LeverancierResource {

    private final Logger log = LoggerFactory.getLogger(LeverancierResource.class);

    private static final String ENTITY_NAME = "leverancier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeverancierRepository leverancierRepository;

    public LeverancierResource(LeverancierRepository leverancierRepository) {
        this.leverancierRepository = leverancierRepository;
    }

    /**
     * {@code POST  /leveranciers} : Create a new leverancier.
     *
     * @param leverancier the leverancier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leverancier, or with status {@code 400 (Bad Request)} if the leverancier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Leverancier> createLeverancier(@Valid @RequestBody Leverancier leverancier) throws URISyntaxException {
        log.debug("REST request to save Leverancier : {}", leverancier);
        if (leverancier.getId() != null) {
            throw new BadRequestAlertException("A new leverancier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        leverancier = leverancierRepository.save(leverancier);
        return ResponseEntity.created(new URI("/api/leveranciers/" + leverancier.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, leverancier.getId().toString()))
            .body(leverancier);
    }

    /**
     * {@code PUT  /leveranciers/:id} : Updates an existing leverancier.
     *
     * @param id the id of the leverancier to save.
     * @param leverancier the leverancier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leverancier,
     * or with status {@code 400 (Bad Request)} if the leverancier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leverancier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Leverancier> updateLeverancier(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Leverancier leverancier
    ) throws URISyntaxException {
        log.debug("REST request to update Leverancier : {}, {}", id, leverancier);
        if (leverancier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leverancier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leverancierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        leverancier = leverancierRepository.save(leverancier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leverancier.getId().toString()))
            .body(leverancier);
    }

    /**
     * {@code PATCH  /leveranciers/:id} : Partial updates given fields of an existing leverancier, field will ignore if it is null
     *
     * @param id the id of the leverancier to save.
     * @param leverancier the leverancier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leverancier,
     * or with status {@code 400 (Bad Request)} if the leverancier is not valid,
     * or with status {@code 404 (Not Found)} if the leverancier is not found,
     * or with status {@code 500 (Internal Server Error)} if the leverancier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Leverancier> partialUpdateLeverancier(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Leverancier leverancier
    ) throws URISyntaxException {
        log.debug("REST request to partial update Leverancier partially : {}, {}", id, leverancier);
        if (leverancier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leverancier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leverancierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Leverancier> result = leverancierRepository
            .findById(leverancier.getId())
            .map(existingLeverancier -> {
                if (leverancier.getAgbcode() != null) {
                    existingLeverancier.setAgbcode(leverancier.getAgbcode());
                }
                if (leverancier.getLeverancierscode() != null) {
                    existingLeverancier.setLeverancierscode(leverancier.getLeverancierscode());
                }
                if (leverancier.getNaam() != null) {
                    existingLeverancier.setNaam(leverancier.getNaam());
                }
                if (leverancier.getSoortleverancier() != null) {
                    existingLeverancier.setSoortleverancier(leverancier.getSoortleverancier());
                }
                if (leverancier.getSoortleveranciercode() != null) {
                    existingLeverancier.setSoortleveranciercode(leverancier.getSoortleveranciercode());
                }

                return existingLeverancier;
            })
            .map(leverancierRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leverancier.getId().toString())
        );
    }

    /**
     * {@code GET  /leveranciers} : get all the leveranciers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leveranciers in body.
     */
    @GetMapping("")
    public List<Leverancier> getAllLeveranciers(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Leveranciers");
        if (eagerload) {
            return leverancierRepository.findAllWithEagerRelationships();
        } else {
            return leverancierRepository.findAll();
        }
    }

    /**
     * {@code GET  /leveranciers/:id} : get the "id" leverancier.
     *
     * @param id the id of the leverancier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leverancier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leverancier> getLeverancier(@PathVariable("id") Long id) {
        log.debug("REST request to get Leverancier : {}", id);
        Optional<Leverancier> leverancier = leverancierRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(leverancier);
    }

    /**
     * {@code DELETE  /leveranciers/:id} : delete the "id" leverancier.
     *
     * @param id the id of the leverancier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeverancier(@PathVariable("id") Long id) {
        log.debug("REST request to delete Leverancier : {}", id);
        leverancierRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
