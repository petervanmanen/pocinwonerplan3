package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Classh;
import nl.ritense.demo.repository.ClasshRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classh}.
 */
@RestController
@RequestMapping("/api/classhes")
@Transactional
public class ClasshResource {

    private final Logger log = LoggerFactory.getLogger(ClasshResource.class);

    private static final String ENTITY_NAME = "classh";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasshRepository classhRepository;

    public ClasshResource(ClasshRepository classhRepository) {
        this.classhRepository = classhRepository;
    }

    /**
     * {@code POST  /classhes} : Create a new classh.
     *
     * @param classh the classh to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classh, or with status {@code 400 (Bad Request)} if the classh has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classh> createClassh(@RequestBody Classh classh) throws URISyntaxException {
        log.debug("REST request to save Classh : {}", classh);
        if (classh.getId() != null) {
            throw new BadRequestAlertException("A new classh cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classh = classhRepository.save(classh);
        return ResponseEntity.created(new URI("/api/classhes/" + classh.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classh.getId().toString()))
            .body(classh);
    }

    /**
     * {@code GET  /classhes} : get all the classhes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classhes in body.
     */
    @GetMapping("")
    public List<Classh> getAllClasshes() {
        log.debug("REST request to get all Classhes");
        return classhRepository.findAll();
    }

    /**
     * {@code GET  /classhes/:id} : get the "id" classh.
     *
     * @param id the id of the classh to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classh, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classh> getClassh(@PathVariable("id") Long id) {
        log.debug("REST request to get Classh : {}", id);
        Optional<Classh> classh = classhRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classh);
    }

    /**
     * {@code DELETE  /classhes/:id} : delete the "id" classh.
     *
     * @param id the id of the classh to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassh(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classh : {}", id);
        classhRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
