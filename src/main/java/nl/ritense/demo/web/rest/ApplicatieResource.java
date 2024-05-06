package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Applicatie;
import nl.ritense.demo.repository.ApplicatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Applicatie}.
 */
@RestController
@RequestMapping("/api/applicaties")
@Transactional
public class ApplicatieResource {

    private final Logger log = LoggerFactory.getLogger(ApplicatieResource.class);

    private static final String ENTITY_NAME = "applicatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicatieRepository applicatieRepository;

    public ApplicatieResource(ApplicatieRepository applicatieRepository) {
        this.applicatieRepository = applicatieRepository;
    }

    /**
     * {@code POST  /applicaties} : Create a new applicatie.
     *
     * @param applicatie the applicatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicatie, or with status {@code 400 (Bad Request)} if the applicatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Applicatie> createApplicatie(@Valid @RequestBody Applicatie applicatie) throws URISyntaxException {
        log.debug("REST request to save Applicatie : {}", applicatie);
        if (applicatie.getId() != null) {
            throw new BadRequestAlertException("A new applicatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        applicatie = applicatieRepository.save(applicatie);
        return ResponseEntity.created(new URI("/api/applicaties/" + applicatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, applicatie.getId().toString()))
            .body(applicatie);
    }

    /**
     * {@code PUT  /applicaties/:id} : Updates an existing applicatie.
     *
     * @param id the id of the applicatie to save.
     * @param applicatie the applicatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicatie,
     * or with status {@code 400 (Bad Request)} if the applicatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Applicatie> updateApplicatie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Applicatie applicatie
    ) throws URISyntaxException {
        log.debug("REST request to update Applicatie : {}, {}", id, applicatie);
        if (applicatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        applicatie = applicatieRepository.save(applicatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicatie.getId().toString()))
            .body(applicatie);
    }

    /**
     * {@code PATCH  /applicaties/:id} : Partial updates given fields of an existing applicatie, field will ignore if it is null
     *
     * @param id the id of the applicatie to save.
     * @param applicatie the applicatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicatie,
     * or with status {@code 400 (Bad Request)} if the applicatie is not valid,
     * or with status {@code 404 (Not Found)} if the applicatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Applicatie> partialUpdateApplicatie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Applicatie applicatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Applicatie partially : {}, {}", id, applicatie);
        if (applicatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, applicatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!applicatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Applicatie> result = applicatieRepository
            .findById(applicatie.getId())
            .map(existingApplicatie -> {
                if (applicatie.getApplicatieurl() != null) {
                    existingApplicatie.setApplicatieurl(applicatie.getApplicatieurl());
                }
                if (applicatie.getBeheerstatus() != null) {
                    existingApplicatie.setBeheerstatus(applicatie.getBeheerstatus());
                }
                if (applicatie.getBeleidsdomein() != null) {
                    existingApplicatie.setBeleidsdomein(applicatie.getBeleidsdomein());
                }
                if (applicatie.getCategorie() != null) {
                    existingApplicatie.setCategorie(applicatie.getCategorie());
                }
                if (applicatie.getGuid() != null) {
                    existingApplicatie.setGuid(applicatie.getGuid());
                }
                if (applicatie.getNaam() != null) {
                    existingApplicatie.setNaam(applicatie.getNaam());
                }
                if (applicatie.getOmschrijving() != null) {
                    existingApplicatie.setOmschrijving(applicatie.getOmschrijving());
                }
                if (applicatie.getPackagingstatus() != null) {
                    existingApplicatie.setPackagingstatus(applicatie.getPackagingstatus());
                }

                return existingApplicatie;
            })
            .map(applicatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicatie.getId().toString())
        );
    }

    /**
     * {@code GET  /applicaties} : get all the applicaties.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicaties in body.
     */
    @GetMapping("")
    public List<Applicatie> getAllApplicaties(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Applicaties");
        if (eagerload) {
            return applicatieRepository.findAllWithEagerRelationships();
        } else {
            return applicatieRepository.findAll();
        }
    }

    /**
     * {@code GET  /applicaties/:id} : get the "id" applicatie.
     *
     * @param id the id of the applicatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Applicatie> getApplicatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Applicatie : {}", id);
        Optional<Applicatie> applicatie = applicatieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(applicatie);
    }

    /**
     * {@code DELETE  /applicaties/:id} : delete the "id" applicatie.
     *
     * @param id the id of the applicatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Applicatie : {}", id);
        applicatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
