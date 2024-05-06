package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Fietsparkeervoorziening;
import nl.ritense.demo.repository.FietsparkeervoorzieningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Fietsparkeervoorziening}.
 */
@RestController
@RequestMapping("/api/fietsparkeervoorzienings")
@Transactional
public class FietsparkeervoorzieningResource {

    private final Logger log = LoggerFactory.getLogger(FietsparkeervoorzieningResource.class);

    private static final String ENTITY_NAME = "fietsparkeervoorziening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FietsparkeervoorzieningRepository fietsparkeervoorzieningRepository;

    public FietsparkeervoorzieningResource(FietsparkeervoorzieningRepository fietsparkeervoorzieningRepository) {
        this.fietsparkeervoorzieningRepository = fietsparkeervoorzieningRepository;
    }

    /**
     * {@code POST  /fietsparkeervoorzienings} : Create a new fietsparkeervoorziening.
     *
     * @param fietsparkeervoorziening the fietsparkeervoorziening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fietsparkeervoorziening, or with status {@code 400 (Bad Request)} if the fietsparkeervoorziening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Fietsparkeervoorziening> createFietsparkeervoorziening(
        @RequestBody Fietsparkeervoorziening fietsparkeervoorziening
    ) throws URISyntaxException {
        log.debug("REST request to save Fietsparkeervoorziening : {}", fietsparkeervoorziening);
        if (fietsparkeervoorziening.getId() != null) {
            throw new BadRequestAlertException("A new fietsparkeervoorziening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fietsparkeervoorziening = fietsparkeervoorzieningRepository.save(fietsparkeervoorziening);
        return ResponseEntity.created(new URI("/api/fietsparkeervoorzienings/" + fietsparkeervoorziening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, fietsparkeervoorziening.getId().toString()))
            .body(fietsparkeervoorziening);
    }

    /**
     * {@code PUT  /fietsparkeervoorzienings/:id} : Updates an existing fietsparkeervoorziening.
     *
     * @param id the id of the fietsparkeervoorziening to save.
     * @param fietsparkeervoorziening the fietsparkeervoorziening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fietsparkeervoorziening,
     * or with status {@code 400 (Bad Request)} if the fietsparkeervoorziening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fietsparkeervoorziening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Fietsparkeervoorziening> updateFietsparkeervoorziening(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Fietsparkeervoorziening fietsparkeervoorziening
    ) throws URISyntaxException {
        log.debug("REST request to update Fietsparkeervoorziening : {}, {}", id, fietsparkeervoorziening);
        if (fietsparkeervoorziening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fietsparkeervoorziening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fietsparkeervoorzieningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fietsparkeervoorziening = fietsparkeervoorzieningRepository.save(fietsparkeervoorziening);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fietsparkeervoorziening.getId().toString()))
            .body(fietsparkeervoorziening);
    }

    /**
     * {@code PATCH  /fietsparkeervoorzienings/:id} : Partial updates given fields of an existing fietsparkeervoorziening, field will ignore if it is null
     *
     * @param id the id of the fietsparkeervoorziening to save.
     * @param fietsparkeervoorziening the fietsparkeervoorziening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fietsparkeervoorziening,
     * or with status {@code 400 (Bad Request)} if the fietsparkeervoorziening is not valid,
     * or with status {@code 404 (Not Found)} if the fietsparkeervoorziening is not found,
     * or with status {@code 500 (Internal Server Error)} if the fietsparkeervoorziening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Fietsparkeervoorziening> partialUpdateFietsparkeervoorziening(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Fietsparkeervoorziening fietsparkeervoorziening
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fietsparkeervoorziening partially : {}, {}", id, fietsparkeervoorziening);
        if (fietsparkeervoorziening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fietsparkeervoorziening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fietsparkeervoorzieningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Fietsparkeervoorziening> result = fietsparkeervoorzieningRepository
            .findById(fietsparkeervoorziening.getId())
            .map(existingFietsparkeervoorziening -> {
                if (fietsparkeervoorziening.getAantalparkeerplaatsen() != null) {
                    existingFietsparkeervoorziening.setAantalparkeerplaatsen(fietsparkeervoorziening.getAantalparkeerplaatsen());
                }
                if (fietsparkeervoorziening.getType() != null) {
                    existingFietsparkeervoorziening.setType(fietsparkeervoorziening.getType());
                }
                if (fietsparkeervoorziening.getTypeplus() != null) {
                    existingFietsparkeervoorziening.setTypeplus(fietsparkeervoorziening.getTypeplus());
                }

                return existingFietsparkeervoorziening;
            })
            .map(fietsparkeervoorzieningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fietsparkeervoorziening.getId().toString())
        );
    }

    /**
     * {@code GET  /fietsparkeervoorzienings} : get all the fietsparkeervoorzienings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fietsparkeervoorzienings in body.
     */
    @GetMapping("")
    public List<Fietsparkeervoorziening> getAllFietsparkeervoorzienings() {
        log.debug("REST request to get all Fietsparkeervoorzienings");
        return fietsparkeervoorzieningRepository.findAll();
    }

    /**
     * {@code GET  /fietsparkeervoorzienings/:id} : get the "id" fietsparkeervoorziening.
     *
     * @param id the id of the fietsparkeervoorziening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fietsparkeervoorziening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Fietsparkeervoorziening> getFietsparkeervoorziening(@PathVariable("id") Long id) {
        log.debug("REST request to get Fietsparkeervoorziening : {}", id);
        Optional<Fietsparkeervoorziening> fietsparkeervoorziening = fietsparkeervoorzieningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fietsparkeervoorziening);
    }

    /**
     * {@code DELETE  /fietsparkeervoorzienings/:id} : delete the "id" fietsparkeervoorziening.
     *
     * @param id the id of the fietsparkeervoorziening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFietsparkeervoorziening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Fietsparkeervoorziening : {}", id);
        fietsparkeervoorzieningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
