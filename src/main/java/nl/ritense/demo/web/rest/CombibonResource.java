package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Combibon;
import nl.ritense.demo.repository.CombibonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Combibon}.
 */
@RestController
@RequestMapping("/api/combibons")
@Transactional
public class CombibonResource {

    private final Logger log = LoggerFactory.getLogger(CombibonResource.class);

    private static final String ENTITY_NAME = "combibon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CombibonRepository combibonRepository;

    public CombibonResource(CombibonRepository combibonRepository) {
        this.combibonRepository = combibonRepository;
    }

    /**
     * {@code POST  /combibons} : Create a new combibon.
     *
     * @param combibon the combibon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new combibon, or with status {@code 400 (Bad Request)} if the combibon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Combibon> createCombibon(@RequestBody Combibon combibon) throws URISyntaxException {
        log.debug("REST request to save Combibon : {}", combibon);
        if (combibon.getId() != null) {
            throw new BadRequestAlertException("A new combibon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        combibon = combibonRepository.save(combibon);
        return ResponseEntity.created(new URI("/api/combibons/" + combibon.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, combibon.getId().toString()))
            .body(combibon);
    }

    /**
     * {@code PUT  /combibons/:id} : Updates an existing combibon.
     *
     * @param id the id of the combibon to save.
     * @param combibon the combibon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated combibon,
     * or with status {@code 400 (Bad Request)} if the combibon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the combibon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Combibon> updateCombibon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Combibon combibon
    ) throws URISyntaxException {
        log.debug("REST request to update Combibon : {}, {}", id, combibon);
        if (combibon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, combibon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!combibonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        combibon = combibonRepository.save(combibon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, combibon.getId().toString()))
            .body(combibon);
    }

    /**
     * {@code PATCH  /combibons/:id} : Partial updates given fields of an existing combibon, field will ignore if it is null
     *
     * @param id the id of the combibon to save.
     * @param combibon the combibon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated combibon,
     * or with status {@code 400 (Bad Request)} if the combibon is not valid,
     * or with status {@code 404 (Not Found)} if the combibon is not found,
     * or with status {@code 500 (Internal Server Error)} if the combibon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Combibon> partialUpdateCombibon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Combibon combibon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Combibon partially : {}, {}", id, combibon);
        if (combibon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, combibon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!combibonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Combibon> result = combibonRepository
            .findById(combibon.getId())
            .map(existingCombibon -> {
                if (combibon.getSanctie() != null) {
                    existingCombibon.setSanctie(combibon.getSanctie());
                }

                return existingCombibon;
            })
            .map(combibonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, combibon.getId().toString())
        );
    }

    /**
     * {@code GET  /combibons} : get all the combibons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of combibons in body.
     */
    @GetMapping("")
    public List<Combibon> getAllCombibons() {
        log.debug("REST request to get all Combibons");
        return combibonRepository.findAll();
    }

    /**
     * {@code GET  /combibons/:id} : get the "id" combibon.
     *
     * @param id the id of the combibon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the combibon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Combibon> getCombibon(@PathVariable("id") Long id) {
        log.debug("REST request to get Combibon : {}", id);
        Optional<Combibon> combibon = combibonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(combibon);
    }

    /**
     * {@code DELETE  /combibons/:id} : delete the "id" combibon.
     *
     * @param id the id of the combibon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombibon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Combibon : {}", id);
        combibonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
