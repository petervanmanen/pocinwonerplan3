package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kademuur;
import nl.ritense.demo.repository.KademuurRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kademuur}.
 */
@RestController
@RequestMapping("/api/kademuurs")
@Transactional
public class KademuurResource {

    private final Logger log = LoggerFactory.getLogger(KademuurResource.class);

    private static final String ENTITY_NAME = "kademuur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KademuurRepository kademuurRepository;

    public KademuurResource(KademuurRepository kademuurRepository) {
        this.kademuurRepository = kademuurRepository;
    }

    /**
     * {@code POST  /kademuurs} : Create a new kademuur.
     *
     * @param kademuur the kademuur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kademuur, or with status {@code 400 (Bad Request)} if the kademuur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kademuur> createKademuur(@RequestBody Kademuur kademuur) throws URISyntaxException {
        log.debug("REST request to save Kademuur : {}", kademuur);
        if (kademuur.getId() != null) {
            throw new BadRequestAlertException("A new kademuur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kademuur = kademuurRepository.save(kademuur);
        return ResponseEntity.created(new URI("/api/kademuurs/" + kademuur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kademuur.getId().toString()))
            .body(kademuur);
    }

    /**
     * {@code PUT  /kademuurs/:id} : Updates an existing kademuur.
     *
     * @param id the id of the kademuur to save.
     * @param kademuur the kademuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kademuur,
     * or with status {@code 400 (Bad Request)} if the kademuur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kademuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kademuur> updateKademuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kademuur kademuur
    ) throws URISyntaxException {
        log.debug("REST request to update Kademuur : {}, {}", id, kademuur);
        if (kademuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kademuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kademuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kademuur = kademuurRepository.save(kademuur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kademuur.getId().toString()))
            .body(kademuur);
    }

    /**
     * {@code PATCH  /kademuurs/:id} : Partial updates given fields of an existing kademuur, field will ignore if it is null
     *
     * @param id the id of the kademuur to save.
     * @param kademuur the kademuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kademuur,
     * or with status {@code 400 (Bad Request)} if the kademuur is not valid,
     * or with status {@code 404 (Not Found)} if the kademuur is not found,
     * or with status {@code 500 (Internal Server Error)} if the kademuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kademuur> partialUpdateKademuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Kademuur kademuur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kademuur partially : {}, {}", id, kademuur);
        if (kademuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kademuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kademuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kademuur> result = kademuurRepository
            .findById(kademuur.getId())
            .map(existingKademuur -> {
                if (kademuur.getBelastingklassenieuw() != null) {
                    existingKademuur.setBelastingklassenieuw(kademuur.getBelastingklassenieuw());
                }
                if (kademuur.getBelastingklasseoud() != null) {
                    existingKademuur.setBelastingklasseoud(kademuur.getBelastingklasseoud());
                }
                if (kademuur.getGrijpstenen() != null) {
                    existingKademuur.setGrijpstenen(kademuur.getGrijpstenen());
                }
                if (kademuur.getHoogtebovenkantkademuur() != null) {
                    existingKademuur.setHoogtebovenkantkademuur(kademuur.getHoogtebovenkantkademuur());
                }
                if (kademuur.getMateriaalbovenkantkademuur() != null) {
                    existingKademuur.setMateriaalbovenkantkademuur(kademuur.getMateriaalbovenkantkademuur());
                }
                if (kademuur.getOppervlaktebovenkantkademuur() != null) {
                    existingKademuur.setOppervlaktebovenkantkademuur(kademuur.getOppervlaktebovenkantkademuur());
                }
                if (kademuur.getReddingslijn() != null) {
                    existingKademuur.setReddingslijn(kademuur.getReddingslijn());
                }
                if (kademuur.getType() != null) {
                    existingKademuur.setType(kademuur.getType());
                }
                if (kademuur.getTypebovenkantkademuur() != null) {
                    existingKademuur.setTypebovenkantkademuur(kademuur.getTypebovenkantkademuur());
                }
                if (kademuur.getTypefundering() != null) {
                    existingKademuur.setTypefundering(kademuur.getTypefundering());
                }
                if (kademuur.getTypeverankering() != null) {
                    existingKademuur.setTypeverankering(kademuur.getTypeverankering());
                }

                return existingKademuur;
            })
            .map(kademuurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kademuur.getId().toString())
        );
    }

    /**
     * {@code GET  /kademuurs} : get all the kademuurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kademuurs in body.
     */
    @GetMapping("")
    public List<Kademuur> getAllKademuurs() {
        log.debug("REST request to get all Kademuurs");
        return kademuurRepository.findAll();
    }

    /**
     * {@code GET  /kademuurs/:id} : get the "id" kademuur.
     *
     * @param id the id of the kademuur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kademuur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kademuur> getKademuur(@PathVariable("id") Long id) {
        log.debug("REST request to get Kademuur : {}", id);
        Optional<Kademuur> kademuur = kademuurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kademuur);
    }

    /**
     * {@code DELETE  /kademuurs/:id} : delete the "id" kademuur.
     *
     * @param id the id of the kademuur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKademuur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kademuur : {}", id);
        kademuurRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
