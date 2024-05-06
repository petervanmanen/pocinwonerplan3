package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Verkeerstelling;
import nl.ritense.demo.repository.VerkeerstellingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Verkeerstelling}.
 */
@RestController
@RequestMapping("/api/verkeerstellings")
@Transactional
public class VerkeerstellingResource {

    private final Logger log = LoggerFactory.getLogger(VerkeerstellingResource.class);

    private static final String ENTITY_NAME = "verkeerstelling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerkeerstellingRepository verkeerstellingRepository;

    public VerkeerstellingResource(VerkeerstellingRepository verkeerstellingRepository) {
        this.verkeerstellingRepository = verkeerstellingRepository;
    }

    /**
     * {@code POST  /verkeerstellings} : Create a new verkeerstelling.
     *
     * @param verkeerstelling the verkeerstelling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verkeerstelling, or with status {@code 400 (Bad Request)} if the verkeerstelling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Verkeerstelling> createVerkeerstelling(@RequestBody Verkeerstelling verkeerstelling) throws URISyntaxException {
        log.debug("REST request to save Verkeerstelling : {}", verkeerstelling);
        if (verkeerstelling.getId() != null) {
            throw new BadRequestAlertException("A new verkeerstelling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        verkeerstelling = verkeerstellingRepository.save(verkeerstelling);
        return ResponseEntity.created(new URI("/api/verkeerstellings/" + verkeerstelling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, verkeerstelling.getId().toString()))
            .body(verkeerstelling);
    }

    /**
     * {@code PUT  /verkeerstellings/:id} : Updates an existing verkeerstelling.
     *
     * @param id the id of the verkeerstelling to save.
     * @param verkeerstelling the verkeerstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verkeerstelling,
     * or with status {@code 400 (Bad Request)} if the verkeerstelling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verkeerstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Verkeerstelling> updateVerkeerstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verkeerstelling verkeerstelling
    ) throws URISyntaxException {
        log.debug("REST request to update Verkeerstelling : {}, {}", id, verkeerstelling);
        if (verkeerstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verkeerstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verkeerstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        verkeerstelling = verkeerstellingRepository.save(verkeerstelling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verkeerstelling.getId().toString()))
            .body(verkeerstelling);
    }

    /**
     * {@code PATCH  /verkeerstellings/:id} : Partial updates given fields of an existing verkeerstelling, field will ignore if it is null
     *
     * @param id the id of the verkeerstelling to save.
     * @param verkeerstelling the verkeerstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verkeerstelling,
     * or with status {@code 400 (Bad Request)} if the verkeerstelling is not valid,
     * or with status {@code 404 (Not Found)} if the verkeerstelling is not found,
     * or with status {@code 500 (Internal Server Error)} if the verkeerstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Verkeerstelling> partialUpdateVerkeerstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verkeerstelling verkeerstelling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verkeerstelling partially : {}, {}", id, verkeerstelling);
        if (verkeerstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verkeerstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verkeerstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verkeerstelling> result = verkeerstellingRepository
            .findById(verkeerstelling.getId())
            .map(existingVerkeerstelling -> {
                if (verkeerstelling.getAantal() != null) {
                    existingVerkeerstelling.setAantal(verkeerstelling.getAantal());
                }
                if (verkeerstelling.getTijdtot() != null) {
                    existingVerkeerstelling.setTijdtot(verkeerstelling.getTijdtot());
                }
                if (verkeerstelling.getTijdvanaf() != null) {
                    existingVerkeerstelling.setTijdvanaf(verkeerstelling.getTijdvanaf());
                }

                return existingVerkeerstelling;
            })
            .map(verkeerstellingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verkeerstelling.getId().toString())
        );
    }

    /**
     * {@code GET  /verkeerstellings} : get all the verkeerstellings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verkeerstellings in body.
     */
    @GetMapping("")
    public List<Verkeerstelling> getAllVerkeerstellings() {
        log.debug("REST request to get all Verkeerstellings");
        return verkeerstellingRepository.findAll();
    }

    /**
     * {@code GET  /verkeerstellings/:id} : get the "id" verkeerstelling.
     *
     * @param id the id of the verkeerstelling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verkeerstelling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Verkeerstelling> getVerkeerstelling(@PathVariable("id") Long id) {
        log.debug("REST request to get Verkeerstelling : {}", id);
        Optional<Verkeerstelling> verkeerstelling = verkeerstellingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verkeerstelling);
    }

    /**
     * {@code DELETE  /verkeerstellings/:id} : delete the "id" verkeerstelling.
     *
     * @param id the id of the verkeerstelling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerkeerstelling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Verkeerstelling : {}", id);
        verkeerstellingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
