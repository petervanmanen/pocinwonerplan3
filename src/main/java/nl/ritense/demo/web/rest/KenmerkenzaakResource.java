package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kenmerkenzaak;
import nl.ritense.demo.repository.KenmerkenzaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kenmerkenzaak}.
 */
@RestController
@RequestMapping("/api/kenmerkenzaaks")
@Transactional
public class KenmerkenzaakResource {

    private final Logger log = LoggerFactory.getLogger(KenmerkenzaakResource.class);

    private static final String ENTITY_NAME = "kenmerkenzaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KenmerkenzaakRepository kenmerkenzaakRepository;

    public KenmerkenzaakResource(KenmerkenzaakRepository kenmerkenzaakRepository) {
        this.kenmerkenzaakRepository = kenmerkenzaakRepository;
    }

    /**
     * {@code POST  /kenmerkenzaaks} : Create a new kenmerkenzaak.
     *
     * @param kenmerkenzaak the kenmerkenzaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kenmerkenzaak, or with status {@code 400 (Bad Request)} if the kenmerkenzaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kenmerkenzaak> createKenmerkenzaak(@RequestBody Kenmerkenzaak kenmerkenzaak) throws URISyntaxException {
        log.debug("REST request to save Kenmerkenzaak : {}", kenmerkenzaak);
        if (kenmerkenzaak.getId() != null) {
            throw new BadRequestAlertException("A new kenmerkenzaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kenmerkenzaak = kenmerkenzaakRepository.save(kenmerkenzaak);
        return ResponseEntity.created(new URI("/api/kenmerkenzaaks/" + kenmerkenzaak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kenmerkenzaak.getId().toString()))
            .body(kenmerkenzaak);
    }

    /**
     * {@code PUT  /kenmerkenzaaks/:id} : Updates an existing kenmerkenzaak.
     *
     * @param id the id of the kenmerkenzaak to save.
     * @param kenmerkenzaak the kenmerkenzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kenmerkenzaak,
     * or with status {@code 400 (Bad Request)} if the kenmerkenzaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kenmerkenzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kenmerkenzaak> updateKenmerkenzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kenmerkenzaak kenmerkenzaak
    ) throws URISyntaxException {
        log.debug("REST request to update Kenmerkenzaak : {}, {}", id, kenmerkenzaak);
        if (kenmerkenzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kenmerkenzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kenmerkenzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kenmerkenzaak = kenmerkenzaakRepository.save(kenmerkenzaak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kenmerkenzaak.getId().toString()))
            .body(kenmerkenzaak);
    }

    /**
     * {@code PATCH  /kenmerkenzaaks/:id} : Partial updates given fields of an existing kenmerkenzaak, field will ignore if it is null
     *
     * @param id the id of the kenmerkenzaak to save.
     * @param kenmerkenzaak the kenmerkenzaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kenmerkenzaak,
     * or with status {@code 400 (Bad Request)} if the kenmerkenzaak is not valid,
     * or with status {@code 404 (Not Found)} if the kenmerkenzaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the kenmerkenzaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kenmerkenzaak> partialUpdateKenmerkenzaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kenmerkenzaak kenmerkenzaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kenmerkenzaak partially : {}, {}", id, kenmerkenzaak);
        if (kenmerkenzaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kenmerkenzaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kenmerkenzaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kenmerkenzaak> result = kenmerkenzaakRepository
            .findById(kenmerkenzaak.getId())
            .map(existingKenmerkenzaak -> {
                if (kenmerkenzaak.getKenmerk() != null) {
                    existingKenmerkenzaak.setKenmerk(kenmerkenzaak.getKenmerk());
                }
                if (kenmerkenzaak.getKenmerkbron() != null) {
                    existingKenmerkenzaak.setKenmerkbron(kenmerkenzaak.getKenmerkbron());
                }

                return existingKenmerkenzaak;
            })
            .map(kenmerkenzaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kenmerkenzaak.getId().toString())
        );
    }

    /**
     * {@code GET  /kenmerkenzaaks} : get all the kenmerkenzaaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kenmerkenzaaks in body.
     */
    @GetMapping("")
    public List<Kenmerkenzaak> getAllKenmerkenzaaks() {
        log.debug("REST request to get all Kenmerkenzaaks");
        return kenmerkenzaakRepository.findAll();
    }

    /**
     * {@code GET  /kenmerkenzaaks/:id} : get the "id" kenmerkenzaak.
     *
     * @param id the id of the kenmerkenzaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kenmerkenzaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kenmerkenzaak> getKenmerkenzaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Kenmerkenzaak : {}", id);
        Optional<Kenmerkenzaak> kenmerkenzaak = kenmerkenzaakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kenmerkenzaak);
    }

    /**
     * {@code DELETE  /kenmerkenzaaks/:id} : delete the "id" kenmerkenzaak.
     *
     * @param id the id of the kenmerkenzaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKenmerkenzaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kenmerkenzaak : {}", id);
        kenmerkenzaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
