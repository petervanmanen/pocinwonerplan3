package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Cmdbitem;
import nl.ritense.demo.repository.CmdbitemRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Cmdbitem}.
 */
@RestController
@RequestMapping("/api/cmdbitems")
@Transactional
public class CmdbitemResource {

    private final Logger log = LoggerFactory.getLogger(CmdbitemResource.class);

    private static final String ENTITY_NAME = "cmdbitem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CmdbitemRepository cmdbitemRepository;

    public CmdbitemResource(CmdbitemRepository cmdbitemRepository) {
        this.cmdbitemRepository = cmdbitemRepository;
    }

    /**
     * {@code POST  /cmdbitems} : Create a new cmdbitem.
     *
     * @param cmdbitem the cmdbitem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cmdbitem, or with status {@code 400 (Bad Request)} if the cmdbitem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Cmdbitem> createCmdbitem(@RequestBody Cmdbitem cmdbitem) throws URISyntaxException {
        log.debug("REST request to save Cmdbitem : {}", cmdbitem);
        if (cmdbitem.getId() != null) {
            throw new BadRequestAlertException("A new cmdbitem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cmdbitem = cmdbitemRepository.save(cmdbitem);
        return ResponseEntity.created(new URI("/api/cmdbitems/" + cmdbitem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, cmdbitem.getId().toString()))
            .body(cmdbitem);
    }

    /**
     * {@code PUT  /cmdbitems/:id} : Updates an existing cmdbitem.
     *
     * @param id the id of the cmdbitem to save.
     * @param cmdbitem the cmdbitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmdbitem,
     * or with status {@code 400 (Bad Request)} if the cmdbitem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cmdbitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cmdbitem> updateCmdbitem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cmdbitem cmdbitem
    ) throws URISyntaxException {
        log.debug("REST request to update Cmdbitem : {}, {}", id, cmdbitem);
        if (cmdbitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cmdbitem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cmdbitemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cmdbitem = cmdbitemRepository.save(cmdbitem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cmdbitem.getId().toString()))
            .body(cmdbitem);
    }

    /**
     * {@code PATCH  /cmdbitems/:id} : Partial updates given fields of an existing cmdbitem, field will ignore if it is null
     *
     * @param id the id of the cmdbitem to save.
     * @param cmdbitem the cmdbitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cmdbitem,
     * or with status {@code 400 (Bad Request)} if the cmdbitem is not valid,
     * or with status {@code 404 (Not Found)} if the cmdbitem is not found,
     * or with status {@code 500 (Internal Server Error)} if the cmdbitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cmdbitem> partialUpdateCmdbitem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cmdbitem cmdbitem
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cmdbitem partially : {}, {}", id, cmdbitem);
        if (cmdbitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cmdbitem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cmdbitemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cmdbitem> result = cmdbitemRepository
            .findById(cmdbitem.getId())
            .map(existingCmdbitem -> {
                if (cmdbitem.getBeschrijving() != null) {
                    existingCmdbitem.setBeschrijving(cmdbitem.getBeschrijving());
                }
                if (cmdbitem.getNaam() != null) {
                    existingCmdbitem.setNaam(cmdbitem.getNaam());
                }

                return existingCmdbitem;
            })
            .map(cmdbitemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cmdbitem.getId().toString())
        );
    }

    /**
     * {@code GET  /cmdbitems} : get all the cmdbitems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cmdbitems in body.
     */
    @GetMapping("")
    public List<Cmdbitem> getAllCmdbitems() {
        log.debug("REST request to get all Cmdbitems");
        return cmdbitemRepository.findAll();
    }

    /**
     * {@code GET  /cmdbitems/:id} : get the "id" cmdbitem.
     *
     * @param id the id of the cmdbitem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cmdbitem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cmdbitem> getCmdbitem(@PathVariable("id") Long id) {
        log.debug("REST request to get Cmdbitem : {}", id);
        Optional<Cmdbitem> cmdbitem = cmdbitemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cmdbitem);
    }

    /**
     * {@code DELETE  /cmdbitems/:id} : delete the "id" cmdbitem.
     *
     * @param id the id of the cmdbitem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCmdbitem(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cmdbitem : {}", id);
        cmdbitemRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
