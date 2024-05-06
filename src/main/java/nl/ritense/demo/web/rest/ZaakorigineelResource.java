package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Zaakorigineel;
import nl.ritense.demo.repository.ZaakorigineelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Zaakorigineel}.
 */
@RestController
@RequestMapping("/api/zaakorigineels")
@Transactional
public class ZaakorigineelResource {

    private final Logger log = LoggerFactory.getLogger(ZaakorigineelResource.class);

    private static final String ENTITY_NAME = "zaakorigineel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZaakorigineelRepository zaakorigineelRepository;

    public ZaakorigineelResource(ZaakorigineelRepository zaakorigineelRepository) {
        this.zaakorigineelRepository = zaakorigineelRepository;
    }

    /**
     * {@code POST  /zaakorigineels} : Create a new zaakorigineel.
     *
     * @param zaakorigineel the zaakorigineel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zaakorigineel, or with status {@code 400 (Bad Request)} if the zaakorigineel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Zaakorigineel> createZaakorigineel(@RequestBody Zaakorigineel zaakorigineel) throws URISyntaxException {
        log.debug("REST request to save Zaakorigineel : {}", zaakorigineel);
        if (zaakorigineel.getId() != null) {
            throw new BadRequestAlertException("A new zaakorigineel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zaakorigineel = zaakorigineelRepository.save(zaakorigineel);
        return ResponseEntity.created(new URI("/api/zaakorigineels/" + zaakorigineel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, zaakorigineel.getId().toString()))
            .body(zaakorigineel);
    }

    /**
     * {@code PUT  /zaakorigineels/:id} : Updates an existing zaakorigineel.
     *
     * @param id the id of the zaakorigineel to save.
     * @param zaakorigineel the zaakorigineel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zaakorigineel,
     * or with status {@code 400 (Bad Request)} if the zaakorigineel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zaakorigineel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Zaakorigineel> updateZaakorigineel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zaakorigineel zaakorigineel
    ) throws URISyntaxException {
        log.debug("REST request to update Zaakorigineel : {}, {}", id, zaakorigineel);
        if (zaakorigineel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zaakorigineel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zaakorigineelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        zaakorigineel = zaakorigineelRepository.save(zaakorigineel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zaakorigineel.getId().toString()))
            .body(zaakorigineel);
    }

    /**
     * {@code PATCH  /zaakorigineels/:id} : Partial updates given fields of an existing zaakorigineel, field will ignore if it is null
     *
     * @param id the id of the zaakorigineel to save.
     * @param zaakorigineel the zaakorigineel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zaakorigineel,
     * or with status {@code 400 (Bad Request)} if the zaakorigineel is not valid,
     * or with status {@code 404 (Not Found)} if the zaakorigineel is not found,
     * or with status {@code 500 (Internal Server Error)} if the zaakorigineel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Zaakorigineel> partialUpdateZaakorigineel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Zaakorigineel zaakorigineel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Zaakorigineel partially : {}, {}", id, zaakorigineel);
        if (zaakorigineel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zaakorigineel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zaakorigineelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Zaakorigineel> result = zaakorigineelRepository
            .findById(zaakorigineel.getId())
            .map(existingZaakorigineel -> {
                if (zaakorigineel.getAnderzaakobject() != null) {
                    existingZaakorigineel.setAnderzaakobject(zaakorigineel.getAnderzaakobject());
                }
                if (zaakorigineel.getArchiefnominatie() != null) {
                    existingZaakorigineel.setArchiefnominatie(zaakorigineel.getArchiefnominatie());
                }
                if (zaakorigineel.getDatumeinde() != null) {
                    existingZaakorigineel.setDatumeinde(zaakorigineel.getDatumeinde());
                }
                if (zaakorigineel.getDatumeindegepland() != null) {
                    existingZaakorigineel.setDatumeindegepland(zaakorigineel.getDatumeindegepland());
                }
                if (zaakorigineel.getDatumeindeuiterlijkeafdoening() != null) {
                    existingZaakorigineel.setDatumeindeuiterlijkeafdoening(zaakorigineel.getDatumeindeuiterlijkeafdoening());
                }
                if (zaakorigineel.getDatumlaatstebetaling() != null) {
                    existingZaakorigineel.setDatumlaatstebetaling(zaakorigineel.getDatumlaatstebetaling());
                }
                if (zaakorigineel.getDatumpublicatie() != null) {
                    existingZaakorigineel.setDatumpublicatie(zaakorigineel.getDatumpublicatie());
                }
                if (zaakorigineel.getDatumregistratie() != null) {
                    existingZaakorigineel.setDatumregistratie(zaakorigineel.getDatumregistratie());
                }
                if (zaakorigineel.getDatumstart() != null) {
                    existingZaakorigineel.setDatumstart(zaakorigineel.getDatumstart());
                }
                if (zaakorigineel.getDatumvernietigingdossier() != null) {
                    existingZaakorigineel.setDatumvernietigingdossier(zaakorigineel.getDatumvernietigingdossier());
                }
                if (zaakorigineel.getIndicatiebetaling() != null) {
                    existingZaakorigineel.setIndicatiebetaling(zaakorigineel.getIndicatiebetaling());
                }
                if (zaakorigineel.getIndicatiedeelzaken() != null) {
                    existingZaakorigineel.setIndicatiedeelzaken(zaakorigineel.getIndicatiedeelzaken());
                }
                if (zaakorigineel.getKenmerk() != null) {
                    existingZaakorigineel.setKenmerk(zaakorigineel.getKenmerk());
                }
                if (zaakorigineel.getOmschrijving() != null) {
                    existingZaakorigineel.setOmschrijving(zaakorigineel.getOmschrijving());
                }
                if (zaakorigineel.getOmschrijvingresultaat() != null) {
                    existingZaakorigineel.setOmschrijvingresultaat(zaakorigineel.getOmschrijvingresultaat());
                }
                if (zaakorigineel.getOpschorting() != null) {
                    existingZaakorigineel.setOpschorting(zaakorigineel.getOpschorting());
                }
                if (zaakorigineel.getToelichting() != null) {
                    existingZaakorigineel.setToelichting(zaakorigineel.getToelichting());
                }
                if (zaakorigineel.getToelichtingresultaat() != null) {
                    existingZaakorigineel.setToelichtingresultaat(zaakorigineel.getToelichtingresultaat());
                }
                if (zaakorigineel.getVerlenging() != null) {
                    existingZaakorigineel.setVerlenging(zaakorigineel.getVerlenging());
                }
                if (zaakorigineel.getZaakidentificatie() != null) {
                    existingZaakorigineel.setZaakidentificatie(zaakorigineel.getZaakidentificatie());
                }
                if (zaakorigineel.getZaakniveau() != null) {
                    existingZaakorigineel.setZaakniveau(zaakorigineel.getZaakniveau());
                }

                return existingZaakorigineel;
            })
            .map(zaakorigineelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zaakorigineel.getId().toString())
        );
    }

    /**
     * {@code GET  /zaakorigineels} : get all the zaakorigineels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zaakorigineels in body.
     */
    @GetMapping("")
    public List<Zaakorigineel> getAllZaakorigineels() {
        log.debug("REST request to get all Zaakorigineels");
        return zaakorigineelRepository.findAll();
    }

    /**
     * {@code GET  /zaakorigineels/:id} : get the "id" zaakorigineel.
     *
     * @param id the id of the zaakorigineel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zaakorigineel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Zaakorigineel> getZaakorigineel(@PathVariable("id") Long id) {
        log.debug("REST request to get Zaakorigineel : {}", id);
        Optional<Zaakorigineel> zaakorigineel = zaakorigineelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zaakorigineel);
    }

    /**
     * {@code DELETE  /zaakorigineels/:id} : delete the "id" zaakorigineel.
     *
     * @param id the id of the zaakorigineel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZaakorigineel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Zaakorigineel : {}", id);
        zaakorigineelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
