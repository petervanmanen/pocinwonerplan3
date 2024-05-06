package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Classa;
import nl.ritense.demo.repository.ClassaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Classa}.
 */
@RestController
@RequestMapping("/api/classas")
@Transactional
public class ClassaResource {

    private final Logger log = LoggerFactory.getLogger(ClassaResource.class);

    private static final String ENTITY_NAME = "classa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassaRepository classaRepository;

    public ClassaResource(ClassaRepository classaRepository) {
        this.classaRepository = classaRepository;
    }

    /**
     * {@code POST  /classas} : Create a new classa.
     *
     * @param classa the classa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classa, or with status {@code 400 (Bad Request)} if the classa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Classa> createClassa(@RequestBody Classa classa) throws URISyntaxException {
        log.debug("REST request to save Classa : {}", classa);
        if (classa.getId() != null) {
            throw new BadRequestAlertException("A new classa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classa = classaRepository.save(classa);
        return ResponseEntity.created(new URI("/api/classas/" + classa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, classa.getId().toString()))
            .body(classa);
    }

    /**
     * {@code PUT  /classas/:id} : Updates an existing classa.
     *
     * @param id the id of the classa to save.
     * @param classa the classa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classa,
     * or with status {@code 400 (Bad Request)} if the classa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Classa> updateClassa(@PathVariable(value = "id", required = false) final Long id, @RequestBody Classa classa)
        throws URISyntaxException {
        log.debug("REST request to update Classa : {}, {}", id, classa);
        if (classa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        classa = classaRepository.save(classa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classa.getId().toString()))
            .body(classa);
    }

    /**
     * {@code PATCH  /classas/:id} : Partial updates given fields of an existing classa, field will ignore if it is null
     *
     * @param id the id of the classa to save.
     * @param classa the classa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classa,
     * or with status {@code 400 (Bad Request)} if the classa is not valid,
     * or with status {@code 404 (Not Found)} if the classa is not found,
     * or with status {@code 500 (Internal Server Error)} if the classa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Classa> partialUpdateClassa(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Classa classa
    ) throws URISyntaxException {
        log.debug("REST request to partial update Classa partially : {}, {}", id, classa);
        if (classa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Classa> result = classaRepository
            .findById(classa.getId())
            .map(existingClassa -> {
                if (classa.getNaam() != null) {
                    existingClassa.setNaam(classa.getNaam());
                }

                return existingClassa;
            })
            .map(classaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classa.getId().toString())
        );
    }

    /**
     * {@code GET  /classas} : get all the classas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classas in body.
     */
    @GetMapping("")
    public List<Classa> getAllClassas() {
        log.debug("REST request to get all Classas");
        return classaRepository.findAll();
    }

    /**
     * {@code GET  /classas/:id} : get the "id" classa.
     *
     * @param id the id of the classa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Classa> getClassa(@PathVariable("id") Long id) {
        log.debug("REST request to get Classa : {}", id);
        Optional<Classa> classa = classaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classa);
    }

    /**
     * {@code DELETE  /classas/:id} : delete the "id" classa.
     *
     * @param id the id of the classa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassa(@PathVariable("id") Long id) {
        log.debug("REST request to delete Classa : {}", id);
        classaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
