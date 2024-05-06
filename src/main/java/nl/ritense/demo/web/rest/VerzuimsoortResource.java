package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verzuimsoort;
import nl.ritense.demo.repository.VerzuimsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verzuimsoort}.
 */
@RestController
@RequestMapping("/api/verzuimsoorts")
@Transactional
public class VerzuimsoortResource {

    private final Logger log = LoggerFactory.getLogger(VerzuimsoortResource.class);

    private static final String ENTITY_NAME = "verzuimsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerzuimsoortRepository verzuimsoortRepository;

    public VerzuimsoortResource(VerzuimsoortRepository verzuimsoortRepository) {
        this.verzuimsoortRepository = verzuimsoortRepository;
    }

    /**
     * {@code POST  /verzuimsoorts} : Create a new verzuimsoort.
     *
     * @param verzuimsoort the verzuimsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verzuimsoort, or with status {@code 400 (Bad Request)} if the verzuimsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verzuimsoort> createVerzuimsoort(@Valid @RequestBody Verzuimsoort verzuimsoort) throws URISyntaxException {
        log.debug("REST request to save Verzuimsoort : {}", verzuimsoort);
        if (verzuimsoort.getId() != null) {
            throw new BadRequestAlertException("A new verzuimsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verzuimsoort = verzuimsoortRepository.save(verzuimsoort);
        return ResponseEntity.created(new URI("/api/verzuimsoorts/" + verzuimsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verzuimsoort.getId().toString()))
            .body(verzuimsoort);
    }

    /**
     * {@code PUT  /verzuimsoorts/:id} : Updates an existing verzuimsoort.
     *
     * @param id the id of the verzuimsoort to save.
     * @param verzuimsoort the verzuimsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzuimsoort,
     * or with status {@code 400 (Bad Request)} if the verzuimsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verzuimsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verzuimsoort> updateVerzuimsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verzuimsoort verzuimsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Verzuimsoort : {}, {}", id, verzuimsoort);
        if (verzuimsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzuimsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzuimsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verzuimsoort = verzuimsoortRepository.save(verzuimsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzuimsoort.getId().toString()))
            .body(verzuimsoort);
    }

    /**
     * {@code PATCH  /verzuimsoorts/:id} : Partial updates given fields of an existing verzuimsoort, field will ignore if it is null
     *
     * @param id the id of the verzuimsoort to save.
     * @param verzuimsoort the verzuimsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzuimsoort,
     * or with status {@code 400 (Bad Request)} if the verzuimsoort is not valid,
     * or with status {@code 404 (Not Found)} if the verzuimsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the verzuimsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verzuimsoort> partialUpdateVerzuimsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verzuimsoort verzuimsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verzuimsoort partially : {}, {}", id, verzuimsoort);
        if (verzuimsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzuimsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzuimsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verzuimsoort> result = verzuimsoortRepository
            .findById(verzuimsoort.getId())
            .map(existingVerzuimsoort -> {
                if (verzuimsoort.getNaam() != null) {
                    existingVerzuimsoort.setNaam(verzuimsoort.getNaam());
                }
                if (verzuimsoort.getOmschrijving() != null) {
                    existingVerzuimsoort.setOmschrijving(verzuimsoort.getOmschrijving());
                }

                return existingVerzuimsoort;
            })
            .map(verzuimsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzuimsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /verzuimsoorts} : get all the verzuimsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verzuimsoorts in body.
     */
    @GetMapping("")
    public List<Verzuimsoort> getAllVerzuimsoorts() {
        log.debug("REST request to get all Verzuimsoorts");
        return verzuimsoortRepository.findAll();
    }

    /**
     * {@code GET  /verzuimsoorts/:id} : get the "id" verzuimsoort.
     *
     * @param id the id of the verzuimsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verzuimsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verzuimsoort> getVerzuimsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Verzuimsoort : {}", id);
        Optional<Verzuimsoort> verzuimsoort = verzuimsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verzuimsoort);
    }

    /**
     * {@code DELETE  /verzuimsoorts/:id} : delete the "id" verzuimsoort.
     *
     * @param id the id of the verzuimsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerzuimsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verzuimsoort : {}", id);
        verzuimsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
