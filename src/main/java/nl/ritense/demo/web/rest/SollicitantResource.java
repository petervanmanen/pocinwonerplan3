package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sollicitant;
import nl.ritense.demo.repository.SollicitantRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sollicitant}.
 */
@RestController
@RequestMapping("/api/sollicitants")
@Transactional
public class SollicitantResource {

    private final Logger log = LoggerFactory.getLogger(SollicitantResource.class);

    private static final String ENTITY_NAME = "sollicitant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SollicitantRepository sollicitantRepository;

    public SollicitantResource(SollicitantRepository sollicitantRepository) {
        this.sollicitantRepository = sollicitantRepository;
    }

    /**
     * {@code POST  /sollicitants} : Create a new sollicitant.
     *
     * @param sollicitant the sollicitant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sollicitant, or with status {@code 400 (Bad Request)} if the sollicitant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sollicitant> createSollicitant(@Valid @RequestBody Sollicitant sollicitant) throws URISyntaxException {
        log.debug("REST request to save Sollicitant : {}", sollicitant);
        if (sollicitant.getId() != null) {
            throw new BadRequestAlertException("A new sollicitant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sollicitant = sollicitantRepository.save(sollicitant);
        return ResponseEntity.created(new URI("/api/sollicitants/" + sollicitant.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sollicitant.getId().toString()))
            .body(sollicitant);
    }

    /**
     * {@code PUT  /sollicitants/:id} : Updates an existing sollicitant.
     *
     * @param id the id of the sollicitant to save.
     * @param sollicitant the sollicitant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sollicitant,
     * or with status {@code 400 (Bad Request)} if the sollicitant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sollicitant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sollicitant> updateSollicitant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sollicitant sollicitant
    ) throws URISyntaxException {
        log.debug("REST request to update Sollicitant : {}, {}", id, sollicitant);
        if (sollicitant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sollicitant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sollicitantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sollicitant = sollicitantRepository.save(sollicitant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sollicitant.getId().toString()))
            .body(sollicitant);
    }

    /**
     * {@code PATCH  /sollicitants/:id} : Partial updates given fields of an existing sollicitant, field will ignore if it is null
     *
     * @param id the id of the sollicitant to save.
     * @param sollicitant the sollicitant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sollicitant,
     * or with status {@code 400 (Bad Request)} if the sollicitant is not valid,
     * or with status {@code 404 (Not Found)} if the sollicitant is not found,
     * or with status {@code 500 (Internal Server Error)} if the sollicitant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sollicitant> partialUpdateSollicitant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sollicitant sollicitant
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sollicitant partially : {}, {}", id, sollicitant);
        if (sollicitant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sollicitant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sollicitantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sollicitant> result = sollicitantRepository.findById(sollicitant.getId()).map(sollicitantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sollicitant.getId().toString())
        );
    }

    /**
     * {@code GET  /sollicitants} : get all the sollicitants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sollicitants in body.
     */
    @GetMapping("")
    public List<Sollicitant> getAllSollicitants() {
        log.debug("REST request to get all Sollicitants");
        return sollicitantRepository.findAll();
    }

    /**
     * {@code GET  /sollicitants/:id} : get the "id" sollicitant.
     *
     * @param id the id of the sollicitant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sollicitant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sollicitant> getSollicitant(@PathVariable("id") Long id) {
        log.debug("REST request to get Sollicitant : {}", id);
        Optional<Sollicitant> sollicitant = sollicitantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sollicitant);
    }

    /**
     * {@code DELETE  /sollicitants/:id} : delete the "id" sollicitant.
     *
     * @param id the id of the sollicitant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSollicitant(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sollicitant : {}", id);
        sollicitantRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
