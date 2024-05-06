package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Pvt;
import nl.ritense.demo.repository.PvtRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Pvt}.
 */
@RestController
@RequestMapping("/api/pvts")
@Transactional
public class PvtResource {

    private final Logger log = LoggerFactory.getLogger(PvtResource.class);

    private static final String ENTITY_NAME = "pvt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PvtRepository pvtRepository;

    public PvtResource(PvtRepository pvtRepository) {
        this.pvtRepository = pvtRepository;
    }

    /**
     * {@code POST  /pvts} : Create a new pvt.
     *
     * @param pvt the pvt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pvt, or with status {@code 400 (Bad Request)} if the pvt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pvt> createPvt(@RequestBody Pvt pvt) throws URISyntaxException {
        log.debug("REST request to save Pvt : {}", pvt);
        if (pvt.getId() != null) {
            throw new BadRequestAlertException("A new pvt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pvt = pvtRepository.save(pvt);
        return ResponseEntity.created(new URI("/api/pvts/" + pvt.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, pvt.getId().toString()))
            .body(pvt);
    }

    /**
     * {@code GET  /pvts} : get all the pvts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pvts in body.
     */
    @GetMapping("")
    public List<Pvt> getAllPvts() {
        log.debug("REST request to get all Pvts");
        return pvtRepository.findAll();
    }

    /**
     * {@code GET  /pvts/:id} : get the "id" pvt.
     *
     * @param id the id of the pvt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pvt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pvt> getPvt(@PathVariable("id") Long id) {
        log.debug("REST request to get Pvt : {}", id);
        Optional<Pvt> pvt = pvtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pvt);
    }

    /**
     * {@code DELETE  /pvts/:id} : delete the "id" pvt.
     *
     * @param id the id of the pvt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePvt(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pvt : {}", id);
        pvtRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
