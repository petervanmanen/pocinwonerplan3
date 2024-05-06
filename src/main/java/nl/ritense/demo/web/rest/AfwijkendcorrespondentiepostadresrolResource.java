package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Afwijkendcorrespondentiepostadresrol;
import nl.ritense.demo.repository.AfwijkendcorrespondentiepostadresrolRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Afwijkendcorrespondentiepostadresrol}.
 */
@RestController
@RequestMapping("/api/afwijkendcorrespondentiepostadresrols")
@Transactional
public class AfwijkendcorrespondentiepostadresrolResource {

    private final Logger log = LoggerFactory.getLogger(AfwijkendcorrespondentiepostadresrolResource.class);

    private static final String ENTITY_NAME = "afwijkendcorrespondentiepostadresrol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AfwijkendcorrespondentiepostadresrolRepository afwijkendcorrespondentiepostadresrolRepository;

    public AfwijkendcorrespondentiepostadresrolResource(
        AfwijkendcorrespondentiepostadresrolRepository afwijkendcorrespondentiepostadresrolRepository
    ) {
        this.afwijkendcorrespondentiepostadresrolRepository = afwijkendcorrespondentiepostadresrolRepository;
    }

    /**
     * {@code POST  /afwijkendcorrespondentiepostadresrols} : Create a new afwijkendcorrespondentiepostadresrol.
     *
     * @param afwijkendcorrespondentiepostadresrol the afwijkendcorrespondentiepostadresrol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new afwijkendcorrespondentiepostadresrol, or with status {@code 400 (Bad Request)} if the afwijkendcorrespondentiepostadresrol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Afwijkendcorrespondentiepostadresrol> createAfwijkendcorrespondentiepostadresrol(
        @RequestBody Afwijkendcorrespondentiepostadresrol afwijkendcorrespondentiepostadresrol
    ) throws URISyntaxException {
        log.debug("REST request to save Afwijkendcorrespondentiepostadresrol : {}", afwijkendcorrespondentiepostadresrol);
        if (afwijkendcorrespondentiepostadresrol.getId() != null) {
            throw new BadRequestAlertException(
                "A new afwijkendcorrespondentiepostadresrol cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        afwijkendcorrespondentiepostadresrol = afwijkendcorrespondentiepostadresrolRepository.save(afwijkendcorrespondentiepostadresrol);
        return ResponseEntity.created(new URI("/api/afwijkendcorrespondentiepostadresrols/" + afwijkendcorrespondentiepostadresrol.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    afwijkendcorrespondentiepostadresrol.getId().toString()
                )
            )
            .body(afwijkendcorrespondentiepostadresrol);
    }

    /**
     * {@code PUT  /afwijkendcorrespondentiepostadresrols/:id} : Updates an existing afwijkendcorrespondentiepostadresrol.
     *
     * @param id the id of the afwijkendcorrespondentiepostadresrol to save.
     * @param afwijkendcorrespondentiepostadresrol the afwijkendcorrespondentiepostadresrol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afwijkendcorrespondentiepostadresrol,
     * or with status {@code 400 (Bad Request)} if the afwijkendcorrespondentiepostadresrol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the afwijkendcorrespondentiepostadresrol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Afwijkendcorrespondentiepostadresrol> updateAfwijkendcorrespondentiepostadresrol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Afwijkendcorrespondentiepostadresrol afwijkendcorrespondentiepostadresrol
    ) throws URISyntaxException {
        log.debug("REST request to update Afwijkendcorrespondentiepostadresrol : {}, {}", id, afwijkendcorrespondentiepostadresrol);
        if (afwijkendcorrespondentiepostadresrol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afwijkendcorrespondentiepostadresrol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afwijkendcorrespondentiepostadresrolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        afwijkendcorrespondentiepostadresrol = afwijkendcorrespondentiepostadresrolRepository.save(afwijkendcorrespondentiepostadresrol);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    afwijkendcorrespondentiepostadresrol.getId().toString()
                )
            )
            .body(afwijkendcorrespondentiepostadresrol);
    }

    /**
     * {@code PATCH  /afwijkendcorrespondentiepostadresrols/:id} : Partial updates given fields of an existing afwijkendcorrespondentiepostadresrol, field will ignore if it is null
     *
     * @param id the id of the afwijkendcorrespondentiepostadresrol to save.
     * @param afwijkendcorrespondentiepostadresrol the afwijkendcorrespondentiepostadresrol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afwijkendcorrespondentiepostadresrol,
     * or with status {@code 400 (Bad Request)} if the afwijkendcorrespondentiepostadresrol is not valid,
     * or with status {@code 404 (Not Found)} if the afwijkendcorrespondentiepostadresrol is not found,
     * or with status {@code 500 (Internal Server Error)} if the afwijkendcorrespondentiepostadresrol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Afwijkendcorrespondentiepostadresrol> partialUpdateAfwijkendcorrespondentiepostadresrol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Afwijkendcorrespondentiepostadresrol afwijkendcorrespondentiepostadresrol
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Afwijkendcorrespondentiepostadresrol partially : {}, {}",
            id,
            afwijkendcorrespondentiepostadresrol
        );
        if (afwijkendcorrespondentiepostadresrol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afwijkendcorrespondentiepostadresrol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afwijkendcorrespondentiepostadresrolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Afwijkendcorrespondentiepostadresrol> result = afwijkendcorrespondentiepostadresrolRepository
            .findById(afwijkendcorrespondentiepostadresrol.getId())
            .map(existingAfwijkendcorrespondentiepostadresrol -> {
                if (afwijkendcorrespondentiepostadresrol.getPostadrestype() != null) {
                    existingAfwijkendcorrespondentiepostadresrol.setPostadrestype(afwijkendcorrespondentiepostadresrol.getPostadrestype());
                }
                if (afwijkendcorrespondentiepostadresrol.getPostbusofantwoordnummer() != null) {
                    existingAfwijkendcorrespondentiepostadresrol.setPostbusofantwoordnummer(
                        afwijkendcorrespondentiepostadresrol.getPostbusofantwoordnummer()
                    );
                }
                if (afwijkendcorrespondentiepostadresrol.getPostcodepostadres() != null) {
                    existingAfwijkendcorrespondentiepostadresrol.setPostcodepostadres(
                        afwijkendcorrespondentiepostadresrol.getPostcodepostadres()
                    );
                }

                return existingAfwijkendcorrespondentiepostadresrol;
            })
            .map(afwijkendcorrespondentiepostadresrolRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, afwijkendcorrespondentiepostadresrol.getId().toString())
        );
    }

    /**
     * {@code GET  /afwijkendcorrespondentiepostadresrols} : get all the afwijkendcorrespondentiepostadresrols.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of afwijkendcorrespondentiepostadresrols in body.
     */
    @GetMapping("")
    public List<Afwijkendcorrespondentiepostadresrol> getAllAfwijkendcorrespondentiepostadresrols() {
        log.debug("REST request to get all Afwijkendcorrespondentiepostadresrols");
        return afwijkendcorrespondentiepostadresrolRepository.findAll();
    }

    /**
     * {@code GET  /afwijkendcorrespondentiepostadresrols/:id} : get the "id" afwijkendcorrespondentiepostadresrol.
     *
     * @param id the id of the afwijkendcorrespondentiepostadresrol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the afwijkendcorrespondentiepostadresrol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Afwijkendcorrespondentiepostadresrol> getAfwijkendcorrespondentiepostadresrol(@PathVariable("id") Long id) {
        log.debug("REST request to get Afwijkendcorrespondentiepostadresrol : {}", id);
        Optional<Afwijkendcorrespondentiepostadresrol> afwijkendcorrespondentiepostadresrol =
            afwijkendcorrespondentiepostadresrolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(afwijkendcorrespondentiepostadresrol);
    }

    /**
     * {@code DELETE  /afwijkendcorrespondentiepostadresrols/:id} : delete the "id" afwijkendcorrespondentiepostadresrol.
     *
     * @param id the id of the afwijkendcorrespondentiepostadresrol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAfwijkendcorrespondentiepostadresrol(@PathVariable("id") Long id) {
        log.debug("REST request to delete Afwijkendcorrespondentiepostadresrol : {}", id);
        afwijkendcorrespondentiepostadresrolRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
