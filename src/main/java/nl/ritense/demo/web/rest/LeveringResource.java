package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Levering;
import nl.ritense.demo.repository.LeveringRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Levering}.
 */
@RestController
@RequestMapping("/api/leverings")
@Transactional
public class LeveringResource {

    private final Logger log = LoggerFactory.getLogger(LeveringResource.class);

    private static final String ENTITY_NAME = "levering";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeveringRepository leveringRepository;

    public LeveringResource(LeveringRepository leveringRepository) {
        this.leveringRepository = leveringRepository;
    }

    /**
     * {@code POST  /leverings} : Create a new levering.
     *
     * @param levering the levering to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levering, or with status {@code 400 (Bad Request)} if the levering has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Levering> createLevering(@Valid @RequestBody Levering levering) throws URISyntaxException {
        log.debug("REST request to save Levering : {}", levering);
        if (levering.getId() != null) {
            throw new BadRequestAlertException("A new levering cannot already have an ID", ENTITY_NAME, "idexists");
        }
        levering = leveringRepository.save(levering);
        return ResponseEntity.created(new URI("/api/leverings/" + levering.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, levering.getId().toString()))
            .body(levering);
    }

    /**
     * {@code PUT  /leverings/:id} : Updates an existing levering.
     *
     * @param id the id of the levering to save.
     * @param levering the levering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levering,
     * or with status {@code 400 (Bad Request)} if the levering is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Levering> updateLevering(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Levering levering
    ) throws URISyntaxException {
        log.debug("REST request to update Levering : {}, {}", id, levering);
        if (levering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leveringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        levering = leveringRepository.save(levering);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, levering.getId().toString()))
            .body(levering);
    }

    /**
     * {@code PATCH  /leverings/:id} : Partial updates given fields of an existing levering, field will ignore if it is null
     *
     * @param id the id of the levering to save.
     * @param levering the levering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levering,
     * or with status {@code 400 (Bad Request)} if the levering is not valid,
     * or with status {@code 404 (Not Found)} if the levering is not found,
     * or with status {@code 500 (Internal Server Error)} if the levering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Levering> partialUpdateLevering(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Levering levering
    ) throws URISyntaxException {
        log.debug("REST request to partial update Levering partially : {}, {}", id, levering);
        if (levering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, levering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leveringRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Levering> result = leveringRepository
            .findById(levering.getId())
            .map(existingLevering -> {
                if (levering.getCode() != null) {
                    existingLevering.setCode(levering.getCode());
                }
                if (levering.getDatumstart() != null) {
                    existingLevering.setDatumstart(levering.getDatumstart());
                }
                if (levering.getDatumstop() != null) {
                    existingLevering.setDatumstop(levering.getDatumstop());
                }
                if (levering.getEenheid() != null) {
                    existingLevering.setEenheid(levering.getEenheid());
                }
                if (levering.getFrequentie() != null) {
                    existingLevering.setFrequentie(levering.getFrequentie());
                }
                if (levering.getOmvang() != null) {
                    existingLevering.setOmvang(levering.getOmvang());
                }
                if (levering.getStopreden() != null) {
                    existingLevering.setStopreden(levering.getStopreden());
                }

                return existingLevering;
            })
            .map(leveringRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, levering.getId().toString())
        );
    }

    /**
     * {@code GET  /leverings} : get all the leverings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leverings in body.
     */
    @GetMapping("")
    public List<Levering> getAllLeverings() {
        log.debug("REST request to get all Leverings");
        return leveringRepository.findAll();
    }

    /**
     * {@code GET  /leverings/:id} : get the "id" levering.
     *
     * @param id the id of the levering to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levering, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Levering> getLevering(@PathVariable("id") Long id) {
        log.debug("REST request to get Levering : {}", id);
        Optional<Levering> levering = leveringRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(levering);
    }

    /**
     * {@code DELETE  /leverings/:id} : delete the "id" levering.
     *
     * @param id the id of the levering to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLevering(@PathVariable("id") Long id) {
        log.debug("REST request to delete Levering : {}", id);
        leveringRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
