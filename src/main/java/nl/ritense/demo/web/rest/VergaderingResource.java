package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Vergadering;
import nl.ritense.demo.repository.VergaderingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Vergadering}.
 */
@RestController
@RequestMapping("/api/vergaderings")
@Transactional
public class VergaderingResource {

    private final Logger log = LoggerFactory.getLogger(VergaderingResource.class);

    private static final String ENTITY_NAME = "vergadering";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VergaderingRepository vergaderingRepository;

    public VergaderingResource(VergaderingRepository vergaderingRepository) {
        this.vergaderingRepository = vergaderingRepository;
    }

    /**
     * {@code POST  /vergaderings} : Create a new vergadering.
     *
     * @param vergadering the vergadering to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vergadering, or with status {@code 400 (Bad Request)} if the vergadering has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Vergadering> createVergadering(@Valid @RequestBody Vergadering vergadering) throws URISyntaxException {
        log.debug("REST request to save Vergadering : {}", vergadering);
        if (vergadering.getId() != null) {
            throw new BadRequestAlertException("A new vergadering cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vergadering = vergaderingRepository.save(vergadering);
        return ResponseEntity.created(new URI("/api/vergaderings/" + vergadering.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vergadering.getId().toString()))
            .body(vergadering);
    }

    /**
     * {@code PUT  /vergaderings/:id} : Updates an existing vergadering.
     *
     * @param id the id of the vergadering to save.
     * @param vergadering the vergadering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vergadering,
     * or with status {@code 400 (Bad Request)} if the vergadering is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vergadering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vergadering> updateVergadering(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Vergadering vergadering
    ) throws URISyntaxException {
        log.debug("REST request to update Vergadering : {}, {}", id, vergadering);
        if (vergadering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vergadering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vergaderingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vergadering = vergaderingRepository.save(vergadering);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vergadering.getId().toString()))
            .body(vergadering);
    }

    /**
     * {@code PATCH  /vergaderings/:id} : Partial updates given fields of an existing vergadering, field will ignore if it is null
     *
     * @param id the id of the vergadering to save.
     * @param vergadering the vergadering to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vergadering,
     * or with status {@code 400 (Bad Request)} if the vergadering is not valid,
     * or with status {@code 404 (Not Found)} if the vergadering is not found,
     * or with status {@code 500 (Internal Server Error)} if the vergadering couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Vergadering> partialUpdateVergadering(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Vergadering vergadering
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vergadering partially : {}, {}", id, vergadering);
        if (vergadering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vergadering.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vergaderingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Vergadering> result = vergaderingRepository
            .findById(vergadering.getId())
            .map(existingVergadering -> {
                if (vergadering.getEindtijd() != null) {
                    existingVergadering.setEindtijd(vergadering.getEindtijd());
                }
                if (vergadering.getLocatie() != null) {
                    existingVergadering.setLocatie(vergadering.getLocatie());
                }
                if (vergadering.getStarttijd() != null) {
                    existingVergadering.setStarttijd(vergadering.getStarttijd());
                }
                if (vergadering.getTitel() != null) {
                    existingVergadering.setTitel(vergadering.getTitel());
                }

                return existingVergadering;
            })
            .map(vergaderingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vergadering.getId().toString())
        );
    }

    /**
     * {@code GET  /vergaderings} : get all the vergaderings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vergaderings in body.
     */
    @GetMapping("")
    public List<Vergadering> getAllVergaderings() {
        log.debug("REST request to get all Vergaderings");
        return vergaderingRepository.findAll();
    }

    /**
     * {@code GET  /vergaderings/:id} : get the "id" vergadering.
     *
     * @param id the id of the vergadering to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vergadering, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vergadering> getVergadering(@PathVariable("id") Long id) {
        log.debug("REST request to get Vergadering : {}", id);
        Optional<Vergadering> vergadering = vergaderingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vergadering);
    }

    /**
     * {@code DELETE  /vergaderings/:id} : delete the "id" vergadering.
     *
     * @param id the id of the vergadering to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVergadering(@PathVariable("id") Long id) {
        log.debug("REST request to delete Vergadering : {}", id);
        vergaderingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
