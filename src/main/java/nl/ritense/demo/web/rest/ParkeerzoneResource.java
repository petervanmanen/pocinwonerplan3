package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Parkeerzone;
import nl.ritense.demo.repository.ParkeerzoneRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Parkeerzone}.
 */
@RestController
@RequestMapping("/api/parkeerzones")
@Transactional
public class ParkeerzoneResource {

    private final Logger log = LoggerFactory.getLogger(ParkeerzoneResource.class);

    private static final String ENTITY_NAME = "parkeerzone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkeerzoneRepository parkeerzoneRepository;

    public ParkeerzoneResource(ParkeerzoneRepository parkeerzoneRepository) {
        this.parkeerzoneRepository = parkeerzoneRepository;
    }

    /**
     * {@code POST  /parkeerzones} : Create a new parkeerzone.
     *
     * @param parkeerzone the parkeerzone to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkeerzone, or with status {@code 400 (Bad Request)} if the parkeerzone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Parkeerzone> createParkeerzone(@Valid @RequestBody Parkeerzone parkeerzone) throws URISyntaxException {
        log.debug("REST request to save Parkeerzone : {}", parkeerzone);
        if (parkeerzone.getId() != null) {
            throw new BadRequestAlertException("A new parkeerzone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        parkeerzone = parkeerzoneRepository.save(parkeerzone);
        return ResponseEntity.created(new URI("/api/parkeerzones/" + parkeerzone.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, parkeerzone.getId().toString()))
            .body(parkeerzone);
    }

    /**
     * {@code PUT  /parkeerzones/:id} : Updates an existing parkeerzone.
     *
     * @param id the id of the parkeerzone to save.
     * @param parkeerzone the parkeerzone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeerzone,
     * or with status {@code 400 (Bad Request)} if the parkeerzone is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkeerzone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Parkeerzone> updateParkeerzone(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Parkeerzone parkeerzone
    ) throws URISyntaxException {
        log.debug("REST request to update Parkeerzone : {}, {}", id, parkeerzone);
        if (parkeerzone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeerzone.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeerzoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        parkeerzone = parkeerzoneRepository.save(parkeerzone);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeerzone.getId().toString()))
            .body(parkeerzone);
    }

    /**
     * {@code PATCH  /parkeerzones/:id} : Partial updates given fields of an existing parkeerzone, field will ignore if it is null
     *
     * @param id the id of the parkeerzone to save.
     * @param parkeerzone the parkeerzone to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeerzone,
     * or with status {@code 400 (Bad Request)} if the parkeerzone is not valid,
     * or with status {@code 404 (Not Found)} if the parkeerzone is not found,
     * or with status {@code 500 (Internal Server Error)} if the parkeerzone couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Parkeerzone> partialUpdateParkeerzone(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Parkeerzone parkeerzone
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parkeerzone partially : {}, {}", id, parkeerzone);
        if (parkeerzone.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeerzone.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeerzoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Parkeerzone> result = parkeerzoneRepository
            .findById(parkeerzone.getId())
            .map(existingParkeerzone -> {
                if (parkeerzone.getAantalparkeervlakken() != null) {
                    existingParkeerzone.setAantalparkeervlakken(parkeerzone.getAantalparkeervlakken());
                }
                if (parkeerzone.getAlleendagtarief() != null) {
                    existingParkeerzone.setAlleendagtarief(parkeerzone.getAlleendagtarief());
                }
                if (parkeerzone.getDagtarief() != null) {
                    existingParkeerzone.setDagtarief(parkeerzone.getDagtarief());
                }
                if (parkeerzone.getEindedag() != null) {
                    existingParkeerzone.setEindedag(parkeerzone.getEindedag());
                }
                if (parkeerzone.getEindtijd() != null) {
                    existingParkeerzone.setEindtijd(parkeerzone.getEindtijd());
                }
                if (parkeerzone.getGebruik() != null) {
                    existingParkeerzone.setGebruik(parkeerzone.getGebruik());
                }
                if (parkeerzone.getGeometrie() != null) {
                    existingParkeerzone.setGeometrie(parkeerzone.getGeometrie());
                }
                if (parkeerzone.getIpmcode() != null) {
                    existingParkeerzone.setIpmcode(parkeerzone.getIpmcode());
                }
                if (parkeerzone.getIpmnaam() != null) {
                    existingParkeerzone.setIpmnaam(parkeerzone.getIpmnaam());
                }
                if (parkeerzone.getNaam() != null) {
                    existingParkeerzone.setNaam(parkeerzone.getNaam());
                }
                if (parkeerzone.getParkeergarage() != null) {
                    existingParkeerzone.setParkeergarage(parkeerzone.getParkeergarage());
                }
                if (parkeerzone.getSectorcode() != null) {
                    existingParkeerzone.setSectorcode(parkeerzone.getSectorcode());
                }
                if (parkeerzone.getSoortcode() != null) {
                    existingParkeerzone.setSoortcode(parkeerzone.getSoortcode());
                }
                if (parkeerzone.getStartdag() != null) {
                    existingParkeerzone.setStartdag(parkeerzone.getStartdag());
                }
                if (parkeerzone.getStarttarief() != null) {
                    existingParkeerzone.setStarttarief(parkeerzone.getStarttarief());
                }
                if (parkeerzone.getStarttijd() != null) {
                    existingParkeerzone.setStarttijd(parkeerzone.getStarttijd());
                }
                if (parkeerzone.getTypecode() != null) {
                    existingParkeerzone.setTypecode(parkeerzone.getTypecode());
                }
                if (parkeerzone.getTypenaam() != null) {
                    existingParkeerzone.setTypenaam(parkeerzone.getTypenaam());
                }
                if (parkeerzone.getUurtarief() != null) {
                    existingParkeerzone.setUurtarief(parkeerzone.getUurtarief());
                }

                return existingParkeerzone;
            })
            .map(parkeerzoneRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeerzone.getId().toString())
        );
    }

    /**
     * {@code GET  /parkeerzones} : get all the parkeerzones.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkeerzones in body.
     */
    @GetMapping("")
    public List<Parkeerzone> getAllParkeerzones() {
        log.debug("REST request to get all Parkeerzones");
        return parkeerzoneRepository.findAll();
    }

    /**
     * {@code GET  /parkeerzones/:id} : get the "id" parkeerzone.
     *
     * @param id the id of the parkeerzone to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkeerzone, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Parkeerzone> getParkeerzone(@PathVariable("id") Long id) {
        log.debug("REST request to get Parkeerzone : {}", id);
        Optional<Parkeerzone> parkeerzone = parkeerzoneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parkeerzone);
    }

    /**
     * {@code DELETE  /parkeerzones/:id} : delete the "id" parkeerzone.
     *
     * @param id the id of the parkeerzone to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkeerzone(@PathVariable("id") Long id) {
        log.debug("REST request to delete Parkeerzone : {}", id);
        parkeerzoneRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
