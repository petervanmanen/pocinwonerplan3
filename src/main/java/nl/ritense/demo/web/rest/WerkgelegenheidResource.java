package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Werkgelegenheid;
import nl.ritense.demo.repository.WerkgelegenheidRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Werkgelegenheid}.
 */
@RestController
@RequestMapping("/api/werkgelegenheids")
@Transactional
public class WerkgelegenheidResource {

    private final Logger log = LoggerFactory.getLogger(WerkgelegenheidResource.class);

    private static final String ENTITY_NAME = "werkgelegenheid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WerkgelegenheidRepository werkgelegenheidRepository;

    public WerkgelegenheidResource(WerkgelegenheidRepository werkgelegenheidRepository) {
        this.werkgelegenheidRepository = werkgelegenheidRepository;
    }

    /**
     * {@code POST  /werkgelegenheids} : Create a new werkgelegenheid.
     *
     * @param werkgelegenheid the werkgelegenheid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new werkgelegenheid, or with status {@code 400 (Bad Request)} if the werkgelegenheid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Werkgelegenheid> createWerkgelegenheid(@RequestBody Werkgelegenheid werkgelegenheid) throws URISyntaxException {
        log.debug("REST request to save Werkgelegenheid : {}", werkgelegenheid);
        if (werkgelegenheid.getId() != null) {
            throw new BadRequestAlertException("A new werkgelegenheid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        werkgelegenheid = werkgelegenheidRepository.save(werkgelegenheid);
        return ResponseEntity.created(new URI("/api/werkgelegenheids/" + werkgelegenheid.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, werkgelegenheid.getId().toString()))
            .body(werkgelegenheid);
    }

    /**
     * {@code PUT  /werkgelegenheids/:id} : Updates an existing werkgelegenheid.
     *
     * @param id the id of the werkgelegenheid to save.
     * @param werkgelegenheid the werkgelegenheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated werkgelegenheid,
     * or with status {@code 400 (Bad Request)} if the werkgelegenheid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the werkgelegenheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Werkgelegenheid> updateWerkgelegenheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Werkgelegenheid werkgelegenheid
    ) throws URISyntaxException {
        log.debug("REST request to update Werkgelegenheid : {}, {}", id, werkgelegenheid);
        if (werkgelegenheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, werkgelegenheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!werkgelegenheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        werkgelegenheid = werkgelegenheidRepository.save(werkgelegenheid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, werkgelegenheid.getId().toString()))
            .body(werkgelegenheid);
    }

    /**
     * {@code PATCH  /werkgelegenheids/:id} : Partial updates given fields of an existing werkgelegenheid, field will ignore if it is null
     *
     * @param id the id of the werkgelegenheid to save.
     * @param werkgelegenheid the werkgelegenheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated werkgelegenheid,
     * or with status {@code 400 (Bad Request)} if the werkgelegenheid is not valid,
     * or with status {@code 404 (Not Found)} if the werkgelegenheid is not found,
     * or with status {@code 500 (Internal Server Error)} if the werkgelegenheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Werkgelegenheid> partialUpdateWerkgelegenheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Werkgelegenheid werkgelegenheid
    ) throws URISyntaxException {
        log.debug("REST request to partial update Werkgelegenheid partially : {}, {}", id, werkgelegenheid);
        if (werkgelegenheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, werkgelegenheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!werkgelegenheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Werkgelegenheid> result = werkgelegenheidRepository
            .findById(werkgelegenheid.getId())
            .map(existingWerkgelegenheid -> {
                if (werkgelegenheid.getAantalfulltimemannen() != null) {
                    existingWerkgelegenheid.setAantalfulltimemannen(werkgelegenheid.getAantalfulltimemannen());
                }
                if (werkgelegenheid.getAantalfulltimevrouwen() != null) {
                    existingWerkgelegenheid.setAantalfulltimevrouwen(werkgelegenheid.getAantalfulltimevrouwen());
                }
                if (werkgelegenheid.getAantalparttimemannen() != null) {
                    existingWerkgelegenheid.setAantalparttimemannen(werkgelegenheid.getAantalparttimemannen());
                }
                if (werkgelegenheid.getAantalparttimevrouwen() != null) {
                    existingWerkgelegenheid.setAantalparttimevrouwen(werkgelegenheid.getAantalparttimevrouwen());
                }
                if (werkgelegenheid.getGrootteklasse() != null) {
                    existingWerkgelegenheid.setGrootteklasse(werkgelegenheid.getGrootteklasse());
                }

                return existingWerkgelegenheid;
            })
            .map(werkgelegenheidRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, werkgelegenheid.getId().toString())
        );
    }

    /**
     * {@code GET  /werkgelegenheids} : get all the werkgelegenheids.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of werkgelegenheids in body.
     */
    @GetMapping("")
    public List<Werkgelegenheid> getAllWerkgelegenheids(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftvestiging-is-null".equals(filter)) {
            log.debug("REST request to get all Werkgelegenheids where heeftVestiging is null");
            return StreamSupport.stream(werkgelegenheidRepository.findAll().spliterator(), false)
                .filter(werkgelegenheid -> werkgelegenheid.getHeeftVestiging() == null)
                .toList();
        }
        log.debug("REST request to get all Werkgelegenheids");
        return werkgelegenheidRepository.findAll();
    }

    /**
     * {@code GET  /werkgelegenheids/:id} : get the "id" werkgelegenheid.
     *
     * @param id the id of the werkgelegenheid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the werkgelegenheid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Werkgelegenheid> getWerkgelegenheid(@PathVariable("id") Long id) {
        log.debug("REST request to get Werkgelegenheid : {}", id);
        Optional<Werkgelegenheid> werkgelegenheid = werkgelegenheidRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(werkgelegenheid);
    }

    /**
     * {@code DELETE  /werkgelegenheids/:id} : delete the "id" werkgelegenheid.
     *
     * @param id the id of the werkgelegenheid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWerkgelegenheid(@PathVariable("id") Long id) {
        log.debug("REST request to delete Werkgelegenheid : {}", id);
        werkgelegenheidRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
