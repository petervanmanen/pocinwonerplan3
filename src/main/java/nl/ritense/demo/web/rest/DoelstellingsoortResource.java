package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Doelstellingsoort;
import nl.ritense.demo.repository.DoelstellingsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Doelstellingsoort}.
 */
@RestController
@RequestMapping("/api/doelstellingsoorts")
@Transactional
public class DoelstellingsoortResource {

    private final Logger log = LoggerFactory.getLogger(DoelstellingsoortResource.class);

    private static final String ENTITY_NAME = "doelstellingsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoelstellingsoortRepository doelstellingsoortRepository;

    public DoelstellingsoortResource(DoelstellingsoortRepository doelstellingsoortRepository) {
        this.doelstellingsoortRepository = doelstellingsoortRepository;
    }

    /**
     * {@code POST  /doelstellingsoorts} : Create a new doelstellingsoort.
     *
     * @param doelstellingsoort the doelstellingsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doelstellingsoort, or with status {@code 400 (Bad Request)} if the doelstellingsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Doelstellingsoort> createDoelstellingsoort(@Valid @RequestBody Doelstellingsoort doelstellingsoort)
        throws URISyntaxException {
        log.debug("REST request to save Doelstellingsoort : {}", doelstellingsoort);
        if (doelstellingsoort.getId() != null) {
            throw new BadRequestAlertException("A new doelstellingsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        doelstellingsoort = doelstellingsoortRepository.save(doelstellingsoort);
        return ResponseEntity.created(new URI("/api/doelstellingsoorts/" + doelstellingsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, doelstellingsoort.getId().toString()))
            .body(doelstellingsoort);
    }

    /**
     * {@code PUT  /doelstellingsoorts/:id} : Updates an existing doelstellingsoort.
     *
     * @param id the id of the doelstellingsoort to save.
     * @param doelstellingsoort the doelstellingsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doelstellingsoort,
     * or with status {@code 400 (Bad Request)} if the doelstellingsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doelstellingsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doelstellingsoort> updateDoelstellingsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Doelstellingsoort doelstellingsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Doelstellingsoort : {}, {}", id, doelstellingsoort);
        if (doelstellingsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doelstellingsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doelstellingsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        doelstellingsoort = doelstellingsoortRepository.save(doelstellingsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doelstellingsoort.getId().toString()))
            .body(doelstellingsoort);
    }

    /**
     * {@code PATCH  /doelstellingsoorts/:id} : Partial updates given fields of an existing doelstellingsoort, field will ignore if it is null
     *
     * @param id the id of the doelstellingsoort to save.
     * @param doelstellingsoort the doelstellingsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doelstellingsoort,
     * or with status {@code 400 (Bad Request)} if the doelstellingsoort is not valid,
     * or with status {@code 404 (Not Found)} if the doelstellingsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the doelstellingsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Doelstellingsoort> partialUpdateDoelstellingsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Doelstellingsoort doelstellingsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Doelstellingsoort partially : {}, {}", id, doelstellingsoort);
        if (doelstellingsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doelstellingsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doelstellingsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Doelstellingsoort> result = doelstellingsoortRepository
            .findById(doelstellingsoort.getId())
            .map(existingDoelstellingsoort -> {
                if (doelstellingsoort.getNaam() != null) {
                    existingDoelstellingsoort.setNaam(doelstellingsoort.getNaam());
                }
                if (doelstellingsoort.getOmschrijving() != null) {
                    existingDoelstellingsoort.setOmschrijving(doelstellingsoort.getOmschrijving());
                }

                return existingDoelstellingsoort;
            })
            .map(doelstellingsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doelstellingsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /doelstellingsoorts} : get all the doelstellingsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doelstellingsoorts in body.
     */
    @GetMapping("")
    public List<Doelstellingsoort> getAllDoelstellingsoorts() {
        log.debug("REST request to get all Doelstellingsoorts");
        return doelstellingsoortRepository.findAll();
    }

    /**
     * {@code GET  /doelstellingsoorts/:id} : get the "id" doelstellingsoort.
     *
     * @param id the id of the doelstellingsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doelstellingsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doelstellingsoort> getDoelstellingsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Doelstellingsoort : {}", id);
        Optional<Doelstellingsoort> doelstellingsoort = doelstellingsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(doelstellingsoort);
    }

    /**
     * {@code DELETE  /doelstellingsoorts/:id} : delete the "id" doelstellingsoort.
     *
     * @param id the id of the doelstellingsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoelstellingsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Doelstellingsoort : {}", id);
        doelstellingsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
