package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Fractie;
import nl.ritense.demo.repository.FractieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Fractie}.
 */
@RestController
@RequestMapping("/api/fracties")
@Transactional
public class FractieResource {

    private final Logger log = LoggerFactory.getLogger(FractieResource.class);

    private static final String ENTITY_NAME = "fractie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FractieRepository fractieRepository;

    public FractieResource(FractieRepository fractieRepository) {
        this.fractieRepository = fractieRepository;
    }

    /**
     * {@code POST  /fracties} : Create a new fractie.
     *
     * @param fractie the fractie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fractie, or with status {@code 400 (Bad Request)} if the fractie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Fractie> createFractie(@Valid @RequestBody Fractie fractie) throws URISyntaxException {
        log.debug("REST request to save Fractie : {}", fractie);
        if (fractie.getId() != null) {
            throw new BadRequestAlertException("A new fractie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fractie = fractieRepository.save(fractie);
        return ResponseEntity.created(new URI("/api/fracties/" + fractie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, fractie.getId().toString()))
            .body(fractie);
    }

    /**
     * {@code PUT  /fracties/:id} : Updates an existing fractie.
     *
     * @param id the id of the fractie to save.
     * @param fractie the fractie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fractie,
     * or with status {@code 400 (Bad Request)} if the fractie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fractie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Fractie> updateFractie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Fractie fractie
    ) throws URISyntaxException {
        log.debug("REST request to update Fractie : {}, {}", id, fractie);
        if (fractie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fractie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fractieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fractie = fractieRepository.save(fractie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fractie.getId().toString()))
            .body(fractie);
    }

    /**
     * {@code PATCH  /fracties/:id} : Partial updates given fields of an existing fractie, field will ignore if it is null
     *
     * @param id the id of the fractie to save.
     * @param fractie the fractie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fractie,
     * or with status {@code 400 (Bad Request)} if the fractie is not valid,
     * or with status {@code 404 (Not Found)} if the fractie is not found,
     * or with status {@code 500 (Internal Server Error)} if the fractie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Fractie> partialUpdateFractie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Fractie fractie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fractie partially : {}, {}", id, fractie);
        if (fractie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fractie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fractieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Fractie> result = fractieRepository
            .findById(fractie.getId())
            .map(existingFractie -> {
                if (fractie.getNaam() != null) {
                    existingFractie.setNaam(fractie.getNaam());
                }
                if (fractie.getOmschrijving() != null) {
                    existingFractie.setOmschrijving(fractie.getOmschrijving());
                }

                return existingFractie;
            })
            .map(fractieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fractie.getId().toString())
        );
    }

    /**
     * {@code GET  /fracties} : get all the fracties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fracties in body.
     */
    @GetMapping("")
    public List<Fractie> getAllFracties() {
        log.debug("REST request to get all Fracties");
        return fractieRepository.findAll();
    }

    /**
     * {@code GET  /fracties/:id} : get the "id" fractie.
     *
     * @param id the id of the fractie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fractie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Fractie> getFractie(@PathVariable("id") Long id) {
        log.debug("REST request to get Fractie : {}", id);
        Optional<Fractie> fractie = fractieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fractie);
    }

    /**
     * {@code DELETE  /fracties/:id} : delete the "id" fractie.
     *
     * @param id the id of the fractie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFractie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Fractie : {}", id);
        fractieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
