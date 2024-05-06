package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Waterobject;
import nl.ritense.demo.repository.WaterobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Waterobject}.
 */
@RestController
@RequestMapping("/api/waterobjects")
@Transactional
public class WaterobjectResource {

    private final Logger log = LoggerFactory.getLogger(WaterobjectResource.class);

    private static final String ENTITY_NAME = "waterobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WaterobjectRepository waterobjectRepository;

    public WaterobjectResource(WaterobjectRepository waterobjectRepository) {
        this.waterobjectRepository = waterobjectRepository;
    }

    /**
     * {@code POST  /waterobjects} : Create a new waterobject.
     *
     * @param waterobject the waterobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new waterobject, or with status {@code 400 (Bad Request)} if the waterobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Waterobject> createWaterobject(@Valid @RequestBody Waterobject waterobject) throws URISyntaxException {
        log.debug("REST request to save Waterobject : {}", waterobject);
        if (waterobject.getId() != null) {
            throw new BadRequestAlertException("A new waterobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        waterobject = waterobjectRepository.save(waterobject);
        return ResponseEntity.created(new URI("/api/waterobjects/" + waterobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, waterobject.getId().toString()))
            .body(waterobject);
    }

    /**
     * {@code PUT  /waterobjects/:id} : Updates an existing waterobject.
     *
     * @param id the id of the waterobject to save.
     * @param waterobject the waterobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waterobject,
     * or with status {@code 400 (Bad Request)} if the waterobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the waterobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Waterobject> updateWaterobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Waterobject waterobject
    ) throws URISyntaxException {
        log.debug("REST request to update Waterobject : {}, {}", id, waterobject);
        if (waterobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waterobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waterobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        waterobject = waterobjectRepository.save(waterobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, waterobject.getId().toString()))
            .body(waterobject);
    }

    /**
     * {@code PATCH  /waterobjects/:id} : Partial updates given fields of an existing waterobject, field will ignore if it is null
     *
     * @param id the id of the waterobject to save.
     * @param waterobject the waterobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waterobject,
     * or with status {@code 400 (Bad Request)} if the waterobject is not valid,
     * or with status {@code 404 (Not Found)} if the waterobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the waterobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Waterobject> partialUpdateWaterobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Waterobject waterobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Waterobject partially : {}, {}", id, waterobject);
        if (waterobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waterobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waterobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Waterobject> result = waterobjectRepository
            .findById(waterobject.getId())
            .map(existingWaterobject -> {
                if (waterobject.getBreedte() != null) {
                    existingWaterobject.setBreedte(waterobject.getBreedte());
                }
                if (waterobject.getFolie() != null) {
                    existingWaterobject.setFolie(waterobject.getFolie());
                }
                if (waterobject.getHoogte() != null) {
                    existingWaterobject.setHoogte(waterobject.getHoogte());
                }
                if (waterobject.getInfiltrerendoppervlak() != null) {
                    existingWaterobject.setInfiltrerendoppervlak(waterobject.getInfiltrerendoppervlak());
                }
                if (waterobject.getInfiltrerendvermogen() != null) {
                    existingWaterobject.setInfiltrerendvermogen(waterobject.getInfiltrerendvermogen());
                }
                if (waterobject.getLengte() != null) {
                    existingWaterobject.setLengte(waterobject.getLengte());
                }
                if (waterobject.getLozingspunt() != null) {
                    existingWaterobject.setLozingspunt(waterobject.getLozingspunt());
                }
                if (waterobject.getOppervlakte() != null) {
                    existingWaterobject.setOppervlakte(waterobject.getOppervlakte());
                }
                if (waterobject.getPorositeit() != null) {
                    existingWaterobject.setPorositeit(waterobject.getPorositeit());
                }
                if (waterobject.getStreefdiepte() != null) {
                    existingWaterobject.setStreefdiepte(waterobject.getStreefdiepte());
                }
                if (waterobject.getType() != null) {
                    existingWaterobject.setType(waterobject.getType());
                }
                if (waterobject.getTypeplus() != null) {
                    existingWaterobject.setTypeplus(waterobject.getTypeplus());
                }
                if (waterobject.getTypeplus2() != null) {
                    existingWaterobject.setTypeplus2(waterobject.getTypeplus2());
                }
                if (waterobject.getTypevaarwater() != null) {
                    existingWaterobject.setTypevaarwater(waterobject.getTypevaarwater());
                }
                if (waterobject.getTypewaterplant() != null) {
                    existingWaterobject.setTypewaterplant(waterobject.getTypewaterplant());
                }
                if (waterobject.getUitstroomniveau() != null) {
                    existingWaterobject.setUitstroomniveau(waterobject.getUitstroomniveau());
                }
                if (waterobject.getVaarwegtraject() != null) {
                    existingWaterobject.setVaarwegtraject(waterobject.getVaarwegtraject());
                }
                if (waterobject.getVorm() != null) {
                    existingWaterobject.setVorm(waterobject.getVorm());
                }
                if (waterobject.getWaternaam() != null) {
                    existingWaterobject.setWaternaam(waterobject.getWaternaam());
                }
                if (waterobject.getWaterpeil() != null) {
                    existingWaterobject.setWaterpeil(waterobject.getWaterpeil());
                }
                if (waterobject.getWaterpeilwinter() != null) {
                    existingWaterobject.setWaterpeilwinter(waterobject.getWaterpeilwinter());
                }
                if (waterobject.getWaterpeilzomer() != null) {
                    existingWaterobject.setWaterpeilzomer(waterobject.getWaterpeilzomer());
                }
                if (waterobject.getWaterplanten() != null) {
                    existingWaterobject.setWaterplanten(waterobject.getWaterplanten());
                }

                return existingWaterobject;
            })
            .map(waterobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, waterobject.getId().toString())
        );
    }

    /**
     * {@code GET  /waterobjects} : get all the waterobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of waterobjects in body.
     */
    @GetMapping("")
    public List<Waterobject> getAllWaterobjects() {
        log.debug("REST request to get all Waterobjects");
        return waterobjectRepository.findAll();
    }

    /**
     * {@code GET  /waterobjects/:id} : get the "id" waterobject.
     *
     * @param id the id of the waterobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the waterobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Waterobject> getWaterobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Waterobject : {}", id);
        Optional<Waterobject> waterobject = waterobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(waterobject);
    }

    /**
     * {@code DELETE  /waterobjects/:id} : delete the "id" waterobject.
     *
     * @param id the id of the waterobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaterobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Waterobject : {}", id);
        waterobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
