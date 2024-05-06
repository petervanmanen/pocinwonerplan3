package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Landofgebied;
import nl.ritense.demo.repository.LandofgebiedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Landofgebied}.
 */
@RestController
@RequestMapping("/api/landofgebieds")
@Transactional
public class LandofgebiedResource {

    private final Logger log = LoggerFactory.getLogger(LandofgebiedResource.class);

    private static final String ENTITY_NAME = "landofgebied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LandofgebiedRepository landofgebiedRepository;

    public LandofgebiedResource(LandofgebiedRepository landofgebiedRepository) {
        this.landofgebiedRepository = landofgebiedRepository;
    }

    /**
     * {@code POST  /landofgebieds} : Create a new landofgebied.
     *
     * @param landofgebied the landofgebied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new landofgebied, or with status {@code 400 (Bad Request)} if the landofgebied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Landofgebied> createLandofgebied(@RequestBody Landofgebied landofgebied) throws URISyntaxException {
        log.debug("REST request to save Landofgebied : {}", landofgebied);
        if (landofgebied.getId() != null) {
            throw new BadRequestAlertException("A new landofgebied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        landofgebied = landofgebiedRepository.save(landofgebied);
        return ResponseEntity.created(new URI("/api/landofgebieds/" + landofgebied.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, landofgebied.getId().toString()))
            .body(landofgebied);
    }

    /**
     * {@code PUT  /landofgebieds/:id} : Updates an existing landofgebied.
     *
     * @param id the id of the landofgebied to save.
     * @param landofgebied the landofgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landofgebied,
     * or with status {@code 400 (Bad Request)} if the landofgebied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the landofgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Landofgebied> updateLandofgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Landofgebied landofgebied
    ) throws URISyntaxException {
        log.debug("REST request to update Landofgebied : {}, {}", id, landofgebied);
        if (landofgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landofgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landofgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        landofgebied = landofgebiedRepository.save(landofgebied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, landofgebied.getId().toString()))
            .body(landofgebied);
    }

    /**
     * {@code PATCH  /landofgebieds/:id} : Partial updates given fields of an existing landofgebied, field will ignore if it is null
     *
     * @param id the id of the landofgebied to save.
     * @param landofgebied the landofgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated landofgebied,
     * or with status {@code 400 (Bad Request)} if the landofgebied is not valid,
     * or with status {@code 404 (Not Found)} if the landofgebied is not found,
     * or with status {@code 500 (Internal Server Error)} if the landofgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Landofgebied> partialUpdateLandofgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Landofgebied landofgebied
    ) throws URISyntaxException {
        log.debug("REST request to partial update Landofgebied partially : {}, {}", id, landofgebied);
        if (landofgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, landofgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landofgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Landofgebied> result = landofgebiedRepository
            .findById(landofgebied.getId())
            .map(existingLandofgebied -> {
                if (landofgebied.getDatumeindeland() != null) {
                    existingLandofgebied.setDatumeindeland(landofgebied.getDatumeindeland());
                }
                if (landofgebied.getDatumingangland() != null) {
                    existingLandofgebied.setDatumingangland(landofgebied.getDatumingangland());
                }
                if (landofgebied.getLandcode() != null) {
                    existingLandofgebied.setLandcode(landofgebied.getLandcode());
                }
                if (landofgebied.getLandcodeiso() != null) {
                    existingLandofgebied.setLandcodeiso(landofgebied.getLandcodeiso());
                }
                if (landofgebied.getLandnaam() != null) {
                    existingLandofgebied.setLandnaam(landofgebied.getLandnaam());
                }

                return existingLandofgebied;
            })
            .map(landofgebiedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, landofgebied.getId().toString())
        );
    }

    /**
     * {@code GET  /landofgebieds} : get all the landofgebieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of landofgebieds in body.
     */
    @GetMapping("")
    public List<Landofgebied> getAllLandofgebieds() {
        log.debug("REST request to get all Landofgebieds");
        return landofgebiedRepository.findAll();
    }

    /**
     * {@code GET  /landofgebieds/:id} : get the "id" landofgebied.
     *
     * @param id the id of the landofgebied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the landofgebied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Landofgebied> getLandofgebied(@PathVariable("id") Long id) {
        log.debug("REST request to get Landofgebied : {}", id);
        Optional<Landofgebied> landofgebied = landofgebiedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(landofgebied);
    }

    /**
     * {@code DELETE  /landofgebieds/:id} : delete the "id" landofgebied.
     *
     * @param id the id of the landofgebied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLandofgebied(@PathVariable("id") Long id) {
        log.debug("REST request to delete Landofgebied : {}", id);
        landofgebiedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
