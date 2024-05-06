package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Afwijkendbuitenlandscorrespondentieadresrol;
import nl.ritense.demo.repository.AfwijkendbuitenlandscorrespondentieadresrolRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Afwijkendbuitenlandscorrespondentieadresrol}.
 */
@RestController
@RequestMapping("/api/afwijkendbuitenlandscorrespondentieadresrols")
@Transactional
public class AfwijkendbuitenlandscorrespondentieadresrolResource {

    private final Logger log = LoggerFactory.getLogger(AfwijkendbuitenlandscorrespondentieadresrolResource.class);

    private static final String ENTITY_NAME = "afwijkendbuitenlandscorrespondentieadresrol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AfwijkendbuitenlandscorrespondentieadresrolRepository afwijkendbuitenlandscorrespondentieadresrolRepository;

    public AfwijkendbuitenlandscorrespondentieadresrolResource(
        AfwijkendbuitenlandscorrespondentieadresrolRepository afwijkendbuitenlandscorrespondentieadresrolRepository
    ) {
        this.afwijkendbuitenlandscorrespondentieadresrolRepository = afwijkendbuitenlandscorrespondentieadresrolRepository;
    }

    /**
     * {@code POST  /afwijkendbuitenlandscorrespondentieadresrols} : Create a new afwijkendbuitenlandscorrespondentieadresrol.
     *
     * @param afwijkendbuitenlandscorrespondentieadresrol the afwijkendbuitenlandscorrespondentieadresrol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new afwijkendbuitenlandscorrespondentieadresrol, or with status {@code 400 (Bad Request)} if the afwijkendbuitenlandscorrespondentieadresrol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Afwijkendbuitenlandscorrespondentieadresrol> createAfwijkendbuitenlandscorrespondentieadresrol(
        @RequestBody Afwijkendbuitenlandscorrespondentieadresrol afwijkendbuitenlandscorrespondentieadresrol
    ) throws URISyntaxException {
        log.debug("REST request to save Afwijkendbuitenlandscorrespondentieadresrol : {}", afwijkendbuitenlandscorrespondentieadresrol);
        if (afwijkendbuitenlandscorrespondentieadresrol.getId() != null) {
            throw new BadRequestAlertException(
                "A new afwijkendbuitenlandscorrespondentieadresrol cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        afwijkendbuitenlandscorrespondentieadresrol = afwijkendbuitenlandscorrespondentieadresrolRepository.save(
            afwijkendbuitenlandscorrespondentieadresrol
        );
        return ResponseEntity.created(
            new URI("/api/afwijkendbuitenlandscorrespondentieadresrols/" + afwijkendbuitenlandscorrespondentieadresrol.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    afwijkendbuitenlandscorrespondentieadresrol.getId().toString()
                )
            )
            .body(afwijkendbuitenlandscorrespondentieadresrol);
    }

    /**
     * {@code PUT  /afwijkendbuitenlandscorrespondentieadresrols/:id} : Updates an existing afwijkendbuitenlandscorrespondentieadresrol.
     *
     * @param id the id of the afwijkendbuitenlandscorrespondentieadresrol to save.
     * @param afwijkendbuitenlandscorrespondentieadresrol the afwijkendbuitenlandscorrespondentieadresrol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afwijkendbuitenlandscorrespondentieadresrol,
     * or with status {@code 400 (Bad Request)} if the afwijkendbuitenlandscorrespondentieadresrol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the afwijkendbuitenlandscorrespondentieadresrol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Afwijkendbuitenlandscorrespondentieadresrol> updateAfwijkendbuitenlandscorrespondentieadresrol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Afwijkendbuitenlandscorrespondentieadresrol afwijkendbuitenlandscorrespondentieadresrol
    ) throws URISyntaxException {
        log.debug(
            "REST request to update Afwijkendbuitenlandscorrespondentieadresrol : {}, {}",
            id,
            afwijkendbuitenlandscorrespondentieadresrol
        );
        if (afwijkendbuitenlandscorrespondentieadresrol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afwijkendbuitenlandscorrespondentieadresrol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afwijkendbuitenlandscorrespondentieadresrolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        afwijkendbuitenlandscorrespondentieadresrol = afwijkendbuitenlandscorrespondentieadresrolRepository.save(
            afwijkendbuitenlandscorrespondentieadresrol
        );
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    afwijkendbuitenlandscorrespondentieadresrol.getId().toString()
                )
            )
            .body(afwijkendbuitenlandscorrespondentieadresrol);
    }

    /**
     * {@code PATCH  /afwijkendbuitenlandscorrespondentieadresrols/:id} : Partial updates given fields of an existing afwijkendbuitenlandscorrespondentieadresrol, field will ignore if it is null
     *
     * @param id the id of the afwijkendbuitenlandscorrespondentieadresrol to save.
     * @param afwijkendbuitenlandscorrespondentieadresrol the afwijkendbuitenlandscorrespondentieadresrol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afwijkendbuitenlandscorrespondentieadresrol,
     * or with status {@code 400 (Bad Request)} if the afwijkendbuitenlandscorrespondentieadresrol is not valid,
     * or with status {@code 404 (Not Found)} if the afwijkendbuitenlandscorrespondentieadresrol is not found,
     * or with status {@code 500 (Internal Server Error)} if the afwijkendbuitenlandscorrespondentieadresrol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Afwijkendbuitenlandscorrespondentieadresrol> partialUpdateAfwijkendbuitenlandscorrespondentieadresrol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Afwijkendbuitenlandscorrespondentieadresrol afwijkendbuitenlandscorrespondentieadresrol
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update Afwijkendbuitenlandscorrespondentieadresrol partially : {}, {}",
            id,
            afwijkendbuitenlandscorrespondentieadresrol
        );
        if (afwijkendbuitenlandscorrespondentieadresrol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afwijkendbuitenlandscorrespondentieadresrol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afwijkendbuitenlandscorrespondentieadresrolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Afwijkendbuitenlandscorrespondentieadresrol> result = afwijkendbuitenlandscorrespondentieadresrolRepository
            .findById(afwijkendbuitenlandscorrespondentieadresrol.getId())
            .map(existingAfwijkendbuitenlandscorrespondentieadresrol -> {
                if (afwijkendbuitenlandscorrespondentieadresrol.getAdresbuitenland1() != null) {
                    existingAfwijkendbuitenlandscorrespondentieadresrol.setAdresbuitenland1(
                        afwijkendbuitenlandscorrespondentieadresrol.getAdresbuitenland1()
                    );
                }
                if (afwijkendbuitenlandscorrespondentieadresrol.getAdresbuitenland2() != null) {
                    existingAfwijkendbuitenlandscorrespondentieadresrol.setAdresbuitenland2(
                        afwijkendbuitenlandscorrespondentieadresrol.getAdresbuitenland2()
                    );
                }
                if (afwijkendbuitenlandscorrespondentieadresrol.getAdresbuitenland3() != null) {
                    existingAfwijkendbuitenlandscorrespondentieadresrol.setAdresbuitenland3(
                        afwijkendbuitenlandscorrespondentieadresrol.getAdresbuitenland3()
                    );
                }
                if (afwijkendbuitenlandscorrespondentieadresrol.getLandpostadres() != null) {
                    existingAfwijkendbuitenlandscorrespondentieadresrol.setLandpostadres(
                        afwijkendbuitenlandscorrespondentieadresrol.getLandpostadres()
                    );
                }

                return existingAfwijkendbuitenlandscorrespondentieadresrol;
            })
            .map(afwijkendbuitenlandscorrespondentieadresrolRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                afwijkendbuitenlandscorrespondentieadresrol.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /afwijkendbuitenlandscorrespondentieadresrols} : get all the afwijkendbuitenlandscorrespondentieadresrols.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of afwijkendbuitenlandscorrespondentieadresrols in body.
     */
    @GetMapping("")
    public List<Afwijkendbuitenlandscorrespondentieadresrol> getAllAfwijkendbuitenlandscorrespondentieadresrols() {
        log.debug("REST request to get all Afwijkendbuitenlandscorrespondentieadresrols");
        return afwijkendbuitenlandscorrespondentieadresrolRepository.findAll();
    }

