package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Regeltekst;
import nl.ritense.demo.repository.RegeltekstRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Regeltekst}.
 */
@RestController
@RequestMapping("/api/regelteksts")
@Transactional
public class RegeltekstResource {

    private final Logger log = LoggerFactory.getLogger(RegeltekstResource.class);

    private static final String ENTITY_NAME = "regeltekst";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegeltekstRepository regeltekstRepository;

    public RegeltekstResource(RegeltekstRepository regeltekstRepository) {
        this.regeltekstRepository = regeltekstRepository;
    }

    /**
     * {@code POST  /regelteksts} : Create a new regeltekst.
     *
     * @param regeltekst the regeltekst to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regeltekst, or with status {@code 400 (Bad Request)} if the regeltekst has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Regeltekst> createRegeltekst(@Valid @RequestBody Regeltekst regeltekst) throws URISyntaxException {
        log.debug("REST request to save Regeltekst : {}", regeltekst);
        if (regeltekst.getId() != null) {
            throw new BadRequestAlertException("A new regeltekst cannot already have an ID", ENTITY_NAME, "idexists");
        }
        regeltekst = regeltekstRepository.save(regeltekst);
        return ResponseEntity.created(new URI("/api/regelteksts/" + regeltekst.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, regeltekst.getId().toString()))
            .body(regeltekst);
    }

    /**
     * {@code PUT  /regelteksts/:id} : Updates an existing regeltekst.
     *
     * @param id the id of the regeltekst to save.
     * @param regeltekst the regeltekst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regeltekst,
     * or with status {@code 400 (Bad Request)} if the regeltekst is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regeltekst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Regeltekst> updateRegeltekst(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Regeltekst regeltekst
    ) throws URISyntaxException {
        log.debug("REST request to update Regeltekst : {}, {}", id, regeltekst);
        if (regeltekst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regeltekst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regeltekstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        regeltekst = regeltekstRepository.save(regeltekst);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regeltekst.getId().toString()))
            .body(regeltekst);
    }

    /**
     * {@code PATCH  /regelteksts/:id} : Partial updates given fields of an existing regeltekst, field will ignore if it is null
     *
     * @param id the id of the regeltekst to save.
     * @param regeltekst the regeltekst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regeltekst,
     * or with status {@code 400 (Bad Request)} if the regeltekst is not valid,
     * or with status {@code 404 (Not Found)} if the regeltekst is not found,
     * or with status {@code 500 (Internal Server Error)} if the regeltekst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Regeltekst> partialUpdateRegeltekst(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Regeltekst regeltekst
    ) throws URISyntaxException {
        log.debug("REST request to partial update Regeltekst partially : {}, {}", id, regeltekst);
        if (regeltekst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regeltekst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regeltekstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Regeltekst> result = regeltekstRepository
            .findById(regeltekst.getId())
            .map(existingRegeltekst -> {
                if (regeltekst.getIdentificatie() != null) {
                    existingRegeltekst.setIdentificatie(regeltekst.getIdentificatie());
                }
                if (regeltekst.getOmschrijving() != null) {
                    existingRegeltekst.setOmschrijving(regeltekst.getOmschrijving());
                }
                if (regeltekst.getTekst() != null) {
                    existingRegeltekst.setTekst(regeltekst.getTekst());
                }

                return existingRegeltekst;
            })
            .map(regeltekstRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regeltekst.getId().toString())
        );
    }

    /**
     * {@code GET  /regelteksts} : get all the regelteksts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regelteksts in body.
     */
    @GetMapping("")
    public List<Regeltekst> getAllRegelteksts(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Regelteksts");
        if (eagerload) {
            return regeltekstRepository.findAllWithEagerRelationships();
        } else {
            return regeltekstRepository.findAll();
        }
    }

    /**
     * {@code GET  /regelteksts/:id} : get the "id" regeltekst.
     *
     * @param id the id of the regeltekst to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regeltekst, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Regeltekst> getRegeltekst(@PathVariable("id") Long id) {
        log.debug("REST request to get Regeltekst : {}", id);
        Optional<Regeltekst> regeltekst = regeltekstRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(regeltekst);
    }

    /**
     * {@code DELETE  /regelteksts/:id} : delete the "id" regeltekst.
     *
     * @param id the id of the regeltekst to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegeltekst(@PathVariable("id") Long id) {
        log.debug("REST request to delete Regeltekst : {}", id);
        regeltekstRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
