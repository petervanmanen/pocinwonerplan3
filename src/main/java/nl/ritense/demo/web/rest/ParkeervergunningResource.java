package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Parkeervergunning;
import nl.ritense.demo.repository.ParkeervergunningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Parkeervergunning}.
 */
@RestController
@RequestMapping("/api/parkeervergunnings")
@Transactional
public class ParkeervergunningResource {

    private final Logger log = LoggerFactory.getLogger(ParkeervergunningResource.class);

    private static final String ENTITY_NAME = "parkeervergunning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkeervergunningRepository parkeervergunningRepository;

    public ParkeervergunningResource(ParkeervergunningRepository parkeervergunningRepository) {
        this.parkeervergunningRepository = parkeervergunningRepository;
    }

    /**
     * {@code POST  /parkeervergunnings} : Create a new parkeervergunning.
     *
     * @param parkeervergunning the parkeervergunning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkeervergunning, or with status {@code 400 (Bad Request)} if the parkeervergunning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Parkeervergunning> createParkeervergunning(@RequestBody Parkeervergunning parkeervergunning)
        throws URISyntaxException {
        log.debug("REST request to save Parkeervergunning : {}", parkeervergunning);
        if (parkeervergunning.getId() != null) {
            throw new BadRequestAlertException("A new parkeervergunning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        parkeervergunning = parkeervergunningRepository.save(parkeervergunning);
        return ResponseEntity.created(new URI("/api/parkeervergunnings/" + parkeervergunning.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, parkeervergunning.getId().toString()))
            .body(parkeervergunning);
    }

    /**
     * {@code PUT  /parkeervergunnings/:id} : Updates an existing parkeervergunning.
     *
     * @param id the id of the parkeervergunning to save.
     * @param parkeervergunning the parkeervergunning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeervergunning,
     * or with status {@code 400 (Bad Request)} if the parkeervergunning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkeervergunning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Parkeervergunning> updateParkeervergunning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parkeervergunning parkeervergunning
    ) throws URISyntaxException {
        log.debug("REST request to update Parkeervergunning : {}, {}", id, parkeervergunning);
        if (parkeervergunning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeervergunning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeervergunningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        parkeervergunning = parkeervergunningRepository.save(parkeervergunning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeervergunning.getId().toString()))
            .body(parkeervergunning);
    }

    /**
     * {@code PATCH  /parkeervergunnings/:id} : Partial updates given fields of an existing parkeervergunning, field will ignore if it is null
     *
     * @param id the id of the parkeervergunning to save.
     * @param parkeervergunning the parkeervergunning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeervergunning,
     * or with status {@code 400 (Bad Request)} if the parkeervergunning is not valid,
     * or with status {@code 404 (Not Found)} if the parkeervergunning is not found,
     * or with status {@code 500 (Internal Server Error)} if the parkeervergunning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Parkeervergunning> partialUpdateParkeervergunning(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parkeervergunning parkeervergunning
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parkeervergunning partially : {}, {}", id, parkeervergunning);
        if (parkeervergunning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeervergunning.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeervergunningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Parkeervergunning> result = parkeervergunningRepository
            .findById(parkeervergunning.getId())
            .map(existingParkeervergunning -> {
                if (parkeervergunning.getDatumeindegeldigheid() != null) {
                    existingParkeervergunning.setDatumeindegeldigheid(parkeervergunning.getDatumeindegeldigheid());
                }
                if (parkeervergunning.getDatumreservering() != null) {
                    existingParkeervergunning.setDatumreservering(parkeervergunning.getDatumreservering());
                }
                if (parkeervergunning.getDatumstart() != null) {
                    existingParkeervergunning.setDatumstart(parkeervergunning.getDatumstart());
                }
                if (parkeervergunning.getKenteken() != null) {
                    existingParkeervergunning.setKenteken(parkeervergunning.getKenteken());
                }
                if (parkeervergunning.getMinutenafgeschreven() != null) {
                    existingParkeervergunning.setMinutenafgeschreven(parkeervergunning.getMinutenafgeschreven());
                }
                if (parkeervergunning.getMinutengeldig() != null) {
                    existingParkeervergunning.setMinutengeldig(parkeervergunning.getMinutengeldig());
                }
                if (parkeervergunning.getMinutenresterend() != null) {
                    existingParkeervergunning.setMinutenresterend(parkeervergunning.getMinutenresterend());
                }
                if (parkeervergunning.getNummer() != null) {
                    existingParkeervergunning.setNummer(parkeervergunning.getNummer());
                }
                if (parkeervergunning.getType() != null) {
                    existingParkeervergunning.setType(parkeervergunning.getType());
                }

                return existingParkeervergunning;
            })
            .map(parkeervergunningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeervergunning.getId().toString())
        );
    }

    /**
     * {@code GET  /parkeervergunnings} : get all the parkeervergunnings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkeervergunnings in body.
     */
    @GetMapping("")
    public List<Parkeervergunning> getAllParkeervergunnings() {
        log.debug("REST request to get all Parkeervergunnings");
        return parkeervergunningRepository.findAll();
    }

    /**
     * {@code GET  /parkeervergunnings/:id} : get the "id" parkeervergunning.
     *
     * @param id the id of the parkeervergunning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkeervergunning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Parkeervergunning> getParkeervergunning(@PathVariable("id") Long id) {
        log.debug("REST request to get Parkeervergunning : {}", id);
        Optional<Parkeervergunning> parkeervergunning = parkeervergunningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parkeervergunning);
    }

    /**
     * {@code DELETE  /parkeervergunnings/:id} : delete the "id" parkeervergunning.
     *
     * @param id the id of the parkeervergunning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkeervergunning(@PathVariable("id") Long id) {
        log.debug("REST request to delete Parkeervergunning : {}", id);
        parkeervergunningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
