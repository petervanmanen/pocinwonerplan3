package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Infiltratieput;
import nl.ritense.demo.repository.InfiltratieputRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Infiltratieput}.
 */
@RestController
@RequestMapping("/api/infiltratieputs")
@Transactional
public class InfiltratieputResource {

    private final Logger log = LoggerFactory.getLogger(InfiltratieputResource.class);

    private static final String ENTITY_NAME = "infiltratieput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfiltratieputRepository infiltratieputRepository;

    public InfiltratieputResource(InfiltratieputRepository infiltratieputRepository) {
        this.infiltratieputRepository = infiltratieputRepository;
    }

    /**
     * {@code POST  /infiltratieputs} : Create a new infiltratieput.
     *
     * @param infiltratieput the infiltratieput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infiltratieput, or with status {@code 400 (Bad Request)} if the infiltratieput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Infiltratieput> createInfiltratieput(@RequestBody Infiltratieput infiltratieput) throws URISyntaxException {
        log.debug("REST request to save Infiltratieput : {}", infiltratieput);
        if (infiltratieput.getId() != null) {
            throw new BadRequestAlertException("A new infiltratieput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        infiltratieput = infiltratieputRepository.save(infiltratieput);
        return ResponseEntity.created(new URI("/api/infiltratieputs/" + infiltratieput.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, infiltratieput.getId().toString()))
            .body(infiltratieput);
    }

    /**
     * {@code PUT  /infiltratieputs/:id} : Updates an existing infiltratieput.
     *
     * @param id the id of the infiltratieput to save.
     * @param infiltratieput the infiltratieput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infiltratieput,
     * or with status {@code 400 (Bad Request)} if the infiltratieput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infiltratieput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Infiltratieput> updateInfiltratieput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Infiltratieput infiltratieput
    ) throws URISyntaxException {
        log.debug("REST request to update Infiltratieput : {}, {}", id, infiltratieput);
        if (infiltratieput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infiltratieput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infiltratieputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        infiltratieput = infiltratieputRepository.save(infiltratieput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, infiltratieput.getId().toString()))
            .body(infiltratieput);
    }

    /**
     * {@code PATCH  /infiltratieputs/:id} : Partial updates given fields of an existing infiltratieput, field will ignore if it is null
     *
     * @param id the id of the infiltratieput to save.
     * @param infiltratieput the infiltratieput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infiltratieput,
     * or with status {@code 400 (Bad Request)} if the infiltratieput is not valid,
     * or with status {@code 404 (Not Found)} if the infiltratieput is not found,
     * or with status {@code 500 (Internal Server Error)} if the infiltratieput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Infiltratieput> partialUpdateInfiltratieput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Infiltratieput infiltratieput
    ) throws URISyntaxException {
        log.debug("REST request to partial update Infiltratieput partially : {}, {}", id, infiltratieput);
        if (infiltratieput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infiltratieput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infiltratieputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Infiltratieput> result = infiltratieputRepository
            .findById(infiltratieput.getId())
            .map(existingInfiltratieput -> {
                if (infiltratieput.getPorositeit() != null) {
                    existingInfiltratieput.setPorositeit(infiltratieput.getPorositeit());
                }
                if (infiltratieput.getRisicogebied() != null) {
                    existingInfiltratieput.setRisicogebied(infiltratieput.getRisicogebied());
                }

                return existingInfiltratieput;
            })
            .map(infiltratieputRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, infiltratieput.getId().toString())
        );
    }

    /**
     * {@code GET  /infiltratieputs} : get all the infiltratieputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infiltratieputs in body.
     */
    @GetMapping("")
    public List<Infiltratieput> getAllInfiltratieputs() {
        log.debug("REST request to get all Infiltratieputs");
        return infiltratieputRepository.findAll();
    }

    /**
     * {@code GET  /infiltratieputs/:id} : get the "id" infiltratieput.
     *
     * @param id the id of the infiltratieput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infiltratieput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Infiltratieput> getInfiltratieput(@PathVariable("id") Long id) {
        log.debug("REST request to get Infiltratieput : {}", id);
        Optional<Infiltratieput> infiltratieput = infiltratieputRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(infiltratieput);
    }

    /**
     * {@code DELETE  /infiltratieputs/:id} : delete the "id" infiltratieput.
     *
     * @param id the id of the infiltratieput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInfiltratieput(@PathVariable("id") Long id) {
        log.debug("REST request to delete Infiltratieput : {}", id);
        infiltratieputRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
