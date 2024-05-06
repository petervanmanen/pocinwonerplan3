package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Dienstverband;
import nl.ritense.demo.repository.DienstverbandRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Dienstverband}.
 */
@RestController
@RequestMapping("/api/dienstverbands")
@Transactional
public class DienstverbandResource {

    private final Logger log = LoggerFactory.getLogger(DienstverbandResource.class);

    private static final String ENTITY_NAME = "dienstverband";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DienstverbandRepository dienstverbandRepository;

    public DienstverbandResource(DienstverbandRepository dienstverbandRepository) {
        this.dienstverbandRepository = dienstverbandRepository;
    }

    /**
     * {@code POST  /dienstverbands} : Create a new dienstverband.
     *
     * @param dienstverband the dienstverband to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dienstverband, or with status {@code 400 (Bad Request)} if the dienstverband has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Dienstverband> createDienstverband(@Valid @RequestBody Dienstverband dienstverband) throws URISyntaxException {
        log.debug("REST request to save Dienstverband : {}", dienstverband);
        if (dienstverband.getId() != null) {
            throw new BadRequestAlertException("A new dienstverband cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dienstverband = dienstverbandRepository.save(dienstverband);
        return ResponseEntity.created(new URI("/api/dienstverbands/" + dienstverband.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, dienstverband.getId().toString()))
            .body(dienstverband);
    }

    /**
     * {@code PUT  /dienstverbands/:id} : Updates an existing dienstverband.
     *
     * @param id the id of the dienstverband to save.
     * @param dienstverband the dienstverband to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dienstverband,
     * or with status {@code 400 (Bad Request)} if the dienstverband is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dienstverband couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Dienstverband> updateDienstverband(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Dienstverband dienstverband
    ) throws URISyntaxException {
        log.debug("REST request to update Dienstverband : {}, {}", id, dienstverband);
        if (dienstverband.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dienstverband.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dienstverbandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dienstverband = dienstverbandRepository.save(dienstverband);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dienstverband.getId().toString()))
            .body(dienstverband);
    }

    /**
     * {@code PATCH  /dienstverbands/:id} : Partial updates given fields of an existing dienstverband, field will ignore if it is null
     *
     * @param id the id of the dienstverband to save.
     * @param dienstverband the dienstverband to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dienstverband,
     * or with status {@code 400 (Bad Request)} if the dienstverband is not valid,
     * or with status {@code 404 (Not Found)} if the dienstverband is not found,
     * or with status {@code 500 (Internal Server Error)} if the dienstverband couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Dienstverband> partialUpdateDienstverband(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Dienstverband dienstverband
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dienstverband partially : {}, {}", id, dienstverband);
        if (dienstverband.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dienstverband.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dienstverbandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Dienstverband> result = dienstverbandRepository
            .findById(dienstverband.getId())
            .map(existingDienstverband -> {
                if (dienstverband.getDatumeinde() != null) {
                    existingDienstverband.setDatumeinde(dienstverband.getDatumeinde());
                }
                if (dienstverband.getDatumstart() != null) {
                    existingDienstverband.setDatumstart(dienstverband.getDatumstart());
                }
                if (dienstverband.getPeriodiek() != null) {
                    existingDienstverband.setPeriodiek(dienstverband.getPeriodiek());
                }
                if (dienstverband.getSalaris() != null) {
                    existingDienstverband.setSalaris(dienstverband.getSalaris());
                }
                if (dienstverband.getSchaal() != null) {
                    existingDienstverband.setSchaal(dienstverband.getSchaal());
                }
                if (dienstverband.getUrenperweek() != null) {
                    existingDienstverband.setUrenperweek(dienstverband.getUrenperweek());
                }

                return existingDienstverband;
            })
            .map(dienstverbandRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dienstverband.getId().toString())
        );
    }

    /**
     * {@code GET  /dienstverbands} : get all the dienstverbands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dienstverbands in body.
     */
    @GetMapping("")
    public List<Dienstverband> getAllDienstverbands() {
        log.debug("REST request to get all Dienstverbands");
        return dienstverbandRepository.findAll();
    }

    /**
     * {@code GET  /dienstverbands/:id} : get the "id" dienstverband.
     *
     * @param id the id of the dienstverband to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dienstverband, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Dienstverband> getDienstverband(@PathVariable("id") Long id) {
        log.debug("REST request to get Dienstverband : {}", id);
        Optional<Dienstverband> dienstverband = dienstverbandRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dienstverband);
    }

    /**
     * {@code DELETE  /dienstverbands/:id} : delete the "id" dienstverband.
     *
     * @param id the id of the dienstverband to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDienstverband(@PathVariable("id") Long id) {
        log.debug("REST request to delete Dienstverband : {}", id);
        dienstverbandRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
