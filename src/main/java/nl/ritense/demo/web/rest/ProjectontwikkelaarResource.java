package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Projectontwikkelaar;
import nl.ritense.demo.repository.ProjectontwikkelaarRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Projectontwikkelaar}.
 */
@RestController
@RequestMapping("/api/projectontwikkelaars")
@Transactional
public class ProjectontwikkelaarResource {

    private final Logger log = LoggerFactory.getLogger(ProjectontwikkelaarResource.class);

    private static final String ENTITY_NAME = "projectontwikkelaar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectontwikkelaarRepository projectontwikkelaarRepository;

    public ProjectontwikkelaarResource(ProjectontwikkelaarRepository projectontwikkelaarRepository) {
        this.projectontwikkelaarRepository = projectontwikkelaarRepository;
    }

    /**
     * {@code POST  /projectontwikkelaars} : Create a new projectontwikkelaar.
     *
     * @param projectontwikkelaar the projectontwikkelaar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectontwikkelaar, or with status {@code 400 (Bad Request)} if the projectontwikkelaar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Projectontwikkelaar> createProjectontwikkelaar(@Valid @RequestBody Projectontwikkelaar projectontwikkelaar)
        throws URISyntaxException {
        log.debug("REST request to save Projectontwikkelaar : {}", projectontwikkelaar);
        if (projectontwikkelaar.getId() != null) {
            throw new BadRequestAlertException("A new projectontwikkelaar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        projectontwikkelaar = projectontwikkelaarRepository.save(projectontwikkelaar);
        return ResponseEntity.created(new URI("/api/projectontwikkelaars/" + projectontwikkelaar.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, projectontwikkelaar.getId().toString()))
            .body(projectontwikkelaar);
    }

    /**
     * {@code PUT  /projectontwikkelaars/:id} : Updates an existing projectontwikkelaar.
     *
     * @param id the id of the projectontwikkelaar to save.
     * @param projectontwikkelaar the projectontwikkelaar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectontwikkelaar,
     * or with status {@code 400 (Bad Request)} if the projectontwikkelaar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectontwikkelaar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Projectontwikkelaar> updateProjectontwikkelaar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Projectontwikkelaar projectontwikkelaar
    ) throws URISyntaxException {
        log.debug("REST request to update Projectontwikkelaar : {}, {}", id, projectontwikkelaar);
        if (projectontwikkelaar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectontwikkelaar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectontwikkelaarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        projectontwikkelaar = projectontwikkelaarRepository.save(projectontwikkelaar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectontwikkelaar.getId().toString()))
            .body(projectontwikkelaar);
    }

    /**
     * {@code PATCH  /projectontwikkelaars/:id} : Partial updates given fields of an existing projectontwikkelaar, field will ignore if it is null
     *
     * @param id the id of the projectontwikkelaar to save.
     * @param projectontwikkelaar the projectontwikkelaar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectontwikkelaar,
     * or with status {@code 400 (Bad Request)} if the projectontwikkelaar is not valid,
     * or with status {@code 404 (Not Found)} if the projectontwikkelaar is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectontwikkelaar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Projectontwikkelaar> partialUpdateProjectontwikkelaar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Projectontwikkelaar projectontwikkelaar
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projectontwikkelaar partially : {}, {}", id, projectontwikkelaar);
        if (projectontwikkelaar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectontwikkelaar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectontwikkelaarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Projectontwikkelaar> result = projectontwikkelaarRepository
            .findById(projectontwikkelaar.getId())
            .map(existingProjectontwikkelaar -> {
                if (projectontwikkelaar.getAdres() != null) {
                    existingProjectontwikkelaar.setAdres(projectontwikkelaar.getAdres());
                }
                if (projectontwikkelaar.getNaam() != null) {
                    existingProjectontwikkelaar.setNaam(projectontwikkelaar.getNaam());
                }

                return existingProjectontwikkelaar;
            })
            .map(projectontwikkelaarRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectontwikkelaar.getId().toString())
        );
    }

    /**
     * {@code GET  /projectontwikkelaars} : get all the projectontwikkelaars.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectontwikkelaars in body.
     */
    @GetMapping("")
    public List<Projectontwikkelaar> getAllProjectontwikkelaars(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Projectontwikkelaars");
        if (eagerload) {
            return projectontwikkelaarRepository.findAllWithEagerRelationships();
        } else {
            return projectontwikkelaarRepository.findAll();
        }
    }

    /**
     * {@code GET  /projectontwikkelaars/:id} : get the "id" projectontwikkelaar.
     *
     * @param id the id of the projectontwikkelaar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectontwikkelaar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Projectontwikkelaar> getProjectontwikkelaar(@PathVariable("id") Long id) {
        log.debug("REST request to get Projectontwikkelaar : {}", id);
        Optional<Projectontwikkelaar> projectontwikkelaar = projectontwikkelaarRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(projectontwikkelaar);
    }

    /**
     * {@code DELETE  /projectontwikkelaars/:id} : delete the "id" projectontwikkelaar.
     *
     * @param id the id of the projectontwikkelaar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectontwikkelaar(@PathVariable("id") Long id) {
        log.debug("REST request to delete Projectontwikkelaar : {}", id);
        projectontwikkelaarRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
