package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ondersteunendwaterdeel;
import nl.ritense.demo.repository.OndersteunendwaterdeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ondersteunendwaterdeel}.
 */
@RestController
@RequestMapping("/api/ondersteunendwaterdeels")
@Transactional
public class OndersteunendwaterdeelResource {

    private final Logger log = LoggerFactory.getLogger(OndersteunendwaterdeelResource.class);

    private static final String ENTITY_NAME = "ondersteunendwaterdeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OndersteunendwaterdeelRepository ondersteunendwaterdeelRepository;

    public OndersteunendwaterdeelResource(OndersteunendwaterdeelRepository ondersteunendwaterdeelRepository) {
        this.ondersteunendwaterdeelRepository = ondersteunendwaterdeelRepository;
    }

    /**
     * {@code POST  /ondersteunendwaterdeels} : Create a new ondersteunendwaterdeel.
     *
     * @param ondersteunendwaterdeel the ondersteunendwaterdeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ondersteunendwaterdeel, or with status {@code 400 (Bad Request)} if the ondersteunendwaterdeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ondersteunendwaterdeel> createOndersteunendwaterdeel(@RequestBody Ondersteunendwaterdeel ondersteunendwaterdeel)
        throws URISyntaxException {
        log.debug("REST request to save Ondersteunendwaterdeel : {}", ondersteunendwaterdeel);
        if (ondersteunendwaterdeel.getId() != null) {
            throw new BadRequestAlertException("A new ondersteunendwaterdeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ondersteunendwaterdeel = ondersteunendwaterdeelRepository.save(ondersteunendwaterdeel);
        return ResponseEntity.created(new URI("/api/ondersteunendwaterdeels/" + ondersteunendwaterdeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ondersteunendwaterdeel.getId().toString()))
            .body(ondersteunendwaterdeel);
    }

    /**
     * {@code PUT  /ondersteunendwaterdeels/:id} : Updates an existing ondersteunendwaterdeel.
     *
     * @param id the id of the ondersteunendwaterdeel to save.
     * @param ondersteunendwaterdeel the ondersteunendwaterdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ondersteunendwaterdeel,
     * or with status {@code 400 (Bad Request)} if the ondersteunendwaterdeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ondersteunendwaterdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ondersteunendwaterdeel> updateOndersteunendwaterdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ondersteunendwaterdeel ondersteunendwaterdeel
    ) throws URISyntaxException {
        log.debug("REST request to update Ondersteunendwaterdeel : {}, {}", id, ondersteunendwaterdeel);
        if (ondersteunendwaterdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ondersteunendwaterdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ondersteunendwaterdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ondersteunendwaterdeel = ondersteunendwaterdeelRepository.save(ondersteunendwaterdeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ondersteunendwaterdeel.getId().toString()))
            .body(ondersteunendwaterdeel);
    }

    /**
     * {@code PATCH  /ondersteunendwaterdeels/:id} : Partial updates given fields of an existing ondersteunendwaterdeel, field will ignore if it is null
     *
     * @param id the id of the ondersteunendwaterdeel to save.
     * @param ondersteunendwaterdeel the ondersteunendwaterdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ondersteunendwaterdeel,
     * or with status {@code 400 (Bad Request)} if the ondersteunendwaterdeel is not valid,
     * or with status {@code 404 (Not Found)} if the ondersteunendwaterdeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the ondersteunendwaterdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ondersteunendwaterdeel> partialUpdateOndersteunendwaterdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ondersteunendwaterdeel ondersteunendwaterdeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ondersteunendwaterdeel partially : {}, {}", id, ondersteunendwaterdeel);
        if (ondersteunendwaterdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ondersteunendwaterdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ondersteunendwaterdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ondersteunendwaterdeel> result = ondersteunendwaterdeelRepository
            .findById(ondersteunendwaterdeel.getId())
            .map(existingOndersteunendwaterdeel -> {
                if (ondersteunendwaterdeel.getDatumbegingeldigheidondersteunendwaterdeel() != null) {
                    existingOndersteunendwaterdeel.setDatumbegingeldigheidondersteunendwaterdeel(
                        ondersteunendwaterdeel.getDatumbegingeldigheidondersteunendwaterdeel()
                    );
                }
                if (ondersteunendwaterdeel.getDatumeindegeldigheidondersteunendwaterdeel() != null) {
                    existingOndersteunendwaterdeel.setDatumeindegeldigheidondersteunendwaterdeel(
                        ondersteunendwaterdeel.getDatumeindegeldigheidondersteunendwaterdeel()
                    );
                }
                if (ondersteunendwaterdeel.getGeometrieondersteunendwaterdeel() != null) {
                    existingOndersteunendwaterdeel.setGeometrieondersteunendwaterdeel(
                        ondersteunendwaterdeel.getGeometrieondersteunendwaterdeel()
                    );
                }
                if (ondersteunendwaterdeel.getIdentificatieondersteunendwaterdeel() != null) {
                    existingOndersteunendwaterdeel.setIdentificatieondersteunendwaterdeel(
                        ondersteunendwaterdeel.getIdentificatieondersteunendwaterdeel()
                    );
                }
                if (ondersteunendwaterdeel.getPlustypeondersteunendwaterdeel() != null) {
                    existingOndersteunendwaterdeel.setPlustypeondersteunendwaterdeel(
                        ondersteunendwaterdeel.getPlustypeondersteunendwaterdeel()
                    );
                }
                if (ondersteunendwaterdeel.getRelatievehoogteliggingondersteunendwaterdeel() != null) {
                    existingOndersteunendwaterdeel.setRelatievehoogteliggingondersteunendwaterdeel(
                        ondersteunendwaterdeel.getRelatievehoogteliggingondersteunendwaterdeel()
                    );
                }
                if (ondersteunendwaterdeel.getStatusondersteunendwaterdeel() != null) {
                    existingOndersteunendwaterdeel.setStatusondersteunendwaterdeel(
                        ondersteunendwaterdeel.getStatusondersteunendwaterdeel()
                    );
                }
                if (ondersteunendwaterdeel.getTypeondersteunendwaterdeel() != null) {
                    existingOndersteunendwaterdeel.setTypeondersteunendwaterdeel(ondersteunendwaterdeel.getTypeondersteunendwaterdeel());
                }

                return existingOndersteunendwaterdeel;
            })
            .map(ondersteunendwaterdeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ondersteunendwaterdeel.getId().toString())
        );
    }

    /**
     * {@code GET  /ondersteunendwaterdeels} : get all the ondersteunendwaterdeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ondersteunendwaterdeels in body.
     */
    @GetMapping("")
    public List<Ondersteunendwaterdeel> getAllOndersteunendwaterdeels() {
        log.debug("REST request to get all Ondersteunendwaterdeels");
        return ondersteunendwaterdeelRepository.findAll();
    }

    /**
     * {@code GET  /ondersteunendwaterdeels/:id} : get the "id" ondersteunendwaterdeel.
     *
     * @param id the id of the ondersteunendwaterdeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ondersteunendwaterdeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ondersteunendwaterdeel> getOndersteunendwaterdeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Ondersteunendwaterdeel : {}", id);
        Optional<Ondersteunendwaterdeel> ondersteunendwaterdeel = ondersteunendwaterdeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ondersteunendwaterdeel);
    }

    /**
     * {@code DELETE  /ondersteunendwaterdeels/:id} : delete the "id" ondersteunendwaterdeel.
     *
     * @param id the id of the ondersteunendwaterdeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOndersteunendwaterdeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ondersteunendwaterdeel : {}", id);
        ondersteunendwaterdeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
