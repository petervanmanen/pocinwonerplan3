package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjectclassificatie;
import nl.ritense.demo.repository.EobjectclassificatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjectclassificatie}.
 */
@RestController
@RequestMapping("/api/eobjectclassificaties")
@Transactional
public class EobjectclassificatieResource {

    private final Logger log = LoggerFactory.getLogger(EobjectclassificatieResource.class);

    private static final String ENTITY_NAME = "eobjectclassificatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjectclassificatieRepository eobjectclassificatieRepository;

    public EobjectclassificatieResource(EobjectclassificatieRepository eobjectclassificatieRepository) {
        this.eobjectclassificatieRepository = eobjectclassificatieRepository;
    }

    /**
     * {@code POST  /eobjectclassificaties} : Create a new eobjectclassificatie.
     *
     * @param eobjectclassificatie the eobjectclassificatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjectclassificatie, or with status {@code 400 (Bad Request)} if the eobjectclassificatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjectclassificatie> createEobjectclassificatie(@RequestBody Eobjectclassificatie eobjectclassificatie)
        throws URISyntaxException {
        log.debug("REST request to save Eobjectclassificatie : {}", eobjectclassificatie);
        if (eobjectclassificatie.getId() != null) {
            throw new BadRequestAlertException("A new eobjectclassificatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjectclassificatie = eobjectclassificatieRepository.save(eobjectclassificatie);
        return ResponseEntity.created(new URI("/api/eobjectclassificaties/" + eobjectclassificatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjectclassificatie.getId().toString()))
            .body(eobjectclassificatie);
    }

    /**
     * {@code PUT  /eobjectclassificaties/:id} : Updates an existing eobjectclassificatie.
     *
     * @param id the id of the eobjectclassificatie to save.
     * @param eobjectclassificatie the eobjectclassificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eobjectclassificatie,
     * or with status {@code 400 (Bad Request)} if the eobjectclassificatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eobjectclassificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Eobjectclassificatie> updateEobjectclassificatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Eobjectclassificatie eobjectclassificatie
    ) throws URISyntaxException {
        log.debug("REST request to update Eobjectclassificatie : {}, {}", id, eobjectclassificatie);
        if (eobjectclassificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eobjectclassificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eobjectclassificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eobjectclassificatie = eobjectclassificatieRepository.save(eobjectclassificatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eobjectclassificatie.getId().toString()))
            .body(eobjectclassificatie);
    }

    /**
     * {@code PATCH  /eobjectclassificaties/:id} : Partial updates given fields of an existing eobjectclassificatie, field will ignore if it is null
     *
     * @param id the id of the eobjectclassificatie to save.
     * @param eobjectclassificatie the eobjectclassificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eobjectclassificatie,
     * or with status {@code 400 (Bad Request)} if the eobjectclassificatie is not valid,
     * or with status {@code 404 (Not Found)} if the eobjectclassificatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the eobjectclassificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Eobjectclassificatie> partialUpdateEobjectclassificatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Eobjectclassificatie eobjectclassificatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eobjectclassificatie partially : {}, {}", id, eobjectclassificatie);
        if (eobjectclassificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eobjectclassificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eobjectclassificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Eobjectclassificatie> result = eobjectclassificatieRepository
            .findById(eobjectclassificatie.getId())
            .map(existingEobjectclassificatie -> {
                if (eobjectclassificatie.getNaam() != null) {
                    existingEobjectclassificatie.setNaam(eobjectclassificatie.getNaam());
                }
                if (eobjectclassificatie.getOmschrijving() != null) {
                    existingEobjectclassificatie.setOmschrijving(eobjectclassificatie.getOmschrijving());
                }

                return existingEobjectclassificatie;
            })
            .map(eobjectclassificatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eobjectclassificatie.getId().toString())
        );
    }

    /**
     * {@code GET  /eobjectclassificaties} : get all the eobjectclassificaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjectclassificaties in body.
     */
    @GetMapping("")
    public List<Eobjectclassificatie> getAllEobjectclassificaties() {
        log.debug("REST request to get all Eobjectclassificaties");
        return eobjectclassificatieRepository.findAll();
    }

    /**
     * {@code GET  /eobjectclassificaties/:id} : get the "id" eobjectclassificatie.
     *
     * @param id the id of the eobjectclassificatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjectclassificatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjectclassificatie> getEobjectclassificatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobjectclassificatie : {}", id);
        Optional<Eobjectclassificatie> eobjectclassificatie = eobjectclassificatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjectclassificatie);
    }

    /**
     * {@code DELETE  /eobjectclassificaties/:id} : delete the "id" eobjectclassificatie.
     *
     * @param id the id of the eobjectclassificatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjectclassificatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobjectclassificatie : {}", id);
        eobjectclassificatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
