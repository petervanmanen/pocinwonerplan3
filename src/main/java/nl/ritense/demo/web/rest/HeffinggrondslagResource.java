package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Heffinggrondslag;
import nl.ritense.demo.repository.HeffinggrondslagRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Heffinggrondslag}.
 */
@RestController
@RequestMapping("/api/heffinggrondslags")
@Transactional
public class HeffinggrondslagResource {

    private final Logger log = LoggerFactory.getLogger(HeffinggrondslagResource.class);

    private static final String ENTITY_NAME = "heffinggrondslag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeffinggrondslagRepository heffinggrondslagRepository;

    public HeffinggrondslagResource(HeffinggrondslagRepository heffinggrondslagRepository) {
        this.heffinggrondslagRepository = heffinggrondslagRepository;
    }

    /**
     * {@code POST  /heffinggrondslags} : Create a new heffinggrondslag.
     *
     * @param heffinggrondslag the heffinggrondslag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new heffinggrondslag, or with status {@code 400 (Bad Request)} if the heffinggrondslag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Heffinggrondslag> createHeffinggrondslag(@Valid @RequestBody Heffinggrondslag heffinggrondslag)
        throws URISyntaxException {
        log.debug("REST request to save Heffinggrondslag : {}", heffinggrondslag);
        if (heffinggrondslag.getId() != null) {
            throw new BadRequestAlertException("A new heffinggrondslag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        heffinggrondslag = heffinggrondslagRepository.save(heffinggrondslag);
        return ResponseEntity.created(new URI("/api/heffinggrondslags/" + heffinggrondslag.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, heffinggrondslag.getId().toString()))
            .body(heffinggrondslag);
    }

    /**
     * {@code PUT  /heffinggrondslags/:id} : Updates an existing heffinggrondslag.
     *
     * @param id the id of the heffinggrondslag to save.
     * @param heffinggrondslag the heffinggrondslag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heffinggrondslag,
     * or with status {@code 400 (Bad Request)} if the heffinggrondslag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the heffinggrondslag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Heffinggrondslag> updateHeffinggrondslag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Heffinggrondslag heffinggrondslag
    ) throws URISyntaxException {
        log.debug("REST request to update Heffinggrondslag : {}, {}", id, heffinggrondslag);
        if (heffinggrondslag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heffinggrondslag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heffinggrondslagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        heffinggrondslag = heffinggrondslagRepository.save(heffinggrondslag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, heffinggrondslag.getId().toString()))
            .body(heffinggrondslag);
    }

    /**
     * {@code PATCH  /heffinggrondslags/:id} : Partial updates given fields of an existing heffinggrondslag, field will ignore if it is null
     *
     * @param id the id of the heffinggrondslag to save.
     * @param heffinggrondslag the heffinggrondslag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heffinggrondslag,
     * or with status {@code 400 (Bad Request)} if the heffinggrondslag is not valid,
     * or with status {@code 404 (Not Found)} if the heffinggrondslag is not found,
     * or with status {@code 500 (Internal Server Error)} if the heffinggrondslag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Heffinggrondslag> partialUpdateHeffinggrondslag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Heffinggrondslag heffinggrondslag
    ) throws URISyntaxException {
        log.debug("REST request to partial update Heffinggrondslag partially : {}, {}", id, heffinggrondslag);
        if (heffinggrondslag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heffinggrondslag.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heffinggrondslagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Heffinggrondslag> result = heffinggrondslagRepository
            .findById(heffinggrondslag.getId())
            .map(existingHeffinggrondslag -> {
                if (heffinggrondslag.getBedrag() != null) {
                    existingHeffinggrondslag.setBedrag(heffinggrondslag.getBedrag());
                }
                if (heffinggrondslag.getDomein() != null) {
                    existingHeffinggrondslag.setDomein(heffinggrondslag.getDomein());
                }
                if (heffinggrondslag.getHoofdstuk() != null) {
                    existingHeffinggrondslag.setHoofdstuk(heffinggrondslag.getHoofdstuk());
                }
                if (heffinggrondslag.getOmschrijving() != null) {
                    existingHeffinggrondslag.setOmschrijving(heffinggrondslag.getOmschrijving());
                }
                if (heffinggrondslag.getParagraaf() != null) {
                    existingHeffinggrondslag.setParagraaf(heffinggrondslag.getParagraaf());
                }

                return existingHeffinggrondslag;
            })
            .map(heffinggrondslagRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, heffinggrondslag.getId().toString())
        );
    }

    /**
     * {@code GET  /heffinggrondslags} : get all the heffinggrondslags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heffinggrondslags in body.
     */
    @GetMapping("")
    public List<Heffinggrondslag> getAllHeffinggrondslags() {
        log.debug("REST request to get all Heffinggrondslags");
        return heffinggrondslagRepository.findAll();
    }

    /**
     * {@code GET  /heffinggrondslags/:id} : get the "id" heffinggrondslag.
     *
     * @param id the id of the heffinggrondslag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the heffinggrondslag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Heffinggrondslag> getHeffinggrondslag(@PathVariable("id") Long id) {
        log.debug("REST request to get Heffinggrondslag : {}", id);
        Optional<Heffinggrondslag> heffinggrondslag = heffinggrondslagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(heffinggrondslag);
    }

    /**
     * {@code DELETE  /heffinggrondslags/:id} : delete the "id" heffinggrondslag.
     *
     * @param id the id of the heffinggrondslag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeffinggrondslag(@PathVariable("id") Long id) {
        log.debug("REST request to delete Heffinggrondslag : {}", id);
        heffinggrondslagRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
