package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Reisdocumentsoort;
import nl.ritense.demo.repository.ReisdocumentsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Reisdocumentsoort}.
 */
@RestController
@RequestMapping("/api/reisdocumentsoorts")
@Transactional
public class ReisdocumentsoortResource {

    private final Logger log = LoggerFactory.getLogger(ReisdocumentsoortResource.class);

    private static final String ENTITY_NAME = "reisdocumentsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReisdocumentsoortRepository reisdocumentsoortRepository;

    public ReisdocumentsoortResource(ReisdocumentsoortRepository reisdocumentsoortRepository) {
        this.reisdocumentsoortRepository = reisdocumentsoortRepository;
    }

    /**
     * {@code POST  /reisdocumentsoorts} : Create a new reisdocumentsoort.
     *
     * @param reisdocumentsoort the reisdocumentsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reisdocumentsoort, or with status {@code 400 (Bad Request)} if the reisdocumentsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Reisdocumentsoort> createReisdocumentsoort(@RequestBody Reisdocumentsoort reisdocumentsoort)
        throws URISyntaxException {
        log.debug("REST request to save Reisdocumentsoort : {}", reisdocumentsoort);
        if (reisdocumentsoort.getId() != null) {
            throw new BadRequestAlertException("A new reisdocumentsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        reisdocumentsoort = reisdocumentsoortRepository.save(reisdocumentsoort);
        return ResponseEntity.created(new URI("/api/reisdocumentsoorts/" + reisdocumentsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, reisdocumentsoort.getId().toString()))
            .body(reisdocumentsoort);
    }

    /**
     * {@code PUT  /reisdocumentsoorts/:id} : Updates an existing reisdocumentsoort.
     *
     * @param id the id of the reisdocumentsoort to save.
     * @param reisdocumentsoort the reisdocumentsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reisdocumentsoort,
     * or with status {@code 400 (Bad Request)} if the reisdocumentsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reisdocumentsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reisdocumentsoort> updateReisdocumentsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Reisdocumentsoort reisdocumentsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Reisdocumentsoort : {}, {}", id, reisdocumentsoort);
        if (reisdocumentsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reisdocumentsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reisdocumentsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        reisdocumentsoort = reisdocumentsoortRepository.save(reisdocumentsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reisdocumentsoort.getId().toString()))
            .body(reisdocumentsoort);
    }

    /**
     * {@code PATCH  /reisdocumentsoorts/:id} : Partial updates given fields of an existing reisdocumentsoort, field will ignore if it is null
     *
     * @param id the id of the reisdocumentsoort to save.
     * @param reisdocumentsoort the reisdocumentsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reisdocumentsoort,
     * or with status {@code 400 (Bad Request)} if the reisdocumentsoort is not valid,
     * or with status {@code 404 (Not Found)} if the reisdocumentsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the reisdocumentsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Reisdocumentsoort> partialUpdateReisdocumentsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Reisdocumentsoort reisdocumentsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reisdocumentsoort partially : {}, {}", id, reisdocumentsoort);
        if (reisdocumentsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reisdocumentsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reisdocumentsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Reisdocumentsoort> result = reisdocumentsoortRepository
            .findById(reisdocumentsoort.getId())
            .map(existingReisdocumentsoort -> {
                if (reisdocumentsoort.getDatumbegingeldigheidreisdocumentsoort() != null) {
                    existingReisdocumentsoort.setDatumbegingeldigheidreisdocumentsoort(
                        reisdocumentsoort.getDatumbegingeldigheidreisdocumentsoort()
                    );
                }
                if (reisdocumentsoort.getDatumeindegeldigheidreisdocumentsoort() != null) {
                    existingReisdocumentsoort.setDatumeindegeldigheidreisdocumentsoort(
                        reisdocumentsoort.getDatumeindegeldigheidreisdocumentsoort()
                    );
                }
                if (reisdocumentsoort.getReisdocumentcode() != null) {
                    existingReisdocumentsoort.setReisdocumentcode(reisdocumentsoort.getReisdocumentcode());
                }
                if (reisdocumentsoort.getReisdocumentomschrijving() != null) {
                    existingReisdocumentsoort.setReisdocumentomschrijving(reisdocumentsoort.getReisdocumentomschrijving());
                }

                return existingReisdocumentsoort;
            })
            .map(reisdocumentsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reisdocumentsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /reisdocumentsoorts} : get all the reisdocumentsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reisdocumentsoorts in body.
     */
    @GetMapping("")
    public List<Reisdocumentsoort> getAllReisdocumentsoorts() {
        log.debug("REST request to get all Reisdocumentsoorts");
        return reisdocumentsoortRepository.findAll();
    }

    /**
     * {@code GET  /reisdocumentsoorts/:id} : get the "id" reisdocumentsoort.
     *
     * @param id the id of the reisdocumentsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reisdocumentsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reisdocumentsoort> getReisdocumentsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Reisdocumentsoort : {}", id);
        Optional<Reisdocumentsoort> reisdocumentsoort = reisdocumentsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reisdocumentsoort);
    }

    /**
     * {@code DELETE  /reisdocumentsoorts/:id} : delete the "id" reisdocumentsoort.
     *
     * @param id the id of the reisdocumentsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReisdocumentsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Reisdocumentsoort : {}", id);
        reisdocumentsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
