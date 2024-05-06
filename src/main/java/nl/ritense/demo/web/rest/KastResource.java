package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kast;
import nl.ritense.demo.repository.KastRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kast}.
 */
@RestController
@RequestMapping("/api/kasts")
@Transactional
public class KastResource {

    private final Logger log = LoggerFactory.getLogger(KastResource.class);

    private static final String ENTITY_NAME = "kast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KastRepository kastRepository;

    public KastResource(KastRepository kastRepository) {
        this.kastRepository = kastRepository;
    }

    /**
     * {@code POST  /kasts} : Create a new kast.
     *
     * @param kast the kast to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kast, or with status {@code 400 (Bad Request)} if the kast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kast> createKast(@Valid @RequestBody Kast kast) throws URISyntaxException {
        log.debug("REST request to save Kast : {}", kast);
        if (kast.getId() != null) {
            throw new BadRequestAlertException("A new kast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kast = kastRepository.save(kast);
        return ResponseEntity.created(new URI("/api/kasts/" + kast.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kast.getId().toString()))
            .body(kast);
    }

    /**
     * {@code PUT  /kasts/:id} : Updates an existing kast.
     *
     * @param id the id of the kast to save.
     * @param kast the kast to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kast,
     * or with status {@code 400 (Bad Request)} if the kast is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kast couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kast> updateKast(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Kast kast)
        throws URISyntaxException {
        log.debug("REST request to update Kast : {}, {}", id, kast);
        if (kast.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kast.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kast = kastRepository.save(kast);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kast.getId().toString()))
            .body(kast);
    }

    /**
     * {@code PATCH  /kasts/:id} : Partial updates given fields of an existing kast, field will ignore if it is null
     *
     * @param id the id of the kast to save.
     * @param kast the kast to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kast,
     * or with status {@code 400 (Bad Request)} if the kast is not valid,
     * or with status {@code 404 (Not Found)} if the kast is not found,
     * or with status {@code 500 (Internal Server Error)} if the kast couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kast> partialUpdateKast(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Kast kast
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kast partially : {}, {}", id, kast);
        if (kast.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kast.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kast> result = kastRepository
            .findById(kast.getId())
            .map(existingKast -> {
                if (kast.getKastnummer() != null) {
                    existingKast.setKastnummer(kast.getKastnummer());
                }

                return existingKast;
            })
            .map(kastRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kast.getId().toString())
        );
    }

    /**
     * {@code GET  /kasts} : get all the kasts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kasts in body.
     */
    @GetMapping("")
    public List<Kast> getAllKasts() {
        log.debug("REST request to get all Kasts");
        return kastRepository.findAll();
    }

    /**
     * {@code GET  /kasts/:id} : get the "id" kast.
     *
     * @param id the id of the kast to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kast, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kast> getKast(@PathVariable("id") Long id) {
        log.debug("REST request to get Kast : {}", id);
        Optional<Kast> kast = kastRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kast);
    }

    /**
     * {@code DELETE  /kasts/:id} : delete the "id" kast.
     *
     * @param id the id of the kast to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKast(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kast : {}", id);
        kastRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
