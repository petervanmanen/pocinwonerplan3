package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ophaalmoment;
import nl.ritense.demo.repository.OphaalmomentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ophaalmoment}.
 */
@RestController
@RequestMapping("/api/ophaalmoments")
@Transactional
public class OphaalmomentResource {

    private final Logger log = LoggerFactory.getLogger(OphaalmomentResource.class);

    private static final String ENTITY_NAME = "ophaalmoment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OphaalmomentRepository ophaalmomentRepository;

    public OphaalmomentResource(OphaalmomentRepository ophaalmomentRepository) {
        this.ophaalmomentRepository = ophaalmomentRepository;
    }

    /**
     * {@code POST  /ophaalmoments} : Create a new ophaalmoment.
     *
     * @param ophaalmoment the ophaalmoment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ophaalmoment, or with status {@code 400 (Bad Request)} if the ophaalmoment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ophaalmoment> createOphaalmoment(@RequestBody Ophaalmoment ophaalmoment) throws URISyntaxException {
        log.debug("REST request to save Ophaalmoment : {}", ophaalmoment);
        if (ophaalmoment.getId() != null) {
            throw new BadRequestAlertException("A new ophaalmoment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ophaalmoment = ophaalmomentRepository.save(ophaalmoment);
        return ResponseEntity.created(new URI("/api/ophaalmoments/" + ophaalmoment.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ophaalmoment.getId().toString()))
            .body(ophaalmoment);
    }

    /**
     * {@code PUT  /ophaalmoments/:id} : Updates an existing ophaalmoment.
     *
     * @param id the id of the ophaalmoment to save.
     * @param ophaalmoment the ophaalmoment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ophaalmoment,
     * or with status {@code 400 (Bad Request)} if the ophaalmoment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ophaalmoment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ophaalmoment> updateOphaalmoment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ophaalmoment ophaalmoment
    ) throws URISyntaxException {
        log.debug("REST request to update Ophaalmoment : {}, {}", id, ophaalmoment);
        if (ophaalmoment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ophaalmoment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ophaalmomentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ophaalmoment = ophaalmomentRepository.save(ophaalmoment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ophaalmoment.getId().toString()))
            .body(ophaalmoment);
    }

    /**
     * {@code PATCH  /ophaalmoments/:id} : Partial updates given fields of an existing ophaalmoment, field will ignore if it is null
     *
     * @param id the id of the ophaalmoment to save.
     * @param ophaalmoment the ophaalmoment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ophaalmoment,
     * or with status {@code 400 (Bad Request)} if the ophaalmoment is not valid,
     * or with status {@code 404 (Not Found)} if the ophaalmoment is not found,
     * or with status {@code 500 (Internal Server Error)} if the ophaalmoment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ophaalmoment> partialUpdateOphaalmoment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ophaalmoment ophaalmoment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ophaalmoment partially : {}, {}", id, ophaalmoment);
        if (ophaalmoment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ophaalmoment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ophaalmomentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ophaalmoment> result = ophaalmomentRepository
            .findById(ophaalmoment.getId())
            .map(existingOphaalmoment -> {
                if (ophaalmoment.getGewichtstoename() != null) {
                    existingOphaalmoment.setGewichtstoename(ophaalmoment.getGewichtstoename());
                }
                if (ophaalmoment.getTijdstip() != null) {
                    existingOphaalmoment.setTijdstip(ophaalmoment.getTijdstip());
                }

                return existingOphaalmoment;
            })
            .map(ophaalmomentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ophaalmoment.getId().toString())
        );
    }

    /**
     * {@code GET  /ophaalmoments} : get all the ophaalmoments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ophaalmoments in body.
     */
    @GetMapping("")
    public List<Ophaalmoment> getAllOphaalmoments() {
        log.debug("REST request to get all Ophaalmoments");
        return ophaalmomentRepository.findAll();
    }

    /**
     * {@code GET  /ophaalmoments/:id} : get the "id" ophaalmoment.
     *
     * @param id the id of the ophaalmoment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ophaalmoment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ophaalmoment> getOphaalmoment(@PathVariable("id") Long id) {
        log.debug("REST request to get Ophaalmoment : {}", id);
        Optional<Ophaalmoment> ophaalmoment = ophaalmomentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ophaalmoment);
    }

    /**
     * {@code DELETE  /ophaalmoments/:id} : delete the "id" ophaalmoment.
     *
     * @param id the id of the ophaalmoment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOphaalmoment(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ophaalmoment : {}", id);
        ophaalmomentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
