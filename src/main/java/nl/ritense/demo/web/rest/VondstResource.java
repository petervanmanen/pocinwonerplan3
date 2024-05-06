package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vondst;
import nl.ritense.demo.repository.VondstRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vondst}.
 */
@RestController
@RequestMapping("/api/vondsts")
@Transactional
public class VondstResource {

    private final Logger log = LoggerFactory.getLogger(VondstResource.class);

    private static final String ENTITY_NAME = "vondst";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VondstRepository vondstRepository;

    public VondstResource(VondstRepository vondstRepository) {
        this.vondstRepository = vondstRepository;
    }

    /**
     * {@code POST  /vondsts} : Create a new vondst.
     *
     * @param vondst the vondst to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vondst, or with status {@code 400 (Bad Request)} if the vondst has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vondst> createVondst(@Valid @RequestBody Vondst vondst) throws URISyntaxException {
        log.debug("REST request to save Vondst : {}", vondst);
        if (vondst.getId() != null) {
            throw new BadRequestAlertException("A new vondst cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vondst = vondstRepository.save(vondst);
        return ResponseEntity.created(new URI("/api/vondsts/" + vondst.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vondst.getId().toString()))
            .body(vondst);
    }

    /**
     * {@code PUT  /vondsts/:id} : Updates an existing vondst.
     *
     * @param id the id of the vondst to save.
     * @param vondst the vondst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vondst,
     * or with status {@code 400 (Bad Request)} if the vondst is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vondst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vondst> updateVondst(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vondst vondst
    ) throws URISyntaxException {
        log.debug("REST request to update Vondst : {}, {}", id, vondst);
        if (vondst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vondst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vondstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vondst = vondstRepository.save(vondst);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vondst.getId().toString()))
            .body(vondst);
    }

    /**
     * {@code PATCH  /vondsts/:id} : Partial updates given fields of an existing vondst, field will ignore if it is null
     *
     * @param id the id of the vondst to save.
     * @param vondst the vondst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vondst,
     * or with status {@code 400 (Bad Request)} if the vondst is not valid,
     * or with status {@code 404 (Not Found)} if the vondst is not found,
     * or with status {@code 500 (Internal Server Error)} if the vondst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vondst> partialUpdateVondst(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vondst vondst
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vondst partially : {}, {}", id, vondst);
        if (vondst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vondst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vondstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vondst> result = vondstRepository
            .findById(vondst.getId())
            .map(existingVondst -> {
                if (vondst.getDatum() != null) {
                    existingVondst.setDatum(vondst.getDatum());
                }
                if (vondst.getKey() != null) {
                    existingVondst.setKey(vondst.getKey());
                }
                if (vondst.getKeyvulling() != null) {
                    existingVondst.setKeyvulling(vondst.getKeyvulling());
                }
                if (vondst.getOmschrijving() != null) {
                    existingVondst.setOmschrijving(vondst.getOmschrijving());
                }
                if (vondst.getOmstandigheden() != null) {
                    existingVondst.setOmstandigheden(vondst.getOmstandigheden());
                }
                if (vondst.getProjectcd() != null) {
                    existingVondst.setProjectcd(vondst.getProjectcd());
                }
                if (vondst.getPutnummer() != null) {
                    existingVondst.setPutnummer(vondst.getPutnummer());
                }
                if (vondst.getSpoornummer() != null) {
                    existingVondst.setSpoornummer(vondst.getSpoornummer());
                }
                if (vondst.getVlaknummer() != null) {
                    existingVondst.setVlaknummer(vondst.getVlaknummer());
                }
                if (vondst.getVondstnummer() != null) {
                    existingVondst.setVondstnummer(vondst.getVondstnummer());
                }
                if (vondst.getVullingnummer() != null) {
                    existingVondst.setVullingnummer(vondst.getVullingnummer());
                }
                if (vondst.getXcoordinaat() != null) {
                    existingVondst.setXcoordinaat(vondst.getXcoordinaat());
                }
                if (vondst.getYcoordinaat() != null) {
                    existingVondst.setYcoordinaat(vondst.getYcoordinaat());
                }

                return existingVondst;
            })
            .map(vondstRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vondst.getId().toString())
        );
    }

    /**
     * {@code GET  /vondsts} : get all the vondsts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vondsts in body.
     */
    @GetMapping("")
    public List<Vondst> getAllVondsts() {
        log.debug("REST request to get all Vondsts");
        return vondstRepository.findAll();
    }

    /**
     * {@code GET  /vondsts/:id} : get the "id" vondst.
     *
     * @param id the id of the vondst to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vondst, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vondst> getVondst(@PathVariable("id") Long id) {
        log.debug("REST request to get Vondst : {}", id);
        Optional<Vondst> vondst = vondstRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vondst);
    }

    /**
     * {@code DELETE  /vondsts/:id} : delete the "id" vondst.
     *
     * @param id the id of the vondst to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVondst(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vondst : {}", id);
        vondstRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
