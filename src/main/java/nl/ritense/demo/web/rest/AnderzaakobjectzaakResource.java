package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Anderzaakobjectzaak;
import nl.ritense.demo.repository.AnderzaakobjectzaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Anderzaakobjectzaak}.
 */
@RestController
@RequestMapping("/api/anderzaakobjectzaaks")
@Transactional
public class AnderzaakobjectzaakResource {

    private final Logger log = LoggerFactory.getLogger(AnderzaakobjectzaakResource.class);

    private static final String ENTITY_NAME = "anderzaakobjectzaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnderzaakobjectzaakRepository anderzaakobjectzaakRepository;

    public AnderzaakobjectzaakResource(AnderzaakobjectzaakRepository anderzaakobjectzaakRepository) {
        this.anderzaakobjectzaakRepository = anderzaakobjectzaakRepository;
    }

    /**
     * {@code POST  /anderzaakobjectzaaks} : Create a new anderzaakobjectzaak.
     *
     * @param anderzaakobjectzaak the anderzaakobjectzaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anderzaakobjectzaak, or with status {@code 400 (Bad Request)} if the anderzaakobjectzaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Anderzaakobjectzaak> createAnderzaakobjectzaak(@RequestBody Anderzaakobjectzaak anderzaakobjectzaak)
        throws URISyntaxException {
        log.debug("REST request to save Anderzaakobjectzaak : {}", anderzaakobjectzaak);
        if (anderzaakobjectzaak.getId() != null) {
            throw new BadRequestAlertException("A new anderzaakobjectzaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anderzaakobjectzaak = anderzaakobjectzaakRepository.save(anderzaakobjectzaak);
        return ResponseEntity.created(new URI("/api/anderzaakobjectzaaks/" + anderzaakobjectzaak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, anderzaakobjectzaak.getId().toString()))
            .body(anderzaakobjectzaak);
    }

    /**
     * {@code PUT  /anderzaakobjectzaaks/:id} : Updates an existing anderzaakobjectzaak.
     *
     * @param id the id of the anderzaakobjectzaak to save.
     * @param anderzaakobjectzaak the anderzaakobjectzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anderzaakobjectzaak,
     * or with status {@code 400 (Bad Request)} if the anderzaakobjectzaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anderzaakobjectzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Anderzaakobjectzaak> updateAnderzaakobjectzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Anderzaakobjectzaak anderzaakobjectzaak
    ) throws URISyntaxException {
        log.debug("REST request to update Anderzaakobjectzaak : {}, {}", id, anderzaakobjectzaak);
        if (anderzaakobjectzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anderzaakobjectzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anderzaakobjectzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anderzaakobjectzaak = anderzaakobjectzaakRepository.save(anderzaakobjectzaak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anderzaakobjectzaak.getId().toString()))
            .body(anderzaakobjectzaak);
    }

    /**
     * {@code PATCH  /anderzaakobjectzaaks/:id} : Partial updates given fields of an existing anderzaakobjectzaak, field will ignore if it is null
     *
     * @param id the id of the anderzaakobjectzaak to save.
     * @param anderzaakobjectzaak the anderzaakobjectzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anderzaakobjectzaak,
     * or with status {@code 400 (Bad Request)} if the anderzaakobjectzaak is not valid,
     * or with status {@code 404 (Not Found)} if the anderzaakobjectzaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the anderzaakobjectzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Anderzaakobjectzaak> partialUpdateAnderzaakobjectzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Anderzaakobjectzaak anderzaakobjectzaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Anderzaakobjectzaak partially : {}, {}", id, anderzaakobjectzaak);
        if (anderzaakobjectzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anderzaakobjectzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anderzaakobjectzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Anderzaakobjectzaak> result = anderzaakobjectzaakRepository
            .findById(anderzaakobjectzaak.getId())
            .map(existingAnderzaakobjectzaak -> {
                if (anderzaakobjectzaak.getAnderzaakobjectaanduiding() != null) {
                    existingAnderzaakobjectzaak.setAnderzaakobjectaanduiding(anderzaakobjectzaak.getAnderzaakobjectaanduiding());
                }
                if (anderzaakobjectzaak.getAnderzaakobjectlocatie() != null) {
                    existingAnderzaakobjectzaak.setAnderzaakobjectlocatie(anderzaakobjectzaak.getAnderzaakobjectlocatie());
                }
                if (anderzaakobjectzaak.getAnderzaakobjectomschrijving() != null) {
                    existingAnderzaakobjectzaak.setAnderzaakobjectomschrijving(anderzaakobjectzaak.getAnderzaakobjectomschrijving());
                }
                if (anderzaakobjectzaak.getAnderzaakobjectregistratie() != null) {
                    existingAnderzaakobjectzaak.setAnderzaakobjectregistratie(anderzaakobjectzaak.getAnderzaakobjectregistratie());
                }

                return existingAnderzaakobjectzaak;
            })
            .map(anderzaakobjectzaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anderzaakobjectzaak.getId().toString())
        );
    }

    /**
     * {@code GET  /anderzaakobjectzaaks} : get all the anderzaakobjectzaaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anderzaakobjectzaaks in body.
     */
    @GetMapping("")
    public List<Anderzaakobjectzaak> getAllAnderzaakobjectzaaks() {
        log.debug("REST request to get all Anderzaakobjectzaaks");
        return anderzaakobjectzaakRepository.findAll();
    }

    /**
     * {@code GET  /anderzaakobjectzaaks/:id} : get the "id" anderzaakobjectzaak.
     *
     * @param id the id of the anderzaakobjectzaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anderzaakobjectzaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Anderzaakobjectzaak> getAnderzaakobjectzaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Anderzaakobjectzaak : {}", id);
        Optional<Anderzaakobjectzaak> anderzaakobjectzaak = anderzaakobjectzaakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(anderzaakobjectzaak);
    }

    /**
     * {@code DELETE  /anderzaakobjectzaaks/:id} : delete the "id" anderzaakobjectzaak.
     *
     * @param id the id of the anderzaakobjectzaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnderzaakobjectzaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Anderzaakobjectzaak : {}", id);
        anderzaakobjectzaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
