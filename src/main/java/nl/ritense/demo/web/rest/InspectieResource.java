package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Inspectie;
import nl.ritense.demo.repository.InspectieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inspectie}.
 */
@RestController
@RequestMapping("/api/inspecties")
@Transactional
public class InspectieResource {

    private final Logger log = LoggerFactory.getLogger(InspectieResource.class);

    private static final String ENTITY_NAME = "inspectie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectieRepository inspectieRepository;

    public InspectieResource(InspectieRepository inspectieRepository) {
        this.inspectieRepository = inspectieRepository;
    }

    /**
     * {@code POST  /inspecties} : Create a new inspectie.
     *
     * @param inspectie the inspectie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectie, or with status {@code 400 (Bad Request)} if the inspectie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inspectie> createInspectie(@Valid @RequestBody Inspectie inspectie) throws URISyntaxException {
        log.debug("REST request to save Inspectie : {}", inspectie);
        if (inspectie.getId() != null) {
            throw new BadRequestAlertException("A new inspectie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inspectie = inspectieRepository.save(inspectie);
        return ResponseEntity.created(new URI("/api/inspecties/" + inspectie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inspectie.getId().toString()))
            .body(inspectie);
    }

    /**
     * {@code PUT  /inspecties/:id} : Updates an existing inspectie.
     *
     * @param id the id of the inspectie to save.
     * @param inspectie the inspectie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectie,
     * or with status {@code 400 (Bad Request)} if the inspectie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inspectie> updateInspectie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Inspectie inspectie
    ) throws URISyntaxException {
        log.debug("REST request to update Inspectie : {}, {}", id, inspectie);
        if (inspectie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inspectie = inspectieRepository.save(inspectie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inspectie.getId().toString()))
            .body(inspectie);
    }

    /**
     * {@code PATCH  /inspecties/:id} : Partial updates given fields of an existing inspectie, field will ignore if it is null
     *
     * @param id the id of the inspectie to save.
     * @param inspectie the inspectie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectie,
     * or with status {@code 400 (Bad Request)} if the inspectie is not valid,
     * or with status {@code 404 (Not Found)} if the inspectie is not found,
     * or with status {@code 500 (Internal Server Error)} if the inspectie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inspectie> partialUpdateInspectie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Inspectie inspectie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inspectie partially : {}, {}", id, inspectie);
        if (inspectie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspectie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspectieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inspectie> result = inspectieRepository
            .findById(inspectie.getId())
            .map(existingInspectie -> {
                if (inspectie.getAangemaaktdoor() != null) {
                    existingInspectie.setAangemaaktdoor(inspectie.getAangemaaktdoor());
                }
                if (inspectie.getDatumaanmaak() != null) {
                    existingInspectie.setDatumaanmaak(inspectie.getDatumaanmaak());
                }
                if (inspectie.getDatumgepland() != null) {
                    existingInspectie.setDatumgepland(inspectie.getDatumgepland());
                }
                if (inspectie.getDatuminspectie() != null) {
                    existingInspectie.setDatuminspectie(inspectie.getDatuminspectie());
                }
                if (inspectie.getDatummutatie() != null) {
                    existingInspectie.setDatummutatie(inspectie.getDatummutatie());
                }
                if (inspectie.getGemuteerddoor() != null) {
                    existingInspectie.setGemuteerddoor(inspectie.getGemuteerddoor());
                }
                if (inspectie.getInspectietype() != null) {
                    existingInspectie.setInspectietype(inspectie.getInspectietype());
                }
                if (inspectie.getKenmerk() != null) {
                    existingInspectie.setKenmerk(inspectie.getKenmerk());
                }
                if (inspectie.getOmschrijving() != null) {
                    existingInspectie.setOmschrijving(inspectie.getOmschrijving());
                }
                if (inspectie.getOpmerkingen() != null) {
                    existingInspectie.setOpmerkingen(inspectie.getOpmerkingen());
                }
                if (inspectie.getStatus() != null) {
                    existingInspectie.setStatus(inspectie.getStatus());
                }

                return existingInspectie;
            })
            .map(inspectieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inspectie.getId().toString())
        );
    }

    /**
     * {@code GET  /inspecties} : get all the inspecties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspecties in body.
     */
    @GetMapping("")
    public List<Inspectie> getAllInspecties() {
        log.debug("REST request to get all Inspecties");
        return inspectieRepository.findAll();
    }

    /**
     * {@code GET  /inspecties/:id} : get the "id" inspectie.
     *
     * @param id the id of the inspectie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inspectie> getInspectie(@PathVariable("id") Long id) {
        log.debug("REST request to get Inspectie : {}", id);
        Optional<Inspectie> inspectie = inspectieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inspectie);
    }

    /**
     * {@code DELETE  /inspecties/:id} : delete the "id" inspectie.
     *
     * @param id the id of the inspectie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inspectie : {}", id);
        inspectieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
