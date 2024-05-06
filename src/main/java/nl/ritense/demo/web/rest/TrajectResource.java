package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Traject;
import nl.ritense.demo.repository.TrajectRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Traject}.
 */
@RestController
@RequestMapping("/api/trajects")
@Transactional
public class TrajectResource {

    private final Logger log = LoggerFactory.getLogger(TrajectResource.class);

    private static final String ENTITY_NAME = "traject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrajectRepository trajectRepository;

    public TrajectResource(TrajectRepository trajectRepository) {
        this.trajectRepository = trajectRepository;
    }

    /**
     * {@code POST  /trajects} : Create a new traject.
     *
     * @param traject the traject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new traject, or with status {@code 400 (Bad Request)} if the traject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Traject> createTraject(@Valid @RequestBody Traject traject) throws URISyntaxException {
        log.debug("REST request to save Traject : {}", traject);
        if (traject.getId() != null) {
            throw new BadRequestAlertException("A new traject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        traject = trajectRepository.save(traject);
        return ResponseEntity.created(new URI("/api/trajects/" + traject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, traject.getId().toString()))
            .body(traject);
    }

    /**
     * {@code PUT  /trajects/:id} : Updates an existing traject.
     *
     * @param id the id of the traject to save.
     * @param traject the traject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traject,
     * or with status {@code 400 (Bad Request)} if the traject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the traject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Traject> updateTraject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Traject traject
    ) throws URISyntaxException {
        log.debug("REST request to update Traject : {}, {}", id, traject);
        if (traject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trajectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        traject = trajectRepository.save(traject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, traject.getId().toString()))
            .body(traject);
    }

    /**
     * {@code PATCH  /trajects/:id} : Partial updates given fields of an existing traject, field will ignore if it is null
     *
     * @param id the id of the traject to save.
     * @param traject the traject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traject,
     * or with status {@code 400 (Bad Request)} if the traject is not valid,
     * or with status {@code 404 (Not Found)} if the traject is not found,
     * or with status {@code 500 (Internal Server Error)} if the traject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Traject> partialUpdateTraject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Traject traject
    ) throws URISyntaxException {
        log.debug("REST request to partial update Traject partially : {}, {}", id, traject);
        if (traject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trajectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Traject> result = trajectRepository
            .findById(traject.getId())
            .map(existingTraject -> {
                if (traject.getDatumeinde() != null) {
                    existingTraject.setDatumeinde(traject.getDatumeinde());
                }
                if (traject.getDatumstart() != null) {
                    existingTraject.setDatumstart(traject.getDatumstart());
                }
                if (traject.getDatumtoekenning() != null) {
                    existingTraject.setDatumtoekenning(traject.getDatumtoekenning());
                }
                if (traject.getNaam() != null) {
                    existingTraject.setNaam(traject.getNaam());
                }
                if (traject.getOmschrijving() != null) {
                    existingTraject.setOmschrijving(traject.getOmschrijving());
                }
                if (traject.getResultaat() != null) {
                    existingTraject.setResultaat(traject.getResultaat());
                }

                return existingTraject;
            })
            .map(trajectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, traject.getId().toString())
        );
    }

    /**
     * {@code GET  /trajects} : get all the trajects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trajects in body.
     */
    @GetMapping("")
    public List<Traject> getAllTrajects() {
        log.debug("REST request to get all Trajects");
        return trajectRepository.findAll();
    }

    /**
     * {@code GET  /trajects/:id} : get the "id" traject.
     *
     * @param id the id of the traject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the traject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Traject> getTraject(@PathVariable("id") Long id) {
        log.debug("REST request to get Traject : {}", id);
        Optional<Traject> traject = trajectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(traject);
    }

    /**
     * {@code DELETE  /trajects/:id} : delete the "id" traject.
     *
     * @param id the id of the traject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraject(@PathVariable("id") Long id) {
        log.debug("REST request to delete Traject : {}", id);
        trajectRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
