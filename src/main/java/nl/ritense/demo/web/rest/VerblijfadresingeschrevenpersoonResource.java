package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfadresingeschrevenpersoon;
import nl.ritense.demo.repository.VerblijfadresingeschrevenpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verblijfadresingeschrevenpersoon}.
 */
@RestController
@RequestMapping("/api/verblijfadresingeschrevenpersoons")
@Transactional
public class VerblijfadresingeschrevenpersoonResource {

    private final Logger log = LoggerFactory.getLogger(VerblijfadresingeschrevenpersoonResource.class);

    private static final String ENTITY_NAME = "verblijfadresingeschrevenpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerblijfadresingeschrevenpersoonRepository verblijfadresingeschrevenpersoonRepository;

    public VerblijfadresingeschrevenpersoonResource(VerblijfadresingeschrevenpersoonRepository verblijfadresingeschrevenpersoonRepository) {
        this.verblijfadresingeschrevenpersoonRepository = verblijfadresingeschrevenpersoonRepository;
    }

    /**
     * {@code POST  /verblijfadresingeschrevenpersoons} : Create a new verblijfadresingeschrevenpersoon.
     *
     * @param verblijfadresingeschrevenpersoon the verblijfadresingeschrevenpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verblijfadresingeschrevenpersoon, or with status {@code 400 (Bad Request)} if the verblijfadresingeschrevenpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verblijfadresingeschrevenpersoon> createVerblijfadresingeschrevenpersoon(
        @RequestBody Verblijfadresingeschrevenpersoon verblijfadresingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Verblijfadresingeschrevenpersoon : {}", verblijfadresingeschrevenpersoon);
        if (verblijfadresingeschrevenpersoon.getId() != null) {
            throw new BadRequestAlertException("A new verblijfadresingeschrevenpersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verblijfadresingeschrevenpersoon = verblijfadresingeschrevenpersoonRepository.save(verblijfadresingeschrevenpersoon);
        return ResponseEntity.created(new URI("/api/verblijfadresingeschrevenpersoons/" + verblijfadresingeschrevenpersoon.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    verblijfadresingeschrevenpersoon.getId().toString()
                )
            )
            .body(verblijfadresingeschrevenpersoon);
    }

    /**
     * {@code PUT  /verblijfadresingeschrevenpersoons/:id} : Updates an existing verblijfadresingeschrevenpersoon.
     *
     * @param id the id of the verblijfadresingeschrevenpersoon to save.
     * @param verblijfadresingeschrevenpersoon the verblijfadresingeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfadresingeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the verblijfadresingeschrevenpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verblijfadresingeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verblijfadresingeschrevenpersoon> updateVerblijfadresingeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfadresingeschrevenpersoon verblijfadresingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Verblijfadresingeschrevenpersoon : {}, {}", id, verblijfadresingeschrevenpersoon);
        if (verblijfadresingeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfadresingeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfadresingeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verblijfadresingeschrevenpersoon = verblijfadresingeschrevenpersoonRepository.save(verblijfadresingeschrevenpersoon);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfadresingeschrevenpersoon.getId().toString())
            )
            .body(verblijfadresingeschrevenpersoon);
    }

    /**
     * {@code PATCH  /verblijfadresingeschrevenpersoons/:id} : Partial updates given fields of an existing verblijfadresingeschrevenpersoon, field will ignore if it is null
     *
     * @param id the id of the verblijfadresingeschrevenpersoon to save.
     * @param verblijfadresingeschrevenpersoon the verblijfadresingeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfadresingeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the verblijfadresingeschrevenpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the verblijfadresingeschrevenpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the verblijfadresingeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verblijfadresingeschrevenpersoon> partialUpdateVerblijfadresingeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfadresingeschrevenpersoon verblijfadresingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Verblijfadresingeschrevenpersoon partially : {}, {}",
            id,
            verblijfadresingeschrevenpersoon
        );
        if (verblijfadresingeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfadresingeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfadresingeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verblijfadresingeschrevenpersoon> result = verblijfadresingeschrevenpersoonRepository
            .findById(verblijfadresingeschrevenpersoon.getId())
            .map(existingVerblijfadresingeschrevenpersoon -> {
                if (verblijfadresingeschrevenpersoon.getAdresherkomst() != null) {
                    existingVerblijfadresingeschrevenpersoon.setAdresherkomst(verblijfadresingeschrevenpersoon.getAdresherkomst());
                }
                if (verblijfadresingeschrevenpersoon.getBeschrijvinglocatie() != null) {
                    existingVerblijfadresingeschrevenpersoon.setBeschrijvinglocatie(
                        verblijfadresingeschrevenpersoon.getBeschrijvinglocatie()
                    );
                }

                return existingVerblijfadresingeschrevenpersoon;
            })
            .map(verblijfadresingeschrevenpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfadresingeschrevenpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /verblijfadresingeschrevenpersoons} : get all the verblijfadresingeschrevenpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verblijfadresingeschrevenpersoons in body.
     */
    @GetMapping("")
    public List<Verblijfadresingeschrevenpersoon> getAllVerblijfadresingeschrevenpersoons() {
        log.debug("REST request to get all Verblijfadresingeschrevenpersoons");
        return verblijfadresingeschrevenpersoonRepository.findAll();
    }

    /**
     * {@code GET  /verblijfadresingeschrevenpersoons/:id} : get the "id" verblijfadresingeschrevenpersoon.
     *
     * @param id the id of the verblijfadresingeschrevenpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verblijfadresingeschrevenpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verblijfadresingeschrevenpersoon> getVerblijfadresingeschrevenpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Verblijfadresingeschrevenpersoon : {}", id);
        Optional<Verblijfadresingeschrevenpersoon> verblijfadresingeschrevenpersoon = verblijfadresingeschrevenpersoonRepository.findById(
            id
        );
        return ResponseUtil.wrapOrNotFound(verblijfadresingeschrevenpersoon);
    }

    /**
     * {@code DELETE  /verblijfadresingeschrevenpersoons/:id} : delete the "id" verblijfadresingeschrevenpersoon.
     *
     * @param id the id of the verblijfadresingeschrevenpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerblijfadresingeschrevenpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verblijfadresingeschrevenpersoon : {}", id);
        verblijfadresingeschrevenpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
