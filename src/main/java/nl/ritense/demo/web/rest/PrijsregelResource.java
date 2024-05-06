package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Prijsregel;
import nl.ritense.demo.repository.PrijsregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Prijsregel}.
 */
@RestController
@RequestMapping("/api/prijsregels")
@Transactional
public class PrijsregelResource {

    private final Logger log = LoggerFactory.getLogger(PrijsregelResource.class);

    private static final String ENTITY_NAME = "prijsregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrijsregelRepository prijsregelRepository;

    public PrijsregelResource(PrijsregelRepository prijsregelRepository) {
        this.prijsregelRepository = prijsregelRepository;
    }

    /**
     * {@code POST  /prijsregels} : Create a new prijsregel.
     *
     * @param prijsregel the prijsregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prijsregel, or with status {@code 400 (Bad Request)} if the prijsregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Prijsregel> createPrijsregel(@RequestBody Prijsregel prijsregel) throws URISyntaxException {
        log.debug("REST request to save Prijsregel : {}", prijsregel);
        if (prijsregel.getId() != null) {
            throw new BadRequestAlertException("A new prijsregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prijsregel = prijsregelRepository.save(prijsregel);
        return ResponseEntity.created(new URI("/api/prijsregels/" + prijsregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, prijsregel.getId().toString()))
            .body(prijsregel);
    }

    /**
     * {@code PUT  /prijsregels/:id} : Updates an existing prijsregel.
     *
     * @param id the id of the prijsregel to save.
     * @param prijsregel the prijsregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijsregel,
     * or with status {@code 400 (Bad Request)} if the prijsregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prijsregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Prijsregel> updatePrijsregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Prijsregel prijsregel
    ) throws URISyntaxException {
        log.debug("REST request to update Prijsregel : {}, {}", id, prijsregel);
        if (prijsregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijsregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijsregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        prijsregel = prijsregelRepository.save(prijsregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijsregel.getId().toString()))
            .body(prijsregel);
    }

    /**
     * {@code PATCH  /prijsregels/:id} : Partial updates given fields of an existing prijsregel, field will ignore if it is null
     *
     * @param id the id of the prijsregel to save.
     * @param prijsregel the prijsregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijsregel,
     * or with status {@code 400 (Bad Request)} if the prijsregel is not valid,
     * or with status {@code 404 (Not Found)} if the prijsregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the prijsregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Prijsregel> partialUpdatePrijsregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Prijsregel prijsregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prijsregel partially : {}, {}", id, prijsregel);
        if (prijsregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijsregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijsregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Prijsregel> result = prijsregelRepository
            .findById(prijsregel.getId())
            .map(existingPrijsregel -> {
                if (prijsregel.getBedrag() != null) {
                    existingPrijsregel.setBedrag(prijsregel.getBedrag());
                }
                if (prijsregel.getCredit() != null) {
                    existingPrijsregel.setCredit(prijsregel.getCredit());
                }

                return existingPrijsregel;
            })
            .map(prijsregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijsregel.getId().toString())
        );
    }

    /**
     * {@code GET  /prijsregels} : get all the prijsregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prijsregels in body.
     */
    @GetMapping("")
    public List<Prijsregel> getAllPrijsregels() {
        log.debug("REST request to get all Prijsregels");
        return prijsregelRepository.findAll();
    }

    /**
     * {@code GET  /prijsregels/:id} : get the "id" prijsregel.
     *
     * @param id the id of the prijsregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prijsregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prijsregel> getPrijsregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Prijsregel : {}", id);
        Optional<Prijsregel> prijsregel = prijsregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prijsregel);
    }

    /**
     * {@code DELETE  /prijsregels/:id} : delete the "id" prijsregel.
     *
     * @param id the id of the prijsregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrijsregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Prijsregel : {}", id);
        prijsregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
