package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Activa;
import nl.ritense.demo.repository.ActivaRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Activa}.
 */
@RestController
@RequestMapping("/api/activas")
@Transactional
public class ActivaResource {

    private final Logger log = LoggerFactory.getLogger(ActivaResource.class);

    private static final String ENTITY_NAME = "activa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivaRepository activaRepository;

    public ActivaResource(ActivaRepository activaRepository) {
        this.activaRepository = activaRepository;
    }

    /**
     * {@code POST  /activas} : Create a new activa.
     *
     * @param activa the activa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activa, or with status {@code 400 (Bad Request)} if the activa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Activa> createActiva(@RequestBody Activa activa) throws URISyntaxException {
        log.debug("REST request to save Activa : {}", activa);
        if (activa.getId() != null) {
            throw new BadRequestAlertException("A new activa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        activa = activaRepository.save(activa);
        return ResponseEntity.created(new URI("/api/activas/" + activa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, activa.getId().toString()))
            .body(activa);
    }

    /**
     * {@code PUT  /activas/:id} : Updates an existing activa.
     *
     * @param id the id of the activa to save.
     * @param activa the activa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activa,
     * or with status {@code 400 (Bad Request)} if the activa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Activa> updateActiva(@PathVariable(value = "id", required = false) final Long id, @RequestBody Activa activa)
        throws URISyntaxException {
        log.debug("REST request to update Activa : {}, {}", id, activa);
        if (activa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        activa = activaRepository.save(activa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activa.getId().toString()))
            .body(activa);
    }

    /**
     * {@code PATCH  /activas/:id} : Partial updates given fields of an existing activa, field will ignore if it is null
     *
     * @param id the id of the activa to save.
     * @param activa the activa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activa,
     * or with status {@code 400 (Bad Request)} if the activa is not valid,
     * or with status {@code 404 (Not Found)} if the activa is not found,
     * or with status {@code 500 (Internal Server Error)} if the activa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Activa> partialUpdateActiva(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Activa activa
    ) throws URISyntaxException {
        log.debug("REST request to partial update Activa partially : {}, {}", id, activa);
        if (activa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Activa> result = activaRepository
            .findById(activa.getId())
            .map(existingActiva -> {
                if (activa.getNaam() != null) {
                    existingActiva.setNaam(activa.getNaam());
                }
                if (activa.getOmschrijving() != null) {
                    existingActiva.setOmschrijving(activa.getOmschrijving());
                }

                return existingActiva;
            })
            .map(activaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activa.getId().toString())
        );
    }

    /**
     * {@code GET  /activas} : get all the activas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activas in body.
     */
    @GetMapping("")
    public List<Activa> getAllActivas() {
        log.debug("REST request to get all Activas");
        return activaRepository.findAll();
    }

    /**
     * {@code GET  /activas/:id} : get the "id" activa.
     *
     * @param id the id of the activa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Activa> getActiva(@PathVariable("id") Long id) {
        log.debug("REST request to get Activa : {}", id);
        Optional<Activa> activa = activaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(activa);
    }

    /**
     * {@code DELETE  /activas/:id} : delete the "id" activa.
     *
     * @param id the id of the activa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActiva(@PathVariable("id") Long id) {
        log.debug("REST request to delete Activa : {}", id);
        activaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
