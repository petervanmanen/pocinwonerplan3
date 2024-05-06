package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Voorziening;
import nl.ritense.demo.repository.VoorzieningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Voorziening}.
 */
@RestController
@RequestMapping("/api/voorzienings")
@Transactional
public class VoorzieningResource {

    private final Logger log = LoggerFactory.getLogger(VoorzieningResource.class);

    private static final String ENTITY_NAME = "voorziening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoorzieningRepository voorzieningRepository;

    public VoorzieningResource(VoorzieningRepository voorzieningRepository) {
        this.voorzieningRepository = voorzieningRepository;
    }

    /**
     * {@code POST  /voorzienings} : Create a new voorziening.
     *
     * @param voorziening the voorziening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voorziening, or with status {@code 400 (Bad Request)} if the voorziening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Voorziening> createVoorziening(@Valid @RequestBody Voorziening voorziening) throws URISyntaxException {
        log.debug("REST request to save Voorziening : {}", voorziening);
        if (voorziening.getId() != null) {
            throw new BadRequestAlertException("A new voorziening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        voorziening = voorzieningRepository.save(voorziening);
        return ResponseEntity.created(new URI("/api/voorzienings/" + voorziening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, voorziening.getId().toString()))
            .body(voorziening);
    }

    /**
     * {@code PUT  /voorzienings/:id} : Updates an existing voorziening.
     *
     * @param id the id of the voorziening to save.
     * @param voorziening the voorziening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voorziening,
     * or with status {@code 400 (Bad Request)} if the voorziening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voorziening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Voorziening> updateVoorziening(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Voorziening voorziening
    ) throws URISyntaxException {
        log.debug("REST request to update Voorziening : {}, {}", id, voorziening);
        if (voorziening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voorziening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voorzieningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        voorziening = voorzieningRepository.save(voorziening);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voorziening.getId().toString()))
            .body(voorziening);
    }

    /**
     * {@code PATCH  /voorzienings/:id} : Partial updates given fields of an existing voorziening, field will ignore if it is null
     *
     * @param id the id of the voorziening to save.
     * @param voorziening the voorziening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voorziening,
     * or with status {@code 400 (Bad Request)} if the voorziening is not valid,
     * or with status {@code 404 (Not Found)} if the voorziening is not found,
     * or with status {@code 500 (Internal Server Error)} if the voorziening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Voorziening> partialUpdateVoorziening(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Voorziening voorziening
    ) throws URISyntaxException {
        log.debug("REST request to partial update Voorziening partially : {}, {}", id, voorziening);
        if (voorziening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voorziening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voorzieningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Voorziening> result = voorzieningRepository
            .findById(voorziening.getId())
            .map(existingVoorziening -> {
                if (voorziening.getAantalbeschikbaar() != null) {
                    existingVoorziening.setAantalbeschikbaar(voorziening.getAantalbeschikbaar());
                }
                if (voorziening.getNaam() != null) {
                    existingVoorziening.setNaam(voorziening.getNaam());
                }
                if (voorziening.getOmschrijving() != null) {
                    existingVoorziening.setOmschrijving(voorziening.getOmschrijving());
                }

                return existingVoorziening;
            })
            .map(voorzieningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voorziening.getId().toString())
        );
    }

    /**
     * {@code GET  /voorzienings} : get all the voorzienings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voorzienings in body.
     */
    @GetMapping("")
    public List<Voorziening> getAllVoorzienings() {
        log.debug("REST request to get all Voorzienings");
        return voorzieningRepository.findAll();
    }

    /**
     * {@code GET  /voorzienings/:id} : get the "id" voorziening.
     *
     * @param id the id of the voorziening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voorziening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Voorziening> getVoorziening(@PathVariable("id") Long id) {
        log.debug("REST request to get Voorziening : {}", id);
        Optional<Voorziening> voorziening = voorzieningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(voorziening);
    }

    /**
     * {@code DELETE  /voorzienings/:id} : delete the "id" voorziening.
     *
     * @param id the id of the voorziening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoorziening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Voorziening : {}", id);
        voorzieningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
