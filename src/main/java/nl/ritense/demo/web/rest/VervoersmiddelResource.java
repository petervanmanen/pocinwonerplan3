package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Vervoersmiddel;
import nl.ritense.demo.repository.VervoersmiddelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vervoersmiddel}.
 */
@RestController
@RequestMapping("/api/vervoersmiddels")
@Transactional
public class VervoersmiddelResource {

    private final Logger log = LoggerFactory.getLogger(VervoersmiddelResource.class);

    private static final String ENTITY_NAME = "vervoersmiddel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VervoersmiddelRepository vervoersmiddelRepository;

    public VervoersmiddelResource(VervoersmiddelRepository vervoersmiddelRepository) {
        this.vervoersmiddelRepository = vervoersmiddelRepository;
    }

    /**
     * {@code POST  /vervoersmiddels} : Create a new vervoersmiddel.
     *
     * @param vervoersmiddel the vervoersmiddel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vervoersmiddel, or with status {@code 400 (Bad Request)} if the vervoersmiddel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vervoersmiddel> createVervoersmiddel(@RequestBody Vervoersmiddel vervoersmiddel) throws URISyntaxException {
        log.debug("REST request to save Vervoersmiddel : {}", vervoersmiddel);
        if (vervoersmiddel.getId() != null) {
            throw new BadRequestAlertException("A new vervoersmiddel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vervoersmiddel = vervoersmiddelRepository.save(vervoersmiddel);
        return ResponseEntity.created(new URI("/api/vervoersmiddels/" + vervoersmiddel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vervoersmiddel.getId().toString()))
            .body(vervoersmiddel);
    }

    /**
     * {@code GET  /vervoersmiddels} : get all the vervoersmiddels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vervoersmiddels in body.
     */
    @GetMapping("")
    public List<Vervoersmiddel> getAllVervoersmiddels() {
        log.debug("REST request to get all Vervoersmiddels");
        return vervoersmiddelRepository.findAll();
    }

    /**
     * {@code GET  /vervoersmiddels/:id} : get the "id" vervoersmiddel.
     *
     * @param id the id of the vervoersmiddel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vervoersmiddel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vervoersmiddel> getVervoersmiddel(@PathVariable("id") Long id) {
        log.debug("REST request to get Vervoersmiddel : {}", id);
        Optional<Vervoersmiddel> vervoersmiddel = vervoersmiddelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vervoersmiddel);
    }

    /**
     * {@code DELETE  /vervoersmiddels/:id} : delete the "id" vervoersmiddel.
     *
     * @param id the id of the vervoersmiddel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVervoersmiddel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vervoersmiddel : {}", id);
        vervoersmiddelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
