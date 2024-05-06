package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aanvraaginkooporder;
import nl.ritense.demo.repository.AanvraaginkooporderRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanvraaginkooporder}.
 */
@RestController
@RequestMapping("/api/aanvraaginkooporders")
@Transactional
public class AanvraaginkooporderResource {

    private final Logger log = LoggerFactory.getLogger(AanvraaginkooporderResource.class);

    private static final String ENTITY_NAME = "aanvraaginkooporder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanvraaginkooporderRepository aanvraaginkooporderRepository;

    public AanvraaginkooporderResource(AanvraaginkooporderRepository aanvraaginkooporderRepository) {
        this.aanvraaginkooporderRepository = aanvraaginkooporderRepository;
    }

    /**
     * {@code POST  /aanvraaginkooporders} : Create a new aanvraaginkooporder.
     *
     * @param aanvraaginkooporder the aanvraaginkooporder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanvraaginkooporder, or with status {@code 400 (Bad Request)} if the aanvraaginkooporder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanvraaginkooporder> createAanvraaginkooporder(@RequestBody Aanvraaginkooporder aanvraaginkooporder)
        throws URISyntaxException {
        log.debug("REST request to save Aanvraaginkooporder : {}", aanvraaginkooporder);
        if (aanvraaginkooporder.getId() != null) {
            throw new BadRequestAlertException("A new aanvraaginkooporder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanvraaginkooporder = aanvraaginkooporderRepository.save(aanvraaginkooporder);
        return ResponseEntity.created(new URI("/api/aanvraaginkooporders/" + aanvraaginkooporder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanvraaginkooporder.getId().toString()))
            .body(aanvraaginkooporder);
    }

    /**
     * {@code PUT  /aanvraaginkooporders/:id} : Updates an existing aanvraaginkooporder.
     *
     * @param id the id of the aanvraaginkooporder to save.
     * @param aanvraaginkooporder the aanvraaginkooporder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraaginkooporder,
     * or with status {@code 400 (Bad Request)} if the aanvraaginkooporder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aanvraaginkooporder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aanvraaginkooporder> updateAanvraaginkooporder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraaginkooporder aanvraaginkooporder
    ) throws URISyntaxException {
        log.debug("REST request to update Aanvraaginkooporder : {}, {}", id, aanvraaginkooporder);
        if (aanvraaginkooporder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraaginkooporder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraaginkooporderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aanvraaginkooporder = aanvraaginkooporderRepository.save(aanvraaginkooporder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraaginkooporder.getId().toString()))
            .body(aanvraaginkooporder);
    }

    /**
     * {@code PATCH  /aanvraaginkooporders/:id} : Partial updates given fields of an existing aanvraaginkooporder, field will ignore if it is null
     *
     * @param id the id of the aanvraaginkooporder to save.
     * @param aanvraaginkooporder the aanvraaginkooporder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanvraaginkooporder,
     * or with status {@code 400 (Bad Request)} if the aanvraaginkooporder is not valid,
     * or with status {@code 404 (Not Found)} if the aanvraaginkooporder is not found,
     * or with status {@code 500 (Internal Server Error)} if the aanvraaginkooporder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aanvraaginkooporder> partialUpdateAanvraaginkooporder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanvraaginkooporder aanvraaginkooporder
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aanvraaginkooporder partially : {}, {}", id, aanvraaginkooporder);
        if (aanvraaginkooporder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanvraaginkooporder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanvraaginkooporderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aanvraaginkooporder> result = aanvraaginkooporderRepository
            .findById(aanvraaginkooporder.getId())
            .map(existingAanvraaginkooporder -> {
                if (aanvraaginkooporder.getBetalingovermeerjaren() != null) {
                    existingAanvraaginkooporder.setBetalingovermeerjaren(aanvraaginkooporder.getBetalingovermeerjaren());
                }
                if (aanvraaginkooporder.getCorrespondentienummer() != null) {
                    existingAanvraaginkooporder.setCorrespondentienummer(aanvraaginkooporder.getCorrespondentienummer());
                }
                if (aanvraaginkooporder.getInhuuranders() != null) {
                    existingAanvraaginkooporder.setInhuuranders(aanvraaginkooporder.getInhuuranders());
                }
                if (aanvraaginkooporder.getLeveringofdienst() != null) {
                    existingAanvraaginkooporder.setLeveringofdienst(aanvraaginkooporder.getLeveringofdienst());
                }
                if (aanvraaginkooporder.getNettototaalbedrag() != null) {
                    existingAanvraaginkooporder.setNettototaalbedrag(aanvraaginkooporder.getNettototaalbedrag());
                }
                if (aanvraaginkooporder.getOmschrijving() != null) {
                    existingAanvraaginkooporder.setOmschrijving(aanvraaginkooporder.getOmschrijving());
                }
                if (aanvraaginkooporder.getOnderwerp() != null) {
                    existingAanvraaginkooporder.setOnderwerp(aanvraaginkooporder.getOnderwerp());
                }
                if (aanvraaginkooporder.getReactie() != null) {
                    existingAanvraaginkooporder.setReactie(aanvraaginkooporder.getReactie());
                }
                if (aanvraaginkooporder.getStatus() != null) {
                    existingAanvraaginkooporder.setStatus(aanvraaginkooporder.getStatus());
                }
                if (aanvraaginkooporder.getWijzevaninhuur() != null) {
                    existingAanvraaginkooporder.setWijzevaninhuur(aanvraaginkooporder.getWijzevaninhuur());
                }

                return existingAanvraaginkooporder;
            })
            .map(aanvraaginkooporderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanvraaginkooporder.getId().toString())
        );
    }

    /**
     * {@code GET  /aanvraaginkooporders} : get all the aanvraaginkooporders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanvraaginkooporders in body.
     */
    @GetMapping("")
    public List<Aanvraaginkooporder> getAllAanvraaginkooporders() {
        log.debug("REST request to get all Aanvraaginkooporders");
        return aanvraaginkooporderRepository.findAll();
    }

    /**
     * {@code GET  /aanvraaginkooporders/:id} : get the "id" aanvraaginkooporder.
     *
     * @param id the id of the aanvraaginkooporder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanvraaginkooporder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanvraaginkooporder> getAanvraaginkooporder(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanvraaginkooporder : {}", id);
        Optional<Aanvraaginkooporder> aanvraaginkooporder = aanvraaginkooporderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanvraaginkooporder);
    }

    /**
     * {@code DELETE  /aanvraaginkooporders/:id} : delete the "id" aanvraaginkooporder.
     *
     * @param id the id of the aanvraaginkooporder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanvraaginkooporder(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanvraaginkooporder : {}", id);
        aanvraaginkooporderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
