package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aantal;
import nl.ritense.demo.repository.AantalRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aantal}.
 */
@RestController
@RequestMapping("/api/aantals")
@Transactional
public class AantalResource {

    private final Logger log = LoggerFactory.getLogger(AantalResource.class);

    private static final String ENTITY_NAME = "aantal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AantalRepository aantalRepository;

    public AantalResource(AantalRepository aantalRepository) {
        this.aantalRepository = aantalRepository;
    }

    /**
     * {@code POST  /aantals} : Create a new aantal.
     *
     * @param aantal the aantal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aantal, or with status {@code 400 (Bad Request)} if the aantal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aantal> createAantal(@RequestBody Aantal aantal) throws URISyntaxException {
        log.debug("REST request to save Aantal : {}", aantal);
        if (aantal.getId() != null) {
            throw new BadRequestAlertException("A new aantal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aantal = aantalRepository.save(aantal);
        return ResponseEntity.created(new URI("/api/aantals/" + aantal.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aantal.getId().toString()))
            .body(aantal);
    }

    /**
     * {@code PUT  /aantals/:id} : Updates an existing aantal.
     *
     * @param id the id of the aantal to save.
     * @param aantal the aantal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aantal,
     * or with status {@code 400 (Bad Request)} if the aantal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aantal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aantal> updateAantal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Aantal aantal)
        throws URISyntaxException {
        log.debug("REST request to update Aantal : {}, {}", id, aantal);
        if (aantal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aantal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aantalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aantal = aantalRepository.save(aantal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aantal.getId().toString()))
            .body(aantal);
    }

    /**
     * {@code PATCH  /aantals/:id} : Partial updates given fields of an existing aantal, field will ignore if it is null
     *
     * @param id the id of the aantal to save.
     * @param aantal the aantal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aantal,
     * or with status {@code 400 (Bad Request)} if the aantal is not valid,
     * or with status {@code 404 (Not Found)} if the aantal is not found,
     * or with status {@code 500 (Internal Server Error)} if the aantal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aantal> partialUpdateAantal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aantal aantal
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aantal partially : {}, {}", id, aantal);
        if (aantal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aantal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aantalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aantal> result = aantalRepository
            .findById(aantal.getId())
            .map(existingAantal -> {
                if (aantal.getAantal() != null) {
                    existingAantal.setAantal(aantal.getAantal());
                }

                return existingAantal;
            })
            .map(aantalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aantal.getId().toString())
        );
    }

    /**
     * {@code GET  /aantals} : get all the aantals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aantals in body.
     */
    @GetMapping("")
    public List<Aantal> getAllAantals() {
        log.debug("REST request to get all Aantals");
        return aantalRepository.findAll();
    }

    /**
     * {@code GET  /aantals/:id} : get the "id" aantal.
     *
     * @param id the id of the aantal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aantal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aantal> getAantal(@PathVariable("id") Long id) {
        log.debug("REST request to get Aantal : {}", id);
        Optional<Aantal> aantal = aantalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aantal);
    }

    /**
     * {@code DELETE  /aantals/:id} : delete the "id" aantal.
     *
     * @param id the id of the aantal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAantal(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aantal : {}", id);
        aantalRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
