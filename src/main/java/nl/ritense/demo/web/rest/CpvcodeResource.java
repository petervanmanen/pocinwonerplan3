package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Cpvcode;
import nl.ritense.demo.repository.CpvcodeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Cpvcode}.
 */
@RestController
@RequestMapping("/api/cpvcodes")
@Transactional
public class CpvcodeResource {

    private final Logger log = LoggerFactory.getLogger(CpvcodeResource.class);

    private static final String ENTITY_NAME = "cpvcode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CpvcodeRepository cpvcodeRepository;

    public CpvcodeResource(CpvcodeRepository cpvcodeRepository) {
        this.cpvcodeRepository = cpvcodeRepository;
    }

    /**
     * {@code POST  /cpvcodes} : Create a new cpvcode.
     *
     * @param cpvcode the cpvcode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cpvcode, or with status {@code 400 (Bad Request)} if the cpvcode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Cpvcode> createCpvcode(@RequestBody Cpvcode cpvcode) throws URISyntaxException {
        log.debug("REST request to save Cpvcode : {}", cpvcode);
        if (cpvcode.getId() != null) {
            throw new BadRequestAlertException("A new cpvcode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cpvcode = cpvcodeRepository.save(cpvcode);
        return ResponseEntity.created(new URI("/api/cpvcodes/" + cpvcode.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cpvcode.getId().toString()))
            .body(cpvcode);
    }

    /**
     * {@code PUT  /cpvcodes/:id} : Updates an existing cpvcode.
     *
     * @param id the id of the cpvcode to save.
     * @param cpvcode the cpvcode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cpvcode,
     * or with status {@code 400 (Bad Request)} if the cpvcode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cpvcode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cpvcode> updateCpvcode(@PathVariable(value = "id", required = false) final Long id, @RequestBody Cpvcode cpvcode)
        throws URISyntaxException {
        log.debug("REST request to update Cpvcode : {}, {}", id, cpvcode);
        if (cpvcode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cpvcode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cpvcodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cpvcode = cpvcodeRepository.save(cpvcode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cpvcode.getId().toString()))
            .body(cpvcode);
    }

    /**
     * {@code PATCH  /cpvcodes/:id} : Partial updates given fields of an existing cpvcode, field will ignore if it is null
     *
     * @param id the id of the cpvcode to save.
     * @param cpvcode the cpvcode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cpvcode,
     * or with status {@code 400 (Bad Request)} if the cpvcode is not valid,
     * or with status {@code 404 (Not Found)} if the cpvcode is not found,
     * or with status {@code 500 (Internal Server Error)} if the cpvcode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cpvcode> partialUpdateCpvcode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cpvcode cpvcode
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cpvcode partially : {}, {}", id, cpvcode);
        if (cpvcode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cpvcode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cpvcodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cpvcode> result = cpvcodeRepository
            .findById(cpvcode.getId())
            .map(existingCpvcode -> {
                if (cpvcode.getCode() != null) {
                    existingCpvcode.setCode(cpvcode.getCode());
                }
                if (cpvcode.getOmschrijving() != null) {
                    existingCpvcode.setOmschrijving(cpvcode.getOmschrijving());
                }

                return existingCpvcode;
            })
            .map(cpvcodeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cpvcode.getId().toString())
        );
    }

    /**
     * {@code GET  /cpvcodes} : get all the cpvcodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cpvcodes in body.
     */
    @GetMapping("")
    public List<Cpvcode> getAllCpvcodes() {
        log.debug("REST request to get all Cpvcodes");
        return cpvcodeRepository.findAll();
    }

    /**
     * {@code GET  /cpvcodes/:id} : get the "id" cpvcode.
     *
     * @param id the id of the cpvcode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cpvcode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cpvcode> getCpvcode(@PathVariable("id") Long id) {
        log.debug("REST request to get Cpvcode : {}", id);
        Optional<Cpvcode> cpvcode = cpvcodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cpvcode);
    }

    /**
     * {@code DELETE  /cpvcodes/:id} : delete the "id" cpvcode.
     *
     * @param id the id of the cpvcode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCpvcode(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cpvcode : {}", id);
        cpvcodeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
