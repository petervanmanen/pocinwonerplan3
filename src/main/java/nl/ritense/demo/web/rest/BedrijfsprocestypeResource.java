package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Bedrijfsprocestype;
import nl.ritense.demo.repository.BedrijfsprocestypeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bedrijfsprocestype}.
 */
@RestController
@RequestMapping("/api/bedrijfsprocestypes")
@Transactional
public class BedrijfsprocestypeResource {

    private final Logger log = LoggerFactory.getLogger(BedrijfsprocestypeResource.class);

    private static final String ENTITY_NAME = "bedrijfsprocestype";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BedrijfsprocestypeRepository bedrijfsprocestypeRepository;

    public BedrijfsprocestypeResource(BedrijfsprocestypeRepository bedrijfsprocestypeRepository) {
        this.bedrijfsprocestypeRepository = bedrijfsprocestypeRepository;
    }

    /**
     * {@code POST  /bedrijfsprocestypes} : Create a new bedrijfsprocestype.
     *
     * @param bedrijfsprocestype the bedrijfsprocestype to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bedrijfsprocestype, or with status {@code 400 (Bad Request)} if the bedrijfsprocestype has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bedrijfsprocestype> createBedrijfsprocestype(@Valid @RequestBody Bedrijfsprocestype bedrijfsprocestype)
        throws URISyntaxException {
        log.debug("REST request to save Bedrijfsprocestype : {}", bedrijfsprocestype);
        if (bedrijfsprocestype.getId() != null) {
            throw new BadRequestAlertException("A new bedrijfsprocestype cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bedrijfsprocestype = bedrijfsprocestypeRepository.save(bedrijfsprocestype);
        return ResponseEntity.created(new URI("/api/bedrijfsprocestypes/" + bedrijfsprocestype.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bedrijfsprocestype.getId().toString()))
            .body(bedrijfsprocestype);
    }

    /**
     * {@code PUT  /bedrijfsprocestypes/:id} : Updates an existing bedrijfsprocestype.
     *
     * @param id the id of the bedrijfsprocestype to save.
     * @param bedrijfsprocestype the bedrijfsprocestype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedrijfsprocestype,
     * or with status {@code 400 (Bad Request)} if the bedrijfsprocestype is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bedrijfsprocestype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bedrijfsprocestype> updateBedrijfsprocestype(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bedrijfsprocestype bedrijfsprocestype
    ) throws URISyntaxException {
        log.debug("REST request to update Bedrijfsprocestype : {}, {}", id, bedrijfsprocestype);
        if (bedrijfsprocestype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedrijfsprocestype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedrijfsprocestypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bedrijfsprocestype = bedrijfsprocestypeRepository.save(bedrijfsprocestype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bedrijfsprocestype.getId().toString()))
            .body(bedrijfsprocestype);
    }

    /**
     * {@code PATCH  /bedrijfsprocestypes/:id} : Partial updates given fields of an existing bedrijfsprocestype, field will ignore if it is null
     *
     * @param id the id of the bedrijfsprocestype to save.
     * @param bedrijfsprocestype the bedrijfsprocestype to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedrijfsprocestype,
     * or with status {@code 400 (Bad Request)} if the bedrijfsprocestype is not valid,
     * or with status {@code 404 (Not Found)} if the bedrijfsprocestype is not found,
     * or with status {@code 500 (Internal Server Error)} if the bedrijfsprocestype couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bedrijfsprocestype> partialUpdateBedrijfsprocestype(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bedrijfsprocestype bedrijfsprocestype
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bedrijfsprocestype partially : {}, {}", id, bedrijfsprocestype);
        if (bedrijfsprocestype.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedrijfsprocestype.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedrijfsprocestypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bedrijfsprocestype> result = bedrijfsprocestypeRepository
            .findById(bedrijfsprocestype.getId())
            .map(existingBedrijfsprocestype -> {
                if (bedrijfsprocestype.getOmschrijving() != null) {
                    existingBedrijfsprocestype.setOmschrijving(bedrijfsprocestype.getOmschrijving());
                }

                return existingBedrijfsprocestype;
            })
            .map(bedrijfsprocestypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bedrijfsprocestype.getId().toString())
        );
    }

    /**
     * {@code GET  /bedrijfsprocestypes} : get all the bedrijfsprocestypes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bedrijfsprocestypes in body.
     */
    @GetMapping("")
    public List<Bedrijfsprocestype> getAllBedrijfsprocestypes(@RequestParam(name = "filter", required = false) String filter) {
        if ("isdeelvandeelprocestype-is-null".equals(filter)) {
            log.debug("REST request to get all Bedrijfsprocestypes where isdeelvanDeelprocestype is null");
            return StreamSupport.stream(bedrijfsprocestypeRepository.findAll().spliterator(), false)
                .filter(bedrijfsprocestype -> bedrijfsprocestype.getIsdeelvanDeelprocestype() == null)
                .toList();
        }
        log.debug("REST request to get all Bedrijfsprocestypes");
        return bedrijfsprocestypeRepository.findAll();
    }

    /**
     * {@code GET  /bedrijfsprocestypes/:id} : get the "id" bedrijfsprocestype.
     *
     * @param id the id of the bedrijfsprocestype to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bedrijfsprocestype, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bedrijfsprocestype> getBedrijfsprocestype(@PathVariable("id") Long id) {
        log.debug("REST request to get Bedrijfsprocestype : {}", id);
        Optional<Bedrijfsprocestype> bedrijfsprocestype = bedrijfsprocestypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bedrijfsprocestype);
    }

    /**
     * {@code DELETE  /bedrijfsprocestypes/:id} : delete the "id" bedrijfsprocestype.
     *
     * @param id the id of the bedrijfsprocestype to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBedrijfsprocestype(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bedrijfsprocestype : {}", id);
        bedrijfsprocestypeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
