package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjecttyped;
import nl.ritense.demo.repository.EobjecttypedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjecttyped}.
 */
@RestController
@RequestMapping("/api/eobjecttypeds")
@Transactional
public class EobjecttypedResource {

    private final Logger log = LoggerFactory.getLogger(EobjecttypedResource.class);

    private static final String ENTITY_NAME = "eobjecttyped";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjecttypedRepository eobjecttypedRepository;

    public EobjecttypedResource(EobjecttypedRepository eobjecttypedRepository) {
        this.eobjecttypedRepository = eobjecttypedRepository;
    }

    /**
     * {@code POST  /eobjecttypeds} : Create a new eobjecttyped.
     *
     * @param eobjecttyped the eobjecttyped to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjecttyped, or with status {@code 400 (Bad Request)} if the eobjecttyped has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjecttyped> createEobjecttyped(@RequestBody Eobjecttyped eobjecttyped) throws URISyntaxException {
        log.debug("REST request to save Eobjecttyped : {}", eobjecttyped);
        if (eobjecttyped.getId() != null) {
            throw new BadRequestAlertException("A new eobjecttyped cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjecttyped = eobjecttypedRepository.save(eobjecttyped);
        return ResponseEntity.created(new URI("/api/eobjecttypeds/" + eobjecttyped.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjecttyped.getId().toString()))
            .body(eobjecttyped);
    }

    /**
     * {@code GET  /eobjecttypeds} : get all the eobjecttypeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjecttypeds in body.
     */
    @GetMapping("")
    public List<Eobjecttyped> getAllEobjecttypeds() {
        log.debug("REST request to get all Eobjecttypeds");
        return eobjecttypedRepository.findAll();
    }

    /**
     * {@code GET  /eobjecttypeds/:id} : get the "id" eobjecttyped.
     *
     * @param id the id of the eobjecttyped to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjecttyped, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjecttyped> getEobjecttyped(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobjecttyped : {}", id);
        Optional<Eobjecttyped> eobjecttyped = eobjecttypedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjecttyped);
    }

    /**
     * {@code DELETE  /eobjecttypeds/:id} : delete the "id" eobjecttyped.
     *
     * @param id the id of the eobjecttyped to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjecttyped(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobjecttyped : {}", id);
        eobjecttypedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
