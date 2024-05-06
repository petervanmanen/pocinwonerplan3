package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Waboaanvraagofmelding;
import nl.ritense.demo.repository.WaboaanvraagofmeldingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Waboaanvraagofmelding}.
 */
@RestController
@RequestMapping("/api/waboaanvraagofmeldings")
@Transactional
public class WaboaanvraagofmeldingResource {

    private final Logger log = LoggerFactory.getLogger(WaboaanvraagofmeldingResource.class);

    private static final String ENTITY_NAME = "waboaanvraagofmelding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WaboaanvraagofmeldingRepository waboaanvraagofmeldingRepository;

    public WaboaanvraagofmeldingResource(WaboaanvraagofmeldingRepository waboaanvraagofmeldingRepository) {
        this.waboaanvraagofmeldingRepository = waboaanvraagofmeldingRepository;
    }

    /**
     * {@code POST  /waboaanvraagofmeldings} : Create a new waboaanvraagofmelding.
     *
     * @param waboaanvraagofmelding the waboaanvraagofmelding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new waboaanvraagofmelding, or with status {@code 400 (Bad Request)} if the waboaanvraagofmelding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Waboaanvraagofmelding> createWaboaanvraagofmelding(
        @Valid @RequestBody Waboaanvraagofmelding waboaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to save Waboaanvraagofmelding : {}", waboaanvraagofmelding);
        if (waboaanvraagofmelding.getId() != null) {
            throw new BadRequestAlertException("A new waboaanvraagofmelding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        waboaanvraagofmelding = waboaanvraagofmeldingRepository.save(waboaanvraagofmelding);
        return ResponseEntity.created(new URI("/api/waboaanvraagofmeldings/" + waboaanvraagofmelding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, waboaanvraagofmelding.getId().toString()))
            .body(waboaanvraagofmelding);
    }

    /**
     * {@code PUT  /waboaanvraagofmeldings/:id} : Updates an existing waboaanvraagofmelding.
     *
     * @param id the id of the waboaanvraagofmelding to save.
     * @param waboaanvraagofmelding the waboaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waboaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the waboaanvraagofmelding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the waboaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Waboaanvraagofmelding> updateWaboaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Waboaanvraagofmelding waboaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to update Waboaanvraagofmelding : {}, {}", id, waboaanvraagofmelding);
        if (waboaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waboaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waboaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        waboaanvraagofmelding = waboaanvraagofmeldingRepository.save(waboaanvraagofmelding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, waboaanvraagofmelding.getId().toString()))
            .body(waboaanvraagofmelding);
    }

    /**
     * {@code PATCH  /waboaanvraagofmeldings/:id} : Partial updates given fields of an existing waboaanvraagofmelding, field will ignore if it is null
     *
     * @param id the id of the waboaanvraagofmelding to save.
     * @param waboaanvraagofmelding the waboaanvraagofmelding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated waboaanvraagofmelding,
     * or with status {@code 400 (Bad Request)} if the waboaanvraagofmelding is not valid,
     * or with status {@code 404 (Not Found)} if the waboaanvraagofmelding is not found,
     * or with status {@code 500 (Internal Server Error)} if the waboaanvraagofmelding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Waboaanvraagofmelding> partialUpdateWaboaanvraagofmelding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Waboaanvraagofmelding waboaanvraagofmelding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Waboaanvraagofmelding partially : {}, {}", id, waboaanvraagofmelding);
        if (waboaanvraagofmelding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, waboaanvraagofmelding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!waboaanvraagofmeldingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Waboaanvraagofmelding> result = waboaanvraagofmeldingRepository
            .findById(waboaanvraagofmelding.getId())
            .map(existingWaboaanvraagofmelding -> {
                if (waboaanvraagofmelding.getBouwkosten() != null) {
                    existingWaboaanvraagofmelding.setBouwkosten(waboaanvraagofmelding.getBouwkosten());
                }
                if (waboaanvraagofmelding.getOlonummer() != null) {
                    existingWaboaanvraagofmelding.setOlonummer(waboaanvraagofmelding.getOlonummer());
                }
                if (waboaanvraagofmelding.getOmschrijving() != null) {
                    existingWaboaanvraagofmelding.setOmschrijving(waboaanvraagofmelding.getOmschrijving());
                }
                if (waboaanvraagofmelding.getProjectkosten() != null) {
                    existingWaboaanvraagofmelding.setProjectkosten(waboaanvraagofmelding.getProjectkosten());
                }
                if (waboaanvraagofmelding.getRegistratienummer() != null) {
                    existingWaboaanvraagofmelding.setRegistratienummer(waboaanvraagofmelding.getRegistratienummer());
                }

                return existingWaboaanvraagofmelding;
            })
            .map(waboaanvraagofmeldingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, waboaanvraagofmelding.getId().toString())
        );
    }

    /**
     * {@code GET  /waboaanvraagofmeldings} : get all the waboaanvraagofmeldings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of waboaanvraagofmeldings in body.
     */
    @GetMapping("")
    public List<Waboaanvraagofmelding> getAllWaboaanvraagofmeldings() {
        log.debug("REST request to get all Waboaanvraagofmeldings");
        return waboaanvraagofmeldingRepository.findAll();
    }

    /**
     * {@code GET  /waboaanvraagofmeldings/:id} : get the "id" waboaanvraagofmelding.
     *
     * @param id the id of the waboaanvraagofmelding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the waboaanvraagofmelding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Waboaanvraagofmelding> getWaboaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to get Waboaanvraagofmelding : {}", id);
        Optional<Waboaanvraagofmelding> waboaanvraagofmelding = waboaanvraagofmeldingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(waboaanvraagofmelding);
    }

    /**
     * {@code DELETE  /waboaanvraagofmeldings/:id} : delete the "id" waboaanvraagofmelding.
     *
     * @param id the id of the waboaanvraagofmelding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaboaanvraagofmelding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Waboaanvraagofmelding : {}", id);
        waboaanvraagofmeldingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
