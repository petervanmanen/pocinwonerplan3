package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Classb;
import nl.ritense.demo.repository.ClassbRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classb}.
 */
@RestController
@RequestMapping("/api/classbs")
@Transactional
public class ClassbResource {

    private final Logger log = LoggerFactory.getLogger(ClassbResource.class);

    private static final String ENTITY_NAME = "classb";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassbRepository classbRepository;

    public ClassbResource(ClassbRepository classbRepository) {
        this.classbRepository = classbRepository;
    }

    /**
     * {@code POST  /classbs} : Create a new classb.
     *
     * @param classb the classb to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classb, or with status {@code 400 (Bad Request)} if the classb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classb> createClassb(@Valid @RequestBody Classb classb) throws URISyntaxException {
        log.debug("REST request to save Classb : {}", classb);
        if (classb.getId() != null) {
            throw new BadRequestAlertException("A new classb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classb = classbRepository.save(classb);
        return ResponseEntity.created(new URI("/api/classbs/" + classb.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classb.getId().toString()))
            .body(classb);
    }

    /**
     * {@code PUT  /classbs/:id} : Updates an existing classb.
     *
     * @param id the id of the classb to save.
     * @param classb the classb to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classb,
     * or with status {@code 400 (Bad Request)} if the classb is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classb couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Classb> updateClassb(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Classb classb
    ) throws URISyntaxException {
        log.debug("REST request to update Classb : {}, {}", id, classb);
        if (classb.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classb.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        classb = classbRepository.save(classb);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classb.getId().toString()))
            .body(classb);
    }

    /**
     * {@code PATCH  /classbs/:id} : Partial updates given fields of an existing classb, field will ignore if it is null
     *
     * @param id the id of the classb to save.
     * @param classb the classb to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classb,
     * or with status {@code 400 (Bad Request)} if the classb is not valid,
     * or with status {@code 404 (Not Found)} if the classb is not found,
     * or with status {@code 500 (Internal Server Error)} if the classb couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Classb> partialUpdateClassb(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Classb classb
    ) throws URISyntaxException {
        log.debug("REST request to partial update Classb partially : {}, {}", id, classb);
        if (classb.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classb.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classbRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Classb> result = classbRepository
            .findById(classb.getId())
            .map(existingClassb -> {
                if (classb.getNaam() != null) {
                    existingClassb.setNaam(classb.getNaam());
                }
                if (classb.getOmschrijving() != null) {
                    existingClassb.setOmschrijving(classb.getOmschrijving());
                }

                return existingClassb;
            })
            .map(classbRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classb.getId().toString())
        );
    }

    /**
     * {@code GET  /classbs} : get all the classbs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classbs in body.
     */
    @GetMapping("")
    public List<Classb> getAllClassbs() {
        log.debug("REST request to get all Classbs");
        return classbRepository.findAll();
    }

    /**
     * {@code GET  /classbs/:id} : get the "id" classb.
     *
     * @param id the id of the classb to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classb, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classb> getClassb(@PathVariable("id") Long id) {
        log.debug("REST request to get Classb : {}", id);
        Optional<Classb> classb = classbRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classb);
    }

    /**
     * {@code DELETE  /classbs/:id} : delete the "id" classb.
     *
     * @param id the id of the classb to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassb(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classb : {}", id);
        classbRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
