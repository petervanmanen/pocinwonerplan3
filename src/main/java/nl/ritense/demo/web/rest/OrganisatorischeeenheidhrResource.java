package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Organisatorischeeenheidhr;
import nl.ritense.demo.repository.OrganisatorischeeenheidhrRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Organisatorischeeenheidhr}.
 */
@RestController
@RequestMapping("/api/organisatorischeeenheidhrs")
@Transactional
public class OrganisatorischeeenheidhrResource {

    private final Logger log = LoggerFactory.getLogger(OrganisatorischeeenheidhrResource.class);

    private static final String ENTITY_NAME = "organisatorischeeenheidhr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganisatorischeeenheidhrRepository organisatorischeeenheidhrRepository;

    public OrganisatorischeeenheidhrResource(OrganisatorischeeenheidhrRepository organisatorischeeenheidhrRepository) {
        this.organisatorischeeenheidhrRepository = organisatorischeeenheidhrRepository;
    }

    /**
     * {@code POST  /organisatorischeeenheidhrs} : Create a new organisatorischeeenheidhr.
     *
     * @param organisatorischeeenheidhr the organisatorischeeenheidhr to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organisatorischeeenheidhr, or with status {@code 400 (Bad Request)} if the organisatorischeeenheidhr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Organisatorischeeenheidhr> createOrganisatorischeeenheidhr(
        @RequestBody Organisatorischeeenheidhr organisatorischeeenheidhr
    ) throws URISyntaxException {
        log.debug("REST request to save Organisatorischeeenheidhr : {}", organisatorischeeenheidhr);
        if (organisatorischeeenheidhr.getId() != null) {
            throw new BadRequestAlertException("A new organisatorischeeenheidhr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organisatorischeeenheidhr = organisatorischeeenheidhrRepository.save(organisatorischeeenheidhr);
        return ResponseEntity.created(new URI("/api/organisatorischeeenheidhrs/" + organisatorischeeenheidhr.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, organisatorischeeenheidhr.getId().toString())
            )
            .body(organisatorischeeenheidhr);
    }

    /**
     * {@code PUT  /organisatorischeeenheidhrs/:id} : Updates an existing organisatorischeeenheidhr.
     *
     * @param id the id of the organisatorischeeenheidhr to save.
     * @param organisatorischeeenheidhr the organisatorischeeenheidhr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisatorischeeenheidhr,
     * or with status {@code 400 (Bad Request)} if the organisatorischeeenheidhr is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organisatorischeeenheidhr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Organisatorischeeenheidhr> updateOrganisatorischeeenheidhr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Organisatorischeeenheidhr organisatorischeeenheidhr
    ) throws URISyntaxException {
        log.debug("REST request to update Organisatorischeeenheidhr : {}, {}", id, organisatorischeeenheidhr);
        if (organisatorischeeenheidhr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organisatorischeeenheidhr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organisatorischeeenheidhrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        organisatorischeeenheidhr = organisatorischeeenheidhrRepository.save(organisatorischeeenheidhr);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organisatorischeeenheidhr.getId().toString()))
            .body(organisatorischeeenheidhr);
    }

    /**
     * {@code PATCH  /organisatorischeeenheidhrs/:id} : Partial updates given fields of an existing organisatorischeeenheidhr, field will ignore if it is null
     *
     * @param id the id of the organisatorischeeenheidhr to save.
     * @param organisatorischeeenheidhr the organisatorischeeenheidhr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisatorischeeenheidhr,
     * or with status {@code 400 (Bad Request)} if the organisatorischeeenheidhr is not valid,
     * or with status {@code 404 (Not Found)} if the organisatorischeeenheidhr is not found,
     * or with status {@code 500 (Internal Server Error)} if the organisatorischeeenheidhr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Organisatorischeeenheidhr> partialUpdateOrganisatorischeeenheidhr(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Organisatorischeeenheidhr organisatorischeeenheidhr
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organisatorischeeenheidhr partially : {}, {}", id, organisatorischeeenheidhr);
        if (organisatorischeeenheidhr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organisatorischeeenheidhr.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organisatorischeeenheidhrRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Organisatorischeeenheidhr> result = organisatorischeeenheidhrRepository
            .findById(organisatorischeeenheidhr.getId())
            .map(existingOrganisatorischeeenheidhr -> {
                if (organisatorischeeenheidhr.getNaam() != null) {
                    existingOrganisatorischeeenheidhr.setNaam(organisatorischeeenheidhr.getNaam());
                }
                if (organisatorischeeenheidhr.getType() != null) {
                    existingOrganisatorischeeenheidhr.setType(organisatorischeeenheidhr.getType());
                }

                return existingOrganisatorischeeenheidhr;
            })
            .map(organisatorischeeenheidhrRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organisatorischeeenheidhr.getId().toString())
        );
    }

    /**
     * {@code GET  /organisatorischeeenheidhrs} : get all the organisatorischeeenheidhrs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisatorischeeenheidhrs in body.
     */
    @GetMapping("")
    public List<Organisatorischeeenheidhr> getAllOrganisatorischeeenheidhrs() {
        log.debug("REST request to get all Organisatorischeeenheidhrs");
        return organisatorischeeenheidhrRepository.findAll();
    }

    /**
     * {@code GET  /organisatorischeeenheidhrs/:id} : get the "id" organisatorischeeenheidhr.
     *
     * @param id the id of the organisatorischeeenheidhr to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisatorischeeenheidhr, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Organisatorischeeenheidhr> getOrganisatorischeeenheidhr(@PathVariable("id") Long id) {
        log.debug("REST request to get Organisatorischeeenheidhr : {}", id);
        Optional<Organisatorischeeenheidhr> organisatorischeeenheidhr = organisatorischeeenheidhrRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organisatorischeeenheidhr);
    }

    /**
     * {@code DELETE  /organisatorischeeenheidhrs/:id} : delete the "id" organisatorischeeenheidhr.
     *
     * @param id the id of the organisatorischeeenheidhr to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganisatorischeeenheidhr(@PathVariable("id") Long id) {
        log.debug("REST request to delete Organisatorischeeenheidhr : {}", id);
        organisatorischeeenheidhrRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
