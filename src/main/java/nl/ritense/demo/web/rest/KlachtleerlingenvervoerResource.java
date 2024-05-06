package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Klachtleerlingenvervoer;
import nl.ritense.demo.repository.KlachtleerlingenvervoerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Klachtleerlingenvervoer}.
 */
@RestController
@RequestMapping("/api/klachtleerlingenvervoers")
@Transactional
public class KlachtleerlingenvervoerResource {

    private final Logger log = LoggerFactory.getLogger(KlachtleerlingenvervoerResource.class);

    private static final String ENTITY_NAME = "klachtleerlingenvervoer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KlachtleerlingenvervoerRepository klachtleerlingenvervoerRepository;

    public KlachtleerlingenvervoerResource(KlachtleerlingenvervoerRepository klachtleerlingenvervoerRepository) {
        this.klachtleerlingenvervoerRepository = klachtleerlingenvervoerRepository;
    }

    /**
     * {@code POST  /klachtleerlingenvervoers} : Create a new klachtleerlingenvervoer.
     *
     * @param klachtleerlingenvervoer the klachtleerlingenvervoer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new klachtleerlingenvervoer, or with status {@code 400 (Bad Request)} if the klachtleerlingenvervoer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Klachtleerlingenvervoer> createKlachtleerlingenvervoer(
        @RequestBody Klachtleerlingenvervoer klachtleerlingenvervoer
    ) throws URISyntaxException {
        log.debug("REST request to save Klachtleerlingenvervoer : {}", klachtleerlingenvervoer);
        if (klachtleerlingenvervoer.getId() != null) {
            throw new BadRequestAlertException("A new klachtleerlingenvervoer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        klachtleerlingenvervoer = klachtleerlingenvervoerRepository.save(klachtleerlingenvervoer);
        return ResponseEntity.created(new URI("/api/klachtleerlingenvervoers/" + klachtleerlingenvervoer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, klachtleerlingenvervoer.getId().toString()))
            .body(klachtleerlingenvervoer);
    }

    /**
     * {@code GET  /klachtleerlingenvervoers} : get all the klachtleerlingenvervoers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of klachtleerlingenvervoers in body.
     */
    @GetMapping("")
    public List<Klachtleerlingenvervoer> getAllKlachtleerlingenvervoers() {
        log.debug("REST request to get all Klachtleerlingenvervoers");
        return klachtleerlingenvervoerRepository.findAll();
    }

    /**
     * {@code GET  /klachtleerlingenvervoers/:id} : get the "id" klachtleerlingenvervoer.
     *
     * @param id the id of the klachtleerlingenvervoer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the klachtleerlingenvervoer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Klachtleerlingenvervoer> getKlachtleerlingenvervoer(@PathVariable("id") Long id) {
        log.debug("REST request to get Klachtleerlingenvervoer : {}", id);
        Optional<Klachtleerlingenvervoer> klachtleerlingenvervoer = klachtleerlingenvervoerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(klachtleerlingenvervoer);
    }

    /**
     * {@code DELETE  /klachtleerlingenvervoers/:id} : delete the "id" klachtleerlingenvervoer.
     *
     * @param id the id of the klachtleerlingenvervoer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKlachtleerlingenvervoer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Klachtleerlingenvervoer : {}", id);
        klachtleerlingenvervoerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
