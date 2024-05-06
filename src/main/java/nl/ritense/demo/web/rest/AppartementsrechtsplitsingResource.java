package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Appartementsrechtsplitsing;
import nl.ritense.demo.repository.AppartementsrechtsplitsingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Appartementsrechtsplitsing}.
 */
@RestController
@RequestMapping("/api/appartementsrechtsplitsings")
@Transactional
public class AppartementsrechtsplitsingResource {

    private final Logger log = LoggerFactory.getLogger(AppartementsrechtsplitsingResource.class);

    private static final String ENTITY_NAME = "appartementsrechtsplitsing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppartementsrechtsplitsingRepository appartementsrechtsplitsingRepository;

    public AppartementsrechtsplitsingResource(AppartementsrechtsplitsingRepository appartementsrechtsplitsingRepository) {
        this.appartementsrechtsplitsingRepository = appartementsrechtsplitsingRepository;
    }

    /**
     * {@code POST  /appartementsrechtsplitsings} : Create a new appartementsrechtsplitsing.
     *
     * @param appartementsrechtsplitsing the appartementsrechtsplitsing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appartementsrechtsplitsing, or with status {@code 400 (Bad Request)} if the appartementsrechtsplitsing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Appartementsrechtsplitsing> createAppartementsrechtsplitsing(
        @RequestBody Appartementsrechtsplitsing appartementsrechtsplitsing
    ) throws URISyntaxException {
        log.debug("REST request to save Appartementsrechtsplitsing : {}", appartementsrechtsplitsing);
        if (appartementsrechtsplitsing.getId() != null) {
            throw new BadRequestAlertException("A new appartementsrechtsplitsing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appartementsrechtsplitsing = appartementsrechtsplitsingRepository.save(appartementsrechtsplitsing);
        return ResponseEntity.created(new URI("/api/appartementsrechtsplitsings/" + appartementsrechtsplitsing.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appartementsrechtsplitsing.getId().toString())
            )
            .body(appartementsrechtsplitsing);
    }

    /**
     * {@code PUT  /appartementsrechtsplitsings/:id} : Updates an existing appartementsrechtsplitsing.
     *
     * @param id the id of the appartementsrechtsplitsing to save.
     * @param appartementsrechtsplitsing the appartementsrechtsplitsing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appartementsrechtsplitsing,
     * or with status {@code 400 (Bad Request)} if the appartementsrechtsplitsing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appartementsrechtsplitsing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appartementsrechtsplitsing> updateAppartementsrechtsplitsing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appartementsrechtsplitsing appartementsrechtsplitsing
    ) throws URISyntaxException {
        log.debug("REST request to update Appartementsrechtsplitsing : {}, {}", id, appartementsrechtsplitsing);
        if (appartementsrechtsplitsing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appartementsrechtsplitsing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appartementsrechtsplitsingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appartementsrechtsplitsing = appartementsrechtsplitsingRepository.save(appartementsrechtsplitsing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appartementsrechtsplitsing.getId().toString()))
            .body(appartementsrechtsplitsing);
    }

    /**
     * {@code PATCH  /appartementsrechtsplitsings/:id} : Partial updates given fields of an existing appartementsrechtsplitsing, field will ignore if it is null
     *
     * @param id the id of the appartementsrechtsplitsing to save.
     * @param appartementsrechtsplitsing the appartementsrechtsplitsing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appartementsrechtsplitsing,
     * or with status {@code 400 (Bad Request)} if the appartementsrechtsplitsing is not valid,
     * or with status {@code 404 (Not Found)} if the appartementsrechtsplitsing is not found,
     * or with status {@code 500 (Internal Server Error)} if the appartementsrechtsplitsing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Appartementsrechtsplitsing> partialUpdateAppartementsrechtsplitsing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appartementsrechtsplitsing appartementsrechtsplitsing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Appartementsrechtsplitsing partially : {}, {}", id, appartementsrechtsplitsing);
        if (appartementsrechtsplitsing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appartementsrechtsplitsing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appartementsrechtsplitsingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Appartementsrechtsplitsing> result = appartementsrechtsplitsingRepository
            .findById(appartementsrechtsplitsing.getId())
            .map(existingAppartementsrechtsplitsing -> {
                if (appartementsrechtsplitsing.getDdentificatieappartementsrechtsplitsing() != null) {
                    existingAppartementsrechtsplitsing.setDdentificatieappartementsrechtsplitsing(
                        appartementsrechtsplitsing.getDdentificatieappartementsrechtsplitsing()
                    );
                }
                if (appartementsrechtsplitsing.getTypesplitsing() != null) {
                    existingAppartementsrechtsplitsing.setTypesplitsing(appartementsrechtsplitsing.getTypesplitsing());
                }

                return existingAppartementsrechtsplitsing;
            })
            .map(appartementsrechtsplitsingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appartementsrechtsplitsing.getId().toString())
        );
    }

    /**
     * {@code GET  /appartementsrechtsplitsings} : get all the appartementsrechtsplitsings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appartementsrechtsplitsings in body.
     */
    @GetMapping("")
    public List<Appartementsrechtsplitsing> getAllAppartementsrechtsplitsings() {
        log.debug("REST request to get all Appartementsrechtsplitsings");
        return appartementsrechtsplitsingRepository.findAll();
    }

    /**
     * {@code GET  /appartementsrechtsplitsings/:id} : get the "id" appartementsrechtsplitsing.
     *
     * @param id the id of the appartementsrechtsplitsing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appartementsrechtsplitsing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appartementsrechtsplitsing> getAppartementsrechtsplitsing(@PathVariable("id") Long id) {
        log.debug("REST request to get Appartementsrechtsplitsing : {}", id);
        Optional<Appartementsrechtsplitsing> appartementsrechtsplitsing = appartementsrechtsplitsingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appartementsrechtsplitsing);
    }

    /**
     * {@code DELETE  /appartementsrechtsplitsings/:id} : delete the "id" appartementsrechtsplitsing.
     *
     * @param id the id of the appartementsrechtsplitsing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppartementsrechtsplitsing(@PathVariable("id") Long id) {
        log.debug("REST request to delete Appartementsrechtsplitsing : {}", id);
        appartementsrechtsplitsingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
