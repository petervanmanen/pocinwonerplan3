package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Subproducttype;
import nl.ritense.demo.repository.SubproducttypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Subproducttype}.
 */
@RestController
@RequestMapping("/api/subproducttypes")
@Transactional
public class SubproducttypeResource {

    private final Logger log = LoggerFactory.getLogger(SubproducttypeResource.class);

    private static final String ENTITY_NAME = "subproducttype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubproducttypeRepository subproducttypeRepository;

    public SubproducttypeResource(SubproducttypeRepository subproducttypeRepository) {
        this.subproducttypeRepository = subproducttypeRepository;
    }

    /**
     * {@code POST  /subproducttypes} : Create a new subproducttype.
     *
     * @param subproducttype the subproducttype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subproducttype, or with status {@code 400 (Bad Request)} if the subproducttype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Subproducttype> createSubproducttype(@RequestBody Subproducttype subproducttype) throws URISyntaxException {
        log.debug("REST request to save Subproducttype : {}", subproducttype);
        if (subproducttype.getId() != null) {
            throw new BadRequestAlertException("A new subproducttype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subproducttype = subproducttypeRepository.save(subproducttype);
        return ResponseEntity.created(new URI("/api/subproducttypes/" + subproducttype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, subproducttype.getId().toString()))
            .body(subproducttype);
    }

    /**
     * {@code PUT  /subproducttypes/:id} : Updates an existing subproducttype.
     *
     * @param id the id of the subproducttype to save.
     * @param subproducttype the subproducttype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subproducttype,
     * or with status {@code 400 (Bad Request)} if the subproducttype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subproducttype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Subproducttype> updateSubproducttype(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Subproducttype subproducttype
    ) throws URISyntaxException {
        log.debug("REST request to update Subproducttype : {}, {}", id, subproducttype);
        if (subproducttype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subproducttype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subproducttypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subproducttype = subproducttypeRepository.save(subproducttype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subproducttype.getId().toString()))
            .body(subproducttype);
    }

    /**
     * {@code PATCH  /subproducttypes/:id} : Partial updates given fields of an existing subproducttype, field will ignore if it is null
     *
     * @param id the id of the subproducttype to save.
     * @param subproducttype the subproducttype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subproducttype,
     * or with status {@code 400 (Bad Request)} if the subproducttype is not valid,
     * or with status {@code 404 (Not Found)} if the subproducttype is not found,
     * or with status {@code 500 (Internal Server Error)} if the subproducttype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Subproducttype> partialUpdateSubproducttype(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Subproducttype subproducttype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subproducttype partially : {}, {}", id, subproducttype);
        if (subproducttype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subproducttype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subproducttypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Subproducttype> result = subproducttypeRepository
            .findById(subproducttype.getId())
            .map(existingSubproducttype -> {
                if (subproducttype.getOmschrijving() != null) {
                    existingSubproducttype.setOmschrijving(subproducttype.getOmschrijving());
                }
                if (subproducttype.getPrioriteit() != null) {
                    existingSubproducttype.setPrioriteit(subproducttype.getPrioriteit());
                }

                return existingSubproducttype;
            })
            .map(subproducttypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subproducttype.getId().toString())
        );
    }

    /**
     * {@code GET  /subproducttypes} : get all the subproducttypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subproducttypes in body.
     */
    @GetMapping("")
    public List<Subproducttype> getAllSubproducttypes() {
        log.debug("REST request to get all Subproducttypes");
        return subproducttypeRepository.findAll();
    }

    /**
     * {@code GET  /subproducttypes/:id} : get the "id" subproducttype.
     *
     * @param id the id of the subproducttype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subproducttype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subproducttype> getSubproducttype(@PathVariable("id") Long id) {
        log.debug("REST request to get Subproducttype : {}", id);
        Optional<Subproducttype> subproducttype = subproducttypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subproducttype);
    }

    /**
     * {@code DELETE  /subproducttypes/:id} : delete the "id" subproducttype.
     *
     * @param id the id of the subproducttype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubproducttype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Subproducttype : {}", id);
        subproducttypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
