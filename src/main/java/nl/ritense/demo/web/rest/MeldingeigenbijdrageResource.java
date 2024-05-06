package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Meldingeigenbijdrage;
import nl.ritense.demo.repository.MeldingeigenbijdrageRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Meldingeigenbijdrage}.
 */
@RestController
@RequestMapping("/api/meldingeigenbijdrages")
@Transactional
public class MeldingeigenbijdrageResource {

    private final Logger log = LoggerFactory.getLogger(MeldingeigenbijdrageResource.class);

    private static final String ENTITY_NAME = "meldingeigenbijdrage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeldingeigenbijdrageRepository meldingeigenbijdrageRepository;

    public MeldingeigenbijdrageResource(MeldingeigenbijdrageRepository meldingeigenbijdrageRepository) {
        this.meldingeigenbijdrageRepository = meldingeigenbijdrageRepository;
    }

    /**
     * {@code POST  /meldingeigenbijdrages} : Create a new meldingeigenbijdrage.
     *
     * @param meldingeigenbijdrage the meldingeigenbijdrage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meldingeigenbijdrage, or with status {@code 400 (Bad Request)} if the meldingeigenbijdrage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Meldingeigenbijdrage> createMeldingeigenbijdrage(@RequestBody Meldingeigenbijdrage meldingeigenbijdrage)
        throws URISyntaxException {
        log.debug("REST request to save Meldingeigenbijdrage : {}", meldingeigenbijdrage);
        if (meldingeigenbijdrage.getId() != null) {
            throw new BadRequestAlertException("A new meldingeigenbijdrage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        meldingeigenbijdrage = meldingeigenbijdrageRepository.save(meldingeigenbijdrage);
        return ResponseEntity.created(new URI("/api/meldingeigenbijdrages/" + meldingeigenbijdrage.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, meldingeigenbijdrage.getId().toString()))
            .body(meldingeigenbijdrage);
    }

    /**
     * {@code PUT  /meldingeigenbijdrages/:id} : Updates an existing meldingeigenbijdrage.
     *
     * @param id the id of the meldingeigenbijdrage to save.
     * @param meldingeigenbijdrage the meldingeigenbijdrage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meldingeigenbijdrage,
     * or with status {@code 400 (Bad Request)} if the meldingeigenbijdrage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meldingeigenbijdrage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Meldingeigenbijdrage> updateMeldingeigenbijdrage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Meldingeigenbijdrage meldingeigenbijdrage
    ) throws URISyntaxException {
        log.debug("REST request to update Meldingeigenbijdrage : {}, {}", id, meldingeigenbijdrage);
        if (meldingeigenbijdrage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, meldingeigenbijdrage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!meldingeigenbijdrageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        meldingeigenbijdrage = meldingeigenbijdrageRepository.save(meldingeigenbijdrage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, meldingeigenbijdrage.getId().toString()))
            .body(meldingeigenbijdrage);
    }

    /**
     * {@code PATCH  /meldingeigenbijdrages/:id} : Partial updates given fields of an existing meldingeigenbijdrage, field will ignore if it is null
     *
     * @param id the id of the meldingeigenbijdrage to save.
     * @param meldingeigenbijdrage the meldingeigenbijdrage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meldingeigenbijdrage,
     * or with status {@code 400 (Bad Request)} if the meldingeigenbijdrage is not valid,
     * or with status {@code 404 (Not Found)} if the meldingeigenbijdrage is not found,
     * or with status {@code 500 (Internal Server Error)} if the meldingeigenbijdrage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Meldingeigenbijdrage> partialUpdateMeldingeigenbijdrage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Meldingeigenbijdrage meldingeigenbijdrage
    ) throws URISyntaxException {
        log.debug("REST request to partial update Meldingeigenbijdrage partially : {}, {}", id, meldingeigenbijdrage);
        if (meldingeigenbijdrage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, meldingeigenbijdrage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!meldingeigenbijdrageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Meldingeigenbijdrage> result = meldingeigenbijdrageRepository
            .findById(meldingeigenbijdrage.getId())
            .map(existingMeldingeigenbijdrage -> {
                if (meldingeigenbijdrage.getDatumstart() != null) {
                    existingMeldingeigenbijdrage.setDatumstart(meldingeigenbijdrage.getDatumstart());
                }
                if (meldingeigenbijdrage.getDatumstop() != null) {
                    existingMeldingeigenbijdrage.setDatumstop(meldingeigenbijdrage.getDatumstop());
                }

                return existingMeldingeigenbijdrage;
            })
            .map(meldingeigenbijdrageRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, meldingeigenbijdrage.getId().toString())
        );
    }

    /**
     * {@code GET  /meldingeigenbijdrages} : get all the meldingeigenbijdrages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meldingeigenbijdrages in body.
     */
    @GetMapping("")
    public List<Meldingeigenbijdrage> getAllMeldingeigenbijdrages() {
        log.debug("REST request to get all Meldingeigenbijdrages");
        return meldingeigenbijdrageRepository.findAll();
    }

    /**
     * {@code GET  /meldingeigenbijdrages/:id} : get the "id" meldingeigenbijdrage.
     *
     * @param id the id of the meldingeigenbijdrage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meldingeigenbijdrage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Meldingeigenbijdrage> getMeldingeigenbijdrage(@PathVariable("id") Long id) {
        log.debug("REST request to get Meldingeigenbijdrage : {}", id);
        Optional<Meldingeigenbijdrage> meldingeigenbijdrage = meldingeigenbijdrageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(meldingeigenbijdrage);
    }

    /**
     * {@code DELETE  /meldingeigenbijdrages/:id} : delete the "id" meldingeigenbijdrage.
     *
     * @param id the id of the meldingeigenbijdrage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeldingeigenbijdrage(@PathVariable("id") Long id) {
        log.debug("REST request to delete Meldingeigenbijdrage : {}", id);
        meldingeigenbijdrageRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
