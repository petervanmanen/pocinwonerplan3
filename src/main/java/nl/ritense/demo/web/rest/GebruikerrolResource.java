package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gebruikerrol;
import nl.ritense.demo.repository.GebruikerrolRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gebruikerrol}.
 */
@RestController
@RequestMapping("/api/gebruikerrols")
@Transactional
public class GebruikerrolResource {

    private final Logger log = LoggerFactory.getLogger(GebruikerrolResource.class);

    private static final String ENTITY_NAME = "gebruikerrol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GebruikerrolRepository gebruikerrolRepository;

    public GebruikerrolResource(GebruikerrolRepository gebruikerrolRepository) {
        this.gebruikerrolRepository = gebruikerrolRepository;
    }

    /**
     * {@code POST  /gebruikerrols} : Create a new gebruikerrol.
     *
     * @param gebruikerrol the gebruikerrol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gebruikerrol, or with status {@code 400 (Bad Request)} if the gebruikerrol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gebruikerrol> createGebruikerrol(@RequestBody Gebruikerrol gebruikerrol) throws URISyntaxException {
        log.debug("REST request to save Gebruikerrol : {}", gebruikerrol);
        if (gebruikerrol.getId() != null) {
            throw new BadRequestAlertException("A new gebruikerrol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gebruikerrol = gebruikerrolRepository.save(gebruikerrol);
        return ResponseEntity.created(new URI("/api/gebruikerrols/" + gebruikerrol.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gebruikerrol.getId().toString()))
            .body(gebruikerrol);
    }

    /**
     * {@code PUT  /gebruikerrols/:id} : Updates an existing gebruikerrol.
     *
     * @param id the id of the gebruikerrol to save.
     * @param gebruikerrol the gebruikerrol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebruikerrol,
     * or with status {@code 400 (Bad Request)} if the gebruikerrol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gebruikerrol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gebruikerrol> updateGebruikerrol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gebruikerrol gebruikerrol
    ) throws URISyntaxException {
        log.debug("REST request to update Gebruikerrol : {}, {}", id, gebruikerrol);
        if (gebruikerrol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebruikerrol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebruikerrolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gebruikerrol = gebruikerrolRepository.save(gebruikerrol);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebruikerrol.getId().toString()))
            .body(gebruikerrol);
    }

    /**
     * {@code PATCH  /gebruikerrols/:id} : Partial updates given fields of an existing gebruikerrol, field will ignore if it is null
     *
     * @param id the id of the gebruikerrol to save.
     * @param gebruikerrol the gebruikerrol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebruikerrol,
     * or with status {@code 400 (Bad Request)} if the gebruikerrol is not valid,
     * or with status {@code 404 (Not Found)} if the gebruikerrol is not found,
     * or with status {@code 500 (Internal Server Error)} if the gebruikerrol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gebruikerrol> partialUpdateGebruikerrol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gebruikerrol gebruikerrol
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gebruikerrol partially : {}, {}", id, gebruikerrol);
        if (gebruikerrol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebruikerrol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebruikerrolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gebruikerrol> result = gebruikerrolRepository
            .findById(gebruikerrol.getId())
            .map(existingGebruikerrol -> {
                if (gebruikerrol.getRol() != null) {
                    existingGebruikerrol.setRol(gebruikerrol.getRol());
                }

                return existingGebruikerrol;
            })
            .map(gebruikerrolRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebruikerrol.getId().toString())
        );
    }

    /**
     * {@code GET  /gebruikerrols} : get all the gebruikerrols.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gebruikerrols in body.
     */
    @GetMapping("")
    public List<Gebruikerrol> getAllGebruikerrols() {
        log.debug("REST request to get all Gebruikerrols");
        return gebruikerrolRepository.findAll();
    }

    /**
     * {@code GET  /gebruikerrols/:id} : get the "id" gebruikerrol.
     *
     * @param id the id of the gebruikerrol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gebruikerrol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gebruikerrol> getGebruikerrol(@PathVariable("id") Long id) {
        log.debug("REST request to get Gebruikerrol : {}", id);
        Optional<Gebruikerrol> gebruikerrol = gebruikerrolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gebruikerrol);
    }

    /**
     * {@code DELETE  /gebruikerrols/:id} : delete the "id" gebruikerrol.
     *
     * @param id the id of the gebruikerrol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebruikerrol(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gebruikerrol : {}", id);
        gebruikerrolRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
