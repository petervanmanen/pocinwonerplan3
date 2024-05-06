package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Soortspoor;
import nl.ritense.demo.repository.SoortspoorRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Soortspoor}.
 */
@RestController
@RequestMapping("/api/soortspoors")
@Transactional
public class SoortspoorResource {

    private final Logger log = LoggerFactory.getLogger(SoortspoorResource.class);

    private static final String ENTITY_NAME = "soortspoor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoortspoorRepository soortspoorRepository;

    public SoortspoorResource(SoortspoorRepository soortspoorRepository) {
        this.soortspoorRepository = soortspoorRepository;
    }

    /**
     * {@code POST  /soortspoors} : Create a new soortspoor.
     *
     * @param soortspoor the soortspoor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soortspoor, or with status {@code 400 (Bad Request)} if the soortspoor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Soortspoor> createSoortspoor(@RequestBody Soortspoor soortspoor) throws URISyntaxException {
        log.debug("REST request to save Soortspoor : {}", soortspoor);
        if (soortspoor.getId() != null) {
            throw new BadRequestAlertException("A new soortspoor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        soortspoor = soortspoorRepository.save(soortspoor);
        return ResponseEntity.created(new URI("/api/soortspoors/" + soortspoor.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, soortspoor.getId().toString()))
            .body(soortspoor);
    }

    /**
     * {@code PUT  /soortspoors/:id} : Updates an existing soortspoor.
     *
     * @param id the id of the soortspoor to save.
     * @param soortspoor the soortspoor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortspoor,
     * or with status {@code 400 (Bad Request)} if the soortspoor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soortspoor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Soortspoor> updateSoortspoor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortspoor soortspoor
    ) throws URISyntaxException {
        log.debug("REST request to update Soortspoor : {}, {}", id, soortspoor);
        if (soortspoor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortspoor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortspoorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        soortspoor = soortspoorRepository.save(soortspoor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortspoor.getId().toString()))
            .body(soortspoor);
    }

    /**
     * {@code PATCH  /soortspoors/:id} : Partial updates given fields of an existing soortspoor, field will ignore if it is null
     *
     * @param id the id of the soortspoor to save.
     * @param soortspoor the soortspoor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortspoor,
     * or with status {@code 400 (Bad Request)} if the soortspoor is not valid,
     * or with status {@code 404 (Not Found)} if the soortspoor is not found,
     * or with status {@code 500 (Internal Server Error)} if the soortspoor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Soortspoor> partialUpdateSoortspoor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortspoor soortspoor
    ) throws URISyntaxException {
        log.debug("REST request to partial update Soortspoor partially : {}, {}", id, soortspoor);
        if (soortspoor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortspoor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortspoorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Soortspoor> result = soortspoorRepository
            .findById(soortspoor.getId())
            .map(existingSoortspoor -> {
                if (soortspoor.getFunctiespoor() != null) {
                    existingSoortspoor.setFunctiespoor(soortspoor.getFunctiespoor());
                }
                if (soortspoor.getIndicatieplusbrpopulatie() != null) {
                    existingSoortspoor.setIndicatieplusbrpopulatie(soortspoor.getIndicatieplusbrpopulatie());
                }

                return existingSoortspoor;
            })
            .map(soortspoorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortspoor.getId().toString())
        );
    }

    /**
     * {@code GET  /soortspoors} : get all the soortspoors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soortspoors in body.
     */
    @GetMapping("")
    public List<Soortspoor> getAllSoortspoors() {
        log.debug("REST request to get all Soortspoors");
        return soortspoorRepository.findAll();
    }

    /**
     * {@code GET  /soortspoors/:id} : get the "id" soortspoor.
     *
     * @param id the id of the soortspoor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soortspoor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Soortspoor> getSoortspoor(@PathVariable("id") Long id) {
        log.debug("REST request to get Soortspoor : {}", id);
        Optional<Soortspoor> soortspoor = soortspoorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(soortspoor);
    }

    /**
     * {@code DELETE  /soortspoors/:id} : delete the "id" soortspoor.
     *
     * @param id the id of the soortspoor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoortspoor(@PathVariable("id") Long id) {
        log.debug("REST request to delete Soortspoor : {}", id);
        soortspoorRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
