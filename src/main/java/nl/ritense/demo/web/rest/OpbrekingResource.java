package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Opbreking;
import nl.ritense.demo.repository.OpbrekingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Opbreking}.
 */
@RestController
@RequestMapping("/api/opbrekings")
@Transactional
public class OpbrekingResource {

    private final Logger log = LoggerFactory.getLogger(OpbrekingResource.class);

    private static final String ENTITY_NAME = "opbreking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpbrekingRepository opbrekingRepository;

    public OpbrekingResource(OpbrekingRepository opbrekingRepository) {
        this.opbrekingRepository = opbrekingRepository;
    }

    /**
     * {@code POST  /opbrekings} : Create a new opbreking.
     *
     * @param opbreking the opbreking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opbreking, or with status {@code 400 (Bad Request)} if the opbreking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Opbreking> createOpbreking(@RequestBody Opbreking opbreking) throws URISyntaxException {
        log.debug("REST request to save Opbreking : {}", opbreking);
        if (opbreking.getId() != null) {
            throw new BadRequestAlertException("A new opbreking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opbreking = opbrekingRepository.save(opbreking);
        return ResponseEntity.created(new URI("/api/opbrekings/" + opbreking.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, opbreking.getId().toString()))
            .body(opbreking);
    }

    /**
     * {@code GET  /opbrekings} : get all the opbrekings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opbrekings in body.
     */
    @GetMapping("")
    public List<Opbreking> getAllOpbrekings() {
        log.debug("REST request to get all Opbrekings");
        return opbrekingRepository.findAll();
    }

    /**
     * {@code GET  /opbrekings/:id} : get the "id" opbreking.
     *
     * @param id the id of the opbreking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opbreking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Opbreking> getOpbreking(@PathVariable("id") Long id) {
        log.debug("REST request to get Opbreking : {}", id);
        Optional<Opbreking> opbreking = opbrekingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(opbreking);
    }

    /**
     * {@code DELETE  /opbrekings/:id} : delete the "id" opbreking.
     *
     * @param id the id of the opbreking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpbreking(@PathVariable("id") Long id) {
        log.debug("REST request to delete Opbreking : {}", id);
        opbrekingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
