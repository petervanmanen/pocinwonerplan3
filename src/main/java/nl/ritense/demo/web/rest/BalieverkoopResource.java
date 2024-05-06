package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Balieverkoop;
import nl.ritense.demo.repository.BalieverkoopRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Balieverkoop}.
 */
@RestController
@RequestMapping("/api/balieverkoops")
@Transactional
public class BalieverkoopResource {

    private final Logger log = LoggerFactory.getLogger(BalieverkoopResource.class);

    private static final String ENTITY_NAME = "balieverkoop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BalieverkoopRepository balieverkoopRepository;

    public BalieverkoopResource(BalieverkoopRepository balieverkoopRepository) {
        this.balieverkoopRepository = balieverkoopRepository;
    }

    /**
     * {@code POST  /balieverkoops} : Create a new balieverkoop.
     *
     * @param balieverkoop the balieverkoop to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new balieverkoop, or with status {@code 400 (Bad Request)} if the balieverkoop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Balieverkoop> createBalieverkoop(@RequestBody Balieverkoop balieverkoop) throws URISyntaxException {
        log.debug("REST request to save Balieverkoop : {}", balieverkoop);
        if (balieverkoop.getId() != null) {
            throw new BadRequestAlertException("A new balieverkoop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        balieverkoop = balieverkoopRepository.save(balieverkoop);
        return ResponseEntity.created(new URI("/api/balieverkoops/" + balieverkoop.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, balieverkoop.getId().toString()))
            .body(balieverkoop);
    }

    /**
     * {@code PUT  /balieverkoops/:id} : Updates an existing balieverkoop.
     *
     * @param id the id of the balieverkoop to save.
     * @param balieverkoop the balieverkoop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balieverkoop,
     * or with status {@code 400 (Bad Request)} if the balieverkoop is not valid,
     * or with status {@code 500 (Internal Server Error)} if the balieverkoop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Balieverkoop> updateBalieverkoop(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Balieverkoop balieverkoop
    ) throws URISyntaxException {
        log.debug("REST request to update Balieverkoop : {}, {}", id, balieverkoop);
        if (balieverkoop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balieverkoop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balieverkoopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        balieverkoop = balieverkoopRepository.save(balieverkoop);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, balieverkoop.getId().toString()))
            .body(balieverkoop);
    }

    /**
     * {@code PATCH  /balieverkoops/:id} : Partial updates given fields of an existing balieverkoop, field will ignore if it is null
     *
     * @param id the id of the balieverkoop to save.
     * @param balieverkoop the balieverkoop to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated balieverkoop,
     * or with status {@code 400 (Bad Request)} if the balieverkoop is not valid,
     * or with status {@code 404 (Not Found)} if the balieverkoop is not found,
     * or with status {@code 500 (Internal Server Error)} if the balieverkoop couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Balieverkoop> partialUpdateBalieverkoop(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Balieverkoop balieverkoop
    ) throws URISyntaxException {
        log.debug("REST request to partial update Balieverkoop partially : {}, {}", id, balieverkoop);
        if (balieverkoop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, balieverkoop.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!balieverkoopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Balieverkoop> result = balieverkoopRepository
            .findById(balieverkoop.getId())
            .map(existingBalieverkoop -> {
                if (balieverkoop.getAantal() != null) {
                    existingBalieverkoop.setAantal(balieverkoop.getAantal());
                }
                if (balieverkoop.getKanaal() != null) {
                    existingBalieverkoop.setKanaal(balieverkoop.getKanaal());
                }
                if (balieverkoop.getVerkooptijd() != null) {
                    existingBalieverkoop.setVerkooptijd(balieverkoop.getVerkooptijd());
                }

                return existingBalieverkoop;
            })
            .map(balieverkoopRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, balieverkoop.getId().toString())
        );
    }

    /**
     * {@code GET  /balieverkoops} : get all the balieverkoops.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of balieverkoops in body.
     */
    @GetMapping("")
    public List<Balieverkoop> getAllBalieverkoops() {
        log.debug("REST request to get all Balieverkoops");
        return balieverkoopRepository.findAll();
    }

    /**
     * {@code GET  /balieverkoops/:id} : get the "id" balieverkoop.
     *
     * @param id the id of the balieverkoop to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the balieverkoop, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Balieverkoop> getBalieverkoop(@PathVariable("id") Long id) {
        log.debug("REST request to get Balieverkoop : {}", id);
        Optional<Balieverkoop> balieverkoop = balieverkoopRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(balieverkoop);
    }

    /**
     * {@code DELETE  /balieverkoops/:id} : delete the "id" balieverkoop.
     *
     * @param id the id of the balieverkoop to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalieverkoop(@PathVariable("id") Long id) {
        log.debug("REST request to delete Balieverkoop : {}", id);
        balieverkoopRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
