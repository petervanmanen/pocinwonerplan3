package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Brondocumenten;
import nl.ritense.demo.repository.BrondocumentenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Brondocumenten}.
 */
@RestController
@RequestMapping("/api/brondocumentens")
@Transactional
public class BrondocumentenResource {

    private final Logger log = LoggerFactory.getLogger(BrondocumentenResource.class);

    private static final String ENTITY_NAME = "brondocumenten";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BrondocumentenRepository brondocumentenRepository;

    public BrondocumentenResource(BrondocumentenRepository brondocumentenRepository) {
        this.brondocumentenRepository = brondocumentenRepository;
    }

    /**
     * {@code POST  /brondocumentens} : Create a new brondocumenten.
     *
     * @param brondocumenten the brondocumenten to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new brondocumenten, or with status {@code 400 (Bad Request)} if the brondocumenten has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Brondocumenten> createBrondocumenten(@Valid @RequestBody Brondocumenten brondocumenten)
        throws URISyntaxException {
        log.debug("REST request to save Brondocumenten : {}", brondocumenten);
        if (brondocumenten.getId() != null) {
            throw new BadRequestAlertException("A new brondocumenten cannot already have an ID", ENTITY_NAME, "idexists");
        }
        brondocumenten = brondocumentenRepository.save(brondocumenten);
        return ResponseEntity.created(new URI("/api/brondocumentens/" + brondocumenten.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, brondocumenten.getId().toString()))
            .body(brondocumenten);
    }

    /**
     * {@code PUT  /brondocumentens/:id} : Updates an existing brondocumenten.
     *
     * @param id the id of the brondocumenten to save.
     * @param brondocumenten the brondocumenten to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated brondocumenten,
     * or with status {@code 400 (Bad Request)} if the brondocumenten is not valid,
     * or with status {@code 500 (Internal Server Error)} if the brondocumenten couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Brondocumenten> updateBrondocumenten(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Brondocumenten brondocumenten
    ) throws URISyntaxException {
        log.debug("REST request to update Brondocumenten : {}, {}", id, brondocumenten);
        if (brondocumenten.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, brondocumenten.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!brondocumentenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        brondocumenten = brondocumentenRepository.save(brondocumenten);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, brondocumenten.getId().toString()))
            .body(brondocumenten);
    }

    /**
     * {@code PATCH  /brondocumentens/:id} : Partial updates given fields of an existing brondocumenten, field will ignore if it is null
     *
     * @param id the id of the brondocumenten to save.
     * @param brondocumenten the brondocumenten to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated brondocumenten,
     * or with status {@code 400 (Bad Request)} if the brondocumenten is not valid,
     * or with status {@code 404 (Not Found)} if the brondocumenten is not found,
     * or with status {@code 500 (Internal Server Error)} if the brondocumenten couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Brondocumenten> partialUpdateBrondocumenten(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Brondocumenten brondocumenten
    ) throws URISyntaxException {
        log.debug("REST request to partial update Brondocumenten partially : {}, {}", id, brondocumenten);
        if (brondocumenten.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, brondocumenten.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!brondocumentenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Brondocumenten> result = brondocumentenRepository
            .findById(brondocumenten.getId())
            .map(existingBrondocumenten -> {
                if (brondocumenten.getAktegemeente() != null) {
                    existingBrondocumenten.setAktegemeente(brondocumenten.getAktegemeente());
                }
                if (brondocumenten.getDatumdocument() != null) {
                    existingBrondocumenten.setDatumdocument(brondocumenten.getDatumdocument());
                }
                if (brondocumenten.getDocumentgemeente() != null) {
                    existingBrondocumenten.setDocumentgemeente(brondocumenten.getDocumentgemeente());
                }
                if (brondocumenten.getDocumentidentificatie() != null) {
                    existingBrondocumenten.setDocumentidentificatie(brondocumenten.getDocumentidentificatie());
                }
                if (brondocumenten.getDocumentomschrijving() != null) {
                    existingBrondocumenten.setDocumentomschrijving(brondocumenten.getDocumentomschrijving());
                }

                return existingBrondocumenten;
            })
            .map(brondocumentenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, brondocumenten.getId().toString())
        );
    }

    /**
     * {@code GET  /brondocumentens} : get all the brondocumentens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of brondocumentens in body.
     */
    @GetMapping("")
    public List<Brondocumenten> getAllBrondocumentens() {
        log.debug("REST request to get all Brondocumentens");
        return brondocumentenRepository.findAll();
    }

    /**
     * {@code GET  /brondocumentens/:id} : get the "id" brondocumenten.
     *
     * @param id the id of the brondocumenten to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the brondocumenten, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Brondocumenten> getBrondocumenten(@PathVariable("id") Long id) {
        log.debug("REST request to get Brondocumenten : {}", id);
        Optional<Brondocumenten> brondocumenten = brondocumentenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(brondocumenten);
    }

    /**
     * {@code DELETE  /brondocumentens/:id} : delete the "id" brondocumenten.
     *
     * @param id the id of the brondocumenten to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrondocumenten(@PathVariable("id") Long id) {
        log.debug("REST request to delete Brondocumenten : {}", id);
        brondocumentenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
