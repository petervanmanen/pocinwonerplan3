package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Bredeintake;
import nl.ritense.demo.repository.BredeintakeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bredeintake}.
 */
@RestController
@RequestMapping("/api/bredeintakes")
@Transactional
public class BredeintakeResource {

    private final Logger log = LoggerFactory.getLogger(BredeintakeResource.class);

    private static final String ENTITY_NAME = "bredeintake";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BredeintakeRepository bredeintakeRepository;

    public BredeintakeResource(BredeintakeRepository bredeintakeRepository) {
        this.bredeintakeRepository = bredeintakeRepository;
    }

    /**
     * {@code POST  /bredeintakes} : Create a new bredeintake.
     *
     * @param bredeintake the bredeintake to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bredeintake, or with status {@code 400 (Bad Request)} if the bredeintake has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bredeintake> createBredeintake(@RequestBody Bredeintake bredeintake) throws URISyntaxException {
        log.debug("REST request to save Bredeintake : {}", bredeintake);
        if (bredeintake.getId() != null) {
            throw new BadRequestAlertException("A new bredeintake cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bredeintake = bredeintakeRepository.save(bredeintake);
        return ResponseEntity.created(new URI("/api/bredeintakes/" + bredeintake.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bredeintake.getId().toString()))
            .body(bredeintake);
    }

    /**
     * {@code GET  /bredeintakes} : get all the bredeintakes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bredeintakes in body.
     */
    @GetMapping("")
    public List<Bredeintake> getAllBredeintakes() {
        log.debug("REST request to get all Bredeintakes");
        return bredeintakeRepository.findAll();
    }

    /**
     * {@code GET  /bredeintakes/:id} : get the "id" bredeintake.
     *
     * @param id the id of the bredeintake to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bredeintake, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bredeintake> getBredeintake(@PathVariable("id") Long id) {
        log.debug("REST request to get Bredeintake : {}", id);
        Optional<Bredeintake> bredeintake = bredeintakeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bredeintake);
    }

    /**
     * {@code DELETE  /bredeintakes/:id} : delete the "id" bredeintake.
     *
     * @param id the id of the bredeintake to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBredeintake(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bredeintake : {}", id);
        bredeintakeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
