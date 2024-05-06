package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Taakveld;
import nl.ritense.demo.repository.TaakveldRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Taakveld}.
 */
@RestController
@RequestMapping("/api/taakvelds")
@Transactional
public class TaakveldResource {

    private final Logger log = LoggerFactory.getLogger(TaakveldResource.class);

    private static final String ENTITY_NAME = "taakveld";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaakveldRepository taakveldRepository;

    public TaakveldResource(TaakveldRepository taakveldRepository) {
        this.taakveldRepository = taakveldRepository;
    }

    /**
     * {@code POST  /taakvelds} : Create a new taakveld.
     *
     * @param taakveld the taakveld to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taakveld, or with status {@code 400 (Bad Request)} if the taakveld has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Taakveld> createTaakveld(@Valid @RequestBody Taakveld taakveld) throws URISyntaxException {
        log.debug("REST request to save Taakveld : {}", taakveld);
        if (taakveld.getId() != null) {
            throw new BadRequestAlertException("A new taakveld cannot already have an ID", ENTITY_NAME, "idexists");
        }
        taakveld = taakveldRepository.save(taakveld);
        return ResponseEntity.created(new URI("/api/taakvelds/" + taakveld.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, taakveld.getId().toString()))
            .body(taakveld);
    }

    /**
     * {@code PUT  /taakvelds/:id} : Updates an existing taakveld.
     *
     * @param id the id of the taakveld to save.
     * @param taakveld the taakveld to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taakveld,
     * or with status {@code 400 (Bad Request)} if the taakveld is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taakveld couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Taakveld> updateTaakveld(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Taakveld taakveld
    ) throws URISyntaxException {
        log.debug("REST request to update Taakveld : {}, {}", id, taakveld);
        if (taakveld.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taakveld.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taakveldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        taakveld = taakveldRepository.save(taakveld);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taakveld.getId().toString()))
            .body(taakveld);
    }

    /**
     * {@code PATCH  /taakvelds/:id} : Partial updates given fields of an existing taakveld, field will ignore if it is null
     *
     * @param id the id of the taakveld to save.
     * @param taakveld the taakveld to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taakveld,
     * or with status {@code 400 (Bad Request)} if the taakveld is not valid,
     * or with status {@code 404 (Not Found)} if the taakveld is not found,
     * or with status {@code 500 (Internal Server Error)} if the taakveld couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Taakveld> partialUpdateTaakveld(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Taakveld taakveld
    ) throws URISyntaxException {
        log.debug("REST request to partial update Taakveld partially : {}, {}", id, taakveld);
        if (taakveld.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taakveld.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taakveldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Taakveld> result = taakveldRepository
            .findById(taakveld.getId())
            .map(existingTaakveld -> {
                if (taakveld.getNaam() != null) {
                    existingTaakveld.setNaam(taakveld.getNaam());
                }

                return existingTaakveld;
            })
            .map(taakveldRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taakveld.getId().toString())
        );
    }

    /**
     * {@code GET  /taakvelds} : get all the taakvelds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taakvelds in body.
     */
    @GetMapping("")
    public List<Taakveld> getAllTaakvelds() {
        log.debug("REST request to get all Taakvelds");
        return taakveldRepository.findAll();
    }

    /**
     * {@code GET  /taakvelds/:id} : get the "id" taakveld.
     *
     * @param id the id of the taakveld to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taakveld, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Taakveld> getTaakveld(@PathVariable("id") Long id) {
        log.debug("REST request to get Taakveld : {}", id);
        Optional<Taakveld> taakveld = taakveldRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taakveld);
    }

    /**
     * {@code DELETE  /taakvelds/:id} : delete the "id" taakveld.
     *
     * @param id the id of the taakveld to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaakveld(@PathVariable("id") Long id) {
        log.debug("REST request to delete Taakveld : {}", id);
        taakveldRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
