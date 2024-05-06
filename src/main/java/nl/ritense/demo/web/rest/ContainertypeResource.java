package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Containertype;
import nl.ritense.demo.repository.ContainertypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Containertype}.
 */
@RestController
@RequestMapping("/api/containertypes")
@Transactional
public class ContainertypeResource {

    private final Logger log = LoggerFactory.getLogger(ContainertypeResource.class);

    private static final String ENTITY_NAME = "containertype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContainertypeRepository containertypeRepository;

    public ContainertypeResource(ContainertypeRepository containertypeRepository) {
        this.containertypeRepository = containertypeRepository;
    }

    /**
     * {@code POST  /containertypes} : Create a new containertype.
     *
     * @param containertype the containertype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new containertype, or with status {@code 400 (Bad Request)} if the containertype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Containertype> createContainertype(@Valid @RequestBody Containertype containertype) throws URISyntaxException {
        log.debug("REST request to save Containertype : {}", containertype);
        if (containertype.getId() != null) {
            throw new BadRequestAlertException("A new containertype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        containertype = containertypeRepository.save(containertype);
        return ResponseEntity.created(new URI("/api/containertypes/" + containertype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, containertype.getId().toString()))
            .body(containertype);
    }

    /**
     * {@code PUT  /containertypes/:id} : Updates an existing containertype.
     *
     * @param id the id of the containertype to save.
     * @param containertype the containertype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containertype,
     * or with status {@code 400 (Bad Request)} if the containertype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the containertype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Containertype> updateContainertype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Containertype containertype
    ) throws URISyntaxException {
        log.debug("REST request to update Containertype : {}, {}", id, containertype);
        if (containertype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containertype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containertypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        containertype = containertypeRepository.save(containertype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, containertype.getId().toString()))
            .body(containertype);
    }

    /**
     * {@code PATCH  /containertypes/:id} : Partial updates given fields of an existing containertype, field will ignore if it is null
     *
     * @param id the id of the containertype to save.
     * @param containertype the containertype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containertype,
     * or with status {@code 400 (Bad Request)} if the containertype is not valid,
     * or with status {@code 404 (Not Found)} if the containertype is not found,
     * or with status {@code 500 (Internal Server Error)} if the containertype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Containertype> partialUpdateContainertype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Containertype containertype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Containertype partially : {}, {}", id, containertype);
        if (containertype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containertype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containertypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Containertype> result = containertypeRepository
            .findById(containertype.getId())
            .map(existingContainertype -> {
                if (containertype.getNaam() != null) {
                    existingContainertype.setNaam(containertype.getNaam());
                }
                if (containertype.getOmschrijving() != null) {
                    existingContainertype.setOmschrijving(containertype.getOmschrijving());
                }

                return existingContainertype;
            })
            .map(containertypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, containertype.getId().toString())
        );
    }

    /**
     * {@code GET  /containertypes} : get all the containertypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of containertypes in body.
     */
    @GetMapping("")
    public List<Containertype> getAllContainertypes() {
        log.debug("REST request to get all Containertypes");
        return containertypeRepository.findAll();
    }

    /**
     * {@code GET  /containertypes/:id} : get the "id" containertype.
     *
     * @param id the id of the containertype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the containertype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Containertype> getContainertype(@PathVariable("id") Long id) {
        log.debug("REST request to get Containertype : {}", id);
        Optional<Containertype> containertype = containertypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(containertype);
    }

    /**
     * {@code DELETE  /containertypes/:id} : delete the "id" containertype.
     *
     * @param id the id of the containertype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContainertype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Containertype : {}", id);
        containertypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
