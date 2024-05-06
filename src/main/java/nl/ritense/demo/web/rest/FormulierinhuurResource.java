package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Formulierinhuur;
import nl.ritense.demo.repository.FormulierinhuurRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Formulierinhuur}.
 */
@RestController
@RequestMapping("/api/formulierinhuurs")
@Transactional
public class FormulierinhuurResource {

    private final Logger log = LoggerFactory.getLogger(FormulierinhuurResource.class);

    private static final String ENTITY_NAME = "formulierinhuur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormulierinhuurRepository formulierinhuurRepository;

    public FormulierinhuurResource(FormulierinhuurRepository formulierinhuurRepository) {
        this.formulierinhuurRepository = formulierinhuurRepository;
    }

    /**
     * {@code POST  /formulierinhuurs} : Create a new formulierinhuur.
     *
     * @param formulierinhuur the formulierinhuur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formulierinhuur, or with status {@code 400 (Bad Request)} if the formulierinhuur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Formulierinhuur> createFormulierinhuur(@RequestBody Formulierinhuur formulierinhuur) throws URISyntaxException {
        log.debug("REST request to save Formulierinhuur : {}", formulierinhuur);
        if (formulierinhuur.getId() != null) {
            throw new BadRequestAlertException("A new formulierinhuur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formulierinhuur = formulierinhuurRepository.save(formulierinhuur);
        return ResponseEntity.created(new URI("/api/formulierinhuurs/" + formulierinhuur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, formulierinhuur.getId().toString()))
            .body(formulierinhuur);
    }

    /**
     * {@code PUT  /formulierinhuurs/:id} : Updates an existing formulierinhuur.
     *
     * @param id the id of the formulierinhuur to save.
     * @param formulierinhuur the formulierinhuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formulierinhuur,
     * or with status {@code 400 (Bad Request)} if the formulierinhuur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formulierinhuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Formulierinhuur> updateFormulierinhuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formulierinhuur formulierinhuur
    ) throws URISyntaxException {
        log.debug("REST request to update Formulierinhuur : {}, {}", id, formulierinhuur);
        if (formulierinhuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formulierinhuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formulierinhuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formulierinhuur = formulierinhuurRepository.save(formulierinhuur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formulierinhuur.getId().toString()))
            .body(formulierinhuur);
    }

    /**
     * {@code PATCH  /formulierinhuurs/:id} : Partial updates given fields of an existing formulierinhuur, field will ignore if it is null
     *
     * @param id the id of the formulierinhuur to save.
     * @param formulierinhuur the formulierinhuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formulierinhuur,
     * or with status {@code 400 (Bad Request)} if the formulierinhuur is not valid,
     * or with status {@code 404 (Not Found)} if the formulierinhuur is not found,
     * or with status {@code 500 (Internal Server Error)} if the formulierinhuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Formulierinhuur> partialUpdateFormulierinhuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formulierinhuur formulierinhuur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formulierinhuur partially : {}, {}", id, formulierinhuur);
        if (formulierinhuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formulierinhuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formulierinhuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Formulierinhuur> result = formulierinhuurRepository
            .findById(formulierinhuur.getId())
            .map(existingFormulierinhuur -> {
                if (formulierinhuur.getAkkoordfinancieeladviseur() != null) {
                    existingFormulierinhuur.setAkkoordfinancieeladviseur(formulierinhuur.getAkkoordfinancieeladviseur());
                }
                if (formulierinhuur.getAkkoordhradviseur() != null) {
                    existingFormulierinhuur.setAkkoordhradviseur(formulierinhuur.getAkkoordhradviseur());
                }
                if (formulierinhuur.getDatuminganginhuur() != null) {
                    existingFormulierinhuur.setDatuminganginhuur(formulierinhuur.getDatuminganginhuur());
                }
                if (formulierinhuur.getFunctienaaminhuur() != null) {
                    existingFormulierinhuur.setFunctienaaminhuur(formulierinhuur.getFunctienaaminhuur());
                }

                return existingFormulierinhuur;
            })
            .map(formulierinhuurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formulierinhuur.getId().toString())
        );
    }

    /**
     * {@code GET  /formulierinhuurs} : get all the formulierinhuurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formulierinhuurs in body.
     */
    @GetMapping("")
    public List<Formulierinhuur> getAllFormulierinhuurs() {
        log.debug("REST request to get all Formulierinhuurs");
        return formulierinhuurRepository.findAll();
    }

    /**
     * {@code GET  /formulierinhuurs/:id} : get the "id" formulierinhuur.
     *
     * @param id the id of the formulierinhuur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formulierinhuur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Formulierinhuur> getFormulierinhuur(@PathVariable("id") Long id) {
        log.debug("REST request to get Formulierinhuur : {}", id);
        Optional<Formulierinhuur> formulierinhuur = formulierinhuurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formulierinhuur);
    }

    /**
     * {@code DELETE  /formulierinhuurs/:id} : delete the "id" formulierinhuur.
     *
     * @param id the id of the formulierinhuur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormulierinhuur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Formulierinhuur : {}", id);
        formulierinhuurRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
