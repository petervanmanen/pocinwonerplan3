package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Spoor;
import nl.ritense.demo.repository.SpoorRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Spoor}.
 */
@RestController
@RequestMapping("/api/spoors")
@Transactional
public class SpoorResource {

    private final Logger log = LoggerFactory.getLogger(SpoorResource.class);

    private static final String ENTITY_NAME = "spoor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpoorRepository spoorRepository;

    public SpoorResource(SpoorRepository spoorRepository) {
        this.spoorRepository = spoorRepository;
    }

    /**
     * {@code POST  /spoors} : Create a new spoor.
     *
     * @param spoor the spoor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spoor, or with status {@code 400 (Bad Request)} if the spoor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Spoor> createSpoor(@Valid @RequestBody Spoor spoor) throws URISyntaxException {
        log.debug("REST request to save Spoor : {}", spoor);
        if (spoor.getId() != null) {
            throw new BadRequestAlertException("A new spoor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        spoor = spoorRepository.save(spoor);
        return ResponseEntity.created(new URI("/api/spoors/" + spoor.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, spoor.getId().toString()))
            .body(spoor);
    }

    /**
     * {@code PUT  /spoors/:id} : Updates an existing spoor.
     *
     * @param id the id of the spoor to save.
     * @param spoor the spoor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spoor,
     * or with status {@code 400 (Bad Request)} if the spoor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spoor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Spoor> updateSpoor(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Spoor spoor)
        throws URISyntaxException {
        log.debug("REST request to update Spoor : {}, {}", id, spoor);
        if (spoor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spoor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spoorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        spoor = spoorRepository.save(spoor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, spoor.getId().toString()))
            .body(spoor);
    }

    /**
     * {@code PATCH  /spoors/:id} : Partial updates given fields of an existing spoor, field will ignore if it is null
     *
     * @param id the id of the spoor to save.
     * @param spoor the spoor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spoor,
     * or with status {@code 400 (Bad Request)} if the spoor is not valid,
     * or with status {@code 404 (Not Found)} if the spoor is not found,
     * or with status {@code 500 (Internal Server Error)} if the spoor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Spoor> partialUpdateSpoor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Spoor spoor
    ) throws URISyntaxException {
        log.debug("REST request to partial update Spoor partially : {}, {}", id, spoor);
        if (spoor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, spoor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!spoorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Spoor> result = spoorRepository
            .findById(spoor.getId())
            .map(existingSpoor -> {
                if (spoor.getAard() != null) {
                    existingSpoor.setAard(spoor.getAard());
                }
                if (spoor.getBeschrijving() != null) {
                    existingSpoor.setBeschrijving(spoor.getBeschrijving());
                }
                if (spoor.getDatering() != null) {
                    existingSpoor.setDatering(spoor.getDatering());
                }
                if (spoor.getDatum() != null) {
                    existingSpoor.setDatum(spoor.getDatum());
                }
                if (spoor.getHoogteboven() != null) {
                    existingSpoor.setHoogteboven(spoor.getHoogteboven());
                }
                if (spoor.getHoogteonder() != null) {
                    existingSpoor.setHoogteonder(spoor.getHoogteonder());
                }
                if (spoor.getKey() != null) {
                    existingSpoor.setKey(spoor.getKey());
                }
                if (spoor.getKeyvlak() != null) {
                    existingSpoor.setKeyvlak(spoor.getKeyvlak());
                }
                if (spoor.getProjectcd() != null) {
                    existingSpoor.setProjectcd(spoor.getProjectcd());
                }
                if (spoor.getPutnummer() != null) {
                    existingSpoor.setPutnummer(spoor.getPutnummer());
                }
                if (spoor.getSpoornummer() != null) {
                    existingSpoor.setSpoornummer(spoor.getSpoornummer());
                }
                if (spoor.getVlaknummer() != null) {
                    existingSpoor.setVlaknummer(spoor.getVlaknummer());
                }
                if (spoor.getVorm() != null) {
                    existingSpoor.setVorm(spoor.getVorm());
                }

                return existingSpoor;
            })
            .map(spoorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, spoor.getId().toString())
        );
    }

    /**
     * {@code GET  /spoors} : get all the spoors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spoors in body.
     */
    @GetMapping("")
    public List<Spoor> getAllSpoors() {
        log.debug("REST request to get all Spoors");
        return spoorRepository.findAll();
    }

    /**
     * {@code GET  /spoors/:id} : get the "id" spoor.
     *
     * @param id the id of the spoor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spoor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Spoor> getSpoor(@PathVariable("id") Long id) {
        log.debug("REST request to get Spoor : {}", id);
        Optional<Spoor> spoor = spoorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(spoor);
    }

    /**
     * {@code DELETE  /spoors/:id} : delete the "id" spoor.
     *
     * @param id the id of the spoor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpoor(@PathVariable("id") Long id) {
        log.debug("REST request to delete Spoor : {}", id);
        spoorRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
