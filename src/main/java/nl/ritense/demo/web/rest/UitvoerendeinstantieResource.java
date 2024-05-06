package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Uitvoerendeinstantie;
import nl.ritense.demo.repository.UitvoerendeinstantieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitvoerendeinstantie}.
 */
@RestController
@RequestMapping("/api/uitvoerendeinstanties")
@Transactional
public class UitvoerendeinstantieResource {

    private final Logger log = LoggerFactory.getLogger(UitvoerendeinstantieResource.class);

    private static final String ENTITY_NAME = "uitvoerendeinstantie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitvoerendeinstantieRepository uitvoerendeinstantieRepository;

    public UitvoerendeinstantieResource(UitvoerendeinstantieRepository uitvoerendeinstantieRepository) {
        this.uitvoerendeinstantieRepository = uitvoerendeinstantieRepository;
    }

    /**
     * {@code POST  /uitvoerendeinstanties} : Create a new uitvoerendeinstantie.
     *
     * @param uitvoerendeinstantie the uitvoerendeinstantie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitvoerendeinstantie, or with status {@code 400 (Bad Request)} if the uitvoerendeinstantie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitvoerendeinstantie> createUitvoerendeinstantie(@RequestBody Uitvoerendeinstantie uitvoerendeinstantie)
        throws URISyntaxException {
        log.debug("REST request to save Uitvoerendeinstantie : {}", uitvoerendeinstantie);
        if (uitvoerendeinstantie.getId() != null) {
            throw new BadRequestAlertException("A new uitvoerendeinstantie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitvoerendeinstantie = uitvoerendeinstantieRepository.save(uitvoerendeinstantie);
        return ResponseEntity.created(new URI("/api/uitvoerendeinstanties/" + uitvoerendeinstantie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitvoerendeinstantie.getId().toString()))
            .body(uitvoerendeinstantie);
    }

    /**
     * {@code PUT  /uitvoerendeinstanties/:id} : Updates an existing uitvoerendeinstantie.
     *
     * @param id the id of the uitvoerendeinstantie to save.
     * @param uitvoerendeinstantie the uitvoerendeinstantie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitvoerendeinstantie,
     * or with status {@code 400 (Bad Request)} if the uitvoerendeinstantie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uitvoerendeinstantie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Uitvoerendeinstantie> updateUitvoerendeinstantie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitvoerendeinstantie uitvoerendeinstantie
    ) throws URISyntaxException {
        log.debug("REST request to update Uitvoerendeinstantie : {}, {}", id, uitvoerendeinstantie);
        if (uitvoerendeinstantie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitvoerendeinstantie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitvoerendeinstantieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uitvoerendeinstantie = uitvoerendeinstantieRepository.save(uitvoerendeinstantie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitvoerendeinstantie.getId().toString()))
            .body(uitvoerendeinstantie);
    }

    /**
     * {@code PATCH  /uitvoerendeinstanties/:id} : Partial updates given fields of an existing uitvoerendeinstantie, field will ignore if it is null
     *
     * @param id the id of the uitvoerendeinstantie to save.
     * @param uitvoerendeinstantie the uitvoerendeinstantie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitvoerendeinstantie,
     * or with status {@code 400 (Bad Request)} if the uitvoerendeinstantie is not valid,
     * or with status {@code 404 (Not Found)} if the uitvoerendeinstantie is not found,
     * or with status {@code 500 (Internal Server Error)} if the uitvoerendeinstantie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Uitvoerendeinstantie> partialUpdateUitvoerendeinstantie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitvoerendeinstantie uitvoerendeinstantie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uitvoerendeinstantie partially : {}, {}", id, uitvoerendeinstantie);
        if (uitvoerendeinstantie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitvoerendeinstantie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitvoerendeinstantieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Uitvoerendeinstantie> result = uitvoerendeinstantieRepository
            .findById(uitvoerendeinstantie.getId())
            .map(existingUitvoerendeinstantie -> {
                if (uitvoerendeinstantie.getNaam() != null) {
                    existingUitvoerendeinstantie.setNaam(uitvoerendeinstantie.getNaam());
                }

                return existingUitvoerendeinstantie;
            })
            .map(uitvoerendeinstantieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitvoerendeinstantie.getId().toString())
        );
    }

    /**
     * {@code GET  /uitvoerendeinstanties} : get all the uitvoerendeinstanties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitvoerendeinstanties in body.
     */
    @GetMapping("")
    public List<Uitvoerendeinstantie> getAllUitvoerendeinstanties() {
        log.debug("REST request to get all Uitvoerendeinstanties");
        return uitvoerendeinstantieRepository.findAll();
    }

    /**
     * {@code GET  /uitvoerendeinstanties/:id} : get the "id" uitvoerendeinstantie.
     *
     * @param id the id of the uitvoerendeinstantie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitvoerendeinstantie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitvoerendeinstantie> getUitvoerendeinstantie(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitvoerendeinstantie : {}", id);
        Optional<Uitvoerendeinstantie> uitvoerendeinstantie = uitvoerendeinstantieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitvoerendeinstantie);
    }

    /**
     * {@code DELETE  /uitvoerendeinstanties/:id} : delete the "id" uitvoerendeinstantie.
     *
     * @param id the id of the uitvoerendeinstantie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitvoerendeinstantie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitvoerendeinstantie : {}", id);
        uitvoerendeinstantieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
