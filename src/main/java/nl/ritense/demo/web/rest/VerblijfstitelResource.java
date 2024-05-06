package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfstitel;
import nl.ritense.demo.repository.VerblijfstitelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verblijfstitel}.
 */
@RestController
@RequestMapping("/api/verblijfstitels")
@Transactional
public class VerblijfstitelResource {

    private final Logger log = LoggerFactory.getLogger(VerblijfstitelResource.class);

    private static final String ENTITY_NAME = "verblijfstitel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerblijfstitelRepository verblijfstitelRepository;

    public VerblijfstitelResource(VerblijfstitelRepository verblijfstitelRepository) {
        this.verblijfstitelRepository = verblijfstitelRepository;
    }

    /**
     * {@code POST  /verblijfstitels} : Create a new verblijfstitel.
     *
     * @param verblijfstitel the verblijfstitel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verblijfstitel, or with status {@code 400 (Bad Request)} if the verblijfstitel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verblijfstitel> createVerblijfstitel(@RequestBody Verblijfstitel verblijfstitel) throws URISyntaxException {
        log.debug("REST request to save Verblijfstitel : {}", verblijfstitel);
        if (verblijfstitel.getId() != null) {
            throw new BadRequestAlertException("A new verblijfstitel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verblijfstitel = verblijfstitelRepository.save(verblijfstitel);
        return ResponseEntity.created(new URI("/api/verblijfstitels/" + verblijfstitel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verblijfstitel.getId().toString()))
            .body(verblijfstitel);
    }

    /**
     * {@code PUT  /verblijfstitels/:id} : Updates an existing verblijfstitel.
     *
     * @param id the id of the verblijfstitel to save.
     * @param verblijfstitel the verblijfstitel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfstitel,
     * or with status {@code 400 (Bad Request)} if the verblijfstitel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verblijfstitel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verblijfstitel> updateVerblijfstitel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfstitel verblijfstitel
    ) throws URISyntaxException {
        log.debug("REST request to update Verblijfstitel : {}, {}", id, verblijfstitel);
        if (verblijfstitel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfstitel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfstitelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verblijfstitel = verblijfstitelRepository.save(verblijfstitel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfstitel.getId().toString()))
            .body(verblijfstitel);
    }

    /**
     * {@code PATCH  /verblijfstitels/:id} : Partial updates given fields of an existing verblijfstitel, field will ignore if it is null
     *
     * @param id the id of the verblijfstitel to save.
     * @param verblijfstitel the verblijfstitel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verblijfstitel,
     * or with status {@code 400 (Bad Request)} if the verblijfstitel is not valid,
     * or with status {@code 404 (Not Found)} if the verblijfstitel is not found,
     * or with status {@code 500 (Internal Server Error)} if the verblijfstitel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verblijfstitel> partialUpdateVerblijfstitel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verblijfstitel verblijfstitel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verblijfstitel partially : {}, {}", id, verblijfstitel);
        if (verblijfstitel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verblijfstitel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verblijfstitelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verblijfstitel> result = verblijfstitelRepository
            .findById(verblijfstitel.getId())
            .map(existingVerblijfstitel -> {
                if (verblijfstitel.getAanduidingverblijfstitel() != null) {
                    existingVerblijfstitel.setAanduidingverblijfstitel(verblijfstitel.getAanduidingverblijfstitel());
                }
                if (verblijfstitel.getDatumbegin() != null) {
                    existingVerblijfstitel.setDatumbegin(verblijfstitel.getDatumbegin());
                }
                if (verblijfstitel.getDatumeinde() != null) {
                    existingVerblijfstitel.setDatumeinde(verblijfstitel.getDatumeinde());
                }
                if (verblijfstitel.getDatumopname() != null) {
                    existingVerblijfstitel.setDatumopname(verblijfstitel.getDatumopname());
                }
                if (verblijfstitel.getDatumbegingeldigheidverblijfstitel() != null) {
                    existingVerblijfstitel.setDatumbegingeldigheidverblijfstitel(verblijfstitel.getDatumbegingeldigheidverblijfstitel());
                }
                if (verblijfstitel.getVerblijfstitelcode() != null) {
                    existingVerblijfstitel.setVerblijfstitelcode(verblijfstitel.getVerblijfstitelcode());
                }

                return existingVerblijfstitel;
            })
            .map(verblijfstitelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verblijfstitel.getId().toString())
        );
    }

    /**
     * {@code GET  /verblijfstitels} : get all the verblijfstitels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verblijfstitels in body.
     */
    @GetMapping("")
    public List<Verblijfstitel> getAllVerblijfstitels() {
        log.debug("REST request to get all Verblijfstitels");
        return verblijfstitelRepository.findAll();
    }

    /**
     * {@code GET  /verblijfstitels/:id} : get the "id" verblijfstitel.
     *
     * @param id the id of the verblijfstitel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verblijfstitel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verblijfstitel> getVerblijfstitel(@PathVariable("id") Long id) {
        log.debug("REST request to get Verblijfstitel : {}", id);
        Optional<Verblijfstitel> verblijfstitel = verblijfstitelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verblijfstitel);
    }

    /**
     * {@code DELETE  /verblijfstitels/:id} : delete the "id" verblijfstitel.
     *
     * @param id the id of the verblijfstitel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerblijfstitel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verblijfstitel : {}", id);
        verblijfstitelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
