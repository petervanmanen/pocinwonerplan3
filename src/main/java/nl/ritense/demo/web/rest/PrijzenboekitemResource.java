package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Prijzenboekitem;
import nl.ritense.demo.repository.PrijzenboekitemRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Prijzenboekitem}.
 */
@RestController
@RequestMapping("/api/prijzenboekitems")
@Transactional
public class PrijzenboekitemResource {

    private final Logger log = LoggerFactory.getLogger(PrijzenboekitemResource.class);

    private static final String ENTITY_NAME = "prijzenboekitem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrijzenboekitemRepository prijzenboekitemRepository;

    public PrijzenboekitemResource(PrijzenboekitemRepository prijzenboekitemRepository) {
        this.prijzenboekitemRepository = prijzenboekitemRepository;
    }

    /**
     * {@code POST  /prijzenboekitems} : Create a new prijzenboekitem.
     *
     * @param prijzenboekitem the prijzenboekitem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prijzenboekitem, or with status {@code 400 (Bad Request)} if the prijzenboekitem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Prijzenboekitem> createPrijzenboekitem(@RequestBody Prijzenboekitem prijzenboekitem) throws URISyntaxException {
        log.debug("REST request to save Prijzenboekitem : {}", prijzenboekitem);
        if (prijzenboekitem.getId() != null) {
            throw new BadRequestAlertException("A new prijzenboekitem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prijzenboekitem = prijzenboekitemRepository.save(prijzenboekitem);
        return ResponseEntity.created(new URI("/api/prijzenboekitems/" + prijzenboekitem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, prijzenboekitem.getId().toString()))
            .body(prijzenboekitem);
    }

    /**
     * {@code PUT  /prijzenboekitems/:id} : Updates an existing prijzenboekitem.
     *
     * @param id the id of the prijzenboekitem to save.
     * @param prijzenboekitem the prijzenboekitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijzenboekitem,
     * or with status {@code 400 (Bad Request)} if the prijzenboekitem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prijzenboekitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Prijzenboekitem> updatePrijzenboekitem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Prijzenboekitem prijzenboekitem
    ) throws URISyntaxException {
        log.debug("REST request to update Prijzenboekitem : {}, {}", id, prijzenboekitem);
        if (prijzenboekitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijzenboekitem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijzenboekitemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        prijzenboekitem = prijzenboekitemRepository.save(prijzenboekitem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijzenboekitem.getId().toString()))
            .body(prijzenboekitem);
    }

    /**
     * {@code PATCH  /prijzenboekitems/:id} : Partial updates given fields of an existing prijzenboekitem, field will ignore if it is null
     *
     * @param id the id of the prijzenboekitem to save.
     * @param prijzenboekitem the prijzenboekitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijzenboekitem,
     * or with status {@code 400 (Bad Request)} if the prijzenboekitem is not valid,
     * or with status {@code 404 (Not Found)} if the prijzenboekitem is not found,
     * or with status {@code 500 (Internal Server Error)} if the prijzenboekitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Prijzenboekitem> partialUpdatePrijzenboekitem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Prijzenboekitem prijzenboekitem
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prijzenboekitem partially : {}, {}", id, prijzenboekitem);
        if (prijzenboekitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijzenboekitem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijzenboekitemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Prijzenboekitem> result = prijzenboekitemRepository
            .findById(prijzenboekitem.getId())
            .map(existingPrijzenboekitem -> {
                if (prijzenboekitem.getDatumeindegeldigheid() != null) {
                    existingPrijzenboekitem.setDatumeindegeldigheid(prijzenboekitem.getDatumeindegeldigheid());
                }
                if (prijzenboekitem.getDatumstart() != null) {
                    existingPrijzenboekitem.setDatumstart(prijzenboekitem.getDatumstart());
                }
                if (prijzenboekitem.getPrijs() != null) {
                    existingPrijzenboekitem.setPrijs(prijzenboekitem.getPrijs());
                }
                if (prijzenboekitem.getVerrichting() != null) {
                    existingPrijzenboekitem.setVerrichting(prijzenboekitem.getVerrichting());
                }

                return existingPrijzenboekitem;
            })
            .map(prijzenboekitemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijzenboekitem.getId().toString())
        );
    }

    /**
     * {@code GET  /prijzenboekitems} : get all the prijzenboekitems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prijzenboekitems in body.
     */
    @GetMapping("")
    public List<Prijzenboekitem> getAllPrijzenboekitems() {
        log.debug("REST request to get all Prijzenboekitems");
        return prijzenboekitemRepository.findAll();
    }

    /**
     * {@code GET  /prijzenboekitems/:id} : get the "id" prijzenboekitem.
     *
     * @param id the id of the prijzenboekitem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prijzenboekitem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prijzenboekitem> getPrijzenboekitem(@PathVariable("id") Long id) {
        log.debug("REST request to get Prijzenboekitem : {}", id);
        Optional<Prijzenboekitem> prijzenboekitem = prijzenboekitemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prijzenboekitem);
    }

    /**
     * {@code DELETE  /prijzenboekitems/:id} : delete the "id" prijzenboekitem.
     *
     * @param id the id of the prijzenboekitem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrijzenboekitem(@PathVariable("id") Long id) {
        log.debug("REST request to delete Prijzenboekitem : {}", id);
        prijzenboekitemRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
