package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vindplaats;
import nl.ritense.demo.repository.VindplaatsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vindplaats}.
 */
@RestController
@RequestMapping("/api/vindplaats")
@Transactional
public class VindplaatsResource {

    private final Logger log = LoggerFactory.getLogger(VindplaatsResource.class);

    private static final String ENTITY_NAME = "vindplaats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VindplaatsRepository vindplaatsRepository;

    public VindplaatsResource(VindplaatsRepository vindplaatsRepository) {
        this.vindplaatsRepository = vindplaatsRepository;
    }

    /**
     * {@code POST  /vindplaats} : Create a new vindplaats.
     *
     * @param vindplaats the vindplaats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vindplaats, or with status {@code 400 (Bad Request)} if the vindplaats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vindplaats> createVindplaats(@Valid @RequestBody Vindplaats vindplaats) throws URISyntaxException {
        log.debug("REST request to save Vindplaats : {}", vindplaats);
        if (vindplaats.getId() != null) {
            throw new BadRequestAlertException("A new vindplaats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vindplaats = vindplaatsRepository.save(vindplaats);
        return ResponseEntity.created(new URI("/api/vindplaats/" + vindplaats.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vindplaats.getId().toString()))
            .body(vindplaats);
    }

    /**
     * {@code PUT  /vindplaats/:id} : Updates an existing vindplaats.
     *
     * @param id the id of the vindplaats to save.
     * @param vindplaats the vindplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vindplaats,
     * or with status {@code 400 (Bad Request)} if the vindplaats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vindplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vindplaats> updateVindplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vindplaats vindplaats
    ) throws URISyntaxException {
        log.debug("REST request to update Vindplaats : {}, {}", id, vindplaats);
        if (vindplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vindplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vindplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vindplaats = vindplaatsRepository.save(vindplaats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vindplaats.getId().toString()))
            .body(vindplaats);
    }

    /**
     * {@code PATCH  /vindplaats/:id} : Partial updates given fields of an existing vindplaats, field will ignore if it is null
     *
     * @param id the id of the vindplaats to save.
     * @param vindplaats the vindplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vindplaats,
     * or with status {@code 400 (Bad Request)} if the vindplaats is not valid,
     * or with status {@code 404 (Not Found)} if the vindplaats is not found,
     * or with status {@code 500 (Internal Server Error)} if the vindplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vindplaats> partialUpdateVindplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vindplaats vindplaats
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vindplaats partially : {}, {}", id, vindplaats);
        if (vindplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vindplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vindplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vindplaats> result = vindplaatsRepository
            .findById(vindplaats.getId())
            .map(existingVindplaats -> {
                if (vindplaats.getAard() != null) {
                    existingVindplaats.setAard(vindplaats.getAard());
                }
                if (vindplaats.getBegindatering() != null) {
                    existingVindplaats.setBegindatering(vindplaats.getBegindatering());
                }
                if (vindplaats.getBeschrijving() != null) {
                    existingVindplaats.setBeschrijving(vindplaats.getBeschrijving());
                }
                if (vindplaats.getBibliografie() != null) {
                    existingVindplaats.setBibliografie(vindplaats.getBibliografie());
                }
                if (vindplaats.getDatering() != null) {
                    existingVindplaats.setDatering(vindplaats.getDatering());
                }
                if (vindplaats.getDepot() != null) {
                    existingVindplaats.setDepot(vindplaats.getDepot());
                }
                if (vindplaats.getDocumentatie() != null) {
                    existingVindplaats.setDocumentatie(vindplaats.getDocumentatie());
                }
                if (vindplaats.getEinddatering() != null) {
                    existingVindplaats.setEinddatering(vindplaats.getEinddatering());
                }
                if (vindplaats.getGemeente() != null) {
                    existingVindplaats.setGemeente(vindplaats.getGemeente());
                }
                if (vindplaats.getLocatie() != null) {
                    existingVindplaats.setLocatie(vindplaats.getLocatie());
                }
                if (vindplaats.getMobilia() != null) {
                    existingVindplaats.setMobilia(vindplaats.getMobilia());
                }
                if (vindplaats.getOnderzoek() != null) {
                    existingVindplaats.setOnderzoek(vindplaats.getOnderzoek());
                }
                if (vindplaats.getProjectcode() != null) {
                    existingVindplaats.setProjectcode(vindplaats.getProjectcode());
                }
                if (vindplaats.getVindplaats() != null) {
                    existingVindplaats.setVindplaats(vindplaats.getVindplaats());
                }

                return existingVindplaats;
            })
            .map(vindplaatsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vindplaats.getId().toString())
        );
    }

    /**
     * {@code GET  /vindplaats} : get all the vindplaats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vindplaats in body.
     */
    @GetMapping("")
    public List<Vindplaats> getAllVindplaats() {
        log.debug("REST request to get all Vindplaats");
        return vindplaatsRepository.findAll();
    }

    /**
     * {@code GET  /vindplaats/:id} : get the "id" vindplaats.
     *
     * @param id the id of the vindplaats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vindplaats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vindplaats> getVindplaats(@PathVariable("id") Long id) {
        log.debug("REST request to get Vindplaats : {}", id);
        Optional<Vindplaats> vindplaats = vindplaatsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vindplaats);
    }

    /**
     * {@code DELETE  /vindplaats/:id} : delete the "id" vindplaats.
     *
     * @param id the id of the vindplaats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVindplaats(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vindplaats : {}", id);
        vindplaatsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
