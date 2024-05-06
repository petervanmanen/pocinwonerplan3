package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Z;
import nl.ritense.demo.repository.ZRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Z}.
 */
@RestController
@RequestMapping("/api/zs")
@Transactional
public class ZResource {

    private final Logger log = LoggerFactory.getLogger(ZResource.class);

    private static final String ENTITY_NAME = "z";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZRepository zRepository;

    public ZResource(ZRepository zRepository) {
        this.zRepository = zRepository;
    }

    /**
     * {@code POST  /zs} : Create a new z.
     *
     * @param z the z to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new z, or with status {@code 400 (Bad Request)} if the z has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Z> createZ(@RequestBody Z z) throws URISyntaxException {
        log.debug("REST request to save Z : {}", z);
        if (z.getId() != null) {
            throw new BadRequestAlertException("A new z cannot already have an ID", ENTITY_NAME, "idexists");
        }
        z = zRepository.save(z);
        return ResponseEntity.created(new URI("/api/zs/" + z.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, z.getId().toString()))
            .body(z);
    }

    /**
     * {@code GET  /zs} : get all the zS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zS in body.
     */
    @GetMapping("")
    public List<Z> getAllZS() {
        log.debug("REST request to get all ZS");
        return zRepository.findAll();
    }

    /**
     * {@code GET  /zs/:id} : get the "id" z.
     *
     * @param id the id of the z to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the z, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Z> getZ(@PathVariable("id") Long id) {
        log.debug("REST request to get Z : {}", id);
        Optional<Z> z = zRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(z);
    }

    /**
     * {@code DELETE  /zs/:id} : delete the "id" z.
     *
     * @param id the id of the z to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZ(@PathVariable("id") Long id) {
        log.debug("REST request to delete Z : {}", id);
        zRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
