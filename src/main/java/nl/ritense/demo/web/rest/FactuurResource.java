package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Factuur;
import nl.ritense.demo.repository.FactuurRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Factuur}.
 */
@RestController
@RequestMapping("/api/factuurs")
@Transactional
public class FactuurResource {

    private final Logger log = LoggerFactory.getLogger(FactuurResource.class);

    private static final String ENTITY_NAME = "factuur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactuurRepository factuurRepository;

    public FactuurResource(FactuurRepository factuurRepository) {
        this.factuurRepository = factuurRepository;
    }

    /**
     * {@code POST  /factuurs} : Create a new factuur.
     *
     * @param factuur the factuur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factuur, or with status {@code 400 (Bad Request)} if the factuur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Factuur> createFactuur(@Valid @RequestBody Factuur factuur) throws URISyntaxException {
        log.debug("REST request to save Factuur : {}", factuur);
        if (factuur.getId() != null) {
            throw new BadRequestAlertException("A new factuur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        factuur = factuurRepository.save(factuur);
        return ResponseEntity.created(new URI("/api/factuurs/" + factuur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, factuur.getId().toString()))
            .body(factuur);
    }

    /**
     * {@code PUT  /factuurs/:id} : Updates an existing factuur.
     *
     * @param id the id of the factuur to save.
     * @param factuur the factuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factuur,
     * or with status {@code 400 (Bad Request)} if the factuur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Factuur> updateFactuur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Factuur factuur
    ) throws URISyntaxException {
        log.debug("REST request to update Factuur : {}, {}", id, factuur);
        if (factuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        factuur = factuurRepository.save(factuur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factuur.getId().toString()))
            .body(factuur);
    }

    /**
     * {@code PATCH  /factuurs/:id} : Partial updates given fields of an existing factuur, field will ignore if it is null
     *
     * @param id the id of the factuur to save.
     * @param factuur the factuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factuur,
     * or with status {@code 400 (Bad Request)} if the factuur is not valid,
     * or with status {@code 404 (Not Found)} if the factuur is not found,
     * or with status {@code 500 (Internal Server Error)} if the factuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Factuur> partialUpdateFactuur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Factuur factuur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Factuur partially : {}, {}", id, factuur);
        if (factuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Factuur> result = factuurRepository
            .findById(factuur.getId())
            .map(existingFactuur -> {
                if (factuur.getBetaalbaarper() != null) {
                    existingFactuur.setBetaalbaarper(factuur.getBetaalbaarper());
                }
                if (factuur.getBetaaltermijn() != null) {
                    existingFactuur.setBetaaltermijn(factuur.getBetaaltermijn());
                }
                if (factuur.getCode() != null) {
                    existingFactuur.setCode(factuur.getCode());
                }
                if (factuur.getDatumfactuur() != null) {
                    existingFactuur.setDatumfactuur(factuur.getDatumfactuur());
                }
                if (factuur.getFactuurbedragbtw() != null) {
                    existingFactuur.setFactuurbedragbtw(factuur.getFactuurbedragbtw());
                }
                if (factuur.getFactuurbedragexclusiefbtw() != null) {
                    existingFactuur.setFactuurbedragexclusiefbtw(factuur.getFactuurbedragexclusiefbtw());
                }
                if (factuur.getOmschrijving() != null) {
                    existingFactuur.setOmschrijving(factuur.getOmschrijving());
                }

                return existingFactuur;
            })
            .map(factuurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factuur.getId().toString())
        );
    }

    /**
     * {@code GET  /factuurs} : get all the factuurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factuurs in body.
     */
    @GetMapping("")
    public List<Factuur> getAllFactuurs() {
        log.debug("REST request to get all Factuurs");
        return factuurRepository.findAll();
    }

    /**
     * {@code GET  /factuurs/:id} : get the "id" factuur.
     *
     * @param id the id of the factuur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factuur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Factuur> getFactuur(@PathVariable("id") Long id) {
        log.debug("REST request to get Factuur : {}", id);
        Optional<Factuur> factuur = factuurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(factuur);
    }

    /**
     * {@code DELETE  /factuurs/:id} : delete the "id" factuur.
     *
     * @param id the id of the factuur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactuur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Factuur : {}", id);
        factuurRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
