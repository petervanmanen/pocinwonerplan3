package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vuilniswagen;
import nl.ritense.demo.repository.VuilniswagenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vuilniswagen}.
 */
@RestController
@RequestMapping("/api/vuilniswagens")
@Transactional
public class VuilniswagenResource {

    private final Logger log = LoggerFactory.getLogger(VuilniswagenResource.class);

    private static final String ENTITY_NAME = "vuilniswagen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VuilniswagenRepository vuilniswagenRepository;

    public VuilniswagenResource(VuilniswagenRepository vuilniswagenRepository) {
        this.vuilniswagenRepository = vuilniswagenRepository;
    }

    /**
     * {@code POST  /vuilniswagens} : Create a new vuilniswagen.
     *
     * @param vuilniswagen the vuilniswagen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vuilniswagen, or with status {@code 400 (Bad Request)} if the vuilniswagen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vuilniswagen> createVuilniswagen(@Valid @RequestBody Vuilniswagen vuilniswagen) throws URISyntaxException {
        log.debug("REST request to save Vuilniswagen : {}", vuilniswagen);
        if (vuilniswagen.getId() != null) {
            throw new BadRequestAlertException("A new vuilniswagen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vuilniswagen = vuilniswagenRepository.save(vuilniswagen);
        return ResponseEntity.created(new URI("/api/vuilniswagens/" + vuilniswagen.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vuilniswagen.getId().toString()))
            .body(vuilniswagen);
    }

    /**
     * {@code PUT  /vuilniswagens/:id} : Updates an existing vuilniswagen.
     *
     * @param id the id of the vuilniswagen to save.
     * @param vuilniswagen the vuilniswagen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vuilniswagen,
     * or with status {@code 400 (Bad Request)} if the vuilniswagen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vuilniswagen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vuilniswagen> updateVuilniswagen(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vuilniswagen vuilniswagen
    ) throws URISyntaxException {
        log.debug("REST request to update Vuilniswagen : {}, {}", id, vuilniswagen);
        if (vuilniswagen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vuilniswagen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vuilniswagenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vuilniswagen = vuilniswagenRepository.save(vuilniswagen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vuilniswagen.getId().toString()))
            .body(vuilniswagen);
    }

    /**
     * {@code PATCH  /vuilniswagens/:id} : Partial updates given fields of an existing vuilniswagen, field will ignore if it is null
     *
     * @param id the id of the vuilniswagen to save.
     * @param vuilniswagen the vuilniswagen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vuilniswagen,
     * or with status {@code 400 (Bad Request)} if the vuilniswagen is not valid,
     * or with status {@code 404 (Not Found)} if the vuilniswagen is not found,
     * or with status {@code 500 (Internal Server Error)} if the vuilniswagen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vuilniswagen> partialUpdateVuilniswagen(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vuilniswagen vuilniswagen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vuilniswagen partially : {}, {}", id, vuilniswagen);
        if (vuilniswagen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vuilniswagen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vuilniswagenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vuilniswagen> result = vuilniswagenRepository
            .findById(vuilniswagen.getId())
            .map(existingVuilniswagen -> {
                if (vuilniswagen.getCode() != null) {
                    existingVuilniswagen.setCode(vuilniswagen.getCode());
                }
                if (vuilniswagen.getKenteken() != null) {
                    existingVuilniswagen.setKenteken(vuilniswagen.getKenteken());
                }
                if (vuilniswagen.getType() != null) {
                    existingVuilniswagen.setType(vuilniswagen.getType());
                }

                return existingVuilniswagen;
            })
            .map(vuilniswagenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vuilniswagen.getId().toString())
        );
    }

    /**
     * {@code GET  /vuilniswagens} : get all the vuilniswagens.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vuilniswagens in body.
     */
    @GetMapping("")
    public List<Vuilniswagen> getAllVuilniswagens(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Vuilniswagens");
        if (eagerload) {
            return vuilniswagenRepository.findAllWithEagerRelationships();
        } else {
            return vuilniswagenRepository.findAll();
        }
    }

    /**
     * {@code GET  /vuilniswagens/:id} : get the "id" vuilniswagen.
     *
     * @param id the id of the vuilniswagen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vuilniswagen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vuilniswagen> getVuilniswagen(@PathVariable("id") Long id) {
        log.debug("REST request to get Vuilniswagen : {}", id);
        Optional<Vuilniswagen> vuilniswagen = vuilniswagenRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(vuilniswagen);
    }

    /**
     * {@code DELETE  /vuilniswagens/:id} : delete the "id" vuilniswagen.
     *
     * @param id the id of the vuilniswagen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVuilniswagen(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vuilniswagen : {}", id);
        vuilniswagenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
