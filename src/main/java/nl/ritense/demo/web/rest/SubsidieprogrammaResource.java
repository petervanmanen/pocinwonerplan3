package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Subsidieprogramma;
import nl.ritense.demo.repository.SubsidieprogrammaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Subsidieprogramma}.
 */
@RestController
@RequestMapping("/api/subsidieprogrammas")
@Transactional
public class SubsidieprogrammaResource {

    private final Logger log = LoggerFactory.getLogger(SubsidieprogrammaResource.class);

    private static final String ENTITY_NAME = "subsidieprogramma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubsidieprogrammaRepository subsidieprogrammaRepository;

    public SubsidieprogrammaResource(SubsidieprogrammaRepository subsidieprogrammaRepository) {
        this.subsidieprogrammaRepository = subsidieprogrammaRepository;
    }

    /**
     * {@code POST  /subsidieprogrammas} : Create a new subsidieprogramma.
     *
     * @param subsidieprogramma the subsidieprogramma to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subsidieprogramma, or with status {@code 400 (Bad Request)} if the subsidieprogramma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Subsidieprogramma> createSubsidieprogramma(@RequestBody Subsidieprogramma subsidieprogramma)
        throws URISyntaxException {
        log.debug("REST request to save Subsidieprogramma : {}", subsidieprogramma);
        if (subsidieprogramma.getId() != null) {
            throw new BadRequestAlertException("A new subsidieprogramma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subsidieprogramma = subsidieprogrammaRepository.save(subsidieprogramma);
        return ResponseEntity.created(new URI("/api/subsidieprogrammas/" + subsidieprogramma.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, subsidieprogramma.getId().toString()))
            .body(subsidieprogramma);
    }

    /**
     * {@code PUT  /subsidieprogrammas/:id} : Updates an existing subsidieprogramma.
     *
     * @param id the id of the subsidieprogramma to save.
     * @param subsidieprogramma the subsidieprogramma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidieprogramma,
     * or with status {@code 400 (Bad Request)} if the subsidieprogramma is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subsidieprogramma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Subsidieprogramma> updateSubsidieprogramma(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Subsidieprogramma subsidieprogramma
    ) throws URISyntaxException {
        log.debug("REST request to update Subsidieprogramma : {}, {}", id, subsidieprogramma);
        if (subsidieprogramma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidieprogramma.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidieprogrammaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subsidieprogramma = subsidieprogrammaRepository.save(subsidieprogramma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidieprogramma.getId().toString()))
            .body(subsidieprogramma);
    }

    /**
     * {@code PATCH  /subsidieprogrammas/:id} : Partial updates given fields of an existing subsidieprogramma, field will ignore if it is null
     *
     * @param id the id of the subsidieprogramma to save.
     * @param subsidieprogramma the subsidieprogramma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidieprogramma,
     * or with status {@code 400 (Bad Request)} if the subsidieprogramma is not valid,
     * or with status {@code 404 (Not Found)} if the subsidieprogramma is not found,
     * or with status {@code 500 (Internal Server Error)} if the subsidieprogramma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Subsidieprogramma> partialUpdateSubsidieprogramma(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Subsidieprogramma subsidieprogramma
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subsidieprogramma partially : {}, {}", id, subsidieprogramma);
        if (subsidieprogramma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidieprogramma.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidieprogrammaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Subsidieprogramma> result = subsidieprogrammaRepository
            .findById(subsidieprogramma.getId())
            .map(existingSubsidieprogramma -> {
                if (subsidieprogramma.getDatumeinde() != null) {
                    existingSubsidieprogramma.setDatumeinde(subsidieprogramma.getDatumeinde());
                }
                if (subsidieprogramma.getDatumstart() != null) {
                    existingSubsidieprogramma.setDatumstart(subsidieprogramma.getDatumstart());
                }
                if (subsidieprogramma.getNaam() != null) {
                    existingSubsidieprogramma.setNaam(subsidieprogramma.getNaam());
                }
                if (subsidieprogramma.getOmschrijving() != null) {
                    existingSubsidieprogramma.setOmschrijving(subsidieprogramma.getOmschrijving());
                }
                if (subsidieprogramma.getProgrammabegroting() != null) {
                    existingSubsidieprogramma.setProgrammabegroting(subsidieprogramma.getProgrammabegroting());
                }

                return existingSubsidieprogramma;
            })
            .map(subsidieprogrammaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidieprogramma.getId().toString())
        );
    }

    /**
     * {@code GET  /subsidieprogrammas} : get all the subsidieprogrammas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subsidieprogrammas in body.
     */
    @GetMapping("")
    public List<Subsidieprogramma> getAllSubsidieprogrammas() {
        log.debug("REST request to get all Subsidieprogrammas");
        return subsidieprogrammaRepository.findAll();
    }

    /**
     * {@code GET  /subsidieprogrammas/:id} : get the "id" subsidieprogramma.
     *
     * @param id the id of the subsidieprogramma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subsidieprogramma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subsidieprogramma> getSubsidieprogramma(@PathVariable("id") Long id) {
        log.debug("REST request to get Subsidieprogramma : {}", id);
        Optional<Subsidieprogramma> subsidieprogramma = subsidieprogrammaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subsidieprogramma);
    }

    /**
     * {@code DELETE  /subsidieprogrammas/:id} : delete the "id" subsidieprogramma.
     *
     * @param id the id of the subsidieprogramma to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubsidieprogramma(@PathVariable("id") Long id) {
        log.debug("REST request to delete Subsidieprogramma : {}", id);
        subsidieprogrammaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
