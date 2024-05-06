package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Woonfraudeaanvraagofmelding;
import nl.ritense.demo.repository.WoonfraudeaanvraagofmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Woonfraudeaanvraagofmelding}.
 */
@RestController
@RequestMapping("/api/woonfraudeaanvraagofmeldings")
@Transactional
public class WoonfraudeaanvraagofmeldingResource {

    private final Logger log = LoggerFactory.getLogger(WoonfraudeaanvraagofmeldingResource.class);

    private static final String ENTITY_NAME = "woonfraudeaanvraagofmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WoonfraudeaanvraagofmeldingRepository woonfraudeaanvraagofmeldingRepository;

    public WoonfraudeaanvraagofmeldingResource(WoonfraudeaanvraagofmeldingRepository woonfraudeaanvraagofmeldingRepository) {
        this.woonfraudeaanvraagofmeldingRepository = woonfraudeaanvraagofmeldingRepository;
    }

    /**
     * {@code POST  /woonfraudeaanvraagofmeldings} : Create a new woonfraudeaanvraagofmelding.
     *
     * @param woonfraudeaanvraagofmelding the woonfraudeaanvraagofmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new woonfraudeaanvraagofmelding, or with status {@code 400 (Bad Request)} if the woonfraudeaanvraagofmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Woonfraudeaanvraagofmelding> createWoonfraudeaanvraagofmelding(
        @RequestBody Woonfraudeaanvraagofmelding woonfraudeaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to save Woonfraudeaanvraagofmelding : {}", woonfraudeaanvraagofmelding);
        if (woonfraudeaanvraagofmelding.getId() != null) {
            throw new BadRequestAlertException("A new woonfraudeaanvraagofmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        woonfraudeaanvraagofmelding = woonfraudeaanvraagofmeldingRepository.save(woonfraudeaanvraagofmelding);
        return ResponseEntity.created(new URI("/api/woonfraudeaanvraagofmeldings/" + woonfraudeaanvraagofmelding.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, woonfraudeaanvraagofmelding.getId().toString())
            )
            .body(woonfraudeaanvraagofmelding);
    }

    /**
     * {@code PUT  /woonfraudeaanvraagofmeldings/:id} : Updates an existing woonfraudeaanvraagofmelding.
     *
     * @param id the id of the woonfraudeaanvraagofmelding to save.
     * @param woonfraudeaanvraagofmelding the woonfraudeaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated woonfraudeaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the woonfraudeaanvraagofmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the woonfraudeaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Woonfraudeaanvraagofmelding> updateWoonfraudeaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Woonfraudeaanvraagofmelding woonfraudeaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Woonfraudeaanvraagofmelding : {}, {}", id, woonfraudeaanvraagofmelding);
        if (woonfraudeaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, woonfraudeaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!woonfraudeaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        woonfraudeaanvraagofmelding = woonfraudeaanvraagofmeldingRepository.save(woonfraudeaanvraagofmelding);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, woonfraudeaanvraagofmelding.getId().toString())
            )
            .body(woonfraudeaanvraagofmelding);
    }

    /**
     * {@code PATCH  /woonfraudeaanvraagofmeldings/:id} : Partial updates given fields of an existing woonfraudeaanvraagofmelding, field will ignore if it is null
     *
     * @param id the id of the woonfraudeaanvraagofmelding to save.
     * @param woonfraudeaanvraagofmelding the woonfraudeaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated woonfraudeaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the woonfraudeaanvraagofmelding is not valid,
     * or with status {@code 404 (Not Found)} if the woonfraudeaanvraagofmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the woonfraudeaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Woonfraudeaanvraagofmelding> partialUpdateWoonfraudeaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Woonfraudeaanvraagofmelding woonfraudeaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Woonfraudeaanvraagofmelding partially : {}, {}", id, woonfraudeaanvraagofmelding);
        if (woonfraudeaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, woonfraudeaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!woonfraudeaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Woonfraudeaanvraagofmelding> result = woonfraudeaanvraagofmeldingRepository
            .findById(woonfraudeaanvraagofmelding.getId())
            .map(existingWoonfraudeaanvraagofmelding -> {
                if (woonfraudeaanvraagofmelding.getAdres() != null) {
                    existingWoonfraudeaanvraagofmelding.setAdres(woonfraudeaanvraagofmelding.getAdres());
                }
                if (woonfraudeaanvraagofmelding.getCategorie() != null) {
                    existingWoonfraudeaanvraagofmelding.setCategorie(woonfraudeaanvraagofmelding.getCategorie());
                }
                if (woonfraudeaanvraagofmelding.getLocatieomschrijving() != null) {
                    existingWoonfraudeaanvraagofmelding.setLocatieomschrijving(woonfraudeaanvraagofmelding.getLocatieomschrijving());
                }
                if (woonfraudeaanvraagofmelding.getMeldingomschrijving() != null) {
                    existingWoonfraudeaanvraagofmelding.setMeldingomschrijving(woonfraudeaanvraagofmelding.getMeldingomschrijving());
                }
                if (woonfraudeaanvraagofmelding.getMeldingtekst() != null) {
                    existingWoonfraudeaanvraagofmelding.setMeldingtekst(woonfraudeaanvraagofmelding.getMeldingtekst());
                }

                return existingWoonfraudeaanvraagofmelding;
            })
            .map(woonfraudeaanvraagofmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, woonfraudeaanvraagofmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /woonfraudeaanvraagofmeldings} : get all the woonfraudeaanvraagofmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of woonfraudeaanvraagofmeldings in body.
     */
    @GetMapping("")
    public List<Woonfraudeaanvraagofmelding> getAllWoonfraudeaanvraagofmeldings() {
        log.debug("REST request to get all Woonfraudeaanvraagofmeldings");
        return woonfraudeaanvraagofmeldingRepository.findAll();
    }

    /**
     * {@code GET  /woonfraudeaanvraagofmeldings/:id} : get the "id" woonfraudeaanvraagofmelding.
     *
     * @param id the id of the woonfraudeaanvraagofmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the woonfraudeaanvraagofmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Woonfraudeaanvraagofmelding> getWoonfraudeaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Woonfraudeaanvraagofmelding : {}", id);
        Optional<Woonfraudeaanvraagofmelding> woonfraudeaanvraagofmelding = woonfraudeaanvraagofmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(woonfraudeaanvraagofmelding);
    }

    /**
     * {@code DELETE  /woonfraudeaanvraagofmeldings/:id} : delete the "id" woonfraudeaanvraagofmelding.
     *
     * @param id the id of the woonfraudeaanvraagofmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWoonfraudeaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Woonfraudeaanvraagofmelding : {}", id);
        woonfraudeaanvraagofmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
