package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Inschrijving;
import nl.ritense.demo.repository.InschrijvingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inschrijving}.
 */
@RestController
@RequestMapping("/api/inschrijvings")
@Transactional
public class InschrijvingResource {

    private final Logger log = LoggerFactory.getLogger(InschrijvingResource.class);

    private static final String ENTITY_NAME = "inschrijving";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InschrijvingRepository inschrijvingRepository;

    public InschrijvingResource(InschrijvingRepository inschrijvingRepository) {
        this.inschrijvingRepository = inschrijvingRepository;
    }

    /**
     * {@code POST  /inschrijvings} : Create a new inschrijving.
     *
     * @param inschrijving the inschrijving to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inschrijving, or with status {@code 400 (Bad Request)} if the inschrijving has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inschrijving> createInschrijving(@RequestBody Inschrijving inschrijving) throws URISyntaxException {
        log.debug("REST request to save Inschrijving : {}", inschrijving);
        if (inschrijving.getId() != null) {
            throw new BadRequestAlertException("A new inschrijving cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inschrijving = inschrijvingRepository.save(inschrijving);
        return ResponseEntity.created(new URI("/api/inschrijvings/" + inschrijving.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inschrijving.getId().toString()))
            .body(inschrijving);
    }

    /**
     * {@code PUT  /inschrijvings/:id} : Updates an existing inschrijving.
     *
     * @param id the id of the inschrijving to save.
     * @param inschrijving the inschrijving to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inschrijving,
     * or with status {@code 400 (Bad Request)} if the inschrijving is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inschrijving couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inschrijving> updateInschrijving(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inschrijving inschrijving
    ) throws URISyntaxException {
        log.debug("REST request to update Inschrijving : {}, {}", id, inschrijving);
        if (inschrijving.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inschrijving.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inschrijvingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inschrijving = inschrijvingRepository.save(inschrijving);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inschrijving.getId().toString()))
            .body(inschrijving);
    }

    /**
     * {@code PATCH  /inschrijvings/:id} : Partial updates given fields of an existing inschrijving, field will ignore if it is null
     *
     * @param id the id of the inschrijving to save.
     * @param inschrijving the inschrijving to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inschrijving,
     * or with status {@code 400 (Bad Request)} if the inschrijving is not valid,
     * or with status {@code 404 (Not Found)} if the inschrijving is not found,
     * or with status {@code 500 (Internal Server Error)} if the inschrijving couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inschrijving> partialUpdateInschrijving(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inschrijving inschrijving
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inschrijving partially : {}, {}", id, inschrijving);
        if (inschrijving.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inschrijving.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inschrijvingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inschrijving> result = inschrijvingRepository
            .findById(inschrijving.getId())
            .map(existingInschrijving -> {
                if (inschrijving.getDatum() != null) {
                    existingInschrijving.setDatum(inschrijving.getDatum());
                }

                return existingInschrijving;
            })
            .map(inschrijvingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inschrijving.getId().toString())
        );
    }

    /**
     * {@code GET  /inschrijvings} : get all the inschrijvings.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inschrijvings in body.
     */
    @GetMapping("")
    public List<Inschrijving> getAllInschrijvings(@RequestParam(name = "filter", required = false) String filter) {
        if ("betreftgunning-is-null".equals(filter)) {
            log.debug("REST request to get all Inschrijvings where betreftGunning is null");
            return StreamSupport.stream(inschrijvingRepository.findAll().spliterator(), false)
                .filter(inschrijving -> inschrijving.getBetreftGunning() == null)
                .toList();
        }
        log.debug("REST request to get all Inschrijvings");
        return inschrijvingRepository.findAll();
    }

    /**
     * {@code GET  /inschrijvings/:id} : get the "id" inschrijving.
     *
     * @param id the id of the inschrijving to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inschrijving, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inschrijving> getInschrijving(@PathVariable("id") Long id) {
        log.debug("REST request to get Inschrijving : {}", id);
        Optional<Inschrijving> inschrijving = inschrijvingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inschrijving);
    }

    /**
     * {@code DELETE  /inschrijvings/:id} : delete the "id" inschrijving.
     *
     * @param id the id of the inschrijving to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInschrijving(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inschrijving : {}", id);
        inschrijvingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
