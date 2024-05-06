package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Documenttype;
import nl.ritense.demo.repository.DocumenttypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Documenttype}.
 */
@RestController
@RequestMapping("/api/documenttypes")
@Transactional
public class DocumenttypeResource {

    private final Logger log = LoggerFactory.getLogger(DocumenttypeResource.class);

    private static final String ENTITY_NAME = "documenttype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumenttypeRepository documenttypeRepository;

    public DocumenttypeResource(DocumenttypeRepository documenttypeRepository) {
        this.documenttypeRepository = documenttypeRepository;
    }

    /**
     * {@code POST  /documenttypes} : Create a new documenttype.
     *
     * @param documenttype the documenttype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documenttype, or with status {@code 400 (Bad Request)} if the documenttype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Documenttype> createDocumenttype(@Valid @RequestBody Documenttype documenttype) throws URISyntaxException {
        log.debug("REST request to save Documenttype : {}", documenttype);
        if (documenttype.getId() != null) {
            throw new BadRequestAlertException("A new documenttype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        documenttype = documenttypeRepository.save(documenttype);
        return ResponseEntity.created(new URI("/api/documenttypes/" + documenttype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, documenttype.getId().toString()))
            .body(documenttype);
    }

    /**
     * {@code PUT  /documenttypes/:id} : Updates an existing documenttype.
     *
     * @param id the id of the documenttype to save.
     * @param documenttype the documenttype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documenttype,
     * or with status {@code 400 (Bad Request)} if the documenttype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documenttype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Documenttype> updateDocumenttype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Documenttype documenttype
    ) throws URISyntaxException {
        log.debug("REST request to update Documenttype : {}, {}", id, documenttype);
        if (documenttype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documenttype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documenttypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documenttype = documenttypeRepository.save(documenttype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documenttype.getId().toString()))
            .body(documenttype);
    }

    /**
     * {@code PATCH  /documenttypes/:id} : Partial updates given fields of an existing documenttype, field will ignore if it is null
     *
     * @param id the id of the documenttype to save.
     * @param documenttype the documenttype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documenttype,
     * or with status {@code 400 (Bad Request)} if the documenttype is not valid,
     * or with status {@code 404 (Not Found)} if the documenttype is not found,
     * or with status {@code 500 (Internal Server Error)} if the documenttype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Documenttype> partialUpdateDocumenttype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Documenttype documenttype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Documenttype partially : {}, {}", id, documenttype);
        if (documenttype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documenttype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documenttypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Documenttype> result = documenttypeRepository
            .findById(documenttype.getId())
            .map(existingDocumenttype -> {
                if (documenttype.getDatumbegingeldigheiddocumenttype() != null) {
                    existingDocumenttype.setDatumbegingeldigheiddocumenttype(documenttype.getDatumbegingeldigheiddocumenttype());
                }
                if (documenttype.getDatumeindegeldigheiddocumenttype() != null) {
                    existingDocumenttype.setDatumeindegeldigheiddocumenttype(documenttype.getDatumeindegeldigheiddocumenttype());
                }
                if (documenttype.getDocumentcategorie() != null) {
                    existingDocumenttype.setDocumentcategorie(documenttype.getDocumentcategorie());
                }
                if (documenttype.getDocumenttypeomschrijving() != null) {
                    existingDocumenttype.setDocumenttypeomschrijving(documenttype.getDocumenttypeomschrijving());
                }
                if (documenttype.getDocumenttypeomschrijvinggeneriek() != null) {
                    existingDocumenttype.setDocumenttypeomschrijvinggeneriek(documenttype.getDocumenttypeomschrijvinggeneriek());
                }
                if (documenttype.getDocumenttypetrefwoord() != null) {
                    existingDocumenttype.setDocumenttypetrefwoord(documenttype.getDocumenttypetrefwoord());
                }

                return existingDocumenttype;
            })
            .map(documenttypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documenttype.getId().toString())
        );
    }

    /**
     * {@code GET  /documenttypes} : get all the documenttypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documenttypes in body.
     */
    @GetMapping("")
    public List<Documenttype> getAllDocumenttypes() {
        log.debug("REST request to get all Documenttypes");
        return documenttypeRepository.findAll();
    }

    /**
     * {@code GET  /documenttypes/:id} : get the "id" documenttype.
     *
     * @param id the id of the documenttype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documenttype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Documenttype> getDocumenttype(@PathVariable("id") Long id) {
        log.debug("REST request to get Documenttype : {}", id);
        Optional<Documenttype> documenttype = documenttypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documenttype);
    }

    /**
     * {@code DELETE  /documenttypes/:id} : delete the "id" documenttype.
     *
     * @param id the id of the documenttype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumenttype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Documenttype : {}", id);
        documenttypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
