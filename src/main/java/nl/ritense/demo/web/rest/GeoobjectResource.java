package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Geoobject;
import nl.ritense.demo.repository.GeoobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Geoobject}.
 */
@RestController
@RequestMapping("/api/geoobjects")
@Transactional
public class GeoobjectResource {

    private final Logger log = LoggerFactory.getLogger(GeoobjectResource.class);

    private static final String ENTITY_NAME = "geoobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeoobjectRepository geoobjectRepository;

    public GeoobjectResource(GeoobjectRepository geoobjectRepository) {
        this.geoobjectRepository = geoobjectRepository;
    }

    /**
     * {@code POST  /geoobjects} : Create a new geoobject.
     *
     * @param geoobject the geoobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geoobject, or with status {@code 400 (Bad Request)} if the geoobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Geoobject> createGeoobject(@RequestBody Geoobject geoobject) throws URISyntaxException {
        log.debug("REST request to save Geoobject : {}", geoobject);
        if (geoobject.getId() != null) {
            throw new BadRequestAlertException("A new geoobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        geoobject = geoobjectRepository.save(geoobject);
        return ResponseEntity.created(new URI("/api/geoobjects/" + geoobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, geoobject.getId().toString()))
            .body(geoobject);
    }

    /**
     * {@code PUT  /geoobjects/:id} : Updates an existing geoobject.
     *
     * @param id the id of the geoobject to save.
     * @param geoobject the geoobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geoobject,
     * or with status {@code 400 (Bad Request)} if the geoobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geoobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Geoobject> updateGeoobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Geoobject geoobject
    ) throws URISyntaxException {
        log.debug("REST request to update Geoobject : {}, {}", id, geoobject);
        if (geoobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geoobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geoobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        geoobject = geoobjectRepository.save(geoobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geoobject.getId().toString()))
            .body(geoobject);
    }

    /**
     * {@code PATCH  /geoobjects/:id} : Partial updates given fields of an existing geoobject, field will ignore if it is null
     *
     * @param id the id of the geoobject to save.
     * @param geoobject the geoobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geoobject,
     * or with status {@code 400 (Bad Request)} if the geoobject is not valid,
     * or with status {@code 404 (Not Found)} if the geoobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the geoobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Geoobject> partialUpdateGeoobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Geoobject geoobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Geoobject partially : {}, {}", id, geoobject);
        if (geoobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geoobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geoobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Geoobject> result = geoobjectRepository
            .findById(geoobject.getId())
            .map(existingGeoobject -> {
                if (geoobject.getDatumbegingeldigheid() != null) {
                    existingGeoobject.setDatumbegingeldigheid(geoobject.getDatumbegingeldigheid());
                }
                if (geoobject.getDatumeindegeldigheid() != null) {
                    existingGeoobject.setDatumeindegeldigheid(geoobject.getDatumeindegeldigheid());
                }
                if (geoobject.getGeometriesoort() != null) {
                    existingGeoobject.setGeometriesoort(geoobject.getGeometriesoort());
                }
                if (geoobject.getIdentificatie() != null) {
                    existingGeoobject.setIdentificatie(geoobject.getIdentificatie());
                }

                return existingGeoobject;
            })
            .map(geoobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geoobject.getId().toString())
        );
    }

    /**
     * {@code GET  /geoobjects} : get all the geoobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geoobjects in body.
     */
    @GetMapping("")
    public List<Geoobject> getAllGeoobjects() {
        log.debug("REST request to get all Geoobjects");
        return geoobjectRepository.findAll();
    }

    /**
     * {@code GET  /geoobjects/:id} : get the "id" geoobject.
     *
     * @param id the id of the geoobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geoobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Geoobject> getGeoobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Geoobject : {}", id);
        Optional<Geoobject> geoobject = geoobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(geoobject);
    }

    /**
     * {@code DELETE  /geoobjects/:id} : delete the "id" geoobject.
     *
     * @param id the id of the geoobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeoobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Geoobject : {}", id);
        geoobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
