package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Classi;
import nl.ritense.demo.repository.ClassiRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classi}.
 */
@RestController
@RequestMapping("/api/classis")
@Transactional
public class ClassiResource {

    private final Logger log = LoggerFactory.getLogger(ClassiResource.class);

    private static final String ENTITY_NAME = "classi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassiRepository classiRepository;

    public ClassiResource(ClassiRepository classiRepository) {
        this.classiRepository = classiRepository;
    }

    /**
     * {@code POST  /classis} : Create a new classi.
     *
     * @param classi the classi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classi, or with status {@code 400 (Bad Request)} if the classi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classi> createClassi(@RequestBody Classi classi) throws URISyntaxException {
        log.debug("REST request to save Classi : {}", classi);
        if (classi.getId() != null) {
            throw new BadRequestAlertException("A new classi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classi = classiRepository.save(classi);
        return ResponseEntity.created(new URI("/api/classis/" + classi.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classi.getId().toString()))
            .body(classi);
    }

    /**
     * {@code GET  /classis} : get all the classis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classis in body.
     */
    @GetMapping("")
    public List<Classi> getAllClassis() {
        log.debug("REST request to get all Classis");
        return classiRepository.findAll();
    }

    /**
     * {@code GET  /classis/:id} : get the "id" classi.
     *
     * @param id the id of the classi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classi> getClassi(@PathVariable("id") Long id) {
        log.debug("REST request to get Classi : {}", id);
        Optional<Classi> classi = classiRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classi);
    }

    /**
     * {@code DELETE  /classis/:id} : delete the "id" classi.
     *
     * @param id the id of the classi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassi(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classi : {}", id);
        classiRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
