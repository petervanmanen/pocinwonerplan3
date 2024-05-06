package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Puntengroep;
import nl.ritense.demo.repository.PuntengroepRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Puntengroep}.
 */
@RestController
@RequestMapping("/api/puntengroeps")
@Transactional
public class PuntengroepResource {

    private final Logger log = LoggerFactory.getLogger(PuntengroepResource.class);

    private static final String ENTITY_NAME = "puntengroep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PuntengroepRepository puntengroepRepository;

    public PuntengroepResource(PuntengroepRepository puntengroepRepository) {
        this.puntengroepRepository = puntengroepRepository;
    }

    /**
     * {@code POST  /puntengroeps} : Create a new puntengroep.
     *
     * @param puntengroep the puntengroep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new puntengroep, or with status {@code 400 (Bad Request)} if the puntengroep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Puntengroep> createPuntengroep(@RequestBody Puntengroep puntengroep) throws URISyntaxException {
        log.debug("REST request to save Puntengroep : {}", puntengroep);
        if (puntengroep.getId() != null) {
            throw new BadRequestAlertException("A new puntengroep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        puntengroep = puntengroepRepository.save(puntengroep);
        return ResponseEntity.created(new URI("/api/puntengroeps/" + puntengroep.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, puntengroep.getId().toString()))
            .body(puntengroep);
    }

    /**
     * {@code GET  /puntengroeps} : get all the puntengroeps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of puntengroeps in body.
     */
    @GetMapping("")
    public List<Puntengroep> getAllPuntengroeps() {
        log.debug("REST request to get all Puntengroeps");
        return puntengroepRepository.findAll();
    }

    /**
     * {@code GET  /puntengroeps/:id} : get the "id" puntengroep.
     *
     * @param id the id of the puntengroep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the puntengroep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Puntengroep> getPuntengroep(@PathVariable("id") Long id) {
        log.debug("REST request to get Puntengroep : {}", id);
        Optional<Puntengroep> puntengroep = puntengroepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(puntengroep);
    }

    /**
     * {@code DELETE  /puntengroeps/:id} : delete the "id" puntengroep.
     *
     * @param id the id of the puntengroep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePuntengroep(@PathVariable("id") Long id) {
        log.debug("REST request to delete Puntengroep : {}", id);
        puntengroepRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
