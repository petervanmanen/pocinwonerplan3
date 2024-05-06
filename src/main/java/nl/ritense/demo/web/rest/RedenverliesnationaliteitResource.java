package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Redenverliesnationaliteit;
import nl.ritense.demo.repository.RedenverliesnationaliteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Redenverliesnationaliteit}.
 */
@RestController
@RequestMapping("/api/redenverliesnationaliteits")
@Transactional
public class RedenverliesnationaliteitResource {

    private final Logger log = LoggerFactory.getLogger(RedenverliesnationaliteitResource.class);

    private static final String ENTITY_NAME = "redenverliesnationaliteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RedenverliesnationaliteitRepository redenverliesnationaliteitRepository;

    public RedenverliesnationaliteitResource(RedenverliesnationaliteitRepository redenverliesnationaliteitRepository) {
        this.redenverliesnationaliteitRepository = redenverliesnationaliteitRepository;
    }

    /**
     * {@code POST  /redenverliesnationaliteits} : Create a new redenverliesnationaliteit.
     *
     * @param redenverliesnationaliteit the redenverliesnationaliteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new redenverliesnationaliteit, or with status {@code 400 (Bad Request)} if the redenverliesnationaliteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Redenverliesnationaliteit> createRedenverliesnationaliteit(
        @RequestBody Redenverliesnationaliteit redenverliesnationaliteit
    ) throws URISyntaxException {
        log.debug("REST request to save Redenverliesnationaliteit : {}", redenverliesnationaliteit);
        if (redenverliesnationaliteit.getId() != null) {
            throw new BadRequestAlertException("A new redenverliesnationaliteit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        redenverliesnationaliteit = redenverliesnationaliteitRepository.save(redenverliesnationaliteit);
        return ResponseEntity.created(new URI("/api/redenverliesnationaliteits/" + redenverliesnationaliteit.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, redenverliesnationaliteit.getId().toString())
            )
            .body(redenverliesnationaliteit);
    }

    /**
     * {@code PUT  /redenverliesnationaliteits/:id} : Updates an existing redenverliesnationaliteit.
     *
     * @param id the id of the redenverliesnationaliteit to save.
     * @param redenverliesnationaliteit the redenverliesnationaliteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redenverliesnationaliteit,
     * or with status {@code 400 (Bad Request)} if the redenverliesnationaliteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the redenverliesnationaliteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Redenverliesnationaliteit> updateRedenverliesnationaliteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Redenverliesnationaliteit redenverliesnationaliteit
    ) throws URISyntaxException {
        log.debug("REST request to update Redenverliesnationaliteit : {}, {}", id, redenverliesnationaliteit);
        if (redenverliesnationaliteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redenverliesnationaliteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redenverliesnationaliteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        redenverliesnationaliteit = redenverliesnationaliteitRepository.save(redenverliesnationaliteit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, redenverliesnationaliteit.getId().toString()))
            .body(redenverliesnationaliteit);
    }

    /**
     * {@code PATCH  /redenverliesnationaliteits/:id} : Partial updates given fields of an existing redenverliesnationaliteit, field will ignore if it is null
     *
     * @param id the id of the redenverliesnationaliteit to save.
     * @param redenverliesnationaliteit the redenverliesnationaliteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redenverliesnationaliteit,
     * or with status {@code 400 (Bad Request)} if the redenverliesnationaliteit is not valid,
     * or with status {@code 404 (Not Found)} if the redenverliesnationaliteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the redenverliesnationaliteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Redenverliesnationaliteit> partialUpdateRedenverliesnationaliteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Redenverliesnationaliteit redenverliesnationaliteit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Redenverliesnationaliteit partially : {}, {}", id, redenverliesnationaliteit);
        if (redenverliesnationaliteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redenverliesnationaliteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redenverliesnationaliteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Redenverliesnationaliteit> result = redenverliesnationaliteitRepository
            .findById(redenverliesnationaliteit.getId())
            .map(existingRedenverliesnationaliteit -> {
                if (redenverliesnationaliteit.getDatumaanvanggeldigheidverlies() != null) {
                    existingRedenverliesnationaliteit.setDatumaanvanggeldigheidverlies(
                        redenverliesnationaliteit.getDatumaanvanggeldigheidverlies()
                    );
                }
                if (redenverliesnationaliteit.getDatumeindegeldigheidverlies() != null) {
                    existingRedenverliesnationaliteit.setDatumeindegeldigheidverlies(
                        redenverliesnationaliteit.getDatumeindegeldigheidverlies()
                    );
                }
                if (redenverliesnationaliteit.getOmschrijvingverlies() != null) {
                    existingRedenverliesnationaliteit.setOmschrijvingverlies(redenverliesnationaliteit.getOmschrijvingverlies());
                }
                if (redenverliesnationaliteit.getRedennummerverlies() != null) {
                    existingRedenverliesnationaliteit.setRedennummerverlies(redenverliesnationaliteit.getRedennummerverlies());
                }

                return existingRedenverliesnationaliteit;
            })
            .map(redenverliesnationaliteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, redenverliesnationaliteit.getId().toString())
        );
    }

    /**
     * {@code GET  /redenverliesnationaliteits} : get all the redenverliesnationaliteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of redenverliesnationaliteits in body.
     */
    @GetMapping("")
    public List<Redenverliesnationaliteit> getAllRedenverliesnationaliteits() {
        log.debug("REST request to get all Redenverliesnationaliteits");
        return redenverliesnationaliteitRepository.findAll();
    }

    /**
     * {@code GET  /redenverliesnationaliteits/:id} : get the "id" redenverliesnationaliteit.
     *
     * @param id the id of the redenverliesnationaliteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the redenverliesnationaliteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Redenverliesnationaliteit> getRedenverliesnationaliteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Redenverliesnationaliteit : {}", id);
        Optional<Redenverliesnationaliteit> redenverliesnationaliteit = redenverliesnationaliteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(redenverliesnationaliteit);
    }

    /**
     * {@code DELETE  /redenverliesnationaliteits/:id} : delete the "id" redenverliesnationaliteit.
     *
     * @param id the id of the redenverliesnationaliteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRedenverliesnationaliteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Redenverliesnationaliteit : {}", id);
        redenverliesnationaliteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
