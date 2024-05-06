package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Organisatorischeeenheid;
import nl.ritense.demo.repository.OrganisatorischeeenheidRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Organisatorischeeenheid}.
 */
@RestController
@RequestMapping("/api/organisatorischeeenheids")
@Transactional
public class OrganisatorischeeenheidResource {

    private final Logger log = LoggerFactory.getLogger(OrganisatorischeeenheidResource.class);

    private static final String ENTITY_NAME = "organisatorischeeenheid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganisatorischeeenheidRepository organisatorischeeenheidRepository;

    public OrganisatorischeeenheidResource(OrganisatorischeeenheidRepository organisatorischeeenheidRepository) {
        this.organisatorischeeenheidRepository = organisatorischeeenheidRepository;
    }

    /**
     * {@code POST  /organisatorischeeenheids} : Create a new organisatorischeeenheid.
     *
     * @param organisatorischeeenheid the organisatorischeeenheid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organisatorischeeenheid, or with status {@code 400 (Bad Request)} if the organisatorischeeenheid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Organisatorischeeenheid> createOrganisatorischeeenheid(
        @Valid @RequestBody Organisatorischeeenheid organisatorischeeenheid
    ) throws URISyntaxException {
        log.debug("REST request to save Organisatorischeeenheid : {}", organisatorischeeenheid);
        if (organisatorischeeenheid.getId() != null) {
            throw new BadRequestAlertException("A new organisatorischeeenheid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organisatorischeeenheid = organisatorischeeenheidRepository.save(organisatorischeeenheid);
        return ResponseEntity.created(new URI("/api/organisatorischeeenheids/" + organisatorischeeenheid.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, organisatorischeeenheid.getId().toString()))
            .body(organisatorischeeenheid);
    }

    /**
     * {@code PUT  /organisatorischeeenheids/:id} : Updates an existing organisatorischeeenheid.
     *
     * @param id the id of the organisatorischeeenheid to save.
     * @param organisatorischeeenheid the organisatorischeeenheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisatorischeeenheid,
     * or with status {@code 400 (Bad Request)} if the organisatorischeeenheid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organisatorischeeenheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Organisatorischeeenheid> updateOrganisatorischeeenheid(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Organisatorischeeenheid organisatorischeeenheid
    ) throws URISyntaxException {
        log.debug("REST request to update Organisatorischeeenheid : {}, {}", id, organisatorischeeenheid);
        if (organisatorischeeenheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organisatorischeeenheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organisatorischeeenheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        organisatorischeeenheid = organisatorischeeenheidRepository.save(organisatorischeeenheid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organisatorischeeenheid.getId().toString()))
            .body(organisatorischeeenheid);
    }

    /**
     * {@code PATCH  /organisatorischeeenheids/:id} : Partial updates given fields of an existing organisatorischeeenheid, field will ignore if it is null
     *
     * @param id the id of the organisatorischeeenheid to save.
     * @param organisatorischeeenheid the organisatorischeeenheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisatorischeeenheid,
     * or with status {@code 400 (Bad Request)} if the organisatorischeeenheid is not valid,
     * or with status {@code 404 (Not Found)} if the organisatorischeeenheid is not found,
     * or with status {@code 500 (Internal Server Error)} if the organisatorischeeenheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Organisatorischeeenheid> partialUpdateOrganisatorischeeenheid(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Organisatorischeeenheid organisatorischeeenheid
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organisatorischeeenheid partially : {}, {}", id, organisatorischeeenheid);
        if (organisatorischeeenheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organisatorischeeenheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organisatorischeeenheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Organisatorischeeenheid> result = organisatorischeeenheidRepository
            .findById(organisatorischeeenheid.getId())
            .map(existingOrganisatorischeeenheid -> {
                if (organisatorischeeenheid.getDatumontstaan() != null) {
                    existingOrganisatorischeeenheid.setDatumontstaan(organisatorischeeenheid.getDatumontstaan());
                }
                if (organisatorischeeenheid.getDatumopheffing() != null) {
                    existingOrganisatorischeeenheid.setDatumopheffing(organisatorischeeenheid.getDatumopheffing());
                }
                if (organisatorischeeenheid.getEmailadres() != null) {
                    existingOrganisatorischeeenheid.setEmailadres(organisatorischeeenheid.getEmailadres());
                }
                if (organisatorischeeenheid.getFaxnummer() != null) {
                    existingOrganisatorischeeenheid.setFaxnummer(organisatorischeeenheid.getFaxnummer());
                }
                if (organisatorischeeenheid.getFormatie() != null) {
                    existingOrganisatorischeeenheid.setFormatie(organisatorischeeenheid.getFormatie());
                }
                if (organisatorischeeenheid.getNaam() != null) {
                    existingOrganisatorischeeenheid.setNaam(organisatorischeeenheid.getNaam());
                }
                if (organisatorischeeenheid.getNaamverkort() != null) {
                    existingOrganisatorischeeenheid.setNaamverkort(organisatorischeeenheid.getNaamverkort());
                }
                if (organisatorischeeenheid.getOmschrijving() != null) {
                    existingOrganisatorischeeenheid.setOmschrijving(organisatorischeeenheid.getOmschrijving());
                }
                if (organisatorischeeenheid.getOrganisatieidentificatie() != null) {
                    existingOrganisatorischeeenheid.setOrganisatieidentificatie(organisatorischeeenheid.getOrganisatieidentificatie());
                }
                if (organisatorischeeenheid.getTelefoonnummer() != null) {
                    existingOrganisatorischeeenheid.setTelefoonnummer(organisatorischeeenheid.getTelefoonnummer());
                }
                if (organisatorischeeenheid.getToelichting() != null) {
                    existingOrganisatorischeeenheid.setToelichting(organisatorischeeenheid.getToelichting());
                }

                return existingOrganisatorischeeenheid;
            })
            .map(organisatorischeeenheidRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organisatorischeeenheid.getId().toString())
        );
    }

    /**
     * {@code GET  /organisatorischeeenheids} : get all the organisatorischeeenheids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisatorischeeenheids in body.
     */
    @GetMapping("")
    public List<Organisatorischeeenheid> getAllOrganisatorischeeenheids() {
        log.debug("REST request to get all Organisatorischeeenheids");
        return organisatorischeeenheidRepository.findAll();
    }

    /**
     * {@code GET  /organisatorischeeenheids/:id} : get the "id" organisatorischeeenheid.
     *
     * @param id the id of the organisatorischeeenheid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisatorischeeenheid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Organisatorischeeenheid> getOrganisatorischeeenheid(@PathVariable("id") Long id) {
        log.debug("REST request to get Organisatorischeeenheid : {}", id);
        Optional<Organisatorischeeenheid> organisatorischeeenheid = organisatorischeeenheidRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organisatorischeeenheid);
    }

    /**
     * {@code DELETE  /organisatorischeeenheids/:id} : delete the "id" organisatorischeeenheid.
     *
     * @param id the id of the organisatorischeeenheid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganisatorischeeenheid(@PathVariable("id") Long id) {
        log.debug("REST request to delete Organisatorischeeenheid : {}", id);
        organisatorischeeenheidRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
