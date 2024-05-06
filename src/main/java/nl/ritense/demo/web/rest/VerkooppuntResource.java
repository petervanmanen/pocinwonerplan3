package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verkooppunt;
import nl.ritense.demo.repository.VerkooppuntRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verkooppunt}.
 */
@RestController
@RequestMapping("/api/verkooppunts")
@Transactional
public class VerkooppuntResource {

    private final Logger log = LoggerFactory.getLogger(VerkooppuntResource.class);

    private static final String ENTITY_NAME = "verkooppunt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerkooppuntRepository verkooppuntRepository;

    public VerkooppuntResource(VerkooppuntRepository verkooppuntRepository) {
        this.verkooppuntRepository = verkooppuntRepository;
    }

    /**
     * {@code POST  /verkooppunts} : Create a new verkooppunt.
     *
     * @param verkooppunt the verkooppunt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verkooppunt, or with status {@code 400 (Bad Request)} if the verkooppunt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verkooppunt> createVerkooppunt(@RequestBody Verkooppunt verkooppunt) throws URISyntaxException {
        log.debug("REST request to save Verkooppunt : {}", verkooppunt);
        if (verkooppunt.getId() != null) {
            throw new BadRequestAlertException("A new verkooppunt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verkooppunt = verkooppuntRepository.save(verkooppunt);
        return ResponseEntity.created(new URI("/api/verkooppunts/" + verkooppunt.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verkooppunt.getId().toString()))
            .body(verkooppunt);
    }

    /**
     * {@code PUT  /verkooppunts/:id} : Updates an existing verkooppunt.
     *
     * @param id the id of the verkooppunt to save.
     * @param verkooppunt the verkooppunt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verkooppunt,
     * or with status {@code 400 (Bad Request)} if the verkooppunt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verkooppunt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verkooppunt> updateVerkooppunt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verkooppunt verkooppunt
    ) throws URISyntaxException {
        log.debug("REST request to update Verkooppunt : {}, {}", id, verkooppunt);
        if (verkooppunt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verkooppunt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verkooppuntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verkooppunt = verkooppuntRepository.save(verkooppunt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verkooppunt.getId().toString()))
            .body(verkooppunt);
    }

    /**
     * {@code PATCH  /verkooppunts/:id} : Partial updates given fields of an existing verkooppunt, field will ignore if it is null
     *
     * @param id the id of the verkooppunt to save.
     * @param verkooppunt the verkooppunt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verkooppunt,
     * or with status {@code 400 (Bad Request)} if the verkooppunt is not valid,
     * or with status {@code 404 (Not Found)} if the verkooppunt is not found,
     * or with status {@code 500 (Internal Server Error)} if the verkooppunt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verkooppunt> partialUpdateVerkooppunt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verkooppunt verkooppunt
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verkooppunt partially : {}, {}", id, verkooppunt);
        if (verkooppunt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verkooppunt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verkooppuntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verkooppunt> result = verkooppuntRepository
            .findById(verkooppunt.getId())
            .map(existingVerkooppunt -> {
                if (verkooppunt.getWinkelformule() != null) {
                    existingVerkooppunt.setWinkelformule(verkooppunt.getWinkelformule());
                }

                return existingVerkooppunt;
            })
            .map(verkooppuntRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verkooppunt.getId().toString())
        );
    }

    /**
     * {@code GET  /verkooppunts} : get all the verkooppunts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verkooppunts in body.
     */
    @GetMapping("")
    public List<Verkooppunt> getAllVerkooppunts() {
        log.debug("REST request to get all Verkooppunts");
        return verkooppuntRepository.findAll();
    }

    /**
     * {@code GET  /verkooppunts/:id} : get the "id" verkooppunt.
     *
     * @param id the id of the verkooppunt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verkooppunt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verkooppunt> getVerkooppunt(@PathVariable("id") Long id) {
        log.debug("REST request to get Verkooppunt : {}", id);
        Optional<Verkooppunt> verkooppunt = verkooppuntRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verkooppunt);
    }

    /**
     * {@code DELETE  /verkooppunts/:id} : delete the "id" verkooppunt.
     *
     * @param id the id of the verkooppunt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerkooppunt(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verkooppunt : {}", id);
        verkooppuntRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
