package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Scoresoort;
import nl.ritense.demo.repository.ScoresoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Scoresoort}.
 */
@RestController
@RequestMapping("/api/scoresoorts")
@Transactional
public class ScoresoortResource {

    private final Logger log = LoggerFactory.getLogger(ScoresoortResource.class);

    private static final String ENTITY_NAME = "scoresoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScoresoortRepository scoresoortRepository;

    public ScoresoortResource(ScoresoortRepository scoresoortRepository) {
        this.scoresoortRepository = scoresoortRepository;
    }

    /**
     * {@code POST  /scoresoorts} : Create a new scoresoort.
     *
     * @param scoresoort the scoresoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scoresoort, or with status {@code 400 (Bad Request)} if the scoresoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Scoresoort> createScoresoort(@Valid @RequestBody Scoresoort scoresoort) throws URISyntaxException {
        log.debug("REST request to save Scoresoort : {}", scoresoort);
        if (scoresoort.getId() != null) {
            throw new BadRequestAlertException("A new scoresoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        scoresoort = scoresoortRepository.save(scoresoort);
        return ResponseEntity.created(new URI("/api/scoresoorts/" + scoresoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, scoresoort.getId().toString()))
            .body(scoresoort);
    }

    /**
     * {@code PUT  /scoresoorts/:id} : Updates an existing scoresoort.
     *
     * @param id the id of the scoresoort to save.
     * @param scoresoort the scoresoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scoresoort,
     * or with status {@code 400 (Bad Request)} if the scoresoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scoresoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Scoresoort> updateScoresoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Scoresoort scoresoort
    ) throws URISyntaxException {
        log.debug("REST request to update Scoresoort : {}, {}", id, scoresoort);
        if (scoresoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scoresoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scoresoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        scoresoort = scoresoortRepository.save(scoresoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scoresoort.getId().toString()))
            .body(scoresoort);
    }

    /**
     * {@code PATCH  /scoresoorts/:id} : Partial updates given fields of an existing scoresoort, field will ignore if it is null
     *
     * @param id the id of the scoresoort to save.
     * @param scoresoort the scoresoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scoresoort,
     * or with status {@code 400 (Bad Request)} if the scoresoort is not valid,
     * or with status {@code 404 (Not Found)} if the scoresoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the scoresoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Scoresoort> partialUpdateScoresoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Scoresoort scoresoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Scoresoort partially : {}, {}", id, scoresoort);
        if (scoresoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scoresoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scoresoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Scoresoort> result = scoresoortRepository
            .findById(scoresoort.getId())
            .map(existingScoresoort -> {
                if (scoresoort.getNiveau() != null) {
                    existingScoresoort.setNiveau(scoresoort.getNiveau());
                }

                return existingScoresoort;
            })
            .map(scoresoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scoresoort.getId().toString())
        );
    }

    /**
     * {@code GET  /scoresoorts} : get all the scoresoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scoresoorts in body.
     */
    @GetMapping("")
    public List<Scoresoort> getAllScoresoorts() {
        log.debug("REST request to get all Scoresoorts");
        return scoresoortRepository.findAll();
    }

    /**
     * {@code GET  /scoresoorts/:id} : get the "id" scoresoort.
     *
     * @param id the id of the scoresoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scoresoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Scoresoort> getScoresoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Scoresoort : {}", id);
        Optional<Scoresoort> scoresoort = scoresoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scoresoort);
    }

    /**
     * {@code DELETE  /scoresoorts/:id} : delete the "id" scoresoort.
     *
     * @param id the id of the scoresoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScoresoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Scoresoort : {}", id);
        scoresoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
