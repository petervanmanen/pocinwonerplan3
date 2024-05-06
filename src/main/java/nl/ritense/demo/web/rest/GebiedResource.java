package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gebied;
import nl.ritense.demo.repository.GebiedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gebied}.
 */
@RestController
@RequestMapping("/api/gebieds")
@Transactional
public class GebiedResource {

    private final Logger log = LoggerFactory.getLogger(GebiedResource.class);

    private static final String ENTITY_NAME = "gebied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GebiedRepository gebiedRepository;

    public GebiedResource(GebiedRepository gebiedRepository) {
        this.gebiedRepository = gebiedRepository;
    }

    /**
     * {@code POST  /gebieds} : Create a new gebied.
     *
     * @param gebied the gebied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gebied, or with status {@code 400 (Bad Request)} if the gebied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gebied> createGebied(@RequestBody Gebied gebied) throws URISyntaxException {
        log.debug("REST request to save Gebied : {}", gebied);
        if (gebied.getId() != null) {
            throw new BadRequestAlertException("A new gebied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gebied = gebiedRepository.save(gebied);
        return ResponseEntity.created(new URI("/api/gebieds/" + gebied.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gebied.getId().toString()))
            .body(gebied);
    }

    /**
     * {@code PUT  /gebieds/:id} : Updates an existing gebied.
     *
     * @param id the id of the gebied to save.
     * @param gebied the gebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebied,
     * or with status {@code 400 (Bad Request)} if the gebied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gebied> updateGebied(@PathVariable(value = "id", required = false) final Long id, @RequestBody Gebied gebied)
        throws URISyntaxException {
        log.debug("REST request to update Gebied : {}, {}", id, gebied);
        if (gebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gebied = gebiedRepository.save(gebied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebied.getId().toString()))
            .body(gebied);
    }

    /**
     * {@code PATCH  /gebieds/:id} : Partial updates given fields of an existing gebied, field will ignore if it is null
     *
     * @param id the id of the gebied to save.
     * @param gebied the gebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebied,
     * or with status {@code 400 (Bad Request)} if the gebied is not valid,
     * or with status {@code 404 (Not Found)} if the gebied is not found,
     * or with status {@code 500 (Internal Server Error)} if the gebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gebied> partialUpdateGebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gebied gebied
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gebied partially : {}, {}", id, gebied);
        if (gebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gebied> result = gebiedRepository
            .findById(gebied.getId())
            .map(existingGebied -> {
                if (gebied.getGebied() != null) {
                    existingGebied.setGebied(gebied.getGebied());
                }

                return existingGebied;
            })
            .map(gebiedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebied.getId().toString())
        );
    }

    /**
     * {@code GET  /gebieds} : get all the gebieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gebieds in body.
     */
    @GetMapping("")
    public List<Gebied> getAllGebieds() {
        log.debug("REST request to get all Gebieds");
        return gebiedRepository.findAll();
    }

    /**
     * {@code GET  /gebieds/:id} : get the "id" gebied.
     *
     * @param id the id of the gebied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gebied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gebied> getGebied(@PathVariable("id") Long id) {
        log.debug("REST request to get Gebied : {}", id);
        Optional<Gebied> gebied = gebiedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gebied);
    }

    /**
     * {@code DELETE  /gebieds/:id} : delete the "id" gebied.
     *
     * @param id the id of the gebied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebied(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gebied : {}", id);
        gebiedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
