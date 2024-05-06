package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Eobjectrelatie;
import nl.ritense.demo.repository.EobjectrelatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Eobjectrelatie}.
 */
@RestController
@RequestMapping("/api/eobjectrelaties")
@Transactional
public class EobjectrelatieResource {

    private final Logger log = LoggerFactory.getLogger(EobjectrelatieResource.class);

    private static final String ENTITY_NAME = "eobjectrelatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EobjectrelatieRepository eobjectrelatieRepository;

    public EobjectrelatieResource(EobjectrelatieRepository eobjectrelatieRepository) {
        this.eobjectrelatieRepository = eobjectrelatieRepository;
    }

    /**
     * {@code POST  /eobjectrelaties} : Create a new eobjectrelatie.
     *
     * @param eobjectrelatie the eobjectrelatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eobjectrelatie, or with status {@code 400 (Bad Request)} if the eobjectrelatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Eobjectrelatie> createEobjectrelatie(@RequestBody Eobjectrelatie eobjectrelatie) throws URISyntaxException {
        log.debug("REST request to save Eobjectrelatie : {}", eobjectrelatie);
        if (eobjectrelatie.getId() != null) {
            throw new BadRequestAlertException("A new eobjectrelatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eobjectrelatie = eobjectrelatieRepository.save(eobjectrelatie);
        return ResponseEntity.created(new URI("/api/eobjectrelaties/" + eobjectrelatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eobjectrelatie.getId().toString()))
            .body(eobjectrelatie);
    }

    /**
     * {@code PUT  /eobjectrelaties/:id} : Updates an existing eobjectrelatie.
     *
     * @param id the id of the eobjectrelatie to save.
     * @param eobjectrelatie the eobjectrelatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eobjectrelatie,
     * or with status {@code 400 (Bad Request)} if the eobjectrelatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eobjectrelatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Eobjectrelatie> updateEobjectrelatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Eobjectrelatie eobjectrelatie
    ) throws URISyntaxException {
        log.debug("REST request to update Eobjectrelatie : {}, {}", id, eobjectrelatie);
        if (eobjectrelatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eobjectrelatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eobjectrelatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eobjectrelatie = eobjectrelatieRepository.save(eobjectrelatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eobjectrelatie.getId().toString()))
            .body(eobjectrelatie);
    }

    /**
     * {@code PATCH  /eobjectrelaties/:id} : Partial updates given fields of an existing eobjectrelatie, field will ignore if it is null
     *
     * @param id the id of the eobjectrelatie to save.
     * @param eobjectrelatie the eobjectrelatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eobjectrelatie,
     * or with status {@code 400 (Bad Request)} if the eobjectrelatie is not valid,
     * or with status {@code 404 (Not Found)} if the eobjectrelatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the eobjectrelatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Eobjectrelatie> partialUpdateEobjectrelatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Eobjectrelatie eobjectrelatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eobjectrelatie partially : {}, {}", id, eobjectrelatie);
        if (eobjectrelatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eobjectrelatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eobjectrelatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Eobjectrelatie> result = eobjectrelatieRepository
            .findById(eobjectrelatie.getId())
            .map(existingEobjectrelatie -> {
                if (eobjectrelatie.getRol() != null) {
                    existingEobjectrelatie.setRol(eobjectrelatie.getRol());
                }

                return existingEobjectrelatie;
            })
            .map(eobjectrelatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eobjectrelatie.getId().toString())
        );
    }

    /**
     * {@code GET  /eobjectrelaties} : get all the eobjectrelaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eobjectrelaties in body.
     */
    @GetMapping("")
    public List<Eobjectrelatie> getAllEobjectrelaties() {
        log.debug("REST request to get all Eobjectrelaties");
        return eobjectrelatieRepository.findAll();
    }

    /**
     * {@code GET  /eobjectrelaties/:id} : get the "id" eobjectrelatie.
     *
     * @param id the id of the eobjectrelatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eobjectrelatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Eobjectrelatie> getEobjectrelatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Eobjectrelatie : {}", id);
        Optional<Eobjectrelatie> eobjectrelatie = eobjectrelatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eobjectrelatie);
    }

    /**
     * {@code DELETE  /eobjectrelaties/:id} : delete the "id" eobjectrelatie.
     *
     * @param id the id of the eobjectrelatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEobjectrelatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Eobjectrelatie : {}", id);
        eobjectrelatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
