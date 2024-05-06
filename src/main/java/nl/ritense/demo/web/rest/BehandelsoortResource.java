package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Behandelsoort;
import nl.ritense.demo.repository.BehandelsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Behandelsoort}.
 */
@RestController
@RequestMapping("/api/behandelsoorts")
@Transactional
public class BehandelsoortResource {

    private final Logger log = LoggerFactory.getLogger(BehandelsoortResource.class);

    private static final String ENTITY_NAME = "behandelsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BehandelsoortRepository behandelsoortRepository;

    public BehandelsoortResource(BehandelsoortRepository behandelsoortRepository) {
        this.behandelsoortRepository = behandelsoortRepository;
    }

    /**
     * {@code POST  /behandelsoorts} : Create a new behandelsoort.
     *
     * @param behandelsoort the behandelsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new behandelsoort, or with status {@code 400 (Bad Request)} if the behandelsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Behandelsoort> createBehandelsoort(@Valid @RequestBody Behandelsoort behandelsoort) throws URISyntaxException {
        log.debug("REST request to save Behandelsoort : {}", behandelsoort);
        if (behandelsoort.getId() != null) {
            throw new BadRequestAlertException("A new behandelsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        behandelsoort = behandelsoortRepository.save(behandelsoort);
        return ResponseEntity.created(new URI("/api/behandelsoorts/" + behandelsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, behandelsoort.getId().toString()))
            .body(behandelsoort);
    }

    /**
     * {@code PUT  /behandelsoorts/:id} : Updates an existing behandelsoort.
     *
     * @param id the id of the behandelsoort to save.
     * @param behandelsoort the behandelsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated behandelsoort,
     * or with status {@code 400 (Bad Request)} if the behandelsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the behandelsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Behandelsoort> updateBehandelsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Behandelsoort behandelsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Behandelsoort : {}, {}", id, behandelsoort);
        if (behandelsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, behandelsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!behandelsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        behandelsoort = behandelsoortRepository.save(behandelsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, behandelsoort.getId().toString()))
            .body(behandelsoort);
    }

    /**
     * {@code PATCH  /behandelsoorts/:id} : Partial updates given fields of an existing behandelsoort, field will ignore if it is null
     *
     * @param id the id of the behandelsoort to save.
     * @param behandelsoort the behandelsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated behandelsoort,
     * or with status {@code 400 (Bad Request)} if the behandelsoort is not valid,
     * or with status {@code 404 (Not Found)} if the behandelsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the behandelsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Behandelsoort> partialUpdateBehandelsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Behandelsoort behandelsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Behandelsoort partially : {}, {}", id, behandelsoort);
        if (behandelsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, behandelsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!behandelsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Behandelsoort> result = behandelsoortRepository
            .findById(behandelsoort.getId())
            .map(existingBehandelsoort -> {
                if (behandelsoort.getNaam() != null) {
                    existingBehandelsoort.setNaam(behandelsoort.getNaam());
                }
                if (behandelsoort.getOmschrijving() != null) {
                    existingBehandelsoort.setOmschrijving(behandelsoort.getOmschrijving());
                }

                return existingBehandelsoort;
            })
            .map(behandelsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, behandelsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /behandelsoorts} : get all the behandelsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of behandelsoorts in body.
     */
    @GetMapping("")
    public List<Behandelsoort> getAllBehandelsoorts() {
        log.debug("REST request to get all Behandelsoorts");
        return behandelsoortRepository.findAll();
    }

    /**
     * {@code GET  /behandelsoorts/:id} : get the "id" behandelsoort.
     *
     * @param id the id of the behandelsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the behandelsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Behandelsoort> getBehandelsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Behandelsoort : {}", id);
        Optional<Behandelsoort> behandelsoort = behandelsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(behandelsoort);
    }

    /**
     * {@code DELETE  /behandelsoorts/:id} : delete the "id" behandelsoort.
     *
     * @param id the id of the behandelsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBehandelsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Behandelsoort : {}", id);
        behandelsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
