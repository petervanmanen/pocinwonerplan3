package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Parkeerrecht;
import nl.ritense.demo.repository.ParkeerrechtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Parkeerrecht}.
 */
@RestController
@RequestMapping("/api/parkeerrechts")
@Transactional
public class ParkeerrechtResource {

    private final Logger log = LoggerFactory.getLogger(ParkeerrechtResource.class);

    private static final String ENTITY_NAME = "parkeerrecht";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkeerrechtRepository parkeerrechtRepository;

    public ParkeerrechtResource(ParkeerrechtRepository parkeerrechtRepository) {
        this.parkeerrechtRepository = parkeerrechtRepository;
    }

    /**
     * {@code POST  /parkeerrechts} : Create a new parkeerrecht.
     *
     * @param parkeerrecht the parkeerrecht to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkeerrecht, or with status {@code 400 (Bad Request)} if the parkeerrecht has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Parkeerrecht> createParkeerrecht(@RequestBody Parkeerrecht parkeerrecht) throws URISyntaxException {
        log.debug("REST request to save Parkeerrecht : {}", parkeerrecht);
        if (parkeerrecht.getId() != null) {
            throw new BadRequestAlertException("A new parkeerrecht cannot already have an ID", ENTITY_NAME, "idexists");
        }
        parkeerrecht = parkeerrechtRepository.save(parkeerrecht);
        return ResponseEntity.created(new URI("/api/parkeerrechts/" + parkeerrecht.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, parkeerrecht.getId().toString()))
            .body(parkeerrecht);
    }

    /**
     * {@code PUT  /parkeerrechts/:id} : Updates an existing parkeerrecht.
     *
     * @param id the id of the parkeerrecht to save.
     * @param parkeerrecht the parkeerrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeerrecht,
     * or with status {@code 400 (Bad Request)} if the parkeerrecht is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkeerrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Parkeerrecht> updateParkeerrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parkeerrecht parkeerrecht
    ) throws URISyntaxException {
        log.debug("REST request to update Parkeerrecht : {}, {}", id, parkeerrecht);
        if (parkeerrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeerrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeerrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        parkeerrecht = parkeerrechtRepository.save(parkeerrecht);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeerrecht.getId().toString()))
            .body(parkeerrecht);
    }

    /**
     * {@code PATCH  /parkeerrechts/:id} : Partial updates given fields of an existing parkeerrecht, field will ignore if it is null
     *
     * @param id the id of the parkeerrecht to save.
     * @param parkeerrecht the parkeerrecht to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeerrecht,
     * or with status {@code 400 (Bad Request)} if the parkeerrecht is not valid,
     * or with status {@code 404 (Not Found)} if the parkeerrecht is not found,
     * or with status {@code 500 (Internal Server Error)} if the parkeerrecht couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Parkeerrecht> partialUpdateParkeerrecht(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parkeerrecht parkeerrecht
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parkeerrecht partially : {}, {}", id, parkeerrecht);
        if (parkeerrecht.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeerrecht.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeerrechtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Parkeerrecht> result = parkeerrechtRepository
            .findById(parkeerrecht.getId())
            .map(existingParkeerrecht -> {
                if (parkeerrecht.getAanmaaktijd() != null) {
                    existingParkeerrecht.setAanmaaktijd(parkeerrecht.getAanmaaktijd());
                }
                if (parkeerrecht.getBedragaankoop() != null) {
                    existingParkeerrecht.setBedragaankoop(parkeerrecht.getBedragaankoop());
                }
                if (parkeerrecht.getBedragbtw() != null) {
                    existingParkeerrecht.setBedragbtw(parkeerrecht.getBedragbtw());
                }
                if (parkeerrecht.getDatumtijdeinde() != null) {
                    existingParkeerrecht.setDatumtijdeinde(parkeerrecht.getDatumtijdeinde());
                }
                if (parkeerrecht.getDatumtijdstart() != null) {
                    existingParkeerrecht.setDatumtijdstart(parkeerrecht.getDatumtijdstart());
                }
                if (parkeerrecht.getProductnaam() != null) {
                    existingParkeerrecht.setProductnaam(parkeerrecht.getProductnaam());
                }
                if (parkeerrecht.getProductomschrijving() != null) {
                    existingParkeerrecht.setProductomschrijving(parkeerrecht.getProductomschrijving());
                }

                return existingParkeerrecht;
            })
            .map(parkeerrechtRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeerrecht.getId().toString())
        );
    }

    /**
     * {@code GET  /parkeerrechts} : get all the parkeerrechts.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkeerrechts in body.
     */
    @GetMapping("")
    public List<Parkeerrecht> getAllParkeerrechts(@RequestParam(name = "filter", required = false) String filter) {
        if ("resulteertparkeervergunning-is-null".equals(filter)) {
            log.debug("REST request to get all Parkeerrechts where resulteertParkeervergunning is null");
            return StreamSupport.stream(parkeerrechtRepository.findAll().spliterator(), false)
                .filter(parkeerrecht -> parkeerrecht.getResulteertParkeervergunning() == null)
                .toList();
        }
        log.debug("REST request to get all Parkeerrechts");
        return parkeerrechtRepository.findAll();
    }

    /**
     * {@code GET  /parkeerrechts/:id} : get the "id" parkeerrecht.
     *
     * @param id the id of the parkeerrecht to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkeerrecht, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Parkeerrecht> getParkeerrecht(@PathVariable("id") Long id) {
        log.debug("REST request to get Parkeerrecht : {}", id);
        Optional<Parkeerrecht> parkeerrecht = parkeerrechtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parkeerrecht);
    }

    /**
     * {@code DELETE  /parkeerrechts/:id} : delete the "id" parkeerrecht.
     *
     * @param id the id of the parkeerrecht to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkeerrecht(@PathVariable("id") Long id) {
        log.debug("REST request to delete Parkeerrecht : {}", id);
        parkeerrechtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
