package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjecttypef;
import nl.ritense.demo.repository.EobjecttypefRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjecttypef}.
 */
@RestController
@RequestMapping("/api/eobjecttypefs")
@Transactional
public class EobjecttypefResource {

    private final Logger log = LoggerFactory.getLogger(EobjecttypefResource.class);

    private static final String ENTITY_NAME = "eobjecttypef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjecttypefRepository eobjecttypefRepository;

    public EobjecttypefResource(EobjecttypefRepository eobjecttypefRepository) {
        this.eobjecttypefRepository = eobjecttypefRepository;
    }

    /**
     * {@code POST  /eobjecttypefs} : Create a new eobjecttypef.
     *
     * @param eobjecttypef the eobjecttypef to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjecttypef, or with status {@code 400 (Bad Request)} if the eobjecttypef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjecttypef> createEobjecttypef(@RequestBody Eobjecttypef eobjecttypef) throws URISyntaxException {
        log.debug("REST request to save Eobjecttypef : {}", eobjecttypef);
        if (eobjecttypef.getId() != null) {
            throw new BadRequestAlertException("A new eobjecttypef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjecttypef = eobjecttypefRepository.save(eobjecttypef);
        return ResponseEntity.created(new URI("/api/eobjecttypefs/" + eobjecttypef.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjecttypef.getId().toString()))
            .body(eobjecttypef);
    }

    /**
     * {@code GET  /eobjecttypefs} : get all the eobjecttypefs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjecttypefs in body.
     */
    @GetMapping("")
    public List<Eobjecttypef> getAllEobjecttypefs() {
        log.debug("REST request to get all Eobjecttypefs");
        return eobjecttypefRepository.findAll();
    }

    /**
     * {@code GET  /eobjecttypefs/:id} : get the "id" eobjecttypef.
     *
     * @param id the id of the eobjecttypef to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjecttypef, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjecttypef> getEobjecttypef(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobjecttypef : {}", id);
        Optional<Eobjecttypef> eobjecttypef = eobjecttypefRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjecttypef);
    }

    /**
     * {@code DELETE  /eobjecttypefs/:id} : delete the "id" eobjecttypef.
     *
     * @param id the id of the eobjecttypef to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjecttypef(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobjecttypef : {}", id);
        eobjecttypefRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
