package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bouwstijl;
import nl.ritense.demo.repository.BouwstijlRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bouwstijl}.
 */
@RestController
@RequestMapping("/api/bouwstijls")
@Transactional
public class BouwstijlResource {

    private final Logger log = LoggerFactory.getLogger(BouwstijlResource.class);

    private static final String ENTITY_NAME = "bouwstijl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BouwstijlRepository bouwstijlRepository;

    public BouwstijlResource(BouwstijlRepository bouwstijlRepository) {
        this.bouwstijlRepository = bouwstijlRepository;
    }

    /**
     * {@code POST  /bouwstijls} : Create a new bouwstijl.
     *
     * @param bouwstijl the bouwstijl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bouwstijl, or with status {@code 400 (Bad Request)} if the bouwstijl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bouwstijl> createBouwstijl(@RequestBody Bouwstijl bouwstijl) throws URISyntaxException {
        log.debug("REST request to save Bouwstijl : {}", bouwstijl);
        if (bouwstijl.getId() != null) {
            throw new BadRequestAlertException("A new bouwstijl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bouwstijl = bouwstijlRepository.save(bouwstijl);
        return ResponseEntity.created(new URI("/api/bouwstijls/" + bouwstijl.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bouwstijl.getId().toString()))
            .body(bouwstijl);
    }

    /**
     * {@code PUT  /bouwstijls/:id} : Updates an existing bouwstijl.
     *
     * @param id the id of the bouwstijl to save.
     * @param bouwstijl the bouwstijl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwstijl,
     * or with status {@code 400 (Bad Request)} if the bouwstijl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bouwstijl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bouwstijl> updateBouwstijl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bouwstijl bouwstijl
    ) throws URISyntaxException {
        log.debug("REST request to update Bouwstijl : {}, {}", id, bouwstijl);
        if (bouwstijl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwstijl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwstijlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bouwstijl = bouwstijlRepository.save(bouwstijl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwstijl.getId().toString()))
            .body(bouwstijl);
    }

    /**
     * {@code PATCH  /bouwstijls/:id} : Partial updates given fields of an existing bouwstijl, field will ignore if it is null
     *
     * @param id the id of the bouwstijl to save.
     * @param bouwstijl the bouwstijl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bouwstijl,
     * or with status {@code 400 (Bad Request)} if the bouwstijl is not valid,
     * or with status {@code 404 (Not Found)} if the bouwstijl is not found,
     * or with status {@code 500 (Internal Server Error)} if the bouwstijl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bouwstijl> partialUpdateBouwstijl(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bouwstijl bouwstijl
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bouwstijl partially : {}, {}", id, bouwstijl);
        if (bouwstijl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bouwstijl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bouwstijlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bouwstijl> result = bouwstijlRepository
            .findById(bouwstijl.getId())
            .map(existingBouwstijl -> {
                if (bouwstijl.getHoofdstijl() != null) {
                    existingBouwstijl.setHoofdstijl(bouwstijl.getHoofdstijl());
                }
                if (bouwstijl.getSubstijl() != null) {
                    existingBouwstijl.setSubstijl(bouwstijl.getSubstijl());
                }
                if (bouwstijl.getToelichting() != null) {
                    existingBouwstijl.setToelichting(bouwstijl.getToelichting());
                }
                if (bouwstijl.getZuiverheid() != null) {
                    existingBouwstijl.setZuiverheid(bouwstijl.getZuiverheid());
                }

                return existingBouwstijl;
            })
            .map(bouwstijlRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bouwstijl.getId().toString())
        );
    }

    /**
     * {@code GET  /bouwstijls} : get all the bouwstijls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bouwstijls in body.
     */
    @GetMapping("")
    public List<Bouwstijl> getAllBouwstijls() {
        log.debug("REST request to get all Bouwstijls");
        return bouwstijlRepository.findAll();
    }

    /**
     * {@code GET  /bouwstijls/:id} : get the "id" bouwstijl.
     *
     * @param id the id of the bouwstijl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bouwstijl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bouwstijl> getBouwstijl(@PathVariable("id") Long id) {
        log.debug("REST request to get Bouwstijl : {}", id);
        Optional<Bouwstijl> bouwstijl = bouwstijlRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bouwstijl);
    }

    /**
     * {@code DELETE  /bouwstijls/:id} : delete the "id" bouwstijl.
     *
     * @param id the id of the bouwstijl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBouwstijl(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bouwstijl : {}", id);
        bouwstijlRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
