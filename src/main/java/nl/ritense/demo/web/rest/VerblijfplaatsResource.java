package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfplaats;
import nl.ritense.demo.repository.VerblijfplaatsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verblijfplaats}.
 */
@RestController
@RequestMapping("/api/verblijfplaats")
@Transactional
public class VerblijfplaatsResource {

    private final Logger log = LoggerFactory.getLogger(VerblijfplaatsResource.class);

    private static final String ENTITY_NAME = "verblijfplaats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerblijfplaatsRepository verblijfplaatsRepository;

    public VerblijfplaatsResource(VerblijfplaatsRepository verblijfplaatsRepository) {
        this.verblijfplaatsRepository = verblijfplaatsRepository;
    }

    /**
     * {@code POST  /verblijfplaats} : Create a new verblijfplaats.
     *
     * @param verblijfplaats the verblijfplaats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verblijfplaats, or with status {@code 400 (Bad Request)} if the verblijfplaats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verblijfplaats> createVerblijfplaats(@RequestBody Verblijfplaats verblijfplaats) throws URISyntaxException {
        log.debug("REST request to save Verblijfplaats : {}", verblijfplaats);
        if (verblijfplaats.getId() != null) {
            throw new BadRequestAlertException("A new verblijfplaats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verblijfplaats = verblijfplaatsRepository.save(verblijfplaats);
        return ResponseEntity.created(new URI("/api/verblijfplaats/" + verblijfplaats.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verblijfplaats.getId().toString()))
            .body(verblijfplaats);
    }

    /**
     * {@code GET  /verblijfplaats} : get all the verblijfplaats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verblijfplaats in body.
     */
    @GetMapping("")
    public List<Verblijfplaats> getAllVerblijfplaats() {
        log.debug("REST request to get all Verblijfplaats");
        return verblijfplaatsRepository.findAll();
    }

    /**
     * {@code GET  /verblijfplaats/:id} : get the "id" verblijfplaats.
     *
     * @param id the id of the verblijfplaats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verblijfplaats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verblijfplaats> getVerblijfplaats(@PathVariable("id") Long id) {
        log.debug("REST request to get Verblijfplaats : {}", id);
        Optional<Verblijfplaats> verblijfplaats = verblijfplaatsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verblijfplaats);
    }

    /**
     * {@code DELETE  /verblijfplaats/:id} : delete the "id" verblijfplaats.
     *
     * @param id the id of the verblijfplaats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerblijfplaats(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verblijfplaats : {}", id);
        verblijfplaatsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
