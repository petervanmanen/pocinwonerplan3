package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Hoofdstuk;
import nl.ritense.demo.repository.HoofdstukRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Hoofdstuk}.
 */
@RestController
@RequestMapping("/api/hoofdstuks")
@Transactional
public class HoofdstukResource {

    private final Logger log = LoggerFactory.getLogger(HoofdstukResource.class);

    private static final String ENTITY_NAME = "hoofdstuk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoofdstukRepository hoofdstukRepository;

    public HoofdstukResource(HoofdstukRepository hoofdstukRepository) {
        this.hoofdstukRepository = hoofdstukRepository;
    }

    /**
     * {@code POST  /hoofdstuks} : Create a new hoofdstuk.
     *
     * @param hoofdstuk the hoofdstuk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hoofdstuk, or with status {@code 400 (Bad Request)} if the hoofdstuk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Hoofdstuk> createHoofdstuk(@Valid @RequestBody Hoofdstuk hoofdstuk) throws URISyntaxException {
        log.debug("REST request to save Hoofdstuk : {}", hoofdstuk);
        if (hoofdstuk.getId() != null) {
            throw new BadRequestAlertException("A new hoofdstuk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hoofdstuk = hoofdstukRepository.save(hoofdstuk);
        return ResponseEntity.created(new URI("/api/hoofdstuks/" + hoofdstuk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, hoofdstuk.getId().toString()))
            .body(hoofdstuk);
    }

    /**
     * {@code PUT  /hoofdstuks/:id} : Updates an existing hoofdstuk.
     *
     * @param id the id of the hoofdstuk to save.
     * @param hoofdstuk the hoofdstuk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoofdstuk,
     * or with status {@code 400 (Bad Request)} if the hoofdstuk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hoofdstuk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hoofdstuk> updateHoofdstuk(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Hoofdstuk hoofdstuk
    ) throws URISyntaxException {
        log.debug("REST request to update Hoofdstuk : {}, {}", id, hoofdstuk);
        if (hoofdstuk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoofdstuk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoofdstukRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hoofdstuk = hoofdstukRepository.save(hoofdstuk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hoofdstuk.getId().toString()))
            .body(hoofdstuk);
    }

    /**
     * {@code PATCH  /hoofdstuks/:id} : Partial updates given fields of an existing hoofdstuk, field will ignore if it is null
     *
     * @param id the id of the hoofdstuk to save.
     * @param hoofdstuk the hoofdstuk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoofdstuk,
     * or with status {@code 400 (Bad Request)} if the hoofdstuk is not valid,
     * or with status {@code 404 (Not Found)} if the hoofdstuk is not found,
     * or with status {@code 500 (Internal Server Error)} if the hoofdstuk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Hoofdstuk> partialUpdateHoofdstuk(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Hoofdstuk hoofdstuk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hoofdstuk partially : {}, {}", id, hoofdstuk);
        if (hoofdstuk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoofdstuk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoofdstukRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Hoofdstuk> result = hoofdstukRepository
            .findById(hoofdstuk.getId())
            .map(existingHoofdstuk -> {
                if (hoofdstuk.getNaam() != null) {
                    existingHoofdstuk.setNaam(hoofdstuk.getNaam());
                }
                if (hoofdstuk.getNummer() != null) {
                    existingHoofdstuk.setNummer(hoofdstuk.getNummer());
                }
                if (hoofdstuk.getOmschrijving() != null) {
                    existingHoofdstuk.setOmschrijving(hoofdstuk.getOmschrijving());
                }

                return existingHoofdstuk;
            })
            .map(hoofdstukRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hoofdstuk.getId().toString())
        );
    }

    /**
     * {@code GET  /hoofdstuks} : get all the hoofdstuks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hoofdstuks in body.
     */
    @GetMapping("")
    public List<Hoofdstuk> getAllHoofdstuks(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Hoofdstuks");
        if (eagerload) {
            return hoofdstukRepository.findAllWithEagerRelationships();
        } else {
            return hoofdstukRepository.findAll();
        }
    }

    /**
     * {@code GET  /hoofdstuks/:id} : get the "id" hoofdstuk.
     *
     * @param id the id of the hoofdstuk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hoofdstuk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hoofdstuk> getHoofdstuk(@PathVariable("id") Long id) {
        log.debug("REST request to get Hoofdstuk : {}", id);
        Optional<Hoofdstuk> hoofdstuk = hoofdstukRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(hoofdstuk);
    }

    /**
     * {@code DELETE  /hoofdstuks/:id} : delete the "id" hoofdstuk.
     *
     * @param id the id of the hoofdstuk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoofdstuk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Hoofdstuk : {}", id);
        hoofdstukRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
