package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Initiatiefnemer;
import nl.ritense.demo.repository.InitiatiefnemerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Initiatiefnemer}.
 */
@RestController
@RequestMapping("/api/initiatiefnemers")
@Transactional
public class InitiatiefnemerResource {

    private final Logger log = LoggerFactory.getLogger(InitiatiefnemerResource.class);

    private static final String ENTITY_NAME = "initiatiefnemer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InitiatiefnemerRepository initiatiefnemerRepository;

    public InitiatiefnemerResource(InitiatiefnemerRepository initiatiefnemerRepository) {
        this.initiatiefnemerRepository = initiatiefnemerRepository;
    }

    /**
     * {@code POST  /initiatiefnemers} : Create a new initiatiefnemer.
     *
     * @param initiatiefnemer the initiatiefnemer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new initiatiefnemer, or with status {@code 400 (Bad Request)} if the initiatiefnemer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Initiatiefnemer> createInitiatiefnemer(@Valid @RequestBody Initiatiefnemer initiatiefnemer)
        throws URISyntaxException {
        log.debug("REST request to save Initiatiefnemer : {}", initiatiefnemer);
        if (initiatiefnemer.getId() != null) {
            throw new BadRequestAlertException("A new initiatiefnemer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        initiatiefnemer = initiatiefnemerRepository.save(initiatiefnemer);
        return ResponseEntity.created(new URI("/api/initiatiefnemers/" + initiatiefnemer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, initiatiefnemer.getId().toString()))
            .body(initiatiefnemer);
    }

    /**
     * {@code GET  /initiatiefnemers} : get all the initiatiefnemers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of initiatiefnemers in body.
     */
    @GetMapping("")
    public List<Initiatiefnemer> getAllInitiatiefnemers() {
        log.debug("REST request to get all Initiatiefnemers");
        return initiatiefnemerRepository.findAll();
    }

    /**
     * {@code GET  /initiatiefnemers/:id} : get the "id" initiatiefnemer.
     *
     * @param id the id of the initiatiefnemer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the initiatiefnemer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Initiatiefnemer> getInitiatiefnemer(@PathVariable("id") Long id) {
        log.debug("REST request to get Initiatiefnemer : {}", id);
        Optional<Initiatiefnemer> initiatiefnemer = initiatiefnemerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(initiatiefnemer);
    }

    /**
     * {@code DELETE  /initiatiefnemers/:id} : delete the "id" initiatiefnemer.
     *
     * @param id the id of the initiatiefnemer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInitiatiefnemer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Initiatiefnemer : {}", id);
        initiatiefnemerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
