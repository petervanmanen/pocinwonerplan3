package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Onderwerp;
import nl.ritense.demo.repository.OnderwerpRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onderwerp}.
 */
@RestController
@RequestMapping("/api/onderwerps")
@Transactional
public class OnderwerpResource {

    private final Logger log = LoggerFactory.getLogger(OnderwerpResource.class);

    private static final String ENTITY_NAME = "onderwerp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnderwerpRepository onderwerpRepository;

    public OnderwerpResource(OnderwerpRepository onderwerpRepository) {
        this.onderwerpRepository = onderwerpRepository;
    }

    /**
     * {@code POST  /onderwerps} : Create a new onderwerp.
     *
     * @param onderwerp the onderwerp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onderwerp, or with status {@code 400 (Bad Request)} if the onderwerp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onderwerp> createOnderwerp(@Valid @RequestBody Onderwerp onderwerp) throws URISyntaxException {
        log.debug("REST request to save Onderwerp : {}", onderwerp);
        if (onderwerp.getId() != null) {
            throw new BadRequestAlertException("A new onderwerp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onderwerp = onderwerpRepository.save(onderwerp);
        return ResponseEntity.created(new URI("/api/onderwerps/" + onderwerp.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onderwerp.getId().toString()))
            .body(onderwerp);
    }

    /**
     * {@code PUT  /onderwerps/:id} : Updates an existing onderwerp.
     *
     * @param id the id of the onderwerp to save.
     * @param onderwerp the onderwerp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderwerp,
     * or with status {@code 400 (Bad Request)} if the onderwerp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the onderwerp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Onderwerp> updateOnderwerp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Onderwerp onderwerp
    ) throws URISyntaxException {
        log.debug("REST request to update Onderwerp : {}, {}", id, onderwerp);
        if (onderwerp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderwerp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderwerpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        onderwerp = onderwerpRepository.save(onderwerp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onderwerp.getId().toString()))
            .body(onderwerp);
    }

    /**
     * {@code PATCH  /onderwerps/:id} : Partial updates given fields of an existing onderwerp, field will ignore if it is null
     *
     * @param id the id of the onderwerp to save.
     * @param onderwerp the onderwerp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderwerp,
     * or with status {@code 400 (Bad Request)} if the onderwerp is not valid,
     * or with status {@code 404 (Not Found)} if the onderwerp is not found,
     * or with status {@code 500 (Internal Server Error)} if the onderwerp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Onderwerp> partialUpdateOnderwerp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Onderwerp onderwerp
    ) throws URISyntaxException {
        log.debug("REST request to partial update Onderwerp partially : {}, {}", id, onderwerp);
        if (onderwerp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderwerp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderwerpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Onderwerp> result = onderwerpRepository.findById(onderwerp.getId()).map(onderwerpRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onderwerp.getId().toString())
        );
    }

    /**
     * {@code GET  /onderwerps} : get all the onderwerps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onderwerps in body.
     */
    @GetMapping("")
    public List<Onderwerp> getAllOnderwerps() {
        log.debug("REST request to get all Onderwerps");
        return onderwerpRepository.findAll();
    }

    /**
     * {@code GET  /onderwerps/:id} : get the "id" onderwerp.
     *
     * @param id the id of the onderwerp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onderwerp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onderwerp> getOnderwerp(@PathVariable("id") Long id) {
        log.debug("REST request to get Onderwerp : {}", id);
        Optional<Onderwerp> onderwerp = onderwerpRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onderwerp);
    }

    /**
     * {@code DELETE  /onderwerps/:id} : delete the "id" onderwerp.
     *
     * @param id the id of the onderwerp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderwerp(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onderwerp : {}", id);
        onderwerpRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
