package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Soortfunctioneelgebied;
import nl.ritense.demo.repository.SoortfunctioneelgebiedRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Soortfunctioneelgebied}.
 */
@RestController
@RequestMapping("/api/soortfunctioneelgebieds")
@Transactional
public class SoortfunctioneelgebiedResource {

    private final Logger log = LoggerFactory.getLogger(SoortfunctioneelgebiedResource.class);

    private static final String ENTITY_NAME = "soortfunctioneelgebied";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoortfunctioneelgebiedRepository soortfunctioneelgebiedRepository;

    public SoortfunctioneelgebiedResource(SoortfunctioneelgebiedRepository soortfunctioneelgebiedRepository) {
        this.soortfunctioneelgebiedRepository = soortfunctioneelgebiedRepository;
    }

    /**
     * {@code POST  /soortfunctioneelgebieds} : Create a new soortfunctioneelgebied.
     *
     * @param soortfunctioneelgebied the soortfunctioneelgebied to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soortfunctioneelgebied, or with status {@code 400 (Bad Request)} if the soortfunctioneelgebied has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Soortfunctioneelgebied> createSoortfunctioneelgebied(@RequestBody Soortfunctioneelgebied soortfunctioneelgebied)
        throws URISyntaxException {
        log.debug("REST request to save Soortfunctioneelgebied : {}", soortfunctioneelgebied);
        if (soortfunctioneelgebied.getId() != null) {
            throw new BadRequestAlertException("A new soortfunctioneelgebied cannot already have an ID", ENTITY_NAME, "idexists");
        }
        soortfunctioneelgebied = soortfunctioneelgebiedRepository.save(soortfunctioneelgebied);
        return ResponseEntity.created(new URI("/api/soortfunctioneelgebieds/" + soortfunctioneelgebied.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, soortfunctioneelgebied.getId().toString()))
            .body(soortfunctioneelgebied);
    }

    /**
     * {@code PUT  /soortfunctioneelgebieds/:id} : Updates an existing soortfunctioneelgebied.
     *
     * @param id the id of the soortfunctioneelgebied to save.
     * @param soortfunctioneelgebied the soortfunctioneelgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortfunctioneelgebied,
     * or with status {@code 400 (Bad Request)} if the soortfunctioneelgebied is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soortfunctioneelgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Soortfunctioneelgebied> updateSoortfunctioneelgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortfunctioneelgebied soortfunctioneelgebied
    ) throws URISyntaxException {
        log.debug("REST request to update Soortfunctioneelgebied : {}, {}", id, soortfunctioneelgebied);
        if (soortfunctioneelgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortfunctioneelgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortfunctioneelgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        soortfunctioneelgebied = soortfunctioneelgebiedRepository.save(soortfunctioneelgebied);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortfunctioneelgebied.getId().toString()))
            .body(soortfunctioneelgebied);
    }

    /**
     * {@code PATCH  /soortfunctioneelgebieds/:id} : Partial updates given fields of an existing soortfunctioneelgebied, field will ignore if it is null
     *
     * @param id the id of the soortfunctioneelgebied to save.
     * @param soortfunctioneelgebied the soortfunctioneelgebied to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortfunctioneelgebied,
     * or with status {@code 400 (Bad Request)} if the soortfunctioneelgebied is not valid,
     * or with status {@code 404 (Not Found)} if the soortfunctioneelgebied is not found,
     * or with status {@code 500 (Internal Server Error)} if the soortfunctioneelgebied couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Soortfunctioneelgebied> partialUpdateSoortfunctioneelgebied(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortfunctioneelgebied soortfunctioneelgebied
    ) throws URISyntaxException {
        log.debug("REST request to partial update Soortfunctioneelgebied partially : {}, {}", id, soortfunctioneelgebied);
        if (soortfunctioneelgebied.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortfunctioneelgebied.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortfunctioneelgebiedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Soortfunctioneelgebied> result = soortfunctioneelgebiedRepository
            .findById(soortfunctioneelgebied.getId())
            .map(existingSoortfunctioneelgebied -> {
                if (soortfunctioneelgebied.getIndicatieplusbrpopulatie() != null) {
                    existingSoortfunctioneelgebied.setIndicatieplusbrpopulatie(soortfunctioneelgebied.getIndicatieplusbrpopulatie());
                }
                if (soortfunctioneelgebied.getTypefunctioneelgebied() != null) {
                    existingSoortfunctioneelgebied.setTypefunctioneelgebied(soortfunctioneelgebied.getTypefunctioneelgebied());
                }

                return existingSoortfunctioneelgebied;
            })
            .map(soortfunctioneelgebiedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortfunctioneelgebied.getId().toString())
        );
    }

    /**
     * {@code GET  /soortfunctioneelgebieds} : get all the soortfunctioneelgebieds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soortfunctioneelgebieds in body.
     */
    @GetMapping("")
    public List<Soortfunctioneelgebied> getAllSoortfunctioneelgebieds() {
        log.debug("REST request to get all Soortfunctioneelgebieds");
        return soortfunctioneelgebiedRepository.findAll();
    }

    /**
     * {@code GET  /soortfunctioneelgebieds/:id} : get the "id" soortfunctioneelgebied.
     *
     * @param id the id of the soortfunctioneelgebied to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soortfunctioneelgebied, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Soortfunctioneelgebied> getSoortfunctioneelgebied(@PathVariable("id") Long id) {
        log.debug("REST request to get Soortfunctioneelgebied : {}", id);
        Optional<Soortfunctioneelgebied> soortfunctioneelgebied = soortfunctioneelgebiedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(soortfunctioneelgebied);
    }

    /**
     * {@code DELETE  /soortfunctioneelgebieds/:id} : delete the "id" soortfunctioneelgebied.
     *
     * @param id the id of the soortfunctioneelgebied to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoortfunctioneelgebied(@PathVariable("id") Long id) {
        log.debug("REST request to delete Soortfunctioneelgebied : {}", id);
        soortfunctioneelgebiedRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
