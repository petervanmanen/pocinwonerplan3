package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overlijdeningeschrevenpersoon;
import nl.ritense.demo.repository.OverlijdeningeschrevenpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overlijdeningeschrevenpersoon}.
 */
@RestController
@RequestMapping("/api/overlijdeningeschrevenpersoons")
@Transactional
public class OverlijdeningeschrevenpersoonResource {

    private final Logger log = LoggerFactory.getLogger(OverlijdeningeschrevenpersoonResource.class);

    private static final String ENTITY_NAME = "overlijdeningeschrevenpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverlijdeningeschrevenpersoonRepository overlijdeningeschrevenpersoonRepository;

    public OverlijdeningeschrevenpersoonResource(OverlijdeningeschrevenpersoonRepository overlijdeningeschrevenpersoonRepository) {
        this.overlijdeningeschrevenpersoonRepository = overlijdeningeschrevenpersoonRepository;
    }

    /**
     * {@code POST  /overlijdeningeschrevenpersoons} : Create a new overlijdeningeschrevenpersoon.
     *
     * @param overlijdeningeschrevenpersoon the overlijdeningeschrevenpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overlijdeningeschrevenpersoon, or with status {@code 400 (Bad Request)} if the overlijdeningeschrevenpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overlijdeningeschrevenpersoon> createOverlijdeningeschrevenpersoon(
        @RequestBody Overlijdeningeschrevenpersoon overlijdeningeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Overlijdeningeschrevenpersoon : {}", overlijdeningeschrevenpersoon);
        if (overlijdeningeschrevenpersoon.getId() != null) {
            throw new BadRequestAlertException("A new overlijdeningeschrevenpersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        overlijdeningeschrevenpersoon = overlijdeningeschrevenpersoonRepository.save(overlijdeningeschrevenpersoon);
        return ResponseEntity.created(new URI("/api/overlijdeningeschrevenpersoons/" + overlijdeningeschrevenpersoon.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, overlijdeningeschrevenpersoon.getId().toString())
            )
            .body(overlijdeningeschrevenpersoon);
    }

    /**
     * {@code PUT  /overlijdeningeschrevenpersoons/:id} : Updates an existing overlijdeningeschrevenpersoon.
     *
     * @param id the id of the overlijdeningeschrevenpersoon to save.
     * @param overlijdeningeschrevenpersoon the overlijdeningeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overlijdeningeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the overlijdeningeschrevenpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overlijdeningeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overlijdeningeschrevenpersoon> updateOverlijdeningeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overlijdeningeschrevenpersoon overlijdeningeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Overlijdeningeschrevenpersoon : {}, {}", id, overlijdeningeschrevenpersoon);
        if (overlijdeningeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overlijdeningeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overlijdeningeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overlijdeningeschrevenpersoon = overlijdeningeschrevenpersoonRepository.save(overlijdeningeschrevenpersoon);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overlijdeningeschrevenpersoon.getId().toString())
            )
            .body(overlijdeningeschrevenpersoon);
    }

    /**
     * {@code PATCH  /overlijdeningeschrevenpersoons/:id} : Partial updates given fields of an existing overlijdeningeschrevenpersoon, field will ignore if it is null
     *
     * @param id the id of the overlijdeningeschrevenpersoon to save.
     * @param overlijdeningeschrevenpersoon the overlijdeningeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overlijdeningeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the overlijdeningeschrevenpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the overlijdeningeschrevenpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the overlijdeningeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overlijdeningeschrevenpersoon> partialUpdateOverlijdeningeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overlijdeningeschrevenpersoon overlijdeningeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Overlijdeningeschrevenpersoon partially : {}, {}", id, overlijdeningeschrevenpersoon);
        if (overlijdeningeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overlijdeningeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overlijdeningeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overlijdeningeschrevenpersoon> result = overlijdeningeschrevenpersoonRepository
            .findById(overlijdeningeschrevenpersoon.getId())
            .map(existingOverlijdeningeschrevenpersoon -> {
                if (overlijdeningeschrevenpersoon.getDatumoverlijden() != null) {
                    existingOverlijdeningeschrevenpersoon.setDatumoverlijden(overlijdeningeschrevenpersoon.getDatumoverlijden());
                }
                if (overlijdeningeschrevenpersoon.getLandoverlijden() != null) {
                    existingOverlijdeningeschrevenpersoon.setLandoverlijden(overlijdeningeschrevenpersoon.getLandoverlijden());
                }
                if (overlijdeningeschrevenpersoon.getOverlijdensplaats() != null) {
                    existingOverlijdeningeschrevenpersoon.setOverlijdensplaats(overlijdeningeschrevenpersoon.getOverlijdensplaats());
                }

                return existingOverlijdeningeschrevenpersoon;
            })
            .map(overlijdeningeschrevenpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overlijdeningeschrevenpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /overlijdeningeschrevenpersoons} : get all the overlijdeningeschrevenpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overlijdeningeschrevenpersoons in body.
     */
    @GetMapping("")
    public List<Overlijdeningeschrevenpersoon> getAllOverlijdeningeschrevenpersoons() {
        log.debug("REST request to get all Overlijdeningeschrevenpersoons");
        return overlijdeningeschrevenpersoonRepository.findAll();
    }

    /**
     * {@code GET  /overlijdeningeschrevenpersoons/:id} : get the "id" overlijdeningeschrevenpersoon.
     *
     * @param id the id of the overlijdeningeschrevenpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overlijdeningeschrevenpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overlijdeningeschrevenpersoon> getOverlijdeningeschrevenpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Overlijdeningeschrevenpersoon : {}", id);
        Optional<Overlijdeningeschrevenpersoon> overlijdeningeschrevenpersoon = overlijdeningeschrevenpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overlijdeningeschrevenpersoon);
    }

    /**
     * {@code DELETE  /overlijdeningeschrevenpersoons/:id} : delete the "id" overlijdeningeschrevenpersoon.
     *
     * @param id the id of the overlijdeningeschrevenpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverlijdeningeschrevenpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overlijdeningeschrevenpersoon : {}", id);
        overlijdeningeschrevenpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
