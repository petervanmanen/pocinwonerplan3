package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Terreindeel;
import nl.ritense.demo.repository.TerreindeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Terreindeel}.
 */
@RestController
@RequestMapping("/api/terreindeels")
@Transactional
public class TerreindeelResource {

    private final Logger log = LoggerFactory.getLogger(TerreindeelResource.class);

    private static final String ENTITY_NAME = "terreindeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerreindeelRepository terreindeelRepository;

    public TerreindeelResource(TerreindeelRepository terreindeelRepository) {
        this.terreindeelRepository = terreindeelRepository;
    }

    /**
     * {@code POST  /terreindeels} : Create a new terreindeel.
     *
     * @param terreindeel the terreindeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terreindeel, or with status {@code 400 (Bad Request)} if the terreindeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Terreindeel> createTerreindeel(@Valid @RequestBody Terreindeel terreindeel) throws URISyntaxException {
        log.debug("REST request to save Terreindeel : {}", terreindeel);
        if (terreindeel.getId() != null) {
            throw new BadRequestAlertException("A new terreindeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        terreindeel = terreindeelRepository.save(terreindeel);
        return ResponseEntity.created(new URI("/api/terreindeels/" + terreindeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, terreindeel.getId().toString()))
            .body(terreindeel);
    }

    /**
     * {@code PUT  /terreindeels/:id} : Updates an existing terreindeel.
     *
     * @param id the id of the terreindeel to save.
     * @param terreindeel the terreindeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terreindeel,
     * or with status {@code 400 (Bad Request)} if the terreindeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terreindeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Terreindeel> updateTerreindeel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Terreindeel terreindeel
    ) throws URISyntaxException {
        log.debug("REST request to update Terreindeel : {}, {}", id, terreindeel);
        if (terreindeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, terreindeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!terreindeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        terreindeel = terreindeelRepository.save(terreindeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, terreindeel.getId().toString()))
            .body(terreindeel);
    }

    /**
     * {@code PATCH  /terreindeels/:id} : Partial updates given fields of an existing terreindeel, field will ignore if it is null
     *
     * @param id the id of the terreindeel to save.
     * @param terreindeel the terreindeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terreindeel,
     * or with status {@code 400 (Bad Request)} if the terreindeel is not valid,
     * or with status {@code 404 (Not Found)} if the terreindeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the terreindeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Terreindeel> partialUpdateTerreindeel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Terreindeel terreindeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Terreindeel partially : {}, {}", id, terreindeel);
        if (terreindeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, terreindeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!terreindeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Terreindeel> result = terreindeelRepository
            .findById(terreindeel.getId())
            .map(existingTerreindeel -> {
                if (terreindeel.getBreedte() != null) {
                    existingTerreindeel.setBreedte(terreindeel.getBreedte());
                }
                if (terreindeel.getCultuurhistorischwaardevol() != null) {
                    existingTerreindeel.setCultuurhistorischwaardevol(terreindeel.getCultuurhistorischwaardevol());
                }
                if (terreindeel.getHerplantplicht() != null) {
                    existingTerreindeel.setHerplantplicht(terreindeel.getHerplantplicht());
                }
                if (terreindeel.getOppervlakte() != null) {
                    existingTerreindeel.setOppervlakte(terreindeel.getOppervlakte());
                }
                if (terreindeel.getOptalud() != null) {
                    existingTerreindeel.setOptalud(terreindeel.getOptalud());
                }
                if (terreindeel.getPercentageloofbos() != null) {
                    existingTerreindeel.setPercentageloofbos(terreindeel.getPercentageloofbos());
                }
                if (terreindeel.getTerreindeelsoortnaam() != null) {
                    existingTerreindeel.setTerreindeelsoortnaam(terreindeel.getTerreindeelsoortnaam());
                }
                if (terreindeel.getType() != null) {
                    existingTerreindeel.setType(terreindeel.getType());
                }
                if (terreindeel.getTypebewerking() != null) {
                    existingTerreindeel.setTypebewerking(terreindeel.getTypebewerking());
                }
                if (terreindeel.getTypeplus() != null) {
                    existingTerreindeel.setTypeplus(terreindeel.getTypeplus());
                }
                if (terreindeel.getTypeplus2() != null) {
                    existingTerreindeel.setTypeplus2(terreindeel.getTypeplus2());
                }

                return existingTerreindeel;
            })
            .map(terreindeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, terreindeel.getId().toString())
        );
    }

    /**
     * {@code GET  /terreindeels} : get all the terreindeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terreindeels in body.
     */
    @GetMapping("")
    public List<Terreindeel> getAllTerreindeels() {
        log.debug("REST request to get all Terreindeels");
        return terreindeelRepository.findAll();
    }

    /**
     * {@code GET  /terreindeels/:id} : get the "id" terreindeel.
     *
     * @param id the id of the terreindeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terreindeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Terreindeel> getTerreindeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Terreindeel : {}", id);
        Optional<Terreindeel> terreindeel = terreindeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(terreindeel);
    }

    /**
     * {@code DELETE  /terreindeels/:id} : delete the "id" terreindeel.
     *
     * @param id the id of the terreindeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerreindeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Terreindeel : {}", id);
        terreindeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
