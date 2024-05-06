package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Activiteitsoort;
import nl.ritense.demo.repository.ActiviteitsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Activiteitsoort}.
 */
@RestController
@RequestMapping("/api/activiteitsoorts")
@Transactional
public class ActiviteitsoortResource {

    private final Logger log = LoggerFactory.getLogger(ActiviteitsoortResource.class);

    private static final String ENTITY_NAME = "activiteitsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActiviteitsoortRepository activiteitsoortRepository;

    public ActiviteitsoortResource(ActiviteitsoortRepository activiteitsoortRepository) {
        this.activiteitsoortRepository = activiteitsoortRepository;
    }

    /**
     * {@code POST  /activiteitsoorts} : Create a new activiteitsoort.
     *
     * @param activiteitsoort the activiteitsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activiteitsoort, or with status {@code 400 (Bad Request)} if the activiteitsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Activiteitsoort> createActiviteitsoort(@Valid @RequestBody Activiteitsoort activiteitsoort)
        throws URISyntaxException {
        log.debug("REST request to save Activiteitsoort : {}", activiteitsoort);
        if (activiteitsoort.getId() != null) {
            throw new BadRequestAlertException("A new activiteitsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        activiteitsoort = activiteitsoortRepository.save(activiteitsoort);
        return ResponseEntity.created(new URI("/api/activiteitsoorts/" + activiteitsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, activiteitsoort.getId().toString()))
            .body(activiteitsoort);
    }

    /**
     * {@code PUT  /activiteitsoorts/:id} : Updates an existing activiteitsoort.
     *
     * @param id the id of the activiteitsoort to save.
     * @param activiteitsoort the activiteitsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activiteitsoort,
     * or with status {@code 400 (Bad Request)} if the activiteitsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activiteitsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Activiteitsoort> updateActiviteitsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Activiteitsoort activiteitsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Activiteitsoort : {}, {}", id, activiteitsoort);
        if (activiteitsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activiteitsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activiteitsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        activiteitsoort = activiteitsoortRepository.save(activiteitsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activiteitsoort.getId().toString()))
            .body(activiteitsoort);
    }

    /**
     * {@code PATCH  /activiteitsoorts/:id} : Partial updates given fields of an existing activiteitsoort, field will ignore if it is null
     *
     * @param id the id of the activiteitsoort to save.
     * @param activiteitsoort the activiteitsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activiteitsoort,
     * or with status {@code 400 (Bad Request)} if the activiteitsoort is not valid,
     * or with status {@code 404 (Not Found)} if the activiteitsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the activiteitsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Activiteitsoort> partialUpdateActiviteitsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Activiteitsoort activiteitsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Activiteitsoort partially : {}, {}", id, activiteitsoort);
        if (activiteitsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activiteitsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activiteitsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Activiteitsoort> result = activiteitsoortRepository
            .findById(activiteitsoort.getId())
            .map(existingActiviteitsoort -> {
                if (activiteitsoort.getNaam() != null) {
                    existingActiviteitsoort.setNaam(activiteitsoort.getNaam());
                }
                if (activiteitsoort.getOmschrijving() != null) {
                    existingActiviteitsoort.setOmschrijving(activiteitsoort.getOmschrijving());
                }

                return existingActiviteitsoort;
            })
            .map(activiteitsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activiteitsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /activiteitsoorts} : get all the activiteitsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activiteitsoorts in body.
     */
    @GetMapping("")
    public List<Activiteitsoort> getAllActiviteitsoorts() {
        log.debug("REST request to get all Activiteitsoorts");
        return activiteitsoortRepository.findAll();
    }

    /**
     * {@code GET  /activiteitsoorts/:id} : get the "id" activiteitsoort.
     *
     * @param id the id of the activiteitsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activiteitsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Activiteitsoort> getActiviteitsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Activiteitsoort : {}", id);
        Optional<Activiteitsoort> activiteitsoort = activiteitsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(activiteitsoort);
    }

    /**
     * {@code DELETE  /activiteitsoorts/:id} : delete the "id" activiteitsoort.
     *
     * @param id the id of the activiteitsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActiviteitsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Activiteitsoort : {}", id);
        activiteitsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
