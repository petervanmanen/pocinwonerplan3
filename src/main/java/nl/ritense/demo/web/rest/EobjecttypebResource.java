package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjecttypeb;
import nl.ritense.demo.repository.EobjecttypebRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjecttypeb}.
 */
@RestController
@RequestMapping("/api/eobjecttypebs")
@Transactional
public class EobjecttypebResource {

    private final Logger log = LoggerFactory.getLogger(EobjecttypebResource.class);

    private static final String ENTITY_NAME = "eobjecttypeb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjecttypebRepository eobjecttypebRepository;

    public EobjecttypebResource(EobjecttypebRepository eobjecttypebRepository) {
        this.eobjecttypebRepository = eobjecttypebRepository;
    }

    /**
     * {@code POST  /eobjecttypebs} : Create a new eobjecttypeb.
     *
     * @param eobjecttypeb the eobjecttypeb to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjecttypeb, or with status {@code 400 (Bad Request)} if the eobjecttypeb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjecttypeb> createEobjecttypeb(@RequestBody Eobjecttypeb eobjecttypeb) throws URISyntaxException {
        log.debug("REST request to save Eobjecttypeb : {}", eobjecttypeb);
        if (eobjecttypeb.getId() != null) {
            throw new BadRequestAlertException("A new eobjecttypeb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjecttypeb = eobjecttypebRepository.save(eobjecttypeb);
        return ResponseEntity.created(new URI("/api/eobjecttypebs/" + eobjecttypeb.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjecttypeb.getId().toString()))
            .body(eobjecttypeb);
    }

    /**
     * {@code GET  /eobjecttypebs} : get all the eobjecttypebs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjecttypebs in body.
     */
    @GetMapping("")
    public List<Eobjecttypeb> getAllEobjecttypebs() {
        log.debug("REST request to get all Eobjecttypebs");
        return eobjecttypebRepository.findAll();
    }

    /**
     * {@code GET  /eobjecttypebs/:id} : get the "id" eobjecttypeb.
     *
     * @param id the id of the eobjecttypeb to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjecttypeb, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjecttypeb> getEobjecttypeb(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobjecttypeb : {}", id);
        Optional<Eobjecttypeb> eobjecttypeb = eobjecttypebRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjecttypeb);
    }

    /**
     * {@code DELETE  /eobjecttypebs/:id} : delete the "id" eobjecttypeb.
     *
     * @param id the id of the eobjecttypeb to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjecttypeb(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobjecttypeb : {}", id);
        eobjecttypebRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
