package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Gezinsmigrantenoverigemigrant;
import nl.ritense.demo.repository.GezinsmigrantenoverigemigrantRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gezinsmigrantenoverigemigrant}.
 */
@RestController
@RequestMapping("/api/gezinsmigrantenoverigemigrants")
@Transactional
public class GezinsmigrantenoverigemigrantResource {

    private final Logger log = LoggerFactory.getLogger(GezinsmigrantenoverigemigrantResource.class);

    private static final String ENTITY_NAME = "gezinsmigrantenoverigemigrant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GezinsmigrantenoverigemigrantRepository gezinsmigrantenoverigemigrantRepository;

    public GezinsmigrantenoverigemigrantResource(GezinsmigrantenoverigemigrantRepository gezinsmigrantenoverigemigrantRepository) {
        this.gezinsmigrantenoverigemigrantRepository = gezinsmigrantenoverigemigrantRepository;
    }

    /**
     * {@code POST  /gezinsmigrantenoverigemigrants} : Create a new gezinsmigrantenoverigemigrant.
     *
     * @param gezinsmigrantenoverigemigrant the gezinsmigrantenoverigemigrant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gezinsmigrantenoverigemigrant, or with status {@code 400 (Bad Request)} if the gezinsmigrantenoverigemigrant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gezinsmigrantenoverigemigrant> createGezinsmigrantenoverigemigrant(
        @RequestBody Gezinsmigrantenoverigemigrant gezinsmigrantenoverigemigrant
    ) throws URISyntaxException {
        log.debug("REST request to save Gezinsmigrantenoverigemigrant : {}", gezinsmigrantenoverigemigrant);
        if (gezinsmigrantenoverigemigrant.getId() != null) {
            throw new BadRequestAlertException("A new gezinsmigrantenoverigemigrant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gezinsmigrantenoverigemigrant = gezinsmigrantenoverigemigrantRepository.save(gezinsmigrantenoverigemigrant);
        return ResponseEntity.created(new URI("/api/gezinsmigrantenoverigemigrants/" + gezinsmigrantenoverigemigrant.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gezinsmigrantenoverigemigrant.getId().toString())
            )
            .body(gezinsmigrantenoverigemigrant);
    }

    /**
     * {@code GET  /gezinsmigrantenoverigemigrants} : get all the gezinsmigrantenoverigemigrants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gezinsmigrantenoverigemigrants in body.
     */
    @GetMapping("")
    public List<Gezinsmigrantenoverigemigrant> getAllGezinsmigrantenoverigemigrants() {
        log.debug("REST request to get all Gezinsmigrantenoverigemigrants");
        return gezinsmigrantenoverigemigrantRepository.findAll();
    }

    /**
     * {@code GET  /gezinsmigrantenoverigemigrants/:id} : get the "id" gezinsmigrantenoverigemigrant.
     *
     * @param id the id of the gezinsmigrantenoverigemigrant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gezinsmigrantenoverigemigrant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gezinsmigrantenoverigemigrant> getGezinsmigrantenoverigemigrant(@PathVariable("id") Long id) {
        log.debug("REST request to get Gezinsmigrantenoverigemigrant : {}", id);
        Optional<Gezinsmigrantenoverigemigrant> gezinsmigrantenoverigemigrant = gezinsmigrantenoverigemigrantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gezinsmigrantenoverigemigrant);
    }

    /**
     * {@code DELETE  /gezinsmigrantenoverigemigrants/:id} : delete the "id" gezinsmigrantenoverigemigrant.
     *
     * @param id the id of the gezinsmigrantenoverigemigrant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGezinsmigrantenoverigemigrant(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gezinsmigrantenoverigemigrant : {}", id);
        gezinsmigrantenoverigemigrantRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
