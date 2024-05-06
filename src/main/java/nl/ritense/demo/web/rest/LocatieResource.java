package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Locatie;
import nl.ritense.demo.repository.LocatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Locatie}.
 */
@RestController
@RequestMapping("/api/locaties")
@Transactional
public class LocatieResource {

    private final Logger log = LoggerFactory.getLogger(LocatieResource.class);

    private static final String ENTITY_NAME = "locatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocatieRepository locatieRepository;

    public LocatieResource(LocatieRepository locatieRepository) {
        this.locatieRepository = locatieRepository;
    }

    /**
     * {@code POST  /locaties} : Create a new locatie.
     *
     * @param locatie the locatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locatie, or with status {@code 400 (Bad Request)} if the locatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Locatie> createLocatie(@Valid @RequestBody Locatie locatie) throws URISyntaxException {
        log.debug("REST request to save Locatie : {}", locatie);
        if (locatie.getId() != null) {
            throw new BadRequestAlertException("A new locatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        locatie = locatieRepository.save(locatie);
        return ResponseEntity.created(new URI("/api/locaties/" + locatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, locatie.getId().toString()))
            .body(locatie);
    }

    /**
     * {@code PUT  /locaties/:id} : Updates an existing locatie.
     *
     * @param id the id of the locatie to save.
     * @param locatie the locatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatie,
     * or with status {@code 400 (Bad Request)} if the locatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Locatie> updateLocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Locatie locatie
    ) throws URISyntaxException {
        log.debug("REST request to update Locatie : {}, {}", id, locatie);
        if (locatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locatie = locatieRepository.save(locatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatie.getId().toString()))
            .body(locatie);
    }

    /**
     * {@code PATCH  /locaties/:id} : Partial updates given fields of an existing locatie, field will ignore if it is null
     *
     * @param id the id of the locatie to save.
     * @param locatie the locatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatie,
     * or with status {@code 400 (Bad Request)} if the locatie is not valid,
     * or with status {@code 404 (Not Found)} if the locatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the locatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Locatie> partialUpdateLocatie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Locatie locatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Locatie partially : {}, {}", id, locatie);
        if (locatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Locatie> result = locatieRepository
            .findById(locatie.getId())
            .map(existingLocatie -> {
                if (locatie.getAdres() != null) {
                    existingLocatie.setAdres(locatie.getAdres());
                }

                return existingLocatie;
            })
            .map(locatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatie.getId().toString())
        );
    }

    /**
     * {@code GET  /locaties} : get all the locaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locaties in body.
     */
    @GetMapping("")
    public List<Locatie> getAllLocaties() {
        log.debug("REST request to get all Locaties");
        return locatieRepository.findAll();
    }

    /**
     * {@code GET  /locaties/:id} : get the "id" locatie.
     *
     * @param id the id of the locatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Locatie> getLocatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Locatie : {}", id);
        Optional<Locatie> locatie = locatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locatie);
    }

    /**
     * {@code DELETE  /locaties/:id} : delete the "id" locatie.
     *
     * @param id the id of the locatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Locatie : {}", id);
        locatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
