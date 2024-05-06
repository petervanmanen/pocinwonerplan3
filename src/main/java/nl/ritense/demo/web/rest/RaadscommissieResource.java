package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Raadscommissie;
import nl.ritense.demo.repository.RaadscommissieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Raadscommissie}.
 */
@RestController
@RequestMapping("/api/raadscommissies")
@Transactional
public class RaadscommissieResource {

    private final Logger log = LoggerFactory.getLogger(RaadscommissieResource.class);

    private static final String ENTITY_NAME = "raadscommissie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RaadscommissieRepository raadscommissieRepository;

    public RaadscommissieResource(RaadscommissieRepository raadscommissieRepository) {
        this.raadscommissieRepository = raadscommissieRepository;
    }

    /**
     * {@code POST  /raadscommissies} : Create a new raadscommissie.
     *
     * @param raadscommissie the raadscommissie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raadscommissie, or with status {@code 400 (Bad Request)} if the raadscommissie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Raadscommissie> createRaadscommissie(@RequestBody Raadscommissie raadscommissie) throws URISyntaxException {
        log.debug("REST request to save Raadscommissie : {}", raadscommissie);
        if (raadscommissie.getId() != null) {
            throw new BadRequestAlertException("A new raadscommissie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        raadscommissie = raadscommissieRepository.save(raadscommissie);
        return ResponseEntity.created(new URI("/api/raadscommissies/" + raadscommissie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, raadscommissie.getId().toString()))
            .body(raadscommissie);
    }

    /**
     * {@code PUT  /raadscommissies/:id} : Updates an existing raadscommissie.
     *
     * @param id the id of the raadscommissie to save.
     * @param raadscommissie the raadscommissie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raadscommissie,
     * or with status {@code 400 (Bad Request)} if the raadscommissie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raadscommissie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Raadscommissie> updateRaadscommissie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Raadscommissie raadscommissie
    ) throws URISyntaxException {
        log.debug("REST request to update Raadscommissie : {}, {}", id, raadscommissie);
        if (raadscommissie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raadscommissie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raadscommissieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        raadscommissie = raadscommissieRepository.save(raadscommissie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raadscommissie.getId().toString()))
            .body(raadscommissie);
    }

    /**
     * {@code PATCH  /raadscommissies/:id} : Partial updates given fields of an existing raadscommissie, field will ignore if it is null
     *
     * @param id the id of the raadscommissie to save.
     * @param raadscommissie the raadscommissie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raadscommissie,
     * or with status {@code 400 (Bad Request)} if the raadscommissie is not valid,
     * or with status {@code 404 (Not Found)} if the raadscommissie is not found,
     * or with status {@code 500 (Internal Server Error)} if the raadscommissie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Raadscommissie> partialUpdateRaadscommissie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Raadscommissie raadscommissie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Raadscommissie partially : {}, {}", id, raadscommissie);
        if (raadscommissie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raadscommissie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raadscommissieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Raadscommissie> result = raadscommissieRepository
            .findById(raadscommissie.getId())
            .map(existingRaadscommissie -> {
                if (raadscommissie.getNaam() != null) {
                    existingRaadscommissie.setNaam(raadscommissie.getNaam());
                }

                return existingRaadscommissie;
            })
            .map(raadscommissieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, raadscommissie.getId().toString())
        );
    }

    /**
     * {@code GET  /raadscommissies} : get all the raadscommissies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of raadscommissies in body.
     */
    @GetMapping("")
    public List<Raadscommissie> getAllRaadscommissies() {
        log.debug("REST request to get all Raadscommissies");
        return raadscommissieRepository.findAll();
    }

    /**
     * {@code GET  /raadscommissies/:id} : get the "id" raadscommissie.
     *
     * @param id the id of the raadscommissie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raadscommissie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Raadscommissie> getRaadscommissie(@PathVariable("id") Long id) {
        log.debug("REST request to get Raadscommissie : {}", id);
        Optional<Raadscommissie> raadscommissie = raadscommissieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(raadscommissie);
    }

    /**
     * {@code DELETE  /raadscommissies/:id} : delete the "id" raadscommissie.
     *
     * @param id the id of the raadscommissie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRaadscommissie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Raadscommissie : {}", id);
        raadscommissieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
