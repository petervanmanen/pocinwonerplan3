package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfsrechtingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.VerblijfsrechtingeschrevennatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verblijfsrechtingeschrevennatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/verblijfsrechtingeschrevennatuurlijkpersoons")
@Transactional
public class VerblijfsrechtingeschrevennatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(VerblijfsrechtingeschrevennatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "verblijfsrechtingeschrevennatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerblijfsrechtingeschrevennatuurlijkpersoonRepository verblijfsrechtingeschrevennatuurlijkpersoonRepository;

    public VerblijfsrechtingeschrevennatuurlijkpersoonResource(
        VerblijfsrechtingeschrevennatuurlijkpersoonRepository verblijfsrechtingeschrevennatuurlijkpersoonRepository
    ) {
        this.verblijfsrechtingeschrevennatuurlijkpersoonRepository = verblijfsrechtingeschrevennatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /verblijfsrechtingeschrevennatuurlijkpersoons} : Create a new verblijfsrechtingeschrevennatuurlijkpersoon.
     *
     * @param verblijfsrechtingeschrevennatuurlijkpersoon the verblijfsrechtingeschrevennatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verblijfsrechtingeschrevennatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the verblijfsrechtingeschrevennatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verblijfsrechtingeschrevennatuurlijkpersoon> createVerblijfsrechtingeschrevennatuurlijkpersoon(
        @RequestBody Verblijfsrechtingeschrevennatuurlijkpersoon verblijfsrechtingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Verblijfsrechtingeschrevennatuurlijkpersoon : {}", verblijfsrechtingeschrevennatuurlijkpersoon);
        if (verblijfsrechtingeschrevennatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new verblijfsrechtingeschrevennatuurlijkpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        verblijfsrechtingeschrevennatuurlijkpersoon = verblijfsrechtingeschrevennatuurlijkpersoonRepository.save(
            verblijfsrechtingeschrevennatuurlijkpersoon
        );
        return ResponseEntity.created(
            new URI("/api/verblijfsrechtingeschrevennatuurlijkpersoons/" + verblijfsrechtingeschrevennatuurlijkpersoon.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    verblijfsrechtingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(verblijfsrechtingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PUT  /verblijfsrechtingeschrevennatuurlijkpersoons/:id} : Updates an existing verblijfsrechtingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the verblijfsrechtingeschrevennatuurlijkpersoon to save.
     * @param verblijfsrechtingeschrevennatuurlijkpersoon the verblijfsrechtingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfsrechtingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the verblijfsrechtingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verblijfsrechtingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verblijfsrechtingeschrevennatuurlijkpersoon> updateVerblijfsrechtingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfsrechtingeschrevennatuurlijkpersoon verblijfsrechtingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to update Verblijfsrechtingeschrevennatuurlijkpersoon : {}, {}",
            id,
            verblijfsrechtingeschrevennatuurlijkpersoon
        );
        if (verblijfsrechtingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfsrechtingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfsrechtingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verblijfsrechtingeschrevennatuurlijkpersoon = verblijfsrechtingeschrevennatuurlijkpersoonRepository.save(
            verblijfsrechtingeschrevennatuurlijkpersoon
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    verblijfsrechtingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(verblijfsrechtingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /verblijfsrechtingeschrevennatuurlijkpersoons/:id} : Partial updates given fields of an existing verblijfsrechtingeschrevennatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the verblijfsrechtingeschrevennatuurlijkpersoon to save.
     * @param verblijfsrechtingeschrevennatuurlijkpersoon the verblijfsrechtingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfsrechtingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the verblijfsrechtingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the verblijfsrechtingeschrevennatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the verblijfsrechtingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verblijfsrechtingeschrevennatuurlijkpersoon> partialUpdateVerblijfsrechtingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfsrechtingeschrevennatuurlijkpersoon verblijfsrechtingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Verblijfsrechtingeschrevennatuurlijkpersoon partially : {}, {}",
            id,
            verblijfsrechtingeschrevennatuurlijkpersoon
        );
        if (verblijfsrechtingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfsrechtingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfsrechtingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verblijfsrechtingeschrevennatuurlijkpersoon> result = verblijfsrechtingeschrevennatuurlijkpersoonRepository
            .findById(verblijfsrechtingeschrevennatuurlijkpersoon.getId())
            .map(existingVerblijfsrechtingeschrevennatuurlijkpersoon -> {
                if (verblijfsrechtingeschrevennatuurlijkpersoon.getAanduidingverblijfsrecht() != null) {
                    existingVerblijfsrechtingeschrevennatuurlijkpersoon.setAanduidingverblijfsrecht(
                        verblijfsrechtingeschrevennatuurlijkpersoon.getAanduidingverblijfsrecht()
                    );
                }
                if (verblijfsrechtingeschrevennatuurlijkpersoon.getDatumaanvangverblijfsrecht() != null) {
                    existingVerblijfsrechtingeschrevennatuurlijkpersoon.setDatumaanvangverblijfsrecht(
                        verblijfsrechtingeschrevennatuurlijkpersoon.getDatumaanvangverblijfsrecht()
                    );
                }
                if (verblijfsrechtingeschrevennatuurlijkpersoon.getDatummededelingverblijfsrecht() != null) {
                    existingVerblijfsrechtingeschrevennatuurlijkpersoon.setDatummededelingverblijfsrecht(
                        verblijfsrechtingeschrevennatuurlijkpersoon.getDatummededelingverblijfsrecht()
                    );
                }
                if (verblijfsrechtingeschrevennatuurlijkpersoon.getDatumvoorzieneindeverblijfsrecht() != null) {
                    existingVerblijfsrechtingeschrevennatuurlijkpersoon.setDatumvoorzieneindeverblijfsrecht(
                        verblijfsrechtingeschrevennatuurlijkpersoon.getDatumvoorzieneindeverblijfsrecht()
                    );
                }

                return existingVerblijfsrechtingeschrevennatuurlijkpersoon;
            })
            .map(verblijfsrechtingeschrevennatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                verblijfsrechtingeschrevennatuurlijkpersoon.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /verblijfsrechtingeschrevennatuurlijkpersoons} : get all the verblijfsrechtingeschrevennatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verblijfsrechtingeschrevennatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Verblijfsrechtingeschrevennatuurlijkpersoon> getAllVerblijfsrechtingeschrevennatuurlijkpersoons() {
        log.debug("REST request to get all Verblijfsrechtingeschrevennatuurlijkpersoons");
        return verblijfsrechtingeschrevennatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /verblijfsrechtingeschrevennatuurlijkpersoons/:id} : get the "id" verblijfsrechtingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the verblijfsrechtingeschrevennatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verblijfsrechtingeschrevennatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verblijfsrechtingeschrevennatuurlijkpersoon> getVerblijfsrechtingeschrevennatuurlijkpersoon(
        @PathVariable("id") Long id
    ) {
        log.debug("REST request to get Verblijfsrechtingeschrevennatuurlijkpersoon : {}", id);
        Optional<Verblijfsrechtingeschrevennatuurlijkpersoon> verblijfsrechtingeschrevennatuurlijkpersoon =
            verblijfsrechtingeschrevennatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verblijfsrechtingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /verblijfsrechtingeschrevennatuurlijkpersoons/:id} : delete the "id" verblijfsrechtingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the verblijfsrechtingeschrevennatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerblijfsrechtingeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verblijfsrechtingeschrevennatuurlijkpersoon : {}", id);
        verblijfsrechtingeschrevennatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
