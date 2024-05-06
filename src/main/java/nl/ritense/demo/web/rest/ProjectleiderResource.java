package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Projectleider;
import nl.ritense.demo.repository.ProjectleiderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Projectleider}.
 */
@RestController
@RequestMapping("/api/projectleiders")
@Transactional
public class ProjectleiderResource {

    private final Logger log = LoggerFactory.getLogger(ProjectleiderResource.class);

    private static final String ENTITY_NAME = "projectleider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectleiderRepository projectleiderRepository;

    public ProjectleiderResource(ProjectleiderRepository projectleiderRepository) {
        this.projectleiderRepository = projectleiderRepository;
    }

    /**
     * {@code POST  /projectleiders} : Create a new projectleider.
     *
     * @param projectleider the projectleider to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectleider, or with status {@code 400 (Bad Request)} if the projectleider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Projectleider> createProjectleider(@RequestBody Projectleider projectleider) throws URISyntaxException {
        log.debug("REST request to save Projectleider : {}", projectleider);
        if (projectleider.getId() != null) {
            throw new BadRequestAlertException("A new projectleider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        projectleider = projectleiderRepository.save(projectleider);
        return ResponseEntity.created(new URI("/api/projectleiders/" + projectleider.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, projectleider.getId().toString()))
            .body(projectleider);
    }

    /**
     * {@code PUT  /projectleiders/:id} : Updates an existing projectleider.
     *
     * @param id the id of the projectleider to save.
     * @param projectleider the projectleider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectleider,
     * or with status {@code 400 (Bad Request)} if the projectleider is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectleider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Projectleider> updateProjectleider(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Projectleider projectleider
    ) throws URISyntaxException {
        log.debug("REST request to update Projectleider : {}, {}", id, projectleider);
        if (projectleider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectleider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectleiderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        projectleider = projectleiderRepository.save(projectleider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectleider.getId().toString()))
            .body(projectleider);
    }

    /**
     * {@code PATCH  /projectleiders/:id} : Partial updates given fields of an existing projectleider, field will ignore if it is null
     *
     * @param id the id of the projectleider to save.
     * @param projectleider the projectleider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectleider,
     * or with status {@code 400 (Bad Request)} if the projectleider is not valid,
     * or with status {@code 404 (Not Found)} if the projectleider is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectleider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Projectleider> partialUpdateProjectleider(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Projectleider projectleider
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projectleider partially : {}, {}", id, projectleider);
        if (projectleider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectleider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectleiderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Projectleider> result = projectleiderRepository
            .findById(projectleider.getId())
            .map(existingProjectleider -> {
                if (projectleider.getNaam() != null) {
                    existingProjectleider.setNaam(projectleider.getNaam());
                }

                return existingProjectleider;
            })
            .map(projectleiderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectleider.getId().toString())
        );
    }

    /**
     * {@code GET  /projectleiders} : get all the projectleiders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectleiders in body.
     */
    @GetMapping("")
    public List<Projectleider> getAllProjectleiders() {
        log.debug("REST request to get all Projectleiders");
        return projectleiderRepository.findAll();
    }

    /**
     * {@code GET  /projectleiders/:id} : get the "id" projectleider.
     *
     * @param id the id of the projectleider to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectleider, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Projectleider> getProjectleider(@PathVariable("id") Long id) {
        log.debug("REST request to get Projectleider : {}", id);
        Optional<Projectleider> projectleider = projectleiderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectleider);
    }

    /**
     * {@code DELETE  /projectleiders/:id} : delete the "id" projectleider.
     *
     * @param id the id of the projectleider to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectleider(@PathVariable("id") Long id) {
        log.debug("REST request to delete Projectleider : {}", id);
        projectleiderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
