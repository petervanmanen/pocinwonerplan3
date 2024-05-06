package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Nationaliteitingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.NationaliteitingeschrevennatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Nationaliteitingeschrevennatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/nationaliteitingeschrevennatuurlijkpersoons")
@Transactional
public class NationaliteitingeschrevennatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(NationaliteitingeschrevennatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "nationaliteitingeschrevennatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NationaliteitingeschrevennatuurlijkpersoonRepository nationaliteitingeschrevennatuurlijkpersoonRepository;

    public NationaliteitingeschrevennatuurlijkpersoonResource(
        NationaliteitingeschrevennatuurlijkpersoonRepository nationaliteitingeschrevennatuurlijkpersoonRepository
    ) {
        this.nationaliteitingeschrevennatuurlijkpersoonRepository = nationaliteitingeschrevennatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /nationaliteitingeschrevennatuurlijkpersoons} : Create a new nationaliteitingeschrevennatuurlijkpersoon.
     *
     * @param nationaliteitingeschrevennatuurlijkpersoon the nationaliteitingeschrevennatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nationaliteitingeschrevennatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the nationaliteitingeschrevennatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Nationaliteitingeschrevennatuurlijkpersoon> createNationaliteitingeschrevennatuurlijkpersoon(
        @RequestBody Nationaliteitingeschrevennatuurlijkpersoon nationaliteitingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Nationaliteitingeschrevennatuurlijkpersoon : {}", nationaliteitingeschrevennatuurlijkpersoon);
        if (nationaliteitingeschrevennatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new nationaliteitingeschrevennatuurlijkpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        nationaliteitingeschrevennatuurlijkpersoon = nationaliteitingeschrevennatuurlijkpersoonRepository.save(
            nationaliteitingeschrevennatuurlijkpersoon
        );
        return ResponseEntity.created(
            new URI("/api/nationaliteitingeschrevennatuurlijkpersoons/" + nationaliteitingeschrevennatuurlijkpersoon.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    nationaliteitingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(nationaliteitingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PUT  /nationaliteitingeschrevennatuurlijkpersoons/:id} : Updates an existing nationaliteitingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the nationaliteitingeschrevennatuurlijkpersoon to save.
     * @param nationaliteitingeschrevennatuurlijkpersoon the nationaliteitingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationaliteitingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the nationaliteitingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nationaliteitingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nationaliteitingeschrevennatuurlijkpersoon> updateNationaliteitingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nationaliteitingeschrevennatuurlijkpersoon nationaliteitingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to update Nationaliteitingeschrevennatuurlijkpersoon : {}, {}",
            id,
            nationaliteitingeschrevennatuurlijkpersoon
        );
        if (nationaliteitingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nationaliteitingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nationaliteitingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nationaliteitingeschrevennatuurlijkpersoon = nationaliteitingeschrevennatuurlijkpersoonRepository.save(
            nationaliteitingeschrevennatuurlijkpersoon
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    nationaliteitingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(nationaliteitingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /nationaliteitingeschrevennatuurlijkpersoons/:id} : Partial updates given fields of an existing nationaliteitingeschrevennatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the nationaliteitingeschrevennatuurlijkpersoon to save.
     * @param nationaliteitingeschrevennatuurlijkpersoon the nationaliteitingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationaliteitingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the nationaliteitingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the nationaliteitingeschrevennatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the nationaliteitingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nationaliteitingeschrevennatuurlijkpersoon> partialUpdateNationaliteitingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nationaliteitingeschrevennatuurlijkpersoon nationaliteitingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Nationaliteitingeschrevennatuurlijkpersoon partially : {}, {}",
            id,
            nationaliteitingeschrevennatuurlijkpersoon
        );
        if (nationaliteitingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nationaliteitingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nationaliteitingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nationaliteitingeschrevennatuurlijkpersoon> result = nationaliteitingeschrevennatuurlijkpersoonRepository
            .findById(nationaliteitingeschrevennatuurlijkpersoon.getId())
            .map(existingNationaliteitingeschrevennatuurlijkpersoon -> {
                if (nationaliteitingeschrevennatuurlijkpersoon.getBuitenlandspersoonsnummer() != null) {
                    existingNationaliteitingeschrevennatuurlijkpersoon.setBuitenlandspersoonsnummer(
                        nationaliteitingeschrevennatuurlijkpersoon.getBuitenlandspersoonsnummer()
                    );
                }
                if (nationaliteitingeschrevennatuurlijkpersoon.getNationaliteit() != null) {
                    existingNationaliteitingeschrevennatuurlijkpersoon.setNationaliteit(
                        nationaliteitingeschrevennatuurlijkpersoon.getNationaliteit()
                    );
                }
                if (nationaliteitingeschrevennatuurlijkpersoon.getRedenverkrijging() != null) {
                    existingNationaliteitingeschrevennatuurlijkpersoon.setRedenverkrijging(
                        nationaliteitingeschrevennatuurlijkpersoon.getRedenverkrijging()
                    );
                }
                if (nationaliteitingeschrevennatuurlijkpersoon.getRedenverlies() != null) {
                    existingNationaliteitingeschrevennatuurlijkpersoon.setRedenverlies(
                        nationaliteitingeschrevennatuurlijkpersoon.getRedenverlies()
                    );
                }

                return existingNationaliteitingeschrevennatuurlijkpersoon;
            })
            .map(nationaliteitingeschrevennatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                nationaliteitingeschrevennatuurlijkpersoon.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /nationaliteitingeschrevennatuurlijkpersoons} : get all the nationaliteitingeschrevennatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nationaliteitingeschrevennatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Nationaliteitingeschrevennatuurlijkpersoon> getAllNationaliteitingeschrevennatuurlijkpersoons() {
        log.debug("REST request to get all Nationaliteitingeschrevennatuurlijkpersoons");
        return nationaliteitingeschrevennatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /nationaliteitingeschrevennatuurlijkpersoons/:id} : get the "id" nationaliteitingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the nationaliteitingeschrevennatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nationaliteitingeschrevennatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nationaliteitingeschrevennatuurlijkpersoon> getNationaliteitingeschrevennatuurlijkpersoon(
        @PathVariable("id") Long id
    ) {
        log.debug("REST request to get Nationaliteitingeschrevennatuurlijkpersoon : {}", id);
        Optional<Nationaliteitingeschrevennatuurlijkpersoon> nationaliteitingeschrevennatuurlijkpersoon =
            nationaliteitingeschrevennatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nationaliteitingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /nationaliteitingeschrevennatuurlijkpersoons/:id} : delete the "id" nationaliteitingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the nationaliteitingeschrevennatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNationaliteitingeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Nationaliteitingeschrevennatuurlijkpersoon : {}", id);
        nationaliteitingeschrevennatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
