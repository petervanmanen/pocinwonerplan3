package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Meldingongeval;
import nl.ritense.demo.repository.MeldingongevalRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Meldingongeval}.
 */
@RestController
@RequestMapping("/api/meldingongevals")
@Transactional
public class MeldingongevalResource {

    private final Logger log = LoggerFactory.getLogger(MeldingongevalResource.class);

    private static final String ENTITY_NAME = "meldingongeval";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeldingongevalRepository meldingongevalRepository;

    public MeldingongevalResource(MeldingongevalRepository meldingongevalRepository) {
        this.meldingongevalRepository = meldingongevalRepository;
    }

    /**
     * {@code POST  /meldingongevals} : Create a new meldingongeval.
     *
     * @param meldingongeval the meldingongeval to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meldingongeval, or with status {@code 400 (Bad Request)} if the meldingongeval has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Meldingongeval> createMeldingongeval(@RequestBody Meldingongeval meldingongeval) throws URISyntaxException {
        log.debug("REST request to save Meldingongeval : {}", meldingongeval);
        if (meldingongeval.getId() != null) {
            throw new BadRequestAlertException("A new meldingongeval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        meldingongeval = meldingongevalRepository.save(meldingongeval);
        return ResponseEntity.created(new URI("/api/meldingongevals/" + meldingongeval.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, meldingongeval.getId().toString()))
            .body(meldingongeval);
    }

    /**
     * {@code GET  /meldingongevals} : get all the meldingongevals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meldingongevals in body.
     */
    @GetMapping("")
    public List<Meldingongeval> getAllMeldingongevals() {
        log.debug("REST request to get all Meldingongevals");
        return meldingongevalRepository.findAll();
    }

    /**
     * {@code GET  /meldingongevals/:id} : get the "id" meldingongeval.
     *
     * @param id the id of the meldingongeval to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meldingongeval, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Meldingongeval> getMeldingongeval(@PathVariable("id") Long id) {
        log.debug("REST request to get Meldingongeval : {}", id);
        Optional<Meldingongeval> meldingongeval = meldingongevalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(meldingongeval);
    }

    /**
     * {@code DELETE  /meldingongevals/:id} : delete the "id" meldingongeval.
     *
     * @param id the id of the meldingongeval to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeldingongeval(@PathVariable("id") Long id) {
        log.debug("REST request to delete Meldingongeval : {}", id);
        meldingongevalRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
