package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Betaling;
import nl.ritense.demo.repository.BetalingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Betaling}.
 */
@RestController
@RequestMapping("/api/betalings")
@Transactional
public class BetalingResource {

    private final Logger log = LoggerFactory.getLogger(BetalingResource.class);

    private static final String ENTITY_NAME = "betaling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BetalingRepository betalingRepository;

    public BetalingResource(BetalingRepository betalingRepository) {
        this.betalingRepository = betalingRepository;
    }

    /**
     * {@code POST  /betalings} : Create a new betaling.
     *
     * @param betaling the betaling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new betaling, or with status {@code 400 (Bad Request)} if the betaling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Betaling> createBetaling(@RequestBody Betaling betaling) throws URISyntaxException {
        log.debug("REST request to save Betaling : {}", betaling);
        if (betaling.getId() != null) {
            throw new BadRequestAlertException("A new betaling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        betaling = betalingRepository.save(betaling);
        return ResponseEntity.created(new URI("/api/betalings/" + betaling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, betaling.getId().toString()))
            .body(betaling);
    }

    /**
     * {@code PUT  /betalings/:id} : Updates an existing betaling.
     *
     * @param id the id of the betaling to save.
     * @param betaling the betaling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated betaling,
     * or with status {@code 400 (Bad Request)} if the betaling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the betaling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Betaling> updateBetaling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Betaling betaling
    ) throws URISyntaxException {
        log.debug("REST request to update Betaling : {}, {}", id, betaling);
        if (betaling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, betaling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!betalingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        betaling = betalingRepository.save(betaling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, betaling.getId().toString()))
            .body(betaling);
    }

    /**
     * {@code PATCH  /betalings/:id} : Partial updates given fields of an existing betaling, field will ignore if it is null
     *
     * @param id the id of the betaling to save.
     * @param betaling the betaling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated betaling,
     * or with status {@code 400 (Bad Request)} if the betaling is not valid,
     * or with status {@code 404 (Not Found)} if the betaling is not found,
     * or with status {@code 500 (Internal Server Error)} if the betaling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Betaling> partialUpdateBetaling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Betaling betaling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Betaling partially : {}, {}", id, betaling);
        if (betaling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, betaling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!betalingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Betaling> result = betalingRepository
            .findById(betaling.getId())
            .map(existingBetaling -> {
                if (betaling.getBedrag() != null) {
                    existingBetaling.setBedrag(betaling.getBedrag());
                }
                if (betaling.getDatumtijd() != null) {
                    existingBetaling.setDatumtijd(betaling.getDatumtijd());
                }
                if (betaling.getOmschrijving() != null) {
                    existingBetaling.setOmschrijving(betaling.getOmschrijving());
                }
                if (betaling.getValuta() != null) {
                    existingBetaling.setValuta(betaling.getValuta());
                }

                return existingBetaling;
            })
            .map(betalingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, betaling.getId().toString())
        );
    }

    /**
     * {@code GET  /betalings} : get all the betalings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of betalings in body.
     */
    @GetMapping("")
    public List<Betaling> getAllBetalings() {
        log.debug("REST request to get all Betalings");
        return betalingRepository.findAll();
    }

    /**
     * {@code GET  /betalings/:id} : get the "id" betaling.
     *
     * @param id the id of the betaling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the betaling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Betaling> getBetaling(@PathVariable("id") Long id) {
        log.debug("REST request to get Betaling : {}", id);
        Optional<Betaling> betaling = betalingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(betaling);
    }

    /**
     * {@code DELETE  /betalings/:id} : delete the "id" betaling.
     *
     * @param id the id of the betaling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBetaling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Betaling : {}", id);
        betalingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
