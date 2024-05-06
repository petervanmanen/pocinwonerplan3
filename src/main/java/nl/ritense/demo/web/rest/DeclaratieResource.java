package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Declaratie;
import nl.ritense.demo.repository.DeclaratieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Declaratie}.
 */
@RestController
@RequestMapping("/api/declaraties")
@Transactional
public class DeclaratieResource {

    private final Logger log = LoggerFactory.getLogger(DeclaratieResource.class);

    private static final String ENTITY_NAME = "declaratie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeclaratieRepository declaratieRepository;

    public DeclaratieResource(DeclaratieRepository declaratieRepository) {
        this.declaratieRepository = declaratieRepository;
    }

    /**
     * {@code POST  /declaraties} : Create a new declaratie.
     *
     * @param declaratie the declaratie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new declaratie, or with status {@code 400 (Bad Request)} if the declaratie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Declaratie> createDeclaratie(@Valid @RequestBody Declaratie declaratie) throws URISyntaxException {
        log.debug("REST request to save Declaratie : {}", declaratie);
        if (declaratie.getId() != null) {
            throw new BadRequestAlertException("A new declaratie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        declaratie = declaratieRepository.save(declaratie);
        return ResponseEntity.created(new URI("/api/declaraties/" + declaratie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, declaratie.getId().toString()))
            .body(declaratie);
    }

    /**
     * {@code PUT  /declaraties/:id} : Updates an existing declaratie.
     *
     * @param id the id of the declaratie to save.
     * @param declaratie the declaratie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declaratie,
     * or with status {@code 400 (Bad Request)} if the declaratie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the declaratie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Declaratie> updateDeclaratie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Declaratie declaratie
    ) throws URISyntaxException {
        log.debug("REST request to update Declaratie : {}, {}", id, declaratie);
        if (declaratie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declaratie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declaratieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        declaratie = declaratieRepository.save(declaratie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, declaratie.getId().toString()))
            .body(declaratie);
    }

    /**
     * {@code PATCH  /declaraties/:id} : Partial updates given fields of an existing declaratie, field will ignore if it is null
     *
     * @param id the id of the declaratie to save.
     * @param declaratie the declaratie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declaratie,
     * or with status {@code 400 (Bad Request)} if the declaratie is not valid,
     * or with status {@code 404 (Not Found)} if the declaratie is not found,
     * or with status {@code 500 (Internal Server Error)} if the declaratie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Declaratie> partialUpdateDeclaratie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Declaratie declaratie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Declaratie partially : {}, {}", id, declaratie);
        if (declaratie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declaratie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declaratieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Declaratie> result = declaratieRepository
            .findById(declaratie.getId())
            .map(existingDeclaratie -> {
                if (declaratie.getDatumdeclaratie() != null) {
                    existingDeclaratie.setDatumdeclaratie(declaratie.getDatumdeclaratie());
                }
                if (declaratie.getDeclaratiebedrag() != null) {
                    existingDeclaratie.setDeclaratiebedrag(declaratie.getDeclaratiebedrag());
                }
                if (declaratie.getDeclaratiestatus() != null) {
                    existingDeclaratie.setDeclaratiestatus(declaratie.getDeclaratiestatus());
                }

                return existingDeclaratie;
            })
            .map(declaratieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, declaratie.getId().toString())
        );
    }

    /**
     * {@code GET  /declaraties} : get all the declaraties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of declaraties in body.
     */
    @GetMapping("")
    public List<Declaratie> getAllDeclaraties() {
        log.debug("REST request to get all Declaraties");
        return declaratieRepository.findAll();
    }

    /**
     * {@code GET  /declaraties/:id} : get the "id" declaratie.
     *
     * @param id the id of the declaratie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the declaratie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Declaratie> getDeclaratie(@PathVariable("id") Long id) {
        log.debug("REST request to get Declaratie : {}", id);
        Optional<Declaratie> declaratie = declaratieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(declaratie);
    }

    /**
     * {@code DELETE  /declaraties/:id} : delete the "id" declaratie.
     *
     * @param id the id of the declaratie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeclaratie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Declaratie : {}", id);
        declaratieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
