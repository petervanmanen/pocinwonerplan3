package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Indieningsvereisten;
import nl.ritense.demo.repository.IndieningsvereistenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Indieningsvereisten}.
 */
@RestController
@RequestMapping("/api/indieningsvereistens")
@Transactional
public class IndieningsvereistenResource {

    private final Logger log = LoggerFactory.getLogger(IndieningsvereistenResource.class);

    private static final String ENTITY_NAME = "indieningsvereisten";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndieningsvereistenRepository indieningsvereistenRepository;

    public IndieningsvereistenResource(IndieningsvereistenRepository indieningsvereistenRepository) {
        this.indieningsvereistenRepository = indieningsvereistenRepository;
    }

    /**
     * {@code POST  /indieningsvereistens} : Create a new indieningsvereisten.
     *
     * @param indieningsvereisten the indieningsvereisten to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indieningsvereisten, or with status {@code 400 (Bad Request)} if the indieningsvereisten has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Indieningsvereisten> createIndieningsvereisten(@RequestBody Indieningsvereisten indieningsvereisten)
        throws URISyntaxException {
        log.debug("REST request to save Indieningsvereisten : {}", indieningsvereisten);
        if (indieningsvereisten.getId() != null) {
            throw new BadRequestAlertException("A new indieningsvereisten cannot already have an ID", ENTITY_NAME, "idexists");
        }
        indieningsvereisten = indieningsvereistenRepository.save(indieningsvereisten);
        return ResponseEntity.created(new URI("/api/indieningsvereistens/" + indieningsvereisten.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, indieningsvereisten.getId().toString()))
            .body(indieningsvereisten);
    }

    /**
     * {@code GET  /indieningsvereistens} : get all the indieningsvereistens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indieningsvereistens in body.
     */
    @GetMapping("")
    public List<Indieningsvereisten> getAllIndieningsvereistens() {
        log.debug("REST request to get all Indieningsvereistens");
        return indieningsvereistenRepository.findAll();
    }

    /**
     * {@code GET  /indieningsvereistens/:id} : get the "id" indieningsvereisten.
     *
     * @param id the id of the indieningsvereisten to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indieningsvereisten, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Indieningsvereisten> getIndieningsvereisten(@PathVariable("id") Long id) {
        log.debug("REST request to get Indieningsvereisten : {}", id);
        Optional<Indieningsvereisten> indieningsvereisten = indieningsvereistenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(indieningsvereisten);
    }

    /**
     * {@code DELETE  /indieningsvereistens/:id} : delete the "id" indieningsvereisten.
     *
     * @param id the id of the indieningsvereisten to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndieningsvereisten(@PathVariable("id") Long id) {
        log.debug("REST request to delete Indieningsvereisten : {}", id);
        indieningsvereistenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
