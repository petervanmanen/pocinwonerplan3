package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Adresseerbaarobject;
import nl.ritense.demo.repository.AdresseerbaarobjectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Adresseerbaarobject}.
 */
@RestController
@RequestMapping("/api/adresseerbaarobjects")
@Transactional
public class AdresseerbaarobjectResource {

    private final Logger log = LoggerFactory.getLogger(AdresseerbaarobjectResource.class);

    private static final String ENTITY_NAME = "adresseerbaarobject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdresseerbaarobjectRepository adresseerbaarobjectRepository;

    public AdresseerbaarobjectResource(AdresseerbaarobjectRepository adresseerbaarobjectRepository) {
        this.adresseerbaarobjectRepository = adresseerbaarobjectRepository;
    }

    /**
     * {@code POST  /adresseerbaarobjects} : Create a new adresseerbaarobject.
     *
     * @param adresseerbaarobject the adresseerbaarobject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adresseerbaarobject, or with status {@code 400 (Bad Request)} if the adresseerbaarobject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Adresseerbaarobject> createAdresseerbaarobject(@RequestBody Adresseerbaarobject adresseerbaarobject)
        throws URISyntaxException {
        log.debug("REST request to save Adresseerbaarobject : {}", adresseerbaarobject);
        if (adresseerbaarobject.getId() != null) {
            throw new BadRequestAlertException("A new adresseerbaarobject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adresseerbaarobject = adresseerbaarobjectRepository.save(adresseerbaarobject);
        return ResponseEntity.created(new URI("/api/adresseerbaarobjects/" + adresseerbaarobject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, adresseerbaarobject.getId().toString()))
            .body(adresseerbaarobject);
    }

    /**
     * {@code PUT  /adresseerbaarobjects/:id} : Updates an existing adresseerbaarobject.
     *
     * @param id the id of the adresseerbaarobject to save.
     * @param adresseerbaarobject the adresseerbaarobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresseerbaarobject,
     * or with status {@code 400 (Bad Request)} if the adresseerbaarobject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adresseerbaarobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Adresseerbaarobject> updateAdresseerbaarobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Adresseerbaarobject adresseerbaarobject
    ) throws URISyntaxException {
        log.debug("REST request to update Adresseerbaarobject : {}, {}", id, adresseerbaarobject);
        if (adresseerbaarobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresseerbaarobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresseerbaarobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adresseerbaarobject = adresseerbaarobjectRepository.save(adresseerbaarobject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adresseerbaarobject.getId().toString()))
            .body(adresseerbaarobject);
    }

    /**
     * {@code PATCH  /adresseerbaarobjects/:id} : Partial updates given fields of an existing adresseerbaarobject, field will ignore if it is null
     *
     * @param id the id of the adresseerbaarobject to save.
     * @param adresseerbaarobject the adresseerbaarobject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adresseerbaarobject,
     * or with status {@code 400 (Bad Request)} if the adresseerbaarobject is not valid,
     * or with status {@code 404 (Not Found)} if the adresseerbaarobject is not found,
     * or with status {@code 500 (Internal Server Error)} if the adresseerbaarobject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Adresseerbaarobject> partialUpdateAdresseerbaarobject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Adresseerbaarobject adresseerbaarobject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Adresseerbaarobject partially : {}, {}", id, adresseerbaarobject);
        if (adresseerbaarobject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adresseerbaarobject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adresseerbaarobjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Adresseerbaarobject> result = adresseerbaarobjectRepository
            .findById(adresseerbaarobject.getId())
            .map(existingAdresseerbaarobject -> {
                if (adresseerbaarobject.getIdentificatie() != null) {
                    existingAdresseerbaarobject.setIdentificatie(adresseerbaarobject.getIdentificatie());
                }
                if (adresseerbaarobject.getTypeadresseerbaarobject() != null) {
                    existingAdresseerbaarobject.setTypeadresseerbaarobject(adresseerbaarobject.getTypeadresseerbaarobject());
                }
                if (adresseerbaarobject.getVersie() != null) {
                    existingAdresseerbaarobject.setVersie(adresseerbaarobject.getVersie());
                }

                return existingAdresseerbaarobject;
            })
            .map(adresseerbaarobjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adresseerbaarobject.getId().toString())
        );
    }

    /**
     * {@code GET  /adresseerbaarobjects} : get all the adresseerbaarobjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adresseerbaarobjects in body.
     */
    @GetMapping("")
    public List<Adresseerbaarobject> getAllAdresseerbaarobjects() {
        log.debug("REST request to get all Adresseerbaarobjects");
        return adresseerbaarobjectRepository.findAll();
    }

    /**
     * {@code GET  /adresseerbaarobjects/:id} : get the "id" adresseerbaarobject.
     *
     * @param id the id of the adresseerbaarobject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adresseerbaarobject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Adresseerbaarobject> getAdresseerbaarobject(@PathVariable("id") Long id) {
        log.debug("REST request to get Adresseerbaarobject : {}", id);
        Optional<Adresseerbaarobject> adresseerbaarobject = adresseerbaarobjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adresseerbaarobject);
    }

    /**
     * {@code DELETE  /adresseerbaarobjects/:id} : delete the "id" adresseerbaarobject.
     *
     * @param id the id of the adresseerbaarobject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdresseerbaarobject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Adresseerbaarobject : {}", id);
        adresseerbaarobjectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
