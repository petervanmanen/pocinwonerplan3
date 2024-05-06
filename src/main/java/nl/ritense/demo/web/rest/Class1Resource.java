package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Class1;
import nl.ritense.demo.repository.Class1Repository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Class1}.
 */
@RestController
@RequestMapping("/api/class-1-s")
@Transactional
public class Class1Resource {

    private final Logger log = LoggerFactory.getLogger(Class1Resource.class);

    private static final String ENTITY_NAME = "class1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Class1Repository class1Repository;

    public Class1Resource(Class1Repository class1Repository) {
        this.class1Repository = class1Repository;
    }

    /**
     * {@code POST  /class-1-s} : Create a new class1.
     *
     * @param class1 the class1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new class1, or with status {@code 400 (Bad Request)} if the class1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Class1> createClass1(@RequestBody Class1 class1) throws URISyntaxException {
        log.debug("REST request to save Class1 : {}", class1);
        if (class1.getId() != null) {
            throw new BadRequestAlertException("A new class1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        class1 = class1Repository.save(class1);
        return ResponseEntity.created(new URI("/api/class-1-s/" + class1.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, class1.getId().toString()))
            .body(class1);
    }

    /**
     * {@code PUT  /class-1-s/:id} : Updates an existing class1.
     *
     * @param id the id of the class1 to save.
     * @param class1 the class1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated class1,
     * or with status {@code 400 (Bad Request)} if the class1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the class1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Class1> updateClass1(@PathVariable(value = "id", required = false) final Long id, @RequestBody Class1 class1)
        throws URISyntaxException {
        log.debug("REST request to update Class1 : {}, {}", id, class1);
        if (class1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, class1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!class1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        class1 = class1Repository.save(class1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, class1.getId().toString()))
            .body(class1);
    }

    /**
     * {@code PATCH  /class-1-s/:id} : Partial updates given fields of an existing class1, field will ignore if it is null
     *
     * @param id the id of the class1 to save.
     * @param class1 the class1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated class1,
     * or with status {@code 400 (Bad Request)} if the class1 is not valid,
     * or with status {@code 404 (Not Found)} if the class1 is not found,
     * or with status {@code 500 (Internal Server Error)} if the class1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Class1> partialUpdateClass1(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Class1 class1
    ) throws URISyntaxException {
        log.debug("REST request to partial update Class1 partially : {}, {}", id, class1);
        if (class1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, class1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!class1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Class1> result = class1Repository
            .findById(class1.getId())
            .map(existingClass1 -> {
                if (class1.getWaarde() != null) {
                    existingClass1.setWaarde(class1.getWaarde());
                }

                return existingClass1;
            })
            .map(class1Repository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, class1.getId().toString())
        );
    }

    /**
     * {@code GET  /class-1-s} : get all the class1s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of class1s in body.
     */
    @GetMapping("")
    public List<Class1> getAllClass1s() {
        log.debug("REST request to get all Class1s");
        return class1Repository.findAll();
    }

    /**
     * {@code GET  /class-1-s/:id} : get the "id" class1.
     *
     * @param id the id of the class1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the class1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Class1> getClass1(@PathVariable("id") Long id) {
        log.debug("REST request to get Class1 : {}", id);
        Optional<Class1> class1 = class1Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(class1);
    }

    /**
     * {@code DELETE  /class-1-s/:id} : delete the "id" class1.
     *
     * @param id the id of the class1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass1(@PathVariable("id") Long id) {
        log.debug("REST request to delete Class1 : {}", id);
        class1Repository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
