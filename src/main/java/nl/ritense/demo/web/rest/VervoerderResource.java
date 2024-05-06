package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Vervoerder;
import nl.ritense.demo.repository.VervoerderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vervoerder}.
 */
@RestController
@RequestMapping("/api/vervoerders")
@Transactional
public class VervoerderResource {

    private final Logger log = LoggerFactory.getLogger(VervoerderResource.class);

    private static final String ENTITY_NAME = "vervoerder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VervoerderRepository vervoerderRepository;

    public VervoerderResource(VervoerderRepository vervoerderRepository) {
        this.vervoerderRepository = vervoerderRepository;
    }

    /**
     * {@code POST  /vervoerders} : Create a new vervoerder.
     *
     * @param vervoerder the vervoerder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vervoerder, or with status {@code 400 (Bad Request)} if the vervoerder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vervoerder> createVervoerder(@RequestBody Vervoerder vervoerder) throws URISyntaxException {
        log.debug("REST request to save Vervoerder : {}", vervoerder);
        if (vervoerder.getId() != null) {
            throw new BadRequestAlertException("A new vervoerder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vervoerder = vervoerderRepository.save(vervoerder);
        return ResponseEntity.created(new URI("/api/vervoerders/" + vervoerder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vervoerder.getId().toString()))
            .body(vervoerder);
    }

    /**
     * {@code GET  /vervoerders} : get all the vervoerders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vervoerders in body.
     */
    @GetMapping("")
    public List<Vervoerder> getAllVervoerders() {
        log.debug("REST request to get all Vervoerders");
        return vervoerderRepository.findAll();
    }

    /**
     * {@code GET  /vervoerders/:id} : get the "id" vervoerder.
     *
     * @param id the id of the vervoerder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vervoerder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vervoerder> getVervoerder(@PathVariable("id") Long id) {
        log.debug("REST request to get Vervoerder : {}", id);
        Optional<Vervoerder> vervoerder = vervoerderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vervoerder);
    }

    /**
     * {@code DELETE  /vervoerders/:id} : delete the "id" vervoerder.
     *
     * @param id the id of the vervoerder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVervoerder(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vervoerder : {}", id);
        vervoerderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
