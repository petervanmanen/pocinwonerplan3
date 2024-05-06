package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjecttypec;
import nl.ritense.demo.repository.EobjecttypecRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjecttypec}.
 */
@RestController
@RequestMapping("/api/eobjecttypecs")
@Transactional
public class EobjecttypecResource {

    private final Logger log = LoggerFactory.getLogger(EobjecttypecResource.class);

    private static final String ENTITY_NAME = "eobjecttypec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjecttypecRepository eobjecttypecRepository;

    public EobjecttypecResource(EobjecttypecRepository eobjecttypecRepository) {
        this.eobjecttypecRepository = eobjecttypecRepository;
    }

    /**
     * {@code POST  /eobjecttypecs} : Create a new eobjecttypec.
     *
     * @param eobjecttypec the eobjecttypec to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjecttypec, or with status {@code 400 (Bad Request)} if the eobjecttypec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjecttypec> createEobjecttypec(@RequestBody Eobjecttypec eobjecttypec) throws URISyntaxException {
        log.debug("REST request to save Eobjecttypec : {}", eobjecttypec);
        if (eobjecttypec.getId() != null) {
            throw new BadRequestAlertException("A new eobjecttypec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjecttypec = eobjecttypecRepository.save(eobjecttypec);
        return ResponseEntity.created(new URI("/api/eobjecttypecs/" + eobjecttypec.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjecttypec.getId().toString()))
            .body(eobjecttypec);
    }

    /**
     * {@code GET  /eobjecttypecs} : get all the eobjecttypecs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjecttypecs in body.
     */
    @GetMapping("")
    public List<Eobjecttypec> getAllEobjecttypecs() {
        log.debug("REST request to get all Eobjecttypecs");
        return eobjecttypecRepository.findAll();
    }

    /**
     * {@code GET  /eobjecttypecs/:id} : get the "id" eobjecttypec.
     *
     * @param id the id of the eobjecttypec to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjecttypec, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjecttypec> getEobjecttypec(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobjecttypec : {}", id);
        Optional<Eobjecttypec> eobjecttypec = eobjecttypecRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjecttypec);
    }

    /**
     * {@code DELETE  /eobjecttypecs/:id} : delete the "id" eobjecttypec.
     *
     * @param id the id of the eobjecttypec to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjecttypec(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobjecttypec : {}", id);
        eobjecttypecRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
