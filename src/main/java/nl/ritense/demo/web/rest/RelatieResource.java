package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Relatie;
import nl.ritense.demo.repository.RelatieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Relatie}.
 */
@RestController
@RequestMapping("/api/relaties")
@Transactional
public class RelatieResource {

    private final Logger log = LoggerFactory.getLogger(RelatieResource.class);

    private static final String ENTITY_NAME = "relatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelatieRepository relatieRepository;

    public RelatieResource(RelatieRepository relatieRepository) {
        this.relatieRepository = relatieRepository;
    }

    /**
     * {@code POST  /relaties} : Create a new relatie.
     *
     * @param relatie the relatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relatie, or with status {@code 400 (Bad Request)} if the relatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Relatie> createRelatie(@RequestBody Relatie relatie) throws URISyntaxException {
        log.debug("REST request to save Relatie : {}", relatie);
        if (relatie.getId() != null) {
            throw new BadRequestAlertException("A new relatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        relatie = relatieRepository.save(relatie);
        return ResponseEntity.created(new URI("/api/relaties/" + relatie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, relatie.getId().toString()))
            .body(relatie);
    }

    /**
     * {@code PUT  /relaties/:id} : Updates an existing relatie.
     *
     * @param id the id of the relatie to save.
     * @param relatie the relatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatie,
     * or with status {@code 400 (Bad Request)} if the relatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Relatie> updateRelatie(@PathVariable(value = "id", required = false) final Long id, @RequestBody Relatie relatie)
        throws URISyntaxException {
        log.debug("REST request to update Relatie : {}, {}", id, relatie);
        if (relatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        relatie = relatieRepository.save(relatie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, relatie.getId().toString()))
            .body(relatie);
    }

    /**
     * {@code PATCH  /relaties/:id} : Partial updates given fields of an existing relatie, field will ignore if it is null
     *
     * @param id the id of the relatie to save.
     * @param relatie the relatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relatie,
     * or with status {@code 400 (Bad Request)} if the relatie is not valid,
     * or with status {@code 404 (Not Found)} if the relatie is not found,
     * or with status {@code 500 (Internal Server Error)} if the relatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Relatie> partialUpdateRelatie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Relatie relatie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Relatie partially : {}, {}", id, relatie);
        if (relatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relatie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Relatie> result = relatieRepository
            .findById(relatie.getId())
            .map(existingRelatie -> {
                if (relatie.getRelatiesoort() != null) {
                    existingRelatie.setRelatiesoort(relatie.getRelatiesoort());
                }

                return existingRelatie;
            })
            .map(relatieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, relatie.getId().toString())
        );
    }

    /**
     * {@code GET  /relaties} : get all the relaties.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relaties in body.
     */
    @GetMapping("")
    public List<Relatie> getAllRelaties(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Relaties");
        if (eagerload) {
            return relatieRepository.findAllWithEagerRelationships();
        } else {
            return relatieRepository.findAll();
        }
    }

    /**
     * {@code GET  /relaties/:id} : get the "id" relatie.
     *
     * @param id the id of the relatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Relatie> getRelatie(@PathVariable("id") Long id) {
        log.debug("REST request to get Relatie : {}", id);
        Optional<Relatie> relatie = relatieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(relatie);
    }

    /**
     * {@code DELETE  /relaties/:id} : delete the "id" relatie.
     *
     * @param id the id of the relatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelatie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Relatie : {}", id);
        relatieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
