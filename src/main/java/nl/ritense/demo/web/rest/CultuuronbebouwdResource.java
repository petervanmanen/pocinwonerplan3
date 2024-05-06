package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Cultuuronbebouwd;
import nl.ritense.demo.repository.CultuuronbebouwdRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Cultuuronbebouwd}.
 */
@RestController
@RequestMapping("/api/cultuuronbebouwds")
@Transactional
public class CultuuronbebouwdResource {

    private final Logger log = LoggerFactory.getLogger(CultuuronbebouwdResource.class);

    private static final String ENTITY_NAME = "cultuuronbebouwd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CultuuronbebouwdRepository cultuuronbebouwdRepository;

    public CultuuronbebouwdResource(CultuuronbebouwdRepository cultuuronbebouwdRepository) {
        this.cultuuronbebouwdRepository = cultuuronbebouwdRepository;
    }

    /**
     * {@code POST  /cultuuronbebouwds} : Create a new cultuuronbebouwd.
     *
     * @param cultuuronbebouwd the cultuuronbebouwd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cultuuronbebouwd, or with status {@code 400 (Bad Request)} if the cultuuronbebouwd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Cultuuronbebouwd> createCultuuronbebouwd(@RequestBody Cultuuronbebouwd cultuuronbebouwd)
        throws URISyntaxException {
        log.debug("REST request to save Cultuuronbebouwd : {}", cultuuronbebouwd);
        if (cultuuronbebouwd.getId() != null) {
            throw new BadRequestAlertException("A new cultuuronbebouwd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cultuuronbebouwd = cultuuronbebouwdRepository.save(cultuuronbebouwd);
        return ResponseEntity.created(new URI("/api/cultuuronbebouwds/" + cultuuronbebouwd.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cultuuronbebouwd.getId().toString()))
            .body(cultuuronbebouwd);
    }

    /**
     * {@code PUT  /cultuuronbebouwds/:id} : Updates an existing cultuuronbebouwd.
     *
     * @param id the id of the cultuuronbebouwd to save.
     * @param cultuuronbebouwd the cultuuronbebouwd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cultuuronbebouwd,
     * or with status {@code 400 (Bad Request)} if the cultuuronbebouwd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cultuuronbebouwd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cultuuronbebouwd> updateCultuuronbebouwd(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cultuuronbebouwd cultuuronbebouwd
    ) throws URISyntaxException {
        log.debug("REST request to update Cultuuronbebouwd : {}, {}", id, cultuuronbebouwd);
        if (cultuuronbebouwd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cultuuronbebouwd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cultuuronbebouwdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cultuuronbebouwd = cultuuronbebouwdRepository.save(cultuuronbebouwd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cultuuronbebouwd.getId().toString()))
            .body(cultuuronbebouwd);
    }

    /**
     * {@code PATCH  /cultuuronbebouwds/:id} : Partial updates given fields of an existing cultuuronbebouwd, field will ignore if it is null
     *
     * @param id the id of the cultuuronbebouwd to save.
     * @param cultuuronbebouwd the cultuuronbebouwd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cultuuronbebouwd,
     * or with status {@code 400 (Bad Request)} if the cultuuronbebouwd is not valid,
     * or with status {@code 404 (Not Found)} if the cultuuronbebouwd is not found,
     * or with status {@code 500 (Internal Server Error)} if the cultuuronbebouwd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cultuuronbebouwd> partialUpdateCultuuronbebouwd(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cultuuronbebouwd cultuuronbebouwd
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cultuuronbebouwd partially : {}, {}", id, cultuuronbebouwd);
        if (cultuuronbebouwd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cultuuronbebouwd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cultuuronbebouwdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cultuuronbebouwd> result = cultuuronbebouwdRepository
            .findById(cultuuronbebouwd.getId())
            .map(existingCultuuronbebouwd -> {
                if (cultuuronbebouwd.getCultuurcodeonbebouwd() != null) {
                    existingCultuuronbebouwd.setCultuurcodeonbebouwd(cultuuronbebouwd.getCultuurcodeonbebouwd());
                }

                return existingCultuuronbebouwd;
            })
            .map(cultuuronbebouwdRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cultuuronbebouwd.getId().toString())
        );
    }

    /**
     * {@code GET  /cultuuronbebouwds} : get all the cultuuronbebouwds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cultuuronbebouwds in body.
     */
    @GetMapping("")
    public List<Cultuuronbebouwd> getAllCultuuronbebouwds() {
        log.debug("REST request to get all Cultuuronbebouwds");
        return cultuuronbebouwdRepository.findAll();
    }

    /**
     * {@code GET  /cultuuronbebouwds/:id} : get the "id" cultuuronbebouwd.
     *
     * @param id the id of the cultuuronbebouwd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cultuuronbebouwd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cultuuronbebouwd> getCultuuronbebouwd(@PathVariable("id") Long id) {
        log.debug("REST request to get Cultuuronbebouwd : {}", id);
        Optional<Cultuuronbebouwd> cultuuronbebouwd = cultuuronbebouwdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cultuuronbebouwd);
    }

    /**
     * {@code DELETE  /cultuuronbebouwds/:id} : delete the "id" cultuuronbebouwd.
     *
     * @param id the id of the cultuuronbebouwd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCultuuronbebouwd(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cultuuronbebouwd : {}", id);
        cultuuronbebouwdRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
