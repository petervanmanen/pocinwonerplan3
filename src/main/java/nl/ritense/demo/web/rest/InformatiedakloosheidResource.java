package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Informatiedakloosheid;
import nl.ritense.demo.repository.InformatiedakloosheidRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Informatiedakloosheid}.
 */
@RestController
@RequestMapping("/api/informatiedakloosheids")
@Transactional
public class InformatiedakloosheidResource {

    private final Logger log = LoggerFactory.getLogger(InformatiedakloosheidResource.class);

    private static final String ENTITY_NAME = "informatiedakloosheid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InformatiedakloosheidRepository informatiedakloosheidRepository;

    public InformatiedakloosheidResource(InformatiedakloosheidRepository informatiedakloosheidRepository) {
        this.informatiedakloosheidRepository = informatiedakloosheidRepository;
    }

    /**
     * {@code POST  /informatiedakloosheids} : Create a new informatiedakloosheid.
     *
     * @param informatiedakloosheid the informatiedakloosheid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informatiedakloosheid, or with status {@code 400 (Bad Request)} if the informatiedakloosheid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Informatiedakloosheid> createInformatiedakloosheid(@RequestBody Informatiedakloosheid informatiedakloosheid)
        throws URISyntaxException {
        log.debug("REST request to save Informatiedakloosheid : {}", informatiedakloosheid);
        if (informatiedakloosheid.getId() != null) {
            throw new BadRequestAlertException("A new informatiedakloosheid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        informatiedakloosheid = informatiedakloosheidRepository.save(informatiedakloosheid);
        return ResponseEntity.created(new URI("/api/informatiedakloosheids/" + informatiedakloosheid.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, informatiedakloosheid.getId().toString()))
            .body(informatiedakloosheid);
    }

    /**
     * {@code PUT  /informatiedakloosheids/:id} : Updates an existing informatiedakloosheid.
     *
     * @param id the id of the informatiedakloosheid to save.
     * @param informatiedakloosheid the informatiedakloosheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informatiedakloosheid,
     * or with status {@code 400 (Bad Request)} if the informatiedakloosheid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informatiedakloosheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Informatiedakloosheid> updateInformatiedakloosheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Informatiedakloosheid informatiedakloosheid
    ) throws URISyntaxException {
        log.debug("REST request to update Informatiedakloosheid : {}, {}", id, informatiedakloosheid);
        if (informatiedakloosheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informatiedakloosheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informatiedakloosheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        informatiedakloosheid = informatiedakloosheidRepository.save(informatiedakloosheid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, informatiedakloosheid.getId().toString()))
            .body(informatiedakloosheid);
    }

    /**
     * {@code PATCH  /informatiedakloosheids/:id} : Partial updates given fields of an existing informatiedakloosheid, field will ignore if it is null
     *
     * @param id the id of the informatiedakloosheid to save.
     * @param informatiedakloosheid the informatiedakloosheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informatiedakloosheid,
     * or with status {@code 400 (Bad Request)} if the informatiedakloosheid is not valid,
     * or with status {@code 404 (Not Found)} if the informatiedakloosheid is not found,
     * or with status {@code 500 (Internal Server Error)} if the informatiedakloosheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Informatiedakloosheid> partialUpdateInformatiedakloosheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Informatiedakloosheid informatiedakloosheid
    ) throws URISyntaxException {
        log.debug("REST request to partial update Informatiedakloosheid partially : {}, {}", id, informatiedakloosheid);
        if (informatiedakloosheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informatiedakloosheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informatiedakloosheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Informatiedakloosheid> result = informatiedakloosheidRepository
            .findById(informatiedakloosheid.getId())
            .map(existingInformatiedakloosheid -> {
                if (informatiedakloosheid.getDatumeinde() != null) {
                    existingInformatiedakloosheid.setDatumeinde(informatiedakloosheid.getDatumeinde());
                }
                if (informatiedakloosheid.getDatumstart() != null) {
                    existingInformatiedakloosheid.setDatumstart(informatiedakloosheid.getDatumstart());
                }
                if (informatiedakloosheid.getGemeenteoorsprong() != null) {
                    existingInformatiedakloosheid.setGemeenteoorsprong(informatiedakloosheid.getGemeenteoorsprong());
                }
                if (informatiedakloosheid.getToestemminggemeentelijkbriefadres() != null) {
                    existingInformatiedakloosheid.setToestemminggemeentelijkbriefadres(
                        informatiedakloosheid.getToestemminggemeentelijkbriefadres()
                    );
                }
                if (informatiedakloosheid.getToestemmingnachtopvang() != null) {
                    existingInformatiedakloosheid.setToestemmingnachtopvang(informatiedakloosheid.getToestemmingnachtopvang());
                }

                return existingInformatiedakloosheid;
            })
            .map(informatiedakloosheidRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, informatiedakloosheid.getId().toString())
        );
    }

    /**
     * {@code GET  /informatiedakloosheids} : get all the informatiedakloosheids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informatiedakloosheids in body.
     */
    @GetMapping("")
    public List<Informatiedakloosheid> getAllInformatiedakloosheids() {
        log.debug("REST request to get all Informatiedakloosheids");
        return informatiedakloosheidRepository.findAll();
    }

    /**
     * {@code GET  /informatiedakloosheids/:id} : get the "id" informatiedakloosheid.
     *
     * @param id the id of the informatiedakloosheid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informatiedakloosheid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Informatiedakloosheid> getInformatiedakloosheid(@PathVariable("id") Long id) {
        log.debug("REST request to get Informatiedakloosheid : {}", id);
        Optional<Informatiedakloosheid> informatiedakloosheid = informatiedakloosheidRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(informatiedakloosheid);
    }

    /**
     * {@code DELETE  /informatiedakloosheids/:id} : delete the "id" informatiedakloosheid.
     *
     * @param id the id of the informatiedakloosheid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInformatiedakloosheid(@PathVariable("id") Long id) {
        log.debug("REST request to delete Informatiedakloosheid : {}", id);
        informatiedakloosheidRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
