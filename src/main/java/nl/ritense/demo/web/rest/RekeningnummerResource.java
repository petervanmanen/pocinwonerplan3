package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Rekeningnummer;
import nl.ritense.demo.repository.RekeningnummerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Rekeningnummer}.
 */
@RestController
@RequestMapping("/api/rekeningnummers")
@Transactional
public class RekeningnummerResource {

    private final Logger log = LoggerFactory.getLogger(RekeningnummerResource.class);

    private static final String ENTITY_NAME = "rekeningnummer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RekeningnummerRepository rekeningnummerRepository;

    public RekeningnummerResource(RekeningnummerRepository rekeningnummerRepository) {
        this.rekeningnummerRepository = rekeningnummerRepository;
    }

    /**
     * {@code POST  /rekeningnummers} : Create a new rekeningnummer.
     *
     * @param rekeningnummer the rekeningnummer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rekeningnummer, or with status {@code 400 (Bad Request)} if the rekeningnummer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Rekeningnummer> createRekeningnummer(@RequestBody Rekeningnummer rekeningnummer) throws URISyntaxException {
        log.debug("REST request to save Rekeningnummer : {}", rekeningnummer);
        if (rekeningnummer.getId() != null) {
            throw new BadRequestAlertException("A new rekeningnummer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rekeningnummer = rekeningnummerRepository.save(rekeningnummer);
        return ResponseEntity.created(new URI("/api/rekeningnummers/" + rekeningnummer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rekeningnummer.getId().toString()))
            .body(rekeningnummer);
    }

    /**
     * {@code PUT  /rekeningnummers/:id} : Updates an existing rekeningnummer.
     *
     * @param id the id of the rekeningnummer to save.
     * @param rekeningnummer the rekeningnummer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rekeningnummer,
     * or with status {@code 400 (Bad Request)} if the rekeningnummer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rekeningnummer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rekeningnummer> updateRekeningnummer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rekeningnummer rekeningnummer
    ) throws URISyntaxException {
        log.debug("REST request to update Rekeningnummer : {}, {}", id, rekeningnummer);
        if (rekeningnummer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rekeningnummer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rekeningnummerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rekeningnummer = rekeningnummerRepository.save(rekeningnummer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rekeningnummer.getId().toString()))
            .body(rekeningnummer);
    }

    /**
     * {@code PATCH  /rekeningnummers/:id} : Partial updates given fields of an existing rekeningnummer, field will ignore if it is null
     *
     * @param id the id of the rekeningnummer to save.
     * @param rekeningnummer the rekeningnummer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rekeningnummer,
     * or with status {@code 400 (Bad Request)} if the rekeningnummer is not valid,
     * or with status {@code 404 (Not Found)} if the rekeningnummer is not found,
     * or with status {@code 500 (Internal Server Error)} if the rekeningnummer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rekeningnummer> partialUpdateRekeningnummer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rekeningnummer rekeningnummer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rekeningnummer partially : {}, {}", id, rekeningnummer);
        if (rekeningnummer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rekeningnummer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rekeningnummerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rekeningnummer> result = rekeningnummerRepository
            .findById(rekeningnummer.getId())
            .map(existingRekeningnummer -> {
                if (rekeningnummer.getBic() != null) {
                    existingRekeningnummer.setBic(rekeningnummer.getBic());
                }
                if (rekeningnummer.getIban() != null) {
                    existingRekeningnummer.setIban(rekeningnummer.getIban());
                }

                return existingRekeningnummer;
            })
            .map(rekeningnummerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rekeningnummer.getId().toString())
        );
    }

    /**
     * {@code GET  /rekeningnummers} : get all the rekeningnummers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rekeningnummers in body.
     */
    @GetMapping("")
    public List<Rekeningnummer> getAllRekeningnummers() {
        log.debug("REST request to get all Rekeningnummers");
        return rekeningnummerRepository.findAll();
    }

    /**
     * {@code GET  /rekeningnummers/:id} : get the "id" rekeningnummer.
     *
     * @param id the id of the rekeningnummer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rekeningnummer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rekeningnummer> getRekeningnummer(@PathVariable("id") Long id) {
        log.debug("REST request to get Rekeningnummer : {}", id);
        Optional<Rekeningnummer> rekeningnummer = rekeningnummerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rekeningnummer);
    }

    /**
     * {@code DELETE  /rekeningnummers/:id} : delete the "id" rekeningnummer.
     *
     * @param id the id of the rekeningnummer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRekeningnummer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rekeningnummer : {}", id);
        rekeningnummerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
