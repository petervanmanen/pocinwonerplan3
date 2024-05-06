package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Formatieplaats;
import nl.ritense.demo.repository.FormatieplaatsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Formatieplaats}.
 */
@RestController
@RequestMapping("/api/formatieplaats")
@Transactional
public class FormatieplaatsResource {

    private final Logger log = LoggerFactory.getLogger(FormatieplaatsResource.class);

    private static final String ENTITY_NAME = "formatieplaats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormatieplaatsRepository formatieplaatsRepository;

    public FormatieplaatsResource(FormatieplaatsRepository formatieplaatsRepository) {
        this.formatieplaatsRepository = formatieplaatsRepository;
    }

    /**
     * {@code POST  /formatieplaats} : Create a new formatieplaats.
     *
     * @param formatieplaats the formatieplaats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formatieplaats, or with status {@code 400 (Bad Request)} if the formatieplaats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Formatieplaats> createFormatieplaats(@Valid @RequestBody Formatieplaats formatieplaats)
        throws URISyntaxException {
        log.debug("REST request to save Formatieplaats : {}", formatieplaats);
        if (formatieplaats.getId() != null) {
            throw new BadRequestAlertException("A new formatieplaats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formatieplaats = formatieplaatsRepository.save(formatieplaats);
        return ResponseEntity.created(new URI("/api/formatieplaats/" + formatieplaats.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, formatieplaats.getId().toString()))
            .body(formatieplaats);
    }

    /**
     * {@code PUT  /formatieplaats/:id} : Updates an existing formatieplaats.
     *
     * @param id the id of the formatieplaats to save.
     * @param formatieplaats the formatieplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formatieplaats,
     * or with status {@code 400 (Bad Request)} if the formatieplaats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formatieplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Formatieplaats> updateFormatieplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Formatieplaats formatieplaats
    ) throws URISyntaxException {
        log.debug("REST request to update Formatieplaats : {}, {}", id, formatieplaats);
        if (formatieplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formatieplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formatieplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formatieplaats = formatieplaatsRepository.save(formatieplaats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formatieplaats.getId().toString()))
            .body(formatieplaats);
    }

    /**
     * {@code PATCH  /formatieplaats/:id} : Partial updates given fields of an existing formatieplaats, field will ignore if it is null
     *
     * @param id the id of the formatieplaats to save.
     * @param formatieplaats the formatieplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formatieplaats,
     * or with status {@code 400 (Bad Request)} if the formatieplaats is not valid,
     * or with status {@code 404 (Not Found)} if the formatieplaats is not found,
     * or with status {@code 500 (Internal Server Error)} if the formatieplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Formatieplaats> partialUpdateFormatieplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Formatieplaats formatieplaats
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formatieplaats partially : {}, {}", id, formatieplaats);
        if (formatieplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formatieplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formatieplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Formatieplaats> result = formatieplaatsRepository
            .findById(formatieplaats.getId())
            .map(existingFormatieplaats -> {
                if (formatieplaats.getUrenperweek() != null) {
                    existingFormatieplaats.setUrenperweek(formatieplaats.getUrenperweek());
                }

                return existingFormatieplaats;
            })
            .map(formatieplaatsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formatieplaats.getId().toString())
        );
    }

    /**
     * {@code GET  /formatieplaats} : get all the formatieplaats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formatieplaats in body.
     */
    @GetMapping("")
    public List<Formatieplaats> getAllFormatieplaats() {
        log.debug("REST request to get all Formatieplaats");
        return formatieplaatsRepository.findAll();
    }

    /**
     * {@code GET  /formatieplaats/:id} : get the "id" formatieplaats.
     *
     * @param id the id of the formatieplaats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formatieplaats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Formatieplaats> getFormatieplaats(@PathVariable("id") Long id) {
        log.debug("REST request to get Formatieplaats : {}", id);
        Optional<Formatieplaats> formatieplaats = formatieplaatsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formatieplaats);
    }

    /**
     * {@code DELETE  /formatieplaats/:id} : delete the "id" formatieplaats.
     *
     * @param id the id of the formatieplaats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormatieplaats(@PathVariable("id") Long id) {
        log.debug("REST request to delete Formatieplaats : {}", id);
        formatieplaatsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
