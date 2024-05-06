package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Loopbaanstap;
import nl.ritense.demo.repository.LoopbaanstapRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Loopbaanstap}.
 */
@RestController
@RequestMapping("/api/loopbaanstaps")
@Transactional
public class LoopbaanstapResource {

    private final Logger log = LoggerFactory.getLogger(LoopbaanstapResource.class);

    private static final String ENTITY_NAME = "loopbaanstap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoopbaanstapRepository loopbaanstapRepository;

    public LoopbaanstapResource(LoopbaanstapRepository loopbaanstapRepository) {
        this.loopbaanstapRepository = loopbaanstapRepository;
    }

    /**
     * {@code POST  /loopbaanstaps} : Create a new loopbaanstap.
     *
     * @param loopbaanstap the loopbaanstap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loopbaanstap, or with status {@code 400 (Bad Request)} if the loopbaanstap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Loopbaanstap> createLoopbaanstap(@RequestBody Loopbaanstap loopbaanstap) throws URISyntaxException {
        log.debug("REST request to save Loopbaanstap : {}", loopbaanstap);
        if (loopbaanstap.getId() != null) {
            throw new BadRequestAlertException("A new loopbaanstap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        loopbaanstap = loopbaanstapRepository.save(loopbaanstap);
        return ResponseEntity.created(new URI("/api/loopbaanstaps/" + loopbaanstap.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, loopbaanstap.getId().toString()))
            .body(loopbaanstap);
    }

    /**
     * {@code PUT  /loopbaanstaps/:id} : Updates an existing loopbaanstap.
     *
     * @param id the id of the loopbaanstap to save.
     * @param loopbaanstap the loopbaanstap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loopbaanstap,
     * or with status {@code 400 (Bad Request)} if the loopbaanstap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loopbaanstap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Loopbaanstap> updateLoopbaanstap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Loopbaanstap loopbaanstap
    ) throws URISyntaxException {
        log.debug("REST request to update Loopbaanstap : {}, {}", id, loopbaanstap);
        if (loopbaanstap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loopbaanstap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loopbaanstapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        loopbaanstap = loopbaanstapRepository.save(loopbaanstap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, loopbaanstap.getId().toString()))
            .body(loopbaanstap);
    }

    /**
     * {@code PATCH  /loopbaanstaps/:id} : Partial updates given fields of an existing loopbaanstap, field will ignore if it is null
     *
     * @param id the id of the loopbaanstap to save.
     * @param loopbaanstap the loopbaanstap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loopbaanstap,
     * or with status {@code 400 (Bad Request)} if the loopbaanstap is not valid,
     * or with status {@code 404 (Not Found)} if the loopbaanstap is not found,
     * or with status {@code 500 (Internal Server Error)} if the loopbaanstap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Loopbaanstap> partialUpdateLoopbaanstap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Loopbaanstap loopbaanstap
    ) throws URISyntaxException {
        log.debug("REST request to partial update Loopbaanstap partially : {}, {}", id, loopbaanstap);
        if (loopbaanstap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loopbaanstap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loopbaanstapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Loopbaanstap> result = loopbaanstapRepository
            .findById(loopbaanstap.getId())
            .map(existingLoopbaanstap -> {
                if (loopbaanstap.getKlas() != null) {
                    existingLoopbaanstap.setKlas(loopbaanstap.getKlas());
                }
                if (loopbaanstap.getOnderwijstype() != null) {
                    existingLoopbaanstap.setOnderwijstype(loopbaanstap.getOnderwijstype());
                }
                if (loopbaanstap.getSchooljaar() != null) {
                    existingLoopbaanstap.setSchooljaar(loopbaanstap.getSchooljaar());
                }

                return existingLoopbaanstap;
            })
            .map(loopbaanstapRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, loopbaanstap.getId().toString())
        );
    }

    /**
     * {@code GET  /loopbaanstaps} : get all the loopbaanstaps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loopbaanstaps in body.
     */
    @GetMapping("")
    public List<Loopbaanstap> getAllLoopbaanstaps() {
        log.debug("REST request to get all Loopbaanstaps");
        return loopbaanstapRepository.findAll();
    }

    /**
     * {@code GET  /loopbaanstaps/:id} : get the "id" loopbaanstap.
     *
     * @param id the id of the loopbaanstap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loopbaanstap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Loopbaanstap> getLoopbaanstap(@PathVariable("id") Long id) {
        log.debug("REST request to get Loopbaanstap : {}", id);
        Optional<Loopbaanstap> loopbaanstap = loopbaanstapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(loopbaanstap);
    }

    /**
     * {@code DELETE  /loopbaanstaps/:id} : delete the "id" loopbaanstap.
     *
     * @param id the id of the loopbaanstap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoopbaanstap(@PathVariable("id") Long id) {
        log.debug("REST request to delete Loopbaanstap : {}", id);
        loopbaanstapRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
