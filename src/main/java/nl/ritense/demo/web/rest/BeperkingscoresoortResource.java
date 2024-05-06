package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beperkingscoresoort;
import nl.ritense.demo.repository.BeperkingscoresoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beperkingscoresoort}.
 */
@RestController
@RequestMapping("/api/beperkingscoresoorts")
@Transactional
public class BeperkingscoresoortResource {

    private final Logger log = LoggerFactory.getLogger(BeperkingscoresoortResource.class);

    private static final String ENTITY_NAME = "beperkingscoresoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeperkingscoresoortRepository beperkingscoresoortRepository;

    public BeperkingscoresoortResource(BeperkingscoresoortRepository beperkingscoresoortRepository) {
        this.beperkingscoresoortRepository = beperkingscoresoortRepository;
    }

    /**
     * {@code POST  /beperkingscoresoorts} : Create a new beperkingscoresoort.
     *
     * @param beperkingscoresoort the beperkingscoresoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beperkingscoresoort, or with status {@code 400 (Bad Request)} if the beperkingscoresoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beperkingscoresoort> createBeperkingscoresoort(@RequestBody Beperkingscoresoort beperkingscoresoort)
        throws URISyntaxException {
        log.debug("REST request to save Beperkingscoresoort : {}", beperkingscoresoort);
        if (beperkingscoresoort.getId() != null) {
            throw new BadRequestAlertException("A new beperkingscoresoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beperkingscoresoort = beperkingscoresoortRepository.save(beperkingscoresoort);
        return ResponseEntity.created(new URI("/api/beperkingscoresoorts/" + beperkingscoresoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beperkingscoresoort.getId().toString()))
            .body(beperkingscoresoort);
    }

    /**
     * {@code PUT  /beperkingscoresoorts/:id} : Updates an existing beperkingscoresoort.
     *
     * @param id the id of the beperkingscoresoort to save.
     * @param beperkingscoresoort the beperkingscoresoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperkingscoresoort,
     * or with status {@code 400 (Bad Request)} if the beperkingscoresoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beperkingscoresoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beperkingscoresoort> updateBeperkingscoresoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beperkingscoresoort beperkingscoresoort
    ) throws URISyntaxException {
        log.debug("REST request to update Beperkingscoresoort : {}, {}", id, beperkingscoresoort);
        if (beperkingscoresoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperkingscoresoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingscoresoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beperkingscoresoort = beperkingscoresoortRepository.save(beperkingscoresoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperkingscoresoort.getId().toString()))
            .body(beperkingscoresoort);
    }

    /**
     * {@code PATCH  /beperkingscoresoorts/:id} : Partial updates given fields of an existing beperkingscoresoort, field will ignore if it is null
     *
     * @param id the id of the beperkingscoresoort to save.
     * @param beperkingscoresoort the beperkingscoresoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperkingscoresoort,
     * or with status {@code 400 (Bad Request)} if the beperkingscoresoort is not valid,
     * or with status {@code 404 (Not Found)} if the beperkingscoresoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the beperkingscoresoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beperkingscoresoort> partialUpdateBeperkingscoresoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beperkingscoresoort beperkingscoresoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beperkingscoresoort partially : {}, {}", id, beperkingscoresoort);
        if (beperkingscoresoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperkingscoresoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingscoresoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beperkingscoresoort> result = beperkingscoresoortRepository
            .findById(beperkingscoresoort.getId())
            .map(existingBeperkingscoresoort -> {
                if (beperkingscoresoort.getVraag() != null) {
                    existingBeperkingscoresoort.setVraag(beperkingscoresoort.getVraag());
                }
                if (beperkingscoresoort.getWet() != null) {
                    existingBeperkingscoresoort.setWet(beperkingscoresoort.getWet());
                }

                return existingBeperkingscoresoort;
            })
            .map(beperkingscoresoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperkingscoresoort.getId().toString())
        );
    }

    /**
     * {@code GET  /beperkingscoresoorts} : get all the beperkingscoresoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beperkingscoresoorts in body.
     */
    @GetMapping("")
    public List<Beperkingscoresoort> getAllBeperkingscoresoorts() {
        log.debug("REST request to get all Beperkingscoresoorts");
        return beperkingscoresoortRepository.findAll();
    }

    /**
     * {@code GET  /beperkingscoresoorts/:id} : get the "id" beperkingscoresoort.
     *
     * @param id the id of the beperkingscoresoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beperkingscoresoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beperkingscoresoort> getBeperkingscoresoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Beperkingscoresoort : {}", id);
        Optional<Beperkingscoresoort> beperkingscoresoort = beperkingscoresoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beperkingscoresoort);
    }

    /**
     * {@code DELETE  /beperkingscoresoorts/:id} : delete the "id" beperkingscoresoort.
     *
     * @param id the id of the beperkingscoresoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeperkingscoresoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beperkingscoresoort : {}", id);
        beperkingscoresoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
