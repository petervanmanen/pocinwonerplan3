package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Geweldsincident;
import nl.ritense.demo.repository.GeweldsincidentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Geweldsincident}.
 */
@RestController
@RequestMapping("/api/geweldsincidents")
@Transactional
public class GeweldsincidentResource {

    private final Logger log = LoggerFactory.getLogger(GeweldsincidentResource.class);

    private static final String ENTITY_NAME = "geweldsincident";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeweldsincidentRepository geweldsincidentRepository;

    public GeweldsincidentResource(GeweldsincidentRepository geweldsincidentRepository) {
        this.geweldsincidentRepository = geweldsincidentRepository;
    }

    /**
     * {@code POST  /geweldsincidents} : Create a new geweldsincident.
     *
     * @param geweldsincident the geweldsincident to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geweldsincident, or with status {@code 400 (Bad Request)} if the geweldsincident has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Geweldsincident> createGeweldsincident(@Valid @RequestBody Geweldsincident geweldsincident)
        throws URISyntaxException {
        log.debug("REST request to save Geweldsincident : {}", geweldsincident);
        if (geweldsincident.getId() != null) {
            throw new BadRequestAlertException("A new geweldsincident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        geweldsincident = geweldsincidentRepository.save(geweldsincident);
        return ResponseEntity.created(new URI("/api/geweldsincidents/" + geweldsincident.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, geweldsincident.getId().toString()))
            .body(geweldsincident);
    }

    /**
     * {@code PUT  /geweldsincidents/:id} : Updates an existing geweldsincident.
     *
     * @param id the id of the geweldsincident to save.
     * @param geweldsincident the geweldsincident to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geweldsincident,
     * or with status {@code 400 (Bad Request)} if the geweldsincident is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geweldsincident couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Geweldsincident> updateGeweldsincident(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Geweldsincident geweldsincident
    ) throws URISyntaxException {
        log.debug("REST request to update Geweldsincident : {}, {}", id, geweldsincident);
        if (geweldsincident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geweldsincident.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geweldsincidentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        geweldsincident = geweldsincidentRepository.save(geweldsincident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geweldsincident.getId().toString()))
            .body(geweldsincident);
    }

    /**
     * {@code PATCH  /geweldsincidents/:id} : Partial updates given fields of an existing geweldsincident, field will ignore if it is null
     *
     * @param id the id of the geweldsincident to save.
     * @param geweldsincident the geweldsincident to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geweldsincident,
     * or with status {@code 400 (Bad Request)} if the geweldsincident is not valid,
     * or with status {@code 404 (Not Found)} if the geweldsincident is not found,
     * or with status {@code 500 (Internal Server Error)} if the geweldsincident couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Geweldsincident> partialUpdateGeweldsincident(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Geweldsincident geweldsincident
    ) throws URISyntaxException {
        log.debug("REST request to partial update Geweldsincident partially : {}, {}", id, geweldsincident);
        if (geweldsincident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geweldsincident.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geweldsincidentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Geweldsincident> result = geweldsincidentRepository
            .findById(geweldsincident.getId())
            .map(existingGeweldsincident -> {
                if (geweldsincident.getDatum() != null) {
                    existingGeweldsincident.setDatum(geweldsincident.getDatum());
                }
                if (geweldsincident.getOmschrijving() != null) {
                    existingGeweldsincident.setOmschrijving(geweldsincident.getOmschrijving());
                }
                if (geweldsincident.getType() != null) {
                    existingGeweldsincident.setType(geweldsincident.getType());
                }

                return existingGeweldsincident;
            })
            .map(geweldsincidentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geweldsincident.getId().toString())
        );
    }

    /**
     * {@code GET  /geweldsincidents} : get all the geweldsincidents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geweldsincidents in body.
     */
    @GetMapping("")
    public List<Geweldsincident> getAllGeweldsincidents() {
        log.debug("REST request to get all Geweldsincidents");
        return geweldsincidentRepository.findAll();
    }

    /**
     * {@code GET  /geweldsincidents/:id} : get the "id" geweldsincident.
     *
     * @param id the id of the geweldsincident to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geweldsincident, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Geweldsincident> getGeweldsincident(@PathVariable("id") Long id) {
        log.debug("REST request to get Geweldsincident : {}", id);
        Optional<Geweldsincident> geweldsincident = geweldsincidentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(geweldsincident);
    }

    /**
     * {@code DELETE  /geweldsincidents/:id} : delete the "id" geweldsincident.
     *
     * @param id the id of the geweldsincident to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeweldsincident(@PathVariable("id") Long id) {
        log.debug("REST request to delete Geweldsincident : {}", id);
        geweldsincidentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
