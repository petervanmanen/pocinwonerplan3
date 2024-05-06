package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Bemalingsgebied;
import nl.ritense.demo.repository.BemalingsgebiedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Bemalingsgebied}.
 */
@RestController
@RequestMapping("/api/bemalingsgebieds")
@Transactional
public class BemalingsgebiedResource {

    private final Logger log = LoggerFactory.getLogger(BemalingsgebiedResource.class);

    private static final String ENTITY_NAME = "bemalingsgebied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BemalingsgebiedRepository bemalingsgebiedRepository;

    public BemalingsgebiedResource(BemalingsgebiedRepository bemalingsgebiedRepository) {
        this.bemalingsgebiedRepository = bemalingsgebiedRepository;
    }

    /**
     * {@code POST  /bemalingsgebieds} : Create a new bemalingsgebied.
     *
     * @param bemalingsgebied the bemalingsgebied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bemalingsgebied, or with status {@code 400 (Bad Request)} if the bemalingsgebied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Bemalingsgebied> createBemalingsgebied(@RequestBody Bemalingsgebied bemalingsgebied) throws URISyntaxException {
        log.debug("REST request to save Bemalingsgebied : {}", bemalingsgebied);
        if (bemalingsgebied.getId() != null) {
            throw new BadRequestAlertException("A new bemalingsgebied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bemalingsgebied = bemalingsgebiedRepository.save(bemalingsgebied);
        return ResponseEntity.created(new URI("/api/bemalingsgebieds/" + bemalingsgebied.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bemalingsgebied.getId().toString()))
            .body(bemalingsgebied);
    }

    /**
     * {@code PUT  /bemalingsgebieds/:id} : Updates an existing bemalingsgebied.
     *
     * @param id the id of the bemalingsgebied to save.
     * @param bemalingsgebied the bemalingsgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bemalingsgebied,
     * or with status {@code 400 (Bad Request)} if the bemalingsgebied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bemalingsgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bemalingsgebied> updateBemalingsgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bemalingsgebied bemalingsgebied
    ) throws URISyntaxException {
        log.debug("REST request to update Bemalingsgebied : {}, {}", id, bemalingsgebied);
        if (bemalingsgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bemalingsgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bemalingsgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bemalingsgebied = bemalingsgebiedRepository.save(bemalingsgebied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bemalingsgebied.getId().toString()))
            .body(bemalingsgebied);
    }

    /**
     * {@code PATCH  /bemalingsgebieds/:id} : Partial updates given fields of an existing bemalingsgebied, field will ignore if it is null
     *
     * @param id the id of the bemalingsgebied to save.
     * @param bemalingsgebied the bemalingsgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bemalingsgebied,
     * or with status {@code 400 (Bad Request)} if the bemalingsgebied is not valid,
     * or with status {@code 404 (Not Found)} if the bemalingsgebied is not found,
     * or with status {@code 500 (Internal Server Error)} if the bemalingsgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bemalingsgebied> partialUpdateBemalingsgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bemalingsgebied bemalingsgebied
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bemalingsgebied partially : {}, {}", id, bemalingsgebied);
        if (bemalingsgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bemalingsgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bemalingsgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bemalingsgebied> result = bemalingsgebiedRepository
            .findById(bemalingsgebied.getId())
            .map(existingBemalingsgebied -> {
                if (bemalingsgebied.getRioleringsgebied() != null) {
                    existingBemalingsgebied.setRioleringsgebied(bemalingsgebied.getRioleringsgebied());
                }

                return existingBemalingsgebied;
            })
            .map(bemalingsgebiedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bemalingsgebied.getId().toString())
        );
    }

    /**
     * {@code GET  /bemalingsgebieds} : get all the bemalingsgebieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bemalingsgebieds in body.
     */
    @GetMapping("")
    public List<Bemalingsgebied> getAllBemalingsgebieds() {
        log.debug("REST request to get all Bemalingsgebieds");
        return bemalingsgebiedRepository.findAll();
    }

    /**
     * {@code GET  /bemalingsgebieds/:id} : get the "id" bemalingsgebied.
     *
     * @param id the id of the bemalingsgebied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bemalingsgebied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bemalingsgebied> getBemalingsgebied(@PathVariable("id") Long id) {
        log.debug("REST request to get Bemalingsgebied : {}", id);
        Optional<Bemalingsgebied> bemalingsgebied = bemalingsgebiedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bemalingsgebied);
    }

    /**
     * {@code DELETE  /bemalingsgebieds/:id} : delete the "id" bemalingsgebied.
     *
     * @param id the id of the bemalingsgebied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBemalingsgebied(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bemalingsgebied : {}", id);
        bemalingsgebiedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
