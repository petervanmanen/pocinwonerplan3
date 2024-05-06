package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Verlichtingsobject;
import nl.ritense.demo.repository.VerlichtingsobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verlichtingsobject}.
 */
@RestController
@RequestMapping("/api/verlichtingsobjects")
@Transactional
public class VerlichtingsobjectResource {

    private final Logger log = LoggerFactory.getLogger(VerlichtingsobjectResource.class);

    private static final String ENTITY_NAME = "verlichtingsobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerlichtingsobjectRepository verlichtingsobjectRepository;

    public VerlichtingsobjectResource(VerlichtingsobjectRepository verlichtingsobjectRepository) {
        this.verlichtingsobjectRepository = verlichtingsobjectRepository;
    }

    /**
     * {@code POST  /verlichtingsobjects} : Create a new verlichtingsobject.
     *
     * @param verlichtingsobject the verlichtingsobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verlichtingsobject, or with status {@code 400 (Bad Request)} if the verlichtingsobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verlichtingsobject> createVerlichtingsobject(@RequestBody Verlichtingsobject verlichtingsobject)
        throws URISyntaxException {
        log.debug("REST request to save Verlichtingsobject : {}", verlichtingsobject);
        if (verlichtingsobject.getId() != null) {
            throw new BadRequestAlertException("A new verlichtingsobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verlichtingsobject = verlichtingsobjectRepository.save(verlichtingsobject);
        return ResponseEntity.created(new URI("/api/verlichtingsobjects/" + verlichtingsobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verlichtingsobject.getId().toString()))
            .body(verlichtingsobject);
    }

    /**
     * {@code GET  /verlichtingsobjects} : get all the verlichtingsobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verlichtingsobjects in body.
     */
    @GetMapping("")
    public List<Verlichtingsobject> getAllVerlichtingsobjects() {
        log.debug("REST request to get all Verlichtingsobjects");
        return verlichtingsobjectRepository.findAll();
    }

    /**
     * {@code GET  /verlichtingsobjects/:id} : get the "id" verlichtingsobject.
     *
     * @param id the id of the verlichtingsobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verlichtingsobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verlichtingsobject> getVerlichtingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Verlichtingsobject : {}", id);
        Optional<Verlichtingsobject> verlichtingsobject = verlichtingsobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verlichtingsobject);
    }

    /**
     * {@code DELETE  /verlichtingsobjects/:id} : delete the "id" verlichtingsobject.
     *
     * @param id the id of the verlichtingsobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerlichtingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verlichtingsobject : {}", id);
        verlichtingsobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
