package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vthaanvraagofmelding;
import nl.ritense.demo.repository.VthaanvraagofmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vthaanvraagofmelding}.
 */
@RestController
@RequestMapping("/api/vthaanvraagofmeldings")
@Transactional
public class VthaanvraagofmeldingResource {

    private final Logger log = LoggerFactory.getLogger(VthaanvraagofmeldingResource.class);

    private static final String ENTITY_NAME = "vthaanvraagofmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VthaanvraagofmeldingRepository vthaanvraagofmeldingRepository;

    public VthaanvraagofmeldingResource(VthaanvraagofmeldingRepository vthaanvraagofmeldingRepository) {
        this.vthaanvraagofmeldingRepository = vthaanvraagofmeldingRepository;
    }

    /**
     * {@code POST  /vthaanvraagofmeldings} : Create a new vthaanvraagofmelding.
     *
     * @param vthaanvraagofmelding the vthaanvraagofmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vthaanvraagofmelding, or with status {@code 400 (Bad Request)} if the vthaanvraagofmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vthaanvraagofmelding> createVthaanvraagofmelding(@RequestBody Vthaanvraagofmelding vthaanvraagofmelding)
        throws URISyntaxException {
        log.debug("REST request to save Vthaanvraagofmelding : {}", vthaanvraagofmelding);
        if (vthaanvraagofmelding.getId() != null) {
            throw new BadRequestAlertException("A new vthaanvraagofmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vthaanvraagofmelding = vthaanvraagofmeldingRepository.save(vthaanvraagofmelding);
        return ResponseEntity.created(new URI("/api/vthaanvraagofmeldings/" + vthaanvraagofmelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vthaanvraagofmelding.getId().toString()))
            .body(vthaanvraagofmelding);
    }

    /**
     * {@code PUT  /vthaanvraagofmeldings/:id} : Updates an existing vthaanvraagofmelding.
     *
     * @param id the id of the vthaanvraagofmelding to save.
     * @param vthaanvraagofmelding the vthaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vthaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the vthaanvraagofmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vthaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vthaanvraagofmelding> updateVthaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vthaanvraagofmelding vthaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Vthaanvraagofmelding : {}, {}", id, vthaanvraagofmelding);
        if (vthaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vthaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vthaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vthaanvraagofmelding = vthaanvraagofmeldingRepository.save(vthaanvraagofmelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vthaanvraagofmelding.getId().toString()))
            .body(vthaanvraagofmelding);
    }

    /**
     * {@code PATCH  /vthaanvraagofmeldings/:id} : Partial updates given fields of an existing vthaanvraagofmelding, field will ignore if it is null
     *
     * @param id the id of the vthaanvraagofmelding to save.
     * @param vthaanvraagofmelding the vthaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vthaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the vthaanvraagofmelding is not valid,
     * or with status {@code 404 (Not Found)} if the vthaanvraagofmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the vthaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vthaanvraagofmelding> partialUpdateVthaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Vthaanvraagofmelding vthaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vthaanvraagofmelding partially : {}, {}", id, vthaanvraagofmelding);
        if (vthaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vthaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vthaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vthaanvraagofmelding> result = vthaanvraagofmeldingRepository
            .findById(vthaanvraagofmelding.getId())
            .map(existingVthaanvraagofmelding -> {
                if (vthaanvraagofmelding.getOmschrijving() != null) {
                    existingVthaanvraagofmelding.setOmschrijving(vthaanvraagofmelding.getOmschrijving());
                }

                return existingVthaanvraagofmelding;
            })
            .map(vthaanvraagofmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vthaanvraagofmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /vthaanvraagofmeldings} : get all the vthaanvraagofmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vthaanvraagofmeldings in body.
     */
    @GetMapping("")
    public List<Vthaanvraagofmelding> getAllVthaanvraagofmeldings() {
        log.debug("REST request to get all Vthaanvraagofmeldings");
        return vthaanvraagofmeldingRepository.findAll();
    }

    /**
     * {@code GET  /vthaanvraagofmeldings/:id} : get the "id" vthaanvraagofmelding.
     *
     * @param id the id of the vthaanvraagofmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vthaanvraagofmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vthaanvraagofmelding> getVthaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Vthaanvraagofmelding : {}", id);
        Optional<Vthaanvraagofmelding> vthaanvraagofmelding = vthaanvraagofmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vthaanvraagofmelding);
    }

    /**
     * {@code DELETE  /vthaanvraagofmeldings/:id} : delete the "id" vthaanvraagofmelding.
     *
     * @param id the id of the vthaanvraagofmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVthaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vthaanvraagofmelding : {}", id);
        vthaanvraagofmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
