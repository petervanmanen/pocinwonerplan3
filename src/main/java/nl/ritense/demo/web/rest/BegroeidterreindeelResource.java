package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Begroeidterreindeel;
import nl.ritense.demo.repository.BegroeidterreindeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Begroeidterreindeel}.
 */
@RestController
@RequestMapping("/api/begroeidterreindeels")
@Transactional
public class BegroeidterreindeelResource {

    private final Logger log = LoggerFactory.getLogger(BegroeidterreindeelResource.class);

    private static final String ENTITY_NAME = "begroeidterreindeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BegroeidterreindeelRepository begroeidterreindeelRepository;

    public BegroeidterreindeelResource(BegroeidterreindeelRepository begroeidterreindeelRepository) {
        this.begroeidterreindeelRepository = begroeidterreindeelRepository;
    }

    /**
     * {@code POST  /begroeidterreindeels} : Create a new begroeidterreindeel.
     *
     * @param begroeidterreindeel the begroeidterreindeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new begroeidterreindeel, or with status {@code 400 (Bad Request)} if the begroeidterreindeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Begroeidterreindeel> createBegroeidterreindeel(@RequestBody Begroeidterreindeel begroeidterreindeel)
        throws URISyntaxException {
        log.debug("REST request to save Begroeidterreindeel : {}", begroeidterreindeel);
        if (begroeidterreindeel.getId() != null) {
            throw new BadRequestAlertException("A new begroeidterreindeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        begroeidterreindeel = begroeidterreindeelRepository.save(begroeidterreindeel);
        return ResponseEntity.created(new URI("/api/begroeidterreindeels/" + begroeidterreindeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, begroeidterreindeel.getId().toString()))
            .body(begroeidterreindeel);
    }

    /**
     * {@code PUT  /begroeidterreindeels/:id} : Updates an existing begroeidterreindeel.
     *
     * @param id the id of the begroeidterreindeel to save.
     * @param begroeidterreindeel the begroeidterreindeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated begroeidterreindeel,
     * or with status {@code 400 (Bad Request)} if the begroeidterreindeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the begroeidterreindeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Begroeidterreindeel> updateBegroeidterreindeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Begroeidterreindeel begroeidterreindeel
    ) throws URISyntaxException {
        log.debug("REST request to update Begroeidterreindeel : {}, {}", id, begroeidterreindeel);
        if (begroeidterreindeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, begroeidterreindeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!begroeidterreindeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        begroeidterreindeel = begroeidterreindeelRepository.save(begroeidterreindeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, begroeidterreindeel.getId().toString()))
            .body(begroeidterreindeel);
    }

    /**
     * {@code PATCH  /begroeidterreindeels/:id} : Partial updates given fields of an existing begroeidterreindeel, field will ignore if it is null
     *
     * @param id the id of the begroeidterreindeel to save.
     * @param begroeidterreindeel the begroeidterreindeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated begroeidterreindeel,
     * or with status {@code 400 (Bad Request)} if the begroeidterreindeel is not valid,
     * or with status {@code 404 (Not Found)} if the begroeidterreindeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the begroeidterreindeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Begroeidterreindeel> partialUpdateBegroeidterreindeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Begroeidterreindeel begroeidterreindeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Begroeidterreindeel partially : {}, {}", id, begroeidterreindeel);
        if (begroeidterreindeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, begroeidterreindeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!begroeidterreindeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Begroeidterreindeel> result = begroeidterreindeelRepository
            .findById(begroeidterreindeel.getId())
            .map(existingBegroeidterreindeel -> {
                if (begroeidterreindeel.getBegroeidterreindeeloptalud() != null) {
                    existingBegroeidterreindeel.setBegroeidterreindeeloptalud(begroeidterreindeel.getBegroeidterreindeeloptalud());
                }
                if (begroeidterreindeel.getDatumbegingeldigheidbegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setDatumbegingeldigheidbegroeidterreindeel(
                        begroeidterreindeel.getDatumbegingeldigheidbegroeidterreindeel()
                    );
                }
                if (begroeidterreindeel.getDatumeindegeldigheidbegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setDatumeindegeldigheidbegroeidterreindeel(
                        begroeidterreindeel.getDatumeindegeldigheidbegroeidterreindeel()
                    );
                }
                if (begroeidterreindeel.getFysiekvoorkomenbegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setFysiekvoorkomenbegroeidterreindeel(
                        begroeidterreindeel.getFysiekvoorkomenbegroeidterreindeel()
                    );
                }
                if (begroeidterreindeel.getGeometriebegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setGeometriebegroeidterreindeel(begroeidterreindeel.getGeometriebegroeidterreindeel());
                }
                if (begroeidterreindeel.getIdentificatiebegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setIdentificatiebegroeidterreindeel(
                        begroeidterreindeel.getIdentificatiebegroeidterreindeel()
                    );
                }
                if (begroeidterreindeel.getKruinlijngeometriebegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setKruinlijngeometriebegroeidterreindeel(
                        begroeidterreindeel.getKruinlijngeometriebegroeidterreindeel()
                    );
                }
                if (begroeidterreindeel.getLod0geometriebegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setLod0geometriebegroeidterreindeel(
                        begroeidterreindeel.getLod0geometriebegroeidterreindeel()
                    );
                }
                if (begroeidterreindeel.getPlusfysiekvoorkomenbegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setPlusfysiekvoorkomenbegroeidterreindeel(
                        begroeidterreindeel.getPlusfysiekvoorkomenbegroeidterreindeel()
                    );
                }
                if (begroeidterreindeel.getRelatievehoogteliggingbegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setRelatievehoogteliggingbegroeidterreindeel(
                        begroeidterreindeel.getRelatievehoogteliggingbegroeidterreindeel()
                    );
                }
                if (begroeidterreindeel.getStatusbegroeidterreindeel() != null) {
                    existingBegroeidterreindeel.setStatusbegroeidterreindeel(begroeidterreindeel.getStatusbegroeidterreindeel());
                }

                return existingBegroeidterreindeel;
            })
            .map(begroeidterreindeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, begroeidterreindeel.getId().toString())
        );
    }

    /**
     * {@code GET  /begroeidterreindeels} : get all the begroeidterreindeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of begroeidterreindeels in body.
     */
    @GetMapping("")
    public List<Begroeidterreindeel> getAllBegroeidterreindeels() {
        log.debug("REST request to get all Begroeidterreindeels");
        return begroeidterreindeelRepository.findAll();
    }

    /**
     * {@code GET  /begroeidterreindeels/:id} : get the "id" begroeidterreindeel.
     *
     * @param id the id of the begroeidterreindeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the begroeidterreindeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Begroeidterreindeel> getBegroeidterreindeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Begroeidterreindeel : {}", id);
        Optional<Begroeidterreindeel> begroeidterreindeel = begroeidterreindeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(begroeidterreindeel);
    }

    /**
     * {@code DELETE  /begroeidterreindeels/:id} : delete the "id" begroeidterreindeel.
     *
     * @param id the id of the begroeidterreindeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBegroeidterreindeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Begroeidterreindeel : {}", id);
        begroeidterreindeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
