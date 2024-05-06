package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Begroting;
import nl.ritense.demo.repository.BegrotingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Begroting}.
 */
@RestController
@RequestMapping("/api/begrotings")
@Transactional
public class BegrotingResource {

    private final Logger log = LoggerFactory.getLogger(BegrotingResource.class);

    private static final String ENTITY_NAME = "begroting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BegrotingRepository begrotingRepository;

    public BegrotingResource(BegrotingRepository begrotingRepository) {
        this.begrotingRepository = begrotingRepository;
    }

    /**
     * {@code POST  /begrotings} : Create a new begroting.
     *
     * @param begroting the begroting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new begroting, or with status {@code 400 (Bad Request)} if the begroting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Begroting> createBegroting(@Valid @RequestBody Begroting begroting) throws URISyntaxException {
        log.debug("REST request to save Begroting : {}", begroting);
        if (begroting.getId() != null) {
            throw new BadRequestAlertException("A new begroting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        begroting = begrotingRepository.save(begroting);
        return ResponseEntity.created(new URI("/api/begrotings/" + begroting.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, begroting.getId().toString()))
            .body(begroting);
    }

    /**
     * {@code PUT  /begrotings/:id} : Updates an existing begroting.
     *
     * @param id the id of the begroting to save.
     * @param begroting the begroting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated begroting,
     * or with status {@code 400 (Bad Request)} if the begroting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the begroting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Begroting> updateBegroting(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Begroting begroting
    ) throws URISyntaxException {
        log.debug("REST request to update Begroting : {}, {}", id, begroting);
        if (begroting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, begroting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!begrotingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        begroting = begrotingRepository.save(begroting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, begroting.getId().toString()))
            .body(begroting);
    }

    /**
     * {@code PATCH  /begrotings/:id} : Partial updates given fields of an existing begroting, field will ignore if it is null
     *
     * @param id the id of the begroting to save.
     * @param begroting the begroting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated begroting,
     * or with status {@code 400 (Bad Request)} if the begroting is not valid,
     * or with status {@code 404 (Not Found)} if the begroting is not found,
     * or with status {@code 500 (Internal Server Error)} if the begroting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Begroting> partialUpdateBegroting(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Begroting begroting
    ) throws URISyntaxException {
        log.debug("REST request to partial update Begroting partially : {}, {}", id, begroting);
        if (begroting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, begroting.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!begrotingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Begroting> result = begrotingRepository
            .findById(begroting.getId())
            .map(existingBegroting -> {
                if (begroting.getNaam() != null) {
                    existingBegroting.setNaam(begroting.getNaam());
                }
                if (begroting.getNummer() != null) {
                    existingBegroting.setNummer(begroting.getNummer());
                }
                if (begroting.getOmschrijving() != null) {
                    existingBegroting.setOmschrijving(begroting.getOmschrijving());
                }

                return existingBegroting;
            })
            .map(begrotingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, begroting.getId().toString())
        );
    }

    /**
     * {@code GET  /begrotings} : get all the begrotings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of begrotings in body.
     */
    @GetMapping("")
    public List<Begroting> getAllBegrotings() {
        log.debug("REST request to get all Begrotings");
        return begrotingRepository.findAll();
    }

    /**
     * {@code GET  /begrotings/:id} : get the "id" begroting.
     *
     * @param id the id of the begroting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the begroting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Begroting> getBegroting(@PathVariable("id") Long id) {
        log.debug("REST request to get Begroting : {}", id);
        Optional<Begroting> begroting = begrotingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(begroting);
    }

    /**
     * {@code DELETE  /begrotings/:id} : delete the "id" begroting.
     *
     * @param id the id of the begroting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBegroting(@PathVariable("id") Long id) {
        log.debug("REST request to delete Begroting : {}", id);
        begrotingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
