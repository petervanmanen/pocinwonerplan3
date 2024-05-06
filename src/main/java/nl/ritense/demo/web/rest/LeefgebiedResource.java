package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Leefgebied;
import nl.ritense.demo.repository.LeefgebiedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Leefgebied}.
 */
@RestController
@RequestMapping("/api/leefgebieds")
@Transactional
public class LeefgebiedResource {

    private final Logger log = LoggerFactory.getLogger(LeefgebiedResource.class);

    private static final String ENTITY_NAME = "leefgebied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeefgebiedRepository leefgebiedRepository;

    public LeefgebiedResource(LeefgebiedRepository leefgebiedRepository) {
        this.leefgebiedRepository = leefgebiedRepository;
    }

    /**
     * {@code POST  /leefgebieds} : Create a new leefgebied.
     *
     * @param leefgebied the leefgebied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leefgebied, or with status {@code 400 (Bad Request)} if the leefgebied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Leefgebied> createLeefgebied(@Valid @RequestBody Leefgebied leefgebied) throws URISyntaxException {
        log.debug("REST request to save Leefgebied : {}", leefgebied);
        if (leefgebied.getId() != null) {
            throw new BadRequestAlertException("A new leefgebied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        leefgebied = leefgebiedRepository.save(leefgebied);
        return ResponseEntity.created(new URI("/api/leefgebieds/" + leefgebied.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, leefgebied.getId().toString()))
            .body(leefgebied);
    }

    /**
     * {@code PUT  /leefgebieds/:id} : Updates an existing leefgebied.
     *
     * @param id the id of the leefgebied to save.
     * @param leefgebied the leefgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leefgebied,
     * or with status {@code 400 (Bad Request)} if the leefgebied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leefgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Leefgebied> updateLeefgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Leefgebied leefgebied
    ) throws URISyntaxException {
        log.debug("REST request to update Leefgebied : {}, {}", id, leefgebied);
        if (leefgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leefgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leefgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        leefgebied = leefgebiedRepository.save(leefgebied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leefgebied.getId().toString()))
            .body(leefgebied);
    }

    /**
     * {@code PATCH  /leefgebieds/:id} : Partial updates given fields of an existing leefgebied, field will ignore if it is null
     *
     * @param id the id of the leefgebied to save.
     * @param leefgebied the leefgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leefgebied,
     * or with status {@code 400 (Bad Request)} if the leefgebied is not valid,
     * or with status {@code 404 (Not Found)} if the leefgebied is not found,
     * or with status {@code 500 (Internal Server Error)} if the leefgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Leefgebied> partialUpdateLeefgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Leefgebied leefgebied
    ) throws URISyntaxException {
        log.debug("REST request to partial update Leefgebied partially : {}, {}", id, leefgebied);
        if (leefgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leefgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leefgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Leefgebied> result = leefgebiedRepository
            .findById(leefgebied.getId())
            .map(existingLeefgebied -> {
                if (leefgebied.getNaam() != null) {
                    existingLeefgebied.setNaam(leefgebied.getNaam());
                }

                return existingLeefgebied;
            })
            .map(leefgebiedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leefgebied.getId().toString())
        );
    }

    /**
     * {@code GET  /leefgebieds} : get all the leefgebieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leefgebieds in body.
     */
    @GetMapping("")
    public List<Leefgebied> getAllLeefgebieds() {
        log.debug("REST request to get all Leefgebieds");
        return leefgebiedRepository.findAll();
    }

    /**
     * {@code GET  /leefgebieds/:id} : get the "id" leefgebied.
     *
     * @param id the id of the leefgebied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leefgebied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Leefgebied> getLeefgebied(@PathVariable("id") Long id) {
        log.debug("REST request to get Leefgebied : {}", id);
        Optional<Leefgebied> leefgebied = leefgebiedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(leefgebied);
    }

    /**
     * {@code DELETE  /leefgebieds/:id} : delete the "id" leefgebied.
     *
     * @param id the id of the leefgebied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeefgebied(@PathVariable("id") Long id) {
        log.debug("REST request to delete Leefgebied : {}", id);
        leefgebiedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
