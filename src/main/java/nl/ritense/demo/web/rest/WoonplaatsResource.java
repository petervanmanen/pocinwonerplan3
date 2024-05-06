package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Woonplaats;
import nl.ritense.demo.repository.WoonplaatsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Woonplaats}.
 */
@RestController
@RequestMapping("/api/woonplaats")
@Transactional
public class WoonplaatsResource {

    private final Logger log = LoggerFactory.getLogger(WoonplaatsResource.class);

    private static final String ENTITY_NAME = "woonplaats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WoonplaatsRepository woonplaatsRepository;

    public WoonplaatsResource(WoonplaatsRepository woonplaatsRepository) {
        this.woonplaatsRepository = woonplaatsRepository;
    }

    /**
     * {@code POST  /woonplaats} : Create a new woonplaats.
     *
     * @param woonplaats the woonplaats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new woonplaats, or with status {@code 400 (Bad Request)} if the woonplaats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Woonplaats> createWoonplaats(@Valid @RequestBody Woonplaats woonplaats) throws URISyntaxException {
        log.debug("REST request to save Woonplaats : {}", woonplaats);
        if (woonplaats.getId() != null) {
            throw new BadRequestAlertException("A new woonplaats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        woonplaats = woonplaatsRepository.save(woonplaats);
        return ResponseEntity.created(new URI("/api/woonplaats/" + woonplaats.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, woonplaats.getId().toString()))
            .body(woonplaats);
    }

    /**
     * {@code PUT  /woonplaats/:id} : Updates an existing woonplaats.
     *
     * @param id the id of the woonplaats to save.
     * @param woonplaats the woonplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated woonplaats,
     * or with status {@code 400 (Bad Request)} if the woonplaats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the woonplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Woonplaats> updateWoonplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Woonplaats woonplaats
    ) throws URISyntaxException {
        log.debug("REST request to update Woonplaats : {}, {}", id, woonplaats);
        if (woonplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, woonplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!woonplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        woonplaats = woonplaatsRepository.save(woonplaats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, woonplaats.getId().toString()))
            .body(woonplaats);
    }

    /**
     * {@code PATCH  /woonplaats/:id} : Partial updates given fields of an existing woonplaats, field will ignore if it is null
     *
     * @param id the id of the woonplaats to save.
     * @param woonplaats the woonplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated woonplaats,
     * or with status {@code 400 (Bad Request)} if the woonplaats is not valid,
     * or with status {@code 404 (Not Found)} if the woonplaats is not found,
     * or with status {@code 500 (Internal Server Error)} if the woonplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Woonplaats> partialUpdateWoonplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Woonplaats woonplaats
    ) throws URISyntaxException {
        log.debug("REST request to partial update Woonplaats partially : {}, {}", id, woonplaats);
        if (woonplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, woonplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!woonplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Woonplaats> result = woonplaatsRepository
            .findById(woonplaats.getId())
            .map(existingWoonplaats -> {
                if (woonplaats.getDatumbegingeldigheid() != null) {
                    existingWoonplaats.setDatumbegingeldigheid(woonplaats.getDatumbegingeldigheid());
                }
                if (woonplaats.getDatumeinde() != null) {
                    existingWoonplaats.setDatumeinde(woonplaats.getDatumeinde());
                }
                if (woonplaats.getDatumeindegeldigheid() != null) {
                    existingWoonplaats.setDatumeindegeldigheid(woonplaats.getDatumeindegeldigheid());
                }
                if (woonplaats.getDatumingang() != null) {
                    existingWoonplaats.setDatumingang(woonplaats.getDatumingang());
                }
                if (woonplaats.getGeconstateerd() != null) {
                    existingWoonplaats.setGeconstateerd(woonplaats.getGeconstateerd());
                }
                if (woonplaats.getGeometrie() != null) {
                    existingWoonplaats.setGeometrie(woonplaats.getGeometrie());
                }
                if (woonplaats.getIdentificatie() != null) {
                    existingWoonplaats.setIdentificatie(woonplaats.getIdentificatie());
                }
                if (woonplaats.getInonderzoek() != null) {
                    existingWoonplaats.setInonderzoek(woonplaats.getInonderzoek());
                }
                if (woonplaats.getStatus() != null) {
                    existingWoonplaats.setStatus(woonplaats.getStatus());
                }
                if (woonplaats.getVersie() != null) {
                    existingWoonplaats.setVersie(woonplaats.getVersie());
                }
                if (woonplaats.getWoonplaatsnaam() != null) {
                    existingWoonplaats.setWoonplaatsnaam(woonplaats.getWoonplaatsnaam());
                }
                if (woonplaats.getWoonplaatsnaamnen() != null) {
                    existingWoonplaats.setWoonplaatsnaamnen(woonplaats.getWoonplaatsnaamnen());
                }

                return existingWoonplaats;
            })
            .map(woonplaatsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, woonplaats.getId().toString())
        );
    }

    /**
     * {@code GET  /woonplaats} : get all the woonplaats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of woonplaats in body.
     */
    @GetMapping("")
    public List<Woonplaats> getAllWoonplaats() {
        log.debug("REST request to get all Woonplaats");
        return woonplaatsRepository.findAll();
    }

    /**
     * {@code GET  /woonplaats/:id} : get the "id" woonplaats.
     *
     * @param id the id of the woonplaats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the woonplaats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Woonplaats> getWoonplaats(@PathVariable("id") Long id) {
        log.debug("REST request to get Woonplaats : {}", id);
        Optional<Woonplaats> woonplaats = woonplaatsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(woonplaats);
    }

    /**
     * {@code DELETE  /woonplaats/:id} : delete the "id" woonplaats.
     *
     * @param id the id of the woonplaats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWoonplaats(@PathVariable("id") Long id) {
        log.debug("REST request to delete Woonplaats : {}", id);
        woonplaatsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
