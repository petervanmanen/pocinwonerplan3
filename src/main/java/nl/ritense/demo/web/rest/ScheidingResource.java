package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Scheiding;
import nl.ritense.demo.repository.ScheidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Scheiding}.
 */
@RestController
@RequestMapping("/api/scheidings")
@Transactional
public class ScheidingResource {

    private final Logger log = LoggerFactory.getLogger(ScheidingResource.class);

    private static final String ENTITY_NAME = "scheiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheidingRepository scheidingRepository;

    public ScheidingResource(ScheidingRepository scheidingRepository) {
        this.scheidingRepository = scheidingRepository;
    }

    /**
     * {@code POST  /scheidings} : Create a new scheiding.
     *
     * @param scheiding the scheiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheiding, or with status {@code 400 (Bad Request)} if the scheiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Scheiding> createScheiding(@Valid @RequestBody Scheiding scheiding) throws URISyntaxException {
        log.debug("REST request to save Scheiding : {}", scheiding);
        if (scheiding.getId() != null) {
            throw new BadRequestAlertException("A new scheiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        scheiding = scheidingRepository.save(scheiding);
        return ResponseEntity.created(new URI("/api/scheidings/" + scheiding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, scheiding.getId().toString()))
            .body(scheiding);
    }

    /**
     * {@code PUT  /scheidings/:id} : Updates an existing scheiding.
     *
     * @param id the id of the scheiding to save.
     * @param scheiding the scheiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheiding,
     * or with status {@code 400 (Bad Request)} if the scheiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Scheiding> updateScheiding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Scheiding scheiding
    ) throws URISyntaxException {
        log.debug("REST request to update Scheiding : {}, {}", id, scheiding);
        if (scheiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scheiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scheidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        scheiding = scheidingRepository.save(scheiding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scheiding.getId().toString()))
            .body(scheiding);
    }

    /**
     * {@code PATCH  /scheidings/:id} : Partial updates given fields of an existing scheiding, field will ignore if it is null
     *
     * @param id the id of the scheiding to save.
     * @param scheiding the scheiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheiding,
     * or with status {@code 400 (Bad Request)} if the scheiding is not valid,
     * or with status {@code 404 (Not Found)} if the scheiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the scheiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Scheiding> partialUpdateScheiding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Scheiding scheiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Scheiding partially : {}, {}", id, scheiding);
        if (scheiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scheiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scheidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Scheiding> result = scheidingRepository
            .findById(scheiding.getId())
            .map(existingScheiding -> {
                if (scheiding.getAanleghoogte() != null) {
                    existingScheiding.setAanleghoogte(scheiding.getAanleghoogte());
                }
                if (scheiding.getBreedte() != null) {
                    existingScheiding.setBreedte(scheiding.getBreedte());
                }
                if (scheiding.getHoogte() != null) {
                    existingScheiding.setHoogte(scheiding.getHoogte());
                }
                if (scheiding.getJaaronderhouduitgevoerd() != null) {
                    existingScheiding.setJaaronderhouduitgevoerd(scheiding.getJaaronderhouduitgevoerd());
                }
                if (scheiding.getLengte() != null) {
                    existingScheiding.setLengte(scheiding.getLengte());
                }
                if (scheiding.getLeverancier() != null) {
                    existingScheiding.setLeverancier(scheiding.getLeverancier());
                }
                if (scheiding.getEobjectnaam() != null) {
                    existingScheiding.setEobjectnaam(scheiding.getEobjectnaam());
                }
                if (scheiding.getEobjectnummer() != null) {
                    existingScheiding.setEobjectnummer(scheiding.getEobjectnummer());
                }
                if (scheiding.getOppervlakte() != null) {
                    existingScheiding.setOppervlakte(scheiding.getOppervlakte());
                }
                if (scheiding.getScheidingmateriaal() != null) {
                    existingScheiding.setScheidingmateriaal(scheiding.getScheidingmateriaal());
                }
                if (scheiding.getVerplaatsbaar() != null) {
                    existingScheiding.setVerplaatsbaar(scheiding.getVerplaatsbaar());
                }

                return existingScheiding;
            })
            .map(scheidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scheiding.getId().toString())
        );
    }

    /**
     * {@code GET  /scheidings} : get all the scheidings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scheidings in body.
     */
    @GetMapping("")
    public List<Scheiding> getAllScheidings() {
        log.debug("REST request to get all Scheidings");
        return scheidingRepository.findAll();
    }

    /**
     * {@code GET  /scheidings/:id} : get the "id" scheiding.
     *
     * @param id the id of the scheiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Scheiding> getScheiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Scheiding : {}", id);
        Optional<Scheiding> scheiding = scheidingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scheiding);
    }

    /**
     * {@code DELETE  /scheidings/:id} : delete the "id" scheiding.
     *
     * @param id the id of the scheiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Scheiding : {}", id);
        scheidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
