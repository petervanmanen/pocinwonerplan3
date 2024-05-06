package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Put;
import nl.ritense.demo.repository.PutRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Put}.
 */
@RestController
@RequestMapping("/api/puts")
@Transactional
public class PutResource {

    private final Logger log = LoggerFactory.getLogger(PutResource.class);

    private static final String ENTITY_NAME = "put";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PutRepository putRepository;

    public PutResource(PutRepository putRepository) {
        this.putRepository = putRepository;
    }

    /**
     * {@code POST  /puts} : Create a new put.
     *
     * @param put the put to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new put, or with status {@code 400 (Bad Request)} if the put has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Put> createPut(@Valid @RequestBody Put put) throws URISyntaxException {
        log.debug("REST request to save Put : {}", put);
        if (put.getId() != null) {
            throw new BadRequestAlertException("A new put cannot already have an ID", ENTITY_NAME, "idexists");
        }
        put = putRepository.save(put);
        return ResponseEntity.created(new URI("/api/puts/" + put.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, put.getId().toString()))
            .body(put);
    }

    /**
     * {@code PUT  /puts/:id} : Updates an existing put.
     *
     * @param id the id of the put to save.
     * @param put the put to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated put,
     * or with status {@code 400 (Bad Request)} if the put is not valid,
     * or with status {@code 500 (Internal Server Error)} if the put couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Put> updatePut(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Put put)
        throws URISyntaxException {
        log.debug("REST request to update Put : {}, {}", id, put);
        if (put.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, put.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!putRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        put = putRepository.save(put);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, put.getId().toString()))
            .body(put);
    }

    /**
     * {@code PATCH  /puts/:id} : Partial updates given fields of an existing put, field will ignore if it is null
     *
     * @param id the id of the put to save.
     * @param put the put to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated put,
     * or with status {@code 400 (Bad Request)} if the put is not valid,
     * or with status {@code 404 (Not Found)} if the put is not found,
     * or with status {@code 500 (Internal Server Error)} if the put couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Put> partialUpdatePut(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Put put)
        throws URISyntaxException {
        log.debug("REST request to partial update Put partially : {}, {}", id, put);
        if (put.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, put.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!putRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Put> result = putRepository
            .findById(put.getId())
            .map(existingPut -> {
                if (put.getKey() != null) {
                    existingPut.setKey(put.getKey());
                }
                if (put.getProjectcd() != null) {
                    existingPut.setProjectcd(put.getProjectcd());
                }
                if (put.getPutnummer() != null) {
                    existingPut.setPutnummer(put.getPutnummer());
                }

                return existingPut;
            })
            .map(putRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, put.getId().toString())
        );
    }

    /**
     * {@code GET  /puts} : get all the puts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of puts in body.
     */
    @GetMapping("")
    public List<Put> getAllPuts(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Puts");
        if (eagerload) {
            return putRepository.findAllWithEagerRelationships();
        } else {
            return putRepository.findAll();
        }
    }

    /**
     * {@code GET  /puts/:id} : get the "id" put.
     *
     * @param id the id of the put to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the put, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Put> getPut(@PathVariable("id") Long id) {
        log.debug("REST request to get Put : {}", id);
        Optional<Put> put = putRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(put);
    }

    /**
     * {@code DELETE  /puts/:id} : delete the "id" put.
     *
     * @param id the id of the put to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePut(@PathVariable("id") Long id) {
        log.debug("REST request to delete Put : {}", id);
        putRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
