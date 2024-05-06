package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overbruggingsdeel;
import nl.ritense.demo.repository.OverbruggingsdeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overbruggingsdeel}.
 */
@RestController
@RequestMapping("/api/overbruggingsdeels")
@Transactional
public class OverbruggingsdeelResource {

    private final Logger log = LoggerFactory.getLogger(OverbruggingsdeelResource.class);

    private static final String ENTITY_NAME = "overbruggingsdeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverbruggingsdeelRepository overbruggingsdeelRepository;

    public OverbruggingsdeelResource(OverbruggingsdeelRepository overbruggingsdeelRepository) {
        this.overbruggingsdeelRepository = overbruggingsdeelRepository;
    }

    /**
     * {@code POST  /overbruggingsdeels} : Create a new overbruggingsdeel.
     *
     * @param overbruggingsdeel the overbruggingsdeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overbruggingsdeel, or with status {@code 400 (Bad Request)} if the overbruggingsdeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overbruggingsdeel> createOverbruggingsdeel(@RequestBody Overbruggingsdeel overbruggingsdeel)
        throws URISyntaxException {
        log.debug("REST request to save Overbruggingsdeel : {}", overbruggingsdeel);
        if (overbruggingsdeel.getId() != null) {
            throw new BadRequestAlertException("A new overbruggingsdeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        overbruggingsdeel = overbruggingsdeelRepository.save(overbruggingsdeel);
        return ResponseEntity.created(new URI("/api/overbruggingsdeels/" + overbruggingsdeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, overbruggingsdeel.getId().toString()))
            .body(overbruggingsdeel);
    }

    /**
     * {@code PUT  /overbruggingsdeels/:id} : Updates an existing overbruggingsdeel.
     *
     * @param id the id of the overbruggingsdeel to save.
     * @param overbruggingsdeel the overbruggingsdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overbruggingsdeel,
     * or with status {@code 400 (Bad Request)} if the overbruggingsdeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overbruggingsdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overbruggingsdeel> updateOverbruggingsdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overbruggingsdeel overbruggingsdeel
    ) throws URISyntaxException {
        log.debug("REST request to update Overbruggingsdeel : {}, {}", id, overbruggingsdeel);
        if (overbruggingsdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overbruggingsdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overbruggingsdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overbruggingsdeel = overbruggingsdeelRepository.save(overbruggingsdeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overbruggingsdeel.getId().toString()))
            .body(overbruggingsdeel);
    }

    /**
     * {@code PATCH  /overbruggingsdeels/:id} : Partial updates given fields of an existing overbruggingsdeel, field will ignore if it is null
     *
     * @param id the id of the overbruggingsdeel to save.
     * @param overbruggingsdeel the overbruggingsdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overbruggingsdeel,
     * or with status {@code 400 (Bad Request)} if the overbruggingsdeel is not valid,
     * or with status {@code 404 (Not Found)} if the overbruggingsdeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the overbruggingsdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overbruggingsdeel> partialUpdateOverbruggingsdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overbruggingsdeel overbruggingsdeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Overbruggingsdeel partially : {}, {}", id, overbruggingsdeel);
        if (overbruggingsdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overbruggingsdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overbruggingsdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overbruggingsdeel> result = overbruggingsdeelRepository
            .findById(overbruggingsdeel.getId())
            .map(existingOverbruggingsdeel -> {
                if (overbruggingsdeel.getDatumbegingeldigheidoverbruggingsdeel() != null) {
                    existingOverbruggingsdeel.setDatumbegingeldigheidoverbruggingsdeel(
                        overbruggingsdeel.getDatumbegingeldigheidoverbruggingsdeel()
                    );
                }
                if (overbruggingsdeel.getDatumeindegeldigheidoverbruggingsdeel() != null) {
                    existingOverbruggingsdeel.setDatumeindegeldigheidoverbruggingsdeel(
                        overbruggingsdeel.getDatumeindegeldigheidoverbruggingsdeel()
                    );
                }
                if (overbruggingsdeel.getGeometrieoverbruggingsdeel() != null) {
                    existingOverbruggingsdeel.setGeometrieoverbruggingsdeel(overbruggingsdeel.getGeometrieoverbruggingsdeel());
                }
                if (overbruggingsdeel.getHoortbijtypeoverbrugging() != null) {
                    existingOverbruggingsdeel.setHoortbijtypeoverbrugging(overbruggingsdeel.getHoortbijtypeoverbrugging());
                }
                if (overbruggingsdeel.getIdentificatieoverbruggingsdeel() != null) {
                    existingOverbruggingsdeel.setIdentificatieoverbruggingsdeel(overbruggingsdeel.getIdentificatieoverbruggingsdeel());
                }
                if (overbruggingsdeel.getLod0geometrieoverbruggingsdeel() != null) {
                    existingOverbruggingsdeel.setLod0geometrieoverbruggingsdeel(overbruggingsdeel.getLod0geometrieoverbruggingsdeel());
                }
                if (overbruggingsdeel.getOverbruggingisbeweegbaar() != null) {
                    existingOverbruggingsdeel.setOverbruggingisbeweegbaar(overbruggingsdeel.getOverbruggingisbeweegbaar());
                }
                if (overbruggingsdeel.getRelatievehoogteliggingoverbruggingsdeel() != null) {
                    existingOverbruggingsdeel.setRelatievehoogteliggingoverbruggingsdeel(
                        overbruggingsdeel.getRelatievehoogteliggingoverbruggingsdeel()
                    );
                }
                if (overbruggingsdeel.getStatusoverbruggingsdeel() != null) {
                    existingOverbruggingsdeel.setStatusoverbruggingsdeel(overbruggingsdeel.getStatusoverbruggingsdeel());
                }
                if (overbruggingsdeel.getTypeoverbruggingsdeel() != null) {
                    existingOverbruggingsdeel.setTypeoverbruggingsdeel(overbruggingsdeel.getTypeoverbruggingsdeel());
                }

                return existingOverbruggingsdeel;
            })
            .map(overbruggingsdeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overbruggingsdeel.getId().toString())
        );
    }

    /**
     * {@code GET  /overbruggingsdeels} : get all the overbruggingsdeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overbruggingsdeels in body.
     */
    @GetMapping("")
    public List<Overbruggingsdeel> getAllOverbruggingsdeels() {
        log.debug("REST request to get all Overbruggingsdeels");
        return overbruggingsdeelRepository.findAll();
    }

    /**
     * {@code GET  /overbruggingsdeels/:id} : get the "id" overbruggingsdeel.
     *
     * @param id the id of the overbruggingsdeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overbruggingsdeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overbruggingsdeel> getOverbruggingsdeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Overbruggingsdeel : {}", id);
        Optional<Overbruggingsdeel> overbruggingsdeel = overbruggingsdeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overbruggingsdeel);
    }

    /**
     * {@code DELETE  /overbruggingsdeels/:id} : delete the "id" overbruggingsdeel.
     *
     * @param id the id of the overbruggingsdeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverbruggingsdeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overbruggingsdeel : {}", id);
        overbruggingsdeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
