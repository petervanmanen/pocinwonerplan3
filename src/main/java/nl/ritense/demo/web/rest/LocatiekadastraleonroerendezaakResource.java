package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Locatiekadastraleonroerendezaak;
import nl.ritense.demo.repository.LocatiekadastraleonroerendezaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Locatiekadastraleonroerendezaak}.
 */
@RestController
@RequestMapping("/api/locatiekadastraleonroerendezaaks")
@Transactional
public class LocatiekadastraleonroerendezaakResource {

    private final Logger log = LoggerFactory.getLogger(LocatiekadastraleonroerendezaakResource.class);

    private static final String ENTITY_NAME = "locatiekadastraleonroerendezaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocatiekadastraleonroerendezaakRepository locatiekadastraleonroerendezaakRepository;

    public LocatiekadastraleonroerendezaakResource(LocatiekadastraleonroerendezaakRepository locatiekadastraleonroerendezaakRepository) {
        this.locatiekadastraleonroerendezaakRepository = locatiekadastraleonroerendezaakRepository;
    }

    /**
     * {@code POST  /locatiekadastraleonroerendezaaks} : Create a new locatiekadastraleonroerendezaak.
     *
     * @param locatiekadastraleonroerendezaak the locatiekadastraleonroerendezaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locatiekadastraleonroerendezaak, or with status {@code 400 (Bad Request)} if the locatiekadastraleonroerendezaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Locatiekadastraleonroerendezaak> createLocatiekadastraleonroerendezaak(
        @RequestBody Locatiekadastraleonroerendezaak locatiekadastraleonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to save Locatiekadastraleonroerendezaak : {}", locatiekadastraleonroerendezaak);
        if (locatiekadastraleonroerendezaak.getId() != null) {
            throw new BadRequestAlertException("A new locatiekadastraleonroerendezaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        locatiekadastraleonroerendezaak = locatiekadastraleonroerendezaakRepository.save(locatiekadastraleonroerendezaak);
        return ResponseEntity.created(new URI("/api/locatiekadastraleonroerendezaaks/" + locatiekadastraleonroerendezaak.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    locatiekadastraleonroerendezaak.getId().toString()
                )
            )
            .body(locatiekadastraleonroerendezaak);
    }

    /**
     * {@code PUT  /locatiekadastraleonroerendezaaks/:id} : Updates an existing locatiekadastraleonroerendezaak.
     *
     * @param id the id of the locatiekadastraleonroerendezaak to save.
     * @param locatiekadastraleonroerendezaak the locatiekadastraleonroerendezaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatiekadastraleonroerendezaak,
     * or with status {@code 400 (Bad Request)} if the locatiekadastraleonroerendezaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locatiekadastraleonroerendezaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Locatiekadastraleonroerendezaak> updateLocatiekadastraleonroerendezaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Locatiekadastraleonroerendezaak locatiekadastraleonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to update Locatiekadastraleonroerendezaak : {}, {}", id, locatiekadastraleonroerendezaak);
        if (locatiekadastraleonroerendezaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatiekadastraleonroerendezaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatiekadastraleonroerendezaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locatiekadastraleonroerendezaak = locatiekadastraleonroerendezaakRepository.save(locatiekadastraleonroerendezaak);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatiekadastraleonroerendezaak.getId().toString())
            )
            .body(locatiekadastraleonroerendezaak);
    }

    /**
     * {@code PATCH  /locatiekadastraleonroerendezaaks/:id} : Partial updates given fields of an existing locatiekadastraleonroerendezaak, field will ignore if it is null
     *
     * @param id the id of the locatiekadastraleonroerendezaak to save.
     * @param locatiekadastraleonroerendezaak the locatiekadastraleonroerendezaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatiekadastraleonroerendezaak,
     * or with status {@code 400 (Bad Request)} if the locatiekadastraleonroerendezaak is not valid,
     * or with status {@code 404 (Not Found)} if the locatiekadastraleonroerendezaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the locatiekadastraleonroerendezaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Locatiekadastraleonroerendezaak> partialUpdateLocatiekadastraleonroerendezaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Locatiekadastraleonroerendezaak locatiekadastraleonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Locatiekadastraleonroerendezaak partially : {}, {}", id, locatiekadastraleonroerendezaak);
        if (locatiekadastraleonroerendezaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatiekadastraleonroerendezaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatiekadastraleonroerendezaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Locatiekadastraleonroerendezaak> result = locatiekadastraleonroerendezaakRepository
            .findById(locatiekadastraleonroerendezaak.getId())
            .map(existingLocatiekadastraleonroerendezaak -> {
                if (locatiekadastraleonroerendezaak.getAardcultuurbebouwd() != null) {
                    existingLocatiekadastraleonroerendezaak.setAardcultuurbebouwd(locatiekadastraleonroerendezaak.getAardcultuurbebouwd());
                }
                if (locatiekadastraleonroerendezaak.getLocatieomschrijving() != null) {
                    existingLocatiekadastraleonroerendezaak.setLocatieomschrijving(
                        locatiekadastraleonroerendezaak.getLocatieomschrijving()
                    );
                }

                return existingLocatiekadastraleonroerendezaak;
            })
            .map(locatiekadastraleonroerendezaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatiekadastraleonroerendezaak.getId().toString())
        );
    }

    /**
     * {@code GET  /locatiekadastraleonroerendezaaks} : get all the locatiekadastraleonroerendezaaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locatiekadastraleonroerendezaaks in body.
     */
    @GetMapping("")
    public List<Locatiekadastraleonroerendezaak> getAllLocatiekadastraleonroerendezaaks() {
        log.debug("REST request to get all Locatiekadastraleonroerendezaaks");
        return locatiekadastraleonroerendezaakRepository.findAll();
    }

    /**
     * {@code GET  /locatiekadastraleonroerendezaaks/:id} : get the "id" locatiekadastraleonroerendezaak.
     *
     * @param id the id of the locatiekadastraleonroerendezaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locatiekadastraleonroerendezaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Locatiekadastraleonroerendezaak> getLocatiekadastraleonroerendezaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Locatiekadastraleonroerendezaak : {}", id);
        Optional<Locatiekadastraleonroerendezaak> locatiekadastraleonroerendezaak = locatiekadastraleonroerendezaakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locatiekadastraleonroerendezaak);
    }

    /**
     * {@code DELETE  /locatiekadastraleonroerendezaaks/:id} : delete the "id" locatiekadastraleonroerendezaak.
     *
     * @param id the id of the locatiekadastraleonroerendezaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocatiekadastraleonroerendezaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Locatiekadastraleonroerendezaak : {}", id);
        locatiekadastraleonroerendezaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
