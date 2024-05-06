package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Uitlaatconstructie;
import nl.ritense.demo.repository.UitlaatconstructieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Uitlaatconstructie}.
 */
@RestController
@RequestMapping("/api/uitlaatconstructies")
@Transactional
public class UitlaatconstructieResource {

    private final Logger log = LoggerFactory.getLogger(UitlaatconstructieResource.class);

    private static final String ENTITY_NAME = "uitlaatconstructie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UitlaatconstructieRepository uitlaatconstructieRepository;

    public UitlaatconstructieResource(UitlaatconstructieRepository uitlaatconstructieRepository) {
        this.uitlaatconstructieRepository = uitlaatconstructieRepository;
    }

    /**
     * {@code POST  /uitlaatconstructies} : Create a new uitlaatconstructie.
     *
     * @param uitlaatconstructie the uitlaatconstructie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uitlaatconstructie, or with status {@code 400 (Bad Request)} if the uitlaatconstructie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Uitlaatconstructie> createUitlaatconstructie(@RequestBody Uitlaatconstructie uitlaatconstructie)
        throws URISyntaxException {
        log.debug("REST request to save Uitlaatconstructie : {}", uitlaatconstructie);
        if (uitlaatconstructie.getId() != null) {
            throw new BadRequestAlertException("A new uitlaatconstructie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uitlaatconstructie = uitlaatconstructieRepository.save(uitlaatconstructie);
        return ResponseEntity.created(new URI("/api/uitlaatconstructies/" + uitlaatconstructie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, uitlaatconstructie.getId().toString()))
            .body(uitlaatconstructie);
    }

    /**
     * {@code PUT  /uitlaatconstructies/:id} : Updates an existing uitlaatconstructie.
     *
     * @param id the id of the uitlaatconstructie to save.
     * @param uitlaatconstructie the uitlaatconstructie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitlaatconstructie,
     * or with status {@code 400 (Bad Request)} if the uitlaatconstructie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uitlaatconstructie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Uitlaatconstructie> updateUitlaatconstructie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitlaatconstructie uitlaatconstructie
    ) throws URISyntaxException {
        log.debug("REST request to update Uitlaatconstructie : {}, {}", id, uitlaatconstructie);
        if (uitlaatconstructie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitlaatconstructie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitlaatconstructieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uitlaatconstructie = uitlaatconstructieRepository.save(uitlaatconstructie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitlaatconstructie.getId().toString()))
            .body(uitlaatconstructie);
    }

    /**
     * {@code PATCH  /uitlaatconstructies/:id} : Partial updates given fields of an existing uitlaatconstructie, field will ignore if it is null
     *
     * @param id the id of the uitlaatconstructie to save.
     * @param uitlaatconstructie the uitlaatconstructie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uitlaatconstructie,
     * or with status {@code 400 (Bad Request)} if the uitlaatconstructie is not valid,
     * or with status {@code 404 (Not Found)} if the uitlaatconstructie is not found,
     * or with status {@code 500 (Internal Server Error)} if the uitlaatconstructie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Uitlaatconstructie> partialUpdateUitlaatconstructie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Uitlaatconstructie uitlaatconstructie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Uitlaatconstructie partially : {}, {}", id, uitlaatconstructie);
        if (uitlaatconstructie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uitlaatconstructie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uitlaatconstructieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Uitlaatconstructie> result = uitlaatconstructieRepository
            .findById(uitlaatconstructie.getId())
            .map(existingUitlaatconstructie -> {
                if (uitlaatconstructie.getType() != null) {
                    existingUitlaatconstructie.setType(uitlaatconstructie.getType());
                }
                if (uitlaatconstructie.getWaterobject() != null) {
                    existingUitlaatconstructie.setWaterobject(uitlaatconstructie.getWaterobject());
                }

                return existingUitlaatconstructie;
            })
            .map(uitlaatconstructieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uitlaatconstructie.getId().toString())
        );
    }

    /**
     * {@code GET  /uitlaatconstructies} : get all the uitlaatconstructies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uitlaatconstructies in body.
     */
    @GetMapping("")
    public List<Uitlaatconstructie> getAllUitlaatconstructies() {
        log.debug("REST request to get all Uitlaatconstructies");
        return uitlaatconstructieRepository.findAll();
    }

    /**
     * {@code GET  /uitlaatconstructies/:id} : get the "id" uitlaatconstructie.
     *
     * @param id the id of the uitlaatconstructie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uitlaatconstructie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Uitlaatconstructie> getUitlaatconstructie(@PathVariable("id") Long id) {
        log.debug("REST request to get Uitlaatconstructie : {}", id);
        Optional<Uitlaatconstructie> uitlaatconstructie = uitlaatconstructieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uitlaatconstructie);
    }

    /**
     * {@code DELETE  /uitlaatconstructies/:id} : delete the "id" uitlaatconstructie.
     *
     * @param id the id of the uitlaatconstructie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUitlaatconstructie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Uitlaatconstructie : {}", id);
        uitlaatconstructieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
