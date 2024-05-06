package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Zaak;
import nl.ritense.demo.repository.ZaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Zaak}.
 */
@RestController
@RequestMapping("/api/zaaks")
@Transactional
public class ZaakResource {

    private final Logger log = LoggerFactory.getLogger(ZaakResource.class);

    private static final String ENTITY_NAME = "zaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZaakRepository zaakRepository;

    public ZaakResource(ZaakRepository zaakRepository) {
        this.zaakRepository = zaakRepository;
    }

    /**
     * {@code POST  /zaaks} : Create a new zaak.
     *
     * @param zaak the zaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zaak, or with status {@code 400 (Bad Request)} if the zaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Zaak> createZaak(@Valid @RequestBody Zaak zaak) throws URISyntaxException {
        log.debug("REST request to save Zaak : {}", zaak);
        if (zaak.getId() != null) {
            throw new BadRequestAlertException("A new zaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zaak = zaakRepository.save(zaak);
        return ResponseEntity.created(new URI("/api/zaaks/" + zaak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, zaak.getId().toString()))
            .body(zaak);
    }

    /**
     * {@code PUT  /zaaks/:id} : Updates an existing zaak.
     *
     * @param id the id of the zaak to save.
     * @param zaak the zaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zaak,
     * or with status {@code 400 (Bad Request)} if the zaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Zaak> updateZaak(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Zaak zaak)
        throws URISyntaxException {
        log.debug("REST request to update Zaak : {}, {}", id, zaak);
        if (zaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        zaak = zaakRepository.save(zaak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zaak.getId().toString()))
            .body(zaak);
    }

    /**
     * {@code PATCH  /zaaks/:id} : Partial updates given fields of an existing zaak, field will ignore if it is null
     *
     * @param id the id of the zaak to save.
     * @param zaak the zaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zaak,
     * or with status {@code 400 (Bad Request)} if the zaak is not valid,
     * or with status {@code 404 (Not Found)} if the zaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the zaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Zaak> partialUpdateZaak(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Zaak zaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Zaak partially : {}, {}", id, zaak);
        if (zaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Zaak> result = zaakRepository
            .findById(zaak.getId())
            .map(existingZaak -> {
                if (zaak.getEmpty() != null) {
                    existingZaak.setEmpty(zaak.getEmpty());
                }
                if (zaak.getArchiefnominatie() != null) {
                    existingZaak.setArchiefnominatie(zaak.getArchiefnominatie());
                }
                if (zaak.getDatumeinde() != null) {
                    existingZaak.setDatumeinde(zaak.getDatumeinde());
                }
                if (zaak.getDatumeindegepland() != null) {
                    existingZaak.setDatumeindegepland(zaak.getDatumeindegepland());
                }
                if (zaak.getDatumeindeuiterlijkeafdoening() != null) {
                    existingZaak.setDatumeindeuiterlijkeafdoening(zaak.getDatumeindeuiterlijkeafdoening());
                }
                if (zaak.getDatumlaatstebetaling() != null) {
                    existingZaak.setDatumlaatstebetaling(zaak.getDatumlaatstebetaling());
                }
                if (zaak.getDatumpublicatie() != null) {
                    existingZaak.setDatumpublicatie(zaak.getDatumpublicatie());
                }
                if (zaak.getDatumregistratie() != null) {
                    existingZaak.setDatumregistratie(zaak.getDatumregistratie());
                }
                if (zaak.getDatumstart() != null) {
                    existingZaak.setDatumstart(zaak.getDatumstart());
                }
                if (zaak.getDatumvernietigingdossier() != null) {
                    existingZaak.setDatumvernietigingdossier(zaak.getDatumvernietigingdossier());
                }
                if (zaak.getDocument() != null) {
                    existingZaak.setDocument(zaak.getDocument());
                }
                if (zaak.getDuurverlenging() != null) {
                    existingZaak.setDuurverlenging(zaak.getDuurverlenging());
                }
                if (zaak.getIndicatiebetaling() != null) {
                    existingZaak.setIndicatiebetaling(zaak.getIndicatiebetaling());
                }
                if (zaak.getIndicatiedeelzaken() != null) {
                    existingZaak.setIndicatiedeelzaken(zaak.getIndicatiedeelzaken());
                }
                if (zaak.getIndicatieopschorting() != null) {
                    existingZaak.setIndicatieopschorting(zaak.getIndicatieopschorting());
                }
                if (zaak.getLeges() != null) {
                    existingZaak.setLeges(zaak.getLeges());
                }
                if (zaak.getOmschrijving() != null) {
                    existingZaak.setOmschrijving(zaak.getOmschrijving());
                }
                if (zaak.getOmschrijvingresultaat() != null) {
                    existingZaak.setOmschrijvingresultaat(zaak.getOmschrijvingresultaat());
                }
                if (zaak.getRedenopschorting() != null) {
                    existingZaak.setRedenopschorting(zaak.getRedenopschorting());
                }
                if (zaak.getRedenverlenging() != null) {
                    existingZaak.setRedenverlenging(zaak.getRedenverlenging());
                }
                if (zaak.getToelichting() != null) {
                    existingZaak.setToelichting(zaak.getToelichting());
                }
                if (zaak.getToelichtingresultaat() != null) {
                    existingZaak.setToelichtingresultaat(zaak.getToelichtingresultaat());
                }
                if (zaak.getVertrouwelijkheid() != null) {
                    existingZaak.setVertrouwelijkheid(zaak.getVertrouwelijkheid());
                }
                if (zaak.getZaakidentificatie() != null) {
                    existingZaak.setZaakidentificatie(zaak.getZaakidentificatie());
                }
                if (zaak.getZaakniveau() != null) {
                    existingZaak.setZaakniveau(zaak.getZaakniveau());
                }

                return existingZaak;
            })
            .map(zaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zaak.getId().toString())
        );
    }

    /**
     * {@code GET  /zaaks} : get all the zaaks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zaaks in body.
     */
    @GetMapping("")
    public List<Zaak> getAllZaaks(
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("leidttotverzoek-is-null".equals(filter)) {
            log.debug("REST request to get all Zaaks where leidttotVerzoek is null");
            return StreamSupport.stream(zaakRepository.findAll().spliterator(), false)
                .filter(zaak -> zaak.getLeidttotVerzoek() == null)
                .toList();
        }

        if ("heeftsubsidie-is-null".equals(filter)) {
            log.debug("REST request to get all Zaaks where heeftSubsidie is null");
            return StreamSupport.stream(zaakRepository.findAll().spliterator(), false)
                .filter(zaak -> zaak.getHeeftSubsidie() == null)
                .toList();
        }

        if ("betreftaanbesteding-is-null".equals(filter)) {
            log.debug("REST request to get all Zaaks where betreftAanbesteding is null");
            return StreamSupport.stream(zaakRepository.findAll().spliterator(), false)
                .filter(zaak -> zaak.getBetreftAanbesteding() == null)
                .toList();
        }
        log.debug("REST request to get all Zaaks");
        if (eagerload) {
            return zaakRepository.findAllWithEagerRelationships();
        } else {
            return zaakRepository.findAll();
        }
    }

    /**
     * {@code GET  /zaaks/:id} : get the "id" zaak.
     *
     * @param id the id of the zaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Zaak> getZaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Zaak : {}", id);
        Optional<Zaak> zaak = zaakRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(zaak);
    }

    /**
     * {@code DELETE  /zaaks/:id} : delete the "id" zaak.
     *
     * @param id the id of the zaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Zaak : {}", id);
        zaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
