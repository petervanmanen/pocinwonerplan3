package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Wegdeel;
import nl.ritense.demo.repository.WegdeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Wegdeel}.
 */
@RestController
@RequestMapping("/api/wegdeels")
@Transactional
public class WegdeelResource {

    private final Logger log = LoggerFactory.getLogger(WegdeelResource.class);

    private static final String ENTITY_NAME = "wegdeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WegdeelRepository wegdeelRepository;

    public WegdeelResource(WegdeelRepository wegdeelRepository) {
        this.wegdeelRepository = wegdeelRepository;
    }

    /**
     * {@code POST  /wegdeels} : Create a new wegdeel.
     *
     * @param wegdeel the wegdeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wegdeel, or with status {@code 400 (Bad Request)} if the wegdeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Wegdeel> createWegdeel(@RequestBody Wegdeel wegdeel) throws URISyntaxException {
        log.debug("REST request to save Wegdeel : {}", wegdeel);
        if (wegdeel.getId() != null) {
            throw new BadRequestAlertException("A new wegdeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        wegdeel = wegdeelRepository.save(wegdeel);
        return ResponseEntity.created(new URI("/api/wegdeels/" + wegdeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, wegdeel.getId().toString()))
            .body(wegdeel);
    }

    /**
     * {@code PUT  /wegdeels/:id} : Updates an existing wegdeel.
     *
     * @param id the id of the wegdeel to save.
     * @param wegdeel the wegdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wegdeel,
     * or with status {@code 400 (Bad Request)} if the wegdeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wegdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Wegdeel> updateWegdeel(@PathVariable(value = "id", required = false) final Long id, @RequestBody Wegdeel wegdeel)
        throws URISyntaxException {
        log.debug("REST request to update Wegdeel : {}, {}", id, wegdeel);
        if (wegdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wegdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wegdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        wegdeel = wegdeelRepository.save(wegdeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wegdeel.getId().toString()))
            .body(wegdeel);
    }

    /**
     * {@code PATCH  /wegdeels/:id} : Partial updates given fields of an existing wegdeel, field will ignore if it is null
     *
     * @param id the id of the wegdeel to save.
     * @param wegdeel the wegdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wegdeel,
     * or with status {@code 400 (Bad Request)} if the wegdeel is not valid,
     * or with status {@code 404 (Not Found)} if the wegdeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the wegdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wegdeel> partialUpdateWegdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wegdeel wegdeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wegdeel partially : {}, {}", id, wegdeel);
        if (wegdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wegdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wegdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wegdeel> result = wegdeelRepository
            .findById(wegdeel.getId())
            .map(existingWegdeel -> {
                if (wegdeel.getDatumbegingeldigheidwegdeel() != null) {
                    existingWegdeel.setDatumbegingeldigheidwegdeel(wegdeel.getDatumbegingeldigheidwegdeel());
                }
                if (wegdeel.getDatumeindegeldigheidwegdeel() != null) {
                    existingWegdeel.setDatumeindegeldigheidwegdeel(wegdeel.getDatumeindegeldigheidwegdeel());
                }
                if (wegdeel.getFunctiewegdeel() != null) {
                    existingWegdeel.setFunctiewegdeel(wegdeel.getFunctiewegdeel());
                }
                if (wegdeel.getFysiekvoorkomenwegdeel() != null) {
                    existingWegdeel.setFysiekvoorkomenwegdeel(wegdeel.getFysiekvoorkomenwegdeel());
                }
                if (wegdeel.getGeometriewegdeel() != null) {
                    existingWegdeel.setGeometriewegdeel(wegdeel.getGeometriewegdeel());
                }
                if (wegdeel.getIdentificatiewegdeel() != null) {
                    existingWegdeel.setIdentificatiewegdeel(wegdeel.getIdentificatiewegdeel());
                }
                if (wegdeel.getKruinlijngeometriewegdeel() != null) {
                    existingWegdeel.setKruinlijngeometriewegdeel(wegdeel.getKruinlijngeometriewegdeel());
                }
                if (wegdeel.getLod0geometriewegdeel() != null) {
                    existingWegdeel.setLod0geometriewegdeel(wegdeel.getLod0geometriewegdeel());
                }
                if (wegdeel.getPlusfunctiewegdeel() != null) {
                    existingWegdeel.setPlusfunctiewegdeel(wegdeel.getPlusfunctiewegdeel());
                }
                if (wegdeel.getPlusfysiekvoorkomenwegdeel() != null) {
                    existingWegdeel.setPlusfysiekvoorkomenwegdeel(wegdeel.getPlusfysiekvoorkomenwegdeel());
                }
                if (wegdeel.getRelatievehoogteliggingwegdeel() != null) {
                    existingWegdeel.setRelatievehoogteliggingwegdeel(wegdeel.getRelatievehoogteliggingwegdeel());
                }
                if (wegdeel.getStatuswegdeel() != null) {
                    existingWegdeel.setStatuswegdeel(wegdeel.getStatuswegdeel());
                }
                if (wegdeel.getWegdeeloptalud() != null) {
                    existingWegdeel.setWegdeeloptalud(wegdeel.getWegdeeloptalud());
                }

                return existingWegdeel;
            })
            .map(wegdeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wegdeel.getId().toString())
        );
    }

    /**
     * {@code GET  /wegdeels} : get all the wegdeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wegdeels in body.
     */
    @GetMapping("")
    public List<Wegdeel> getAllWegdeels() {
        log.debug("REST request to get all Wegdeels");
        return wegdeelRepository.findAll();
    }

    /**
     * {@code GET  /wegdeels/:id} : get the "id" wegdeel.
     *
     * @param id the id of the wegdeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wegdeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wegdeel> getWegdeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Wegdeel : {}", id);
        Optional<Wegdeel> wegdeel = wegdeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wegdeel);
    }

    /**
     * {@code DELETE  /wegdeels/:id} : delete the "id" wegdeel.
     *
     * @param id the id of the wegdeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWegdeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Wegdeel : {}", id);
        wegdeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
