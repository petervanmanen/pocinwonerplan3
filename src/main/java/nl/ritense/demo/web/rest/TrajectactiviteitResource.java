package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Trajectactiviteit;
import nl.ritense.demo.repository.TrajectactiviteitRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Trajectactiviteit}.
 */
@RestController
@RequestMapping("/api/trajectactiviteits")
@Transactional
public class TrajectactiviteitResource {

    private final Logger log = LoggerFactory.getLogger(TrajectactiviteitResource.class);

    private static final String ENTITY_NAME = "trajectactiviteit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrajectactiviteitRepository trajectactiviteitRepository;

    public TrajectactiviteitResource(TrajectactiviteitRepository trajectactiviteitRepository) {
        this.trajectactiviteitRepository = trajectactiviteitRepository;
    }

    /**
     * {@code POST  /trajectactiviteits} : Create a new trajectactiviteit.
     *
     * @param trajectactiviteit the trajectactiviteit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trajectactiviteit, or with status {@code 400 (Bad Request)} if the trajectactiviteit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Trajectactiviteit> createTrajectactiviteit(@Valid @RequestBody Trajectactiviteit trajectactiviteit)
        throws URISyntaxException {
        log.debug("REST request to save Trajectactiviteit : {}", trajectactiviteit);
        if (trajectactiviteit.getId() != null) {
            throw new BadRequestAlertException("A new trajectactiviteit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        trajectactiviteit = trajectactiviteitRepository.save(trajectactiviteit);
        return ResponseEntity.created(new URI("/api/trajectactiviteits/" + trajectactiviteit.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, trajectactiviteit.getId().toString()))
            .body(trajectactiviteit);
    }

    /**
     * {@code PUT  /trajectactiviteits/:id} : Updates an existing trajectactiviteit.
     *
     * @param id the id of the trajectactiviteit to save.
     * @param trajectactiviteit the trajectactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trajectactiviteit,
     * or with status {@code 400 (Bad Request)} if the trajectactiviteit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trajectactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Trajectactiviteit> updateTrajectactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Trajectactiviteit trajectactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to update Trajectactiviteit : {}, {}", id, trajectactiviteit);
        if (trajectactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trajectactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trajectactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        trajectactiviteit = trajectactiviteitRepository.save(trajectactiviteit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trajectactiviteit.getId().toString()))
            .body(trajectactiviteit);
    }

    /**
     * {@code PATCH  /trajectactiviteits/:id} : Partial updates given fields of an existing trajectactiviteit, field will ignore if it is null
     *
     * @param id the id of the trajectactiviteit to save.
     * @param trajectactiviteit the trajectactiviteit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trajectactiviteit,
     * or with status {@code 400 (Bad Request)} if the trajectactiviteit is not valid,
     * or with status {@code 404 (Not Found)} if the trajectactiviteit is not found,
     * or with status {@code 500 (Internal Server Error)} if the trajectactiviteit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Trajectactiviteit> partialUpdateTrajectactiviteit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Trajectactiviteit trajectactiviteit
    ) throws URISyntaxException {
        log.debug("REST request to partial update Trajectactiviteit partially : {}, {}", id, trajectactiviteit);
        if (trajectactiviteit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trajectactiviteit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trajectactiviteitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Trajectactiviteit> result = trajectactiviteitRepository
            .findById(trajectactiviteit.getId())
            .map(existingTrajectactiviteit -> {
                if (trajectactiviteit.getContract() != null) {
                    existingTrajectactiviteit.setContract(trajectactiviteit.getContract());
                }
                if (trajectactiviteit.getCrediteur() != null) {
                    existingTrajectactiviteit.setCrediteur(trajectactiviteit.getCrediteur());
                }
                if (trajectactiviteit.getDatumbegin() != null) {
                    existingTrajectactiviteit.setDatumbegin(trajectactiviteit.getDatumbegin());
                }
                if (trajectactiviteit.getDatumeinde() != null) {
                    existingTrajectactiviteit.setDatumeinde(trajectactiviteit.getDatumeinde());
                }
                if (trajectactiviteit.getDatumstatus() != null) {
                    existingTrajectactiviteit.setDatumstatus(trajectactiviteit.getDatumstatus());
                }
                if (trajectactiviteit.getKinderopvang() != null) {
                    existingTrajectactiviteit.setKinderopvang(trajectactiviteit.getKinderopvang());
                }
                if (trajectactiviteit.getStatus() != null) {
                    existingTrajectactiviteit.setStatus(trajectactiviteit.getStatus());
                }
                if (trajectactiviteit.getSuccesvol() != null) {
                    existingTrajectactiviteit.setSuccesvol(trajectactiviteit.getSuccesvol());
                }

                return existingTrajectactiviteit;
            })
            .map(trajectactiviteitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trajectactiviteit.getId().toString())
        );
    }

    /**
     * {@code GET  /trajectactiviteits} : get all the trajectactiviteits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trajectactiviteits in body.
     */
    @GetMapping("")
    public List<Trajectactiviteit> getAllTrajectactiviteits() {
        log.debug("REST request to get all Trajectactiviteits");
        return trajectactiviteitRepository.findAll();
    }

    /**
     * {@code GET  /trajectactiviteits/:id} : get the "id" trajectactiviteit.
     *
     * @param id the id of the trajectactiviteit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trajectactiviteit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Trajectactiviteit> getTrajectactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to get Trajectactiviteit : {}", id);
        Optional<Trajectactiviteit> trajectactiviteit = trajectactiviteitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trajectactiviteit);
    }

    /**
     * {@code DELETE  /trajectactiviteits/:id} : delete the "id" trajectactiviteit.
     *
     * @param id the id of the trajectactiviteit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajectactiviteit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Trajectactiviteit : {}", id);
        trajectactiviteitRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
