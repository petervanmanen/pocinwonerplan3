package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verzoek;
import nl.ritense.demo.repository.VerzoekRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verzoek}.
 */
@RestController
@RequestMapping("/api/verzoeks")
@Transactional
public class VerzoekResource {

    private final Logger log = LoggerFactory.getLogger(VerzoekResource.class);

    private static final String ENTITY_NAME = "verzoek";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerzoekRepository verzoekRepository;

    public VerzoekResource(VerzoekRepository verzoekRepository) {
        this.verzoekRepository = verzoekRepository;
    }

    /**
     * {@code POST  /verzoeks} : Create a new verzoek.
     *
     * @param verzoek the verzoek to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verzoek, or with status {@code 400 (Bad Request)} if the verzoek has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verzoek> createVerzoek(@Valid @RequestBody Verzoek verzoek) throws URISyntaxException {
        log.debug("REST request to save Verzoek : {}", verzoek);
        if (verzoek.getId() != null) {
            throw new BadRequestAlertException("A new verzoek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verzoek = verzoekRepository.save(verzoek);
        return ResponseEntity.created(new URI("/api/verzoeks/" + verzoek.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verzoek.getId().toString()))
            .body(verzoek);
    }

    /**
     * {@code PUT  /verzoeks/:id} : Updates an existing verzoek.
     *
     * @param id the id of the verzoek to save.
     * @param verzoek the verzoek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzoek,
     * or with status {@code 400 (Bad Request)} if the verzoek is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verzoek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verzoek> updateVerzoek(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verzoek verzoek
    ) throws URISyntaxException {
        log.debug("REST request to update Verzoek : {}, {}", id, verzoek);
        if (verzoek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzoek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzoekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verzoek = verzoekRepository.save(verzoek);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzoek.getId().toString()))
            .body(verzoek);
    }

    /**
     * {@code PATCH  /verzoeks/:id} : Partial updates given fields of an existing verzoek, field will ignore if it is null
     *
     * @param id the id of the verzoek to save.
     * @param verzoek the verzoek to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verzoek,
     * or with status {@code 400 (Bad Request)} if the verzoek is not valid,
     * or with status {@code 404 (Not Found)} if the verzoek is not found,
     * or with status {@code 500 (Internal Server Error)} if the verzoek couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verzoek> partialUpdateVerzoek(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verzoek verzoek
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verzoek partially : {}, {}", id, verzoek);
        if (verzoek.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verzoek.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verzoekRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verzoek> result = verzoekRepository
            .findById(verzoek.getId())
            .map(existingVerzoek -> {
                if (verzoek.getAkkoordverklaring() != null) {
                    existingVerzoek.setAkkoordverklaring(verzoek.getAkkoordverklaring());
                }
                if (verzoek.getAmbtshalve() != null) {
                    existingVerzoek.setAmbtshalve(verzoek.getAmbtshalve());
                }
                if (verzoek.getDatumindiening() != null) {
                    existingVerzoek.setDatumindiening(verzoek.getDatumindiening());
                }
                if (verzoek.getDoel() != null) {
                    existingVerzoek.setDoel(verzoek.getDoel());
                }
                if (verzoek.getNaam() != null) {
                    existingVerzoek.setNaam(verzoek.getNaam());
                }
                if (verzoek.getReferentieaanvrager() != null) {
                    existingVerzoek.setReferentieaanvrager(verzoek.getReferentieaanvrager());
                }
                if (verzoek.getToelichtinglateraantelevereninformatie() != null) {
                    existingVerzoek.setToelichtinglateraantelevereninformatie(verzoek.getToelichtinglateraantelevereninformatie());
                }
                if (verzoek.getToelichtingnietaantelevereninformatie() != null) {
                    existingVerzoek.setToelichtingnietaantelevereninformatie(verzoek.getToelichtingnietaantelevereninformatie());
                }
                if (verzoek.getToelichtingverzoek() != null) {
                    existingVerzoek.setToelichtingverzoek(verzoek.getToelichtingverzoek());
                }
                if (verzoek.getType() != null) {
                    existingVerzoek.setType(verzoek.getType());
                }
                if (verzoek.getVerzoeknummer() != null) {
                    existingVerzoek.setVerzoeknummer(verzoek.getVerzoeknummer());
                }
                if (verzoek.getVolgnummer() != null) {
                    existingVerzoek.setVolgnummer(verzoek.getVolgnummer());
                }

                return existingVerzoek;
            })
            .map(verzoekRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verzoek.getId().toString())
        );
    }

    /**
     * {@code GET  /verzoeks} : get all the verzoeks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verzoeks in body.
     */
    @GetMapping("")
    public List<Verzoek> getAllVerzoeks(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Verzoeks");
        if (eagerload) {
            return verzoekRepository.findAllWithEagerRelationships();
        } else {
            return verzoekRepository.findAll();
        }
    }

    /**
     * {@code GET  /verzoeks/:id} : get the "id" verzoek.
     *
     * @param id the id of the verzoek to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verzoek, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verzoek> getVerzoek(@PathVariable("id") Long id) {
        log.debug("REST request to get Verzoek : {}", id);
        Optional<Verzoek> verzoek = verzoekRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(verzoek);
    }

    /**
     * {@code DELETE  /verzoeks/:id} : delete the "id" verzoek.
     *
     * @param id the id of the verzoek to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerzoek(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verzoek : {}", id);
        verzoekRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
