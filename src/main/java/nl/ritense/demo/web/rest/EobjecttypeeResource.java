package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjecttypee;
import nl.ritense.demo.repository.EobjecttypeeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjecttypee}.
 */
@RestController
@RequestMapping("/api/eobjecttypees")
@Transactional
public class EobjecttypeeResource {

    private final Logger log = LoggerFactory.getLogger(EobjecttypeeResource.class);

    private static final String ENTITY_NAME = "eobjecttypee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjecttypeeRepository eobjecttypeeRepository;

    public EobjecttypeeResource(EobjecttypeeRepository eobjecttypeeRepository) {
        this.eobjecttypeeRepository = eobjecttypeeRepository;
    }

    /**
     * {@code POST  /eobjecttypees} : Create a new eobjecttypee.
     *
     * @param eobjecttypee the eobjecttypee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjecttypee, or with status {@code 400 (Bad Request)} if the eobjecttypee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjecttypee> createEobjecttypee(@RequestBody Eobjecttypee eobjecttypee) throws URISyntaxException {
        log.debug("REST request to save Eobjecttypee : {}", eobjecttypee);
        if (eobjecttypee.getId() != null) {
            throw new BadRequestAlertException("A new eobjecttypee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjecttypee = eobjecttypeeRepository.save(eobjecttypee);
        return ResponseEntity.created(new URI("/api/eobjecttypees/" + eobjecttypee.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjecttypee.getId().toString()))
            .body(eobjecttypee);
    }

    /**
     * {@code GET  /eobjecttypees} : get all the eobjecttypees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjecttypees in body.
     */
    @GetMapping("")
    public List<Eobjecttypee> getAllEobjecttypees() {
        log.debug("REST request to get all Eobjecttypees");
        return eobjecttypeeRepository.findAll();
    }

    /**
     * {@code GET  /eobjecttypees/:id} : get the "id" eobjecttypee.
     *
     * @param id the id of the eobjecttypee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjecttypee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjecttypee> getEobjecttypee(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobjecttypee : {}", id);
        Optional<Eobjecttypee> eobjecttypee = eobjecttypeeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjecttypee);
    }

    /**
     * {@code DELETE  /eobjecttypees/:id} : delete the "id" eobjecttypee.
     *
     * @param id the id of the eobjecttypee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjecttypee(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobjecttypee : {}", id);
        eobjecttypeeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
