package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Archief;
import nl.ritense.demo.repository.ArchiefRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Archief}.
 */
@RestController
@RequestMapping("/api/archiefs")
@Transactional
public class ArchiefResource {

    private final Logger log = LoggerFactory.getLogger(ArchiefResource.class);

    private static final String ENTITY_NAME = "archief";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArchiefRepository archiefRepository;

    public ArchiefResource(ArchiefRepository archiefRepository) {
        this.archiefRepository = archiefRepository;
    }

    /**
     * {@code POST  /archiefs} : Create a new archief.
     *
     * @param archief the archief to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new archief, or with status {@code 400 (Bad Request)} if the archief has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Archief> createArchief(@Valid @RequestBody Archief archief) throws URISyntaxException {
        log.debug("REST request to save Archief : {}", archief);
        if (archief.getId() != null) {
            throw new BadRequestAlertException("A new archief cannot already have an ID", ENTITY_NAME, "idexists");
        }
        archief = archiefRepository.save(archief);
        return ResponseEntity.created(new URI("/api/archiefs/" + archief.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, archief.getId().toString()))
            .body(archief);
    }

    /**
     * {@code PUT  /archiefs/:id} : Updates an existing archief.
     *
     * @param id the id of the archief to save.
     * @param archief the archief to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archief,
     * or with status {@code 400 (Bad Request)} if the archief is not valid,
     * or with status {@code 500 (Internal Server Error)} if the archief couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Archief> updateArchief(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Archief archief
    ) throws URISyntaxException {
        log.debug("REST request to update Archief : {}, {}", id, archief);
        if (archief.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archief.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archiefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        archief = archiefRepository.save(archief);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, archief.getId().toString()))
            .body(archief);
    }

    /**
     * {@code PATCH  /archiefs/:id} : Partial updates given fields of an existing archief, field will ignore if it is null
     *
     * @param id the id of the archief to save.
     * @param archief the archief to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archief,
     * or with status {@code 400 (Bad Request)} if the archief is not valid,
     * or with status {@code 404 (Not Found)} if the archief is not found,
     * or with status {@code 500 (Internal Server Error)} if the archief couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Archief> partialUpdateArchief(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Archief archief
    ) throws URISyntaxException {
        log.debug("REST request to partial update Archief partially : {}, {}", id, archief);
        if (archief.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archief.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archiefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Archief> result = archiefRepository
            .findById(archief.getId())
            .map(existingArchief -> {
                if (archief.getArchiefnummer() != null) {
                    existingArchief.setArchiefnummer(archief.getArchiefnummer());
                }
                if (archief.getNaam() != null) {
                    existingArchief.setNaam(archief.getNaam());
                }
                if (archief.getOmschrijving() != null) {
                    existingArchief.setOmschrijving(archief.getOmschrijving());
                }
                if (archief.getOpenbaarheidsbeperking() != null) {
                    existingArchief.setOpenbaarheidsbeperking(archief.getOpenbaarheidsbeperking());
                }

                return existingArchief;
            })
            .map(archiefRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, archief.getId().toString())
        );
    }

    /**
     * {@code GET  /archiefs} : get all the archiefs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of archiefs in body.
     */
    @GetMapping("")
    public List<Archief> getAllArchiefs(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Archiefs");
        if (eagerload) {
            return archiefRepository.findAllWithEagerRelationships();
        } else {
            return archiefRepository.findAll();
        }
    }

    /**
     * {@code GET  /archiefs/:id} : get the "id" archief.
     *
     * @param id the id of the archief to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the archief, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Archief> getArchief(@PathVariable("id") Long id) {
        log.debug("REST request to get Archief : {}", id);
        Optional<Archief> archief = archiefRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(archief);
    }

    /**
     * {@code DELETE  /archiefs/:id} : delete the "id" archief.
     *
     * @param id the id of the archief to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArchief(@PathVariable("id") Long id) {
        log.debug("REST request to delete Archief : {}", id);
        archiefRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
