package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gebouwdobject;
import nl.ritense.demo.repository.GebouwdobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gebouwdobject}.
 */
@RestController
@RequestMapping("/api/gebouwdobjects")
@Transactional
public class GebouwdobjectResource {

    private final Logger log = LoggerFactory.getLogger(GebouwdobjectResource.class);

    private static final String ENTITY_NAME = "gebouwdobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GebouwdobjectRepository gebouwdobjectRepository;

    public GebouwdobjectResource(GebouwdobjectRepository gebouwdobjectRepository) {
        this.gebouwdobjectRepository = gebouwdobjectRepository;
    }

    /**
     * {@code POST  /gebouwdobjects} : Create a new gebouwdobject.
     *
     * @param gebouwdobject the gebouwdobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gebouwdobject, or with status {@code 400 (Bad Request)} if the gebouwdobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gebouwdobject> createGebouwdobject(@Valid @RequestBody Gebouwdobject gebouwdobject) throws URISyntaxException {
        log.debug("REST request to save Gebouwdobject : {}", gebouwdobject);
        if (gebouwdobject.getId() != null) {
            throw new BadRequestAlertException("A new gebouwdobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gebouwdobject = gebouwdobjectRepository.save(gebouwdobject);
        return ResponseEntity.created(new URI("/api/gebouwdobjects/" + gebouwdobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gebouwdobject.getId().toString()))
            .body(gebouwdobject);
    }

    /**
     * {@code PUT  /gebouwdobjects/:id} : Updates an existing gebouwdobject.
     *
     * @param id the id of the gebouwdobject to save.
     * @param gebouwdobject the gebouwdobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebouwdobject,
     * or with status {@code 400 (Bad Request)} if the gebouwdobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gebouwdobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gebouwdobject> updateGebouwdobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Gebouwdobject gebouwdobject
    ) throws URISyntaxException {
        log.debug("REST request to update Gebouwdobject : {}, {}", id, gebouwdobject);
        if (gebouwdobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebouwdobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebouwdobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gebouwdobject = gebouwdobjectRepository.save(gebouwdobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebouwdobject.getId().toString()))
            .body(gebouwdobject);
    }

    /**
     * {@code PATCH  /gebouwdobjects/:id} : Partial updates given fields of an existing gebouwdobject, field will ignore if it is null
     *
     * @param id the id of the gebouwdobject to save.
     * @param gebouwdobject the gebouwdobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebouwdobject,
     * or with status {@code 400 (Bad Request)} if the gebouwdobject is not valid,
     * or with status {@code 404 (Not Found)} if the gebouwdobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the gebouwdobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gebouwdobject> partialUpdateGebouwdobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Gebouwdobject gebouwdobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gebouwdobject partially : {}, {}", id, gebouwdobject);
        if (gebouwdobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebouwdobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebouwdobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gebouwdobject> result = gebouwdobjectRepository
            .findById(gebouwdobject.getId())
            .map(existingGebouwdobject -> {
                if (gebouwdobject.getBouwkundigebestemmingactueel() != null) {
                    existingGebouwdobject.setBouwkundigebestemmingactueel(gebouwdobject.getBouwkundigebestemmingactueel());
                }
                if (gebouwdobject.getBrutoinhoud() != null) {
                    existingGebouwdobject.setBrutoinhoud(gebouwdobject.getBrutoinhoud());
                }
                if (gebouwdobject.getIdentificatie() != null) {
                    existingGebouwdobject.setIdentificatie(gebouwdobject.getIdentificatie());
                }
                if (gebouwdobject.getInwinningoppervlakte() != null) {
                    existingGebouwdobject.setInwinningoppervlakte(gebouwdobject.getInwinningoppervlakte());
                }
                if (gebouwdobject.getOppervlakteobject() != null) {
                    existingGebouwdobject.setOppervlakteobject(gebouwdobject.getOppervlakteobject());
                }
                if (gebouwdobject.getStatusvoortgangbouw() != null) {
                    existingGebouwdobject.setStatusvoortgangbouw(gebouwdobject.getStatusvoortgangbouw());
                }

                return existingGebouwdobject;
            })
            .map(gebouwdobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebouwdobject.getId().toString())
        );
    }

    /**
     * {@code GET  /gebouwdobjects} : get all the gebouwdobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gebouwdobjects in body.
     */
    @GetMapping("")
    public List<Gebouwdobject> getAllGebouwdobjects() {
        log.debug("REST request to get all Gebouwdobjects");
        return gebouwdobjectRepository.findAll();
    }

    /**
     * {@code GET  /gebouwdobjects/:id} : get the "id" gebouwdobject.
     *
     * @param id the id of the gebouwdobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gebouwdobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gebouwdobject> getGebouwdobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Gebouwdobject : {}", id);
        Optional<Gebouwdobject> gebouwdobject = gebouwdobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gebouwdobject);
    }

    /**
     * {@code DELETE  /gebouwdobjects/:id} : delete the "id" gebouwdobject.
     *
     * @param id the id of the gebouwdobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebouwdobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gebouwdobject : {}", id);
        gebouwdobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
