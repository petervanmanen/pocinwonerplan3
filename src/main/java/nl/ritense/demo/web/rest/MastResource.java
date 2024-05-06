package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Mast;
import nl.ritense.demo.repository.MastRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Mast}.
 */
@RestController
@RequestMapping("/api/masts")
@Transactional
public class MastResource {

    private final Logger log = LoggerFactory.getLogger(MastResource.class);

    private static final String ENTITY_NAME = "mast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MastRepository mastRepository;

    public MastResource(MastRepository mastRepository) {
        this.mastRepository = mastRepository;
    }

    /**
     * {@code POST  /masts} : Create a new mast.
     *
     * @param mast the mast to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mast, or with status {@code 400 (Bad Request)} if the mast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Mast> createMast(@RequestBody Mast mast) throws URISyntaxException {
        log.debug("REST request to save Mast : {}", mast);
        if (mast.getId() != null) {
            throw new BadRequestAlertException("A new mast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mast = mastRepository.save(mast);
        return ResponseEntity.created(new URI("/api/masts/" + mast.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, mast.getId().toString()))
            .body(mast);
    }

    /**
     * {@code GET  /masts} : get all the masts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of masts in body.
     */
    @GetMapping("")
    public List<Mast> getAllMasts() {
        log.debug("REST request to get all Masts");
        return mastRepository.findAll();
    }

    /**
     * {@code GET  /masts/:id} : get the "id" mast.
     *
     * @param id the id of the mast to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mast, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mast> getMast(@PathVariable("id") Long id) {
        log.debug("REST request to get Mast : {}", id);
        Optional<Mast> mast = mastRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mast);
    }

    /**
     * {@code DELETE  /masts/:id} : delete the "id" mast.
     *
     * @param id the id of the mast to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMast(@PathVariable("id") Long id) {
        log.debug("REST request to delete Mast : {}", id);
        mastRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
