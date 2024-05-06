package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Relatiesoort;
import nl.ritense.demo.repository.RelatiesoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Relatiesoort}.
 */
@RestController
@RequestMapping("/api/relatiesoorts")
@Transactional
public class RelatiesoortResource {

    private final Logger log = LoggerFactory.getLogger(RelatiesoortResource.class);

    private static final String ENTITY_NAME = "relatiesoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelatiesoortRepository relatiesoortRepository;

    public RelatiesoortResource(RelatiesoortRepository relatiesoortRepository) {
        this.relatiesoortRepository = relatiesoortRepository;
    }

    /**
     * {@code POST  /relatiesoorts} : Create a new relatiesoort.
     *
     * @param relatiesoort the relatiesoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relatiesoort, or with status {@code 400 (Bad Request)} if the relatiesoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Relatiesoort> createRelatiesoort(@Valid @RequestBody Relatiesoort relatiesoort) throws URISyntaxException {
        log.debug("REST request to save Relatiesoort : {}", relatiesoort);
        if (relatiesoort.getId() != null) {
            throw new BadRequestAlertException("A new relatiesoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        relatiesoort = relatiesoortRepository.save(relatiesoort);
        return ResponseEntity.created(new URI("/api/relatiesoorts/" + relatiesoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, relatiesoort.getId().toString()))
            .body(relatiesoort);
    }

    /**
     * {@code GET  /relatiesoorts} : get all the relatiesoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relatiesoorts in body.
     */
    @GetMapping("")
    public List<Relatiesoort> getAllRelatiesoorts() {
        log.debug("REST request to get all Relatiesoorts");
        return relatiesoortRepository.findAll();
    }

    /**
     * {@code GET  /relatiesoorts/:id} : get the "id" relatiesoort.
     *
     * @param id the id of the relatiesoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relatiesoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Relatiesoort> getRelatiesoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Relatiesoort : {}", id);
        Optional<Relatiesoort> relatiesoort = relatiesoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(relatiesoort);
    }

    /**
     * {@code DELETE  /relatiesoorts/:id} : delete the "id" relatiesoort.
     *
     * @param id the id of the relatiesoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelatiesoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Relatiesoort : {}", id);
        relatiesoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
