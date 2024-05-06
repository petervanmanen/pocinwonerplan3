package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Formulierverlenginginhuur;
import nl.ritense.demo.repository.FormulierverlenginginhuurRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Formulierverlenginginhuur}.
 */
@RestController
@RequestMapping("/api/formulierverlenginginhuurs")
@Transactional
public class FormulierverlenginginhuurResource {

    private final Logger log = LoggerFactory.getLogger(FormulierverlenginginhuurResource.class);

    private static final String ENTITY_NAME = "formulierverlenginginhuur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormulierverlenginginhuurRepository formulierverlenginginhuurRepository;

    public FormulierverlenginginhuurResource(FormulierverlenginginhuurRepository formulierverlenginginhuurRepository) {
        this.formulierverlenginginhuurRepository = formulierverlenginginhuurRepository;
    }

    /**
     * {@code POST  /formulierverlenginginhuurs} : Create a new formulierverlenginginhuur.
     *
     * @param formulierverlenginginhuur the formulierverlenginginhuur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formulierverlenginginhuur, or with status {@code 400 (Bad Request)} if the formulierverlenginginhuur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Formulierverlenginginhuur> createFormulierverlenginginhuur(
        @RequestBody Formulierverlenginginhuur formulierverlenginginhuur
    ) throws URISyntaxException {
        log.debug("REST request to save Formulierverlenginginhuur : {}", formulierverlenginginhuur);
        if (formulierverlenginginhuur.getId() != null) {
            throw new BadRequestAlertException("A new formulierverlenginginhuur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formulierverlenginginhuur = formulierverlenginginhuurRepository.save(formulierverlenginginhuur);
        return ResponseEntity.created(new URI("/api/formulierverlenginginhuurs/" + formulierverlenginginhuur.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, formulierverlenginginhuur.getId().toString())
            )
            .body(formulierverlenginginhuur);
    }

    /**
     * {@code PUT  /formulierverlenginginhuurs/:id} : Updates an existing formulierverlenginginhuur.
     *
     * @param id the id of the formulierverlenginginhuur to save.
     * @param formulierverlenginginhuur the formulierverlenginginhuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formulierverlenginginhuur,
     * or with status {@code 400 (Bad Request)} if the formulierverlenginginhuur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formulierverlenginginhuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Formulierverlenginginhuur> updateFormulierverlenginginhuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formulierverlenginginhuur formulierverlenginginhuur
    ) throws URISyntaxException {
        log.debug("REST request to update Formulierverlenginginhuur : {}, {}", id, formulierverlenginginhuur);
        if (formulierverlenginginhuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formulierverlenginginhuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formulierverlenginginhuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formulierverlenginginhuur = formulierverlenginginhuurRepository.save(formulierverlenginginhuur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formulierverlenginginhuur.getId().toString()))
            .body(formulierverlenginginhuur);
    }

    /**
     * {@code PATCH  /formulierverlenginginhuurs/:id} : Partial updates given fields of an existing formulierverlenginginhuur, field will ignore if it is null
     *
     * @param id the id of the formulierverlenginginhuur to save.
     * @param formulierverlenginginhuur the formulierverlenginginhuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formulierverlenginginhuur,
     * or with status {@code 400 (Bad Request)} if the formulierverlenginginhuur is not valid,
     * or with status {@code 404 (Not Found)} if the formulierverlenginginhuur is not found,
     * or with status {@code 500 (Internal Server Error)} if the formulierverlenginginhuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Formulierverlenginginhuur> partialUpdateFormulierverlenginginhuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formulierverlenginginhuur formulierverlenginginhuur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formulierverlenginginhuur partially : {}, {}", id, formulierverlenginginhuur);
        if (formulierverlenginginhuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formulierverlenginginhuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formulierverlenginginhuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Formulierverlenginginhuur> result = formulierverlenginginhuurRepository
            .findById(formulierverlenginginhuur.getId())
            .map(existingFormulierverlenginginhuur -> {
                if (formulierverlenginginhuur.getDatumeindenieuw() != null) {
                    existingFormulierverlenginginhuur.setDatumeindenieuw(formulierverlenginginhuur.getDatumeindenieuw());
                }
                if (formulierverlenginginhuur.getIndicatieredeninhuurgewijzigd() != null) {
                    existingFormulierverlenginginhuur.setIndicatieredeninhuurgewijzigd(
                        formulierverlenginginhuur.getIndicatieredeninhuurgewijzigd()
                    );
                }
                if (formulierverlenginginhuur.getIndicatieverhogeninkooporder() != null) {
                    existingFormulierverlenginginhuur.setIndicatieverhogeninkooporder(
                        formulierverlenginginhuur.getIndicatieverhogeninkooporder()
                    );
                }
                if (formulierverlenginginhuur.getToelichting() != null) {
                    existingFormulierverlenginginhuur.setToelichting(formulierverlenginginhuur.getToelichting());
                }

                return existingFormulierverlenginginhuur;
            })
            .map(formulierverlenginginhuurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formulierverlenginginhuur.getId().toString())
        );
    }

    /**
     * {@code GET  /formulierverlenginginhuurs} : get all the formulierverlenginginhuurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formulierverlenginginhuurs in body.
     */
    @GetMapping("")
    public List<Formulierverlenginginhuur> getAllFormulierverlenginginhuurs() {
        log.debug("REST request to get all Formulierverlenginginhuurs");
        return formulierverlenginginhuurRepository.findAll();
    }

    /**
     * {@code GET  /formulierverlenginginhuurs/:id} : get the "id" formulierverlenginginhuur.
     *
     * @param id the id of the formulierverlenginginhuur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formulierverlenginginhuur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Formulierverlenginginhuur> getFormulierverlenginginhuur(@PathVariable("id") Long id) {
        log.debug("REST request to get Formulierverlenginginhuur : {}", id);
        Optional<Formulierverlenginginhuur> formulierverlenginginhuur = formulierverlenginginhuurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formulierverlenginginhuur);
    }

    /**
     * {@code DELETE  /formulierverlenginginhuurs/:id} : delete the "id" formulierverlenginginhuur.
     *
     * @param id the id of the formulierverlenginginhuur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormulierverlenginginhuur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Formulierverlenginginhuur : {}", id);
        formulierverlenginginhuurRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
