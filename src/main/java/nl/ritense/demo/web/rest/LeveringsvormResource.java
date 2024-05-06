package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Leveringsvorm;
import nl.ritense.demo.repository.LeveringsvormRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Leveringsvorm}.
 */
@RestController
@RequestMapping("/api/leveringsvorms")
@Transactional
public class LeveringsvormResource {

    private final Logger log = LoggerFactory.getLogger(LeveringsvormResource.class);

    private static final String ENTITY_NAME = "leveringsvorm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeveringsvormRepository leveringsvormRepository;

    public LeveringsvormResource(LeveringsvormRepository leveringsvormRepository) {
        this.leveringsvormRepository = leveringsvormRepository;
    }

    /**
     * {@code POST  /leveringsvorms} : Create a new leveringsvorm.
     *
     * @param leveringsvorm the leveringsvorm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leveringsvorm, or with status {@code 400 (Bad Request)} if the leveringsvorm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Leveringsvorm> createLeveringsvorm(@RequestBody Leveringsvorm leveringsvorm) throws URISyntaxException {
        log.debug("REST request to save Leveringsvorm : {}", leveringsvorm);
        if (leveringsvorm.getId() != null) {
            throw new BadRequestAlertException("A new leveringsvorm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        leveringsvorm = leveringsvormRepository.save(leveringsvorm);
        return ResponseEntity.created(new URI("/api/leveringsvorms/" + leveringsvorm.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, leveringsvorm.getId().toString()))
            .body(leveringsvorm);
    }

    /**
     * {@code PUT  /leveringsvorms/:id} : Updates an existing leveringsvorm.
     *
     * @param id the id of the leveringsvorm to save.
     * @param leveringsvorm the leveringsvorm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leveringsvorm,
     * or with status {@code 400 (Bad Request)} if the leveringsvorm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leveringsvorm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Leveringsvorm> updateLeveringsvorm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Leveringsvorm leveringsvorm
    ) throws URISyntaxException {
        log.debug("REST request to update Leveringsvorm : {}, {}", id, leveringsvorm);
        if (leveringsvorm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leveringsvorm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leveringsvormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        leveringsvorm = leveringsvormRepository.save(leveringsvorm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leveringsvorm.getId().toString()))
            .body(leveringsvorm);
    }

    /**
     * {@code PATCH  /leveringsvorms/:id} : Partial updates given fields of an existing leveringsvorm, field will ignore if it is null
     *
     * @param id the id of the leveringsvorm to save.
     * @param leveringsvorm the leveringsvorm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leveringsvorm,
     * or with status {@code 400 (Bad Request)} if the leveringsvorm is not valid,
     * or with status {@code 404 (Not Found)} if the leveringsvorm is not found,
     * or with status {@code 500 (Internal Server Error)} if the leveringsvorm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Leveringsvorm> partialUpdateLeveringsvorm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Leveringsvorm leveringsvorm
    ) throws URISyntaxException {
        log.debug("REST request to partial update Leveringsvorm partially : {}, {}", id, leveringsvorm);
        if (leveringsvorm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leveringsvorm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leveringsvormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Leveringsvorm> result = leveringsvormRepository
            .findById(leveringsvorm.getId())
            .map(existingLeveringsvorm -> {
                if (leveringsvorm.getLeveringsvormcode() != null) {
                    existingLeveringsvorm.setLeveringsvormcode(leveringsvorm.getLeveringsvormcode());
                }
                if (leveringsvorm.getNaam() != null) {
                    existingLeveringsvorm.setNaam(leveringsvorm.getNaam());
                }
                if (leveringsvorm.getWet() != null) {
                    existingLeveringsvorm.setWet(leveringsvorm.getWet());
                }

                return existingLeveringsvorm;
            })
            .map(leveringsvormRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leveringsvorm.getId().toString())
        );
    }

    /**
     * {@code GET  /leveringsvorms} : get all the leveringsvorms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leveringsvorms in body.
     */
    @GetMapping("")
    public List<Leveringsvorm> getAllLeveringsvorms() {
        log.debug("REST request to get all Leveringsvorms");
        return leveringsvormRepository.findAll();
    }

    /**
     * {@code GET  /leveringsvorms/:id} : get the "id" leveringsvorm.
     *
     * @param id the id of the leveringsvorm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leveringsvorm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leveringsvorm> getLeveringsvorm(@PathVariable("id") Long id) {
        log.debug("REST request to get Leveringsvorm : {}", id);
        Optional<Leveringsvorm> leveringsvorm = leveringsvormRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(leveringsvorm);
    }

    /**
     * {@code DELETE  /leveringsvorms/:id} : delete the "id" leveringsvorm.
     *
     * @param id the id of the leveringsvorm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeveringsvorm(@PathVariable("id") Long id) {
        log.debug("REST request to delete Leveringsvorm : {}", id);
        leveringsvormRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
