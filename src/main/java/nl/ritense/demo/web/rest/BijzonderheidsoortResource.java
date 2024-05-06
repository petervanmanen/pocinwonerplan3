package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bijzonderheidsoort;
import nl.ritense.demo.repository.BijzonderheidsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bijzonderheidsoort}.
 */
@RestController
@RequestMapping("/api/bijzonderheidsoorts")
@Transactional
public class BijzonderheidsoortResource {

    private final Logger log = LoggerFactory.getLogger(BijzonderheidsoortResource.class);

    private static final String ENTITY_NAME = "bijzonderheidsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BijzonderheidsoortRepository bijzonderheidsoortRepository;

    public BijzonderheidsoortResource(BijzonderheidsoortRepository bijzonderheidsoortRepository) {
        this.bijzonderheidsoortRepository = bijzonderheidsoortRepository;
    }

    /**
     * {@code POST  /bijzonderheidsoorts} : Create a new bijzonderheidsoort.
     *
     * @param bijzonderheidsoort the bijzonderheidsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bijzonderheidsoort, or with status {@code 400 (Bad Request)} if the bijzonderheidsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bijzonderheidsoort> createBijzonderheidsoort(@Valid @RequestBody Bijzonderheidsoort bijzonderheidsoort)
        throws URISyntaxException {
        log.debug("REST request to save Bijzonderheidsoort : {}", bijzonderheidsoort);
        if (bijzonderheidsoort.getId() != null) {
            throw new BadRequestAlertException("A new bijzonderheidsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bijzonderheidsoort = bijzonderheidsoortRepository.save(bijzonderheidsoort);
        return ResponseEntity.created(new URI("/api/bijzonderheidsoorts/" + bijzonderheidsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bijzonderheidsoort.getId().toString()))
            .body(bijzonderheidsoort);
    }

    /**
     * {@code PUT  /bijzonderheidsoorts/:id} : Updates an existing bijzonderheidsoort.
     *
     * @param id the id of the bijzonderheidsoort to save.
     * @param bijzonderheidsoort the bijzonderheidsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bijzonderheidsoort,
     * or with status {@code 400 (Bad Request)} if the bijzonderheidsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bijzonderheidsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bijzonderheidsoort> updateBijzonderheidsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bijzonderheidsoort bijzonderheidsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Bijzonderheidsoort : {}, {}", id, bijzonderheidsoort);
        if (bijzonderheidsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bijzonderheidsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bijzonderheidsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bijzonderheidsoort = bijzonderheidsoortRepository.save(bijzonderheidsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bijzonderheidsoort.getId().toString()))
            .body(bijzonderheidsoort);
    }

    /**
     * {@code PATCH  /bijzonderheidsoorts/:id} : Partial updates given fields of an existing bijzonderheidsoort, field will ignore if it is null
     *
     * @param id the id of the bijzonderheidsoort to save.
     * @param bijzonderheidsoort the bijzonderheidsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bijzonderheidsoort,
     * or with status {@code 400 (Bad Request)} if the bijzonderheidsoort is not valid,
     * or with status {@code 404 (Not Found)} if the bijzonderheidsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the bijzonderheidsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bijzonderheidsoort> partialUpdateBijzonderheidsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bijzonderheidsoort bijzonderheidsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bijzonderheidsoort partially : {}, {}", id, bijzonderheidsoort);
        if (bijzonderheidsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bijzonderheidsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bijzonderheidsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bijzonderheidsoort> result = bijzonderheidsoortRepository
            .findById(bijzonderheidsoort.getId())
            .map(existingBijzonderheidsoort -> {
                if (bijzonderheidsoort.getNaam() != null) {
                    existingBijzonderheidsoort.setNaam(bijzonderheidsoort.getNaam());
                }
                if (bijzonderheidsoort.getOmschrijving() != null) {
                    existingBijzonderheidsoort.setOmschrijving(bijzonderheidsoort.getOmschrijving());
                }

                return existingBijzonderheidsoort;
            })
            .map(bijzonderheidsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bijzonderheidsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /bijzonderheidsoorts} : get all the bijzonderheidsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bijzonderheidsoorts in body.
     */
    @GetMapping("")
    public List<Bijzonderheidsoort> getAllBijzonderheidsoorts() {
        log.debug("REST request to get all Bijzonderheidsoorts");
        return bijzonderheidsoortRepository.findAll();
    }

    /**
     * {@code GET  /bijzonderheidsoorts/:id} : get the "id" bijzonderheidsoort.
     *
     * @param id the id of the bijzonderheidsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bijzonderheidsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bijzonderheidsoort> getBijzonderheidsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Bijzonderheidsoort : {}", id);
        Optional<Bijzonderheidsoort> bijzonderheidsoort = bijzonderheidsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bijzonderheidsoort);
    }

    /**
     * {@code DELETE  /bijzonderheidsoorts/:id} : delete the "id" bijzonderheidsoort.
     *
     * @param id the id of the bijzonderheidsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBijzonderheidsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bijzonderheidsoort : {}", id);
        bijzonderheidsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
