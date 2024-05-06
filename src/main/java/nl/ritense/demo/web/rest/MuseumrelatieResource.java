package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Museumrelatie;
import nl.ritense.demo.repository.MuseumrelatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Museumrelatie}.
 */
@RestController
@RequestMapping("/api/museumrelaties")
@Transactional
public class MuseumrelatieResource {

    private final Logger log = LoggerFactory.getLogger(MuseumrelatieResource.class);

    private static final String ENTITY_NAME = "museumrelatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MuseumrelatieRepository museumrelatieRepository;

    public MuseumrelatieResource(MuseumrelatieRepository museumrelatieRepository) {
        this.museumrelatieRepository = museumrelatieRepository;
    }

    /**
     * {@code POST  /museumrelaties} : Create a new museumrelatie.
     *
     * @param museumrelatie the museumrelatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new museumrelatie, or with status {@code 400 (Bad Request)} if the museumrelatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Museumrelatie> createMuseumrelatie(@Valid @RequestBody Museumrelatie museumrelatie) throws URISyntaxException {
        log.debug("REST request to save Museumrelatie : {}", museumrelatie);
        if (museumrelatie.getId() != null) {
            throw new BadRequestAlertException("A new museumrelatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        museumrelatie = museumrelatieRepository.save(museumrelatie);
        return ResponseEntity.created(new URI("/api/museumrelaties/" + museumrelatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, museumrelatie.getId().toString()))
            .body(museumrelatie);
    }

    /**
     * {@code PUT  /museumrelaties/:id} : Updates an existing museumrelatie.
     *
     * @param id the id of the museumrelatie to save.
     * @param museumrelatie the museumrelatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated museumrelatie,
     * or with status {@code 400 (Bad Request)} if the museumrelatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the museumrelatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Museumrelatie> updateMuseumrelatie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Museumrelatie museumrelatie
    ) throws URISyntaxException {
        log.debug("REST request to update Museumrelatie : {}, {}", id, museumrelatie);
        if (museumrelatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, museumrelatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!museumrelatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        museumrelatie = museumrelatieRepository.save(museumrelatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, museumrelatie.getId().toString()))
            .body(museumrelatie);
    }

    /**
     * {@code PATCH  /museumrelaties/:id} : Partial updates given fields of an existing museumrelatie, field will ignore if it is null
     *
     * @param id the id of the museumrelatie to save.
     * @param museumrelatie the museumrelatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated museumrelatie,
     * or with status {@code 400 (Bad Request)} if the museumrelatie is not valid,
     * or with status {@code 404 (Not Found)} if the museumrelatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the museumrelatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Museumrelatie> partialUpdateMuseumrelatie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Museumrelatie museumrelatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Museumrelatie partially : {}, {}", id, museumrelatie);
        if (museumrelatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, museumrelatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!museumrelatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Museumrelatie> result = museumrelatieRepository
            .findById(museumrelatie.getId())
            .map(existingMuseumrelatie -> {
                if (museumrelatie.getRelatiesoort() != null) {
                    existingMuseumrelatie.setRelatiesoort(museumrelatie.getRelatiesoort());
                }

                return existingMuseumrelatie;
            })
            .map(museumrelatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, museumrelatie.getId().toString())
        );
    }

    /**
     * {@code GET  /museumrelaties} : get all the museumrelaties.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of museumrelaties in body.
     */
    @GetMapping("")
    public List<Museumrelatie> getAllMuseumrelaties(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Museumrelaties");
        if (eagerload) {
            return museumrelatieRepository.findAllWithEagerRelationships();
        } else {
            return museumrelatieRepository.findAll();
        }
    }

    /**
     * {@code GET  /museumrelaties/:id} : get the "id" museumrelatie.
     *
     * @param id the id of the museumrelatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the museumrelatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Museumrelatie> getMuseumrelatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Museumrelatie : {}", id);
        Optional<Museumrelatie> museumrelatie = museumrelatieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(museumrelatie);
    }

    /**
     * {@code DELETE  /museumrelaties/:id} : delete the "id" museumrelatie.
     *
     * @param id the id of the museumrelatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMuseumrelatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Museumrelatie : {}", id);
        museumrelatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
