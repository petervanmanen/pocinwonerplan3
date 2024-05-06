package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Areaal;
import nl.ritense.demo.repository.AreaalRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Areaal}.
 */
@RestController
@RequestMapping("/api/areaals")
@Transactional
public class AreaalResource {

    private final Logger log = LoggerFactory.getLogger(AreaalResource.class);

    private static final String ENTITY_NAME = "areaal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaalRepository areaalRepository;

    public AreaalResource(AreaalRepository areaalRepository) {
        this.areaalRepository = areaalRepository;
    }

    /**
     * {@code POST  /areaals} : Create a new areaal.
     *
     * @param areaal the areaal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaal, or with status {@code 400 (Bad Request)} if the areaal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Areaal> createAreaal(@Valid @RequestBody Areaal areaal) throws URISyntaxException {
        log.debug("REST request to save Areaal : {}", areaal);
        if (areaal.getId() != null) {
            throw new BadRequestAlertException("A new areaal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        areaal = areaalRepository.save(areaal);
        return ResponseEntity.created(new URI("/api/areaals/" + areaal.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, areaal.getId().toString()))
            .body(areaal);
    }

    /**
     * {@code PUT  /areaals/:id} : Updates an existing areaal.
     *
     * @param id the id of the areaal to save.
     * @param areaal the areaal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaal,
     * or with status {@code 400 (Bad Request)} if the areaal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Areaal> updateAreaal(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Areaal areaal
    ) throws URISyntaxException {
        log.debug("REST request to update Areaal : {}, {}", id, areaal);
        if (areaal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        areaal = areaalRepository.save(areaal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, areaal.getId().toString()))
            .body(areaal);
    }

    /**
     * {@code PATCH  /areaals/:id} : Partial updates given fields of an existing areaal, field will ignore if it is null
     *
     * @param id the id of the areaal to save.
     * @param areaal the areaal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaal,
     * or with status {@code 400 (Bad Request)} if the areaal is not valid,
     * or with status {@code 404 (Not Found)} if the areaal is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Areaal> partialUpdateAreaal(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Areaal areaal
    ) throws URISyntaxException {
        log.debug("REST request to partial update Areaal partially : {}, {}", id, areaal);
        if (areaal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Areaal> result = areaalRepository
            .findById(areaal.getId())
            .map(existingAreaal -> {
                if (areaal.getGeometrie() != null) {
                    existingAreaal.setGeometrie(areaal.getGeometrie());
                }

                return existingAreaal;
            })
            .map(areaalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, areaal.getId().toString())
        );
    }

    /**
     * {@code GET  /areaals} : get all the areaals.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaals in body.
     */
    @GetMapping("")
    public List<Areaal> getAllAreaals(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Areaals");
        if (eagerload) {
            return areaalRepository.findAllWithEagerRelationships();
        } else {
            return areaalRepository.findAll();
        }
    }

    /**
     * {@code GET  /areaals/:id} : get the "id" areaal.
     *
     * @param id the id of the areaal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Areaal> getAreaal(@PathVariable("id") Long id) {
        log.debug("REST request to get Areaal : {}", id);
        Optional<Areaal> areaal = areaalRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(areaal);
    }

    /**
     * {@code DELETE  /areaals/:id} : delete the "id" areaal.
     *
     * @param id the id of the areaal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAreaal(@PathVariable("id") Long id) {
        log.debug("REST request to delete Areaal : {}", id);
        areaalRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
