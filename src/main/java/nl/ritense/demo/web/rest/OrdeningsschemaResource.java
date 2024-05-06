package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Ordeningsschema;
import nl.ritense.demo.repository.OrdeningsschemaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Ordeningsschema}.
 */
@RestController
@RequestMapping("/api/ordeningsschemas")
@Transactional
public class OrdeningsschemaResource {

    private final Logger log = LoggerFactory.getLogger(OrdeningsschemaResource.class);

    private static final String ENTITY_NAME = "ordeningsschema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdeningsschemaRepository ordeningsschemaRepository;

    public OrdeningsschemaResource(OrdeningsschemaRepository ordeningsschemaRepository) {
        this.ordeningsschemaRepository = ordeningsschemaRepository;
    }

    /**
     * {@code POST  /ordeningsschemas} : Create a new ordeningsschema.
     *
     * @param ordeningsschema the ordeningsschema to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordeningsschema, or with status {@code 400 (Bad Request)} if the ordeningsschema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Ordeningsschema> createOrdeningsschema(@RequestBody Ordeningsschema ordeningsschema) throws URISyntaxException {
        log.debug("REST request to save Ordeningsschema : {}", ordeningsschema);
        if (ordeningsschema.getId() != null) {
            throw new BadRequestAlertException("A new ordeningsschema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ordeningsschema = ordeningsschemaRepository.save(ordeningsschema);
        return ResponseEntity.created(new URI("/api/ordeningsschemas/" + ordeningsschema.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ordeningsschema.getId().toString()))
            .body(ordeningsschema);
    }

    /**
     * {@code PUT  /ordeningsschemas/:id} : Updates an existing ordeningsschema.
     *
     * @param id the id of the ordeningsschema to save.
     * @param ordeningsschema the ordeningsschema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordeningsschema,
     * or with status {@code 400 (Bad Request)} if the ordeningsschema is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordeningsschema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ordeningsschema> updateOrdeningsschema(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ordeningsschema ordeningsschema
    ) throws URISyntaxException {
        log.debug("REST request to update Ordeningsschema : {}, {}", id, ordeningsschema);
        if (ordeningsschema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordeningsschema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordeningsschemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ordeningsschema = ordeningsschemaRepository.save(ordeningsschema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordeningsschema.getId().toString()))
            .body(ordeningsschema);
    }

    /**
     * {@code PATCH  /ordeningsschemas/:id} : Partial updates given fields of an existing ordeningsschema, field will ignore if it is null
     *
     * @param id the id of the ordeningsschema to save.
     * @param ordeningsschema the ordeningsschema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordeningsschema,
     * or with status {@code 400 (Bad Request)} if the ordeningsschema is not valid,
     * or with status {@code 404 (Not Found)} if the ordeningsschema is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordeningsschema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ordeningsschema> partialUpdateOrdeningsschema(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Ordeningsschema ordeningsschema
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ordeningsschema partially : {}, {}", id, ordeningsschema);
        if (ordeningsschema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordeningsschema.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordeningsschemaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ordeningsschema> result = ordeningsschemaRepository
            .findById(ordeningsschema.getId())
            .map(existingOrdeningsschema -> {
                if (ordeningsschema.getNaam() != null) {
                    existingOrdeningsschema.setNaam(ordeningsschema.getNaam());
                }
                if (ordeningsschema.getText() != null) {
                    existingOrdeningsschema.setText(ordeningsschema.getText());
                }

                return existingOrdeningsschema;
            })
            .map(ordeningsschemaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordeningsschema.getId().toString())
        );
    }

    /**
     * {@code GET  /ordeningsschemas} : get all the ordeningsschemas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordeningsschemas in body.
     */
    @GetMapping("")
    public List<Ordeningsschema> getAllOrdeningsschemas() {
        log.debug("REST request to get all Ordeningsschemas");
        return ordeningsschemaRepository.findAll();
    }

    /**
     * {@code GET  /ordeningsschemas/:id} : get the "id" ordeningsschema.
     *
     * @param id the id of the ordeningsschema to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordeningsschema, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ordeningsschema> getOrdeningsschema(@PathVariable("id") Long id) {
        log.debug("REST request to get Ordeningsschema : {}", id);
        Optional<Ordeningsschema> ordeningsschema = ordeningsschemaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ordeningsschema);
    }

    /**
     * {@code DELETE  /ordeningsschemas/:id} : delete the "id" ordeningsschema.
     *
     * @param id the id of the ordeningsschema to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdeningsschema(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ordeningsschema : {}", id);
        ordeningsschemaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
