package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Trajectactiviteitsoort;
import nl.ritense.demo.repository.TrajectactiviteitsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Trajectactiviteitsoort}.
 */
@RestController
@RequestMapping("/api/trajectactiviteitsoorts")
@Transactional
public class TrajectactiviteitsoortResource {

    private final Logger log = LoggerFactory.getLogger(TrajectactiviteitsoortResource.class);

    private static final String ENTITY_NAME = "trajectactiviteitsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrajectactiviteitsoortRepository trajectactiviteitsoortRepository;

    public TrajectactiviteitsoortResource(TrajectactiviteitsoortRepository trajectactiviteitsoortRepository) {
        this.trajectactiviteitsoortRepository = trajectactiviteitsoortRepository;
    }

    /**
     * {@code POST  /trajectactiviteitsoorts} : Create a new trajectactiviteitsoort.
     *
     * @param trajectactiviteitsoort the trajectactiviteitsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trajectactiviteitsoort, or with status {@code 400 (Bad Request)} if the trajectactiviteitsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Trajectactiviteitsoort> createTrajectactiviteitsoort(
        @Valid @RequestBody Trajectactiviteitsoort trajectactiviteitsoort
    ) throws URISyntaxException {
        log.debug("REST request to save Trajectactiviteitsoort : {}", trajectactiviteitsoort);
        if (trajectactiviteitsoort.getId() != null) {
            throw new BadRequestAlertException("A new trajectactiviteitsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        trajectactiviteitsoort = trajectactiviteitsoortRepository.save(trajectactiviteitsoort);
        return ResponseEntity.created(new URI("/api/trajectactiviteitsoorts/" + trajectactiviteitsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, trajectactiviteitsoort.getId().toString()))
            .body(trajectactiviteitsoort);
    }

    /**
     * {@code PUT  /trajectactiviteitsoorts/:id} : Updates an existing trajectactiviteitsoort.
     *
     * @param id the id of the trajectactiviteitsoort to save.
     * @param trajectactiviteitsoort the trajectactiviteitsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trajectactiviteitsoort,
     * or with status {@code 400 (Bad Request)} if the trajectactiviteitsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trajectactiviteitsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Trajectactiviteitsoort> updateTrajectactiviteitsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Trajectactiviteitsoort trajectactiviteitsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Trajectactiviteitsoort : {}, {}", id, trajectactiviteitsoort);
        if (trajectactiviteitsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trajectactiviteitsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trajectactiviteitsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        trajectactiviteitsoort = trajectactiviteitsoortRepository.save(trajectactiviteitsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trajectactiviteitsoort.getId().toString()))
            .body(trajectactiviteitsoort);
    }

    /**
     * {@code PATCH  /trajectactiviteitsoorts/:id} : Partial updates given fields of an existing trajectactiviteitsoort, field will ignore if it is null
     *
     * @param id the id of the trajectactiviteitsoort to save.
     * @param trajectactiviteitsoort the trajectactiviteitsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trajectactiviteitsoort,
     * or with status {@code 400 (Bad Request)} if the trajectactiviteitsoort is not valid,
     * or with status {@code 404 (Not Found)} if the trajectactiviteitsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the trajectactiviteitsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Trajectactiviteitsoort> partialUpdateTrajectactiviteitsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Trajectactiviteitsoort trajectactiviteitsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Trajectactiviteitsoort partially : {}, {}", id, trajectactiviteitsoort);
        if (trajectactiviteitsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trajectactiviteitsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trajectactiviteitsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Trajectactiviteitsoort> result = trajectactiviteitsoortRepository
            .findById(trajectactiviteitsoort.getId())
            .map(existingTrajectactiviteitsoort -> {
                if (trajectactiviteitsoort.getAanleverensrg() != null) {
                    existingTrajectactiviteitsoort.setAanleverensrg(trajectactiviteitsoort.getAanleverensrg());
                }
                if (trajectactiviteitsoort.getOmschrijving() != null) {
                    existingTrajectactiviteitsoort.setOmschrijving(trajectactiviteitsoort.getOmschrijving());
                }
                if (trajectactiviteitsoort.getTypetrajectsrg() != null) {
                    existingTrajectactiviteitsoort.setTypetrajectsrg(trajectactiviteitsoort.getTypetrajectsrg());
                }

                return existingTrajectactiviteitsoort;
            })
            .map(trajectactiviteitsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trajectactiviteitsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /trajectactiviteitsoorts} : get all the trajectactiviteitsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trajectactiviteitsoorts in body.
     */
    @GetMapping("")
    public List<Trajectactiviteitsoort> getAllTrajectactiviteitsoorts() {
        log.debug("REST request to get all Trajectactiviteitsoorts");
        return trajectactiviteitsoortRepository.findAll();
    }

    /**
     * {@code GET  /trajectactiviteitsoorts/:id} : get the "id" trajectactiviteitsoort.
     *
     * @param id the id of the trajectactiviteitsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trajectactiviteitsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Trajectactiviteitsoort> getTrajectactiviteitsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Trajectactiviteitsoort : {}", id);
        Optional<Trajectactiviteitsoort> trajectactiviteitsoort = trajectactiviteitsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trajectactiviteitsoort);
    }

    /**
     * {@code DELETE  /trajectactiviteitsoorts/:id} : delete the "id" trajectactiviteitsoort.
     *
     * @param id the id of the trajectactiviteitsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajectactiviteitsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Trajectactiviteitsoort : {}", id);
        trajectactiviteitsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
