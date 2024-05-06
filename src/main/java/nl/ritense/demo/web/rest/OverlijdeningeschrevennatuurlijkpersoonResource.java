package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overlijdeningeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.OverlijdeningeschrevennatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overlijdeningeschrevennatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/overlijdeningeschrevennatuurlijkpersoons")
@Transactional
public class OverlijdeningeschrevennatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(OverlijdeningeschrevennatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "overlijdeningeschrevennatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverlijdeningeschrevennatuurlijkpersoonRepository overlijdeningeschrevennatuurlijkpersoonRepository;

    public OverlijdeningeschrevennatuurlijkpersoonResource(
        OverlijdeningeschrevennatuurlijkpersoonRepository overlijdeningeschrevennatuurlijkpersoonRepository
    ) {
        this.overlijdeningeschrevennatuurlijkpersoonRepository = overlijdeningeschrevennatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /overlijdeningeschrevennatuurlijkpersoons} : Create a new overlijdeningeschrevennatuurlijkpersoon.
     *
     * @param overlijdeningeschrevennatuurlijkpersoon the overlijdeningeschrevennatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overlijdeningeschrevennatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the overlijdeningeschrevennatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overlijdeningeschrevennatuurlijkpersoon> createOverlijdeningeschrevennatuurlijkpersoon(
        @RequestBody Overlijdeningeschrevennatuurlijkpersoon overlijdeningeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Overlijdeningeschrevennatuurlijkpersoon : {}", overlijdeningeschrevennatuurlijkpersoon);
        if (overlijdeningeschrevennatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new overlijdeningeschrevennatuurlijkpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        overlijdeningeschrevennatuurlijkpersoon = overlijdeningeschrevennatuurlijkpersoonRepository.save(
            overlijdeningeschrevennatuurlijkpersoon
        );
        return ResponseEntity.created(
            new URI("/api/overlijdeningeschrevennatuurlijkpersoons/" + overlijdeningeschrevennatuurlijkpersoon.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    overlijdeningeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(overlijdeningeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PUT  /overlijdeningeschrevennatuurlijkpersoons/:id} : Updates an existing overlijdeningeschrevennatuurlijkpersoon.
     *
     * @param id the id of the overlijdeningeschrevennatuurlijkpersoon to save.
     * @param overlijdeningeschrevennatuurlijkpersoon the overlijdeningeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overlijdeningeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the overlijdeningeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overlijdeningeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overlijdeningeschrevennatuurlijkpersoon> updateOverlijdeningeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overlijdeningeschrevennatuurlijkpersoon overlijdeningeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Overlijdeningeschrevennatuurlijkpersoon : {}, {}", id, overlijdeningeschrevennatuurlijkpersoon);
        if (overlijdeningeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overlijdeningeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overlijdeningeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overlijdeningeschrevennatuurlijkpersoon = overlijdeningeschrevennatuurlijkpersoonRepository.save(
            overlijdeningeschrevennatuurlijkpersoon
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    overlijdeningeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(overlijdeningeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /overlijdeningeschrevennatuurlijkpersoons/:id} : Partial updates given fields of an existing overlijdeningeschrevennatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the overlijdeningeschrevennatuurlijkpersoon to save.
     * @param overlijdeningeschrevennatuurlijkpersoon the overlijdeningeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overlijdeningeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the overlijdeningeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the overlijdeningeschrevennatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the overlijdeningeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overlijdeningeschrevennatuurlijkpersoon> partialUpdateOverlijdeningeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overlijdeningeschrevennatuurlijkpersoon overlijdeningeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Overlijdeningeschrevennatuurlijkpersoon partially : {}, {}",
            id,
            overlijdeningeschrevennatuurlijkpersoon
        );
        if (overlijdeningeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overlijdeningeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overlijdeningeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overlijdeningeschrevennatuurlijkpersoon> result = overlijdeningeschrevennatuurlijkpersoonRepository
            .findById(overlijdeningeschrevennatuurlijkpersoon.getId())
            .map(existingOverlijdeningeschrevennatuurlijkpersoon -> {
                if (overlijdeningeschrevennatuurlijkpersoon.getBuitenlandseplaatsoverlijden() != null) {
                    existingOverlijdeningeschrevennatuurlijkpersoon.setBuitenlandseplaatsoverlijden(
                        overlijdeningeschrevennatuurlijkpersoon.getBuitenlandseplaatsoverlijden()
                    );
                }
                if (overlijdeningeschrevennatuurlijkpersoon.getBuitenlandseregiooverlijden() != null) {
                    existingOverlijdeningeschrevennatuurlijkpersoon.setBuitenlandseregiooverlijden(
                        overlijdeningeschrevennatuurlijkpersoon.getBuitenlandseregiooverlijden()
                    );
                }
                if (overlijdeningeschrevennatuurlijkpersoon.getDatumoverlijden() != null) {
                    existingOverlijdeningeschrevennatuurlijkpersoon.setDatumoverlijden(
                        overlijdeningeschrevennatuurlijkpersoon.getDatumoverlijden()
                    );
                }
                if (overlijdeningeschrevennatuurlijkpersoon.getGemeenteoverlijden() != null) {
                    existingOverlijdeningeschrevennatuurlijkpersoon.setGemeenteoverlijden(
                        overlijdeningeschrevennatuurlijkpersoon.getGemeenteoverlijden()
                    );
                }
                if (overlijdeningeschrevennatuurlijkpersoon.getLandofgebiedoverlijden() != null) {
                    existingOverlijdeningeschrevennatuurlijkpersoon.setLandofgebiedoverlijden(
                        overlijdeningeschrevennatuurlijkpersoon.getLandofgebiedoverlijden()
                    );
                }
                if (overlijdeningeschrevennatuurlijkpersoon.getOmschrijvinglocatieoverlijden() != null) {
                    existingOverlijdeningeschrevennatuurlijkpersoon.setOmschrijvinglocatieoverlijden(
                        overlijdeningeschrevennatuurlijkpersoon.getOmschrijvinglocatieoverlijden()
                    );
                }

                return existingOverlijdeningeschrevennatuurlijkpersoon;
            })
            .map(overlijdeningeschrevennatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                overlijdeningeschrevennatuurlijkpersoon.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /overlijdeningeschrevennatuurlijkpersoons} : get all the overlijdeningeschrevennatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overlijdeningeschrevennatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Overlijdeningeschrevennatuurlijkpersoon> getAllOverlijdeningeschrevennatuurlijkpersoons() {
        log.debug("REST request to get all Overlijdeningeschrevennatuurlijkpersoons");
        return overlijdeningeschrevennatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /overlijdeningeschrevennatuurlijkpersoons/:id} : get the "id" overlijdeningeschrevennatuurlijkpersoon.
     *
     * @param id the id of the overlijdeningeschrevennatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overlijdeningeschrevennatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overlijdeningeschrevennatuurlijkpersoon> getOverlijdeningeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Overlijdeningeschrevennatuurlijkpersoon : {}", id);
        Optional<Overlijdeningeschrevennatuurlijkpersoon> overlijdeningeschrevennatuurlijkpersoon =
            overlijdeningeschrevennatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overlijdeningeschrevennatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /overlijdeningeschrevennatuurlijkpersoons/:id} : delete the "id" overlijdeningeschrevennatuurlijkpersoon.
     *
     * @param id the id of the overlijdeningeschrevennatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverlijdeningeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overlijdeningeschrevennatuurlijkpersoon : {}", id);
        overlijdeningeschrevennatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
