package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Redenverkrijgingnationaliteit;
import nl.ritense.demo.repository.RedenverkrijgingnationaliteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Redenverkrijgingnationaliteit}.
 */
@RestController
@RequestMapping("/api/redenverkrijgingnationaliteits")
@Transactional
public class RedenverkrijgingnationaliteitResource {

    private final Logger log = LoggerFactory.getLogger(RedenverkrijgingnationaliteitResource.class);

    private static final String ENTITY_NAME = "redenverkrijgingnationaliteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RedenverkrijgingnationaliteitRepository redenverkrijgingnationaliteitRepository;

    public RedenverkrijgingnationaliteitResource(RedenverkrijgingnationaliteitRepository redenverkrijgingnationaliteitRepository) {
        this.redenverkrijgingnationaliteitRepository = redenverkrijgingnationaliteitRepository;
    }

    /**
     * {@code POST  /redenverkrijgingnationaliteits} : Create a new redenverkrijgingnationaliteit.
     *
     * @param redenverkrijgingnationaliteit the redenverkrijgingnationaliteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new redenverkrijgingnationaliteit, or with status {@code 400 (Bad Request)} if the redenverkrijgingnationaliteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Redenverkrijgingnationaliteit> createRedenverkrijgingnationaliteit(
        @RequestBody Redenverkrijgingnationaliteit redenverkrijgingnationaliteit
    ) throws URISyntaxException {
        log.debug("REST request to save Redenverkrijgingnationaliteit : {}", redenverkrijgingnationaliteit);
        if (redenverkrijgingnationaliteit.getId() != null) {
            throw new BadRequestAlertException("A new redenverkrijgingnationaliteit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        redenverkrijgingnationaliteit = redenverkrijgingnationaliteitRepository.save(redenverkrijgingnationaliteit);
        return ResponseEntity.created(new URI("/api/redenverkrijgingnationaliteits/" + redenverkrijgingnationaliteit.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, redenverkrijgingnationaliteit.getId().toString())
            )
            .body(redenverkrijgingnationaliteit);
    }

    /**
     * {@code PUT  /redenverkrijgingnationaliteits/:id} : Updates an existing redenverkrijgingnationaliteit.
     *
     * @param id the id of the redenverkrijgingnationaliteit to save.
     * @param redenverkrijgingnationaliteit the redenverkrijgingnationaliteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redenverkrijgingnationaliteit,
     * or with status {@code 400 (Bad Request)} if the redenverkrijgingnationaliteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the redenverkrijgingnationaliteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Redenverkrijgingnationaliteit> updateRedenverkrijgingnationaliteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Redenverkrijgingnationaliteit redenverkrijgingnationaliteit
    ) throws URISyntaxException {
        log.debug("REST request to update Redenverkrijgingnationaliteit : {}, {}", id, redenverkrijgingnationaliteit);
        if (redenverkrijgingnationaliteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redenverkrijgingnationaliteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redenverkrijgingnationaliteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        redenverkrijgingnationaliteit = redenverkrijgingnationaliteitRepository.save(redenverkrijgingnationaliteit);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, redenverkrijgingnationaliteit.getId().toString())
            )
            .body(redenverkrijgingnationaliteit);
    }

    /**
     * {@code PATCH  /redenverkrijgingnationaliteits/:id} : Partial updates given fields of an existing redenverkrijgingnationaliteit, field will ignore if it is null
     *
     * @param id the id of the redenverkrijgingnationaliteit to save.
     * @param redenverkrijgingnationaliteit the redenverkrijgingnationaliteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated redenverkrijgingnationaliteit,
     * or with status {@code 400 (Bad Request)} if the redenverkrijgingnationaliteit is not valid,
     * or with status {@code 404 (Not Found)} if the redenverkrijgingnationaliteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the redenverkrijgingnationaliteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Redenverkrijgingnationaliteit> partialUpdateRedenverkrijgingnationaliteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Redenverkrijgingnationaliteit redenverkrijgingnationaliteit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Redenverkrijgingnationaliteit partially : {}, {}", id, redenverkrijgingnationaliteit);
        if (redenverkrijgingnationaliteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, redenverkrijgingnationaliteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!redenverkrijgingnationaliteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Redenverkrijgingnationaliteit> result = redenverkrijgingnationaliteitRepository
            .findById(redenverkrijgingnationaliteit.getId())
            .map(existingRedenverkrijgingnationaliteit -> {
                if (redenverkrijgingnationaliteit.getDatumaanvanggeldigheidverkrijging() != null) {
                    existingRedenverkrijgingnationaliteit.setDatumaanvanggeldigheidverkrijging(
                        redenverkrijgingnationaliteit.getDatumaanvanggeldigheidverkrijging()
                    );
                }
                if (redenverkrijgingnationaliteit.getDatumeindegeldigheidverkrijging() != null) {
                    existingRedenverkrijgingnationaliteit.setDatumeindegeldigheidverkrijging(
                        redenverkrijgingnationaliteit.getDatumeindegeldigheidverkrijging()
                    );
                }
                if (redenverkrijgingnationaliteit.getOmschrijvingverkrijging() != null) {
                    existingRedenverkrijgingnationaliteit.setOmschrijvingverkrijging(
                        redenverkrijgingnationaliteit.getOmschrijvingverkrijging()
                    );
                }
                if (redenverkrijgingnationaliteit.getRedennummerverkrijging() != null) {
                    existingRedenverkrijgingnationaliteit.setRedennummerverkrijging(
                        redenverkrijgingnationaliteit.getRedennummerverkrijging()
                    );
                }

                return existingRedenverkrijgingnationaliteit;
            })
            .map(redenverkrijgingnationaliteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, redenverkrijgingnationaliteit.getId().toString())
        );
    }

    /**
     * {@code GET  /redenverkrijgingnationaliteits} : get all the redenverkrijgingnationaliteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of redenverkrijgingnationaliteits in body.
     */
    @GetMapping("")
    public List<Redenverkrijgingnationaliteit> getAllRedenverkrijgingnationaliteits() {
        log.debug("REST request to get all Redenverkrijgingnationaliteits");
        return redenverkrijgingnationaliteitRepository.findAll();
    }

    /**
     * {@code GET  /redenverkrijgingnationaliteits/:id} : get the "id" redenverkrijgingnationaliteit.
     *
     * @param id the id of the redenverkrijgingnationaliteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the redenverkrijgingnationaliteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Redenverkrijgingnationaliteit> getRedenverkrijgingnationaliteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Redenverkrijgingnationaliteit : {}", id);
        Optional<Redenverkrijgingnationaliteit> redenverkrijgingnationaliteit = redenverkrijgingnationaliteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(redenverkrijgingnationaliteit);
    }

    /**
     * {@code DELETE  /redenverkrijgingnationaliteits/:id} : delete the "id" redenverkrijgingnationaliteit.
     *
     * @param id the id of the redenverkrijgingnationaliteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRedenverkrijgingnationaliteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Redenverkrijgingnationaliteit : {}", id);
        redenverkrijgingnationaliteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
