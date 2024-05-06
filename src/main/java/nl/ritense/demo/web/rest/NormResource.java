package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Norm;
import nl.ritense.demo.repository.NormRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Norm}.
 */
@RestController
@RequestMapping("/api/norms")
@Transactional
public class NormResource {

    private final Logger log = LoggerFactory.getLogger(NormResource.class);

    private static final String ENTITY_NAME = "norm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NormRepository normRepository;

    public NormResource(NormRepository normRepository) {
        this.normRepository = normRepository;
    }

    /**
     * {@code POST  /norms} : Create a new norm.
     *
     * @param norm the norm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new norm, or with status {@code 400 (Bad Request)} if the norm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Norm> createNorm(@Valid @RequestBody Norm norm) throws URISyntaxException {
        log.debug("REST request to save Norm : {}", norm);
        if (norm.getId() != null) {
            throw new BadRequestAlertException("A new norm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        norm = normRepository.save(norm);
        return ResponseEntity.created(new URI("/api/norms/" + norm.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, norm.getId().toString()))
            .body(norm);
    }

    /**
     * {@code PUT  /norms/:id} : Updates an existing norm.
     *
     * @param id the id of the norm to save.
     * @param norm the norm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated norm,
     * or with status {@code 400 (Bad Request)} if the norm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the norm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Norm> updateNorm(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Norm norm)
        throws URISyntaxException {
        log.debug("REST request to update Norm : {}, {}", id, norm);
        if (norm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, norm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!normRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        norm = normRepository.save(norm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, norm.getId().toString()))
            .body(norm);
    }

    /**
     * {@code PATCH  /norms/:id} : Partial updates given fields of an existing norm, field will ignore if it is null
     *
     * @param id the id of the norm to save.
     * @param norm the norm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated norm,
     * or with status {@code 400 (Bad Request)} if the norm is not valid,
     * or with status {@code 404 (Not Found)} if the norm is not found,
     * or with status {@code 500 (Internal Server Error)} if the norm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Norm> partialUpdateNorm(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Norm norm
    ) throws URISyntaxException {
        log.debug("REST request to partial update Norm partially : {}, {}", id, norm);
        if (norm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, norm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!normRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Norm> result = normRepository
            .findById(norm.getId())
            .map(existingNorm -> {
                if (norm.getNen3610id() != null) {
                    existingNorm.setNen3610id(norm.getNen3610id());
                }

                return existingNorm;
            })
            .map(normRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, norm.getId().toString())
        );
    }

    /**
     * {@code GET  /norms} : get all the norms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of norms in body.
     */
    @GetMapping("")
    public List<Norm> getAllNorms() {
        log.debug("REST request to get all Norms");
        return normRepository.findAll();
    }

    /**
     * {@code GET  /norms/:id} : get the "id" norm.
     *
     * @param id the id of the norm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the norm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Norm> getNorm(@PathVariable("id") Long id) {
        log.debug("REST request to get Norm : {}", id);
        Optional<Norm> norm = normRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(norm);
    }

    /**
     * {@code DELETE  /norms/:id} : delete the "id" norm.
     *
     * @param id the id of the norm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNorm(@PathVariable("id") Long id) {
        log.debug("REST request to delete Norm : {}", id);
        normRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
