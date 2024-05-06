package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Cultuurcodeonbebouwd;
import nl.ritense.demo.repository.CultuurcodeonbebouwdRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Cultuurcodeonbebouwd}.
 */
@RestController
@RequestMapping("/api/cultuurcodeonbebouwds")
@Transactional
public class CultuurcodeonbebouwdResource {

    private final Logger log = LoggerFactory.getLogger(CultuurcodeonbebouwdResource.class);

    private static final String ENTITY_NAME = "cultuurcodeonbebouwd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CultuurcodeonbebouwdRepository cultuurcodeonbebouwdRepository;

    public CultuurcodeonbebouwdResource(CultuurcodeonbebouwdRepository cultuurcodeonbebouwdRepository) {
        this.cultuurcodeonbebouwdRepository = cultuurcodeonbebouwdRepository;
    }

    /**
     * {@code POST  /cultuurcodeonbebouwds} : Create a new cultuurcodeonbebouwd.
     *
     * @param cultuurcodeonbebouwd the cultuurcodeonbebouwd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cultuurcodeonbebouwd, or with status {@code 400 (Bad Request)} if the cultuurcodeonbebouwd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Cultuurcodeonbebouwd> createCultuurcodeonbebouwd(@RequestBody Cultuurcodeonbebouwd cultuurcodeonbebouwd)
        throws URISyntaxException {
        log.debug("REST request to save Cultuurcodeonbebouwd : {}", cultuurcodeonbebouwd);
        if (cultuurcodeonbebouwd.getId() != null) {
            throw new BadRequestAlertException("A new cultuurcodeonbebouwd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cultuurcodeonbebouwd = cultuurcodeonbebouwdRepository.save(cultuurcodeonbebouwd);
        return ResponseEntity.created(new URI("/api/cultuurcodeonbebouwds/" + cultuurcodeonbebouwd.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cultuurcodeonbebouwd.getId().toString()))
            .body(cultuurcodeonbebouwd);
    }

    /**
     * {@code PUT  /cultuurcodeonbebouwds/:id} : Updates an existing cultuurcodeonbebouwd.
     *
     * @param id the id of the cultuurcodeonbebouwd to save.
     * @param cultuurcodeonbebouwd the cultuurcodeonbebouwd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cultuurcodeonbebouwd,
     * or with status {@code 400 (Bad Request)} if the cultuurcodeonbebouwd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cultuurcodeonbebouwd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cultuurcodeonbebouwd> updateCultuurcodeonbebouwd(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cultuurcodeonbebouwd cultuurcodeonbebouwd
    ) throws URISyntaxException {
        log.debug("REST request to update Cultuurcodeonbebouwd : {}, {}", id, cultuurcodeonbebouwd);
        if (cultuurcodeonbebouwd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cultuurcodeonbebouwd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cultuurcodeonbebouwdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cultuurcodeonbebouwd = cultuurcodeonbebouwdRepository.save(cultuurcodeonbebouwd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cultuurcodeonbebouwd.getId().toString()))
            .body(cultuurcodeonbebouwd);
    }

    /**
     * {@code PATCH  /cultuurcodeonbebouwds/:id} : Partial updates given fields of an existing cultuurcodeonbebouwd, field will ignore if it is null
     *
     * @param id the id of the cultuurcodeonbebouwd to save.
     * @param cultuurcodeonbebouwd the cultuurcodeonbebouwd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cultuurcodeonbebouwd,
     * or with status {@code 400 (Bad Request)} if the cultuurcodeonbebouwd is not valid,
     * or with status {@code 404 (Not Found)} if the cultuurcodeonbebouwd is not found,
     * or with status {@code 500 (Internal Server Error)} if the cultuurcodeonbebouwd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cultuurcodeonbebouwd> partialUpdateCultuurcodeonbebouwd(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cultuurcodeonbebouwd cultuurcodeonbebouwd
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cultuurcodeonbebouwd partially : {}, {}", id, cultuurcodeonbebouwd);
        if (cultuurcodeonbebouwd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cultuurcodeonbebouwd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cultuurcodeonbebouwdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cultuurcodeonbebouwd> result = cultuurcodeonbebouwdRepository
            .findById(cultuurcodeonbebouwd.getId())
            .map(existingCultuurcodeonbebouwd -> {
                if (cultuurcodeonbebouwd.getCultuurcodeonbebouwd() != null) {
                    existingCultuurcodeonbebouwd.setCultuurcodeonbebouwd(cultuurcodeonbebouwd.getCultuurcodeonbebouwd());
                }
                if (cultuurcodeonbebouwd.getDatumbegingeldigheidcultuurcodeonbebouwd() != null) {
                    existingCultuurcodeonbebouwd.setDatumbegingeldigheidcultuurcodeonbebouwd(
                        cultuurcodeonbebouwd.getDatumbegingeldigheidcultuurcodeonbebouwd()
                    );
                }
                if (cultuurcodeonbebouwd.getDatumeindegeldigheidcultuurcodeonbebouwd() != null) {
                    existingCultuurcodeonbebouwd.setDatumeindegeldigheidcultuurcodeonbebouwd(
                        cultuurcodeonbebouwd.getDatumeindegeldigheidcultuurcodeonbebouwd()
                    );
                }
                if (cultuurcodeonbebouwd.getNaamcultuurcodeonbebouwd() != null) {
                    existingCultuurcodeonbebouwd.setNaamcultuurcodeonbebouwd(cultuurcodeonbebouwd.getNaamcultuurcodeonbebouwd());
                }

                return existingCultuurcodeonbebouwd;
            })
            .map(cultuurcodeonbebouwdRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cultuurcodeonbebouwd.getId().toString())
        );
    }

    /**
     * {@code GET  /cultuurcodeonbebouwds} : get all the cultuurcodeonbebouwds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cultuurcodeonbebouwds in body.
     */
    @GetMapping("")
    public List<Cultuurcodeonbebouwd> getAllCultuurcodeonbebouwds() {
        log.debug("REST request to get all Cultuurcodeonbebouwds");
        return cultuurcodeonbebouwdRepository.findAll();
    }

    /**
     * {@code GET  /cultuurcodeonbebouwds/:id} : get the "id" cultuurcodeonbebouwd.
     *
     * @param id the id of the cultuurcodeonbebouwd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cultuurcodeonbebouwd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cultuurcodeonbebouwd> getCultuurcodeonbebouwd(@PathVariable("id") Long id) {
        log.debug("REST request to get Cultuurcodeonbebouwd : {}", id);
        Optional<Cultuurcodeonbebouwd> cultuurcodeonbebouwd = cultuurcodeonbebouwdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cultuurcodeonbebouwd);
    }

    /**
     * {@code DELETE  /cultuurcodeonbebouwds/:id} : delete the "id" cultuurcodeonbebouwd.
     *
     * @param id the id of the cultuurcodeonbebouwd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCultuurcodeonbebouwd(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cultuurcodeonbebouwd : {}", id);
        cultuurcodeonbebouwdRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
