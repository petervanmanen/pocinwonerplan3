package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sbiactiviteitvestiging;
import nl.ritense.demo.repository.SbiactiviteitvestigingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sbiactiviteitvestiging}.
 */
@RestController
@RequestMapping("/api/sbiactiviteitvestigings")
@Transactional
public class SbiactiviteitvestigingResource {

    private final Logger log = LoggerFactory.getLogger(SbiactiviteitvestigingResource.class);

    private static final String ENTITY_NAME = "sbiactiviteitvestiging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SbiactiviteitvestigingRepository sbiactiviteitvestigingRepository;

    public SbiactiviteitvestigingResource(SbiactiviteitvestigingRepository sbiactiviteitvestigingRepository) {
        this.sbiactiviteitvestigingRepository = sbiactiviteitvestigingRepository;
    }

    /**
     * {@code POST  /sbiactiviteitvestigings} : Create a new sbiactiviteitvestiging.
     *
     * @param sbiactiviteitvestiging the sbiactiviteitvestiging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sbiactiviteitvestiging, or with status {@code 400 (Bad Request)} if the sbiactiviteitvestiging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sbiactiviteitvestiging> createSbiactiviteitvestiging(
        @Valid @RequestBody Sbiactiviteitvestiging sbiactiviteitvestiging
    ) throws URISyntaxException {
        log.debug("REST request to save Sbiactiviteitvestiging : {}", sbiactiviteitvestiging);
        if (sbiactiviteitvestiging.getId() != null) {
            throw new BadRequestAlertException("A new sbiactiviteitvestiging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sbiactiviteitvestiging = sbiactiviteitvestigingRepository.save(sbiactiviteitvestiging);
        return ResponseEntity.created(new URI("/api/sbiactiviteitvestigings/" + sbiactiviteitvestiging.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sbiactiviteitvestiging.getId().toString()))
            .body(sbiactiviteitvestiging);
    }

    /**
     * {@code PUT  /sbiactiviteitvestigings/:id} : Updates an existing sbiactiviteitvestiging.
     *
     * @param id the id of the sbiactiviteitvestiging to save.
     * @param sbiactiviteitvestiging the sbiactiviteitvestiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sbiactiviteitvestiging,
     * or with status {@code 400 (Bad Request)} if the sbiactiviteitvestiging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sbiactiviteitvestiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sbiactiviteitvestiging> updateSbiactiviteitvestiging(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sbiactiviteitvestiging sbiactiviteitvestiging
    ) throws URISyntaxException {
        log.debug("REST request to update Sbiactiviteitvestiging : {}, {}", id, sbiactiviteitvestiging);
        if (sbiactiviteitvestiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sbiactiviteitvestiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sbiactiviteitvestigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sbiactiviteitvestiging = sbiactiviteitvestigingRepository.save(sbiactiviteitvestiging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sbiactiviteitvestiging.getId().toString()))
            .body(sbiactiviteitvestiging);
    }

    /**
     * {@code PATCH  /sbiactiviteitvestigings/:id} : Partial updates given fields of an existing sbiactiviteitvestiging, field will ignore if it is null
     *
     * @param id the id of the sbiactiviteitvestiging to save.
     * @param sbiactiviteitvestiging the sbiactiviteitvestiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sbiactiviteitvestiging,
     * or with status {@code 400 (Bad Request)} if the sbiactiviteitvestiging is not valid,
     * or with status {@code 404 (Not Found)} if the sbiactiviteitvestiging is not found,
     * or with status {@code 500 (Internal Server Error)} if the sbiactiviteitvestiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sbiactiviteitvestiging> partialUpdateSbiactiviteitvestiging(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sbiactiviteitvestiging sbiactiviteitvestiging
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sbiactiviteitvestiging partially : {}, {}", id, sbiactiviteitvestiging);
        if (sbiactiviteitvestiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sbiactiviteitvestiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sbiactiviteitvestigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sbiactiviteitvestiging> result = sbiactiviteitvestigingRepository
            .findById(sbiactiviteitvestiging.getId())
            .map(existingSbiactiviteitvestiging -> {
                if (sbiactiviteitvestiging.getIndicatiehoofdactiviteit() != null) {
                    existingSbiactiviteitvestiging.setIndicatiehoofdactiviteit(sbiactiviteitvestiging.getIndicatiehoofdactiviteit());
                }
                if (sbiactiviteitvestiging.getSbicode() != null) {
                    existingSbiactiviteitvestiging.setSbicode(sbiactiviteitvestiging.getSbicode());
                }

                return existingSbiactiviteitvestiging;
            })
            .map(sbiactiviteitvestigingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sbiactiviteitvestiging.getId().toString())
        );
    }

    /**
     * {@code GET  /sbiactiviteitvestigings} : get all the sbiactiviteitvestigings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sbiactiviteitvestigings in body.
     */
    @GetMapping("")
    public List<Sbiactiviteitvestiging> getAllSbiactiviteitvestigings() {
        log.debug("REST request to get all Sbiactiviteitvestigings");
        return sbiactiviteitvestigingRepository.findAll();
    }

    /**
     * {@code GET  /sbiactiviteitvestigings/:id} : get the "id" sbiactiviteitvestiging.
     *
     * @param id the id of the sbiactiviteitvestiging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sbiactiviteitvestiging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sbiactiviteitvestiging> getSbiactiviteitvestiging(@PathVariable("id") Long id) {
        log.debug("REST request to get Sbiactiviteitvestiging : {}", id);
        Optional<Sbiactiviteitvestiging> sbiactiviteitvestiging = sbiactiviteitvestigingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sbiactiviteitvestiging);
    }

    /**
     * {@code DELETE  /sbiactiviteitvestigings/:id} : delete the "id" sbiactiviteitvestiging.
     *
     * @param id the id of the sbiactiviteitvestiging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSbiactiviteitvestiging(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sbiactiviteitvestiging : {}", id);
        sbiactiviteitvestigingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
