package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Externebron;
import nl.ritense.demo.repository.ExternebronRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Externebron}.
 */
@RestController
@RequestMapping("/api/externebrons")
@Transactional
public class ExternebronResource {

    private final Logger log = LoggerFactory.getLogger(ExternebronResource.class);

    private static final String ENTITY_NAME = "externebron";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExternebronRepository externebronRepository;

    public ExternebronResource(ExternebronRepository externebronRepository) {
        this.externebronRepository = externebronRepository;
    }

    /**
     * {@code POST  /externebrons} : Create a new externebron.
     *
     * @param externebron the externebron to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new externebron, or with status {@code 400 (Bad Request)} if the externebron has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Externebron> createExternebron(@RequestBody Externebron externebron) throws URISyntaxException {
        log.debug("REST request to save Externebron : {}", externebron);
        if (externebron.getId() != null) {
            throw new BadRequestAlertException("A new externebron cannot already have an ID", ENTITY_NAME, "idexists");
        }
        externebron = externebronRepository.save(externebron);
        return ResponseEntity.created(new URI("/api/externebrons/" + externebron.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, externebron.getId().toString()))
            .body(externebron);
    }

    /**
     * {@code PUT  /externebrons/:id} : Updates an existing externebron.
     *
     * @param id the id of the externebron to save.
     * @param externebron the externebron to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated externebron,
     * or with status {@code 400 (Bad Request)} if the externebron is not valid,
     * or with status {@code 500 (Internal Server Error)} if the externebron couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Externebron> updateExternebron(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Externebron externebron
    ) throws URISyntaxException {
        log.debug("REST request to update Externebron : {}, {}", id, externebron);
        if (externebron.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, externebron.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!externebronRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        externebron = externebronRepository.save(externebron);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, externebron.getId().toString()))
            .body(externebron);
    }

    /**
     * {@code PATCH  /externebrons/:id} : Partial updates given fields of an existing externebron, field will ignore if it is null
     *
     * @param id the id of the externebron to save.
     * @param externebron the externebron to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated externebron,
     * or with status {@code 400 (Bad Request)} if the externebron is not valid,
     * or with status {@code 404 (Not Found)} if the externebron is not found,
     * or with status {@code 500 (Internal Server Error)} if the externebron couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Externebron> partialUpdateExternebron(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Externebron externebron
    ) throws URISyntaxException {
        log.debug("REST request to partial update Externebron partially : {}, {}", id, externebron);
        if (externebron.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, externebron.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!externebronRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Externebron> result = externebronRepository
            .findById(externebron.getId())
            .map(existingExternebron -> {
                if (externebron.getGuid() != null) {
                    existingExternebron.setGuid(externebron.getGuid());
                }
                if (externebron.getNaam() != null) {
                    existingExternebron.setNaam(externebron.getNaam());
                }

                return existingExternebron;
            })
            .map(externebronRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, externebron.getId().toString())
        );
    }

    /**
     * {@code GET  /externebrons} : get all the externebrons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of externebrons in body.
     */
    @GetMapping("")
    public List<Externebron> getAllExternebrons() {
        log.debug("REST request to get all Externebrons");
        return externebronRepository.findAll();
    }

    /**
     * {@code GET  /externebrons/:id} : get the "id" externebron.
     *
     * @param id the id of the externebron to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the externebron, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Externebron> getExternebron(@PathVariable("id") Long id) {
        log.debug("REST request to get Externebron : {}", id);
        Optional<Externebron> externebron = externebronRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(externebron);
    }

    /**
     * {@code DELETE  /externebrons/:id} : delete the "id" externebron.
     *
     * @param id the id of the externebron to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExternebron(@PathVariable("id") Long id) {
        log.debug("REST request to delete Externebron : {}", id);
        externebronRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
