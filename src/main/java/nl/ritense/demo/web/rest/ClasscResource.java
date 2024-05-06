package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Classc;
import nl.ritense.demo.repository.ClasscRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classc}.
 */
@RestController
@RequestMapping("/api/classcs")
@Transactional
public class ClasscResource {

    private final Logger log = LoggerFactory.getLogger(ClasscResource.class);

    private static final String ENTITY_NAME = "classc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasscRepository classcRepository;

    public ClasscResource(ClasscRepository classcRepository) {
        this.classcRepository = classcRepository;
    }

    /**
     * {@code POST  /classcs} : Create a new classc.
     *
     * @param classc the classc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classc, or with status {@code 400 (Bad Request)} if the classc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classc> createClassc(@RequestBody Classc classc) throws URISyntaxException {
        log.debug("REST request to save Classc : {}", classc);
        if (classc.getId() != null) {
            throw new BadRequestAlertException("A new classc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classc = classcRepository.save(classc);
        return ResponseEntity.created(new URI("/api/classcs/" + classc.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classc.getId().toString()))
            .body(classc);
    }

    /**
     * {@code PUT  /classcs/:id} : Updates an existing classc.
     *
     * @param id the id of the classc to save.
     * @param classc the classc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classc,
     * or with status {@code 400 (Bad Request)} if the classc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Classc> updateClassc(@PathVariable(value = "id", required = false) final Long id, @RequestBody Classc classc)
        throws URISyntaxException {
        log.debug("REST request to update Classc : {}, {}", id, classc);
        if (classc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classcRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        classc = classcRepository.save(classc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classc.getId().toString()))
            .body(classc);
    }

    /**
     * {@code PATCH  /classcs/:id} : Partial updates given fields of an existing classc, field will ignore if it is null
     *
     * @param id the id of the classc to save.
     * @param classc the classc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classc,
     * or with status {@code 400 (Bad Request)} if the classc is not valid,
     * or with status {@code 404 (Not Found)} if the classc is not found,
     * or with status {@code 500 (Internal Server Error)} if the classc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Classc> partialUpdateClassc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Classc classc
    ) throws URISyntaxException {
        log.debug("REST request to partial update Classc partially : {}, {}", id, classc);
        if (classc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classcRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Classc> result = classcRepository
            .findById(classc.getId())
            .map(existingClassc -> {
                if (classc.getBedrag() != null) {
                    existingClassc.setBedrag(classc.getBedrag());
                }
                if (classc.getNaam() != null) {
                    existingClassc.setNaam(classc.getNaam());
                }

                return existingClassc;
            })
            .map(classcRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classc.getId().toString())
        );
    }

    /**
     * {@code GET  /classcs} : get all the classcs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classcs in body.
     */
    @GetMapping("")
    public List<Classc> getAllClasscs() {
        log.debug("REST request to get all Classcs");
        return classcRepository.findAll();
    }

    /**
     * {@code GET  /classcs/:id} : get the "id" classc.
     *
     * @param id the id of the classc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classc> getClassc(@PathVariable("id") Long id) {
        log.debug("REST request to get Classc : {}", id);
        Optional<Classc> classc = classcRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classc);
    }

    /**
     * {@code DELETE  /classcs/:id} : delete the "id" classc.
     *
     * @param id the id of the classc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassc(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classc : {}", id);
        classcRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
