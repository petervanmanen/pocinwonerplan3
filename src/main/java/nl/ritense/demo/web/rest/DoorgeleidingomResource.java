package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Doorgeleidingom;
import nl.ritense.demo.repository.DoorgeleidingomRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Doorgeleidingom}.
 */
@RestController
@RequestMapping("/api/doorgeleidingoms")
@Transactional
public class DoorgeleidingomResource {

    private final Logger log = LoggerFactory.getLogger(DoorgeleidingomResource.class);

    private static final String ENTITY_NAME = "doorgeleidingom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoorgeleidingomRepository doorgeleidingomRepository;

    public DoorgeleidingomResource(DoorgeleidingomRepository doorgeleidingomRepository) {
        this.doorgeleidingomRepository = doorgeleidingomRepository;
    }

    /**
     * {@code POST  /doorgeleidingoms} : Create a new doorgeleidingom.
     *
     * @param doorgeleidingom the doorgeleidingom to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doorgeleidingom, or with status {@code 400 (Bad Request)} if the doorgeleidingom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Doorgeleidingom> createDoorgeleidingom(@RequestBody Doorgeleidingom doorgeleidingom) throws URISyntaxException {
        log.debug("REST request to save Doorgeleidingom : {}", doorgeleidingom);
        if (doorgeleidingom.getId() != null) {
            throw new BadRequestAlertException("A new doorgeleidingom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        doorgeleidingom = doorgeleidingomRepository.save(doorgeleidingom);
        return ResponseEntity.created(new URI("/api/doorgeleidingoms/" + doorgeleidingom.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, doorgeleidingom.getId().toString()))
            .body(doorgeleidingom);
    }

    /**
     * {@code PUT  /doorgeleidingoms/:id} : Updates an existing doorgeleidingom.
     *
     * @param id the id of the doorgeleidingom to save.
     * @param doorgeleidingom the doorgeleidingom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doorgeleidingom,
     * or with status {@code 400 (Bad Request)} if the doorgeleidingom is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doorgeleidingom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doorgeleidingom> updateDoorgeleidingom(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Doorgeleidingom doorgeleidingom
    ) throws URISyntaxException {
        log.debug("REST request to update Doorgeleidingom : {}, {}", id, doorgeleidingom);
        if (doorgeleidingom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doorgeleidingom.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doorgeleidingomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        doorgeleidingom = doorgeleidingomRepository.save(doorgeleidingom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doorgeleidingom.getId().toString()))
            .body(doorgeleidingom);
    }

    /**
     * {@code PATCH  /doorgeleidingoms/:id} : Partial updates given fields of an existing doorgeleidingom, field will ignore if it is null
     *
     * @param id the id of the doorgeleidingom to save.
     * @param doorgeleidingom the doorgeleidingom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doorgeleidingom,
     * or with status {@code 400 (Bad Request)} if the doorgeleidingom is not valid,
     * or with status {@code 404 (Not Found)} if the doorgeleidingom is not found,
     * or with status {@code 500 (Internal Server Error)} if the doorgeleidingom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Doorgeleidingom> partialUpdateDoorgeleidingom(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Doorgeleidingom doorgeleidingom
    ) throws URISyntaxException {
        log.debug("REST request to partial update Doorgeleidingom partially : {}, {}", id, doorgeleidingom);
        if (doorgeleidingom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doorgeleidingom.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doorgeleidingomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Doorgeleidingom> result = doorgeleidingomRepository
            .findById(doorgeleidingom.getId())
            .map(existingDoorgeleidingom -> {
                if (doorgeleidingom.getAfdoening() != null) {
                    existingDoorgeleidingom.setAfdoening(doorgeleidingom.getAfdoening());
                }

                return existingDoorgeleidingom;
            })
            .map(doorgeleidingomRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doorgeleidingom.getId().toString())
        );
    }

    /**
     * {@code GET  /doorgeleidingoms} : get all the doorgeleidingoms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doorgeleidingoms in body.
     */
    @GetMapping("")
    public List<Doorgeleidingom> getAllDoorgeleidingoms() {
        log.debug("REST request to get all Doorgeleidingoms");
        return doorgeleidingomRepository.findAll();
    }

    /**
     * {@code GET  /doorgeleidingoms/:id} : get the "id" doorgeleidingom.
     *
     * @param id the id of the doorgeleidingom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doorgeleidingom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doorgeleidingom> getDoorgeleidingom(@PathVariable("id") Long id) {
        log.debug("REST request to get Doorgeleidingom : {}", id);
        Optional<Doorgeleidingom> doorgeleidingom = doorgeleidingomRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doorgeleidingom);
    }

    /**
     * {@code DELETE  /doorgeleidingoms/:id} : delete the "id" doorgeleidingom.
     *
     * @param id the id of the doorgeleidingom to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoorgeleidingom(@PathVariable("id") Long id) {
        log.debug("REST request to delete Doorgeleidingom : {}", id);
        doorgeleidingomRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
