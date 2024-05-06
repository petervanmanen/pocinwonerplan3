package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Aanvraagleerlingenvervoer;
import nl.ritense.demo.repository.AanvraagleerlingenvervoerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanvraagleerlingenvervoer}.
 */
@RestController
@RequestMapping("/api/aanvraagleerlingenvervoers")
@Transactional
public class AanvraagleerlingenvervoerResource {

    private final Logger log = LoggerFactory.getLogger(AanvraagleerlingenvervoerResource.class);

    private static final String ENTITY_NAME = "aanvraagleerlingenvervoer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanvraagleerlingenvervoerRepository aanvraagleerlingenvervoerRepository;

    public AanvraagleerlingenvervoerResource(AanvraagleerlingenvervoerRepository aanvraagleerlingenvervoerRepository) {
        this.aanvraagleerlingenvervoerRepository = aanvraagleerlingenvervoerRepository;
    }

    /**
     * {@code POST  /aanvraagleerlingenvervoers} : Create a new aanvraagleerlingenvervoer.
     *
     * @param aanvraagleerlingenvervoer the aanvraagleerlingenvervoer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanvraagleerlingenvervoer, or with status {@code 400 (Bad Request)} if the aanvraagleerlingenvervoer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanvraagleerlingenvervoer> createAanvraagleerlingenvervoer(
        @RequestBody Aanvraagleerlingenvervoer aanvraagleerlingenvervoer
    ) throws URISyntaxException {
        log.debug("REST request to save Aanvraagleerlingenvervoer : {}", aanvraagleerlingenvervoer);
        if (aanvraagleerlingenvervoer.getId() != null) {
            throw new BadRequestAlertException("A new aanvraagleerlingenvervoer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanvraagleerlingenvervoer = aanvraagleerlingenvervoerRepository.save(aanvraagleerlingenvervoer);
        return ResponseEntity.created(new URI("/api/aanvraagleerlingenvervoers/" + aanvraagleerlingenvervoer.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanvraagleerlingenvervoer.getId().toString())
            )
            .body(aanvraagleerlingenvervoer);
    }

    /**
     * {@code GET  /aanvraagleerlingenvervoers} : get all the aanvraagleerlingenvervoers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanvraagleerlingenvervoers in body.
     */
    @GetMapping("")
    public List<Aanvraagleerlingenvervoer> getAllAanvraagleerlingenvervoers() {
        log.debug("REST request to get all Aanvraagleerlingenvervoers");
        return aanvraagleerlingenvervoerRepository.findAll();
    }

    /**
     * {@code GET  /aanvraagleerlingenvervoers/:id} : get the "id" aanvraagleerlingenvervoer.
     *
     * @param id the id of the aanvraagleerlingenvervoer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanvraagleerlingenvervoer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanvraagleerlingenvervoer> getAanvraagleerlingenvervoer(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanvraagleerlingenvervoer : {}", id);
        Optional<Aanvraagleerlingenvervoer> aanvraagleerlingenvervoer = aanvraagleerlingenvervoerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanvraagleerlingenvervoer);
    }

    /**
     * {@code DELETE  /aanvraagleerlingenvervoers/:id} : delete the "id" aanvraagleerlingenvervoer.
     *
     * @param id the id of the aanvraagleerlingenvervoer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanvraagleerlingenvervoer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanvraagleerlingenvervoer : {}", id);
        aanvraagleerlingenvervoerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
