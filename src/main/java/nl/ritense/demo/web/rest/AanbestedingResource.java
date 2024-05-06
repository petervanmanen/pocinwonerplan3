package nl.ritense.demo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aanbesteding;
import nl.ritense.demo.repository.AanbestedingRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanbesteding}.
 */
@RestController
@RequestMapping("/api/aanbestedings")
@Transactional
public class AanbestedingResource {

    private final Logger log = LoggerFactory.getLogger(AanbestedingResource.class);

    private static final String ENTITY_NAME = "aanbesteding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanbestedingRepository aanbestedingRepository;

    public AanbestedingResource(AanbestedingRepository aanbestedingRepository) {
        this.aanbestedingRepository = aanbestedingRepository;
    }

    /**
     * {@code POST  /aanbestedings} : Create a new aanbesteding.
     *
     * @param aanbesteding the aanbesteding to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanbesteding, or with status {@code 400 (Bad Request)} if the aanbesteding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanbesteding> createAanbesteding(@Valid @RequestBody Aanbesteding aanbesteding) throws URISyntaxException {
        log.debug("REST request to save Aanbesteding : {}", aanbesteding);
        if (aanbesteding.getId() != null) {
            throw new BadRequestAlertException("A new aanbesteding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanbesteding = aanbestedingRepository.save(aanbesteding);
        return ResponseEntity.created(new URI("/api/aanbestedings/" + aanbesteding.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanbesteding.getId().toString()))
            .body(aanbesteding);
    }

    /**
     * {@code PUT  /aanbestedings/:id} : Updates an existing aanbesteding.
     *
     * @param id the id of the aanbesteding to save.
     * @param aanbesteding the aanbesteding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanbesteding,
     * or with status {@code 400 (Bad Request)} if the aanbesteding is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aanbesteding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aanbesteding> updateAanbesteding(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Aanbesteding aanbesteding
    ) throws URISyntaxException {
        log.debug("REST request to update Aanbesteding : {}, {}", id, aanbesteding);
        if (aanbesteding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanbesteding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanbestedingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aanbesteding = aanbestedingRepository.save(aanbesteding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanbesteding.getId().toString()))
            .body(aanbesteding);
    }

    /**
     * {@code PATCH  /aanbestedings/:id} : Partial updates given fields of an existing aanbesteding, field will ignore if it is null
     *
     * @param id the id of the aanbesteding to save.
     * @param aanbesteding the aanbesteding to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanbesteding,
     * or with status {@code 400 (Bad Request)} if the aanbesteding is not valid,
     * or with status {@code 404 (Not Found)} if the aanbesteding is not found,
     * or with status {@code 500 (Internal Server Error)} if the aanbesteding couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aanbesteding> partialUpdateAanbesteding(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Aanbesteding aanbesteding
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aanbesteding partially : {}, {}", id, aanbesteding);
        if (aanbesteding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanbesteding.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanbestedingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aanbesteding> result = aanbestedingRepository
            .findById(aanbesteding.getId())
            .map(existingAanbesteding -> {
                if (aanbesteding.getDatumpublicatie() != null) {
                    existingAanbesteding.setDatumpublicatie(aanbesteding.getDatumpublicatie());
                }
                if (aanbesteding.getDatumstart() != null) {
                    existingAanbesteding.setDatumstart(aanbesteding.getDatumstart());
                }
                if (aanbesteding.getDigitaal() != null) {
                    existingAanbesteding.setDigitaal(aanbesteding.getDigitaal());
                }
                if (aanbesteding.getNaam() != null) {
                    existingAanbesteding.setNaam(aanbesteding.getNaam());
                }
                if (aanbesteding.getProcedure() != null) {
                    existingAanbesteding.setProcedure(aanbesteding.getProcedure());
                }
                if (aanbesteding.getReferentienummer() != null) {
                    existingAanbesteding.setReferentienummer(aanbesteding.getReferentienummer());
                }
                if (aanbesteding.getScoremaximaal() != null) {
                    existingAanbesteding.setScoremaximaal(aanbesteding.getScoremaximaal());
                }
                if (aanbesteding.getStatus() != null) {
                    existingAanbesteding.setStatus(aanbesteding.getStatus());
                }
                if (aanbesteding.getTendernedkenmerk() != null) {
                    existingAanbesteding.setTendernedkenmerk(aanbesteding.getTendernedkenmerk());
                }
                if (aanbesteding.getType() != null) {
                    existingAanbesteding.setType(aanbesteding.getType());
                }
                if (aanbesteding.getVolgendesluiting() != null) {
                    existingAanbesteding.setVolgendesluiting(aanbesteding.getVolgendesluiting());
                }

                return existingAanbesteding;
            })
            .map(aanbestedingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanbesteding.getId().toString())
        );
    }

    /**
     * {@code GET  /aanbestedings} : get all the aanbestedings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanbestedings in body.
     */
    @GetMapping("")
    public List<Aanbesteding> getAllAanbestedings() {
        log.debug("REST request to get all Aanbestedings");
        return aanbestedingRepository.findAll();
    }

    /**
     * {@code GET  /aanbestedings/:id} : get the "id" aanbesteding.
     *
     * @param id the id of the aanbesteding to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanbesteding, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanbesteding> getAanbesteding(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanbesteding : {}", id);
        Optional<Aanbesteding> aanbesteding = aanbestedingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanbesteding);
    }

    /**
     * {@code DELETE  /aanbestedings/:id} : delete the "id" aanbesteding.
     *
     * @param id the id of the aanbesteding to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanbesteding(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanbesteding : {}", id);
        aanbestedingRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
