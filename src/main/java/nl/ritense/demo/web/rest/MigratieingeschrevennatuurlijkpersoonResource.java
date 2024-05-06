package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Migratieingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.MigratieingeschrevennatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Migratieingeschrevennatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/migratieingeschrevennatuurlijkpersoons")
@Transactional
public class MigratieingeschrevennatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(MigratieingeschrevennatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "migratieingeschrevennatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MigratieingeschrevennatuurlijkpersoonRepository migratieingeschrevennatuurlijkpersoonRepository;

    public MigratieingeschrevennatuurlijkpersoonResource(
        MigratieingeschrevennatuurlijkpersoonRepository migratieingeschrevennatuurlijkpersoonRepository
    ) {
        this.migratieingeschrevennatuurlijkpersoonRepository = migratieingeschrevennatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /migratieingeschrevennatuurlijkpersoons} : Create a new migratieingeschrevennatuurlijkpersoon.
     *
     * @param migratieingeschrevennatuurlijkpersoon the migratieingeschrevennatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new migratieingeschrevennatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the migratieingeschrevennatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Migratieingeschrevennatuurlijkpersoon> createMigratieingeschrevennatuurlijkpersoon(
        @RequestBody Migratieingeschrevennatuurlijkpersoon migratieingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Migratieingeschrevennatuurlijkpersoon : {}", migratieingeschrevennatuurlijkpersoon);
        if (migratieingeschrevennatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new migratieingeschrevennatuurlijkpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        migratieingeschrevennatuurlijkpersoon = migratieingeschrevennatuurlijkpersoonRepository.save(migratieingeschrevennatuurlijkpersoon);
        return ResponseEntity.created(
            new URI("/api/migratieingeschrevennatuurlijkpersoons/" + migratieingeschrevennatuurlijkpersoon.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    migratieingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(migratieingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PUT  /migratieingeschrevennatuurlijkpersoons/:id} : Updates an existing migratieingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the migratieingeschrevennatuurlijkpersoon to save.
     * @param migratieingeschrevennatuurlijkpersoon the migratieingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated migratieingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the migratieingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the migratieingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Migratieingeschrevennatuurlijkpersoon> updateMigratieingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Migratieingeschrevennatuurlijkpersoon migratieingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Migratieingeschrevennatuurlijkpersoon : {}, {}", id, migratieingeschrevennatuurlijkpersoon);
        if (migratieingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, migratieingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!migratieingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        migratieingeschrevennatuurlijkpersoon = migratieingeschrevennatuurlijkpersoonRepository.save(migratieingeschrevennatuurlijkpersoon);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    migratieingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(migratieingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /migratieingeschrevennatuurlijkpersoons/:id} : Partial updates given fields of an existing migratieingeschrevennatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the migratieingeschrevennatuurlijkpersoon to save.
     * @param migratieingeschrevennatuurlijkpersoon the migratieingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated migratieingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the migratieingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the migratieingeschrevennatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the migratieingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Migratieingeschrevennatuurlijkpersoon> partialUpdateMigratieingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Migratieingeschrevennatuurlijkpersoon migratieingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Migratieingeschrevennatuurlijkpersoon partially : {}, {}",
            id,
            migratieingeschrevennatuurlijkpersoon
        );
        if (migratieingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, migratieingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!migratieingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Migratieingeschrevennatuurlijkpersoon> result = migratieingeschrevennatuurlijkpersoonRepository
            .findById(migratieingeschrevennatuurlijkpersoon.getId())
            .map(existingMigratieingeschrevennatuurlijkpersoon -> {
                if (migratieingeschrevennatuurlijkpersoon.getAangevermigratie() != null) {
                    existingMigratieingeschrevennatuurlijkpersoon.setAangevermigratie(
                        migratieingeschrevennatuurlijkpersoon.getAangevermigratie()
                    );
                }
                if (migratieingeschrevennatuurlijkpersoon.getRedenwijzigingmigratie() != null) {
                    existingMigratieingeschrevennatuurlijkpersoon.setRedenwijzigingmigratie(
                        migratieingeschrevennatuurlijkpersoon.getRedenwijzigingmigratie()
                    );
                }
                if (migratieingeschrevennatuurlijkpersoon.getSoortmigratie() != null) {
                    existingMigratieingeschrevennatuurlijkpersoon.setSoortmigratie(
                        migratieingeschrevennatuurlijkpersoon.getSoortmigratie()
                    );
                }

                return existingMigratieingeschrevennatuurlijkpersoon;
            })
            .map(migratieingeschrevennatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                migratieingeschrevennatuurlijkpersoon.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /migratieingeschrevennatuurlijkpersoons} : get all the migratieingeschrevennatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of migratieingeschrevennatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Migratieingeschrevennatuurlijkpersoon> getAllMigratieingeschrevennatuurlijkpersoons() {
        log.debug("REST request to get all Migratieingeschrevennatuurlijkpersoons");
        return migratieingeschrevennatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /migratieingeschrevennatuurlijkpersoons/:id} : get the "id" migratieingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the migratieingeschrevennatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the migratieingeschrevennatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Migratieingeschrevennatuurlijkpersoon> getMigratieingeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Migratieingeschrevennatuurlijkpersoon : {}", id);
        Optional<Migratieingeschrevennatuurlijkpersoon> migratieingeschrevennatuurlijkpersoon =
            migratieingeschrevennatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(migratieingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /migratieingeschrevennatuurlijkpersoons/:id} : delete the "id" migratieingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the migratieingeschrevennatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMigratieingeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Migratieingeschrevennatuurlijkpersoon : {}", id);
        migratieingeschrevennatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
