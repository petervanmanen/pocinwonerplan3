package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Kostenplaats;
import nl.ritense.demo.repository.KostenplaatsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Kostenplaats}.
 */
@RestController
@RequestMapping("/api/kostenplaats")
@Transactional
public class KostenplaatsResource {

    private final Logger log = LoggerFactory.getLogger(KostenplaatsResource.class);

    private static final String ENTITY_NAME = "kostenplaats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KostenplaatsRepository kostenplaatsRepository;

    public KostenplaatsResource(KostenplaatsRepository kostenplaatsRepository) {
        this.kostenplaatsRepository = kostenplaatsRepository;
    }

    /**
     * {@code POST  /kostenplaats} : Create a new kostenplaats.
     *
     * @param kostenplaats the kostenplaats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kostenplaats, or with status {@code 400 (Bad Request)} if the kostenplaats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Kostenplaats> createKostenplaats(@Valid @RequestBody Kostenplaats kostenplaats) throws URISyntaxException {
        log.debug("REST request to save Kostenplaats : {}", kostenplaats);
        if (kostenplaats.getId() != null) {
            throw new BadRequestAlertException("A new kostenplaats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        kostenplaats = kostenplaatsRepository.save(kostenplaats);
        return ResponseEntity.created(new URI("/api/kostenplaats/" + kostenplaats.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, kostenplaats.getId().toString()))
            .body(kostenplaats);
    }

    /**
     * {@code PUT  /kostenplaats/:id} : Updates an existing kostenplaats.
     *
     * @param id the id of the kostenplaats to save.
     * @param kostenplaats the kostenplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kostenplaats,
     * or with status {@code 400 (Bad Request)} if the kostenplaats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kostenplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kostenplaats> updateKostenplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Kostenplaats kostenplaats
    ) throws URISyntaxException {
        log.debug("REST request to update Kostenplaats : {}, {}", id, kostenplaats);
        if (kostenplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kostenplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kostenplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        kostenplaats = kostenplaatsRepository.save(kostenplaats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kostenplaats.getId().toString()))
            .body(kostenplaats);
    }

    /**
     * {@code PATCH  /kostenplaats/:id} : Partial updates given fields of an existing kostenplaats, field will ignore if it is null
     *
     * @param id the id of the kostenplaats to save.
     * @param kostenplaats the kostenplaats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kostenplaats,
     * or with status {@code 400 (Bad Request)} if the kostenplaats is not valid,
     * or with status {@code 404 (Not Found)} if the kostenplaats is not found,
     * or with status {@code 500 (Internal Server Error)} if the kostenplaats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kostenplaats> partialUpdateKostenplaats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Kostenplaats kostenplaats
    ) throws URISyntaxException {
        log.debug("REST request to partial update Kostenplaats partially : {}, {}", id, kostenplaats);
        if (kostenplaats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kostenplaats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kostenplaatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kostenplaats> result = kostenplaatsRepository
            .findById(kostenplaats.getId())
            .map(existingKostenplaats -> {
                if (kostenplaats.getBtwcode() != null) {
                    existingKostenplaats.setBtwcode(kostenplaats.getBtwcode());
                }
                if (kostenplaats.getBtwomschrijving() != null) {
                    existingKostenplaats.setBtwomschrijving(kostenplaats.getBtwomschrijving());
                }
                if (kostenplaats.getKostenplaatssoortcode() != null) {
                    existingKostenplaats.setKostenplaatssoortcode(kostenplaats.getKostenplaatssoortcode());
                }
                if (kostenplaats.getKostenplaatssoortomschrijving() != null) {
                    existingKostenplaats.setKostenplaatssoortomschrijving(kostenplaats.getKostenplaatssoortomschrijving());
                }
                if (kostenplaats.getKostenplaatstypecode() != null) {
                    existingKostenplaats.setKostenplaatstypecode(kostenplaats.getKostenplaatstypecode());
                }
                if (kostenplaats.getKostenplaatstypeomschrijving() != null) {
                    existingKostenplaats.setKostenplaatstypeomschrijving(kostenplaats.getKostenplaatstypeomschrijving());
                }
                if (kostenplaats.getNaam() != null) {
                    existingKostenplaats.setNaam(kostenplaats.getNaam());
                }
                if (kostenplaats.getOmschrijving() != null) {
                    existingKostenplaats.setOmschrijving(kostenplaats.getOmschrijving());
                }

                return existingKostenplaats;
            })
            .map(kostenplaatsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kostenplaats.getId().toString())
        );
    }

    /**
     * {@code GET  /kostenplaats} : get all the kostenplaats.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kostenplaats in body.
     */
    @GetMapping("")
    public List<Kostenplaats> getAllKostenplaats(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all Kostenplaats");
        if (eagerload) {
            return kostenplaatsRepository.findAllWithEagerRelationships();
        } else {
            return kostenplaatsRepository.findAll();
        }
    }

    /**
     * {@code GET  /kostenplaats/:id} : get the "id" kostenplaats.
     *
     * @param id the id of the kostenplaats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kostenplaats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kostenplaats> getKostenplaats(@PathVariable("id") Long id) {
        log.debug("REST request to get Kostenplaats : {}", id);
        Optional<Kostenplaats> kostenplaats = kostenplaatsRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(kostenplaats);
    }

    /**
     * {@code DELETE  /kostenplaats/:id} : delete the "id" kostenplaats.
     *
     * @param id the id of the kostenplaats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKostenplaats(@PathVariable("id") Long id) {
        log.debug("REST request to delete Kostenplaats : {}", id);
        kostenplaatsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
