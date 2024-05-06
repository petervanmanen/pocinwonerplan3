package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sportlocatie;
import nl.ritense.demo.repository.SportlocatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sportlocatie}.
 */
@RestController
@RequestMapping("/api/sportlocaties")
@Transactional
public class SportlocatieResource {

    private final Logger log = LoggerFactory.getLogger(SportlocatieResource.class);

    private static final String ENTITY_NAME = "sportlocatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportlocatieRepository sportlocatieRepository;

    public SportlocatieResource(SportlocatieRepository sportlocatieRepository) {
        this.sportlocatieRepository = sportlocatieRepository;
    }

    /**
     * {@code POST  /sportlocaties} : Create a new sportlocatie.
     *
     * @param sportlocatie the sportlocatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sportlocatie, or with status {@code 400 (Bad Request)} if the sportlocatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sportlocatie> createSportlocatie(@RequestBody Sportlocatie sportlocatie) throws URISyntaxException {
        log.debug("REST request to save Sportlocatie : {}", sportlocatie);
        if (sportlocatie.getId() != null) {
            throw new BadRequestAlertException("A new sportlocatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sportlocatie = sportlocatieRepository.save(sportlocatie);
        return ResponseEntity.created(new URI("/api/sportlocaties/" + sportlocatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sportlocatie.getId().toString()))
            .body(sportlocatie);
    }

    /**
     * {@code PUT  /sportlocaties/:id} : Updates an existing sportlocatie.
     *
     * @param id the id of the sportlocatie to save.
     * @param sportlocatie the sportlocatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportlocatie,
     * or with status {@code 400 (Bad Request)} if the sportlocatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sportlocatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sportlocatie> updateSportlocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sportlocatie sportlocatie
    ) throws URISyntaxException {
        log.debug("REST request to update Sportlocatie : {}, {}", id, sportlocatie);
        if (sportlocatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportlocatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportlocatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sportlocatie = sportlocatieRepository.save(sportlocatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sportlocatie.getId().toString()))
            .body(sportlocatie);
    }

    /**
     * {@code PATCH  /sportlocaties/:id} : Partial updates given fields of an existing sportlocatie, field will ignore if it is null
     *
     * @param id the id of the sportlocatie to save.
     * @param sportlocatie the sportlocatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportlocatie,
     * or with status {@code 400 (Bad Request)} if the sportlocatie is not valid,
     * or with status {@code 404 (Not Found)} if the sportlocatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the sportlocatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sportlocatie> partialUpdateSportlocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sportlocatie sportlocatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sportlocatie partially : {}, {}", id, sportlocatie);
        if (sportlocatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportlocatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportlocatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sportlocatie> result = sportlocatieRepository
            .findById(sportlocatie.getId())
            .map(existingSportlocatie -> {
                if (sportlocatie.getNaam() != null) {
                    existingSportlocatie.setNaam(sportlocatie.getNaam());
                }

                return existingSportlocatie;
            })
            .map(sportlocatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sportlocatie.getId().toString())
        );
    }

    /**
     * {@code GET  /sportlocaties} : get all the sportlocaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sportlocaties in body.
     */
    @GetMapping("")
    public List<Sportlocatie> getAllSportlocaties() {
        log.debug("REST request to get all Sportlocaties");
        return sportlocatieRepository.findAll();
    }

    /**
     * {@code GET  /sportlocaties/:id} : get the "id" sportlocatie.
     *
     * @param id the id of the sportlocatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sportlocatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sportlocatie> getSportlocatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Sportlocatie : {}", id);
        Optional<Sportlocatie> sportlocatie = sportlocatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sportlocatie);
    }

    /**
     * {@code DELETE  /sportlocaties/:id} : delete the "id" sportlocatie.
     *
     * @param id the id of the sportlocatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportlocatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sportlocatie : {}", id);
        sportlocatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
