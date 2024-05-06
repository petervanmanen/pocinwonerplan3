package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Strijdigheidofnietigheid;
import nl.ritense.demo.repository.StrijdigheidofnietigheidRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Strijdigheidofnietigheid}.
 */
@RestController
@RequestMapping("/api/strijdigheidofnietigheids")
@Transactional
public class StrijdigheidofnietigheidResource {

    private final Logger log = LoggerFactory.getLogger(StrijdigheidofnietigheidResource.class);

    private static final String ENTITY_NAME = "strijdigheidofnietigheid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrijdigheidofnietigheidRepository strijdigheidofnietigheidRepository;

    public StrijdigheidofnietigheidResource(StrijdigheidofnietigheidRepository strijdigheidofnietigheidRepository) {
        this.strijdigheidofnietigheidRepository = strijdigheidofnietigheidRepository;
    }

    /**
     * {@code POST  /strijdigheidofnietigheids} : Create a new strijdigheidofnietigheid.
     *
     * @param strijdigheidofnietigheid the strijdigheidofnietigheid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strijdigheidofnietigheid, or with status {@code 400 (Bad Request)} if the strijdigheidofnietigheid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Strijdigheidofnietigheid> createStrijdigheidofnietigheid(
        @RequestBody Strijdigheidofnietigheid strijdigheidofnietigheid
    ) throws URISyntaxException {
        log.debug("REST request to save Strijdigheidofnietigheid : {}", strijdigheidofnietigheid);
        if (strijdigheidofnietigheid.getId() != null) {
            throw new BadRequestAlertException("A new strijdigheidofnietigheid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        strijdigheidofnietigheid = strijdigheidofnietigheidRepository.save(strijdigheidofnietigheid);
        return ResponseEntity.created(new URI("/api/strijdigheidofnietigheids/" + strijdigheidofnietigheid.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, strijdigheidofnietigheid.getId().toString()))
            .body(strijdigheidofnietigheid);
    }

    /**
     * {@code PUT  /strijdigheidofnietigheids/:id} : Updates an existing strijdigheidofnietigheid.
     *
     * @param id the id of the strijdigheidofnietigheid to save.
     * @param strijdigheidofnietigheid the strijdigheidofnietigheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strijdigheidofnietigheid,
     * or with status {@code 400 (Bad Request)} if the strijdigheidofnietigheid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strijdigheidofnietigheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Strijdigheidofnietigheid> updateStrijdigheidofnietigheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Strijdigheidofnietigheid strijdigheidofnietigheid
    ) throws URISyntaxException {
        log.debug("REST request to update Strijdigheidofnietigheid : {}, {}", id, strijdigheidofnietigheid);
        if (strijdigheidofnietigheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strijdigheidofnietigheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strijdigheidofnietigheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        strijdigheidofnietigheid = strijdigheidofnietigheidRepository.save(strijdigheidofnietigheid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, strijdigheidofnietigheid.getId().toString()))
            .body(strijdigheidofnietigheid);
    }

    /**
     * {@code PATCH  /strijdigheidofnietigheids/:id} : Partial updates given fields of an existing strijdigheidofnietigheid, field will ignore if it is null
     *
     * @param id the id of the strijdigheidofnietigheid to save.
     * @param strijdigheidofnietigheid the strijdigheidofnietigheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strijdigheidofnietigheid,
     * or with status {@code 400 (Bad Request)} if the strijdigheidofnietigheid is not valid,
     * or with status {@code 404 (Not Found)} if the strijdigheidofnietigheid is not found,
     * or with status {@code 500 (Internal Server Error)} if the strijdigheidofnietigheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Strijdigheidofnietigheid> partialUpdateStrijdigheidofnietigheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Strijdigheidofnietigheid strijdigheidofnietigheid
    ) throws URISyntaxException {
        log.debug("REST request to partial update Strijdigheidofnietigheid partially : {}, {}", id, strijdigheidofnietigheid);
        if (strijdigheidofnietigheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strijdigheidofnietigheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strijdigheidofnietigheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Strijdigheidofnietigheid> result = strijdigheidofnietigheidRepository
            .findById(strijdigheidofnietigheid.getId())
            .map(existingStrijdigheidofnietigheid -> {
                if (strijdigheidofnietigheid.getAanduidingstrijdigheidnietigheid() != null) {
                    existingStrijdigheidofnietigheid.setAanduidingstrijdigheidnietigheid(
                        strijdigheidofnietigheid.getAanduidingstrijdigheidnietigheid()
                    );
                }

                return existingStrijdigheidofnietigheid;
            })
            .map(strijdigheidofnietigheidRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, strijdigheidofnietigheid.getId().toString())
        );
    }

    /**
     * {@code GET  /strijdigheidofnietigheids} : get all the strijdigheidofnietigheids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strijdigheidofnietigheids in body.
     */
    @GetMapping("")
    public List<Strijdigheidofnietigheid> getAllStrijdigheidofnietigheids() {
        log.debug("REST request to get all Strijdigheidofnietigheids");
        return strijdigheidofnietigheidRepository.findAll();
    }

    /**
     * {@code GET  /strijdigheidofnietigheids/:id} : get the "id" strijdigheidofnietigheid.
     *
     * @param id the id of the strijdigheidofnietigheid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strijdigheidofnietigheid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Strijdigheidofnietigheid> getStrijdigheidofnietigheid(@PathVariable("id") Long id) {
        log.debug("REST request to get Strijdigheidofnietigheid : {}", id);
        Optional<Strijdigheidofnietigheid> strijdigheidofnietigheid = strijdigheidofnietigheidRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(strijdigheidofnietigheid);
    }

    /**
     * {@code DELETE  /strijdigheidofnietigheids/:id} : delete the "id" strijdigheidofnietigheid.
     *
     * @param id the id of the strijdigheidofnietigheid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrijdigheidofnietigheid(@PathVariable("id") Long id) {
        log.debug("REST request to delete Strijdigheidofnietigheid : {}", id);
        strijdigheidofnietigheidRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
