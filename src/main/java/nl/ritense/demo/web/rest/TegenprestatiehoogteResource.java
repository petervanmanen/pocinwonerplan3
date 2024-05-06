package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Tegenprestatiehoogte;
import nl.ritense.demo.repository.TegenprestatiehoogteRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Tegenprestatiehoogte}.
 */
@RestController
@RequestMapping("/api/tegenprestatiehoogtes")
@Transactional
public class TegenprestatiehoogteResource {

    private final Logger log = LoggerFactory.getLogger(TegenprestatiehoogteResource.class);

    private static final String ENTITY_NAME = "tegenprestatiehoogte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TegenprestatiehoogteRepository tegenprestatiehoogteRepository;

    public TegenprestatiehoogteResource(TegenprestatiehoogteRepository tegenprestatiehoogteRepository) {
        this.tegenprestatiehoogteRepository = tegenprestatiehoogteRepository;
    }

    /**
     * {@code POST  /tegenprestatiehoogtes} : Create a new tegenprestatiehoogte.
     *
     * @param tegenprestatiehoogte the tegenprestatiehoogte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tegenprestatiehoogte, or with status {@code 400 (Bad Request)} if the tegenprestatiehoogte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tegenprestatiehoogte> createTegenprestatiehoogte(@RequestBody Tegenprestatiehoogte tegenprestatiehoogte)
        throws URISyntaxException {
        log.debug("REST request to save Tegenprestatiehoogte : {}", tegenprestatiehoogte);
        if (tegenprestatiehoogte.getId() != null) {
            throw new BadRequestAlertException("A new tegenprestatiehoogte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tegenprestatiehoogte = tegenprestatiehoogteRepository.save(tegenprestatiehoogte);
        return ResponseEntity.created(new URI("/api/tegenprestatiehoogtes/" + tegenprestatiehoogte.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tegenprestatiehoogte.getId().toString()))
            .body(tegenprestatiehoogte);
    }

    /**
     * {@code PUT  /tegenprestatiehoogtes/:id} : Updates an existing tegenprestatiehoogte.
     *
     * @param id the id of the tegenprestatiehoogte to save.
     * @param tegenprestatiehoogte the tegenprestatiehoogte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tegenprestatiehoogte,
     * or with status {@code 400 (Bad Request)} if the tegenprestatiehoogte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tegenprestatiehoogte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tegenprestatiehoogte> updateTegenprestatiehoogte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tegenprestatiehoogte tegenprestatiehoogte
    ) throws URISyntaxException {
        log.debug("REST request to update Tegenprestatiehoogte : {}, {}", id, tegenprestatiehoogte);
        if (tegenprestatiehoogte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tegenprestatiehoogte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tegenprestatiehoogteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tegenprestatiehoogte = tegenprestatiehoogteRepository.save(tegenprestatiehoogte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tegenprestatiehoogte.getId().toString()))
            .body(tegenprestatiehoogte);
    }

    /**
     * {@code PATCH  /tegenprestatiehoogtes/:id} : Partial updates given fields of an existing tegenprestatiehoogte, field will ignore if it is null
     *
     * @param id the id of the tegenprestatiehoogte to save.
     * @param tegenprestatiehoogte the tegenprestatiehoogte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tegenprestatiehoogte,
     * or with status {@code 400 (Bad Request)} if the tegenprestatiehoogte is not valid,
     * or with status {@code 404 (Not Found)} if the tegenprestatiehoogte is not found,
     * or with status {@code 500 (Internal Server Error)} if the tegenprestatiehoogte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tegenprestatiehoogte> partialUpdateTegenprestatiehoogte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tegenprestatiehoogte tegenprestatiehoogte
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tegenprestatiehoogte partially : {}, {}", id, tegenprestatiehoogte);
        if (tegenprestatiehoogte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tegenprestatiehoogte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tegenprestatiehoogteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tegenprestatiehoogte> result = tegenprestatiehoogteRepository
            .findById(tegenprestatiehoogte.getId())
            .map(existingTegenprestatiehoogte -> {
                if (tegenprestatiehoogte.getCode() != null) {
                    existingTegenprestatiehoogte.setCode(tegenprestatiehoogte.getCode());
                }
                if (tegenprestatiehoogte.getNaam() != null) {
                    existingTegenprestatiehoogte.setNaam(tegenprestatiehoogte.getNaam());
                }
                if (tegenprestatiehoogte.getOmschrijving() != null) {
                    existingTegenprestatiehoogte.setOmschrijving(tegenprestatiehoogte.getOmschrijving());
                }

                return existingTegenprestatiehoogte;
            })
            .map(tegenprestatiehoogteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tegenprestatiehoogte.getId().toString())
        );
    }

    /**
     * {@code GET  /tegenprestatiehoogtes} : get all the tegenprestatiehoogtes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tegenprestatiehoogtes in body.
     */
    @GetMapping("")
    public List<Tegenprestatiehoogte> getAllTegenprestatiehoogtes() {
        log.debug("REST request to get all Tegenprestatiehoogtes");
        return tegenprestatiehoogteRepository.findAll();
    }

    /**
     * {@code GET  /tegenprestatiehoogtes/:id} : get the "id" tegenprestatiehoogte.
     *
     * @param id the id of the tegenprestatiehoogte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tegenprestatiehoogte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tegenprestatiehoogte> getTegenprestatiehoogte(@PathVariable("id") Long id) {
        log.debug("REST request to get Tegenprestatiehoogte : {}", id);
        Optional<Tegenprestatiehoogte> tegenprestatiehoogte = tegenprestatiehoogteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tegenprestatiehoogte);
    }

    /**
     * {@code DELETE  /tegenprestatiehoogtes/:id} : delete the "id" tegenprestatiehoogte.
     *
     * @param id the id of the tegenprestatiehoogte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTegenprestatiehoogte(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tegenprestatiehoogte : {}", id);
        tegenprestatiehoogteRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
