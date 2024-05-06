package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Stremming;
import nl.ritense.demo.repository.StremmingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Stremming}.
 */
@RestController
@RequestMapping("/api/stremmings")
@Transactional
public class StremmingResource {

    private final Logger log = LoggerFactory.getLogger(StremmingResource.class);

    private static final String ENTITY_NAME = "stremming";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StremmingRepository stremmingRepository;

    public StremmingResource(StremmingRepository stremmingRepository) {
        this.stremmingRepository = stremmingRepository;
    }

    /**
     * {@code POST  /stremmings} : Create a new stremming.
     *
     * @param stremming the stremming to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stremming, or with status {@code 400 (Bad Request)} if the stremming has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Stremming> createStremming(@RequestBody Stremming stremming) throws URISyntaxException {
        log.debug("REST request to save Stremming : {}", stremming);
        if (stremming.getId() != null) {
            throw new BadRequestAlertException("A new stremming cannot already have an ID", ENTITY_NAME, "idexists");
        }
        stremming = stremmingRepository.save(stremming);
        return ResponseEntity.created(new URI("/api/stremmings/" + stremming.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, stremming.getId().toString()))
            .body(stremming);
    }

    /**
     * {@code PUT  /stremmings/:id} : Updates an existing stremming.
     *
     * @param id the id of the stremming to save.
     * @param stremming the stremming to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stremming,
     * or with status {@code 400 (Bad Request)} if the stremming is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stremming couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stremming> updateStremming(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Stremming stremming
    ) throws URISyntaxException {
        log.debug("REST request to update Stremming : {}, {}", id, stremming);
        if (stremming.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stremming.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stremmingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        stremming = stremmingRepository.save(stremming);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stremming.getId().toString()))
            .body(stremming);
    }

    /**
     * {@code PATCH  /stremmings/:id} : Partial updates given fields of an existing stremming, field will ignore if it is null
     *
     * @param id the id of the stremming to save.
     * @param stremming the stremming to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stremming,
     * or with status {@code 400 (Bad Request)} if the stremming is not valid,
     * or with status {@code 404 (Not Found)} if the stremming is not found,
     * or with status {@code 500 (Internal Server Error)} if the stremming couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Stremming> partialUpdateStremming(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Stremming stremming
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stremming partially : {}, {}", id, stremming);
        if (stremming.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stremming.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stremmingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Stremming> result = stremmingRepository
            .findById(stremming.getId())
            .map(existingStremming -> {
                if (stremming.getAantalgehinderden() != null) {
                    existingStremming.setAantalgehinderden(stremming.getAantalgehinderden());
                }
                if (stremming.getDatumaanmelding() != null) {
                    existingStremming.setDatumaanmelding(stremming.getDatumaanmelding());
                }
                if (stremming.getDatumeinde() != null) {
                    existingStremming.setDatumeinde(stremming.getDatumeinde());
                }
                if (stremming.getDatumstart() != null) {
                    existingStremming.setDatumstart(stremming.getDatumstart());
                }
                if (stremming.getDatumwijziging() != null) {
                    existingStremming.setDatumwijziging(stremming.getDatumwijziging());
                }
                if (stremming.getDelentoegestaan() != null) {
                    existingStremming.setDelentoegestaan(stremming.getDelentoegestaan());
                }
                if (stremming.getGeschiktvoorpublicatie() != null) {
                    existingStremming.setGeschiktvoorpublicatie(stremming.getGeschiktvoorpublicatie());
                }
                if (stremming.getHinderklasse() != null) {
                    existingStremming.setHinderklasse(stremming.getHinderklasse());
                }
                if (stremming.getLocatie() != null) {
                    existingStremming.setLocatie(stremming.getLocatie());
                }
                if (stremming.getNaam() != null) {
                    existingStremming.setNaam(stremming.getNaam());
                }
                if (stremming.getStatus() != null) {
                    existingStremming.setStatus(stremming.getStatus());
                }

                return existingStremming;
            })
            .map(stremmingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stremming.getId().toString())
        );
    }

    /**
     * {@code GET  /stremmings} : get all the stremmings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stremmings in body.
     */
    @GetMapping("")
    public List<Stremming> getAllStremmings(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Stremmings");
        if (eagerload) {
            return stremmingRepository.findAllWithEagerRelationships();
        } else {
            return stremmingRepository.findAll();
        }
    }

    /**
     * {@code GET  /stremmings/:id} : get the "id" stremming.
     *
     * @param id the id of the stremming to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stremming, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stremming> getStremming(@PathVariable("id") Long id) {
        log.debug("REST request to get Stremming : {}", id);
        Optional<Stremming> stremming = stremmingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(stremming);
    }

    /**
     * {@code DELETE  /stremmings/:id} : delete the "id" stremming.
     *
     * @param id the id of the stremming to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStremming(@PathVariable("id") Long id) {
        log.debug("REST request to delete Stremming : {}", id);
        stremmingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
