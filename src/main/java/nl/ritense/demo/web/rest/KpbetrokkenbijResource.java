package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kpbetrokkenbij;
import nl.ritense.demo.repository.KpbetrokkenbijRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kpbetrokkenbij}.
 */
@RestController
@RequestMapping("/api/kpbetrokkenbijs")
@Transactional
public class KpbetrokkenbijResource {

    private final Logger log = LoggerFactory.getLogger(KpbetrokkenbijResource.class);

    private static final String ENTITY_NAME = "kpbetrokkenbij";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KpbetrokkenbijRepository kpbetrokkenbijRepository;

    public KpbetrokkenbijResource(KpbetrokkenbijRepository kpbetrokkenbijRepository) {
        this.kpbetrokkenbijRepository = kpbetrokkenbijRepository;
    }

    /**
     * {@code POST  /kpbetrokkenbijs} : Create a new kpbetrokkenbij.
     *
     * @param kpbetrokkenbij the kpbetrokkenbij to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kpbetrokkenbij, or with status {@code 400 (Bad Request)} if the kpbetrokkenbij has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kpbetrokkenbij> createKpbetrokkenbij(@RequestBody Kpbetrokkenbij kpbetrokkenbij) throws URISyntaxException {
        log.debug("REST request to save Kpbetrokkenbij : {}", kpbetrokkenbij);
        if (kpbetrokkenbij.getId() != null) {
            throw new BadRequestAlertException("A new kpbetrokkenbij cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kpbetrokkenbij = kpbetrokkenbijRepository.save(kpbetrokkenbij);
        return ResponseEntity.created(new URI("/api/kpbetrokkenbijs/" + kpbetrokkenbij.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kpbetrokkenbij.getId().toString()))
            .body(kpbetrokkenbij);
    }

    /**
     * {@code PUT  /kpbetrokkenbijs/:id} : Updates an existing kpbetrokkenbij.
     *
     * @param id the id of the kpbetrokkenbij to save.
     * @param kpbetrokkenbij the kpbetrokkenbij to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpbetrokkenbij,
     * or with status {@code 400 (Bad Request)} if the kpbetrokkenbij is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kpbetrokkenbij couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kpbetrokkenbij> updateKpbetrokkenbij(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kpbetrokkenbij kpbetrokkenbij
    ) throws URISyntaxException {
        log.debug("REST request to update Kpbetrokkenbij : {}, {}", id, kpbetrokkenbij);
        if (kpbetrokkenbij.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpbetrokkenbij.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpbetrokkenbijRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kpbetrokkenbij = kpbetrokkenbijRepository.save(kpbetrokkenbij);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpbetrokkenbij.getId().toString()))
            .body(kpbetrokkenbij);
    }

    /**
     * {@code PATCH  /kpbetrokkenbijs/:id} : Partial updates given fields of an existing kpbetrokkenbij, field will ignore if it is null
     *
     * @param id the id of the kpbetrokkenbij to save.
     * @param kpbetrokkenbij the kpbetrokkenbij to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpbetrokkenbij,
     * or with status {@code 400 (Bad Request)} if the kpbetrokkenbij is not valid,
     * or with status {@code 404 (Not Found)} if the kpbetrokkenbij is not found,
     * or with status {@code 500 (Internal Server Error)} if the kpbetrokkenbij couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kpbetrokkenbij> partialUpdateKpbetrokkenbij(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kpbetrokkenbij kpbetrokkenbij
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kpbetrokkenbij partially : {}, {}", id, kpbetrokkenbij);
        if (kpbetrokkenbij.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpbetrokkenbij.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpbetrokkenbijRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kpbetrokkenbij> result = kpbetrokkenbijRepository
            .findById(kpbetrokkenbij.getId())
            .map(existingKpbetrokkenbij -> {
                if (kpbetrokkenbij.getDatumbegingeldigheid() != null) {
                    existingKpbetrokkenbij.setDatumbegingeldigheid(kpbetrokkenbij.getDatumbegingeldigheid());
                }
                if (kpbetrokkenbij.getDatumeindegeldigheid() != null) {
                    existingKpbetrokkenbij.setDatumeindegeldigheid(kpbetrokkenbij.getDatumeindegeldigheid());
                }

                return existingKpbetrokkenbij;
            })
            .map(kpbetrokkenbijRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpbetrokkenbij.getId().toString())
        );
    }

    /**
     * {@code GET  /kpbetrokkenbijs} : get all the kpbetrokkenbijs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kpbetrokkenbijs in body.
     */
    @GetMapping("")
    public List<Kpbetrokkenbij> getAllKpbetrokkenbijs() {
        log.debug("REST request to get all Kpbetrokkenbijs");
        return kpbetrokkenbijRepository.findAll();
    }

    /**
     * {@code GET  /kpbetrokkenbijs/:id} : get the "id" kpbetrokkenbij.
     *
     * @param id the id of the kpbetrokkenbij to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kpbetrokkenbij, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kpbetrokkenbij> getKpbetrokkenbij(@PathVariable("id") Long id) {
        log.debug("REST request to get Kpbetrokkenbij : {}", id);
        Optional<Kpbetrokkenbij> kpbetrokkenbij = kpbetrokkenbijRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kpbetrokkenbij);
    }

    /**
     * {@code DELETE  /kpbetrokkenbijs/:id} : delete the "id" kpbetrokkenbij.
     *
     * @param id the id of the kpbetrokkenbij to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKpbetrokkenbij(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kpbetrokkenbij : {}", id);
        kpbetrokkenbijRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
