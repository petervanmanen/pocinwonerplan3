package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gebruiksdoel;
import nl.ritense.demo.repository.GebruiksdoelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gebruiksdoel}.
 */
@RestController
@RequestMapping("/api/gebruiksdoels")
@Transactional
public class GebruiksdoelResource {

    private final Logger log = LoggerFactory.getLogger(GebruiksdoelResource.class);

    private static final String ENTITY_NAME = "gebruiksdoel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GebruiksdoelRepository gebruiksdoelRepository;

    public GebruiksdoelResource(GebruiksdoelRepository gebruiksdoelRepository) {
        this.gebruiksdoelRepository = gebruiksdoelRepository;
    }

    /**
     * {@code POST  /gebruiksdoels} : Create a new gebruiksdoel.
     *
     * @param gebruiksdoel the gebruiksdoel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gebruiksdoel, or with status {@code 400 (Bad Request)} if the gebruiksdoel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gebruiksdoel> createGebruiksdoel(@RequestBody Gebruiksdoel gebruiksdoel) throws URISyntaxException {
        log.debug("REST request to save Gebruiksdoel : {}", gebruiksdoel);
        if (gebruiksdoel.getId() != null) {
            throw new BadRequestAlertException("A new gebruiksdoel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gebruiksdoel = gebruiksdoelRepository.save(gebruiksdoel);
        return ResponseEntity.created(new URI("/api/gebruiksdoels/" + gebruiksdoel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gebruiksdoel.getId().toString()))
            .body(gebruiksdoel);
    }

    /**
     * {@code PUT  /gebruiksdoels/:id} : Updates an existing gebruiksdoel.
     *
     * @param id the id of the gebruiksdoel to save.
     * @param gebruiksdoel the gebruiksdoel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebruiksdoel,
     * or with status {@code 400 (Bad Request)} if the gebruiksdoel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gebruiksdoel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gebruiksdoel> updateGebruiksdoel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gebruiksdoel gebruiksdoel
    ) throws URISyntaxException {
        log.debug("REST request to update Gebruiksdoel : {}, {}", id, gebruiksdoel);
        if (gebruiksdoel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebruiksdoel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebruiksdoelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gebruiksdoel = gebruiksdoelRepository.save(gebruiksdoel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebruiksdoel.getId().toString()))
            .body(gebruiksdoel);
    }

    /**
     * {@code PATCH  /gebruiksdoels/:id} : Partial updates given fields of an existing gebruiksdoel, field will ignore if it is null
     *
     * @param id the id of the gebruiksdoel to save.
     * @param gebruiksdoel the gebruiksdoel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebruiksdoel,
     * or with status {@code 400 (Bad Request)} if the gebruiksdoel is not valid,
     * or with status {@code 404 (Not Found)} if the gebruiksdoel is not found,
     * or with status {@code 500 (Internal Server Error)} if the gebruiksdoel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gebruiksdoel> partialUpdateGebruiksdoel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gebruiksdoel gebruiksdoel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gebruiksdoel partially : {}, {}", id, gebruiksdoel);
        if (gebruiksdoel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebruiksdoel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebruiksdoelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gebruiksdoel> result = gebruiksdoelRepository
            .findById(gebruiksdoel.getId())
            .map(existingGebruiksdoel -> {
                if (gebruiksdoel.getGebruiksdoelgebouwdobject() != null) {
                    existingGebruiksdoel.setGebruiksdoelgebouwdobject(gebruiksdoel.getGebruiksdoelgebouwdobject());
                }

                return existingGebruiksdoel;
            })
            .map(gebruiksdoelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebruiksdoel.getId().toString())
        );
    }

    /**
     * {@code GET  /gebruiksdoels} : get all the gebruiksdoels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gebruiksdoels in body.
     */
    @GetMapping("")
    public List<Gebruiksdoel> getAllGebruiksdoels() {
        log.debug("REST request to get all Gebruiksdoels");
        return gebruiksdoelRepository.findAll();
    }

    /**
     * {@code GET  /gebruiksdoels/:id} : get the "id" gebruiksdoel.
     *
     * @param id the id of the gebruiksdoel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gebruiksdoel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gebruiksdoel> getGebruiksdoel(@PathVariable("id") Long id) {
        log.debug("REST request to get Gebruiksdoel : {}", id);
        Optional<Gebruiksdoel> gebruiksdoel = gebruiksdoelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gebruiksdoel);
    }

    /**
     * {@code DELETE  /gebruiksdoels/:id} : delete the "id" gebruiksdoel.
     *
     * @param id the id of the gebruiksdoel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebruiksdoel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gebruiksdoel : {}", id);
        gebruiksdoelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
