package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Fraudesoort;
import nl.ritense.demo.repository.FraudesoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Fraudesoort}.
 */
@RestController
@RequestMapping("/api/fraudesoorts")
@Transactional
public class FraudesoortResource {

    private final Logger log = LoggerFactory.getLogger(FraudesoortResource.class);

    private static final String ENTITY_NAME = "fraudesoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraudesoortRepository fraudesoortRepository;

    public FraudesoortResource(FraudesoortRepository fraudesoortRepository) {
        this.fraudesoortRepository = fraudesoortRepository;
    }

    /**
     * {@code POST  /fraudesoorts} : Create a new fraudesoort.
     *
     * @param fraudesoort the fraudesoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fraudesoort, or with status {@code 400 (Bad Request)} if the fraudesoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Fraudesoort> createFraudesoort(@Valid @RequestBody Fraudesoort fraudesoort) throws URISyntaxException {
        log.debug("REST request to save Fraudesoort : {}", fraudesoort);
        if (fraudesoort.getId() != null) {
            throw new BadRequestAlertException("A new fraudesoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fraudesoort = fraudesoortRepository.save(fraudesoort);
        return ResponseEntity.created(new URI("/api/fraudesoorts/" + fraudesoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, fraudesoort.getId().toString()))
            .body(fraudesoort);
    }

    /**
     * {@code PUT  /fraudesoorts/:id} : Updates an existing fraudesoort.
     *
     * @param id the id of the fraudesoort to save.
     * @param fraudesoort the fraudesoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraudesoort,
     * or with status {@code 400 (Bad Request)} if the fraudesoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fraudesoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Fraudesoort> updateFraudesoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Fraudesoort fraudesoort
    ) throws URISyntaxException {
        log.debug("REST request to update Fraudesoort : {}, {}", id, fraudesoort);
        if (fraudesoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraudesoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraudesoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fraudesoort = fraudesoortRepository.save(fraudesoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fraudesoort.getId().toString()))
            .body(fraudesoort);
    }

    /**
     * {@code PATCH  /fraudesoorts/:id} : Partial updates given fields of an existing fraudesoort, field will ignore if it is null
     *
     * @param id the id of the fraudesoort to save.
     * @param fraudesoort the fraudesoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraudesoort,
     * or with status {@code 400 (Bad Request)} if the fraudesoort is not valid,
     * or with status {@code 404 (Not Found)} if the fraudesoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the fraudesoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Fraudesoort> partialUpdateFraudesoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Fraudesoort fraudesoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fraudesoort partially : {}, {}", id, fraudesoort);
        if (fraudesoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraudesoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraudesoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Fraudesoort> result = fraudesoortRepository
            .findById(fraudesoort.getId())
            .map(existingFraudesoort -> {
                if (fraudesoort.getNaam() != null) {
                    existingFraudesoort.setNaam(fraudesoort.getNaam());
                }

                return existingFraudesoort;
            })
            .map(fraudesoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fraudesoort.getId().toString())
        );
    }

    /**
     * {@code GET  /fraudesoorts} : get all the fraudesoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fraudesoorts in body.
     */
    @GetMapping("")
    public List<Fraudesoort> getAllFraudesoorts() {
        log.debug("REST request to get all Fraudesoorts");
        return fraudesoortRepository.findAll();
    }

    /**
     * {@code GET  /fraudesoorts/:id} : get the "id" fraudesoort.
     *
     * @param id the id of the fraudesoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fraudesoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Fraudesoort> getFraudesoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Fraudesoort : {}", id);
        Optional<Fraudesoort> fraudesoort = fraudesoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fraudesoort);
    }

    /**
     * {@code DELETE  /fraudesoorts/:id} : delete the "id" fraudesoort.
     *
     * @param id the id of the fraudesoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFraudesoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Fraudesoort : {}", id);
        fraudesoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
