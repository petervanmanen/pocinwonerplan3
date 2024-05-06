package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Pip;
import nl.ritense.demo.repository.PipRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Pip}.
 */
@RestController
@RequestMapping("/api/pips")
@Transactional
public class PipResource {

    private final Logger log = LoggerFactory.getLogger(PipResource.class);

    private static final String ENTITY_NAME = "pip";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PipRepository pipRepository;

    public PipResource(PipRepository pipRepository) {
        this.pipRepository = pipRepository;
    }

    /**
     * {@code POST  /pips} : Create a new pip.
     *
     * @param pip the pip to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pip, or with status {@code 400 (Bad Request)} if the pip has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pip> createPip(@RequestBody Pip pip) throws URISyntaxException {
        log.debug("REST request to save Pip : {}", pip);
        if (pip.getId() != null) {
            throw new BadRequestAlertException("A new pip cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pip = pipRepository.save(pip);
        return ResponseEntity.created(new URI("/api/pips/" + pip.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, pip.getId().toString()))
            .body(pip);
    }

    /**
     * {@code GET  /pips} : get all the pips.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pips in body.
     */
    @GetMapping("")
    public List<Pip> getAllPips() {
        log.debug("REST request to get all Pips");
        return pipRepository.findAll();
    }

    /**
     * {@code GET  /pips/:id} : get the "id" pip.
     *
     * @param id the id of the pip to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pip, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pip> getPip(@PathVariable("id") Long id) {
        log.debug("REST request to get Pip : {}", id);
        Optional<Pip> pip = pipRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pip);
    }

    /**
     * {@code DELETE  /pips/:id} : delete the "id" pip.
     *
     * @param id the id of the pip to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePip(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pip : {}", id);
        pipRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
