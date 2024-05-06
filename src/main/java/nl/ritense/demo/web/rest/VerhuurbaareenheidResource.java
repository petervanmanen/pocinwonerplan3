package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verhuurbaareenheid;
import nl.ritense.demo.repository.VerhuurbaareenheidRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verhuurbaareenheid}.
 */
@RestController
@RequestMapping("/api/verhuurbaareenheids")
@Transactional
public class VerhuurbaareenheidResource {

    private final Logger log = LoggerFactory.getLogger(VerhuurbaareenheidResource.class);

    private static final String ENTITY_NAME = "verhuurbaareenheid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerhuurbaareenheidRepository verhuurbaareenheidRepository;

    public VerhuurbaareenheidResource(VerhuurbaareenheidRepository verhuurbaareenheidRepository) {
        this.verhuurbaareenheidRepository = verhuurbaareenheidRepository;
    }

    /**
     * {@code POST  /verhuurbaareenheids} : Create a new verhuurbaareenheid.
     *
     * @param verhuurbaareenheid the verhuurbaareenheid to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verhuurbaareenheid, or with status {@code 400 (Bad Request)} if the verhuurbaareenheid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verhuurbaareenheid> createVerhuurbaareenheid(@RequestBody Verhuurbaareenheid verhuurbaareenheid)
        throws URISyntaxException {
        log.debug("REST request to save Verhuurbaareenheid : {}", verhuurbaareenheid);
        if (verhuurbaareenheid.getId() != null) {
            throw new BadRequestAlertException("A new verhuurbaareenheid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verhuurbaareenheid = verhuurbaareenheidRepository.save(verhuurbaareenheid);
        return ResponseEntity.created(new URI("/api/verhuurbaareenheids/" + verhuurbaareenheid.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verhuurbaareenheid.getId().toString()))
            .body(verhuurbaareenheid);
    }

    /**
     * {@code PUT  /verhuurbaareenheids/:id} : Updates an existing verhuurbaareenheid.
     *
     * @param id the id of the verhuurbaareenheid to save.
     * @param verhuurbaareenheid the verhuurbaareenheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verhuurbaareenheid,
     * or with status {@code 400 (Bad Request)} if the verhuurbaareenheid is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verhuurbaareenheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verhuurbaareenheid> updateVerhuurbaareenheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verhuurbaareenheid verhuurbaareenheid
    ) throws URISyntaxException {
        log.debug("REST request to update Verhuurbaareenheid : {}, {}", id, verhuurbaareenheid);
        if (verhuurbaareenheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verhuurbaareenheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verhuurbaareenheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verhuurbaareenheid = verhuurbaareenheidRepository.save(verhuurbaareenheid);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verhuurbaareenheid.getId().toString()))
            .body(verhuurbaareenheid);
    }

    /**
     * {@code PATCH  /verhuurbaareenheids/:id} : Partial updates given fields of an existing verhuurbaareenheid, field will ignore if it is null
     *
     * @param id the id of the verhuurbaareenheid to save.
     * @param verhuurbaareenheid the verhuurbaareenheid to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verhuurbaareenheid,
     * or with status {@code 400 (Bad Request)} if the verhuurbaareenheid is not valid,
     * or with status {@code 404 (Not Found)} if the verhuurbaareenheid is not found,
     * or with status {@code 500 (Internal Server Error)} if the verhuurbaareenheid couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verhuurbaareenheid> partialUpdateVerhuurbaareenheid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verhuurbaareenheid verhuurbaareenheid
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verhuurbaareenheid partially : {}, {}", id, verhuurbaareenheid);
        if (verhuurbaareenheid.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verhuurbaareenheid.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verhuurbaareenheidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verhuurbaareenheid> result = verhuurbaareenheidRepository
            .findById(verhuurbaareenheid.getId())
            .map(existingVerhuurbaareenheid -> {
                if (verhuurbaareenheid.getAdres() != null) {
                    existingVerhuurbaareenheid.setAdres(verhuurbaareenheid.getAdres());
                }
                if (verhuurbaareenheid.getAfmeting() != null) {
                    existingVerhuurbaareenheid.setAfmeting(verhuurbaareenheid.getAfmeting());
                }
                if (verhuurbaareenheid.getBezetting() != null) {
                    existingVerhuurbaareenheid.setBezetting(verhuurbaareenheid.getBezetting());
                }
                if (verhuurbaareenheid.getDatumeinde() != null) {
                    existingVerhuurbaareenheid.setDatumeinde(verhuurbaareenheid.getDatumeinde());
                }
                if (verhuurbaareenheid.getDatumstart() != null) {
                    existingVerhuurbaareenheid.setDatumstart(verhuurbaareenheid.getDatumstart());
                }
                if (verhuurbaareenheid.getDatumwerkelijkbegin() != null) {
                    existingVerhuurbaareenheid.setDatumwerkelijkbegin(verhuurbaareenheid.getDatumwerkelijkbegin());
                }
                if (verhuurbaareenheid.getDatumwerkelijkeinde() != null) {
                    existingVerhuurbaareenheid.setDatumwerkelijkeinde(verhuurbaareenheid.getDatumwerkelijkeinde());
                }
                if (verhuurbaareenheid.getHuurprijs() != null) {
                    existingVerhuurbaareenheid.setHuurprijs(verhuurbaareenheid.getHuurprijs());
                }
                if (verhuurbaareenheid.getIdentificatie() != null) {
                    existingVerhuurbaareenheid.setIdentificatie(verhuurbaareenheid.getIdentificatie());
                }
                if (verhuurbaareenheid.getNaam() != null) {
                    existingVerhuurbaareenheid.setNaam(verhuurbaareenheid.getNaam());
                }
                if (verhuurbaareenheid.getNettoomtrek() != null) {
                    existingVerhuurbaareenheid.setNettoomtrek(verhuurbaareenheid.getNettoomtrek());
                }
                if (verhuurbaareenheid.getNettooppervlak() != null) {
                    existingVerhuurbaareenheid.setNettooppervlak(verhuurbaareenheid.getNettooppervlak());
                }
                if (verhuurbaareenheid.getOpmerkingen() != null) {
                    existingVerhuurbaareenheid.setOpmerkingen(verhuurbaareenheid.getOpmerkingen());
                }
                if (verhuurbaareenheid.getType() != null) {
                    existingVerhuurbaareenheid.setType(verhuurbaareenheid.getType());
                }

                return existingVerhuurbaareenheid;
            })
            .map(verhuurbaareenheidRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verhuurbaareenheid.getId().toString())
        );
    }

    /**
     * {@code GET  /verhuurbaareenheids} : get all the verhuurbaareenheids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verhuurbaareenheids in body.
     */
    @GetMapping("")
    public List<Verhuurbaareenheid> getAllVerhuurbaareenheids() {
        log.debug("REST request to get all Verhuurbaareenheids");
        return verhuurbaareenheidRepository.findAll();
    }

    /**
     * {@code GET  /verhuurbaareenheids/:id} : get the "id" verhuurbaareenheid.
     *
     * @param id the id of the verhuurbaareenheid to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verhuurbaareenheid, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verhuurbaareenheid> getVerhuurbaareenheid(@PathVariable("id") Long id) {
        log.debug("REST request to get Verhuurbaareenheid : {}", id);
        Optional<Verhuurbaareenheid> verhuurbaareenheid = verhuurbaareenheidRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verhuurbaareenheid);
    }

    /**
     * {@code DELETE  /verhuurbaareenheids/:id} : delete the "id" verhuurbaareenheid.
     *
     * @param id the id of the verhuurbaareenheid to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerhuurbaareenheid(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verhuurbaareenheid : {}", id);
        verhuurbaareenheidRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
