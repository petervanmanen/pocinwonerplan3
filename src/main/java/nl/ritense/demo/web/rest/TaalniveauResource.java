package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Taalniveau;
import nl.ritense.demo.repository.TaalniveauRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Taalniveau}.
 */
@RestController
@RequestMapping("/api/taalniveaus")
@Transactional
public class TaalniveauResource {

    private final Logger log = LoggerFactory.getLogger(TaalniveauResource.class);

    private static final String ENTITY_NAME = "taalniveau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaalniveauRepository taalniveauRepository;

    public TaalniveauResource(TaalniveauRepository taalniveauRepository) {
        this.taalniveauRepository = taalniveauRepository;
    }

    /**
     * {@code POST  /taalniveaus} : Create a new taalniveau.
     *
     * @param taalniveau the taalniveau to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taalniveau, or with status {@code 400 (Bad Request)} if the taalniveau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Taalniveau> createTaalniveau(@Valid @RequestBody Taalniveau taalniveau) throws URISyntaxException {
        log.debug("REST request to save Taalniveau : {}", taalniveau);
        if (taalniveau.getId() != null) {
            throw new BadRequestAlertException("A new taalniveau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        taalniveau = taalniveauRepository.save(taalniveau);
        return ResponseEntity.created(new URI("/api/taalniveaus/" + taalniveau.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, taalniveau.getId().toString()))
            .body(taalniveau);
    }

    /**
     * {@code PUT  /taalniveaus/:id} : Updates an existing taalniveau.
     *
     * @param id the id of the taalniveau to save.
     * @param taalniveau the taalniveau to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taalniveau,
     * or with status {@code 400 (Bad Request)} if the taalniveau is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taalniveau couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Taalniveau> updateTaalniveau(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Taalniveau taalniveau
    ) throws URISyntaxException {
        log.debug("REST request to update Taalniveau : {}, {}", id, taalniveau);
        if (taalniveau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taalniveau.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taalniveauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        taalniveau = taalniveauRepository.save(taalniveau);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taalniveau.getId().toString()))
            .body(taalniveau);
    }

    /**
     * {@code PATCH  /taalniveaus/:id} : Partial updates given fields of an existing taalniveau, field will ignore if it is null
     *
     * @param id the id of the taalniveau to save.
     * @param taalniveau the taalniveau to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taalniveau,
     * or with status {@code 400 (Bad Request)} if the taalniveau is not valid,
     * or with status {@code 404 (Not Found)} if the taalniveau is not found,
     * or with status {@code 500 (Internal Server Error)} if the taalniveau couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Taalniveau> partialUpdateTaalniveau(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Taalniveau taalniveau
    ) throws URISyntaxException {
        log.debug("REST request to partial update Taalniveau partially : {}, {}", id, taalniveau);
        if (taalniveau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taalniveau.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taalniveauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Taalniveau> result = taalniveauRepository
            .findById(taalniveau.getId())
            .map(existingTaalniveau -> {
                if (taalniveau.getMondeling() != null) {
                    existingTaalniveau.setMondeling(taalniveau.getMondeling());
                }
                if (taalniveau.getSchriftelijk() != null) {
                    existingTaalniveau.setSchriftelijk(taalniveau.getSchriftelijk());
                }

                return existingTaalniveau;
            })
            .map(taalniveauRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taalniveau.getId().toString())
        );
    }

    /**
     * {@code GET  /taalniveaus} : get all the taalniveaus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taalniveaus in body.
     */
    @GetMapping("")
    public List<Taalniveau> getAllTaalniveaus() {
        log.debug("REST request to get all Taalniveaus");
        return taalniveauRepository.findAll();
    }

    /**
     * {@code GET  /taalniveaus/:id} : get the "id" taalniveau.
     *
     * @param id the id of the taalniveau to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taalniveau, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Taalniveau> getTaalniveau(@PathVariable("id") Long id) {
        log.debug("REST request to get Taalniveau : {}", id);
        Optional<Taalniveau> taalniveau = taalniveauRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taalniveau);
    }

    /**
     * {@code DELETE  /taalniveaus/:id} : delete the "id" taalniveau.
     *
     * @param id the id of the taalniveau to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaalniveau(@PathVariable("id") Long id) {
        log.debug("REST request to delete Taalniveau : {}", id);
        taalniveauRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
