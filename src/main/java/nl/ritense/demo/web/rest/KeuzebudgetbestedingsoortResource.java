package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Keuzebudgetbestedingsoort;
import nl.ritense.demo.repository.KeuzebudgetbestedingsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Keuzebudgetbestedingsoort}.
 */
@RestController
@RequestMapping("/api/keuzebudgetbestedingsoorts")
@Transactional
public class KeuzebudgetbestedingsoortResource {

    private final Logger log = LoggerFactory.getLogger(KeuzebudgetbestedingsoortResource.class);

    private static final String ENTITY_NAME = "keuzebudgetbestedingsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KeuzebudgetbestedingsoortRepository keuzebudgetbestedingsoortRepository;

    public KeuzebudgetbestedingsoortResource(KeuzebudgetbestedingsoortRepository keuzebudgetbestedingsoortRepository) {
        this.keuzebudgetbestedingsoortRepository = keuzebudgetbestedingsoortRepository;
    }

    /**
     * {@code POST  /keuzebudgetbestedingsoorts} : Create a new keuzebudgetbestedingsoort.
     *
     * @param keuzebudgetbestedingsoort the keuzebudgetbestedingsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new keuzebudgetbestedingsoort, or with status {@code 400 (Bad Request)} if the keuzebudgetbestedingsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Keuzebudgetbestedingsoort> createKeuzebudgetbestedingsoort(
        @RequestBody Keuzebudgetbestedingsoort keuzebudgetbestedingsoort
    ) throws URISyntaxException {
        log.debug("REST request to save Keuzebudgetbestedingsoort : {}", keuzebudgetbestedingsoort);
        if (keuzebudgetbestedingsoort.getId() != null) {
            throw new BadRequestAlertException("A new keuzebudgetbestedingsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        keuzebudgetbestedingsoort = keuzebudgetbestedingsoortRepository.save(keuzebudgetbestedingsoort);
        return ResponseEntity.created(new URI("/api/keuzebudgetbestedingsoorts/" + keuzebudgetbestedingsoort.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, keuzebudgetbestedingsoort.getId().toString())
            )
            .body(keuzebudgetbestedingsoort);
    }

    /**
     * {@code PUT  /keuzebudgetbestedingsoorts/:id} : Updates an existing keuzebudgetbestedingsoort.
     *
     * @param id the id of the keuzebudgetbestedingsoort to save.
     * @param keuzebudgetbestedingsoort the keuzebudgetbestedingsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keuzebudgetbestedingsoort,
     * or with status {@code 400 (Bad Request)} if the keuzebudgetbestedingsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the keuzebudgetbestedingsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Keuzebudgetbestedingsoort> updateKeuzebudgetbestedingsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Keuzebudgetbestedingsoort keuzebudgetbestedingsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Keuzebudgetbestedingsoort : {}, {}", id, keuzebudgetbestedingsoort);
        if (keuzebudgetbestedingsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, keuzebudgetbestedingsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!keuzebudgetbestedingsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        keuzebudgetbestedingsoort = keuzebudgetbestedingsoortRepository.save(keuzebudgetbestedingsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, keuzebudgetbestedingsoort.getId().toString()))
            .body(keuzebudgetbestedingsoort);
    }

    /**
     * {@code PATCH  /keuzebudgetbestedingsoorts/:id} : Partial updates given fields of an existing keuzebudgetbestedingsoort, field will ignore if it is null
     *
     * @param id the id of the keuzebudgetbestedingsoort to save.
     * @param keuzebudgetbestedingsoort the keuzebudgetbestedingsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated keuzebudgetbestedingsoort,
     * or with status {@code 400 (Bad Request)} if the keuzebudgetbestedingsoort is not valid,
     * or with status {@code 404 (Not Found)} if the keuzebudgetbestedingsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the keuzebudgetbestedingsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Keuzebudgetbestedingsoort> partialUpdateKeuzebudgetbestedingsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Keuzebudgetbestedingsoort keuzebudgetbestedingsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Keuzebudgetbestedingsoort partially : {}, {}", id, keuzebudgetbestedingsoort);
        if (keuzebudgetbestedingsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, keuzebudgetbestedingsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!keuzebudgetbestedingsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Keuzebudgetbestedingsoort> result = keuzebudgetbestedingsoortRepository
            .findById(keuzebudgetbestedingsoort.getId())
            .map(existingKeuzebudgetbestedingsoort -> {
                if (keuzebudgetbestedingsoort.getNaam() != null) {
                    existingKeuzebudgetbestedingsoort.setNaam(keuzebudgetbestedingsoort.getNaam());
                }
                if (keuzebudgetbestedingsoort.getOmschrijving() != null) {
                    existingKeuzebudgetbestedingsoort.setOmschrijving(keuzebudgetbestedingsoort.getOmschrijving());
                }

                return existingKeuzebudgetbestedingsoort;
            })
            .map(keuzebudgetbestedingsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, keuzebudgetbestedingsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /keuzebudgetbestedingsoorts} : get all the keuzebudgetbestedingsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of keuzebudgetbestedingsoorts in body.
     */
    @GetMapping("")
    public List<Keuzebudgetbestedingsoort> getAllKeuzebudgetbestedingsoorts() {
        log.debug("REST request to get all Keuzebudgetbestedingsoorts");
        return keuzebudgetbestedingsoortRepository.findAll();
    }

    /**
     * {@code GET  /keuzebudgetbestedingsoorts/:id} : get the "id" keuzebudgetbestedingsoort.
     *
     * @param id the id of the keuzebudgetbestedingsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the keuzebudgetbestedingsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Keuzebudgetbestedingsoort> getKeuzebudgetbestedingsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Keuzebudgetbestedingsoort : {}", id);
        Optional<Keuzebudgetbestedingsoort> keuzebudgetbestedingsoort = keuzebudgetbestedingsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(keuzebudgetbestedingsoort);
    }

    /**
     * {@code DELETE  /keuzebudgetbestedingsoorts/:id} : delete the "id" keuzebudgetbestedingsoort.
     *
     * @param id the id of the keuzebudgetbestedingsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeuzebudgetbestedingsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Keuzebudgetbestedingsoort : {}", id);
        keuzebudgetbestedingsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
