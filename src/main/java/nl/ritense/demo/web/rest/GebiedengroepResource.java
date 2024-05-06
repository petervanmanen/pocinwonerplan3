package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Gebiedengroep;
import nl.ritense.demo.repository.GebiedengroepRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gebiedengroep}.
 */
@RestController
@RequestMapping("/api/gebiedengroeps")
@Transactional
public class GebiedengroepResource {

    private final Logger log = LoggerFactory.getLogger(GebiedengroepResource.class);

    private static final String ENTITY_NAME = "gebiedengroep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GebiedengroepRepository gebiedengroepRepository;

    public GebiedengroepResource(GebiedengroepRepository gebiedengroepRepository) {
        this.gebiedengroepRepository = gebiedengroepRepository;
    }

    /**
     * {@code POST  /gebiedengroeps} : Create a new gebiedengroep.
     *
     * @param gebiedengroep the gebiedengroep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gebiedengroep, or with status {@code 400 (Bad Request)} if the gebiedengroep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gebiedengroep> createGebiedengroep(@RequestBody Gebiedengroep gebiedengroep) throws URISyntaxException {
        log.debug("REST request to save Gebiedengroep : {}", gebiedengroep);
        if (gebiedengroep.getId() != null) {
            throw new BadRequestAlertException("A new gebiedengroep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gebiedengroep = gebiedengroepRepository.save(gebiedengroep);
        return ResponseEntity.created(new URI("/api/gebiedengroeps/" + gebiedengroep.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gebiedengroep.getId().toString()))
            .body(gebiedengroep);
    }

    /**
     * {@code GET  /gebiedengroeps} : get all the gebiedengroeps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gebiedengroeps in body.
     */
    @GetMapping("")
    public List<Gebiedengroep> getAllGebiedengroeps() {
        log.debug("REST request to get all Gebiedengroeps");
        return gebiedengroepRepository.findAll();
    }

    /**
     * {@code GET  /gebiedengroeps/:id} : get the "id" gebiedengroep.
     *
     * @param id the id of the gebiedengroep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gebiedengroep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gebiedengroep> getGebiedengroep(@PathVariable("id") Long id) {
        log.debug("REST request to get Gebiedengroep : {}", id);
        Optional<Gebiedengroep> gebiedengroep = gebiedengroepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gebiedengroep);
    }

    /**
     * {@code DELETE  /gebiedengroeps/:id} : delete the "id" gebiedengroep.
     *
     * @param id the id of the gebiedengroep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebiedengroep(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gebiedengroep : {}", id);
        gebiedengroepRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
