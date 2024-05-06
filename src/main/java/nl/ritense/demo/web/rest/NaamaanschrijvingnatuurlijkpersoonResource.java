package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Naamaanschrijvingnatuurlijkpersoon;
import nl.ritense.demo.repository.NaamaanschrijvingnatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Naamaanschrijvingnatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/naamaanschrijvingnatuurlijkpersoons")
@Transactional
public class NaamaanschrijvingnatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(NaamaanschrijvingnatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "naamaanschrijvingnatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaamaanschrijvingnatuurlijkpersoonRepository naamaanschrijvingnatuurlijkpersoonRepository;

    public NaamaanschrijvingnatuurlijkpersoonResource(
        NaamaanschrijvingnatuurlijkpersoonRepository naamaanschrijvingnatuurlijkpersoonRepository
    ) {
        this.naamaanschrijvingnatuurlijkpersoonRepository = naamaanschrijvingnatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /naamaanschrijvingnatuurlijkpersoons} : Create a new naamaanschrijvingnatuurlijkpersoon.
     *
     * @param naamaanschrijvingnatuurlijkpersoon the naamaanschrijvingnatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naamaanschrijvingnatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the naamaanschrijvingnatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Naamaanschrijvingnatuurlijkpersoon> createNaamaanschrijvingnatuurlijkpersoon(
        @Valid @RequestBody Naamaanschrijvingnatuurlijkpersoon naamaanschrijvingnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Naamaanschrijvingnatuurlijkpersoon : {}", naamaanschrijvingnatuurlijkpersoon);
        if (naamaanschrijvingnatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new naamaanschrijvingnatuurlijkpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        naamaanschrijvingnatuurlijkpersoon = naamaanschrijvingnatuurlijkpersoonRepository.save(naamaanschrijvingnatuurlijkpersoon);
        return ResponseEntity.created(new URI("/api/naamaanschrijvingnatuurlijkpersoons/" + naamaanschrijvingnatuurlijkpersoon.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    naamaanschrijvingnatuurlijkpersoon.getId().toString()
                )
            )
            .body(naamaanschrijvingnatuurlijkpersoon);
    }

    /**
     * {@code PUT  /naamaanschrijvingnatuurlijkpersoons/:id} : Updates an existing naamaanschrijvingnatuurlijkpersoon.
     *
     * @param id the id of the naamaanschrijvingnatuurlijkpersoon to save.
     * @param naamaanschrijvingnatuurlijkpersoon the naamaanschrijvingnatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naamaanschrijvingnatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the naamaanschrijvingnatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the naamaanschrijvingnatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Naamaanschrijvingnatuurlijkpersoon> updateNaamaanschrijvingnatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Naamaanschrijvingnatuurlijkpersoon naamaanschrijvingnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Naamaanschrijvingnatuurlijkpersoon : {}, {}", id, naamaanschrijvingnatuurlijkpersoon);
        if (naamaanschrijvingnatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naamaanschrijvingnatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naamaanschrijvingnatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        naamaanschrijvingnatuurlijkpersoon = naamaanschrijvingnatuurlijkpersoonRepository.save(naamaanschrijvingnatuurlijkpersoon);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    naamaanschrijvingnatuurlijkpersoon.getId().toString()
                )
            )
            .body(naamaanschrijvingnatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /naamaanschrijvingnatuurlijkpersoons/:id} : Partial updates given fields of an existing naamaanschrijvingnatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the naamaanschrijvingnatuurlijkpersoon to save.
     * @param naamaanschrijvingnatuurlijkpersoon the naamaanschrijvingnatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naamaanschrijvingnatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the naamaanschrijvingnatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the naamaanschrijvingnatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the naamaanschrijvingnatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Naamaanschrijvingnatuurlijkpersoon> partialUpdateNaamaanschrijvingnatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Naamaanschrijvingnatuurlijkpersoon naamaanschrijvingnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Naamaanschrijvingnatuurlijkpersoon partially : {}, {}",
            id,
            naamaanschrijvingnatuurlijkpersoon
        );
        if (naamaanschrijvingnatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naamaanschrijvingnatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naamaanschrijvingnatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Naamaanschrijvingnatuurlijkpersoon> result = naamaanschrijvingnatuurlijkpersoonRepository
            .findById(naamaanschrijvingnatuurlijkpersoon.getId())
            .map(existingNaamaanschrijvingnatuurlijkpersoon -> {
                if (naamaanschrijvingnatuurlijkpersoon.getAanhefaanschrijving() != null) {
                    existingNaamaanschrijvingnatuurlijkpersoon.setAanhefaanschrijving(
                        naamaanschrijvingnatuurlijkpersoon.getAanhefaanschrijving()
                    );
                }
                if (naamaanschrijvingnatuurlijkpersoon.getGeslachtsnaamaanschrijving() != null) {
                    existingNaamaanschrijvingnatuurlijkpersoon.setGeslachtsnaamaanschrijving(
                        naamaanschrijvingnatuurlijkpersoon.getGeslachtsnaamaanschrijving()
                    );
                }
                if (naamaanschrijvingnatuurlijkpersoon.getVoorlettersaanschrijving() != null) {
                    existingNaamaanschrijvingnatuurlijkpersoon.setVoorlettersaanschrijving(
                        naamaanschrijvingnatuurlijkpersoon.getVoorlettersaanschrijving()
                    );
                }
                if (naamaanschrijvingnatuurlijkpersoon.getVoornamenaanschrijving() != null) {
                    existingNaamaanschrijvingnatuurlijkpersoon.setVoornamenaanschrijving(
                        naamaanschrijvingnatuurlijkpersoon.getVoornamenaanschrijving()
                    );
                }

                return existingNaamaanschrijvingnatuurlijkpersoon;
            })
            .map(naamaanschrijvingnatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naamaanschrijvingnatuurlijkpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /naamaanschrijvingnatuurlijkpersoons} : get all the naamaanschrijvingnatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naamaanschrijvingnatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Naamaanschrijvingnatuurlijkpersoon> getAllNaamaanschrijvingnatuurlijkpersoons() {
        log.debug("REST request to get all Naamaanschrijvingnatuurlijkpersoons");
        return naamaanschrijvingnatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /naamaanschrijvingnatuurlijkpersoons/:id} : get the "id" naamaanschrijvingnatuurlijkpersoon.
     *
     * @param id the id of the naamaanschrijvingnatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naamaanschrijvingnatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Naamaanschrijvingnatuurlijkpersoon> getNaamaanschrijvingnatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Naamaanschrijvingnatuurlijkpersoon : {}", id);
        Optional<Naamaanschrijvingnatuurlijkpersoon> naamaanschrijvingnatuurlijkpersoon =
            naamaanschrijvingnatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(naamaanschrijvingnatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /naamaanschrijvingnatuurlijkpersoons/:id} : delete the "id" naamaanschrijvingnatuurlijkpersoon.
     *
     * @param id the id of the naamaanschrijvingnatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNaamaanschrijvingnatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Naamaanschrijvingnatuurlijkpersoon : {}", id);
        naamaanschrijvingnatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
