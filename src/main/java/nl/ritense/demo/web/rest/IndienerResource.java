package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Indiener;
import nl.ritense.demo.repository.IndienerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Indiener}.
 */
@RestController
@RequestMapping("/api/indieners")
@Transactional
public class IndienerResource {

    private final Logger log = LoggerFactory.getLogger(IndienerResource.class);

    private static final String ENTITY_NAME = "indiener";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndienerRepository indienerRepository;

    public IndienerResource(IndienerRepository indienerRepository) {
        this.indienerRepository = indienerRepository;
    }

    /**
     * {@code POST  /indieners} : Create a new indiener.
     *
     * @param indiener the indiener to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indiener, or with status {@code 400 (Bad Request)} if the indiener has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Indiener> createIndiener(@RequestBody Indiener indiener) throws URISyntaxException {
        log.debug("REST request to save Indiener : {}", indiener);
        if (indiener.getId() != null) {
            throw new BadRequestAlertException("A new indiener cannot already have an ID", ENTITY_NAME, "idexists");
        }
        indiener = indienerRepository.save(indiener);
        return ResponseEntity.created(new URI("/api/indieners/" + indiener.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, indiener.getId().toString()))
            .body(indiener);
    }

    /**
     * {@code PUT  /indieners/:id} : Updates an existing indiener.
     *
     * @param id the id of the indiener to save.
     * @param indiener the indiener to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indiener,
     * or with status {@code 400 (Bad Request)} if the indiener is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indiener couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Indiener> updateIndiener(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Indiener indiener
    ) throws URISyntaxException {
        log.debug("REST request to update Indiener : {}, {}", id, indiener);
        if (indiener.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indiener.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indienerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        indiener = indienerRepository.save(indiener);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indiener.getId().toString()))
            .body(indiener);
    }

    /**
     * {@code PATCH  /indieners/:id} : Partial updates given fields of an existing indiener, field will ignore if it is null
     *
     * @param id the id of the indiener to save.
     * @param indiener the indiener to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indiener,
     * or with status {@code 400 (Bad Request)} if the indiener is not valid,
     * or with status {@code 404 (Not Found)} if the indiener is not found,
     * or with status {@code 500 (Internal Server Error)} if the indiener couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Indiener> partialUpdateIndiener(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Indiener indiener
    ) throws URISyntaxException {
        log.debug("REST request to partial update Indiener partially : {}, {}", id, indiener);
        if (indiener.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indiener.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indienerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Indiener> result = indienerRepository
            .findById(indiener.getId())
            .map(existingIndiener -> {
                if (indiener.getNaam() != null) {
                    existingIndiener.setNaam(indiener.getNaam());
                }
                if (indiener.getOmschrijving() != null) {
                    existingIndiener.setOmschrijving(indiener.getOmschrijving());
                }

                return existingIndiener;
            })
            .map(indienerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indiener.getId().toString())
        );
    }

    /**
     * {@code GET  /indieners} : get all the indieners.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indieners in body.
     */
    @GetMapping("")
    public List<Indiener> getAllIndieners(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Indieners");
        if (eagerload) {
            return indienerRepository.findAllWithEagerRelationships();
        } else {
            return indienerRepository.findAll();
        }
    }

    /**
     * {@code GET  /indieners/:id} : get the "id" indiener.
     *
     * @param id the id of the indiener to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indiener, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Indiener> getIndiener(@PathVariable("id") Long id) {
        log.debug("REST request to get Indiener : {}", id);
        Optional<Indiener> indiener = indienerRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(indiener);
    }

    /**
     * {@code DELETE  /indieners/:id} : delete the "id" indiener.
     *
     * @param id the id of the indiener to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndiener(@PathVariable("id") Long id) {
        log.debug("REST request to delete Indiener : {}", id);
        indienerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
