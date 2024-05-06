package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Pachter;
import nl.ritense.demo.repository.PachterRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Pachter}.
 */
@RestController
@RequestMapping("/api/pachters")
@Transactional
public class PachterResource {

    private final Logger log = LoggerFactory.getLogger(PachterResource.class);

    private static final String ENTITY_NAME = "pachter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PachterRepository pachterRepository;

    public PachterResource(PachterRepository pachterRepository) {
        this.pachterRepository = pachterRepository;
    }

    /**
     * {@code POST  /pachters} : Create a new pachter.
     *
     * @param pachter the pachter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pachter, or with status {@code 400 (Bad Request)} if the pachter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pachter> createPachter(@RequestBody Pachter pachter) throws URISyntaxException {
        log.debug("REST request to save Pachter : {}", pachter);
        if (pachter.getId() != null) {
            throw new BadRequestAlertException("A new pachter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pachter = pachterRepository.save(pachter);
        return ResponseEntity.created(new URI("/api/pachters/" + pachter.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, pachter.getId().toString()))
            .body(pachter);
    }

    /**
     * {@code GET  /pachters} : get all the pachters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pachters in body.
     */
    @GetMapping("")
    public List<Pachter> getAllPachters() {
        log.debug("REST request to get all Pachters");
        return pachterRepository.findAll();
    }

    /**
     * {@code GET  /pachters/:id} : get the "id" pachter.
     *
     * @param id the id of the pachter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pachter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pachter> getPachter(@PathVariable("id") Long id) {
        log.debug("REST request to get Pachter : {}", id);
        Optional<Pachter> pachter = pachterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pachter);
    }

    /**
     * {@code DELETE  /pachters/:id} : delete the "id" pachter.
     *
     * @param id the id of the pachter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePachter(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pachter : {}", id);
        pachterRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
