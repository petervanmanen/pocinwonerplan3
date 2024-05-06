package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Locatieonroerendezaak;
import nl.ritense.demo.repository.LocatieonroerendezaakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Locatieonroerendezaak}.
 */
@RestController
@RequestMapping("/api/locatieonroerendezaaks")
@Transactional
public class LocatieonroerendezaakResource {

    private final Logger log = LoggerFactory.getLogger(LocatieonroerendezaakResource.class);

    private static final String ENTITY_NAME = "locatieonroerendezaak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocatieonroerendezaakRepository locatieonroerendezaakRepository;

    public LocatieonroerendezaakResource(LocatieonroerendezaakRepository locatieonroerendezaakRepository) {
        this.locatieonroerendezaakRepository = locatieonroerendezaakRepository;
    }

    /**
     * {@code POST  /locatieonroerendezaaks} : Create a new locatieonroerendezaak.
     *
     * @param locatieonroerendezaak the locatieonroerendezaak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locatieonroerendezaak, or with status {@code 400 (Bad Request)} if the locatieonroerendezaak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Locatieonroerendezaak> createLocatieonroerendezaak(@RequestBody Locatieonroerendezaak locatieonroerendezaak)
        throws URISyntaxException {
        log.debug("REST request to save Locatieonroerendezaak : {}", locatieonroerendezaak);
        if (locatieonroerendezaak.getId() != null) {
            throw new BadRequestAlertException("A new locatieonroerendezaak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        locatieonroerendezaak = locatieonroerendezaakRepository.save(locatieonroerendezaak);
        return ResponseEntity.created(new URI("/api/locatieonroerendezaaks/" + locatieonroerendezaak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, locatieonroerendezaak.getId().toString()))
            .body(locatieonroerendezaak);
    }

    /**
     * {@code PUT  /locatieonroerendezaaks/:id} : Updates an existing locatieonroerendezaak.
     *
     * @param id the id of the locatieonroerendezaak to save.
     * @param locatieonroerendezaak the locatieonroerendezaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatieonroerendezaak,
     * or with status {@code 400 (Bad Request)} if the locatieonroerendezaak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locatieonroerendezaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Locatieonroerendezaak> updateLocatieonroerendezaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Locatieonroerendezaak locatieonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to update Locatieonroerendezaak : {}, {}", id, locatieonroerendezaak);
        if (locatieonroerendezaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatieonroerendezaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatieonroerendezaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locatieonroerendezaak = locatieonroerendezaakRepository.save(locatieonroerendezaak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatieonroerendezaak.getId().toString()))
            .body(locatieonroerendezaak);
    }

    /**
     * {@code PATCH  /locatieonroerendezaaks/:id} : Partial updates given fields of an existing locatieonroerendezaak, field will ignore if it is null
     *
     * @param id the id of the locatieonroerendezaak to save.
     * @param locatieonroerendezaak the locatieonroerendezaak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locatieonroerendezaak,
     * or with status {@code 400 (Bad Request)} if the locatieonroerendezaak is not valid,
     * or with status {@code 404 (Not Found)} if the locatieonroerendezaak is not found,
     * or with status {@code 500 (Internal Server Error)} if the locatieonroerendezaak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Locatieonroerendezaak> partialUpdateLocatieonroerendezaak(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Locatieonroerendezaak locatieonroerendezaak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Locatieonroerendezaak partially : {}, {}", id, locatieonroerendezaak);
        if (locatieonroerendezaak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, locatieonroerendezaak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locatieonroerendezaakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Locatieonroerendezaak> result = locatieonroerendezaakRepository
            .findById(locatieonroerendezaak.getId())
            .map(existingLocatieonroerendezaak -> {
                if (locatieonroerendezaak.getAdrestype() != null) {
                    existingLocatieonroerendezaak.setAdrestype(locatieonroerendezaak.getAdrestype());
                }
                if (locatieonroerendezaak.getCultuurcodebebouwd() != null) {
                    existingLocatieonroerendezaak.setCultuurcodebebouwd(locatieonroerendezaak.getCultuurcodebebouwd());
                }
                if (locatieonroerendezaak.getDatumbegingeldigheid() != null) {
                    existingLocatieonroerendezaak.setDatumbegingeldigheid(locatieonroerendezaak.getDatumbegingeldigheid());
                }
                if (locatieonroerendezaak.getDatumeindegeldigheid() != null) {
                    existingLocatieonroerendezaak.setDatumeindegeldigheid(locatieonroerendezaak.getDatumeindegeldigheid());
                }
                if (locatieonroerendezaak.getGeometrie() != null) {
                    existingLocatieonroerendezaak.setGeometrie(locatieonroerendezaak.getGeometrie());
                }
                if (locatieonroerendezaak.getLocatieomschrijving() != null) {
                    existingLocatieonroerendezaak.setLocatieomschrijving(locatieonroerendezaak.getLocatieomschrijving());
                }

                return existingLocatieonroerendezaak;
            })
            .map(locatieonroerendezaakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locatieonroerendezaak.getId().toString())
        );
    }

    /**
     * {@code GET  /locatieonroerendezaaks} : get all the locatieonroerendezaaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locatieonroerendezaaks in body.
     */
    @GetMapping("")
    public List<Locatieonroerendezaak> getAllLocatieonroerendezaaks() {
        log.debug("REST request to get all Locatieonroerendezaaks");
        return locatieonroerendezaakRepository.findAll();
    }

    /**
     * {@code GET  /locatieonroerendezaaks/:id} : get the "id" locatieonroerendezaak.
     *
     * @param id the id of the locatieonroerendezaak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locatieonroerendezaak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Locatieonroerendezaak> getLocatieonroerendezaak(@PathVariable("id") Long id) {
        log.debug("REST request to get Locatieonroerendezaak : {}", id);
        Optional<Locatieonroerendezaak> locatieonroerendezaak = locatieonroerendezaakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locatieonroerendezaak);
    }

    /**
     * {@code DELETE  /locatieonroerendezaaks/:id} : delete the "id" locatieonroerendezaak.
     *
     * @param id the id of the locatieonroerendezaak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocatieonroerendezaak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Locatieonroerendezaak : {}", id);
        locatieonroerendezaakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
