package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Adresaanduiding;
import nl.ritense.demo.repository.AdresaanduidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Adresaanduiding}.
 */
@RestController
@RequestMapping("/api/adresaanduidings")
@Transactional
public class AdresaanduidingResource {

    private final Logger log = LoggerFactory.getLogger(AdresaanduidingResource.class);

    private static final String ENTITY_NAME = "adresaanduiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdresaanduidingRepository adresaanduidingRepository;

    public AdresaanduidingResource(AdresaanduidingRepository adresaanduidingRepository) {
        this.adresaanduidingRepository = adresaanduidingRepository;
    }

    /**
     * {@code POST  /adresaanduidings} : Create a new adresaanduiding.
     *
     * @param adresaanduiding the adresaanduiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adresaanduiding, or with status {@code 400 (Bad Request)} if the adresaanduiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Adresaanduiding> createAdresaanduiding(@RequestBody Adresaanduiding adresaanduiding) throws URISyntaxException {
        log.debug("REST request to save Adresaanduiding : {}", adresaanduiding);
        if (adresaanduiding.getId() != null) {
            throw new BadRequestAlertException("A new adresaanduiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adresaanduiding = adresaanduidingRepository.save(adresaanduiding);
        return ResponseEntity.created(new URI("/api/adresaanduidings/" + adresaanduiding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, adresaanduiding.getId().toString()))
            .body(adresaanduiding);
    }

    /**
     * {@code PUT  /adresaanduidings/:id} : Updates an existing adresaanduiding.
     *
     * @param id the id of the adresaanduiding to save.
     * @param adresaanduiding the adresaanduiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresaanduiding,
     * or with status {@code 400 (Bad Request)} if the adresaanduiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adresaanduiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Adresaanduiding> updateAdresaanduiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Adresaanduiding adresaanduiding
    ) throws URISyntaxException {
        log.debug("REST request to update Adresaanduiding : {}, {}", id, adresaanduiding);
        if (adresaanduiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresaanduiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresaanduidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adresaanduiding = adresaanduidingRepository.save(adresaanduiding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adresaanduiding.getId().toString()))
            .body(adresaanduiding);
    }

    /**
     * {@code PATCH  /adresaanduidings/:id} : Partial updates given fields of an existing adresaanduiding, field will ignore if it is null
     *
     * @param id the id of the adresaanduiding to save.
     * @param adresaanduiding the adresaanduiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresaanduiding,
     * or with status {@code 400 (Bad Request)} if the adresaanduiding is not valid,
     * or with status {@code 404 (Not Found)} if the adresaanduiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the adresaanduiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Adresaanduiding> partialUpdateAdresaanduiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Adresaanduiding adresaanduiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Adresaanduiding partially : {}, {}", id, adresaanduiding);
        if (adresaanduiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresaanduiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresaanduidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Adresaanduiding> result = adresaanduidingRepository
            .findById(adresaanduiding.getId())
            .map(existingAdresaanduiding -> {
                if (adresaanduiding.getAdresaanduiding() != null) {
                    existingAdresaanduiding.setAdresaanduiding(adresaanduiding.getAdresaanduiding());
                }

                return existingAdresaanduiding;
            })
            .map(adresaanduidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adresaanduiding.getId().toString())
        );
    }

    /**
     * {@code GET  /adresaanduidings} : get all the adresaanduidings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adresaanduidings in body.
     */
    @GetMapping("")
    public List<Adresaanduiding> getAllAdresaanduidings() {
        log.debug("REST request to get all Adresaanduidings");
        return adresaanduidingRepository.findAll();
    }

    /**
     * {@code GET  /adresaanduidings/:id} : get the "id" adresaanduiding.
     *
     * @param id the id of the adresaanduiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adresaanduiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Adresaanduiding> getAdresaanduiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Adresaanduiding : {}", id);
        Optional<Adresaanduiding> adresaanduiding = adresaanduidingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adresaanduiding);
    }

    /**
     * {@code DELETE  /adresaanduidings/:id} : delete the "id" adresaanduiding.
     *
     * @param id the id of the adresaanduiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdresaanduiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Adresaanduiding : {}", id);
        adresaanduidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
