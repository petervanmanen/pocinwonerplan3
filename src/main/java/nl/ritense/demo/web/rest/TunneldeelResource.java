package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Tunneldeel;
import nl.ritense.demo.repository.TunneldeelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Tunneldeel}.
 */
@RestController
@RequestMapping("/api/tunneldeels")
@Transactional
public class TunneldeelResource {

    private final Logger log = LoggerFactory.getLogger(TunneldeelResource.class);

    private static final String ENTITY_NAME = "tunneldeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TunneldeelRepository tunneldeelRepository;

    public TunneldeelResource(TunneldeelRepository tunneldeelRepository) {
        this.tunneldeelRepository = tunneldeelRepository;
    }

    /**
     * {@code POST  /tunneldeels} : Create a new tunneldeel.
     *
     * @param tunneldeel the tunneldeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tunneldeel, or with status {@code 400 (Bad Request)} if the tunneldeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tunneldeel> createTunneldeel(@RequestBody Tunneldeel tunneldeel) throws URISyntaxException {
        log.debug("REST request to save Tunneldeel : {}", tunneldeel);
        if (tunneldeel.getId() != null) {
            throw new BadRequestAlertException("A new tunneldeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tunneldeel = tunneldeelRepository.save(tunneldeel);
        return ResponseEntity.created(new URI("/api/tunneldeels/" + tunneldeel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, tunneldeel.getId().toString()))
            .body(tunneldeel);
    }

    /**
     * {@code PUT  /tunneldeels/:id} : Updates an existing tunneldeel.
     *
     * @param id the id of the tunneldeel to save.
     * @param tunneldeel the tunneldeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tunneldeel,
     * or with status {@code 400 (Bad Request)} if the tunneldeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tunneldeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tunneldeel> updateTunneldeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tunneldeel tunneldeel
    ) throws URISyntaxException {
        log.debug("REST request to update Tunneldeel : {}, {}", id, tunneldeel);
        if (tunneldeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tunneldeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tunneldeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tunneldeel = tunneldeelRepository.save(tunneldeel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tunneldeel.getId().toString()))
            .body(tunneldeel);
    }

    /**
     * {@code PATCH  /tunneldeels/:id} : Partial updates given fields of an existing tunneldeel, field will ignore if it is null
     *
     * @param id the id of the tunneldeel to save.
     * @param tunneldeel the tunneldeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tunneldeel,
     * or with status {@code 400 (Bad Request)} if the tunneldeel is not valid,
     * or with status {@code 404 (Not Found)} if the tunneldeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the tunneldeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tunneldeel> partialUpdateTunneldeel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tunneldeel tunneldeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tunneldeel partially : {}, {}", id, tunneldeel);
        if (tunneldeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tunneldeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tunneldeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tunneldeel> result = tunneldeelRepository
            .findById(tunneldeel.getId())
            .map(existingTunneldeel -> {
                if (tunneldeel.getDatumbegingeldigheidtunneldeel() != null) {
                    existingTunneldeel.setDatumbegingeldigheidtunneldeel(tunneldeel.getDatumbegingeldigheidtunneldeel());
                }
                if (tunneldeel.getDatumeindegeldigheidtunneldeel() != null) {
                    existingTunneldeel.setDatumeindegeldigheidtunneldeel(tunneldeel.getDatumeindegeldigheidtunneldeel());
                }
                if (tunneldeel.getGeometrietunneldeel() != null) {
                    existingTunneldeel.setGeometrietunneldeel(tunneldeel.getGeometrietunneldeel());
                }
                if (tunneldeel.getIdentificatietunneldeel() != null) {
                    existingTunneldeel.setIdentificatietunneldeel(tunneldeel.getIdentificatietunneldeel());
                }
                if (tunneldeel.getLod0geometrietunneldeel() != null) {
                    existingTunneldeel.setLod0geometrietunneldeel(tunneldeel.getLod0geometrietunneldeel());
                }
                if (tunneldeel.getRelatievehoogteliggingtunneldeel() != null) {
                    existingTunneldeel.setRelatievehoogteliggingtunneldeel(tunneldeel.getRelatievehoogteliggingtunneldeel());
                }
                if (tunneldeel.getStatustunneldeel() != null) {
                    existingTunneldeel.setStatustunneldeel(tunneldeel.getStatustunneldeel());
                }

                return existingTunneldeel;
            })
            .map(tunneldeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tunneldeel.getId().toString())
        );
    }

    /**
     * {@code GET  /tunneldeels} : get all the tunneldeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tunneldeels in body.
     */
    @GetMapping("")
    public List<Tunneldeel> getAllTunneldeels() {
        log.debug("REST request to get all Tunneldeels");
        return tunneldeelRepository.findAll();
    }

    /**
     * {@code GET  /tunneldeels/:id} : get the "id" tunneldeel.
     *
     * @param id the id of the tunneldeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tunneldeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tunneldeel> getTunneldeel(@PathVariable("id") Long id) {
        log.debug("REST request to get Tunneldeel : {}", id);
        Optional<Tunneldeel> tunneldeel = tunneldeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tunneldeel);
    }

    /**
     * {@code DELETE  /tunneldeels/:id} : delete the "id" tunneldeel.
     *
     * @param id the id of the tunneldeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTunneldeel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tunneldeel : {}", id);
        tunneldeelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
