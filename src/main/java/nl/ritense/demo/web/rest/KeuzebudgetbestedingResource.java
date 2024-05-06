package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Keuzebudgetbesteding;
import nl.ritense.demo.repository.KeuzebudgetbestedingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Keuzebudgetbesteding}.
 */
@RestController
@RequestMapping("/api/keuzebudgetbestedings")
@Transactional
public class KeuzebudgetbestedingResource {

    private final Logger log = LoggerFactory.getLogger(KeuzebudgetbestedingResource.class);

    private static final String ENTITY_NAME = "keuzebudgetbesteding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeuzebudgetbestedingRepository keuzebudgetbestedingRepository;

    public KeuzebudgetbestedingResource(KeuzebudgetbestedingRepository keuzebudgetbestedingRepository) {
        this.keuzebudgetbestedingRepository = keuzebudgetbestedingRepository;
    }

    /**
     * {@code POST  /keuzebudgetbestedings} : Create a new keuzebudgetbesteding.
     *
     * @param keuzebudgetbesteding the keuzebudgetbesteding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keuzebudgetbesteding, or with status {@code 400 (Bad Request)} if the keuzebudgetbesteding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Keuzebudgetbesteding> createKeuzebudgetbesteding(@RequestBody Keuzebudgetbesteding keuzebudgetbesteding)
        throws URISyntaxException {
        log.debug("REST request to save Keuzebudgetbesteding : {}", keuzebudgetbesteding);
        if (keuzebudgetbesteding.getId() != null) {
            throw new BadRequestAlertException("A new keuzebudgetbesteding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        keuzebudgetbesteding = keuzebudgetbestedingRepository.save(keuzebudgetbesteding);
        return ResponseEntity.created(new URI("/api/keuzebudgetbestedings/" + keuzebudgetbesteding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, keuzebudgetbesteding.getId().toString()))
            .body(keuzebudgetbesteding);
    }

    /**
     * {@code PUT  /keuzebudgetbestedings/:id} : Updates an existing keuzebudgetbesteding.
     *
     * @param id the id of the keuzebudgetbesteding to save.
     * @param keuzebudgetbesteding the keuzebudgetbesteding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keuzebudgetbesteding,
     * or with status {@code 400 (Bad Request)} if the keuzebudgetbesteding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keuzebudgetbesteding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Keuzebudgetbesteding> updateKeuzebudgetbesteding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Keuzebudgetbesteding keuzebudgetbesteding
    ) throws URISyntaxException {
        log.debug("REST request to update Keuzebudgetbesteding : {}, {}", id, keuzebudgetbesteding);
        if (keuzebudgetbesteding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, keuzebudgetbesteding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!keuzebudgetbestedingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        keuzebudgetbesteding = keuzebudgetbestedingRepository.save(keuzebudgetbesteding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, keuzebudgetbesteding.getId().toString()))
            .body(keuzebudgetbesteding);
    }

    /**
     * {@code PATCH  /keuzebudgetbestedings/:id} : Partial updates given fields of an existing keuzebudgetbesteding, field will ignore if it is null
     *
     * @param id the id of the keuzebudgetbesteding to save.
     * @param keuzebudgetbesteding the keuzebudgetbesteding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keuzebudgetbesteding,
     * or with status {@code 400 (Bad Request)} if the keuzebudgetbesteding is not valid,
     * or with status {@code 404 (Not Found)} if the keuzebudgetbesteding is not found,
     * or with status {@code 500 (Internal Server Error)} if the keuzebudgetbesteding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Keuzebudgetbesteding> partialUpdateKeuzebudgetbesteding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Keuzebudgetbesteding keuzebudgetbesteding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Keuzebudgetbesteding partially : {}, {}", id, keuzebudgetbesteding);
        if (keuzebudgetbesteding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, keuzebudgetbesteding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!keuzebudgetbestedingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Keuzebudgetbesteding> result = keuzebudgetbestedingRepository
            .findById(keuzebudgetbesteding.getId())
            .map(existingKeuzebudgetbesteding -> {
                if (keuzebudgetbesteding.getBedrag() != null) {
                    existingKeuzebudgetbesteding.setBedrag(keuzebudgetbesteding.getBedrag());
                }
                if (keuzebudgetbesteding.getDatum() != null) {
                    existingKeuzebudgetbesteding.setDatum(keuzebudgetbesteding.getDatum());
                }

                return existingKeuzebudgetbesteding;
            })
            .map(keuzebudgetbestedingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, keuzebudgetbesteding.getId().toString())
        );
    }

    /**
     * {@code GET  /keuzebudgetbestedings} : get all the keuzebudgetbestedings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keuzebudgetbestedings in body.
     */
    @GetMapping("")
    public List<Keuzebudgetbesteding> getAllKeuzebudgetbestedings() {
        log.debug("REST request to get all Keuzebudgetbestedings");
        return keuzebudgetbestedingRepository.findAll();
    }

    /**
     * {@code GET  /keuzebudgetbestedings/:id} : get the "id" keuzebudgetbesteding.
     *
     * @param id the id of the keuzebudgetbesteding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keuzebudgetbesteding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Keuzebudgetbesteding> getKeuzebudgetbesteding(@PathVariable("id") Long id) {
        log.debug("REST request to get Keuzebudgetbesteding : {}", id);
        Optional<Keuzebudgetbesteding> keuzebudgetbesteding = keuzebudgetbestedingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(keuzebudgetbesteding);
    }

    /**
     * {@code DELETE  /keuzebudgetbestedings/:id} : delete the "id" keuzebudgetbesteding.
     *
     * @param id the id of the keuzebudgetbesteding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeuzebudgetbesteding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Keuzebudgetbesteding : {}", id);
        keuzebudgetbestedingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
