package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overigescheiding;
import nl.ritense.demo.repository.OverigescheidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overigescheiding}.
 */
@RestController
@RequestMapping("/api/overigescheidings")
@Transactional
public class OverigescheidingResource {

    private final Logger log = LoggerFactory.getLogger(OverigescheidingResource.class);

    private static final String ENTITY_NAME = "overigescheiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverigescheidingRepository overigescheidingRepository;

    public OverigescheidingResource(OverigescheidingRepository overigescheidingRepository) {
        this.overigescheidingRepository = overigescheidingRepository;
    }

    /**
     * {@code POST  /overigescheidings} : Create a new overigescheiding.
     *
     * @param overigescheiding the overigescheiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overigescheiding, or with status {@code 400 (Bad Request)} if the overigescheiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overigescheiding> createOverigescheiding(@RequestBody Overigescheiding overigescheiding)
        throws URISyntaxException {
        log.debug("REST request to save Overigescheiding : {}", overigescheiding);
        if (overigescheiding.getId() != null) {
            throw new BadRequestAlertException("A new overigescheiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        overigescheiding = overigescheidingRepository.save(overigescheiding);
        return ResponseEntity.created(new URI("/api/overigescheidings/" + overigescheiding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, overigescheiding.getId().toString()))
            .body(overigescheiding);
    }

    /**
     * {@code PUT  /overigescheidings/:id} : Updates an existing overigescheiding.
     *
     * @param id the id of the overigescheiding to save.
     * @param overigescheiding the overigescheiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overigescheiding,
     * or with status {@code 400 (Bad Request)} if the overigescheiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overigescheiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overigescheiding> updateOverigescheiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overigescheiding overigescheiding
    ) throws URISyntaxException {
        log.debug("REST request to update Overigescheiding : {}, {}", id, overigescheiding);
        if (overigescheiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overigescheiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overigescheidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overigescheiding = overigescheidingRepository.save(overigescheiding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overigescheiding.getId().toString()))
            .body(overigescheiding);
    }

    /**
     * {@code PATCH  /overigescheidings/:id} : Partial updates given fields of an existing overigescheiding, field will ignore if it is null
     *
     * @param id the id of the overigescheiding to save.
     * @param overigescheiding the overigescheiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overigescheiding,
     * or with status {@code 400 (Bad Request)} if the overigescheiding is not valid,
     * or with status {@code 404 (Not Found)} if the overigescheiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the overigescheiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overigescheiding> partialUpdateOverigescheiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overigescheiding overigescheiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Overigescheiding partially : {}, {}", id, overigescheiding);
        if (overigescheiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overigescheiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overigescheidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overigescheiding> result = overigescheidingRepository
            .findById(overigescheiding.getId())
            .map(existingOverigescheiding -> {
                if (overigescheiding.getDatumbegingeldigheidoverigescheiding() != null) {
                    existingOverigescheiding.setDatumbegingeldigheidoverigescheiding(
                        overigescheiding.getDatumbegingeldigheidoverigescheiding()
                    );
                }
                if (overigescheiding.getDatumeindegeldigheidoverigescheiding() != null) {
                    existingOverigescheiding.setDatumeindegeldigheidoverigescheiding(
                        overigescheiding.getDatumeindegeldigheidoverigescheiding()
                    );
                }
                if (overigescheiding.getGeometrieoverigescheiding() != null) {
                    existingOverigescheiding.setGeometrieoverigescheiding(overigescheiding.getGeometrieoverigescheiding());
                }
                if (overigescheiding.getIdentificatieoverigescheiding() != null) {
                    existingOverigescheiding.setIdentificatieoverigescheiding(overigescheiding.getIdentificatieoverigescheiding());
                }
                if (overigescheiding.getLod0geometrieoverigescheiding() != null) {
                    existingOverigescheiding.setLod0geometrieoverigescheiding(overigescheiding.getLod0geometrieoverigescheiding());
                }
                if (overigescheiding.getLod1geometrieoverigescheiding() != null) {
                    existingOverigescheiding.setLod1geometrieoverigescheiding(overigescheiding.getLod1geometrieoverigescheiding());
                }
                if (overigescheiding.getLod2geometrieoverigescheiding() != null) {
                    existingOverigescheiding.setLod2geometrieoverigescheiding(overigescheiding.getLod2geometrieoverigescheiding());
                }
                if (overigescheiding.getLod3geometrieoverigescheiding() != null) {
                    existingOverigescheiding.setLod3geometrieoverigescheiding(overigescheiding.getLod3geometrieoverigescheiding());
                }
                if (overigescheiding.getRelatievehoogteliggingoverigescheiding() != null) {
                    existingOverigescheiding.setRelatievehoogteliggingoverigescheiding(
                        overigescheiding.getRelatievehoogteliggingoverigescheiding()
                    );
                }
                if (overigescheiding.getStatusoverigescheiding() != null) {
                    existingOverigescheiding.setStatusoverigescheiding(overigescheiding.getStatusoverigescheiding());
                }
                if (overigescheiding.getTypeoverigescheiding() != null) {
                    existingOverigescheiding.setTypeoverigescheiding(overigescheiding.getTypeoverigescheiding());
                }

                return existingOverigescheiding;
            })
            .map(overigescheidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overigescheiding.getId().toString())
        );
    }

    /**
     * {@code GET  /overigescheidings} : get all the overigescheidings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overigescheidings in body.
     */
    @GetMapping("")
    public List<Overigescheiding> getAllOverigescheidings() {
        log.debug("REST request to get all Overigescheidings");
        return overigescheidingRepository.findAll();
    }

    /**
     * {@code GET  /overigescheidings/:id} : get the "id" overigescheiding.
     *
     * @param id the id of the overigescheiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overigescheiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overigescheiding> getOverigescheiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Overigescheiding : {}", id);
        Optional<Overigescheiding> overigescheiding = overigescheidingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overigescheiding);
    }

    /**
     * {@code DELETE  /overigescheidings/:id} : delete the "id" overigescheiding.
     *
     * @param id the id of the overigescheiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverigescheiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overigescheiding : {}", id);
        overigescheidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
