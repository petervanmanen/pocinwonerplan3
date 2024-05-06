package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Uren;
import nl.ritense.demo.repository.UrenRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uren}.
 */
@RestController
@RequestMapping("/api/urens")
@Transactional
public class UrenResource {

    private final Logger log = LoggerFactory.getLogger(UrenResource.class);

    private static final String ENTITY_NAME = "uren";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UrenRepository urenRepository;

    public UrenResource(UrenRepository urenRepository) {
        this.urenRepository = urenRepository;
    }

    /**
     * {@code POST  /urens} : Create a new uren.
     *
     * @param uren the uren to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uren, or with status {@code 400 (Bad Request)} if the uren has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uren> createUren(@Valid @RequestBody Uren uren) throws URISyntaxException {
        log.debug("REST request to save Uren : {}", uren);
        if (uren.getId() != null) {
            throw new BadRequestAlertException("A new uren cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uren = urenRepository.save(uren);
        return ResponseEntity.created(new URI("/api/urens/" + uren.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uren.getId().toString()))
            .body(uren);
    }

    /**
     * {@code PUT  /urens/:id} : Updates an existing uren.
     *
     * @param id the id of the uren to save.
     * @param uren the uren to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uren,
     * or with status {@code 400 (Bad Request)} if the uren is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uren couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Uren> updateUren(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Uren uren)
        throws URISyntaxException {
        log.debug("REST request to update Uren : {}, {}", id, uren);
        if (uren.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uren.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!urenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uren = urenRepository.save(uren);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uren.getId().toString()))
            .body(uren);
    }

    /**
     * {@code PATCH  /urens/:id} : Partial updates given fields of an existing uren, field will ignore if it is null
     *
     * @param id the id of the uren to save.
     * @param uren the uren to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uren,
     * or with status {@code 400 (Bad Request)} if the uren is not valid,
     * or with status {@code 404 (Not Found)} if the uren is not found,
     * or with status {@code 500 (Internal Server Error)} if the uren couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Uren> partialUpdateUren(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Uren uren
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uren partially : {}, {}", id, uren);
        if (uren.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uren.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!urenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Uren> result = urenRepository
            .findById(uren.getId())
            .map(existingUren -> {
                if (uren.getAantal() != null) {
                    existingUren.setAantal(uren.getAantal());
                }

                return existingUren;
            })
            .map(urenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uren.getId().toString())
        );
    }

    /**
     * {@code GET  /urens} : get all the urens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of urens in body.
     */
    @GetMapping("")
    public List<Uren> getAllUrens() {
        log.debug("REST request to get all Urens");
        return urenRepository.findAll();
    }

    /**
     * {@code GET  /urens/:id} : get the "id" uren.
     *
     * @param id the id of the uren to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uren, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uren> getUren(@PathVariable("id") Long id) {
        log.debug("REST request to get Uren : {}", id);
        Optional<Uren> uren = urenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uren);
    }

    /**
     * {@code DELETE  /urens/:id} : delete the "id" uren.
     *
     * @param id the id of the uren to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUren(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uren : {}", id);
        urenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
