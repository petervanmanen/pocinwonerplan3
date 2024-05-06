package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Afvalbak;
import nl.ritense.demo.repository.AfvalbakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Afvalbak}.
 */
@RestController
@RequestMapping("/api/afvalbaks")
@Transactional
public class AfvalbakResource {

    private final Logger log = LoggerFactory.getLogger(AfvalbakResource.class);

    private static final String ENTITY_NAME = "afvalbak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AfvalbakRepository afvalbakRepository;

    public AfvalbakResource(AfvalbakRepository afvalbakRepository) {
        this.afvalbakRepository = afvalbakRepository;
    }

    /**
     * {@code POST  /afvalbaks} : Create a new afvalbak.
     *
     * @param afvalbak the afvalbak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new afvalbak, or with status {@code 400 (Bad Request)} if the afvalbak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Afvalbak> createAfvalbak(@RequestBody Afvalbak afvalbak) throws URISyntaxException {
        log.debug("REST request to save Afvalbak : {}", afvalbak);
        if (afvalbak.getId() != null) {
            throw new BadRequestAlertException("A new afvalbak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        afvalbak = afvalbakRepository.save(afvalbak);
        return ResponseEntity.created(new URI("/api/afvalbaks/" + afvalbak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, afvalbak.getId().toString()))
            .body(afvalbak);
    }

    /**
     * {@code PUT  /afvalbaks/:id} : Updates an existing afvalbak.
     *
     * @param id the id of the afvalbak to save.
     * @param afvalbak the afvalbak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afvalbak,
     * or with status {@code 400 (Bad Request)} if the afvalbak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the afvalbak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Afvalbak> updateAfvalbak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Afvalbak afvalbak
    ) throws URISyntaxException {
        log.debug("REST request to update Afvalbak : {}, {}", id, afvalbak);
        if (afvalbak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afvalbak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afvalbakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        afvalbak = afvalbakRepository.save(afvalbak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, afvalbak.getId().toString()))
            .body(afvalbak);
    }

    /**
     * {@code PATCH  /afvalbaks/:id} : Partial updates given fields of an existing afvalbak, field will ignore if it is null
     *
     * @param id the id of the afvalbak to save.
     * @param afvalbak the afvalbak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afvalbak,
     * or with status {@code 400 (Bad Request)} if the afvalbak is not valid,
     * or with status {@code 404 (Not Found)} if the afvalbak is not found,
     * or with status {@code 500 (Internal Server Error)} if the afvalbak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Afvalbak> partialUpdateAfvalbak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Afvalbak afvalbak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Afvalbak partially : {}, {}", id, afvalbak);
        if (afvalbak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afvalbak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afvalbakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Afvalbak> result = afvalbakRepository
            .findById(afvalbak.getId())
            .map(existingAfvalbak -> {
                if (afvalbak.getType() != null) {
                    existingAfvalbak.setType(afvalbak.getType());
                }
                if (afvalbak.getTypeplus() != null) {
                    existingAfvalbak.setTypeplus(afvalbak.getTypeplus());
                }

                return existingAfvalbak;
            })
            .map(afvalbakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, afvalbak.getId().toString())
        );
    }

    /**
     * {@code GET  /afvalbaks} : get all the afvalbaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of afvalbaks in body.
     */
    @GetMapping("")
    public List<Afvalbak> getAllAfvalbaks() {
        log.debug("REST request to get all Afvalbaks");
        return afvalbakRepository.findAll();
    }

    /**
     * {@code GET  /afvalbaks/:id} : get the "id" afvalbak.
     *
     * @param id the id of the afvalbak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the afvalbak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Afvalbak> getAfvalbak(@PathVariable("id") Long id) {
        log.debug("REST request to get Afvalbak : {}", id);
        Optional<Afvalbak> afvalbak = afvalbakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(afvalbak);
    }

    /**
     * {@code DELETE  /afvalbaks/:id} : delete the "id" afvalbak.
     *
     * @param id the id of the afvalbak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAfvalbak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Afvalbak : {}", id);
        afvalbakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
