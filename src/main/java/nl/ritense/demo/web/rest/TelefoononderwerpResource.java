package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Telefoononderwerp;
import nl.ritense.demo.repository.TelefoononderwerpRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Telefoononderwerp}.
 */
@RestController
@RequestMapping("/api/telefoononderwerps")
@Transactional
public class TelefoononderwerpResource {

    private final Logger log = LoggerFactory.getLogger(TelefoononderwerpResource.class);

    private static final String ENTITY_NAME = "telefoononderwerp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelefoononderwerpRepository telefoononderwerpRepository;

    public TelefoononderwerpResource(TelefoononderwerpRepository telefoononderwerpRepository) {
        this.telefoononderwerpRepository = telefoononderwerpRepository;
    }

    /**
     * {@code POST  /telefoononderwerps} : Create a new telefoononderwerp.
     *
     * @param telefoononderwerp the telefoononderwerp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telefoononderwerp, or with status {@code 400 (Bad Request)} if the telefoononderwerp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Telefoononderwerp> createTelefoononderwerp(@RequestBody Telefoononderwerp telefoononderwerp)
        throws URISyntaxException {
        log.debug("REST request to save Telefoononderwerp : {}", telefoononderwerp);
        if (telefoononderwerp.getId() != null) {
            throw new BadRequestAlertException("A new telefoononderwerp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        telefoononderwerp = telefoononderwerpRepository.save(telefoononderwerp);
        return ResponseEntity.created(new URI("/api/telefoononderwerps/" + telefoononderwerp.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, telefoononderwerp.getId().toString()))
            .body(telefoononderwerp);
    }

    /**
     * {@code PUT  /telefoononderwerps/:id} : Updates an existing telefoononderwerp.
     *
     * @param id the id of the telefoononderwerp to save.
     * @param telefoononderwerp the telefoononderwerp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telefoononderwerp,
     * or with status {@code 400 (Bad Request)} if the telefoononderwerp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telefoononderwerp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Telefoononderwerp> updateTelefoononderwerp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Telefoononderwerp telefoononderwerp
    ) throws URISyntaxException {
        log.debug("REST request to update Telefoononderwerp : {}, {}", id, telefoononderwerp);
        if (telefoononderwerp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telefoononderwerp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telefoononderwerpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        telefoononderwerp = telefoononderwerpRepository.save(telefoononderwerp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telefoononderwerp.getId().toString()))
            .body(telefoononderwerp);
    }

    /**
     * {@code PATCH  /telefoononderwerps/:id} : Partial updates given fields of an existing telefoononderwerp, field will ignore if it is null
     *
     * @param id the id of the telefoononderwerp to save.
     * @param telefoononderwerp the telefoononderwerp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telefoononderwerp,
     * or with status {@code 400 (Bad Request)} if the telefoononderwerp is not valid,
     * or with status {@code 404 (Not Found)} if the telefoononderwerp is not found,
     * or with status {@code 500 (Internal Server Error)} if the telefoononderwerp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Telefoononderwerp> partialUpdateTelefoononderwerp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Telefoononderwerp telefoononderwerp
    ) throws URISyntaxException {
        log.debug("REST request to partial update Telefoononderwerp partially : {}, {}", id, telefoononderwerp);
        if (telefoononderwerp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telefoononderwerp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telefoononderwerpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Telefoononderwerp> result = telefoononderwerpRepository
            .findById(telefoononderwerp.getId())
            .map(existingTelefoononderwerp -> {
                if (telefoononderwerp.getOnderwerp() != null) {
                    existingTelefoononderwerp.setOnderwerp(telefoononderwerp.getOnderwerp());
                }

                return existingTelefoononderwerp;
            })
            .map(telefoononderwerpRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telefoononderwerp.getId().toString())
        );
    }

    /**
     * {@code GET  /telefoononderwerps} : get all the telefoononderwerps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telefoononderwerps in body.
     */
    @GetMapping("")
    public List<Telefoononderwerp> getAllTelefoononderwerps() {
        log.debug("REST request to get all Telefoononderwerps");
        return telefoononderwerpRepository.findAll();
    }

    /**
     * {@code GET  /telefoononderwerps/:id} : get the "id" telefoononderwerp.
     *
     * @param id the id of the telefoononderwerp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telefoononderwerp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Telefoononderwerp> getTelefoononderwerp(@PathVariable("id") Long id) {
        log.debug("REST request to get Telefoononderwerp : {}", id);
        Optional<Telefoononderwerp> telefoononderwerp = telefoononderwerpRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(telefoononderwerp);
    }

    /**
     * {@code DELETE  /telefoononderwerps/:id} : delete the "id" telefoononderwerp.
     *
     * @param id the id of the telefoononderwerp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelefoononderwerp(@PathVariable("id") Long id) {
        log.debug("REST request to delete Telefoononderwerp : {}", id);
        telefoononderwerpRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
