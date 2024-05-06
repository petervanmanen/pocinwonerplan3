package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjecttype;
import nl.ritense.demo.repository.EobjecttypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjecttype}.
 */
@RestController
@RequestMapping("/api/eobjecttypes")
@Transactional
public class EobjecttypeResource {

    private final Logger log = LoggerFactory.getLogger(EobjecttypeResource.class);

    private static final String ENTITY_NAME = "eobjecttype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjecttypeRepository eobjecttypeRepository;

    public EobjecttypeResource(EobjecttypeRepository eobjecttypeRepository) {
        this.eobjecttypeRepository = eobjecttypeRepository;
    }

    /**
     * {@code POST  /eobjecttypes} : Create a new eobjecttype.
     *
     * @param eobjecttype the eobjecttype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjecttype, or with status {@code 400 (Bad Request)} if the eobjecttype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjecttype> createEobjecttype(@RequestBody Eobjecttype eobjecttype) throws URISyntaxException {
        log.debug("REST request to save Eobjecttype : {}", eobjecttype);
        if (eobjecttype.getId() != null) {
            throw new BadRequestAlertException("A new eobjecttype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjecttype = eobjecttypeRepository.save(eobjecttype);
        return ResponseEntity.created(new URI("/api/eobjecttypes/" + eobjecttype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjecttype.getId()))
            .body(eobjecttype);
    }

    /**
     * {@code PUT  /eobjecttypes/:id} : Updates an existing eobjecttype.
     *
     * @param id the id of the eobjecttype to save.
     * @param eobjecttype the eobjecttype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eobjecttype,
     * or with status {@code 400 (Bad Request)} if the eobjecttype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eobjecttype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Eobjecttype> updateEobjecttype(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Eobjecttype eobjecttype
    ) throws URISyntaxException {
        log.debug("REST request to update Eobjecttype : {}, {}", id, eobjecttype);
        if (eobjecttype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eobjecttype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eobjecttypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eobjecttype = eobjecttypeRepository.save(eobjecttype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eobjecttype.getId()))
            .body(eobjecttype);
    }

    /**
     * {@code PATCH  /eobjecttypes/:id} : Partial updates given fields of an existing eobjecttype, field will ignore if it is null
     *
     * @param id the id of the eobjecttype to save.
     * @param eobjecttype the eobjecttype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eobjecttype,
     * or with status {@code 400 (Bad Request)} if the eobjecttype is not valid,
     * or with status {@code 404 (Not Found)} if the eobjecttype is not found,
     * or with status {@code 500 (Internal Server Error)} if the eobjecttype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Eobjecttype> partialUpdateEobjecttype(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Eobjecttype eobjecttype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eobjecttype partially : {}, {}", id, eobjecttype);
        if (eobjecttype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eobjecttype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eobjecttypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Eobjecttype> result = eobjecttypeRepository
            .findById(eobjecttype.getId())
            .map(existingEobjecttype -> {
                if (eobjecttype.getDatumopname() != null) {
                    existingEobjecttype.setDatumopname(eobjecttype.getDatumopname());
                }
                if (eobjecttype.getDefinitie() != null) {
                    existingEobjecttype.setDefinitie(eobjecttype.getDefinitie());
                }
                if (eobjecttype.getEaguid() != null) {
                    existingEobjecttype.setEaguid(eobjecttype.getEaguid());
                }
                if (eobjecttype.getHerkomst() != null) {
                    existingEobjecttype.setHerkomst(eobjecttype.getHerkomst());
                }
                if (eobjecttype.getHerkomstdefinitie() != null) {
                    existingEobjecttype.setHerkomstdefinitie(eobjecttype.getHerkomstdefinitie());
                }
                if (eobjecttype.getIndicatieabstract() != null) {
                    existingEobjecttype.setIndicatieabstract(eobjecttype.getIndicatieabstract());
                }
                if (eobjecttype.getKwaliteit() != null) {
                    existingEobjecttype.setKwaliteit(eobjecttype.getKwaliteit());
                }
                if (eobjecttype.getNaam() != null) {
                    existingEobjecttype.setNaam(eobjecttype.getNaam());
                }
                if (eobjecttype.getPopulatie() != null) {
                    existingEobjecttype.setPopulatie(eobjecttype.getPopulatie());
                }
                if (eobjecttype.getStereotype() != null) {
                    existingEobjecttype.setStereotype(eobjecttype.getStereotype());
                }
                if (eobjecttype.getToelichting() != null) {
                    existingEobjecttype.setToelichting(eobjecttype.getToelichting());
                }
                if (eobjecttype.getUniekeaanduiding() != null) {
                    existingEobjecttype.setUniekeaanduiding(eobjecttype.getUniekeaanduiding());
                }

                return existingEobjecttype;
            })
            .map(eobjecttypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eobjecttype.getId())
        );
    }

    /**
     * {@code GET  /eobjecttypes} : get all the eobjecttypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjecttypes in body.
     */
    @GetMapping("")
    public List<Eobjecttype> getAllEobjecttypes() {
        log.debug("REST request to get all Eobjecttypes");
        return eobjecttypeRepository.findAll();
    }

    /**
     * {@code GET  /eobjecttypes/:id} : get the "id" eobjecttype.
     *
     * @param id the id of the eobjecttype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjecttype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjecttype> getEobjecttype(@PathVariable("id") String id) {
        log.debug("REST request to get Eobjecttype : {}", id);
        Optional<Eobjecttype> eobjecttype = eobjecttypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjecttype);
    }

    /**
     * {@code DELETE  /eobjecttypes/:id} : delete the "id" eobjecttype.
     *
     * @param id the id of the eobjecttype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjecttype(@PathVariable("id") String id) {
        log.debug("REST request to delete Eobjecttype : {}", id);
        eobjecttypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
