package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Activasoort;
import nl.ritense.demo.repository.ActivasoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Activasoort}.
 */
@RestController
@RequestMapping("/api/activasoorts")
@Transactional
public class ActivasoortResource {

    private final Logger log = LoggerFactory.getLogger(ActivasoortResource.class);

    private static final String ENTITY_NAME = "activasoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivasoortRepository activasoortRepository;

    public ActivasoortResource(ActivasoortRepository activasoortRepository) {
        this.activasoortRepository = activasoortRepository;
    }

    /**
     * {@code POST  /activasoorts} : Create a new activasoort.
     *
     * @param activasoort the activasoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activasoort, or with status {@code 400 (Bad Request)} if the activasoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Activasoort> createActivasoort(@Valid @RequestBody Activasoort activasoort) throws URISyntaxException {
        log.debug("REST request to save Activasoort : {}", activasoort);
        if (activasoort.getId() != null) {
            throw new BadRequestAlertException("A new activasoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        activasoort = activasoortRepository.save(activasoort);
        return ResponseEntity.created(new URI("/api/activasoorts/" + activasoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, activasoort.getId().toString()))
            .body(activasoort);
    }

    /**
     * {@code PUT  /activasoorts/:id} : Updates an existing activasoort.
     *
     * @param id the id of the activasoort to save.
     * @param activasoort the activasoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activasoort,
     * or with status {@code 400 (Bad Request)} if the activasoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activasoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Activasoort> updateActivasoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Activasoort activasoort
    ) throws URISyntaxException {
        log.debug("REST request to update Activasoort : {}, {}", id, activasoort);
        if (activasoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activasoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activasoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        activasoort = activasoortRepository.save(activasoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activasoort.getId().toString()))
            .body(activasoort);
    }

    /**
     * {@code PATCH  /activasoorts/:id} : Partial updates given fields of an existing activasoort, field will ignore if it is null
     *
     * @param id the id of the activasoort to save.
     * @param activasoort the activasoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activasoort,
     * or with status {@code 400 (Bad Request)} if the activasoort is not valid,
     * or with status {@code 404 (Not Found)} if the activasoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the activasoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Activasoort> partialUpdateActivasoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Activasoort activasoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Activasoort partially : {}, {}", id, activasoort);
        if (activasoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activasoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activasoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Activasoort> result = activasoortRepository
            .findById(activasoort.getId())
            .map(existingActivasoort -> {
                if (activasoort.getNaam() != null) {
                    existingActivasoort.setNaam(activasoort.getNaam());
                }
                if (activasoort.getOmschrijving() != null) {
                    existingActivasoort.setOmschrijving(activasoort.getOmschrijving());
                }

                return existingActivasoort;
            })
            .map(activasoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activasoort.getId().toString())
        );
    }

    /**
     * {@code GET  /activasoorts} : get all the activasoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activasoorts in body.
     */
    @GetMapping("")
    public List<Activasoort> getAllActivasoorts() {
        log.debug("REST request to get all Activasoorts");
        return activasoortRepository.findAll();
    }

    /**
     * {@code GET  /activasoorts/:id} : get the "id" activasoort.
     *
     * @param id the id of the activasoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activasoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Activasoort> getActivasoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Activasoort : {}", id);
        Optional<Activasoort> activasoort = activasoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(activasoort);
    }

    /**
     * {@code DELETE  /activasoorts/:id} : delete the "id" activasoort.
     *
     * @param id the id of the activasoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivasoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Activasoort : {}", id);
        activasoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
