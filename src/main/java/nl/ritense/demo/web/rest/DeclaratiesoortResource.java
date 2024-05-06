package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Declaratiesoort;
import nl.ritense.demo.repository.DeclaratiesoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Declaratiesoort}.
 */
@RestController
@RequestMapping("/api/declaratiesoorts")
@Transactional
public class DeclaratiesoortResource {

    private final Logger log = LoggerFactory.getLogger(DeclaratiesoortResource.class);

    private static final String ENTITY_NAME = "declaratiesoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeclaratiesoortRepository declaratiesoortRepository;

    public DeclaratiesoortResource(DeclaratiesoortRepository declaratiesoortRepository) {
        this.declaratiesoortRepository = declaratiesoortRepository;
    }

    /**
     * {@code POST  /declaratiesoorts} : Create a new declaratiesoort.
     *
     * @param declaratiesoort the declaratiesoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new declaratiesoort, or with status {@code 400 (Bad Request)} if the declaratiesoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Declaratiesoort> createDeclaratiesoort(@Valid @RequestBody Declaratiesoort declaratiesoort)
        throws URISyntaxException {
        log.debug("REST request to save Declaratiesoort : {}", declaratiesoort);
        if (declaratiesoort.getId() != null) {
            throw new BadRequestAlertException("A new declaratiesoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        declaratiesoort = declaratiesoortRepository.save(declaratiesoort);
        return ResponseEntity.created(new URI("/api/declaratiesoorts/" + declaratiesoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, declaratiesoort.getId().toString()))
            .body(declaratiesoort);
    }

    /**
     * {@code PUT  /declaratiesoorts/:id} : Updates an existing declaratiesoort.
     *
     * @param id the id of the declaratiesoort to save.
     * @param declaratiesoort the declaratiesoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declaratiesoort,
     * or with status {@code 400 (Bad Request)} if the declaratiesoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the declaratiesoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Declaratiesoort> updateDeclaratiesoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Declaratiesoort declaratiesoort
    ) throws URISyntaxException {
        log.debug("REST request to update Declaratiesoort : {}, {}", id, declaratiesoort);
        if (declaratiesoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declaratiesoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declaratiesoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        declaratiesoort = declaratiesoortRepository.save(declaratiesoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, declaratiesoort.getId().toString()))
            .body(declaratiesoort);
    }

    /**
     * {@code PATCH  /declaratiesoorts/:id} : Partial updates given fields of an existing declaratiesoort, field will ignore if it is null
     *
     * @param id the id of the declaratiesoort to save.
     * @param declaratiesoort the declaratiesoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declaratiesoort,
     * or with status {@code 400 (Bad Request)} if the declaratiesoort is not valid,
     * or with status {@code 404 (Not Found)} if the declaratiesoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the declaratiesoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Declaratiesoort> partialUpdateDeclaratiesoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Declaratiesoort declaratiesoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Declaratiesoort partially : {}, {}", id, declaratiesoort);
        if (declaratiesoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declaratiesoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declaratiesoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Declaratiesoort> result = declaratiesoortRepository
            .findById(declaratiesoort.getId())
            .map(existingDeclaratiesoort -> {
                if (declaratiesoort.getNaam() != null) {
                    existingDeclaratiesoort.setNaam(declaratiesoort.getNaam());
                }
                if (declaratiesoort.getOmschrijving() != null) {
                    existingDeclaratiesoort.setOmschrijving(declaratiesoort.getOmschrijving());
                }

                return existingDeclaratiesoort;
            })
            .map(declaratiesoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, declaratiesoort.getId().toString())
        );
    }

    /**
     * {@code GET  /declaratiesoorts} : get all the declaratiesoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of declaratiesoorts in body.
     */
    @GetMapping("")
    public List<Declaratiesoort> getAllDeclaratiesoorts() {
        log.debug("REST request to get all Declaratiesoorts");
        return declaratiesoortRepository.findAll();
    }

    /**
     * {@code GET  /declaratiesoorts/:id} : get the "id" declaratiesoort.
     *
     * @param id the id of the declaratiesoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the declaratiesoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Declaratiesoort> getDeclaratiesoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Declaratiesoort : {}", id);
        Optional<Declaratiesoort> declaratiesoort = declaratiesoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(declaratiesoort);
    }

    /**
     * {@code DELETE  /declaratiesoorts/:id} : delete the "id" declaratiesoort.
     *
     * @param id the id of the declaratiesoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeclaratiesoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Declaratiesoort : {}", id);
        declaratiesoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
