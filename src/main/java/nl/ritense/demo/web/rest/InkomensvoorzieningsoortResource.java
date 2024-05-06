package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Inkomensvoorzieningsoort;
import nl.ritense.demo.repository.InkomensvoorzieningsoortRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inkomensvoorzieningsoort}.
 */
@RestController
@RequestMapping("/api/inkomensvoorzieningsoorts")
@Transactional
public class InkomensvoorzieningsoortResource {

    private final Logger log = LoggerFactory.getLogger(InkomensvoorzieningsoortResource.class);

    private static final String ENTITY_NAME = "inkomensvoorzieningsoort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InkomensvoorzieningsoortRepository inkomensvoorzieningsoortRepository;

    public InkomensvoorzieningsoortResource(InkomensvoorzieningsoortRepository inkomensvoorzieningsoortRepository) {
        this.inkomensvoorzieningsoortRepository = inkomensvoorzieningsoortRepository;
    }

    /**
     * {@code POST  /inkomensvoorzieningsoorts} : Create a new inkomensvoorzieningsoort.
     *
     * @param inkomensvoorzieningsoort the inkomensvoorzieningsoort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inkomensvoorzieningsoort, or with status {@code 400 (Bad Request)} if the inkomensvoorzieningsoort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inkomensvoorzieningsoort> createInkomensvoorzieningsoort(
        @Valid @RequestBody Inkomensvoorzieningsoort inkomensvoorzieningsoort
    ) throws URISyntaxException {
        log.debug("REST request to save Inkomensvoorzieningsoort : {}", inkomensvoorzieningsoort);
        if (inkomensvoorzieningsoort.getId() != null) {
            throw new BadRequestAlertException("A new inkomensvoorzieningsoort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inkomensvoorzieningsoort = inkomensvoorzieningsoortRepository.save(inkomensvoorzieningsoort);
        return ResponseEntity.created(new URI("/api/inkomensvoorzieningsoorts/" + inkomensvoorzieningsoort.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inkomensvoorzieningsoort.getId().toString()))
            .body(inkomensvoorzieningsoort);
    }

    /**
     * {@code PUT  /inkomensvoorzieningsoorts/:id} : Updates an existing inkomensvoorzieningsoort.
     *
     * @param id the id of the inkomensvoorzieningsoort to save.
     * @param inkomensvoorzieningsoort the inkomensvoorzieningsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inkomensvoorzieningsoort,
     * or with status {@code 400 (Bad Request)} if the inkomensvoorzieningsoort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inkomensvoorzieningsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inkomensvoorzieningsoort> updateInkomensvoorzieningsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Inkomensvoorzieningsoort inkomensvoorzieningsoort
    ) throws URISyntaxException {
        log.debug("REST request to update Inkomensvoorzieningsoort : {}, {}", id, inkomensvoorzieningsoort);
        if (inkomensvoorzieningsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inkomensvoorzieningsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inkomensvoorzieningsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inkomensvoorzieningsoort = inkomensvoorzieningsoortRepository.save(inkomensvoorzieningsoort);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inkomensvoorzieningsoort.getId().toString()))
            .body(inkomensvoorzieningsoort);
    }

    /**
     * {@code PATCH  /inkomensvoorzieningsoorts/:id} : Partial updates given fields of an existing inkomensvoorzieningsoort, field will ignore if it is null
     *
     * @param id the id of the inkomensvoorzieningsoort to save.
     * @param inkomensvoorzieningsoort the inkomensvoorzieningsoort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inkomensvoorzieningsoort,
     * or with status {@code 400 (Bad Request)} if the inkomensvoorzieningsoort is not valid,
     * or with status {@code 404 (Not Found)} if the inkomensvoorzieningsoort is not found,
     * or with status {@code 500 (Internal Server Error)} if the inkomensvoorzieningsoort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inkomensvoorzieningsoort> partialUpdateInkomensvoorzieningsoort(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Inkomensvoorzieningsoort inkomensvoorzieningsoort
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inkomensvoorzieningsoort partially : {}, {}", id, inkomensvoorzieningsoort);
        if (inkomensvoorzieningsoort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inkomensvoorzieningsoort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inkomensvoorzieningsoortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inkomensvoorzieningsoort> result = inkomensvoorzieningsoortRepository
            .findById(inkomensvoorzieningsoort.getId())
            .map(existingInkomensvoorzieningsoort -> {
                if (inkomensvoorzieningsoort.getCode() != null) {
                    existingInkomensvoorzieningsoort.setCode(inkomensvoorzieningsoort.getCode());
                }
                if (inkomensvoorzieningsoort.getNaam() != null) {
                    existingInkomensvoorzieningsoort.setNaam(inkomensvoorzieningsoort.getNaam());
                }
                if (inkomensvoorzieningsoort.getOmschrijving() != null) {
                    existingInkomensvoorzieningsoort.setOmschrijving(inkomensvoorzieningsoort.getOmschrijving());
                }
                if (inkomensvoorzieningsoort.getRegeling() != null) {
                    existingInkomensvoorzieningsoort.setRegeling(inkomensvoorzieningsoort.getRegeling());
                }
                if (inkomensvoorzieningsoort.getRegelingcode() != null) {
                    existingInkomensvoorzieningsoort.setRegelingcode(inkomensvoorzieningsoort.getRegelingcode());
                }
                if (inkomensvoorzieningsoort.getVergoeding() != null) {
                    existingInkomensvoorzieningsoort.setVergoeding(inkomensvoorzieningsoort.getVergoeding());
                }
                if (inkomensvoorzieningsoort.getVergoedingscode() != null) {
                    existingInkomensvoorzieningsoort.setVergoedingscode(inkomensvoorzieningsoort.getVergoedingscode());
                }
                if (inkomensvoorzieningsoort.getWet() != null) {
                    existingInkomensvoorzieningsoort.setWet(inkomensvoorzieningsoort.getWet());
                }

                return existingInkomensvoorzieningsoort;
            })
            .map(inkomensvoorzieningsoortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inkomensvoorzieningsoort.getId().toString())
        );
    }

    /**
     * {@code GET  /inkomensvoorzieningsoorts} : get all the inkomensvoorzieningsoorts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inkomensvoorzieningsoorts in body.
     */
    @GetMapping("")
    public List<Inkomensvoorzieningsoort> getAllInkomensvoorzieningsoorts() {
        log.debug("REST request to get all Inkomensvoorzieningsoorts");
        return inkomensvoorzieningsoortRepository.findAll();
    }

    /**
     * {@code GET  /inkomensvoorzieningsoorts/:id} : get the "id" inkomensvoorzieningsoort.
     *
     * @param id the id of the inkomensvoorzieningsoort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inkomensvoorzieningsoort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inkomensvoorzieningsoort> getInkomensvoorzieningsoort(@PathVariable("id") Long id) {
        log.debug("REST request to get Inkomensvoorzieningsoort : {}", id);
        Optional<Inkomensvoorzieningsoort> inkomensvoorzieningsoort = inkomensvoorzieningsoortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inkomensvoorzieningsoort);
    }

    /**
     * {@code DELETE  /inkomensvoorzieningsoorts/:id} : delete the "id" inkomensvoorzieningsoort.
     *
     * @param id the id of the inkomensvoorzieningsoort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInkomensvoorzieningsoort(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inkomensvoorzieningsoort : {}", id);
        inkomensvoorzieningsoortRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
