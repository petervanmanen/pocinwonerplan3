package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Factuurregel;
import nl.ritense.demo.repository.FactuurregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Factuurregel}.
 */
@RestController
@RequestMapping("/api/factuurregels")
@Transactional
public class FactuurregelResource {

    private final Logger log = LoggerFactory.getLogger(FactuurregelResource.class);

    private static final String ENTITY_NAME = "factuurregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactuurregelRepository factuurregelRepository;

    public FactuurregelResource(FactuurregelRepository factuurregelRepository) {
        this.factuurregelRepository = factuurregelRepository;
    }

    /**
     * {@code POST  /factuurregels} : Create a new factuurregel.
     *
     * @param factuurregel the factuurregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factuurregel, or with status {@code 400 (Bad Request)} if the factuurregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Factuurregel> createFactuurregel(@Valid @RequestBody Factuurregel factuurregel) throws URISyntaxException {
        log.debug("REST request to save Factuurregel : {}", factuurregel);
        if (factuurregel.getId() != null) {
            throw new BadRequestAlertException("A new factuurregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        factuurregel = factuurregelRepository.save(factuurregel);
        return ResponseEntity.created(new URI("/api/factuurregels/" + factuurregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, factuurregel.getId().toString()))
            .body(factuurregel);
    }

    /**
     * {@code PUT  /factuurregels/:id} : Updates an existing factuurregel.
     *
     * @param id the id of the factuurregel to save.
     * @param factuurregel the factuurregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factuurregel,
     * or with status {@code 400 (Bad Request)} if the factuurregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factuurregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Factuurregel> updateFactuurregel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Factuurregel factuurregel
    ) throws URISyntaxException {
        log.debug("REST request to update Factuurregel : {}, {}", id, factuurregel);
        if (factuurregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factuurregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factuurregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        factuurregel = factuurregelRepository.save(factuurregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factuurregel.getId().toString()))
            .body(factuurregel);
    }

    /**
     * {@code PATCH  /factuurregels/:id} : Partial updates given fields of an existing factuurregel, field will ignore if it is null
     *
     * @param id the id of the factuurregel to save.
     * @param factuurregel the factuurregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factuurregel,
     * or with status {@code 400 (Bad Request)} if the factuurregel is not valid,
     * or with status {@code 404 (Not Found)} if the factuurregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the factuurregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Factuurregel> partialUpdateFactuurregel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Factuurregel factuurregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Factuurregel partially : {}, {}", id, factuurregel);
        if (factuurregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factuurregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factuurregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Factuurregel> result = factuurregelRepository
            .findById(factuurregel.getId())
            .map(existingFactuurregel -> {
                if (factuurregel.getAantal() != null) {
                    existingFactuurregel.setAantal(factuurregel.getAantal());
                }
                if (factuurregel.getBedragbtw() != null) {
                    existingFactuurregel.setBedragbtw(factuurregel.getBedragbtw());
                }
                if (factuurregel.getBedragexbtw() != null) {
                    existingFactuurregel.setBedragexbtw(factuurregel.getBedragexbtw());
                }
                if (factuurregel.getBtwpercentage() != null) {
                    existingFactuurregel.setBtwpercentage(factuurregel.getBtwpercentage());
                }
                if (factuurregel.getNummer() != null) {
                    existingFactuurregel.setNummer(factuurregel.getNummer());
                }
                if (factuurregel.getOmschrijving() != null) {
                    existingFactuurregel.setOmschrijving(factuurregel.getOmschrijving());
                }

                return existingFactuurregel;
            })
            .map(factuurregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factuurregel.getId().toString())
        );
    }

    /**
     * {@code GET  /factuurregels} : get all the factuurregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factuurregels in body.
     */
    @GetMapping("")
    public List<Factuurregel> getAllFactuurregels() {
        log.debug("REST request to get all Factuurregels");
        return factuurregelRepository.findAll();
    }

    /**
     * {@code GET  /factuurregels/:id} : get the "id" factuurregel.
     *
     * @param id the id of the factuurregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factuurregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Factuurregel> getFactuurregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Factuurregel : {}", id);
        Optional<Factuurregel> factuurregel = factuurregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(factuurregel);
    }

    /**
     * {@code DELETE  /factuurregels/:id} : delete the "id" factuurregel.
     *
     * @param id the id of the factuurregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactuurregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Factuurregel : {}", id);
        factuurregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
