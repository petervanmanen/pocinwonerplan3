package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beperking;
import nl.ritense.demo.repository.BeperkingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beperking}.
 */
@RestController
@RequestMapping("/api/beperkings")
@Transactional
public class BeperkingResource {

    private final Logger log = LoggerFactory.getLogger(BeperkingResource.class);

    private static final String ENTITY_NAME = "beperking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeperkingRepository beperkingRepository;

    public BeperkingResource(BeperkingRepository beperkingRepository) {
        this.beperkingRepository = beperkingRepository;
    }

    /**
     * {@code POST  /beperkings} : Create a new beperking.
     *
     * @param beperking the beperking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beperking, or with status {@code 400 (Bad Request)} if the beperking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beperking> createBeperking(@RequestBody Beperking beperking) throws URISyntaxException {
        log.debug("REST request to save Beperking : {}", beperking);
        if (beperking.getId() != null) {
            throw new BadRequestAlertException("A new beperking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beperking = beperkingRepository.save(beperking);
        return ResponseEntity.created(new URI("/api/beperkings/" + beperking.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beperking.getId().toString()))
            .body(beperking);
    }

    /**
     * {@code PUT  /beperkings/:id} : Updates an existing beperking.
     *
     * @param id the id of the beperking to save.
     * @param beperking the beperking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperking,
     * or with status {@code 400 (Bad Request)} if the beperking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beperking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beperking> updateBeperking(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beperking beperking
    ) throws URISyntaxException {
        log.debug("REST request to update Beperking : {}, {}", id, beperking);
        if (beperking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beperking = beperkingRepository.save(beperking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperking.getId().toString()))
            .body(beperking);
    }

    /**
     * {@code PATCH  /beperkings/:id} : Partial updates given fields of an existing beperking, field will ignore if it is null
     *
     * @param id the id of the beperking to save.
     * @param beperking the beperking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beperking,
     * or with status {@code 400 (Bad Request)} if the beperking is not valid,
     * or with status {@code 404 (Not Found)} if the beperking is not found,
     * or with status {@code 500 (Internal Server Error)} if the beperking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beperking> partialUpdateBeperking(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Beperking beperking
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beperking partially : {}, {}", id, beperking);
        if (beperking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beperking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beperkingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beperking> result = beperkingRepository
            .findById(beperking.getId())
            .map(existingBeperking -> {
                if (beperking.getCategorie() != null) {
                    existingBeperking.setCategorie(beperking.getCategorie());
                }
                if (beperking.getCommentaar() != null) {
                    existingBeperking.setCommentaar(beperking.getCommentaar());
                }
                if (beperking.getDuur() != null) {
                    existingBeperking.setDuur(beperking.getDuur());
                }
                if (beperking.getWet() != null) {
                    existingBeperking.setWet(beperking.getWet());
                }

                return existingBeperking;
            })
            .map(beperkingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beperking.getId().toString())
        );
    }

    /**
     * {@code GET  /beperkings} : get all the beperkings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beperkings in body.
     */
    @GetMapping("")
    public List<Beperking> getAllBeperkings() {
        log.debug("REST request to get all Beperkings");
        return beperkingRepository.findAll();
    }

    /**
     * {@code GET  /beperkings/:id} : get the "id" beperking.
     *
     * @param id the id of the beperking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beperking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beperking> getBeperking(@PathVariable("id") Long id) {
        log.debug("REST request to get Beperking : {}", id);
        Optional<Beperking> beperking = beperkingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beperking);
    }

    /**
     * {@code DELETE  /beperkings/:id} : delete the "id" beperking.
     *
     * @param id the id of the beperking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeperking(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beperking : {}", id);
        beperkingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
