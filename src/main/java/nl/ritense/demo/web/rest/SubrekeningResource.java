package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Subrekening;
import nl.ritense.demo.repository.SubrekeningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Subrekening}.
 */
@RestController
@RequestMapping("/api/subrekenings")
@Transactional
public class SubrekeningResource {

    private final Logger log = LoggerFactory.getLogger(SubrekeningResource.class);

    private static final String ENTITY_NAME = "subrekening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubrekeningRepository subrekeningRepository;

    public SubrekeningResource(SubrekeningRepository subrekeningRepository) {
        this.subrekeningRepository = subrekeningRepository;
    }

    /**
     * {@code POST  /subrekenings} : Create a new subrekening.
     *
     * @param subrekening the subrekening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subrekening, or with status {@code 400 (Bad Request)} if the subrekening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Subrekening> createSubrekening(@Valid @RequestBody Subrekening subrekening) throws URISyntaxException {
        log.debug("REST request to save Subrekening : {}", subrekening);
        if (subrekening.getId() != null) {
            throw new BadRequestAlertException("A new subrekening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subrekening = subrekeningRepository.save(subrekening);
        return ResponseEntity.created(new URI("/api/subrekenings/" + subrekening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, subrekening.getId().toString()))
            .body(subrekening);
    }

    /**
     * {@code PUT  /subrekenings/:id} : Updates an existing subrekening.
     *
     * @param id the id of the subrekening to save.
     * @param subrekening the subrekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subrekening,
     * or with status {@code 400 (Bad Request)} if the subrekening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subrekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Subrekening> updateSubrekening(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Subrekening subrekening
    ) throws URISyntaxException {
        log.debug("REST request to update Subrekening : {}, {}", id, subrekening);
        if (subrekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subrekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subrekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subrekening = subrekeningRepository.save(subrekening);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subrekening.getId().toString()))
            .body(subrekening);
    }

    /**
     * {@code PATCH  /subrekenings/:id} : Partial updates given fields of an existing subrekening, field will ignore if it is null
     *
     * @param id the id of the subrekening to save.
     * @param subrekening the subrekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subrekening,
     * or with status {@code 400 (Bad Request)} if the subrekening is not valid,
     * or with status {@code 404 (Not Found)} if the subrekening is not found,
     * or with status {@code 500 (Internal Server Error)} if the subrekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Subrekening> partialUpdateSubrekening(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Subrekening subrekening
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subrekening partially : {}, {}", id, subrekening);
        if (subrekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subrekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subrekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Subrekening> result = subrekeningRepository
            .findById(subrekening.getId())
            .map(existingSubrekening -> {
                if (subrekening.getNaam() != null) {
                    existingSubrekening.setNaam(subrekening.getNaam());
                }
                if (subrekening.getNummer() != null) {
                    existingSubrekening.setNummer(subrekening.getNummer());
                }
                if (subrekening.getOmschrijving() != null) {
                    existingSubrekening.setOmschrijving(subrekening.getOmschrijving());
                }

                return existingSubrekening;
            })
            .map(subrekeningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subrekening.getId().toString())
        );
    }

    /**
     * {@code GET  /subrekenings} : get all the subrekenings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subrekenings in body.
     */
    @GetMapping("")
    public List<Subrekening> getAllSubrekenings() {
        log.debug("REST request to get all Subrekenings");
        return subrekeningRepository.findAll();
    }

    /**
     * {@code GET  /subrekenings/:id} : get the "id" subrekening.
     *
     * @param id the id of the subrekening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subrekening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subrekening> getSubrekening(@PathVariable("id") Long id) {
        log.debug("REST request to get Subrekening : {}", id);
        Optional<Subrekening> subrekening = subrekeningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subrekening);
    }

    /**
     * {@code DELETE  /subrekenings/:id} : delete the "id" subrekening.
     *
     * @param id the id of the subrekening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubrekening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Subrekening : {}", id);
        subrekeningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
