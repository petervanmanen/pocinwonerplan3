package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Beschikking;
import nl.ritense.demo.repository.BeschikkingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Beschikking}.
 */
@RestController
@RequestMapping("/api/beschikkings")
@Transactional
public class BeschikkingResource {

    private final Logger log = LoggerFactory.getLogger(BeschikkingResource.class);

    private static final String ENTITY_NAME = "beschikking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeschikkingRepository beschikkingRepository;

    public BeschikkingResource(BeschikkingRepository beschikkingRepository) {
        this.beschikkingRepository = beschikkingRepository;
    }

    /**
     * {@code POST  /beschikkings} : Create a new beschikking.
     *
     * @param beschikking the beschikking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beschikking, or with status {@code 400 (Bad Request)} if the beschikking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Beschikking> createBeschikking(@Valid @RequestBody Beschikking beschikking) throws URISyntaxException {
        log.debug("REST request to save Beschikking : {}", beschikking);
        if (beschikking.getId() != null) {
            throw new BadRequestAlertException("A new beschikking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        beschikking = beschikkingRepository.save(beschikking);
        return ResponseEntity.created(new URI("/api/beschikkings/" + beschikking.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, beschikking.getId().toString()))
            .body(beschikking);
    }

    /**
     * {@code PUT  /beschikkings/:id} : Updates an existing beschikking.
     *
     * @param id the id of the beschikking to save.
     * @param beschikking the beschikking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beschikking,
     * or with status {@code 400 (Bad Request)} if the beschikking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beschikking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beschikking> updateBeschikking(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Beschikking beschikking
    ) throws URISyntaxException {
        log.debug("REST request to update Beschikking : {}, {}", id, beschikking);
        if (beschikking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beschikking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beschikkingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        beschikking = beschikkingRepository.save(beschikking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beschikking.getId().toString()))
            .body(beschikking);
    }

    /**
     * {@code PATCH  /beschikkings/:id} : Partial updates given fields of an existing beschikking, field will ignore if it is null
     *
     * @param id the id of the beschikking to save.
     * @param beschikking the beschikking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beschikking,
     * or with status {@code 400 (Bad Request)} if the beschikking is not valid,
     * or with status {@code 404 (Not Found)} if the beschikking is not found,
     * or with status {@code 500 (Internal Server Error)} if the beschikking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beschikking> partialUpdateBeschikking(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Beschikking beschikking
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beschikking partially : {}, {}", id, beschikking);
        if (beschikking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beschikking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beschikkingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beschikking> result = beschikkingRepository
            .findById(beschikking.getId())
            .map(existingBeschikking -> {
                if (beschikking.getCode() != null) {
                    existingBeschikking.setCode(beschikking.getCode());
                }
                if (beschikking.getCommentaar() != null) {
                    existingBeschikking.setCommentaar(beschikking.getCommentaar());
                }
                if (beschikking.getDatumafgifte() != null) {
                    existingBeschikking.setDatumafgifte(beschikking.getDatumafgifte());
                }
                if (beschikking.getGrondslagen() != null) {
                    existingBeschikking.setGrondslagen(beschikking.getGrondslagen());
                }
                if (beschikking.getWet() != null) {
                    existingBeschikking.setWet(beschikking.getWet());
                }

                return existingBeschikking;
            })
            .map(beschikkingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, beschikking.getId().toString())
        );
    }

    /**
     * {@code GET  /beschikkings} : get all the beschikkings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beschikkings in body.
     */
    @GetMapping("")
    public List<Beschikking> getAllBeschikkings() {
        log.debug("REST request to get all Beschikkings");
        return beschikkingRepository.findAll();
    }

    /**
     * {@code GET  /beschikkings/:id} : get the "id" beschikking.
     *
     * @param id the id of the beschikking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beschikking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beschikking> getBeschikking(@PathVariable("id") Long id) {
        log.debug("REST request to get Beschikking : {}", id);
        Optional<Beschikking> beschikking = beschikkingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beschikking);
    }

    /**
     * {@code DELETE  /beschikkings/:id} : delete the "id" beschikking.
     *
     * @param id the id of the beschikking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeschikking(@PathVariable("id") Long id) {
        log.debug("REST request to delete Beschikking : {}", id);
        beschikkingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
