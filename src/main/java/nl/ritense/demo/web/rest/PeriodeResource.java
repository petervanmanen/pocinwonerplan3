package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Periode;
import nl.ritense.demo.repository.PeriodeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Periode}.
 */
@RestController
@RequestMapping("/api/periodes")
@Transactional
public class PeriodeResource {

    private final Logger log = LoggerFactory.getLogger(PeriodeResource.class);

    private static final String ENTITY_NAME = "periode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodeRepository periodeRepository;

    public PeriodeResource(PeriodeRepository periodeRepository) {
        this.periodeRepository = periodeRepository;
    }

    /**
     * {@code POST  /periodes} : Create a new periode.
     *
     * @param periode the periode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periode, or with status {@code 400 (Bad Request)} if the periode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Periode> createPeriode(@Valid @RequestBody Periode periode) throws URISyntaxException {
        log.debug("REST request to save Periode : {}", periode);
        if (periode.getId() != null) {
            throw new BadRequestAlertException("A new periode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        periode = periodeRepository.save(periode);
        return ResponseEntity.created(new URI("/api/periodes/" + periode.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, periode.getId().toString()))
            .body(periode);
    }

    /**
     * {@code PUT  /periodes/:id} : Updates an existing periode.
     *
     * @param id the id of the periode to save.
     * @param periode the periode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periode,
     * or with status {@code 400 (Bad Request)} if the periode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Periode> updatePeriode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Periode periode
    ) throws URISyntaxException {
        log.debug("REST request to update Periode : {}, {}", id, periode);
        if (periode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        periode = periodeRepository.save(periode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, periode.getId().toString()))
            .body(periode);
    }

    /**
     * {@code PATCH  /periodes/:id} : Partial updates given fields of an existing periode, field will ignore if it is null
     *
     * @param id the id of the periode to save.
     * @param periode the periode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periode,
     * or with status {@code 400 (Bad Request)} if the periode is not valid,
     * or with status {@code 404 (Not Found)} if the periode is not found,
     * or with status {@code 500 (Internal Server Error)} if the periode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Periode> partialUpdatePeriode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Periode periode
    ) throws URISyntaxException {
        log.debug("REST request to partial update Periode partially : {}, {}", id, periode);
        if (periode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Periode> result = periodeRepository
            .findById(periode.getId())
            .map(existingPeriode -> {
                if (periode.getDatumeinde() != null) {
                    existingPeriode.setDatumeinde(periode.getDatumeinde());
                }
                if (periode.getDatumstart() != null) {
                    existingPeriode.setDatumstart(periode.getDatumstart());
                }
                if (periode.getOmschrijving() != null) {
                    existingPeriode.setOmschrijving(periode.getOmschrijving());
                }

                return existingPeriode;
            })
            .map(periodeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, periode.getId().toString())
        );
    }

    /**
     * {@code GET  /periodes} : get all the periodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodes in body.
     */
    @GetMapping("")
    public List<Periode> getAllPeriodes() {
        log.debug("REST request to get all Periodes");
        return periodeRepository.findAll();
    }

    /**
     * {@code GET  /periodes/:id} : get the "id" periode.
     *
     * @param id the id of the periode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Periode> getPeriode(@PathVariable("id") Long id) {
        log.debug("REST request to get Periode : {}", id);
        Optional<Periode> periode = periodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(periode);
    }

    /**
     * {@code DELETE  /periodes/:id} : delete the "id" periode.
     *
     * @param id the id of the periode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriode(@PathVariable("id") Long id) {
        log.debug("REST request to delete Periode : {}", id);
        periodeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
