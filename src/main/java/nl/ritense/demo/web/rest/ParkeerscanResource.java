package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Parkeerscan;
import nl.ritense.demo.repository.ParkeerscanRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Parkeerscan}.
 */
@RestController
@RequestMapping("/api/parkeerscans")
@Transactional
public class ParkeerscanResource {

    private final Logger log = LoggerFactory.getLogger(ParkeerscanResource.class);

    private static final String ENTITY_NAME = "parkeerscan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkeerscanRepository parkeerscanRepository;

    public ParkeerscanResource(ParkeerscanRepository parkeerscanRepository) {
        this.parkeerscanRepository = parkeerscanRepository;
    }

    /**
     * {@code POST  /parkeerscans} : Create a new parkeerscan.
     *
     * @param parkeerscan the parkeerscan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkeerscan, or with status {@code 400 (Bad Request)} if the parkeerscan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Parkeerscan> createParkeerscan(@RequestBody Parkeerscan parkeerscan) throws URISyntaxException {
        log.debug("REST request to save Parkeerscan : {}", parkeerscan);
        if (parkeerscan.getId() != null) {
            throw new BadRequestAlertException("A new parkeerscan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        parkeerscan = parkeerscanRepository.save(parkeerscan);
        return ResponseEntity.created(new URI("/api/parkeerscans/" + parkeerscan.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, parkeerscan.getId().toString()))
            .body(parkeerscan);
    }

    /**
     * {@code PUT  /parkeerscans/:id} : Updates an existing parkeerscan.
     *
     * @param id the id of the parkeerscan to save.
     * @param parkeerscan the parkeerscan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeerscan,
     * or with status {@code 400 (Bad Request)} if the parkeerscan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkeerscan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Parkeerscan> updateParkeerscan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parkeerscan parkeerscan
    ) throws URISyntaxException {
        log.debug("REST request to update Parkeerscan : {}, {}", id, parkeerscan);
        if (parkeerscan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeerscan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeerscanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        parkeerscan = parkeerscanRepository.save(parkeerscan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeerscan.getId().toString()))
            .body(parkeerscan);
    }

    /**
     * {@code PATCH  /parkeerscans/:id} : Partial updates given fields of an existing parkeerscan, field will ignore if it is null
     *
     * @param id the id of the parkeerscan to save.
     * @param parkeerscan the parkeerscan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeerscan,
     * or with status {@code 400 (Bad Request)} if the parkeerscan is not valid,
     * or with status {@code 404 (Not Found)} if the parkeerscan is not found,
     * or with status {@code 500 (Internal Server Error)} if the parkeerscan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Parkeerscan> partialUpdateParkeerscan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parkeerscan parkeerscan
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parkeerscan partially : {}, {}", id, parkeerscan);
        if (parkeerscan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeerscan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeerscanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Parkeerscan> result = parkeerscanRepository
            .findById(parkeerscan.getId())
            .map(existingParkeerscan -> {
                if (parkeerscan.getCodegebruiker() != null) {
                    existingParkeerscan.setCodegebruiker(parkeerscan.getCodegebruiker());
                }
                if (parkeerscan.getCodescanvoertuig() != null) {
                    existingParkeerscan.setCodescanvoertuig(parkeerscan.getCodescanvoertuig());
                }
                if (parkeerscan.getCoordinaten() != null) {
                    existingParkeerscan.setCoordinaten(parkeerscan.getCoordinaten());
                }
                if (parkeerscan.getFoto() != null) {
                    existingParkeerscan.setFoto(parkeerscan.getFoto());
                }
                if (parkeerscan.getKenteken() != null) {
                    existingParkeerscan.setKenteken(parkeerscan.getKenteken());
                }
                if (parkeerscan.getParkeerrecht() != null) {
                    existingParkeerscan.setParkeerrecht(parkeerscan.getParkeerrecht());
                }
                if (parkeerscan.getTijdstip() != null) {
                    existingParkeerscan.setTijdstip(parkeerscan.getTijdstip());
                }
                if (parkeerscan.getTransactieid() != null) {
                    existingParkeerscan.setTransactieid(parkeerscan.getTransactieid());
                }

                return existingParkeerscan;
            })
            .map(parkeerscanRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeerscan.getId().toString())
        );
    }

    /**
     * {@code GET  /parkeerscans} : get all the parkeerscans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkeerscans in body.
     */
    @GetMapping("")
    public List<Parkeerscan> getAllParkeerscans() {
        log.debug("REST request to get all Parkeerscans");
        return parkeerscanRepository.findAll();
    }

    /**
     * {@code GET  /parkeerscans/:id} : get the "id" parkeerscan.
     *
     * @param id the id of the parkeerscan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkeerscan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Parkeerscan> getParkeerscan(@PathVariable("id") Long id) {
        log.debug("REST request to get Parkeerscan : {}", id);
        Optional<Parkeerscan> parkeerscan = parkeerscanRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parkeerscan);
    }

    /**
     * {@code DELETE  /parkeerscans/:id} : delete the "id" parkeerscan.
     *
     * @param id the id of the parkeerscan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkeerscan(@PathVariable("id") Long id) {
        log.debug("REST request to delete Parkeerscan : {}", id);
        parkeerscanRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
