package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Verkeerslicht;
import nl.ritense.demo.repository.VerkeerslichtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verkeerslicht}.
 */
@RestController
@RequestMapping("/api/verkeerslichts")
@Transactional
public class VerkeerslichtResource {

    private final Logger log = LoggerFactory.getLogger(VerkeerslichtResource.class);

    private static final String ENTITY_NAME = "verkeerslicht";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerkeerslichtRepository verkeerslichtRepository;

    public VerkeerslichtResource(VerkeerslichtRepository verkeerslichtRepository) {
        this.verkeerslichtRepository = verkeerslichtRepository;
    }

    /**
     * {@code POST  /verkeerslichts} : Create a new verkeerslicht.
     *
     * @param verkeerslicht the verkeerslicht to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verkeerslicht, or with status {@code 400 (Bad Request)} if the verkeerslicht has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verkeerslicht> createVerkeerslicht(@RequestBody Verkeerslicht verkeerslicht) throws URISyntaxException {
        log.debug("REST request to save Verkeerslicht : {}", verkeerslicht);
        if (verkeerslicht.getId() != null) {
            throw new BadRequestAlertException("A new verkeerslicht cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verkeerslicht = verkeerslichtRepository.save(verkeerslicht);
        return ResponseEntity.created(new URI("/api/verkeerslichts/" + verkeerslicht.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verkeerslicht.getId().toString()))
            .body(verkeerslicht);
    }

    /**
     * {@code GET  /verkeerslichts} : get all the verkeerslichts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verkeerslichts in body.
     */
    @GetMapping("")
    public List<Verkeerslicht> getAllVerkeerslichts() {
        log.debug("REST request to get all Verkeerslichts");
        return verkeerslichtRepository.findAll();
    }

    /**
     * {@code GET  /verkeerslichts/:id} : get the "id" verkeerslicht.
     *
     * @param id the id of the verkeerslicht to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verkeerslicht, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verkeerslicht> getVerkeerslicht(@PathVariable("id") Long id) {
        log.debug("REST request to get Verkeerslicht : {}", id);
        Optional<Verkeerslicht> verkeerslicht = verkeerslichtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verkeerslicht);
    }

    /**
     * {@code DELETE  /verkeerslichts/:id} : delete the "id" verkeerslicht.
     *
     * @param id the id of the verkeerslicht to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerkeerslicht(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verkeerslicht : {}", id);
        verkeerslichtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
