package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Huurder;
import nl.ritense.demo.repository.HuurderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Huurder}.
 */
@RestController
@RequestMapping("/api/huurders")
@Transactional
public class HuurderResource {

    private final Logger log = LoggerFactory.getLogger(HuurderResource.class);

    private static final String ENTITY_NAME = "huurder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HuurderRepository huurderRepository;

    public HuurderResource(HuurderRepository huurderRepository) {
        this.huurderRepository = huurderRepository;
    }

    /**
     * {@code POST  /huurders} : Create a new huurder.
     *
     * @param huurder the huurder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new huurder, or with status {@code 400 (Bad Request)} if the huurder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Huurder> createHuurder(@RequestBody Huurder huurder) throws URISyntaxException {
        log.debug("REST request to save Huurder : {}", huurder);
        if (huurder.getId() != null) {
            throw new BadRequestAlertException("A new huurder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        huurder = huurderRepository.save(huurder);
        return ResponseEntity.created(new URI("/api/huurders/" + huurder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, huurder.getId().toString()))
            .body(huurder);
    }

    /**
     * {@code GET  /huurders} : get all the huurders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of huurders in body.
     */
    @GetMapping("")
    public List<Huurder> getAllHuurders() {
        log.debug("REST request to get all Huurders");
        return huurderRepository.findAll();
    }

    /**
     * {@code GET  /huurders/:id} : get the "id" huurder.
     *
     * @param id the id of the huurder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the huurder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Huurder> getHuurder(@PathVariable("id") Long id) {
        log.debug("REST request to get Huurder : {}", id);
        Optional<Huurder> huurder = huurderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(huurder);
    }

    /**
     * {@code DELETE  /huurders/:id} : delete the "id" huurder.
     *
     * @param id the id of the huurder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHuurder(@PathVariable("id") Long id) {
        log.debug("REST request to delete Huurder : {}", id);
        huurderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
