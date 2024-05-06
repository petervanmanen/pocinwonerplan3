package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Voorzieningsoort;
import nl.ritense.demo.repository.VoorzieningsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Voorzieningsoort}.
 */
@RestController
@RequestMapping("/api/voorzieningsoorts")
@Transactional
public class VoorzieningsoortResource {

    private final Logger log = LoggerFactory.getLogger(VoorzieningsoortResource.class);

    private static final String ENTITY_NAME = "voorzieningsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoorzieningsoortRepository voorzieningsoortRepository;

    public VoorzieningsoortResource(VoorzieningsoortRepository voorzieningsoortRepository) {
        this.voorzieningsoortRepository = voorzieningsoortRepository;
    }

    /**
     * {@code POST  /voorzieningsoorts} : Create a new voorzieningsoort.
     *
     * @param voorzieningsoort the voorzieningsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voorzieningsoort, or with status {@code 400 (Bad Request)} if the voorzieningsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Voorzieningsoort> createVoorzieningsoort(@Valid @RequestBody Voorzieningsoort voorzieningsoort)
        throws URISyntaxException {
        log.debug("REST request to save Voorzieningsoort : {}", voorzieningsoort);
        if (voorzieningsoort.getId() != null) {
            throw new BadRequestAlertException("A new voorzieningsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        voorzieningsoort = voorzieningsoortRepository.save(voorzieningsoort);
        return ResponseEntity.created(new URI("/api/voorzieningsoorts/" + voorzieningsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, voorzieningsoort.getId().toString()))
            .body(voorzieningsoort);
    }

    /**
     * {@code PUT  /voorzieningsoorts/:id} : Updates an existing voorzieningsoort.
     *
     * @param id the id of the voorzieningsoort to save.
     * @param voorzieningsoort the voorzieningsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voorzieningsoort,
     * or with status {@code 400 (Bad Request)} if the voorzieningsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voorzieningsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Voorzieningsoort> updateVoorzieningsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Voorzieningsoort voorzieningsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Voorzieningsoort : {}, {}", id, voorzieningsoort);
        if (voorzieningsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voorzieningsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voorzieningsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        voorzieningsoort = voorzieningsoortRepository.save(voorzieningsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voorzieningsoort.getId().toString()))
            .body(voorzieningsoort);
    }

    /**
     * {@code PATCH  /voorzieningsoorts/:id} : Partial updates given fields of an existing voorzieningsoort, field will ignore if it is null
     *
     * @param id the id of the voorzieningsoort to save.
     * @param voorzieningsoort the voorzieningsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voorzieningsoort,
     * or with status {@code 400 (Bad Request)} if the voorzieningsoort is not valid,
     * or with status {@code 404 (Not Found)} if the voorzieningsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the voorzieningsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Voorzieningsoort> partialUpdateVoorzieningsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Voorzieningsoort voorzieningsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Voorzieningsoort partially : {}, {}", id, voorzieningsoort);
        if (voorzieningsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voorzieningsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voorzieningsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Voorzieningsoort> result = voorzieningsoortRepository
            .findById(voorzieningsoort.getId())
            .map(existingVoorzieningsoort -> {
                if (voorzieningsoort.getCode() != null) {
                    existingVoorzieningsoort.setCode(voorzieningsoort.getCode());
                }
                if (voorzieningsoort.getNaam() != null) {
                    existingVoorzieningsoort.setNaam(voorzieningsoort.getNaam());
                }
                if (voorzieningsoort.getOmschrijving() != null) {
                    existingVoorzieningsoort.setOmschrijving(voorzieningsoort.getOmschrijving());
                }
                if (voorzieningsoort.getProductcategorie() != null) {
                    existingVoorzieningsoort.setProductcategorie(voorzieningsoort.getProductcategorie());
                }
                if (voorzieningsoort.getProductcategoriecode() != null) {
                    existingVoorzieningsoort.setProductcategoriecode(voorzieningsoort.getProductcategoriecode());
                }
                if (voorzieningsoort.getProductcode() != null) {
                    existingVoorzieningsoort.setProductcode(voorzieningsoort.getProductcode());
                }
                if (voorzieningsoort.getWet() != null) {
                    existingVoorzieningsoort.setWet(voorzieningsoort.getWet());
                }

                return existingVoorzieningsoort;
            })
            .map(voorzieningsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voorzieningsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /voorzieningsoorts} : get all the voorzieningsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voorzieningsoorts in body.
     */
    @GetMapping("")
    public List<Voorzieningsoort> getAllVoorzieningsoorts() {
        log.debug("REST request to get all Voorzieningsoorts");
        return voorzieningsoortRepository.findAll();
    }

    /**
     * {@code GET  /voorzieningsoorts/:id} : get the "id" voorzieningsoort.
     *
     * @param id the id of the voorzieningsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voorzieningsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Voorzieningsoort> getVoorzieningsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Voorzieningsoort : {}", id);
        Optional<Voorzieningsoort> voorzieningsoort = voorzieningsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(voorzieningsoort);
    }

    /**
     * {@code DELETE  /voorzieningsoorts/:id} : delete the "id" voorzieningsoort.
     *
     * @param id the id of the voorzieningsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoorzieningsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Voorzieningsoort : {}", id);
        voorzieningsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
