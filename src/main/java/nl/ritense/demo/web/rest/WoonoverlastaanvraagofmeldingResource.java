package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Woonoverlastaanvraagofmelding;
import nl.ritense.demo.repository.WoonoverlastaanvraagofmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Woonoverlastaanvraagofmelding}.
 */
@RestController
@RequestMapping("/api/woonoverlastaanvraagofmeldings")
@Transactional
public class WoonoverlastaanvraagofmeldingResource {

    private final Logger log = LoggerFactory.getLogger(WoonoverlastaanvraagofmeldingResource.class);

    private static final String ENTITY_NAME = "woonoverlastaanvraagofmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WoonoverlastaanvraagofmeldingRepository woonoverlastaanvraagofmeldingRepository;

    public WoonoverlastaanvraagofmeldingResource(WoonoverlastaanvraagofmeldingRepository woonoverlastaanvraagofmeldingRepository) {
        this.woonoverlastaanvraagofmeldingRepository = woonoverlastaanvraagofmeldingRepository;
    }

    /**
     * {@code POST  /woonoverlastaanvraagofmeldings} : Create a new woonoverlastaanvraagofmelding.
     *
     * @param woonoverlastaanvraagofmelding the woonoverlastaanvraagofmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new woonoverlastaanvraagofmelding, or with status {@code 400 (Bad Request)} if the woonoverlastaanvraagofmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Woonoverlastaanvraagofmelding> createWoonoverlastaanvraagofmelding(
        @RequestBody Woonoverlastaanvraagofmelding woonoverlastaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to save Woonoverlastaanvraagofmelding : {}", woonoverlastaanvraagofmelding);
        if (woonoverlastaanvraagofmelding.getId() != null) {
            throw new BadRequestAlertException("A new woonoverlastaanvraagofmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        woonoverlastaanvraagofmelding = woonoverlastaanvraagofmeldingRepository.save(woonoverlastaanvraagofmelding);
        return ResponseEntity.created(new URI("/api/woonoverlastaanvraagofmeldings/" + woonoverlastaanvraagofmelding.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, woonoverlastaanvraagofmelding.getId().toString())
            )
            .body(woonoverlastaanvraagofmelding);
    }

    /**
     * {@code PUT  /woonoverlastaanvraagofmeldings/:id} : Updates an existing woonoverlastaanvraagofmelding.
     *
     * @param id the id of the woonoverlastaanvraagofmelding to save.
     * @param woonoverlastaanvraagofmelding the woonoverlastaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated woonoverlastaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the woonoverlastaanvraagofmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the woonoverlastaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Woonoverlastaanvraagofmelding> updateWoonoverlastaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Woonoverlastaanvraagofmelding woonoverlastaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Woonoverlastaanvraagofmelding : {}, {}", id, woonoverlastaanvraagofmelding);
        if (woonoverlastaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, woonoverlastaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!woonoverlastaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        woonoverlastaanvraagofmelding = woonoverlastaanvraagofmeldingRepository.save(woonoverlastaanvraagofmelding);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, woonoverlastaanvraagofmelding.getId().toString())
            )
            .body(woonoverlastaanvraagofmelding);
    }

    /**
     * {@code PATCH  /woonoverlastaanvraagofmeldings/:id} : Partial updates given fields of an existing woonoverlastaanvraagofmelding, field will ignore if it is null
     *
     * @param id the id of the woonoverlastaanvraagofmelding to save.
     * @param woonoverlastaanvraagofmelding the woonoverlastaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated woonoverlastaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the woonoverlastaanvraagofmelding is not valid,
     * or with status {@code 404 (Not Found)} if the woonoverlastaanvraagofmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the woonoverlastaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Woonoverlastaanvraagofmelding> partialUpdateWoonoverlastaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Woonoverlastaanvraagofmelding woonoverlastaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Woonoverlastaanvraagofmelding partially : {}, {}", id, woonoverlastaanvraagofmelding);
        if (woonoverlastaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, woonoverlastaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!woonoverlastaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Woonoverlastaanvraagofmelding> result = woonoverlastaanvraagofmeldingRepository
            .findById(woonoverlastaanvraagofmelding.getId())
            .map(existingWoonoverlastaanvraagofmelding -> {
                if (woonoverlastaanvraagofmelding.getLocatie() != null) {
                    existingWoonoverlastaanvraagofmelding.setLocatie(woonoverlastaanvraagofmelding.getLocatie());
                }
                if (woonoverlastaanvraagofmelding.getLocatieomschrijving() != null) {
                    existingWoonoverlastaanvraagofmelding.setLocatieomschrijving(woonoverlastaanvraagofmelding.getLocatieomschrijving());
                }
                if (woonoverlastaanvraagofmelding.getMeldingomschrijving() != null) {
                    existingWoonoverlastaanvraagofmelding.setMeldingomschrijving(woonoverlastaanvraagofmelding.getMeldingomschrijving());
                }
                if (woonoverlastaanvraagofmelding.getMeldingtekst() != null) {
                    existingWoonoverlastaanvraagofmelding.setMeldingtekst(woonoverlastaanvraagofmelding.getMeldingtekst());
                }

                return existingWoonoverlastaanvraagofmelding;
            })
            .map(woonoverlastaanvraagofmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, woonoverlastaanvraagofmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /woonoverlastaanvraagofmeldings} : get all the woonoverlastaanvraagofmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of woonoverlastaanvraagofmeldings in body.
     */
    @GetMapping("")
    public List<Woonoverlastaanvraagofmelding> getAllWoonoverlastaanvraagofmeldings() {
        log.debug("REST request to get all Woonoverlastaanvraagofmeldings");
        return woonoverlastaanvraagofmeldingRepository.findAll();
    }

    /**
     * {@code GET  /woonoverlastaanvraagofmeldings/:id} : get the "id" woonoverlastaanvraagofmelding.
     *
     * @param id the id of the woonoverlastaanvraagofmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the woonoverlastaanvraagofmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Woonoverlastaanvraagofmelding> getWoonoverlastaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Woonoverlastaanvraagofmelding : {}", id);
        Optional<Woonoverlastaanvraagofmelding> woonoverlastaanvraagofmelding = woonoverlastaanvraagofmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(woonoverlastaanvraagofmelding);
    }

    /**
     * {@code DELETE  /woonoverlastaanvraagofmeldings/:id} : delete the "id" woonoverlastaanvraagofmelding.
     *
     * @param id the id of the woonoverlastaanvraagofmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWoonoverlastaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Woonoverlastaanvraagofmelding : {}", id);
        woonoverlastaanvraagofmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
