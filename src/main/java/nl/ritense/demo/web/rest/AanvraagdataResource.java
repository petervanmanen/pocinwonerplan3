package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aanvraagdata;
import nl.ritense.demo.repository.AanvraagdataRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanvraagdata}.
 */
@RestController
@RequestMapping("/api/aanvraagdata")
@Transactional
public class AanvraagdataResource {

    private final Logger log = LoggerFactory.getLogger(AanvraagdataResource.class);

    private static final String ENTITY_NAME = "aanvraagdata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanvraagdataRepository aanvraagdataRepository;

    public AanvraagdataResource(AanvraagdataRepository aanvraagdataRepository) {
        this.aanvraagdataRepository = aanvraagdataRepository;
    }

    /**
     * {@code POST  /aanvraagdata} : Create a new aanvraagdata.
     *
     * @param aanvraagdata the aanvraagdata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanvraagdata, or with status {@code 400 (Bad Request)} if the aanvraagdata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanvraagdata> createAanvraagdata(@RequestBody Aanvraagdata aanvraagdata) throws URISyntaxException {
        log.debug("REST request to save Aanvraagdata : {}", aanvraagdata);
        if (aanvraagdata.getId() != null) {
            throw new BadRequestAlertException("A new aanvraagdata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanvraagdata = aanvraagdataRepository.save(aanvraagdata);
        return ResponseEntity.created(new URI("/api/aanvraagdata/" + aanvraagdata.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanvraagdata.getId().toString()))
            .body(aanvraagdata);
    }

    /**
     * {@code PUT  /aanvraagdata/:id} : Updates an existing aanvraagdata.
     *
     * @param id the id of the aanvraagdata to save.
     * @param aanvraagdata the aanvraagdata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraagdata,
     * or with status {@code 400 (Bad Request)} if the aanvraagdata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aanvraagdata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aanvraagdata> updateAanvraagdata(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraagdata aanvraagdata
    ) throws URISyntaxException {
        log.debug("REST request to update Aanvraagdata : {}, {}", id, aanvraagdata);
        if (aanvraagdata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraagdata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraagdataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aanvraagdata = aanvraagdataRepository.save(aanvraagdata);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraagdata.getId().toString()))
            .body(aanvraagdata);
    }

    /**
     * {@code PATCH  /aanvraagdata/:id} : Partial updates given fields of an existing aanvraagdata, field will ignore if it is null
     *
     * @param id the id of the aanvraagdata to save.
     * @param aanvraagdata the aanvraagdata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraagdata,
     * or with status {@code 400 (Bad Request)} if the aanvraagdata is not valid,
     * or with status {@code 404 (Not Found)} if the aanvraagdata is not found,
     * or with status {@code 500 (Internal Server Error)} if the aanvraagdata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aanvraagdata> partialUpdateAanvraagdata(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraagdata aanvraagdata
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aanvraagdata partially : {}, {}", id, aanvraagdata);
        if (aanvraagdata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraagdata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraagdataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aanvraagdata> result = aanvraagdataRepository
            .findById(aanvraagdata.getId())
            .map(existingAanvraagdata -> {
                if (aanvraagdata.getData() != null) {
                    existingAanvraagdata.setData(aanvraagdata.getData());
                }
                if (aanvraagdata.getVeld() != null) {
                    existingAanvraagdata.setVeld(aanvraagdata.getVeld());
                }

                return existingAanvraagdata;
            })
            .map(aanvraagdataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraagdata.getId().toString())
        );
    }

    /**
     * {@code GET  /aanvraagdata} : get all the aanvraagdata.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanvraagdata in body.
     */
    @GetMapping("")
    public List<Aanvraagdata> getAllAanvraagdata() {
        log.debug("REST request to get all Aanvraagdata");
        return aanvraagdataRepository.findAll();
    }

    /**
     * {@code GET  /aanvraagdata/:id} : get the "id" aanvraagdata.
     *
     * @param id the id of the aanvraagdata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanvraagdata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanvraagdata> getAanvraagdata(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanvraagdata : {}", id);
        Optional<Aanvraagdata> aanvraagdata = aanvraagdataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanvraagdata);
    }

    /**
     * {@code DELETE  /aanvraagdata/:id} : delete the "id" aanvraagdata.
     *
     * @param id the id of the aanvraagdata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanvraagdata(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanvraagdata : {}", id);
        aanvraagdataRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
