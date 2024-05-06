package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Eobject;
import nl.ritense.demo.repository.EobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobject}.
 */
@RestController
@RequestMapping("/api/eobjects")
@Transactional
public class EobjectResource {

    private final Logger log = LoggerFactory.getLogger(EobjectResource.class);

    private static final String ENTITY_NAME = "eobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjectRepository eobjectRepository;

    public EobjectResource(EobjectRepository eobjectRepository) {
        this.eobjectRepository = eobjectRepository;
    }

    /**
     * {@code POST  /eobjects} : Create a new eobject.
     *
     * @param eobject the eobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobject, or with status {@code 400 (Bad Request)} if the eobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobject> createEobject(@RequestBody Eobject eobject) throws URISyntaxException {
        log.debug("REST request to save Eobject : {}", eobject);
        if (eobject.getId() != null) {
            throw new BadRequestAlertException("A new eobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobject = eobjectRepository.save(eobject);
        return ResponseEntity.created(new URI("/api/eobjects/" + eobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobject.getId().toString()))
            .body(eobject);
    }

    /**
     * {@code PUT  /eobjects/:id} : Updates an existing eobject.
     *
     * @param id the id of the eobject to save.
     * @param eobject the eobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eobject,
     * or with status {@code 400 (Bad Request)} if the eobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Eobject> updateEobject(@PathVariable(value = "id", required = false) final Long id, @RequestBody Eobject eobject)
        throws URISyntaxException {
        log.debug("REST request to update Eobject : {}, {}", id, eobject);
        if (eobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eobject = eobjectRepository.save(eobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eobject.getId().toString()))
            .body(eobject);
    }

    /**
     * {@code PATCH  /eobjects/:id} : Partial updates given fields of an existing eobject, field will ignore if it is null
     *
     * @param id the id of the eobject to save.
     * @param eobject the eobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eobject,
     * or with status {@code 400 (Bad Request)} if the eobject is not valid,
     * or with status {@code 404 (Not Found)} if the eobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the eobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Eobject> partialUpdateEobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Eobject eobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eobject partially : {}, {}", id, eobject);
        if (eobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Eobject> result = eobjectRepository
            .findById(eobject.getId())
            .map(existingEobject -> {
                if (eobject.getAdresbinnenland() != null) {
                    existingEobject.setAdresbinnenland(eobject.getAdresbinnenland());
                }
                if (eobject.getAdresbuitenland() != null) {
                    existingEobject.setAdresbuitenland(eobject.getAdresbuitenland());
                }
                if (eobject.getDomein() != null) {
                    existingEobject.setDomein(eobject.getDomein());
                }
                if (eobject.getGeometrie() != null) {
                    existingEobject.setGeometrie(eobject.getGeometrie());
                }
                if (eobject.getIdentificatie() != null) {
                    existingEobject.setIdentificatie(eobject.getIdentificatie());
                }
                if (eobject.getIndicatierisico() != null) {
                    existingEobject.setIndicatierisico(eobject.getIndicatierisico());
                }
                if (eobject.getKadastraleaanduiding() != null) {
                    existingEobject.setKadastraleaanduiding(eobject.getKadastraleaanduiding());
                }
                if (eobject.getNaam() != null) {
                    existingEobject.setNaam(eobject.getNaam());
                }
                if (eobject.getEobjecttype() != null) {
                    existingEobject.setEobjecttype(eobject.getEobjecttype());
                }
                if (eobject.getToelichting() != null) {
                    existingEobject.setToelichting(eobject.getToelichting());
                }

                return existingEobject;
            })
            .map(eobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eobject.getId().toString())
        );
    }

    /**
     * {@code GET  /eobjects} : get all the eobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjects in body.
     */
    @GetMapping("")
    public List<Eobject> getAllEobjects() {
        log.debug("REST request to get all Eobjects");
        return eobjectRepository.findAll();
    }

    /**
     * {@code GET  /eobjects/:id} : get the "id" eobject.
     *
     * @param id the id of the eobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobject> getEobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobject : {}", id);
        Optional<Eobject> eobject = eobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobject);
    }

    /**
     * {@code DELETE  /eobjects/:id} : delete the "id" eobject.
     *
     * @param id the id of the eobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobject : {}", id);
        eobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
