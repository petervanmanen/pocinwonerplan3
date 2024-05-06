package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Wozwaarde;
import nl.ritense.demo.repository.WozwaardeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Wozwaarde}.
 */
@RestController
@RequestMapping("/api/wozwaardes")
@Transactional
public class WozwaardeResource {

    private final Logger log = LoggerFactory.getLogger(WozwaardeResource.class);

    private static final String ENTITY_NAME = "wozwaarde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WozwaardeRepository wozwaardeRepository;

    public WozwaardeResource(WozwaardeRepository wozwaardeRepository) {
        this.wozwaardeRepository = wozwaardeRepository;
    }

    /**
     * {@code POST  /wozwaardes} : Create a new wozwaarde.
     *
     * @param wozwaarde the wozwaarde to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wozwaarde, or with status {@code 400 (Bad Request)} if the wozwaarde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Wozwaarde> createWozwaarde(@RequestBody Wozwaarde wozwaarde) throws URISyntaxException {
        log.debug("REST request to save Wozwaarde : {}", wozwaarde);
        if (wozwaarde.getId() != null) {
            throw new BadRequestAlertException("A new wozwaarde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        wozwaarde = wozwaardeRepository.save(wozwaarde);
        return ResponseEntity.created(new URI("/api/wozwaardes/" + wozwaarde.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, wozwaarde.getId().toString()))
            .body(wozwaarde);
    }

    /**
     * {@code PUT  /wozwaardes/:id} : Updates an existing wozwaarde.
     *
     * @param id the id of the wozwaarde to save.
     * @param wozwaarde the wozwaarde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozwaarde,
     * or with status {@code 400 (Bad Request)} if the wozwaarde is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wozwaarde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Wozwaarde> updateWozwaarde(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozwaarde wozwaarde
    ) throws URISyntaxException {
        log.debug("REST request to update Wozwaarde : {}, {}", id, wozwaarde);
        if (wozwaarde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozwaarde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozwaardeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        wozwaarde = wozwaardeRepository.save(wozwaarde);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozwaarde.getId().toString()))
            .body(wozwaarde);
    }

    /**
     * {@code PATCH  /wozwaardes/:id} : Partial updates given fields of an existing wozwaarde, field will ignore if it is null
     *
     * @param id the id of the wozwaarde to save.
     * @param wozwaarde the wozwaarde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wozwaarde,
     * or with status {@code 400 (Bad Request)} if the wozwaarde is not valid,
     * or with status {@code 404 (Not Found)} if the wozwaarde is not found,
     * or with status {@code 500 (Internal Server Error)} if the wozwaarde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wozwaarde> partialUpdateWozwaarde(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wozwaarde wozwaarde
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wozwaarde partially : {}, {}", id, wozwaarde);
        if (wozwaarde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wozwaarde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wozwaardeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wozwaarde> result = wozwaardeRepository
            .findById(wozwaarde.getId())
            .map(existingWozwaarde -> {
                if (wozwaarde.getDatumpeilingtoestand() != null) {
                    existingWozwaarde.setDatumpeilingtoestand(wozwaarde.getDatumpeilingtoestand());
                }
                if (wozwaarde.getDatumwaardepeiling() != null) {
                    existingWozwaarde.setDatumwaardepeiling(wozwaarde.getDatumwaardepeiling());
                }
                if (wozwaarde.getStatusbeschikking() != null) {
                    existingWozwaarde.setStatusbeschikking(wozwaarde.getStatusbeschikking());
                }
                if (wozwaarde.getVastgesteldewaarde() != null) {
                    existingWozwaarde.setVastgesteldewaarde(wozwaarde.getVastgesteldewaarde());
                }

                return existingWozwaarde;
            })
            .map(wozwaardeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wozwaarde.getId().toString())
        );
    }

    /**
     * {@code GET  /wozwaardes} : get all the wozwaardes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wozwaardes in body.
     */
    @GetMapping("")
    public List<Wozwaarde> getAllWozwaardes() {
        log.debug("REST request to get all Wozwaardes");
        return wozwaardeRepository.findAll();
    }

    /**
     * {@code GET  /wozwaardes/:id} : get the "id" wozwaarde.
     *
     * @param id the id of the wozwaarde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wozwaarde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wozwaarde> getWozwaarde(@PathVariable("id") Long id) {
        log.debug("REST request to get Wozwaarde : {}", id);
        Optional<Wozwaarde> wozwaarde = wozwaardeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wozwaarde);
    }

    /**
     * {@code DELETE  /wozwaardes/:id} : delete the "id" wozwaarde.
     *
     * @param id the id of the wozwaarde to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWozwaarde(@PathVariable("id") Long id) {
        log.debug("REST request to delete Wozwaarde : {}", id);
        wozwaardeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
