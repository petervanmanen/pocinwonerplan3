package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kponstaanuit;
import nl.ritense.demo.repository.KponstaanuitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kponstaanuit}.
 */
@RestController
@RequestMapping("/api/kponstaanuits")
@Transactional
public class KponstaanuitResource {

    private final Logger log = LoggerFactory.getLogger(KponstaanuitResource.class);

    private static final String ENTITY_NAME = "kponstaanuit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KponstaanuitRepository kponstaanuitRepository;

    public KponstaanuitResource(KponstaanuitRepository kponstaanuitRepository) {
        this.kponstaanuitRepository = kponstaanuitRepository;
    }

    /**
     * {@code POST  /kponstaanuits} : Create a new kponstaanuit.
     *
     * @param kponstaanuit the kponstaanuit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kponstaanuit, or with status {@code 400 (Bad Request)} if the kponstaanuit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kponstaanuit> createKponstaanuit(@RequestBody Kponstaanuit kponstaanuit) throws URISyntaxException {
        log.debug("REST request to save Kponstaanuit : {}", kponstaanuit);
        if (kponstaanuit.getId() != null) {
            throw new BadRequestAlertException("A new kponstaanuit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kponstaanuit = kponstaanuitRepository.save(kponstaanuit);
        return ResponseEntity.created(new URI("/api/kponstaanuits/" + kponstaanuit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kponstaanuit.getId().toString()))
            .body(kponstaanuit);
    }

    /**
     * {@code PUT  /kponstaanuits/:id} : Updates an existing kponstaanuit.
     *
     * @param id the id of the kponstaanuit to save.
     * @param kponstaanuit the kponstaanuit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kponstaanuit,
     * or with status {@code 400 (Bad Request)} if the kponstaanuit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kponstaanuit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kponstaanuit> updateKponstaanuit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kponstaanuit kponstaanuit
    ) throws URISyntaxException {
        log.debug("REST request to update Kponstaanuit : {}, {}", id, kponstaanuit);
        if (kponstaanuit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kponstaanuit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kponstaanuitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kponstaanuit = kponstaanuitRepository.save(kponstaanuit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kponstaanuit.getId().toString()))
            .body(kponstaanuit);
    }

    /**
     * {@code PATCH  /kponstaanuits/:id} : Partial updates given fields of an existing kponstaanuit, field will ignore if it is null
     *
     * @param id the id of the kponstaanuit to save.
     * @param kponstaanuit the kponstaanuit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kponstaanuit,
     * or with status {@code 400 (Bad Request)} if the kponstaanuit is not valid,
     * or with status {@code 404 (Not Found)} if the kponstaanuit is not found,
     * or with status {@code 500 (Internal Server Error)} if the kponstaanuit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kponstaanuit> partialUpdateKponstaanuit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kponstaanuit kponstaanuit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kponstaanuit partially : {}, {}", id, kponstaanuit);
        if (kponstaanuit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kponstaanuit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kponstaanuitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kponstaanuit> result = kponstaanuitRepository
            .findById(kponstaanuit.getId())
            .map(existingKponstaanuit -> {
                if (kponstaanuit.getDatumbegingeldigheid() != null) {
                    existingKponstaanuit.setDatumbegingeldigheid(kponstaanuit.getDatumbegingeldigheid());
                }
                if (kponstaanuit.getDatumeindegeldigheid() != null) {
                    existingKponstaanuit.setDatumeindegeldigheid(kponstaanuit.getDatumeindegeldigheid());
                }

                return existingKponstaanuit;
            })
            .map(kponstaanuitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kponstaanuit.getId().toString())
        );
    }

    /**
     * {@code GET  /kponstaanuits} : get all the kponstaanuits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kponstaanuits in body.
     */
    @GetMapping("")
    public List<Kponstaanuit> getAllKponstaanuits() {
        log.debug("REST request to get all Kponstaanuits");
        return kponstaanuitRepository.findAll();
    }

    /**
     * {@code GET  /kponstaanuits/:id} : get the "id" kponstaanuit.
     *
     * @param id the id of the kponstaanuit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kponstaanuit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kponstaanuit> getKponstaanuit(@PathVariable("id") Long id) {
        log.debug("REST request to get Kponstaanuit : {}", id);
        Optional<Kponstaanuit> kponstaanuit = kponstaanuitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kponstaanuit);
    }

    /**
     * {@code DELETE  /kponstaanuits/:id} : delete the "id" kponstaanuit.
     *
     * @param id the id of the kponstaanuit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKponstaanuit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kponstaanuit : {}", id);
        kponstaanuitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
