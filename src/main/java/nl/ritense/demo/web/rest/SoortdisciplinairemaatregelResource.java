package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Soortdisciplinairemaatregel;
import nl.ritense.demo.repository.SoortdisciplinairemaatregelRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Soortdisciplinairemaatregel}.
 */
@RestController
@RequestMapping("/api/soortdisciplinairemaatregels")
@Transactional
public class SoortdisciplinairemaatregelResource {

    private final Logger log = LoggerFactory.getLogger(SoortdisciplinairemaatregelResource.class);

    private static final String ENTITY_NAME = "soortdisciplinairemaatregel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoortdisciplinairemaatregelRepository soortdisciplinairemaatregelRepository;

    public SoortdisciplinairemaatregelResource(SoortdisciplinairemaatregelRepository soortdisciplinairemaatregelRepository) {
        this.soortdisciplinairemaatregelRepository = soortdisciplinairemaatregelRepository;
    }

    /**
     * {@code POST  /soortdisciplinairemaatregels} : Create a new soortdisciplinairemaatregel.
     *
     * @param soortdisciplinairemaatregel the soortdisciplinairemaatregel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soortdisciplinairemaatregel, or with status {@code 400 (Bad Request)} if the soortdisciplinairemaatregel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Soortdisciplinairemaatregel> createSoortdisciplinairemaatregel(
        @RequestBody Soortdisciplinairemaatregel soortdisciplinairemaatregel
    ) throws URISyntaxException {
        log.debug("REST request to save Soortdisciplinairemaatregel : {}", soortdisciplinairemaatregel);
        if (soortdisciplinairemaatregel.getId() != null) {
            throw new BadRequestAlertException("A new soortdisciplinairemaatregel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        soortdisciplinairemaatregel = soortdisciplinairemaatregelRepository.save(soortdisciplinairemaatregel);
        return ResponseEntity.created(new URI("/api/soortdisciplinairemaatregels/" + soortdisciplinairemaatregel.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, soortdisciplinairemaatregel.getId().toString())
            )
            .body(soortdisciplinairemaatregel);
    }

    /**
     * {@code PUT  /soortdisciplinairemaatregels/:id} : Updates an existing soortdisciplinairemaatregel.
     *
     * @param id the id of the soortdisciplinairemaatregel to save.
     * @param soortdisciplinairemaatregel the soortdisciplinairemaatregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortdisciplinairemaatregel,
     * or with status {@code 400 (Bad Request)} if the soortdisciplinairemaatregel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soortdisciplinairemaatregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Soortdisciplinairemaatregel> updateSoortdisciplinairemaatregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortdisciplinairemaatregel soortdisciplinairemaatregel
    ) throws URISyntaxException {
        log.debug("REST request to update Soortdisciplinairemaatregel : {}, {}", id, soortdisciplinairemaatregel);
        if (soortdisciplinairemaatregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortdisciplinairemaatregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortdisciplinairemaatregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        soortdisciplinairemaatregel = soortdisciplinairemaatregelRepository.save(soortdisciplinairemaatregel);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortdisciplinairemaatregel.getId().toString())
            )
            .body(soortdisciplinairemaatregel);
    }

    /**
     * {@code PATCH  /soortdisciplinairemaatregels/:id} : Partial updates given fields of an existing soortdisciplinairemaatregel, field will ignore if it is null
     *
     * @param id the id of the soortdisciplinairemaatregel to save.
     * @param soortdisciplinairemaatregel the soortdisciplinairemaatregel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortdisciplinairemaatregel,
     * or with status {@code 400 (Bad Request)} if the soortdisciplinairemaatregel is not valid,
     * or with status {@code 404 (Not Found)} if the soortdisciplinairemaatregel is not found,
     * or with status {@code 500 (Internal Server Error)} if the soortdisciplinairemaatregel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Soortdisciplinairemaatregel> partialUpdateSoortdisciplinairemaatregel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortdisciplinairemaatregel soortdisciplinairemaatregel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Soortdisciplinairemaatregel partially : {}, {}", id, soortdisciplinairemaatregel);
        if (soortdisciplinairemaatregel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortdisciplinairemaatregel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortdisciplinairemaatregelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Soortdisciplinairemaatregel> result = soortdisciplinairemaatregelRepository
            .findById(soortdisciplinairemaatregel.getId())
            .map(existingSoortdisciplinairemaatregel -> {
                if (soortdisciplinairemaatregel.getNaam() != null) {
                    existingSoortdisciplinairemaatregel.setNaam(soortdisciplinairemaatregel.getNaam());
                }
                if (soortdisciplinairemaatregel.getOmschrijving() != null) {
                    existingSoortdisciplinairemaatregel.setOmschrijving(soortdisciplinairemaatregel.getOmschrijving());
                }

                return existingSoortdisciplinairemaatregel;
            })
            .map(soortdisciplinairemaatregelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortdisciplinairemaatregel.getId().toString())
        );
    }

    /**
     * {@code GET  /soortdisciplinairemaatregels} : get all the soortdisciplinairemaatregels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soortdisciplinairemaatregels in body.
     */
    @GetMapping("")
    public List<Soortdisciplinairemaatregel> getAllSoortdisciplinairemaatregels() {
        log.debug("REST request to get all Soortdisciplinairemaatregels");
        return soortdisciplinairemaatregelRepository.findAll();
    }

    /**
     * {@code GET  /soortdisciplinairemaatregels/:id} : get the "id" soortdisciplinairemaatregel.
     *
     * @param id the id of the soortdisciplinairemaatregel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soortdisciplinairemaatregel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Soortdisciplinairemaatregel> getSoortdisciplinairemaatregel(@PathVariable("id") Long id) {
        log.debug("REST request to get Soortdisciplinairemaatregel : {}", id);
        Optional<Soortdisciplinairemaatregel> soortdisciplinairemaatregel = soortdisciplinairemaatregelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(soortdisciplinairemaatregel);
    }

    /**
     * {@code DELETE  /soortdisciplinairemaatregels/:id} : delete the "id" soortdisciplinairemaatregel.
     *
     * @param id the id of the soortdisciplinairemaatregel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoortdisciplinairemaatregel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Soortdisciplinairemaatregel : {}", id);
        soortdisciplinairemaatregelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
