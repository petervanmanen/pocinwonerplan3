package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Lijn;
import nl.ritense.demo.repository.LijnRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Lijn}.
 */
@RestController
@RequestMapping("/api/lijns")
@Transactional
public class LijnResource {

    private final Logger log = LoggerFactory.getLogger(LijnResource.class);

    private static final String ENTITY_NAME = "lijn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LijnRepository lijnRepository;

    public LijnResource(LijnRepository lijnRepository) {
        this.lijnRepository = lijnRepository;
    }

    /**
     * {@code POST  /lijns} : Create a new lijn.
     *
     * @param lijn the lijn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lijn, or with status {@code 400 (Bad Request)} if the lijn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Lijn> createLijn(@RequestBody Lijn lijn) throws URISyntaxException {
        log.debug("REST request to save Lijn : {}", lijn);
        if (lijn.getId() != null) {
            throw new BadRequestAlertException("A new lijn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        lijn = lijnRepository.save(lijn);
        return ResponseEntity.created(new URI("/api/lijns/" + lijn.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, lijn.getId().toString()))
            .body(lijn);
    }

    /**
     * {@code PUT  /lijns/:id} : Updates an existing lijn.
     *
     * @param id the id of the lijn to save.
     * @param lijn the lijn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lijn,
     * or with status {@code 400 (Bad Request)} if the lijn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lijn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Lijn> updateLijn(@PathVariable(value = "id", required = false) final Long id, @RequestBody Lijn lijn)
        throws URISyntaxException {
        log.debug("REST request to update Lijn : {}, {}", id, lijn);
        if (lijn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lijn.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lijnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        lijn = lijnRepository.save(lijn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lijn.getId().toString()))
            .body(lijn);
    }

    /**
     * {@code PATCH  /lijns/:id} : Partial updates given fields of an existing lijn, field will ignore if it is null
     *
     * @param id the id of the lijn to save.
     * @param lijn the lijn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lijn,
     * or with status {@code 400 (Bad Request)} if the lijn is not valid,
     * or with status {@code 404 (Not Found)} if the lijn is not found,
     * or with status {@code 500 (Internal Server Error)} if the lijn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Lijn> partialUpdateLijn(@PathVariable(value = "id", required = false) final Long id, @RequestBody Lijn lijn)
        throws URISyntaxException {
        log.debug("REST request to partial update Lijn partially : {}, {}", id, lijn);
        if (lijn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lijn.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lijnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Lijn> result = lijnRepository
            .findById(lijn.getId())
            .map(existingLijn -> {
                if (lijn.getLijn() != null) {
                    existingLijn.setLijn(lijn.getLijn());
                }

                return existingLijn;
            })
            .map(lijnRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lijn.getId().toString())
        );
    }

    /**
     * {@code GET  /lijns} : get all the lijns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lijns in body.
     */
    @GetMapping("")
    public List<Lijn> getAllLijns() {
        log.debug("REST request to get all Lijns");
        return lijnRepository.findAll();
    }

    /**
     * {@code GET  /lijns/:id} : get the "id" lijn.
     *
     * @param id the id of the lijn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lijn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lijn> getLijn(@PathVariable("id") Long id) {
        log.debug("REST request to get Lijn : {}", id);
        Optional<Lijn> lijn = lijnRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lijn);
    }

    /**
     * {@code DELETE  /lijns/:id} : delete the "id" lijn.
     *
     * @param id the id of the lijn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLijn(@PathVariable("id") Long id) {
        log.debug("REST request to delete Lijn : {}", id);
        lijnRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
