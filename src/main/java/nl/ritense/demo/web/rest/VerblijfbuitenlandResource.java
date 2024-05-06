package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfbuitenland;
import nl.ritense.demo.repository.VerblijfbuitenlandRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verblijfbuitenland}.
 */
@RestController
@RequestMapping("/api/verblijfbuitenlands")
@Transactional
public class VerblijfbuitenlandResource {

    private final Logger log = LoggerFactory.getLogger(VerblijfbuitenlandResource.class);

    private static final String ENTITY_NAME = "verblijfbuitenland";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerblijfbuitenlandRepository verblijfbuitenlandRepository;

    public VerblijfbuitenlandResource(VerblijfbuitenlandRepository verblijfbuitenlandRepository) {
        this.verblijfbuitenlandRepository = verblijfbuitenlandRepository;
    }

    /**
     * {@code POST  /verblijfbuitenlands} : Create a new verblijfbuitenland.
     *
     * @param verblijfbuitenland the verblijfbuitenland to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verblijfbuitenland, or with status {@code 400 (Bad Request)} if the verblijfbuitenland has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verblijfbuitenland> createVerblijfbuitenland(@RequestBody Verblijfbuitenland verblijfbuitenland)
        throws URISyntaxException {
        log.debug("REST request to save Verblijfbuitenland : {}", verblijfbuitenland);
        if (verblijfbuitenland.getId() != null) {
            throw new BadRequestAlertException("A new verblijfbuitenland cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verblijfbuitenland = verblijfbuitenlandRepository.save(verblijfbuitenland);
        return ResponseEntity.created(new URI("/api/verblijfbuitenlands/" + verblijfbuitenland.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verblijfbuitenland.getId().toString()))
            .body(verblijfbuitenland);
    }

    /**
     * {@code PUT  /verblijfbuitenlands/:id} : Updates an existing verblijfbuitenland.
     *
     * @param id the id of the verblijfbuitenland to save.
     * @param verblijfbuitenland the verblijfbuitenland to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfbuitenland,
     * or with status {@code 400 (Bad Request)} if the verblijfbuitenland is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verblijfbuitenland couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verblijfbuitenland> updateVerblijfbuitenland(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfbuitenland verblijfbuitenland
    ) throws URISyntaxException {
        log.debug("REST request to update Verblijfbuitenland : {}, {}", id, verblijfbuitenland);
        if (verblijfbuitenland.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfbuitenland.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfbuitenlandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verblijfbuitenland = verblijfbuitenlandRepository.save(verblijfbuitenland);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfbuitenland.getId().toString()))
            .body(verblijfbuitenland);
    }

    /**
     * {@code PATCH  /verblijfbuitenlands/:id} : Partial updates given fields of an existing verblijfbuitenland, field will ignore if it is null
     *
     * @param id the id of the verblijfbuitenland to save.
     * @param verblijfbuitenland the verblijfbuitenland to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfbuitenland,
     * or with status {@code 400 (Bad Request)} if the verblijfbuitenland is not valid,
     * or with status {@code 404 (Not Found)} if the verblijfbuitenland is not found,
     * or with status {@code 500 (Internal Server Error)} if the verblijfbuitenland couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verblijfbuitenland> partialUpdateVerblijfbuitenland(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfbuitenland verblijfbuitenland
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verblijfbuitenland partially : {}, {}", id, verblijfbuitenland);
        if (verblijfbuitenland.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfbuitenland.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfbuitenlandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verblijfbuitenland> result = verblijfbuitenlandRepository
            .findById(verblijfbuitenland.getId())
            .map(existingVerblijfbuitenland -> {
                if (verblijfbuitenland.getAdresregelbuitenland1() != null) {
                    existingVerblijfbuitenland.setAdresregelbuitenland1(verblijfbuitenland.getAdresregelbuitenland1());
                }
                if (verblijfbuitenland.getAdresregelbuitenland2() != null) {
                    existingVerblijfbuitenland.setAdresregelbuitenland2(verblijfbuitenland.getAdresregelbuitenland2());
                }
                if (verblijfbuitenland.getAdresregelbuitenland3() != null) {
                    existingVerblijfbuitenland.setAdresregelbuitenland3(verblijfbuitenland.getAdresregelbuitenland3());
                }
                if (verblijfbuitenland.getAdresregelbuitenland4() != null) {
                    existingVerblijfbuitenland.setAdresregelbuitenland4(verblijfbuitenland.getAdresregelbuitenland4());
                }
                if (verblijfbuitenland.getAdresregelbuitenland5() != null) {
                    existingVerblijfbuitenland.setAdresregelbuitenland5(verblijfbuitenland.getAdresregelbuitenland5());
                }
                if (verblijfbuitenland.getAdresregelbuitenland6() != null) {
                    existingVerblijfbuitenland.setAdresregelbuitenland6(verblijfbuitenland.getAdresregelbuitenland6());
                }
                if (verblijfbuitenland.getLandofgebiedverblijfadres() != null) {
                    existingVerblijfbuitenland.setLandofgebiedverblijfadres(verblijfbuitenland.getLandofgebiedverblijfadres());
                }

                return existingVerblijfbuitenland;
            })
            .map(verblijfbuitenlandRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfbuitenland.getId().toString())
        );
    }

    /**
     * {@code GET  /verblijfbuitenlands} : get all the verblijfbuitenlands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verblijfbuitenlands in body.
     */
    @GetMapping("")
    public List<Verblijfbuitenland> getAllVerblijfbuitenlands() {
        log.debug("REST request to get all Verblijfbuitenlands");
        return verblijfbuitenlandRepository.findAll();
    }

    /**
     * {@code GET  /verblijfbuitenlands/:id} : get the "id" verblijfbuitenland.
     *
     * @param id the id of the verblijfbuitenland to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verblijfbuitenland, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verblijfbuitenland> getVerblijfbuitenland(@PathVariable("id") Long id) {
        log.debug("REST request to get Verblijfbuitenland : {}", id);
        Optional<Verblijfbuitenland> verblijfbuitenland = verblijfbuitenlandRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verblijfbuitenland);
    }

    /**
     * {@code DELETE  /verblijfbuitenlands/:id} : delete the "id" verblijfbuitenland.
     *
     * @param id the id of the verblijfbuitenland to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerblijfbuitenland(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verblijfbuitenland : {}", id);
        verblijfbuitenlandRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
