package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Storing;
import nl.ritense.demo.repository.StoringRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Storing}.
 */
@RestController
@RequestMapping("/api/storings")
@Transactional
public class StoringResource {

    private final Logger log = LoggerFactory.getLogger(StoringResource.class);

    private static final String ENTITY_NAME = "storing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StoringRepository storingRepository;

    public StoringResource(StoringRepository storingRepository) {
        this.storingRepository = storingRepository;
    }

    /**
     * {@code POST  /storings} : Create a new storing.
     *
     * @param storing the storing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storing, or with status {@code 400 (Bad Request)} if the storing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Storing> createStoring(@RequestBody Storing storing) throws URISyntaxException {
        log.debug("REST request to save Storing : {}", storing);
        if (storing.getId() != null) {
            throw new BadRequestAlertException("A new storing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        storing = storingRepository.save(storing);
        return ResponseEntity.created(new URI("/api/storings/" + storing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, storing.getId().toString()))
            .body(storing);
    }

    /**
     * {@code GET  /storings} : get all the storings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storings in body.
     */
    @GetMapping("")
    public List<Storing> getAllStorings() {
        log.debug("REST request to get all Storings");
        return storingRepository.findAll();
    }

    /**
     * {@code GET  /storings/:id} : get the "id" storing.
     *
     * @param id the id of the storing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Storing> getStoring(@PathVariable("id") Long id) {
        log.debug("REST request to get Storing : {}", id);
        Optional<Storing> storing = storingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(storing);
    }

    /**
     * {@code DELETE  /storings/:id} : delete the "id" storing.
     *
     * @param id the id of the storing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoring(@PathVariable("id") Long id) {
        log.debug("REST request to delete Storing : {}", id);
        storingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
