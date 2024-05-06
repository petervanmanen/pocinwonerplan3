package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Depot;
import nl.ritense.demo.repository.DepotRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Depot}.
 */
@RestController
@RequestMapping("/api/depots")
@Transactional
public class DepotResource {

    private final Logger log = LoggerFactory.getLogger(DepotResource.class);

    private static final String ENTITY_NAME = "depot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepotRepository depotRepository;

    public DepotResource(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    /**
     * {@code POST  /depots} : Create a new depot.
     *
     * @param depot the depot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depot, or with status {@code 400 (Bad Request)} if the depot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Depot> createDepot(@Valid @RequestBody Depot depot) throws URISyntaxException {
        log.debug("REST request to save Depot : {}", depot);
        if (depot.getId() != null) {
            throw new BadRequestAlertException("A new depot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        depot = depotRepository.save(depot);
        return ResponseEntity.created(new URI("/api/depots/" + depot.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, depot.getId().toString()))
            .body(depot);
    }

    /**
     * {@code PUT  /depots/:id} : Updates an existing depot.
     *
     * @param id the id of the depot to save.
     * @param depot the depot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depot,
     * or with status {@code 400 (Bad Request)} if the depot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Depot> updateDepot(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Depot depot)
        throws URISyntaxException {
        log.debug("REST request to update Depot : {}, {}", id, depot);
        if (depot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        depot = depotRepository.save(depot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, depot.getId().toString()))
            .body(depot);
    }

    /**
     * {@code PATCH  /depots/:id} : Partial updates given fields of an existing depot, field will ignore if it is null
     *
     * @param id the id of the depot to save.
     * @param depot the depot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depot,
     * or with status {@code 400 (Bad Request)} if the depot is not valid,
     * or with status {@code 404 (Not Found)} if the depot is not found,
     * or with status {@code 500 (Internal Server Error)} if the depot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Depot> partialUpdateDepot(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Depot depot
    ) throws URISyntaxException {
        log.debug("REST request to partial update Depot partially : {}, {}", id, depot);
        if (depot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depot.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Depot> result = depotRepository
            .findById(depot.getId())
            .map(existingDepot -> {
                if (depot.getNaam() != null) {
                    existingDepot.setNaam(depot.getNaam());
                }
                if (depot.getOmschrijving() != null) {
                    existingDepot.setOmschrijving(depot.getOmschrijving());
                }

                return existingDepot;
            })
            .map(depotRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, depot.getId().toString())
        );
    }

    /**
     * {@code GET  /depots} : get all the depots.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depots in body.
     */
    @GetMapping("")
    public List<Depot> getAllDepots() {
        log.debug("REST request to get all Depots");
        return depotRepository.findAll();
    }

    /**
     * {@code GET  /depots/:id} : get the "id" depot.
     *
     * @param id the id of the depot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Depot> getDepot(@PathVariable("id") Long id) {
        log.debug("REST request to get Depot : {}", id);
        Optional<Depot> depot = depotRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(depot);
    }

    /**
     * {@code DELETE  /depots/:id} : delete the "id" depot.
     *
     * @param id the id of the depot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepot(@PathVariable("id") Long id) {
        log.debug("REST request to delete Depot : {}", id);
        depotRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
