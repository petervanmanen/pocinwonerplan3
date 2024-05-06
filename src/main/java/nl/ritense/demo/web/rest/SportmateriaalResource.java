package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sportmateriaal;
import nl.ritense.demo.repository.SportmateriaalRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sportmateriaal}.
 */
@RestController
@RequestMapping("/api/sportmateriaals")
@Transactional
public class SportmateriaalResource {

    private final Logger log = LoggerFactory.getLogger(SportmateriaalResource.class);

    private static final String ENTITY_NAME = "sportmateriaal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportmateriaalRepository sportmateriaalRepository;

    public SportmateriaalResource(SportmateriaalRepository sportmateriaalRepository) {
        this.sportmateriaalRepository = sportmateriaalRepository;
    }

    /**
     * {@code POST  /sportmateriaals} : Create a new sportmateriaal.
     *
     * @param sportmateriaal the sportmateriaal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sportmateriaal, or with status {@code 400 (Bad Request)} if the sportmateriaal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sportmateriaal> createSportmateriaal(@RequestBody Sportmateriaal sportmateriaal) throws URISyntaxException {
        log.debug("REST request to save Sportmateriaal : {}", sportmateriaal);
        if (sportmateriaal.getId() != null) {
            throw new BadRequestAlertException("A new sportmateriaal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sportmateriaal = sportmateriaalRepository.save(sportmateriaal);
        return ResponseEntity.created(new URI("/api/sportmateriaals/" + sportmateriaal.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sportmateriaal.getId().toString()))
            .body(sportmateriaal);
    }

    /**
     * {@code PUT  /sportmateriaals/:id} : Updates an existing sportmateriaal.
     *
     * @param id the id of the sportmateriaal to save.
     * @param sportmateriaal the sportmateriaal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportmateriaal,
     * or with status {@code 400 (Bad Request)} if the sportmateriaal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sportmateriaal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sportmateriaal> updateSportmateriaal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sportmateriaal sportmateriaal
    ) throws URISyntaxException {
        log.debug("REST request to update Sportmateriaal : {}, {}", id, sportmateriaal);
        if (sportmateriaal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportmateriaal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportmateriaalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sportmateriaal = sportmateriaalRepository.save(sportmateriaal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sportmateriaal.getId().toString()))
            .body(sportmateriaal);
    }

    /**
     * {@code PATCH  /sportmateriaals/:id} : Partial updates given fields of an existing sportmateriaal, field will ignore if it is null
     *
     * @param id the id of the sportmateriaal to save.
     * @param sportmateriaal the sportmateriaal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportmateriaal,
     * or with status {@code 400 (Bad Request)} if the sportmateriaal is not valid,
     * or with status {@code 404 (Not Found)} if the sportmateriaal is not found,
     * or with status {@code 500 (Internal Server Error)} if the sportmateriaal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sportmateriaal> partialUpdateSportmateriaal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sportmateriaal sportmateriaal
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sportmateriaal partially : {}, {}", id, sportmateriaal);
        if (sportmateriaal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportmateriaal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportmateriaalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sportmateriaal> result = sportmateriaalRepository
            .findById(sportmateriaal.getId())
            .map(existingSportmateriaal -> {
                if (sportmateriaal.getNaam() != null) {
                    existingSportmateriaal.setNaam(sportmateriaal.getNaam());
                }

                return existingSportmateriaal;
            })
            .map(sportmateriaalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sportmateriaal.getId().toString())
        );
    }

    /**
     * {@code GET  /sportmateriaals} : get all the sportmateriaals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sportmateriaals in body.
     */
    @GetMapping("")
    public List<Sportmateriaal> getAllSportmateriaals() {
        log.debug("REST request to get all Sportmateriaals");
        return sportmateriaalRepository.findAll();
    }

    /**
     * {@code GET  /sportmateriaals/:id} : get the "id" sportmateriaal.
     *
     * @param id the id of the sportmateriaal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sportmateriaal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sportmateriaal> getSportmateriaal(@PathVariable("id") Long id) {
        log.debug("REST request to get Sportmateriaal : {}", id);
        Optional<Sportmateriaal> sportmateriaal = sportmateriaalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sportmateriaal);
    }

    /**
     * {@code DELETE  /sportmateriaals/:id} : delete the "id" sportmateriaal.
     *
     * @param id the id of the sportmateriaal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportmateriaal(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sportmateriaal : {}", id);
        sportmateriaalRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
