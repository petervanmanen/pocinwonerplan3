package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aankondiging;
import nl.ritense.demo.repository.AankondigingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aankondiging}.
 */
@RestController
@RequestMapping("/api/aankondigings")
@Transactional
public class AankondigingResource {

    private final Logger log = LoggerFactory.getLogger(AankondigingResource.class);

    private static final String ENTITY_NAME = "aankondiging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AankondigingRepository aankondigingRepository;

    public AankondigingResource(AankondigingRepository aankondigingRepository) {
        this.aankondigingRepository = aankondigingRepository;
    }

    /**
     * {@code POST  /aankondigings} : Create a new aankondiging.
     *
     * @param aankondiging the aankondiging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aankondiging, or with status {@code 400 (Bad Request)} if the aankondiging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aankondiging> createAankondiging(@RequestBody Aankondiging aankondiging) throws URISyntaxException {
        log.debug("REST request to save Aankondiging : {}", aankondiging);
        if (aankondiging.getId() != null) {
            throw new BadRequestAlertException("A new aankondiging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aankondiging = aankondigingRepository.save(aankondiging);
        return ResponseEntity.created(new URI("/api/aankondigings/" + aankondiging.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aankondiging.getId().toString()))
            .body(aankondiging);
    }

    /**
     * {@code PUT  /aankondigings/:id} : Updates an existing aankondiging.
     *
     * @param id the id of the aankondiging to save.
     * @param aankondiging the aankondiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aankondiging,
     * or with status {@code 400 (Bad Request)} if the aankondiging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aankondiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aankondiging> updateAankondiging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aankondiging aankondiging
    ) throws URISyntaxException {
        log.debug("REST request to update Aankondiging : {}, {}", id, aankondiging);
        if (aankondiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aankondiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aankondigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aankondiging = aankondigingRepository.save(aankondiging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aankondiging.getId().toString()))
            .body(aankondiging);
    }

    /**
     * {@code PATCH  /aankondigings/:id} : Partial updates given fields of an existing aankondiging, field will ignore if it is null
     *
     * @param id the id of the aankondiging to save.
     * @param aankondiging the aankondiging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aankondiging,
     * or with status {@code 400 (Bad Request)} if the aankondiging is not valid,
     * or with status {@code 404 (Not Found)} if the aankondiging is not found,
     * or with status {@code 500 (Internal Server Error)} if the aankondiging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aankondiging> partialUpdateAankondiging(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aankondiging aankondiging
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aankondiging partially : {}, {}", id, aankondiging);
        if (aankondiging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aankondiging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aankondigingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aankondiging> result = aankondigingRepository
            .findById(aankondiging.getId())
            .map(existingAankondiging -> {
                if (aankondiging.getBeschrijving() != null) {
                    existingAankondiging.setBeschrijving(aankondiging.getBeschrijving());
                }
                if (aankondiging.getCategorie() != null) {
                    existingAankondiging.setCategorie(aankondiging.getCategorie());
                }
                if (aankondiging.getDatum() != null) {
                    existingAankondiging.setDatum(aankondiging.getDatum());
                }
                if (aankondiging.getNaam() != null) {
                    existingAankondiging.setNaam(aankondiging.getNaam());
                }
                if (aankondiging.getType() != null) {
                    existingAankondiging.setType(aankondiging.getType());
                }

                return existingAankondiging;
            })
            .map(aankondigingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aankondiging.getId().toString())
        );
    }

    /**
     * {@code GET  /aankondigings} : get all the aankondigings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aankondigings in body.
     */
    @GetMapping("")
    public List<Aankondiging> getAllAankondigings() {
        log.debug("REST request to get all Aankondigings");
        return aankondigingRepository.findAll();
    }

    /**
     * {@code GET  /aankondigings/:id} : get the "id" aankondiging.
     *
     * @param id the id of the aankondiging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aankondiging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aankondiging> getAankondiging(@PathVariable("id") Long id) {
        log.debug("REST request to get Aankondiging : {}", id);
        Optional<Aankondiging> aankondiging = aankondigingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aankondiging);
    }

    /**
     * {@code DELETE  /aankondigings/:id} : delete the "id" aankondiging.
     *
     * @param id the id of the aankondiging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAankondiging(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aankondiging : {}", id);
        aankondigingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
