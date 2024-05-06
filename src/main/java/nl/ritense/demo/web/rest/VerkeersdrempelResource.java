package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verkeersdrempel;
import nl.ritense.demo.repository.VerkeersdrempelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verkeersdrempel}.
 */
@RestController
@RequestMapping("/api/verkeersdrempels")
@Transactional
public class VerkeersdrempelResource {

    private final Logger log = LoggerFactory.getLogger(VerkeersdrempelResource.class);

    private static final String ENTITY_NAME = "verkeersdrempel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerkeersdrempelRepository verkeersdrempelRepository;

    public VerkeersdrempelResource(VerkeersdrempelRepository verkeersdrempelRepository) {
        this.verkeersdrempelRepository = verkeersdrempelRepository;
    }

    /**
     * {@code POST  /verkeersdrempels} : Create a new verkeersdrempel.
     *
     * @param verkeersdrempel the verkeersdrempel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verkeersdrempel, or with status {@code 400 (Bad Request)} if the verkeersdrempel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verkeersdrempel> createVerkeersdrempel(@Valid @RequestBody Verkeersdrempel verkeersdrempel)
        throws URISyntaxException {
        log.debug("REST request to save Verkeersdrempel : {}", verkeersdrempel);
        if (verkeersdrempel.getId() != null) {
            throw new BadRequestAlertException("A new verkeersdrempel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verkeersdrempel = verkeersdrempelRepository.save(verkeersdrempel);
        return ResponseEntity.created(new URI("/api/verkeersdrempels/" + verkeersdrempel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verkeersdrempel.getId().toString()))
            .body(verkeersdrempel);
    }

    /**
     * {@code PUT  /verkeersdrempels/:id} : Updates an existing verkeersdrempel.
     *
     * @param id the id of the verkeersdrempel to save.
     * @param verkeersdrempel the verkeersdrempel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verkeersdrempel,
     * or with status {@code 400 (Bad Request)} if the verkeersdrempel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verkeersdrempel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verkeersdrempel> updateVerkeersdrempel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verkeersdrempel verkeersdrempel
    ) throws URISyntaxException {
        log.debug("REST request to update Verkeersdrempel : {}, {}", id, verkeersdrempel);
        if (verkeersdrempel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verkeersdrempel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verkeersdrempelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verkeersdrempel = verkeersdrempelRepository.save(verkeersdrempel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verkeersdrempel.getId().toString()))
            .body(verkeersdrempel);
    }

    /**
     * {@code PATCH  /verkeersdrempels/:id} : Partial updates given fields of an existing verkeersdrempel, field will ignore if it is null
     *
     * @param id the id of the verkeersdrempel to save.
     * @param verkeersdrempel the verkeersdrempel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verkeersdrempel,
     * or with status {@code 400 (Bad Request)} if the verkeersdrempel is not valid,
     * or with status {@code 404 (Not Found)} if the verkeersdrempel is not found,
     * or with status {@code 500 (Internal Server Error)} if the verkeersdrempel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verkeersdrempel> partialUpdateVerkeersdrempel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verkeersdrempel verkeersdrempel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verkeersdrempel partially : {}, {}", id, verkeersdrempel);
        if (verkeersdrempel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verkeersdrempel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verkeersdrempelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verkeersdrempel> result = verkeersdrempelRepository
            .findById(verkeersdrempel.getId())
            .map(existingVerkeersdrempel -> {
                if (verkeersdrempel.getOntwerpsnelheid() != null) {
                    existingVerkeersdrempel.setOntwerpsnelheid(verkeersdrempel.getOntwerpsnelheid());
                }
                if (verkeersdrempel.getType() != null) {
                    existingVerkeersdrempel.setType(verkeersdrempel.getType());
                }
                if (verkeersdrempel.getTypeplus() != null) {
                    existingVerkeersdrempel.setTypeplus(verkeersdrempel.getTypeplus());
                }

                return existingVerkeersdrempel;
            })
            .map(verkeersdrempelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verkeersdrempel.getId().toString())
        );
    }

    /**
     * {@code GET  /verkeersdrempels} : get all the verkeersdrempels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verkeersdrempels in body.
     */
    @GetMapping("")
    public List<Verkeersdrempel> getAllVerkeersdrempels() {
        log.debug("REST request to get all Verkeersdrempels");
        return verkeersdrempelRepository.findAll();
    }

    /**
     * {@code GET  /verkeersdrempels/:id} : get the "id" verkeersdrempel.
     *
     * @param id the id of the verkeersdrempel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verkeersdrempel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verkeersdrempel> getVerkeersdrempel(@PathVariable("id") Long id) {
        log.debug("REST request to get Verkeersdrempel : {}", id);
        Optional<Verkeersdrempel> verkeersdrempel = verkeersdrempelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verkeersdrempel);
    }

    /**
     * {@code DELETE  /verkeersdrempels/:id} : delete the "id" verkeersdrempel.
     *
     * @param id the id of the verkeersdrempel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerkeersdrempel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verkeersdrempel : {}", id);
        verkeersdrempelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
