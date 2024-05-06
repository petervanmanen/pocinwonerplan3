package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sportterrein;
import nl.ritense.demo.repository.SportterreinRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sportterrein}.
 */
@RestController
@RequestMapping("/api/sportterreins")
@Transactional
public class SportterreinResource {

    private final Logger log = LoggerFactory.getLogger(SportterreinResource.class);

    private static final String ENTITY_NAME = "sportterrein";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportterreinRepository sportterreinRepository;

    public SportterreinResource(SportterreinRepository sportterreinRepository) {
        this.sportterreinRepository = sportterreinRepository;
    }

    /**
     * {@code POST  /sportterreins} : Create a new sportterrein.
     *
     * @param sportterrein the sportterrein to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sportterrein, or with status {@code 400 (Bad Request)} if the sportterrein has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sportterrein> createSportterrein(@RequestBody Sportterrein sportterrein) throws URISyntaxException {
        log.debug("REST request to save Sportterrein : {}", sportterrein);
        if (sportterrein.getId() != null) {
            throw new BadRequestAlertException("A new sportterrein cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sportterrein = sportterreinRepository.save(sportterrein);
        return ResponseEntity.created(new URI("/api/sportterreins/" + sportterrein.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sportterrein.getId().toString()))
            .body(sportterrein);
    }

    /**
     * {@code PUT  /sportterreins/:id} : Updates an existing sportterrein.
     *
     * @param id the id of the sportterrein to save.
     * @param sportterrein the sportterrein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportterrein,
     * or with status {@code 400 (Bad Request)} if the sportterrein is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sportterrein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sportterrein> updateSportterrein(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sportterrein sportterrein
    ) throws URISyntaxException {
        log.debug("REST request to update Sportterrein : {}, {}", id, sportterrein);
        if (sportterrein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportterrein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportterreinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sportterrein = sportterreinRepository.save(sportterrein);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sportterrein.getId().toString()))
            .body(sportterrein);
    }

    /**
     * {@code PATCH  /sportterreins/:id} : Partial updates given fields of an existing sportterrein, field will ignore if it is null
     *
     * @param id the id of the sportterrein to save.
     * @param sportterrein the sportterrein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sportterrein,
     * or with status {@code 400 (Bad Request)} if the sportterrein is not valid,
     * or with status {@code 404 (Not Found)} if the sportterrein is not found,
     * or with status {@code 500 (Internal Server Error)} if the sportterrein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sportterrein> partialUpdateSportterrein(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sportterrein sportterrein
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sportterrein partially : {}, {}", id, sportterrein);
        if (sportterrein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sportterrein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sportterreinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sportterrein> result = sportterreinRepository
            .findById(sportterrein.getId())
            .map(existingSportterrein -> {
                if (sportterrein.getDrainage() != null) {
                    existingSportterrein.setDrainage(sportterrein.getDrainage());
                }
                if (sportterrein.getGebruiksvorm() != null) {
                    existingSportterrein.setGebruiksvorm(sportterrein.getGebruiksvorm());
                }
                if (sportterrein.getSportcomplex() != null) {
                    existingSportterrein.setSportcomplex(sportterrein.getSportcomplex());
                }
                if (sportterrein.getSportterreintypesport() != null) {
                    existingSportterrein.setSportterreintypesport(sportterrein.getSportterreintypesport());
                }
                if (sportterrein.getType() != null) {
                    existingSportterrein.setType(sportterrein.getType());
                }
                if (sportterrein.getTypeplus() != null) {
                    existingSportterrein.setTypeplus(sportterrein.getTypeplus());
                }
                if (sportterrein.getVeldnummer() != null) {
                    existingSportterrein.setVeldnummer(sportterrein.getVeldnummer());
                }
                if (sportterrein.getVerlicht() != null) {
                    existingSportterrein.setVerlicht(sportterrein.getVerlicht());
                }

                return existingSportterrein;
            })
            .map(sportterreinRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sportterrein.getId().toString())
        );
    }

    /**
     * {@code GET  /sportterreins} : get all the sportterreins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sportterreins in body.
     */
    @GetMapping("")
    public List<Sportterrein> getAllSportterreins() {
        log.debug("REST request to get all Sportterreins");
        return sportterreinRepository.findAll();
    }

    /**
     * {@code GET  /sportterreins/:id} : get the "id" sportterrein.
     *
     * @param id the id of the sportterrein to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sportterrein, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sportterrein> getSportterrein(@PathVariable("id") Long id) {
        log.debug("REST request to get Sportterrein : {}", id);
        Optional<Sportterrein> sportterrein = sportterreinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sportterrein);
    }

    /**
     * {@code DELETE  /sportterreins/:id} : delete the "id" sportterrein.
     *
     * @param id the id of the sportterrein to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportterrein(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sportterrein : {}", id);
        sportterreinRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
