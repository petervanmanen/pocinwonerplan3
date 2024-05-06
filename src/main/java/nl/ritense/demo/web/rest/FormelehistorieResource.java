package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Formelehistorie;
import nl.ritense.demo.repository.FormelehistorieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Formelehistorie}.
 */
@RestController
@RequestMapping("/api/formelehistories")
@Transactional
public class FormelehistorieResource {

    private final Logger log = LoggerFactory.getLogger(FormelehistorieResource.class);

    private static final String ENTITY_NAME = "formelehistorie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormelehistorieRepository formelehistorieRepository;

    public FormelehistorieResource(FormelehistorieRepository formelehistorieRepository) {
        this.formelehistorieRepository = formelehistorieRepository;
    }

    /**
     * {@code POST  /formelehistories} : Create a new formelehistorie.
     *
     * @param formelehistorie the formelehistorie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formelehistorie, or with status {@code 400 (Bad Request)} if the formelehistorie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Formelehistorie> createFormelehistorie(@RequestBody Formelehistorie formelehistorie) throws URISyntaxException {
        log.debug("REST request to save Formelehistorie : {}", formelehistorie);
        if (formelehistorie.getId() != null) {
            throw new BadRequestAlertException("A new formelehistorie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formelehistorie = formelehistorieRepository.save(formelehistorie);
        return ResponseEntity.created(new URI("/api/formelehistories/" + formelehistorie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, formelehistorie.getId().toString()))
            .body(formelehistorie);
    }

    /**
     * {@code PUT  /formelehistories/:id} : Updates an existing formelehistorie.
     *
     * @param id the id of the formelehistorie to save.
     * @param formelehistorie the formelehistorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formelehistorie,
     * or with status {@code 400 (Bad Request)} if the formelehistorie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formelehistorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Formelehistorie> updateFormelehistorie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formelehistorie formelehistorie
    ) throws URISyntaxException {
        log.debug("REST request to update Formelehistorie : {}, {}", id, formelehistorie);
        if (formelehistorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formelehistorie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formelehistorieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formelehistorie = formelehistorieRepository.save(formelehistorie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formelehistorie.getId().toString()))
            .body(formelehistorie);
    }

    /**
     * {@code PATCH  /formelehistories/:id} : Partial updates given fields of an existing formelehistorie, field will ignore if it is null
     *
     * @param id the id of the formelehistorie to save.
     * @param formelehistorie the formelehistorie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formelehistorie,
     * or with status {@code 400 (Bad Request)} if the formelehistorie is not valid,
     * or with status {@code 404 (Not Found)} if the formelehistorie is not found,
     * or with status {@code 500 (Internal Server Error)} if the formelehistorie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Formelehistorie> partialUpdateFormelehistorie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Formelehistorie formelehistorie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Formelehistorie partially : {}, {}", id, formelehistorie);
        if (formelehistorie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formelehistorie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formelehistorieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Formelehistorie> result = formelehistorieRepository
            .findById(formelehistorie.getId())
            .map(existingFormelehistorie -> {
                if (formelehistorie.getTijdstipregistratiegegevens() != null) {
                    existingFormelehistorie.setTijdstipregistratiegegevens(formelehistorie.getTijdstipregistratiegegevens());
                }

                return existingFormelehistorie;
            })
            .map(formelehistorieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formelehistorie.getId().toString())
        );
    }

    /**
     * {@code GET  /formelehistories} : get all the formelehistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formelehistories in body.
     */
    @GetMapping("")
    public List<Formelehistorie> getAllFormelehistories() {
        log.debug("REST request to get all Formelehistories");
        return formelehistorieRepository.findAll();
    }

    /**
     * {@code GET  /formelehistories/:id} : get the "id" formelehistorie.
     *
     * @param id the id of the formelehistorie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formelehistorie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Formelehistorie> getFormelehistorie(@PathVariable("id") Long id) {
        log.debug("REST request to get Formelehistorie : {}", id);
        Optional<Formelehistorie> formelehistorie = formelehistorieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formelehistorie);
    }

    /**
     * {@code DELETE  /formelehistories/:id} : delete the "id" formelehistorie.
     *
     * @param id the id of the formelehistorie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormelehistorie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Formelehistorie : {}", id);
        formelehistorieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
