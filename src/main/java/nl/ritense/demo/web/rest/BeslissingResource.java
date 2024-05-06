package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beslissing;
import nl.ritense.demo.repository.BeslissingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beslissing}.
 */
@RestController
@RequestMapping("/api/beslissings")
@Transactional
public class BeslissingResource {

    private final Logger log = LoggerFactory.getLogger(BeslissingResource.class);

    private static final String ENTITY_NAME = "beslissing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeslissingRepository beslissingRepository;

    public BeslissingResource(BeslissingRepository beslissingRepository) {
        this.beslissingRepository = beslissingRepository;
    }

    /**
     * {@code POST  /beslissings} : Create a new beslissing.
     *
     * @param beslissing the beslissing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beslissing, or with status {@code 400 (Bad Request)} if the beslissing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beslissing> createBeslissing(@RequestBody Beslissing beslissing) throws URISyntaxException {
        log.debug("REST request to save Beslissing : {}", beslissing);
        if (beslissing.getId() != null) {
            throw new BadRequestAlertException("A new beslissing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beslissing = beslissingRepository.save(beslissing);
        return ResponseEntity.created(new URI("/api/beslissings/" + beslissing.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beslissing.getId().toString()))
            .body(beslissing);
    }

    /**
     * {@code PUT  /beslissings/:id} : Updates an existing beslissing.
     *
     * @param id the id of the beslissing to save.
     * @param beslissing the beslissing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beslissing,
     * or with status {@code 400 (Bad Request)} if the beslissing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beslissing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beslissing> updateBeslissing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beslissing beslissing
    ) throws URISyntaxException {
        log.debug("REST request to update Beslissing : {}, {}", id, beslissing);
        if (beslissing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beslissing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beslissingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beslissing = beslissingRepository.save(beslissing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beslissing.getId().toString()))
            .body(beslissing);
    }

    /**
     * {@code PATCH  /beslissings/:id} : Partial updates given fields of an existing beslissing, field will ignore if it is null
     *
     * @param id the id of the beslissing to save.
     * @param beslissing the beslissing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beslissing,
     * or with status {@code 400 (Bad Request)} if the beslissing is not valid,
     * or with status {@code 404 (Not Found)} if the beslissing is not found,
     * or with status {@code 500 (Internal Server Error)} if the beslissing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beslissing> partialUpdateBeslissing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beslissing beslissing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beslissing partially : {}, {}", id, beslissing);
        if (beslissing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beslissing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beslissingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beslissing> result = beslissingRepository
            .findById(beslissing.getId())
            .map(existingBeslissing -> {
                if (beslissing.getDatum() != null) {
                    existingBeslissing.setDatum(beslissing.getDatum());
                }
                if (beslissing.getOpmerkingen() != null) {
                    existingBeslissing.setOpmerkingen(beslissing.getOpmerkingen());
                }
                if (beslissing.getReden() != null) {
                    existingBeslissing.setReden(beslissing.getReden());
                }

                return existingBeslissing;
            })
            .map(beslissingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beslissing.getId().toString())
        );
    }

    /**
     * {@code GET  /beslissings} : get all the beslissings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beslissings in body.
     */
    @GetMapping("")
    public List<Beslissing> getAllBeslissings() {
        log.debug("REST request to get all Beslissings");
        return beslissingRepository.findAll();
    }

    /**
     * {@code GET  /beslissings/:id} : get the "id" beslissing.
     *
     * @param id the id of the beslissing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beslissing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beslissing> getBeslissing(@PathVariable("id") Long id) {
        log.debug("REST request to get Beslissing : {}", id);
        Optional<Beslissing> beslissing = beslissingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beslissing);
    }

    /**
     * {@code DELETE  /beslissings/:id} : delete the "id" beslissing.
     *
     * @param id the id of the beslissing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeslissing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beslissing : {}", id);
        beslissingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
