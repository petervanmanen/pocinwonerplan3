package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Collectie;
import nl.ritense.demo.repository.CollectieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Collectie}.
 */
@RestController
@RequestMapping("/api/collecties")
@Transactional
public class CollectieResource {

    private final Logger log = LoggerFactory.getLogger(CollectieResource.class);

    private static final String ENTITY_NAME = "collectie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectieRepository collectieRepository;

    public CollectieResource(CollectieRepository collectieRepository) {
        this.collectieRepository = collectieRepository;
    }

    /**
     * {@code POST  /collecties} : Create a new collectie.
     *
     * @param collectie the collectie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collectie, or with status {@code 400 (Bad Request)} if the collectie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Collectie> createCollectie(@RequestBody Collectie collectie) throws URISyntaxException {
        log.debug("REST request to save Collectie : {}", collectie);
        if (collectie.getId() != null) {
            throw new BadRequestAlertException("A new collectie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        collectie = collectieRepository.save(collectie);
        return ResponseEntity.created(new URI("/api/collecties/" + collectie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, collectie.getId().toString()))
            .body(collectie);
    }

    /**
     * {@code PUT  /collecties/:id} : Updates an existing collectie.
     *
     * @param id the id of the collectie to save.
     * @param collectie the collectie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectie,
     * or with status {@code 400 (Bad Request)} if the collectie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collectie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Collectie> updateCollectie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Collectie collectie
    ) throws URISyntaxException {
        log.debug("REST request to update Collectie : {}, {}", id, collectie);
        if (collectie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collectie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        collectie = collectieRepository.save(collectie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collectie.getId().toString()))
            .body(collectie);
    }

    /**
     * {@code PATCH  /collecties/:id} : Partial updates given fields of an existing collectie, field will ignore if it is null
     *
     * @param id the id of the collectie to save.
     * @param collectie the collectie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectie,
     * or with status {@code 400 (Bad Request)} if the collectie is not valid,
     * or with status {@code 404 (Not Found)} if the collectie is not found,
     * or with status {@code 500 (Internal Server Error)} if the collectie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Collectie> partialUpdateCollectie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Collectie collectie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Collectie partially : {}, {}", id, collectie);
        if (collectie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collectie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Collectie> result = collectieRepository
            .findById(collectie.getId())
            .map(existingCollectie -> {
                if (collectie.getNaam() != null) {
                    existingCollectie.setNaam(collectie.getNaam());
                }
                if (collectie.getOmschrijving() != null) {
                    existingCollectie.setOmschrijving(collectie.getOmschrijving());
                }

                return existingCollectie;
            })
            .map(collectieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collectie.getId().toString())
        );
    }

    /**
     * {@code GET  /collecties} : get all the collecties.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collecties in body.
     */
    @GetMapping("")
    public List<Collectie> getAllCollecties(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Collecties");
        if (eagerload) {
            return collectieRepository.findAllWithEagerRelationships();
        } else {
            return collectieRepository.findAll();
        }
    }

    /**
     * {@code GET  /collecties/:id} : get the "id" collectie.
     *
     * @param id the id of the collectie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collectie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Collectie> getCollectie(@PathVariable("id") Long id) {
        log.debug("REST request to get Collectie : {}", id);
        Optional<Collectie> collectie = collectieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(collectie);
    }

    /**
     * {@code DELETE  /collecties/:id} : delete the "id" collectie.
     *
     * @param id the id of the collectie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Collectie : {}", id);
        collectieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
