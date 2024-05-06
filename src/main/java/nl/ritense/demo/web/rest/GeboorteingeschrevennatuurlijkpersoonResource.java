package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Geboorteingeschrevennatuurlijkpersoon;
import nl.ritense.demo.repository.GeboorteingeschrevennatuurlijkpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Geboorteingeschrevennatuurlijkpersoon}.
 */
@RestController
@RequestMapping("/api/geboorteingeschrevennatuurlijkpersoons")
@Transactional
public class GeboorteingeschrevennatuurlijkpersoonResource {

    private final Logger log = LoggerFactory.getLogger(GeboorteingeschrevennatuurlijkpersoonResource.class);

    private static final String ENTITY_NAME = "geboorteingeschrevennatuurlijkpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeboorteingeschrevennatuurlijkpersoonRepository geboorteingeschrevennatuurlijkpersoonRepository;

    public GeboorteingeschrevennatuurlijkpersoonResource(
        GeboorteingeschrevennatuurlijkpersoonRepository geboorteingeschrevennatuurlijkpersoonRepository
    ) {
        this.geboorteingeschrevennatuurlijkpersoonRepository = geboorteingeschrevennatuurlijkpersoonRepository;
    }

    /**
     * {@code POST  /geboorteingeschrevennatuurlijkpersoons} : Create a new geboorteingeschrevennatuurlijkpersoon.
     *
     * @param geboorteingeschrevennatuurlijkpersoon the geboorteingeschrevennatuurlijkpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geboorteingeschrevennatuurlijkpersoon, or with status {@code 400 (Bad Request)} if the geboorteingeschrevennatuurlijkpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Geboorteingeschrevennatuurlijkpersoon> createGeboorteingeschrevennatuurlijkpersoon(
        @RequestBody Geboorteingeschrevennatuurlijkpersoon geboorteingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Geboorteingeschrevennatuurlijkpersoon : {}", geboorteingeschrevennatuurlijkpersoon);
        if (geboorteingeschrevennatuurlijkpersoon.getId() != null) {
            throw new BadRequestAlertException(
                "A new geboorteingeschrevennatuurlijkpersoon cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        geboorteingeschrevennatuurlijkpersoon = geboorteingeschrevennatuurlijkpersoonRepository.save(geboorteingeschrevennatuurlijkpersoon);
        return ResponseEntity.created(
            new URI("/api/geboorteingeschrevennatuurlijkpersoons/" + geboorteingeschrevennatuurlijkpersoon.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    geboorteingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(geboorteingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PUT  /geboorteingeschrevennatuurlijkpersoons/:id} : Updates an existing geboorteingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the geboorteingeschrevennatuurlijkpersoon to save.
     * @param geboorteingeschrevennatuurlijkpersoon the geboorteingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geboorteingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the geboorteingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geboorteingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Geboorteingeschrevennatuurlijkpersoon> updateGeboorteingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Geboorteingeschrevennatuurlijkpersoon geboorteingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Geboorteingeschrevennatuurlijkpersoon : {}, {}", id, geboorteingeschrevennatuurlijkpersoon);
        if (geboorteingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geboorteingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geboorteingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        geboorteingeschrevennatuurlijkpersoon = geboorteingeschrevennatuurlijkpersoonRepository.save(geboorteingeschrevennatuurlijkpersoon);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    geboorteingeschrevennatuurlijkpersoon.getId().toString()
                )
            )
            .body(geboorteingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code PATCH  /geboorteingeschrevennatuurlijkpersoons/:id} : Partial updates given fields of an existing geboorteingeschrevennatuurlijkpersoon, field will ignore if it is null
     *
     * @param id the id of the geboorteingeschrevennatuurlijkpersoon to save.
     * @param geboorteingeschrevennatuurlijkpersoon the geboorteingeschrevennatuurlijkpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geboorteingeschrevennatuurlijkpersoon,
     * or with status {@code 400 (Bad Request)} if the geboorteingeschrevennatuurlijkpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the geboorteingeschrevennatuurlijkpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the geboorteingeschrevennatuurlijkpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Geboorteingeschrevennatuurlijkpersoon> partialUpdateGeboorteingeschrevennatuurlijkpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Geboorteingeschrevennatuurlijkpersoon geboorteingeschrevennatuurlijkpersoon
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Geboorteingeschrevennatuurlijkpersoon partially : {}, {}",
            id,
            geboorteingeschrevennatuurlijkpersoon
        );
        if (geboorteingeschrevennatuurlijkpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geboorteingeschrevennatuurlijkpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geboorteingeschrevennatuurlijkpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Geboorteingeschrevennatuurlijkpersoon> result = geboorteingeschrevennatuurlijkpersoonRepository
            .findById(geboorteingeschrevennatuurlijkpersoon.getId())
            .map(existingGeboorteingeschrevennatuurlijkpersoon -> {
                if (geboorteingeschrevennatuurlijkpersoon.getBuitenlandseplaatsgeboorte() != null) {
                    existingGeboorteingeschrevennatuurlijkpersoon.setBuitenlandseplaatsgeboorte(
                        geboorteingeschrevennatuurlijkpersoon.getBuitenlandseplaatsgeboorte()
                    );
                }
                if (geboorteingeschrevennatuurlijkpersoon.getBuitenlandseregiogeboorte() != null) {
                    existingGeboorteingeschrevennatuurlijkpersoon.setBuitenlandseregiogeboorte(
                        geboorteingeschrevennatuurlijkpersoon.getBuitenlandseregiogeboorte()
                    );
                }
                if (geboorteingeschrevennatuurlijkpersoon.getDatumgeboorte() != null) {
                    existingGeboorteingeschrevennatuurlijkpersoon.setDatumgeboorte(
                        geboorteingeschrevennatuurlijkpersoon.getDatumgeboorte()
                    );
                }
                if (geboorteingeschrevennatuurlijkpersoon.getGemeentegeboorte() != null) {
                    existingGeboorteingeschrevennatuurlijkpersoon.setGemeentegeboorte(
                        geboorteingeschrevennatuurlijkpersoon.getGemeentegeboorte()
                    );
                }
                if (geboorteingeschrevennatuurlijkpersoon.getLandofgebiedgeboorte() != null) {
                    existingGeboorteingeschrevennatuurlijkpersoon.setLandofgebiedgeboorte(
                        geboorteingeschrevennatuurlijkpersoon.getLandofgebiedgeboorte()
                    );
                }
                if (geboorteingeschrevennatuurlijkpersoon.getOmschrijvinglocatiegeboorte() != null) {
                    existingGeboorteingeschrevennatuurlijkpersoon.setOmschrijvinglocatiegeboorte(
                        geboorteingeschrevennatuurlijkpersoon.getOmschrijvinglocatiegeboorte()
                    );
                }

                return existingGeboorteingeschrevennatuurlijkpersoon;
            })
            .map(geboorteingeschrevennatuurlijkpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                geboorteingeschrevennatuurlijkpersoon.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /geboorteingeschrevennatuurlijkpersoons} : get all the geboorteingeschrevennatuurlijkpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geboorteingeschrevennatuurlijkpersoons in body.
     */
    @GetMapping("")
    public List<Geboorteingeschrevennatuurlijkpersoon> getAllGeboorteingeschrevennatuurlijkpersoons() {
        log.debug("REST request to get all Geboorteingeschrevennatuurlijkpersoons");
        return geboorteingeschrevennatuurlijkpersoonRepository.findAll();
    }

    /**
     * {@code GET  /geboorteingeschrevennatuurlijkpersoons/:id} : get the "id" geboorteingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the geboorteingeschrevennatuurlijkpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geboorteingeschrevennatuurlijkpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Geboorteingeschrevennatuurlijkpersoon> getGeboorteingeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Geboorteingeschrevennatuurlijkpersoon : {}", id);
        Optional<Geboorteingeschrevennatuurlijkpersoon> geboorteingeschrevennatuurlijkpersoon =
            geboorteingeschrevennatuurlijkpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(geboorteingeschrevennatuurlijkpersoon);
    }

    /**
     * {@code DELETE  /geboorteingeschrevennatuurlijkpersoons/:id} : delete the "id" geboorteingeschrevennatuurlijkpersoon.
     *
     * @param id the id of the geboorteingeschrevennatuurlijkpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeboorteingeschrevennatuurlijkpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Geboorteingeschrevennatuurlijkpersoon : {}", id);
        geboorteingeschrevennatuurlijkpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
