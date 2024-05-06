package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Uitstroomredensoort;
import nl.ritense.demo.repository.UitstroomredensoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitstroomredensoort}.
 */
@RestController
@RequestMapping("/api/uitstroomredensoorts")
@Transactional
public class UitstroomredensoortResource {

    private final Logger log = LoggerFactory.getLogger(UitstroomredensoortResource.class);

    private static final String ENTITY_NAME = "uitstroomredensoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitstroomredensoortRepository uitstroomredensoortRepository;

    public UitstroomredensoortResource(UitstroomredensoortRepository uitstroomredensoortRepository) {
        this.uitstroomredensoortRepository = uitstroomredensoortRepository;
    }

    /**
     * {@code POST  /uitstroomredensoorts} : Create a new uitstroomredensoort.
     *
     * @param uitstroomredensoort the uitstroomredensoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitstroomredensoort, or with status {@code 400 (Bad Request)} if the uitstroomredensoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitstroomredensoort> createUitstroomredensoort(@Valid @RequestBody Uitstroomredensoort uitstroomredensoort)
        throws URISyntaxException {
        log.debug("REST request to save Uitstroomredensoort : {}", uitstroomredensoort);
        if (uitstroomredensoort.getId() != null) {
            throw new BadRequestAlertException("A new uitstroomredensoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitstroomredensoort = uitstroomredensoortRepository.save(uitstroomredensoort);
        return ResponseEntity.created(new URI("/api/uitstroomredensoorts/" + uitstroomredensoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitstroomredensoort.getId().toString()))
            .body(uitstroomredensoort);
    }

    /**
     * {@code PUT  /uitstroomredensoorts/:id} : Updates an existing uitstroomredensoort.
     *
     * @param id the id of the uitstroomredensoort to save.
     * @param uitstroomredensoort the uitstroomredensoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitstroomredensoort,
     * or with status {@code 400 (Bad Request)} if the uitstroomredensoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uitstroomredensoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Uitstroomredensoort> updateUitstroomredensoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Uitstroomredensoort uitstroomredensoort
    ) throws URISyntaxException {
        log.debug("REST request to update Uitstroomredensoort : {}, {}", id, uitstroomredensoort);
        if (uitstroomredensoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitstroomredensoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitstroomredensoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uitstroomredensoort = uitstroomredensoortRepository.save(uitstroomredensoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitstroomredensoort.getId().toString()))
            .body(uitstroomredensoort);
    }

    /**
     * {@code PATCH  /uitstroomredensoorts/:id} : Partial updates given fields of an existing uitstroomredensoort, field will ignore if it is null
     *
     * @param id the id of the uitstroomredensoort to save.
     * @param uitstroomredensoort the uitstroomredensoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitstroomredensoort,
     * or with status {@code 400 (Bad Request)} if the uitstroomredensoort is not valid,
     * or with status {@code 404 (Not Found)} if the uitstroomredensoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the uitstroomredensoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Uitstroomredensoort> partialUpdateUitstroomredensoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Uitstroomredensoort uitstroomredensoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uitstroomredensoort partially : {}, {}", id, uitstroomredensoort);
        if (uitstroomredensoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitstroomredensoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitstroomredensoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Uitstroomredensoort> result = uitstroomredensoortRepository
            .findById(uitstroomredensoort.getId())
            .map(existingUitstroomredensoort -> {
                if (uitstroomredensoort.getNaam() != null) {
                    existingUitstroomredensoort.setNaam(uitstroomredensoort.getNaam());
                }
                if (uitstroomredensoort.getOmschrijving() != null) {
                    existingUitstroomredensoort.setOmschrijving(uitstroomredensoort.getOmschrijving());
                }

                return existingUitstroomredensoort;
            })
            .map(uitstroomredensoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitstroomredensoort.getId().toString())
        );
    }

    /**
     * {@code GET  /uitstroomredensoorts} : get all the uitstroomredensoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitstroomredensoorts in body.
     */
    @GetMapping("")
    public List<Uitstroomredensoort> getAllUitstroomredensoorts() {
        log.debug("REST request to get all Uitstroomredensoorts");
        return uitstroomredensoortRepository.findAll();
    }

    /**
     * {@code GET  /uitstroomredensoorts/:id} : get the "id" uitstroomredensoort.
     *
     * @param id the id of the uitstroomredensoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitstroomredensoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitstroomredensoort> getUitstroomredensoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitstroomredensoort : {}", id);
        Optional<Uitstroomredensoort> uitstroomredensoort = uitstroomredensoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitstroomredensoort);
    }

    /**
     * {@code DELETE  /uitstroomredensoorts/:id} : delete the "id" uitstroomredensoort.
     *
     * @param id the id of the uitstroomredensoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitstroomredensoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitstroomredensoort : {}", id);
        uitstroomredensoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
