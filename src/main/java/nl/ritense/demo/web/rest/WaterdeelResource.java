package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Waterdeel;
import nl.ritense.demo.repository.WaterdeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Waterdeel}.
 */
@RestController
@RequestMapping("/api/waterdeels")
@Transactional
public class WaterdeelResource {

    private final Logger log = LoggerFactory.getLogger(WaterdeelResource.class);

    private static final String ENTITY_NAME = "waterdeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WaterdeelRepository waterdeelRepository;

    public WaterdeelResource(WaterdeelRepository waterdeelRepository) {
        this.waterdeelRepository = waterdeelRepository;
    }

    /**
     * {@code POST  /waterdeels} : Create a new waterdeel.
     *
     * @param waterdeel the waterdeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new waterdeel, or with status {@code 400 (Bad Request)} if the waterdeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Waterdeel> createWaterdeel(@RequestBody Waterdeel waterdeel) throws URISyntaxException {
        log.debug("REST request to save Waterdeel : {}", waterdeel);
        if (waterdeel.getId() != null) {
            throw new BadRequestAlertException("A new waterdeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        waterdeel = waterdeelRepository.save(waterdeel);
        return ResponseEntity.created(new URI("/api/waterdeels/" + waterdeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, waterdeel.getId().toString()))
            .body(waterdeel);
    }

    /**
     * {@code PUT  /waterdeels/:id} : Updates an existing waterdeel.
     *
     * @param id the id of the waterdeel to save.
     * @param waterdeel the waterdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waterdeel,
     * or with status {@code 400 (Bad Request)} if the waterdeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the waterdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Waterdeel> updateWaterdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Waterdeel waterdeel
    ) throws URISyntaxException {
        log.debug("REST request to update Waterdeel : {}, {}", id, waterdeel);
        if (waterdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waterdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waterdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        waterdeel = waterdeelRepository.save(waterdeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, waterdeel.getId().toString()))
            .body(waterdeel);
    }

    /**
     * {@code PATCH  /waterdeels/:id} : Partial updates given fields of an existing waterdeel, field will ignore if it is null
     *
     * @param id the id of the waterdeel to save.
     * @param waterdeel the waterdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waterdeel,
     * or with status {@code 400 (Bad Request)} if the waterdeel is not valid,
     * or with status {@code 404 (Not Found)} if the waterdeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the waterdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Waterdeel> partialUpdateWaterdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Waterdeel waterdeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Waterdeel partially : {}, {}", id, waterdeel);
        if (waterdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waterdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waterdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Waterdeel> result = waterdeelRepository
            .findById(waterdeel.getId())
            .map(existingWaterdeel -> {
                if (waterdeel.getDatumbegingeldigheidwaterdeel() != null) {
                    existingWaterdeel.setDatumbegingeldigheidwaterdeel(waterdeel.getDatumbegingeldigheidwaterdeel());
                }
                if (waterdeel.getDatumeindegeldigheidwaterdeel() != null) {
                    existingWaterdeel.setDatumeindegeldigheidwaterdeel(waterdeel.getDatumeindegeldigheidwaterdeel());
                }
                if (waterdeel.getGeometriewaterdeel() != null) {
                    existingWaterdeel.setGeometriewaterdeel(waterdeel.getGeometriewaterdeel());
                }
                if (waterdeel.getIdentificatiewaterdeel() != null) {
                    existingWaterdeel.setIdentificatiewaterdeel(waterdeel.getIdentificatiewaterdeel());
                }
                if (waterdeel.getPlustypewaterdeel() != null) {
                    existingWaterdeel.setPlustypewaterdeel(waterdeel.getPlustypewaterdeel());
                }
                if (waterdeel.getRelatievehoogteliggingwaterdeel() != null) {
                    existingWaterdeel.setRelatievehoogteliggingwaterdeel(waterdeel.getRelatievehoogteliggingwaterdeel());
                }
                if (waterdeel.getStatuswaterdeel() != null) {
                    existingWaterdeel.setStatuswaterdeel(waterdeel.getStatuswaterdeel());
                }
                if (waterdeel.getTypewaterdeel() != null) {
                    existingWaterdeel.setTypewaterdeel(waterdeel.getTypewaterdeel());
                }

                return existingWaterdeel;
            })
            .map(waterdeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, waterdeel.getId().toString())
        );
    }

    /**
     * {@code GET  /waterdeels} : get all the waterdeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of waterdeels in body.
     */
    @GetMapping("")
    public List<Waterdeel> getAllWaterdeels() {
        log.debug("REST request to get all Waterdeels");
        return waterdeelRepository.findAll();
    }

    /**
     * {@code GET  /waterdeels/:id} : get the "id" waterdeel.
     *
     * @param id the id of the waterdeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the waterdeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Waterdeel> getWaterdeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Waterdeel : {}", id);
        Optional<Waterdeel> waterdeel = waterdeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(waterdeel);
    }

    /**
     * {@code DELETE  /waterdeels/:id} : delete the "id" waterdeel.
     *
     * @param id the id of the waterdeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaterdeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Waterdeel : {}", id);
        waterdeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
