package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Doelgroep;
import nl.ritense.demo.repository.DoelgroepRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Doelgroep}.
 */
@RestController
@RequestMapping("/api/doelgroeps")
@Transactional
public class DoelgroepResource {

    private final Logger log = LoggerFactory.getLogger(DoelgroepResource.class);

    private static final String ENTITY_NAME = "doelgroep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoelgroepRepository doelgroepRepository;

    public DoelgroepResource(DoelgroepRepository doelgroepRepository) {
        this.doelgroepRepository = doelgroepRepository;
    }

    /**
     * {@code POST  /doelgroeps} : Create a new doelgroep.
     *
     * @param doelgroep the doelgroep to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doelgroep, or with status {@code 400 (Bad Request)} if the doelgroep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Doelgroep> createDoelgroep(@Valid @RequestBody Doelgroep doelgroep) throws URISyntaxException {
        log.debug("REST request to save Doelgroep : {}", doelgroep);
        if (doelgroep.getId() != null) {
            throw new BadRequestAlertException("A new doelgroep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        doelgroep = doelgroepRepository.save(doelgroep);
        return ResponseEntity.created(new URI("/api/doelgroeps/" + doelgroep.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, doelgroep.getId().toString()))
            .body(doelgroep);
    }

    /**
     * {@code PUT  /doelgroeps/:id} : Updates an existing doelgroep.
     *
     * @param id the id of the doelgroep to save.
     * @param doelgroep the doelgroep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doelgroep,
     * or with status {@code 400 (Bad Request)} if the doelgroep is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doelgroep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doelgroep> updateDoelgroep(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Doelgroep doelgroep
    ) throws URISyntaxException {
        log.debug("REST request to update Doelgroep : {}, {}", id, doelgroep);
        if (doelgroep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doelgroep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doelgroepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        doelgroep = doelgroepRepository.save(doelgroep);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doelgroep.getId().toString()))
            .body(doelgroep);
    }

    /**
     * {@code PATCH  /doelgroeps/:id} : Partial updates given fields of an existing doelgroep, field will ignore if it is null
     *
     * @param id the id of the doelgroep to save.
     * @param doelgroep the doelgroep to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doelgroep,
     * or with status {@code 400 (Bad Request)} if the doelgroep is not valid,
     * or with status {@code 404 (Not Found)} if the doelgroep is not found,
     * or with status {@code 500 (Internal Server Error)} if the doelgroep couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Doelgroep> partialUpdateDoelgroep(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Doelgroep doelgroep
    ) throws URISyntaxException {
        log.debug("REST request to partial update Doelgroep partially : {}, {}", id, doelgroep);
        if (doelgroep.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doelgroep.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doelgroepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Doelgroep> result = doelgroepRepository
            .findById(doelgroep.getId())
            .map(existingDoelgroep -> {
                if (doelgroep.getBranch() != null) {
                    existingDoelgroep.setBranch(doelgroep.getBranch());
                }
                if (doelgroep.getNaam() != null) {
                    existingDoelgroep.setNaam(doelgroep.getNaam());
                }
                if (doelgroep.getOmschrijving() != null) {
                    existingDoelgroep.setOmschrijving(doelgroep.getOmschrijving());
                }
                if (doelgroep.getSegment() != null) {
                    existingDoelgroep.setSegment(doelgroep.getSegment());
                }

                return existingDoelgroep;
            })
            .map(doelgroepRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doelgroep.getId().toString())
        );
    }

    /**
     * {@code GET  /doelgroeps} : get all the doelgroeps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doelgroeps in body.
     */
    @GetMapping("")
    public List<Doelgroep> getAllDoelgroeps() {
        log.debug("REST request to get all Doelgroeps");
        return doelgroepRepository.findAll();
    }

    /**
     * {@code GET  /doelgroeps/:id} : get the "id" doelgroep.
     *
     * @param id the id of the doelgroep to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doelgroep, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doelgroep> getDoelgroep(@PathVariable("id") Long id) {
        log.debug("REST request to get Doelgroep : {}", id);
        Optional<Doelgroep> doelgroep = doelgroepRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doelgroep);
    }

    /**
     * {@code DELETE  /doelgroeps/:id} : delete the "id" doelgroep.
     *
     * @param id the id of the doelgroep to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoelgroep(@PathVariable("id") Long id) {
        log.debug("REST request to delete Doelgroep : {}", id);
        doelgroepRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
