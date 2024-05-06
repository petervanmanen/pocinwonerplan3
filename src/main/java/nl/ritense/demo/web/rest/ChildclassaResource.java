package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Childclassa;
import nl.ritense.demo.repository.ChildclassaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Childclassa}.
 */
@RestController
@RequestMapping("/api/childclassas")
@Transactional
public class ChildclassaResource {

    private final Logger log = LoggerFactory.getLogger(ChildclassaResource.class);

    private static final String ENTITY_NAME = "childclassa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChildclassaRepository childclassaRepository;

    public ChildclassaResource(ChildclassaRepository childclassaRepository) {
        this.childclassaRepository = childclassaRepository;
    }

    /**
     * {@code POST  /childclassas} : Create a new childclassa.
     *
     * @param childclassa the childclassa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childclassa, or with status {@code 400 (Bad Request)} if the childclassa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Childclassa> createChildclassa(@RequestBody Childclassa childclassa) throws URISyntaxException {
        log.debug("REST request to save Childclassa : {}", childclassa);
        if (childclassa.getId() != null) {
            throw new BadRequestAlertException("A new childclassa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        childclassa = childclassaRepository.save(childclassa);
        return ResponseEntity.created(new URI("/api/childclassas/" + childclassa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, childclassa.getId().toString()))
            .body(childclassa);
    }

    /**
     * {@code PUT  /childclassas/:id} : Updates an existing childclassa.
     *
     * @param id the id of the childclassa to save.
     * @param childclassa the childclassa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childclassa,
     * or with status {@code 400 (Bad Request)} if the childclassa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the childclassa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Childclassa> updateChildclassa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Childclassa childclassa
    ) throws URISyntaxException {
        log.debug("REST request to update Childclassa : {}, {}", id, childclassa);
        if (childclassa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, childclassa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!childclassaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        childclassa = childclassaRepository.save(childclassa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, childclassa.getId().toString()))
            .body(childclassa);
    }

    /**
     * {@code PATCH  /childclassas/:id} : Partial updates given fields of an existing childclassa, field will ignore if it is null
     *
     * @param id the id of the childclassa to save.
     * @param childclassa the childclassa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childclassa,
     * or with status {@code 400 (Bad Request)} if the childclassa is not valid,
     * or with status {@code 404 (Not Found)} if the childclassa is not found,
     * or with status {@code 500 (Internal Server Error)} if the childclassa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Childclassa> partialUpdateChildclassa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Childclassa childclassa
    ) throws URISyntaxException {
        log.debug("REST request to partial update Childclassa partially : {}, {}", id, childclassa);
        if (childclassa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, childclassa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!childclassaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Childclassa> result = childclassaRepository
            .findById(childclassa.getId())
            .map(existingChildclassa -> {
                if (childclassa.getKleur() != null) {
                    existingChildclassa.setKleur(childclassa.getKleur());
                }

                return existingChildclassa;
            })
            .map(childclassaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, childclassa.getId().toString())
        );
    }

    /**
     * {@code GET  /childclassas} : get all the childclassas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of childclassas in body.
     */
    @GetMapping("")
    public List<Childclassa> getAllChildclassas() {
        log.debug("REST request to get all Childclassas");
        return childclassaRepository.findAll();
    }

    /**
     * {@code GET  /childclassas/:id} : get the "id" childclassa.
     *
     * @param id the id of the childclassa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childclassa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Childclassa> getChildclassa(@PathVariable("id") Long id) {
        log.debug("REST request to get Childclassa : {}", id);
        Optional<Childclassa> childclassa = childclassaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(childclassa);
    }

    /**
     * {@code DELETE  /childclassas/:id} : delete the "id" childclassa.
     *
     * @param id the id of the childclassa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChildclassa(@PathVariable("id") Long id) {
        log.debug("REST request to delete Childclassa : {}", id);
        childclassaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
