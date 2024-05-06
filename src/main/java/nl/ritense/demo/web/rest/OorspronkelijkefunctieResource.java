package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Oorspronkelijkefunctie;
import nl.ritense.demo.repository.OorspronkelijkefunctieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Oorspronkelijkefunctie}.
 */
@RestController
@RequestMapping("/api/oorspronkelijkefuncties")
@Transactional
public class OorspronkelijkefunctieResource {

    private final Logger log = LoggerFactory.getLogger(OorspronkelijkefunctieResource.class);

    private static final String ENTITY_NAME = "oorspronkelijkefunctie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OorspronkelijkefunctieRepository oorspronkelijkefunctieRepository;

    public OorspronkelijkefunctieResource(OorspronkelijkefunctieRepository oorspronkelijkefunctieRepository) {
        this.oorspronkelijkefunctieRepository = oorspronkelijkefunctieRepository;
    }

    /**
     * {@code POST  /oorspronkelijkefuncties} : Create a new oorspronkelijkefunctie.
     *
     * @param oorspronkelijkefunctie the oorspronkelijkefunctie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oorspronkelijkefunctie, or with status {@code 400 (Bad Request)} if the oorspronkelijkefunctie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Oorspronkelijkefunctie> createOorspronkelijkefunctie(@RequestBody Oorspronkelijkefunctie oorspronkelijkefunctie)
        throws URISyntaxException {
        log.debug("REST request to save Oorspronkelijkefunctie : {}", oorspronkelijkefunctie);
        if (oorspronkelijkefunctie.getId() != null) {
            throw new BadRequestAlertException("A new oorspronkelijkefunctie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        oorspronkelijkefunctie = oorspronkelijkefunctieRepository.save(oorspronkelijkefunctie);
        return ResponseEntity.created(new URI("/api/oorspronkelijkefuncties/" + oorspronkelijkefunctie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, oorspronkelijkefunctie.getId().toString()))
            .body(oorspronkelijkefunctie);
    }

    /**
     * {@code PUT  /oorspronkelijkefuncties/:id} : Updates an existing oorspronkelijkefunctie.
     *
     * @param id the id of the oorspronkelijkefunctie to save.
     * @param oorspronkelijkefunctie the oorspronkelijkefunctie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oorspronkelijkefunctie,
     * or with status {@code 400 (Bad Request)} if the oorspronkelijkefunctie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oorspronkelijkefunctie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Oorspronkelijkefunctie> updateOorspronkelijkefunctie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Oorspronkelijkefunctie oorspronkelijkefunctie
    ) throws URISyntaxException {
        log.debug("REST request to update Oorspronkelijkefunctie : {}, {}", id, oorspronkelijkefunctie);
        if (oorspronkelijkefunctie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oorspronkelijkefunctie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oorspronkelijkefunctieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        oorspronkelijkefunctie = oorspronkelijkefunctieRepository.save(oorspronkelijkefunctie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, oorspronkelijkefunctie.getId().toString()))
            .body(oorspronkelijkefunctie);
    }

    /**
     * {@code PATCH  /oorspronkelijkefuncties/:id} : Partial updates given fields of an existing oorspronkelijkefunctie, field will ignore if it is null
     *
     * @param id the id of the oorspronkelijkefunctie to save.
     * @param oorspronkelijkefunctie the oorspronkelijkefunctie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oorspronkelijkefunctie,
     * or with status {@code 400 (Bad Request)} if the oorspronkelijkefunctie is not valid,
     * or with status {@code 404 (Not Found)} if the oorspronkelijkefunctie is not found,
     * or with status {@code 500 (Internal Server Error)} if the oorspronkelijkefunctie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Oorspronkelijkefunctie> partialUpdateOorspronkelijkefunctie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Oorspronkelijkefunctie oorspronkelijkefunctie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Oorspronkelijkefunctie partially : {}, {}", id, oorspronkelijkefunctie);
        if (oorspronkelijkefunctie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oorspronkelijkefunctie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oorspronkelijkefunctieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Oorspronkelijkefunctie> result = oorspronkelijkefunctieRepository
            .findById(oorspronkelijkefunctie.getId())
            .map(existingOorspronkelijkefunctie -> {
                if (oorspronkelijkefunctie.getFunctie() != null) {
                    existingOorspronkelijkefunctie.setFunctie(oorspronkelijkefunctie.getFunctie());
                }
                if (oorspronkelijkefunctie.getFunctiesoort() != null) {
                    existingOorspronkelijkefunctie.setFunctiesoort(oorspronkelijkefunctie.getFunctiesoort());
                }
                if (oorspronkelijkefunctie.getHoofdcategorie() != null) {
                    existingOorspronkelijkefunctie.setHoofdcategorie(oorspronkelijkefunctie.getHoofdcategorie());
                }
                if (oorspronkelijkefunctie.getHoofdfunctie() != null) {
                    existingOorspronkelijkefunctie.setHoofdfunctie(oorspronkelijkefunctie.getHoofdfunctie());
                }
                if (oorspronkelijkefunctie.getSubcategorie() != null) {
                    existingOorspronkelijkefunctie.setSubcategorie(oorspronkelijkefunctie.getSubcategorie());
                }
                if (oorspronkelijkefunctie.getToelichting() != null) {
                    existingOorspronkelijkefunctie.setToelichting(oorspronkelijkefunctie.getToelichting());
                }
                if (oorspronkelijkefunctie.getVerbijzondering() != null) {
                    existingOorspronkelijkefunctie.setVerbijzondering(oorspronkelijkefunctie.getVerbijzondering());
                }

                return existingOorspronkelijkefunctie;
            })
            .map(oorspronkelijkefunctieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, oorspronkelijkefunctie.getId().toString())
        );
    }

    /**
     * {@code GET  /oorspronkelijkefuncties} : get all the oorspronkelijkefuncties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oorspronkelijkefuncties in body.
     */
    @GetMapping("")
    public List<Oorspronkelijkefunctie> getAllOorspronkelijkefuncties() {
        log.debug("REST request to get all Oorspronkelijkefuncties");
        return oorspronkelijkefunctieRepository.findAll();
    }

    /**
     * {@code GET  /oorspronkelijkefuncties/:id} : get the "id" oorspronkelijkefunctie.
     *
     * @param id the id of the oorspronkelijkefunctie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oorspronkelijkefunctie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Oorspronkelijkefunctie> getOorspronkelijkefunctie(@PathVariable("id") Long id) {
        log.debug("REST request to get Oorspronkelijkefunctie : {}", id);
        Optional<Oorspronkelijkefunctie> oorspronkelijkefunctie = oorspronkelijkefunctieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(oorspronkelijkefunctie);
    }

    /**
     * {@code DELETE  /oorspronkelijkefuncties/:id} : delete the "id" oorspronkelijkefunctie.
     *
     * @param id the id of the oorspronkelijkefunctie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOorspronkelijkefunctie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Oorspronkelijkefunctie : {}", id);
        oorspronkelijkefunctieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
