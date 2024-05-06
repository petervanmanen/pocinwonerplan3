package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Samengesteldenaamnatuurlijkpersoon;
import nl.ritense.demo.repository.SamengesteldenaamnatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Samengesteldenaamnatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/samengesteldenaamnatuurlijkpersoons")
@Transactional
public class SamengesteldenaamnatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(SamengesteldenaamnatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "samengesteldenaamnatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SamengesteldenaamnatuurlijkpersoonRepository samengesteldenaamnatuurlijkpersoonRepository;

    public SamengesteldenaamnatuurlijkpersoonResource(
        SamengesteldenaamnatuurlijkpersoonRepository samengesteldenaamnatuurlijkpersoonRepository
    ) {
        this.samengesteldenaamnatuurlijkpersoonRepository = samengesteldenaamnatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /samengesteldenaamnatuurlijkpersoons} : Create a new samengesteldenaamnatuurlijkpersoon.
     *
     * @param samengesteldenaamnatuurlijkpersoon the samengesteldenaamnatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new samengesteldenaamnatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the samengesteldenaamnatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Samengesteldenaamnatuurlijkpersoon> createSamengesteldenaamnatuurlijkpersoon(
        @RequestBody Samengesteldenaamnatuurlijkpersoon samengesteldenaamnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Samengesteldenaamnatuurlijkpersoon : {}", samengesteldenaamnatuurlijkpersoon);
        if (samengesteldenaamnatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new samengesteldenaamnatuurlijkpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        samengesteldenaamnatuurlijkpersoon = samengesteldenaamnatuurlijkpersoonRepository.save(samengesteldenaamnatuurlijkpersoon);
        return ResponseEntity.created(new URI("/api/samengesteldenaamnatuurlijkpersoons/" + samengesteldenaamnatuurlijkpersoon.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    samengesteldenaamnatuurlijkpersoon.getId().toString()
                )
            )
            .body(samengesteldenaamnatuurlijkpersoon);
    }

    /**
     * {@code PUT  /samengesteldenaamnatuurlijkpersoons/:id} : Updates an existing samengesteldenaamnatuurlijkpersoon.
     *
     * @param id the id of the samengesteldenaamnatuurlijkpersoon to save.
     * @param samengesteldenaamnatuurlijkpersoon the samengesteldenaamnatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated samengesteldenaamnatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the samengesteldenaamnatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the samengesteldenaamnatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Samengesteldenaamnatuurlijkpersoon> updateSamengesteldenaamnatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Samengesteldenaamnatuurlijkpersoon samengesteldenaamnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Samengesteldenaamnatuurlijkpersoon : {}, {}", id, samengesteldenaamnatuurlijkpersoon);
        if (samengesteldenaamnatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, samengesteldenaamnatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!samengesteldenaamnatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        samengesteldenaamnatuurlijkpersoon = samengesteldenaamnatuurlijkpersoonRepository.save(samengesteldenaamnatuurlijkpersoon);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    samengesteldenaamnatuurlijkpersoon.getId().toString()
                )
            )
            .body(samengesteldenaamnatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /samengesteldenaamnatuurlijkpersoons/:id} : Partial updates given fields of an existing samengesteldenaamnatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the samengesteldenaamnatuurlijkpersoon to save.
     * @param samengesteldenaamnatuurlijkpersoon the samengesteldenaamnatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated samengesteldenaamnatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the samengesteldenaamnatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the samengesteldenaamnatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the samengesteldenaamnatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Samengesteldenaamnatuurlijkpersoon> partialUpdateSamengesteldenaamnatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Samengesteldenaamnatuurlijkpersoon samengesteldenaamnatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Samengesteldenaamnatuurlijkpersoon partially : {}, {}",
            id,
            samengesteldenaamnatuurlijkpersoon
        );
        if (samengesteldenaamnatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, samengesteldenaamnatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!samengesteldenaamnatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Samengesteldenaamnatuurlijkpersoon> result = samengesteldenaamnatuurlijkpersoonRepository
            .findById(samengesteldenaamnatuurlijkpersoon.getId())
            .map(existingSamengesteldenaamnatuurlijkpersoon -> {
                if (samengesteldenaamnatuurlijkpersoon.getAdellijketitel() != null) {
                    existingSamengesteldenaamnatuurlijkpersoon.setAdellijketitel(samengesteldenaamnatuurlijkpersoon.getAdellijketitel());
                }
                if (samengesteldenaamnatuurlijkpersoon.getGeslachtsnaamstam() != null) {
                    existingSamengesteldenaamnatuurlijkpersoon.setGeslachtsnaamstam(
                        samengesteldenaamnatuurlijkpersoon.getGeslachtsnaamstam()
                    );
                }
                if (samengesteldenaamnatuurlijkpersoon.getNamenreeks() != null) {
                    existingSamengesteldenaamnatuurlijkpersoon.setNamenreeks(samengesteldenaamnatuurlijkpersoon.getNamenreeks());
                }
                if (samengesteldenaamnatuurlijkpersoon.getPredicaat() != null) {
                    existingSamengesteldenaamnatuurlijkpersoon.setPredicaat(samengesteldenaamnatuurlijkpersoon.getPredicaat());
                }
                if (samengesteldenaamnatuurlijkpersoon.getScheidingsteken() != null) {
                    existingSamengesteldenaamnatuurlijkpersoon.setScheidingsteken(samengesteldenaamnatuurlijkpersoon.getScheidingsteken());
                }
                if (samengesteldenaamnatuurlijkpersoon.getVoornamen() != null) {
                    existingSamengesteldenaamnatuurlijkpersoon.setVoornamen(samengesteldenaamnatuurlijkpersoon.getVoornamen());
                }
                if (samengesteldenaamnatuurlijkpersoon.getVoorvoegsel() != null) {
                    existingSamengesteldenaamnatuurlijkpersoon.setVoorvoegsel(samengesteldenaamnatuurlijkpersoon.getVoorvoegsel());
                }

                return existingSamengesteldenaamnatuurlijkpersoon;
            })
            .map(samengesteldenaamnatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, samengesteldenaamnatuurlijkpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /samengesteldenaamnatuurlijkpersoons} : get all the samengesteldenaamnatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of samengesteldenaamnatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Samengesteldenaamnatuurlijkpersoon> getAllSamengesteldenaamnatuurlijkpersoons() {
        log.debug("REST request to get all Samengesteldenaamnatuurlijkpersoons");
        return samengesteldenaamnatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /samengesteldenaamnatuurlijkpersoons/:id} : get the "id" samengesteldenaamnatuurlijkpersoon.
     *
     * @param id the id of the samengesteldenaamnatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the samengesteldenaamnatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Samengesteldenaamnatuurlijkpersoon> getSamengesteldenaamnatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Samengesteldenaamnatuurlijkpersoon : {}", id);
        Optional<Samengesteldenaamnatuurlijkpersoon> samengesteldenaamnatuurlijkpersoon =
            samengesteldenaamnatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(samengesteldenaamnatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /samengesteldenaamnatuurlijkpersoons/:id} : delete the "id" samengesteldenaamnatuurlijkpersoon.
     *
     * @param id the id of the samengesteldenaamnatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSamengesteldenaamnatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Samengesteldenaamnatuurlijkpersoon : {}", id);
        samengesteldenaamnatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
