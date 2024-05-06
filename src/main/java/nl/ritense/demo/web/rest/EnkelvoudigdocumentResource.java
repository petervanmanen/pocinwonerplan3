package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Enkelvoudigdocument;
import nl.ritense.demo.repository.EnkelvoudigdocumentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Enkelvoudigdocument}.
 */
@RestController
@RequestMapping("/api/enkelvoudigdocuments")
@Transactional
public class EnkelvoudigdocumentResource {

    private final Logger log = LoggerFactory.getLogger(EnkelvoudigdocumentResource.class);

    private static final String ENTITY_NAME = "enkelvoudigdocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnkelvoudigdocumentRepository enkelvoudigdocumentRepository;

    public EnkelvoudigdocumentResource(EnkelvoudigdocumentRepository enkelvoudigdocumentRepository) {
        this.enkelvoudigdocumentRepository = enkelvoudigdocumentRepository;
    }

    /**
     * {@code POST  /enkelvoudigdocuments} : Create a new enkelvoudigdocument.
     *
     * @param enkelvoudigdocument the enkelvoudigdocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enkelvoudigdocument, or with status {@code 400 (Bad Request)} if the enkelvoudigdocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Enkelvoudigdocument> createEnkelvoudigdocument(@Valid @RequestBody Enkelvoudigdocument enkelvoudigdocument)
        throws URISyntaxException {
        log.debug("REST request to save Enkelvoudigdocument : {}", enkelvoudigdocument);
        if (enkelvoudigdocument.getId() != null) {
            throw new BadRequestAlertException("A new enkelvoudigdocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        enkelvoudigdocument = enkelvoudigdocumentRepository.save(enkelvoudigdocument);
        return ResponseEntity.created(new URI("/api/enkelvoudigdocuments/" + enkelvoudigdocument.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, enkelvoudigdocument.getId().toString()))
            .body(enkelvoudigdocument);
    }

    /**
     * {@code PUT  /enkelvoudigdocuments/:id} : Updates an existing enkelvoudigdocument.
     *
     * @param id the id of the enkelvoudigdocument to save.
     * @param enkelvoudigdocument the enkelvoudigdocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enkelvoudigdocument,
     * or with status {@code 400 (Bad Request)} if the enkelvoudigdocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enkelvoudigdocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Enkelvoudigdocument> updateEnkelvoudigdocument(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Enkelvoudigdocument enkelvoudigdocument
    ) throws URISyntaxException {
        log.debug("REST request to update Enkelvoudigdocument : {}, {}", id, enkelvoudigdocument);
        if (enkelvoudigdocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enkelvoudigdocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enkelvoudigdocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        enkelvoudigdocument = enkelvoudigdocumentRepository.save(enkelvoudigdocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, enkelvoudigdocument.getId().toString()))
            .body(enkelvoudigdocument);
    }

    /**
     * {@code PATCH  /enkelvoudigdocuments/:id} : Partial updates given fields of an existing enkelvoudigdocument, field will ignore if it is null
     *
     * @param id the id of the enkelvoudigdocument to save.
     * @param enkelvoudigdocument the enkelvoudigdocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enkelvoudigdocument,
     * or with status {@code 400 (Bad Request)} if the enkelvoudigdocument is not valid,
     * or with status {@code 404 (Not Found)} if the enkelvoudigdocument is not found,
     * or with status {@code 500 (Internal Server Error)} if the enkelvoudigdocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Enkelvoudigdocument> partialUpdateEnkelvoudigdocument(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Enkelvoudigdocument enkelvoudigdocument
    ) throws URISyntaxException {
        log.debug("REST request to partial update Enkelvoudigdocument partially : {}, {}", id, enkelvoudigdocument);
        if (enkelvoudigdocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enkelvoudigdocument.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enkelvoudigdocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Enkelvoudigdocument> result = enkelvoudigdocumentRepository
            .findById(enkelvoudigdocument.getId())
            .map(existingEnkelvoudigdocument -> {
                if (enkelvoudigdocument.getBestandsnaam() != null) {
                    existingEnkelvoudigdocument.setBestandsnaam(enkelvoudigdocument.getBestandsnaam());
                }
                if (enkelvoudigdocument.getDocumentformaat() != null) {
                    existingEnkelvoudigdocument.setDocumentformaat(enkelvoudigdocument.getDocumentformaat());
                }
                if (enkelvoudigdocument.getDocumentinhoud() != null) {
                    existingEnkelvoudigdocument.setDocumentinhoud(enkelvoudigdocument.getDocumentinhoud());
                }
                if (enkelvoudigdocument.getDocumentlink() != null) {
                    existingEnkelvoudigdocument.setDocumentlink(enkelvoudigdocument.getDocumentlink());
                }
                if (enkelvoudigdocument.getDocumentstatus() != null) {
                    existingEnkelvoudigdocument.setDocumentstatus(enkelvoudigdocument.getDocumentstatus());
                }
                if (enkelvoudigdocument.getDocumenttaal() != null) {
                    existingEnkelvoudigdocument.setDocumenttaal(enkelvoudigdocument.getDocumenttaal());
                }
                if (enkelvoudigdocument.getDocumentversie() != null) {
                    existingEnkelvoudigdocument.setDocumentversie(enkelvoudigdocument.getDocumentversie());
                }

                return existingEnkelvoudigdocument;
            })
            .map(enkelvoudigdocumentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, enkelvoudigdocument.getId().toString())
        );
    }

    /**
     * {@code GET  /enkelvoudigdocuments} : get all the enkelvoudigdocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enkelvoudigdocuments in body.
     */
    @GetMapping("")
    public List<Enkelvoudigdocument> getAllEnkelvoudigdocuments() {
        log.debug("REST request to get all Enkelvoudigdocuments");
        return enkelvoudigdocumentRepository.findAll();
    }

    /**
     * {@code GET  /enkelvoudigdocuments/:id} : get the "id" enkelvoudigdocument.
     *
     * @param id the id of the enkelvoudigdocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enkelvoudigdocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Enkelvoudigdocument> getEnkelvoudigdocument(@PathVariable("id") Long id) {
        log.debug("REST request to get Enkelvoudigdocument : {}", id);
        Optional<Enkelvoudigdocument> enkelvoudigdocument = enkelvoudigdocumentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(enkelvoudigdocument);
    }

    /**
     * {@code DELETE  /enkelvoudigdocuments/:id} : delete the "id" enkelvoudigdocument.
     *
     * @param id the id of the enkelvoudigdocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnkelvoudigdocument(@PathVariable("id") Long id) {
        log.debug("REST request to delete Enkelvoudigdocument : {}", id);
        enkelvoudigdocumentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
