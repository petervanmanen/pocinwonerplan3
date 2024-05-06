package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Beschikkingleerlingenvervoer;
import nl.ritense.demo.repository.BeschikkingleerlingenvervoerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beschikkingleerlingenvervoer}.
 */
@RestController
@RequestMapping("/api/beschikkingleerlingenvervoers")
@Transactional
public class BeschikkingleerlingenvervoerResource {

    private final Logger log = LoggerFactory.getLogger(BeschikkingleerlingenvervoerResource.class);

    private static final String ENTITY_NAME = "beschikkingleerlingenvervoer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeschikkingleerlingenvervoerRepository beschikkingleerlingenvervoerRepository;

    public BeschikkingleerlingenvervoerResource(BeschikkingleerlingenvervoerRepository beschikkingleerlingenvervoerRepository) {
        this.beschikkingleerlingenvervoerRepository = beschikkingleerlingenvervoerRepository;
    }

    /**
     * {@code POST  /beschikkingleerlingenvervoers} : Create a new beschikkingleerlingenvervoer.
     *
     * @param beschikkingleerlingenvervoer the beschikkingleerlingenvervoer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beschikkingleerlingenvervoer, or with status {@code 400 (Bad Request)} if the beschikkingleerlingenvervoer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beschikkingleerlingenvervoer> createBeschikkingleerlingenvervoer(
        @RequestBody Beschikkingleerlingenvervoer beschikkingleerlingenvervoer
    ) throws URISyntaxException {
        log.debug("REST request to save Beschikkingleerlingenvervoer : {}", beschikkingleerlingenvervoer);
        if (beschikkingleerlingenvervoer.getId() != null) {
            throw new BadRequestAlertException("A new beschikkingleerlingenvervoer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beschikkingleerlingenvervoer = beschikkingleerlingenvervoerRepository.save(beschikkingleerlingenvervoer);
        return ResponseEntity.created(new URI("/api/beschikkingleerlingenvervoers/" + beschikkingleerlingenvervoer.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beschikkingleerlingenvervoer.getId().toString())
            )
            .body(beschikkingleerlingenvervoer);
    }

    /**
     * {@code GET  /beschikkingleerlingenvervoers} : get all the beschikkingleerlingenvervoers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beschikkingleerlingenvervoers in body.
     */
    @GetMapping("")
    public List<Beschikkingleerlingenvervoer> getAllBeschikkingleerlingenvervoers() {
        log.debug("REST request to get all Beschikkingleerlingenvervoers");
        return beschikkingleerlingenvervoerRepository.findAll();
    }

    /**
     * {@code GET  /beschikkingleerlingenvervoers/:id} : get the "id" beschikkingleerlingenvervoer.
     *
     * @param id the id of the beschikkingleerlingenvervoer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beschikkingleerlingenvervoer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beschikkingleerlingenvervoer> getBeschikkingleerlingenvervoer(@PathVariable("id") Long id) {
        log.debug("REST request to get Beschikkingleerlingenvervoer : {}", id);
        Optional<Beschikkingleerlingenvervoer> beschikkingleerlingenvervoer = beschikkingleerlingenvervoerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beschikkingleerlingenvervoer);
    }

    /**
     * {@code DELETE  /beschikkingleerlingenvervoers/:id} : delete the "id" beschikkingleerlingenvervoer.
     *
     * @param id the id of the beschikkingleerlingenvervoer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeschikkingleerlingenvervoer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beschikkingleerlingenvervoer : {}", id);
        beschikkingleerlingenvervoerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
