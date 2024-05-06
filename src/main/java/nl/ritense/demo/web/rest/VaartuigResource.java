package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vaartuig;
import nl.ritense.demo.repository.VaartuigRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vaartuig}.
 */
@RestController
@RequestMapping("/api/vaartuigs")
@Transactional
public class VaartuigResource {

    private final Logger log = LoggerFactory.getLogger(VaartuigResource.class);

    private static final String ENTITY_NAME = "vaartuig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VaartuigRepository vaartuigRepository;

    public VaartuigResource(VaartuigRepository vaartuigRepository) {
        this.vaartuigRepository = vaartuigRepository;
    }

    /**
     * {@code POST  /vaartuigs} : Create a new vaartuig.
     *
     * @param vaartuig the vaartuig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vaartuig, or with status {@code 400 (Bad Request)} if the vaartuig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vaartuig> createVaartuig(@Valid @RequestBody Vaartuig vaartuig) throws URISyntaxException {
        log.debug("REST request to save Vaartuig : {}", vaartuig);
        if (vaartuig.getId() != null) {
            throw new BadRequestAlertException("A new vaartuig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vaartuig = vaartuigRepository.save(vaartuig);
        return ResponseEntity.created(new URI("/api/vaartuigs/" + vaartuig.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vaartuig.getId().toString()))
            .body(vaartuig);
    }

    /**
     * {@code PUT  /vaartuigs/:id} : Updates an existing vaartuig.
     *
     * @param id the id of the vaartuig to save.
     * @param vaartuig the vaartuig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vaartuig,
     * or with status {@code 400 (Bad Request)} if the vaartuig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vaartuig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vaartuig> updateVaartuig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vaartuig vaartuig
    ) throws URISyntaxException {
        log.debug("REST request to update Vaartuig : {}, {}", id, vaartuig);
        if (vaartuig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vaartuig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vaartuigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vaartuig = vaartuigRepository.save(vaartuig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vaartuig.getId().toString()))
            .body(vaartuig);
    }

    /**
     * {@code PATCH  /vaartuigs/:id} : Partial updates given fields of an existing vaartuig, field will ignore if it is null
     *
     * @param id the id of the vaartuig to save.
     * @param vaartuig the vaartuig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vaartuig,
     * or with status {@code 400 (Bad Request)} if the vaartuig is not valid,
     * or with status {@code 404 (Not Found)} if the vaartuig is not found,
     * or with status {@code 500 (Internal Server Error)} if the vaartuig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vaartuig> partialUpdateVaartuig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vaartuig vaartuig
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vaartuig partially : {}, {}", id, vaartuig);
        if (vaartuig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vaartuig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vaartuigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vaartuig> result = vaartuigRepository
            .findById(vaartuig.getId())
            .map(existingVaartuig -> {
                if (vaartuig.getBreedte() != null) {
                    existingVaartuig.setBreedte(vaartuig.getBreedte());
                }
                if (vaartuig.getHoogte() != null) {
                    existingVaartuig.setHoogte(vaartuig.getHoogte());
                }
                if (vaartuig.getKleur() != null) {
                    existingVaartuig.setKleur(vaartuig.getKleur());
                }
                if (vaartuig.getLengte() != null) {
                    existingVaartuig.setLengte(vaartuig.getLengte());
                }
                if (vaartuig.getNaamvaartuig() != null) {
                    existingVaartuig.setNaamvaartuig(vaartuig.getNaamvaartuig());
                }
                if (vaartuig.getRegistratienummer() != null) {
                    existingVaartuig.setRegistratienummer(vaartuig.getRegistratienummer());
                }

                return existingVaartuig;
            })
            .map(vaartuigRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vaartuig.getId().toString())
        );
    }

    /**
     * {@code GET  /vaartuigs} : get all the vaartuigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vaartuigs in body.
     */
    @GetMapping("")
    public List<Vaartuig> getAllVaartuigs() {
        log.debug("REST request to get all Vaartuigs");
        return vaartuigRepository.findAll();
    }

    /**
     * {@code GET  /vaartuigs/:id} : get the "id" vaartuig.
     *
     * @param id the id of the vaartuig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vaartuig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vaartuig> getVaartuig(@PathVariable("id") Long id) {
        log.debug("REST request to get Vaartuig : {}", id);
        Optional<Vaartuig> vaartuig = vaartuigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vaartuig);
    }

    /**
     * {@code DELETE  /vaartuigs/:id} : delete the "id" vaartuig.
     *
     * @param id the id of the vaartuig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaartuig(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vaartuig : {}", id);
        vaartuigRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
