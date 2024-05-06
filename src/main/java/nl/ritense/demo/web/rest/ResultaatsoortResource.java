package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Resultaatsoort;
import nl.ritense.demo.repository.ResultaatsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Resultaatsoort}.
 */
@RestController
@RequestMapping("/api/resultaatsoorts")
@Transactional
public class ResultaatsoortResource {

    private final Logger log = LoggerFactory.getLogger(ResultaatsoortResource.class);

    private static final String ENTITY_NAME = "resultaatsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultaatsoortRepository resultaatsoortRepository;

    public ResultaatsoortResource(ResultaatsoortRepository resultaatsoortRepository) {
        this.resultaatsoortRepository = resultaatsoortRepository;
    }

    /**
     * {@code POST  /resultaatsoorts} : Create a new resultaatsoort.
     *
     * @param resultaatsoort the resultaatsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultaatsoort, or with status {@code 400 (Bad Request)} if the resultaatsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Resultaatsoort> createResultaatsoort(@Valid @RequestBody Resultaatsoort resultaatsoort)
        throws URISyntaxException {
        log.debug("REST request to save Resultaatsoort : {}", resultaatsoort);
        if (resultaatsoort.getId() != null) {
            throw new BadRequestAlertException("A new resultaatsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        resultaatsoort = resultaatsoortRepository.save(resultaatsoort);
        return ResponseEntity.created(new URI("/api/resultaatsoorts/" + resultaatsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, resultaatsoort.getId().toString()))
            .body(resultaatsoort);
    }

    /**
     * {@code PUT  /resultaatsoorts/:id} : Updates an existing resultaatsoort.
     *
     * @param id the id of the resultaatsoort to save.
     * @param resultaatsoort the resultaatsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultaatsoort,
     * or with status {@code 400 (Bad Request)} if the resultaatsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultaatsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Resultaatsoort> updateResultaatsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Resultaatsoort resultaatsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Resultaatsoort : {}, {}", id, resultaatsoort);
        if (resultaatsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultaatsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultaatsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        resultaatsoort = resultaatsoortRepository.save(resultaatsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultaatsoort.getId().toString()))
            .body(resultaatsoort);
    }

    /**
     * {@code PATCH  /resultaatsoorts/:id} : Partial updates given fields of an existing resultaatsoort, field will ignore if it is null
     *
     * @param id the id of the resultaatsoort to save.
     * @param resultaatsoort the resultaatsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultaatsoort,
     * or with status {@code 400 (Bad Request)} if the resultaatsoort is not valid,
     * or with status {@code 404 (Not Found)} if the resultaatsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the resultaatsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Resultaatsoort> partialUpdateResultaatsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Resultaatsoort resultaatsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Resultaatsoort partially : {}, {}", id, resultaatsoort);
        if (resultaatsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultaatsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultaatsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Resultaatsoort> result = resultaatsoortRepository
            .findById(resultaatsoort.getId())
            .map(existingResultaatsoort -> {
                if (resultaatsoort.getNaam() != null) {
                    existingResultaatsoort.setNaam(resultaatsoort.getNaam());
                }
                if (resultaatsoort.getOmschrijving() != null) {
                    existingResultaatsoort.setOmschrijving(resultaatsoort.getOmschrijving());
                }

                return existingResultaatsoort;
            })
            .map(resultaatsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultaatsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /resultaatsoorts} : get all the resultaatsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultaatsoorts in body.
     */
    @GetMapping("")
    public List<Resultaatsoort> getAllResultaatsoorts() {
        log.debug("REST request to get all Resultaatsoorts");
        return resultaatsoortRepository.findAll();
    }

    /**
     * {@code GET  /resultaatsoorts/:id} : get the "id" resultaatsoort.
     *
     * @param id the id of the resultaatsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultaatsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Resultaatsoort> getResultaatsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Resultaatsoort : {}", id);
        Optional<Resultaatsoort> resultaatsoort = resultaatsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultaatsoort);
    }

    /**
     * {@code DELETE  /resultaatsoorts/:id} : delete the "id" resultaatsoort.
     *
     * @param id the id of the resultaatsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResultaatsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Resultaatsoort : {}", id);
        resultaatsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
