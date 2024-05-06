package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Prijsafspraak;
import nl.ritense.demo.repository.PrijsafspraakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Prijsafspraak}.
 */
@RestController
@RequestMapping("/api/prijsafspraaks")
@Transactional
public class PrijsafspraakResource {

    private final Logger log = LoggerFactory.getLogger(PrijsafspraakResource.class);

    private static final String ENTITY_NAME = "prijsafspraak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrijsafspraakRepository prijsafspraakRepository;

    public PrijsafspraakResource(PrijsafspraakRepository prijsafspraakRepository) {
        this.prijsafspraakRepository = prijsafspraakRepository;
    }

    /**
     * {@code POST  /prijsafspraaks} : Create a new prijsafspraak.
     *
     * @param prijsafspraak the prijsafspraak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prijsafspraak, or with status {@code 400 (Bad Request)} if the prijsafspraak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Prijsafspraak> createPrijsafspraak(@Valid @RequestBody Prijsafspraak prijsafspraak) throws URISyntaxException {
        log.debug("REST request to save Prijsafspraak : {}", prijsafspraak);
        if (prijsafspraak.getId() != null) {
            throw new BadRequestAlertException("A new prijsafspraak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prijsafspraak = prijsafspraakRepository.save(prijsafspraak);
        return ResponseEntity.created(new URI("/api/prijsafspraaks/" + prijsafspraak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, prijsafspraak.getId().toString()))
            .body(prijsafspraak);
    }

    /**
     * {@code PUT  /prijsafspraaks/:id} : Updates an existing prijsafspraak.
     *
     * @param id the id of the prijsafspraak to save.
     * @param prijsafspraak the prijsafspraak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijsafspraak,
     * or with status {@code 400 (Bad Request)} if the prijsafspraak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prijsafspraak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Prijsafspraak> updatePrijsafspraak(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Prijsafspraak prijsafspraak
    ) throws URISyntaxException {
        log.debug("REST request to update Prijsafspraak : {}, {}", id, prijsafspraak);
        if (prijsafspraak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijsafspraak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijsafspraakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        prijsafspraak = prijsafspraakRepository.save(prijsafspraak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijsafspraak.getId().toString()))
            .body(prijsafspraak);
    }

    /**
     * {@code PATCH  /prijsafspraaks/:id} : Partial updates given fields of an existing prijsafspraak, field will ignore if it is null
     *
     * @param id the id of the prijsafspraak to save.
     * @param prijsafspraak the prijsafspraak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prijsafspraak,
     * or with status {@code 400 (Bad Request)} if the prijsafspraak is not valid,
     * or with status {@code 404 (Not Found)} if the prijsafspraak is not found,
     * or with status {@code 500 (Internal Server Error)} if the prijsafspraak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Prijsafspraak> partialUpdatePrijsafspraak(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Prijsafspraak prijsafspraak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prijsafspraak partially : {}, {}", id, prijsafspraak);
        if (prijsafspraak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prijsafspraak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prijsafspraakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Prijsafspraak> result = prijsafspraakRepository
            .findById(prijsafspraak.getId())
            .map(existingPrijsafspraak -> {
                if (prijsafspraak.getDatumeinde() != null) {
                    existingPrijsafspraak.setDatumeinde(prijsafspraak.getDatumeinde());
                }
                if (prijsafspraak.getDatumstart() != null) {
                    existingPrijsafspraak.setDatumstart(prijsafspraak.getDatumstart());
                }
                if (prijsafspraak.getTitel() != null) {
                    existingPrijsafspraak.setTitel(prijsafspraak.getTitel());
                }

                return existingPrijsafspraak;
            })
            .map(prijsafspraakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prijsafspraak.getId().toString())
        );
    }

    /**
     * {@code GET  /prijsafspraaks} : get all the prijsafspraaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prijsafspraaks in body.
     */
    @GetMapping("")
    public List<Prijsafspraak> getAllPrijsafspraaks() {
        log.debug("REST request to get all Prijsafspraaks");
        return prijsafspraakRepository.findAll();
    }

    /**
     * {@code GET  /prijsafspraaks/:id} : get the "id" prijsafspraak.
     *
     * @param id the id of the prijsafspraak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prijsafspraak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prijsafspraak> getPrijsafspraak(@PathVariable("id") Long id) {
        log.debug("REST request to get Prijsafspraak : {}", id);
        Optional<Prijsafspraak> prijsafspraak = prijsafspraakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prijsafspraak);
    }

    /**
     * {@code DELETE  /prijsafspraaks/:id} : delete the "id" prijsafspraak.
     *
     * @param id the id of the prijsafspraak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrijsafspraak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Prijsafspraak : {}", id);
        prijsafspraakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
