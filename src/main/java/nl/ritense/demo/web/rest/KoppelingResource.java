package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Koppeling;
import nl.ritense.demo.repository.KoppelingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Koppeling}.
 */
@RestController
@RequestMapping("/api/koppelings")
@Transactional
public class KoppelingResource {

    private final Logger log = LoggerFactory.getLogger(KoppelingResource.class);

    private static final String ENTITY_NAME = "koppeling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KoppelingRepository koppelingRepository;

    public KoppelingResource(KoppelingRepository koppelingRepository) {
        this.koppelingRepository = koppelingRepository;
    }

    /**
     * {@code POST  /koppelings} : Create a new koppeling.
     *
     * @param koppeling the koppeling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new koppeling, or with status {@code 400 (Bad Request)} if the koppeling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Koppeling> createKoppeling(@RequestBody Koppeling koppeling) throws URISyntaxException {
        log.debug("REST request to save Koppeling : {}", koppeling);
        if (koppeling.getId() != null) {
            throw new BadRequestAlertException("A new koppeling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        koppeling = koppelingRepository.save(koppeling);
        return ResponseEntity.created(new URI("/api/koppelings/" + koppeling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, koppeling.getId().toString()))
            .body(koppeling);
    }

    /**
     * {@code PUT  /koppelings/:id} : Updates an existing koppeling.
     *
     * @param id the id of the koppeling to save.
     * @param koppeling the koppeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koppeling,
     * or with status {@code 400 (Bad Request)} if the koppeling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the koppeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Koppeling> updateKoppeling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Koppeling koppeling
    ) throws URISyntaxException {
        log.debug("REST request to update Koppeling : {}, {}", id, koppeling);
        if (koppeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koppeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koppelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        koppeling = koppelingRepository.save(koppeling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, koppeling.getId().toString()))
            .body(koppeling);
    }

    /**
     * {@code PATCH  /koppelings/:id} : Partial updates given fields of an existing koppeling, field will ignore if it is null
     *
     * @param id the id of the koppeling to save.
     * @param koppeling the koppeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated koppeling,
     * or with status {@code 400 (Bad Request)} if the koppeling is not valid,
     * or with status {@code 404 (Not Found)} if the koppeling is not found,
     * or with status {@code 500 (Internal Server Error)} if the koppeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Koppeling> partialUpdateKoppeling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Koppeling koppeling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Koppeling partially : {}, {}", id, koppeling);
        if (koppeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, koppeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!koppelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Koppeling> result = koppelingRepository
            .findById(koppeling.getId())
            .map(existingKoppeling -> {
                if (koppeling.getBeschrijving() != null) {
                    existingKoppeling.setBeschrijving(koppeling.getBeschrijving());
                }
                if (koppeling.getDirect() != null) {
                    existingKoppeling.setDirect(koppeling.getDirect());
                }
                if (koppeling.getToelichting() != null) {
                    existingKoppeling.setToelichting(koppeling.getToelichting());
                }

                return existingKoppeling;
            })
            .map(koppelingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, koppeling.getId().toString())
        );
    }

    /**
     * {@code GET  /koppelings} : get all the koppelings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of koppelings in body.
     */
    @GetMapping("")
    public List<Koppeling> getAllKoppelings() {
        log.debug("REST request to get all Koppelings");
        return koppelingRepository.findAll();
    }

    /**
     * {@code GET  /koppelings/:id} : get the "id" koppeling.
     *
     * @param id the id of the koppeling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the koppeling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Koppeling> getKoppeling(@PathVariable("id") Long id) {
        log.debug("REST request to get Koppeling : {}", id);
        Optional<Koppeling> koppeling = koppelingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(koppeling);
    }

    /**
     * {@code DELETE  /koppelings/:id} : delete the "id" koppeling.
     *
     * @param id the id of the koppeling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKoppeling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Koppeling : {}", id);
        koppelingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
