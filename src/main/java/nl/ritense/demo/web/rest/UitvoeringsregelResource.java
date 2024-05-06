package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Uitvoeringsregel;
import nl.ritense.demo.repository.UitvoeringsregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitvoeringsregel}.
 */
@RestController
@RequestMapping("/api/uitvoeringsregels")
@Transactional
public class UitvoeringsregelResource {

    private final Logger log = LoggerFactory.getLogger(UitvoeringsregelResource.class);

    private static final String ENTITY_NAME = "uitvoeringsregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitvoeringsregelRepository uitvoeringsregelRepository;

    public UitvoeringsregelResource(UitvoeringsregelRepository uitvoeringsregelRepository) {
        this.uitvoeringsregelRepository = uitvoeringsregelRepository;
    }

    /**
     * {@code POST  /uitvoeringsregels} : Create a new uitvoeringsregel.
     *
     * @param uitvoeringsregel the uitvoeringsregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitvoeringsregel, or with status {@code 400 (Bad Request)} if the uitvoeringsregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitvoeringsregel> createUitvoeringsregel(@RequestBody Uitvoeringsregel uitvoeringsregel)
        throws URISyntaxException {
        log.debug("REST request to save Uitvoeringsregel : {}", uitvoeringsregel);
        if (uitvoeringsregel.getId() != null) {
            throw new BadRequestAlertException("A new uitvoeringsregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitvoeringsregel = uitvoeringsregelRepository.save(uitvoeringsregel);
        return ResponseEntity.created(new URI("/api/uitvoeringsregels/" + uitvoeringsregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitvoeringsregel.getId().toString()))
            .body(uitvoeringsregel);
    }

    /**
     * {@code PUT  /uitvoeringsregels/:id} : Updates an existing uitvoeringsregel.
     *
     * @param id the id of the uitvoeringsregel to save.
     * @param uitvoeringsregel the uitvoeringsregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitvoeringsregel,
     * or with status {@code 400 (Bad Request)} if the uitvoeringsregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uitvoeringsregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Uitvoeringsregel> updateUitvoeringsregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitvoeringsregel uitvoeringsregel
    ) throws URISyntaxException {
        log.debug("REST request to update Uitvoeringsregel : {}, {}", id, uitvoeringsregel);
        if (uitvoeringsregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitvoeringsregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitvoeringsregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uitvoeringsregel = uitvoeringsregelRepository.save(uitvoeringsregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitvoeringsregel.getId().toString()))
            .body(uitvoeringsregel);
    }

    /**
     * {@code PATCH  /uitvoeringsregels/:id} : Partial updates given fields of an existing uitvoeringsregel, field will ignore if it is null
     *
     * @param id the id of the uitvoeringsregel to save.
     * @param uitvoeringsregel the uitvoeringsregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitvoeringsregel,
     * or with status {@code 400 (Bad Request)} if the uitvoeringsregel is not valid,
     * or with status {@code 404 (Not Found)} if the uitvoeringsregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the uitvoeringsregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Uitvoeringsregel> partialUpdateUitvoeringsregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitvoeringsregel uitvoeringsregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uitvoeringsregel partially : {}, {}", id, uitvoeringsregel);
        if (uitvoeringsregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitvoeringsregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitvoeringsregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Uitvoeringsregel> result = uitvoeringsregelRepository
            .findById(uitvoeringsregel.getId())
            .map(existingUitvoeringsregel -> {
                if (uitvoeringsregel.getNaam() != null) {
                    existingUitvoeringsregel.setNaam(uitvoeringsregel.getNaam());
                }
                if (uitvoeringsregel.getOmschrijving() != null) {
                    existingUitvoeringsregel.setOmschrijving(uitvoeringsregel.getOmschrijving());
                }
                if (uitvoeringsregel.getRegel() != null) {
                    existingUitvoeringsregel.setRegel(uitvoeringsregel.getRegel());
                }

                return existingUitvoeringsregel;
            })
            .map(uitvoeringsregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitvoeringsregel.getId().toString())
        );
    }

    /**
     * {@code GET  /uitvoeringsregels} : get all the uitvoeringsregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitvoeringsregels in body.
     */
    @GetMapping("")
    public List<Uitvoeringsregel> getAllUitvoeringsregels() {
        log.debug("REST request to get all Uitvoeringsregels");
        return uitvoeringsregelRepository.findAll();
    }

    /**
     * {@code GET  /uitvoeringsregels/:id} : get the "id" uitvoeringsregel.
     *
     * @param id the id of the uitvoeringsregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitvoeringsregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitvoeringsregel> getUitvoeringsregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitvoeringsregel : {}", id);
        Optional<Uitvoeringsregel> uitvoeringsregel = uitvoeringsregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitvoeringsregel);
    }

    /**
     * {@code DELETE  /uitvoeringsregels/:id} : delete the "id" uitvoeringsregel.
     *
     * @param id the id of the uitvoeringsregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitvoeringsregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitvoeringsregel : {}", id);
        uitvoeringsregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
