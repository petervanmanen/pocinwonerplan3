package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aanwezigedeelnemer;
import nl.ritense.demo.repository.AanwezigedeelnemerRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanwezigedeelnemer}.
 */
@RestController
@RequestMapping("/api/aanwezigedeelnemers")
@Transactional
public class AanwezigedeelnemerResource {

    private final Logger log = LoggerFactory.getLogger(AanwezigedeelnemerResource.class);

    private static final String ENTITY_NAME = "aanwezigedeelnemer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanwezigedeelnemerRepository aanwezigedeelnemerRepository;

    public AanwezigedeelnemerResource(AanwezigedeelnemerRepository aanwezigedeelnemerRepository) {
        this.aanwezigedeelnemerRepository = aanwezigedeelnemerRepository;
    }

    /**
     * {@code POST  /aanwezigedeelnemers} : Create a new aanwezigedeelnemer.
     *
     * @param aanwezigedeelnemer the aanwezigedeelnemer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanwezigedeelnemer, or with status {@code 400 (Bad Request)} if the aanwezigedeelnemer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanwezigedeelnemer> createAanwezigedeelnemer(@RequestBody Aanwezigedeelnemer aanwezigedeelnemer)
        throws URISyntaxException {
        log.debug("REST request to save Aanwezigedeelnemer : {}", aanwezigedeelnemer);
        if (aanwezigedeelnemer.getId() != null) {
            throw new BadRequestAlertException("A new aanwezigedeelnemer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanwezigedeelnemer = aanwezigedeelnemerRepository.save(aanwezigedeelnemer);
        return ResponseEntity.created(new URI("/api/aanwezigedeelnemers/" + aanwezigedeelnemer.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanwezigedeelnemer.getId().toString()))
            .body(aanwezigedeelnemer);
    }

    /**
     * {@code PUT  /aanwezigedeelnemers/:id} : Updates an existing aanwezigedeelnemer.
     *
     * @param id the id of the aanwezigedeelnemer to save.
     * @param aanwezigedeelnemer the aanwezigedeelnemer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanwezigedeelnemer,
     * or with status {@code 400 (Bad Request)} if the aanwezigedeelnemer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aanwezigedeelnemer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aanwezigedeelnemer> updateAanwezigedeelnemer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanwezigedeelnemer aanwezigedeelnemer
    ) throws URISyntaxException {
        log.debug("REST request to update Aanwezigedeelnemer : {}, {}", id, aanwezigedeelnemer);
        if (aanwezigedeelnemer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanwezigedeelnemer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanwezigedeelnemerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aanwezigedeelnemer = aanwezigedeelnemerRepository.save(aanwezigedeelnemer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanwezigedeelnemer.getId().toString()))
            .body(aanwezigedeelnemer);
    }

    /**
     * {@code PATCH  /aanwezigedeelnemers/:id} : Partial updates given fields of an existing aanwezigedeelnemer, field will ignore if it is null
     *
     * @param id the id of the aanwezigedeelnemer to save.
     * @param aanwezigedeelnemer the aanwezigedeelnemer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanwezigedeelnemer,
     * or with status {@code 400 (Bad Request)} if the aanwezigedeelnemer is not valid,
     * or with status {@code 404 (Not Found)} if the aanwezigedeelnemer is not found,
     * or with status {@code 500 (Internal Server Error)} if the aanwezigedeelnemer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aanwezigedeelnemer> partialUpdateAanwezigedeelnemer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanwezigedeelnemer aanwezigedeelnemer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aanwezigedeelnemer partially : {}, {}", id, aanwezigedeelnemer);
        if (aanwezigedeelnemer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanwezigedeelnemer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanwezigedeelnemerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aanwezigedeelnemer> result = aanwezigedeelnemerRepository
            .findById(aanwezigedeelnemer.getId())
            .map(existingAanwezigedeelnemer -> {
                if (aanwezigedeelnemer.getAanvangaanwezigheid() != null) {
                    existingAanwezigedeelnemer.setAanvangaanwezigheid(aanwezigedeelnemer.getAanvangaanwezigheid());
                }
                if (aanwezigedeelnemer.getEindeaanwezigheid() != null) {
                    existingAanwezigedeelnemer.setEindeaanwezigheid(aanwezigedeelnemer.getEindeaanwezigheid());
                }
                if (aanwezigedeelnemer.getNaam() != null) {
                    existingAanwezigedeelnemer.setNaam(aanwezigedeelnemer.getNaam());
                }
                if (aanwezigedeelnemer.getRol() != null) {
                    existingAanwezigedeelnemer.setRol(aanwezigedeelnemer.getRol());
                }
                if (aanwezigedeelnemer.getVertegenwoordigtorganisatie() != null) {
                    existingAanwezigedeelnemer.setVertegenwoordigtorganisatie(aanwezigedeelnemer.getVertegenwoordigtorganisatie());
                }

                return existingAanwezigedeelnemer;
            })
            .map(aanwezigedeelnemerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanwezigedeelnemer.getId().toString())
        );
    }

    /**
     * {@code GET  /aanwezigedeelnemers} : get all the aanwezigedeelnemers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanwezigedeelnemers in body.
     */
    @GetMapping("")
    public List<Aanwezigedeelnemer> getAllAanwezigedeelnemers() {
        log.debug("REST request to get all Aanwezigedeelnemers");
        return aanwezigedeelnemerRepository.findAll();
    }

    /**
     * {@code GET  /aanwezigedeelnemers/:id} : get the "id" aanwezigedeelnemer.
     *
     * @param id the id of the aanwezigedeelnemer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanwezigedeelnemer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanwezigedeelnemer> getAanwezigedeelnemer(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanwezigedeelnemer : {}", id);
        Optional<Aanwezigedeelnemer> aanwezigedeelnemer = aanwezigedeelnemerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanwezigedeelnemer);
    }

    /**
     * {@code DELETE  /aanwezigedeelnemers/:id} : delete the "id" aanwezigedeelnemer.
     *
     * @param id the id of the aanwezigedeelnemer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanwezigedeelnemer(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanwezigedeelnemer : {}", id);
        aanwezigedeelnemerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
