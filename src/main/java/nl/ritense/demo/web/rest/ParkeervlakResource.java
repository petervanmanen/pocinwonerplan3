package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Parkeervlak;
import nl.ritense.demo.repository.ParkeervlakRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Parkeervlak}.
 */
@RestController
@RequestMapping("/api/parkeervlaks")
@Transactional
public class ParkeervlakResource {

    private final Logger log = LoggerFactory.getLogger(ParkeervlakResource.class);

    private static final String ENTITY_NAME = "parkeervlak";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkeervlakRepository parkeervlakRepository;

    public ParkeervlakResource(ParkeervlakRepository parkeervlakRepository) {
        this.parkeervlakRepository = parkeervlakRepository;
    }

    /**
     * {@code POST  /parkeervlaks} : Create a new parkeervlak.
     *
     * @param parkeervlak the parkeervlak to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkeervlak, or with status {@code 400 (Bad Request)} if the parkeervlak has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Parkeervlak> createParkeervlak(@Valid @RequestBody Parkeervlak parkeervlak) throws URISyntaxException {
        log.debug("REST request to save Parkeervlak : {}", parkeervlak);
        if (parkeervlak.getId() != null) {
            throw new BadRequestAlertException("A new parkeervlak cannot already have an ID", ENTITY_NAME, "idexists");
        }
        parkeervlak = parkeervlakRepository.save(parkeervlak);
        return ResponseEntity.created(new URI("/api/parkeervlaks/" + parkeervlak.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, parkeervlak.getId().toString()))
            .body(parkeervlak);
    }

    /**
     * {@code PUT  /parkeervlaks/:id} : Updates an existing parkeervlak.
     *
     * @param id the id of the parkeervlak to save.
     * @param parkeervlak the parkeervlak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeervlak,
     * or with status {@code 400 (Bad Request)} if the parkeervlak is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkeervlak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Parkeervlak> updateParkeervlak(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Parkeervlak parkeervlak
    ) throws URISyntaxException {
        log.debug("REST request to update Parkeervlak : {}, {}", id, parkeervlak);
        if (parkeervlak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeervlak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeervlakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        parkeervlak = parkeervlakRepository.save(parkeervlak);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeervlak.getId().toString()))
            .body(parkeervlak);
    }

    /**
     * {@code PATCH  /parkeervlaks/:id} : Partial updates given fields of an existing parkeervlak, field will ignore if it is null
     *
     * @param id the id of the parkeervlak to save.
     * @param parkeervlak the parkeervlak to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkeervlak,
     * or with status {@code 400 (Bad Request)} if the parkeervlak is not valid,
     * or with status {@code 404 (Not Found)} if the parkeervlak is not found,
     * or with status {@code 500 (Internal Server Error)} if the parkeervlak couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Parkeervlak> partialUpdateParkeervlak(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Parkeervlak parkeervlak
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parkeervlak partially : {}, {}", id, parkeervlak);
        if (parkeervlak.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parkeervlak.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parkeervlakRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Parkeervlak> result = parkeervlakRepository
            .findById(parkeervlak.getId())
            .map(existingParkeervlak -> {
                if (parkeervlak.getAantal() != null) {
                    existingParkeervlak.setAantal(parkeervlak.getAantal());
                }
                if (parkeervlak.getCoordinaten() != null) {
                    existingParkeervlak.setCoordinaten(parkeervlak.getCoordinaten());
                }
                if (parkeervlak.getDoelgroep() != null) {
                    existingParkeervlak.setDoelgroep(parkeervlak.getDoelgroep());
                }
                if (parkeervlak.getFiscaal() != null) {
                    existingParkeervlak.setFiscaal(parkeervlak.getFiscaal());
                }
                if (parkeervlak.getPlaats() != null) {
                    existingParkeervlak.setPlaats(parkeervlak.getPlaats());
                }
                if (parkeervlak.getVlakid() != null) {
                    existingParkeervlak.setVlakid(parkeervlak.getVlakid());
                }

                return existingParkeervlak;
            })
            .map(parkeervlakRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkeervlak.getId().toString())
        );
    }

    /**
     * {@code GET  /parkeervlaks} : get all the parkeervlaks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkeervlaks in body.
     */
    @GetMapping("")
    public List<Parkeervlak> getAllParkeervlaks() {
        log.debug("REST request to get all Parkeervlaks");
        return parkeervlakRepository.findAll();
    }

    /**
     * {@code GET  /parkeervlaks/:id} : get the "id" parkeervlak.
     *
     * @param id the id of the parkeervlak to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkeervlak, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Parkeervlak> getParkeervlak(@PathVariable("id") Long id) {
        log.debug("REST request to get Parkeervlak : {}", id);
        Optional<Parkeervlak> parkeervlak = parkeervlakRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parkeervlak);
    }

    /**
     * {@code DELETE  /parkeervlaks/:id} : delete the "id" parkeervlak.
     *
     * @param id the id of the parkeervlak to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkeervlak(@PathVariable("id") Long id) {
        log.debug("REST request to delete Parkeervlak : {}", id);
        parkeervlakRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
