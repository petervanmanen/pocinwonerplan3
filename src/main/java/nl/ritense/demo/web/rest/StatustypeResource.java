package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Statustype;
import nl.ritense.demo.repository.StatustypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Statustype}.
 */
@RestController
@RequestMapping("/api/statustypes")
@Transactional
public class StatustypeResource {

    private final Logger log = LoggerFactory.getLogger(StatustypeResource.class);

    private static final String ENTITY_NAME = "statustype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatustypeRepository statustypeRepository;

    public StatustypeResource(StatustypeRepository statustypeRepository) {
        this.statustypeRepository = statustypeRepository;
    }

    /**
     * {@code POST  /statustypes} : Create a new statustype.
     *
     * @param statustype the statustype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statustype, or with status {@code 400 (Bad Request)} if the statustype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Statustype> createStatustype(@Valid @RequestBody Statustype statustype) throws URISyntaxException {
        log.debug("REST request to save Statustype : {}", statustype);
        if (statustype.getId() != null) {
            throw new BadRequestAlertException("A new statustype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        statustype = statustypeRepository.save(statustype);
        return ResponseEntity.created(new URI("/api/statustypes/" + statustype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, statustype.getId().toString()))
            .body(statustype);
    }

    /**
     * {@code PUT  /statustypes/:id} : Updates an existing statustype.
     *
     * @param id the id of the statustype to save.
     * @param statustype the statustype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statustype,
     * or with status {@code 400 (Bad Request)} if the statustype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statustype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Statustype> updateStatustype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Statustype statustype
    ) throws URISyntaxException {
        log.debug("REST request to update Statustype : {}, {}", id, statustype);
        if (statustype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statustype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statustypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        statustype = statustypeRepository.save(statustype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statustype.getId().toString()))
            .body(statustype);
    }

    /**
     * {@code PATCH  /statustypes/:id} : Partial updates given fields of an existing statustype, field will ignore if it is null
     *
     * @param id the id of the statustype to save.
     * @param statustype the statustype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statustype,
     * or with status {@code 400 (Bad Request)} if the statustype is not valid,
     * or with status {@code 404 (Not Found)} if the statustype is not found,
     * or with status {@code 500 (Internal Server Error)} if the statustype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Statustype> partialUpdateStatustype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Statustype statustype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Statustype partially : {}, {}", id, statustype);
        if (statustype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statustype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statustypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Statustype> result = statustypeRepository
            .findById(statustype.getId())
            .map(existingStatustype -> {
                if (statustype.getDatumbegingeldigheidstatustype() != null) {
                    existingStatustype.setDatumbegingeldigheidstatustype(statustype.getDatumbegingeldigheidstatustype());
                }
                if (statustype.getDatumeindegeldigheidstatustype() != null) {
                    existingStatustype.setDatumeindegeldigheidstatustype(statustype.getDatumeindegeldigheidstatustype());
                }
                if (statustype.getDoorlooptijdstatus() != null) {
                    existingStatustype.setDoorlooptijdstatus(statustype.getDoorlooptijdstatus());
                }
                if (statustype.getStatustypeomschrijving() != null) {
                    existingStatustype.setStatustypeomschrijving(statustype.getStatustypeomschrijving());
                }
                if (statustype.getStatustypeomschrijvinggeneriek() != null) {
                    existingStatustype.setStatustypeomschrijvinggeneriek(statustype.getStatustypeomschrijvinggeneriek());
                }
                if (statustype.getStatustypevolgnummer() != null) {
                    existingStatustype.setStatustypevolgnummer(statustype.getStatustypevolgnummer());
                }

                return existingStatustype;
            })
            .map(statustypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statustype.getId().toString())
        );
    }

    /**
     * {@code GET  /statustypes} : get all the statustypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statustypes in body.
     */
    @GetMapping("")
    public List<Statustype> getAllStatustypes() {
        log.debug("REST request to get all Statustypes");
        return statustypeRepository.findAll();
    }

    /**
     * {@code GET  /statustypes/:id} : get the "id" statustype.
     *
     * @param id the id of the statustype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statustype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Statustype> getStatustype(@PathVariable("id") Long id) {
        log.debug("REST request to get Statustype : {}", id);
        Optional<Statustype> statustype = statustypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(statustype);
    }

    /**
     * {@code DELETE  /statustypes/:id} : delete the "id" statustype.
     *
     * @param id the id of the statustype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatustype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Statustype : {}", id);
        statustypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
