package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Omgevingsdocument;
import nl.ritense.demo.repository.OmgevingsdocumentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Omgevingsdocument}.
 */
@RestController
@RequestMapping("/api/omgevingsdocuments")
@Transactional
public class OmgevingsdocumentResource {

    private final Logger log = LoggerFactory.getLogger(OmgevingsdocumentResource.class);

    private static final String ENTITY_NAME = "omgevingsdocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OmgevingsdocumentRepository omgevingsdocumentRepository;

    public OmgevingsdocumentResource(OmgevingsdocumentRepository omgevingsdocumentRepository) {
        this.omgevingsdocumentRepository = omgevingsdocumentRepository;
    }

    /**
     * {@code POST  /omgevingsdocuments} : Create a new omgevingsdocument.
     *
     * @param omgevingsdocument the omgevingsdocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new omgevingsdocument, or with status {@code 400 (Bad Request)} if the omgevingsdocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Omgevingsdocument> createOmgevingsdocument(@Valid @RequestBody Omgevingsdocument omgevingsdocument)
        throws URISyntaxException {
        log.debug("REST request to save Omgevingsdocument : {}", omgevingsdocument);
        if (omgevingsdocument.getId() != null) {
            throw new BadRequestAlertException("A new omgevingsdocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        omgevingsdocument = omgevingsdocumentRepository.save(omgevingsdocument);
        return ResponseEntity.created(new URI("/api/omgevingsdocuments/" + omgevingsdocument.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, omgevingsdocument.getId().toString()))
            .body(omgevingsdocument);
    }

    /**
     * {@code GET  /omgevingsdocuments} : get all the omgevingsdocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of omgevingsdocuments in body.
     */
    @GetMapping("")
    public List<Omgevingsdocument> getAllOmgevingsdocuments() {
        log.debug("REST request to get all Omgevingsdocuments");
        return omgevingsdocumentRepository.findAll();
    }

    /**
     * {@code GET  /omgevingsdocuments/:id} : get the "id" omgevingsdocument.
     *
     * @param id the id of the omgevingsdocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the omgevingsdocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Omgevingsdocument> getOmgevingsdocument(@PathVariable("id") Long id) {
        log.debug("REST request to get Omgevingsdocument : {}", id);
        Optional<Omgevingsdocument> omgevingsdocument = omgevingsdocumentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(omgevingsdocument);
    }

    /**
     * {@code DELETE  /omgevingsdocuments/:id} : delete the "id" omgevingsdocument.
     *
     * @param id the id of the omgevingsdocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOmgevingsdocument(@PathVariable("id") Long id) {
        log.debug("REST request to delete Omgevingsdocument : {}", id);
        omgevingsdocumentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
