package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Stelling;
import nl.ritense.demo.repository.StellingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Stelling}.
 */
@RestController
@RequestMapping("/api/stellings")
@Transactional
public class StellingResource {

    private final Logger log = LoggerFactory.getLogger(StellingResource.class);

    private static final String ENTITY_NAME = "stelling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StellingRepository stellingRepository;

    public StellingResource(StellingRepository stellingRepository) {
        this.stellingRepository = stellingRepository;
    }

    /**
     * {@code POST  /stellings} : Create a new stelling.
     *
     * @param stelling the stelling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stelling, or with status {@code 400 (Bad Request)} if the stelling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Stelling> createStelling(@Valid @RequestBody Stelling stelling) throws URISyntaxException {
        log.debug("REST request to save Stelling : {}", stelling);
        if (stelling.getId() != null) {
            throw new BadRequestAlertException("A new stelling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        stelling = stellingRepository.save(stelling);
        return ResponseEntity.created(new URI("/api/stellings/" + stelling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, stelling.getId().toString()))
            .body(stelling);
    }

    /**
     * {@code PUT  /stellings/:id} : Updates an existing stelling.
     *
     * @param id the id of the stelling to save.
     * @param stelling the stelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stelling,
     * or with status {@code 400 (Bad Request)} if the stelling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stelling> updateStelling(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Stelling stelling
    ) throws URISyntaxException {
        log.debug("REST request to update Stelling : {}, {}", id, stelling);
        if (stelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        stelling = stellingRepository.save(stelling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stelling.getId().toString()))
            .body(stelling);
    }

    /**
     * {@code PATCH  /stellings/:id} : Partial updates given fields of an existing stelling, field will ignore if it is null
     *
     * @param id the id of the stelling to save.
     * @param stelling the stelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stelling,
     * or with status {@code 400 (Bad Request)} if the stelling is not valid,
     * or with status {@code 404 (Not Found)} if the stelling is not found,
     * or with status {@code 500 (Internal Server Error)} if the stelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Stelling> partialUpdateStelling(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Stelling stelling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stelling partially : {}, {}", id, stelling);
        if (stelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Stelling> result = stellingRepository
            .findById(stelling.getId())
            .map(existingStelling -> {
                if (stelling.getInhoud() != null) {
                    existingStelling.setInhoud(stelling.getInhoud());
                }
                if (stelling.getStellingcode() != null) {
                    existingStelling.setStellingcode(stelling.getStellingcode());
                }

                return existingStelling;
            })
            .map(stellingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stelling.getId().toString())
        );
    }

    /**
     * {@code GET  /stellings} : get all the stellings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stellings in body.
     */
    @GetMapping("")
    public List<Stelling> getAllStellings() {
        log.debug("REST request to get all Stellings");
        return stellingRepository.findAll();
    }

    /**
     * {@code GET  /stellings/:id} : get the "id" stelling.
     *
     * @param id the id of the stelling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stelling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stelling> getStelling(@PathVariable("id") Long id) {
        log.debug("REST request to get Stelling : {}", id);
        Optional<Stelling> stelling = stellingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stelling);
    }

    /**
     * {@code DELETE  /stellings/:id} : delete the "id" stelling.
     *
     * @param id the id of the stelling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStelling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Stelling : {}", id);
        stellingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
