package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Keermuur;
import nl.ritense.demo.repository.KeermuurRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Keermuur}.
 */
@RestController
@RequestMapping("/api/keermuurs")
@Transactional
public class KeermuurResource {

    private final Logger log = LoggerFactory.getLogger(KeermuurResource.class);

    private static final String ENTITY_NAME = "keermuur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeermuurRepository keermuurRepository;

    public KeermuurResource(KeermuurRepository keermuurRepository) {
        this.keermuurRepository = keermuurRepository;
    }

    /**
     * {@code POST  /keermuurs} : Create a new keermuur.
     *
     * @param keermuur the keermuur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keermuur, or with status {@code 400 (Bad Request)} if the keermuur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Keermuur> createKeermuur(@RequestBody Keermuur keermuur) throws URISyntaxException {
        log.debug("REST request to save Keermuur : {}", keermuur);
        if (keermuur.getId() != null) {
            throw new BadRequestAlertException("A new keermuur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        keermuur = keermuurRepository.save(keermuur);
        return ResponseEntity.created(new URI("/api/keermuurs/" + keermuur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, keermuur.getId().toString()))
            .body(keermuur);
    }

    /**
     * {@code PUT  /keermuurs/:id} : Updates an existing keermuur.
     *
     * @param id the id of the keermuur to save.
     * @param keermuur the keermuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keermuur,
     * or with status {@code 400 (Bad Request)} if the keermuur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keermuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Keermuur> updateKeermuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Keermuur keermuur
    ) throws URISyntaxException {
        log.debug("REST request to update Keermuur : {}, {}", id, keermuur);
        if (keermuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, keermuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!keermuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        keermuur = keermuurRepository.save(keermuur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, keermuur.getId().toString()))
            .body(keermuur);
    }

    /**
     * {@code PATCH  /keermuurs/:id} : Partial updates given fields of an existing keermuur, field will ignore if it is null
     *
     * @param id the id of the keermuur to save.
     * @param keermuur the keermuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keermuur,
     * or with status {@code 400 (Bad Request)} if the keermuur is not valid,
     * or with status {@code 404 (Not Found)} if the keermuur is not found,
     * or with status {@code 500 (Internal Server Error)} if the keermuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Keermuur> partialUpdateKeermuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Keermuur keermuur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Keermuur partially : {}, {}", id, keermuur);
        if (keermuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, keermuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!keermuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Keermuur> result = keermuurRepository
            .findById(keermuur.getId())
            .map(existingKeermuur -> {
                if (keermuur.getBelastingklassenieuw() != null) {
                    existingKeermuur.setBelastingklassenieuw(keermuur.getBelastingklassenieuw());
                }
                if (keermuur.getBelastingklasseoud() != null) {
                    existingKeermuur.setBelastingklasseoud(keermuur.getBelastingklasseoud());
                }
                if (keermuur.getType() != null) {
                    existingKeermuur.setType(keermuur.getType());
                }

                return existingKeermuur;
            })
            .map(keermuurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, keermuur.getId().toString())
        );
    }

    /**
     * {@code GET  /keermuurs} : get all the keermuurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keermuurs in body.
     */
    @GetMapping("")
    public List<Keermuur> getAllKeermuurs() {
        log.debug("REST request to get all Keermuurs");
        return keermuurRepository.findAll();
    }

    /**
     * {@code GET  /keermuurs/:id} : get the "id" keermuur.
     *
     * @param id the id of the keermuur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keermuur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Keermuur> getKeermuur(@PathVariable("id") Long id) {
        log.debug("REST request to get Keermuur : {}", id);
        Optional<Keermuur> keermuur = keermuurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(keermuur);
    }

    /**
     * {@code DELETE  /keermuurs/:id} : delete the "id" keermuur.
     *
     * @param id the id of the keermuur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeermuur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Keermuur : {}", id);
        keermuurRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
