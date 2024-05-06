package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Inonderzoek;
import nl.ritense.demo.repository.InonderzoekRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inonderzoek}.
 */
@RestController
@RequestMapping("/api/inonderzoeks")
@Transactional
public class InonderzoekResource {

    private final Logger log = LoggerFactory.getLogger(InonderzoekResource.class);

    private static final String ENTITY_NAME = "inonderzoek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InonderzoekRepository inonderzoekRepository;

    public InonderzoekResource(InonderzoekRepository inonderzoekRepository) {
        this.inonderzoekRepository = inonderzoekRepository;
    }

    /**
     * {@code POST  /inonderzoeks} : Create a new inonderzoek.
     *
     * @param inonderzoek the inonderzoek to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inonderzoek, or with status {@code 400 (Bad Request)} if the inonderzoek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inonderzoek> createInonderzoek(@RequestBody Inonderzoek inonderzoek) throws URISyntaxException {
        log.debug("REST request to save Inonderzoek : {}", inonderzoek);
        if (inonderzoek.getId() != null) {
            throw new BadRequestAlertException("A new inonderzoek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inonderzoek = inonderzoekRepository.save(inonderzoek);
        return ResponseEntity.created(new URI("/api/inonderzoeks/" + inonderzoek.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inonderzoek.getId().toString()))
            .body(inonderzoek);
    }

    /**
     * {@code PUT  /inonderzoeks/:id} : Updates an existing inonderzoek.
     *
     * @param id the id of the inonderzoek to save.
     * @param inonderzoek the inonderzoek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inonderzoek,
     * or with status {@code 400 (Bad Request)} if the inonderzoek is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inonderzoek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inonderzoek> updateInonderzoek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inonderzoek inonderzoek
    ) throws URISyntaxException {
        log.debug("REST request to update Inonderzoek : {}, {}", id, inonderzoek);
        if (inonderzoek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inonderzoek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inonderzoekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inonderzoek = inonderzoekRepository.save(inonderzoek);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inonderzoek.getId().toString()))
            .body(inonderzoek);
    }

    /**
     * {@code PATCH  /inonderzoeks/:id} : Partial updates given fields of an existing inonderzoek, field will ignore if it is null
     *
     * @param id the id of the inonderzoek to save.
     * @param inonderzoek the inonderzoek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inonderzoek,
     * or with status {@code 400 (Bad Request)} if the inonderzoek is not valid,
     * or with status {@code 404 (Not Found)} if the inonderzoek is not found,
     * or with status {@code 500 (Internal Server Error)} if the inonderzoek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inonderzoek> partialUpdateInonderzoek(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inonderzoek inonderzoek
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inonderzoek partially : {}, {}", id, inonderzoek);
        if (inonderzoek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inonderzoek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inonderzoekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inonderzoek> result = inonderzoekRepository
            .findById(inonderzoek.getId())
            .map(existingInonderzoek -> {
                if (inonderzoek.getAanduidinggegevensinonderzoek() != null) {
                    existingInonderzoek.setAanduidinggegevensinonderzoek(inonderzoek.getAanduidinggegevensinonderzoek());
                }

                return existingInonderzoek;
            })
            .map(inonderzoekRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inonderzoek.getId().toString())
        );
    }

    /**
     * {@code GET  /inonderzoeks} : get all the inonderzoeks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inonderzoeks in body.
     */
    @GetMapping("")
    public List<Inonderzoek> getAllInonderzoeks() {
        log.debug("REST request to get all Inonderzoeks");
        return inonderzoekRepository.findAll();
    }

    /**
     * {@code GET  /inonderzoeks/:id} : get the "id" inonderzoek.
     *
     * @param id the id of the inonderzoek to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inonderzoek, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inonderzoek> getInonderzoek(@PathVariable("id") Long id) {
        log.debug("REST request to get Inonderzoek : {}", id);
        Optional<Inonderzoek> inonderzoek = inonderzoekRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inonderzoek);
    }

    /**
     * {@code DELETE  /inonderzoeks/:id} : delete the "id" inonderzoek.
     *
     * @param id the id of the inonderzoek to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInonderzoek(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inonderzoek : {}", id);
        inonderzoekRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
