package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Academischetitel;
import nl.ritense.demo.repository.AcademischetitelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Academischetitel}.
 */
@RestController
@RequestMapping("/api/academischetitels")
@Transactional
public class AcademischetitelResource {

    private final Logger log = LoggerFactory.getLogger(AcademischetitelResource.class);

    private static final String ENTITY_NAME = "academischetitel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademischetitelRepository academischetitelRepository;

    public AcademischetitelResource(AcademischetitelRepository academischetitelRepository) {
        this.academischetitelRepository = academischetitelRepository;
    }

    /**
     * {@code POST  /academischetitels} : Create a new academischetitel.
     *
     * @param academischetitel the academischetitel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academischetitel, or with status {@code 400 (Bad Request)} if the academischetitel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Academischetitel> createAcademischetitel(@RequestBody Academischetitel academischetitel)
        throws URISyntaxException {
        log.debug("REST request to save Academischetitel : {}", academischetitel);
        if (academischetitel.getId() != null) {
            throw new BadRequestAlertException("A new academischetitel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        academischetitel = academischetitelRepository.save(academischetitel);
        return ResponseEntity.created(new URI("/api/academischetitels/" + academischetitel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, academischetitel.getId().toString()))
            .body(academischetitel);
    }

    /**
     * {@code PUT  /academischetitels/:id} : Updates an existing academischetitel.
     *
     * @param id the id of the academischetitel to save.
     * @param academischetitel the academischetitel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academischetitel,
     * or with status {@code 400 (Bad Request)} if the academischetitel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academischetitel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Academischetitel> updateAcademischetitel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Academischetitel academischetitel
    ) throws URISyntaxException {
        log.debug("REST request to update Academischetitel : {}, {}", id, academischetitel);
        if (academischetitel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academischetitel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academischetitelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        academischetitel = academischetitelRepository.save(academischetitel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, academischetitel.getId().toString()))
            .body(academischetitel);
    }

    /**
     * {@code PATCH  /academischetitels/:id} : Partial updates given fields of an existing academischetitel, field will ignore if it is null
     *
     * @param id the id of the academischetitel to save.
     * @param academischetitel the academischetitel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academischetitel,
     * or with status {@code 400 (Bad Request)} if the academischetitel is not valid,
     * or with status {@code 404 (Not Found)} if the academischetitel is not found,
     * or with status {@code 500 (Internal Server Error)} if the academischetitel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Academischetitel> partialUpdateAcademischetitel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Academischetitel academischetitel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Academischetitel partially : {}, {}", id, academischetitel);
        if (academischetitel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academischetitel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academischetitelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Academischetitel> result = academischetitelRepository
            .findById(academischetitel.getId())
            .map(existingAcademischetitel -> {
                if (academischetitel.getCodeacademischetitel() != null) {
                    existingAcademischetitel.setCodeacademischetitel(academischetitel.getCodeacademischetitel());
                }
                if (academischetitel.getDatumbegingeldigheidtitel() != null) {
                    existingAcademischetitel.setDatumbegingeldigheidtitel(academischetitel.getDatumbegingeldigheidtitel());
                }
                if (academischetitel.getDatumeindegeldigheidtitel() != null) {
                    existingAcademischetitel.setDatumeindegeldigheidtitel(academischetitel.getDatumeindegeldigheidtitel());
                }
                if (academischetitel.getOmschrijvingacademischetitel() != null) {
                    existingAcademischetitel.setOmschrijvingacademischetitel(academischetitel.getOmschrijvingacademischetitel());
                }
                if (academischetitel.getPositieacademischetiteltovnaam() != null) {
                    existingAcademischetitel.setPositieacademischetiteltovnaam(academischetitel.getPositieacademischetiteltovnaam());
                }

                return existingAcademischetitel;
            })
            .map(academischetitelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, academischetitel.getId().toString())
        );
    }

    /**
     * {@code GET  /academischetitels} : get all the academischetitels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academischetitels in body.
     */
    @GetMapping("")
    public List<Academischetitel> getAllAcademischetitels() {
        log.debug("REST request to get all Academischetitels");
        return academischetitelRepository.findAll();
    }

    /**
     * {@code GET  /academischetitels/:id} : get the "id" academischetitel.
     *
     * @param id the id of the academischetitel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academischetitel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Academischetitel> getAcademischetitel(@PathVariable("id") Long id) {
        log.debug("REST request to get Academischetitel : {}", id);
        Optional<Academischetitel> academischetitel = academischetitelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(academischetitel);
    }

    /**
     * {@code DELETE  /academischetitels/:id} : delete the "id" academischetitel.
     *
     * @param id the id of the academischetitel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademischetitel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Academischetitel : {}", id);
        academischetitelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
