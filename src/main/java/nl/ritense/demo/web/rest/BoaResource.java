package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Boa;
import nl.ritense.demo.repository.BoaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Boa}.
 */
@RestController
@RequestMapping("/api/boas")
@Transactional
public class BoaResource {

    private final Logger log = LoggerFactory.getLogger(BoaResource.class);

    private static final String ENTITY_NAME = "boa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoaRepository boaRepository;

    public BoaResource(BoaRepository boaRepository) {
        this.boaRepository = boaRepository;
    }

    /**
     * {@code POST  /boas} : Create a new boa.
     *
     * @param boa the boa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boa, or with status {@code 400 (Bad Request)} if the boa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Boa> createBoa(@RequestBody Boa boa) throws URISyntaxException {
        log.debug("REST request to save Boa : {}", boa);
        if (boa.getId() != null) {
            throw new BadRequestAlertException("A new boa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        boa = boaRepository.save(boa);
        return ResponseEntity.created(new URI("/api/boas/" + boa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, boa.getId().toString()))
            .body(boa);
    }

    /**
     * {@code GET  /boas} : get all the boas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boas in body.
     */
    @GetMapping("")
    public List<Boa> getAllBoas() {
        log.debug("REST request to get all Boas");
        return boaRepository.findAll();
    }

    /**
     * {@code GET  /boas/:id} : get the "id" boa.
     *
     * @param id the id of the boa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Boa> getBoa(@PathVariable("id") Long id) {
        log.debug("REST request to get Boa : {}", id);
        Optional<Boa> boa = boaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(boa);
    }

    /**
     * {@code DELETE  /boas/:id} : delete the "id" boa.
     *
     * @param id the id of the boa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete Boa : {}", id);
        boaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
