package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Entreekaart;
import nl.ritense.demo.repository.EntreekaartRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Entreekaart}.
 */
@RestController
@RequestMapping("/api/entreekaarts")
@Transactional
public class EntreekaartResource {

    private final Logger log = LoggerFactory.getLogger(EntreekaartResource.class);

    private static final String ENTITY_NAME = "entreekaart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntreekaartRepository entreekaartRepository;

    public EntreekaartResource(EntreekaartRepository entreekaartRepository) {
        this.entreekaartRepository = entreekaartRepository;
    }

    /**
     * {@code POST  /entreekaarts} : Create a new entreekaart.
     *
     * @param entreekaart the entreekaart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entreekaart, or with status {@code 400 (Bad Request)} if the entreekaart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Entreekaart> createEntreekaart(@RequestBody Entreekaart entreekaart) throws URISyntaxException {
        log.debug("REST request to save Entreekaart : {}", entreekaart);
        if (entreekaart.getId() != null) {
            throw new BadRequestAlertException("A new entreekaart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        entreekaart = entreekaartRepository.save(entreekaart);
        return ResponseEntity.created(new URI("/api/entreekaarts/" + entreekaart.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, entreekaart.getId().toString()))
            .body(entreekaart);
    }

    /**
     * {@code PUT  /entreekaarts/:id} : Updates an existing entreekaart.
     *
     * @param id the id of the entreekaart to save.
     * @param entreekaart the entreekaart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entreekaart,
     * or with status {@code 400 (Bad Request)} if the entreekaart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entreekaart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Entreekaart> updateEntreekaart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Entreekaart entreekaart
    ) throws URISyntaxException {
        log.debug("REST request to update Entreekaart : {}, {}", id, entreekaart);
        if (entreekaart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entreekaart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entreekaartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        entreekaart = entreekaartRepository.save(entreekaart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entreekaart.getId().toString()))
            .body(entreekaart);
    }

    /**
     * {@code PATCH  /entreekaarts/:id} : Partial updates given fields of an existing entreekaart, field will ignore if it is null
     *
     * @param id the id of the entreekaart to save.
     * @param entreekaart the entreekaart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entreekaart,
     * or with status {@code 400 (Bad Request)} if the entreekaart is not valid,
     * or with status {@code 404 (Not Found)} if the entreekaart is not found,
     * or with status {@code 500 (Internal Server Error)} if the entreekaart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Entreekaart> partialUpdateEntreekaart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Entreekaart entreekaart
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entreekaart partially : {}, {}", id, entreekaart);
        if (entreekaart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entreekaart.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entreekaartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Entreekaart> result = entreekaartRepository
            .findById(entreekaart.getId())
            .map(existingEntreekaart -> {
                if (entreekaart.getRondleiding() != null) {
                    existingEntreekaart.setRondleiding(entreekaart.getRondleiding());
                }

                return existingEntreekaart;
            })
            .map(entreekaartRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entreekaart.getId().toString())
        );
    }

    /**
     * {@code GET  /entreekaarts} : get all the entreekaarts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entreekaarts in body.
     */
    @GetMapping("")
    public List<Entreekaart> getAllEntreekaarts() {
        log.debug("REST request to get all Entreekaarts");
        return entreekaartRepository.findAll();
    }

    /**
     * {@code GET  /entreekaarts/:id} : get the "id" entreekaart.
     *
     * @param id the id of the entreekaart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entreekaart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Entreekaart> getEntreekaart(@PathVariable("id") Long id) {
        log.debug("REST request to get Entreekaart : {}", id);
        Optional<Entreekaart> entreekaart = entreekaartRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entreekaart);
    }

    /**
     * {@code DELETE  /entreekaarts/:id} : delete the "id" entreekaart.
     *
     * @param id the id of the entreekaart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntreekaart(@PathVariable("id") Long id) {
        log.debug("REST request to delete Entreekaart : {}", id);
        entreekaartRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
