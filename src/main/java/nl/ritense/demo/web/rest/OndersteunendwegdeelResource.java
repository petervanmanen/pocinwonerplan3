package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ondersteunendwegdeel;
import nl.ritense.demo.repository.OndersteunendwegdeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ondersteunendwegdeel}.
 */
@RestController
@RequestMapping("/api/ondersteunendwegdeels")
@Transactional
public class OndersteunendwegdeelResource {

    private final Logger log = LoggerFactory.getLogger(OndersteunendwegdeelResource.class);

    private static final String ENTITY_NAME = "ondersteunendwegdeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OndersteunendwegdeelRepository ondersteunendwegdeelRepository;

    public OndersteunendwegdeelResource(OndersteunendwegdeelRepository ondersteunendwegdeelRepository) {
        this.ondersteunendwegdeelRepository = ondersteunendwegdeelRepository;
    }

    /**
     * {@code POST  /ondersteunendwegdeels} : Create a new ondersteunendwegdeel.
     *
     * @param ondersteunendwegdeel the ondersteunendwegdeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ondersteunendwegdeel, or with status {@code 400 (Bad Request)} if the ondersteunendwegdeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ondersteunendwegdeel> createOndersteunendwegdeel(@RequestBody Ondersteunendwegdeel ondersteunendwegdeel)
        throws URISyntaxException {
        log.debug("REST request to save Ondersteunendwegdeel : {}", ondersteunendwegdeel);
        if (ondersteunendwegdeel.getId() != null) {
            throw new BadRequestAlertException("A new ondersteunendwegdeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ondersteunendwegdeel = ondersteunendwegdeelRepository.save(ondersteunendwegdeel);
        return ResponseEntity.created(new URI("/api/ondersteunendwegdeels/" + ondersteunendwegdeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ondersteunendwegdeel.getId().toString()))
            .body(ondersteunendwegdeel);
    }

    /**
     * {@code PUT  /ondersteunendwegdeels/:id} : Updates an existing ondersteunendwegdeel.
     *
     * @param id the id of the ondersteunendwegdeel to save.
     * @param ondersteunendwegdeel the ondersteunendwegdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ondersteunendwegdeel,
     * or with status {@code 400 (Bad Request)} if the ondersteunendwegdeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ondersteunendwegdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ondersteunendwegdeel> updateOndersteunendwegdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ondersteunendwegdeel ondersteunendwegdeel
    ) throws URISyntaxException {
        log.debug("REST request to update Ondersteunendwegdeel : {}, {}", id, ondersteunendwegdeel);
        if (ondersteunendwegdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ondersteunendwegdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ondersteunendwegdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ondersteunendwegdeel = ondersteunendwegdeelRepository.save(ondersteunendwegdeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ondersteunendwegdeel.getId().toString()))
            .body(ondersteunendwegdeel);
    }

    /**
     * {@code PATCH  /ondersteunendwegdeels/:id} : Partial updates given fields of an existing ondersteunendwegdeel, field will ignore if it is null
     *
     * @param id the id of the ondersteunendwegdeel to save.
     * @param ondersteunendwegdeel the ondersteunendwegdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ondersteunendwegdeel,
     * or with status {@code 400 (Bad Request)} if the ondersteunendwegdeel is not valid,
     * or with status {@code 404 (Not Found)} if the ondersteunendwegdeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the ondersteunendwegdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ondersteunendwegdeel> partialUpdateOndersteunendwegdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ondersteunendwegdeel ondersteunendwegdeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ondersteunendwegdeel partially : {}, {}", id, ondersteunendwegdeel);
        if (ondersteunendwegdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ondersteunendwegdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ondersteunendwegdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ondersteunendwegdeel> result = ondersteunendwegdeelRepository
            .findById(ondersteunendwegdeel.getId())
            .map(existingOndersteunendwegdeel -> {
                if (ondersteunendwegdeel.getDatumbegingeldigheidondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setDatumbegingeldigheidondersteunendwegdeel(
                        ondersteunendwegdeel.getDatumbegingeldigheidondersteunendwegdeel()
                    );
                }
                if (ondersteunendwegdeel.getDatumeindegeldigheidondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setDatumeindegeldigheidondersteunendwegdeel(
                        ondersteunendwegdeel.getDatumeindegeldigheidondersteunendwegdeel()
                    );
                }
                if (ondersteunendwegdeel.getFunctieondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setFunctieondersteunendwegdeel(ondersteunendwegdeel.getFunctieondersteunendwegdeel());
                }
                if (ondersteunendwegdeel.getFysiekvoorkomenondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setFysiekvoorkomenondersteunendwegdeel(
                        ondersteunendwegdeel.getFysiekvoorkomenondersteunendwegdeel()
                    );
                }
                if (ondersteunendwegdeel.getGeometrieondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setGeometrieondersteunendwegdeel(ondersteunendwegdeel.getGeometrieondersteunendwegdeel());
                }
                if (ondersteunendwegdeel.getIdentificatieondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setIdentificatieondersteunendwegdeel(
                        ondersteunendwegdeel.getIdentificatieondersteunendwegdeel()
                    );
                }
                if (ondersteunendwegdeel.getKruinlijngeometrieondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setKruinlijngeometrieondersteunendwegdeel(
                        ondersteunendwegdeel.getKruinlijngeometrieondersteunendwegdeel()
                    );
                }
                if (ondersteunendwegdeel.getLod0geometrieondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setLod0geometrieondersteunendwegdeel(
                        ondersteunendwegdeel.getLod0geometrieondersteunendwegdeel()
                    );
                }
                if (ondersteunendwegdeel.getOndersteunendwegdeeloptalud() != null) {
                    existingOndersteunendwegdeel.setOndersteunendwegdeeloptalud(ondersteunendwegdeel.getOndersteunendwegdeeloptalud());
                }
                if (ondersteunendwegdeel.getPlusfunctieondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setPlusfunctieondersteunendwegdeel(
                        ondersteunendwegdeel.getPlusfunctieondersteunendwegdeel()
                    );
                }
                if (ondersteunendwegdeel.getPlusfysiekvoorkomenondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setPlusfysiekvoorkomenondersteunendwegdeel(
                        ondersteunendwegdeel.getPlusfysiekvoorkomenondersteunendwegdeel()
                    );
                }
                if (ondersteunendwegdeel.getRelatievehoogteliggingondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setRelatievehoogteliggingondersteunendwegdeel(
                        ondersteunendwegdeel.getRelatievehoogteliggingondersteunendwegdeel()
                    );
                }
                if (ondersteunendwegdeel.getStatusondersteunendwegdeel() != null) {
                    existingOndersteunendwegdeel.setStatusondersteunendwegdeel(ondersteunendwegdeel.getStatusondersteunendwegdeel());
                }

                return existingOndersteunendwegdeel;
            })
            .map(ondersteunendwegdeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ondersteunendwegdeel.getId().toString())
        );
    }

    /**
     * {@code GET  /ondersteunendwegdeels} : get all the ondersteunendwegdeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ondersteunendwegdeels in body.
     */
    @GetMapping("")
    public List<Ondersteunendwegdeel> getAllOndersteunendwegdeels() {
        log.debug("REST request to get all Ondersteunendwegdeels");
        return ondersteunendwegdeelRepository.findAll();
    }

    /**
     * {@code GET  /ondersteunendwegdeels/:id} : get the "id" ondersteunendwegdeel.
     *
     * @param id the id of the ondersteunendwegdeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ondersteunendwegdeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ondersteunendwegdeel> getOndersteunendwegdeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Ondersteunendwegdeel : {}", id);
        Optional<Ondersteunendwegdeel> ondersteunendwegdeel = ondersteunendwegdeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ondersteunendwegdeel);
    }

    /**
     * {@code DELETE  /ondersteunendwegdeels/:id} : delete the "id" ondersteunendwegdeel.
     *
     * @param id the id of the ondersteunendwegdeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOndersteunendwegdeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ondersteunendwegdeel : {}", id);
        ondersteunendwegdeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
