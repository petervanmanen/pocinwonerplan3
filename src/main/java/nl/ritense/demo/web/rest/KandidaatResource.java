package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Kandidaat;
import nl.ritense.demo.repository.KandidaatRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kandidaat}.
 */
@RestController
@RequestMapping("/api/kandidaats")
@Transactional
public class KandidaatResource {

    private final Logger log = LoggerFactory.getLogger(KandidaatResource.class);

    private static final String ENTITY_NAME = "kandidaat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KandidaatRepository kandidaatRepository;

    public KandidaatResource(KandidaatRepository kandidaatRepository) {
        this.kandidaatRepository = kandidaatRepository;
    }

    /**
     * {@code POST  /kandidaats} : Create a new kandidaat.
     *
     * @param kandidaat the kandidaat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kandidaat, or with status {@code 400 (Bad Request)} if the kandidaat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kandidaat> createKandidaat(@Valid @RequestBody Kandidaat kandidaat) throws URISyntaxException {
        log.debug("REST request to save Kandidaat : {}", kandidaat);
        if (kandidaat.getId() != null) {
            throw new BadRequestAlertException("A new kandidaat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kandidaat = kandidaatRepository.save(kandidaat);
        return ResponseEntity.created(new URI("/api/kandidaats/" + kandidaat.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kandidaat.getId().toString()))
            .body(kandidaat);
    }

    /**
     * {@code PUT  /kandidaats/:id} : Updates an existing kandidaat.
     *
     * @param id the id of the kandidaat to save.
     * @param kandidaat the kandidaat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kandidaat,
     * or with status {@code 400 (Bad Request)} if the kandidaat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kandidaat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kandidaat> updateKandidaat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Kandidaat kandidaat
    ) throws URISyntaxException {
        log.debug("REST request to update Kandidaat : {}, {}", id, kandidaat);
        if (kandidaat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kandidaat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kandidaatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kandidaat = kandidaatRepository.save(kandidaat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kandidaat.getId().toString()))
            .body(kandidaat);
    }

    /**
     * {@code PATCH  /kandidaats/:id} : Partial updates given fields of an existing kandidaat, field will ignore if it is null
     *
     * @param id the id of the kandidaat to save.
     * @param kandidaat the kandidaat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kandidaat,
     * or with status {@code 400 (Bad Request)} if the kandidaat is not valid,
     * or with status {@code 404 (Not Found)} if the kandidaat is not found,
     * or with status {@code 500 (Internal Server Error)} if the kandidaat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kandidaat> partialUpdateKandidaat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Kandidaat kandidaat
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kandidaat partially : {}, {}", id, kandidaat);
        if (kandidaat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kandidaat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kandidaatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kandidaat> result = kandidaatRepository
            .findById(kandidaat.getId())
            .map(existingKandidaat -> {
                if (kandidaat.getDatumingestuurd() != null) {
                    existingKandidaat.setDatumingestuurd(kandidaat.getDatumingestuurd());
                }

                return existingKandidaat;
            })
            .map(kandidaatRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kandidaat.getId().toString())
        );
    }

    /**
     * {@code GET  /kandidaats} : get all the kandidaats.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kandidaats in body.
     */
    @GetMapping("")
    public List<Kandidaat> getAllKandidaats(@RequestParam(name = "filter", required = false) String filter) {
        if ("betreftgunning-is-null".equals(filter)) {
            log.debug("REST request to get all Kandidaats where betreftGunning is null");
            return StreamSupport.stream(kandidaatRepository.findAll().spliterator(), false)
                .filter(kandidaat -> kandidaat.getBetreftGunning() == null)
                .toList();
        }
        log.debug("REST request to get all Kandidaats");
        return kandidaatRepository.findAll();
    }

    /**
     * {@code GET  /kandidaats/:id} : get the "id" kandidaat.
     *
     * @param id the id of the kandidaat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kandidaat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kandidaat> getKandidaat(@PathVariable("id") Long id) {
        log.debug("REST request to get Kandidaat : {}", id);
        Optional<Kandidaat> kandidaat = kandidaatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kandidaat);
    }

    /**
     * {@code DELETE  /kandidaats/:id} : delete the "id" kandidaat.
     *
     * @param id the id of the kandidaat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKandidaat(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kandidaat : {}", id);
        kandidaatRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
