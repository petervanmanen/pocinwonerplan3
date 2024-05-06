package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Uitschrijving;
import nl.ritense.demo.repository.UitschrijvingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitschrijving}.
 */
@RestController
@RequestMapping("/api/uitschrijvings")
@Transactional
public class UitschrijvingResource {

    private final Logger log = LoggerFactory.getLogger(UitschrijvingResource.class);

    private static final String ENTITY_NAME = "uitschrijving";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitschrijvingRepository uitschrijvingRepository;

    public UitschrijvingResource(UitschrijvingRepository uitschrijvingRepository) {
        this.uitschrijvingRepository = uitschrijvingRepository;
    }

    /**
     * {@code POST  /uitschrijvings} : Create a new uitschrijving.
     *
     * @param uitschrijving the uitschrijving to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitschrijving, or with status {@code 400 (Bad Request)} if the uitschrijving has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitschrijving> createUitschrijving(@RequestBody Uitschrijving uitschrijving) throws URISyntaxException {
        log.debug("REST request to save Uitschrijving : {}", uitschrijving);
        if (uitschrijving.getId() != null) {
            throw new BadRequestAlertException("A new uitschrijving cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitschrijving = uitschrijvingRepository.save(uitschrijving);
        return ResponseEntity.created(new URI("/api/uitschrijvings/" + uitschrijving.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitschrijving.getId().toString()))
            .body(uitschrijving);
    }

    /**
     * {@code PUT  /uitschrijvings/:id} : Updates an existing uitschrijving.
     *
     * @param id the id of the uitschrijving to save.
     * @param uitschrijving the uitschrijving to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitschrijving,
     * or with status {@code 400 (Bad Request)} if the uitschrijving is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uitschrijving couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Uitschrijving> updateUitschrijving(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitschrijving uitschrijving
    ) throws URISyntaxException {
        log.debug("REST request to update Uitschrijving : {}, {}", id, uitschrijving);
        if (uitschrijving.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitschrijving.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitschrijvingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uitschrijving = uitschrijvingRepository.save(uitschrijving);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitschrijving.getId().toString()))
            .body(uitschrijving);
    }

    /**
     * {@code PATCH  /uitschrijvings/:id} : Partial updates given fields of an existing uitschrijving, field will ignore if it is null
     *
     * @param id the id of the uitschrijving to save.
     * @param uitschrijving the uitschrijving to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitschrijving,
     * or with status {@code 400 (Bad Request)} if the uitschrijving is not valid,
     * or with status {@code 404 (Not Found)} if the uitschrijving is not found,
     * or with status {@code 500 (Internal Server Error)} if the uitschrijving couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Uitschrijving> partialUpdateUitschrijving(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitschrijving uitschrijving
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uitschrijving partially : {}, {}", id, uitschrijving);
        if (uitschrijving.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitschrijving.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitschrijvingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Uitschrijving> result = uitschrijvingRepository
            .findById(uitschrijving.getId())
            .map(existingUitschrijving -> {
                if (uitschrijving.getDatum() != null) {
                    existingUitschrijving.setDatum(uitschrijving.getDatum());
                }
                if (uitschrijving.getDiplomabehaald() != null) {
                    existingUitschrijving.setDiplomabehaald(uitschrijving.getDiplomabehaald());
                }

                return existingUitschrijving;
            })
            .map(uitschrijvingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitschrijving.getId().toString())
        );
    }

    /**
     * {@code GET  /uitschrijvings} : get all the uitschrijvings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitschrijvings in body.
     */
    @GetMapping("")
    public List<Uitschrijving> getAllUitschrijvings() {
        log.debug("REST request to get all Uitschrijvings");
        return uitschrijvingRepository.findAll();
    }

    /**
     * {@code GET  /uitschrijvings/:id} : get the "id" uitschrijving.
     *
     * @param id the id of the uitschrijving to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitschrijving, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitschrijving> getUitschrijving(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitschrijving : {}", id);
        Optional<Uitschrijving> uitschrijving = uitschrijvingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitschrijving);
    }

    /**
     * {@code DELETE  /uitschrijvings/:id} : delete the "id" uitschrijving.
     *
     * @param id the id of the uitschrijving to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitschrijving(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitschrijving : {}", id);
        uitschrijvingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
