package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Stuwgebied;
import nl.ritense.demo.repository.StuwgebiedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Stuwgebied}.
 */
@RestController
@RequestMapping("/api/stuwgebieds")
@Transactional
public class StuwgebiedResource {

    private final Logger log = LoggerFactory.getLogger(StuwgebiedResource.class);

    private static final String ENTITY_NAME = "stuwgebied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StuwgebiedRepository stuwgebiedRepository;

    public StuwgebiedResource(StuwgebiedRepository stuwgebiedRepository) {
        this.stuwgebiedRepository = stuwgebiedRepository;
    }

    /**
     * {@code POST  /stuwgebieds} : Create a new stuwgebied.
     *
     * @param stuwgebied the stuwgebied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stuwgebied, or with status {@code 400 (Bad Request)} if the stuwgebied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Stuwgebied> createStuwgebied(@RequestBody Stuwgebied stuwgebied) throws URISyntaxException {
        log.debug("REST request to save Stuwgebied : {}", stuwgebied);
        if (stuwgebied.getId() != null) {
            throw new BadRequestAlertException("A new stuwgebied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        stuwgebied = stuwgebiedRepository.save(stuwgebied);
        return ResponseEntity.created(new URI("/api/stuwgebieds/" + stuwgebied.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, stuwgebied.getId().toString()))
            .body(stuwgebied);
    }

    /**
     * {@code PUT  /stuwgebieds/:id} : Updates an existing stuwgebied.
     *
     * @param id the id of the stuwgebied to save.
     * @param stuwgebied the stuwgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stuwgebied,
     * or with status {@code 400 (Bad Request)} if the stuwgebied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stuwgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stuwgebied> updateStuwgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Stuwgebied stuwgebied
    ) throws URISyntaxException {
        log.debug("REST request to update Stuwgebied : {}, {}", id, stuwgebied);
        if (stuwgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stuwgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stuwgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        stuwgebied = stuwgebiedRepository.save(stuwgebied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stuwgebied.getId().toString()))
            .body(stuwgebied);
    }

    /**
     * {@code PATCH  /stuwgebieds/:id} : Partial updates given fields of an existing stuwgebied, field will ignore if it is null
     *
     * @param id the id of the stuwgebied to save.
     * @param stuwgebied the stuwgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stuwgebied,
     * or with status {@code 400 (Bad Request)} if the stuwgebied is not valid,
     * or with status {@code 404 (Not Found)} if the stuwgebied is not found,
     * or with status {@code 500 (Internal Server Error)} if the stuwgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Stuwgebied> partialUpdateStuwgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Stuwgebied stuwgebied
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stuwgebied partially : {}, {}", id, stuwgebied);
        if (stuwgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stuwgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stuwgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Stuwgebied> result = stuwgebiedRepository
            .findById(stuwgebied.getId())
            .map(existingStuwgebied -> {
                if (stuwgebied.getBemalingsgebied() != null) {
                    existingStuwgebied.setBemalingsgebied(stuwgebied.getBemalingsgebied());
                }

                return existingStuwgebied;
            })
            .map(stuwgebiedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stuwgebied.getId().toString())
        );
    }

    /**
     * {@code GET  /stuwgebieds} : get all the stuwgebieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stuwgebieds in body.
     */
    @GetMapping("")
    public List<Stuwgebied> getAllStuwgebieds() {
        log.debug("REST request to get all Stuwgebieds");
        return stuwgebiedRepository.findAll();
    }

    /**
     * {@code GET  /stuwgebieds/:id} : get the "id" stuwgebied.
     *
     * @param id the id of the stuwgebied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stuwgebied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stuwgebied> getStuwgebied(@PathVariable("id") Long id) {
        log.debug("REST request to get Stuwgebied : {}", id);
        Optional<Stuwgebied> stuwgebied = stuwgebiedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stuwgebied);
    }

    /**
     * {@code DELETE  /stuwgebieds/:id} : delete the "id" stuwgebied.
     *
     * @param id the id of the stuwgebied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStuwgebied(@PathVariable("id") Long id) {
        log.debug("REST request to delete Stuwgebied : {}", id);
        stuwgebiedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
