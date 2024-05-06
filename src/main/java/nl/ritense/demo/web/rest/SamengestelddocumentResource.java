package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Samengestelddocument;
import nl.ritense.demo.repository.SamengestelddocumentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Samengestelddocument}.
 */
@RestController
@RequestMapping("/api/samengestelddocuments")
@Transactional
public class SamengestelddocumentResource {

    private final Logger log = LoggerFactory.getLogger(SamengestelddocumentResource.class);

    private static final String ENTITY_NAME = "samengestelddocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SamengestelddocumentRepository samengestelddocumentRepository;

    public SamengestelddocumentResource(SamengestelddocumentRepository samengestelddocumentRepository) {
        this.samengestelddocumentRepository = samengestelddocumentRepository;
    }

    /**
     * {@code POST  /samengestelddocuments} : Create a new samengestelddocument.
     *
     * @param samengestelddocument the samengestelddocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new samengestelddocument, or with status {@code 400 (Bad Request)} if the samengestelddocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Samengestelddocument> createSamengestelddocument(@RequestBody Samengestelddocument samengestelddocument)
        throws URISyntaxException {
        log.debug("REST request to save Samengestelddocument : {}", samengestelddocument);
        if (samengestelddocument.getId() != null) {
            throw new BadRequestAlertException("A new samengestelddocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        samengestelddocument = samengestelddocumentRepository.save(samengestelddocument);
        return ResponseEntity.created(new URI("/api/samengestelddocuments/" + samengestelddocument.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, samengestelddocument.getId().toString()))
            .body(samengestelddocument);
    }

    /**
     * {@code GET  /samengestelddocuments} : get all the samengestelddocuments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of samengestelddocuments in body.
     */
    @GetMapping("")
    public List<Samengestelddocument> getAllSamengestelddocuments() {
        log.debug("REST request to get all Samengestelddocuments");
        return samengestelddocumentRepository.findAll();
    }

    /**
     * {@code GET  /samengestelddocuments/:id} : get the "id" samengestelddocument.
     *
     * @param id the id of the samengestelddocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the samengestelddocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Samengestelddocument> getSamengestelddocument(@PathVariable("id") Long id) {
        log.debug("REST request to get Samengestelddocument : {}", id);
        Optional<Samengestelddocument> samengestelddocument = samengestelddocumentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(samengestelddocument);
    }

    /**
     * {@code DELETE  /samengestelddocuments/:id} : delete the "id" samengestelddocument.
     *
     * @param id the id of the samengestelddocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSamengestelddocument(@PathVariable("id") Long id) {
        log.debug("REST request to delete Samengestelddocument : {}", id);
        samengestelddocumentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
