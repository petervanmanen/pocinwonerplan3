package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ligplaatsontheffing;
import nl.ritense.demo.repository.LigplaatsontheffingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ligplaatsontheffing}.
 */
@RestController
@RequestMapping("/api/ligplaatsontheffings")
@Transactional
public class LigplaatsontheffingResource {

    private final Logger log = LoggerFactory.getLogger(LigplaatsontheffingResource.class);

    private static final String ENTITY_NAME = "ligplaatsontheffing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigplaatsontheffingRepository ligplaatsontheffingRepository;

    public LigplaatsontheffingResource(LigplaatsontheffingRepository ligplaatsontheffingRepository) {
        this.ligplaatsontheffingRepository = ligplaatsontheffingRepository;
    }

    /**
     * {@code POST  /ligplaatsontheffings} : Create a new ligplaatsontheffing.
     *
     * @param ligplaatsontheffing the ligplaatsontheffing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligplaatsontheffing, or with status {@code 400 (Bad Request)} if the ligplaatsontheffing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ligplaatsontheffing> createLigplaatsontheffing(@RequestBody Ligplaatsontheffing ligplaatsontheffing)
        throws URISyntaxException {
        log.debug("REST request to save Ligplaatsontheffing : {}", ligplaatsontheffing);
        if (ligplaatsontheffing.getId() != null) {
            throw new BadRequestAlertException("A new ligplaatsontheffing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ligplaatsontheffing = ligplaatsontheffingRepository.save(ligplaatsontheffing);
        return ResponseEntity.created(new URI("/api/ligplaatsontheffings/" + ligplaatsontheffing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ligplaatsontheffing.getId().toString()))
            .body(ligplaatsontheffing);
    }

    /**
     * {@code PUT  /ligplaatsontheffings/:id} : Updates an existing ligplaatsontheffing.
     *
     * @param id the id of the ligplaatsontheffing to save.
     * @param ligplaatsontheffing the ligplaatsontheffing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligplaatsontheffing,
     * or with status {@code 400 (Bad Request)} if the ligplaatsontheffing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligplaatsontheffing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ligplaatsontheffing> updateLigplaatsontheffing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ligplaatsontheffing ligplaatsontheffing
    ) throws URISyntaxException {
        log.debug("REST request to update Ligplaatsontheffing : {}, {}", id, ligplaatsontheffing);
        if (ligplaatsontheffing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligplaatsontheffing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligplaatsontheffingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ligplaatsontheffing = ligplaatsontheffingRepository.save(ligplaatsontheffing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ligplaatsontheffing.getId().toString()))
            .body(ligplaatsontheffing);
    }

    /**
     * {@code PATCH  /ligplaatsontheffings/:id} : Partial updates given fields of an existing ligplaatsontheffing, field will ignore if it is null
     *
     * @param id the id of the ligplaatsontheffing to save.
     * @param ligplaatsontheffing the ligplaatsontheffing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligplaatsontheffing,
     * or with status {@code 400 (Bad Request)} if the ligplaatsontheffing is not valid,
     * or with status {@code 404 (Not Found)} if the ligplaatsontheffing is not found,
     * or with status {@code 500 (Internal Server Error)} if the ligplaatsontheffing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ligplaatsontheffing> partialUpdateLigplaatsontheffing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ligplaatsontheffing ligplaatsontheffing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ligplaatsontheffing partially : {}, {}", id, ligplaatsontheffing);
        if (ligplaatsontheffing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligplaatsontheffing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligplaatsontheffingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ligplaatsontheffing> result = ligplaatsontheffingRepository
            .findById(ligplaatsontheffing.getId())
            .map(existingLigplaatsontheffing -> {
                if (ligplaatsontheffing.getStickernummer() != null) {
                    existingLigplaatsontheffing.setStickernummer(ligplaatsontheffing.getStickernummer());
                }

                return existingLigplaatsontheffing;
            })
            .map(ligplaatsontheffingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ligplaatsontheffing.getId().toString())
        );
    }

    /**
     * {@code GET  /ligplaatsontheffings} : get all the ligplaatsontheffings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligplaatsontheffings in body.
     */
    @GetMapping("")
    public List<Ligplaatsontheffing> getAllLigplaatsontheffings() {
        log.debug("REST request to get all Ligplaatsontheffings");
        return ligplaatsontheffingRepository.findAll();
    }

    /**
     * {@code GET  /ligplaatsontheffings/:id} : get the "id" ligplaatsontheffing.
     *
     * @param id the id of the ligplaatsontheffing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligplaatsontheffing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ligplaatsontheffing> getLigplaatsontheffing(@PathVariable("id") Long id) {
        log.debug("REST request to get Ligplaatsontheffing : {}", id);
        Optional<Ligplaatsontheffing> ligplaatsontheffing = ligplaatsontheffingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ligplaatsontheffing);
    }

    /**
     * {@code DELETE  /ligplaatsontheffings/:id} : delete the "id" ligplaatsontheffing.
     *
     * @param id the id of the ligplaatsontheffing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigplaatsontheffing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ligplaatsontheffing : {}", id);
        ligplaatsontheffingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