    /**
     * {@code GET  /afwijkendbuitenlandscorrespondentieadresrols/:id} : get the "id" afwijkendbuitenlandscorrespondentieadresrol.
     *
     * @param id the id of the afwijkendbuitenlandscorrespondentieadresrol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the afwijkendbuitenlandscorrespondentieadresrol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Afwijkendbuitenlandscorrespondentieadresrol> getAfwijkendbuitenlandscorrespondentieadresrol(
        @PathVariable("id") Long id
    ) {
        log.debug("REST request to get Afwijkendbuitenlandscorrespondentieadresrol : {}", id);
        Optional<Afwijkendbuitenlandscorrespondentieadresrol> afwijkendbuitenlandscorrespondentieadresrol =
            afwijkendbuitenlandscorrespondentieadresrolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(afwijkendbuitenlandscorrespondentieadresrol);
    }

    /**
     * {@code DELETE  /afwijkendbuitenlandscorrespondentieadresrols/:id} : delete the "id" afwijkendbuitenlandscorrespondentieadresrol.
     *
     * @param id the id of the afwijkendbuitenlandscorrespondentieadresrol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAfwijkendbuitenlandscorrespondentieadresrol(@PathVariable("id") Long id) {
        log.debug("REST request to delete Afwijkendbuitenlandscorrespondentieadresrol : {}", id);
        afwijkendbuitenlandscorrespondentieadresrolRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
