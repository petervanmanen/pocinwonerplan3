package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Genotenopleiding;
import nl.ritense.demo.repository.GenotenopleidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Genotenopleiding}.
 */
@RestController
@RequestMapping("/api/genotenopleidings")
@Transactional
public class GenotenopleidingResource {

    private final Logger log = LoggerFactory.getLogger(GenotenopleidingResource.class);

    private static final String ENTITY_NAME = "genotenopleiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenotenopleidingRepository genotenopleidingRepository;

    public GenotenopleidingResource(GenotenopleidingRepository genotenopleidingRepository) {
        this.genotenopleidingRepository = genotenopleidingRepository;
    }

    /**
     * {@code POST  /genotenopleidings} : Create a new genotenopleiding.
     *
     * @param genotenopleiding the genotenopleiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new genotenopleiding, or with status {@code 400 (Bad Request)} if the genotenopleiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Genotenopleiding> createGenotenopleiding(@RequestBody Genotenopleiding genotenopleiding)
        throws URISyntaxException {
        log.debug("REST request to save Genotenopleiding : {}", genotenopleiding);
        if (genotenopleiding.getId() != null) {
            throw new BadRequestAlertException("A new genotenopleiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        genotenopleiding = genotenopleidingRepository.save(genotenopleiding);
        return ResponseEntity.created(new URI("/api/genotenopleidings/" + genotenopleiding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, genotenopleiding.getId().toString()))
            .body(genotenopleiding);
    }

    /**
     * {@code PUT  /genotenopleidings/:id} : Updates an existing genotenopleiding.
     *
     * @param id the id of the genotenopleiding to save.
     * @param genotenopleiding the genotenopleiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genotenopleiding,
     * or with status {@code 400 (Bad Request)} if the genotenopleiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the genotenopleiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Genotenopleiding> updateGenotenopleiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Genotenopleiding genotenopleiding
    ) throws URISyntaxException {
        log.debug("REST request to update Genotenopleiding : {}, {}", id, genotenopleiding);
        if (genotenopleiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genotenopleiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genotenopleidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        genotenopleiding = genotenopleidingRepository.save(genotenopleiding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, genotenopleiding.getId().toString()))
            .body(genotenopleiding);
    }

    /**
     * {@code PATCH  /genotenopleidings/:id} : Partial updates given fields of an existing genotenopleiding, field will ignore if it is null
     *
     * @param id the id of the genotenopleiding to save.
     * @param genotenopleiding the genotenopleiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genotenopleiding,
     * or with status {@code 400 (Bad Request)} if the genotenopleiding is not valid,
     * or with status {@code 404 (Not Found)} if the genotenopleiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the genotenopleiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Genotenopleiding> partialUpdateGenotenopleiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Genotenopleiding genotenopleiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Genotenopleiding partially : {}, {}", id, genotenopleiding);
        if (genotenopleiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genotenopleiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genotenopleidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Genotenopleiding> result = genotenopleidingRepository
            .findById(genotenopleiding.getId())
            .map(existingGenotenopleiding -> {
                if (genotenopleiding.getDatumeinde() != null) {
                    existingGenotenopleiding.setDatumeinde(genotenopleiding.getDatumeinde());
                }
                if (genotenopleiding.getDatumstart() != null) {
                    existingGenotenopleiding.setDatumstart(genotenopleiding.getDatumstart());
                }
                if (genotenopleiding.getDatumtoewijzing() != null) {
                    existingGenotenopleiding.setDatumtoewijzing(genotenopleiding.getDatumtoewijzing());
                }
                if (genotenopleiding.getPrijs() != null) {
                    existingGenotenopleiding.setPrijs(genotenopleiding.getPrijs());
                }
                if (genotenopleiding.getVerrekenen() != null) {
                    existingGenotenopleiding.setVerrekenen(genotenopleiding.getVerrekenen());
                }

                return existingGenotenopleiding;
            })
            .map(genotenopleidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, genotenopleiding.getId().toString())
        );
    }

    /**
     * {@code GET  /genotenopleidings} : get all the genotenopleidings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of genotenopleidings in body.
     */
    @GetMapping("")
    public List<Genotenopleiding> getAllGenotenopleidings() {
        log.debug("REST request to get all Genotenopleidings");
        return genotenopleidingRepository.findAll();
    }

    /**
     * {@code GET  /genotenopleidings/:id} : get the "id" genotenopleiding.
     *
     * @param id the id of the genotenopleiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the genotenopleiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Genotenopleiding> getGenotenopleiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Genotenopleiding : {}", id);
        Optional<Genotenopleiding> genotenopleiding = genotenopleidingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(genotenopleiding);
    }

    /**
     * {@code DELETE  /genotenopleidings/:id} : delete the "id" genotenopleiding.
     *
     * @param id the id of the genotenopleiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenotenopleiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Genotenopleiding : {}", id);
        genotenopleidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
