package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Belanghebbende;
import nl.ritense.demo.repository.BelanghebbendeRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Belanghebbende}.
 */
@RestController
@RequestMapping("/api/belanghebbendes")
@Transactional
public class BelanghebbendeResource {

    private final Logger log = LoggerFactory.getLogger(BelanghebbendeResource.class);

    private static final String ENTITY_NAME = "belanghebbende";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BelanghebbendeRepository belanghebbendeRepository;

    public BelanghebbendeResource(BelanghebbendeRepository belanghebbendeRepository) {
        this.belanghebbendeRepository = belanghebbendeRepository;
    }

    /**
     * {@code POST  /belanghebbendes} : Create a new belanghebbende.
     *
     * @param belanghebbende the belanghebbende to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new belanghebbende, or with status {@code 400 (Bad Request)} if the belanghebbende has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Belanghebbende> createBelanghebbende(@RequestBody Belanghebbende belanghebbende) throws URISyntaxException {
        log.debug("REST request to save Belanghebbende : {}", belanghebbende);
        if (belanghebbende.getId() != null) {
            throw new BadRequestAlertException("A new belanghebbende cannot already have an ID", ENTITY_NAME, "idexists");
        }
        belanghebbende = belanghebbendeRepository.save(belanghebbende);
        return ResponseEntity.created(new URI("/api/belanghebbendes/" + belanghebbende.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, belanghebbende.getId().toString()))
            .body(belanghebbende);
    }

    /**
     * {@code PUT  /belanghebbendes/:id} : Updates an existing belanghebbende.
     *
     * @param id the id of the belanghebbende to save.
     * @param belanghebbende the belanghebbende to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated belanghebbende,
     * or with status {@code 400 (Bad Request)} if the belanghebbende is not valid,
     * or with status {@code 500 (Internal Server Error)} if the belanghebbende couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Belanghebbende> updateBelanghebbende(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Belanghebbende belanghebbende
    ) throws URISyntaxException {
        log.debug("REST request to update Belanghebbende : {}, {}", id, belanghebbende);
        if (belanghebbende.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, belanghebbende.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!belanghebbendeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        belanghebbende = belanghebbendeRepository.save(belanghebbende);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, belanghebbende.getId().toString()))
            .body(belanghebbende);
    }

    /**
     * {@code PATCH  /belanghebbendes/:id} : Partial updates given fields of an existing belanghebbende, field will ignore if it is null
     *
     * @param id the id of the belanghebbende to save.
     * @param belanghebbende the belanghebbende to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated belanghebbende,
     * or with status {@code 400 (Bad Request)} if the belanghebbende is not valid,
     * or with status {@code 404 (Not Found)} if the belanghebbende is not found,
     * or with status {@code 500 (Internal Server Error)} if the belanghebbende couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Belanghebbende> partialUpdateBelanghebbende(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Belanghebbende belanghebbende
    ) throws URISyntaxException {
        log.debug("REST request to partial update Belanghebbende partially : {}, {}", id, belanghebbende);
        if (belanghebbende.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, belanghebbende.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!belanghebbendeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Belanghebbende> result = belanghebbendeRepository
            .findById(belanghebbende.getId())
            .map(existingBelanghebbende -> {
                if (belanghebbende.getDatumstart() != null) {
                    existingBelanghebbende.setDatumstart(belanghebbende.getDatumstart());
                }
                if (belanghebbende.getDatumtot() != null) {
                    existingBelanghebbende.setDatumtot(belanghebbende.getDatumtot());
                }

                return existingBelanghebbende;
            })
            .map(belanghebbendeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, belanghebbende.getId().toString())
        );
    }

    /**
     * {@code GET  /belanghebbendes} : get all the belanghebbendes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of belanghebbendes in body.
     */
    @GetMapping("")
    public List<Belanghebbende> getAllBelanghebbendes() {
        log.debug("REST request to get all Belanghebbendes");
        return belanghebbendeRepository.findAll();
    }

    /**
     * {@code GET  /belanghebbendes/:id} : get the "id" belanghebbende.
     *
     * @param id the id of the belanghebbende to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the belanghebbende, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Belanghebbende> getBelanghebbende(@PathVariable("id") Long id) {
        log.debug("REST request to get Belanghebbende : {}", id);
        Optional<Belanghebbende> belanghebbende = belanghebbendeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(belanghebbende);
    }

    /**
     * {@code DELETE  /belanghebbendes/:id} : delete the "id" belanghebbende.
     *
     * @param id the id of the belanghebbende to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBelanghebbende(@PathVariable("id") Long id) {
        log.debug("REST request to delete Belanghebbende : {}", id);
        belanghebbendeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
