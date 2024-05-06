package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Moraanvraagofmelding;
import nl.ritense.demo.repository.MoraanvraagofmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Moraanvraagofmelding}.
 */
@RestController
@RequestMapping("/api/moraanvraagofmeldings")
@Transactional
public class MoraanvraagofmeldingResource {

    private final Logger log = LoggerFactory.getLogger(MoraanvraagofmeldingResource.class);

    private static final String ENTITY_NAME = "moraanvraagofmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoraanvraagofmeldingRepository moraanvraagofmeldingRepository;

    public MoraanvraagofmeldingResource(MoraanvraagofmeldingRepository moraanvraagofmeldingRepository) {
        this.moraanvraagofmeldingRepository = moraanvraagofmeldingRepository;
    }

    /**
     * {@code POST  /moraanvraagofmeldings} : Create a new moraanvraagofmelding.
     *
     * @param moraanvraagofmelding the moraanvraagofmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moraanvraagofmelding, or with status {@code 400 (Bad Request)} if the moraanvraagofmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Moraanvraagofmelding> createMoraanvraagofmelding(@RequestBody Moraanvraagofmelding moraanvraagofmelding)
        throws URISyntaxException {
        log.debug("REST request to save Moraanvraagofmelding : {}", moraanvraagofmelding);
        if (moraanvraagofmelding.getId() != null) {
            throw new BadRequestAlertException("A new moraanvraagofmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        moraanvraagofmelding = moraanvraagofmeldingRepository.save(moraanvraagofmelding);
        return ResponseEntity.created(new URI("/api/moraanvraagofmeldings/" + moraanvraagofmelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, moraanvraagofmelding.getId().toString()))
            .body(moraanvraagofmelding);
    }

    /**
     * {@code PUT  /moraanvraagofmeldings/:id} : Updates an existing moraanvraagofmelding.
     *
     * @param id the id of the moraanvraagofmelding to save.
     * @param moraanvraagofmelding the moraanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moraanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the moraanvraagofmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moraanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Moraanvraagofmelding> updateMoraanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Moraanvraagofmelding moraanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Moraanvraagofmelding : {}, {}", id, moraanvraagofmelding);
        if (moraanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moraanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moraanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        moraanvraagofmelding = moraanvraagofmeldingRepository.save(moraanvraagofmelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moraanvraagofmelding.getId().toString()))
            .body(moraanvraagofmelding);
    }

    /**
     * {@code PATCH  /moraanvraagofmeldings/:id} : Partial updates given fields of an existing moraanvraagofmelding, field will ignore if it is null
     *
     * @param id the id of the moraanvraagofmelding to save.
     * @param moraanvraagofmelding the moraanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moraanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the moraanvraagofmelding is not valid,
     * or with status {@code 404 (Not Found)} if the moraanvraagofmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the moraanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Moraanvraagofmelding> partialUpdateMoraanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Moraanvraagofmelding moraanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Moraanvraagofmelding partially : {}, {}", id, moraanvraagofmelding);
        if (moraanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moraanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moraanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Moraanvraagofmelding> result = moraanvraagofmeldingRepository
            .findById(moraanvraagofmelding.getId())
            .map(existingMoraanvraagofmelding -> {
                if (moraanvraagofmelding.getCrow() != null) {
                    existingMoraanvraagofmelding.setCrow(moraanvraagofmelding.getCrow());
                }
                if (moraanvraagofmelding.getLocatie() != null) {
                    existingMoraanvraagofmelding.setLocatie(moraanvraagofmelding.getLocatie());
                }
                if (moraanvraagofmelding.getLocatieomschrijving() != null) {
                    existingMoraanvraagofmelding.setLocatieomschrijving(moraanvraagofmelding.getLocatieomschrijving());
                }
                if (moraanvraagofmelding.getMeldingomschrijving() != null) {
                    existingMoraanvraagofmelding.setMeldingomschrijving(moraanvraagofmelding.getMeldingomschrijving());
                }
                if (moraanvraagofmelding.getMeldingtekst() != null) {
                    existingMoraanvraagofmelding.setMeldingtekst(moraanvraagofmelding.getMeldingtekst());
                }

                return existingMoraanvraagofmelding;
            })
            .map(moraanvraagofmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moraanvraagofmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /moraanvraagofmeldings} : get all the moraanvraagofmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moraanvraagofmeldings in body.
     */
    @GetMapping("")
    public List<Moraanvraagofmelding> getAllMoraanvraagofmeldings() {
        log.debug("REST request to get all Moraanvraagofmeldings");
        return moraanvraagofmeldingRepository.findAll();
    }

    /**
     * {@code GET  /moraanvraagofmeldings/:id} : get the "id" moraanvraagofmelding.
     *
     * @param id the id of the moraanvraagofmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moraanvraagofmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Moraanvraagofmelding> getMoraanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Moraanvraagofmelding : {}", id);
        Optional<Moraanvraagofmelding> moraanvraagofmelding = moraanvraagofmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(moraanvraagofmelding);
    }

    /**
     * {@code DELETE  /moraanvraagofmeldings/:id} : delete the "id" moraanvraagofmelding.
     *
     * @param id the id of the moraanvraagofmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoraanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Moraanvraagofmelding : {}", id);
        moraanvraagofmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
