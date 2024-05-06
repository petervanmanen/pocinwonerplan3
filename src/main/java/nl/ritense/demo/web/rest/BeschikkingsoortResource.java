package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Beschikkingsoort;
import nl.ritense.demo.repository.BeschikkingsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beschikkingsoort}.
 */
@RestController
@RequestMapping("/api/beschikkingsoorts")
@Transactional
public class BeschikkingsoortResource {

    private final Logger log = LoggerFactory.getLogger(BeschikkingsoortResource.class);

    private static final String ENTITY_NAME = "beschikkingsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeschikkingsoortRepository beschikkingsoortRepository;

    public BeschikkingsoortResource(BeschikkingsoortRepository beschikkingsoortRepository) {
        this.beschikkingsoortRepository = beschikkingsoortRepository;
    }

    /**
     * {@code POST  /beschikkingsoorts} : Create a new beschikkingsoort.
     *
     * @param beschikkingsoort the beschikkingsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beschikkingsoort, or with status {@code 400 (Bad Request)} if the beschikkingsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beschikkingsoort> createBeschikkingsoort(@RequestBody Beschikkingsoort beschikkingsoort)
        throws URISyntaxException {
        log.debug("REST request to save Beschikkingsoort : {}", beschikkingsoort);
        if (beschikkingsoort.getId() != null) {
            throw new BadRequestAlertException("A new beschikkingsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beschikkingsoort = beschikkingsoortRepository.save(beschikkingsoort);
        return ResponseEntity.created(new URI("/api/beschikkingsoorts/" + beschikkingsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beschikkingsoort.getId().toString()))
            .body(beschikkingsoort);
    }

    /**
     * {@code GET  /beschikkingsoorts} : get all the beschikkingsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beschikkingsoorts in body.
     */
    @GetMapping("")
    public List<Beschikkingsoort> getAllBeschikkingsoorts() {
        log.debug("REST request to get all Beschikkingsoorts");
        return beschikkingsoortRepository.findAll();
    }

    /**
     * {@code GET  /beschikkingsoorts/:id} : get the "id" beschikkingsoort.
     *
     * @param id the id of the beschikkingsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beschikkingsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beschikkingsoort> getBeschikkingsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Beschikkingsoort : {}", id);
        Optional<Beschikkingsoort> beschikkingsoort = beschikkingsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beschikkingsoort);
    }

    /**
     * {@code DELETE  /beschikkingsoorts/:id} : delete the "id" beschikkingsoort.
     *
     * @param id the id of the beschikkingsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeschikkingsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beschikkingsoort : {}", id);
        beschikkingsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
