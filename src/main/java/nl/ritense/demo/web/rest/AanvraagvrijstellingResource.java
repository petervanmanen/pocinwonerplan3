package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aanvraagvrijstelling;
import nl.ritense.demo.repository.AanvraagvrijstellingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanvraagvrijstelling}.
 */
@RestController
@RequestMapping("/api/aanvraagvrijstellings")
@Transactional
public class AanvraagvrijstellingResource {

    private final Logger log = LoggerFactory.getLogger(AanvraagvrijstellingResource.class);

    private static final String ENTITY_NAME = "aanvraagvrijstelling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanvraagvrijstellingRepository aanvraagvrijstellingRepository;

    public AanvraagvrijstellingResource(AanvraagvrijstellingRepository aanvraagvrijstellingRepository) {
        this.aanvraagvrijstellingRepository = aanvraagvrijstellingRepository;
    }

    /**
     * {@code POST  /aanvraagvrijstellings} : Create a new aanvraagvrijstelling.
     *
     * @param aanvraagvrijstelling the aanvraagvrijstelling to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanvraagvrijstelling, or with status {@code 400 (Bad Request)} if the aanvraagvrijstelling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanvraagvrijstelling> createAanvraagvrijstelling(@RequestBody Aanvraagvrijstelling aanvraagvrijstelling)
        throws URISyntaxException {
        log.debug("REST request to save Aanvraagvrijstelling : {}", aanvraagvrijstelling);
        if (aanvraagvrijstelling.getId() != null) {
            throw new BadRequestAlertException("A new aanvraagvrijstelling cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanvraagvrijstelling = aanvraagvrijstellingRepository.save(aanvraagvrijstelling);
        return ResponseEntity.created(new URI("/api/aanvraagvrijstellings/" + aanvraagvrijstelling.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanvraagvrijstelling.getId().toString()))
            .body(aanvraagvrijstelling);
    }

    /**
     * {@code PUT  /aanvraagvrijstellings/:id} : Updates an existing aanvraagvrijstelling.
     *
     * @param id the id of the aanvraagvrijstelling to save.
     * @param aanvraagvrijstelling the aanvraagvrijstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraagvrijstelling,
     * or with status {@code 400 (Bad Request)} if the aanvraagvrijstelling is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aanvraagvrijstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aanvraagvrijstelling> updateAanvraagvrijstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraagvrijstelling aanvraagvrijstelling
    ) throws URISyntaxException {
        log.debug("REST request to update Aanvraagvrijstelling : {}, {}", id, aanvraagvrijstelling);
        if (aanvraagvrijstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraagvrijstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraagvrijstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aanvraagvrijstelling = aanvraagvrijstellingRepository.save(aanvraagvrijstelling);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraagvrijstelling.getId().toString()))
            .body(aanvraagvrijstelling);
    }

    /**
     * {@code PATCH  /aanvraagvrijstellings/:id} : Partial updates given fields of an existing aanvraagvrijstelling, field will ignore if it is null
     *
     * @param id the id of the aanvraagvrijstelling to save.
     * @param aanvraagvrijstelling the aanvraagvrijstelling to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraagvrijstelling,
     * or with status {@code 400 (Bad Request)} if the aanvraagvrijstelling is not valid,
     * or with status {@code 404 (Not Found)} if the aanvraagvrijstelling is not found,
     * or with status {@code 500 (Internal Server Error)} if the aanvraagvrijstelling couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aanvraagvrijstelling> partialUpdateAanvraagvrijstelling(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraagvrijstelling aanvraagvrijstelling
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aanvraagvrijstelling partially : {}, {}", id, aanvraagvrijstelling);
        if (aanvraagvrijstelling.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraagvrijstelling.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraagvrijstellingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aanvraagvrijstelling> result = aanvraagvrijstellingRepository
            .findById(aanvraagvrijstelling.getId())
            .map(existingAanvraagvrijstelling -> {
                if (aanvraagvrijstelling.getBuitenlandseschoollocatie() != null) {
                    existingAanvraagvrijstelling.setBuitenlandseschoollocatie(aanvraagvrijstelling.getBuitenlandseschoollocatie());
                }
                if (aanvraagvrijstelling.getDatumaanvraag() != null) {
                    existingAanvraagvrijstelling.setDatumaanvraag(aanvraagvrijstelling.getDatumaanvraag());
                }

                return existingAanvraagvrijstelling;
            })
            .map(aanvraagvrijstellingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraagvrijstelling.getId().toString())
        );
    }

    /**
     * {@code GET  /aanvraagvrijstellings} : get all the aanvraagvrijstellings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanvraagvrijstellings in body.
     */
    @GetMapping("")
    public List<Aanvraagvrijstelling> getAllAanvraagvrijstellings() {
        log.debug("REST request to get all Aanvraagvrijstellings");
        return aanvraagvrijstellingRepository.findAll();
    }

    /**
     * {@code GET  /aanvraagvrijstellings/:id} : get the "id" aanvraagvrijstelling.
     *
     * @param id the id of the aanvraagvrijstelling to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanvraagvrijstelling, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanvraagvrijstelling> getAanvraagvrijstelling(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanvraagvrijstelling : {}", id);
        Optional<Aanvraagvrijstelling> aanvraagvrijstelling = aanvraagvrijstellingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanvraagvrijstelling);
    }

    /**
     * {@code DELETE  /aanvraagvrijstellings/:id} : delete the "id" aanvraagvrijstelling.
     *
     * @param id the id of the aanvraagvrijstelling to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanvraagvrijstelling(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanvraagvrijstelling : {}", id);
        aanvraagvrijstellingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
