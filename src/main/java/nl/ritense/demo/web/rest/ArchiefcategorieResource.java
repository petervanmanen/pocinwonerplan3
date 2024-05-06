package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Archiefcategorie;
import nl.ritense.demo.repository.ArchiefcategorieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Archiefcategorie}.
 */
@RestController
@RequestMapping("/api/archiefcategories")
@Transactional
public class ArchiefcategorieResource {

    private final Logger log = LoggerFactory.getLogger(ArchiefcategorieResource.class);

    private static final String ENTITY_NAME = "archiefcategorie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArchiefcategorieRepository archiefcategorieRepository;

    public ArchiefcategorieResource(ArchiefcategorieRepository archiefcategorieRepository) {
        this.archiefcategorieRepository = archiefcategorieRepository;
    }

    /**
     * {@code POST  /archiefcategories} : Create a new archiefcategorie.
     *
     * @param archiefcategorie the archiefcategorie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new archiefcategorie, or with status {@code 400 (Bad Request)} if the archiefcategorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Archiefcategorie> createArchiefcategorie(@Valid @RequestBody Archiefcategorie archiefcategorie)
        throws URISyntaxException {
        log.debug("REST request to save Archiefcategorie : {}", archiefcategorie);
        if (archiefcategorie.getId() != null) {
            throw new BadRequestAlertException("A new archiefcategorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        archiefcategorie = archiefcategorieRepository.save(archiefcategorie);
        return ResponseEntity.created(new URI("/api/archiefcategories/" + archiefcategorie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, archiefcategorie.getId().toString()))
            .body(archiefcategorie);
    }

    /**
     * {@code PUT  /archiefcategories/:id} : Updates an existing archiefcategorie.
     *
     * @param id the id of the archiefcategorie to save.
     * @param archiefcategorie the archiefcategorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archiefcategorie,
     * or with status {@code 400 (Bad Request)} if the archiefcategorie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the archiefcategorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Archiefcategorie> updateArchiefcategorie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Archiefcategorie archiefcategorie
    ) throws URISyntaxException {
        log.debug("REST request to update Archiefcategorie : {}, {}", id, archiefcategorie);
        if (archiefcategorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archiefcategorie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archiefcategorieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        archiefcategorie = archiefcategorieRepository.save(archiefcategorie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, archiefcategorie.getId().toString()))
            .body(archiefcategorie);
    }

    /**
     * {@code PATCH  /archiefcategories/:id} : Partial updates given fields of an existing archiefcategorie, field will ignore if it is null
     *
     * @param id the id of the archiefcategorie to save.
     * @param archiefcategorie the archiefcategorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated archiefcategorie,
     * or with status {@code 400 (Bad Request)} if the archiefcategorie is not valid,
     * or with status {@code 404 (Not Found)} if the archiefcategorie is not found,
     * or with status {@code 500 (Internal Server Error)} if the archiefcategorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Archiefcategorie> partialUpdateArchiefcategorie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Archiefcategorie archiefcategorie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Archiefcategorie partially : {}, {}", id, archiefcategorie);
        if (archiefcategorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, archiefcategorie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!archiefcategorieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Archiefcategorie> result = archiefcategorieRepository
            .findById(archiefcategorie.getId())
            .map(existingArchiefcategorie -> {
                if (archiefcategorie.getNaam() != null) {
                    existingArchiefcategorie.setNaam(archiefcategorie.getNaam());
                }
                if (archiefcategorie.getNummer() != null) {
                    existingArchiefcategorie.setNummer(archiefcategorie.getNummer());
                }
                if (archiefcategorie.getOmschrijving() != null) {
                    existingArchiefcategorie.setOmschrijving(archiefcategorie.getOmschrijving());
                }

                return existingArchiefcategorie;
            })
            .map(archiefcategorieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, archiefcategorie.getId().toString())
        );
    }

    /**
     * {@code GET  /archiefcategories} : get all the archiefcategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of archiefcategories in body.
     */
    @GetMapping("")
    public List<Archiefcategorie> getAllArchiefcategories() {
        log.debug("REST request to get all Archiefcategories");
        return archiefcategorieRepository.findAll();
    }

    /**
     * {@code GET  /archiefcategories/:id} : get the "id" archiefcategorie.
     *
     * @param id the id of the archiefcategorie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the archiefcategorie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Archiefcategorie> getArchiefcategorie(@PathVariable("id") Long id) {
        log.debug("REST request to get Archiefcategorie : {}", id);
        Optional<Archiefcategorie> archiefcategorie = archiefcategorieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(archiefcategorie);
    }

    /**
     * {@code DELETE  /archiefcategories/:id} : delete the "id" archiefcategorie.
     *
     * @param id the id of the archiefcategorie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArchiefcategorie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Archiefcategorie : {}", id);
        archiefcategorieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
