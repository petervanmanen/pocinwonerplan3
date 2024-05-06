package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Reisdocument;
import nl.ritense.demo.repository.ReisdocumentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Reisdocument}.
 */
@RestController
@RequestMapping("/api/reisdocuments")
@Transactional
public class ReisdocumentResource {

    private final Logger log = LoggerFactory.getLogger(ReisdocumentResource.class);

    private static final String ENTITY_NAME = "reisdocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReisdocumentRepository reisdocumentRepository;

    public ReisdocumentResource(ReisdocumentRepository reisdocumentRepository) {
        this.reisdocumentRepository = reisdocumentRepository;
    }

    /**
     * {@code POST  /reisdocuments} : Create a new reisdocument.
     *
     * @param reisdocument the reisdocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reisdocument, or with status {@code 400 (Bad Request)} if the reisdocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Reisdocument> createReisdocument(@RequestBody Reisdocument reisdocument) throws URISyntaxException {
        log.debug("REST request to save Reisdocument : {}", reisdocument);
        if (reisdocument.getId() != null) {
            throw new BadRequestAlertException("A new reisdocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        reisdocument = reisdocumentRepository.save(reisdocument);
        return ResponseEntity.created(new URI("/api/reisdocuments/" + reisdocument.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, reisdocument.getId().toString()))
            .body(reisdocument);
    }

    /**
     * {@code PUT  /reisdocuments/:id} : Updates an existing reisdocument.
     *
     * @param id the id of the reisdocument to save.
     * @param reisdocument the reisdocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reisdocument,
     * or with status {@code 400 (Bad Request)} if the reisdocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reisdocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reisdocument> updateReisdocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Reisdocument reisdocument
    ) throws URISyntaxException {
        log.debug("REST request to update Reisdocument : {}, {}", id, reisdocument);
        if (reisdocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reisdocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reisdocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        reisdocument = reisdocumentRepository.save(reisdocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reisdocument.getId().toString()))
            .body(reisdocument);
    }

    /**
     * {@code PATCH  /reisdocuments/:id} : Partial updates given fields of an existing reisdocument, field will ignore if it is null
     *
     * @param id the id of the reisdocument to save.
     * @param reisdocument the reisdocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reisdocument,
     * or with status {@code 400 (Bad Request)} if the reisdocument is not valid,
     * or with status {@code 404 (Not Found)} if the reisdocument is not found,
     * or with status {@code 500 (Internal Server Error)} if the reisdocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Reisdocument> partialUpdateReisdocument(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Reisdocument reisdocument
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reisdocument partially : {}, {}", id, reisdocument);
        if (reisdocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reisdocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reisdocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Reisdocument> result = reisdocumentRepository
            .findById(reisdocument.getId())
            .map(existingReisdocument -> {
                if (reisdocument.getAanduidinginhoudingvermissing() != null) {
                    existingReisdocument.setAanduidinginhoudingvermissing(reisdocument.getAanduidinginhoudingvermissing());
                }
                if (reisdocument.getAutoriteitvanafgifte() != null) {
                    existingReisdocument.setAutoriteitvanafgifte(reisdocument.getAutoriteitvanafgifte());
                }
                if (reisdocument.getDatumeindegeldigheiddocument() != null) {
                    existingReisdocument.setDatumeindegeldigheiddocument(reisdocument.getDatumeindegeldigheiddocument());
                }
                if (reisdocument.getDatumingangdocument() != null) {
                    existingReisdocument.setDatumingangdocument(reisdocument.getDatumingangdocument());
                }
                if (reisdocument.getDatuminhoudingofvermissing() != null) {
                    existingReisdocument.setDatuminhoudingofvermissing(reisdocument.getDatuminhoudingofvermissing());
                }
                if (reisdocument.getDatumuitgifte() != null) {
                    existingReisdocument.setDatumuitgifte(reisdocument.getDatumuitgifte());
                }
                if (reisdocument.getReisdocumentnummer() != null) {
                    existingReisdocument.setReisdocumentnummer(reisdocument.getReisdocumentnummer());
                }
                if (reisdocument.getSoort() != null) {
                    existingReisdocument.setSoort(reisdocument.getSoort());
                }

                return existingReisdocument;
            })
            .map(reisdocumentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reisdocument.getId().toString())
        );
    }

    /**
     * {@code GET  /reisdocuments} : get all the reisdocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reisdocuments in body.
     */
    @GetMapping("")
    public List<Reisdocument> getAllReisdocuments() {
        log.debug("REST request to get all Reisdocuments");
        return reisdocumentRepository.findAll();
    }

    /**
     * {@code GET  /reisdocuments/:id} : get the "id" reisdocument.
     *
     * @param id the id of the reisdocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reisdocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reisdocument> getReisdocument(@PathVariable("id") Long id) {
        log.debug("REST request to get Reisdocument : {}", id);
        Optional<Reisdocument> reisdocument = reisdocumentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reisdocument);
    }

    /**
     * {@code DELETE  /reisdocuments/:id} : delete the "id" reisdocument.
     *
     * @param id the id of the reisdocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReisdocument(@PathVariable("id") Long id) {
        log.debug("REST request to delete Reisdocument : {}", id);
        reisdocumentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
