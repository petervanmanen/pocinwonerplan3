package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sollicitatiegesprek;
import nl.ritense.demo.repository.SollicitatiegesprekRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sollicitatiegesprek}.
 */
@RestController
@RequestMapping("/api/sollicitatiegespreks")
@Transactional
public class SollicitatiegesprekResource {

    private final Logger log = LoggerFactory.getLogger(SollicitatiegesprekResource.class);

    private static final String ENTITY_NAME = "sollicitatiegesprek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SollicitatiegesprekRepository sollicitatiegesprekRepository;

    public SollicitatiegesprekResource(SollicitatiegesprekRepository sollicitatiegesprekRepository) {
        this.sollicitatiegesprekRepository = sollicitatiegesprekRepository;
    }

    /**
     * {@code POST  /sollicitatiegespreks} : Create a new sollicitatiegesprek.
     *
     * @param sollicitatiegesprek the sollicitatiegesprek to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sollicitatiegesprek, or with status {@code 400 (Bad Request)} if the sollicitatiegesprek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sollicitatiegesprek> createSollicitatiegesprek(@RequestBody Sollicitatiegesprek sollicitatiegesprek)
        throws URISyntaxException {
        log.debug("REST request to save Sollicitatiegesprek : {}", sollicitatiegesprek);
        if (sollicitatiegesprek.getId() != null) {
            throw new BadRequestAlertException("A new sollicitatiegesprek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sollicitatiegesprek = sollicitatiegesprekRepository.save(sollicitatiegesprek);
        return ResponseEntity.created(new URI("/api/sollicitatiegespreks/" + sollicitatiegesprek.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sollicitatiegesprek.getId().toString()))
            .body(sollicitatiegesprek);
    }

    /**
     * {@code PUT  /sollicitatiegespreks/:id} : Updates an existing sollicitatiegesprek.
     *
     * @param id the id of the sollicitatiegesprek to save.
     * @param sollicitatiegesprek the sollicitatiegesprek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sollicitatiegesprek,
     * or with status {@code 400 (Bad Request)} if the sollicitatiegesprek is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sollicitatiegesprek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sollicitatiegesprek> updateSollicitatiegesprek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sollicitatiegesprek sollicitatiegesprek
    ) throws URISyntaxException {
        log.debug("REST request to update Sollicitatiegesprek : {}, {}", id, sollicitatiegesprek);
        if (sollicitatiegesprek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sollicitatiegesprek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sollicitatiegesprekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sollicitatiegesprek = sollicitatiegesprekRepository.save(sollicitatiegesprek);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sollicitatiegesprek.getId().toString()))
            .body(sollicitatiegesprek);
    }

    /**
     * {@code PATCH  /sollicitatiegespreks/:id} : Partial updates given fields of an existing sollicitatiegesprek, field will ignore if it is null
     *
     * @param id the id of the sollicitatiegesprek to save.
     * @param sollicitatiegesprek the sollicitatiegesprek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sollicitatiegesprek,
     * or with status {@code 400 (Bad Request)} if the sollicitatiegesprek is not valid,
     * or with status {@code 404 (Not Found)} if the sollicitatiegesprek is not found,
     * or with status {@code 500 (Internal Server Error)} if the sollicitatiegesprek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sollicitatiegesprek> partialUpdateSollicitatiegesprek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sollicitatiegesprek sollicitatiegesprek
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sollicitatiegesprek partially : {}, {}", id, sollicitatiegesprek);
        if (sollicitatiegesprek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sollicitatiegesprek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sollicitatiegesprekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sollicitatiegesprek> result = sollicitatiegesprekRepository
            .findById(sollicitatiegesprek.getId())
            .map(existingSollicitatiegesprek -> {
                if (sollicitatiegesprek.getAangenomen() != null) {
                    existingSollicitatiegesprek.setAangenomen(sollicitatiegesprek.getAangenomen());
                }
                if (sollicitatiegesprek.getDatum() != null) {
                    existingSollicitatiegesprek.setDatum(sollicitatiegesprek.getDatum());
                }
                if (sollicitatiegesprek.getOpmerkingen() != null) {
                    existingSollicitatiegesprek.setOpmerkingen(sollicitatiegesprek.getOpmerkingen());
                }
                if (sollicitatiegesprek.getVolgendgesprek() != null) {
                    existingSollicitatiegesprek.setVolgendgesprek(sollicitatiegesprek.getVolgendgesprek());
                }

                return existingSollicitatiegesprek;
            })
            .map(sollicitatiegesprekRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sollicitatiegesprek.getId().toString())
        );
    }

    /**
     * {@code GET  /sollicitatiegespreks} : get all the sollicitatiegespreks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sollicitatiegespreks in body.
     */
    @GetMapping("")
    public List<Sollicitatiegesprek> getAllSollicitatiegespreks(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Sollicitatiegespreks");
        if (eagerload) {
            return sollicitatiegesprekRepository.findAllWithEagerRelationships();
        } else {
            return sollicitatiegesprekRepository.findAll();
        }
    }

    /**
     * {@code GET  /sollicitatiegespreks/:id} : get the "id" sollicitatiegesprek.
     *
     * @param id the id of the sollicitatiegesprek to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sollicitatiegesprek, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sollicitatiegesprek> getSollicitatiegesprek(@PathVariable("id") Long id) {
        log.debug("REST request to get Sollicitatiegesprek : {}", id);
        Optional<Sollicitatiegesprek> sollicitatiegesprek = sollicitatiegesprekRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(sollicitatiegesprek);
    }

    /**
     * {@code DELETE  /sollicitatiegespreks/:id} : delete the "id" sollicitatiegesprek.
     *
     * @param id the id of the sollicitatiegesprek to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSollicitatiegesprek(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sollicitatiegesprek : {}", id);
        sollicitatiegesprekRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
