package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Subsidiebeschikking;
import nl.ritense.demo.repository.SubsidiebeschikkingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Subsidiebeschikking}.
 */
@RestController
@RequestMapping("/api/subsidiebeschikkings")
@Transactional
public class SubsidiebeschikkingResource {

    private final Logger log = LoggerFactory.getLogger(SubsidiebeschikkingResource.class);

    private static final String ENTITY_NAME = "subsidiebeschikking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubsidiebeschikkingRepository subsidiebeschikkingRepository;

    public SubsidiebeschikkingResource(SubsidiebeschikkingRepository subsidiebeschikkingRepository) {
        this.subsidiebeschikkingRepository = subsidiebeschikkingRepository;
    }

    /**
     * {@code POST  /subsidiebeschikkings} : Create a new subsidiebeschikking.
     *
     * @param subsidiebeschikking the subsidiebeschikking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subsidiebeschikking, or with status {@code 400 (Bad Request)} if the subsidiebeschikking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Subsidiebeschikking> createSubsidiebeschikking(@RequestBody Subsidiebeschikking subsidiebeschikking)
        throws URISyntaxException {
        log.debug("REST request to save Subsidiebeschikking : {}", subsidiebeschikking);
        if (subsidiebeschikking.getId() != null) {
            throw new BadRequestAlertException("A new subsidiebeschikking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subsidiebeschikking = subsidiebeschikkingRepository.save(subsidiebeschikking);
        return ResponseEntity.created(new URI("/api/subsidiebeschikkings/" + subsidiebeschikking.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, subsidiebeschikking.getId().toString()))
            .body(subsidiebeschikking);
    }

    /**
     * {@code PUT  /subsidiebeschikkings/:id} : Updates an existing subsidiebeschikking.
     *
     * @param id the id of the subsidiebeschikking to save.
     * @param subsidiebeschikking the subsidiebeschikking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidiebeschikking,
     * or with status {@code 400 (Bad Request)} if the subsidiebeschikking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subsidiebeschikking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Subsidiebeschikking> updateSubsidiebeschikking(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Subsidiebeschikking subsidiebeschikking
    ) throws URISyntaxException {
        log.debug("REST request to update Subsidiebeschikking : {}, {}", id, subsidiebeschikking);
        if (subsidiebeschikking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidiebeschikking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidiebeschikkingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subsidiebeschikking = subsidiebeschikkingRepository.save(subsidiebeschikking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidiebeschikking.getId().toString()))
            .body(subsidiebeschikking);
    }

    /**
     * {@code PATCH  /subsidiebeschikkings/:id} : Partial updates given fields of an existing subsidiebeschikking, field will ignore if it is null
     *
     * @param id the id of the subsidiebeschikking to save.
     * @param subsidiebeschikking the subsidiebeschikking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidiebeschikking,
     * or with status {@code 400 (Bad Request)} if the subsidiebeschikking is not valid,
     * or with status {@code 404 (Not Found)} if the subsidiebeschikking is not found,
     * or with status {@code 500 (Internal Server Error)} if the subsidiebeschikking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Subsidiebeschikking> partialUpdateSubsidiebeschikking(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Subsidiebeschikking subsidiebeschikking
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subsidiebeschikking partially : {}, {}", id, subsidiebeschikking);
        if (subsidiebeschikking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidiebeschikking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidiebeschikkingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Subsidiebeschikking> result = subsidiebeschikkingRepository
            .findById(subsidiebeschikking.getId())
            .map(existingSubsidiebeschikking -> {
                if (subsidiebeschikking.getBeschikkingsnummer() != null) {
                    existingSubsidiebeschikking.setBeschikkingsnummer(subsidiebeschikking.getBeschikkingsnummer());
                }
                if (subsidiebeschikking.getBeschiktbedrag() != null) {
                    existingSubsidiebeschikking.setBeschiktbedrag(subsidiebeschikking.getBeschiktbedrag());
                }
                if (subsidiebeschikking.getBesluit() != null) {
                    existingSubsidiebeschikking.setBesluit(subsidiebeschikking.getBesluit());
                }
                if (subsidiebeschikking.getInternkenmerk() != null) {
                    existingSubsidiebeschikking.setInternkenmerk(subsidiebeschikking.getInternkenmerk());
                }
                if (subsidiebeschikking.getKenmerk() != null) {
                    existingSubsidiebeschikking.setKenmerk(subsidiebeschikking.getKenmerk());
                }
                if (subsidiebeschikking.getOntvangen() != null) {
                    existingSubsidiebeschikking.setOntvangen(subsidiebeschikking.getOntvangen());
                }
                if (subsidiebeschikking.getOpmerkingen() != null) {
                    existingSubsidiebeschikking.setOpmerkingen(subsidiebeschikking.getOpmerkingen());
                }

                return existingSubsidiebeschikking;
            })
            .map(subsidiebeschikkingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidiebeschikking.getId().toString())
        );
    }

    /**
     * {@code GET  /subsidiebeschikkings} : get all the subsidiebeschikkings.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subsidiebeschikkings in body.
     */
    @GetMapping("")
    public List<Subsidiebeschikking> getAllSubsidiebeschikkings(@RequestParam(name = "filter", required = false) String filter) {
        if ("mondtuitsubsidieaanvraag-is-null".equals(filter)) {
            log.debug("REST request to get all Subsidiebeschikkings where mondtuitSubsidieaanvraag is null");
            return StreamSupport.stream(subsidiebeschikkingRepository.findAll().spliterator(), false)
                .filter(subsidiebeschikking -> subsidiebeschikking.getMondtuitSubsidieaanvraag() == null)
                .toList();
        }
        log.debug("REST request to get all Subsidiebeschikkings");
        return subsidiebeschikkingRepository.findAll();
    }

    /**
     * {@code GET  /subsidiebeschikkings/:id} : get the "id" subsidiebeschikking.
     *
     * @param id the id of the subsidiebeschikking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subsidiebeschikking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subsidiebeschikking> getSubsidiebeschikking(@PathVariable("id") Long id) {
        log.debug("REST request to get Subsidiebeschikking : {}", id);
        Optional<Subsidiebeschikking> subsidiebeschikking = subsidiebeschikkingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subsidiebeschikking);
    }

    /**
     * {@code DELETE  /subsidiebeschikkings/:id} : delete the "id" subsidiebeschikking.
     *
     * @param id the id of the subsidiebeschikking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubsidiebeschikking(@PathVariable("id") Long id) {
        log.debug("REST request to delete Subsidiebeschikking : {}", id);
        subsidiebeschikkingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
