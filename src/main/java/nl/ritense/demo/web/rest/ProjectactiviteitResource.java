package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Projectactiviteit;
import nl.ritense.demo.repository.ProjectactiviteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Projectactiviteit}.
 */
@RestController
@RequestMapping("/api/projectactiviteits")
@Transactional
public class ProjectactiviteitResource {

    private final Logger log = LoggerFactory.getLogger(ProjectactiviteitResource.class);

    private static final String ENTITY_NAME = "projectactiviteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectactiviteitRepository projectactiviteitRepository;

    public ProjectactiviteitResource(ProjectactiviteitRepository projectactiviteitRepository) {
        this.projectactiviteitRepository = projectactiviteitRepository;
    }

    /**
     * {@code POST  /projectactiviteits} : Create a new projectactiviteit.
     *
     * @param projectactiviteit the projectactiviteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectactiviteit, or with status {@code 400 (Bad Request)} if the projectactiviteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Projectactiviteit> createProjectactiviteit(@RequestBody Projectactiviteit projectactiviteit)
        throws URISyntaxException {
        log.debug("REST request to save Projectactiviteit : {}", projectactiviteit);
        if (projectactiviteit.getId() != null) {
            throw new BadRequestAlertException("A new projectactiviteit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        projectactiviteit = projectactiviteitRepository.save(projectactiviteit);
        return ResponseEntity.created(new URI("/api/projectactiviteits/" + projectactiviteit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, projectactiviteit.getId().toString()))
            .body(projectactiviteit);
    }

    /**
     * {@code PUT  /projectactiviteits/:id} : Updates an existing projectactiviteit.
     *
     * @param id the id of the projectactiviteit to save.
     * @param projectactiviteit the projectactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectactiviteit,
     * or with status {@code 400 (Bad Request)} if the projectactiviteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Projectactiviteit> updateProjectactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Projectactiviteit projectactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to update Projectactiviteit : {}, {}", id, projectactiviteit);
        if (projectactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        projectactiviteit = projectactiviteitRepository.save(projectactiviteit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectactiviteit.getId().toString()))
            .body(projectactiviteit);
    }

    /**
     * {@code PATCH  /projectactiviteits/:id} : Partial updates given fields of an existing projectactiviteit, field will ignore if it is null
     *
     * @param id the id of the projectactiviteit to save.
     * @param projectactiviteit the projectactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectactiviteit,
     * or with status {@code 400 (Bad Request)} if the projectactiviteit is not valid,
     * or with status {@code 404 (Not Found)} if the projectactiviteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Projectactiviteit> partialUpdateProjectactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Projectactiviteit projectactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projectactiviteit partially : {}, {}", id, projectactiviteit);
        if (projectactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Projectactiviteit> result = projectactiviteitRepository
            .findById(projectactiviteit.getId())
            .map(projectactiviteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectactiviteit.getId().toString())
        );
    }

    /**
     * {@code GET  /projectactiviteits} : get all the projectactiviteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectactiviteits in body.
     */
    @GetMapping("")
    public List<Projectactiviteit> getAllProjectactiviteits() {
        log.debug("REST request to get all Projectactiviteits");
        return projectactiviteitRepository.findAll();
    }

    /**
     * {@code GET  /projectactiviteits/:id} : get the "id" projectactiviteit.
     *
     * @param id the id of the projectactiviteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectactiviteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Projectactiviteit> getProjectactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Projectactiviteit : {}", id);
        Optional<Projectactiviteit> projectactiviteit = projectactiviteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectactiviteit);
    }

    /**
     * {@code DELETE  /projectactiviteits/:id} : delete the "id" projectactiviteit.
     *
     * @param id the id of the projectactiviteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Projectactiviteit : {}", id);
        projectactiviteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
