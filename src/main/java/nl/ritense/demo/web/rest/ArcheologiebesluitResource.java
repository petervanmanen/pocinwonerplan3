package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Archeologiebesluit;
import nl.ritense.demo.repository.ArcheologiebesluitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Archeologiebesluit}.
 */
@RestController
@RequestMapping("/api/archeologiebesluits")
@Transactional
public class ArcheologiebesluitResource {

    private final Logger log = LoggerFactory.getLogger(ArcheologiebesluitResource.class);

    private static final String ENTITY_NAME = "archeologiebesluit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArcheologiebesluitRepository archeologiebesluitRepository;

    public ArcheologiebesluitResource(ArcheologiebesluitRepository archeologiebesluitRepository) {
        this.archeologiebesluitRepository = archeologiebesluitRepository;
    }

    /**
     * {@code POST  /archeologiebesluits} : Create a new archeologiebesluit.
     *
     * @param archeologiebesluit the archeologiebesluit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new archeologiebesluit, or with status {@code 400 (Bad Request)} if the archeologiebesluit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Archeologiebesluit> createArcheologiebesluit(@RequestBody Archeologiebesluit archeologiebesluit)
        throws URISyntaxException {
        log.debug("REST request to save Archeologiebesluit : {}", archeologiebesluit);
        if (archeologiebesluit.getId() != null) {
            throw new BadRequestAlertException("A new archeologiebesluit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        archeologiebesluit = archeologiebesluitRepository.save(archeologiebesluit);
        return ResponseEntity.created(new URI("/api/archeologiebesluits/" + archeologiebesluit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, archeologiebesluit.getId().toString()))
            .body(archeologiebesluit);
    }

    /**
     * {@code PUT  /archeologiebesluits/:id} : Updates an existing archeologiebesluit.
     *
     * @param id the id of the archeologiebesluit to save.
     * @param archeologiebesluit the archeologiebesluit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archeologiebesluit,
     * or with status {@code 400 (Bad Request)} if the archeologiebesluit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the archeologiebesluit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Archeologiebesluit> updateArcheologiebesluit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Archeologiebesluit archeologiebesluit
    ) throws URISyntaxException {
        log.debug("REST request to update Archeologiebesluit : {}, {}", id, archeologiebesluit);
        if (archeologiebesluit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archeologiebesluit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archeologiebesluitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        archeologiebesluit = archeologiebesluitRepository.save(archeologiebesluit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, archeologiebesluit.getId().toString()))
            .body(archeologiebesluit);
    }

    /**
     * {@code PATCH  /archeologiebesluits/:id} : Partial updates given fields of an existing archeologiebesluit, field will ignore if it is null
     *
     * @param id the id of the archeologiebesluit to save.
     * @param archeologiebesluit the archeologiebesluit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archeologiebesluit,
     * or with status {@code 400 (Bad Request)} if the archeologiebesluit is not valid,
     * or with status {@code 404 (Not Found)} if the archeologiebesluit is not found,
     * or with status {@code 500 (Internal Server Error)} if the archeologiebesluit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Archeologiebesluit> partialUpdateArcheologiebesluit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Archeologiebesluit archeologiebesluit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Archeologiebesluit partially : {}, {}", id, archeologiebesluit);
        if (archeologiebesluit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archeologiebesluit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archeologiebesluitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Archeologiebesluit> result = archeologiebesluitRepository
            .findById(archeologiebesluit.getId())
            .map(archeologiebesluitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, archeologiebesluit.getId().toString())
        );
    }

    /**
     * {@code GET  /archeologiebesluits} : get all the archeologiebesluits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of archeologiebesluits in body.
     */
    @GetMapping("")
    public List<Archeologiebesluit> getAllArcheologiebesluits() {
        log.debug("REST request to get all Archeologiebesluits");
        return archeologiebesluitRepository.findAll();
    }

    /**
     * {@code GET  /archeologiebesluits/:id} : get the "id" archeologiebesluit.
     *
     * @param id the id of the archeologiebesluit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the archeologiebesluit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Archeologiebesluit> getArcheologiebesluit(@PathVariable("id") Long id) {
        log.debug("REST request to get Archeologiebesluit : {}", id);
        Optional<Archeologiebesluit> archeologiebesluit = archeologiebesluitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(archeologiebesluit);
    }

    /**
     * {@code DELETE  /archeologiebesluits/:id} : delete the "id" archeologiebesluit.
     *
     * @param id the id of the archeologiebesluit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArcheologiebesluit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Archeologiebesluit : {}", id);
        archeologiebesluitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
