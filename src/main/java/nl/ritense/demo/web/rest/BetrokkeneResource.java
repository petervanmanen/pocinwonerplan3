package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Betrokkene;
import nl.ritense.demo.repository.BetrokkeneRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Betrokkene}.
 */
@RestController
@RequestMapping("/api/betrokkenes")
@Transactional
public class BetrokkeneResource {

    private final Logger log = LoggerFactory.getLogger(BetrokkeneResource.class);

    private static final String ENTITY_NAME = "betrokkene";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BetrokkeneRepository betrokkeneRepository;

    public BetrokkeneResource(BetrokkeneRepository betrokkeneRepository) {
        this.betrokkeneRepository = betrokkeneRepository;
    }

    /**
     * {@code POST  /betrokkenes} : Create a new betrokkene.
     *
     * @param betrokkene the betrokkene to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new betrokkene, or with status {@code 400 (Bad Request)} if the betrokkene has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Betrokkene> createBetrokkene(@Valid @RequestBody Betrokkene betrokkene) throws URISyntaxException {
        log.debug("REST request to save Betrokkene : {}", betrokkene);
        if (betrokkene.getId() != null) {
            throw new BadRequestAlertException("A new betrokkene cannot already have an ID", ENTITY_NAME, "idexists");
        }
        betrokkene = betrokkeneRepository.save(betrokkene);
        return ResponseEntity.created(new URI("/api/betrokkenes/" + betrokkene.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, betrokkene.getId().toString()))
            .body(betrokkene);
    }

    /**
     * {@code PUT  /betrokkenes/:id} : Updates an existing betrokkene.
     *
     * @param id the id of the betrokkene to save.
     * @param betrokkene the betrokkene to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated betrokkene,
     * or with status {@code 400 (Bad Request)} if the betrokkene is not valid,
     * or with status {@code 500 (Internal Server Error)} if the betrokkene couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Betrokkene> updateBetrokkene(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Betrokkene betrokkene
    ) throws URISyntaxException {
        log.debug("REST request to update Betrokkene : {}, {}", id, betrokkene);
        if (betrokkene.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, betrokkene.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!betrokkeneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        betrokkene = betrokkeneRepository.save(betrokkene);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, betrokkene.getId().toString()))
            .body(betrokkene);
    }

    /**
     * {@code PATCH  /betrokkenes/:id} : Partial updates given fields of an existing betrokkene, field will ignore if it is null
     *
     * @param id the id of the betrokkene to save.
     * @param betrokkene the betrokkene to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated betrokkene,
     * or with status {@code 400 (Bad Request)} if the betrokkene is not valid,
     * or with status {@code 404 (Not Found)} if the betrokkene is not found,
     * or with status {@code 500 (Internal Server Error)} if the betrokkene couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Betrokkene> partialUpdateBetrokkene(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Betrokkene betrokkene
    ) throws URISyntaxException {
        log.debug("REST request to partial update Betrokkene partially : {}, {}", id, betrokkene);
        if (betrokkene.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, betrokkene.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!betrokkeneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Betrokkene> result = betrokkeneRepository
            .findById(betrokkene.getId())
            .map(existingBetrokkene -> {
                if (betrokkene.getAdresbinnenland() != null) {
                    existingBetrokkene.setAdresbinnenland(betrokkene.getAdresbinnenland());
                }
                if (betrokkene.getAdresbuitenland() != null) {
                    existingBetrokkene.setAdresbuitenland(betrokkene.getAdresbuitenland());
                }
                if (betrokkene.getIdentificatie() != null) {
                    existingBetrokkene.setIdentificatie(betrokkene.getIdentificatie());
                }
                if (betrokkene.getNaam() != null) {
                    existingBetrokkene.setNaam(betrokkene.getNaam());
                }
                if (betrokkene.getRol() != null) {
                    existingBetrokkene.setRol(betrokkene.getRol());
                }

                return existingBetrokkene;
            })
            .map(betrokkeneRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, betrokkene.getId().toString())
        );
    }

    /**
     * {@code GET  /betrokkenes} : get all the betrokkenes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of betrokkenes in body.
     */
    @GetMapping("")
    public List<Betrokkene> getAllBetrokkenes(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Betrokkenes");
        if (eagerload) {
            return betrokkeneRepository.findAllWithEagerRelationships();
        } else {
            return betrokkeneRepository.findAll();
        }
    }

    /**
     * {@code GET  /betrokkenes/:id} : get the "id" betrokkene.
     *
     * @param id the id of the betrokkene to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the betrokkene, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Betrokkene> getBetrokkene(@PathVariable("id") Long id) {
        log.debug("REST request to get Betrokkene : {}", id);
        Optional<Betrokkene> betrokkene = betrokkeneRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(betrokkene);
    }

    /**
     * {@code DELETE  /betrokkenes/:id} : delete the "id" betrokkene.
     *
     * @param id the id of the betrokkene to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBetrokkene(@PathVariable("id") Long id) {
        log.debug("REST request to delete Betrokkene : {}", id);
        betrokkeneRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
