package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Classf;
import nl.ritense.demo.repository.ClassfRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classf}.
 */
@RestController
@RequestMapping("/api/classfs")
@Transactional
public class ClassfResource {

    private final Logger log = LoggerFactory.getLogger(ClassfResource.class);

    private static final String ENTITY_NAME = "classf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassfRepository classfRepository;

    public ClassfResource(ClassfRepository classfRepository) {
        this.classfRepository = classfRepository;
    }

    /**
     * {@code POST  /classfs} : Create a new classf.
     *
     * @param classf the classf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classf, or with status {@code 400 (Bad Request)} if the classf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classf> createClassf(@RequestBody Classf classf) throws URISyntaxException {
        log.debug("REST request to save Classf : {}", classf);
        if (classf.getId() != null) {
            throw new BadRequestAlertException("A new classf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classf = classfRepository.save(classf);
        return ResponseEntity.created(new URI("/api/classfs/" + classf.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classf.getId().toString()))
            .body(classf);
    }

    /**
     * {@code GET  /classfs} : get all the classfs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classfs in body.
     */
    @GetMapping("")
    public List<Classf> getAllClassfs() {
        log.debug("REST request to get all Classfs");
        return classfRepository.findAll();
    }

    /**
     * {@code GET  /classfs/:id} : get the "id" classf.
     *
     * @param id the id of the classf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classf> getClassf(@PathVariable("id") Long id) {
        log.debug("REST request to get Classf : {}", id);
        Optional<Classf> classf = classfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classf);
    }

    /**
     * {@code DELETE  /classfs/:id} : delete the "id" classf.
     *
     * @param id the id of the classf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassf(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classf : {}", id);
        classfRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
