package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Omgevingswaarderegel;
import nl.ritense.demo.repository.OmgevingswaarderegelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Omgevingswaarderegel}.
 */
@RestController
@RequestMapping("/api/omgevingswaarderegels")
@Transactional
public class OmgevingswaarderegelResource {

    private final Logger log = LoggerFactory.getLogger(OmgevingswaarderegelResource.class);

    private static final String ENTITY_NAME = "omgevingswaarderegel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OmgevingswaarderegelRepository omgevingswaarderegelRepository;

    public OmgevingswaarderegelResource(OmgevingswaarderegelRepository omgevingswaarderegelRepository) {
        this.omgevingswaarderegelRepository = omgevingswaarderegelRepository;
    }

    /**
     * {@code POST  /omgevingswaarderegels} : Create a new omgevingswaarderegel.
     *
     * @param omgevingswaarderegel the omgevingswaarderegel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new omgevingswaarderegel, or with status {@code 400 (Bad Request)} if the omgevingswaarderegel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Omgevingswaarderegel> createOmgevingswaarderegel(@Valid @RequestBody Omgevingswaarderegel omgevingswaarderegel)
        throws URISyntaxException {
        log.debug("REST request to save Omgevingswaarderegel : {}", omgevingswaarderegel);
        if (omgevingswaarderegel.getId() != null) {
            throw new BadRequestAlertException("A new omgevingswaarderegel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        omgevingswaarderegel = omgevingswaarderegelRepository.save(omgevingswaarderegel);
        return ResponseEntity.created(new URI("/api/omgevingswaarderegels/" + omgevingswaarderegel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, omgevingswaarderegel.getId().toString()))
            .body(omgevingswaarderegel);
    }

    /**
     * {@code PUT  /omgevingswaarderegels/:id} : Updates an existing omgevingswaarderegel.
     *
     * @param id the id of the omgevingswaarderegel to save.
     * @param omgevingswaarderegel the omgevingswaarderegel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omgevingswaarderegel,
     * or with status {@code 400 (Bad Request)} if the omgevingswaarderegel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the omgevingswaarderegel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Omgevingswaarderegel> updateOmgevingswaarderegel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Omgevingswaarderegel omgevingswaarderegel
    ) throws URISyntaxException {
        log.debug("REST request to update Omgevingswaarderegel : {}, {}", id, omgevingswaarderegel);
        if (omgevingswaarderegel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omgevingswaarderegel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omgevingswaarderegelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        omgevingswaarderegel = omgevingswaarderegelRepository.save(omgevingswaarderegel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omgevingswaarderegel.getId().toString()))
            .body(omgevingswaarderegel);
    }

    /**
     * {@code PATCH  /omgevingswaarderegels/:id} : Partial updates given fields of an existing omgevingswaarderegel, field will ignore if it is null
     *
     * @param id the id of the omgevingswaarderegel to save.
     * @param omgevingswaarderegel the omgevingswaarderegel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omgevingswaarderegel,
     * or with status {@code 400 (Bad Request)} if the omgevingswaarderegel is not valid,
     * or with status {@code 404 (Not Found)} if the omgevingswaarderegel is not found,
     * or with status {@code 500 (Internal Server Error)} if the omgevingswaarderegel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Omgevingswaarderegel> partialUpdateOmgevingswaarderegel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Omgevingswaarderegel omgevingswaarderegel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Omgevingswaarderegel partially : {}, {}", id, omgevingswaarderegel);
        if (omgevingswaarderegel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omgevingswaarderegel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omgevingswaarderegelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Omgevingswaarderegel> result = omgevingswaarderegelRepository
            .findById(omgevingswaarderegel.getId())
            .map(existingOmgevingswaarderegel -> {
                if (omgevingswaarderegel.getGroep() != null) {
                    existingOmgevingswaarderegel.setGroep(omgevingswaarderegel.getGroep());
                }
                if (omgevingswaarderegel.getNaam() != null) {
                    existingOmgevingswaarderegel.setNaam(omgevingswaarderegel.getNaam());
                }

                return existingOmgevingswaarderegel;
            })
            .map(omgevingswaarderegelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omgevingswaarderegel.getId().toString())
        );
    }

    /**
     * {@code GET  /omgevingswaarderegels} : get all the omgevingswaarderegels.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of omgevingswaarderegels in body.
     */
    @GetMapping("")
    public List<Omgevingswaarderegel> getAllOmgevingswaarderegels(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Omgevingswaarderegels");
        if (eagerload) {
            return omgevingswaarderegelRepository.findAllWithEagerRelationships();
        } else {
            return omgevingswaarderegelRepository.findAll();
        }
    }

    /**
     * {@code GET  /omgevingswaarderegels/:id} : get the "id" omgevingswaarderegel.
     *
     * @param id the id of the omgevingswaarderegel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the omgevingswaarderegel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Omgevingswaarderegel> getOmgevingswaarderegel(@PathVariable("id") Long id) {
        log.debug("REST request to get Omgevingswaarderegel : {}", id);
        Optional<Omgevingswaarderegel> omgevingswaarderegel = omgevingswaarderegelRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(omgevingswaarderegel);
    }

    /**
     * {@code DELETE  /omgevingswaarderegels/:id} : delete the "id" omgevingswaarderegel.
     *
     * @param id the id of the omgevingswaarderegel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOmgevingswaarderegel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Omgevingswaarderegel : {}", id);
        omgevingswaarderegelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
