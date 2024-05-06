package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Werknemer;
import nl.ritense.demo.repository.WerknemerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Werknemer}.
 */
@RestController
@RequestMapping("/api/werknemers")
@Transactional
public class WerknemerResource {

    private final Logger log = LoggerFactory.getLogger(WerknemerResource.class);

    private static final String ENTITY_NAME = "werknemer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WerknemerRepository werknemerRepository;

    public WerknemerResource(WerknemerRepository werknemerRepository) {
        this.werknemerRepository = werknemerRepository;
    }

    /**
     * {@code POST  /werknemers} : Create a new werknemer.
     *
     * @param werknemer the werknemer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new werknemer, or with status {@code 400 (Bad Request)} if the werknemer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Werknemer> createWerknemer(@Valid @RequestBody Werknemer werknemer) throws URISyntaxException {
        log.debug("REST request to save Werknemer : {}", werknemer);
        if (werknemer.getId() != null) {
            throw new BadRequestAlertException("A new werknemer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        werknemer = werknemerRepository.save(werknemer);
        return ResponseEntity.created(new URI("/api/werknemers/" + werknemer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, werknemer.getId().toString()))
            .body(werknemer);
    }

    /**
     * {@code PUT  /werknemers/:id} : Updates an existing werknemer.
     *
     * @param id the id of the werknemer to save.
     * @param werknemer the werknemer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated werknemer,
     * or with status {@code 400 (Bad Request)} if the werknemer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the werknemer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Werknemer> updateWerknemer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Werknemer werknemer
    ) throws URISyntaxException {
        log.debug("REST request to update Werknemer : {}, {}", id, werknemer);
        if (werknemer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, werknemer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!werknemerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        werknemer = werknemerRepository.save(werknemer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, werknemer.getId().toString()))
            .body(werknemer);
    }

    /**
     * {@code PATCH  /werknemers/:id} : Partial updates given fields of an existing werknemer, field will ignore if it is null
     *
     * @param id the id of the werknemer to save.
     * @param werknemer the werknemer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated werknemer,
     * or with status {@code 400 (Bad Request)} if the werknemer is not valid,
     * or with status {@code 404 (Not Found)} if the werknemer is not found,
     * or with status {@code 500 (Internal Server Error)} if the werknemer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Werknemer> partialUpdateWerknemer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Werknemer werknemer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Werknemer partially : {}, {}", id, werknemer);
        if (werknemer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, werknemer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!werknemerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Werknemer> result = werknemerRepository
            .findById(werknemer.getId())
            .map(existingWerknemer -> {
                if (werknemer.getGeboortedatum() != null) {
                    existingWerknemer.setGeboortedatum(werknemer.getGeboortedatum());
                }
                if (werknemer.getNaam() != null) {
                    existingWerknemer.setNaam(werknemer.getNaam());
                }
                if (werknemer.getVoornaam() != null) {
                    existingWerknemer.setVoornaam(werknemer.getVoornaam());
                }
                if (werknemer.getWoonplaats() != null) {
                    existingWerknemer.setWoonplaats(werknemer.getWoonplaats());
                }

                return existingWerknemer;
            })
            .map(werknemerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, werknemer.getId().toString())
        );
    }

    /**
     * {@code GET  /werknemers} : get all the werknemers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of werknemers in body.
     */
    @GetMapping("")
    public List<Werknemer> getAllWerknemers(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Werknemers");
        if (eagerload) {
            return werknemerRepository.findAllWithEagerRelationships();
        } else {
            return werknemerRepository.findAll();
        }
    }

    /**
     * {@code GET  /werknemers/:id} : get the "id" werknemer.
     *
     * @param id the id of the werknemer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the werknemer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Werknemer> getWerknemer(@PathVariable("id") Long id) {
        log.debug("REST request to get Werknemer : {}", id);
        Optional<Werknemer> werknemer = werknemerRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(werknemer);
    }

    /**
     * {@code DELETE  /werknemers/:id} : delete the "id" werknemer.
     *
     * @param id the id of the werknemer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWerknemer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Werknemer : {}", id);
        werknemerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
