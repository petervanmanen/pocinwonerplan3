package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kunstwerkdeel;
import nl.ritense.demo.repository.KunstwerkdeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kunstwerkdeel}.
 */
@RestController
@RequestMapping("/api/kunstwerkdeels")
@Transactional
public class KunstwerkdeelResource {

    private final Logger log = LoggerFactory.getLogger(KunstwerkdeelResource.class);

    private static final String ENTITY_NAME = "kunstwerkdeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KunstwerkdeelRepository kunstwerkdeelRepository;

    public KunstwerkdeelResource(KunstwerkdeelRepository kunstwerkdeelRepository) {
        this.kunstwerkdeelRepository = kunstwerkdeelRepository;
    }

    /**
     * {@code POST  /kunstwerkdeels} : Create a new kunstwerkdeel.
     *
     * @param kunstwerkdeel the kunstwerkdeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kunstwerkdeel, or with status {@code 400 (Bad Request)} if the kunstwerkdeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kunstwerkdeel> createKunstwerkdeel(@RequestBody Kunstwerkdeel kunstwerkdeel) throws URISyntaxException {
        log.debug("REST request to save Kunstwerkdeel : {}", kunstwerkdeel);
        if (kunstwerkdeel.getId() != null) {
            throw new BadRequestAlertException("A new kunstwerkdeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kunstwerkdeel = kunstwerkdeelRepository.save(kunstwerkdeel);
        return ResponseEntity.created(new URI("/api/kunstwerkdeels/" + kunstwerkdeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kunstwerkdeel.getId().toString()))
            .body(kunstwerkdeel);
    }

    /**
     * {@code PUT  /kunstwerkdeels/:id} : Updates an existing kunstwerkdeel.
     *
     * @param id the id of the kunstwerkdeel to save.
     * @param kunstwerkdeel the kunstwerkdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kunstwerkdeel,
     * or with status {@code 400 (Bad Request)} if the kunstwerkdeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kunstwerkdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kunstwerkdeel> updateKunstwerkdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kunstwerkdeel kunstwerkdeel
    ) throws URISyntaxException {
        log.debug("REST request to update Kunstwerkdeel : {}, {}", id, kunstwerkdeel);
        if (kunstwerkdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kunstwerkdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kunstwerkdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kunstwerkdeel = kunstwerkdeelRepository.save(kunstwerkdeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kunstwerkdeel.getId().toString()))
            .body(kunstwerkdeel);
    }

    /**
     * {@code PATCH  /kunstwerkdeels/:id} : Partial updates given fields of an existing kunstwerkdeel, field will ignore if it is null
     *
     * @param id the id of the kunstwerkdeel to save.
     * @param kunstwerkdeel the kunstwerkdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kunstwerkdeel,
     * or with status {@code 400 (Bad Request)} if the kunstwerkdeel is not valid,
     * or with status {@code 404 (Not Found)} if the kunstwerkdeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the kunstwerkdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kunstwerkdeel> partialUpdateKunstwerkdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kunstwerkdeel kunstwerkdeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kunstwerkdeel partially : {}, {}", id, kunstwerkdeel);
        if (kunstwerkdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kunstwerkdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kunstwerkdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kunstwerkdeel> result = kunstwerkdeelRepository
            .findById(kunstwerkdeel.getId())
            .map(existingKunstwerkdeel -> {
                if (kunstwerkdeel.getDatumbegingeldigheidkunstwerkdeel() != null) {
                    existingKunstwerkdeel.setDatumbegingeldigheidkunstwerkdeel(kunstwerkdeel.getDatumbegingeldigheidkunstwerkdeel());
                }
                if (kunstwerkdeel.getDatumeindegeldigheidkunstwerkdeel() != null) {
                    existingKunstwerkdeel.setDatumeindegeldigheidkunstwerkdeel(kunstwerkdeel.getDatumeindegeldigheidkunstwerkdeel());
                }
                if (kunstwerkdeel.getGeometriekunstwerkdeel() != null) {
                    existingKunstwerkdeel.setGeometriekunstwerkdeel(kunstwerkdeel.getGeometriekunstwerkdeel());
                }
                if (kunstwerkdeel.getIdentificatiekunstwerkdeel() != null) {
                    existingKunstwerkdeel.setIdentificatiekunstwerkdeel(kunstwerkdeel.getIdentificatiekunstwerkdeel());
                }
                if (kunstwerkdeel.getLod0geometriekunstwerkdeel() != null) {
                    existingKunstwerkdeel.setLod0geometriekunstwerkdeel(kunstwerkdeel.getLod0geometriekunstwerkdeel());
                }
                if (kunstwerkdeel.getLod1geometriekunstwerkdeel() != null) {
                    existingKunstwerkdeel.setLod1geometriekunstwerkdeel(kunstwerkdeel.getLod1geometriekunstwerkdeel());
                }
                if (kunstwerkdeel.getLod2geometriekunstwerkdeel() != null) {
                    existingKunstwerkdeel.setLod2geometriekunstwerkdeel(kunstwerkdeel.getLod2geometriekunstwerkdeel());
                }
                if (kunstwerkdeel.getLod3geometriekunstwerkdeel() != null) {
                    existingKunstwerkdeel.setLod3geometriekunstwerkdeel(kunstwerkdeel.getLod3geometriekunstwerkdeel());
                }
                if (kunstwerkdeel.getRelatievehoogteliggingkunstwerkdeel() != null) {
                    existingKunstwerkdeel.setRelatievehoogteliggingkunstwerkdeel(kunstwerkdeel.getRelatievehoogteliggingkunstwerkdeel());
                }
                if (kunstwerkdeel.getStatuskunstwerkdeel() != null) {
                    existingKunstwerkdeel.setStatuskunstwerkdeel(kunstwerkdeel.getStatuskunstwerkdeel());
                }

                return existingKunstwerkdeel;
            })
            .map(kunstwerkdeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kunstwerkdeel.getId().toString())
        );
    }

    /**
     * {@code GET  /kunstwerkdeels} : get all the kunstwerkdeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kunstwerkdeels in body.
     */
    @GetMapping("")
    public List<Kunstwerkdeel> getAllKunstwerkdeels() {
        log.debug("REST request to get all Kunstwerkdeels");
        return kunstwerkdeelRepository.findAll();
    }

    /**
     * {@code GET  /kunstwerkdeels/:id} : get the "id" kunstwerkdeel.
     *
     * @param id the id of the kunstwerkdeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kunstwerkdeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kunstwerkdeel> getKunstwerkdeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Kunstwerkdeel : {}", id);
        Optional<Kunstwerkdeel> kunstwerkdeel = kunstwerkdeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kunstwerkdeel);
    }

    /**
     * {@code DELETE  /kunstwerkdeels/:id} : delete the "id" kunstwerkdeel.
     *
     * @param id the id of the kunstwerkdeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKunstwerkdeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kunstwerkdeel : {}", id);
        kunstwerkdeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
