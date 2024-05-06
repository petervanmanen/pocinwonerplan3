package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kwalificatie;
import nl.ritense.demo.repository.KwalificatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kwalificatie}.
 */
@RestController
@RequestMapping("/api/kwalificaties")
@Transactional
public class KwalificatieResource {

    private final Logger log = LoggerFactory.getLogger(KwalificatieResource.class);

    private static final String ENTITY_NAME = "kwalificatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KwalificatieRepository kwalificatieRepository;

    public KwalificatieResource(KwalificatieRepository kwalificatieRepository) {
        this.kwalificatieRepository = kwalificatieRepository;
    }

    /**
     * {@code POST  /kwalificaties} : Create a new kwalificatie.
     *
     * @param kwalificatie the kwalificatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kwalificatie, or with status {@code 400 (Bad Request)} if the kwalificatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kwalificatie> createKwalificatie(@RequestBody Kwalificatie kwalificatie) throws URISyntaxException {
        log.debug("REST request to save Kwalificatie : {}", kwalificatie);
        if (kwalificatie.getId() != null) {
            throw new BadRequestAlertException("A new kwalificatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kwalificatie = kwalificatieRepository.save(kwalificatie);
        return ResponseEntity.created(new URI("/api/kwalificaties/" + kwalificatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kwalificatie.getId().toString()))
            .body(kwalificatie);
    }

    /**
     * {@code PUT  /kwalificaties/:id} : Updates an existing kwalificatie.
     *
     * @param id the id of the kwalificatie to save.
     * @param kwalificatie the kwalificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kwalificatie,
     * or with status {@code 400 (Bad Request)} if the kwalificatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kwalificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kwalificatie> updateKwalificatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kwalificatie kwalificatie
    ) throws URISyntaxException {
        log.debug("REST request to update Kwalificatie : {}, {}", id, kwalificatie);
        if (kwalificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kwalificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kwalificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kwalificatie = kwalificatieRepository.save(kwalificatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kwalificatie.getId().toString()))
            .body(kwalificatie);
    }

    /**
     * {@code PATCH  /kwalificaties/:id} : Partial updates given fields of an existing kwalificatie, field will ignore if it is null
     *
     * @param id the id of the kwalificatie to save.
     * @param kwalificatie the kwalificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kwalificatie,
     * or with status {@code 400 (Bad Request)} if the kwalificatie is not valid,
     * or with status {@code 404 (Not Found)} if the kwalificatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the kwalificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kwalificatie> partialUpdateKwalificatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kwalificatie kwalificatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kwalificatie partially : {}, {}", id, kwalificatie);
        if (kwalificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kwalificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kwalificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kwalificatie> result = kwalificatieRepository
            .findById(kwalificatie.getId())
            .map(existingKwalificatie -> {
                if (kwalificatie.getEindegeldigheid() != null) {
                    existingKwalificatie.setEindegeldigheid(kwalificatie.getEindegeldigheid());
                }
                if (kwalificatie.getStartgeldigheid() != null) {
                    existingKwalificatie.setStartgeldigheid(kwalificatie.getStartgeldigheid());
                }

                return existingKwalificatie;
            })
            .map(kwalificatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kwalificatie.getId().toString())
        );
    }

    /**
     * {@code GET  /kwalificaties} : get all the kwalificaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kwalificaties in body.
     */
    @GetMapping("")
    public List<Kwalificatie> getAllKwalificaties() {
        log.debug("REST request to get all Kwalificaties");
        return kwalificatieRepository.findAll();
    }

    /**
     * {@code GET  /kwalificaties/:id} : get the "id" kwalificatie.
     *
     * @param id the id of the kwalificatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kwalificatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kwalificatie> getKwalificatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Kwalificatie : {}", id);
        Optional<Kwalificatie> kwalificatie = kwalificatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kwalificatie);
    }

    /**
     * {@code DELETE  /kwalificaties/:id} : delete the "id" kwalificatie.
     *
     * @param id the id of the kwalificatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKwalificatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kwalificatie : {}", id);
        kwalificatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
