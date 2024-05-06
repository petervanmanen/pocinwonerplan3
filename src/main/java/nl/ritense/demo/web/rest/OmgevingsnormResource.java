package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Omgevingsnorm;
import nl.ritense.demo.repository.OmgevingsnormRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Omgevingsnorm}.
 */
@RestController
@RequestMapping("/api/omgevingsnorms")
@Transactional
public class OmgevingsnormResource {

    private final Logger log = LoggerFactory.getLogger(OmgevingsnormResource.class);

    private static final String ENTITY_NAME = "omgevingsnorm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OmgevingsnormRepository omgevingsnormRepository;

    public OmgevingsnormResource(OmgevingsnormRepository omgevingsnormRepository) {
        this.omgevingsnormRepository = omgevingsnormRepository;
    }

    /**
     * {@code POST  /omgevingsnorms} : Create a new omgevingsnorm.
     *
     * @param omgevingsnorm the omgevingsnorm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new omgevingsnorm, or with status {@code 400 (Bad Request)} if the omgevingsnorm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Omgevingsnorm> createOmgevingsnorm(@RequestBody Omgevingsnorm omgevingsnorm) throws URISyntaxException {
        log.debug("REST request to save Omgevingsnorm : {}", omgevingsnorm);
        if (omgevingsnorm.getId() != null) {
            throw new BadRequestAlertException("A new omgevingsnorm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        omgevingsnorm = omgevingsnormRepository.save(omgevingsnorm);
        return ResponseEntity.created(new URI("/api/omgevingsnorms/" + omgevingsnorm.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, omgevingsnorm.getId().toString()))
            .body(omgevingsnorm);
    }

    /**
     * {@code PUT  /omgevingsnorms/:id} : Updates an existing omgevingsnorm.
     *
     * @param id the id of the omgevingsnorm to save.
     * @param omgevingsnorm the omgevingsnorm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omgevingsnorm,
     * or with status {@code 400 (Bad Request)} if the omgevingsnorm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the omgevingsnorm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Omgevingsnorm> updateOmgevingsnorm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Omgevingsnorm omgevingsnorm
    ) throws URISyntaxException {
        log.debug("REST request to update Omgevingsnorm : {}, {}", id, omgevingsnorm);
        if (omgevingsnorm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omgevingsnorm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omgevingsnormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        omgevingsnorm = omgevingsnormRepository.save(omgevingsnorm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omgevingsnorm.getId().toString()))
            .body(omgevingsnorm);
    }

    /**
     * {@code PATCH  /omgevingsnorms/:id} : Partial updates given fields of an existing omgevingsnorm, field will ignore if it is null
     *
     * @param id the id of the omgevingsnorm to save.
     * @param omgevingsnorm the omgevingsnorm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omgevingsnorm,
     * or with status {@code 400 (Bad Request)} if the omgevingsnorm is not valid,
     * or with status {@code 404 (Not Found)} if the omgevingsnorm is not found,
     * or with status {@code 500 (Internal Server Error)} if the omgevingsnorm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Omgevingsnorm> partialUpdateOmgevingsnorm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Omgevingsnorm omgevingsnorm
    ) throws URISyntaxException {
        log.debug("REST request to partial update Omgevingsnorm partially : {}, {}", id, omgevingsnorm);
        if (omgevingsnorm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omgevingsnorm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omgevingsnormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Omgevingsnorm> result = omgevingsnormRepository
            .findById(omgevingsnorm.getId())
            .map(existingOmgevingsnorm -> {
                if (omgevingsnorm.getNaam() != null) {
                    existingOmgevingsnorm.setNaam(omgevingsnorm.getNaam());
                }
                if (omgevingsnorm.getOmgevingsnormgroep() != null) {
                    existingOmgevingsnorm.setOmgevingsnormgroep(omgevingsnorm.getOmgevingsnormgroep());
                }

                return existingOmgevingsnorm;
            })
            .map(omgevingsnormRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omgevingsnorm.getId().toString())
        );
    }

    /**
     * {@code GET  /omgevingsnorms} : get all the omgevingsnorms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of omgevingsnorms in body.
     */
    @GetMapping("")
    public List<Omgevingsnorm> getAllOmgevingsnorms() {
        log.debug("REST request to get all Omgevingsnorms");
        return omgevingsnormRepository.findAll();
    }

    /**
     * {@code GET  /omgevingsnorms/:id} : get the "id" omgevingsnorm.
     *
     * @param id the id of the omgevingsnorm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the omgevingsnorm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Omgevingsnorm> getOmgevingsnorm(@PathVariable("id") Long id) {
        log.debug("REST request to get Omgevingsnorm : {}", id);
        Optional<Omgevingsnorm> omgevingsnorm = omgevingsnormRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(omgevingsnorm);
    }

    /**
     * {@code DELETE  /omgevingsnorms/:id} : delete the "id" omgevingsnorm.
     *
     * @param id the id of the omgevingsnorm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOmgevingsnorm(@PathVariable("id") Long id) {
        log.debug("REST request to delete Omgevingsnorm : {}", id);
        omgevingsnormRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
