package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Tentoonstelling;
import nl.ritense.demo.repository.TentoonstellingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Tentoonstelling}.
 */
@RestController
@RequestMapping("/api/tentoonstellings")
@Transactional
public class TentoonstellingResource {

    private final Logger log = LoggerFactory.getLogger(TentoonstellingResource.class);

    private static final String ENTITY_NAME = "tentoonstelling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TentoonstellingRepository tentoonstellingRepository;

    public TentoonstellingResource(TentoonstellingRepository tentoonstellingRepository) {
        this.tentoonstellingRepository = tentoonstellingRepository;
    }

    /**
     * {@code POST  /tentoonstellings} : Create a new tentoonstelling.
     *
     * @param tentoonstelling the tentoonstelling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tentoonstelling, or with status {@code 400 (Bad Request)} if the tentoonstelling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tentoonstelling> createTentoonstelling(@RequestBody Tentoonstelling tentoonstelling) throws URISyntaxException {
        log.debug("REST request to save Tentoonstelling : {}", tentoonstelling);
        if (tentoonstelling.getId() != null) {
            throw new BadRequestAlertException("A new tentoonstelling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tentoonstelling = tentoonstellingRepository.save(tentoonstelling);
        return ResponseEntity.created(new URI("/api/tentoonstellings/" + tentoonstelling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tentoonstelling.getId().toString()))
            .body(tentoonstelling);
    }

    /**
     * {@code PUT  /tentoonstellings/:id} : Updates an existing tentoonstelling.
     *
     * @param id the id of the tentoonstelling to save.
     * @param tentoonstelling the tentoonstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tentoonstelling,
     * or with status {@code 400 (Bad Request)} if the tentoonstelling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tentoonstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tentoonstelling> updateTentoonstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tentoonstelling tentoonstelling
    ) throws URISyntaxException {
        log.debug("REST request to update Tentoonstelling : {}, {}", id, tentoonstelling);
        if (tentoonstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tentoonstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tentoonstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tentoonstelling = tentoonstellingRepository.save(tentoonstelling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tentoonstelling.getId().toString()))
            .body(tentoonstelling);
    }

    /**
     * {@code PATCH  /tentoonstellings/:id} : Partial updates given fields of an existing tentoonstelling, field will ignore if it is null
     *
     * @param id the id of the tentoonstelling to save.
     * @param tentoonstelling the tentoonstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tentoonstelling,
     * or with status {@code 400 (Bad Request)} if the tentoonstelling is not valid,
     * or with status {@code 404 (Not Found)} if the tentoonstelling is not found,
     * or with status {@code 500 (Internal Server Error)} if the tentoonstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tentoonstelling> partialUpdateTentoonstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tentoonstelling tentoonstelling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tentoonstelling partially : {}, {}", id, tentoonstelling);
        if (tentoonstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tentoonstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tentoonstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tentoonstelling> result = tentoonstellingRepository
            .findById(tentoonstelling.getId())
            .map(existingTentoonstelling -> {
                if (tentoonstelling.getDatumeinde() != null) {
                    existingTentoonstelling.setDatumeinde(tentoonstelling.getDatumeinde());
                }
                if (tentoonstelling.getDatumstart() != null) {
                    existingTentoonstelling.setDatumstart(tentoonstelling.getDatumstart());
                }
                if (tentoonstelling.getOmschrijving() != null) {
                    existingTentoonstelling.setOmschrijving(tentoonstelling.getOmschrijving());
                }
                if (tentoonstelling.getSubtitel() != null) {
                    existingTentoonstelling.setSubtitel(tentoonstelling.getSubtitel());
                }
                if (tentoonstelling.getTitel() != null) {
                    existingTentoonstelling.setTitel(tentoonstelling.getTitel());
                }

                return existingTentoonstelling;
            })
            .map(tentoonstellingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tentoonstelling.getId().toString())
        );
    }

    /**
     * {@code GET  /tentoonstellings} : get all the tentoonstellings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tentoonstellings in body.
     */
    @GetMapping("")
    public List<Tentoonstelling> getAllTentoonstellings() {
        log.debug("REST request to get all Tentoonstellings");
        return tentoonstellingRepository.findAll();
    }

    /**
     * {@code GET  /tentoonstellings/:id} : get the "id" tentoonstelling.
     *
     * @param id the id of the tentoonstelling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tentoonstelling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tentoonstelling> getTentoonstelling(@PathVariable("id") Long id) {
        log.debug("REST request to get Tentoonstelling : {}", id);
        Optional<Tentoonstelling> tentoonstelling = tentoonstellingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tentoonstelling);
    }

    /**
     * {@code DELETE  /tentoonstellings/:id} : delete the "id" tentoonstelling.
     *
     * @param id the id of the tentoonstelling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTentoonstelling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tentoonstelling : {}", id);
        tentoonstellingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
