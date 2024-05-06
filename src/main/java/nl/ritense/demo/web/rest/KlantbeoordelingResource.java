package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Klantbeoordeling;
import nl.ritense.demo.repository.KlantbeoordelingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Klantbeoordeling}.
 */
@RestController
@RequestMapping("/api/klantbeoordelings")
@Transactional
public class KlantbeoordelingResource {

    private final Logger log = LoggerFactory.getLogger(KlantbeoordelingResource.class);

    private static final String ENTITY_NAME = "klantbeoordeling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KlantbeoordelingRepository klantbeoordelingRepository;

    public KlantbeoordelingResource(KlantbeoordelingRepository klantbeoordelingRepository) {
        this.klantbeoordelingRepository = klantbeoordelingRepository;
    }

    /**
     * {@code POST  /klantbeoordelings} : Create a new klantbeoordeling.
     *
     * @param klantbeoordeling the klantbeoordeling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new klantbeoordeling, or with status {@code 400 (Bad Request)} if the klantbeoordeling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Klantbeoordeling> createKlantbeoordeling(@Valid @RequestBody Klantbeoordeling klantbeoordeling)
        throws URISyntaxException {
        log.debug("REST request to save Klantbeoordeling : {}", klantbeoordeling);
        if (klantbeoordeling.getId() != null) {
            throw new BadRequestAlertException("A new klantbeoordeling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        klantbeoordeling = klantbeoordelingRepository.save(klantbeoordeling);
        return ResponseEntity.created(new URI("/api/klantbeoordelings/" + klantbeoordeling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, klantbeoordeling.getId().toString()))
            .body(klantbeoordeling);
    }

    /**
     * {@code PUT  /klantbeoordelings/:id} : Updates an existing klantbeoordeling.
     *
     * @param id the id of the klantbeoordeling to save.
     * @param klantbeoordeling the klantbeoordeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klantbeoordeling,
     * or with status {@code 400 (Bad Request)} if the klantbeoordeling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the klantbeoordeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Klantbeoordeling> updateKlantbeoordeling(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Klantbeoordeling klantbeoordeling
    ) throws URISyntaxException {
        log.debug("REST request to update Klantbeoordeling : {}, {}", id, klantbeoordeling);
        if (klantbeoordeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klantbeoordeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klantbeoordelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        klantbeoordeling = klantbeoordelingRepository.save(klantbeoordeling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, klantbeoordeling.getId().toString()))
            .body(klantbeoordeling);
    }

    /**
     * {@code PATCH  /klantbeoordelings/:id} : Partial updates given fields of an existing klantbeoordeling, field will ignore if it is null
     *
     * @param id the id of the klantbeoordeling to save.
     * @param klantbeoordeling the klantbeoordeling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klantbeoordeling,
     * or with status {@code 400 (Bad Request)} if the klantbeoordeling is not valid,
     * or with status {@code 404 (Not Found)} if the klantbeoordeling is not found,
     * or with status {@code 500 (Internal Server Error)} if the klantbeoordeling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Klantbeoordeling> partialUpdateKlantbeoordeling(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Klantbeoordeling klantbeoordeling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Klantbeoordeling partially : {}, {}", id, klantbeoordeling);
        if (klantbeoordeling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klantbeoordeling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klantbeoordelingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Klantbeoordeling> result = klantbeoordelingRepository
            .findById(klantbeoordeling.getId())
            .map(existingKlantbeoordeling -> {
                if (klantbeoordeling.getBeoordeling() != null) {
                    existingKlantbeoordeling.setBeoordeling(klantbeoordeling.getBeoordeling());
                }
                if (klantbeoordeling.getCategorie() != null) {
                    existingKlantbeoordeling.setCategorie(klantbeoordeling.getCategorie());
                }
                if (klantbeoordeling.getContactopnemen() != null) {
                    existingKlantbeoordeling.setContactopnemen(klantbeoordeling.getContactopnemen());
                }
                if (klantbeoordeling.getDdbeoordeling() != null) {
                    existingKlantbeoordeling.setDdbeoordeling(klantbeoordeling.getDdbeoordeling());
                }
                if (klantbeoordeling.getKanaal() != null) {
                    existingKlantbeoordeling.setKanaal(klantbeoordeling.getKanaal());
                }
                if (klantbeoordeling.getOnderwerp() != null) {
                    existingKlantbeoordeling.setOnderwerp(klantbeoordeling.getOnderwerp());
                }
                if (klantbeoordeling.getSubcategorie() != null) {
                    existingKlantbeoordeling.setSubcategorie(klantbeoordeling.getSubcategorie());
                }

                return existingKlantbeoordeling;
            })
            .map(klantbeoordelingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, klantbeoordeling.getId().toString())
        );
    }

    /**
     * {@code GET  /klantbeoordelings} : get all the klantbeoordelings.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of klantbeoordelings in body.
     */
    @GetMapping("")
    public List<Klantbeoordeling> getAllKlantbeoordelings(@RequestParam(name = "filter", required = false) String filter) {
        if ("heeftzaak-is-null".equals(filter)) {
            log.debug("REST request to get all Klantbeoordelings where heeftZaak is null");
            return StreamSupport.stream(klantbeoordelingRepository.findAll().spliterator(), false)
                .filter(klantbeoordeling -> klantbeoordeling.getHeeftZaak() == null)
                .toList();
        }
        log.debug("REST request to get all Klantbeoordelings");
        return klantbeoordelingRepository.findAll();
    }

    /**
     * {@code GET  /klantbeoordelings/:id} : get the "id" klantbeoordeling.
     *
     * @param id the id of the klantbeoordeling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the klantbeoordeling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Klantbeoordeling> getKlantbeoordeling(@PathVariable("id") Long id) {
        log.debug("REST request to get Klantbeoordeling : {}", id);
        Optional<Klantbeoordeling> klantbeoordeling = klantbeoordelingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(klantbeoordeling);
    }

    /**
     * {@code DELETE  /klantbeoordelings/:id} : delete the "id" klantbeoordeling.
     *
     * @param id the id of the klantbeoordeling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKlantbeoordeling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Klantbeoordeling : {}", id);
        klantbeoordelingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
