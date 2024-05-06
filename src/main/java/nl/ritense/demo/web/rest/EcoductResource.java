package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ecoduct;
import nl.ritense.demo.repository.EcoductRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ecoduct}.
 */
@RestController
@RequestMapping("/api/ecoducts")
@Transactional
public class EcoductResource {

    private final Logger log = LoggerFactory.getLogger(EcoductResource.class);

    private static final String ENTITY_NAME = "ecoduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcoductRepository ecoductRepository;

    public EcoductResource(EcoductRepository ecoductRepository) {
        this.ecoductRepository = ecoductRepository;
    }

    /**
     * {@code POST  /ecoducts} : Create a new ecoduct.
     *
     * @param ecoduct the ecoduct to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecoduct, or with status {@code 400 (Bad Request)} if the ecoduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ecoduct> createEcoduct(@Valid @RequestBody Ecoduct ecoduct) throws URISyntaxException {
        log.debug("REST request to save Ecoduct : {}", ecoduct);
        if (ecoduct.getId() != null) {
            throw new BadRequestAlertException("A new ecoduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ecoduct = ecoductRepository.save(ecoduct);
        return ResponseEntity.created(new URI("/api/ecoducts/" + ecoduct.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ecoduct.getId().toString()))
            .body(ecoduct);
    }

    /**
     * {@code PUT  /ecoducts/:id} : Updates an existing ecoduct.
     *
     * @param id the id of the ecoduct to save.
     * @param ecoduct the ecoduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecoduct,
     * or with status {@code 400 (Bad Request)} if the ecoduct is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecoduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ecoduct> updateEcoduct(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ecoduct ecoduct
    ) throws URISyntaxException {
        log.debug("REST request to update Ecoduct : {}, {}", id, ecoduct);
        if (ecoduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecoduct.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecoductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ecoduct = ecoductRepository.save(ecoduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ecoduct.getId().toString()))
            .body(ecoduct);
    }

    /**
     * {@code PATCH  /ecoducts/:id} : Partial updates given fields of an existing ecoduct, field will ignore if it is null
     *
     * @param id the id of the ecoduct to save.
     * @param ecoduct the ecoduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecoduct,
     * or with status {@code 400 (Bad Request)} if the ecoduct is not valid,
     * or with status {@code 404 (Not Found)} if the ecoduct is not found,
     * or with status {@code 500 (Internal Server Error)} if the ecoduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ecoduct> partialUpdateEcoduct(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ecoduct ecoduct
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ecoduct partially : {}, {}", id, ecoduct);
        if (ecoduct.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecoduct.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ecoductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ecoduct> result = ecoductRepository
            .findById(ecoduct.getId())
            .map(existingEcoduct -> {
                if (ecoduct.getAantaloverspanningen() != null) {
                    existingEcoduct.setAantaloverspanningen(ecoduct.getAantaloverspanningen());
                }
                if (ecoduct.getDraagvermogen() != null) {
                    existingEcoduct.setDraagvermogen(ecoduct.getDraagvermogen());
                }
                if (ecoduct.getMaximaaltoelaatbaarvoertuiggewicht() != null) {
                    existingEcoduct.setMaximaaltoelaatbaarvoertuiggewicht(ecoduct.getMaximaaltoelaatbaarvoertuiggewicht());
                }
                if (ecoduct.getMaximaleasbelasting() != null) {
                    existingEcoduct.setMaximaleasbelasting(ecoduct.getMaximaleasbelasting());
                }
                if (ecoduct.getMaximaleoverspanning() != null) {
                    existingEcoduct.setMaximaleoverspanning(ecoduct.getMaximaleoverspanning());
                }
                if (ecoduct.getOverbruggingsobjectdoorrijopening() != null) {
                    existingEcoduct.setOverbruggingsobjectdoorrijopening(ecoduct.getOverbruggingsobjectdoorrijopening());
                }
                if (ecoduct.getType() != null) {
                    existingEcoduct.setType(ecoduct.getType());
                }
                if (ecoduct.getZwaarstevoertuig() != null) {
                    existingEcoduct.setZwaarstevoertuig(ecoduct.getZwaarstevoertuig());
                }

                return existingEcoduct;
            })
            .map(ecoductRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ecoduct.getId().toString())
        );
    }

    /**
     * {@code GET  /ecoducts} : get all the ecoducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecoducts in body.
     */
    @GetMapping("")
    public List<Ecoduct> getAllEcoducts() {
        log.debug("REST request to get all Ecoducts");
        return ecoductRepository.findAll();
    }

    /**
     * {@code GET  /ecoducts/:id} : get the "id" ecoduct.
     *
     * @param id the id of the ecoduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecoduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ecoduct> getEcoduct(@PathVariable("id") Long id) {
        log.debug("REST request to get Ecoduct : {}", id);
        Optional<Ecoduct> ecoduct = ecoductRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ecoduct);
    }

    /**
     * {@code DELETE  /ecoducts/:id} : delete the "id" ecoduct.
     *
     * @param id the id of the ecoduct to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEcoduct(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ecoduct : {}", id);
        ecoductRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
