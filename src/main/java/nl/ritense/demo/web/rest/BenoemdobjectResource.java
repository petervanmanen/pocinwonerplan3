package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Benoemdobject;
import nl.ritense.demo.repository.BenoemdobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Benoemdobject}.
 */
@RestController
@RequestMapping("/api/benoemdobjects")
@Transactional
public class BenoemdobjectResource {

    private final Logger log = LoggerFactory.getLogger(BenoemdobjectResource.class);

    private static final String ENTITY_NAME = "benoemdobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenoemdobjectRepository benoemdobjectRepository;

    public BenoemdobjectResource(BenoemdobjectRepository benoemdobjectRepository) {
        this.benoemdobjectRepository = benoemdobjectRepository;
    }

    /**
     * {@code POST  /benoemdobjects} : Create a new benoemdobject.
     *
     * @param benoemdobject the benoemdobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benoemdobject, or with status {@code 400 (Bad Request)} if the benoemdobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Benoemdobject> createBenoemdobject(@Valid @RequestBody Benoemdobject benoemdobject) throws URISyntaxException {
        log.debug("REST request to save Benoemdobject : {}", benoemdobject);
        if (benoemdobject.getId() != null) {
            throw new BadRequestAlertException("A new benoemdobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        benoemdobject = benoemdobjectRepository.save(benoemdobject);
        return ResponseEntity.created(new URI("/api/benoemdobjects/" + benoemdobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, benoemdobject.getId().toString()))
            .body(benoemdobject);
    }

    /**
     * {@code PUT  /benoemdobjects/:id} : Updates an existing benoemdobject.
     *
     * @param id the id of the benoemdobject to save.
     * @param benoemdobject the benoemdobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benoemdobject,
     * or with status {@code 400 (Bad Request)} if the benoemdobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benoemdobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Benoemdobject> updateBenoemdobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Benoemdobject benoemdobject
    ) throws URISyntaxException {
        log.debug("REST request to update Benoemdobject : {}, {}", id, benoemdobject);
        if (benoemdobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benoemdobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benoemdobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        benoemdobject = benoemdobjectRepository.save(benoemdobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benoemdobject.getId().toString()))
            .body(benoemdobject);
    }

    /**
     * {@code PATCH  /benoemdobjects/:id} : Partial updates given fields of an existing benoemdobject, field will ignore if it is null
     *
     * @param id the id of the benoemdobject to save.
     * @param benoemdobject the benoemdobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benoemdobject,
     * or with status {@code 400 (Bad Request)} if the benoemdobject is not valid,
     * or with status {@code 404 (Not Found)} if the benoemdobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the benoemdobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Benoemdobject> partialUpdateBenoemdobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Benoemdobject benoemdobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Benoemdobject partially : {}, {}", id, benoemdobject);
        if (benoemdobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benoemdobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benoemdobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Benoemdobject> result = benoemdobjectRepository
            .findById(benoemdobject.getId())
            .map(existingBenoemdobject -> {
                if (benoemdobject.getDatumbegingeldigheid() != null) {
                    existingBenoemdobject.setDatumbegingeldigheid(benoemdobject.getDatumbegingeldigheid());
                }
                if (benoemdobject.getDatumeindegeldigheid() != null) {
                    existingBenoemdobject.setDatumeindegeldigheid(benoemdobject.getDatumeindegeldigheid());
                }
                if (benoemdobject.getGeometriepunt() != null) {
                    existingBenoemdobject.setGeometriepunt(benoemdobject.getGeometriepunt());
                }
                if (benoemdobject.getGeometrievlak() != null) {
                    existingBenoemdobject.setGeometrievlak(benoemdobject.getGeometrievlak());
                }
                if (benoemdobject.getIdentificatie() != null) {
                    existingBenoemdobject.setIdentificatie(benoemdobject.getIdentificatie());
                }

                return existingBenoemdobject;
            })
            .map(benoemdobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benoemdobject.getId().toString())
        );
    }

    /**
     * {@code GET  /benoemdobjects} : get all the benoemdobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benoemdobjects in body.
     */
    @GetMapping("")
    public List<Benoemdobject> getAllBenoemdobjects() {
        log.debug("REST request to get all Benoemdobjects");
        return benoemdobjectRepository.findAll();
    }

    /**
     * {@code GET  /benoemdobjects/:id} : get the "id" benoemdobject.
     *
     * @param id the id of the benoemdobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benoemdobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Benoemdobject> getBenoemdobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Benoemdobject : {}", id);
        Optional<Benoemdobject> benoemdobject = benoemdobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(benoemdobject);
    }

    /**
     * {@code DELETE  /benoemdobjects/:id} : delete the "id" benoemdobject.
     *
     * @param id the id of the benoemdobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBenoemdobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Benoemdobject : {}", id);
        benoemdobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
