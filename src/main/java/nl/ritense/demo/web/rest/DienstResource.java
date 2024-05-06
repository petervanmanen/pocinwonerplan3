package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Dienst;
import nl.ritense.demo.repository.DienstRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Dienst}.
 */
@RestController
@RequestMapping("/api/diensts")
@Transactional
public class DienstResource {

    private final Logger log = LoggerFactory.getLogger(DienstResource.class);

    private static final String ENTITY_NAME = "dienst";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DienstRepository dienstRepository;

    public DienstResource(DienstRepository dienstRepository) {
        this.dienstRepository = dienstRepository;
    }

    /**
     * {@code POST  /diensts} : Create a new dienst.
     *
     * @param dienst the dienst to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dienst, or with status {@code 400 (Bad Request)} if the dienst has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Dienst> createDienst(@RequestBody Dienst dienst) throws URISyntaxException {
        log.debug("REST request to save Dienst : {}", dienst);
        if (dienst.getId() != null) {
            throw new BadRequestAlertException("A new dienst cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dienst = dienstRepository.save(dienst);
        return ResponseEntity.created(new URI("/api/diensts/" + dienst.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, dienst.getId().toString()))
            .body(dienst);
    }

    /**
     * {@code PUT  /diensts/:id} : Updates an existing dienst.
     *
     * @param id the id of the dienst to save.
     * @param dienst the dienst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dienst,
     * or with status {@code 400 (Bad Request)} if the dienst is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dienst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Dienst> updateDienst(@PathVariable(value = "id", required = false) final Long id, @RequestBody Dienst dienst)
        throws URISyntaxException {
        log.debug("REST request to update Dienst : {}, {}", id, dienst);
        if (dienst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dienst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dienstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dienst = dienstRepository.save(dienst);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dienst.getId().toString()))
            .body(dienst);
    }

    /**
     * {@code PATCH  /diensts/:id} : Partial updates given fields of an existing dienst, field will ignore if it is null
     *
     * @param id the id of the dienst to save.
     * @param dienst the dienst to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dienst,
     * or with status {@code 400 (Bad Request)} if the dienst is not valid,
     * or with status {@code 404 (Not Found)} if the dienst is not found,
     * or with status {@code 500 (Internal Server Error)} if the dienst couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Dienst> partialUpdateDienst(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Dienst dienst
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dienst partially : {}, {}", id, dienst);
        if (dienst.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dienst.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dienstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Dienst> result = dienstRepository.findById(dienst.getId()).map(dienstRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dienst.getId().toString())
        );
    }

    /**
     * {@code GET  /diensts} : get all the diensts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diensts in body.
     */
    @GetMapping("")
    public List<Dienst> getAllDiensts() {
        log.debug("REST request to get all Diensts");
        return dienstRepository.findAll();
    }

    /**
     * {@code GET  /diensts/:id} : get the "id" dienst.
     *
     * @param id the id of the dienst to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dienst, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Dienst> getDienst(@PathVariable("id") Long id) {
        log.debug("REST request to get Dienst : {}", id);
        Optional<Dienst> dienst = dienstRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dienst);
    }

    /**
     * {@code DELETE  /diensts/:id} : delete the "id" dienst.
     *
     * @param id the id of the dienst to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDienst(@PathVariable("id") Long id) {
        log.debug("REST request to delete Dienst : {}", id);
        dienstRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
