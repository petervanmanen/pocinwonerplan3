package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Regelingsoort;
import nl.ritense.demo.repository.RegelingsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Regelingsoort}.
 */
@RestController
@RequestMapping("/api/regelingsoorts")
@Transactional
public class RegelingsoortResource {

    private final Logger log = LoggerFactory.getLogger(RegelingsoortResource.class);

    private static final String ENTITY_NAME = "regelingsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegelingsoortRepository regelingsoortRepository;

    public RegelingsoortResource(RegelingsoortRepository regelingsoortRepository) {
        this.regelingsoortRepository = regelingsoortRepository;
    }

    /**
     * {@code POST  /regelingsoorts} : Create a new regelingsoort.
     *
     * @param regelingsoort the regelingsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regelingsoort, or with status {@code 400 (Bad Request)} if the regelingsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Regelingsoort> createRegelingsoort(@Valid @RequestBody Regelingsoort regelingsoort) throws URISyntaxException {
        log.debug("REST request to save Regelingsoort : {}", regelingsoort);
        if (regelingsoort.getId() != null) {
            throw new BadRequestAlertException("A new regelingsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        regelingsoort = regelingsoortRepository.save(regelingsoort);
        return ResponseEntity.created(new URI("/api/regelingsoorts/" + regelingsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, regelingsoort.getId().toString()))
            .body(regelingsoort);
    }

    /**
     * {@code PUT  /regelingsoorts/:id} : Updates an existing regelingsoort.
     *
     * @param id the id of the regelingsoort to save.
     * @param regelingsoort the regelingsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regelingsoort,
     * or with status {@code 400 (Bad Request)} if the regelingsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regelingsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Regelingsoort> updateRegelingsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Regelingsoort regelingsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Regelingsoort : {}, {}", id, regelingsoort);
        if (regelingsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regelingsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regelingsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        regelingsoort = regelingsoortRepository.save(regelingsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regelingsoort.getId().toString()))
            .body(regelingsoort);
    }

    /**
     * {@code PATCH  /regelingsoorts/:id} : Partial updates given fields of an existing regelingsoort, field will ignore if it is null
     *
     * @param id the id of the regelingsoort to save.
     * @param regelingsoort the regelingsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regelingsoort,
     * or with status {@code 400 (Bad Request)} if the regelingsoort is not valid,
     * or with status {@code 404 (Not Found)} if the regelingsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the regelingsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Regelingsoort> partialUpdateRegelingsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Regelingsoort regelingsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Regelingsoort partially : {}, {}", id, regelingsoort);
        if (regelingsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regelingsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regelingsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Regelingsoort> result = regelingsoortRepository
            .findById(regelingsoort.getId())
            .map(existingRegelingsoort -> {
                if (regelingsoort.getNaam() != null) {
                    existingRegelingsoort.setNaam(regelingsoort.getNaam());
                }
                if (regelingsoort.getOmschrijving() != null) {
                    existingRegelingsoort.setOmschrijving(regelingsoort.getOmschrijving());
                }

                return existingRegelingsoort;
            })
            .map(regelingsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regelingsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /regelingsoorts} : get all the regelingsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regelingsoorts in body.
     */
    @GetMapping("")
    public List<Regelingsoort> getAllRegelingsoorts() {
        log.debug("REST request to get all Regelingsoorts");
        return regelingsoortRepository.findAll();
    }

    /**
     * {@code GET  /regelingsoorts/:id} : get the "id" regelingsoort.
     *
     * @param id the id of the regelingsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regelingsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Regelingsoort> getRegelingsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Regelingsoort : {}", id);
        Optional<Regelingsoort> regelingsoort = regelingsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regelingsoort);
    }

    /**
     * {@code DELETE  /regelingsoorts/:id} : delete the "id" regelingsoort.
     *
     * @param id the id of the regelingsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegelingsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Regelingsoort : {}", id);
        regelingsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
