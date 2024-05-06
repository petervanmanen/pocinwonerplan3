package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gegeven;
import nl.ritense.demo.repository.GegevenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gegeven}.
 */
@RestController
@RequestMapping("/api/gegevens")
@Transactional
public class GegevenResource {

    private final Logger log = LoggerFactory.getLogger(GegevenResource.class);

    private static final String ENTITY_NAME = "gegeven";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GegevenRepository gegevenRepository;

    public GegevenResource(GegevenRepository gegevenRepository) {
        this.gegevenRepository = gegevenRepository;
    }

    /**
     * {@code POST  /gegevens} : Create a new gegeven.
     *
     * @param gegeven the gegeven to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gegeven, or with status {@code 400 (Bad Request)} if the gegeven has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gegeven> createGegeven(@RequestBody Gegeven gegeven) throws URISyntaxException {
        log.debug("REST request to save Gegeven : {}", gegeven);
        if (gegeven.getId() != null) {
            throw new BadRequestAlertException("A new gegeven cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gegeven = gegevenRepository.save(gegeven);
        return ResponseEntity.created(new URI("/api/gegevens/" + gegeven.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gegeven.getId()))
            .body(gegeven);
    }

    /**
     * {@code PUT  /gegevens/:id} : Updates an existing gegeven.
     *
     * @param id the id of the gegeven to save.
     * @param gegeven the gegeven to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gegeven,
     * or with status {@code 400 (Bad Request)} if the gegeven is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gegeven couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gegeven> updateGegeven(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Gegeven gegeven
    ) throws URISyntaxException {
        log.debug("REST request to update Gegeven : {}, {}", id, gegeven);
        if (gegeven.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gegeven.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gegevenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gegeven = gegevenRepository.save(gegeven);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gegeven.getId()))
            .body(gegeven);
    }

    /**
     * {@code PATCH  /gegevens/:id} : Partial updates given fields of an existing gegeven, field will ignore if it is null
     *
     * @param id the id of the gegeven to save.
     * @param gegeven the gegeven to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gegeven,
     * or with status {@code 400 (Bad Request)} if the gegeven is not valid,
     * or with status {@code 404 (Not Found)} if the gegeven is not found,
     * or with status {@code 500 (Internal Server Error)} if the gegeven couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gegeven> partialUpdateGegeven(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Gegeven gegeven
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gegeven partially : {}, {}", id, gegeven);
        if (gegeven.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gegeven.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gegevenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gegeven> result = gegevenRepository
            .findById(gegeven.getId())
            .map(existingGegeven -> {
                if (gegeven.getAlias() != null) {
                    existingGegeven.setAlias(gegeven.getAlias());
                }
                if (gegeven.getEaguid() != null) {
                    existingGegeven.setEaguid(gegeven.getEaguid());
                }
                if (gegeven.getNaam() != null) {
                    existingGegeven.setNaam(gegeven.getNaam());
                }
                if (gegeven.getStereotype() != null) {
                    existingGegeven.setStereotype(gegeven.getStereotype());
                }
                if (gegeven.getToelichting() != null) {
                    existingGegeven.setToelichting(gegeven.getToelichting());
                }

                return existingGegeven;
            })
            .map(gegevenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gegeven.getId())
        );
    }

    /**
     * {@code GET  /gegevens} : get all the gegevens.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gegevens in body.
     */
    @GetMapping("")
    public List<Gegeven> getAllGegevens(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Gegevens");
        if (eagerload) {
            return gegevenRepository.findAllWithEagerRelationships();
        } else {
            return gegevenRepository.findAll();
        }
    }

    /**
     * {@code GET  /gegevens/:id} : get the "id" gegeven.
     *
     * @param id the id of the gegeven to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gegeven, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gegeven> getGegeven(@PathVariable("id") String id) {
        log.debug("REST request to get Gegeven : {}", id);
        Optional<Gegeven> gegeven = gegevenRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(gegeven);
    }

    /**
     * {@code DELETE  /gegevens/:id} : delete the "id" gegeven.
     *
     * @param id the id of the gegeven to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGegeven(@PathVariable("id") String id) {
        log.debug("REST request to delete Gegeven : {}", id);
        gegevenRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
