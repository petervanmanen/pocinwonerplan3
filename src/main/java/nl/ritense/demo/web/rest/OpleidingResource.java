package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Opleiding;
import nl.ritense.demo.repository.OpleidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Opleiding}.
 */
@RestController
@RequestMapping("/api/opleidings")
@Transactional
public class OpleidingResource {

    private final Logger log = LoggerFactory.getLogger(OpleidingResource.class);

    private static final String ENTITY_NAME = "opleiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpleidingRepository opleidingRepository;

    public OpleidingResource(OpleidingRepository opleidingRepository) {
        this.opleidingRepository = opleidingRepository;
    }

    /**
     * {@code POST  /opleidings} : Create a new opleiding.
     *
     * @param opleiding the opleiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opleiding, or with status {@code 400 (Bad Request)} if the opleiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Opleiding> createOpleiding(@Valid @RequestBody Opleiding opleiding) throws URISyntaxException {
        log.debug("REST request to save Opleiding : {}", opleiding);
        if (opleiding.getId() != null) {
            throw new BadRequestAlertException("A new opleiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opleiding = opleidingRepository.save(opleiding);
        return ResponseEntity.created(new URI("/api/opleidings/" + opleiding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, opleiding.getId().toString()))
            .body(opleiding);
    }

    /**
     * {@code PUT  /opleidings/:id} : Updates an existing opleiding.
     *
     * @param id the id of the opleiding to save.
     * @param opleiding the opleiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opleiding,
     * or with status {@code 400 (Bad Request)} if the opleiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opleiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Opleiding> updateOpleiding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Opleiding opleiding
    ) throws URISyntaxException {
        log.debug("REST request to update Opleiding : {}, {}", id, opleiding);
        if (opleiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opleiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opleidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        opleiding = opleidingRepository.save(opleiding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opleiding.getId().toString()))
            .body(opleiding);
    }

    /**
     * {@code PATCH  /opleidings/:id} : Partial updates given fields of an existing opleiding, field will ignore if it is null
     *
     * @param id the id of the opleiding to save.
     * @param opleiding the opleiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opleiding,
     * or with status {@code 400 (Bad Request)} if the opleiding is not valid,
     * or with status {@code 404 (Not Found)} if the opleiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the opleiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Opleiding> partialUpdateOpleiding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Opleiding opleiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Opleiding partially : {}, {}", id, opleiding);
        if (opleiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opleiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opleidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Opleiding> result = opleidingRepository
            .findById(opleiding.getId())
            .map(existingOpleiding -> {
                if (opleiding.getInstituut() != null) {
                    existingOpleiding.setInstituut(opleiding.getInstituut());
                }
                if (opleiding.getNaam() != null) {
                    existingOpleiding.setNaam(opleiding.getNaam());
                }
                if (opleiding.getOmschrijving() != null) {
                    existingOpleiding.setOmschrijving(opleiding.getOmschrijving());
                }
                if (opleiding.getPrijs() != null) {
                    existingOpleiding.setPrijs(opleiding.getPrijs());
                }

                return existingOpleiding;
            })
            .map(opleidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opleiding.getId().toString())
        );
    }

    /**
     * {@code GET  /opleidings} : get all the opleidings.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opleidings in body.
     */
    @GetMapping("")
    public List<Opleiding> getAllOpleidings(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Opleidings");
        if (eagerload) {
            return opleidingRepository.findAllWithEagerRelationships();
        } else {
            return opleidingRepository.findAll();
        }
    }

    /**
     * {@code GET  /opleidings/:id} : get the "id" opleiding.
     *
     * @param id the id of the opleiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opleiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Opleiding> getOpleiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Opleiding : {}", id);
        Optional<Opleiding> opleiding = opleidingRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(opleiding);
    }

    /**
     * {@code DELETE  /opleidings/:id} : delete the "id" opleiding.
     *
     * @param id the id of the opleiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpleiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Opleiding : {}", id);
        opleidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
