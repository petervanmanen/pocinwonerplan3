package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verkeersbesluit;
import nl.ritense.demo.repository.VerkeersbesluitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verkeersbesluit}.
 */
@RestController
@RequestMapping("/api/verkeersbesluits")
@Transactional
public class VerkeersbesluitResource {

    private final Logger log = LoggerFactory.getLogger(VerkeersbesluitResource.class);

    private static final String ENTITY_NAME = "verkeersbesluit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerkeersbesluitRepository verkeersbesluitRepository;

    public VerkeersbesluitResource(VerkeersbesluitRepository verkeersbesluitRepository) {
        this.verkeersbesluitRepository = verkeersbesluitRepository;
    }

    /**
     * {@code POST  /verkeersbesluits} : Create a new verkeersbesluit.
     *
     * @param verkeersbesluit the verkeersbesluit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verkeersbesluit, or with status {@code 400 (Bad Request)} if the verkeersbesluit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verkeersbesluit> createVerkeersbesluit(@Valid @RequestBody Verkeersbesluit verkeersbesluit)
        throws URISyntaxException {
        log.debug("REST request to save Verkeersbesluit : {}", verkeersbesluit);
        if (verkeersbesluit.getId() != null) {
            throw new BadRequestAlertException("A new verkeersbesluit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verkeersbesluit = verkeersbesluitRepository.save(verkeersbesluit);
        return ResponseEntity.created(new URI("/api/verkeersbesluits/" + verkeersbesluit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verkeersbesluit.getId().toString()))
            .body(verkeersbesluit);
    }

    /**
     * {@code PUT  /verkeersbesluits/:id} : Updates an existing verkeersbesluit.
     *
     * @param id the id of the verkeersbesluit to save.
     * @param verkeersbesluit the verkeersbesluit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verkeersbesluit,
     * or with status {@code 400 (Bad Request)} if the verkeersbesluit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verkeersbesluit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verkeersbesluit> updateVerkeersbesluit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verkeersbesluit verkeersbesluit
    ) throws URISyntaxException {
        log.debug("REST request to update Verkeersbesluit : {}, {}", id, verkeersbesluit);
        if (verkeersbesluit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verkeersbesluit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verkeersbesluitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verkeersbesluit = verkeersbesluitRepository.save(verkeersbesluit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verkeersbesluit.getId().toString()))
            .body(verkeersbesluit);
    }

    /**
     * {@code PATCH  /verkeersbesluits/:id} : Partial updates given fields of an existing verkeersbesluit, field will ignore if it is null
     *
     * @param id the id of the verkeersbesluit to save.
     * @param verkeersbesluit the verkeersbesluit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verkeersbesluit,
     * or with status {@code 400 (Bad Request)} if the verkeersbesluit is not valid,
     * or with status {@code 404 (Not Found)} if the verkeersbesluit is not found,
     * or with status {@code 500 (Internal Server Error)} if the verkeersbesluit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verkeersbesluit> partialUpdateVerkeersbesluit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verkeersbesluit verkeersbesluit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verkeersbesluit partially : {}, {}", id, verkeersbesluit);
        if (verkeersbesluit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verkeersbesluit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verkeersbesluitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verkeersbesluit> result = verkeersbesluitRepository
            .findById(verkeersbesluit.getId())
            .map(existingVerkeersbesluit -> {
                if (verkeersbesluit.getDatumbesluit() != null) {
                    existingVerkeersbesluit.setDatumbesluit(verkeersbesluit.getDatumbesluit());
                }
                if (verkeersbesluit.getDatumeinde() != null) {
                    existingVerkeersbesluit.setDatumeinde(verkeersbesluit.getDatumeinde());
                }
                if (verkeersbesluit.getDatumstart() != null) {
                    existingVerkeersbesluit.setDatumstart(verkeersbesluit.getDatumstart());
                }
                if (verkeersbesluit.getHuisnummer() != null) {
                    existingVerkeersbesluit.setHuisnummer(verkeersbesluit.getHuisnummer());
                }
                if (verkeersbesluit.getPostcode() != null) {
                    existingVerkeersbesluit.setPostcode(verkeersbesluit.getPostcode());
                }
                if (verkeersbesluit.getReferentienummer() != null) {
                    existingVerkeersbesluit.setReferentienummer(verkeersbesluit.getReferentienummer());
                }
                if (verkeersbesluit.getStraat() != null) {
                    existingVerkeersbesluit.setStraat(verkeersbesluit.getStraat());
                }
                if (verkeersbesluit.getTitel() != null) {
                    existingVerkeersbesluit.setTitel(verkeersbesluit.getTitel());
                }

                return existingVerkeersbesluit;
            })
            .map(verkeersbesluitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verkeersbesluit.getId().toString())
        );
    }

    /**
     * {@code GET  /verkeersbesluits} : get all the verkeersbesluits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verkeersbesluits in body.
     */
    @GetMapping("")
    public List<Verkeersbesluit> getAllVerkeersbesluits() {
        log.debug("REST request to get all Verkeersbesluits");
        return verkeersbesluitRepository.findAll();
    }

    /**
     * {@code GET  /verkeersbesluits/:id} : get the "id" verkeersbesluit.
     *
     * @param id the id of the verkeersbesluit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verkeersbesluit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verkeersbesluit> getVerkeersbesluit(@PathVariable("id") Long id) {
        log.debug("REST request to get Verkeersbesluit : {}", id);
        Optional<Verkeersbesluit> verkeersbesluit = verkeersbesluitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verkeersbesluit);
    }

    /**
     * {@code DELETE  /verkeersbesluits/:id} : delete the "id" verkeersbesluit.
     *
     * @param id the id of the verkeersbesluit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerkeersbesluit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verkeersbesluit : {}", id);
        verkeersbesluitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
