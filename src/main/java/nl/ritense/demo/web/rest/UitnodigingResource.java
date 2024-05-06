package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Uitnodiging;
import nl.ritense.demo.repository.UitnodigingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitnodiging}.
 */
@RestController
@RequestMapping("/api/uitnodigings")
@Transactional
public class UitnodigingResource {

    private final Logger log = LoggerFactory.getLogger(UitnodigingResource.class);

    private static final String ENTITY_NAME = "uitnodiging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitnodigingRepository uitnodigingRepository;

    public UitnodigingResource(UitnodigingRepository uitnodigingRepository) {
        this.uitnodigingRepository = uitnodigingRepository;
    }

    /**
     * {@code POST  /uitnodigings} : Create a new uitnodiging.
     *
     * @param uitnodiging the uitnodiging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitnodiging, or with status {@code 400 (Bad Request)} if the uitnodiging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitnodiging> createUitnodiging(@RequestBody Uitnodiging uitnodiging) throws URISyntaxException {
        log.debug("REST request to save Uitnodiging : {}", uitnodiging);
        if (uitnodiging.getId() != null) {
            throw new BadRequestAlertException("A new uitnodiging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitnodiging = uitnodigingRepository.save(uitnodiging);
        return ResponseEntity.created(new URI("/api/uitnodigings/" + uitnodiging.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitnodiging.getId().toString()))
            .body(uitnodiging);
    }

    /**
     * {@code PUT  /uitnodigings/:id} : Updates an existing uitnodiging.
     *
     * @param id the id of the uitnodiging to save.
     * @param uitnodiging the uitnodiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitnodiging,
     * or with status {@code 400 (Bad Request)} if the uitnodiging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uitnodiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Uitnodiging> updateUitnodiging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitnodiging uitnodiging
    ) throws URISyntaxException {
        log.debug("REST request to update Uitnodiging : {}, {}", id, uitnodiging);
        if (uitnodiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitnodiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitnodigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uitnodiging = uitnodigingRepository.save(uitnodiging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitnodiging.getId().toString()))
            .body(uitnodiging);
    }

    /**
     * {@code PATCH  /uitnodigings/:id} : Partial updates given fields of an existing uitnodiging, field will ignore if it is null
     *
     * @param id the id of the uitnodiging to save.
     * @param uitnodiging the uitnodiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitnodiging,
     * or with status {@code 400 (Bad Request)} if the uitnodiging is not valid,
     * or with status {@code 404 (Not Found)} if the uitnodiging is not found,
     * or with status {@code 500 (Internal Server Error)} if the uitnodiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Uitnodiging> partialUpdateUitnodiging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitnodiging uitnodiging
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uitnodiging partially : {}, {}", id, uitnodiging);
        if (uitnodiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitnodiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitnodigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Uitnodiging> result = uitnodigingRepository
            .findById(uitnodiging.getId())
            .map(existingUitnodiging -> {
                if (uitnodiging.getAfgewezen() != null) {
                    existingUitnodiging.setAfgewezen(uitnodiging.getAfgewezen());
                }
                if (uitnodiging.getDatum() != null) {
                    existingUitnodiging.setDatum(uitnodiging.getDatum());
                }
                if (uitnodiging.getGeaccepteerd() != null) {
                    existingUitnodiging.setGeaccepteerd(uitnodiging.getGeaccepteerd());
                }

                return existingUitnodiging;
            })
            .map(uitnodigingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitnodiging.getId().toString())
        );
    }

    /**
     * {@code GET  /uitnodigings} : get all the uitnodigings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitnodigings in body.
     */
    @GetMapping("")
    public List<Uitnodiging> getAllUitnodigings() {
        log.debug("REST request to get all Uitnodigings");
        return uitnodigingRepository.findAll();
    }

    /**
     * {@code GET  /uitnodigings/:id} : get the "id" uitnodiging.
     *
     * @param id the id of the uitnodiging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitnodiging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitnodiging> getUitnodiging(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitnodiging : {}", id);
        Optional<Uitnodiging> uitnodiging = uitnodigingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitnodiging);
    }

    /**
     * {@code DELETE  /uitnodigings/:id} : delete the "id" uitnodiging.
     *
     * @param id the id of the uitnodiging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitnodiging(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitnodiging : {}", id);
        uitnodigingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
