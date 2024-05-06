package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overigeadresseerbaarobjectaanduiding;
import nl.ritense.demo.repository.OverigeadresseerbaarobjectaanduidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overigeadresseerbaarobjectaanduiding}.
 */
@RestController
@RequestMapping("/api/overigeadresseerbaarobjectaanduidings")
@Transactional
public class OverigeadresseerbaarobjectaanduidingResource {

    private final Logger log = LoggerFactory.getLogger(OverigeadresseerbaarobjectaanduidingResource.class);

    private static final String ENTITY_NAME = "overigeadresseerbaarobjectaanduiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverigeadresseerbaarobjectaanduidingRepository overigeadresseerbaarobjectaanduidingRepository;

    public OverigeadresseerbaarobjectaanduidingResource(
        OverigeadresseerbaarobjectaanduidingRepository overigeadresseerbaarobjectaanduidingRepository
    ) {
        this.overigeadresseerbaarobjectaanduidingRepository = overigeadresseerbaarobjectaanduidingRepository;
    }

    /**
     * {@code POST  /overigeadresseerbaarobjectaanduidings} : Create a new overigeadresseerbaarobjectaanduiding.
     *
     * @param overigeadresseerbaarobjectaanduiding the overigeadresseerbaarobjectaanduiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overigeadresseerbaarobjectaanduiding, or with status {@code 400 (Bad Request)} if the overigeadresseerbaarobjectaanduiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overigeadresseerbaarobjectaanduiding> createOverigeadresseerbaarobjectaanduiding(
        @RequestBody Overigeadresseerbaarobjectaanduiding overigeadresseerbaarobjectaanduiding
    ) throws URISyntaxException {
        log.debug("REST request to save Overigeadresseerbaarobjectaanduiding : {}", overigeadresseerbaarobjectaanduiding);
        if (overigeadresseerbaarobjectaanduiding.getId() != null) {
            throw new BadRequestAlertException(
                "A new overigeadresseerbaarobjectaanduiding cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        overigeadresseerbaarobjectaanduiding = overigeadresseerbaarobjectaanduidingRepository.save(overigeadresseerbaarobjectaanduiding);
        return ResponseEntity.created(new URI("/api/overigeadresseerbaarobjectaanduidings/" + overigeadresseerbaarobjectaanduiding.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    overigeadresseerbaarobjectaanduiding.getId().toString()
                )
            )
            .body(overigeadresseerbaarobjectaanduiding);
    }

    /**
     * {@code PUT  /overigeadresseerbaarobjectaanduidings/:id} : Updates an existing overigeadresseerbaarobjectaanduiding.
     *
     * @param id the id of the overigeadresseerbaarobjectaanduiding to save.
     * @param overigeadresseerbaarobjectaanduiding the overigeadresseerbaarobjectaanduiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overigeadresseerbaarobjectaanduiding,
     * or with status {@code 400 (Bad Request)} if the overigeadresseerbaarobjectaanduiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overigeadresseerbaarobjectaanduiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overigeadresseerbaarobjectaanduiding> updateOverigeadresseerbaarobjectaanduiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overigeadresseerbaarobjectaanduiding overigeadresseerbaarobjectaanduiding
    ) throws URISyntaxException {
        log.debug("REST request to update Overigeadresseerbaarobjectaanduiding : {}, {}", id, overigeadresseerbaarobjectaanduiding);
        if (overigeadresseerbaarobjectaanduiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overigeadresseerbaarobjectaanduiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overigeadresseerbaarobjectaanduidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overigeadresseerbaarobjectaanduiding = overigeadresseerbaarobjectaanduidingRepository.save(overigeadresseerbaarobjectaanduiding);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    overigeadresseerbaarobjectaanduiding.getId().toString()
                )
            )
            .body(overigeadresseerbaarobjectaanduiding);
    }

    /**
     * {@code PATCH  /overigeadresseerbaarobjectaanduidings/:id} : Partial updates given fields of an existing overigeadresseerbaarobjectaanduiding, field will ignore if it is null
     *
     * @param id the id of the overigeadresseerbaarobjectaanduiding to save.
     * @param overigeadresseerbaarobjectaanduiding the overigeadresseerbaarobjectaanduiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overigeadresseerbaarobjectaanduiding,
     * or with status {@code 400 (Bad Request)} if the overigeadresseerbaarobjectaanduiding is not valid,
     * or with status {@code 404 (Not Found)} if the overigeadresseerbaarobjectaanduiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the overigeadresseerbaarobjectaanduiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overigeadresseerbaarobjectaanduiding> partialUpdateOverigeadresseerbaarobjectaanduiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overigeadresseerbaarobjectaanduiding overigeadresseerbaarobjectaanduiding
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Overigeadresseerbaarobjectaanduiding partially : {}, {}",
            id,
            overigeadresseerbaarobjectaanduiding
        );
        if (overigeadresseerbaarobjectaanduiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overigeadresseerbaarobjectaanduiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overigeadresseerbaarobjectaanduidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overigeadresseerbaarobjectaanduiding> result = overigeadresseerbaarobjectaanduidingRepository
            .findById(overigeadresseerbaarobjectaanduiding.getId())
            .map(existingOverigeadresseerbaarobjectaanduiding -> {
                if (overigeadresseerbaarobjectaanduiding.getIdentificatiecodeoverigadresseerbaarobjectaanduiding() != null) {
                    existingOverigeadresseerbaarobjectaanduiding.setIdentificatiecodeoverigadresseerbaarobjectaanduiding(
                        overigeadresseerbaarobjectaanduiding.getIdentificatiecodeoverigadresseerbaarobjectaanduiding()
                    );
                }

                return existingOverigeadresseerbaarobjectaanduiding;
            })
            .map(overigeadresseerbaarobjectaanduidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overigeadresseerbaarobjectaanduiding.getId().toString())
        );
    }

    /**
     * {@code GET  /overigeadresseerbaarobjectaanduidings} : get all the overigeadresseerbaarobjectaanduidings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overigeadresseerbaarobjectaanduidings in body.
     */
    @GetMapping("")
    public List<Overigeadresseerbaarobjectaanduiding> getAllOverigeadresseerbaarobjectaanduidings() {
        log.debug("REST request to get all Overigeadresseerbaarobjectaanduidings");
        return overigeadresseerbaarobjectaanduidingRepository.findAll();
    }

    /**
     * {@code GET  /overigeadresseerbaarobjectaanduidings/:id} : get the "id" overigeadresseerbaarobjectaanduiding.
     *
     * @param id the id of the overigeadresseerbaarobjectaanduiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overigeadresseerbaarobjectaanduiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overigeadresseerbaarobjectaanduiding> getOverigeadresseerbaarobjectaanduiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Overigeadresseerbaarobjectaanduiding : {}", id);
        Optional<Overigeadresseerbaarobjectaanduiding> overigeadresseerbaarobjectaanduiding =
            overigeadresseerbaarobjectaanduidingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overigeadresseerbaarobjectaanduiding);
    }

    /**
     * {@code DELETE  /overigeadresseerbaarobjectaanduidings/:id} : delete the "id" overigeadresseerbaarobjectaanduiding.
     *
     * @param id the id of the overigeadresseerbaarobjectaanduiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverigeadresseerbaarobjectaanduiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overigeadresseerbaarobjectaanduiding : {}", id);
        overigeadresseerbaarobjectaanduidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
