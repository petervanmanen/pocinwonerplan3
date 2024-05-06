package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aomstatus;
import nl.ritense.demo.repository.AomstatusRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aomstatus}.
 */
@RestController
@RequestMapping("/api/aomstatuses")
@Transactional
public class AomstatusResource {

    private final Logger log = LoggerFactory.getLogger(AomstatusResource.class);

    private static final String ENTITY_NAME = "aomstatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AomstatusRepository aomstatusRepository;

    public AomstatusResource(AomstatusRepository aomstatusRepository) {
        this.aomstatusRepository = aomstatusRepository;
    }

    /**
     * {@code POST  /aomstatuses} : Create a new aomstatus.
     *
     * @param aomstatus the aomstatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aomstatus, or with status {@code 400 (Bad Request)} if the aomstatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aomstatus> createAomstatus(@RequestBody Aomstatus aomstatus) throws URISyntaxException {
        log.debug("REST request to save Aomstatus : {}", aomstatus);
        if (aomstatus.getId() != null) {
            throw new BadRequestAlertException("A new aomstatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aomstatus = aomstatusRepository.save(aomstatus);
        return ResponseEntity.created(new URI("/api/aomstatuses/" + aomstatus.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aomstatus.getId().toString()))
            .body(aomstatus);
    }

    /**
     * {@code PUT  /aomstatuses/:id} : Updates an existing aomstatus.
     *
     * @param id the id of the aomstatus to save.
     * @param aomstatus the aomstatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aomstatus,
     * or with status {@code 400 (Bad Request)} if the aomstatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aomstatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aomstatus> updateAomstatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aomstatus aomstatus
    ) throws URISyntaxException {
        log.debug("REST request to update Aomstatus : {}, {}", id, aomstatus);
        if (aomstatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aomstatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aomstatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aomstatus = aomstatusRepository.save(aomstatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aomstatus.getId().toString()))
            .body(aomstatus);
    }

    /**
     * {@code PATCH  /aomstatuses/:id} : Partial updates given fields of an existing aomstatus, field will ignore if it is null
     *
     * @param id the id of the aomstatus to save.
     * @param aomstatus the aomstatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aomstatus,
     * or with status {@code 400 (Bad Request)} if the aomstatus is not valid,
     * or with status {@code 404 (Not Found)} if the aomstatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the aomstatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aomstatus> partialUpdateAomstatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aomstatus aomstatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aomstatus partially : {}, {}", id, aomstatus);
        if (aomstatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aomstatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aomstatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aomstatus> result = aomstatusRepository
            .findById(aomstatus.getId())
            .map(existingAomstatus -> {
                if (aomstatus.getDatumbeginstatus() != null) {
                    existingAomstatus.setDatumbeginstatus(aomstatus.getDatumbeginstatus());
                }
                if (aomstatus.getDatumeindestatus() != null) {
                    existingAomstatus.setDatumeindestatus(aomstatus.getDatumeindestatus());
                }
                if (aomstatus.getStatus() != null) {
                    existingAomstatus.setStatus(aomstatus.getStatus());
                }
                if (aomstatus.getStatuscode() != null) {
                    existingAomstatus.setStatuscode(aomstatus.getStatuscode());
                }
                if (aomstatus.getStatusvolgorde() != null) {
                    existingAomstatus.setStatusvolgorde(aomstatus.getStatusvolgorde());
                }

                return existingAomstatus;
            })
            .map(aomstatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aomstatus.getId().toString())
        );
    }

    /**
     * {@code GET  /aomstatuses} : get all the aomstatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aomstatuses in body.
     */
    @GetMapping("")
    public List<Aomstatus> getAllAomstatuses() {
        log.debug("REST request to get all Aomstatuses");
        return aomstatusRepository.findAll();
    }

    /**
     * {@code GET  /aomstatuses/:id} : get the "id" aomstatus.
     *
     * @param id the id of the aomstatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aomstatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aomstatus> getAomstatus(@PathVariable("id") Long id) {
        log.debug("REST request to get Aomstatus : {}", id);
        Optional<Aomstatus> aomstatus = aomstatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aomstatus);
    }

    /**
     * {@code DELETE  /aomstatuses/:id} : delete the "id" aomstatus.
     *
     * @param id the id of the aomstatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAomstatus(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aomstatus : {}", id);
        aomstatusRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
