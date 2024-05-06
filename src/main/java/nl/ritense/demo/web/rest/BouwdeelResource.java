package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bouwdeel;
import nl.ritense.demo.repository.BouwdeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bouwdeel}.
 */
@RestController
@RequestMapping("/api/bouwdeels")
@Transactional
public class BouwdeelResource {

    private final Logger log = LoggerFactory.getLogger(BouwdeelResource.class);

    private static final String ENTITY_NAME = "bouwdeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BouwdeelRepository bouwdeelRepository;

    public BouwdeelResource(BouwdeelRepository bouwdeelRepository) {
        this.bouwdeelRepository = bouwdeelRepository;
    }

    /**
     * {@code POST  /bouwdeels} : Create a new bouwdeel.
     *
     * @param bouwdeel the bouwdeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bouwdeel, or with status {@code 400 (Bad Request)} if the bouwdeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bouwdeel> createBouwdeel(@Valid @RequestBody Bouwdeel bouwdeel) throws URISyntaxException {
        log.debug("REST request to save Bouwdeel : {}", bouwdeel);
        if (bouwdeel.getId() != null) {
            throw new BadRequestAlertException("A new bouwdeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bouwdeel = bouwdeelRepository.save(bouwdeel);
        return ResponseEntity.created(new URI("/api/bouwdeels/" + bouwdeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bouwdeel.getId().toString()))
            .body(bouwdeel);
    }

    /**
     * {@code PUT  /bouwdeels/:id} : Updates an existing bouwdeel.
     *
     * @param id the id of the bouwdeel to save.
     * @param bouwdeel the bouwdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwdeel,
     * or with status {@code 400 (Bad Request)} if the bouwdeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bouwdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bouwdeel> updateBouwdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bouwdeel bouwdeel
    ) throws URISyntaxException {
        log.debug("REST request to update Bouwdeel : {}, {}", id, bouwdeel);
        if (bouwdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bouwdeel = bouwdeelRepository.save(bouwdeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwdeel.getId().toString()))
            .body(bouwdeel);
    }

    /**
     * {@code PATCH  /bouwdeels/:id} : Partial updates given fields of an existing bouwdeel, field will ignore if it is null
     *
     * @param id the id of the bouwdeel to save.
     * @param bouwdeel the bouwdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwdeel,
     * or with status {@code 400 (Bad Request)} if the bouwdeel is not valid,
     * or with status {@code 404 (Not Found)} if the bouwdeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the bouwdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bouwdeel> partialUpdateBouwdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bouwdeel bouwdeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bouwdeel partially : {}, {}", id, bouwdeel);
        if (bouwdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bouwdeel> result = bouwdeelRepository
            .findById(bouwdeel.getId())
            .map(existingBouwdeel -> {
                if (bouwdeel.getCode() != null) {
                    existingBouwdeel.setCode(bouwdeel.getCode());
                }
                if (bouwdeel.getOmschrijving() != null) {
                    existingBouwdeel.setOmschrijving(bouwdeel.getOmschrijving());
                }

                return existingBouwdeel;
            })
            .map(bouwdeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwdeel.getId().toString())
        );
    }

    /**
     * {@code GET  /bouwdeels} : get all the bouwdeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bouwdeels in body.
     */
    @GetMapping("")
    public List<Bouwdeel> getAllBouwdeels() {
        log.debug("REST request to get all Bouwdeels");
        return bouwdeelRepository.findAll();
    }

    /**
     * {@code GET  /bouwdeels/:id} : get the "id" bouwdeel.
     *
     * @param id the id of the bouwdeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bouwdeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bouwdeel> getBouwdeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Bouwdeel : {}", id);
        Optional<Bouwdeel> bouwdeel = bouwdeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bouwdeel);
    }

    /**
     * {@code DELETE  /bouwdeels/:id} : delete the "id" bouwdeel.
     *
     * @param id the id of the bouwdeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBouwdeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bouwdeel : {}", id);
        bouwdeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
