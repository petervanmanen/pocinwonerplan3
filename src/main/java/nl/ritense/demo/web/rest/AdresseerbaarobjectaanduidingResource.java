package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Adresseerbaarobjectaanduiding;
import nl.ritense.demo.repository.AdresseerbaarobjectaanduidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Adresseerbaarobjectaanduiding}.
 */
@RestController
@RequestMapping("/api/adresseerbaarobjectaanduidings")
@Transactional
public class AdresseerbaarobjectaanduidingResource {

    private final Logger log = LoggerFactory.getLogger(AdresseerbaarobjectaanduidingResource.class);

    private static final String ENTITY_NAME = "adresseerbaarobjectaanduiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdresseerbaarobjectaanduidingRepository adresseerbaarobjectaanduidingRepository;

    public AdresseerbaarobjectaanduidingResource(AdresseerbaarobjectaanduidingRepository adresseerbaarobjectaanduidingRepository) {
        this.adresseerbaarobjectaanduidingRepository = adresseerbaarobjectaanduidingRepository;
    }

    /**
     * {@code POST  /adresseerbaarobjectaanduidings} : Create a new adresseerbaarobjectaanduiding.
     *
     * @param adresseerbaarobjectaanduiding the adresseerbaarobjectaanduiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adresseerbaarobjectaanduiding, or with status {@code 400 (Bad Request)} if the adresseerbaarobjectaanduiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Adresseerbaarobjectaanduiding> createAdresseerbaarobjectaanduiding(
        @Valid @RequestBody Adresseerbaarobjectaanduiding adresseerbaarobjectaanduiding
    ) throws URISyntaxException {
        log.debug("REST request to save Adresseerbaarobjectaanduiding : {}", adresseerbaarobjectaanduiding);
        if (adresseerbaarobjectaanduiding.getId() != null) {
            throw new BadRequestAlertException("A new adresseerbaarobjectaanduiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adresseerbaarobjectaanduiding = adresseerbaarobjectaanduidingRepository.save(adresseerbaarobjectaanduiding);
        return ResponseEntity.created(new URI("/api/adresseerbaarobjectaanduidings/" + adresseerbaarobjectaanduiding.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, adresseerbaarobjectaanduiding.getId().toString())
            )
            .body(adresseerbaarobjectaanduiding);
    }

    /**
     * {@code PUT  /adresseerbaarobjectaanduidings/:id} : Updates an existing adresseerbaarobjectaanduiding.
     *
     * @param id the id of the adresseerbaarobjectaanduiding to save.
     * @param adresseerbaarobjectaanduiding the adresseerbaarobjectaanduiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresseerbaarobjectaanduiding,
     * or with status {@code 400 (Bad Request)} if the adresseerbaarobjectaanduiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adresseerbaarobjectaanduiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Adresseerbaarobjectaanduiding> updateAdresseerbaarobjectaanduiding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Adresseerbaarobjectaanduiding adresseerbaarobjectaanduiding
    ) throws URISyntaxException {
        log.debug("REST request to update Adresseerbaarobjectaanduiding : {}, {}", id, adresseerbaarobjectaanduiding);
        if (adresseerbaarobjectaanduiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresseerbaarobjectaanduiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresseerbaarobjectaanduidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adresseerbaarobjectaanduiding = adresseerbaarobjectaanduidingRepository.save(adresseerbaarobjectaanduiding);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adresseerbaarobjectaanduiding.getId().toString())
            )
            .body(adresseerbaarobjectaanduiding);
    }

    /**
     * {@code PATCH  /adresseerbaarobjectaanduidings/:id} : Partial updates given fields of an existing adresseerbaarobjectaanduiding, field will ignore if it is null
     *
     * @param id the id of the adresseerbaarobjectaanduiding to save.
     * @param adresseerbaarobjectaanduiding the adresseerbaarobjectaanduiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresseerbaarobjectaanduiding,
     * or with status {@code 400 (Bad Request)} if the adresseerbaarobjectaanduiding is not valid,
     * or with status {@code 404 (Not Found)} if the adresseerbaarobjectaanduiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the adresseerbaarobjectaanduiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Adresseerbaarobjectaanduiding> partialUpdateAdresseerbaarobjectaanduiding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Adresseerbaarobjectaanduiding adresseerbaarobjectaanduiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Adresseerbaarobjectaanduiding partially : {}, {}", id, adresseerbaarobjectaanduiding);
        if (adresseerbaarobjectaanduiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresseerbaarobjectaanduiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresseerbaarobjectaanduidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Adresseerbaarobjectaanduiding> result = adresseerbaarobjectaanduidingRepository
            .findById(adresseerbaarobjectaanduiding.getId())
            .map(existingAdresseerbaarobjectaanduiding -> {
                if (adresseerbaarobjectaanduiding.getIdentificatie() != null) {
                    existingAdresseerbaarobjectaanduiding.setIdentificatie(adresseerbaarobjectaanduiding.getIdentificatie());
                }

                return existingAdresseerbaarobjectaanduiding;
            })
            .map(adresseerbaarobjectaanduidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adresseerbaarobjectaanduiding.getId().toString())
        );
    }

    /**
     * {@code GET  /adresseerbaarobjectaanduidings} : get all the adresseerbaarobjectaanduidings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adresseerbaarobjectaanduidings in body.
     */
    @GetMapping("")
    public List<Adresseerbaarobjectaanduiding> getAllAdresseerbaarobjectaanduidings() {
        log.debug("REST request to get all Adresseerbaarobjectaanduidings");
        return adresseerbaarobjectaanduidingRepository.findAll();
    }

    /**
     * {@code GET  /adresseerbaarobjectaanduidings/:id} : get the "id" adresseerbaarobjectaanduiding.
     *
     * @param id the id of the adresseerbaarobjectaanduiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adresseerbaarobjectaanduiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Adresseerbaarobjectaanduiding> getAdresseerbaarobjectaanduiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Adresseerbaarobjectaanduiding : {}", id);
        Optional<Adresseerbaarobjectaanduiding> adresseerbaarobjectaanduiding = adresseerbaarobjectaanduidingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adresseerbaarobjectaanduiding);
    }

    /**
     * {@code DELETE  /adresseerbaarobjectaanduidings/:id} : delete the "id" adresseerbaarobjectaanduiding.
     *
     * @param id the id of the adresseerbaarobjectaanduiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdresseerbaarobjectaanduiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Adresseerbaarobjectaanduiding : {}", id);
        adresseerbaarobjectaanduidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
