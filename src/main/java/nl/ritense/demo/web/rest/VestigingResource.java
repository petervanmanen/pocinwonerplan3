package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vestiging;
import nl.ritense.demo.repository.VestigingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vestiging}.
 */
@RestController
@RequestMapping("/api/vestigings")
@Transactional
public class VestigingResource {

    private final Logger log = LoggerFactory.getLogger(VestigingResource.class);

    private static final String ENTITY_NAME = "vestiging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VestigingRepository vestigingRepository;

    public VestigingResource(VestigingRepository vestigingRepository) {
        this.vestigingRepository = vestigingRepository;
    }

    /**
     * {@code POST  /vestigings} : Create a new vestiging.
     *
     * @param vestiging the vestiging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vestiging, or with status {@code 400 (Bad Request)} if the vestiging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vestiging> createVestiging(@Valid @RequestBody Vestiging vestiging) throws URISyntaxException {
        log.debug("REST request to save Vestiging : {}", vestiging);
        if (vestiging.getId() != null) {
            throw new BadRequestAlertException("A new vestiging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vestiging = vestigingRepository.save(vestiging);
        return ResponseEntity.created(new URI("/api/vestigings/" + vestiging.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vestiging.getId().toString()))
            .body(vestiging);
    }

    /**
     * {@code PUT  /vestigings/:id} : Updates an existing vestiging.
     *
     * @param id the id of the vestiging to save.
     * @param vestiging the vestiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vestiging,
     * or with status {@code 400 (Bad Request)} if the vestiging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vestiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vestiging> updateVestiging(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vestiging vestiging
    ) throws URISyntaxException {
        log.debug("REST request to update Vestiging : {}, {}", id, vestiging);
        if (vestiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vestiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vestigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vestiging = vestigingRepository.save(vestiging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vestiging.getId().toString()))
            .body(vestiging);
    }

    /**
     * {@code PATCH  /vestigings/:id} : Partial updates given fields of an existing vestiging, field will ignore if it is null
     *
     * @param id the id of the vestiging to save.
     * @param vestiging the vestiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vestiging,
     * or with status {@code 400 (Bad Request)} if the vestiging is not valid,
     * or with status {@code 404 (Not Found)} if the vestiging is not found,
     * or with status {@code 500 (Internal Server Error)} if the vestiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vestiging> partialUpdateVestiging(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vestiging vestiging
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vestiging partially : {}, {}", id, vestiging);
        if (vestiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vestiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vestigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vestiging> result = vestigingRepository
            .findById(vestiging.getId())
            .map(existingVestiging -> {
                if (vestiging.getCommercielevestiging() != null) {
                    existingVestiging.setCommercielevestiging(vestiging.getCommercielevestiging());
                }
                if (vestiging.getDatumaanvang() != null) {
                    existingVestiging.setDatumaanvang(vestiging.getDatumaanvang());
                }
                if (vestiging.getDatumeinde() != null) {
                    existingVestiging.setDatumeinde(vestiging.getDatumeinde());
                }
                if (vestiging.getDatumvoortzetting() != null) {
                    existingVestiging.setDatumvoortzetting(vestiging.getDatumvoortzetting());
                }
                if (vestiging.getFulltimewerkzamemannen() != null) {
                    existingVestiging.setFulltimewerkzamemannen(vestiging.getFulltimewerkzamemannen());
                }
                if (vestiging.getFulltimewerkzamevrouwen() != null) {
                    existingVestiging.setFulltimewerkzamevrouwen(vestiging.getFulltimewerkzamevrouwen());
                }
                if (vestiging.getHandelsnaam() != null) {
                    existingVestiging.setHandelsnaam(vestiging.getHandelsnaam());
                }
                if (vestiging.getParttimewerkzamemannen() != null) {
                    existingVestiging.setParttimewerkzamemannen(vestiging.getParttimewerkzamemannen());
                }
                if (vestiging.getParttimewerkzamevrouwen() != null) {
                    existingVestiging.setParttimewerkzamevrouwen(vestiging.getParttimewerkzamevrouwen());
                }
                if (vestiging.getToevoegingadres() != null) {
                    existingVestiging.setToevoegingadres(vestiging.getToevoegingadres());
                }
                if (vestiging.getTotaalwerkzamepersonen() != null) {
                    existingVestiging.setTotaalwerkzamepersonen(vestiging.getTotaalwerkzamepersonen());
                }
                if (vestiging.getVerkortenaam() != null) {
                    existingVestiging.setVerkortenaam(vestiging.getVerkortenaam());
                }
                if (vestiging.getVestigingsnummer() != null) {
                    existingVestiging.setVestigingsnummer(vestiging.getVestigingsnummer());
                }

                return existingVestiging;
            })
            .map(vestigingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vestiging.getId().toString())
        );
    }

    /**
     * {@code GET  /vestigings} : get all the vestigings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vestigings in body.
     */
    @GetMapping("")
    public List<Vestiging> getAllVestigings() {
        log.debug("REST request to get all Vestigings");
        return vestigingRepository.findAll();
    }

    /**
     * {@code GET  /vestigings/:id} : get the "id" vestiging.
     *
     * @param id the id of the vestiging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vestiging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vestiging> getVestiging(@PathVariable("id") Long id) {
        log.debug("REST request to get Vestiging : {}", id);
        Optional<Vestiging> vestiging = vestigingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vestiging);
    }

    /**
     * {@code DELETE  /vestigings/:id} : delete the "id" vestiging.
     *
     * @param id the id of the vestiging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVestiging(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vestiging : {}", id);
        vestigingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
