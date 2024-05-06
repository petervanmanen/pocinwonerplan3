package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Soortoverigbouwwerk;
import nl.ritense.demo.repository.SoortoverigbouwwerkRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Soortoverigbouwwerk}.
 */
@RestController
@RequestMapping("/api/soortoverigbouwwerks")
@Transactional
public class SoortoverigbouwwerkResource {

    private final Logger log = LoggerFactory.getLogger(SoortoverigbouwwerkResource.class);

    private static final String ENTITY_NAME = "soortoverigbouwwerk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoortoverigbouwwerkRepository soortoverigbouwwerkRepository;

    public SoortoverigbouwwerkResource(SoortoverigbouwwerkRepository soortoverigbouwwerkRepository) {
        this.soortoverigbouwwerkRepository = soortoverigbouwwerkRepository;
    }

    /**
     * {@code POST  /soortoverigbouwwerks} : Create a new soortoverigbouwwerk.
     *
     * @param soortoverigbouwwerk the soortoverigbouwwerk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soortoverigbouwwerk, or with status {@code 400 (Bad Request)} if the soortoverigbouwwerk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Soortoverigbouwwerk> createSoortoverigbouwwerk(@RequestBody Soortoverigbouwwerk soortoverigbouwwerk)
        throws URISyntaxException {
        log.debug("REST request to save Soortoverigbouwwerk : {}", soortoverigbouwwerk);
        if (soortoverigbouwwerk.getId() != null) {
            throw new BadRequestAlertException("A new soortoverigbouwwerk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        soortoverigbouwwerk = soortoverigbouwwerkRepository.save(soortoverigbouwwerk);
        return ResponseEntity.created(new URI("/api/soortoverigbouwwerks/" + soortoverigbouwwerk.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, soortoverigbouwwerk.getId().toString()))
            .body(soortoverigbouwwerk);
    }

    /**
     * {@code PUT  /soortoverigbouwwerks/:id} : Updates an existing soortoverigbouwwerk.
     *
     * @param id the id of the soortoverigbouwwerk to save.
     * @param soortoverigbouwwerk the soortoverigbouwwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortoverigbouwwerk,
     * or with status {@code 400 (Bad Request)} if the soortoverigbouwwerk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soortoverigbouwwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Soortoverigbouwwerk> updateSoortoverigbouwwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortoverigbouwwerk soortoverigbouwwerk
    ) throws URISyntaxException {
        log.debug("REST request to update Soortoverigbouwwerk : {}, {}", id, soortoverigbouwwerk);
        if (soortoverigbouwwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortoverigbouwwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortoverigbouwwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        soortoverigbouwwerk = soortoverigbouwwerkRepository.save(soortoverigbouwwerk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortoverigbouwwerk.getId().toString()))
            .body(soortoverigbouwwerk);
    }

    /**
     * {@code PATCH  /soortoverigbouwwerks/:id} : Partial updates given fields of an existing soortoverigbouwwerk, field will ignore if it is null
     *
     * @param id the id of the soortoverigbouwwerk to save.
     * @param soortoverigbouwwerk the soortoverigbouwwerk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soortoverigbouwwerk,
     * or with status {@code 400 (Bad Request)} if the soortoverigbouwwerk is not valid,
     * or with status {@code 404 (Not Found)} if the soortoverigbouwwerk is not found,
     * or with status {@code 500 (Internal Server Error)} if the soortoverigbouwwerk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Soortoverigbouwwerk> partialUpdateSoortoverigbouwwerk(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Soortoverigbouwwerk soortoverigbouwwerk
    ) throws URISyntaxException {
        log.debug("REST request to partial update Soortoverigbouwwerk partially : {}, {}", id, soortoverigbouwwerk);
        if (soortoverigbouwwerk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soortoverigbouwwerk.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soortoverigbouwwerkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Soortoverigbouwwerk> result = soortoverigbouwwerkRepository
            .findById(soortoverigbouwwerk.getId())
            .map(existingSoortoverigbouwwerk -> {
                if (soortoverigbouwwerk.getIndicatieplusbrpopulatie() != null) {
                    existingSoortoverigbouwwerk.setIndicatieplusbrpopulatie(soortoverigbouwwerk.getIndicatieplusbrpopulatie());
                }
                if (soortoverigbouwwerk.getTypeoverigbouwwerk() != null) {
                    existingSoortoverigbouwwerk.setTypeoverigbouwwerk(soortoverigbouwwerk.getTypeoverigbouwwerk());
                }

                return existingSoortoverigbouwwerk;
            })
            .map(soortoverigbouwwerkRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soortoverigbouwwerk.getId().toString())
        );
    }

    /**
     * {@code GET  /soortoverigbouwwerks} : get all the soortoverigbouwwerks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soortoverigbouwwerks in body.
     */
    @GetMapping("")
    public List<Soortoverigbouwwerk> getAllSoortoverigbouwwerks() {
        log.debug("REST request to get all Soortoverigbouwwerks");
        return soortoverigbouwwerkRepository.findAll();
    }

    /**
     * {@code GET  /soortoverigbouwwerks/:id} : get the "id" soortoverigbouwwerk.
     *
     * @param id the id of the soortoverigbouwwerk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soortoverigbouwwerk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Soortoverigbouwwerk> getSoortoverigbouwwerk(@PathVariable("id") Long id) {
        log.debug("REST request to get Soortoverigbouwwerk : {}", id);
        Optional<Soortoverigbouwwerk> soortoverigbouwwerk = soortoverigbouwwerkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(soortoverigbouwwerk);
    }

    /**
     * {@code DELETE  /soortoverigbouwwerks/:id} : delete the "id" soortoverigbouwwerk.
     *
     * @param id the id of the soortoverigbouwwerk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoortoverigbouwwerk(@PathVariable("id") Long id) {
        log.debug("REST request to delete Soortoverigbouwwerk : {}", id);
        soortoverigbouwwerkRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
