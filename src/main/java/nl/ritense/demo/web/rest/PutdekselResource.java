package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Putdeksel;
import nl.ritense.demo.repository.PutdekselRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Putdeksel}.
 */
@RestController
@RequestMapping("/api/putdeksels")
@Transactional
public class PutdekselResource {

    private final Logger log = LoggerFactory.getLogger(PutdekselResource.class);

    private static final String ENTITY_NAME = "putdeksel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PutdekselRepository putdekselRepository;

    public PutdekselResource(PutdekselRepository putdekselRepository) {
        this.putdekselRepository = putdekselRepository;
    }

    /**
     * {@code POST  /putdeksels} : Create a new putdeksel.
     *
     * @param putdeksel the putdeksel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new putdeksel, or with status {@code 400 (Bad Request)} if the putdeksel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Putdeksel> createPutdeksel(@RequestBody Putdeksel putdeksel) throws URISyntaxException {
        log.debug("REST request to save Putdeksel : {}", putdeksel);
        if (putdeksel.getId() != null) {
            throw new BadRequestAlertException("A new putdeksel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        putdeksel = putdekselRepository.save(putdeksel);
        return ResponseEntity.created(new URI("/api/putdeksels/" + putdeksel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, putdeksel.getId().toString()))
            .body(putdeksel);
    }

    /**
     * {@code PUT  /putdeksels/:id} : Updates an existing putdeksel.
     *
     * @param id the id of the putdeksel to save.
     * @param putdeksel the putdeksel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated putdeksel,
     * or with status {@code 400 (Bad Request)} if the putdeksel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the putdeksel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Putdeksel> updatePutdeksel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Putdeksel putdeksel
    ) throws URISyntaxException {
        log.debug("REST request to update Putdeksel : {}, {}", id, putdeksel);
        if (putdeksel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, putdeksel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!putdekselRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        putdeksel = putdekselRepository.save(putdeksel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, putdeksel.getId().toString()))
            .body(putdeksel);
    }

    /**
     * {@code PATCH  /putdeksels/:id} : Partial updates given fields of an existing putdeksel, field will ignore if it is null
     *
     * @param id the id of the putdeksel to save.
     * @param putdeksel the putdeksel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated putdeksel,
     * or with status {@code 400 (Bad Request)} if the putdeksel is not valid,
     * or with status {@code 404 (Not Found)} if the putdeksel is not found,
     * or with status {@code 500 (Internal Server Error)} if the putdeksel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Putdeksel> partialUpdatePutdeksel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Putdeksel putdeksel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Putdeksel partially : {}, {}", id, putdeksel);
        if (putdeksel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, putdeksel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!putdekselRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Putdeksel> result = putdekselRepository
            .findById(putdeksel.getId())
            .map(existingPutdeksel -> {
                if (putdeksel.getDiameter() != null) {
                    existingPutdeksel.setDiameter(putdeksel.getDiameter());
                }
                if (putdeksel.getPut() != null) {
                    existingPutdeksel.setPut(putdeksel.getPut());
                }
                if (putdeksel.getType() != null) {
                    existingPutdeksel.setType(putdeksel.getType());
                }
                if (putdeksel.getVorm() != null) {
                    existingPutdeksel.setVorm(putdeksel.getVorm());
                }

                return existingPutdeksel;
            })
            .map(putdekselRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, putdeksel.getId().toString())
        );
    }

    /**
     * {@code GET  /putdeksels} : get all the putdeksels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of putdeksels in body.
     */
    @GetMapping("")
    public List<Putdeksel> getAllPutdeksels() {
        log.debug("REST request to get all Putdeksels");
        return putdekselRepository.findAll();
    }

    /**
     * {@code GET  /putdeksels/:id} : get the "id" putdeksel.
     *
     * @param id the id of the putdeksel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the putdeksel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Putdeksel> getPutdeksel(@PathVariable("id") Long id) {
        log.debug("REST request to get Putdeksel : {}", id);
        Optional<Putdeksel> putdeksel = putdekselRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(putdeksel);
    }

    /**
     * {@code DELETE  /putdeksels/:id} : delete the "id" putdeksel.
     *
     * @param id the id of the putdeksel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePutdeksel(@PathVariable("id") Long id) {
        log.debug("REST request to delete Putdeksel : {}", id);
        putdekselRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
