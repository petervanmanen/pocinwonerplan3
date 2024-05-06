package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Nederlandsenationaliteitingeschrevenpersoon;
import nl.ritense.demo.repository.NederlandsenationaliteitingeschrevenpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Nederlandsenationaliteitingeschrevenpersoon}.
 */
@RestController
@RequestMapping("/api/nederlandsenationaliteitingeschrevenpersoons")
@Transactional
public class NederlandsenationaliteitingeschrevenpersoonResource {

    private final Logger log = LoggerFactory.getLogger(NederlandsenationaliteitingeschrevenpersoonResource.class);

    private static final String ENTITY_NAME = "nederlandsenationaliteitingeschrevenpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NederlandsenationaliteitingeschrevenpersoonRepository nederlandsenationaliteitingeschrevenpersoonRepository;

    public NederlandsenationaliteitingeschrevenpersoonResource(
        NederlandsenationaliteitingeschrevenpersoonRepository nederlandsenationaliteitingeschrevenpersoonRepository
    ) {
        this.nederlandsenationaliteitingeschrevenpersoonRepository = nederlandsenationaliteitingeschrevenpersoonRepository;
    }

    /**
     * {@code POST  /nederlandsenationaliteitingeschrevenpersoons} : Create a new nederlandsenationaliteitingeschrevenpersoon.
     *
     * @param nederlandsenationaliteitingeschrevenpersoon the nederlandsenationaliteitingeschrevenpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nederlandsenationaliteitingeschrevenpersoon, or with status {@code 400 (Bad Request)} if the nederlandsenationaliteitingeschrevenpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Nederlandsenationaliteitingeschrevenpersoon> createNederlandsenationaliteitingeschrevenpersoon(
        @RequestBody Nederlandsenationaliteitingeschrevenpersoon nederlandsenationaliteitingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Nederlandsenationaliteitingeschrevenpersoon : {}", nederlandsenationaliteitingeschrevenpersoon);
        if (nederlandsenationaliteitingeschrevenpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new nederlandsenationaliteitingeschrevenpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        nederlandsenationaliteitingeschrevenpersoon = nederlandsenationaliteitingeschrevenpersoonRepository.save(
            nederlandsenationaliteitingeschrevenpersoon
        );
        return ResponseEntity.created(
            new URI("/api/nederlandsenationaliteitingeschrevenpersoons/" + nederlandsenationaliteitingeschrevenpersoon.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    nederlandsenationaliteitingeschrevenpersoon.getId().toString()
                )
            )
            .body(nederlandsenationaliteitingeschrevenpersoon);
    }

    /**
     * {@code PUT  /nederlandsenationaliteitingeschrevenpersoons/:id} : Updates an existing nederlandsenationaliteitingeschrevenpersoon.
     *
     * @param id the id of the nederlandsenationaliteitingeschrevenpersoon to save.
     * @param nederlandsenationaliteitingeschrevenpersoon the nederlandsenationaliteitingeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nederlandsenationaliteitingeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the nederlandsenationaliteitingeschrevenpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nederlandsenationaliteitingeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nederlandsenationaliteitingeschrevenpersoon> updateNederlandsenationaliteitingeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nederlandsenationaliteitingeschrevenpersoon nederlandsenationaliteitingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to update Nederlandsenationaliteitingeschrevenpersoon : {}, {}",
            id,
            nederlandsenationaliteitingeschrevenpersoon
        );
        if (nederlandsenationaliteitingeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nederlandsenationaliteitingeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nederlandsenationaliteitingeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nederlandsenationaliteitingeschrevenpersoon = nederlandsenationaliteitingeschrevenpersoonRepository.save(
            nederlandsenationaliteitingeschrevenpersoon
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    nederlandsenationaliteitingeschrevenpersoon.getId().toString()
                )
            )
            .body(nederlandsenationaliteitingeschrevenpersoon);
    }

    /**
     * {@code PATCH  /nederlandsenationaliteitingeschrevenpersoons/:id} : Partial updates given fields of an existing nederlandsenationaliteitingeschrevenpersoon, field will ignore if it is null
     *
     * @param id the id of the nederlandsenationaliteitingeschrevenpersoon to save.
     * @param nederlandsenationaliteitingeschrevenpersoon the nederlandsenationaliteitingeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nederlandsenationaliteitingeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the nederlandsenationaliteitingeschrevenpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the nederlandsenationaliteitingeschrevenpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the nederlandsenationaliteitingeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nederlandsenationaliteitingeschrevenpersoon> partialUpdateNederlandsenationaliteitingeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nederlandsenationaliteitingeschrevenpersoon nederlandsenationaliteitingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Nederlandsenationaliteitingeschrevenpersoon partially : {}, {}",
            id,
            nederlandsenationaliteitingeschrevenpersoon
        );
        if (nederlandsenationaliteitingeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nederlandsenationaliteitingeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nederlandsenationaliteitingeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nederlandsenationaliteitingeschrevenpersoon> result = nederlandsenationaliteitingeschrevenpersoonRepository
            .findById(nederlandsenationaliteitingeschrevenpersoon.getId())
            .map(existingNederlandsenationaliteitingeschrevenpersoon -> {
                if (nederlandsenationaliteitingeschrevenpersoon.getAanduidingbijzondernederlanderschap() != null) {
                    existingNederlandsenationaliteitingeschrevenpersoon.setAanduidingbijzondernederlanderschap(
                        nederlandsenationaliteitingeschrevenpersoon.getAanduidingbijzondernederlanderschap()
                    );
                }
                if (nederlandsenationaliteitingeschrevenpersoon.getNationaliteit() != null) {
                    existingNederlandsenationaliteitingeschrevenpersoon.setNationaliteit(
                        nederlandsenationaliteitingeschrevenpersoon.getNationaliteit()
                    );
                }
                if (nederlandsenationaliteitingeschrevenpersoon.getRedenverkrijgingnederlandsenationaliteit() != null) {
                    existingNederlandsenationaliteitingeschrevenpersoon.setRedenverkrijgingnederlandsenationaliteit(
                        nederlandsenationaliteitingeschrevenpersoon.getRedenverkrijgingnederlandsenationaliteit()
                    );
                }
                if (nederlandsenationaliteitingeschrevenpersoon.getRedenverliesnederlandsenationaliteit() != null) {
                    existingNederlandsenationaliteitingeschrevenpersoon.setRedenverliesnederlandsenationaliteit(
                        nederlandsenationaliteitingeschrevenpersoon.getRedenverliesnederlandsenationaliteit()
                    );
                }

                return existingNederlandsenationaliteitingeschrevenpersoon;
            })
            .map(nederlandsenationaliteitingeschrevenpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                nederlandsenationaliteitingeschrevenpersoon.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /nederlandsenationaliteitingeschrevenpersoons} : get all the nederlandsenationaliteitingeschrevenpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nederlandsenationaliteitingeschrevenpersoons in body.
     */
    @GetMapping("")
    public List<Nederlandsenationaliteitingeschrevenpersoon> getAllNederlandsenationaliteitingeschrevenpersoons() {
        log.debug("REST request to get all Nederlandsenationaliteitingeschrevenpersoons");
        return nederlandsenationaliteitingeschrevenpersoonRepository.findAll();
    }

    /**
     * {@code GET  /nederlandsenationaliteitingeschrevenpersoons/:id} : get the "id" nederlandsenationaliteitingeschrevenpersoon.
     *
     * @param id the id of the nederlandsenationaliteitingeschrevenpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nederlandsenationaliteitingeschrevenpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nederlandsenationaliteitingeschrevenpersoon> getNederlandsenationaliteitingeschrevenpersoon(
        @PathVariable("id") Long id
    ) {
        log.debug("REST request to get Nederlandsenationaliteitingeschrevenpersoon : {}", id);
        Optional<Nederlandsenationaliteitingeschrevenpersoon> nederlandsenationaliteitingeschrevenpersoon =
            nederlandsenationaliteitingeschrevenpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nederlandsenationaliteitingeschrevenpersoon);
    }

    /**
     * {@code DELETE  /nederlandsenationaliteitingeschrevenpersoons/:id} : delete the "id" nederlandsenationaliteitingeschrevenpersoon.
     *
     * @param id the id of the nederlandsenationaliteitingeschrevenpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNederlandsenationaliteitingeschrevenpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Nederlandsenationaliteitingeschrevenpersoon : {}", id);
        nederlandsenationaliteitingeschrevenpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
