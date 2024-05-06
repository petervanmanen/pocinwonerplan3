package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Opdrachtnemer;
import nl.ritense.demo.repository.OpdrachtnemerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Opdrachtnemer}.
 */
@RestController
@RequestMapping("/api/opdrachtnemers")
@Transactional
public class OpdrachtnemerResource {

    private final Logger log = LoggerFactory.getLogger(OpdrachtnemerResource.class);

    private static final String ENTITY_NAME = "opdrachtnemer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpdrachtnemerRepository opdrachtnemerRepository;

    public OpdrachtnemerResource(OpdrachtnemerRepository opdrachtnemerRepository) {
        this.opdrachtnemerRepository = opdrachtnemerRepository;
    }

    /**
     * {@code POST  /opdrachtnemers} : Create a new opdrachtnemer.
     *
     * @param opdrachtnemer the opdrachtnemer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opdrachtnemer, or with status {@code 400 (Bad Request)} if the opdrachtnemer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Opdrachtnemer> createOpdrachtnemer(@Valid @RequestBody Opdrachtnemer opdrachtnemer) throws URISyntaxException {
        log.debug("REST request to save Opdrachtnemer : {}", opdrachtnemer);
        if (opdrachtnemer.getId() != null) {
            throw new BadRequestAlertException("A new opdrachtnemer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opdrachtnemer = opdrachtnemerRepository.save(opdrachtnemer);
        return ResponseEntity.created(new URI("/api/opdrachtnemers/" + opdrachtnemer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, opdrachtnemer.getId().toString()))
            .body(opdrachtnemer);
    }

    /**
     * {@code PUT  /opdrachtnemers/:id} : Updates an existing opdrachtnemer.
     *
     * @param id the id of the opdrachtnemer to save.
     * @param opdrachtnemer the opdrachtnemer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opdrachtnemer,
     * or with status {@code 400 (Bad Request)} if the opdrachtnemer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opdrachtnemer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Opdrachtnemer> updateOpdrachtnemer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Opdrachtnemer opdrachtnemer
    ) throws URISyntaxException {
        log.debug("REST request to update Opdrachtnemer : {}, {}", id, opdrachtnemer);
        if (opdrachtnemer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opdrachtnemer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opdrachtnemerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        opdrachtnemer = opdrachtnemerRepository.save(opdrachtnemer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opdrachtnemer.getId().toString()))
            .body(opdrachtnemer);
    }

    /**
     * {@code PATCH  /opdrachtnemers/:id} : Partial updates given fields of an existing opdrachtnemer, field will ignore if it is null
     *
     * @param id the id of the opdrachtnemer to save.
     * @param opdrachtnemer the opdrachtnemer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opdrachtnemer,
     * or with status {@code 400 (Bad Request)} if the opdrachtnemer is not valid,
     * or with status {@code 404 (Not Found)} if the opdrachtnemer is not found,
     * or with status {@code 500 (Internal Server Error)} if the opdrachtnemer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Opdrachtnemer> partialUpdateOpdrachtnemer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Opdrachtnemer opdrachtnemer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Opdrachtnemer partially : {}, {}", id, opdrachtnemer);
        if (opdrachtnemer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opdrachtnemer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opdrachtnemerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Opdrachtnemer> result = opdrachtnemerRepository
            .findById(opdrachtnemer.getId())
            .map(existingOpdrachtnemer -> {
                if (opdrachtnemer.getClustercode() != null) {
                    existingOpdrachtnemer.setClustercode(opdrachtnemer.getClustercode());
                }
                if (opdrachtnemer.getClustercodeomschrijving() != null) {
                    existingOpdrachtnemer.setClustercodeomschrijving(opdrachtnemer.getClustercodeomschrijving());
                }
                if (opdrachtnemer.getNaam() != null) {
                    existingOpdrachtnemer.setNaam(opdrachtnemer.getNaam());
                }
                if (opdrachtnemer.getNummer() != null) {
                    existingOpdrachtnemer.setNummer(opdrachtnemer.getNummer());
                }
                if (opdrachtnemer.getOmschrijving() != null) {
                    existingOpdrachtnemer.setOmschrijving(opdrachtnemer.getOmschrijving());
                }

                return existingOpdrachtnemer;
            })
            .map(opdrachtnemerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opdrachtnemer.getId().toString())
        );
    }

    /**
     * {@code GET  /opdrachtnemers} : get all the opdrachtnemers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opdrachtnemers in body.
     */
    @GetMapping("")
    public List<Opdrachtnemer> getAllOpdrachtnemers() {
        log.debug("REST request to get all Opdrachtnemers");
        return opdrachtnemerRepository.findAll();
    }

    /**
     * {@code GET  /opdrachtnemers/:id} : get the "id" opdrachtnemer.
     *
     * @param id the id of the opdrachtnemer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opdrachtnemer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Opdrachtnemer> getOpdrachtnemer(@PathVariable("id") Long id) {
        log.debug("REST request to get Opdrachtnemer : {}", id);
        Optional<Opdrachtnemer> opdrachtnemer = opdrachtnemerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(opdrachtnemer);
    }

    /**
     * {@code DELETE  /opdrachtnemers/:id} : delete the "id" opdrachtnemer.
     *
     * @param id the id of the opdrachtnemer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpdrachtnemer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Opdrachtnemer : {}", id);
        opdrachtnemerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
