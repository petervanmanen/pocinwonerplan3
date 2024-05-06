package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Budgetuitputting;
import nl.ritense.demo.repository.BudgetuitputtingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Budgetuitputting}.
 */
@RestController
@RequestMapping("/api/budgetuitputtings")
@Transactional
public class BudgetuitputtingResource {

    private final Logger log = LoggerFactory.getLogger(BudgetuitputtingResource.class);

    private static final String ENTITY_NAME = "budgetuitputting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BudgetuitputtingRepository budgetuitputtingRepository;

    public BudgetuitputtingResource(BudgetuitputtingRepository budgetuitputtingRepository) {
        this.budgetuitputtingRepository = budgetuitputtingRepository;
    }

    /**
     * {@code POST  /budgetuitputtings} : Create a new budgetuitputting.
     *
     * @param budgetuitputting the budgetuitputting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new budgetuitputting, or with status {@code 400 (Bad Request)} if the budgetuitputting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Budgetuitputting> createBudgetuitputting(@RequestBody Budgetuitputting budgetuitputting)
        throws URISyntaxException {
        log.debug("REST request to save Budgetuitputting : {}", budgetuitputting);
        if (budgetuitputting.getId() != null) {
            throw new BadRequestAlertException("A new budgetuitputting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        budgetuitputting = budgetuitputtingRepository.save(budgetuitputting);
        return ResponseEntity.created(new URI("/api/budgetuitputtings/" + budgetuitputting.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, budgetuitputting.getId().toString()))
            .body(budgetuitputting);
    }

    /**
     * {@code PUT  /budgetuitputtings/:id} : Updates an existing budgetuitputting.
     *
     * @param id the id of the budgetuitputting to save.
     * @param budgetuitputting the budgetuitputting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated budgetuitputting,
     * or with status {@code 400 (Bad Request)} if the budgetuitputting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the budgetuitputting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Budgetuitputting> updateBudgetuitputting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Budgetuitputting budgetuitputting
    ) throws URISyntaxException {
        log.debug("REST request to update Budgetuitputting : {}, {}", id, budgetuitputting);
        if (budgetuitputting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, budgetuitputting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!budgetuitputtingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        budgetuitputting = budgetuitputtingRepository.save(budgetuitputting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, budgetuitputting.getId().toString()))
            .body(budgetuitputting);
    }

    /**
     * {@code PATCH  /budgetuitputtings/:id} : Partial updates given fields of an existing budgetuitputting, field will ignore if it is null
     *
     * @param id the id of the budgetuitputting to save.
     * @param budgetuitputting the budgetuitputting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated budgetuitputting,
     * or with status {@code 400 (Bad Request)} if the budgetuitputting is not valid,
     * or with status {@code 404 (Not Found)} if the budgetuitputting is not found,
     * or with status {@code 500 (Internal Server Error)} if the budgetuitputting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Budgetuitputting> partialUpdateBudgetuitputting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Budgetuitputting budgetuitputting
    ) throws URISyntaxException {
        log.debug("REST request to partial update Budgetuitputting partially : {}, {}", id, budgetuitputting);
        if (budgetuitputting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, budgetuitputting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!budgetuitputtingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Budgetuitputting> result = budgetuitputtingRepository
            .findById(budgetuitputting.getId())
            .map(existingBudgetuitputting -> {
                if (budgetuitputting.getDatum() != null) {
                    existingBudgetuitputting.setDatum(budgetuitputting.getDatum());
                }
                if (budgetuitputting.getUitgenutbedrag() != null) {
                    existingBudgetuitputting.setUitgenutbedrag(budgetuitputting.getUitgenutbedrag());
                }

                return existingBudgetuitputting;
            })
            .map(budgetuitputtingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, budgetuitputting.getId().toString())
        );
    }

    /**
     * {@code GET  /budgetuitputtings} : get all the budgetuitputtings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of budgetuitputtings in body.
     */
    @GetMapping("")
    public List<Budgetuitputting> getAllBudgetuitputtings() {
        log.debug("REST request to get all Budgetuitputtings");
        return budgetuitputtingRepository.findAll();
    }

    /**
     * {@code GET  /budgetuitputtings/:id} : get the "id" budgetuitputting.
     *
     * @param id the id of the budgetuitputting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the budgetuitputting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Budgetuitputting> getBudgetuitputting(@PathVariable("id") Long id) {
        log.debug("REST request to get Budgetuitputting : {}", id);
        Optional<Budgetuitputting> budgetuitputting = budgetuitputtingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(budgetuitputting);
    }

    /**
     * {@code DELETE  /budgetuitputtings/:id} : delete the "id" budgetuitputting.
     *
     * @param id the id of the budgetuitputting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetuitputting(@PathVariable("id") Long id) {
        log.debug("REST request to delete Budgetuitputting : {}", id);
        budgetuitputtingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
