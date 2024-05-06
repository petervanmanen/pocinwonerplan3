package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verlengingzaak;
import nl.ritense.demo.repository.VerlengingzaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verlengingzaak}.
 */
@RestController
@RequestMapping("/api/verlengingzaaks")
@Transactional
public class VerlengingzaakResource {

    private final Logger log = LoggerFactory.getLogger(VerlengingzaakResource.class);

    private static final String ENTITY_NAME = "verlengingzaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerlengingzaakRepository verlengingzaakRepository;

    public VerlengingzaakResource(VerlengingzaakRepository verlengingzaakRepository) {
        this.verlengingzaakRepository = verlengingzaakRepository;
    }

    /**
     * {@code POST  /verlengingzaaks} : Create a new verlengingzaak.
     *
     * @param verlengingzaak the verlengingzaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verlengingzaak, or with status {@code 400 (Bad Request)} if the verlengingzaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verlengingzaak> createVerlengingzaak(@RequestBody Verlengingzaak verlengingzaak) throws URISyntaxException {
        log.debug("REST request to save Verlengingzaak : {}", verlengingzaak);
        if (verlengingzaak.getId() != null) {
            throw new BadRequestAlertException("A new verlengingzaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verlengingzaak = verlengingzaakRepository.save(verlengingzaak);
        return ResponseEntity.created(new URI("/api/verlengingzaaks/" + verlengingzaak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verlengingzaak.getId().toString()))
            .body(verlengingzaak);
    }

    /**
     * {@code PUT  /verlengingzaaks/:id} : Updates an existing verlengingzaak.
     *
     * @param id the id of the verlengingzaak to save.
     * @param verlengingzaak the verlengingzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verlengingzaak,
     * or with status {@code 400 (Bad Request)} if the verlengingzaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verlengingzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verlengingzaak> updateVerlengingzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verlengingzaak verlengingzaak
    ) throws URISyntaxException {
        log.debug("REST request to update Verlengingzaak : {}, {}", id, verlengingzaak);
        if (verlengingzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verlengingzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verlengingzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verlengingzaak = verlengingzaakRepository.save(verlengingzaak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verlengingzaak.getId().toString()))
            .body(verlengingzaak);
    }

    /**
     * {@code PATCH  /verlengingzaaks/:id} : Partial updates given fields of an existing verlengingzaak, field will ignore if it is null
     *
     * @param id the id of the verlengingzaak to save.
     * @param verlengingzaak the verlengingzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verlengingzaak,
     * or with status {@code 400 (Bad Request)} if the verlengingzaak is not valid,
     * or with status {@code 404 (Not Found)} if the verlengingzaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the verlengingzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verlengingzaak> partialUpdateVerlengingzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verlengingzaak verlengingzaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verlengingzaak partially : {}, {}", id, verlengingzaak);
        if (verlengingzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verlengingzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verlengingzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verlengingzaak> result = verlengingzaakRepository
            .findById(verlengingzaak.getId())
            .map(existingVerlengingzaak -> {
                if (verlengingzaak.getDuurverlenging() != null) {
                    existingVerlengingzaak.setDuurverlenging(verlengingzaak.getDuurverlenging());
                }
                if (verlengingzaak.getRedenverlenging() != null) {
                    existingVerlengingzaak.setRedenverlenging(verlengingzaak.getRedenverlenging());
                }

                return existingVerlengingzaak;
            })
            .map(verlengingzaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verlengingzaak.getId().toString())
        );
    }

    /**
     * {@code GET  /verlengingzaaks} : get all the verlengingzaaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verlengingzaaks in body.
     */
    @GetMapping("")
    public List<Verlengingzaak> getAllVerlengingzaaks() {
        log.debug("REST request to get all Verlengingzaaks");
        return verlengingzaakRepository.findAll();
    }

    /**
     * {@code GET  /verlengingzaaks/:id} : get the "id" verlengingzaak.
     *
     * @param id the id of the verlengingzaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verlengingzaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verlengingzaak> getVerlengingzaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Verlengingzaak : {}", id);
        Optional<Verlengingzaak> verlengingzaak = verlengingzaakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verlengingzaak);
    }

    /**
     * {@code DELETE  /verlengingzaaks/:id} : delete the "id" verlengingzaak.
     *
     * @param id the id of the verlengingzaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerlengingzaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verlengingzaak : {}", id);
        verlengingzaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
