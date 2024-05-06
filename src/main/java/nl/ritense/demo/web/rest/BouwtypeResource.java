package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bouwtype;
import nl.ritense.demo.repository.BouwtypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bouwtype}.
 */
@RestController
@RequestMapping("/api/bouwtypes")
@Transactional
public class BouwtypeResource {

    private final Logger log = LoggerFactory.getLogger(BouwtypeResource.class);

    private static final String ENTITY_NAME = "bouwtype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BouwtypeRepository bouwtypeRepository;

    public BouwtypeResource(BouwtypeRepository bouwtypeRepository) {
        this.bouwtypeRepository = bouwtypeRepository;
    }

    /**
     * {@code POST  /bouwtypes} : Create a new bouwtype.
     *
     * @param bouwtype the bouwtype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bouwtype, or with status {@code 400 (Bad Request)} if the bouwtype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bouwtype> createBouwtype(@RequestBody Bouwtype bouwtype) throws URISyntaxException {
        log.debug("REST request to save Bouwtype : {}", bouwtype);
        if (bouwtype.getId() != null) {
            throw new BadRequestAlertException("A new bouwtype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bouwtype = bouwtypeRepository.save(bouwtype);
        return ResponseEntity.created(new URI("/api/bouwtypes/" + bouwtype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bouwtype.getId().toString()))
            .body(bouwtype);
    }

    /**
     * {@code PUT  /bouwtypes/:id} : Updates an existing bouwtype.
     *
     * @param id the id of the bouwtype to save.
     * @param bouwtype the bouwtype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwtype,
     * or with status {@code 400 (Bad Request)} if the bouwtype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bouwtype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bouwtype> updateBouwtype(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bouwtype bouwtype
    ) throws URISyntaxException {
        log.debug("REST request to update Bouwtype : {}, {}", id, bouwtype);
        if (bouwtype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwtype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwtypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bouwtype = bouwtypeRepository.save(bouwtype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwtype.getId().toString()))
            .body(bouwtype);
    }

    /**
     * {@code PATCH  /bouwtypes/:id} : Partial updates given fields of an existing bouwtype, field will ignore if it is null
     *
     * @param id the id of the bouwtype to save.
     * @param bouwtype the bouwtype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwtype,
     * or with status {@code 400 (Bad Request)} if the bouwtype is not valid,
     * or with status {@code 404 (Not Found)} if the bouwtype is not found,
     * or with status {@code 500 (Internal Server Error)} if the bouwtype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bouwtype> partialUpdateBouwtype(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bouwtype bouwtype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bouwtype partially : {}, {}", id, bouwtype);
        if (bouwtype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwtype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwtypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bouwtype> result = bouwtypeRepository
            .findById(bouwtype.getId())
            .map(existingBouwtype -> {
                if (bouwtype.getHoofdcategorie() != null) {
                    existingBouwtype.setHoofdcategorie(bouwtype.getHoofdcategorie());
                }
                if (bouwtype.getSubcategorie() != null) {
                    existingBouwtype.setSubcategorie(bouwtype.getSubcategorie());
                }
                if (bouwtype.getToelichting() != null) {
                    existingBouwtype.setToelichting(bouwtype.getToelichting());
                }

                return existingBouwtype;
            })
            .map(bouwtypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwtype.getId().toString())
        );
    }

    /**
     * {@code GET  /bouwtypes} : get all the bouwtypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bouwtypes in body.
     */
    @GetMapping("")
    public List<Bouwtype> getAllBouwtypes() {
        log.debug("REST request to get all Bouwtypes");
        return bouwtypeRepository.findAll();
    }

    /**
     * {@code GET  /bouwtypes/:id} : get the "id" bouwtype.
     *
     * @param id the id of the bouwtype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bouwtype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bouwtype> getBouwtype(@PathVariable("id") Long id) {
        log.debug("REST request to get Bouwtype : {}", id);
        Optional<Bouwtype> bouwtype = bouwtypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bouwtype);
    }

    /**
     * {@code DELETE  /bouwtypes/:id} : delete the "id" bouwtype.
     *
     * @param id the id of the bouwtype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBouwtype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bouwtype : {}", id);
        bouwtypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
