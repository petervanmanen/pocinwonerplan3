package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Faseoplevering;
import nl.ritense.demo.repository.FaseopleveringRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Faseoplevering}.
 */
@RestController
@RequestMapping("/api/faseopleverings")
@Transactional
public class FaseopleveringResource {

    private final Logger log = LoggerFactory.getLogger(FaseopleveringResource.class);

    private static final String ENTITY_NAME = "faseoplevering";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FaseopleveringRepository faseopleveringRepository;

    public FaseopleveringResource(FaseopleveringRepository faseopleveringRepository) {
        this.faseopleveringRepository = faseopleveringRepository;
    }

    /**
     * {@code POST  /faseopleverings} : Create a new faseoplevering.
     *
     * @param faseoplevering the faseoplevering to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faseoplevering, or with status {@code 400 (Bad Request)} if the faseoplevering has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Faseoplevering> createFaseoplevering(@RequestBody Faseoplevering faseoplevering) throws URISyntaxException {
        log.debug("REST request to save Faseoplevering : {}", faseoplevering);
        if (faseoplevering.getId() != null) {
            throw new BadRequestAlertException("A new faseoplevering cannot already have an ID", ENTITY_NAME, "idexists");
        }
        faseoplevering = faseopleveringRepository.save(faseoplevering);
        return ResponseEntity.created(new URI("/api/faseopleverings/" + faseoplevering.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, faseoplevering.getId().toString()))
            .body(faseoplevering);
    }

    /**
     * {@code GET  /faseopleverings} : get all the faseopleverings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faseopleverings in body.
     */
    @GetMapping("")
    public List<Faseoplevering> getAllFaseopleverings() {
        log.debug("REST request to get all Faseopleverings");
        return faseopleveringRepository.findAll();
    }

    /**
     * {@code GET  /faseopleverings/:id} : get the "id" faseoplevering.
     *
     * @param id the id of the faseoplevering to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the faseoplevering, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Faseoplevering> getFaseoplevering(@PathVariable("id") Long id) {
        log.debug("REST request to get Faseoplevering : {}", id);
        Optional<Faseoplevering> faseoplevering = faseopleveringRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(faseoplevering);
    }

    /**
     * {@code DELETE  /faseopleverings/:id} : delete the "id" faseoplevering.
     *
     * @param id the id of the faseoplevering to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaseoplevering(@PathVariable("id") Long id) {
        log.debug("REST request to delete Faseoplevering : {}", id);
        faseopleveringRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
