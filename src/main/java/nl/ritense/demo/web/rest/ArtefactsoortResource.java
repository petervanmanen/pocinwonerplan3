package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Artefactsoort;
import nl.ritense.demo.repository.ArtefactsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Artefactsoort}.
 */
@RestController
@RequestMapping("/api/artefactsoorts")
@Transactional
public class ArtefactsoortResource {

    private final Logger log = LoggerFactory.getLogger(ArtefactsoortResource.class);

    private static final String ENTITY_NAME = "artefactsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtefactsoortRepository artefactsoortRepository;

    public ArtefactsoortResource(ArtefactsoortRepository artefactsoortRepository) {
        this.artefactsoortRepository = artefactsoortRepository;
    }

    /**
     * {@code POST  /artefactsoorts} : Create a new artefactsoort.
     *
     * @param artefactsoort the artefactsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artefactsoort, or with status {@code 400 (Bad Request)} if the artefactsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Artefactsoort> createArtefactsoort(@Valid @RequestBody Artefactsoort artefactsoort) throws URISyntaxException {
        log.debug("REST request to save Artefactsoort : {}", artefactsoort);
        if (artefactsoort.getId() != null) {
            throw new BadRequestAlertException("A new artefactsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        artefactsoort = artefactsoortRepository.save(artefactsoort);
        return ResponseEntity.created(new URI("/api/artefactsoorts/" + artefactsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, artefactsoort.getId().toString()))
            .body(artefactsoort);
    }

    /**
     * {@code PUT  /artefactsoorts/:id} : Updates an existing artefactsoort.
     *
     * @param id the id of the artefactsoort to save.
     * @param artefactsoort the artefactsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artefactsoort,
     * or with status {@code 400 (Bad Request)} if the artefactsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artefactsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Artefactsoort> updateArtefactsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Artefactsoort artefactsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Artefactsoort : {}, {}", id, artefactsoort);
        if (artefactsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artefactsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artefactsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        artefactsoort = artefactsoortRepository.save(artefactsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artefactsoort.getId().toString()))
            .body(artefactsoort);
    }

    /**
     * {@code PATCH  /artefactsoorts/:id} : Partial updates given fields of an existing artefactsoort, field will ignore if it is null
     *
     * @param id the id of the artefactsoort to save.
     * @param artefactsoort the artefactsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artefactsoort,
     * or with status {@code 400 (Bad Request)} if the artefactsoort is not valid,
     * or with status {@code 404 (Not Found)} if the artefactsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the artefactsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Artefactsoort> partialUpdateArtefactsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Artefactsoort artefactsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Artefactsoort partially : {}, {}", id, artefactsoort);
        if (artefactsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artefactsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artefactsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Artefactsoort> result = artefactsoortRepository
            .findById(artefactsoort.getId())
            .map(existingArtefactsoort -> {
                if (artefactsoort.getCode() != null) {
                    existingArtefactsoort.setCode(artefactsoort.getCode());
                }
                if (artefactsoort.getNaam() != null) {
                    existingArtefactsoort.setNaam(artefactsoort.getNaam());
                }
                if (artefactsoort.getOmschrijving() != null) {
                    existingArtefactsoort.setOmschrijving(artefactsoort.getOmschrijving());
                }

                return existingArtefactsoort;
            })
            .map(artefactsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artefactsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /artefactsoorts} : get all the artefactsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artefactsoorts in body.
     */
    @GetMapping("")
    public List<Artefactsoort> getAllArtefactsoorts() {
        log.debug("REST request to get all Artefactsoorts");
        return artefactsoortRepository.findAll();
    }

    /**
     * {@code GET  /artefactsoorts/:id} : get the "id" artefactsoort.
     *
     * @param id the id of the artefactsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artefactsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Artefactsoort> getArtefactsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Artefactsoort : {}", id);
        Optional<Artefactsoort> artefactsoort = artefactsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(artefactsoort);
    }

    /**
     * {@code DELETE  /artefactsoorts/:id} : delete the "id" artefactsoort.
     *
     * @param id the id of the artefactsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtefactsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Artefactsoort : {}", id);
        artefactsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
