package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Materielehistorie;
import nl.ritense.demo.repository.MaterielehistorieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Materielehistorie}.
 */
@RestController
@RequestMapping("/api/materielehistories")
@Transactional
public class MaterielehistorieResource {

    private final Logger log = LoggerFactory.getLogger(MaterielehistorieResource.class);

    private static final String ENTITY_NAME = "materielehistorie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterielehistorieRepository materielehistorieRepository;

    public MaterielehistorieResource(MaterielehistorieRepository materielehistorieRepository) {
        this.materielehistorieRepository = materielehistorieRepository;
    }

    /**
     * {@code POST  /materielehistories} : Create a new materielehistorie.
     *
     * @param materielehistorie the materielehistorie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materielehistorie, or with status {@code 400 (Bad Request)} if the materielehistorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Materielehistorie> createMaterielehistorie(@RequestBody Materielehistorie materielehistorie)
        throws URISyntaxException {
        log.debug("REST request to save Materielehistorie : {}", materielehistorie);
        if (materielehistorie.getId() != null) {
            throw new BadRequestAlertException("A new materielehistorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        materielehistorie = materielehistorieRepository.save(materielehistorie);
        return ResponseEntity.created(new URI("/api/materielehistories/" + materielehistorie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, materielehistorie.getId().toString()))
            .body(materielehistorie);
    }

    /**
     * {@code PUT  /materielehistories/:id} : Updates an existing materielehistorie.
     *
     * @param id the id of the materielehistorie to save.
     * @param materielehistorie the materielehistorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materielehistorie,
     * or with status {@code 400 (Bad Request)} if the materielehistorie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materielehistorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Materielehistorie> updateMaterielehistorie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Materielehistorie materielehistorie
    ) throws URISyntaxException {
        log.debug("REST request to update Materielehistorie : {}, {}", id, materielehistorie);
        if (materielehistorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materielehistorie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materielehistorieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        materielehistorie = materielehistorieRepository.save(materielehistorie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materielehistorie.getId().toString()))
            .body(materielehistorie);
    }

    /**
     * {@code PATCH  /materielehistories/:id} : Partial updates given fields of an existing materielehistorie, field will ignore if it is null
     *
     * @param id the id of the materielehistorie to save.
     * @param materielehistorie the materielehistorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materielehistorie,
     * or with status {@code 400 (Bad Request)} if the materielehistorie is not valid,
     * or with status {@code 404 (Not Found)} if the materielehistorie is not found,
     * or with status {@code 500 (Internal Server Error)} if the materielehistorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Materielehistorie> partialUpdateMaterielehistorie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Materielehistorie materielehistorie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Materielehistorie partially : {}, {}", id, materielehistorie);
        if (materielehistorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materielehistorie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materielehistorieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Materielehistorie> result = materielehistorieRepository
            .findById(materielehistorie.getId())
            .map(existingMaterielehistorie -> {
                if (materielehistorie.getDatumbegingeldigheidgegevens() != null) {
                    existingMaterielehistorie.setDatumbegingeldigheidgegevens(materielehistorie.getDatumbegingeldigheidgegevens());
                }
                if (materielehistorie.getDatumeindegeldigheidgegevens() != null) {
                    existingMaterielehistorie.setDatumeindegeldigheidgegevens(materielehistorie.getDatumeindegeldigheidgegevens());
                }

                return existingMaterielehistorie;
            })
            .map(materielehistorieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materielehistorie.getId().toString())
        );
    }

    /**
     * {@code GET  /materielehistories} : get all the materielehistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materielehistories in body.
     */
    @GetMapping("")
    public List<Materielehistorie> getAllMaterielehistories() {
        log.debug("REST request to get all Materielehistories");
        return materielehistorieRepository.findAll();
    }

    /**
     * {@code GET  /materielehistories/:id} : get the "id" materielehistorie.
     *
     * @param id the id of the materielehistorie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materielehistorie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Materielehistorie> getMaterielehistorie(@PathVariable("id") Long id) {
        log.debug("REST request to get Materielehistorie : {}", id);
        Optional<Materielehistorie> materielehistorie = materielehistorieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(materielehistorie);
    }

    /**
     * {@code DELETE  /materielehistories/:id} : delete the "id" materielehistorie.
     *
     * @param id the id of the materielehistorie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterielehistorie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Materielehistorie : {}", id);
        materielehistorieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
