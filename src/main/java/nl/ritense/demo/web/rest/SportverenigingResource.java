package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sportvereniging;
import nl.ritense.demo.repository.SportverenigingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sportvereniging}.
 */
@RestController
@RequestMapping("/api/sportverenigings")
@Transactional
public class SportverenigingResource {

    private final Logger log = LoggerFactory.getLogger(SportverenigingResource.class);

    private static final String ENTITY_NAME = "sportvereniging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportverenigingRepository sportverenigingRepository;

    public SportverenigingResource(SportverenigingRepository sportverenigingRepository) {
        this.sportverenigingRepository = sportverenigingRepository;
    }

    /**
     * {@code POST  /sportverenigings} : Create a new sportvereniging.
     *
     * @param sportvereniging the sportvereniging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sportvereniging, or with status {@code 400 (Bad Request)} if the sportvereniging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sportvereniging> createSportvereniging(@RequestBody Sportvereniging sportvereniging) throws URISyntaxException {
        log.debug("REST request to save Sportvereniging : {}", sportvereniging);
        if (sportvereniging.getId() != null) {
            throw new BadRequestAlertException("A new sportvereniging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sportvereniging = sportverenigingRepository.save(sportvereniging);
        return ResponseEntity.created(new URI("/api/sportverenigings/" + sportvereniging.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sportvereniging.getId().toString()))
            .body(sportvereniging);
    }

    /**
     * {@code PUT  /sportverenigings/:id} : Updates an existing sportvereniging.
     *
     * @param id the id of the sportvereniging to save.
     * @param sportvereniging the sportvereniging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportvereniging,
     * or with status {@code 400 (Bad Request)} if the sportvereniging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sportvereniging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sportvereniging> updateSportvereniging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sportvereniging sportvereniging
    ) throws URISyntaxException {
        log.debug("REST request to update Sportvereniging : {}, {}", id, sportvereniging);
        if (sportvereniging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportvereniging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportverenigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sportvereniging = sportverenigingRepository.save(sportvereniging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sportvereniging.getId().toString()))
            .body(sportvereniging);
    }

    /**
     * {@code PATCH  /sportverenigings/:id} : Partial updates given fields of an existing sportvereniging, field will ignore if it is null
     *
     * @param id the id of the sportvereniging to save.
     * @param sportvereniging the sportvereniging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportvereniging,
     * or with status {@code 400 (Bad Request)} if the sportvereniging is not valid,
     * or with status {@code 404 (Not Found)} if the sportvereniging is not found,
     * or with status {@code 500 (Internal Server Error)} if the sportvereniging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sportvereniging> partialUpdateSportvereniging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sportvereniging sportvereniging
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sportvereniging partially : {}, {}", id, sportvereniging);
        if (sportvereniging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportvereniging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportverenigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sportvereniging> result = sportverenigingRepository
            .findById(sportvereniging.getId())
            .map(existingSportvereniging -> {
                if (sportvereniging.getAantalnormteams() != null) {
                    existingSportvereniging.setAantalnormteams(sportvereniging.getAantalnormteams());
                }
                if (sportvereniging.getAdres() != null) {
                    existingSportvereniging.setAdres(sportvereniging.getAdres());
                }
                if (sportvereniging.getBinnensport() != null) {
                    existingSportvereniging.setBinnensport(sportvereniging.getBinnensport());
                }
                if (sportvereniging.getBuitensport() != null) {
                    existingSportvereniging.setBuitensport(sportvereniging.getBuitensport());
                }
                if (sportvereniging.getEmail() != null) {
                    existingSportvereniging.setEmail(sportvereniging.getEmail());
                }
                if (sportvereniging.getLedenaantal() != null) {
                    existingSportvereniging.setLedenaantal(sportvereniging.getLedenaantal());
                }
                if (sportvereniging.getNaam() != null) {
                    existingSportvereniging.setNaam(sportvereniging.getNaam());
                }
                if (sportvereniging.getTypesport() != null) {
                    existingSportvereniging.setTypesport(sportvereniging.getTypesport());
                }

                return existingSportvereniging;
            })
            .map(sportverenigingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sportvereniging.getId().toString())
        );
    }

    /**
     * {@code GET  /sportverenigings} : get all the sportverenigings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sportverenigings in body.
     */
    @GetMapping("")
    public List<Sportvereniging> getAllSportverenigings(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Sportverenigings");
        if (eagerload) {
            return sportverenigingRepository.findAllWithEagerRelationships();
        } else {
            return sportverenigingRepository.findAll();
        }
    }

    /**
     * {@code GET  /sportverenigings/:id} : get the "id" sportvereniging.
     *
     * @param id the id of the sportvereniging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sportvereniging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sportvereniging> getSportvereniging(@PathVariable("id") Long id) {
        log.debug("REST request to get Sportvereniging : {}", id);
        Optional<Sportvereniging> sportvereniging = sportverenigingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(sportvereniging);
    }

    /**
     * {@code DELETE  /sportverenigings/:id} : delete the "id" sportvereniging.
     *
     * @param id the id of the sportvereniging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportvereniging(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sportvereniging : {}", id);
        sportverenigingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
