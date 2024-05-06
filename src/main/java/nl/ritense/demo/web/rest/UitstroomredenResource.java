package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Uitstroomreden;
import nl.ritense.demo.repository.UitstroomredenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitstroomreden}.
 */
@RestController
@RequestMapping("/api/uitstroomredens")
@Transactional
public class UitstroomredenResource {

    private final Logger log = LoggerFactory.getLogger(UitstroomredenResource.class);

    private static final String ENTITY_NAME = "uitstroomreden";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitstroomredenRepository uitstroomredenRepository;

    public UitstroomredenResource(UitstroomredenRepository uitstroomredenRepository) {
        this.uitstroomredenRepository = uitstroomredenRepository;
    }

    /**
     * {@code POST  /uitstroomredens} : Create a new uitstroomreden.
     *
     * @param uitstroomreden the uitstroomreden to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitstroomreden, or with status {@code 400 (Bad Request)} if the uitstroomreden has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitstroomreden> createUitstroomreden(@RequestBody Uitstroomreden uitstroomreden) throws URISyntaxException {
        log.debug("REST request to save Uitstroomreden : {}", uitstroomreden);
        if (uitstroomreden.getId() != null) {
            throw new BadRequestAlertException("A new uitstroomreden cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitstroomreden = uitstroomredenRepository.save(uitstroomreden);
        return ResponseEntity.created(new URI("/api/uitstroomredens/" + uitstroomreden.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitstroomreden.getId().toString()))
            .body(uitstroomreden);
    }

    /**
     * {@code PUT  /uitstroomredens/:id} : Updates an existing uitstroomreden.
     *
     * @param id the id of the uitstroomreden to save.
     * @param uitstroomreden the uitstroomreden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitstroomreden,
     * or with status {@code 400 (Bad Request)} if the uitstroomreden is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uitstroomreden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Uitstroomreden> updateUitstroomreden(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitstroomreden uitstroomreden
    ) throws URISyntaxException {
        log.debug("REST request to update Uitstroomreden : {}, {}", id, uitstroomreden);
        if (uitstroomreden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitstroomreden.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitstroomredenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uitstroomreden = uitstroomredenRepository.save(uitstroomreden);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitstroomreden.getId().toString()))
            .body(uitstroomreden);
    }

    /**
     * {@code PATCH  /uitstroomredens/:id} : Partial updates given fields of an existing uitstroomreden, field will ignore if it is null
     *
     * @param id the id of the uitstroomreden to save.
     * @param uitstroomreden the uitstroomreden to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitstroomreden,
     * or with status {@code 400 (Bad Request)} if the uitstroomreden is not valid,
     * or with status {@code 404 (Not Found)} if the uitstroomreden is not found,
     * or with status {@code 500 (Internal Server Error)} if the uitstroomreden couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Uitstroomreden> partialUpdateUitstroomreden(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitstroomreden uitstroomreden
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uitstroomreden partially : {}, {}", id, uitstroomreden);
        if (uitstroomreden.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitstroomreden.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitstroomredenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Uitstroomreden> result = uitstroomredenRepository
            .findById(uitstroomreden.getId())
            .map(existingUitstroomreden -> {
                if (uitstroomreden.getDatum() != null) {
                    existingUitstroomreden.setDatum(uitstroomreden.getDatum());
                }
                if (uitstroomreden.getOmschrijving() != null) {
                    existingUitstroomreden.setOmschrijving(uitstroomreden.getOmschrijving());
                }

                return existingUitstroomreden;
            })
            .map(uitstroomredenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitstroomreden.getId().toString())
        );
    }

    /**
     * {@code GET  /uitstroomredens} : get all the uitstroomredens.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitstroomredens in body.
     */
    @GetMapping("")
    public List<Uitstroomreden> getAllUitstroomredens(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftuitstroomredenproject-is-null".equals(filter)) {
            log.debug("REST request to get all Uitstroomredens where heeftuitstroomredenProject is null");
            return StreamSupport.stream(uitstroomredenRepository.findAll().spliterator(), false)
                .filter(uitstroomreden -> uitstroomreden.getHeeftuitstroomredenProject() == null)
                .toList();
        }

        if ("heeftuitstroomredentraject-is-null".equals(filter)) {
            log.debug("REST request to get all Uitstroomredens where heeftuitstroomredenTraject is null");
            return StreamSupport.stream(uitstroomredenRepository.findAll().spliterator(), false)
                .filter(uitstroomreden -> uitstroomreden.getHeeftuitstroomredenTraject() == null)
                .toList();
        }
        log.debug("REST request to get all Uitstroomredens");
        return uitstroomredenRepository.findAll();
    }

    /**
     * {@code GET  /uitstroomredens/:id} : get the "id" uitstroomreden.
     *
     * @param id the id of the uitstroomreden to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitstroomreden, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitstroomreden> getUitstroomreden(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitstroomreden : {}", id);
        Optional<Uitstroomreden> uitstroomreden = uitstroomredenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitstroomreden);
    }

    /**
     * {@code DELETE  /uitstroomredens/:id} : delete the "id" uitstroomreden.
     *
     * @param id the id of the uitstroomreden to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitstroomreden(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitstroomreden : {}", id);
        uitstroomredenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
