package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Deelplanveld;
import nl.ritense.demo.repository.DeelplanveldRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Deelplanveld}.
 */
@RestController
@RequestMapping("/api/deelplanvelds")
@Transactional
public class DeelplanveldResource {

    private final Logger log = LoggerFactory.getLogger(DeelplanveldResource.class);

    private static final String ENTITY_NAME = "deelplanveld";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeelplanveldRepository deelplanveldRepository;

    public DeelplanveldResource(DeelplanveldRepository deelplanveldRepository) {
        this.deelplanveldRepository = deelplanveldRepository;
    }

    /**
     * {@code POST  /deelplanvelds} : Create a new deelplanveld.
     *
     * @param deelplanveld the deelplanveld to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deelplanveld, or with status {@code 400 (Bad Request)} if the deelplanveld has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Deelplanveld> createDeelplanveld(@RequestBody Deelplanveld deelplanveld) throws URISyntaxException {
        log.debug("REST request to save Deelplanveld : {}", deelplanveld);
        if (deelplanveld.getId() != null) {
            throw new BadRequestAlertException("A new deelplanveld cannot already have an ID", ENTITY_NAME, "idexists");
        }
        deelplanveld = deelplanveldRepository.save(deelplanveld);
        return ResponseEntity.created(new URI("/api/deelplanvelds/" + deelplanveld.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, deelplanveld.getId().toString()))
            .body(deelplanveld);
    }

    /**
     * {@code GET  /deelplanvelds} : get all the deelplanvelds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deelplanvelds in body.
     */
    @GetMapping("")
    public List<Deelplanveld> getAllDeelplanvelds() {
        log.debug("REST request to get all Deelplanvelds");
        return deelplanveldRepository.findAll();
    }

    /**
     * {@code GET  /deelplanvelds/:id} : get the "id" deelplanveld.
     *
     * @param id the id of the deelplanveld to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deelplanveld, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Deelplanveld> getDeelplanveld(@PathVariable("id") Long id) {
        log.debug("REST request to get Deelplanveld : {}", id);
        Optional<Deelplanveld> deelplanveld = deelplanveldRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deelplanveld);
    }

    /**
     * {@code DELETE  /deelplanvelds/:id} : delete the "id" deelplanveld.
     *
     * @param id the id of the deelplanveld to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeelplanveld(@PathVariable("id") Long id) {
        log.debug("REST request to delete Deelplanveld : {}", id);
        deelplanveldRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
