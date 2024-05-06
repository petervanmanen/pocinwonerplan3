package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Onderwijsloopbaan;
import nl.ritense.demo.repository.OnderwijsloopbaanRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onderwijsloopbaan}.
 */
@RestController
@RequestMapping("/api/onderwijsloopbaans")
@Transactional
public class OnderwijsloopbaanResource {

    private final Logger log = LoggerFactory.getLogger(OnderwijsloopbaanResource.class);

    private static final String ENTITY_NAME = "onderwijsloopbaan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnderwijsloopbaanRepository onderwijsloopbaanRepository;

    public OnderwijsloopbaanResource(OnderwijsloopbaanRepository onderwijsloopbaanRepository) {
        this.onderwijsloopbaanRepository = onderwijsloopbaanRepository;
    }

    /**
     * {@code POST  /onderwijsloopbaans} : Create a new onderwijsloopbaan.
     *
     * @param onderwijsloopbaan the onderwijsloopbaan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onderwijsloopbaan, or with status {@code 400 (Bad Request)} if the onderwijsloopbaan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onderwijsloopbaan> createOnderwijsloopbaan(@Valid @RequestBody Onderwijsloopbaan onderwijsloopbaan)
        throws URISyntaxException {
        log.debug("REST request to save Onderwijsloopbaan : {}", onderwijsloopbaan);
        if (onderwijsloopbaan.getId() != null) {
            throw new BadRequestAlertException("A new onderwijsloopbaan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onderwijsloopbaan = onderwijsloopbaanRepository.save(onderwijsloopbaan);
        return ResponseEntity.created(new URI("/api/onderwijsloopbaans/" + onderwijsloopbaan.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onderwijsloopbaan.getId().toString()))
            .body(onderwijsloopbaan);
    }

    /**
     * {@code PUT  /onderwijsloopbaans/:id} : Updates an existing onderwijsloopbaan.
     *
     * @param id the id of the onderwijsloopbaan to save.
     * @param onderwijsloopbaan the onderwijsloopbaan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderwijsloopbaan,
     * or with status {@code 400 (Bad Request)} if the onderwijsloopbaan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the onderwijsloopbaan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Onderwijsloopbaan> updateOnderwijsloopbaan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Onderwijsloopbaan onderwijsloopbaan
    ) throws URISyntaxException {
        log.debug("REST request to update Onderwijsloopbaan : {}, {}", id, onderwijsloopbaan);
        if (onderwijsloopbaan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderwijsloopbaan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderwijsloopbaanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        onderwijsloopbaan = onderwijsloopbaanRepository.save(onderwijsloopbaan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onderwijsloopbaan.getId().toString()))
            .body(onderwijsloopbaan);
    }

    /**
     * {@code PATCH  /onderwijsloopbaans/:id} : Partial updates given fields of an existing onderwijsloopbaan, field will ignore if it is null
     *
     * @param id the id of the onderwijsloopbaan to save.
     * @param onderwijsloopbaan the onderwijsloopbaan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderwijsloopbaan,
     * or with status {@code 400 (Bad Request)} if the onderwijsloopbaan is not valid,
     * or with status {@code 404 (Not Found)} if the onderwijsloopbaan is not found,
     * or with status {@code 500 (Internal Server Error)} if the onderwijsloopbaan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Onderwijsloopbaan> partialUpdateOnderwijsloopbaan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Onderwijsloopbaan onderwijsloopbaan
    ) throws URISyntaxException {
        log.debug("REST request to partial update Onderwijsloopbaan partially : {}, {}", id, onderwijsloopbaan);
        if (onderwijsloopbaan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderwijsloopbaan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderwijsloopbaanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Onderwijsloopbaan> result = onderwijsloopbaanRepository
            .findById(onderwijsloopbaan.getId())
            .map(onderwijsloopbaanRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onderwijsloopbaan.getId().toString())
        );
    }

    /**
     * {@code GET  /onderwijsloopbaans} : get all the onderwijsloopbaans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onderwijsloopbaans in body.
     */
    @GetMapping("")
    public List<Onderwijsloopbaan> getAllOnderwijsloopbaans() {
        log.debug("REST request to get all Onderwijsloopbaans");
        return onderwijsloopbaanRepository.findAll();
    }

    /**
     * {@code GET  /onderwijsloopbaans/:id} : get the "id" onderwijsloopbaan.
     *
     * @param id the id of the onderwijsloopbaan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onderwijsloopbaan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onderwijsloopbaan> getOnderwijsloopbaan(@PathVariable("id") Long id) {
        log.debug("REST request to get Onderwijsloopbaan : {}", id);
        Optional<Onderwijsloopbaan> onderwijsloopbaan = onderwijsloopbaanRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onderwijsloopbaan);
    }

    /**
     * {@code DELETE  /onderwijsloopbaans/:id} : delete the "id" onderwijsloopbaan.
     *
     * @param id the id of the onderwijsloopbaan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderwijsloopbaan(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onderwijsloopbaan : {}", id);
        onderwijsloopbaanRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
