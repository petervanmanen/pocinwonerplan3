package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Gebouw;
import nl.ritense.demo.repository.GebouwRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Gebouw}.
 */
@RestController
@RequestMapping("/api/gebouws")
@Transactional
public class GebouwResource {

    private final Logger log = LoggerFactory.getLogger(GebouwResource.class);

    private static final String ENTITY_NAME = "gebouw";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GebouwRepository gebouwRepository;

    public GebouwResource(GebouwRepository gebouwRepository) {
        this.gebouwRepository = gebouwRepository;
    }

    /**
     * {@code POST  /gebouws} : Create a new gebouw.
     *
     * @param gebouw the gebouw to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gebouw, or with status {@code 400 (Bad Request)} if the gebouw has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Gebouw> createGebouw(@Valid @RequestBody Gebouw gebouw) throws URISyntaxException {
        log.debug("REST request to save Gebouw : {}", gebouw);
        if (gebouw.getId() != null) {
            throw new BadRequestAlertException("A new gebouw cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gebouw = gebouwRepository.save(gebouw);
        return ResponseEntity.created(new URI("/api/gebouws/" + gebouw.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, gebouw.getId().toString()))
            .body(gebouw);
    }

    /**
     * {@code PUT  /gebouws/:id} : Updates an existing gebouw.
     *
     * @param id the id of the gebouw to save.
     * @param gebouw the gebouw to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebouw,
     * or with status {@code 400 (Bad Request)} if the gebouw is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gebouw couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gebouw> updateGebouw(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Gebouw gebouw
    ) throws URISyntaxException {
        log.debug("REST request to update Gebouw : {}, {}", id, gebouw);
        if (gebouw.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebouw.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebouwRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gebouw = gebouwRepository.save(gebouw);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebouw.getId().toString()))
            .body(gebouw);
    }

    /**
     * {@code PATCH  /gebouws/:id} : Partial updates given fields of an existing gebouw, field will ignore if it is null
     *
     * @param id the id of the gebouw to save.
     * @param gebouw the gebouw to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gebouw,
     * or with status {@code 400 (Bad Request)} if the gebouw is not valid,
     * or with status {@code 404 (Not Found)} if the gebouw is not found,
     * or with status {@code 500 (Internal Server Error)} if the gebouw couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gebouw> partialUpdateGebouw(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Gebouw gebouw
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gebouw partially : {}, {}", id, gebouw);
        if (gebouw.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gebouw.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gebouwRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gebouw> result = gebouwRepository
            .findById(gebouw.getId())
            .map(existingGebouw -> {
                if (gebouw.getAantal() != null) {
                    existingGebouw.setAantal(gebouw.getAantal());
                }
                if (gebouw.getAantaladressen() != null) {
                    existingGebouw.setAantaladressen(gebouw.getAantaladressen());
                }
                if (gebouw.getAantalkamers() != null) {
                    existingGebouw.setAantalkamers(gebouw.getAantalkamers());
                }
                if (gebouw.getAardgasloos() != null) {
                    existingGebouw.setAardgasloos(gebouw.getAardgasloos());
                }
                if (gebouw.getDuurzaam() != null) {
                    existingGebouw.setDuurzaam(gebouw.getDuurzaam());
                }
                if (gebouw.getEnergielabel() != null) {
                    existingGebouw.setEnergielabel(gebouw.getEnergielabel());
                }
                if (gebouw.getNatuurinclusief() != null) {
                    existingGebouw.setNatuurinclusief(gebouw.getNatuurinclusief());
                }
                if (gebouw.getOppervlakte() != null) {
                    existingGebouw.setOppervlakte(gebouw.getOppervlakte());
                }
                if (gebouw.getRegenwater() != null) {
                    existingGebouw.setRegenwater(gebouw.getRegenwater());
                }

                return existingGebouw;
            })
            .map(gebouwRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gebouw.getId().toString())
        );
    }

    /**
     * {@code GET  /gebouws} : get all the gebouws.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gebouws in body.
     */
    @GetMapping("")
    public List<Gebouw> getAllGebouws() {
        log.debug("REST request to get all Gebouws");
        return gebouwRepository.findAll();
    }

    /**
     * {@code GET  /gebouws/:id} : get the "id" gebouw.
     *
     * @param id the id of the gebouw to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gebouw, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gebouw> getGebouw(@PathVariable("id") Long id) {
        log.debug("REST request to get Gebouw : {}", id);
        Optional<Gebouw> gebouw = gebouwRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gebouw);
    }

    /**
     * {@code DELETE  /gebouws/:id} : delete the "id" gebouw.
     *
     * @param id the id of the gebouw to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebouw(@PathVariable("id") Long id) {
        log.debug("REST request to delete Gebouw : {}", id);
        gebouwRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
