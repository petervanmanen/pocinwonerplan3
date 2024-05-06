package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Fraudegegevens;
import nl.ritense.demo.repository.FraudegegevensRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Fraudegegevens}.
 */
@RestController
@RequestMapping("/api/fraudegegevens")
@Transactional
public class FraudegegevensResource {

    private final Logger log = LoggerFactory.getLogger(FraudegegevensResource.class);

    private static final String ENTITY_NAME = "fraudegegevens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraudegegevensRepository fraudegegevensRepository;

    public FraudegegevensResource(FraudegegevensRepository fraudegegevensRepository) {
        this.fraudegegevensRepository = fraudegegevensRepository;
    }

    /**
     * {@code POST  /fraudegegevens} : Create a new fraudegegevens.
     *
     * @param fraudegegevens the fraudegegevens to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fraudegegevens, or with status {@code 400 (Bad Request)} if the fraudegegevens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Fraudegegevens> createFraudegegevens(@RequestBody Fraudegegevens fraudegegevens) throws URISyntaxException {
        log.debug("REST request to save Fraudegegevens : {}", fraudegegevens);
        if (fraudegegevens.getId() != null) {
            throw new BadRequestAlertException("A new fraudegegevens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fraudegegevens = fraudegegevensRepository.save(fraudegegevens);
        return ResponseEntity.created(new URI("/api/fraudegegevens/" + fraudegegevens.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, fraudegegevens.getId().toString()))
            .body(fraudegegevens);
    }

    /**
     * {@code PUT  /fraudegegevens/:id} : Updates an existing fraudegegevens.
     *
     * @param id the id of the fraudegegevens to save.
     * @param fraudegegevens the fraudegegevens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraudegegevens,
     * or with status {@code 400 (Bad Request)} if the fraudegegevens is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fraudegegevens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Fraudegegevens> updateFraudegegevens(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Fraudegegevens fraudegegevens
    ) throws URISyntaxException {
        log.debug("REST request to update Fraudegegevens : {}, {}", id, fraudegegevens);
        if (fraudegegevens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraudegegevens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraudegegevensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fraudegegevens = fraudegegevensRepository.save(fraudegegevens);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fraudegegevens.getId().toString()))
            .body(fraudegegevens);
    }

    /**
     * {@code PATCH  /fraudegegevens/:id} : Partial updates given fields of an existing fraudegegevens, field will ignore if it is null
     *
     * @param id the id of the fraudegegevens to save.
     * @param fraudegegevens the fraudegegevens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fraudegegevens,
     * or with status {@code 400 (Bad Request)} if the fraudegegevens is not valid,
     * or with status {@code 404 (Not Found)} if the fraudegegevens is not found,
     * or with status {@code 500 (Internal Server Error)} if the fraudegegevens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Fraudegegevens> partialUpdateFraudegegevens(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Fraudegegevens fraudegegevens
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fraudegegevens partially : {}, {}", id, fraudegegevens);
        if (fraudegegevens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fraudegegevens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fraudegegevensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Fraudegegevens> result = fraudegegevensRepository
            .findById(fraudegegevens.getId())
            .map(existingFraudegegevens -> {
                if (fraudegegevens.getBedragfraude() != null) {
                    existingFraudegegevens.setBedragfraude(fraudegegevens.getBedragfraude());
                }
                if (fraudegegevens.getDatumeindefraude() != null) {
                    existingFraudegegevens.setDatumeindefraude(fraudegegevens.getDatumeindefraude());
                }
                if (fraudegegevens.getDatumgeconstateerd() != null) {
                    existingFraudegegevens.setDatumgeconstateerd(fraudegegevens.getDatumgeconstateerd());
                }
                if (fraudegegevens.getDatumstartfraude() != null) {
                    existingFraudegegevens.setDatumstartfraude(fraudegegevens.getDatumstartfraude());
                }
                if (fraudegegevens.getVerrekening() != null) {
                    existingFraudegegevens.setVerrekening(fraudegegevens.getVerrekening());
                }
                if (fraudegegevens.getVorderingen() != null) {
                    existingFraudegegevens.setVorderingen(fraudegegevens.getVorderingen());
                }

                return existingFraudegegevens;
            })
            .map(fraudegegevensRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fraudegegevens.getId().toString())
        );
    }

    /**
     * {@code GET  /fraudegegevens} : get all the fraudegegevens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fraudegegevens in body.
     */
    @GetMapping("")
    public List<Fraudegegevens> getAllFraudegegevens() {
        log.debug("REST request to get all Fraudegegevens");
        return fraudegegevensRepository.findAll();
    }

    /**
     * {@code GET  /fraudegegevens/:id} : get the "id" fraudegegevens.
     *
     * @param id the id of the fraudegegevens to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fraudegegevens, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Fraudegegevens> getFraudegegevens(@PathVariable("id") Long id) {
        log.debug("REST request to get Fraudegegevens : {}", id);
        Optional<Fraudegegevens> fraudegegevens = fraudegegevensRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fraudegegevens);
    }

    /**
     * {@code DELETE  /fraudegegevens/:id} : delete the "id" fraudegegevens.
     *
     * @param id the id of the fraudegegevens to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFraudegegevens(@PathVariable("id") Long id) {
        log.debug("REST request to delete Fraudegegevens : {}", id);
        fraudegegevensRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
