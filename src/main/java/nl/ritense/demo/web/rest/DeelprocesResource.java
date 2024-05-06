package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Deelproces;
import nl.ritense.demo.repository.DeelprocesRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Deelproces}.
 */
@RestController
@RequestMapping("/api/deelproces")
@Transactional
public class DeelprocesResource {

    private final Logger log = LoggerFactory.getLogger(DeelprocesResource.class);

    private static final String ENTITY_NAME = "deelproces";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeelprocesRepository deelprocesRepository;

    public DeelprocesResource(DeelprocesRepository deelprocesRepository) {
        this.deelprocesRepository = deelprocesRepository;
    }

    /**
     * {@code POST  /deelproces} : Create a new deelproces.
     *
     * @param deelproces the deelproces to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deelproces, or with status {@code 400 (Bad Request)} if the deelproces has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Deelproces> createDeelproces(@Valid @RequestBody Deelproces deelproces) throws URISyntaxException {
        log.debug("REST request to save Deelproces : {}", deelproces);
        if (deelproces.getId() != null) {
            throw new BadRequestAlertException("A new deelproces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        deelproces = deelprocesRepository.save(deelproces);
        return ResponseEntity.created(new URI("/api/deelproces/" + deelproces.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, deelproces.getId().toString()))
            .body(deelproces);
    }

    /**
     * {@code PUT  /deelproces/:id} : Updates an existing deelproces.
     *
     * @param id the id of the deelproces to save.
     * @param deelproces the deelproces to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deelproces,
     * or with status {@code 400 (Bad Request)} if the deelproces is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deelproces couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Deelproces> updateDeelproces(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Deelproces deelproces
    ) throws URISyntaxException {
        log.debug("REST request to update Deelproces : {}, {}", id, deelproces);
        if (deelproces.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deelproces.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deelprocesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        deelproces = deelprocesRepository.save(deelproces);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deelproces.getId().toString()))
            .body(deelproces);
    }

    /**
     * {@code PATCH  /deelproces/:id} : Partial updates given fields of an existing deelproces, field will ignore if it is null
     *
     * @param id the id of the deelproces to save.
     * @param deelproces the deelproces to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deelproces,
     * or with status {@code 400 (Bad Request)} if the deelproces is not valid,
     * or with status {@code 404 (Not Found)} if the deelproces is not found,
     * or with status {@code 500 (Internal Server Error)} if the deelproces couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Deelproces> partialUpdateDeelproces(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Deelproces deelproces
    ) throws URISyntaxException {
        log.debug("REST request to partial update Deelproces partially : {}, {}", id, deelproces);
        if (deelproces.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deelproces.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deelprocesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Deelproces> result = deelprocesRepository
            .findById(deelproces.getId())
            .map(existingDeelproces -> {
                if (deelproces.getDatumafgehandeld() != null) {
                    existingDeelproces.setDatumafgehandeld(deelproces.getDatumafgehandeld());
                }
                if (deelproces.getDatumgepland() != null) {
                    existingDeelproces.setDatumgepland(deelproces.getDatumgepland());
                }

                return existingDeelproces;
            })
            .map(deelprocesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deelproces.getId().toString())
        );
    }

    /**
     * {@code GET  /deelproces} : get all the deelproces.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deelproces in body.
     */
    @GetMapping("")
    public List<Deelproces> getAllDeelproces() {
        log.debug("REST request to get all Deelproces");
        return deelprocesRepository.findAll();
    }

    /**
     * {@code GET  /deelproces/:id} : get the "id" deelproces.
     *
     * @param id the id of the deelproces to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deelproces, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Deelproces> getDeelproces(@PathVariable("id") Long id) {
        log.debug("REST request to get Deelproces : {}", id);
        Optional<Deelproces> deelproces = deelprocesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deelproces);
    }

    /**
     * {@code DELETE  /deelproces/:id} : delete the "id" deelproces.
     *
     * @param id the id of the deelproces to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeelproces(@PathVariable("id") Long id) {
        log.debug("REST request to delete Deelproces : {}", id);
        deelprocesRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
