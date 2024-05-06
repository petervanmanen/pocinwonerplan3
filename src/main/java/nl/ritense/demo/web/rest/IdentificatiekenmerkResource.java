package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Identificatiekenmerk;
import nl.ritense.demo.repository.IdentificatiekenmerkRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Identificatiekenmerk}.
 */
@RestController
@RequestMapping("/api/identificatiekenmerks")
@Transactional
public class IdentificatiekenmerkResource {

    private final Logger log = LoggerFactory.getLogger(IdentificatiekenmerkResource.class);

    private static final String ENTITY_NAME = "identificatiekenmerk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdentificatiekenmerkRepository identificatiekenmerkRepository;

    public IdentificatiekenmerkResource(IdentificatiekenmerkRepository identificatiekenmerkRepository) {
        this.identificatiekenmerkRepository = identificatiekenmerkRepository;
    }

    /**
     * {@code POST  /identificatiekenmerks} : Create a new identificatiekenmerk.
     *
     * @param identificatiekenmerk the identificatiekenmerk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new identificatiekenmerk, or with status {@code 400 (Bad Request)} if the identificatiekenmerk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Identificatiekenmerk> createIdentificatiekenmerk(@Valid @RequestBody Identificatiekenmerk identificatiekenmerk)
        throws URISyntaxException {
        log.debug("REST request to save Identificatiekenmerk : {}", identificatiekenmerk);
        if (identificatiekenmerk.getId() != null) {
            throw new BadRequestAlertException("A new identificatiekenmerk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        identificatiekenmerk = identificatiekenmerkRepository.save(identificatiekenmerk);
        return ResponseEntity.created(new URI("/api/identificatiekenmerks/" + identificatiekenmerk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, identificatiekenmerk.getId().toString()))
            .body(identificatiekenmerk);
    }

    /**
     * {@code PUT  /identificatiekenmerks/:id} : Updates an existing identificatiekenmerk.
     *
     * @param id the id of the identificatiekenmerk to save.
     * @param identificatiekenmerk the identificatiekenmerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated identificatiekenmerk,
     * or with status {@code 400 (Bad Request)} if the identificatiekenmerk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the identificatiekenmerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Identificatiekenmerk> updateIdentificatiekenmerk(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Identificatiekenmerk identificatiekenmerk
    ) throws URISyntaxException {
        log.debug("REST request to update Identificatiekenmerk : {}, {}", id, identificatiekenmerk);
        if (identificatiekenmerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, identificatiekenmerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!identificatiekenmerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        identificatiekenmerk = identificatiekenmerkRepository.save(identificatiekenmerk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, identificatiekenmerk.getId().toString()))
            .body(identificatiekenmerk);
    }

    /**
     * {@code PATCH  /identificatiekenmerks/:id} : Partial updates given fields of an existing identificatiekenmerk, field will ignore if it is null
     *
     * @param id the id of the identificatiekenmerk to save.
     * @param identificatiekenmerk the identificatiekenmerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated identificatiekenmerk,
     * or with status {@code 400 (Bad Request)} if the identificatiekenmerk is not valid,
     * or with status {@code 404 (Not Found)} if the identificatiekenmerk is not found,
     * or with status {@code 500 (Internal Server Error)} if the identificatiekenmerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Identificatiekenmerk> partialUpdateIdentificatiekenmerk(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Identificatiekenmerk identificatiekenmerk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Identificatiekenmerk partially : {}, {}", id, identificatiekenmerk);
        if (identificatiekenmerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, identificatiekenmerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!identificatiekenmerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Identificatiekenmerk> result = identificatiekenmerkRepository
            .findById(identificatiekenmerk.getId())
            .map(existingIdentificatiekenmerk -> {
                if (identificatiekenmerk.getKenmerk() != null) {
                    existingIdentificatiekenmerk.setKenmerk(identificatiekenmerk.getKenmerk());
                }

                return existingIdentificatiekenmerk;
            })
            .map(identificatiekenmerkRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, identificatiekenmerk.getId().toString())
        );
    }

    /**
     * {@code GET  /identificatiekenmerks} : get all the identificatiekenmerks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of identificatiekenmerks in body.
     */
    @GetMapping("")
    public List<Identificatiekenmerk> getAllIdentificatiekenmerks() {
        log.debug("REST request to get all Identificatiekenmerks");
        return identificatiekenmerkRepository.findAll();
    }

    /**
     * {@code GET  /identificatiekenmerks/:id} : get the "id" identificatiekenmerk.
     *
     * @param id the id of the identificatiekenmerk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the identificatiekenmerk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Identificatiekenmerk> getIdentificatiekenmerk(@PathVariable("id") Long id) {
        log.debug("REST request to get Identificatiekenmerk : {}", id);
        Optional<Identificatiekenmerk> identificatiekenmerk = identificatiekenmerkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(identificatiekenmerk);
    }

    /**
     * {@code DELETE  /identificatiekenmerks/:id} : delete the "id" identificatiekenmerk.
     *
     * @param id the id of the identificatiekenmerk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdentificatiekenmerk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Identificatiekenmerk : {}", id);
        identificatiekenmerkRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
