package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gebiedsaanwijzing;
import nl.ritense.demo.repository.GebiedsaanwijzingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gebiedsaanwijzing}.
 */
@RestController
@RequestMapping("/api/gebiedsaanwijzings")
@Transactional
public class GebiedsaanwijzingResource {

    private final Logger log = LoggerFactory.getLogger(GebiedsaanwijzingResource.class);

    private static final String ENTITY_NAME = "gebiedsaanwijzing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GebiedsaanwijzingRepository gebiedsaanwijzingRepository;

    public GebiedsaanwijzingResource(GebiedsaanwijzingRepository gebiedsaanwijzingRepository) {
        this.gebiedsaanwijzingRepository = gebiedsaanwijzingRepository;
    }

    /**
     * {@code POST  /gebiedsaanwijzings} : Create a new gebiedsaanwijzing.
     *
     * @param gebiedsaanwijzing the gebiedsaanwijzing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gebiedsaanwijzing, or with status {@code 400 (Bad Request)} if the gebiedsaanwijzing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gebiedsaanwijzing> createGebiedsaanwijzing(@RequestBody Gebiedsaanwijzing gebiedsaanwijzing)
        throws URISyntaxException {
        log.debug("REST request to save Gebiedsaanwijzing : {}", gebiedsaanwijzing);
        if (gebiedsaanwijzing.getId() != null) {
            throw new BadRequestAlertException("A new gebiedsaanwijzing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gebiedsaanwijzing = gebiedsaanwijzingRepository.save(gebiedsaanwijzing);
        return ResponseEntity.created(new URI("/api/gebiedsaanwijzings/" + gebiedsaanwijzing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gebiedsaanwijzing.getId().toString()))
            .body(gebiedsaanwijzing);
    }

    /**
     * {@code PUT  /gebiedsaanwijzings/:id} : Updates an existing gebiedsaanwijzing.
     *
     * @param id the id of the gebiedsaanwijzing to save.
     * @param gebiedsaanwijzing the gebiedsaanwijzing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebiedsaanwijzing,
     * or with status {@code 400 (Bad Request)} if the gebiedsaanwijzing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gebiedsaanwijzing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gebiedsaanwijzing> updateGebiedsaanwijzing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gebiedsaanwijzing gebiedsaanwijzing
    ) throws URISyntaxException {
        log.debug("REST request to update Gebiedsaanwijzing : {}, {}", id, gebiedsaanwijzing);
        if (gebiedsaanwijzing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebiedsaanwijzing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebiedsaanwijzingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gebiedsaanwijzing = gebiedsaanwijzingRepository.save(gebiedsaanwijzing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebiedsaanwijzing.getId().toString()))
            .body(gebiedsaanwijzing);
    }

    /**
     * {@code PATCH  /gebiedsaanwijzings/:id} : Partial updates given fields of an existing gebiedsaanwijzing, field will ignore if it is null
     *
     * @param id the id of the gebiedsaanwijzing to save.
     * @param gebiedsaanwijzing the gebiedsaanwijzing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebiedsaanwijzing,
     * or with status {@code 400 (Bad Request)} if the gebiedsaanwijzing is not valid,
     * or with status {@code 404 (Not Found)} if the gebiedsaanwijzing is not found,
     * or with status {@code 500 (Internal Server Error)} if the gebiedsaanwijzing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gebiedsaanwijzing> partialUpdateGebiedsaanwijzing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gebiedsaanwijzing gebiedsaanwijzing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gebiedsaanwijzing partially : {}, {}", id, gebiedsaanwijzing);
        if (gebiedsaanwijzing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebiedsaanwijzing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebiedsaanwijzingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gebiedsaanwijzing> result = gebiedsaanwijzingRepository
            .findById(gebiedsaanwijzing.getId())
            .map(existingGebiedsaanwijzing -> {
                if (gebiedsaanwijzing.getGroep() != null) {
                    existingGebiedsaanwijzing.setGroep(gebiedsaanwijzing.getGroep());
                }
                if (gebiedsaanwijzing.getNaam() != null) {
                    existingGebiedsaanwijzing.setNaam(gebiedsaanwijzing.getNaam());
                }
                if (gebiedsaanwijzing.getNen3610id() != null) {
                    existingGebiedsaanwijzing.setNen3610id(gebiedsaanwijzing.getNen3610id());
                }

                return existingGebiedsaanwijzing;
            })
            .map(gebiedsaanwijzingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebiedsaanwijzing.getId().toString())
        );
    }

    /**
     * {@code GET  /gebiedsaanwijzings} : get all the gebiedsaanwijzings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gebiedsaanwijzings in body.
     */
    @GetMapping("")
    public List<Gebiedsaanwijzing> getAllGebiedsaanwijzings(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Gebiedsaanwijzings");
        if (eagerload) {
            return gebiedsaanwijzingRepository.findAllWithEagerRelationships();
        } else {
            return gebiedsaanwijzingRepository.findAll();
        }
    }

    /**
     * {@code GET  /gebiedsaanwijzings/:id} : get the "id" gebiedsaanwijzing.
     *
     * @param id the id of the gebiedsaanwijzing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gebiedsaanwijzing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gebiedsaanwijzing> getGebiedsaanwijzing(@PathVariable("id") Long id) {
        log.debug("REST request to get Gebiedsaanwijzing : {}", id);
        Optional<Gebiedsaanwijzing> gebiedsaanwijzing = gebiedsaanwijzingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(gebiedsaanwijzing);
    }

    /**
     * {@code DELETE  /gebiedsaanwijzings/:id} : delete the "id" gebiedsaanwijzing.
     *
     * @param id the id of the gebiedsaanwijzing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebiedsaanwijzing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gebiedsaanwijzing : {}", id);
        gebiedsaanwijzingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
