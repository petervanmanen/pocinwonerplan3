package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Straatsectie;
import nl.ritense.demo.repository.StraatsectieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Straatsectie}.
 */
@RestController
@RequestMapping("/api/straatsecties")
@Transactional
public class StraatsectieResource {

    private final Logger log = LoggerFactory.getLogger(StraatsectieResource.class);

    private static final String ENTITY_NAME = "straatsectie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StraatsectieRepository straatsectieRepository;

    public StraatsectieResource(StraatsectieRepository straatsectieRepository) {
        this.straatsectieRepository = straatsectieRepository;
    }

    /**
     * {@code POST  /straatsecties} : Create a new straatsectie.
     *
     * @param straatsectie the straatsectie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new straatsectie, or with status {@code 400 (Bad Request)} if the straatsectie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Straatsectie> createStraatsectie(@Valid @RequestBody Straatsectie straatsectie) throws URISyntaxException {
        log.debug("REST request to save Straatsectie : {}", straatsectie);
        if (straatsectie.getId() != null) {
            throw new BadRequestAlertException("A new straatsectie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        straatsectie = straatsectieRepository.save(straatsectie);
        return ResponseEntity.created(new URI("/api/straatsecties/" + straatsectie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, straatsectie.getId().toString()))
            .body(straatsectie);
    }

    /**
     * {@code PUT  /straatsecties/:id} : Updates an existing straatsectie.
     *
     * @param id the id of the straatsectie to save.
     * @param straatsectie the straatsectie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated straatsectie,
     * or with status {@code 400 (Bad Request)} if the straatsectie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the straatsectie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Straatsectie> updateStraatsectie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Straatsectie straatsectie
    ) throws URISyntaxException {
        log.debug("REST request to update Straatsectie : {}, {}", id, straatsectie);
        if (straatsectie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, straatsectie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!straatsectieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        straatsectie = straatsectieRepository.save(straatsectie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, straatsectie.getId().toString()))
            .body(straatsectie);
    }

    /**
     * {@code PATCH  /straatsecties/:id} : Partial updates given fields of an existing straatsectie, field will ignore if it is null
     *
     * @param id the id of the straatsectie to save.
     * @param straatsectie the straatsectie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated straatsectie,
     * or with status {@code 400 (Bad Request)} if the straatsectie is not valid,
     * or with status {@code 404 (Not Found)} if the straatsectie is not found,
     * or with status {@code 500 (Internal Server Error)} if the straatsectie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Straatsectie> partialUpdateStraatsectie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Straatsectie straatsectie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Straatsectie partially : {}, {}", id, straatsectie);
        if (straatsectie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, straatsectie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!straatsectieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Straatsectie> result = straatsectieRepository
            .findById(straatsectie.getId())
            .map(existingStraatsectie -> {
                if (straatsectie.getCode() != null) {
                    existingStraatsectie.setCode(straatsectie.getCode());
                }
                if (straatsectie.getOmschrijving() != null) {
                    existingStraatsectie.setOmschrijving(straatsectie.getOmschrijving());
                }
                if (straatsectie.getZonecode() != null) {
                    existingStraatsectie.setZonecode(straatsectie.getZonecode());
                }

                return existingStraatsectie;
            })
            .map(straatsectieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, straatsectie.getId().toString())
        );
    }

    /**
     * {@code GET  /straatsecties} : get all the straatsecties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of straatsecties in body.
     */
    @GetMapping("")
    public List<Straatsectie> getAllStraatsecties() {
        log.debug("REST request to get all Straatsecties");
        return straatsectieRepository.findAll();
    }

    /**
     * {@code GET  /straatsecties/:id} : get the "id" straatsectie.
     *
     * @param id the id of the straatsectie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the straatsectie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Straatsectie> getStraatsectie(@PathVariable("id") Long id) {
        log.debug("REST request to get Straatsectie : {}", id);
        Optional<Straatsectie> straatsectie = straatsectieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(straatsectie);
    }

    /**
     * {@code DELETE  /straatsecties/:id} : delete the "id" straatsectie.
     *
     * @param id the id of the straatsectie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStraatsectie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Straatsectie : {}", id);
        straatsectieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
