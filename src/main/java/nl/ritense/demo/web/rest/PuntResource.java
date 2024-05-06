package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Punt;
import nl.ritense.demo.repository.PuntRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Punt}.
 */
@RestController
@RequestMapping("/api/punts")
@Transactional
public class PuntResource {

    private final Logger log = LoggerFactory.getLogger(PuntResource.class);

    private static final String ENTITY_NAME = "punt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PuntRepository puntRepository;

    public PuntResource(PuntRepository puntRepository) {
        this.puntRepository = puntRepository;
    }

    /**
     * {@code POST  /punts} : Create a new punt.
     *
     * @param punt the punt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new punt, or with status {@code 400 (Bad Request)} if the punt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Punt> createPunt(@RequestBody Punt punt) throws URISyntaxException {
        log.debug("REST request to save Punt : {}", punt);
        if (punt.getId() != null) {
            throw new BadRequestAlertException("A new punt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        punt = puntRepository.save(punt);
        return ResponseEntity.created(new URI("/api/punts/" + punt.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, punt.getId().toString()))
            .body(punt);
    }

    /**
     * {@code PUT  /punts/:id} : Updates an existing punt.
     *
     * @param id the id of the punt to save.
     * @param punt the punt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated punt,
     * or with status {@code 400 (Bad Request)} if the punt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the punt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Punt> updatePunt(@PathVariable(value = "id", required = false) final Long id, @RequestBody Punt punt)
        throws URISyntaxException {
        log.debug("REST request to update Punt : {}, {}", id, punt);
        if (punt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, punt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!puntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        punt = puntRepository.save(punt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, punt.getId().toString()))
            .body(punt);
    }

    /**
     * {@code PATCH  /punts/:id} : Partial updates given fields of an existing punt, field will ignore if it is null
     *
     * @param id the id of the punt to save.
     * @param punt the punt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated punt,
     * or with status {@code 400 (Bad Request)} if the punt is not valid,
     * or with status {@code 404 (Not Found)} if the punt is not found,
     * or with status {@code 500 (Internal Server Error)} if the punt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Punt> partialUpdatePunt(@PathVariable(value = "id", required = false) final Long id, @RequestBody Punt punt)
        throws URISyntaxException {
        log.debug("REST request to partial update Punt partially : {}, {}", id, punt);
        if (punt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, punt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!puntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Punt> result = puntRepository
            .findById(punt.getId())
            .map(existingPunt -> {
                if (punt.getPunt() != null) {
                    existingPunt.setPunt(punt.getPunt());
                }

                return existingPunt;
            })
            .map(puntRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, punt.getId().toString())
        );
    }

    /**
     * {@code GET  /punts} : get all the punts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of punts in body.
     */
    @GetMapping("")
    public List<Punt> getAllPunts() {
        log.debug("REST request to get all Punts");
        return puntRepository.findAll();
    }

    /**
     * {@code GET  /punts/:id} : get the "id" punt.
     *
     * @param id the id of the punt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the punt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Punt> getPunt(@PathVariable("id") Long id) {
        log.debug("REST request to get Punt : {}", id);
        Optional<Punt> punt = puntRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(punt);
    }

    /**
     * {@code DELETE  /punts/:id} : delete the "id" punt.
     *
     * @param id the id of the punt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePunt(@PathVariable("id") Long id) {
        log.debug("REST request to delete Punt : {}", id);
        puntRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
