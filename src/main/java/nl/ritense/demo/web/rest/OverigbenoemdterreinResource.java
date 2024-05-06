package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overigbenoemdterrein;
import nl.ritense.demo.repository.OverigbenoemdterreinRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overigbenoemdterrein}.
 */
@RestController
@RequestMapping("/api/overigbenoemdterreins")
@Transactional
public class OverigbenoemdterreinResource {

    private final Logger log = LoggerFactory.getLogger(OverigbenoemdterreinResource.class);

    private static final String ENTITY_NAME = "overigbenoemdterrein";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverigbenoemdterreinRepository overigbenoemdterreinRepository;

    public OverigbenoemdterreinResource(OverigbenoemdterreinRepository overigbenoemdterreinRepository) {
        this.overigbenoemdterreinRepository = overigbenoemdterreinRepository;
    }

    /**
     * {@code POST  /overigbenoemdterreins} : Create a new overigbenoemdterrein.
     *
     * @param overigbenoemdterrein the overigbenoemdterrein to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overigbenoemdterrein, or with status {@code 400 (Bad Request)} if the overigbenoemdterrein has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overigbenoemdterrein> createOverigbenoemdterrein(@RequestBody Overigbenoemdterrein overigbenoemdterrein)
        throws URISyntaxException {
        log.debug("REST request to save Overigbenoemdterrein : {}", overigbenoemdterrein);
        if (overigbenoemdterrein.getId() != null) {
            throw new BadRequestAlertException("A new overigbenoemdterrein cannot already have an ID", ENTITY_NAME, "idexists");
        }
        overigbenoemdterrein = overigbenoemdterreinRepository.save(overigbenoemdterrein);
        return ResponseEntity.created(new URI("/api/overigbenoemdterreins/" + overigbenoemdterrein.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, overigbenoemdterrein.getId().toString()))
            .body(overigbenoemdterrein);
    }

    /**
     * {@code PUT  /overigbenoemdterreins/:id} : Updates an existing overigbenoemdterrein.
     *
     * @param id the id of the overigbenoemdterrein to save.
     * @param overigbenoemdterrein the overigbenoemdterrein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overigbenoemdterrein,
     * or with status {@code 400 (Bad Request)} if the overigbenoemdterrein is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overigbenoemdterrein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overigbenoemdterrein> updateOverigbenoemdterrein(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overigbenoemdterrein overigbenoemdterrein
    ) throws URISyntaxException {
        log.debug("REST request to update Overigbenoemdterrein : {}, {}", id, overigbenoemdterrein);
        if (overigbenoemdterrein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overigbenoemdterrein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overigbenoemdterreinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overigbenoemdterrein = overigbenoemdterreinRepository.save(overigbenoemdterrein);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overigbenoemdterrein.getId().toString()))
            .body(overigbenoemdterrein);
    }

    /**
     * {@code PATCH  /overigbenoemdterreins/:id} : Partial updates given fields of an existing overigbenoemdterrein, field will ignore if it is null
     *
     * @param id the id of the overigbenoemdterrein to save.
     * @param overigbenoemdterrein the overigbenoemdterrein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overigbenoemdterrein,
     * or with status {@code 400 (Bad Request)} if the overigbenoemdterrein is not valid,
     * or with status {@code 404 (Not Found)} if the overigbenoemdterrein is not found,
     * or with status {@code 500 (Internal Server Error)} if the overigbenoemdterrein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overigbenoemdterrein> partialUpdateOverigbenoemdterrein(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overigbenoemdterrein overigbenoemdterrein
    ) throws URISyntaxException {
        log.debug("REST request to partial update Overigbenoemdterrein partially : {}, {}", id, overigbenoemdterrein);
        if (overigbenoemdterrein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overigbenoemdterrein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overigbenoemdterreinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overigbenoemdterrein> result = overigbenoemdterreinRepository
            .findById(overigbenoemdterrein.getId())
            .map(existingOverigbenoemdterrein -> {
                if (overigbenoemdterrein.getGebruiksdoeloverigbenoemdterrein() != null) {
                    existingOverigbenoemdterrein.setGebruiksdoeloverigbenoemdterrein(
                        overigbenoemdterrein.getGebruiksdoeloverigbenoemdterrein()
                    );
                }
                if (overigbenoemdterrein.getOverigbenoemdterreinidentificatie() != null) {
                    existingOverigbenoemdterrein.setOverigbenoemdterreinidentificatie(
                        overigbenoemdterrein.getOverigbenoemdterreinidentificatie()
                    );
                }

                return existingOverigbenoemdterrein;
            })
            .map(overigbenoemdterreinRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overigbenoemdterrein.getId().toString())
        );
    }

    /**
     * {@code GET  /overigbenoemdterreins} : get all the overigbenoemdterreins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overigbenoemdterreins in body.
     */
    @GetMapping("")
    public List<Overigbenoemdterrein> getAllOverigbenoemdterreins() {
        log.debug("REST request to get all Overigbenoemdterreins");
        return overigbenoemdterreinRepository.findAll();
    }

    /**
     * {@code GET  /overigbenoemdterreins/:id} : get the "id" overigbenoemdterrein.
     *
     * @param id the id of the overigbenoemdterrein to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overigbenoemdterrein, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overigbenoemdterrein> getOverigbenoemdterrein(@PathVariable("id") Long id) {
        log.debug("REST request to get Overigbenoemdterrein : {}", id);
        Optional<Overigbenoemdterrein> overigbenoemdterrein = overigbenoemdterreinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overigbenoemdterrein);
    }

    /**
     * {@code DELETE  /overigbenoemdterreins/:id} : delete the "id" overigbenoemdterrein.
     *
     * @param id the id of the overigbenoemdterrein to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverigbenoemdterrein(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overigbenoemdterrein : {}", id);
        overigbenoemdterreinRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
