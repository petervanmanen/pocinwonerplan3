package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overstortconstructie;
import nl.ritense.demo.repository.OverstortconstructieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overstortconstructie}.
 */
@RestController
@RequestMapping("/api/overstortconstructies")
@Transactional
public class OverstortconstructieResource {

    private final Logger log = LoggerFactory.getLogger(OverstortconstructieResource.class);

    private static final String ENTITY_NAME = "overstortconstructie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverstortconstructieRepository overstortconstructieRepository;

    public OverstortconstructieResource(OverstortconstructieRepository overstortconstructieRepository) {
        this.overstortconstructieRepository = overstortconstructieRepository;
    }

    /**
     * {@code POST  /overstortconstructies} : Create a new overstortconstructie.
     *
     * @param overstortconstructie the overstortconstructie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overstortconstructie, or with status {@code 400 (Bad Request)} if the overstortconstructie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overstortconstructie> createOverstortconstructie(@RequestBody Overstortconstructie overstortconstructie)
        throws URISyntaxException {
        log.debug("REST request to save Overstortconstructie : {}", overstortconstructie);
        if (overstortconstructie.getId() != null) {
            throw new BadRequestAlertException("A new overstortconstructie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        overstortconstructie = overstortconstructieRepository.save(overstortconstructie);
        return ResponseEntity.created(new URI("/api/overstortconstructies/" + overstortconstructie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, overstortconstructie.getId().toString()))
            .body(overstortconstructie);
    }

    /**
     * {@code PUT  /overstortconstructies/:id} : Updates an existing overstortconstructie.
     *
     * @param id the id of the overstortconstructie to save.
     * @param overstortconstructie the overstortconstructie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overstortconstructie,
     * or with status {@code 400 (Bad Request)} if the overstortconstructie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overstortconstructie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overstortconstructie> updateOverstortconstructie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overstortconstructie overstortconstructie
    ) throws URISyntaxException {
        log.debug("REST request to update Overstortconstructie : {}, {}", id, overstortconstructie);
        if (overstortconstructie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overstortconstructie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overstortconstructieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overstortconstructie = overstortconstructieRepository.save(overstortconstructie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overstortconstructie.getId().toString()))
            .body(overstortconstructie);
    }

    /**
     * {@code PATCH  /overstortconstructies/:id} : Partial updates given fields of an existing overstortconstructie, field will ignore if it is null
     *
     * @param id the id of the overstortconstructie to save.
     * @param overstortconstructie the overstortconstructie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overstortconstructie,
     * or with status {@code 400 (Bad Request)} if the overstortconstructie is not valid,
     * or with status {@code 404 (Not Found)} if the overstortconstructie is not found,
     * or with status {@code 500 (Internal Server Error)} if the overstortconstructie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overstortconstructie> partialUpdateOverstortconstructie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overstortconstructie overstortconstructie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Overstortconstructie partially : {}, {}", id, overstortconstructie);
        if (overstortconstructie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overstortconstructie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overstortconstructieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overstortconstructie> result = overstortconstructieRepository
            .findById(overstortconstructie.getId())
            .map(existingOverstortconstructie -> {
                if (overstortconstructie.getBassin() != null) {
                    existingOverstortconstructie.setBassin(overstortconstructie.getBassin());
                }
                if (overstortconstructie.getDrempelbreedte() != null) {
                    existingOverstortconstructie.setDrempelbreedte(overstortconstructie.getDrempelbreedte());
                }
                if (overstortconstructie.getDrempelniveau() != null) {
                    existingOverstortconstructie.setDrempelniveau(overstortconstructie.getDrempelniveau());
                }
                if (overstortconstructie.getKlep() != null) {
                    existingOverstortconstructie.setKlep(overstortconstructie.getKlep());
                }
                if (overstortconstructie.getType() != null) {
                    existingOverstortconstructie.setType(overstortconstructie.getType());
                }
                if (overstortconstructie.getVormdrempel() != null) {
                    existingOverstortconstructie.setVormdrempel(overstortconstructie.getVormdrempel());
                }
                if (overstortconstructie.getWaking() != null) {
                    existingOverstortconstructie.setWaking(overstortconstructie.getWaking());
                }

                return existingOverstortconstructie;
            })
            .map(overstortconstructieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overstortconstructie.getId().toString())
        );
    }

    /**
     * {@code GET  /overstortconstructies} : get all the overstortconstructies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overstortconstructies in body.
     */
    @GetMapping("")
    public List<Overstortconstructie> getAllOverstortconstructies() {
        log.debug("REST request to get all Overstortconstructies");
        return overstortconstructieRepository.findAll();
    }

    /**
     * {@code GET  /overstortconstructies/:id} : get the "id" overstortconstructie.
     *
     * @param id the id of the overstortconstructie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overstortconstructie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overstortconstructie> getOverstortconstructie(@PathVariable("id") Long id) {
        log.debug("REST request to get Overstortconstructie : {}", id);
        Optional<Overstortconstructie> overstortconstructie = overstortconstructieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overstortconstructie);
    }

    /**
     * {@code DELETE  /overstortconstructies/:id} : delete the "id" overstortconstructie.
     *
     * @param id the id of the overstortconstructie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverstortconstructie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overstortconstructie : {}", id);
        overstortconstructieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
