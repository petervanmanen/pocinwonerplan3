package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Declaratieregel;
import nl.ritense.demo.repository.DeclaratieregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Declaratieregel}.
 */
@RestController
@RequestMapping("/api/declaratieregels")
@Transactional
public class DeclaratieregelResource {

    private final Logger log = LoggerFactory.getLogger(DeclaratieregelResource.class);

    private static final String ENTITY_NAME = "declaratieregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeclaratieregelRepository declaratieregelRepository;

    public DeclaratieregelResource(DeclaratieregelRepository declaratieregelRepository) {
        this.declaratieregelRepository = declaratieregelRepository;
    }

    /**
     * {@code POST  /declaratieregels} : Create a new declaratieregel.
     *
     * @param declaratieregel the declaratieregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new declaratieregel, or with status {@code 400 (Bad Request)} if the declaratieregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Declaratieregel> createDeclaratieregel(@Valid @RequestBody Declaratieregel declaratieregel)
        throws URISyntaxException {
        log.debug("REST request to save Declaratieregel : {}", declaratieregel);
        if (declaratieregel.getId() != null) {
            throw new BadRequestAlertException("A new declaratieregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        declaratieregel = declaratieregelRepository.save(declaratieregel);
        return ResponseEntity.created(new URI("/api/declaratieregels/" + declaratieregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, declaratieregel.getId().toString()))
            .body(declaratieregel);
    }

    /**
     * {@code PUT  /declaratieregels/:id} : Updates an existing declaratieregel.
     *
     * @param id the id of the declaratieregel to save.
     * @param declaratieregel the declaratieregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declaratieregel,
     * or with status {@code 400 (Bad Request)} if the declaratieregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the declaratieregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Declaratieregel> updateDeclaratieregel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Declaratieregel declaratieregel
    ) throws URISyntaxException {
        log.debug("REST request to update Declaratieregel : {}, {}", id, declaratieregel);
        if (declaratieregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declaratieregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declaratieregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        declaratieregel = declaratieregelRepository.save(declaratieregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, declaratieregel.getId().toString()))
            .body(declaratieregel);
    }

    /**
     * {@code PATCH  /declaratieregels/:id} : Partial updates given fields of an existing declaratieregel, field will ignore if it is null
     *
     * @param id the id of the declaratieregel to save.
     * @param declaratieregel the declaratieregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declaratieregel,
     * or with status {@code 400 (Bad Request)} if the declaratieregel is not valid,
     * or with status {@code 404 (Not Found)} if the declaratieregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the declaratieregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Declaratieregel> partialUpdateDeclaratieregel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Declaratieregel declaratieregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Declaratieregel partially : {}, {}", id, declaratieregel);
        if (declaratieregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declaratieregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declaratieregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Declaratieregel> result = declaratieregelRepository
            .findById(declaratieregel.getId())
            .map(existingDeclaratieregel -> {
                if (declaratieregel.getBedrag() != null) {
                    existingDeclaratieregel.setBedrag(declaratieregel.getBedrag());
                }
                if (declaratieregel.getCode() != null) {
                    existingDeclaratieregel.setCode(declaratieregel.getCode());
                }
                if (declaratieregel.getDatumeinde() != null) {
                    existingDeclaratieregel.setDatumeinde(declaratieregel.getDatumeinde());
                }
                if (declaratieregel.getDatumstart() != null) {
                    existingDeclaratieregel.setDatumstart(declaratieregel.getDatumstart());
                }

                return existingDeclaratieregel;
            })
            .map(declaratieregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, declaratieregel.getId().toString())
        );
    }

    /**
     * {@code GET  /declaratieregels} : get all the declaratieregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of declaratieregels in body.
     */
    @GetMapping("")
    public List<Declaratieregel> getAllDeclaratieregels() {
        log.debug("REST request to get all Declaratieregels");
        return declaratieregelRepository.findAll();
    }

    /**
     * {@code GET  /declaratieregels/:id} : get the "id" declaratieregel.
     *
     * @param id the id of the declaratieregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the declaratieregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Declaratieregel> getDeclaratieregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Declaratieregel : {}", id);
        Optional<Declaratieregel> declaratieregel = declaratieregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(declaratieregel);
    }

    /**
     * {@code DELETE  /declaratieregels/:id} : delete the "id" declaratieregel.
     *
     * @param id the id of the declaratieregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeclaratieregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Declaratieregel : {}", id);
        declaratieregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
