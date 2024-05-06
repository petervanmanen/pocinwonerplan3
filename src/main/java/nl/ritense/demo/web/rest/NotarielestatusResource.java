package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Notarielestatus;
import nl.ritense.demo.repository.NotarielestatusRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Notarielestatus}.
 */
@RestController
@RequestMapping("/api/notarielestatuses")
@Transactional
public class NotarielestatusResource {

    private final Logger log = LoggerFactory.getLogger(NotarielestatusResource.class);

    private static final String ENTITY_NAME = "notarielestatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotarielestatusRepository notarielestatusRepository;

    public NotarielestatusResource(NotarielestatusRepository notarielestatusRepository) {
        this.notarielestatusRepository = notarielestatusRepository;
    }

    /**
     * {@code POST  /notarielestatuses} : Create a new notarielestatus.
     *
     * @param notarielestatus the notarielestatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notarielestatus, or with status {@code 400 (Bad Request)} if the notarielestatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Notarielestatus> createNotarielestatus(@RequestBody Notarielestatus notarielestatus) throws URISyntaxException {
        log.debug("REST request to save Notarielestatus : {}", notarielestatus);
        if (notarielestatus.getId() != null) {
            throw new BadRequestAlertException("A new notarielestatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        notarielestatus = notarielestatusRepository.save(notarielestatus);
        return ResponseEntity.created(new URI("/api/notarielestatuses/" + notarielestatus.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, notarielestatus.getId().toString()))
            .body(notarielestatus);
    }

    /**
     * {@code GET  /notarielestatuses} : get all the notarielestatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notarielestatuses in body.
     */
    @GetMapping("")
    public List<Notarielestatus> getAllNotarielestatuses() {
        log.debug("REST request to get all Notarielestatuses");
        return notarielestatusRepository.findAll();
    }

    /**
     * {@code GET  /notarielestatuses/:id} : get the "id" notarielestatus.
     *
     * @param id the id of the notarielestatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notarielestatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notarielestatus> getNotarielestatus(@PathVariable("id") Long id) {
        log.debug("REST request to get Notarielestatus : {}", id);
        Optional<Notarielestatus> notarielestatus = notarielestatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notarielestatus);
    }

    /**
     * {@code DELETE  /notarielestatuses/:id} : delete the "id" notarielestatus.
     *
     * @param id the id of the notarielestatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotarielestatus(@PathVariable("id") Long id) {
        log.debug("REST request to delete Notarielestatus : {}", id);
        notarielestatusRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
