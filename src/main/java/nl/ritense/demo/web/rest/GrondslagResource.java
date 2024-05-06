package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Grondslag;
import nl.ritense.demo.repository.GrondslagRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Grondslag}.
 */
@RestController
@RequestMapping("/api/grondslags")
@Transactional
public class GrondslagResource {

    private final Logger log = LoggerFactory.getLogger(GrondslagResource.class);

    private static final String ENTITY_NAME = "grondslag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrondslagRepository grondslagRepository;

    public GrondslagResource(GrondslagRepository grondslagRepository) {
        this.grondslagRepository = grondslagRepository;
    }

    /**
     * {@code POST  /grondslags} : Create a new grondslag.
     *
     * @param grondslag the grondslag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grondslag, or with status {@code 400 (Bad Request)} if the grondslag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Grondslag> createGrondslag(@Valid @RequestBody Grondslag grondslag) throws URISyntaxException {
        log.debug("REST request to save Grondslag : {}", grondslag);
        if (grondslag.getId() != null) {
            throw new BadRequestAlertException("A new grondslag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grondslag = grondslagRepository.save(grondslag);
        return ResponseEntity.created(new URI("/api/grondslags/" + grondslag.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, grondslag.getId().toString()))
            .body(grondslag);
    }

    /**
     * {@code PUT  /grondslags/:id} : Updates an existing grondslag.
     *
     * @param id the id of the grondslag to save.
     * @param grondslag the grondslag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grondslag,
     * or with status {@code 400 (Bad Request)} if the grondslag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grondslag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Grondslag> updateGrondslag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Grondslag grondslag
    ) throws URISyntaxException {
        log.debug("REST request to update Grondslag : {}, {}", id, grondslag);
        if (grondslag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grondslag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grondslagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grondslag = grondslagRepository.save(grondslag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grondslag.getId().toString()))
            .body(grondslag);
    }

    /**
     * {@code PATCH  /grondslags/:id} : Partial updates given fields of an existing grondslag, field will ignore if it is null
     *
     * @param id the id of the grondslag to save.
     * @param grondslag the grondslag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grondslag,
     * or with status {@code 400 (Bad Request)} if the grondslag is not valid,
     * or with status {@code 404 (Not Found)} if the grondslag is not found,
     * or with status {@code 500 (Internal Server Error)} if the grondslag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Grondslag> partialUpdateGrondslag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Grondslag grondslag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Grondslag partially : {}, {}", id, grondslag);
        if (grondslag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grondslag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grondslagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Grondslag> result = grondslagRepository
            .findById(grondslag.getId())
            .map(existingGrondslag -> {
                if (grondslag.getCode() != null) {
                    existingGrondslag.setCode(grondslag.getCode());
                }
                if (grondslag.getOmschrijving() != null) {
                    existingGrondslag.setOmschrijving(grondslag.getOmschrijving());
                }

                return existingGrondslag;
            })
            .map(grondslagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grondslag.getId().toString())
        );
    }

    /**
     * {@code GET  /grondslags} : get all the grondslags.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grondslags in body.
     */
    @GetMapping("")
    public List<Grondslag> getAllGrondslags(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Grondslags");
        if (eagerload) {
            return grondslagRepository.findAllWithEagerRelationships();
        } else {
            return grondslagRepository.findAll();
        }
    }

    /**
     * {@code GET  /grondslags/:id} : get the "id" grondslag.
     *
     * @param id the id of the grondslag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grondslag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Grondslag> getGrondslag(@PathVariable("id") Long id) {
        log.debug("REST request to get Grondslag : {}", id);
        Optional<Grondslag> grondslag = grondslagRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(grondslag);
    }

    /**
     * {@code DELETE  /grondslags/:id} : delete the "id" grondslag.
     *
     * @param id the id of the grondslag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrondslag(@PathVariable("id") Long id) {
        log.debug("REST request to delete Grondslag : {}", id);
        grondslagRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
