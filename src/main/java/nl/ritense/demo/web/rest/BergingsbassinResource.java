package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bergingsbassin;
import nl.ritense.demo.repository.BergingsbassinRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bergingsbassin}.
 */
@RestController
@RequestMapping("/api/bergingsbassins")
@Transactional
public class BergingsbassinResource {

    private final Logger log = LoggerFactory.getLogger(BergingsbassinResource.class);

    private static final String ENTITY_NAME = "bergingsbassin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BergingsbassinRepository bergingsbassinRepository;

    public BergingsbassinResource(BergingsbassinRepository bergingsbassinRepository) {
        this.bergingsbassinRepository = bergingsbassinRepository;
    }

    /**
     * {@code POST  /bergingsbassins} : Create a new bergingsbassin.
     *
     * @param bergingsbassin the bergingsbassin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bergingsbassin, or with status {@code 400 (Bad Request)} if the bergingsbassin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bergingsbassin> createBergingsbassin(@RequestBody Bergingsbassin bergingsbassin) throws URISyntaxException {
        log.debug("REST request to save Bergingsbassin : {}", bergingsbassin);
        if (bergingsbassin.getId() != null) {
            throw new BadRequestAlertException("A new bergingsbassin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bergingsbassin = bergingsbassinRepository.save(bergingsbassin);
        return ResponseEntity.created(new URI("/api/bergingsbassins/" + bergingsbassin.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bergingsbassin.getId().toString()))
            .body(bergingsbassin);
    }

    /**
     * {@code PUT  /bergingsbassins/:id} : Updates an existing bergingsbassin.
     *
     * @param id the id of the bergingsbassin to save.
     * @param bergingsbassin the bergingsbassin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bergingsbassin,
     * or with status {@code 400 (Bad Request)} if the bergingsbassin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bergingsbassin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bergingsbassin> updateBergingsbassin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bergingsbassin bergingsbassin
    ) throws URISyntaxException {
        log.debug("REST request to update Bergingsbassin : {}, {}", id, bergingsbassin);
        if (bergingsbassin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bergingsbassin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bergingsbassinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bergingsbassin = bergingsbassinRepository.save(bergingsbassin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bergingsbassin.getId().toString()))
            .body(bergingsbassin);
    }

    /**
     * {@code PATCH  /bergingsbassins/:id} : Partial updates given fields of an existing bergingsbassin, field will ignore if it is null
     *
     * @param id the id of the bergingsbassin to save.
     * @param bergingsbassin the bergingsbassin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bergingsbassin,
     * or with status {@code 400 (Bad Request)} if the bergingsbassin is not valid,
     * or with status {@code 404 (Not Found)} if the bergingsbassin is not found,
     * or with status {@code 500 (Internal Server Error)} if the bergingsbassin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bergingsbassin> partialUpdateBergingsbassin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bergingsbassin bergingsbassin
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bergingsbassin partially : {}, {}", id, bergingsbassin);
        if (bergingsbassin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bergingsbassin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bergingsbassinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bergingsbassin> result = bergingsbassinRepository
            .findById(bergingsbassin.getId())
            .map(existingBergingsbassin -> {
                if (bergingsbassin.getBergendvermogen() != null) {
                    existingBergingsbassin.setBergendvermogen(bergingsbassin.getBergendvermogen());
                }
                if (bergingsbassin.getPompledigingsvoorziening() != null) {
                    existingBergingsbassin.setPompledigingsvoorziening(bergingsbassin.getPompledigingsvoorziening());
                }
                if (bergingsbassin.getPompspoelvoorziening() != null) {
                    existingBergingsbassin.setPompspoelvoorziening(bergingsbassin.getPompspoelvoorziening());
                }
                if (bergingsbassin.getSpoelleiding() != null) {
                    existingBergingsbassin.setSpoelleiding(bergingsbassin.getSpoelleiding());
                }
                if (bergingsbassin.getVorm() != null) {
                    existingBergingsbassin.setVorm(bergingsbassin.getVorm());
                }

                return existingBergingsbassin;
            })
            .map(bergingsbassinRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bergingsbassin.getId().toString())
        );
    }

    /**
     * {@code GET  /bergingsbassins} : get all the bergingsbassins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bergingsbassins in body.
     */
    @GetMapping("")
    public List<Bergingsbassin> getAllBergingsbassins() {
        log.debug("REST request to get all Bergingsbassins");
        return bergingsbassinRepository.findAll();
    }

    /**
     * {@code GET  /bergingsbassins/:id} : get the "id" bergingsbassin.
     *
     * @param id the id of the bergingsbassin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bergingsbassin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bergingsbassin> getBergingsbassin(@PathVariable("id") Long id) {
        log.debug("REST request to get Bergingsbassin : {}", id);
        Optional<Bergingsbassin> bergingsbassin = bergingsbassinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bergingsbassin);
    }

    /**
     * {@code DELETE  /bergingsbassins/:id} : delete the "id" bergingsbassin.
     *
     * @param id the id of the bergingsbassin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBergingsbassin(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bergingsbassin : {}", id);
        bergingsbassinRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
