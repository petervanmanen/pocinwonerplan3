package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfplaatsazc;
import nl.ritense.demo.repository.VerblijfplaatsazcRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verblijfplaatsazc}.
 */
@RestController
@RequestMapping("/api/verblijfplaatsazcs")
@Transactional
public class VerblijfplaatsazcResource {

    private final Logger log = LoggerFactory.getLogger(VerblijfplaatsazcResource.class);

    private static final String ENTITY_NAME = "verblijfplaatsazc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerblijfplaatsazcRepository verblijfplaatsazcRepository;

    public VerblijfplaatsazcResource(VerblijfplaatsazcRepository verblijfplaatsazcRepository) {
        this.verblijfplaatsazcRepository = verblijfplaatsazcRepository;
    }

    /**
     * {@code POST  /verblijfplaatsazcs} : Create a new verblijfplaatsazc.
     *
     * @param verblijfplaatsazc the verblijfplaatsazc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verblijfplaatsazc, or with status {@code 400 (Bad Request)} if the verblijfplaatsazc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verblijfplaatsazc> createVerblijfplaatsazc(@RequestBody Verblijfplaatsazc verblijfplaatsazc)
        throws URISyntaxException {
        log.debug("REST request to save Verblijfplaatsazc : {}", verblijfplaatsazc);
        if (verblijfplaatsazc.getId() != null) {
            throw new BadRequestAlertException("A new verblijfplaatsazc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verblijfplaatsazc = verblijfplaatsazcRepository.save(verblijfplaatsazc);
        return ResponseEntity.created(new URI("/api/verblijfplaatsazcs/" + verblijfplaatsazc.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verblijfplaatsazc.getId().toString()))
            .body(verblijfplaatsazc);
    }

    /**
     * {@code GET  /verblijfplaatsazcs} : get all the verblijfplaatsazcs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verblijfplaatsazcs in body.
     */
    @GetMapping("")
    public List<Verblijfplaatsazc> getAllVerblijfplaatsazcs() {
        log.debug("REST request to get all Verblijfplaatsazcs");
        return verblijfplaatsazcRepository.findAll();
    }

    /**
     * {@code GET  /verblijfplaatsazcs/:id} : get the "id" verblijfplaatsazc.
     *
     * @param id the id of the verblijfplaatsazc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verblijfplaatsazc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verblijfplaatsazc> getVerblijfplaatsazc(@PathVariable("id") Long id) {
        log.debug("REST request to get Verblijfplaatsazc : {}", id);
        Optional<Verblijfplaatsazc> verblijfplaatsazc = verblijfplaatsazcRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verblijfplaatsazc);
    }

    /**
     * {@code DELETE  /verblijfplaatsazcs/:id} : delete the "id" verblijfplaatsazc.
     *
     * @param id the id of the verblijfplaatsazc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerblijfplaatsazc(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verblijfplaatsazc : {}", id);
        verblijfplaatsazcRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
