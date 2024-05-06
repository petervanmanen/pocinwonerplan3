package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Begrotingregel;
import nl.ritense.demo.repository.BegrotingregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Begrotingregel}.
 */
@RestController
@RequestMapping("/api/begrotingregels")
@Transactional
public class BegrotingregelResource {

    private final Logger log = LoggerFactory.getLogger(BegrotingregelResource.class);

    private static final String ENTITY_NAME = "begrotingregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BegrotingregelRepository begrotingregelRepository;

    public BegrotingregelResource(BegrotingregelRepository begrotingregelRepository) {
        this.begrotingregelRepository = begrotingregelRepository;
    }

    /**
     * {@code POST  /begrotingregels} : Create a new begrotingregel.
     *
     * @param begrotingregel the begrotingregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new begrotingregel, or with status {@code 400 (Bad Request)} if the begrotingregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Begrotingregel> createBegrotingregel(@Valid @RequestBody Begrotingregel begrotingregel)
        throws URISyntaxException {
        log.debug("REST request to save Begrotingregel : {}", begrotingregel);
        if (begrotingregel.getId() != null) {
            throw new BadRequestAlertException("A new begrotingregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        begrotingregel = begrotingregelRepository.save(begrotingregel);
        return ResponseEntity.created(new URI("/api/begrotingregels/" + begrotingregel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, begrotingregel.getId().toString()))
            .body(begrotingregel);
    }

    /**
     * {@code PUT  /begrotingregels/:id} : Updates an existing begrotingregel.
     *
     * @param id the id of the begrotingregel to save.
     * @param begrotingregel the begrotingregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated begrotingregel,
     * or with status {@code 400 (Bad Request)} if the begrotingregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the begrotingregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Begrotingregel> updateBegrotingregel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Begrotingregel begrotingregel
    ) throws URISyntaxException {
        log.debug("REST request to update Begrotingregel : {}, {}", id, begrotingregel);
        if (begrotingregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, begrotingregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!begrotingregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        begrotingregel = begrotingregelRepository.save(begrotingregel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, begrotingregel.getId().toString()))
            .body(begrotingregel);
    }

    /**
     * {@code PATCH  /begrotingregels/:id} : Partial updates given fields of an existing begrotingregel, field will ignore if it is null
     *
     * @param id the id of the begrotingregel to save.
     * @param begrotingregel the begrotingregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated begrotingregel,
     * or with status {@code 400 (Bad Request)} if the begrotingregel is not valid,
     * or with status {@code 404 (Not Found)} if the begrotingregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the begrotingregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Begrotingregel> partialUpdateBegrotingregel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Begrotingregel begrotingregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Begrotingregel partially : {}, {}", id, begrotingregel);
        if (begrotingregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, begrotingregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!begrotingregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Begrotingregel> result = begrotingregelRepository
            .findById(begrotingregel.getId())
            .map(existingBegrotingregel -> {
                if (begrotingregel.getBatenlasten() != null) {
                    existingBegrotingregel.setBatenlasten(begrotingregel.getBatenlasten());
                }
                if (begrotingregel.getBedrag() != null) {
                    existingBegrotingregel.setBedrag(begrotingregel.getBedrag());
                }
                if (begrotingregel.getSoortregel() != null) {
                    existingBegrotingregel.setSoortregel(begrotingregel.getSoortregel());
                }

                return existingBegrotingregel;
            })
            .map(begrotingregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, begrotingregel.getId().toString())
        );
    }

    /**
     * {@code GET  /begrotingregels} : get all the begrotingregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of begrotingregels in body.
     */
    @GetMapping("")
    public List<Begrotingregel> getAllBegrotingregels() {
        log.debug("REST request to get all Begrotingregels");
        return begrotingregelRepository.findAll();
    }

    /**
     * {@code GET  /begrotingregels/:id} : get the "id" begrotingregel.
     *
     * @param id the id of the begrotingregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the begrotingregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Begrotingregel> getBegrotingregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Begrotingregel : {}", id);
        Optional<Begrotingregel> begrotingregel = begrotingregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(begrotingregel);
    }

    /**
     * {@code DELETE  /begrotingregels/:id} : delete the "id" begrotingregel.
     *
     * @param id the id of the begrotingregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBegrotingregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Begrotingregel : {}", id);
        begrotingregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
