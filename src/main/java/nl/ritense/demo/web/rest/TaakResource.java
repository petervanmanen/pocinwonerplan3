package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Taak;
import nl.ritense.demo.repository.TaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Taak}.
 */
@RestController
@RequestMapping("/api/taaks")
@Transactional
public class TaakResource {

    private final Logger log = LoggerFactory.getLogger(TaakResource.class);

    private static final String ENTITY_NAME = "taak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaakRepository taakRepository;

    public TaakResource(TaakRepository taakRepository) {
        this.taakRepository = taakRepository;
    }

    /**
     * {@code POST  /taaks} : Create a new taak.
     *
     * @param taak the taak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taak, or with status {@code 400 (Bad Request)} if the taak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Taak> createTaak(@RequestBody Taak taak) throws URISyntaxException {
        log.debug("REST request to save Taak : {}", taak);
        if (taak.getId() != null) {
            throw new BadRequestAlertException("A new taak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        taak = taakRepository.save(taak);
        return ResponseEntity.created(new URI("/api/taaks/" + taak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, taak.getId().toString()))
            .body(taak);
    }

    /**
     * {@code PUT  /taaks/:id} : Updates an existing taak.
     *
     * @param id the id of the taak to save.
     * @param taak the taak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taak,
     * or with status {@code 400 (Bad Request)} if the taak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Taak> updateTaak(@PathVariable(value = "id", required = false) final Long id, @RequestBody Taak taak)
        throws URISyntaxException {
        log.debug("REST request to update Taak : {}, {}", id, taak);
        if (taak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        taak = taakRepository.save(taak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taak.getId().toString()))
            .body(taak);
    }

    /**
     * {@code PATCH  /taaks/:id} : Partial updates given fields of an existing taak, field will ignore if it is null
     *
     * @param id the id of the taak to save.
     * @param taak the taak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taak,
     * or with status {@code 400 (Bad Request)} if the taak is not valid,
     * or with status {@code 404 (Not Found)} if the taak is not found,
     * or with status {@code 500 (Internal Server Error)} if the taak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Taak> partialUpdateTaak(@PathVariable(value = "id", required = false) final Long id, @RequestBody Taak taak)
        throws URISyntaxException {
        log.debug("REST request to partial update Taak partially : {}, {}", id, taak);
        if (taak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Taak> result = taakRepository.findById(taak.getId()).map(taakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taak.getId().toString())
        );
    }

    /**
     * {@code GET  /taaks} : get all the taaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taaks in body.
     */
    @GetMapping("")
    public List<Taak> getAllTaaks() {
        log.debug("REST request to get all Taaks");
        return taakRepository.findAll();
    }

    /**
     * {@code GET  /taaks/:id} : get the "id" taak.
     *
     * @param id the id of the taak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Taak> getTaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Taak : {}", id);
        Optional<Taak> taak = taakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taak);
    }

    /**
     * {@code DELETE  /taaks/:id} : delete the "id" taak.
     *
     * @param id the id of the taak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Taak : {}", id);
        taakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
