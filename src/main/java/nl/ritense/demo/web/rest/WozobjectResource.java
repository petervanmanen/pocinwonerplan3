package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Wozobject;
import nl.ritense.demo.repository.WozobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Wozobject}.
 */
@RestController
@RequestMapping("/api/wozobjects")
@Transactional
public class WozobjectResource {

    private final Logger log = LoggerFactory.getLogger(WozobjectResource.class);

    private static final String ENTITY_NAME = "wozobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WozobjectRepository wozobjectRepository;

    public WozobjectResource(WozobjectRepository wozobjectRepository) {
        this.wozobjectRepository = wozobjectRepository;
    }

    /**
     * {@code POST  /wozobjects} : Create a new wozobject.
     *
     * @param wozobject the wozobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wozobject, or with status {@code 400 (Bad Request)} if the wozobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Wozobject> createWozobject(@RequestBody Wozobject wozobject) throws URISyntaxException {
        log.debug("REST request to save Wozobject : {}", wozobject);
        if (wozobject.getId() != null) {
            throw new BadRequestAlertException("A new wozobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        wozobject = wozobjectRepository.save(wozobject);
        return ResponseEntity.created(new URI("/api/wozobjects/" + wozobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, wozobject.getId().toString()))
            .body(wozobject);
    }

    /**
     * {@code PUT  /wozobjects/:id} : Updates an existing wozobject.
     *
     * @param id the id of the wozobject to save.
     * @param wozobject the wozobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozobject,
     * or with status {@code 400 (Bad Request)} if the wozobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wozobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Wozobject> updateWozobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozobject wozobject
    ) throws URISyntaxException {
        log.debug("REST request to update Wozobject : {}, {}", id, wozobject);
        if (wozobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        wozobject = wozobjectRepository.save(wozobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozobject.getId().toString()))
            .body(wozobject);
    }

    /**
     * {@code PATCH  /wozobjects/:id} : Partial updates given fields of an existing wozobject, field will ignore if it is null
     *
     * @param id the id of the wozobject to save.
     * @param wozobject the wozobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozobject,
     * or with status {@code 400 (Bad Request)} if the wozobject is not valid,
     * or with status {@code 404 (Not Found)} if the wozobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the wozobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wozobject> partialUpdateWozobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozobject wozobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wozobject partially : {}, {}", id, wozobject);
        if (wozobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wozobject> result = wozobjectRepository
            .findById(wozobject.getId())
            .map(existingWozobject -> {
                if (wozobject.getEmpty() != null) {
                    existingWozobject.setEmpty(wozobject.getEmpty());
                }
                if (wozobject.getDatumbegingeldigheidwozobject() != null) {
                    existingWozobject.setDatumbegingeldigheidwozobject(wozobject.getDatumbegingeldigheidwozobject());
                }
                if (wozobject.getDatumeindegeldigheidwozobject() != null) {
                    existingWozobject.setDatumeindegeldigheidwozobject(wozobject.getDatumeindegeldigheidwozobject());
                }
                if (wozobject.getDatumwaardepeiling() != null) {
                    existingWozobject.setDatumwaardepeiling(wozobject.getDatumwaardepeiling());
                }
                if (wozobject.getGebruikscode() != null) {
                    existingWozobject.setGebruikscode(wozobject.getGebruikscode());
                }
                if (wozobject.getGeometriewozobject() != null) {
                    existingWozobject.setGeometriewozobject(wozobject.getGeometriewozobject());
                }
                if (wozobject.getGrondoppervlakte() != null) {
                    existingWozobject.setGrondoppervlakte(wozobject.getGrondoppervlakte());
                }
                if (wozobject.getSoortobjectcode() != null) {
                    existingWozobject.setSoortobjectcode(wozobject.getSoortobjectcode());
                }
                if (wozobject.getStatuswozobject() != null) {
                    existingWozobject.setStatuswozobject(wozobject.getStatuswozobject());
                }
                if (wozobject.getVastgesteldewaarde() != null) {
                    existingWozobject.setVastgesteldewaarde(wozobject.getVastgesteldewaarde());
                }
                if (wozobject.getWozobjectnummer() != null) {
                    existingWozobject.setWozobjectnummer(wozobject.getWozobjectnummer());
                }

                return existingWozobject;
            })
            .map(wozobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozobject.getId().toString())
        );
    }

    /**
     * {@code GET  /wozobjects} : get all the wozobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wozobjects in body.
     */
    @GetMapping("")
    public List<Wozobject> getAllWozobjects() {
        log.debug("REST request to get all Wozobjects");
        return wozobjectRepository.findAll();
    }

    /**
     * {@code GET  /wozobjects/:id} : get the "id" wozobject.
     *
     * @param id the id of the wozobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wozobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wozobject> getWozobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Wozobject : {}", id);
        Optional<Wozobject> wozobject = wozobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wozobject);
    }

    /**
     * {@code DELETE  /wozobjects/:id} : delete the "id" wozobject.
     *
     * @param id the id of the wozobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWozobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Wozobject : {}", id);
        wozobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
