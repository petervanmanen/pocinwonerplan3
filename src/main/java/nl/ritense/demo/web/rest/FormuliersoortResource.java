package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Formuliersoort;
import nl.ritense.demo.repository.FormuliersoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Formuliersoort}.
 */
@RestController
@RequestMapping("/api/formuliersoorts")
@Transactional
public class FormuliersoortResource {

    private final Logger log = LoggerFactory.getLogger(FormuliersoortResource.class);

    private static final String ENTITY_NAME = "formuliersoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormuliersoortRepository formuliersoortRepository;

    public FormuliersoortResource(FormuliersoortRepository formuliersoortRepository) {
        this.formuliersoortRepository = formuliersoortRepository;
    }

    /**
     * {@code POST  /formuliersoorts} : Create a new formuliersoort.
     *
     * @param formuliersoort the formuliersoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formuliersoort, or with status {@code 400 (Bad Request)} if the formuliersoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Formuliersoort> createFormuliersoort(@Valid @RequestBody Formuliersoort formuliersoort)
        throws URISyntaxException {
        log.debug("REST request to save Formuliersoort : {}", formuliersoort);
        if (formuliersoort.getId() != null) {
            throw new BadRequestAlertException("A new formuliersoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formuliersoort = formuliersoortRepository.save(formuliersoort);
        return ResponseEntity.created(new URI("/api/formuliersoorts/" + formuliersoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, formuliersoort.getId().toString()))
            .body(formuliersoort);
    }

    /**
     * {@code PUT  /formuliersoorts/:id} : Updates an existing formuliersoort.
     *
     * @param id the id of the formuliersoort to save.
     * @param formuliersoort the formuliersoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formuliersoort,
     * or with status {@code 400 (Bad Request)} if the formuliersoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formuliersoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Formuliersoort> updateFormuliersoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Formuliersoort formuliersoort
    ) throws URISyntaxException {
        log.debug("REST request to update Formuliersoort : {}, {}", id, formuliersoort);
        if (formuliersoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formuliersoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formuliersoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formuliersoort = formuliersoortRepository.save(formuliersoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formuliersoort.getId().toString()))
            .body(formuliersoort);
    }

    /**
     * {@code PATCH  /formuliersoorts/:id} : Partial updates given fields of an existing formuliersoort, field will ignore if it is null
     *
     * @param id the id of the formuliersoort to save.
     * @param formuliersoort the formuliersoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formuliersoort,
     * or with status {@code 400 (Bad Request)} if the formuliersoort is not valid,
     * or with status {@code 404 (Not Found)} if the formuliersoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the formuliersoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Formuliersoort> partialUpdateFormuliersoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Formuliersoort formuliersoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formuliersoort partially : {}, {}", id, formuliersoort);
        if (formuliersoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formuliersoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formuliersoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Formuliersoort> result = formuliersoortRepository
            .findById(formuliersoort.getId())
            .map(existingFormuliersoort -> {
                if (formuliersoort.getIngebruik() != null) {
                    existingFormuliersoort.setIngebruik(formuliersoort.getIngebruik());
                }
                if (formuliersoort.getNaam() != null) {
                    existingFormuliersoort.setNaam(formuliersoort.getNaam());
                }
                if (formuliersoort.getOnderwerp() != null) {
                    existingFormuliersoort.setOnderwerp(formuliersoort.getOnderwerp());
                }

                return existingFormuliersoort;
            })
            .map(formuliersoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formuliersoort.getId().toString())
        );
    }

    /**
     * {@code GET  /formuliersoorts} : get all the formuliersoorts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formuliersoorts in body.
     */
    @GetMapping("")
    public List<Formuliersoort> getAllFormuliersoorts(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Formuliersoorts");
        if (eagerload) {
            return formuliersoortRepository.findAllWithEagerRelationships();
        } else {
            return formuliersoortRepository.findAll();
        }
    }

    /**
     * {@code GET  /formuliersoorts/:id} : get the "id" formuliersoort.
     *
     * @param id the id of the formuliersoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formuliersoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Formuliersoort> getFormuliersoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Formuliersoort : {}", id);
        Optional<Formuliersoort> formuliersoort = formuliersoortRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(formuliersoort);
    }

    /**
     * {@code DELETE  /formuliersoorts/:id} : delete the "id" formuliersoort.
     *
     * @param id the id of the formuliersoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormuliersoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Formuliersoort : {}", id);
        formuliersoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
