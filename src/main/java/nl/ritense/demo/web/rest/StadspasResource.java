package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Stadspas;
import nl.ritense.demo.repository.StadspasRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Stadspas}.
 */
@RestController
@RequestMapping("/api/stadspas")
@Transactional
public class StadspasResource {

    private final Logger log = LoggerFactory.getLogger(StadspasResource.class);

    private static final String ENTITY_NAME = "stadspas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StadspasRepository stadspasRepository;

    public StadspasResource(StadspasRepository stadspasRepository) {
        this.stadspasRepository = stadspasRepository;
    }

    /**
     * {@code POST  /stadspas} : Create a new stadspas.
     *
     * @param stadspas the stadspas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stadspas, or with status {@code 400 (Bad Request)} if the stadspas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Stadspas> createStadspas(@RequestBody Stadspas stadspas) throws URISyntaxException {
        log.debug("REST request to save Stadspas : {}", stadspas);
        if (stadspas.getId() != null) {
            throw new BadRequestAlertException("A new stadspas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        stadspas = stadspasRepository.save(stadspas);
        return ResponseEntity.created(new URI("/api/stadspas/" + stadspas.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, stadspas.getId().toString()))
            .body(stadspas);
    }

    /**
     * {@code PUT  /stadspas/:id} : Updates an existing stadspas.
     *
     * @param id the id of the stadspas to save.
     * @param stadspas the stadspas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stadspas,
     * or with status {@code 400 (Bad Request)} if the stadspas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stadspas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Stadspas> updateStadspas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Stadspas stadspas
    ) throws URISyntaxException {
        log.debug("REST request to update Stadspas : {}, {}", id, stadspas);
        if (stadspas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stadspas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stadspasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        stadspas = stadspasRepository.save(stadspas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stadspas.getId().toString()))
            .body(stadspas);
    }

    /**
     * {@code PATCH  /stadspas/:id} : Partial updates given fields of an existing stadspas, field will ignore if it is null
     *
     * @param id the id of the stadspas to save.
     * @param stadspas the stadspas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stadspas,
     * or with status {@code 400 (Bad Request)} if the stadspas is not valid,
     * or with status {@code 404 (Not Found)} if the stadspas is not found,
     * or with status {@code 500 (Internal Server Error)} if the stadspas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Stadspas> partialUpdateStadspas(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Stadspas stadspas
    ) throws URISyntaxException {
        log.debug("REST request to partial update Stadspas partially : {}, {}", id, stadspas);
        if (stadspas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stadspas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stadspasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Stadspas> result = stadspasRepository
            .findById(stadspas.getId())
            .map(existingStadspas -> {
                if (stadspas.getDatumeinde() != null) {
                    existingStadspas.setDatumeinde(stadspas.getDatumeinde());
                }
                if (stadspas.getDatumstart() != null) {
                    existingStadspas.setDatumstart(stadspas.getDatumstart());
                }

                return existingStadspas;
            })
            .map(stadspasRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stadspas.getId().toString())
        );
    }

    /**
     * {@code GET  /stadspas} : get all the stadspas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stadspas in body.
     */
    @GetMapping("")
    public List<Stadspas> getAllStadspas() {
        log.debug("REST request to get all Stadspas");
        return stadspasRepository.findAll();
    }

    /**
     * {@code GET  /stadspas/:id} : get the "id" stadspas.
     *
     * @param id the id of the stadspas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stadspas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Stadspas> getStadspas(@PathVariable("id") Long id) {
        log.debug("REST request to get Stadspas : {}", id);
        Optional<Stadspas> stadspas = stadspasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stadspas);
    }

    /**
     * {@code DELETE  /stadspas/:id} : delete the "id" stadspas.
     *
     * @param id the id of the stadspas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStadspas(@PathVariable("id") Long id) {
        log.debug("REST request to delete Stadspas : {}", id);
        stadspasRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
