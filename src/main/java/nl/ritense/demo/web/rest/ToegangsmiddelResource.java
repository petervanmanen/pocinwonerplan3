package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Toegangsmiddel;
import nl.ritense.demo.repository.ToegangsmiddelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Toegangsmiddel}.
 */
@RestController
@RequestMapping("/api/toegangsmiddels")
@Transactional
public class ToegangsmiddelResource {

    private final Logger log = LoggerFactory.getLogger(ToegangsmiddelResource.class);

    private static final String ENTITY_NAME = "toegangsmiddel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ToegangsmiddelRepository toegangsmiddelRepository;

    public ToegangsmiddelResource(ToegangsmiddelRepository toegangsmiddelRepository) {
        this.toegangsmiddelRepository = toegangsmiddelRepository;
    }

    /**
     * {@code POST  /toegangsmiddels} : Create a new toegangsmiddel.
     *
     * @param toegangsmiddel the toegangsmiddel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new toegangsmiddel, or with status {@code 400 (Bad Request)} if the toegangsmiddel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Toegangsmiddel> createToegangsmiddel(@RequestBody Toegangsmiddel toegangsmiddel) throws URISyntaxException {
        log.debug("REST request to save Toegangsmiddel : {}", toegangsmiddel);
        if (toegangsmiddel.getId() != null) {
            throw new BadRequestAlertException("A new toegangsmiddel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        toegangsmiddel = toegangsmiddelRepository.save(toegangsmiddel);
        return ResponseEntity.created(new URI("/api/toegangsmiddels/" + toegangsmiddel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, toegangsmiddel.getId().toString()))
            .body(toegangsmiddel);
    }

    /**
     * {@code GET  /toegangsmiddels} : get all the toegangsmiddels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of toegangsmiddels in body.
     */
    @GetMapping("")
    public List<Toegangsmiddel> getAllToegangsmiddels() {
        log.debug("REST request to get all Toegangsmiddels");
        return toegangsmiddelRepository.findAll();
    }

    /**
     * {@code GET  /toegangsmiddels/:id} : get the "id" toegangsmiddel.
     *
     * @param id the id of the toegangsmiddel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the toegangsmiddel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Toegangsmiddel> getToegangsmiddel(@PathVariable("id") Long id) {
        log.debug("REST request to get Toegangsmiddel : {}", id);
        Optional<Toegangsmiddel> toegangsmiddel = toegangsmiddelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(toegangsmiddel);
    }

    /**
     * {@code DELETE  /toegangsmiddels/:id} : delete the "id" toegangsmiddel.
     *
     * @param id the id of the toegangsmiddel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToegangsmiddel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Toegangsmiddel : {}", id);
        toegangsmiddelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
