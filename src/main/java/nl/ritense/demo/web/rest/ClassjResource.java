package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Classj;
import nl.ritense.demo.repository.ClassjRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classj}.
 */
@RestController
@RequestMapping("/api/classjs")
@Transactional
public class ClassjResource {

    private final Logger log = LoggerFactory.getLogger(ClassjResource.class);

    private static final String ENTITY_NAME = "classj";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassjRepository classjRepository;

    public ClassjResource(ClassjRepository classjRepository) {
        this.classjRepository = classjRepository;
    }

    /**
     * {@code POST  /classjs} : Create a new classj.
     *
     * @param classj the classj to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classj, or with status {@code 400 (Bad Request)} if the classj has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classj> createClassj(@RequestBody Classj classj) throws URISyntaxException {
        log.debug("REST request to save Classj : {}", classj);
        if (classj.getId() != null) {
            throw new BadRequestAlertException("A new classj cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classj = classjRepository.save(classj);
        return ResponseEntity.created(new URI("/api/classjs/" + classj.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classj.getId().toString()))
            .body(classj);
    }

    /**
     * {@code GET  /classjs} : get all the classjs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classjs in body.
     */
    @GetMapping("")
    public List<Classj> getAllClassjs() {
        log.debug("REST request to get all Classjs");
        return classjRepository.findAll();
    }

    /**
     * {@code GET  /classjs/:id} : get the "id" classj.
     *
     * @param id the id of the classj to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classj, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classj> getClassj(@PathVariable("id") Long id) {
        log.debug("REST request to get Classj : {}", id);
        Optional<Classj> classj = classjRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classj);
    }

    /**
     * {@code DELETE  /classjs/:id} : delete the "id" classj.
     *
     * @param id the id of the classj to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassj(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classj : {}", id);
        classjRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
