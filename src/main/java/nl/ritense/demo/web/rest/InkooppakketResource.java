package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Inkooppakket;
import nl.ritense.demo.repository.InkooppakketRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Inkooppakket}.
 */
@RestController
@RequestMapping("/api/inkooppakkets")
@Transactional
public class InkooppakketResource {

    private final Logger log = LoggerFactory.getLogger(InkooppakketResource.class);

    private static final String ENTITY_NAME = "inkooppakket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InkooppakketRepository inkooppakketRepository;

    public InkooppakketResource(InkooppakketRepository inkooppakketRepository) {
        this.inkooppakketRepository = inkooppakketRepository;
    }

    /**
     * {@code POST  /inkooppakkets} : Create a new inkooppakket.
     *
     * @param inkooppakket the inkooppakket to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inkooppakket, or with status {@code 400 (Bad Request)} if the inkooppakket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inkooppakket> createInkooppakket(@Valid @RequestBody Inkooppakket inkooppakket) throws URISyntaxException {
        log.debug("REST request to save Inkooppakket : {}", inkooppakket);
        if (inkooppakket.getId() != null) {
            throw new BadRequestAlertException("A new inkooppakket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inkooppakket = inkooppakketRepository.save(inkooppakket);
        return ResponseEntity.created(new URI("/api/inkooppakkets/" + inkooppakket.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inkooppakket.getId().toString()))
            .body(inkooppakket);
    }

    /**
     * {@code PUT  /inkooppakkets/:id} : Updates an existing inkooppakket.
     *
     * @param id the id of the inkooppakket to save.
     * @param inkooppakket the inkooppakket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inkooppakket,
     * or with status {@code 400 (Bad Request)} if the inkooppakket is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inkooppakket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inkooppakket> updateInkooppakket(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Inkooppakket inkooppakket
    ) throws URISyntaxException {
        log.debug("REST request to update Inkooppakket : {}, {}", id, inkooppakket);
        if (inkooppakket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inkooppakket.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inkooppakketRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inkooppakket = inkooppakketRepository.save(inkooppakket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inkooppakket.getId().toString()))
            .body(inkooppakket);
    }

    /**
     * {@code PATCH  /inkooppakkets/:id} : Partial updates given fields of an existing inkooppakket, field will ignore if it is null
     *
     * @param id the id of the inkooppakket to save.
     * @param inkooppakket the inkooppakket to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inkooppakket,
     * or with status {@code 400 (Bad Request)} if the inkooppakket is not valid,
     * or with status {@code 404 (Not Found)} if the inkooppakket is not found,
     * or with status {@code 500 (Internal Server Error)} if the inkooppakket couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inkooppakket> partialUpdateInkooppakket(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Inkooppakket inkooppakket
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inkooppakket partially : {}, {}", id, inkooppakket);
        if (inkooppakket.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inkooppakket.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inkooppakketRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inkooppakket> result = inkooppakketRepository
            .findById(inkooppakket.getId())
            .map(existingInkooppakket -> {
                if (inkooppakket.getCode() != null) {
                    existingInkooppakket.setCode(inkooppakket.getCode());
                }
                if (inkooppakket.getNaam() != null) {
                    existingInkooppakket.setNaam(inkooppakket.getNaam());
                }
                if (inkooppakket.getType() != null) {
                    existingInkooppakket.setType(inkooppakket.getType());
                }

                return existingInkooppakket;
            })
            .map(inkooppakketRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inkooppakket.getId().toString())
        );
    }

    /**
     * {@code GET  /inkooppakkets} : get all the inkooppakkets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inkooppakkets in body.
     */
    @GetMapping("")
    public List<Inkooppakket> getAllInkooppakkets() {
        log.debug("REST request to get all Inkooppakkets");
        return inkooppakketRepository.findAll();
    }

    /**
     * {@code GET  /inkooppakkets/:id} : get the "id" inkooppakket.
     *
     * @param id the id of the inkooppakket to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inkooppakket, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inkooppakket> getInkooppakket(@PathVariable("id") Long id) {
        log.debug("REST request to get Inkooppakket : {}", id);
        Optional<Inkooppakket> inkooppakket = inkooppakketRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inkooppakket);
    }

    /**
     * {@code DELETE  /inkooppakkets/:id} : delete the "id" inkooppakket.
     *
     * @param id the id of the inkooppakket to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInkooppakket(@PathVariable("id") Long id) {
        log.debug("REST request to delete Inkooppakket : {}", id);
        inkooppakketRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
