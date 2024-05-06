package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Viaduct;
import nl.ritense.demo.repository.ViaductRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Viaduct}.
 */
@RestController
@RequestMapping("/api/viaducts")
@Transactional
public class ViaductResource {

    private final Logger log = LoggerFactory.getLogger(ViaductResource.class);

    private static final String ENTITY_NAME = "viaduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViaductRepository viaductRepository;

    public ViaductResource(ViaductRepository viaductRepository) {
        this.viaductRepository = viaductRepository;
    }

    /**
     * {@code POST  /viaducts} : Create a new viaduct.
     *
     * @param viaduct the viaduct to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viaduct, or with status {@code 400 (Bad Request)} if the viaduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Viaduct> createViaduct(@Valid @RequestBody Viaduct viaduct) throws URISyntaxException {
        log.debug("REST request to save Viaduct : {}", viaduct);
        if (viaduct.getId() != null) {
            throw new BadRequestAlertException("A new viaduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        viaduct = viaductRepository.save(viaduct);
        return ResponseEntity.created(new URI("/api/viaducts/" + viaduct.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, viaduct.getId().toString()))
            .body(viaduct);
    }

    /**
     * {@code PUT  /viaducts/:id} : Updates an existing viaduct.
     *
     * @param id the id of the viaduct to save.
     * @param viaduct the viaduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viaduct,
     * or with status {@code 400 (Bad Request)} if the viaduct is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viaduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Viaduct> updateViaduct(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Viaduct viaduct
    ) throws URISyntaxException {
        log.debug("REST request to update Viaduct : {}, {}", id, viaduct);
        if (viaduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viaduct.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viaductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        viaduct = viaductRepository.save(viaduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, viaduct.getId().toString()))
            .body(viaduct);
    }

    /**
     * {@code PATCH  /viaducts/:id} : Partial updates given fields of an existing viaduct, field will ignore if it is null
     *
     * @param id the id of the viaduct to save.
     * @param viaduct the viaduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viaduct,
     * or with status {@code 400 (Bad Request)} if the viaduct is not valid,
     * or with status {@code 404 (Not Found)} if the viaduct is not found,
     * or with status {@code 500 (Internal Server Error)} if the viaduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Viaduct> partialUpdateViaduct(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Viaduct viaduct
    ) throws URISyntaxException {
        log.debug("REST request to partial update Viaduct partially : {}, {}", id, viaduct);
        if (viaduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viaduct.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viaductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Viaduct> result = viaductRepository
            .findById(viaduct.getId())
            .map(existingViaduct -> {
                if (viaduct.getAantaloverspanningen() != null) {
                    existingViaduct.setAantaloverspanningen(viaduct.getAantaloverspanningen());
                }
                if (viaduct.getBelastingklassenieuw() != null) {
                    existingViaduct.setBelastingklassenieuw(viaduct.getBelastingklassenieuw());
                }
                if (viaduct.getBelastingklasseoud() != null) {
                    existingViaduct.setBelastingklasseoud(viaduct.getBelastingklasseoud());
                }
                if (viaduct.getDraagvermogen() != null) {
                    existingViaduct.setDraagvermogen(viaduct.getDraagvermogen());
                }
                if (viaduct.getMaximaaltoelaatbaarvoertuiggewicht() != null) {
                    existingViaduct.setMaximaaltoelaatbaarvoertuiggewicht(viaduct.getMaximaaltoelaatbaarvoertuiggewicht());
                }
                if (viaduct.getMaximaleasbelasting() != null) {
                    existingViaduct.setMaximaleasbelasting(viaduct.getMaximaleasbelasting());
                }
                if (viaduct.getMaximaleoverspanning() != null) {
                    existingViaduct.setMaximaleoverspanning(viaduct.getMaximaleoverspanning());
                }
                if (viaduct.getOverbruggingsobjectdoorrijopening() != null) {
                    existingViaduct.setOverbruggingsobjectdoorrijopening(viaduct.getOverbruggingsobjectdoorrijopening());
                }
                if (viaduct.getType() != null) {
                    existingViaduct.setType(viaduct.getType());
                }
                if (viaduct.getWaterobject() != null) {
                    existingViaduct.setWaterobject(viaduct.getWaterobject());
                }
                if (viaduct.getZwaarstevoertuig() != null) {
                    existingViaduct.setZwaarstevoertuig(viaduct.getZwaarstevoertuig());
                }

                return existingViaduct;
            })
            .map(viaductRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, viaduct.getId().toString())
        );
    }

    /**
     * {@code GET  /viaducts} : get all the viaducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viaducts in body.
     */
    @GetMapping("")
    public List<Viaduct> getAllViaducts() {
        log.debug("REST request to get all Viaducts");
        return viaductRepository.findAll();
    }

    /**
     * {@code GET  /viaducts/:id} : get the "id" viaduct.
     *
     * @param id the id of the viaduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viaduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Viaduct> getViaduct(@PathVariable("id") Long id) {
        log.debug("REST request to get Viaduct : {}", id);
        Optional<Viaduct> viaduct = viaductRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(viaduct);
    }

    /**
     * {@code DELETE  /viaducts/:id} : delete the "id" viaduct.
     *
     * @param id the id of the viaduct to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaduct(@PathVariable("id") Long id) {
        log.debug("REST request to delete Viaduct : {}", id);
        viaductRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
