package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Subsidieaanvraag;
import nl.ritense.demo.repository.SubsidieaanvraagRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Subsidieaanvraag}.
 */
@RestController
@RequestMapping("/api/subsidieaanvraags")
@Transactional
public class SubsidieaanvraagResource {

    private final Logger log = LoggerFactory.getLogger(SubsidieaanvraagResource.class);

    private static final String ENTITY_NAME = "subsidieaanvraag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubsidieaanvraagRepository subsidieaanvraagRepository;

    public SubsidieaanvraagResource(SubsidieaanvraagRepository subsidieaanvraagRepository) {
        this.subsidieaanvraagRepository = subsidieaanvraagRepository;
    }

    /**
     * {@code POST  /subsidieaanvraags} : Create a new subsidieaanvraag.
     *
     * @param subsidieaanvraag the subsidieaanvraag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subsidieaanvraag, or with status {@code 400 (Bad Request)} if the subsidieaanvraag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Subsidieaanvraag> createSubsidieaanvraag(@Valid @RequestBody Subsidieaanvraag subsidieaanvraag)
        throws URISyntaxException {
        log.debug("REST request to save Subsidieaanvraag : {}", subsidieaanvraag);
        if (subsidieaanvraag.getId() != null) {
            throw new BadRequestAlertException("A new subsidieaanvraag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subsidieaanvraag = subsidieaanvraagRepository.save(subsidieaanvraag);
        return ResponseEntity.created(new URI("/api/subsidieaanvraags/" + subsidieaanvraag.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, subsidieaanvraag.getId().toString()))
            .body(subsidieaanvraag);
    }

    /**
     * {@code PUT  /subsidieaanvraags/:id} : Updates an existing subsidieaanvraag.
     *
     * @param id the id of the subsidieaanvraag to save.
     * @param subsidieaanvraag the subsidieaanvraag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidieaanvraag,
     * or with status {@code 400 (Bad Request)} if the subsidieaanvraag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subsidieaanvraag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Subsidieaanvraag> updateSubsidieaanvraag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Subsidieaanvraag subsidieaanvraag
    ) throws URISyntaxException {
        log.debug("REST request to update Subsidieaanvraag : {}, {}", id, subsidieaanvraag);
        if (subsidieaanvraag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidieaanvraag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidieaanvraagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subsidieaanvraag = subsidieaanvraagRepository.save(subsidieaanvraag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidieaanvraag.getId().toString()))
            .body(subsidieaanvraag);
    }

    /**
     * {@code PATCH  /subsidieaanvraags/:id} : Partial updates given fields of an existing subsidieaanvraag, field will ignore if it is null
     *
     * @param id the id of the subsidieaanvraag to save.
     * @param subsidieaanvraag the subsidieaanvraag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidieaanvraag,
     * or with status {@code 400 (Bad Request)} if the subsidieaanvraag is not valid,
     * or with status {@code 404 (Not Found)} if the subsidieaanvraag is not found,
     * or with status {@code 500 (Internal Server Error)} if the subsidieaanvraag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Subsidieaanvraag> partialUpdateSubsidieaanvraag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Subsidieaanvraag subsidieaanvraag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subsidieaanvraag partially : {}, {}", id, subsidieaanvraag);
        if (subsidieaanvraag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidieaanvraag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidieaanvraagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Subsidieaanvraag> result = subsidieaanvraagRepository
            .findById(subsidieaanvraag.getId())
            .map(existingSubsidieaanvraag -> {
                if (subsidieaanvraag.getAangevraagdbedrag() != null) {
                    existingSubsidieaanvraag.setAangevraagdbedrag(subsidieaanvraag.getAangevraagdbedrag());
                }
                if (subsidieaanvraag.getDatumindiening() != null) {
                    existingSubsidieaanvraag.setDatumindiening(subsidieaanvraag.getDatumindiening());
                }
                if (subsidieaanvraag.getKenmerk() != null) {
                    existingSubsidieaanvraag.setKenmerk(subsidieaanvraag.getKenmerk());
                }
                if (subsidieaanvraag.getOntvangstbevestiging() != null) {
                    existingSubsidieaanvraag.setOntvangstbevestiging(subsidieaanvraag.getOntvangstbevestiging());
                }
                if (subsidieaanvraag.getVerwachtebeschikking() != null) {
                    existingSubsidieaanvraag.setVerwachtebeschikking(subsidieaanvraag.getVerwachtebeschikking());
                }

                return existingSubsidieaanvraag;
            })
            .map(subsidieaanvraagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidieaanvraag.getId().toString())
        );
    }

    /**
     * {@code GET  /subsidieaanvraags} : get all the subsidieaanvraags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subsidieaanvraags in body.
     */
    @GetMapping("")
    public List<Subsidieaanvraag> getAllSubsidieaanvraags() {
        log.debug("REST request to get all Subsidieaanvraags");
        return subsidieaanvraagRepository.findAll();
    }

    /**
     * {@code GET  /subsidieaanvraags/:id} : get the "id" subsidieaanvraag.
     *
     * @param id the id of the subsidieaanvraag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subsidieaanvraag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subsidieaanvraag> getSubsidieaanvraag(@PathVariable("id") Long id) {
        log.debug("REST request to get Subsidieaanvraag : {}", id);
        Optional<Subsidieaanvraag> subsidieaanvraag = subsidieaanvraagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subsidieaanvraag);
    }

    /**
     * {@code DELETE  /subsidieaanvraags/:id} : delete the "id" subsidieaanvraag.
     *
     * @param id the id of the subsidieaanvraag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubsidieaanvraag(@PathVariable("id") Long id) {
        log.debug("REST request to delete Subsidieaanvraag : {}", id);
        subsidieaanvraagRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
