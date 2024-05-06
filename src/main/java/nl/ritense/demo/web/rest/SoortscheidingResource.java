package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Soortscheiding;
import nl.ritense.demo.repository.SoortscheidingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Soortscheiding}.
 */
@RestController
@RequestMapping("/api/soortscheidings")
@Transactional
public class SoortscheidingResource {

    private final Logger log = LoggerFactory.getLogger(SoortscheidingResource.class);

    private static final String ENTITY_NAME = "soortscheiding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoortscheidingRepository soortscheidingRepository;

    public SoortscheidingResource(SoortscheidingRepository soortscheidingRepository) {
        this.soortscheidingRepository = soortscheidingRepository;
    }

    /**
     * {@code POST  /soortscheidings} : Create a new soortscheiding.
     *
     * @param soortscheiding the soortscheiding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soortscheiding, or with status {@code 400 (Bad Request)} if the soortscheiding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Soortscheiding> createSoortscheiding(@RequestBody Soortscheiding soortscheiding) throws URISyntaxException {
        log.debug("REST request to save Soortscheiding : {}", soortscheiding);
        if (soortscheiding.getId() != null) {
            throw new BadRequestAlertException("A new soortscheiding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        soortscheiding = soortscheidingRepository.save(soortscheiding);
        return ResponseEntity.created(new URI("/api/soortscheidings/" + soortscheiding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, soortscheiding.getId().toString()))
            .body(soortscheiding);
    }

    /**
     * {@code PUT  /soortscheidings/:id} : Updates an existing soortscheiding.
     *
     * @param id the id of the soortscheiding to save.
     * @param soortscheiding the soortscheiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortscheiding,
     * or with status {@code 400 (Bad Request)} if the soortscheiding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soortscheiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Soortscheiding> updateSoortscheiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortscheiding soortscheiding
    ) throws URISyntaxException {
        log.debug("REST request to update Soortscheiding : {}, {}", id, soortscheiding);
        if (soortscheiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortscheiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortscheidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        soortscheiding = soortscheidingRepository.save(soortscheiding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortscheiding.getId().toString()))
            .body(soortscheiding);
    }

    /**
     * {@code PATCH  /soortscheidings/:id} : Partial updates given fields of an existing soortscheiding, field will ignore if it is null
     *
     * @param id the id of the soortscheiding to save.
     * @param soortscheiding the soortscheiding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortscheiding,
     * or with status {@code 400 (Bad Request)} if the soortscheiding is not valid,
     * or with status {@code 404 (Not Found)} if the soortscheiding is not found,
     * or with status {@code 500 (Internal Server Error)} if the soortscheiding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Soortscheiding> partialUpdateSoortscheiding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortscheiding soortscheiding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Soortscheiding partially : {}, {}", id, soortscheiding);
        if (soortscheiding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortscheiding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortscheidingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Soortscheiding> result = soortscheidingRepository
            .findById(soortscheiding.getId())
            .map(existingSoortscheiding -> {
                if (soortscheiding.getIndicatieplusbrpopulatie() != null) {
                    existingSoortscheiding.setIndicatieplusbrpopulatie(soortscheiding.getIndicatieplusbrpopulatie());
                }
                if (soortscheiding.getTypescheiding() != null) {
                    existingSoortscheiding.setTypescheiding(soortscheiding.getTypescheiding());
                }

                return existingSoortscheiding;
            })
            .map(soortscheidingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortscheiding.getId().toString())
        );
    }

    /**
     * {@code GET  /soortscheidings} : get all the soortscheidings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soortscheidings in body.
     */
    @GetMapping("")
    public List<Soortscheiding> getAllSoortscheidings() {
        log.debug("REST request to get all Soortscheidings");
        return soortscheidingRepository.findAll();
    }

    /**
     * {@code GET  /soortscheidings/:id} : get the "id" soortscheiding.
     *
     * @param id the id of the soortscheiding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soortscheiding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Soortscheiding> getSoortscheiding(@PathVariable("id") Long id) {
        log.debug("REST request to get Soortscheiding : {}", id);
        Optional<Soortscheiding> soortscheiding = soortscheidingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(soortscheiding);
    }

    /**
     * {@code DELETE  /soortscheidings/:id} : delete the "id" soortscheiding.
     *
     * @param id the id of the soortscheiding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoortscheiding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Soortscheiding : {}", id);
        soortscheidingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
