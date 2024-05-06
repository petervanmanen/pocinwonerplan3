package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Onbegroeidterreindeel;
import nl.ritense.demo.repository.OnbegroeidterreindeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onbegroeidterreindeel}.
 */
@RestController
@RequestMapping("/api/onbegroeidterreindeels")
@Transactional
public class OnbegroeidterreindeelResource {

    private final Logger log = LoggerFactory.getLogger(OnbegroeidterreindeelResource.class);

    private static final String ENTITY_NAME = "onbegroeidterreindeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnbegroeidterreindeelRepository onbegroeidterreindeelRepository;

    public OnbegroeidterreindeelResource(OnbegroeidterreindeelRepository onbegroeidterreindeelRepository) {
        this.onbegroeidterreindeelRepository = onbegroeidterreindeelRepository;
    }

    /**
     * {@code POST  /onbegroeidterreindeels} : Create a new onbegroeidterreindeel.
     *
     * @param onbegroeidterreindeel the onbegroeidterreindeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onbegroeidterreindeel, or with status {@code 400 (Bad Request)} if the onbegroeidterreindeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onbegroeidterreindeel> createOnbegroeidterreindeel(@RequestBody Onbegroeidterreindeel onbegroeidterreindeel)
        throws URISyntaxException {
        log.debug("REST request to save Onbegroeidterreindeel : {}", onbegroeidterreindeel);
        if (onbegroeidterreindeel.getId() != null) {
            throw new BadRequestAlertException("A new onbegroeidterreindeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onbegroeidterreindeel = onbegroeidterreindeelRepository.save(onbegroeidterreindeel);
        return ResponseEntity.created(new URI("/api/onbegroeidterreindeels/" + onbegroeidterreindeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onbegroeidterreindeel.getId().toString()))
            .body(onbegroeidterreindeel);
    }

    /**
     * {@code PUT  /onbegroeidterreindeels/:id} : Updates an existing onbegroeidterreindeel.
     *
     * @param id the id of the onbegroeidterreindeel to save.
     * @param onbegroeidterreindeel the onbegroeidterreindeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onbegroeidterreindeel,
     * or with status {@code 400 (Bad Request)} if the onbegroeidterreindeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the onbegroeidterreindeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Onbegroeidterreindeel> updateOnbegroeidterreindeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Onbegroeidterreindeel onbegroeidterreindeel
    ) throws URISyntaxException {
        log.debug("REST request to update Onbegroeidterreindeel : {}, {}", id, onbegroeidterreindeel);
        if (onbegroeidterreindeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onbegroeidterreindeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onbegroeidterreindeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        onbegroeidterreindeel = onbegroeidterreindeelRepository.save(onbegroeidterreindeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onbegroeidterreindeel.getId().toString()))
            .body(onbegroeidterreindeel);
    }

    /**
     * {@code PATCH  /onbegroeidterreindeels/:id} : Partial updates given fields of an existing onbegroeidterreindeel, field will ignore if it is null
     *
     * @param id the id of the onbegroeidterreindeel to save.
     * @param onbegroeidterreindeel the onbegroeidterreindeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onbegroeidterreindeel,
     * or with status {@code 400 (Bad Request)} if the onbegroeidterreindeel is not valid,
     * or with status {@code 404 (Not Found)} if the onbegroeidterreindeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the onbegroeidterreindeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Onbegroeidterreindeel> partialUpdateOnbegroeidterreindeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Onbegroeidterreindeel onbegroeidterreindeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Onbegroeidterreindeel partially : {}, {}", id, onbegroeidterreindeel);
        if (onbegroeidterreindeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onbegroeidterreindeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onbegroeidterreindeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Onbegroeidterreindeel> result = onbegroeidterreindeelRepository
            .findById(onbegroeidterreindeel.getId())
            .map(existingOnbegroeidterreindeel -> {
                if (onbegroeidterreindeel.getDatumbegingeldigheidonbegroeidterreindeel() != null) {
                    existingOnbegroeidterreindeel.setDatumbegingeldigheidonbegroeidterreindeel(
                        onbegroeidterreindeel.getDatumbegingeldigheidonbegroeidterreindeel()
                    );
                }
                if (onbegroeidterreindeel.getDatumeindegeldigheidonbegroeidterreindeel() != null) {
                    existingOnbegroeidterreindeel.setDatumeindegeldigheidonbegroeidterreindeel(
                        onbegroeidterreindeel.getDatumeindegeldigheidonbegroeidterreindeel()
                    );
                }
                if (onbegroeidterreindeel.getFysiekvoorkomenonbegroeidterreindeel() != null) {
                    existingOnbegroeidterreindeel.setFysiekvoorkomenonbegroeidterreindeel(
                        onbegroeidterreindeel.getFysiekvoorkomenonbegroeidterreindeel()
                    );
                }
                if (onbegroeidterreindeel.getGeometrieonbegroeidterreindeel() != null) {
                    existingOnbegroeidterreindeel.setGeometrieonbegroeidterreindeel(
                        onbegroeidterreindeel.getGeometrieonbegroeidterreindeel()
                    );
                }
                if (onbegroeidterreindeel.getIdentificatieonbegroeidterreindeel() != null) {
                    existingOnbegroeidterreindeel.setIdentificatieonbegroeidterreindeel(
                        onbegroeidterreindeel.getIdentificatieonbegroeidterreindeel()
                    );
                }
                if (onbegroeidterreindeel.getKruinlijngeometrieonbegroeidterreindeel() != null) {
                    existingOnbegroeidterreindeel.setKruinlijngeometrieonbegroeidterreindeel(
                        onbegroeidterreindeel.getKruinlijngeometrieonbegroeidterreindeel()
                    );
                }
                if (onbegroeidterreindeel.getOnbegroeidterreindeeloptalud() != null) {
                    existingOnbegroeidterreindeel.setOnbegroeidterreindeeloptalud(onbegroeidterreindeel.getOnbegroeidterreindeeloptalud());
                }
                if (onbegroeidterreindeel.getPlusfysiekvoorkomenonbegroeidterreindeel() != null) {
                    existingOnbegroeidterreindeel.setPlusfysiekvoorkomenonbegroeidterreindeel(
                        onbegroeidterreindeel.getPlusfysiekvoorkomenonbegroeidterreindeel()
                    );
                }
                if (onbegroeidterreindeel.getRelatievehoogteliggingonbegroeidterreindeel() != null) {
                    existingOnbegroeidterreindeel.setRelatievehoogteliggingonbegroeidterreindeel(
                        onbegroeidterreindeel.getRelatievehoogteliggingonbegroeidterreindeel()
                    );
                }
                if (onbegroeidterreindeel.getStatusonbegroeidterreindeel() != null) {
                    existingOnbegroeidterreindeel.setStatusonbegroeidterreindeel(onbegroeidterreindeel.getStatusonbegroeidterreindeel());
                }

                return existingOnbegroeidterreindeel;
            })
            .map(onbegroeidterreindeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onbegroeidterreindeel.getId().toString())
        );
    }

    /**
     * {@code GET  /onbegroeidterreindeels} : get all the onbegroeidterreindeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onbegroeidterreindeels in body.
     */
    @GetMapping("")
    public List<Onbegroeidterreindeel> getAllOnbegroeidterreindeels() {
        log.debug("REST request to get all Onbegroeidterreindeels");
        return onbegroeidterreindeelRepository.findAll();
    }

    /**
     * {@code GET  /onbegroeidterreindeels/:id} : get the "id" onbegroeidterreindeel.
     *
     * @param id the id of the onbegroeidterreindeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onbegroeidterreindeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onbegroeidterreindeel> getOnbegroeidterreindeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Onbegroeidterreindeel : {}", id);
        Optional<Onbegroeidterreindeel> onbegroeidterreindeel = onbegroeidterreindeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onbegroeidterreindeel);
    }

    /**
     * {@code DELETE  /onbegroeidterreindeels/:id} : delete the "id" onbegroeidterreindeel.
     *
     * @param id the id of the onbegroeidterreindeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnbegroeidterreindeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onbegroeidterreindeel : {}", id);
        onbegroeidterreindeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
