package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Lijnengroep;
import nl.ritense.demo.repository.LijnengroepRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Lijnengroep}.
 */
@RestController
@RequestMapping("/api/lijnengroeps")
@Transactional
public class LijnengroepResource {

    private final Logger log = LoggerFactory.getLogger(LijnengroepResource.class);

    private static final String ENTITY_NAME = "lijnengroep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LijnengroepRepository lijnengroepRepository;

    public LijnengroepResource(LijnengroepRepository lijnengroepRepository) {
        this.lijnengroepRepository = lijnengroepRepository;
    }

    /**
     * {@code POST  /lijnengroeps} : Create a new lijnengroep.
     *
     * @param lijnengroep the lijnengroep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lijnengroep, or with status {@code 400 (Bad Request)} if the lijnengroep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Lijnengroep> createLijnengroep(@RequestBody Lijnengroep lijnengroep) throws URISyntaxException {
        log.debug("REST request to save Lijnengroep : {}", lijnengroep);
        if (lijnengroep.getId() != null) {
            throw new BadRequestAlertException("A new lijnengroep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lijnengroep = lijnengroepRepository.save(lijnengroep);
        return ResponseEntity.created(new URI("/api/lijnengroeps/" + lijnengroep.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lijnengroep.getId().toString()))
            .body(lijnengroep);
    }

    /**
     * {@code GET  /lijnengroeps} : get all the lijnengroeps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lijnengroeps in body.
     */
    @GetMapping("")
    public List<Lijnengroep> getAllLijnengroeps() {
        log.debug("REST request to get all Lijnengroeps");
        return lijnengroepRepository.findAll();
    }

    /**
     * {@code GET  /lijnengroeps/:id} : get the "id" lijnengroep.
     *
     * @param id the id of the lijnengroep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lijnengroep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lijnengroep> getLijnengroep(@PathVariable("id") Long id) {
        log.debug("REST request to get Lijnengroep : {}", id);
        Optional<Lijnengroep> lijnengroep = lijnengroepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lijnengroep);
    }

    /**
     * {@code DELETE  /lijnengroeps/:id} : delete the "id" lijnengroep.
     *
     * @param id the id of the lijnengroep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLijnengroep(@PathVariable("id") Long id) {
        log.debug("REST request to delete Lijnengroep : {}", id);
        lijnengroepRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
