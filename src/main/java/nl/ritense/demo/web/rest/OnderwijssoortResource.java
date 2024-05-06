package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Onderwijssoort;
import nl.ritense.demo.repository.OnderwijssoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Onderwijssoort}.
 */
@RestController
@RequestMapping("/api/onderwijssoorts")
@Transactional
public class OnderwijssoortResource {

    private final Logger log = LoggerFactory.getLogger(OnderwijssoortResource.class);

    private static final String ENTITY_NAME = "onderwijssoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnderwijssoortRepository onderwijssoortRepository;

    public OnderwijssoortResource(OnderwijssoortRepository onderwijssoortRepository) {
        this.onderwijssoortRepository = onderwijssoortRepository;
    }

    /**
     * {@code POST  /onderwijssoorts} : Create a new onderwijssoort.
     *
     * @param onderwijssoort the onderwijssoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onderwijssoort, or with status {@code 400 (Bad Request)} if the onderwijssoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Onderwijssoort> createOnderwijssoort(@Valid @RequestBody Onderwijssoort onderwijssoort)
        throws URISyntaxException {
        log.debug("REST request to save Onderwijssoort : {}", onderwijssoort);
        if (onderwijssoort.getId() != null) {
            throw new BadRequestAlertException("A new onderwijssoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        onderwijssoort = onderwijssoortRepository.save(onderwijssoort);
        return ResponseEntity.created(new URI("/api/onderwijssoorts/" + onderwijssoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, onderwijssoort.getId().toString()))
            .body(onderwijssoort);
    }

    /**
     * {@code PUT  /onderwijssoorts/:id} : Updates an existing onderwijssoort.
     *
     * @param id the id of the onderwijssoort to save.
     * @param onderwijssoort the onderwijssoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderwijssoort,
     * or with status {@code 400 (Bad Request)} if the onderwijssoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the onderwijssoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Onderwijssoort> updateOnderwijssoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Onderwijssoort onderwijssoort
    ) throws URISyntaxException {
        log.debug("REST request to update Onderwijssoort : {}, {}", id, onderwijssoort);
        if (onderwijssoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderwijssoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderwijssoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        onderwijssoort = onderwijssoortRepository.save(onderwijssoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onderwijssoort.getId().toString()))
            .body(onderwijssoort);
    }

    /**
     * {@code PATCH  /onderwijssoorts/:id} : Partial updates given fields of an existing onderwijssoort, field will ignore if it is null
     *
     * @param id the id of the onderwijssoort to save.
     * @param onderwijssoort the onderwijssoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderwijssoort,
     * or with status {@code 400 (Bad Request)} if the onderwijssoort is not valid,
     * or with status {@code 404 (Not Found)} if the onderwijssoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the onderwijssoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Onderwijssoort> partialUpdateOnderwijssoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Onderwijssoort onderwijssoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Onderwijssoort partially : {}, {}", id, onderwijssoort);
        if (onderwijssoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderwijssoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderwijssoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Onderwijssoort> result = onderwijssoortRepository
            .findById(onderwijssoort.getId())
            .map(existingOnderwijssoort -> {
                if (onderwijssoort.getOmschrijving() != null) {
                    existingOnderwijssoort.setOmschrijving(onderwijssoort.getOmschrijving());
                }
                if (onderwijssoort.getOnderwijstype() != null) {
                    existingOnderwijssoort.setOnderwijstype(onderwijssoort.getOnderwijstype());
                }

                return existingOnderwijssoort;
            })
            .map(onderwijssoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onderwijssoort.getId().toString())
        );
    }

    /**
     * {@code GET  /onderwijssoorts} : get all the onderwijssoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onderwijssoorts in body.
     */
    @GetMapping("")
    public List<Onderwijssoort> getAllOnderwijssoorts() {
        log.debug("REST request to get all Onderwijssoorts");
        return onderwijssoortRepository.findAll();
    }

    /**
     * {@code GET  /onderwijssoorts/:id} : get the "id" onderwijssoort.
     *
     * @param id the id of the onderwijssoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onderwijssoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Onderwijssoort> getOnderwijssoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Onderwijssoort : {}", id);
        Optional<Onderwijssoort> onderwijssoort = onderwijssoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onderwijssoort);
    }

    /**
     * {@code DELETE  /onderwijssoorts/:id} : delete the "id" onderwijssoort.
     *
     * @param id the id of the onderwijssoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderwijssoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Onderwijssoort : {}", id);
        onderwijssoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
