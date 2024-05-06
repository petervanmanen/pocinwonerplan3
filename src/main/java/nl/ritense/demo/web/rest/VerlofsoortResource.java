package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verlofsoort;
import nl.ritense.demo.repository.VerlofsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verlofsoort}.
 */
@RestController
@RequestMapping("/api/verlofsoorts")
@Transactional
public class VerlofsoortResource {

    private final Logger log = LoggerFactory.getLogger(VerlofsoortResource.class);

    private static final String ENTITY_NAME = "verlofsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerlofsoortRepository verlofsoortRepository;

    public VerlofsoortResource(VerlofsoortRepository verlofsoortRepository) {
        this.verlofsoortRepository = verlofsoortRepository;
    }

    /**
     * {@code POST  /verlofsoorts} : Create a new verlofsoort.
     *
     * @param verlofsoort the verlofsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verlofsoort, or with status {@code 400 (Bad Request)} if the verlofsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verlofsoort> createVerlofsoort(@Valid @RequestBody Verlofsoort verlofsoort) throws URISyntaxException {
        log.debug("REST request to save Verlofsoort : {}", verlofsoort);
        if (verlofsoort.getId() != null) {
            throw new BadRequestAlertException("A new verlofsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verlofsoort = verlofsoortRepository.save(verlofsoort);
        return ResponseEntity.created(new URI("/api/verlofsoorts/" + verlofsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verlofsoort.getId().toString()))
            .body(verlofsoort);
    }

    /**
     * {@code PUT  /verlofsoorts/:id} : Updates an existing verlofsoort.
     *
     * @param id the id of the verlofsoort to save.
     * @param verlofsoort the verlofsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verlofsoort,
     * or with status {@code 400 (Bad Request)} if the verlofsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verlofsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verlofsoort> updateVerlofsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verlofsoort verlofsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Verlofsoort : {}, {}", id, verlofsoort);
        if (verlofsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verlofsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verlofsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verlofsoort = verlofsoortRepository.save(verlofsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verlofsoort.getId().toString()))
            .body(verlofsoort);
    }

    /**
     * {@code PATCH  /verlofsoorts/:id} : Partial updates given fields of an existing verlofsoort, field will ignore if it is null
     *
     * @param id the id of the verlofsoort to save.
     * @param verlofsoort the verlofsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verlofsoort,
     * or with status {@code 400 (Bad Request)} if the verlofsoort is not valid,
     * or with status {@code 404 (Not Found)} if the verlofsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the verlofsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verlofsoort> partialUpdateVerlofsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verlofsoort verlofsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verlofsoort partially : {}, {}", id, verlofsoort);
        if (verlofsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verlofsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verlofsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verlofsoort> result = verlofsoortRepository
            .findById(verlofsoort.getId())
            .map(existingVerlofsoort -> {
                if (verlofsoort.getNaam() != null) {
                    existingVerlofsoort.setNaam(verlofsoort.getNaam());
                }
                if (verlofsoort.getOmschrijving() != null) {
                    existingVerlofsoort.setOmschrijving(verlofsoort.getOmschrijving());
                }

                return existingVerlofsoort;
            })
            .map(verlofsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verlofsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /verlofsoorts} : get all the verlofsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verlofsoorts in body.
     */
    @GetMapping("")
    public List<Verlofsoort> getAllVerlofsoorts() {
        log.debug("REST request to get all Verlofsoorts");
        return verlofsoortRepository.findAll();
    }

    /**
     * {@code GET  /verlofsoorts/:id} : get the "id" verlofsoort.
     *
     * @param id the id of the verlofsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verlofsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verlofsoort> getVerlofsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Verlofsoort : {}", id);
        Optional<Verlofsoort> verlofsoort = verlofsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verlofsoort);
    }

    /**
     * {@code DELETE  /verlofsoorts/:id} : delete the "id" verlofsoort.
     *
     * @param id the id of the verlofsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerlofsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verlofsoort : {}", id);
        verlofsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
