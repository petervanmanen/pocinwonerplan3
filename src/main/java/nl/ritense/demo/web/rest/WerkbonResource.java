package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Werkbon;
import nl.ritense.demo.repository.WerkbonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Werkbon}.
 */
@RestController
@RequestMapping("/api/werkbons")
@Transactional
public class WerkbonResource {

    private final Logger log = LoggerFactory.getLogger(WerkbonResource.class);

    private static final String ENTITY_NAME = "werkbon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WerkbonRepository werkbonRepository;

    public WerkbonResource(WerkbonRepository werkbonRepository) {
        this.werkbonRepository = werkbonRepository;
    }

    /**
     * {@code POST  /werkbons} : Create a new werkbon.
     *
     * @param werkbon the werkbon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new werkbon, or with status {@code 400 (Bad Request)} if the werkbon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Werkbon> createWerkbon(@RequestBody Werkbon werkbon) throws URISyntaxException {
        log.debug("REST request to save Werkbon : {}", werkbon);
        if (werkbon.getId() != null) {
            throw new BadRequestAlertException("A new werkbon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        werkbon = werkbonRepository.save(werkbon);
        return ResponseEntity.created(new URI("/api/werkbons/" + werkbon.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, werkbon.getId().toString()))
            .body(werkbon);
    }

    /**
     * {@code PUT  /werkbons/:id} : Updates an existing werkbon.
     *
     * @param id the id of the werkbon to save.
     * @param werkbon the werkbon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated werkbon,
     * or with status {@code 400 (Bad Request)} if the werkbon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the werkbon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Werkbon> updateWerkbon(@PathVariable(value = "id", required = false) final Long id, @RequestBody Werkbon werkbon)
        throws URISyntaxException {
        log.debug("REST request to update Werkbon : {}, {}", id, werkbon);
        if (werkbon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, werkbon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!werkbonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        werkbon = werkbonRepository.save(werkbon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, werkbon.getId().toString()))
            .body(werkbon);
    }

    /**
     * {@code PATCH  /werkbons/:id} : Partial updates given fields of an existing werkbon, field will ignore if it is null
     *
     * @param id the id of the werkbon to save.
     * @param werkbon the werkbon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated werkbon,
     * or with status {@code 400 (Bad Request)} if the werkbon is not valid,
     * or with status {@code 404 (Not Found)} if the werkbon is not found,
     * or with status {@code 500 (Internal Server Error)} if the werkbon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Werkbon> partialUpdateWerkbon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Werkbon werkbon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Werkbon partially : {}, {}", id, werkbon);
        if (werkbon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, werkbon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!werkbonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Werkbon> result = werkbonRepository.findById(werkbon.getId()).map(werkbonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, werkbon.getId().toString())
        );
    }

    /**
     * {@code GET  /werkbons} : get all the werkbons.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of werkbons in body.
     */
    @GetMapping("")
    public List<Werkbon> getAllWerkbons(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Werkbons");
        if (eagerload) {
            return werkbonRepository.findAllWithEagerRelationships();
        } else {
            return werkbonRepository.findAll();
        }
    }

    /**
     * {@code GET  /werkbons/:id} : get the "id" werkbon.
     *
     * @param id the id of the werkbon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the werkbon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Werkbon> getWerkbon(@PathVariable("id") Long id) {
        log.debug("REST request to get Werkbon : {}", id);
        Optional<Werkbon> werkbon = werkbonRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(werkbon);
    }

    /**
     * {@code DELETE  /werkbons/:id} : delete the "id" werkbon.
     *
     * @param id the id of the werkbon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWerkbon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Werkbon : {}", id);
        werkbonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
