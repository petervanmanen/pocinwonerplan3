package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Betaalmoment;
import nl.ritense.demo.repository.BetaalmomentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Betaalmoment}.
 */
@RestController
@RequestMapping("/api/betaalmoments")
@Transactional
public class BetaalmomentResource {

    private final Logger log = LoggerFactory.getLogger(BetaalmomentResource.class);

    private static final String ENTITY_NAME = "betaalmoment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BetaalmomentRepository betaalmomentRepository;

    public BetaalmomentResource(BetaalmomentRepository betaalmomentRepository) {
        this.betaalmomentRepository = betaalmomentRepository;
    }

    /**
     * {@code POST  /betaalmoments} : Create a new betaalmoment.
     *
     * @param betaalmoment the betaalmoment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new betaalmoment, or with status {@code 400 (Bad Request)} if the betaalmoment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Betaalmoment> createBetaalmoment(@Valid @RequestBody Betaalmoment betaalmoment) throws URISyntaxException {
        log.debug("REST request to save Betaalmoment : {}", betaalmoment);
        if (betaalmoment.getId() != null) {
            throw new BadRequestAlertException("A new betaalmoment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        betaalmoment = betaalmomentRepository.save(betaalmoment);
        return ResponseEntity.created(new URI("/api/betaalmoments/" + betaalmoment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, betaalmoment.getId().toString()))
            .body(betaalmoment);
    }

    /**
     * {@code PUT  /betaalmoments/:id} : Updates an existing betaalmoment.
     *
     * @param id the id of the betaalmoment to save.
     * @param betaalmoment the betaalmoment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated betaalmoment,
     * or with status {@code 400 (Bad Request)} if the betaalmoment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the betaalmoment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Betaalmoment> updateBetaalmoment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Betaalmoment betaalmoment
    ) throws URISyntaxException {
        log.debug("REST request to update Betaalmoment : {}, {}", id, betaalmoment);
        if (betaalmoment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, betaalmoment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!betaalmomentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        betaalmoment = betaalmomentRepository.save(betaalmoment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, betaalmoment.getId().toString()))
            .body(betaalmoment);
    }

    /**
     * {@code PATCH  /betaalmoments/:id} : Partial updates given fields of an existing betaalmoment, field will ignore if it is null
     *
     * @param id the id of the betaalmoment to save.
     * @param betaalmoment the betaalmoment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated betaalmoment,
     * or with status {@code 400 (Bad Request)} if the betaalmoment is not valid,
     * or with status {@code 404 (Not Found)} if the betaalmoment is not found,
     * or with status {@code 500 (Internal Server Error)} if the betaalmoment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Betaalmoment> partialUpdateBetaalmoment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Betaalmoment betaalmoment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Betaalmoment partially : {}, {}", id, betaalmoment);
        if (betaalmoment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, betaalmoment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!betaalmomentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Betaalmoment> result = betaalmomentRepository
            .findById(betaalmoment.getId())
            .map(existingBetaalmoment -> {
                if (betaalmoment.getBedrag() != null) {
                    existingBetaalmoment.setBedrag(betaalmoment.getBedrag());
                }
                if (betaalmoment.getDatum() != null) {
                    existingBetaalmoment.setDatum(betaalmoment.getDatum());
                }
                if (betaalmoment.getVoorschot() != null) {
                    existingBetaalmoment.setVoorschot(betaalmoment.getVoorschot());
                }

                return existingBetaalmoment;
            })
            .map(betaalmomentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, betaalmoment.getId().toString())
        );
    }

    /**
     * {@code GET  /betaalmoments} : get all the betaalmoments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of betaalmoments in body.
     */
    @GetMapping("")
    public List<Betaalmoment> getAllBetaalmoments() {
        log.debug("REST request to get all Betaalmoments");
        return betaalmomentRepository.findAll();
    }

    /**
     * {@code GET  /betaalmoments/:id} : get the "id" betaalmoment.
     *
     * @param id the id of the betaalmoment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the betaalmoment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Betaalmoment> getBetaalmoment(@PathVariable("id") Long id) {
        log.debug("REST request to get Betaalmoment : {}", id);
        Optional<Betaalmoment> betaalmoment = betaalmomentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(betaalmoment);
    }

    /**
     * {@code DELETE  /betaalmoments/:id} : delete the "id" betaalmoment.
     *
     * @param id the id of the betaalmoment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBetaalmoment(@PathVariable("id") Long id) {
        log.debug("REST request to delete Betaalmoment : {}", id);
        betaalmomentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
