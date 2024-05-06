package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kolk;
import nl.ritense.demo.repository.KolkRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kolk}.
 */
@RestController
@RequestMapping("/api/kolks")
@Transactional
public class KolkResource {

    private final Logger log = LoggerFactory.getLogger(KolkResource.class);

    private static final String ENTITY_NAME = "kolk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KolkRepository kolkRepository;

    public KolkResource(KolkRepository kolkRepository) {
        this.kolkRepository = kolkRepository;
    }

    /**
     * {@code POST  /kolks} : Create a new kolk.
     *
     * @param kolk the kolk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kolk, or with status {@code 400 (Bad Request)} if the kolk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kolk> createKolk(@RequestBody Kolk kolk) throws URISyntaxException {
        log.debug("REST request to save Kolk : {}", kolk);
        if (kolk.getId() != null) {
            throw new BadRequestAlertException("A new kolk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kolk = kolkRepository.save(kolk);
        return ResponseEntity.created(new URI("/api/kolks/" + kolk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kolk.getId().toString()))
            .body(kolk);
    }

    /**
     * {@code PUT  /kolks/:id} : Updates an existing kolk.
     *
     * @param id the id of the kolk to save.
     * @param kolk the kolk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kolk,
     * or with status {@code 400 (Bad Request)} if the kolk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kolk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kolk> updateKolk(@PathVariable(value = "id", required = false) final Long id, @RequestBody Kolk kolk)
        throws URISyntaxException {
        log.debug("REST request to update Kolk : {}, {}", id, kolk);
        if (kolk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kolk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kolkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kolk = kolkRepository.save(kolk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kolk.getId().toString()))
            .body(kolk);
    }

    /**
     * {@code PATCH  /kolks/:id} : Partial updates given fields of an existing kolk, field will ignore if it is null
     *
     * @param id the id of the kolk to save.
     * @param kolk the kolk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kolk,
     * or with status {@code 400 (Bad Request)} if the kolk is not valid,
     * or with status {@code 404 (Not Found)} if the kolk is not found,
     * or with status {@code 500 (Internal Server Error)} if the kolk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kolk> partialUpdateKolk(@PathVariable(value = "id", required = false) final Long id, @RequestBody Kolk kolk)
        throws URISyntaxException {
        log.debug("REST request to partial update Kolk partially : {}, {}", id, kolk);
        if (kolk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kolk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kolkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kolk> result = kolkRepository
            .findById(kolk.getId())
            .map(existingKolk -> {
                if (kolk.getBereikbaarheidkolk() != null) {
                    existingKolk.setBereikbaarheidkolk(kolk.getBereikbaarheidkolk());
                }
                if (kolk.getRisicogebied() != null) {
                    existingKolk.setRisicogebied(kolk.getRisicogebied());
                }
                if (kolk.getType() != null) {
                    existingKolk.setType(kolk.getType());
                }

                return existingKolk;
            })
            .map(kolkRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kolk.getId().toString())
        );
    }

    /**
     * {@code GET  /kolks} : get all the kolks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kolks in body.
     */
    @GetMapping("")
    public List<Kolk> getAllKolks() {
        log.debug("REST request to get all Kolks");
        return kolkRepository.findAll();
    }

    /**
     * {@code GET  /kolks/:id} : get the "id" kolk.
     *
     * @param id the id of the kolk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kolk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kolk> getKolk(@PathVariable("id") Long id) {
        log.debug("REST request to get Kolk : {}", id);
        Optional<Kolk> kolk = kolkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kolk);
    }

    /**
     * {@code DELETE  /kolks/:id} : delete the "id" kolk.
     *
     * @param id the id of the kolk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKolk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kolk : {}", id);
        kolkRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
