package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bedrijfsproces;
import nl.ritense.demo.repository.BedrijfsprocesRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bedrijfsproces}.
 */
@RestController
@RequestMapping("/api/bedrijfsproces")
@Transactional
public class BedrijfsprocesResource {

    private final Logger log = LoggerFactory.getLogger(BedrijfsprocesResource.class);

    private static final String ENTITY_NAME = "bedrijfsproces";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BedrijfsprocesRepository bedrijfsprocesRepository;

    public BedrijfsprocesResource(BedrijfsprocesRepository bedrijfsprocesRepository) {
        this.bedrijfsprocesRepository = bedrijfsprocesRepository;
    }

    /**
     * {@code POST  /bedrijfsproces} : Create a new bedrijfsproces.
     *
     * @param bedrijfsproces the bedrijfsproces to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bedrijfsproces, or with status {@code 400 (Bad Request)} if the bedrijfsproces has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bedrijfsproces> createBedrijfsproces(@Valid @RequestBody Bedrijfsproces bedrijfsproces)
        throws URISyntaxException {
        log.debug("REST request to save Bedrijfsproces : {}", bedrijfsproces);
        if (bedrijfsproces.getId() != null) {
            throw new BadRequestAlertException("A new bedrijfsproces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bedrijfsproces = bedrijfsprocesRepository.save(bedrijfsproces);
        return ResponseEntity.created(new URI("/api/bedrijfsproces/" + bedrijfsproces.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bedrijfsproces.getId().toString()))
            .body(bedrijfsproces);
    }

    /**
     * {@code PUT  /bedrijfsproces/:id} : Updates an existing bedrijfsproces.
     *
     * @param id the id of the bedrijfsproces to save.
     * @param bedrijfsproces the bedrijfsproces to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedrijfsproces,
     * or with status {@code 400 (Bad Request)} if the bedrijfsproces is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bedrijfsproces couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bedrijfsproces> updateBedrijfsproces(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bedrijfsproces bedrijfsproces
    ) throws URISyntaxException {
        log.debug("REST request to update Bedrijfsproces : {}, {}", id, bedrijfsproces);
        if (bedrijfsproces.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedrijfsproces.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedrijfsprocesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bedrijfsproces = bedrijfsprocesRepository.save(bedrijfsproces);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bedrijfsproces.getId().toString()))
            .body(bedrijfsproces);
    }

    /**
     * {@code PATCH  /bedrijfsproces/:id} : Partial updates given fields of an existing bedrijfsproces, field will ignore if it is null
     *
     * @param id the id of the bedrijfsproces to save.
     * @param bedrijfsproces the bedrijfsproces to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedrijfsproces,
     * or with status {@code 400 (Bad Request)} if the bedrijfsproces is not valid,
     * or with status {@code 404 (Not Found)} if the bedrijfsproces is not found,
     * or with status {@code 500 (Internal Server Error)} if the bedrijfsproces couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bedrijfsproces> partialUpdateBedrijfsproces(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bedrijfsproces bedrijfsproces
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bedrijfsproces partially : {}, {}", id, bedrijfsproces);
        if (bedrijfsproces.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedrijfsproces.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedrijfsprocesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bedrijfsproces> result = bedrijfsprocesRepository
            .findById(bedrijfsproces.getId())
            .map(existingBedrijfsproces -> {
                if (bedrijfsproces.getAfgerond() != null) {
                    existingBedrijfsproces.setAfgerond(bedrijfsproces.getAfgerond());
                }
                if (bedrijfsproces.getDatumeind() != null) {
                    existingBedrijfsproces.setDatumeind(bedrijfsproces.getDatumeind());
                }
                if (bedrijfsproces.getDatumstart() != null) {
                    existingBedrijfsproces.setDatumstart(bedrijfsproces.getDatumstart());
                }
                if (bedrijfsproces.getNaam() != null) {
                    existingBedrijfsproces.setNaam(bedrijfsproces.getNaam());
                }
                if (bedrijfsproces.getOmschrijving() != null) {
                    existingBedrijfsproces.setOmschrijving(bedrijfsproces.getOmschrijving());
                }

                return existingBedrijfsproces;
            })
            .map(bedrijfsprocesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bedrijfsproces.getId().toString())
        );
    }

    /**
     * {@code GET  /bedrijfsproces} : get all the bedrijfsproces.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bedrijfsproces in body.
     */
    @GetMapping("")
    public List<Bedrijfsproces> getAllBedrijfsproces(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Bedrijfsproces");
        if (eagerload) {
            return bedrijfsprocesRepository.findAllWithEagerRelationships();
        } else {
            return bedrijfsprocesRepository.findAll();
        }
    }

    /**
     * {@code GET  /bedrijfsproces/:id} : get the "id" bedrijfsproces.
     *
     * @param id the id of the bedrijfsproces to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bedrijfsproces, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bedrijfsproces> getBedrijfsproces(@PathVariable("id") Long id) {
        log.debug("REST request to get Bedrijfsproces : {}", id);
        Optional<Bedrijfsproces> bedrijfsproces = bedrijfsprocesRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(bedrijfsproces);
    }

    /**
     * {@code DELETE  /bedrijfsproces/:id} : delete the "id" bedrijfsproces.
     *
     * @param id the id of the bedrijfsproces to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBedrijfsproces(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bedrijfsproces : {}", id);
        bedrijfsprocesRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
