package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjecttypeg;
import nl.ritense.demo.repository.EobjecttypegRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjecttypeg}.
 */
@RestController
@RequestMapping("/api/eobjecttypegs")
@Transactional
public class EobjecttypegResource {

    private final Logger log = LoggerFactory.getLogger(EobjecttypegResource.class);

    private static final String ENTITY_NAME = "eobjecttypeg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjecttypegRepository eobjecttypegRepository;

    public EobjecttypegResource(EobjecttypegRepository eobjecttypegRepository) {
        this.eobjecttypegRepository = eobjecttypegRepository;
    }

    /**
     * {@code POST  /eobjecttypegs} : Create a new eobjecttypeg.
     *
     * @param eobjecttypeg the eobjecttypeg to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjecttypeg, or with status {@code 400 (Bad Request)} if the eobjecttypeg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjecttypeg> createEobjecttypeg(@RequestBody Eobjecttypeg eobjecttypeg) throws URISyntaxException {
        log.debug("REST request to save Eobjecttypeg : {}", eobjecttypeg);
        if (eobjecttypeg.getId() != null) {
            throw new BadRequestAlertException("A new eobjecttypeg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjecttypeg = eobjecttypegRepository.save(eobjecttypeg);
        return ResponseEntity.created(new URI("/api/eobjecttypegs/" + eobjecttypeg.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjecttypeg.getId().toString()))
            .body(eobjecttypeg);
    }

    /**
     * {@code GET  /eobjecttypegs} : get all the eobjecttypegs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjecttypegs in body.
     */
    @GetMapping("")
    public List<Eobjecttypeg> getAllEobjecttypegs() {
        log.debug("REST request to get all Eobjecttypegs");
        return eobjecttypegRepository.findAll();
    }

    /**
     * {@code GET  /eobjecttypegs/:id} : get the "id" eobjecttypeg.
     *
     * @param id the id of the eobjecttypeg to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjecttypeg, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjecttypeg> getEobjecttypeg(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobjecttypeg : {}", id);
        Optional<Eobjecttypeg> eobjecttypeg = eobjecttypegRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjecttypeg);
    }

    /**
     * {@code DELETE  /eobjecttypegs/:id} : delete the "id" eobjecttypeg.
     *
     * @param id the id of the eobjecttypeg to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjecttypeg(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobjecttypeg : {}", id);
        eobjecttypegRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
