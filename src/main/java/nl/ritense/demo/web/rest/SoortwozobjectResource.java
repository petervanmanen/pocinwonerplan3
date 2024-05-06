package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Soortwozobject;
import nl.ritense.demo.repository.SoortwozobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Soortwozobject}.
 */
@RestController
@RequestMapping("/api/soortwozobjects")
@Transactional
public class SoortwozobjectResource {

    private final Logger log = LoggerFactory.getLogger(SoortwozobjectResource.class);

    private static final String ENTITY_NAME = "soortwozobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoortwozobjectRepository soortwozobjectRepository;

    public SoortwozobjectResource(SoortwozobjectRepository soortwozobjectRepository) {
        this.soortwozobjectRepository = soortwozobjectRepository;
    }

    /**
     * {@code POST  /soortwozobjects} : Create a new soortwozobject.
     *
     * @param soortwozobject the soortwozobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soortwozobject, or with status {@code 400 (Bad Request)} if the soortwozobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Soortwozobject> createSoortwozobject(@RequestBody Soortwozobject soortwozobject) throws URISyntaxException {
        log.debug("REST request to save Soortwozobject : {}", soortwozobject);
        if (soortwozobject.getId() != null) {
            throw new BadRequestAlertException("A new soortwozobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        soortwozobject = soortwozobjectRepository.save(soortwozobject);
        return ResponseEntity.created(new URI("/api/soortwozobjects/" + soortwozobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, soortwozobject.getId().toString()))
            .body(soortwozobject);
    }

    /**
     * {@code PUT  /soortwozobjects/:id} : Updates an existing soortwozobject.
     *
     * @param id the id of the soortwozobject to save.
     * @param soortwozobject the soortwozobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortwozobject,
     * or with status {@code 400 (Bad Request)} if the soortwozobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soortwozobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Soortwozobject> updateSoortwozobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortwozobject soortwozobject
    ) throws URISyntaxException {
        log.debug("REST request to update Soortwozobject : {}, {}", id, soortwozobject);
        if (soortwozobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortwozobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortwozobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        soortwozobject = soortwozobjectRepository.save(soortwozobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortwozobject.getId().toString()))
            .body(soortwozobject);
    }

    /**
     * {@code PATCH  /soortwozobjects/:id} : Partial updates given fields of an existing soortwozobject, field will ignore if it is null
     *
     * @param id the id of the soortwozobject to save.
     * @param soortwozobject the soortwozobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortwozobject,
     * or with status {@code 400 (Bad Request)} if the soortwozobject is not valid,
     * or with status {@code 404 (Not Found)} if the soortwozobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the soortwozobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Soortwozobject> partialUpdateSoortwozobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortwozobject soortwozobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Soortwozobject partially : {}, {}", id, soortwozobject);
        if (soortwozobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortwozobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortwozobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Soortwozobject> result = soortwozobjectRepository
            .findById(soortwozobject.getId())
            .map(existingSoortwozobject -> {
                if (soortwozobject.getDatumbegingeldigheidsoortobjectcode() != null) {
                    existingSoortwozobject.setDatumbegingeldigheidsoortobjectcode(soortwozobject.getDatumbegingeldigheidsoortobjectcode());
                }
                if (soortwozobject.getDatumeindegeldigheidsoortobjectcode() != null) {
                    existingSoortwozobject.setDatumeindegeldigheidsoortobjectcode(soortwozobject.getDatumeindegeldigheidsoortobjectcode());
                }
                if (soortwozobject.getNaamsoortobjectcode() != null) {
                    existingSoortwozobject.setNaamsoortobjectcode(soortwozobject.getNaamsoortobjectcode());
                }
                if (soortwozobject.getOpmerkingensoortobjectcode() != null) {
                    existingSoortwozobject.setOpmerkingensoortobjectcode(soortwozobject.getOpmerkingensoortobjectcode());
                }
                if (soortwozobject.getSoortobjectcode() != null) {
                    existingSoortwozobject.setSoortobjectcode(soortwozobject.getSoortobjectcode());
                }

                return existingSoortwozobject;
            })
            .map(soortwozobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortwozobject.getId().toString())
        );
    }

    /**
     * {@code GET  /soortwozobjects} : get all the soortwozobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soortwozobjects in body.
     */
    @GetMapping("")
    public List<Soortwozobject> getAllSoortwozobjects() {
        log.debug("REST request to get all Soortwozobjects");
        return soortwozobjectRepository.findAll();
    }

    /**
     * {@code GET  /soortwozobjects/:id} : get the "id" soortwozobject.
     *
     * @param id the id of the soortwozobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soortwozobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Soortwozobject> getSoortwozobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Soortwozobject : {}", id);
        Optional<Soortwozobject> soortwozobject = soortwozobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(soortwozobject);
    }

    /**
     * {@code DELETE  /soortwozobjects/:id} : delete the "id" soortwozobject.
     *
     * @param id the id of the soortwozobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoortwozobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Soortwozobject : {}", id);
        soortwozobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
