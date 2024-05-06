package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Ouderofverzorger;
import nl.ritense.demo.repository.OuderofverzorgerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ouderofverzorger}.
 */
@RestController
@RequestMapping("/api/ouderofverzorgers")
@Transactional
public class OuderofverzorgerResource {

    private final Logger log = LoggerFactory.getLogger(OuderofverzorgerResource.class);

    private static final String ENTITY_NAME = "ouderofverzorger";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OuderofverzorgerRepository ouderofverzorgerRepository;

    public OuderofverzorgerResource(OuderofverzorgerRepository ouderofverzorgerRepository) {
        this.ouderofverzorgerRepository = ouderofverzorgerRepository;
    }

    /**
     * {@code POST  /ouderofverzorgers} : Create a new ouderofverzorger.
     *
     * @param ouderofverzorger the ouderofverzorger to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ouderofverzorger, or with status {@code 400 (Bad Request)} if the ouderofverzorger has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ouderofverzorger> createOuderofverzorger(@RequestBody Ouderofverzorger ouderofverzorger)
        throws URISyntaxException {
        log.debug("REST request to save Ouderofverzorger : {}", ouderofverzorger);
        if (ouderofverzorger.getId() != null) {
            throw new BadRequestAlertException("A new ouderofverzorger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ouderofverzorger = ouderofverzorgerRepository.save(ouderofverzorger);
        return ResponseEntity.created(new URI("/api/ouderofverzorgers/" + ouderofverzorger.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ouderofverzorger.getId().toString()))
            .body(ouderofverzorger);
    }

    /**
     * {@code GET  /ouderofverzorgers} : get all the ouderofverzorgers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ouderofverzorgers in body.
     */
    @GetMapping("")
    public List<Ouderofverzorger> getAllOuderofverzorgers() {
        log.debug("REST request to get all Ouderofverzorgers");
        return ouderofverzorgerRepository.findAll();
    }

    /**
     * {@code GET  /ouderofverzorgers/:id} : get the "id" ouderofverzorger.
     *
     * @param id the id of the ouderofverzorger to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ouderofverzorger, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ouderofverzorger> getOuderofverzorger(@PathVariable("id") Long id) {
        log.debug("REST request to get Ouderofverzorger : {}", id);
        Optional<Ouderofverzorger> ouderofverzorger = ouderofverzorgerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ouderofverzorger);
    }

    /**
     * {@code DELETE  /ouderofverzorgers/:id} : delete the "id" ouderofverzorger.
     *
     * @param id the id of the ouderofverzorger to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOuderofverzorger(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ouderofverzorger : {}", id);
        ouderofverzorgerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
