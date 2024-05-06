package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Geboorteingeschrevenpersoon;
import nl.ritense.demo.repository.GeboorteingeschrevenpersoonRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Geboorteingeschrevenpersoon}.
 */
@RestController
@RequestMapping("/api/geboorteingeschrevenpersoons")
@Transactional
public class GeboorteingeschrevenpersoonResource {

    private final Logger log = LoggerFactory.getLogger(GeboorteingeschrevenpersoonResource.class);

    private static final String ENTITY_NAME = "geboorteingeschrevenpersoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeboorteingeschrevenpersoonRepository geboorteingeschrevenpersoonRepository;

    public GeboorteingeschrevenpersoonResource(GeboorteingeschrevenpersoonRepository geboorteingeschrevenpersoonRepository) {
        this.geboorteingeschrevenpersoonRepository = geboorteingeschrevenpersoonRepository;
    }

    /**
     * {@code POST  /geboorteingeschrevenpersoons} : Create a new geboorteingeschrevenpersoon.
     *
     * @param geboorteingeschrevenpersoon the geboorteingeschrevenpersoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geboorteingeschrevenpersoon, or with status {@code 400 (Bad Request)} if the geboorteingeschrevenpersoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Geboorteingeschrevenpersoon> createGeboorteingeschrevenpersoon(
        @RequestBody Geboorteingeschrevenpersoon geboorteingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to save Geboorteingeschrevenpersoon : {}", geboorteingeschrevenpersoon);
        if (geboorteingeschrevenpersoon.getId() != null) {
            throw new BadRequestAlertException("A new geboorteingeschrevenpersoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        geboorteingeschrevenpersoon = geboorteingeschrevenpersoonRepository.save(geboorteingeschrevenpersoon);
        return ResponseEntity.created(new URI("/api/geboorteingeschrevenpersoons/" + geboorteingeschrevenpersoon.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, geboorteingeschrevenpersoon.getId().toString())
            )
            .body(geboorteingeschrevenpersoon);
    }

    /**
     * {@code PUT  /geboorteingeschrevenpersoons/:id} : Updates an existing geboorteingeschrevenpersoon.
     *
     * @param id the id of the geboorteingeschrevenpersoon to save.
     * @param geboorteingeschrevenpersoon the geboorteingeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geboorteingeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the geboorteingeschrevenpersoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geboorteingeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Geboorteingeschrevenpersoon> updateGeboorteingeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Geboorteingeschrevenpersoon geboorteingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to update Geboorteingeschrevenpersoon : {}, {}", id, geboorteingeschrevenpersoon);
        if (geboorteingeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geboorteingeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geboorteingeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        geboorteingeschrevenpersoon = geboorteingeschrevenpersoonRepository.save(geboorteingeschrevenpersoon);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geboorteingeschrevenpersoon.getId().toString())
            )
            .body(geboorteingeschrevenpersoon);
    }

    /**
     * {@code PATCH  /geboorteingeschrevenpersoons/:id} : Partial updates given fields of an existing geboorteingeschrevenpersoon, field will ignore if it is null
     *
     * @param id the id of the geboorteingeschrevenpersoon to save.
     * @param geboorteingeschrevenpersoon the geboorteingeschrevenpersoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geboorteingeschrevenpersoon,
     * or with status {@code 400 (Bad Request)} if the geboorteingeschrevenpersoon is not valid,
     * or with status {@code 404 (Not Found)} if the geboorteingeschrevenpersoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the geboorteingeschrevenpersoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Geboorteingeschrevenpersoon> partialUpdateGeboorteingeschrevenpersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Geboorteingeschrevenpersoon geboorteingeschrevenpersoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Geboorteingeschrevenpersoon partially : {}, {}", id, geboorteingeschrevenpersoon);
        if (geboorteingeschrevenpersoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geboorteingeschrevenpersoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geboorteingeschrevenpersoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Geboorteingeschrevenpersoon> result = geboorteingeschrevenpersoonRepository
            .findById(geboorteingeschrevenpersoon.getId())
            .map(existingGeboorteingeschrevenpersoon -> {
                if (geboorteingeschrevenpersoon.getDatumgeboorte() != null) {
                    existingGeboorteingeschrevenpersoon.setDatumgeboorte(geboorteingeschrevenpersoon.getDatumgeboorte());
                }
                if (geboorteingeschrevenpersoon.getGeboorteland() != null) {
                    existingGeboorteingeschrevenpersoon.setGeboorteland(geboorteingeschrevenpersoon.getGeboorteland());
                }
                if (geboorteingeschrevenpersoon.getGeboorteplaats() != null) {
                    existingGeboorteingeschrevenpersoon.setGeboorteplaats(geboorteingeschrevenpersoon.getGeboorteplaats());
                }

                return existingGeboorteingeschrevenpersoon;
            })
            .map(geboorteingeschrevenpersoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geboorteingeschrevenpersoon.getId().toString())
        );
    }

    /**
     * {@code GET  /geboorteingeschrevenpersoons} : get all the geboorteingeschrevenpersoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geboorteingeschrevenpersoons in body.
     */
    @GetMapping("")
    public List<Geboorteingeschrevenpersoon> getAllGeboorteingeschrevenpersoons() {
        log.debug("REST request to get all Geboorteingeschrevenpersoons");
        return geboorteingeschrevenpersoonRepository.findAll();
    }

    /**
     * {@code GET  /geboorteingeschrevenpersoons/:id} : get the "id" geboorteingeschrevenpersoon.
     *
     * @param id the id of the geboorteingeschrevenpersoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geboorteingeschrevenpersoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Geboorteingeschrevenpersoon> getGeboorteingeschrevenpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to get Geboorteingeschrevenpersoon : {}", id);
        Optional<Geboorteingeschrevenpersoon> geboorteingeschrevenpersoon = geboorteingeschrevenpersoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(geboorteingeschrevenpersoon);
    }

    /**
     * {@code DELETE  /geboorteingeschrevenpersoons/:id} : delete the "id" geboorteingeschrevenpersoon.
     *
     * @param id the id of the geboorteingeschrevenpersoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeboorteingeschrevenpersoon(@PathVariable("id") Long id) {
        log.debug("REST request to delete Geboorteingeschrevenpersoon : {}", id);
        geboorteingeschrevenpersoonRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
