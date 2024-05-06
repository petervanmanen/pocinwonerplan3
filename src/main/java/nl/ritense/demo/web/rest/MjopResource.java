package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Mjop;
import nl.ritense.demo.repository.MjopRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Mjop}.
 */
@RestController
@RequestMapping("/api/mjops")
@Transactional
public class MjopResource {

    private final Logger log = LoggerFactory.getLogger(MjopResource.class);

    private static final String ENTITY_NAME = "mjop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MjopRepository mjopRepository;

    public MjopResource(MjopRepository mjopRepository) {
        this.mjopRepository = mjopRepository;
    }

    /**
     * {@code POST  /mjops} : Create a new mjop.
     *
     * @param mjop the mjop to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mjop, or with status {@code 400 (Bad Request)} if the mjop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Mjop> createMjop(@RequestBody Mjop mjop) throws URISyntaxException {
        log.debug("REST request to save Mjop : {}", mjop);
        if (mjop.getId() != null) {
            throw new BadRequestAlertException("A new mjop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mjop = mjopRepository.save(mjop);
        return ResponseEntity.created(new URI("/api/mjops/" + mjop.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, mjop.getId().toString()))
            .body(mjop);
    }

    /**
     * {@code PUT  /mjops/:id} : Updates an existing mjop.
     *
     * @param id the id of the mjop to save.
     * @param mjop the mjop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mjop,
     * or with status {@code 400 (Bad Request)} if the mjop is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mjop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mjop> updateMjop(@PathVariable(value = "id", required = false) final Long id, @RequestBody Mjop mjop)
        throws URISyntaxException {
        log.debug("REST request to update Mjop : {}, {}", id, mjop);
        if (mjop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mjop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mjopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mjop = mjopRepository.save(mjop);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mjop.getId().toString()))
            .body(mjop);
    }

    /**
     * {@code PATCH  /mjops/:id} : Partial updates given fields of an existing mjop, field will ignore if it is null
     *
     * @param id the id of the mjop to save.
     * @param mjop the mjop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mjop,
     * or with status {@code 400 (Bad Request)} if the mjop is not valid,
     * or with status {@code 404 (Not Found)} if the mjop is not found,
     * or with status {@code 500 (Internal Server Error)} if the mjop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mjop> partialUpdateMjop(@PathVariable(value = "id", required = false) final Long id, @RequestBody Mjop mjop)
        throws URISyntaxException {
        log.debug("REST request to partial update Mjop partially : {}, {}", id, mjop);
        if (mjop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mjop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mjopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mjop> result = mjopRepository
            .findById(mjop.getId())
            .map(existingMjop -> {
                if (mjop.getDatum() != null) {
                    existingMjop.setDatum(mjop.getDatum());
                }
                if (mjop.getOmschrijving() != null) {
                    existingMjop.setOmschrijving(mjop.getOmschrijving());
                }

                return existingMjop;
            })
            .map(mjopRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mjop.getId().toString())
        );
    }

    /**
     * {@code GET  /mjops} : get all the mjops.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mjops in body.
     */
    @GetMapping("")
    public List<Mjop> getAllMjops() {
        log.debug("REST request to get all Mjops");
        return mjopRepository.findAll();
    }

    /**
     * {@code GET  /mjops/:id} : get the "id" mjop.
     *
     * @param id the id of the mjop to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mjop, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mjop> getMjop(@PathVariable("id") Long id) {
        log.debug("REST request to get Mjop : {}", id);
        Optional<Mjop> mjop = mjopRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mjop);
    }

    /**
     * {@code DELETE  /mjops/:id} : delete the "id" mjop.
     *
     * @param id the id of the mjop to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMjop(@PathVariable("id") Long id) {
        log.debug("REST request to delete Mjop : {}", id);
        mjopRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
