package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Normprofiel;
import nl.ritense.demo.repository.NormprofielRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Normprofiel}.
 */
@RestController
@RequestMapping("/api/normprofiels")
@Transactional
public class NormprofielResource {

    private final Logger log = LoggerFactory.getLogger(NormprofielResource.class);

    private static final String ENTITY_NAME = "normprofiel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NormprofielRepository normprofielRepository;

    public NormprofielResource(NormprofielRepository normprofielRepository) {
        this.normprofielRepository = normprofielRepository;
    }

    /**
     * {@code POST  /normprofiels} : Create a new normprofiel.
     *
     * @param normprofiel the normprofiel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new normprofiel, or with status {@code 400 (Bad Request)} if the normprofiel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Normprofiel> createNormprofiel(@Valid @RequestBody Normprofiel normprofiel) throws URISyntaxException {
        log.debug("REST request to save Normprofiel : {}", normprofiel);
        if (normprofiel.getId() != null) {
            throw new BadRequestAlertException("A new normprofiel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        normprofiel = normprofielRepository.save(normprofiel);
        return ResponseEntity.created(new URI("/api/normprofiels/" + normprofiel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, normprofiel.getId().toString()))
            .body(normprofiel);
    }

    /**
     * {@code PUT  /normprofiels/:id} : Updates an existing normprofiel.
     *
     * @param id the id of the normprofiel to save.
     * @param normprofiel the normprofiel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated normprofiel,
     * or with status {@code 400 (Bad Request)} if the normprofiel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the normprofiel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Normprofiel> updateNormprofiel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Normprofiel normprofiel
    ) throws URISyntaxException {
        log.debug("REST request to update Normprofiel : {}, {}", id, normprofiel);
        if (normprofiel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, normprofiel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!normprofielRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        normprofiel = normprofielRepository.save(normprofiel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, normprofiel.getId().toString()))
            .body(normprofiel);
    }

    /**
     * {@code PATCH  /normprofiels/:id} : Partial updates given fields of an existing normprofiel, field will ignore if it is null
     *
     * @param id the id of the normprofiel to save.
     * @param normprofiel the normprofiel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated normprofiel,
     * or with status {@code 400 (Bad Request)} if the normprofiel is not valid,
     * or with status {@code 404 (Not Found)} if the normprofiel is not found,
     * or with status {@code 500 (Internal Server Error)} if the normprofiel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Normprofiel> partialUpdateNormprofiel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Normprofiel normprofiel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Normprofiel partially : {}, {}", id, normprofiel);
        if (normprofiel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, normprofiel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!normprofielRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Normprofiel> result = normprofielRepository
            .findById(normprofiel.getId())
            .map(existingNormprofiel -> {
                if (normprofiel.getCode() != null) {
                    existingNormprofiel.setCode(normprofiel.getCode());
                }
                if (normprofiel.getOmschrijving() != null) {
                    existingNormprofiel.setOmschrijving(normprofiel.getOmschrijving());
                }
                if (normprofiel.getSchaal() != null) {
                    existingNormprofiel.setSchaal(normprofiel.getSchaal());
                }

                return existingNormprofiel;
            })
            .map(normprofielRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, normprofiel.getId().toString())
        );
    }

    /**
     * {@code GET  /normprofiels} : get all the normprofiels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of normprofiels in body.
     */
    @GetMapping("")
    public List<Normprofiel> getAllNormprofiels() {
        log.debug("REST request to get all Normprofiels");
        return normprofielRepository.findAll();
    }

    /**
     * {@code GET  /normprofiels/:id} : get the "id" normprofiel.
     *
     * @param id the id of the normprofiel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the normprofiel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Normprofiel> getNormprofiel(@PathVariable("id") Long id) {
        log.debug("REST request to get Normprofiel : {}", id);
        Optional<Normprofiel> normprofiel = normprofielRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(normprofiel);
    }

    /**
     * {@code DELETE  /normprofiels/:id} : delete the "id" normprofiel.
     *
     * @param id the id of the normprofiel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNormprofiel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Normprofiel : {}", id);
        normprofielRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
