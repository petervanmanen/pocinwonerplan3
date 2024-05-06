package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Classd;
import nl.ritense.demo.repository.ClassdRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classd}.
 */
@RestController
@RequestMapping("/api/classds")
@Transactional
public class ClassdResource {

    private final Logger log = LoggerFactory.getLogger(ClassdResource.class);

    private static final String ENTITY_NAME = "classd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassdRepository classdRepository;

    public ClassdResource(ClassdRepository classdRepository) {
        this.classdRepository = classdRepository;
    }

    /**
     * {@code POST  /classds} : Create a new classd.
     *
     * @param classd the classd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classd, or with status {@code 400 (Bad Request)} if the classd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classd> createClassd(@RequestBody Classd classd) throws URISyntaxException {
        log.debug("REST request to save Classd : {}", classd);
        if (classd.getId() != null) {
            throw new BadRequestAlertException("A new classd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classd = classdRepository.save(classd);
        return ResponseEntity.created(new URI("/api/classds/" + classd.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classd.getId().toString()))
            .body(classd);
    }

    /**
     * {@code GET  /classds} : get all the classds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classds in body.
     */
    @GetMapping("")
    public List<Classd> getAllClassds() {
        log.debug("REST request to get all Classds");
        return classdRepository.findAll();
    }

    /**
     * {@code GET  /classds/:id} : get the "id" classd.
     *
     * @param id the id of the classd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classd> getClassd(@PathVariable("id") Long id) {
        log.debug("REST request to get Classd : {}", id);
        Optional<Classd> classd = classdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classd);
    }

    /**
     * {@code DELETE  /classds/:id} : delete the "id" classd.
     *
     * @param id the id of the classd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassd(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classd : {}", id);
        classdRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
