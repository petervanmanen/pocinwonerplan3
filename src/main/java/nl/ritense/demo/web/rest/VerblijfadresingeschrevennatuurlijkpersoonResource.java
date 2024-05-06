package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfadresingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.VerblijfadresingeschrevennatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verblijfadresingeschrevennatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/verblijfadresingeschrevennatuurlijkpersoons")
@Transactional
public class VerblijfadresingeschrevennatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(VerblijfadresingeschrevennatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "verblijfadresingeschrevennatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerblijfadresingeschrevennatuurlijkpersoonRepository verblijfadresingeschrevennatuurlijkpersoonRepository;

    public VerblijfadresingeschrevennatuurlijkpersoonResource(
        VerblijfadresingeschrevennatuurlijkpersoonRepository verblijfadresingeschrevennatuurlijkpersoonRepository
    ) {
        this.verblijfadresingeschrevennatuurlijkpersoonRepository = verblijfadresingeschrevennatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /verblijfadresingeschrevennatuurlijkpersoons} : Create a new verblijfadresingeschrevennatuurlijkpersoon.
     *
     * @param verblijfadresingeschrevennatuurlijkpersoon the verblijfadresingeschrevennatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verblijfadresingeschrevennatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the verblijfadresingeschrevennatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verblijfadresingeschrevennatuurlijkpersoon> createVerblijfadresingeschrevennatuurlijkpersoon(
        @RequestBody Verblijfadresingeschrevennatuurlijkpersoon verblijfadresingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Verblijfadresingeschrevennatuurlijkpersoon : {}", verblijfadresingeschrevennatuurlijkpersoon);
        if (verblijfadresingeschrevennatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new verblijfadresingeschrevennatuurlijkpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        verblijfadresingeschrevennatuurlijkpersoon = verblijfadresingeschrevennatuurlijkpersoonRepository.save(
            verblijfadresingeschrevennatuurlijkpersoon
        );
        return ResponseEntity.created(
            new URI("/api/verblijfadresingeschrevennatuurlijkpersoons/" + verblijfadresingeschrevennatuurlijkpersoon.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    verblijfadresingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(verblijfadresingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PUT  /verblijfadresingeschrevennatuurlijkpersoons/:id} : Updates an existing verblijfadresingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the verblijfadresingeschrevennatuurlijkpersoon to save.
     * @param verblijfadresingeschrevennatuurlijkpersoon the verblijfadresingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfadresingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the verblijfadresingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verblijfadresingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verblijfadresingeschrevennatuurlijkpersoon> updateVerblijfadresingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfadresingeschrevennatuurlijkpersoon verblijfadresingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to update Verblijfadresingeschrevennatuurlijkpersoon : {}, {}",
            id,
            verblijfadresingeschrevennatuurlijkpersoon
        );
        if (verblijfadresingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfadresingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfadresingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verblijfadresingeschrevennatuurlijkpersoon = verblijfadresingeschrevennatuurlijkpersoonRepository.save(
            verblijfadresingeschrevennatuurlijkpersoon
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    verblijfadresingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(verblijfadresingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /verblijfadresingeschrevennatuurlijkpersoons/:id} : Partial updates given fields of an existing verblijfadresingeschrevennatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the verblijfadresingeschrevennatuurlijkpersoon to save.
     * @param verblijfadresingeschrevennatuurlijkpersoon the verblijfadresingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfadresingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the verblijfadresingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the verblijfadresingeschrevennatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the verblijfadresingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verblijfadresingeschrevennatuurlijkpersoon> partialUpdateVerblijfadresingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfadresingeschrevennatuurlijkpersoon verblijfadresingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Verblijfadresingeschrevennatuurlijkpersoon partially : {}, {}",
            id,
            verblijfadresingeschrevennatuurlijkpersoon
        );
        if (verblijfadresingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfadresingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfadresingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verblijfadresingeschrevennatuurlijkpersoon> result = verblijfadresingeschrevennatuurlijkpersoonRepository
            .findById(verblijfadresingeschrevennatuurlijkpersoon.getId())
            .map(existingVerblijfadresingeschrevennatuurlijkpersoon -> {
                if (verblijfadresingeschrevennatuurlijkpersoon.getAdresherkomst() != null) {
                    existingVerblijfadresingeschrevennatuurlijkpersoon.setAdresherkomst(
                        verblijfadresingeschrevennatuurlijkpersoon.getAdresherkomst()
                    );
                }
                if (verblijfadresingeschrevennatuurlijkpersoon.getBeschrijvinglocatie() != null) {
                    existingVerblijfadresingeschrevennatuurlijkpersoon.setBeschrijvinglocatie(
                        verblijfadresingeschrevennatuurlijkpersoon.getBeschrijvinglocatie()
                    );
                }

                return existingVerblijfadresingeschrevennatuurlijkpersoon;
            })
            .map(verblijfadresingeschrevennatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                verblijfadresingeschrevennatuurlijkpersoon.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /verblijfadresingeschrevennatuurlijkpersoons} : get all the verblijfadresingeschrevennatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verblijfadresingeschrevennatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Verblijfadresingeschrevennatuurlijkpersoon> getAllVerblijfadresingeschrevennatuurlijkpersoons() {
        log.debug("REST request to get all Verblijfadresingeschrevennatuurlijkpersoons");
        return verblijfadresingeschrevennatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /verblijfadresingeschrevennatuurlijkpersoons/:id} : get the "id" verblijfadresingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the verblijfadresingeschrevennatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verblijfadresingeschrevennatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verblijfadresingeschrevennatuurlijkpersoon> getVerblijfadresingeschrevennatuurlijkpersoon(
        @PathVariable("id") Long id
    ) {
        log.debug("REST request to get Verblijfadresingeschrevennatuurlijkpersoon : {}", id);
        Optional<Verblijfadresingeschrevennatuurlijkpersoon> verblijfadresingeschrevennatuurlijkpersoon =
            verblijfadresingeschrevennatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verblijfadresingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /verblijfadresingeschrevennatuurlijkpersoons/:id} : delete the "id" verblijfadresingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the verblijfadresingeschrevennatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerblijfadresingeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verblijfadresingeschrevennatuurlijkpersoon : {}", id);
        verblijfadresingeschrevennatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
