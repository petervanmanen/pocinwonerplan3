package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Indeling;
import nl.ritense.demo.repository.IndelingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Indeling}.
 */
@RestController
@RequestMapping("/api/indelings")
@Transactional
public class IndelingResource {

    private final Logger log = LoggerFactory.getLogger(IndelingResource.class);

    private static final String ENTITY_NAME = "indeling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndelingRepository indelingRepository;

    public IndelingResource(IndelingRepository indelingRepository) {
        this.indelingRepository = indelingRepository;
    }

    /**
     * {@code POST  /indelings} : Create a new indeling.
     *
     * @param indeling the indeling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indeling, or with status {@code 400 (Bad Request)} if the indeling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Indeling> createIndeling(@Valid @RequestBody Indeling indeling) throws URISyntaxException {
        log.debug("REST request to save Indeling : {}", indeling);
        if (indeling.getId() != null) {
            throw new BadRequestAlertException("A new indeling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        indeling = indelingRepository.save(indeling);
        return ResponseEntity.created(new URI("/api/indelings/" + indeling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, indeling.getId().toString()))
            .body(indeling);
    }

    /**
     * {@code PUT  /indelings/:id} : Updates an existing indeling.
     *
     * @param id the id of the indeling to save.
     * @param indeling the indeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indeling,
     * or with status {@code 400 (Bad Request)} if the indeling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Indeling> updateIndeling(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Indeling indeling
    ) throws URISyntaxException {
        log.debug("REST request to update Indeling : {}, {}", id, indeling);
        if (indeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        indeling = indelingRepository.save(indeling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indeling.getId().toString()))
            .body(indeling);
    }

    /**
     * {@code PATCH  /indelings/:id} : Partial updates given fields of an existing indeling, field will ignore if it is null
     *
     * @param id the id of the indeling to save.
     * @param indeling the indeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indeling,
     * or with status {@code 400 (Bad Request)} if the indeling is not valid,
     * or with status {@code 404 (Not Found)} if the indeling is not found,
     * or with status {@code 500 (Internal Server Error)} if the indeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Indeling> partialUpdateIndeling(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Indeling indeling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Indeling partially : {}, {}", id, indeling);
        if (indeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Indeling> result = indelingRepository
            .findById(indeling.getId())
            .map(existingIndeling -> {
                if (indeling.getIndelingsoort() != null) {
                    existingIndeling.setIndelingsoort(indeling.getIndelingsoort());
                }
                if (indeling.getNaam() != null) {
                    existingIndeling.setNaam(indeling.getNaam());
                }
                if (indeling.getNummer() != null) {
                    existingIndeling.setNummer(indeling.getNummer());
                }
                if (indeling.getOmschrijving() != null) {
                    existingIndeling.setOmschrijving(indeling.getOmschrijving());
                }

                return existingIndeling;
            })
            .map(indelingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indeling.getId().toString())
        );
    }

    /**
     * {@code GET  /indelings} : get all the indelings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indelings in body.
     */
    @GetMapping("")
    public List<Indeling> getAllIndelings() {
        log.debug("REST request to get all Indelings");
        return indelingRepository.findAll();
    }

    /**
     * {@code GET  /indelings/:id} : get the "id" indeling.
     *
     * @param id the id of the indeling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indeling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Indeling> getIndeling(@PathVariable("id") Long id) {
        log.debug("REST request to get Indeling : {}", id);
        Optional<Indeling> indeling = indelingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(indeling);
    }

    /**
     * {@code DELETE  /indelings/:id} : delete the "id" indeling.
     *
     * @param id the id of the indeling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndeling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Indeling : {}", id);
        indelingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
