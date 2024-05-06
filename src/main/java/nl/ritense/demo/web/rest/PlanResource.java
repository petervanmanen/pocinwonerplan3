package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Plan;
import nl.ritense.demo.repository.PlanRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Plan}.
 */
@RestController
@RequestMapping("/api/plans")
@Transactional
public class PlanResource {

    private final Logger log = LoggerFactory.getLogger(PlanResource.class);

    private static final String ENTITY_NAME = "plan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanRepository planRepository;

    public PlanResource(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    /**
     * {@code POST  /plans} : Create a new plan.
     *
     * @param plan the plan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plan, or with status {@code 400 (Bad Request)} if the plan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Plan> createPlan(@Valid @RequestBody Plan plan) throws URISyntaxException {
        log.debug("REST request to save Plan : {}", plan);
        if (plan.getId() != null) {
            throw new BadRequestAlertException("A new plan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        plan = planRepository.save(plan);
        return ResponseEntity.created(new URI("/api/plans/" + plan.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, plan.getId().toString()))
            .body(plan);
    }

    /**
     * {@code PUT  /plans/:id} : Updates an existing plan.
     *
     * @param id the id of the plan to save.
     * @param plan the plan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plan,
     * or with status {@code 400 (Bad Request)} if the plan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Plan plan)
        throws URISyntaxException {
        log.debug("REST request to update Plan : {}, {}", id, plan);
        if (plan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        plan = planRepository.save(plan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plan.getId().toString()))
            .body(plan);
    }

    /**
     * {@code PATCH  /plans/:id} : Partial updates given fields of an existing plan, field will ignore if it is null
     *
     * @param id the id of the plan to save.
     * @param plan the plan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plan,
     * or with status {@code 400 (Bad Request)} if the plan is not valid,
     * or with status {@code 404 (Not Found)} if the plan is not found,
     * or with status {@code 500 (Internal Server Error)} if the plan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Plan> partialUpdatePlan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Plan plan
    ) throws URISyntaxException {
        log.debug("REST request to partial update Plan partially : {}, {}", id, plan);
        if (plan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Plan> result = planRepository
            .findById(plan.getId())
            .map(existingPlan -> {
                if (plan.getZeventigprocentverkocht() != null) {
                    existingPlan.setZeventigprocentverkocht(plan.getZeventigprocentverkocht());
                }
                if (plan.getAardgasloos() != null) {
                    existingPlan.setAardgasloos(plan.getAardgasloos());
                }
                if (plan.getBestemminggoedgekeurd() != null) {
                    existingPlan.setBestemminggoedgekeurd(plan.getBestemminggoedgekeurd());
                }
                if (plan.getEersteoplevering() != null) {
                    existingPlan.setEersteoplevering(plan.getEersteoplevering());
                }
                if (plan.getEigendomgemeente() != null) {
                    existingPlan.setEigendomgemeente(plan.getEigendomgemeente());
                }
                if (plan.getGebiedstransformatie() != null) {
                    existingPlan.setGebiedstransformatie(plan.getGebiedstransformatie());
                }
                if (plan.getIntentie() != null) {
                    existingPlan.setIntentie(plan.getIntentie());
                }
                if (plan.getLaatsteoplevering() != null) {
                    existingPlan.setLaatsteoplevering(plan.getLaatsteoplevering());
                }
                if (plan.getNaam() != null) {
                    existingPlan.setNaam(plan.getNaam());
                }
                if (plan.getNummer() != null) {
                    existingPlan.setNummer(plan.getNummer());
                }
                if (plan.getOnherroepelijk() != null) {
                    existingPlan.setOnherroepelijk(plan.getOnherroepelijk());
                }
                if (plan.getPercelen() != null) {
                    existingPlan.setPercelen(plan.getPercelen());
                }
                if (plan.getStartbouw() != null) {
                    existingPlan.setStartbouw(plan.getStartbouw());
                }
                if (plan.getStartverkoop() != null) {
                    existingPlan.setStartverkoop(plan.getStartverkoop());
                }

                return existingPlan;
            })
            .map(planRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plan.getId().toString())
        );
    }

    /**
     * {@code GET  /plans} : get all the plans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plans in body.
     */
    @GetMapping("")
    public List<Plan> getAllPlans() {
        log.debug("REST request to get all Plans");
        return planRepository.findAll();
    }

    /**
     * {@code GET  /plans/:id} : get the "id" plan.
     *
     * @param id the id of the plan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlan(@PathVariable("id") Long id) {
        log.debug("REST request to get Plan : {}", id);
        Optional<Plan> plan = planRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plan);
    }

    /**
     * {@code DELETE  /plans/:id} : delete the "id" plan.
     *
     * @param id the id of the plan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable("id") Long id) {
        log.debug("REST request to delete Plan : {}", id);
        planRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
