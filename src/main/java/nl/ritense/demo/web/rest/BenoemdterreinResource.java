package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Benoemdterrein;
import nl.ritense.demo.repository.BenoemdterreinRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Benoemdterrein}.
 */
@RestController
@RequestMapping("/api/benoemdterreins")
@Transactional
public class BenoemdterreinResource {

    private final Logger log = LoggerFactory.getLogger(BenoemdterreinResource.class);

    private static final String ENTITY_NAME = "benoemdterrein";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenoemdterreinRepository benoemdterreinRepository;

    public BenoemdterreinResource(BenoemdterreinRepository benoemdterreinRepository) {
        this.benoemdterreinRepository = benoemdterreinRepository;
    }

    /**
     * {@code POST  /benoemdterreins} : Create a new benoemdterrein.
     *
     * @param benoemdterrein the benoemdterrein to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benoemdterrein, or with status {@code 400 (Bad Request)} if the benoemdterrein has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Benoemdterrein> createBenoemdterrein(@Valid @RequestBody Benoemdterrein benoemdterrein)
        throws URISyntaxException {
        log.debug("REST request to save Benoemdterrein : {}", benoemdterrein);
        if (benoemdterrein.getId() != null) {
            throw new BadRequestAlertException("A new benoemdterrein cannot already have an ID", ENTITY_NAME, "idexists");
        }
        benoemdterrein = benoemdterreinRepository.save(benoemdterrein);
        return ResponseEntity.created(new URI("/api/benoemdterreins/" + benoemdterrein.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, benoemdterrein.getId().toString()))
            .body(benoemdterrein);
    }

    /**
     * {@code PUT  /benoemdterreins/:id} : Updates an existing benoemdterrein.
     *
     * @param id the id of the benoemdterrein to save.
     * @param benoemdterrein the benoemdterrein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benoemdterrein,
     * or with status {@code 400 (Bad Request)} if the benoemdterrein is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benoemdterrein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Benoemdterrein> updateBenoemdterrein(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Benoemdterrein benoemdterrein
    ) throws URISyntaxException {
        log.debug("REST request to update Benoemdterrein : {}, {}", id, benoemdterrein);
        if (benoemdterrein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benoemdterrein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benoemdterreinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        benoemdterrein = benoemdterreinRepository.save(benoemdterrein);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benoemdterrein.getId().toString()))
            .body(benoemdterrein);
    }

    /**
     * {@code PATCH  /benoemdterreins/:id} : Partial updates given fields of an existing benoemdterrein, field will ignore if it is null
     *
     * @param id the id of the benoemdterrein to save.
     * @param benoemdterrein the benoemdterrein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benoemdterrein,
     * or with status {@code 400 (Bad Request)} if the benoemdterrein is not valid,
     * or with status {@code 404 (Not Found)} if the benoemdterrein is not found,
     * or with status {@code 500 (Internal Server Error)} if the benoemdterrein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Benoemdterrein> partialUpdateBenoemdterrein(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Benoemdterrein benoemdterrein
    ) throws URISyntaxException {
        log.debug("REST request to partial update Benoemdterrein partially : {}, {}", id, benoemdterrein);
        if (benoemdterrein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benoemdterrein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benoemdterreinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Benoemdterrein> result = benoemdterreinRepository
            .findById(benoemdterrein.getId())
            .map(existingBenoemdterrein -> {
                if (benoemdterrein.getIdentificatie() != null) {
                    existingBenoemdterrein.setIdentificatie(benoemdterrein.getIdentificatie());
                }

                return existingBenoemdterrein;
            })
            .map(benoemdterreinRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benoemdterrein.getId().toString())
        );
    }

    /**
     * {@code GET  /benoemdterreins} : get all the benoemdterreins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benoemdterreins in body.
     */
    @GetMapping("")
    public List<Benoemdterrein> getAllBenoemdterreins() {
        log.debug("REST request to get all Benoemdterreins");
        return benoemdterreinRepository.findAll();
    }

    /**
     * {@code GET  /benoemdterreins/:id} : get the "id" benoemdterrein.
     *
     * @param id the id of the benoemdterrein to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benoemdterrein, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Benoemdterrein> getBenoemdterrein(@PathVariable("id") Long id) {
        log.debug("REST request to get Benoemdterrein : {}", id);
        Optional<Benoemdterrein> benoemdterrein = benoemdterreinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(benoemdterrein);
    }

    /**
     * {@code DELETE  /benoemdterreins/:id} : delete the "id" benoemdterrein.
     *
     * @param id the id of the benoemdterrein to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBenoemdterrein(@PathVariable("id") Long id) {
        log.debug("REST request to delete Benoemdterrein : {}", id);
        benoemdterreinRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
