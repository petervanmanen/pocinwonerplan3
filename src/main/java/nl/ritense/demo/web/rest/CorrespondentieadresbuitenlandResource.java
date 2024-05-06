package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Correspondentieadresbuitenland;
import nl.ritense.demo.repository.CorrespondentieadresbuitenlandRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Correspondentieadresbuitenland}.
 */
@RestController
@RequestMapping("/api/correspondentieadresbuitenlands")
@Transactional
public class CorrespondentieadresbuitenlandResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentieadresbuitenlandResource.class);

    private static final String ENTITY_NAME = "correspondentieadresbuitenland";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrespondentieadresbuitenlandRepository correspondentieadresbuitenlandRepository;

    public CorrespondentieadresbuitenlandResource(CorrespondentieadresbuitenlandRepository correspondentieadresbuitenlandRepository) {
        this.correspondentieadresbuitenlandRepository = correspondentieadresbuitenlandRepository;
    }

    /**
     * {@code POST  /correspondentieadresbuitenlands} : Create a new correspondentieadresbuitenland.
     *
     * @param correspondentieadresbuitenland the correspondentieadresbuitenland to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correspondentieadresbuitenland, or with status {@code 400 (Bad Request)} if the correspondentieadresbuitenland has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Correspondentieadresbuitenland> createCorrespondentieadresbuitenland(
        @RequestBody Correspondentieadresbuitenland correspondentieadresbuitenland
    ) throws URISyntaxException {
        log.debug("REST request to save Correspondentieadresbuitenland : {}", correspondentieadresbuitenland);
        if (correspondentieadresbuitenland.getId() != null) {
            throw new BadRequestAlertException("A new correspondentieadresbuitenland cannot already have an ID", ENTITY_NAME, "idexists");
        }
        correspondentieadresbuitenland = correspondentieadresbuitenlandRepository.save(correspondentieadresbuitenland);
        return ResponseEntity.created(new URI("/api/correspondentieadresbuitenlands/" + correspondentieadresbuitenland.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, correspondentieadresbuitenland.getId().toString())
            )
            .body(correspondentieadresbuitenland);
    }

    /**
     * {@code PUT  /correspondentieadresbuitenlands/:id} : Updates an existing correspondentieadresbuitenland.
     *
     * @param id the id of the correspondentieadresbuitenland to save.
     * @param correspondentieadresbuitenland the correspondentieadresbuitenland to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentieadresbuitenland,
     * or with status {@code 400 (Bad Request)} if the correspondentieadresbuitenland is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correspondentieadresbuitenland couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Correspondentieadresbuitenland> updateCorrespondentieadresbuitenland(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Correspondentieadresbuitenland correspondentieadresbuitenland
    ) throws URISyntaxException {
        log.debug("REST request to update Correspondentieadresbuitenland : {}, {}", id, correspondentieadresbuitenland);
        if (correspondentieadresbuitenland.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, correspondentieadresbuitenland.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!correspondentieadresbuitenlandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        correspondentieadresbuitenland = correspondentieadresbuitenlandRepository.save(correspondentieadresbuitenland);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, correspondentieadresbuitenland.getId().toString())
            )
            .body(correspondentieadresbuitenland);
    }

    /**
     * {@code PATCH  /correspondentieadresbuitenlands/:id} : Partial updates given fields of an existing correspondentieadresbuitenland, field will ignore if it is null
     *
     * @param id the id of the correspondentieadresbuitenland to save.
     * @param correspondentieadresbuitenland the correspondentieadresbuitenland to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentieadresbuitenland,
     * or with status {@code 400 (Bad Request)} if the correspondentieadresbuitenland is not valid,
     * or with status {@code 404 (Not Found)} if the correspondentieadresbuitenland is not found,
     * or with status {@code 500 (Internal Server Error)} if the correspondentieadresbuitenland couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Correspondentieadresbuitenland> partialUpdateCorrespondentieadresbuitenland(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Correspondentieadresbuitenland correspondentieadresbuitenland
    ) throws URISyntaxException {
        log.debug("REST request to partial update Correspondentieadresbuitenland partially : {}, {}", id, correspondentieadresbuitenland);
        if (correspondentieadresbuitenland.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, correspondentieadresbuitenland.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!correspondentieadresbuitenlandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Correspondentieadresbuitenland> result = correspondentieadresbuitenlandRepository
            .findById(correspondentieadresbuitenland.getId())
            .map(existingCorrespondentieadresbuitenland -> {
                if (correspondentieadresbuitenland.getAdresbuitenland1() != null) {
                    existingCorrespondentieadresbuitenland.setAdresbuitenland1(correspondentieadresbuitenland.getAdresbuitenland1());
                }
                if (correspondentieadresbuitenland.getAdresbuitenland2() != null) {
                    existingCorrespondentieadresbuitenland.setAdresbuitenland2(correspondentieadresbuitenland.getAdresbuitenland2());
                }
                if (correspondentieadresbuitenland.getAdresbuitenland3() != null) {
                    existingCorrespondentieadresbuitenland.setAdresbuitenland3(correspondentieadresbuitenland.getAdresbuitenland3());
                }
                if (correspondentieadresbuitenland.getAdresbuitenland4() != null) {
                    existingCorrespondentieadresbuitenland.setAdresbuitenland4(correspondentieadresbuitenland.getAdresbuitenland4());
                }
                if (correspondentieadresbuitenland.getAdresbuitenland5() != null) {
                    existingCorrespondentieadresbuitenland.setAdresbuitenland5(correspondentieadresbuitenland.getAdresbuitenland5());
                }
                if (correspondentieadresbuitenland.getAdresbuitenland6() != null) {
                    existingCorrespondentieadresbuitenland.setAdresbuitenland6(correspondentieadresbuitenland.getAdresbuitenland6());
                }
                if (correspondentieadresbuitenland.getLandcorrespondentieadres() != null) {
                    existingCorrespondentieadresbuitenland.setLandcorrespondentieadres(
                        correspondentieadresbuitenland.getLandcorrespondentieadres()
                    );
                }

                return existingCorrespondentieadresbuitenland;
            })
            .map(correspondentieadresbuitenlandRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, correspondentieadresbuitenland.getId().toString())
        );
    }

    /**
     * {@code GET  /correspondentieadresbuitenlands} : get all the correspondentieadresbuitenlands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correspondentieadresbuitenlands in body.
     */
    @GetMapping("")
    public List<Correspondentieadresbuitenland> getAllCorrespondentieadresbuitenlands() {
        log.debug("REST request to get all Correspondentieadresbuitenlands");
        return correspondentieadresbuitenlandRepository.findAll();
    }

    /**
     * {@code GET  /correspondentieadresbuitenlands/:id} : get the "id" correspondentieadresbuitenland.
     *
     * @param id the id of the correspondentieadresbuitenland to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correspondentieadresbuitenland, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Correspondentieadresbuitenland> getCorrespondentieadresbuitenland(@PathVariable("id") Long id) {
        log.debug("REST request to get Correspondentieadresbuitenland : {}", id);
        Optional<Correspondentieadresbuitenland> correspondentieadresbuitenland = correspondentieadresbuitenlandRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(correspondentieadresbuitenland);
    }

    /**
     * {@code DELETE  /correspondentieadresbuitenlands/:id} : delete the "id" correspondentieadresbuitenland.
     *
     * @param id the id of the correspondentieadresbuitenland to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCorrespondentieadresbuitenland(@PathVariable("id") Long id) {
        log.debug("REST request to delete Correspondentieadresbuitenland : {}", id);
        correspondentieadresbuitenlandRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
