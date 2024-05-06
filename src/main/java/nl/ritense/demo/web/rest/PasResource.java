package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Pas;
import nl.ritense.demo.repository.PasRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Pas}.
 */
@RestController
@RequestMapping("/api/pas")
@Transactional
public class PasResource {

    private final Logger log = LoggerFactory.getLogger(PasResource.class);

    private static final String ENTITY_NAME = "pas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PasRepository pasRepository;

    public PasResource(PasRepository pasRepository) {
        this.pasRepository = pasRepository;
    }

    /**
     * {@code POST  /pas} : Create a new pas.
     *
     * @param pas the pas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pas, or with status {@code 400 (Bad Request)} if the pas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pas> createPas(@Valid @RequestBody Pas pas) throws URISyntaxException {
        log.debug("REST request to save Pas : {}", pas);
        if (pas.getId() != null) {
            throw new BadRequestAlertException("A new pas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pas = pasRepository.save(pas);
        return ResponseEntity.created(new URI("/api/pas/" + pas.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, pas.getId().toString()))
            .body(pas);
    }

    /**
     * {@code PUT  /pas/:id} : Updates an existing pas.
     *
     * @param id the id of the pas to save.
     * @param pas the pas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pas,
     * or with status {@code 400 (Bad Request)} if the pas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pas> updatePas(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Pas pas)
        throws URISyntaxException {
        log.debug("REST request to update Pas : {}, {}", id, pas);
        if (pas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pas = pasRepository.save(pas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pas.getId().toString()))
            .body(pas);
    }

    /**
     * {@code PATCH  /pas/:id} : Partial updates given fields of an existing pas, field will ignore if it is null
     *
     * @param id the id of the pas to save.
     * @param pas the pas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pas,
     * or with status {@code 400 (Bad Request)} if the pas is not valid,
     * or with status {@code 404 (Not Found)} if the pas is not found,
     * or with status {@code 500 (Internal Server Error)} if the pas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pas> partialUpdatePas(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Pas pas)
        throws URISyntaxException {
        log.debug("REST request to partial update Pas partially : {}, {}", id, pas);
        if (pas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pas> result = pasRepository
            .findById(pas.getId())
            .map(existingPas -> {
                if (pas.getAdresaanduiding() != null) {
                    existingPas.setAdresaanduiding(pas.getAdresaanduiding());
                }
                if (pas.getPasnummer() != null) {
                    existingPas.setPasnummer(pas.getPasnummer());
                }

                return existingPas;
            })
            .map(pasRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pas.getId().toString())
        );
    }

    /**
     * {@code GET  /pas} : get all the pas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pas in body.
     */
    @GetMapping("")
    public List<Pas> getAllPas(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Pas");
        if (eagerload) {
            return pasRepository.findAllWithEagerRelationships();
        } else {
            return pasRepository.findAll();
        }
    }

    /**
     * {@code GET  /pas/:id} : get the "id" pas.
     *
     * @param id the id of the pas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pas> getPas(@PathVariable("id") Long id) {
        log.debug("REST request to get Pas : {}", id);
        Optional<Pas> pas = pasRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(pas);
    }

    /**
     * {@code DELETE  /pas/:id} : delete the "id" pas.
     *
     * @param id the id of the pas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePas(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pas : {}", id);
        pasRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
