package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Classg;
import nl.ritense.demo.repository.ClassgRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classg}.
 */
@RestController
@RequestMapping("/api/classgs")
@Transactional
public class ClassgResource {

    private final Logger log = LoggerFactory.getLogger(ClassgResource.class);

    private static final String ENTITY_NAME = "classg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassgRepository classgRepository;

    public ClassgResource(ClassgRepository classgRepository) {
        this.classgRepository = classgRepository;
    }

    /**
     * {@code POST  /classgs} : Create a new classg.
     *
     * @param classg the classg to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classg, or with status {@code 400 (Bad Request)} if the classg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classg> createClassg(@RequestBody Classg classg) throws URISyntaxException {
        log.debug("REST request to save Classg : {}", classg);
        if (classg.getId() != null) {
            throw new BadRequestAlertException("A new classg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classg = classgRepository.save(classg);
        return ResponseEntity.created(new URI("/api/classgs/" + classg.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classg.getId().toString()))
            .body(classg);
    }

    /**
     * {@code GET  /classgs} : get all the classgs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classgs in body.
     */
    @GetMapping("")
    public List<Classg> getAllClassgs() {
        log.debug("REST request to get all Classgs");
        return classgRepository.findAll();
    }

    /**
     * {@code GET  /classgs/:id} : get the "id" classg.
     *
     * @param id the id of the classg to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classg, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classg> getClassg(@PathVariable("id") Long id) {
        log.debug("REST request to get Classg : {}", id);
        Optional<Classg> classg = classgRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classg);
    }

    /**
     * {@code DELETE  /classgs/:id} : delete the "id" classg.
     *
     * @param id the id of the classg to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassg(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classg : {}", id);
        classgRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
