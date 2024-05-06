package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ligplaats;
import nl.ritense.demo.repository.LigplaatsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ligplaats}.
 */
@RestController
@RequestMapping("/api/ligplaats")
@Transactional
public class LigplaatsResource {

    private final Logger log = LoggerFactory.getLogger(LigplaatsResource.class);

    private static final String ENTITY_NAME = "ligplaats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigplaatsRepository ligplaatsRepository;

    public LigplaatsResource(LigplaatsRepository ligplaatsRepository) {
        this.ligplaatsRepository = ligplaatsRepository;
    }

    /**
     * {@code POST  /ligplaats} : Create a new ligplaats.
     *
     * @param ligplaats the ligplaats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligplaats, or with status {@code 400 (Bad Request)} if the ligplaats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ligplaats> createLigplaats(@RequestBody Ligplaats ligplaats) throws URISyntaxException {
        log.debug("REST request to save Ligplaats : {}", ligplaats);
        if (ligplaats.getId() != null) {
            throw new BadRequestAlertException("A new ligplaats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ligplaats = ligplaatsRepository.save(ligplaats);
        return ResponseEntity.created(new URI("/api/ligplaats/" + ligplaats.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ligplaats.getId().toString()))
            .body(ligplaats);
    }

    /**
     * {@code PUT  /ligplaats/:id} : Updates an existing ligplaats.
     *
     * @param id the id of the ligplaats to save.
     * @param ligplaats the ligplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligplaats,
     * or with status {@code 400 (Bad Request)} if the ligplaats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ligplaats> updateLigplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ligplaats ligplaats
    ) throws URISyntaxException {
        log.debug("REST request to update Ligplaats : {}, {}", id, ligplaats);
        if (ligplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ligplaats = ligplaatsRepository.save(ligplaats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ligplaats.getId().toString()))
            .body(ligplaats);
    }

    /**
     * {@code PATCH  /ligplaats/:id} : Partial updates given fields of an existing ligplaats, field will ignore if it is null
     *
     * @param id the id of the ligplaats to save.
     * @param ligplaats the ligplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligplaats,
     * or with status {@code 400 (Bad Request)} if the ligplaats is not valid,
     * or with status {@code 404 (Not Found)} if the ligplaats is not found,
     * or with status {@code 500 (Internal Server Error)} if the ligplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ligplaats> partialUpdateLigplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ligplaats ligplaats
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ligplaats partially : {}, {}", id, ligplaats);
        if (ligplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ligplaats> result = ligplaatsRepository
            .findById(ligplaats.getId())
            .map(existingLigplaats -> {
                if (ligplaats.getDatumbegingeldigheid() != null) {
                    existingLigplaats.setDatumbegingeldigheid(ligplaats.getDatumbegingeldigheid());
                }
                if (ligplaats.getDatumeinde() != null) {
                    existingLigplaats.setDatumeinde(ligplaats.getDatumeinde());
                }
                if (ligplaats.getDatumeindegeldigheid() != null) {
                    existingLigplaats.setDatumeindegeldigheid(ligplaats.getDatumeindegeldigheid());
                }
                if (ligplaats.getDatumingang() != null) {
                    existingLigplaats.setDatumingang(ligplaats.getDatumingang());
                }
                if (ligplaats.getDocumentdatum() != null) {
                    existingLigplaats.setDocumentdatum(ligplaats.getDocumentdatum());
                }
                if (ligplaats.getDocumentnummer() != null) {
                    existingLigplaats.setDocumentnummer(ligplaats.getDocumentnummer());
                }
                if (ligplaats.getGeconstateerd() != null) {
                    existingLigplaats.setGeconstateerd(ligplaats.getGeconstateerd());
                }
                if (ligplaats.getGeometrie() != null) {
                    existingLigplaats.setGeometrie(ligplaats.getGeometrie());
                }
                if (ligplaats.getIdentificatie() != null) {
                    existingLigplaats.setIdentificatie(ligplaats.getIdentificatie());
                }
                if (ligplaats.getInonderzoek() != null) {
                    existingLigplaats.setInonderzoek(ligplaats.getInonderzoek());
                }
                if (ligplaats.getStatus() != null) {
                    existingLigplaats.setStatus(ligplaats.getStatus());
                }
                if (ligplaats.getVersie() != null) {
                    existingLigplaats.setVersie(ligplaats.getVersie());
                }

                return existingLigplaats;
            })
            .map(ligplaatsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ligplaats.getId().toString())
        );
    }

    /**
     * {@code GET  /ligplaats} : get all the ligplaats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligplaats in body.
     */
    @GetMapping("")
    public List<Ligplaats> getAllLigplaats() {
        log.debug("REST request to get all Ligplaats");
        return ligplaatsRepository.findAll();
    }

    /**
     * {@code GET  /ligplaats/:id} : get the "id" ligplaats.
     *
     * @param id the id of the ligplaats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligplaats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ligplaats> getLigplaats(@PathVariable("id") Long id) {
        log.debug("REST request to get Ligplaats : {}", id);
        Optional<Ligplaats> ligplaats = ligplaatsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ligplaats);
    }

    /**
     * {@code DELETE  /ligplaats/:id} : delete the "id" ligplaats.
     *
     * @param id the id of the ligplaats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigplaats(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ligplaats : {}", id);
        ligplaatsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
