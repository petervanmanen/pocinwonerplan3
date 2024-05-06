package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Deelprocestype;
import nl.ritense.demo.repository.DeelprocestypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Deelprocestype}.
 */
@RestController
@RequestMapping("/api/deelprocestypes")
@Transactional
public class DeelprocestypeResource {

    private final Logger log = LoggerFactory.getLogger(DeelprocestypeResource.class);

    private static final String ENTITY_NAME = "deelprocestype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeelprocestypeRepository deelprocestypeRepository;

    public DeelprocestypeResource(DeelprocestypeRepository deelprocestypeRepository) {
        this.deelprocestypeRepository = deelprocestypeRepository;
    }

    /**
     * {@code POST  /deelprocestypes} : Create a new deelprocestype.
     *
     * @param deelprocestype the deelprocestype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deelprocestype, or with status {@code 400 (Bad Request)} if the deelprocestype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Deelprocestype> createDeelprocestype(@Valid @RequestBody Deelprocestype deelprocestype)
        throws URISyntaxException {
        log.debug("REST request to save Deelprocestype : {}", deelprocestype);
        if (deelprocestype.getId() != null) {
            throw new BadRequestAlertException("A new deelprocestype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        deelprocestype = deelprocestypeRepository.save(deelprocestype);
        return ResponseEntity.created(new URI("/api/deelprocestypes/" + deelprocestype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, deelprocestype.getId().toString()))
            .body(deelprocestype);
    }

    /**
     * {@code PUT  /deelprocestypes/:id} : Updates an existing deelprocestype.
     *
     * @param id the id of the deelprocestype to save.
     * @param deelprocestype the deelprocestype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deelprocestype,
     * or with status {@code 400 (Bad Request)} if the deelprocestype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deelprocestype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Deelprocestype> updateDeelprocestype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Deelprocestype deelprocestype
    ) throws URISyntaxException {
        log.debug("REST request to update Deelprocestype : {}, {}", id, deelprocestype);
        if (deelprocestype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deelprocestype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deelprocestypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        deelprocestype = deelprocestypeRepository.save(deelprocestype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deelprocestype.getId().toString()))
            .body(deelprocestype);
    }

    /**
     * {@code PATCH  /deelprocestypes/:id} : Partial updates given fields of an existing deelprocestype, field will ignore if it is null
     *
     * @param id the id of the deelprocestype to save.
     * @param deelprocestype the deelprocestype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deelprocestype,
     * or with status {@code 400 (Bad Request)} if the deelprocestype is not valid,
     * or with status {@code 404 (Not Found)} if the deelprocestype is not found,
     * or with status {@code 500 (Internal Server Error)} if the deelprocestype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Deelprocestype> partialUpdateDeelprocestype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Deelprocestype deelprocestype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Deelprocestype partially : {}, {}", id, deelprocestype);
        if (deelprocestype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deelprocestype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deelprocestypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Deelprocestype> result = deelprocestypeRepository
            .findById(deelprocestype.getId())
            .map(existingDeelprocestype -> {
                if (deelprocestype.getOmschrijving() != null) {
                    existingDeelprocestype.setOmschrijving(deelprocestype.getOmschrijving());
                }

                return existingDeelprocestype;
            })
            .map(deelprocestypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deelprocestype.getId().toString())
        );
    }

    /**
     * {@code GET  /deelprocestypes} : get all the deelprocestypes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deelprocestypes in body.
     */
    @GetMapping("")
    public List<Deelprocestype> getAllDeelprocestypes(@RequestParam(name = "filter", required = false) String filter) {
        if ("isvandeelproces-is-null".equals(filter)) {
            log.debug("REST request to get all Deelprocestypes where isvanDeelproces is null");
            return StreamSupport.stream(deelprocestypeRepository.findAll().spliterator(), false)
                .filter(deelprocestype -> deelprocestype.getIsvanDeelproces() == null)
                .toList();
        }
        log.debug("REST request to get all Deelprocestypes");
        return deelprocestypeRepository.findAll();
    }

    /**
     * {@code GET  /deelprocestypes/:id} : get the "id" deelprocestype.
     *
     * @param id the id of the deelprocestype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deelprocestype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Deelprocestype> getDeelprocestype(@PathVariable("id") Long id) {
        log.debug("REST request to get Deelprocestype : {}", id);
        Optional<Deelprocestype> deelprocestype = deelprocestypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deelprocestype);
    }

    /**
     * {@code DELETE  /deelprocestypes/:id} : delete the "id" deelprocestype.
     *
     * @param id the id of the deelprocestype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeelprocestype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Deelprocestype : {}", id);
        deelprocestypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
