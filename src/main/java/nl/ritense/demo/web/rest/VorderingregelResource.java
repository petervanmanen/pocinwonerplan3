package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vorderingregel;
import nl.ritense.demo.repository.VorderingregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vorderingregel}.
 */
@RestController
@RequestMapping("/api/vorderingregels")
@Transactional
public class VorderingregelResource {

    private final Logger log = LoggerFactory.getLogger(VorderingregelResource.class);

    private static final String ENTITY_NAME = "vorderingregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VorderingregelRepository vorderingregelRepository;

    public VorderingregelResource(VorderingregelRepository vorderingregelRepository) {
        this.vorderingregelRepository = vorderingregelRepository;
    }

    /**
     * {@code POST  /vorderingregels} : Create a new vorderingregel.
     *
     * @param vorderingregel the vorderingregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vorderingregel, or with status {@code 400 (Bad Request)} if the vorderingregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vorderingregel> createVorderingregel(@RequestBody Vorderingregel vorderingregel) throws URISyntaxException {
        log.debug("REST request to save Vorderingregel : {}", vorderingregel);
        if (vorderingregel.getId() != null) {
            throw new BadRequestAlertException("A new vorderingregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vorderingregel = vorderingregelRepository.save(vorderingregel);
        return ResponseEntity.created(new URI("/api/vorderingregels/" + vorderingregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vorderingregel.getId().toString()))
            .body(vorderingregel);
    }

    /**
     * {@code PUT  /vorderingregels/:id} : Updates an existing vorderingregel.
     *
     * @param id the id of the vorderingregel to save.
     * @param vorderingregel the vorderingregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vorderingregel,
     * or with status {@code 400 (Bad Request)} if the vorderingregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vorderingregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vorderingregel> updateVorderingregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vorderingregel vorderingregel
    ) throws URISyntaxException {
        log.debug("REST request to update Vorderingregel : {}, {}", id, vorderingregel);
        if (vorderingregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vorderingregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vorderingregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vorderingregel = vorderingregelRepository.save(vorderingregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vorderingregel.getId().toString()))
            .body(vorderingregel);
    }

    /**
     * {@code PATCH  /vorderingregels/:id} : Partial updates given fields of an existing vorderingregel, field will ignore if it is null
     *
     * @param id the id of the vorderingregel to save.
     * @param vorderingregel the vorderingregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vorderingregel,
     * or with status {@code 400 (Bad Request)} if the vorderingregel is not valid,
     * or with status {@code 404 (Not Found)} if the vorderingregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the vorderingregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vorderingregel> partialUpdateVorderingregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vorderingregel vorderingregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vorderingregel partially : {}, {}", id, vorderingregel);
        if (vorderingregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vorderingregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vorderingregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vorderingregel> result = vorderingregelRepository
            .findById(vorderingregel.getId())
            .map(existingVorderingregel -> {
                if (vorderingregel.getAangemaaktdoor() != null) {
                    existingVorderingregel.setAangemaaktdoor(vorderingregel.getAangemaaktdoor());
                }
                if (vorderingregel.getAanmaakdatum() != null) {
                    existingVorderingregel.setAanmaakdatum(vorderingregel.getAanmaakdatum());
                }
                if (vorderingregel.getBedragexclbtw() != null) {
                    existingVorderingregel.setBedragexclbtw(vorderingregel.getBedragexclbtw());
                }
                if (vorderingregel.getBedraginclbtw() != null) {
                    existingVorderingregel.setBedraginclbtw(vorderingregel.getBedraginclbtw());
                }
                if (vorderingregel.getBtwcategorie() != null) {
                    existingVorderingregel.setBtwcategorie(vorderingregel.getBtwcategorie());
                }
                if (vorderingregel.getGemuteerddoor() != null) {
                    existingVorderingregel.setGemuteerddoor(vorderingregel.getGemuteerddoor());
                }
                if (vorderingregel.getMutatiedatum() != null) {
                    existingVorderingregel.setMutatiedatum(vorderingregel.getMutatiedatum());
                }
                if (vorderingregel.getOmschrijving() != null) {
                    existingVorderingregel.setOmschrijving(vorderingregel.getOmschrijving());
                }
                if (vorderingregel.getPeriodiek() != null) {
                    existingVorderingregel.setPeriodiek(vorderingregel.getPeriodiek());
                }
                if (vorderingregel.getType() != null) {
                    existingVorderingregel.setType(vorderingregel.getType());
                }

                return existingVorderingregel;
            })
            .map(vorderingregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vorderingregel.getId().toString())
        );
    }

    /**
     * {@code GET  /vorderingregels} : get all the vorderingregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vorderingregels in body.
     */
    @GetMapping("")
    public List<Vorderingregel> getAllVorderingregels() {
        log.debug("REST request to get all Vorderingregels");
        return vorderingregelRepository.findAll();
    }

    /**
     * {@code GET  /vorderingregels/:id} : get the "id" vorderingregel.
     *
     * @param id the id of the vorderingregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vorderingregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vorderingregel> getVorderingregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Vorderingregel : {}", id);
        Optional<Vorderingregel> vorderingregel = vorderingregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vorderingregel);
    }

    /**
     * {@code DELETE  /vorderingregels/:id} : delete the "id" vorderingregel.
     *
     * @param id the id of the vorderingregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVorderingregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vorderingregel : {}", id);
        vorderingregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
