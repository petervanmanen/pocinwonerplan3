package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Specificatie;
import nl.ritense.demo.repository.SpecificatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Specificatie}.
 */
@RestController
@RequestMapping("/api/specificaties")
@Transactional
public class SpecificatieResource {

    private final Logger log = LoggerFactory.getLogger(SpecificatieResource.class);

    private static final String ENTITY_NAME = "specificatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecificatieRepository specificatieRepository;

    public SpecificatieResource(SpecificatieRepository specificatieRepository) {
        this.specificatieRepository = specificatieRepository;
    }

    /**
     * {@code POST  /specificaties} : Create a new specificatie.
     *
     * @param specificatie the specificatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specificatie, or with status {@code 400 (Bad Request)} if the specificatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Specificatie> createSpecificatie(@Valid @RequestBody Specificatie specificatie) throws URISyntaxException {
        log.debug("REST request to save Specificatie : {}", specificatie);
        if (specificatie.getId() != null) {
            throw new BadRequestAlertException("A new specificatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        specificatie = specificatieRepository.save(specificatie);
        return ResponseEntity.created(new URI("/api/specificaties/" + specificatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, specificatie.getId().toString()))
            .body(specificatie);
    }

    /**
     * {@code PUT  /specificaties/:id} : Updates an existing specificatie.
     *
     * @param id the id of the specificatie to save.
     * @param specificatie the specificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specificatie,
     * or with status {@code 400 (Bad Request)} if the specificatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Specificatie> updateSpecificatie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Specificatie specificatie
    ) throws URISyntaxException {
        log.debug("REST request to update Specificatie : {}, {}", id, specificatie);
        if (specificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        specificatie = specificatieRepository.save(specificatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, specificatie.getId().toString()))
            .body(specificatie);
    }

    /**
     * {@code PATCH  /specificaties/:id} : Partial updates given fields of an existing specificatie, field will ignore if it is null
     *
     * @param id the id of the specificatie to save.
     * @param specificatie the specificatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specificatie,
     * or with status {@code 400 (Bad Request)} if the specificatie is not valid,
     * or with status {@code 404 (Not Found)} if the specificatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the specificatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Specificatie> partialUpdateSpecificatie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Specificatie specificatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Specificatie partially : {}, {}", id, specificatie);
        if (specificatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specificatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specificatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Specificatie> result = specificatieRepository
            .findById(specificatie.getId())
            .map(existingSpecificatie -> {
                if (specificatie.getAntwoord() != null) {
                    existingSpecificatie.setAntwoord(specificatie.getAntwoord());
                }
                if (specificatie.getGroepering() != null) {
                    existingSpecificatie.setGroepering(specificatie.getGroepering());
                }
                if (specificatie.getPubliceerbaar() != null) {
                    existingSpecificatie.setPubliceerbaar(specificatie.getPubliceerbaar());
                }
                if (specificatie.getVraagclassificatie() != null) {
                    existingSpecificatie.setVraagclassificatie(specificatie.getVraagclassificatie());
                }
                if (specificatie.getVraagid() != null) {
                    existingSpecificatie.setVraagid(specificatie.getVraagid());
                }
                if (specificatie.getVraagreferentie() != null) {
                    existingSpecificatie.setVraagreferentie(specificatie.getVraagreferentie());
                }
                if (specificatie.getVraagtekst() != null) {
                    existingSpecificatie.setVraagtekst(specificatie.getVraagtekst());
                }

                return existingSpecificatie;
            })
            .map(specificatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, specificatie.getId().toString())
        );
    }

    /**
     * {@code GET  /specificaties} : get all the specificaties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specificaties in body.
     */
    @GetMapping("")
    public List<Specificatie> getAllSpecificaties() {
        log.debug("REST request to get all Specificaties");
        return specificatieRepository.findAll();
    }

    /**
     * {@code GET  /specificaties/:id} : get the "id" specificatie.
     *
     * @param id the id of the specificatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specificatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Specificatie> getSpecificatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Specificatie : {}", id);
        Optional<Specificatie> specificatie = specificatieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(specificatie);
    }

    /**
     * {@code DELETE  /specificaties/:id} : delete the "id" specificatie.
     *
     * @param id the id of the specificatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecificatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Specificatie : {}", id);
        specificatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
