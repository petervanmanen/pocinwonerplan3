package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Briefadres;
import nl.ritense.demo.repository.BriefadresRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Briefadres}.
 */
@RestController
@RequestMapping("/api/briefadres")
@Transactional
public class BriefadresResource {

    private final Logger log = LoggerFactory.getLogger(BriefadresResource.class);

    private static final String ENTITY_NAME = "briefadres";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BriefadresRepository briefadresRepository;

    public BriefadresResource(BriefadresRepository briefadresRepository) {
        this.briefadresRepository = briefadresRepository;
    }

    /**
     * {@code POST  /briefadres} : Create a new briefadres.
     *
     * @param briefadres the briefadres to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new briefadres, or with status {@code 400 (Bad Request)} if the briefadres has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Briefadres> createBriefadres(@RequestBody Briefadres briefadres) throws URISyntaxException {
        log.debug("REST request to save Briefadres : {}", briefadres);
        if (briefadres.getId() != null) {
            throw new BadRequestAlertException("A new briefadres cannot already have an ID", ENTITY_NAME, "idexists");
        }
        briefadres = briefadresRepository.save(briefadres);
        return ResponseEntity.created(new URI("/api/briefadres/" + briefadres.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, briefadres.getId().toString()))
            .body(briefadres);
    }

    /**
     * {@code PUT  /briefadres/:id} : Updates an existing briefadres.
     *
     * @param id the id of the briefadres to save.
     * @param briefadres the briefadres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated briefadres,
     * or with status {@code 400 (Bad Request)} if the briefadres is not valid,
     * or with status {@code 500 (Internal Server Error)} if the briefadres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Briefadres> updateBriefadres(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Briefadres briefadres
    ) throws URISyntaxException {
        log.debug("REST request to update Briefadres : {}, {}", id, briefadres);
        if (briefadres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, briefadres.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!briefadresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        briefadres = briefadresRepository.save(briefadres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, briefadres.getId().toString()))
            .body(briefadres);
    }

    /**
     * {@code PATCH  /briefadres/:id} : Partial updates given fields of an existing briefadres, field will ignore if it is null
     *
     * @param id the id of the briefadres to save.
     * @param briefadres the briefadres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated briefadres,
     * or with status {@code 400 (Bad Request)} if the briefadres is not valid,
     * or with status {@code 404 (Not Found)} if the briefadres is not found,
     * or with status {@code 500 (Internal Server Error)} if the briefadres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Briefadres> partialUpdateBriefadres(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Briefadres briefadres
    ) throws URISyntaxException {
        log.debug("REST request to partial update Briefadres partially : {}, {}", id, briefadres);
        if (briefadres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, briefadres.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!briefadresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Briefadres> result = briefadresRepository
            .findById(briefadres.getId())
            .map(existingBriefadres -> {
                if (briefadres.getAdresfunctie() != null) {
                    existingBriefadres.setAdresfunctie(briefadres.getAdresfunctie());
                }
                if (briefadres.getDatumaanvang() != null) {
                    existingBriefadres.setDatumaanvang(briefadres.getDatumaanvang());
                }
                if (briefadres.getDatumeinde() != null) {
                    existingBriefadres.setDatumeinde(briefadres.getDatumeinde());
                }
                if (briefadres.getOmschrijvingaangifte() != null) {
                    existingBriefadres.setOmschrijvingaangifte(briefadres.getOmschrijvingaangifte());
                }

                return existingBriefadres;
            })
            .map(briefadresRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, briefadres.getId().toString())
        );
    }

    /**
     * {@code GET  /briefadres} : get all the briefadres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of briefadres in body.
     */
    @GetMapping("")
    public List<Briefadres> getAllBriefadres() {
        log.debug("REST request to get all Briefadres");
        return briefadresRepository.findAll();
    }

    /**
     * {@code GET  /briefadres/:id} : get the "id" briefadres.
     *
     * @param id the id of the briefadres to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the briefadres, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Briefadres> getBriefadres(@PathVariable("id") Long id) {
        log.debug("REST request to get Briefadres : {}", id);
        Optional<Briefadres> briefadres = briefadresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(briefadres);
    }

    /**
     * {@code DELETE  /briefadres/:id} : delete the "id" briefadres.
     *
     * @param id the id of the briefadres to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBriefadres(@PathVariable("id") Long id) {
        log.debug("REST request to delete Briefadres : {}", id);
        briefadresRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
