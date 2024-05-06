package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Projectlocatie;
import nl.ritense.demo.repository.ProjectlocatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Projectlocatie}.
 */
@RestController
@RequestMapping("/api/projectlocaties")
@Transactional
public class ProjectlocatieResource {

    private final Logger log = LoggerFactory.getLogger(ProjectlocatieResource.class);

    private static final String ENTITY_NAME = "projectlocatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectlocatieRepository projectlocatieRepository;

    public ProjectlocatieResource(ProjectlocatieRepository projectlocatieRepository) {
        this.projectlocatieRepository = projectlocatieRepository;
    }

    /**
     * {@code POST  /projectlocaties} : Create a new projectlocatie.
     *
     * @param projectlocatie the projectlocatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectlocatie, or with status {@code 400 (Bad Request)} if the projectlocatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Projectlocatie> createProjectlocatie(@RequestBody Projectlocatie projectlocatie) throws URISyntaxException {
        log.debug("REST request to save Projectlocatie : {}", projectlocatie);
        if (projectlocatie.getId() != null) {
            throw new BadRequestAlertException("A new projectlocatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        projectlocatie = projectlocatieRepository.save(projectlocatie);
        return ResponseEntity.created(new URI("/api/projectlocaties/" + projectlocatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, projectlocatie.getId().toString()))
            .body(projectlocatie);
    }

    /**
     * {@code PUT  /projectlocaties/:id} : Updates an existing projectlocatie.
     *
     * @param id the id of the projectlocatie to save.
     * @param projectlocatie the projectlocatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectlocatie,
     * or with status {@code 400 (Bad Request)} if the projectlocatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectlocatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Projectlocatie> updateProjectlocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Projectlocatie projectlocatie
    ) throws URISyntaxException {
        log.debug("REST request to update Projectlocatie : {}, {}", id, projectlocatie);
        if (projectlocatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectlocatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectlocatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        projectlocatie = projectlocatieRepository.save(projectlocatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectlocatie.getId().toString()))
            .body(projectlocatie);
    }

    /**
     * {@code PATCH  /projectlocaties/:id} : Partial updates given fields of an existing projectlocatie, field will ignore if it is null
     *
     * @param id the id of the projectlocatie to save.
     * @param projectlocatie the projectlocatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectlocatie,
     * or with status {@code 400 (Bad Request)} if the projectlocatie is not valid,
     * or with status {@code 404 (Not Found)} if the projectlocatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectlocatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Projectlocatie> partialUpdateProjectlocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Projectlocatie projectlocatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projectlocatie partially : {}, {}", id, projectlocatie);
        if (projectlocatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectlocatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectlocatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Projectlocatie> result = projectlocatieRepository
            .findById(projectlocatie.getId())
            .map(existingProjectlocatie -> {
                if (projectlocatie.getAdres() != null) {
                    existingProjectlocatie.setAdres(projectlocatie.getAdres());
                }
                if (projectlocatie.getKadastraalperceel() != null) {
                    existingProjectlocatie.setKadastraalperceel(projectlocatie.getKadastraalperceel());
                }
                if (projectlocatie.getKadastralegemeente() != null) {
                    existingProjectlocatie.setKadastralegemeente(projectlocatie.getKadastralegemeente());
                }
                if (projectlocatie.getKadastralesectie() != null) {
                    existingProjectlocatie.setKadastralesectie(projectlocatie.getKadastralesectie());
                }

                return existingProjectlocatie;
            })
            .map(projectlocatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectlocatie.getId().toString())
        );
    }

    /**
     * {@code GET  /projectlocaties} : get all the projectlocaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectlocaties in body.
     */
    @GetMapping("")
    public List<Projectlocatie> getAllProjectlocaties() {
        log.debug("REST request to get all Projectlocaties");
        return projectlocatieRepository.findAll();
    }

    /**
     * {@code GET  /projectlocaties/:id} : get the "id" projectlocatie.
     *
     * @param id the id of the projectlocatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectlocatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Projectlocatie> getProjectlocatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Projectlocatie : {}", id);
        Optional<Projectlocatie> projectlocatie = projectlocatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectlocatie);
    }

    /**
     * {@code DELETE  /projectlocaties/:id} : delete the "id" projectlocatie.
     *
     * @param id the id of the projectlocatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectlocatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Projectlocatie : {}", id);
        projectlocatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
