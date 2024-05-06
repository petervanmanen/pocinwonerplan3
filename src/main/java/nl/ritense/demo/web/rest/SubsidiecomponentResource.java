package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Subsidiecomponent;
import nl.ritense.demo.repository.SubsidiecomponentRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Subsidiecomponent}.
 */
@RestController
@RequestMapping("/api/subsidiecomponents")
@Transactional
public class SubsidiecomponentResource {

    private final Logger log = LoggerFactory.getLogger(SubsidiecomponentResource.class);

    private static final String ENTITY_NAME = "subsidiecomponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubsidiecomponentRepository subsidiecomponentRepository;

    public SubsidiecomponentResource(SubsidiecomponentRepository subsidiecomponentRepository) {
        this.subsidiecomponentRepository = subsidiecomponentRepository;
    }

    /**
     * {@code POST  /subsidiecomponents} : Create a new subsidiecomponent.
     *
     * @param subsidiecomponent the subsidiecomponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subsidiecomponent, or with status {@code 400 (Bad Request)} if the subsidiecomponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Subsidiecomponent> createSubsidiecomponent(@Valid @RequestBody Subsidiecomponent subsidiecomponent)
        throws URISyntaxException {
        log.debug("REST request to save Subsidiecomponent : {}", subsidiecomponent);
        if (subsidiecomponent.getId() != null) {
            throw new BadRequestAlertException("A new subsidiecomponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subsidiecomponent = subsidiecomponentRepository.save(subsidiecomponent);
        return ResponseEntity.created(new URI("/api/subsidiecomponents/" + subsidiecomponent.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, subsidiecomponent.getId().toString()))
            .body(subsidiecomponent);
    }

    /**
     * {@code PUT  /subsidiecomponents/:id} : Updates an existing subsidiecomponent.
     *
     * @param id the id of the subsidiecomponent to save.
     * @param subsidiecomponent the subsidiecomponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidiecomponent,
     * or with status {@code 400 (Bad Request)} if the subsidiecomponent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subsidiecomponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Subsidiecomponent> updateSubsidiecomponent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Subsidiecomponent subsidiecomponent
    ) throws URISyntaxException {
        log.debug("REST request to update Subsidiecomponent : {}, {}", id, subsidiecomponent);
        if (subsidiecomponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidiecomponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidiecomponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subsidiecomponent = subsidiecomponentRepository.save(subsidiecomponent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidiecomponent.getId().toString()))
            .body(subsidiecomponent);
    }

    /**
     * {@code PATCH  /subsidiecomponents/:id} : Partial updates given fields of an existing subsidiecomponent, field will ignore if it is null
     *
     * @param id the id of the subsidiecomponent to save.
     * @param subsidiecomponent the subsidiecomponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsidiecomponent,
     * or with status {@code 400 (Bad Request)} if the subsidiecomponent is not valid,
     * or with status {@code 404 (Not Found)} if the subsidiecomponent is not found,
     * or with status {@code 500 (Internal Server Error)} if the subsidiecomponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Subsidiecomponent> partialUpdateSubsidiecomponent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Subsidiecomponent subsidiecomponent
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subsidiecomponent partially : {}, {}", id, subsidiecomponent);
        if (subsidiecomponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subsidiecomponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subsidiecomponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Subsidiecomponent> result = subsidiecomponentRepository
            .findById(subsidiecomponent.getId())
            .map(existingSubsidiecomponent -> {
                if (subsidiecomponent.getGereserveerdbedrag() != null) {
                    existingSubsidiecomponent.setGereserveerdbedrag(subsidiecomponent.getGereserveerdbedrag());
                }
                if (subsidiecomponent.getToegekendbedrag() != null) {
                    existingSubsidiecomponent.setToegekendbedrag(subsidiecomponent.getToegekendbedrag());
                }

                return existingSubsidiecomponent;
            })
            .map(subsidiecomponentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subsidiecomponent.getId().toString())
        );
    }

    /**
     * {@code GET  /subsidiecomponents} : get all the subsidiecomponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subsidiecomponents in body.
     */
    @GetMapping("")
    public List<Subsidiecomponent> getAllSubsidiecomponents() {
        log.debug("REST request to get all Subsidiecomponents");
        return subsidiecomponentRepository.findAll();
    }

    /**
     * {@code GET  /subsidiecomponents/:id} : get the "id" subsidiecomponent.
     *
     * @param id the id of the subsidiecomponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subsidiecomponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subsidiecomponent> getSubsidiecomponent(@PathVariable("id") Long id) {
        log.debug("REST request to get Subsidiecomponent : {}", id);
        Optional<Subsidiecomponent> subsidiecomponent = subsidiecomponentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subsidiecomponent);
    }

    /**
     * {@code DELETE  /subsidiecomponents/:id} : delete the "id" subsidiecomponent.
     *
     * @param id the id of the subsidiecomponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubsidiecomponent(@PathVariable("id") Long id) {
        log.debug("REST request to delete Subsidiecomponent : {}", id);
        subsidiecomponentRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
