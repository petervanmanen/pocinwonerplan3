package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.B1;
import nl.ritense.demo.repository.B1Repository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.B1}.
 */
@RestController
@RequestMapping("/api/b-1-s")
@Transactional
public class B1Resource {

    private final Logger log = LoggerFactory.getLogger(B1Resource.class);

    private static final String ENTITY_NAME = "b1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final B1Repository b1Repository;

    public B1Resource(B1Repository b1Repository) {
        this.b1Repository = b1Repository;
    }

    /**
     * {@code POST  /b-1-s} : Create a new b1.
     *
     * @param b1 the b1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new b1, or with status {@code 400 (Bad Request)} if the b1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<B1> createB1(@RequestBody B1 b1) throws URISyntaxException {
        log.debug("REST request to save B1 : {}", b1);
        if (b1.getId() != null) {
            throw new BadRequestAlertException("A new b1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        b1 = b1Repository.save(b1);
        return ResponseEntity.created(new URI("/api/b-1-s/" + b1.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, b1.getId().toString()))
            .body(b1);
    }

    /**
     * {@code GET  /b-1-s} : get all the b1s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of b1s in body.
     */
    @GetMapping("")
    public List<B1> getAllB1s() {
        log.debug("REST request to get all B1s");
        return b1Repository.findAll();
    }

    /**
     * {@code GET  /b-1-s/:id} : get the "id" b1.
     *
     * @param id the id of the b1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the b1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<B1> getB1(@PathVariable("id") Long id) {
        log.debug("REST request to get B1 : {}", id);
        Optional<B1> b1 = b1Repository.findById(id);
        return ResponseUtil.wrapOrNotFound(b1);
    }

    /**
     * {@code DELETE  /b-1-s/:id} : delete the "id" b1.
     *
     * @param id the id of the b1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteB1(@PathVariable("id") Long id) {
        log.debug("REST request to delete B1 : {}", id);
        b1Repository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
