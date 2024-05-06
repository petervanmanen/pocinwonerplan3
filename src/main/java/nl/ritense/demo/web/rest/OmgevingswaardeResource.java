package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Omgevingswaarde;
import nl.ritense.demo.repository.OmgevingswaardeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Omgevingswaarde}.
 */
@RestController
@RequestMapping("/api/omgevingswaardes")
@Transactional
public class OmgevingswaardeResource {

    private final Logger log = LoggerFactory.getLogger(OmgevingswaardeResource.class);

    private static final String ENTITY_NAME = "omgevingswaarde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OmgevingswaardeRepository omgevingswaardeRepository;

    public OmgevingswaardeResource(OmgevingswaardeRepository omgevingswaardeRepository) {
        this.omgevingswaardeRepository = omgevingswaardeRepository;
    }

    /**
     * {@code POST  /omgevingswaardes} : Create a new omgevingswaarde.
     *
     * @param omgevingswaarde the omgevingswaarde to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new omgevingswaarde, or with status {@code 400 (Bad Request)} if the omgevingswaarde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Omgevingswaarde> createOmgevingswaarde(@RequestBody Omgevingswaarde omgevingswaarde) throws URISyntaxException {
        log.debug("REST request to save Omgevingswaarde : {}", omgevingswaarde);
        if (omgevingswaarde.getId() != null) {
            throw new BadRequestAlertException("A new omgevingswaarde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        omgevingswaarde = omgevingswaardeRepository.save(omgevingswaarde);
        return ResponseEntity.created(new URI("/api/omgevingswaardes/" + omgevingswaarde.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, omgevingswaarde.getId().toString()))
            .body(omgevingswaarde);
    }

    /**
     * {@code PUT  /omgevingswaardes/:id} : Updates an existing omgevingswaarde.
     *
     * @param id the id of the omgevingswaarde to save.
     * @param omgevingswaarde the omgevingswaarde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omgevingswaarde,
     * or with status {@code 400 (Bad Request)} if the omgevingswaarde is not valid,
     * or with status {@code 500 (Internal Server Error)} if the omgevingswaarde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Omgevingswaarde> updateOmgevingswaarde(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Omgevingswaarde omgevingswaarde
    ) throws URISyntaxException {
        log.debug("REST request to update Omgevingswaarde : {}, {}", id, omgevingswaarde);
        if (omgevingswaarde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omgevingswaarde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omgevingswaardeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        omgevingswaarde = omgevingswaardeRepository.save(omgevingswaarde);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omgevingswaarde.getId().toString()))
            .body(omgevingswaarde);
    }

    /**
     * {@code PATCH  /omgevingswaardes/:id} : Partial updates given fields of an existing omgevingswaarde, field will ignore if it is null
     *
     * @param id the id of the omgevingswaarde to save.
     * @param omgevingswaarde the omgevingswaarde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated omgevingswaarde,
     * or with status {@code 400 (Bad Request)} if the omgevingswaarde is not valid,
     * or with status {@code 404 (Not Found)} if the omgevingswaarde is not found,
     * or with status {@code 500 (Internal Server Error)} if the omgevingswaarde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Omgevingswaarde> partialUpdateOmgevingswaarde(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Omgevingswaarde omgevingswaarde
    ) throws URISyntaxException {
        log.debug("REST request to partial update Omgevingswaarde partially : {}, {}", id, omgevingswaarde);
        if (omgevingswaarde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, omgevingswaarde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!omgevingswaardeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Omgevingswaarde> result = omgevingswaardeRepository
            .findById(omgevingswaarde.getId())
            .map(existingOmgevingswaarde -> {
                if (omgevingswaarde.getNaam() != null) {
                    existingOmgevingswaarde.setNaam(omgevingswaarde.getNaam());
                }
                if (omgevingswaarde.getOmgevingswaardegroep() != null) {
                    existingOmgevingswaarde.setOmgevingswaardegroep(omgevingswaarde.getOmgevingswaardegroep());
                }

                return existingOmgevingswaarde;
            })
            .map(omgevingswaardeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, omgevingswaarde.getId().toString())
        );
    }

    /**
     * {@code GET  /omgevingswaardes} : get all the omgevingswaardes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of omgevingswaardes in body.
     */
    @GetMapping("")
    public List<Omgevingswaarde> getAllOmgevingswaardes() {
        log.debug("REST request to get all Omgevingswaardes");
        return omgevingswaardeRepository.findAll();
    }

    /**
     * {@code GET  /omgevingswaardes/:id} : get the "id" omgevingswaarde.
     *
     * @param id the id of the omgevingswaarde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the omgevingswaarde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Omgevingswaarde> getOmgevingswaarde(@PathVariable("id") Long id) {
        log.debug("REST request to get Omgevingswaarde : {}", id);
        Optional<Omgevingswaarde> omgevingswaarde = omgevingswaardeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(omgevingswaarde);
    }

    /**
     * {@code DELETE  /omgevingswaardes/:id} : delete the "id" omgevingswaarde.
     *
     * @param id the id of the omgevingswaarde to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOmgevingswaarde(@PathVariable("id") Long id) {
        log.debug("REST request to delete Omgevingswaarde : {}", id);
        omgevingswaardeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
