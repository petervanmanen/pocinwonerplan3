package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import nl.ritense.demo.domain.Klantcontact;
import nl.ritense.demo.repository.KlantcontactRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Klantcontact}.
 */
@RestController
@RequestMapping("/api/klantcontacts")
@Transactional
public class KlantcontactResource {

    private final Logger log = LoggerFactory.getLogger(KlantcontactResource.class);

    private static final String ENTITY_NAME = "klantcontact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KlantcontactRepository klantcontactRepository;

    public KlantcontactResource(KlantcontactRepository klantcontactRepository) {
        this.klantcontactRepository = klantcontactRepository;
    }

    /**
     * {@code POST  /klantcontacts} : Create a new klantcontact.
     *
     * @param klantcontact the klantcontact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new klantcontact, or with status {@code 400 (Bad Request)} if the klantcontact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Klantcontact> createKlantcontact(@Valid @RequestBody Klantcontact klantcontact) throws URISyntaxException {
        log.debug("REST request to save Klantcontact : {}", klantcontact);
        if (klantcontact.getId() != null) {
            throw new BadRequestAlertException("A new klantcontact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        klantcontact = klantcontactRepository.save(klantcontact);
        return ResponseEntity.created(new URI("/api/klantcontacts/" + klantcontact.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, klantcontact.getId().toString()))
            .body(klantcontact);
    }

    /**
     * {@code PUT  /klantcontacts/:id} : Updates an existing klantcontact.
     *
     * @param id the id of the klantcontact to save.
     * @param klantcontact the klantcontact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klantcontact,
     * or with status {@code 400 (Bad Request)} if the klantcontact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the klantcontact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Klantcontact> updateKlantcontact(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Klantcontact klantcontact
    ) throws URISyntaxException {
        log.debug("REST request to update Klantcontact : {}, {}", id, klantcontact);
        if (klantcontact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klantcontact.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klantcontactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        klantcontact = klantcontactRepository.save(klantcontact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, klantcontact.getId().toString()))
            .body(klantcontact);
    }

    /**
     * {@code PATCH  /klantcontacts/:id} : Partial updates given fields of an existing klantcontact, field will ignore if it is null
     *
     * @param id the id of the klantcontact to save.
     * @param klantcontact the klantcontact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated klantcontact,
     * or with status {@code 400 (Bad Request)} if the klantcontact is not valid,
     * or with status {@code 404 (Not Found)} if the klantcontact is not found,
     * or with status {@code 500 (Internal Server Error)} if the klantcontact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Klantcontact> partialUpdateKlantcontact(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Klantcontact klantcontact
    ) throws URISyntaxException {
        log.debug("REST request to partial update Klantcontact partially : {}, {}", id, klantcontact);
        if (klantcontact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, klantcontact.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!klantcontactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Klantcontact> result = klantcontactRepository
            .findById(klantcontact.getId())
            .map(existingKlantcontact -> {
                if (klantcontact.getEindtijd() != null) {
                    existingKlantcontact.setEindtijd(klantcontact.getEindtijd());
                }
                if (klantcontact.getKanaal() != null) {
                    existingKlantcontact.setKanaal(klantcontact.getKanaal());
                }
                if (klantcontact.getNotitie() != null) {
                    existingKlantcontact.setNotitie(klantcontact.getNotitie());
                }
                if (klantcontact.getStarttijd() != null) {
                    existingKlantcontact.setStarttijd(klantcontact.getStarttijd());
                }
                if (klantcontact.getTijdsduur() != null) {
                    existingKlantcontact.setTijdsduur(klantcontact.getTijdsduur());
                }
                if (klantcontact.getToelichting() != null) {
                    existingKlantcontact.setToelichting(klantcontact.getToelichting());
                }
                if (klantcontact.getWachttijdtotaal() != null) {
                    existingKlantcontact.setWachttijdtotaal(klantcontact.getWachttijdtotaal());
                }

                return existingKlantcontact;
            })
            .map(klantcontactRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, klantcontact.getId().toString())
        );
    }

    /**
     * {@code GET  /klantcontacts} : get all the klantcontacts.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of klantcontacts in body.
     */
    @GetMapping("")
    public List<Klantcontact> getAllKlantcontacts(@RequestParam(name = "filter", required = false) String filter) {
        if ("mondtuitinbalieafspraak-is-null".equals(filter)) {
            log.debug("REST request to get all Klantcontacts where mondtuitinBalieafspraak is null");
            return StreamSupport.stream(klantcontactRepository.findAll().spliterator(), false)
                .filter(klantcontact -> klantcontact.getMondtuitinBalieafspraak() == null)
                .toList();
        }
        log.debug("REST request to get all Klantcontacts");
        return klantcontactRepository.findAll();
    }

    /**
     * {@code GET  /klantcontacts/:id} : get the "id" klantcontact.
     *
     * @param id the id of the klantcontact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the klantcontact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Klantcontact> getKlantcontact(@PathVariable("id") Long id) {
        log.debug("REST request to get Klantcontact : {}", id);
        Optional<Klantcontact> klantcontact = klantcontactRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(klantcontact);
    }

    /**
     * {@code DELETE  /klantcontacts/:id} : delete the "id" klantcontact.
     *
     * @param id the id of the klantcontact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKlantcontact(@PathVariable("id") Long id) {
        log.debug("REST request to delete Klantcontact : {}", id);
        klantcontactRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
