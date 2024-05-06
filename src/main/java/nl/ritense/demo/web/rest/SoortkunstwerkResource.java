package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Soortkunstwerk;
import nl.ritense.demo.repository.SoortkunstwerkRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Soortkunstwerk}.
 */
@RestController
@RequestMapping("/api/soortkunstwerks")
@Transactional
public class SoortkunstwerkResource {

    private final Logger log = LoggerFactory.getLogger(SoortkunstwerkResource.class);

    private static final String ENTITY_NAME = "soortkunstwerk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoortkunstwerkRepository soortkunstwerkRepository;

    public SoortkunstwerkResource(SoortkunstwerkRepository soortkunstwerkRepository) {
        this.soortkunstwerkRepository = soortkunstwerkRepository;
    }

    /**
     * {@code POST  /soortkunstwerks} : Create a new soortkunstwerk.
     *
     * @param soortkunstwerk the soortkunstwerk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soortkunstwerk, or with status {@code 400 (Bad Request)} if the soortkunstwerk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Soortkunstwerk> createSoortkunstwerk(@RequestBody Soortkunstwerk soortkunstwerk) throws URISyntaxException {
        log.debug("REST request to save Soortkunstwerk : {}", soortkunstwerk);
        if (soortkunstwerk.getId() != null) {
            throw new BadRequestAlertException("A new soortkunstwerk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        soortkunstwerk = soortkunstwerkRepository.save(soortkunstwerk);
        return ResponseEntity.created(new URI("/api/soortkunstwerks/" + soortkunstwerk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, soortkunstwerk.getId().toString()))
            .body(soortkunstwerk);
    }

    /**
     * {@code PUT  /soortkunstwerks/:id} : Updates an existing soortkunstwerk.
     *
     * @param id the id of the soortkunstwerk to save.
     * @param soortkunstwerk the soortkunstwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortkunstwerk,
     * or with status {@code 400 (Bad Request)} if the soortkunstwerk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soortkunstwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Soortkunstwerk> updateSoortkunstwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortkunstwerk soortkunstwerk
    ) throws URISyntaxException {
        log.debug("REST request to update Soortkunstwerk : {}, {}", id, soortkunstwerk);
        if (soortkunstwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortkunstwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortkunstwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        soortkunstwerk = soortkunstwerkRepository.save(soortkunstwerk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortkunstwerk.getId().toString()))
            .body(soortkunstwerk);
    }

    /**
     * {@code PATCH  /soortkunstwerks/:id} : Partial updates given fields of an existing soortkunstwerk, field will ignore if it is null
     *
     * @param id the id of the soortkunstwerk to save.
     * @param soortkunstwerk the soortkunstwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortkunstwerk,
     * or with status {@code 400 (Bad Request)} if the soortkunstwerk is not valid,
     * or with status {@code 404 (Not Found)} if the soortkunstwerk is not found,
     * or with status {@code 500 (Internal Server Error)} if the soortkunstwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Soortkunstwerk> partialUpdateSoortkunstwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortkunstwerk soortkunstwerk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Soortkunstwerk partially : {}, {}", id, soortkunstwerk);
        if (soortkunstwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortkunstwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortkunstwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Soortkunstwerk> result = soortkunstwerkRepository
            .findById(soortkunstwerk.getId())
            .map(existingSoortkunstwerk -> {
                if (soortkunstwerk.getIndicatieplusbrpopulatie() != null) {
                    existingSoortkunstwerk.setIndicatieplusbrpopulatie(soortkunstwerk.getIndicatieplusbrpopulatie());
                }
                if (soortkunstwerk.getTypekunstwerk() != null) {
                    existingSoortkunstwerk.setTypekunstwerk(soortkunstwerk.getTypekunstwerk());
                }

                return existingSoortkunstwerk;
            })
            .map(soortkunstwerkRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortkunstwerk.getId().toString())
        );
    }

    /**
     * {@code GET  /soortkunstwerks} : get all the soortkunstwerks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soortkunstwerks in body.
     */
    @GetMapping("")
    public List<Soortkunstwerk> getAllSoortkunstwerks() {
        log.debug("REST request to get all Soortkunstwerks");
        return soortkunstwerkRepository.findAll();
    }

    /**
     * {@code GET  /soortkunstwerks/:id} : get the "id" soortkunstwerk.
     *
     * @param id the id of the soortkunstwerk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soortkunstwerk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Soortkunstwerk> getSoortkunstwerk(@PathVariable("id") Long id) {
        log.debug("REST request to get Soortkunstwerk : {}", id);
        Optional<Soortkunstwerk> soortkunstwerk = soortkunstwerkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(soortkunstwerk);
    }

    /**
     * {@code DELETE  /soortkunstwerks/:id} : delete the "id" soortkunstwerk.
     *
     * @param id the id of the soortkunstwerk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoortkunstwerk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Soortkunstwerk : {}", id);
        soortkunstwerkRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
