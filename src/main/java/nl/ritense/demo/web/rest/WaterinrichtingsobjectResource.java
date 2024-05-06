package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Waterinrichtingsobject;
import nl.ritense.demo.repository.WaterinrichtingsobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Waterinrichtingsobject}.
 */
@RestController
@RequestMapping("/api/waterinrichtingsobjects")
@Transactional
public class WaterinrichtingsobjectResource {

    private final Logger log = LoggerFactory.getLogger(WaterinrichtingsobjectResource.class);

    private static final String ENTITY_NAME = "waterinrichtingsobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WaterinrichtingsobjectRepository waterinrichtingsobjectRepository;

    public WaterinrichtingsobjectResource(WaterinrichtingsobjectRepository waterinrichtingsobjectRepository) {
        this.waterinrichtingsobjectRepository = waterinrichtingsobjectRepository;
    }

    /**
     * {@code POST  /waterinrichtingsobjects} : Create a new waterinrichtingsobject.
     *
     * @param waterinrichtingsobject the waterinrichtingsobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new waterinrichtingsobject, or with status {@code 400 (Bad Request)} if the waterinrichtingsobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Waterinrichtingsobject> createWaterinrichtingsobject(@RequestBody Waterinrichtingsobject waterinrichtingsobject)
        throws URISyntaxException {
        log.debug("REST request to save Waterinrichtingsobject : {}", waterinrichtingsobject);
        if (waterinrichtingsobject.getId() != null) {
            throw new BadRequestAlertException("A new waterinrichtingsobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        waterinrichtingsobject = waterinrichtingsobjectRepository.save(waterinrichtingsobject);
        return ResponseEntity.created(new URI("/api/waterinrichtingsobjects/" + waterinrichtingsobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, waterinrichtingsobject.getId().toString()))
            .body(waterinrichtingsobject);
    }

    /**
     * {@code PUT  /waterinrichtingsobjects/:id} : Updates an existing waterinrichtingsobject.
     *
     * @param id the id of the waterinrichtingsobject to save.
     * @param waterinrichtingsobject the waterinrichtingsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waterinrichtingsobject,
     * or with status {@code 400 (Bad Request)} if the waterinrichtingsobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the waterinrichtingsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Waterinrichtingsobject> updateWaterinrichtingsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Waterinrichtingsobject waterinrichtingsobject
    ) throws URISyntaxException {
        log.debug("REST request to update Waterinrichtingsobject : {}, {}", id, waterinrichtingsobject);
        if (waterinrichtingsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waterinrichtingsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waterinrichtingsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        waterinrichtingsobject = waterinrichtingsobjectRepository.save(waterinrichtingsobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, waterinrichtingsobject.getId().toString()))
            .body(waterinrichtingsobject);
    }

    /**
     * {@code PATCH  /waterinrichtingsobjects/:id} : Partial updates given fields of an existing waterinrichtingsobject, field will ignore if it is null
     *
     * @param id the id of the waterinrichtingsobject to save.
     * @param waterinrichtingsobject the waterinrichtingsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waterinrichtingsobject,
     * or with status {@code 400 (Bad Request)} if the waterinrichtingsobject is not valid,
     * or with status {@code 404 (Not Found)} if the waterinrichtingsobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the waterinrichtingsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Waterinrichtingsobject> partialUpdateWaterinrichtingsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Waterinrichtingsobject waterinrichtingsobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Waterinrichtingsobject partially : {}, {}", id, waterinrichtingsobject);
        if (waterinrichtingsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waterinrichtingsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waterinrichtingsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Waterinrichtingsobject> result = waterinrichtingsobjectRepository
            .findById(waterinrichtingsobject.getId())
            .map(existingWaterinrichtingsobject -> {
                if (waterinrichtingsobject.getAanleghoogte() != null) {
                    existingWaterinrichtingsobject.setAanleghoogte(waterinrichtingsobject.getAanleghoogte());
                }
                if (waterinrichtingsobject.getBreedte() != null) {
                    existingWaterinrichtingsobject.setBreedte(waterinrichtingsobject.getBreedte());
                }
                if (waterinrichtingsobject.getJaarconserveren() != null) {
                    existingWaterinrichtingsobject.setJaarconserveren(waterinrichtingsobject.getJaarconserveren());
                }
                if (waterinrichtingsobject.getJaaronderhouduitgevoerd() != null) {
                    existingWaterinrichtingsobject.setJaaronderhouduitgevoerd(waterinrichtingsobject.getJaaronderhouduitgevoerd());
                }
                if (waterinrichtingsobject.getKwaliteitsniveauactueel() != null) {
                    existingWaterinrichtingsobject.setKwaliteitsniveauactueel(waterinrichtingsobject.getKwaliteitsniveauactueel());
                }
                if (waterinrichtingsobject.getKwaliteitsniveaugewenst() != null) {
                    existingWaterinrichtingsobject.setKwaliteitsniveaugewenst(waterinrichtingsobject.getKwaliteitsniveaugewenst());
                }
                if (waterinrichtingsobject.getLengte() != null) {
                    existingWaterinrichtingsobject.setLengte(waterinrichtingsobject.getLengte());
                }
                if (waterinrichtingsobject.getLeverancier() != null) {
                    existingWaterinrichtingsobject.setLeverancier(waterinrichtingsobject.getLeverancier());
                }
                if (waterinrichtingsobject.getMateriaal() != null) {
                    existingWaterinrichtingsobject.setMateriaal(waterinrichtingsobject.getMateriaal());
                }
                if (waterinrichtingsobject.getOppervlakte() != null) {
                    existingWaterinrichtingsobject.setOppervlakte(waterinrichtingsobject.getOppervlakte());
                }

                return existingWaterinrichtingsobject;
            })
            .map(waterinrichtingsobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, waterinrichtingsobject.getId().toString())
        );
    }

    /**
     * {@code GET  /waterinrichtingsobjects} : get all the waterinrichtingsobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of waterinrichtingsobjects in body.
     */
    @GetMapping("")
    public List<Waterinrichtingsobject> getAllWaterinrichtingsobjects() {
        log.debug("REST request to get all Waterinrichtingsobjects");
        return waterinrichtingsobjectRepository.findAll();
    }

    /**
     * {@code GET  /waterinrichtingsobjects/:id} : get the "id" waterinrichtingsobject.
     *
     * @param id the id of the waterinrichtingsobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the waterinrichtingsobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Waterinrichtingsobject> getWaterinrichtingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Waterinrichtingsobject : {}", id);
        Optional<Waterinrichtingsobject> waterinrichtingsobject = waterinrichtingsobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(waterinrichtingsobject);
    }

    /**
     * {@code DELETE  /waterinrichtingsobjects/:id} : delete the "id" waterinrichtingsobject.
     *
     * @param id the id of the waterinrichtingsobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaterinrichtingsobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Waterinrichtingsobject : {}", id);
        waterinrichtingsobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
