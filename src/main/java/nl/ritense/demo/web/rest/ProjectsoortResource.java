package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Projectsoort;
import nl.ritense.demo.repository.ProjectsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Projectsoort}.
 */
@RestController
@RequestMapping("/api/projectsoorts")
@Transactional
public class ProjectsoortResource {

    private final Logger log = LoggerFactory.getLogger(ProjectsoortResource.class);

    private static final String ENTITY_NAME = "projectsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectsoortRepository projectsoortRepository;

    public ProjectsoortResource(ProjectsoortRepository projectsoortRepository) {
        this.projectsoortRepository = projectsoortRepository;
    }

    /**
     * {@code POST  /projectsoorts} : Create a new projectsoort.
     *
     * @param projectsoort the projectsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectsoort, or with status {@code 400 (Bad Request)} if the projectsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Projectsoort> createProjectsoort(@Valid @RequestBody Projectsoort projectsoort) throws URISyntaxException {
        log.debug("REST request to save Projectsoort : {}", projectsoort);
        if (projectsoort.getId() != null) {
            throw new BadRequestAlertException("A new projectsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        projectsoort = projectsoortRepository.save(projectsoort);
        return ResponseEntity.created(new URI("/api/projectsoorts/" + projectsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, projectsoort.getId().toString()))
            .body(projectsoort);
    }

    /**
     * {@code PUT  /projectsoorts/:id} : Updates an existing projectsoort.
     *
     * @param id the id of the projectsoort to save.
     * @param projectsoort the projectsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectsoort,
     * or with status {@code 400 (Bad Request)} if the projectsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Projectsoort> updateProjectsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Projectsoort projectsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Projectsoort : {}, {}", id, projectsoort);
        if (projectsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        projectsoort = projectsoortRepository.save(projectsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectsoort.getId().toString()))
            .body(projectsoort);
    }

    /**
     * {@code PATCH  /projectsoorts/:id} : Partial updates given fields of an existing projectsoort, field will ignore if it is null
     *
     * @param id the id of the projectsoort to save.
     * @param projectsoort the projectsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectsoort,
     * or with status {@code 400 (Bad Request)} if the projectsoort is not valid,
     * or with status {@code 404 (Not Found)} if the projectsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Projectsoort> partialUpdateProjectsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Projectsoort projectsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projectsoort partially : {}, {}", id, projectsoort);
        if (projectsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Projectsoort> result = projectsoortRepository
            .findById(projectsoort.getId())
            .map(existingProjectsoort -> {
                if (projectsoort.getNaam() != null) {
                    existingProjectsoort.setNaam(projectsoort.getNaam());
                }
                if (projectsoort.getOmschrijving() != null) {
                    existingProjectsoort.setOmschrijving(projectsoort.getOmschrijving());
                }

                return existingProjectsoort;
            })
            .map(projectsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /projectsoorts} : get all the projectsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectsoorts in body.
     */
    @GetMapping("")
    public List<Projectsoort> getAllProjectsoorts() {
        log.debug("REST request to get all Projectsoorts");
        return projectsoortRepository.findAll();
    }

    /**
     * {@code GET  /projectsoorts/:id} : get the "id" projectsoort.
     *
     * @param id the id of the projectsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Projectsoort> getProjectsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Projectsoort : {}", id);
        Optional<Projectsoort> projectsoort = projectsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectsoort);
    }

    /**
     * {@code DELETE  /projectsoorts/:id} : delete the "id" projectsoort.
     *
     * @param id the id of the projectsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Projectsoort : {}", id);
        projectsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
