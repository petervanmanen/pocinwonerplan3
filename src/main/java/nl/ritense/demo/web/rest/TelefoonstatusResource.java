package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Telefoonstatus;
import nl.ritense.demo.repository.TelefoonstatusRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Telefoonstatus}.
 */
@RestController
@RequestMapping("/api/telefoonstatuses")
@Transactional
public class TelefoonstatusResource {

    private final Logger log = LoggerFactory.getLogger(TelefoonstatusResource.class);

    private static final String ENTITY_NAME = "telefoonstatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelefoonstatusRepository telefoonstatusRepository;

    public TelefoonstatusResource(TelefoonstatusRepository telefoonstatusRepository) {
        this.telefoonstatusRepository = telefoonstatusRepository;
    }

    /**
     * {@code POST  /telefoonstatuses} : Create a new telefoonstatus.
     *
     * @param telefoonstatus the telefoonstatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telefoonstatus, or with status {@code 400 (Bad Request)} if the telefoonstatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Telefoonstatus> createTelefoonstatus(@Valid @RequestBody Telefoonstatus telefoonstatus)
        throws URISyntaxException {
        log.debug("REST request to save Telefoonstatus : {}", telefoonstatus);
        if (telefoonstatus.getId() != null) {
            throw new BadRequestAlertException("A new telefoonstatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        telefoonstatus = telefoonstatusRepository.save(telefoonstatus);
        return ResponseEntity.created(new URI("/api/telefoonstatuses/" + telefoonstatus.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, telefoonstatus.getId().toString()))
            .body(telefoonstatus);
    }

    /**
     * {@code PUT  /telefoonstatuses/:id} : Updates an existing telefoonstatus.
     *
     * @param id the id of the telefoonstatus to save.
     * @param telefoonstatus the telefoonstatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telefoonstatus,
     * or with status {@code 400 (Bad Request)} if the telefoonstatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telefoonstatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Telefoonstatus> updateTelefoonstatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Telefoonstatus telefoonstatus
    ) throws URISyntaxException {
        log.debug("REST request to update Telefoonstatus : {}, {}", id, telefoonstatus);
        if (telefoonstatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telefoonstatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telefoonstatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        telefoonstatus = telefoonstatusRepository.save(telefoonstatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telefoonstatus.getId().toString()))
            .body(telefoonstatus);
    }

    /**
     * {@code PATCH  /telefoonstatuses/:id} : Partial updates given fields of an existing telefoonstatus, field will ignore if it is null
     *
     * @param id the id of the telefoonstatus to save.
     * @param telefoonstatus the telefoonstatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telefoonstatus,
     * or with status {@code 400 (Bad Request)} if the telefoonstatus is not valid,
     * or with status {@code 404 (Not Found)} if the telefoonstatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the telefoonstatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Telefoonstatus> partialUpdateTelefoonstatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Telefoonstatus telefoonstatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update Telefoonstatus partially : {}, {}", id, telefoonstatus);
        if (telefoonstatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telefoonstatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telefoonstatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Telefoonstatus> result = telefoonstatusRepository
            .findById(telefoonstatus.getId())
            .map(existingTelefoonstatus -> {
                if (telefoonstatus.getContactconnectionstate() != null) {
                    existingTelefoonstatus.setContactconnectionstate(telefoonstatus.getContactconnectionstate());
                }
                if (telefoonstatus.getStatus() != null) {
                    existingTelefoonstatus.setStatus(telefoonstatus.getStatus());
                }

                return existingTelefoonstatus;
            })
            .map(telefoonstatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telefoonstatus.getId().toString())
        );
    }

    /**
     * {@code GET  /telefoonstatuses} : get all the telefoonstatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telefoonstatuses in body.
     */
    @GetMapping("")
    public List<Telefoonstatus> getAllTelefoonstatuses() {
        log.debug("REST request to get all Telefoonstatuses");
        return telefoonstatusRepository.findAll();
    }

    /**
     * {@code GET  /telefoonstatuses/:id} : get the "id" telefoonstatus.
     *
     * @param id the id of the telefoonstatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telefoonstatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Telefoonstatus> getTelefoonstatus(@PathVariable("id") Long id) {
        log.debug("REST request to get Telefoonstatus : {}", id);
        Optional<Telefoonstatus> telefoonstatus = telefoonstatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(telefoonstatus);
    }

    /**
     * {@code DELETE  /telefoonstatuses/:id} : delete the "id" telefoonstatus.
     *
     * @param id the id of the telefoonstatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelefoonstatus(@PathVariable("id") Long id) {
        log.debug("REST request to delete Telefoonstatus : {}", id);
        telefoonstatusRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
