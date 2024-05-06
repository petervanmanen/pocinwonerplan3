package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Sportpark;
import nl.ritense.demo.repository.SportparkRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sportpark}.
 */
@RestController
@RequestMapping("/api/sportparks")
@Transactional
public class SportparkResource {

    private final Logger log = LoggerFactory.getLogger(SportparkResource.class);

    private static final String ENTITY_NAME = "sportpark";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SportparkRepository sportparkRepository;

    public SportparkResource(SportparkRepository sportparkRepository) {
        this.sportparkRepository = sportparkRepository;
    }

    /**
     * {@code POST  /sportparks} : Create a new sportpark.
     *
     * @param sportpark the sportpark to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sportpark, or with status {@code 400 (Bad Request)} if the sportpark has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sportpark> createSportpark(@RequestBody Sportpark sportpark) throws URISyntaxException {
        log.debug("REST request to save Sportpark : {}", sportpark);
        if (sportpark.getId() != null) {
            throw new BadRequestAlertException("A new sportpark cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sportpark = sportparkRepository.save(sportpark);
        return ResponseEntity.created(new URI("/api/sportparks/" + sportpark.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sportpark.getId().toString()))
            .body(sportpark);
    }

    /**
     * {@code GET  /sportparks} : get all the sportparks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sportparks in body.
     */
    @GetMapping("")
    public List<Sportpark> getAllSportparks() {
        log.debug("REST request to get all Sportparks");
        return sportparkRepository.findAll();
    }

    /**
     * {@code GET  /sportparks/:id} : get the "id" sportpark.
     *
     * @param id the id of the sportpark to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sportpark, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sportpark> getSportpark(@PathVariable("id") Long id) {
        log.debug("REST request to get Sportpark : {}", id);
        Optional<Sportpark> sportpark = sportparkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sportpark);
    }

    /**
     * {@code DELETE  /sportparks/:id} : delete the "id" sportpark.
     *
     * @param id the id of the sportpark to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportpark(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sportpark : {}", id);
        sportparkRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
