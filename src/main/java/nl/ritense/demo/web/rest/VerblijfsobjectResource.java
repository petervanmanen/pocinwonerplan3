package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfsobject;
import nl.ritense.demo.repository.VerblijfsobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verblijfsobject}.
 */
@RestController
@RequestMapping("/api/verblijfsobjects")
@Transactional
public class VerblijfsobjectResource {

    private final Logger log = LoggerFactory.getLogger(VerblijfsobjectResource.class);

    private static final String ENTITY_NAME = "verblijfsobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerblijfsobjectRepository verblijfsobjectRepository;

    public VerblijfsobjectResource(VerblijfsobjectRepository verblijfsobjectRepository) {
        this.verblijfsobjectRepository = verblijfsobjectRepository;
    }

    /**
     * {@code POST  /verblijfsobjects} : Create a new verblijfsobject.
     *
     * @param verblijfsobject the verblijfsobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verblijfsobject, or with status {@code 400 (Bad Request)} if the verblijfsobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verblijfsobject> createVerblijfsobject(@Valid @RequestBody Verblijfsobject verblijfsobject)
        throws URISyntaxException {
        log.debug("REST request to save Verblijfsobject : {}", verblijfsobject);
        if (verblijfsobject.getId() != null) {
            throw new BadRequestAlertException("A new verblijfsobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verblijfsobject = verblijfsobjectRepository.save(verblijfsobject);
        return ResponseEntity.created(new URI("/api/verblijfsobjects/" + verblijfsobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verblijfsobject.getId().toString()))
            .body(verblijfsobject);
    }

    /**
     * {@code PUT  /verblijfsobjects/:id} : Updates an existing verblijfsobject.
     *
     * @param id the id of the verblijfsobject to save.
     * @param verblijfsobject the verblijfsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfsobject,
     * or with status {@code 400 (Bad Request)} if the verblijfsobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verblijfsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verblijfsobject> updateVerblijfsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verblijfsobject verblijfsobject
    ) throws URISyntaxException {
        log.debug("REST request to update Verblijfsobject : {}, {}", id, verblijfsobject);
        if (verblijfsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verblijfsobject = verblijfsobjectRepository.save(verblijfsobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfsobject.getId().toString()))
            .body(verblijfsobject);
    }

    /**
     * {@code PATCH  /verblijfsobjects/:id} : Partial updates given fields of an existing verblijfsobject, field will ignore if it is null
     *
     * @param id the id of the verblijfsobject to save.
     * @param verblijfsobject the verblijfsobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfsobject,
     * or with status {@code 400 (Bad Request)} if the verblijfsobject is not valid,
     * or with status {@code 404 (Not Found)} if the verblijfsobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the verblijfsobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verblijfsobject> partialUpdateVerblijfsobject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verblijfsobject verblijfsobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verblijfsobject partially : {}, {}", id, verblijfsobject);
        if (verblijfsobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfsobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfsobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verblijfsobject> result = verblijfsobjectRepository
            .findById(verblijfsobject.getId())
            .map(existingVerblijfsobject -> {
                if (verblijfsobject.getAantalkamers() != null) {
                    existingVerblijfsobject.setAantalkamers(verblijfsobject.getAantalkamers());
                }
                if (verblijfsobject.getDatumbegingeldigheid() != null) {
                    existingVerblijfsobject.setDatumbegingeldigheid(verblijfsobject.getDatumbegingeldigheid());
                }
                if (verblijfsobject.getDatumeinde() != null) {
                    existingVerblijfsobject.setDatumeinde(verblijfsobject.getDatumeinde());
                }
                if (verblijfsobject.getDatumeindegeldigheid() != null) {
                    existingVerblijfsobject.setDatumeindegeldigheid(verblijfsobject.getDatumeindegeldigheid());
                }
                if (verblijfsobject.getDatumingang() != null) {
                    existingVerblijfsobject.setDatumingang(verblijfsobject.getDatumingang());
                }
                if (verblijfsobject.getDocumentdatum() != null) {
                    existingVerblijfsobject.setDocumentdatum(verblijfsobject.getDocumentdatum());
                }
                if (verblijfsobject.getDocumentnr() != null) {
                    existingVerblijfsobject.setDocumentnr(verblijfsobject.getDocumentnr());
                }
                if (verblijfsobject.getGebruiksdoel() != null) {
                    existingVerblijfsobject.setGebruiksdoel(verblijfsobject.getGebruiksdoel());
                }
                if (verblijfsobject.getGeconstateerd() != null) {
                    existingVerblijfsobject.setGeconstateerd(verblijfsobject.getGeconstateerd());
                }
                if (verblijfsobject.getGeometrie() != null) {
                    existingVerblijfsobject.setGeometrie(verblijfsobject.getGeometrie());
                }
                if (verblijfsobject.getHoogstebouwlaagverblijfsobject() != null) {
                    existingVerblijfsobject.setHoogstebouwlaagverblijfsobject(verblijfsobject.getHoogstebouwlaagverblijfsobject());
                }
                if (verblijfsobject.getIdentificatie() != null) {
                    existingVerblijfsobject.setIdentificatie(verblijfsobject.getIdentificatie());
                }
                if (verblijfsobject.getInonderzoek() != null) {
                    existingVerblijfsobject.setInonderzoek(verblijfsobject.getInonderzoek());
                }
                if (verblijfsobject.getLaagstebouwlaagverblijfsobject() != null) {
                    existingVerblijfsobject.setLaagstebouwlaagverblijfsobject(verblijfsobject.getLaagstebouwlaagverblijfsobject());
                }
                if (verblijfsobject.getOntsluitingverdieping() != null) {
                    existingVerblijfsobject.setOntsluitingverdieping(verblijfsobject.getOntsluitingverdieping());
                }
                if (verblijfsobject.getSoortwoonobject() != null) {
                    existingVerblijfsobject.setSoortwoonobject(verblijfsobject.getSoortwoonobject());
                }
                if (verblijfsobject.getStatus() != null) {
                    existingVerblijfsobject.setStatus(verblijfsobject.getStatus());
                }
                if (verblijfsobject.getToegangbouwlaagverblijfsobject() != null) {
                    existingVerblijfsobject.setToegangbouwlaagverblijfsobject(verblijfsobject.getToegangbouwlaagverblijfsobject());
                }
                if (verblijfsobject.getVersie() != null) {
                    existingVerblijfsobject.setVersie(verblijfsobject.getVersie());
                }

                return existingVerblijfsobject;
            })
            .map(verblijfsobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfsobject.getId().toString())
        );
    }

    /**
     * {@code GET  /verblijfsobjects} : get all the verblijfsobjects.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verblijfsobjects in body.
     */
    @GetMapping("")
    public List<Verblijfsobject> getAllVerblijfsobjects(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Verblijfsobjects");
        if (eagerload) {
            return verblijfsobjectRepository.findAllWithEagerRelationships();
        } else {
            return verblijfsobjectRepository.findAll();
        }
    }

    /**
     * {@code GET  /verblijfsobjects/:id} : get the "id" verblijfsobject.
     *
     * @param id the id of the verblijfsobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verblijfsobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verblijfsobject> getVerblijfsobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Verblijfsobject : {}", id);
        Optional<Verblijfsobject> verblijfsobject = verblijfsobjectRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(verblijfsobject);
    }

    /**
     * {@code DELETE  /verblijfsobjects/:id} : delete the "id" verblijfsobject.
     *
     * @param id the id of the verblijfsobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerblijfsobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verblijfsobject : {}", id);
        verblijfsobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
