package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Milieustraat;
import nl.ritense.demo.repository.MilieustraatRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Milieustraat}.
 */
@RestController
@RequestMapping("/api/milieustraats")
@Transactional
public class MilieustraatResource {

    private final Logger log = LoggerFactory.getLogger(MilieustraatResource.class);

    private static final String ENTITY_NAME = "milieustraat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MilieustraatRepository milieustraatRepository;

    public MilieustraatResource(MilieustraatRepository milieustraatRepository) {
        this.milieustraatRepository = milieustraatRepository;
    }

    /**
     * {@code POST  /milieustraats} : Create a new milieustraat.
     *
     * @param milieustraat the milieustraat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new milieustraat, or with status {@code 400 (Bad Request)} if the milieustraat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Milieustraat> createMilieustraat(@Valid @RequestBody Milieustraat milieustraat) throws URISyntaxException {
        log.debug("REST request to save Milieustraat : {}", milieustraat);
        if (milieustraat.getId() != null) {
            throw new BadRequestAlertException("A new milieustraat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        milieustraat = milieustraatRepository.save(milieustraat);
        return ResponseEntity.created(new URI("/api/milieustraats/" + milieustraat.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, milieustraat.getId().toString()))
            .body(milieustraat);
    }

    /**
     * {@code PUT  /milieustraats/:id} : Updates an existing milieustraat.
     *
     * @param id the id of the milieustraat to save.
     * @param milieustraat the milieustraat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated milieustraat,
     * or with status {@code 400 (Bad Request)} if the milieustraat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the milieustraat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Milieustraat> updateMilieustraat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Milieustraat milieustraat
    ) throws URISyntaxException {
        log.debug("REST request to update Milieustraat : {}, {}", id, milieustraat);
        if (milieustraat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, milieustraat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!milieustraatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        milieustraat = milieustraatRepository.save(milieustraat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, milieustraat.getId().toString()))
            .body(milieustraat);
    }

    /**
     * {@code PATCH  /milieustraats/:id} : Partial updates given fields of an existing milieustraat, field will ignore if it is null
     *
     * @param id the id of the milieustraat to save.
     * @param milieustraat the milieustraat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated milieustraat,
     * or with status {@code 400 (Bad Request)} if the milieustraat is not valid,
     * or with status {@code 404 (Not Found)} if the milieustraat is not found,
     * or with status {@code 500 (Internal Server Error)} if the milieustraat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Milieustraat> partialUpdateMilieustraat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Milieustraat milieustraat
    ) throws URISyntaxException {
        log.debug("REST request to partial update Milieustraat partially : {}, {}", id, milieustraat);
        if (milieustraat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, milieustraat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!milieustraatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Milieustraat> result = milieustraatRepository
            .findById(milieustraat.getId())
            .map(existingMilieustraat -> {
                if (milieustraat.getAdresaanduiding() != null) {
                    existingMilieustraat.setAdresaanduiding(milieustraat.getAdresaanduiding());
                }
                if (milieustraat.getNaam() != null) {
                    existingMilieustraat.setNaam(milieustraat.getNaam());
                }
                if (milieustraat.getOmschrijving() != null) {
                    existingMilieustraat.setOmschrijving(milieustraat.getOmschrijving());
                }

                return existingMilieustraat;
            })
            .map(milieustraatRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, milieustraat.getId().toString())
        );
    }

    /**
     * {@code GET  /milieustraats} : get all the milieustraats.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of milieustraats in body.
     */
    @GetMapping("")
    public List<Milieustraat> getAllMilieustraats(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Milieustraats");
        if (eagerload) {
            return milieustraatRepository.findAllWithEagerRelationships();
        } else {
            return milieustraatRepository.findAll();
        }
    }

    /**
     * {@code GET  /milieustraats/:id} : get the "id" milieustraat.
     *
     * @param id the id of the milieustraat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the milieustraat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Milieustraat> getMilieustraat(@PathVariable("id") Long id) {
        log.debug("REST request to get Milieustraat : {}", id);
        Optional<Milieustraat> milieustraat = milieustraatRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(milieustraat);
    }

    /**
     * {@code DELETE  /milieustraats/:id} : delete the "id" milieustraat.
     *
     * @param id the id of the milieustraat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilieustraat(@PathVariable("id") Long id) {
        log.debug("REST request to delete Milieustraat : {}", id);
        milieustraatRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
