package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Melding;
import nl.ritense.demo.repository.MeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Melding}.
 */
@RestController
@RequestMapping("/api/meldings")
@Transactional
public class MeldingResource {

    private final Logger log = LoggerFactory.getLogger(MeldingResource.class);

    private static final String ENTITY_NAME = "melding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeldingRepository meldingRepository;

    public MeldingResource(MeldingRepository meldingRepository) {
        this.meldingRepository = meldingRepository;
    }

    /**
     * {@code POST  /meldings} : Create a new melding.
     *
     * @param melding the melding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new melding, or with status {@code 400 (Bad Request)} if the melding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Melding> createMelding(@RequestBody Melding melding) throws URISyntaxException {
        log.debug("REST request to save Melding : {}", melding);
        if (melding.getId() != null) {
            throw new BadRequestAlertException("A new melding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        melding = meldingRepository.save(melding);
        return ResponseEntity.created(new URI("/api/meldings/" + melding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, melding.getId().toString()))
            .body(melding);
    }

    /**
     * {@code PUT  /meldings/:id} : Updates an existing melding.
     *
     * @param id the id of the melding to save.
     * @param melding the melding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated melding,
     * or with status {@code 400 (Bad Request)} if the melding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the melding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Melding> updateMelding(@PathVariable(value = "id", required = false) final Long id, @RequestBody Melding melding)
        throws URISyntaxException {
        log.debug("REST request to update Melding : {}, {}", id, melding);
        if (melding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, melding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!meldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        melding = meldingRepository.save(melding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, melding.getId().toString()))
            .body(melding);
    }

    /**
     * {@code PATCH  /meldings/:id} : Partial updates given fields of an existing melding, field will ignore if it is null
     *
     * @param id the id of the melding to save.
     * @param melding the melding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated melding,
     * or with status {@code 400 (Bad Request)} if the melding is not valid,
     * or with status {@code 404 (Not Found)} if the melding is not found,
     * or with status {@code 500 (Internal Server Error)} if the melding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Melding> partialUpdateMelding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Melding melding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Melding partially : {}, {}", id, melding);
        if (melding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, melding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!meldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Melding> result = meldingRepository
            .findById(melding.getId())
            .map(existingMelding -> {
                if (melding.getVierentwintiguurs() != null) {
                    existingMelding.setVierentwintiguurs(melding.getVierentwintiguurs());
                }
                if (melding.getDatumtijd() != null) {
                    existingMelding.setDatumtijd(melding.getDatumtijd());
                }
                if (melding.getIllegaal() != null) {
                    existingMelding.setIllegaal(melding.getIllegaal());
                }
                if (melding.getMeldingnummer() != null) {
                    existingMelding.setMeldingnummer(melding.getMeldingnummer());
                }
                if (melding.getOmschrijving() != null) {
                    existingMelding.setOmschrijving(melding.getOmschrijving());
                }

                return existingMelding;
            })
            .map(meldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, melding.getId().toString())
        );
    }

    /**
     * {@code GET  /meldings} : get all the meldings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meldings in body.
     */
    @GetMapping("")
    public List<Melding> getAllMeldings(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Meldings");
        if (eagerload) {
            return meldingRepository.findAllWithEagerRelationships();
        } else {
            return meldingRepository.findAll();
        }
    }

    /**
     * {@code GET  /meldings/:id} : get the "id" melding.
     *
     * @param id the id of the melding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the melding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Melding> getMelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Melding : {}", id);
        Optional<Melding> melding = meldingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(melding);
    }

    /**
     * {@code DELETE  /meldings/:id} : delete the "id" melding.
     *
     * @param id the id of the melding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Melding : {}", id);
        meldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
