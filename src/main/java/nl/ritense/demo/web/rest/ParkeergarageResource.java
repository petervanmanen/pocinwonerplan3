package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Parkeergarage;
import nl.ritense.demo.repository.ParkeergarageRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Parkeergarage}.
 */
@RestController
@RequestMapping("/api/parkeergarages")
@Transactional
public class ParkeergarageResource {

    private final Logger log = LoggerFactory.getLogger(ParkeergarageResource.class);

    private static final String ENTITY_NAME = "parkeergarage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkeergarageRepository parkeergarageRepository;

    public ParkeergarageResource(ParkeergarageRepository parkeergarageRepository) {
        this.parkeergarageRepository = parkeergarageRepository;
    }

    /**
     * {@code POST  /parkeergarages} : Create a new parkeergarage.
     *
     * @param parkeergarage the parkeergarage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkeergarage, or with status {@code 400 (Bad Request)} if the parkeergarage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Parkeergarage> createParkeergarage(@RequestBody Parkeergarage parkeergarage) throws URISyntaxException {
        log.debug("REST request to save Parkeergarage : {}", parkeergarage);
        if (parkeergarage.getId() != null) {
            throw new BadRequestAlertException("A new parkeergarage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        parkeergarage = parkeergarageRepository.save(parkeergarage);
        return ResponseEntity.created(new URI("/api/parkeergarages/" + parkeergarage.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, parkeergarage.getId().toString()))
            .body(parkeergarage);
    }

    /**
     * {@code GET  /parkeergarages} : get all the parkeergarages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkeergarages in body.
     */
    @GetMapping("")
    public List<Parkeergarage> getAllParkeergarages() {
        log.debug("REST request to get all Parkeergarages");
        return parkeergarageRepository.findAll();
    }

    /**
     * {@code GET  /parkeergarages/:id} : get the "id" parkeergarage.
     *
     * @param id the id of the parkeergarage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkeergarage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Parkeergarage> getParkeergarage(@PathVariable("id") Long id) {
        log.debug("REST request to get Parkeergarage : {}", id);
        Optional<Parkeergarage> parkeergarage = parkeergarageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parkeergarage);
    }

    /**
     * {@code DELETE  /parkeergarages/:id} : delete the "id" parkeergarage.
     *
     * @param id the id of the parkeergarage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkeergarage(@PathVariable("id") Long id) {
        log.debug("REST request to delete Parkeergarage : {}", id);
        parkeergarageRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
