package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Overigbouwwerk;
import nl.ritense.demo.repository.OverigbouwwerkRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Overigbouwwerk}.
 */
@RestController
@RequestMapping("/api/overigbouwwerks")
@Transactional
public class OverigbouwwerkResource {

    private final Logger log = LoggerFactory.getLogger(OverigbouwwerkResource.class);

    private static final String ENTITY_NAME = "overigbouwwerk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverigbouwwerkRepository overigbouwwerkRepository;

    public OverigbouwwerkResource(OverigbouwwerkRepository overigbouwwerkRepository) {
        this.overigbouwwerkRepository = overigbouwwerkRepository;
    }

    /**
     * {@code POST  /overigbouwwerks} : Create a new overigbouwwerk.
     *
     * @param overigbouwwerk the overigbouwwerk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overigbouwwerk, or with status {@code 400 (Bad Request)} if the overigbouwwerk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Overigbouwwerk> createOverigbouwwerk(@RequestBody Overigbouwwerk overigbouwwerk) throws URISyntaxException {
        log.debug("REST request to save Overigbouwwerk : {}", overigbouwwerk);
        if (overigbouwwerk.getId() != null) {
            throw new BadRequestAlertException("A new overigbouwwerk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        overigbouwwerk = overigbouwwerkRepository.save(overigbouwwerk);
        return ResponseEntity.created(new URI("/api/overigbouwwerks/" + overigbouwwerk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, overigbouwwerk.getId().toString()))
            .body(overigbouwwerk);
    }

    /**
     * {@code PUT  /overigbouwwerks/:id} : Updates an existing overigbouwwerk.
     *
     * @param id the id of the overigbouwwerk to save.
     * @param overigbouwwerk the overigbouwwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overigbouwwerk,
     * or with status {@code 400 (Bad Request)} if the overigbouwwerk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overigbouwwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Overigbouwwerk> updateOverigbouwwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overigbouwwerk overigbouwwerk
    ) throws URISyntaxException {
        log.debug("REST request to update Overigbouwwerk : {}, {}", id, overigbouwwerk);
        if (overigbouwwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overigbouwwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overigbouwwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        overigbouwwerk = overigbouwwerkRepository.save(overigbouwwerk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overigbouwwerk.getId().toString()))
            .body(overigbouwwerk);
    }

    /**
     * {@code PATCH  /overigbouwwerks/:id} : Partial updates given fields of an existing overigbouwwerk, field will ignore if it is null
     *
     * @param id the id of the overigbouwwerk to save.
     * @param overigbouwwerk the overigbouwwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overigbouwwerk,
     * or with status {@code 400 (Bad Request)} if the overigbouwwerk is not valid,
     * or with status {@code 404 (Not Found)} if the overigbouwwerk is not found,
     * or with status {@code 500 (Internal Server Error)} if the overigbouwwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Overigbouwwerk> partialUpdateOverigbouwwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Overigbouwwerk overigbouwwerk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Overigbouwwerk partially : {}, {}", id, overigbouwwerk);
        if (overigbouwwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overigbouwwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overigbouwwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Overigbouwwerk> result = overigbouwwerkRepository
            .findById(overigbouwwerk.getId())
            .map(existingOverigbouwwerk -> {
                if (overigbouwwerk.getDatumbegingeldigheidoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setDatumbegingeldigheidoverigbouwwerk(overigbouwwerk.getDatumbegingeldigheidoverigbouwwerk());
                }
                if (overigbouwwerk.getDatumeindegeldigheidoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setDatumeindegeldigheidoverigbouwwerk(overigbouwwerk.getDatumeindegeldigheidoverigbouwwerk());
                }
                if (overigbouwwerk.getGeometrieoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setGeometrieoverigbouwwerk(overigbouwwerk.getGeometrieoverigbouwwerk());
                }
                if (overigbouwwerk.getIdentificatieoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setIdentificatieoverigbouwwerk(overigbouwwerk.getIdentificatieoverigbouwwerk());
                }
                if (overigbouwwerk.getLod0geometrieoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setLod0geometrieoverigbouwwerk(overigbouwwerk.getLod0geometrieoverigbouwwerk());
                }
                if (overigbouwwerk.getLod1geometrieoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setLod1geometrieoverigbouwwerk(overigbouwwerk.getLod1geometrieoverigbouwwerk());
                }
                if (overigbouwwerk.getLod2geometrieoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setLod2geometrieoverigbouwwerk(overigbouwwerk.getLod2geometrieoverigbouwwerk());
                }
                if (overigbouwwerk.getLod3geometrieoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setLod3geometrieoverigbouwwerk(overigbouwwerk.getLod3geometrieoverigbouwwerk());
                }
                if (overigbouwwerk.getRelatievehoogteliggingoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setRelatievehoogteliggingoverigbouwwerk(
                        overigbouwwerk.getRelatievehoogteliggingoverigbouwwerk()
                    );
                }
                if (overigbouwwerk.getStatusoverigbouwwerk() != null) {
                    existingOverigbouwwerk.setStatusoverigbouwwerk(overigbouwwerk.getStatusoverigbouwwerk());
                }

                return existingOverigbouwwerk;
            })
            .map(overigbouwwerkRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, overigbouwwerk.getId().toString())
        );
    }

    /**
     * {@code GET  /overigbouwwerks} : get all the overigbouwwerks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overigbouwwerks in body.
     */
    @GetMapping("")
    public List<Overigbouwwerk> getAllOverigbouwwerks() {
        log.debug("REST request to get all Overigbouwwerks");
        return overigbouwwerkRepository.findAll();
    }

    /**
     * {@code GET  /overigbouwwerks/:id} : get the "id" overigbouwwerk.
     *
     * @param id the id of the overigbouwwerk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overigbouwwerk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Overigbouwwerk> getOverigbouwwerk(@PathVariable("id") Long id) {
        log.debug("REST request to get Overigbouwwerk : {}", id);
        Optional<Overigbouwwerk> overigbouwwerk = overigbouwwerkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(overigbouwwerk);
    }

    /**
     * {@code DELETE  /overigbouwwerks/:id} : delete the "id" overigbouwwerk.
     *
     * @param id the id of the overigbouwwerk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOverigbouwwerk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Overigbouwwerk : {}", id);
        overigbouwwerkRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
