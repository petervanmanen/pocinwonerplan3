package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Flyover;
import nl.ritense.demo.repository.FlyoverRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Flyover}.
 */
@RestController
@RequestMapping("/api/flyovers")
@Transactional
public class FlyoverResource {

    private final Logger log = LoggerFactory.getLogger(FlyoverResource.class);

    private static final String ENTITY_NAME = "flyover";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlyoverRepository flyoverRepository;

    public FlyoverResource(FlyoverRepository flyoverRepository) {
        this.flyoverRepository = flyoverRepository;
    }

    /**
     * {@code POST  /flyovers} : Create a new flyover.
     *
     * @param flyover the flyover to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flyover, or with status {@code 400 (Bad Request)} if the flyover has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Flyover> createFlyover(@Valid @RequestBody Flyover flyover) throws URISyntaxException {
        log.debug("REST request to save Flyover : {}", flyover);
        if (flyover.getId() != null) {
            throw new BadRequestAlertException("A new flyover cannot already have an ID", ENTITY_NAME, "idexists");
        }
        flyover = flyoverRepository.save(flyover);
        return ResponseEntity.created(new URI("/api/flyovers/" + flyover.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, flyover.getId().toString()))
            .body(flyover);
    }

    /**
     * {@code PUT  /flyovers/:id} : Updates an existing flyover.
     *
     * @param id the id of the flyover to save.
     * @param flyover the flyover to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flyover,
     * or with status {@code 400 (Bad Request)} if the flyover is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flyover couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Flyover> updateFlyover(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Flyover flyover
    ) throws URISyntaxException {
        log.debug("REST request to update Flyover : {}, {}", id, flyover);
        if (flyover.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flyover.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flyoverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        flyover = flyoverRepository.save(flyover);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flyover.getId().toString()))
            .body(flyover);
    }

    /**
     * {@code PATCH  /flyovers/:id} : Partial updates given fields of an existing flyover, field will ignore if it is null
     *
     * @param id the id of the flyover to save.
     * @param flyover the flyover to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flyover,
     * or with status {@code 400 (Bad Request)} if the flyover is not valid,
     * or with status {@code 404 (Not Found)} if the flyover is not found,
     * or with status {@code 500 (Internal Server Error)} if the flyover couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Flyover> partialUpdateFlyover(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Flyover flyover
    ) throws URISyntaxException {
        log.debug("REST request to partial update Flyover partially : {}, {}", id, flyover);
        if (flyover.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flyover.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flyoverRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Flyover> result = flyoverRepository
            .findById(flyover.getId())
            .map(existingFlyover -> {
                if (flyover.getAantaloverspanningen() != null) {
                    existingFlyover.setAantaloverspanningen(flyover.getAantaloverspanningen());
                }
                if (flyover.getBelastingklassenieuw() != null) {
                    existingFlyover.setBelastingklassenieuw(flyover.getBelastingklassenieuw());
                }
                if (flyover.getBelastingklasseoud() != null) {
                    existingFlyover.setBelastingklasseoud(flyover.getBelastingklasseoud());
                }
                if (flyover.getDraagvermogen() != null) {
                    existingFlyover.setDraagvermogen(flyover.getDraagvermogen());
                }
                if (flyover.getMaximaaltoelaatbaarvoertuiggewicht() != null) {
                    existingFlyover.setMaximaaltoelaatbaarvoertuiggewicht(flyover.getMaximaaltoelaatbaarvoertuiggewicht());
                }
                if (flyover.getMaximaleasbelasting() != null) {
                    existingFlyover.setMaximaleasbelasting(flyover.getMaximaleasbelasting());
                }
                if (flyover.getMaximaleoverspanning() != null) {
                    existingFlyover.setMaximaleoverspanning(flyover.getMaximaleoverspanning());
                }
                if (flyover.getOverbruggingsobjectdoorrijopening() != null) {
                    existingFlyover.setOverbruggingsobjectdoorrijopening(flyover.getOverbruggingsobjectdoorrijopening());
                }
                if (flyover.getType() != null) {
                    existingFlyover.setType(flyover.getType());
                }
                if (flyover.getZwaarstevoertuig() != null) {
                    existingFlyover.setZwaarstevoertuig(flyover.getZwaarstevoertuig());
                }

                return existingFlyover;
            })
            .map(flyoverRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, flyover.getId().toString())
        );
    }

    /**
     * {@code GET  /flyovers} : get all the flyovers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flyovers in body.
     */
    @GetMapping("")
    public List<Flyover> getAllFlyovers() {
        log.debug("REST request to get all Flyovers");
        return flyoverRepository.findAll();
    }

    /**
     * {@code GET  /flyovers/:id} : get the "id" flyover.
     *
     * @param id the id of the flyover to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flyover, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Flyover> getFlyover(@PathVariable("id") Long id) {
        log.debug("REST request to get Flyover : {}", id);
        Optional<Flyover> flyover = flyoverRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(flyover);
    }

    /**
     * {@code DELETE  /flyovers/:id} : delete the "id" flyover.
     *
     * @param id the id of the flyover to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlyover(@PathVariable("id") Long id) {
        log.debug("REST request to delete Flyover : {}", id);
        flyoverRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
