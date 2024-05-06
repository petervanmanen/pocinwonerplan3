package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Knm;
import nl.ritense.demo.repository.KnmRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Knm}.
 */
@RestController
@RequestMapping("/api/knms")
@Transactional
public class KnmResource {

    private final Logger log = LoggerFactory.getLogger(KnmResource.class);

    private static final String ENTITY_NAME = "knm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KnmRepository knmRepository;

    public KnmResource(KnmRepository knmRepository) {
        this.knmRepository = knmRepository;
    }

    /**
     * {@code POST  /knms} : Create a new knm.
     *
     * @param knm the knm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new knm, or with status {@code 400 (Bad Request)} if the knm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Knm> createKnm(@RequestBody Knm knm) throws URISyntaxException {
        log.debug("REST request to save Knm : {}", knm);
        if (knm.getId() != null) {
            throw new BadRequestAlertException("A new knm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        knm = knmRepository.save(knm);
        return ResponseEntity.created(new URI("/api/knms/" + knm.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, knm.getId().toString()))
            .body(knm);
    }

    /**
     * {@code GET  /knms} : get all the knms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of knms in body.
     */
    @GetMapping("")
    public List<Knm> getAllKnms() {
        log.debug("REST request to get all Knms");
        return knmRepository.findAll();
    }

    /**
     * {@code GET  /knms/:id} : get the "id" knm.
     *
     * @param id the id of the knm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the knm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Knm> getKnm(@PathVariable("id") Long id) {
        log.debug("REST request to get Knm : {}", id);
        Optional<Knm> knm = knmRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(knm);
    }

    /**
     * {@code DELETE  /knms/:id} : delete the "id" knm.
     *
     * @param id the id of the knm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKnm(@PathVariable("id") Long id) {
        log.debug("REST request to delete Knm : {}", id);
        knmRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
