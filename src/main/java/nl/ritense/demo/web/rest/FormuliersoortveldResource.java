package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Formuliersoortveld;
import nl.ritense.demo.repository.FormuliersoortveldRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Formuliersoortveld}.
 */
@RestController
@RequestMapping("/api/formuliersoortvelds")
@Transactional
public class FormuliersoortveldResource {

    private final Logger log = LoggerFactory.getLogger(FormuliersoortveldResource.class);

    private static final String ENTITY_NAME = "formuliersoortveld";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormuliersoortveldRepository formuliersoortveldRepository;

    public FormuliersoortveldResource(FormuliersoortveldRepository formuliersoortveldRepository) {
        this.formuliersoortveldRepository = formuliersoortveldRepository;
    }

    /**
     * {@code POST  /formuliersoortvelds} : Create a new formuliersoortveld.
     *
     * @param formuliersoortveld the formuliersoortveld to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formuliersoortveld, or with status {@code 400 (Bad Request)} if the formuliersoortveld has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Formuliersoortveld> createFormuliersoortveld(@Valid @RequestBody Formuliersoortveld formuliersoortveld)
        throws URISyntaxException {
        log.debug("REST request to save Formuliersoortveld : {}", formuliersoortveld);
        if (formuliersoortveld.getId() != null) {
            throw new BadRequestAlertException("A new formuliersoortveld cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formuliersoortveld = formuliersoortveldRepository.save(formuliersoortveld);
        return ResponseEntity.created(new URI("/api/formuliersoortvelds/" + formuliersoortveld.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, formuliersoortveld.getId().toString()))
            .body(formuliersoortveld);
    }

    /**
     * {@code PUT  /formuliersoortvelds/:id} : Updates an existing formuliersoortveld.
     *
     * @param id the id of the formuliersoortveld to save.
     * @param formuliersoortveld the formuliersoortveld to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formuliersoortveld,
     * or with status {@code 400 (Bad Request)} if the formuliersoortveld is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formuliersoortveld couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Formuliersoortveld> updateFormuliersoortveld(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Formuliersoortveld formuliersoortveld
    ) throws URISyntaxException {
        log.debug("REST request to update Formuliersoortveld : {}, {}", id, formuliersoortveld);
        if (formuliersoortveld.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formuliersoortveld.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formuliersoortveldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formuliersoortveld = formuliersoortveldRepository.save(formuliersoortveld);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formuliersoortveld.getId().toString()))
            .body(formuliersoortveld);
    }

    /**
     * {@code PATCH  /formuliersoortvelds/:id} : Partial updates given fields of an existing formuliersoortveld, field will ignore if it is null
     *
     * @param id the id of the formuliersoortveld to save.
     * @param formuliersoortveld the formuliersoortveld to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formuliersoortveld,
     * or with status {@code 400 (Bad Request)} if the formuliersoortveld is not valid,
     * or with status {@code 404 (Not Found)} if the formuliersoortveld is not found,
     * or with status {@code 500 (Internal Server Error)} if the formuliersoortveld couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Formuliersoortveld> partialUpdateFormuliersoortveld(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Formuliersoortveld formuliersoortveld
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formuliersoortveld partially : {}, {}", id, formuliersoortveld);
        if (formuliersoortveld.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formuliersoortveld.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formuliersoortveldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Formuliersoortveld> result = formuliersoortveldRepository
            .findById(formuliersoortveld.getId())
            .map(existingFormuliersoortveld -> {
                if (formuliersoortveld.getHelptekst() != null) {
                    existingFormuliersoortveld.setHelptekst(formuliersoortveld.getHelptekst());
                }
                if (formuliersoortveld.getIsverplicht() != null) {
                    existingFormuliersoortveld.setIsverplicht(formuliersoortveld.getIsverplicht());
                }
                if (formuliersoortveld.getLabel() != null) {
                    existingFormuliersoortveld.setLabel(formuliersoortveld.getLabel());
                }
                if (formuliersoortveld.getMaxlengte() != null) {
                    existingFormuliersoortveld.setMaxlengte(formuliersoortveld.getMaxlengte());
                }
                if (formuliersoortveld.getVeldnaam() != null) {
                    existingFormuliersoortveld.setVeldnaam(formuliersoortveld.getVeldnaam());
                }
                if (formuliersoortveld.getVeldtype() != null) {
                    existingFormuliersoortveld.setVeldtype(formuliersoortveld.getVeldtype());
                }

                return existingFormuliersoortveld;
            })
            .map(formuliersoortveldRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formuliersoortveld.getId().toString())
        );
    }

    /**
     * {@code GET  /formuliersoortvelds} : get all the formuliersoortvelds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formuliersoortvelds in body.
     */
    @GetMapping("")
    public List<Formuliersoortveld> getAllFormuliersoortvelds() {
        log.debug("REST request to get all Formuliersoortvelds");
        return formuliersoortveldRepository.findAll();
    }

    /**
     * {@code GET  /formuliersoortvelds/:id} : get the "id" formuliersoortveld.
     *
     * @param id the id of the formuliersoortveld to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formuliersoortveld, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Formuliersoortveld> getFormuliersoortveld(@PathVariable("id") Long id) {
        log.debug("REST request to get Formuliersoortveld : {}", id);
        Optional<Formuliersoortveld> formuliersoortveld = formuliersoortveldRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formuliersoortveld);
    }

    /**
     * {@code DELETE  /formuliersoortvelds/:id} : delete the "id" formuliersoortveld.
     *
     * @param id the id of the formuliersoortveld to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormuliersoortveld(@PathVariable("id") Long id) {
        log.debug("REST request to delete Formuliersoortveld : {}", id);
        formuliersoortveldRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
