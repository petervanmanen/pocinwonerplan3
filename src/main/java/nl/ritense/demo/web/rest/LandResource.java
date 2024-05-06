package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Land;
import nl.ritense.demo.repository.LandRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Land}.
 */
@RestController
@RequestMapping("/api/lands")
@Transactional
public class LandResource {

    private final Logger log = LoggerFactory.getLogger(LandResource.class);

    private static final String ENTITY_NAME = "land";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LandRepository landRepository;

    public LandResource(LandRepository landRepository) {
        this.landRepository = landRepository;
    }

    /**
     * {@code POST  /lands} : Create a new land.
     *
     * @param land the land to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new land, or with status {@code 400 (Bad Request)} if the land has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Land> createLand(@RequestBody Land land) throws URISyntaxException {
        log.debug("REST request to save Land : {}", land);
        if (land.getId() != null) {
            throw new BadRequestAlertException("A new land cannot already have an ID", ENTITY_NAME, "idexists");
        }
        land = landRepository.save(land);
        return ResponseEntity.created(new URI("/api/lands/" + land.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, land.getId().toString()))
            .body(land);
    }

    /**
     * {@code PUT  /lands/:id} : Updates an existing land.
     *
     * @param id the id of the land to save.
     * @param land the land to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated land,
     * or with status {@code 400 (Bad Request)} if the land is not valid,
     * or with status {@code 500 (Internal Server Error)} if the land couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Land> updateLand(@PathVariable(value = "id", required = false) final Long id, @RequestBody Land land)
        throws URISyntaxException {
        log.debug("REST request to update Land : {}, {}", id, land);
        if (land.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, land.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        land = landRepository.save(land);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, land.getId().toString()))
            .body(land);
    }

    /**
     * {@code PATCH  /lands/:id} : Partial updates given fields of an existing land, field will ignore if it is null
     *
     * @param id the id of the land to save.
     * @param land the land to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated land,
     * or with status {@code 400 (Bad Request)} if the land is not valid,
     * or with status {@code 404 (Not Found)} if the land is not found,
     * or with status {@code 500 (Internal Server Error)} if the land couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Land> partialUpdateLand(@PathVariable(value = "id", required = false) final Long id, @RequestBody Land land)
        throws URISyntaxException {
        log.debug("REST request to partial update Land partially : {}, {}", id, land);
        if (land.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, land.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!landRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Land> result = landRepository
            .findById(land.getId())
            .map(existingLand -> {
                if (land.getDatumeindefictief() != null) {
                    existingLand.setDatumeindefictief(land.getDatumeindefictief());
                }
                if (land.getDatumeindeland() != null) {
                    existingLand.setDatumeindeland(land.getDatumeindeland());
                }
                if (land.getDatumingangland() != null) {
                    existingLand.setDatumingangland(land.getDatumingangland());
                }
                if (land.getLandcode() != null) {
                    existingLand.setLandcode(land.getLandcode());
                }
                if (land.getLandcodeisodrieletterig() != null) {
                    existingLand.setLandcodeisodrieletterig(land.getLandcodeisodrieletterig());
                }
                if (land.getLandcodeisotweeletterig() != null) {
                    existingLand.setLandcodeisotweeletterig(land.getLandcodeisotweeletterig());
                }
                if (land.getLandnaam() != null) {
                    existingLand.setLandnaam(land.getLandnaam());
                }

                return existingLand;
            })
            .map(landRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, land.getId().toString())
        );
    }

    /**
     * {@code GET  /lands} : get all the lands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lands in body.
     */
    @GetMapping("")
    public List<Land> getAllLands() {
        log.debug("REST request to get all Lands");
        return landRepository.findAll();
    }

    /**
     * {@code GET  /lands/:id} : get the "id" land.
     *
     * @param id the id of the land to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the land, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Land> getLand(@PathVariable("id") Long id) {
        log.debug("REST request to get Land : {}", id);
        Optional<Land> land = landRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(land);
    }

    /**
     * {@code DELETE  /lands/:id} : delete the "id" land.
     *
     * @param id the id of the land to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLand(@PathVariable("id") Long id) {
        log.debug("REST request to delete Land : {}", id);
        landRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
