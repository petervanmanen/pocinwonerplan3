package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Haltverwijzing;
import nl.ritense.demo.repository.HaltverwijzingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Haltverwijzing}.
 */
@RestController
@RequestMapping("/api/haltverwijzings")
@Transactional
public class HaltverwijzingResource {

    private final Logger log = LoggerFactory.getLogger(HaltverwijzingResource.class);

    private static final String ENTITY_NAME = "haltverwijzing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HaltverwijzingRepository haltverwijzingRepository;

    public HaltverwijzingResource(HaltverwijzingRepository haltverwijzingRepository) {
        this.haltverwijzingRepository = haltverwijzingRepository;
    }

    /**
     * {@code POST  /haltverwijzings} : Create a new haltverwijzing.
     *
     * @param haltverwijzing the haltverwijzing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new haltverwijzing, or with status {@code 400 (Bad Request)} if the haltverwijzing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Haltverwijzing> createHaltverwijzing(@RequestBody Haltverwijzing haltverwijzing) throws URISyntaxException {
        log.debug("REST request to save Haltverwijzing : {}", haltverwijzing);
        if (haltverwijzing.getId() != null) {
            throw new BadRequestAlertException("A new haltverwijzing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        haltverwijzing = haltverwijzingRepository.save(haltverwijzing);
        return ResponseEntity.created(new URI("/api/haltverwijzings/" + haltverwijzing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, haltverwijzing.getId().toString()))
            .body(haltverwijzing);
    }

    /**
     * {@code PUT  /haltverwijzings/:id} : Updates an existing haltverwijzing.
     *
     * @param id the id of the haltverwijzing to save.
     * @param haltverwijzing the haltverwijzing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated haltverwijzing,
     * or with status {@code 400 (Bad Request)} if the haltverwijzing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the haltverwijzing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Haltverwijzing> updateHaltverwijzing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Haltverwijzing haltverwijzing
    ) throws URISyntaxException {
        log.debug("REST request to update Haltverwijzing : {}, {}", id, haltverwijzing);
        if (haltverwijzing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, haltverwijzing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!haltverwijzingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        haltverwijzing = haltverwijzingRepository.save(haltverwijzing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, haltverwijzing.getId().toString()))
            .body(haltverwijzing);
    }

    /**
     * {@code PATCH  /haltverwijzings/:id} : Partial updates given fields of an existing haltverwijzing, field will ignore if it is null
     *
     * @param id the id of the haltverwijzing to save.
     * @param haltverwijzing the haltverwijzing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated haltverwijzing,
     * or with status {@code 400 (Bad Request)} if the haltverwijzing is not valid,
     * or with status {@code 404 (Not Found)} if the haltverwijzing is not found,
     * or with status {@code 500 (Internal Server Error)} if the haltverwijzing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Haltverwijzing> partialUpdateHaltverwijzing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Haltverwijzing haltverwijzing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Haltverwijzing partially : {}, {}", id, haltverwijzing);
        if (haltverwijzing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, haltverwijzing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!haltverwijzingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Haltverwijzing> result = haltverwijzingRepository
            .findById(haltverwijzing.getId())
            .map(existingHaltverwijzing -> {
                if (haltverwijzing.getAfdoening() != null) {
                    existingHaltverwijzing.setAfdoening(haltverwijzing.getAfdoening());
                }
                if (haltverwijzing.getDatummutatie() != null) {
                    existingHaltverwijzing.setDatummutatie(haltverwijzing.getDatummutatie());
                }
                if (haltverwijzing.getDatumretour() != null) {
                    existingHaltverwijzing.setDatumretour(haltverwijzing.getDatumretour());
                }
                if (haltverwijzing.getMemo() != null) {
                    existingHaltverwijzing.setMemo(haltverwijzing.getMemo());
                }

                return existingHaltverwijzing;
            })
            .map(haltverwijzingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, haltverwijzing.getId().toString())
        );
    }

    /**
     * {@code GET  /haltverwijzings} : get all the haltverwijzings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of haltverwijzings in body.
     */
    @GetMapping("")
    public List<Haltverwijzing> getAllHaltverwijzings() {
        log.debug("REST request to get all Haltverwijzings");
        return haltverwijzingRepository.findAll();
    }

    /**
     * {@code GET  /haltverwijzings/:id} : get the "id" haltverwijzing.
     *
     * @param id the id of the haltverwijzing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the haltverwijzing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Haltverwijzing> getHaltverwijzing(@PathVariable("id") Long id) {
        log.debug("REST request to get Haltverwijzing : {}", id);
        Optional<Haltverwijzing> haltverwijzing = haltverwijzingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(haltverwijzing);
    }

    /**
     * {@code DELETE  /haltverwijzings/:id} : delete the "id" haltverwijzing.
     *
     * @param id the id of the haltverwijzing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHaltverwijzing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Haltverwijzing : {}", id);
        haltverwijzingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
