package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Leidingelement;
import nl.ritense.demo.repository.LeidingelementRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Leidingelement}.
 */
@RestController
@RequestMapping("/api/leidingelements")
@Transactional
public class LeidingelementResource {

    private final Logger log = LoggerFactory.getLogger(LeidingelementResource.class);

    private static final String ENTITY_NAME = "leidingelement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeidingelementRepository leidingelementRepository;

    public LeidingelementResource(LeidingelementRepository leidingelementRepository) {
        this.leidingelementRepository = leidingelementRepository;
    }

    /**
     * {@code POST  /leidingelements} : Create a new leidingelement.
     *
     * @param leidingelement the leidingelement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leidingelement, or with status {@code 400 (Bad Request)} if the leidingelement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Leidingelement> createLeidingelement(@RequestBody Leidingelement leidingelement) throws URISyntaxException {
        log.debug("REST request to save Leidingelement : {}", leidingelement);
        if (leidingelement.getId() != null) {
            throw new BadRequestAlertException("A new leidingelement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        leidingelement = leidingelementRepository.save(leidingelement);
        return ResponseEntity.created(new URI("/api/leidingelements/" + leidingelement.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, leidingelement.getId().toString()))
            .body(leidingelement);
    }

    /**
     * {@code PUT  /leidingelements/:id} : Updates an existing leidingelement.
     *
     * @param id the id of the leidingelement to save.
     * @param leidingelement the leidingelement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leidingelement,
     * or with status {@code 400 (Bad Request)} if the leidingelement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leidingelement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Leidingelement> updateLeidingelement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Leidingelement leidingelement
    ) throws URISyntaxException {
        log.debug("REST request to update Leidingelement : {}, {}", id, leidingelement);
        if (leidingelement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leidingelement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leidingelementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        leidingelement = leidingelementRepository.save(leidingelement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leidingelement.getId().toString()))
            .body(leidingelement);
    }

    /**
     * {@code PATCH  /leidingelements/:id} : Partial updates given fields of an existing leidingelement, field will ignore if it is null
     *
     * @param id the id of the leidingelement to save.
     * @param leidingelement the leidingelement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leidingelement,
     * or with status {@code 400 (Bad Request)} if the leidingelement is not valid,
     * or with status {@code 404 (Not Found)} if the leidingelement is not found,
     * or with status {@code 500 (Internal Server Error)} if the leidingelement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Leidingelement> partialUpdateLeidingelement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Leidingelement leidingelement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Leidingelement partially : {}, {}", id, leidingelement);
        if (leidingelement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leidingelement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leidingelementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Leidingelement> result = leidingelementRepository
            .findById(leidingelement.getId())
            .map(existingLeidingelement -> {
                if (leidingelement.getAfwijkendedieptelegging() != null) {
                    existingLeidingelement.setAfwijkendedieptelegging(leidingelement.getAfwijkendedieptelegging());
                }
                if (leidingelement.getDiepte() != null) {
                    existingLeidingelement.setDiepte(leidingelement.getDiepte());
                }
                if (leidingelement.getGeonauwkeurigheidxy() != null) {
                    existingLeidingelement.setGeonauwkeurigheidxy(leidingelement.getGeonauwkeurigheidxy());
                }
                if (leidingelement.getJaaronderhouduitgevoerd() != null) {
                    existingLeidingelement.setJaaronderhouduitgevoerd(leidingelement.getJaaronderhouduitgevoerd());
                }
                if (leidingelement.getLeverancier() != null) {
                    existingLeidingelement.setLeverancier(leidingelement.getLeverancier());
                }
                if (leidingelement.getThemaimkl() != null) {
                    existingLeidingelement.setThemaimkl(leidingelement.getThemaimkl());
                }

                return existingLeidingelement;
            })
            .map(leidingelementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leidingelement.getId().toString())
        );
    }

    /**
     * {@code GET  /leidingelements} : get all the leidingelements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leidingelements in body.
     */
    @GetMapping("")
    public List<Leidingelement> getAllLeidingelements() {
        log.debug("REST request to get all Leidingelements");
        return leidingelementRepository.findAll();
    }

    /**
     * {@code GET  /leidingelements/:id} : get the "id" leidingelement.
     *
     * @param id the id of the leidingelement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leidingelement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leidingelement> getLeidingelement(@PathVariable("id") Long id) {
        log.debug("REST request to get Leidingelement : {}", id);
        Optional<Leidingelement> leidingelement = leidingelementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(leidingelement);
    }

    /**
     * {@code DELETE  /leidingelements/:id} : delete the "id" leidingelement.
     *
     * @param id the id of the leidingelement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeidingelement(@PathVariable("id") Long id) {
        log.debug("REST request to delete Leidingelement : {}", id);
        leidingelementRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
