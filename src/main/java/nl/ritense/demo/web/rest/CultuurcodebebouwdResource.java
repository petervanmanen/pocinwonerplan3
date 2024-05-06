package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Cultuurcodebebouwd;
import nl.ritense.demo.repository.CultuurcodebebouwdRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Cultuurcodebebouwd}.
 */
@RestController
@RequestMapping("/api/cultuurcodebebouwds")
@Transactional
public class CultuurcodebebouwdResource {

    private final Logger log = LoggerFactory.getLogger(CultuurcodebebouwdResource.class);

    private static final String ENTITY_NAME = "cultuurcodebebouwd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CultuurcodebebouwdRepository cultuurcodebebouwdRepository;

    public CultuurcodebebouwdResource(CultuurcodebebouwdRepository cultuurcodebebouwdRepository) {
        this.cultuurcodebebouwdRepository = cultuurcodebebouwdRepository;
    }

    /**
     * {@code POST  /cultuurcodebebouwds} : Create a new cultuurcodebebouwd.
     *
     * @param cultuurcodebebouwd the cultuurcodebebouwd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cultuurcodebebouwd, or with status {@code 400 (Bad Request)} if the cultuurcodebebouwd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Cultuurcodebebouwd> createCultuurcodebebouwd(@RequestBody Cultuurcodebebouwd cultuurcodebebouwd)
        throws URISyntaxException {
        log.debug("REST request to save Cultuurcodebebouwd : {}", cultuurcodebebouwd);
        if (cultuurcodebebouwd.getId() != null) {
            throw new BadRequestAlertException("A new cultuurcodebebouwd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cultuurcodebebouwd = cultuurcodebebouwdRepository.save(cultuurcodebebouwd);
        return ResponseEntity.created(new URI("/api/cultuurcodebebouwds/" + cultuurcodebebouwd.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cultuurcodebebouwd.getId().toString()))
            .body(cultuurcodebebouwd);
    }

    /**
     * {@code PUT  /cultuurcodebebouwds/:id} : Updates an existing cultuurcodebebouwd.
     *
     * @param id the id of the cultuurcodebebouwd to save.
     * @param cultuurcodebebouwd the cultuurcodebebouwd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cultuurcodebebouwd,
     * or with status {@code 400 (Bad Request)} if the cultuurcodebebouwd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cultuurcodebebouwd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cultuurcodebebouwd> updateCultuurcodebebouwd(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cultuurcodebebouwd cultuurcodebebouwd
    ) throws URISyntaxException {
        log.debug("REST request to update Cultuurcodebebouwd : {}, {}", id, cultuurcodebebouwd);
        if (cultuurcodebebouwd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cultuurcodebebouwd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cultuurcodebebouwdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cultuurcodebebouwd = cultuurcodebebouwdRepository.save(cultuurcodebebouwd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cultuurcodebebouwd.getId().toString()))
            .body(cultuurcodebebouwd);
    }

    /**
     * {@code PATCH  /cultuurcodebebouwds/:id} : Partial updates given fields of an existing cultuurcodebebouwd, field will ignore if it is null
     *
     * @param id the id of the cultuurcodebebouwd to save.
     * @param cultuurcodebebouwd the cultuurcodebebouwd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cultuurcodebebouwd,
     * or with status {@code 400 (Bad Request)} if the cultuurcodebebouwd is not valid,
     * or with status {@code 404 (Not Found)} if the cultuurcodebebouwd is not found,
     * or with status {@code 500 (Internal Server Error)} if the cultuurcodebebouwd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cultuurcodebebouwd> partialUpdateCultuurcodebebouwd(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cultuurcodebebouwd cultuurcodebebouwd
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cultuurcodebebouwd partially : {}, {}", id, cultuurcodebebouwd);
        if (cultuurcodebebouwd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cultuurcodebebouwd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cultuurcodebebouwdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cultuurcodebebouwd> result = cultuurcodebebouwdRepository
            .findById(cultuurcodebebouwd.getId())
            .map(existingCultuurcodebebouwd -> {
                if (cultuurcodebebouwd.getCultuurcodebebouwd() != null) {
                    existingCultuurcodebebouwd.setCultuurcodebebouwd(cultuurcodebebouwd.getCultuurcodebebouwd());
                }
                if (cultuurcodebebouwd.getDatumbegingeldigheidcultuurcodebebouwd() != null) {
                    existingCultuurcodebebouwd.setDatumbegingeldigheidcultuurcodebebouwd(
                        cultuurcodebebouwd.getDatumbegingeldigheidcultuurcodebebouwd()
                    );
                }
                if (cultuurcodebebouwd.getDatumeindegeldigheidcultuurcodebebouwd() != null) {
                    existingCultuurcodebebouwd.setDatumeindegeldigheidcultuurcodebebouwd(
                        cultuurcodebebouwd.getDatumeindegeldigheidcultuurcodebebouwd()
                    );
                }
                if (cultuurcodebebouwd.getNaamcultuurcodebebouwd() != null) {
                    existingCultuurcodebebouwd.setNaamcultuurcodebebouwd(cultuurcodebebouwd.getNaamcultuurcodebebouwd());
                }

                return existingCultuurcodebebouwd;
            })
            .map(cultuurcodebebouwdRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cultuurcodebebouwd.getId().toString())
        );
    }

    /**
     * {@code GET  /cultuurcodebebouwds} : get all the cultuurcodebebouwds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cultuurcodebebouwds in body.
     */
    @GetMapping("")
    public List<Cultuurcodebebouwd> getAllCultuurcodebebouwds() {
        log.debug("REST request to get all Cultuurcodebebouwds");
        return cultuurcodebebouwdRepository.findAll();
    }

    /**
     * {@code GET  /cultuurcodebebouwds/:id} : get the "id" cultuurcodebebouwd.
     *
     * @param id the id of the cultuurcodebebouwd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cultuurcodebebouwd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cultuurcodebebouwd> getCultuurcodebebouwd(@PathVariable("id") Long id) {
        log.debug("REST request to get Cultuurcodebebouwd : {}", id);
        Optional<Cultuurcodebebouwd> cultuurcodebebouwd = cultuurcodebebouwdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cultuurcodebebouwd);
    }

    /**
     * {@code DELETE  /cultuurcodebebouwds/:id} : delete the "id" cultuurcodebebouwd.
     *
     * @param id the id of the cultuurcodebebouwd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCultuurcodebebouwd(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cultuurcodebebouwd : {}", id);
        cultuurcodebebouwdRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
