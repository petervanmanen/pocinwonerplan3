package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Functie;
import nl.ritense.demo.repository.FunctieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Functie}.
 */
@RestController
@RequestMapping("/api/functies")
@Transactional
public class FunctieResource {

    private final Logger log = LoggerFactory.getLogger(FunctieResource.class);

    private static final String ENTITY_NAME = "functie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctieRepository functieRepository;

    public FunctieResource(FunctieRepository functieRepository) {
        this.functieRepository = functieRepository;
    }

    /**
     * {@code POST  /functies} : Create a new functie.
     *
     * @param functie the functie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functie, or with status {@code 400 (Bad Request)} if the functie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Functie> createFunctie(@Valid @RequestBody Functie functie) throws URISyntaxException {
        log.debug("REST request to save Functie : {}", functie);
        if (functie.getId() != null) {
            throw new BadRequestAlertException("A new functie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        functie = functieRepository.save(functie);
        return ResponseEntity.created(new URI("/api/functies/" + functie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, functie.getId().toString()))
            .body(functie);
    }

    /**
     * {@code PUT  /functies/:id} : Updates an existing functie.
     *
     * @param id the id of the functie to save.
     * @param functie the functie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functie,
     * or with status {@code 400 (Bad Request)} if the functie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Functie> updateFunctie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Functie functie
    ) throws URISyntaxException {
        log.debug("REST request to update Functie : {}, {}", id, functie);
        if (functie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        functie = functieRepository.save(functie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, functie.getId().toString()))
            .body(functie);
    }

    /**
     * {@code PATCH  /functies/:id} : Partial updates given fields of an existing functie, field will ignore if it is null
     *
     * @param id the id of the functie to save.
     * @param functie the functie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functie,
     * or with status {@code 400 (Bad Request)} if the functie is not valid,
     * or with status {@code 404 (Not Found)} if the functie is not found,
     * or with status {@code 500 (Internal Server Error)} if the functie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Functie> partialUpdateFunctie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Functie functie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Functie partially : {}, {}", id, functie);
        if (functie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Functie> result = functieRepository
            .findById(functie.getId())
            .map(existingFunctie -> {
                if (functie.getGroep() != null) {
                    existingFunctie.setGroep(functie.getGroep());
                }
                if (functie.getNaam() != null) {
                    existingFunctie.setNaam(functie.getNaam());
                }

                return existingFunctie;
            })
            .map(functieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, functie.getId().toString())
        );
    }

    /**
     * {@code GET  /functies} : get all the functies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functies in body.
     */
    @GetMapping("")
    public List<Functie> getAllFuncties() {
        log.debug("REST request to get all Functies");
        return functieRepository.findAll();
    }

    /**
     * {@code GET  /functies/:id} : get the "id" functie.
     *
     * @param id the id of the functie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Functie> getFunctie(@PathVariable("id") Long id) {
        log.debug("REST request to get Functie : {}", id);
        Optional<Functie> functie = functieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(functie);
    }

    /**
     * {@code DELETE  /functies/:id} : delete the "id" functie.
     *
     * @param id the id of the functie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFunctie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Functie : {}", id);
        functieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
