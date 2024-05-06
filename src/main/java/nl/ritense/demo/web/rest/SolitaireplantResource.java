package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Solitaireplant;
import nl.ritense.demo.repository.SolitaireplantRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Solitaireplant}.
 */
@RestController
@RequestMapping("/api/solitaireplants")
@Transactional
public class SolitaireplantResource {

    private final Logger log = LoggerFactory.getLogger(SolitaireplantResource.class);

    private static final String ENTITY_NAME = "solitaireplant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolitaireplantRepository solitaireplantRepository;

    public SolitaireplantResource(SolitaireplantRepository solitaireplantRepository) {
        this.solitaireplantRepository = solitaireplantRepository;
    }

    /**
     * {@code POST  /solitaireplants} : Create a new solitaireplant.
     *
     * @param solitaireplant the solitaireplant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solitaireplant, or with status {@code 400 (Bad Request)} if the solitaireplant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Solitaireplant> createSolitaireplant(@RequestBody Solitaireplant solitaireplant) throws URISyntaxException {
        log.debug("REST request to save Solitaireplant : {}", solitaireplant);
        if (solitaireplant.getId() != null) {
            throw new BadRequestAlertException("A new solitaireplant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        solitaireplant = solitaireplantRepository.save(solitaireplant);
        return ResponseEntity.created(new URI("/api/solitaireplants/" + solitaireplant.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, solitaireplant.getId().toString()))
            .body(solitaireplant);
    }

    /**
     * {@code PUT  /solitaireplants/:id} : Updates an existing solitaireplant.
     *
     * @param id the id of the solitaireplant to save.
     * @param solitaireplant the solitaireplant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solitaireplant,
     * or with status {@code 400 (Bad Request)} if the solitaireplant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solitaireplant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Solitaireplant> updateSolitaireplant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Solitaireplant solitaireplant
    ) throws URISyntaxException {
        log.debug("REST request to update Solitaireplant : {}, {}", id, solitaireplant);
        if (solitaireplant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solitaireplant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solitaireplantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        solitaireplant = solitaireplantRepository.save(solitaireplant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solitaireplant.getId().toString()))
            .body(solitaireplant);
    }

    /**
     * {@code PATCH  /solitaireplants/:id} : Partial updates given fields of an existing solitaireplant, field will ignore if it is null
     *
     * @param id the id of the solitaireplant to save.
     * @param solitaireplant the solitaireplant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solitaireplant,
     * or with status {@code 400 (Bad Request)} if the solitaireplant is not valid,
     * or with status {@code 404 (Not Found)} if the solitaireplant is not found,
     * or with status {@code 500 (Internal Server Error)} if the solitaireplant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Solitaireplant> partialUpdateSolitaireplant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Solitaireplant solitaireplant
    ) throws URISyntaxException {
        log.debug("REST request to partial update Solitaireplant partially : {}, {}", id, solitaireplant);
        if (solitaireplant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solitaireplant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solitaireplantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Solitaireplant> result = solitaireplantRepository
            .findById(solitaireplant.getId())
            .map(existingSolitaireplant -> {
                if (solitaireplant.getHoogte() != null) {
                    existingSolitaireplant.setHoogte(solitaireplant.getHoogte());
                }
                if (solitaireplant.getType() != null) {
                    existingSolitaireplant.setType(solitaireplant.getType());
                }

                return existingSolitaireplant;
            })
            .map(solitaireplantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solitaireplant.getId().toString())
        );
    }

    /**
     * {@code GET  /solitaireplants} : get all the solitaireplants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solitaireplants in body.
     */
    @GetMapping("")
    public List<Solitaireplant> getAllSolitaireplants() {
        log.debug("REST request to get all Solitaireplants");
        return solitaireplantRepository.findAll();
    }

    /**
     * {@code GET  /solitaireplants/:id} : get the "id" solitaireplant.
     *
     * @param id the id of the solitaireplant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solitaireplant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Solitaireplant> getSolitaireplant(@PathVariable("id") Long id) {
        log.debug("REST request to get Solitaireplant : {}", id);
        Optional<Solitaireplant> solitaireplant = solitaireplantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(solitaireplant);
    }

    /**
     * {@code DELETE  /solitaireplants/:id} : delete the "id" solitaireplant.
     *
     * @param id the id of the solitaireplant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolitaireplant(@PathVariable("id") Long id) {
        log.debug("REST request to delete Solitaireplant : {}", id);
        solitaireplantRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
