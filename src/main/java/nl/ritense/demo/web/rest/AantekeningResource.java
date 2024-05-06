package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aantekening;
import nl.ritense.demo.repository.AantekeningRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aantekening}.
 */
@RestController
@RequestMapping("/api/aantekenings")
@Transactional
public class AantekeningResource {

    private final Logger log = LoggerFactory.getLogger(AantekeningResource.class);

    private static final String ENTITY_NAME = "aantekening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AantekeningRepository aantekeningRepository;

    public AantekeningResource(AantekeningRepository aantekeningRepository) {
        this.aantekeningRepository = aantekeningRepository;
    }

    /**
     * {@code POST  /aantekenings} : Create a new aantekening.
     *
     * @param aantekening the aantekening to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aantekening, or with status {@code 400 (Bad Request)} if the aantekening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aantekening> createAantekening(@Valid @RequestBody Aantekening aantekening) throws URISyntaxException {
        log.debug("REST request to save Aantekening : {}", aantekening);
        if (aantekening.getId() != null) {
            throw new BadRequestAlertException("A new aantekening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aantekening = aantekeningRepository.save(aantekening);
        return ResponseEntity.created(new URI("/api/aantekenings/" + aantekening.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aantekening.getId().toString()))
            .body(aantekening);
    }

    /**
     * {@code PUT  /aantekenings/:id} : Updates an existing aantekening.
     *
     * @param id the id of the aantekening to save.
     * @param aantekening the aantekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aantekening,
     * or with status {@code 400 (Bad Request)} if the aantekening is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aantekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aantekening> updateAantekening(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Aantekening aantekening
    ) throws URISyntaxException {
        log.debug("REST request to update Aantekening : {}, {}", id, aantekening);
        if (aantekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aantekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aantekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aantekening = aantekeningRepository.save(aantekening);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aantekening.getId().toString()))
            .body(aantekening);
    }

    /**
     * {@code PATCH  /aantekenings/:id} : Partial updates given fields of an existing aantekening, field will ignore if it is null
     *
     * @param id the id of the aantekening to save.
     * @param aantekening the aantekening to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aantekening,
     * or with status {@code 400 (Bad Request)} if the aantekening is not valid,
     * or with status {@code 404 (Not Found)} if the aantekening is not found,
     * or with status {@code 500 (Internal Server Error)} if the aantekening couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aantekening> partialUpdateAantekening(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Aantekening aantekening
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aantekening partially : {}, {}", id, aantekening);
        if (aantekening.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aantekening.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aantekeningRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aantekening> result = aantekeningRepository
            .findById(aantekening.getId())
            .map(existingAantekening -> {
                if (aantekening.getAard() != null) {
                    existingAantekening.setAard(aantekening.getAard());
                }
                if (aantekening.getBegrenzing() != null) {
                    existingAantekening.setBegrenzing(aantekening.getBegrenzing());
                }
                if (aantekening.getBetreftgedeeltevanperceel() != null) {
                    existingAantekening.setBetreftgedeeltevanperceel(aantekening.getBetreftgedeeltevanperceel());
                }
                if (aantekening.getDatumeinde() != null) {
                    existingAantekening.setDatumeinde(aantekening.getDatumeinde());
                }
                if (aantekening.getDatumeinderecht() != null) {
                    existingAantekening.setDatumeinderecht(aantekening.getDatumeinderecht());
                }
                if (aantekening.getIdentificatie() != null) {
                    existingAantekening.setIdentificatie(aantekening.getIdentificatie());
                }
                if (aantekening.getOmschrijving() != null) {
                    existingAantekening.setOmschrijving(aantekening.getOmschrijving());
                }

                return existingAantekening;
            })
            .map(aantekeningRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aantekening.getId().toString())
        );
    }

    /**
     * {@code GET  /aantekenings} : get all the aantekenings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aantekenings in body.
     */
    @GetMapping("")
    public List<Aantekening> getAllAantekenings() {
        log.debug("REST request to get all Aantekenings");
        return aantekeningRepository.findAll();
    }

    /**
     * {@code GET  /aantekenings/:id} : get the "id" aantekening.
     *
     * @param id the id of the aantekening to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aantekening, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aantekening> getAantekening(@PathVariable("id") Long id) {
        log.debug("REST request to get Aantekening : {}", id);
        Optional<Aantekening> aantekening = aantekeningRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aantekening);
    }

    /**
     * {@code DELETE  /aantekenings/:id} : delete the "id" aantekening.
     *
     * @param id the id of the aantekening to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAantekening(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aantekening : {}", id);
        aantekeningRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
