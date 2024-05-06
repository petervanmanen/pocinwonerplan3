package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bruikleen;
import nl.ritense.demo.repository.BruikleenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bruikleen}.
 */
@RestController
@RequestMapping("/api/bruikleens")
@Transactional
public class BruikleenResource {

    private final Logger log = LoggerFactory.getLogger(BruikleenResource.class);

    private static final String ENTITY_NAME = "bruikleen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BruikleenRepository bruikleenRepository;

    public BruikleenResource(BruikleenRepository bruikleenRepository) {
        this.bruikleenRepository = bruikleenRepository;
    }

    /**
     * {@code POST  /bruikleens} : Create a new bruikleen.
     *
     * @param bruikleen the bruikleen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bruikleen, or with status {@code 400 (Bad Request)} if the bruikleen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bruikleen> createBruikleen(@RequestBody Bruikleen bruikleen) throws URISyntaxException {
        log.debug("REST request to save Bruikleen : {}", bruikleen);
        if (bruikleen.getId() != null) {
            throw new BadRequestAlertException("A new bruikleen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bruikleen = bruikleenRepository.save(bruikleen);
        return ResponseEntity.created(new URI("/api/bruikleens/" + bruikleen.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bruikleen.getId().toString()))
            .body(bruikleen);
    }

    /**
     * {@code PUT  /bruikleens/:id} : Updates an existing bruikleen.
     *
     * @param id the id of the bruikleen to save.
     * @param bruikleen the bruikleen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bruikleen,
     * or with status {@code 400 (Bad Request)} if the bruikleen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bruikleen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bruikleen> updateBruikleen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bruikleen bruikleen
    ) throws URISyntaxException {
        log.debug("REST request to update Bruikleen : {}, {}", id, bruikleen);
        if (bruikleen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bruikleen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bruikleenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bruikleen = bruikleenRepository.save(bruikleen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bruikleen.getId().toString()))
            .body(bruikleen);
    }

    /**
     * {@code PATCH  /bruikleens/:id} : Partial updates given fields of an existing bruikleen, field will ignore if it is null
     *
     * @param id the id of the bruikleen to save.
     * @param bruikleen the bruikleen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bruikleen,
     * or with status {@code 400 (Bad Request)} if the bruikleen is not valid,
     * or with status {@code 404 (Not Found)} if the bruikleen is not found,
     * or with status {@code 500 (Internal Server Error)} if the bruikleen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bruikleen> partialUpdateBruikleen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bruikleen bruikleen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bruikleen partially : {}, {}", id, bruikleen);
        if (bruikleen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bruikleen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bruikleenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bruikleen> result = bruikleenRepository
            .findById(bruikleen.getId())
            .map(existingBruikleen -> {
                if (bruikleen.getAanvraagdoor() != null) {
                    existingBruikleen.setAanvraagdoor(bruikleen.getAanvraagdoor());
                }
                if (bruikleen.getDatumaanvraag() != null) {
                    existingBruikleen.setDatumaanvraag(bruikleen.getDatumaanvraag());
                }
                if (bruikleen.getDatumeinde() != null) {
                    existingBruikleen.setDatumeinde(bruikleen.getDatumeinde());
                }
                if (bruikleen.getDatumstart() != null) {
                    existingBruikleen.setDatumstart(bruikleen.getDatumstart());
                }
                if (bruikleen.getToestemmingdoor() != null) {
                    existingBruikleen.setToestemmingdoor(bruikleen.getToestemmingdoor());
                }

                return existingBruikleen;
            })
            .map(bruikleenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bruikleen.getId().toString())
        );
    }

    /**
     * {@code GET  /bruikleens} : get all the bruikleens.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bruikleens in body.
     */
    @GetMapping("")
    public List<Bruikleen> getAllBruikleens(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Bruikleens");
        if (eagerload) {
            return bruikleenRepository.findAllWithEagerRelationships();
        } else {
            return bruikleenRepository.findAll();
        }
    }

    /**
     * {@code GET  /bruikleens/:id} : get the "id" bruikleen.
     *
     * @param id the id of the bruikleen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bruikleen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bruikleen> getBruikleen(@PathVariable("id") Long id) {
        log.debug("REST request to get Bruikleen : {}", id);
        Optional<Bruikleen> bruikleen = bruikleenRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(bruikleen);
    }

    /**
     * {@code DELETE  /bruikleens/:id} : delete the "id" bruikleen.
     *
     * @param id the id of the bruikleen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBruikleen(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bruikleen : {}", id);
        bruikleenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
