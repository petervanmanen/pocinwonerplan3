package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Notitie;
import nl.ritense.demo.repository.NotitieRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Notitie}.
 */
@RestController
@RequestMapping("/api/notities")
@Transactional
public class NotitieResource {

    private final Logger log = LoggerFactory.getLogger(NotitieResource.class);

    private static final String ENTITY_NAME = "notitie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotitieRepository notitieRepository;

    public NotitieResource(NotitieRepository notitieRepository) {
        this.notitieRepository = notitieRepository;
    }

    /**
     * {@code POST  /notities} : Create a new notitie.
     *
     * @param notitie the notitie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notitie, or with status {@code 400 (Bad Request)} if the notitie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Notitie> createNotitie(@RequestBody Notitie notitie) throws URISyntaxException {
        log.debug("REST request to save Notitie : {}", notitie);
        if (notitie.getId() != null) {
            throw new BadRequestAlertException("A new notitie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        notitie = notitieRepository.save(notitie);
        return ResponseEntity.created(new URI("/api/notities/" + notitie.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, notitie.getId().toString()))
            .body(notitie);
    }

    /**
     * {@code PUT  /notities/:id} : Updates an existing notitie.
     *
     * @param id the id of the notitie to save.
     * @param notitie the notitie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notitie,
     * or with status {@code 400 (Bad Request)} if the notitie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notitie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Notitie> updateNotitie(@PathVariable(value = "id", required = false) final Long id, @RequestBody Notitie notitie)
        throws URISyntaxException {
        log.debug("REST request to update Notitie : {}, {}", id, notitie);
        if (notitie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notitie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notitieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        notitie = notitieRepository.save(notitie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notitie.getId().toString()))
            .body(notitie);
    }

    /**
     * {@code PATCH  /notities/:id} : Partial updates given fields of an existing notitie, field will ignore if it is null
     *
     * @param id the id of the notitie to save.
     * @param notitie the notitie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notitie,
     * or with status {@code 400 (Bad Request)} if the notitie is not valid,
     * or with status {@code 404 (Not Found)} if the notitie is not found,
     * or with status {@code 500 (Internal Server Error)} if the notitie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Notitie> partialUpdateNotitie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Notitie notitie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Notitie partially : {}, {}", id, notitie);
        if (notitie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notitie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notitieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Notitie> result = notitieRepository
            .findById(notitie.getId())
            .map(existingNotitie -> {
                if (notitie.getDatum() != null) {
                    existingNotitie.setDatum(notitie.getDatum());
                }
                if (notitie.getInhoud() != null) {
                    existingNotitie.setInhoud(notitie.getInhoud());
                }

                return existingNotitie;
            })
            .map(notitieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, notitie.getId().toString())
        );
    }

    /**
     * {@code GET  /notities} : get all the notities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notities in body.
     */
    @GetMapping("")
    public List<Notitie> getAllNotities() {
        log.debug("REST request to get all Notities");
        return notitieRepository.findAll();
    }

    /**
     * {@code GET  /notities/:id} : get the "id" notitie.
     *
     * @param id the id of the notitie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notitie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Notitie> getNotitie(@PathVariable("id") Long id) {
        log.debug("REST request to get Notitie : {}", id);
        Optional<Notitie> notitie = notitieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notitie);
    }

    /**
     * {@code DELETE  /notities/:id} : delete the "id" notitie.
     *
     * @param id the id of the notitie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotitie(@PathVariable("id") Long id) {
        log.debug("REST request to delete Notitie : {}", id);
        notitieRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
