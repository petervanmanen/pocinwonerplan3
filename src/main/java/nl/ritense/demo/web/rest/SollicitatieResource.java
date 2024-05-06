package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Sollicitatie;
import nl.ritense.demo.repository.SollicitatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Sollicitatie}.
 */
@RestController
@RequestMapping("/api/sollicitaties")
@Transactional
public class SollicitatieResource {

    private final Logger log = LoggerFactory.getLogger(SollicitatieResource.class);

    private static final String ENTITY_NAME = "sollicitatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SollicitatieRepository sollicitatieRepository;

    public SollicitatieResource(SollicitatieRepository sollicitatieRepository) {
        this.sollicitatieRepository = sollicitatieRepository;
    }

    /**
     * {@code POST  /sollicitaties} : Create a new sollicitatie.
     *
     * @param sollicitatie the sollicitatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sollicitatie, or with status {@code 400 (Bad Request)} if the sollicitatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sollicitatie> createSollicitatie(@Valid @RequestBody Sollicitatie sollicitatie) throws URISyntaxException {
        log.debug("REST request to save Sollicitatie : {}", sollicitatie);
        if (sollicitatie.getId() != null) {
            throw new BadRequestAlertException("A new sollicitatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sollicitatie = sollicitatieRepository.save(sollicitatie);
        return ResponseEntity.created(new URI("/api/sollicitaties/" + sollicitatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sollicitatie.getId().toString()))
            .body(sollicitatie);
    }

    /**
     * {@code PUT  /sollicitaties/:id} : Updates an existing sollicitatie.
     *
     * @param id the id of the sollicitatie to save.
     * @param sollicitatie the sollicitatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sollicitatie,
     * or with status {@code 400 (Bad Request)} if the sollicitatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sollicitatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sollicitatie> updateSollicitatie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sollicitatie sollicitatie
    ) throws URISyntaxException {
        log.debug("REST request to update Sollicitatie : {}, {}", id, sollicitatie);
        if (sollicitatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sollicitatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sollicitatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sollicitatie = sollicitatieRepository.save(sollicitatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sollicitatie.getId().toString()))
            .body(sollicitatie);
    }

    /**
     * {@code PATCH  /sollicitaties/:id} : Partial updates given fields of an existing sollicitatie, field will ignore if it is null
     *
     * @param id the id of the sollicitatie to save.
     * @param sollicitatie the sollicitatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sollicitatie,
     * or with status {@code 400 (Bad Request)} if the sollicitatie is not valid,
     * or with status {@code 404 (Not Found)} if the sollicitatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the sollicitatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sollicitatie> partialUpdateSollicitatie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sollicitatie sollicitatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sollicitatie partially : {}, {}", id, sollicitatie);
        if (sollicitatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sollicitatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sollicitatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sollicitatie> result = sollicitatieRepository
            .findById(sollicitatie.getId())
            .map(existingSollicitatie -> {
                if (sollicitatie.getDatum() != null) {
                    existingSollicitatie.setDatum(sollicitatie.getDatum());
                }

                return existingSollicitatie;
            })
            .map(sollicitatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sollicitatie.getId().toString())
        );
    }

    /**
     * {@code GET  /sollicitaties} : get all the sollicitaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sollicitaties in body.
     */
    @GetMapping("")
    public List<Sollicitatie> getAllSollicitaties() {
        log.debug("REST request to get all Sollicitaties");
        return sollicitatieRepository.findAll();
    }

    /**
     * {@code GET  /sollicitaties/:id} : get the "id" sollicitatie.
     *
     * @param id the id of the sollicitatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sollicitatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sollicitatie> getSollicitatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Sollicitatie : {}", id);
        Optional<Sollicitatie> sollicitatie = sollicitatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sollicitatie);
    }

    /**
     * {@code DELETE  /sollicitaties/:id} : delete the "id" sollicitatie.
     *
     * @param id the id of the sollicitatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSollicitatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Sollicitatie : {}", id);
        sollicitatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
