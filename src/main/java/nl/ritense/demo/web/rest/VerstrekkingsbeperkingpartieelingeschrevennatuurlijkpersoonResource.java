package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons")
@Transactional
public class VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository;

    public VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonResource(
        VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository
    ) {
        this.verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository =
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons} : Create a new verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.
     *
     * @param verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    > createVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(
        @RequestBody Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to save Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon : {}",
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );
        if (verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon =
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.save(
                verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            );
        return ResponseEntity.created(
            new URI(
                "/api/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons/" +
                verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId()
            )
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PUT  /verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons/:id} : Updates an existing verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon to save.
     * @param verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    > updateVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to update Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon : {}, {}",
            id,
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );
        if (verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon =
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.save(
                verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
            );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons/:id} : Partial updates given fields of an existing verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon to save.
     * @param verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    > partialUpdateVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon partially : {}, {}",
            id,
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
        );
        if (verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon> result =
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository
                .findById(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId())
                .map(existingVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon -> {
                    if (verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getGemeenteverordening() != null) {
                        existingVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setGemeenteverordening(
                            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getGemeenteverordening()
                        );
                    }
                    if (verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getOmschrijvingderde() != null) {
                        existingVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setOmschrijvingderde(
                            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getOmschrijvingderde()
                        );
                    }
                    if (verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getPartij() != null) {
                        existingVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.setPartij(
                            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getPartij()
                        );
                    }

                    return existingVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon;
                })
                .map(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons} : get all the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    > getAllVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons() {
        log.debug("REST request to get all Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons");
        return verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons/:id} : get the "id" verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<
        Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon
    > getVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon : {}", id);
        Optional<Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon> verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon =
            verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons/:id} : delete the "id" verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon : {}", id);
        verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
