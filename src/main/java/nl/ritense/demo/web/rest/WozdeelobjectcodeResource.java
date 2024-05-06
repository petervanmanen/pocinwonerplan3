package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Wozdeelobjectcode;
import nl.ritense.demo.repository.WozdeelobjectcodeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Wozdeelobjectcode}.
 */
@RestController
@RequestMapping("/api/wozdeelobjectcodes")
@Transactional
public class WozdeelobjectcodeResource {

    private final Logger log = LoggerFactory.getLogger(WozdeelobjectcodeResource.class);

    private static final String ENTITY_NAME = "wozdeelobjectcode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WozdeelobjectcodeRepository wozdeelobjectcodeRepository;

    public WozdeelobjectcodeResource(WozdeelobjectcodeRepository wozdeelobjectcodeRepository) {
        this.wozdeelobjectcodeRepository = wozdeelobjectcodeRepository;
    }

    /**
     * {@code POST  /wozdeelobjectcodes} : Create a new wozdeelobjectcode.
     *
     * @param wozdeelobjectcode the wozdeelobjectcode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wozdeelobjectcode, or with status {@code 400 (Bad Request)} if the wozdeelobjectcode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Wozdeelobjectcode> createWozdeelobjectcode(@RequestBody Wozdeelobjectcode wozdeelobjectcode)
        throws URISyntaxException {
        log.debug("REST request to save Wozdeelobjectcode : {}", wozdeelobjectcode);
        if (wozdeelobjectcode.getId() != null) {
            throw new BadRequestAlertException("A new wozdeelobjectcode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        wozdeelobjectcode = wozdeelobjectcodeRepository.save(wozdeelobjectcode);
        return ResponseEntity.created(new URI("/api/wozdeelobjectcodes/" + wozdeelobjectcode.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, wozdeelobjectcode.getId().toString()))
            .body(wozdeelobjectcode);
    }

    /**
     * {@code PUT  /wozdeelobjectcodes/:id} : Updates an existing wozdeelobjectcode.
     *
     * @param id the id of the wozdeelobjectcode to save.
     * @param wozdeelobjectcode the wozdeelobjectcode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozdeelobjectcode,
     * or with status {@code 400 (Bad Request)} if the wozdeelobjectcode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wozdeelobjectcode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Wozdeelobjectcode> updateWozdeelobjectcode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozdeelobjectcode wozdeelobjectcode
    ) throws URISyntaxException {
        log.debug("REST request to update Wozdeelobjectcode : {}, {}", id, wozdeelobjectcode);
        if (wozdeelobjectcode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozdeelobjectcode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozdeelobjectcodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        wozdeelobjectcode = wozdeelobjectcodeRepository.save(wozdeelobjectcode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozdeelobjectcode.getId().toString()))
            .body(wozdeelobjectcode);
    }

    /**
     * {@code PATCH  /wozdeelobjectcodes/:id} : Partial updates given fields of an existing wozdeelobjectcode, field will ignore if it is null
     *
     * @param id the id of the wozdeelobjectcode to save.
     * @param wozdeelobjectcode the wozdeelobjectcode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozdeelobjectcode,
     * or with status {@code 400 (Bad Request)} if the wozdeelobjectcode is not valid,
     * or with status {@code 404 (Not Found)} if the wozdeelobjectcode is not found,
     * or with status {@code 500 (Internal Server Error)} if the wozdeelobjectcode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wozdeelobjectcode> partialUpdateWozdeelobjectcode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozdeelobjectcode wozdeelobjectcode
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wozdeelobjectcode partially : {}, {}", id, wozdeelobjectcode);
        if (wozdeelobjectcode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozdeelobjectcode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozdeelobjectcodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wozdeelobjectcode> result = wozdeelobjectcodeRepository
            .findById(wozdeelobjectcode.getId())
            .map(existingWozdeelobjectcode -> {
                if (wozdeelobjectcode.getDatumbegingeldigheiddeelojectcode() != null) {
                    existingWozdeelobjectcode.setDatumbegingeldigheiddeelojectcode(
                        wozdeelobjectcode.getDatumbegingeldigheiddeelojectcode()
                    );
                }
                if (wozdeelobjectcode.getDatumeindegeldigheiddeelobjectcode() != null) {
                    existingWozdeelobjectcode.setDatumeindegeldigheiddeelobjectcode(
                        wozdeelobjectcode.getDatumeindegeldigheiddeelobjectcode()
                    );
                }
                if (wozdeelobjectcode.getDeelobjectcode() != null) {
                    existingWozdeelobjectcode.setDeelobjectcode(wozdeelobjectcode.getDeelobjectcode());
                }
                if (wozdeelobjectcode.getNaamdeelobjectcode() != null) {
                    existingWozdeelobjectcode.setNaamdeelobjectcode(wozdeelobjectcode.getNaamdeelobjectcode());
                }

                return existingWozdeelobjectcode;
            })
            .map(wozdeelobjectcodeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozdeelobjectcode.getId().toString())
        );
    }

    /**
     * {@code GET  /wozdeelobjectcodes} : get all the wozdeelobjectcodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wozdeelobjectcodes in body.
     */
    @GetMapping("")
    public List<Wozdeelobjectcode> getAllWozdeelobjectcodes() {
        log.debug("REST request to get all Wozdeelobjectcodes");
        return wozdeelobjectcodeRepository.findAll();
    }

    /**
     * {@code GET  /wozdeelobjectcodes/:id} : get the "id" wozdeelobjectcode.
     *
     * @param id the id of the wozdeelobjectcode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wozdeelobjectcode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wozdeelobjectcode> getWozdeelobjectcode(@PathVariable("id") Long id) {
        log.debug("REST request to get Wozdeelobjectcode : {}", id);
        Optional<Wozdeelobjectcode> wozdeelobjectcode = wozdeelobjectcodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wozdeelobjectcode);
    }

    /**
     * {@code DELETE  /wozdeelobjectcodes/:id} : delete the "id" wozdeelobjectcode.
     *
     * @param id the id of the wozdeelobjectcode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWozdeelobjectcode(@PathVariable("id") Long id) {
        log.debug("REST request to delete Wozdeelobjectcode : {}", id);
        wozdeelobjectcodeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
