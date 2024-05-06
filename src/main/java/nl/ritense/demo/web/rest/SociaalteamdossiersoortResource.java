package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sociaalteamdossiersoort;
import nl.ritense.demo.repository.SociaalteamdossiersoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sociaalteamdossiersoort}.
 */
@RestController
@RequestMapping("/api/sociaalteamdossiersoorts")
@Transactional
public class SociaalteamdossiersoortResource {

    private final Logger log = LoggerFactory.getLogger(SociaalteamdossiersoortResource.class);

    private static final String ENTITY_NAME = "sociaalteamdossiersoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SociaalteamdossiersoortRepository sociaalteamdossiersoortRepository;

    public SociaalteamdossiersoortResource(SociaalteamdossiersoortRepository sociaalteamdossiersoortRepository) {
        this.sociaalteamdossiersoortRepository = sociaalteamdossiersoortRepository;
    }

    /**
     * {@code POST  /sociaalteamdossiersoorts} : Create a new sociaalteamdossiersoort.
     *
     * @param sociaalteamdossiersoort the sociaalteamdossiersoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sociaalteamdossiersoort, or with status {@code 400 (Bad Request)} if the sociaalteamdossiersoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sociaalteamdossiersoort> createSociaalteamdossiersoort(
        @RequestBody Sociaalteamdossiersoort sociaalteamdossiersoort
    ) throws URISyntaxException {
        log.debug("REST request to save Sociaalteamdossiersoort : {}", sociaalteamdossiersoort);
        if (sociaalteamdossiersoort.getId() != null) {
            throw new BadRequestAlertException("A new sociaalteamdossiersoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sociaalteamdossiersoort = sociaalteamdossiersoortRepository.save(sociaalteamdossiersoort);
        return ResponseEntity.created(new URI("/api/sociaalteamdossiersoorts/" + sociaalteamdossiersoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sociaalteamdossiersoort.getId().toString()))
            .body(sociaalteamdossiersoort);
    }

    /**
     * {@code PUT  /sociaalteamdossiersoorts/:id} : Updates an existing sociaalteamdossiersoort.
     *
     * @param id the id of the sociaalteamdossiersoort to save.
     * @param sociaalteamdossiersoort the sociaalteamdossiersoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sociaalteamdossiersoort,
     * or with status {@code 400 (Bad Request)} if the sociaalteamdossiersoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sociaalteamdossiersoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sociaalteamdossiersoort> updateSociaalteamdossiersoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sociaalteamdossiersoort sociaalteamdossiersoort
    ) throws URISyntaxException {
        log.debug("REST request to update Sociaalteamdossiersoort : {}, {}", id, sociaalteamdossiersoort);
        if (sociaalteamdossiersoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sociaalteamdossiersoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sociaalteamdossiersoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sociaalteamdossiersoort = sociaalteamdossiersoortRepository.save(sociaalteamdossiersoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sociaalteamdossiersoort.getId().toString()))
            .body(sociaalteamdossiersoort);
    }

    /**
     * {@code PATCH  /sociaalteamdossiersoorts/:id} : Partial updates given fields of an existing sociaalteamdossiersoort, field will ignore if it is null
     *
     * @param id the id of the sociaalteamdossiersoort to save.
     * @param sociaalteamdossiersoort the sociaalteamdossiersoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sociaalteamdossiersoort,
     * or with status {@code 400 (Bad Request)} if the sociaalteamdossiersoort is not valid,
     * or with status {@code 404 (Not Found)} if the sociaalteamdossiersoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the sociaalteamdossiersoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sociaalteamdossiersoort> partialUpdateSociaalteamdossiersoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sociaalteamdossiersoort sociaalteamdossiersoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sociaalteamdossiersoort partially : {}, {}", id, sociaalteamdossiersoort);
        if (sociaalteamdossiersoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sociaalteamdossiersoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sociaalteamdossiersoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sociaalteamdossiersoort> result = sociaalteamdossiersoortRepository
            .findById(sociaalteamdossiersoort.getId())
            .map(existingSociaalteamdossiersoort -> {
                if (sociaalteamdossiersoort.getNaam() != null) {
                    existingSociaalteamdossiersoort.setNaam(sociaalteamdossiersoort.getNaam());
                }
                if (sociaalteamdossiersoort.getOmschrijving() != null) {
                    existingSociaalteamdossiersoort.setOmschrijving(sociaalteamdossiersoort.getOmschrijving());
                }

                return existingSociaalteamdossiersoort;
            })
            .map(sociaalteamdossiersoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sociaalteamdossiersoort.getId().toString())
        );
    }

    /**
     * {@code GET  /sociaalteamdossiersoorts} : get all the sociaalteamdossiersoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sociaalteamdossiersoorts in body.
     */
    @GetMapping("")
    public List<Sociaalteamdossiersoort> getAllSociaalteamdossiersoorts() {
        log.debug("REST request to get all Sociaalteamdossiersoorts");
        return sociaalteamdossiersoortRepository.findAll();
    }

    /**
     * {@code GET  /sociaalteamdossiersoorts/:id} : get the "id" sociaalteamdossiersoort.
     *
     * @param id the id of the sociaalteamdossiersoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sociaalteamdossiersoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sociaalteamdossiersoort> getSociaalteamdossiersoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Sociaalteamdossiersoort : {}", id);
        Optional<Sociaalteamdossiersoort> sociaalteamdossiersoort = sociaalteamdossiersoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sociaalteamdossiersoort);
    }

    /**
     * {@code DELETE  /sociaalteamdossiersoorts/:id} : delete the "id" sociaalteamdossiersoort.
     *
     * @param id the id of the sociaalteamdossiersoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSociaalteamdossiersoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sociaalteamdossiersoort : {}", id);
        sociaalteamdossiersoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
