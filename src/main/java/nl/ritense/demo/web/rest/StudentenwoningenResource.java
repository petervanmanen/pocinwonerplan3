package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Studentenwoningen;
import nl.ritense.demo.repository.StudentenwoningenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Studentenwoningen}.
 */
@RestController
@RequestMapping("/api/studentenwoningens")
@Transactional
public class StudentenwoningenResource {

    private final Logger log = LoggerFactory.getLogger(StudentenwoningenResource.class);

    private static final String ENTITY_NAME = "studentenwoningen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentenwoningenRepository studentenwoningenRepository;

    public StudentenwoningenResource(StudentenwoningenRepository studentenwoningenRepository) {
        this.studentenwoningenRepository = studentenwoningenRepository;
    }

    /**
     * {@code POST  /studentenwoningens} : Create a new studentenwoningen.
     *
     * @param studentenwoningen the studentenwoningen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentenwoningen, or with status {@code 400 (Bad Request)} if the studentenwoningen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Studentenwoningen> createStudentenwoningen(@RequestBody Studentenwoningen studentenwoningen)
        throws URISyntaxException {
        log.debug("REST request to save Studentenwoningen : {}", studentenwoningen);
        if (studentenwoningen.getId() != null) {
            throw new BadRequestAlertException("A new studentenwoningen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        studentenwoningen = studentenwoningenRepository.save(studentenwoningen);
        return ResponseEntity.created(new URI("/api/studentenwoningens/" + studentenwoningen.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, studentenwoningen.getId().toString()))
            .body(studentenwoningen);
    }

    /**
     * {@code PUT  /studentenwoningens/:id} : Updates an existing studentenwoningen.
     *
     * @param id the id of the studentenwoningen to save.
     * @param studentenwoningen the studentenwoningen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentenwoningen,
     * or with status {@code 400 (Bad Request)} if the studentenwoningen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentenwoningen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Studentenwoningen> updateStudentenwoningen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Studentenwoningen studentenwoningen
    ) throws URISyntaxException {
        log.debug("REST request to update Studentenwoningen : {}, {}", id, studentenwoningen);
        if (studentenwoningen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentenwoningen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentenwoningenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        studentenwoningen = studentenwoningenRepository.save(studentenwoningen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studentenwoningen.getId().toString()))
            .body(studentenwoningen);
    }

    /**
     * {@code PATCH  /studentenwoningens/:id} : Partial updates given fields of an existing studentenwoningen, field will ignore if it is null
     *
     * @param id the id of the studentenwoningen to save.
     * @param studentenwoningen the studentenwoningen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentenwoningen,
     * or with status {@code 400 (Bad Request)} if the studentenwoningen is not valid,
     * or with status {@code 404 (Not Found)} if the studentenwoningen is not found,
     * or with status {@code 500 (Internal Server Error)} if the studentenwoningen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Studentenwoningen> partialUpdateStudentenwoningen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Studentenwoningen studentenwoningen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Studentenwoningen partially : {}, {}", id, studentenwoningen);
        if (studentenwoningen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, studentenwoningen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentenwoningenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Studentenwoningen> result = studentenwoningenRepository
            .findById(studentenwoningen.getId())
            .map(existingStudentenwoningen -> {
                if (studentenwoningen.getHuurprijs() != null) {
                    existingStudentenwoningen.setHuurprijs(studentenwoningen.getHuurprijs());
                }
                if (studentenwoningen.getZelfstandig() != null) {
                    existingStudentenwoningen.setZelfstandig(studentenwoningen.getZelfstandig());
                }

                return existingStudentenwoningen;
            })
            .map(studentenwoningenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studentenwoningen.getId().toString())
        );
    }

    /**
     * {@code GET  /studentenwoningens} : get all the studentenwoningens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentenwoningens in body.
     */
    @GetMapping("")
    public List<Studentenwoningen> getAllStudentenwoningens() {
        log.debug("REST request to get all Studentenwoningens");
        return studentenwoningenRepository.findAll();
    }

    /**
     * {@code GET  /studentenwoningens/:id} : get the "id" studentenwoningen.
     *
     * @param id the id of the studentenwoningen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentenwoningen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Studentenwoningen> getStudentenwoningen(@PathVariable("id") Long id) {
        log.debug("REST request to get Studentenwoningen : {}", id);
        Optional<Studentenwoningen> studentenwoningen = studentenwoningenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studentenwoningen);
    }

    /**
     * {@code DELETE  /studentenwoningens/:id} : delete the "id" studentenwoningen.
     *
     * @param id the id of the studentenwoningen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentenwoningen(@PathVariable("id") Long id) {
        log.debug("REST request to delete Studentenwoningen : {}", id);
        studentenwoningenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
