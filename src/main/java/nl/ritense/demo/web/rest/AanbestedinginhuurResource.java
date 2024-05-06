package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aanbestedinginhuur;
import nl.ritense.demo.repository.AanbestedinginhuurRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aanbestedinginhuur}.
 */
@RestController
@RequestMapping("/api/aanbestedinginhuurs")
@Transactional
public class AanbestedinginhuurResource {

    private final Logger log = LoggerFactory.getLogger(AanbestedinginhuurResource.class);

    private static final String ENTITY_NAME = "aanbestedinginhuur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AanbestedinginhuurRepository aanbestedinginhuurRepository;

    public AanbestedinginhuurResource(AanbestedinginhuurRepository aanbestedinginhuurRepository) {
        this.aanbestedinginhuurRepository = aanbestedinginhuurRepository;
    }

    /**
     * {@code POST  /aanbestedinginhuurs} : Create a new aanbestedinginhuur.
     *
     * @param aanbestedinginhuur the aanbestedinginhuur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aanbestedinginhuur, or with status {@code 400 (Bad Request)} if the aanbestedinginhuur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aanbestedinginhuur> createAanbestedinginhuur(@RequestBody Aanbestedinginhuur aanbestedinginhuur)
        throws URISyntaxException {
        log.debug("REST request to save Aanbestedinginhuur : {}", aanbestedinginhuur);
        if (aanbestedinginhuur.getId() != null) {
            throw new BadRequestAlertException("A new aanbestedinginhuur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aanbestedinginhuur = aanbestedinginhuurRepository.save(aanbestedinginhuur);
        return ResponseEntity.created(new URI("/api/aanbestedinginhuurs/" + aanbestedinginhuur.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aanbestedinginhuur.getId().toString()))
            .body(aanbestedinginhuur);
    }

    /**
     * {@code PUT  /aanbestedinginhuurs/:id} : Updates an existing aanbestedinginhuur.
     *
     * @param id the id of the aanbestedinginhuur to save.
     * @param aanbestedinginhuur the aanbestedinginhuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanbestedinginhuur,
     * or with status {@code 400 (Bad Request)} if the aanbestedinginhuur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aanbestedinginhuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aanbestedinginhuur> updateAanbestedinginhuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanbestedinginhuur aanbestedinginhuur
    ) throws URISyntaxException {
        log.debug("REST request to update Aanbestedinginhuur : {}, {}", id, aanbestedinginhuur);
        if (aanbestedinginhuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanbestedinginhuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanbestedinginhuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aanbestedinginhuur = aanbestedinginhuurRepository.save(aanbestedinginhuur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanbestedinginhuur.getId().toString()))
            .body(aanbestedinginhuur);
    }

    /**
     * {@code PATCH  /aanbestedinginhuurs/:id} : Partial updates given fields of an existing aanbestedinginhuur, field will ignore if it is null
     *
     * @param id the id of the aanbestedinginhuur to save.
     * @param aanbestedinginhuur the aanbestedinginhuur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aanbestedinginhuur,
     * or with status {@code 400 (Bad Request)} if the aanbestedinginhuur is not valid,
     * or with status {@code 404 (Not Found)} if the aanbestedinginhuur is not found,
     * or with status {@code 500 (Internal Server Error)} if the aanbestedinginhuur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aanbestedinginhuur> partialUpdateAanbestedinginhuur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aanbestedinginhuur aanbestedinginhuur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aanbestedinginhuur partially : {}, {}", id, aanbestedinginhuur);
        if (aanbestedinginhuur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aanbestedinginhuur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aanbestedinginhuurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aanbestedinginhuur> result = aanbestedinginhuurRepository
            .findById(aanbestedinginhuur.getId())
            .map(existingAanbestedinginhuur -> {
                if (aanbestedinginhuur.getAanvraaggesloten() != null) {
                    existingAanbestedinginhuur.setAanvraaggesloten(aanbestedinginhuur.getAanvraaggesloten());
                }
                if (aanbestedinginhuur.getAanvraagnummer() != null) {
                    existingAanbestedinginhuur.setAanvraagnummer(aanbestedinginhuur.getAanvraagnummer());
                }
                if (aanbestedinginhuur.getDatumcreatie() != null) {
                    existingAanbestedinginhuur.setDatumcreatie(aanbestedinginhuur.getDatumcreatie());
                }
                if (aanbestedinginhuur.getDatumopeningkluis() != null) {
                    existingAanbestedinginhuur.setDatumopeningkluis(aanbestedinginhuur.getDatumopeningkluis());
                }
                if (aanbestedinginhuur.getDatumsluiting() != null) {
                    existingAanbestedinginhuur.setDatumsluiting(aanbestedinginhuur.getDatumsluiting());
                }
                if (aanbestedinginhuur.getDatumverzending() != null) {
                    existingAanbestedinginhuur.setDatumverzending(aanbestedinginhuur.getDatumverzending());
                }
                if (aanbestedinginhuur.getFase() != null) {
                    existingAanbestedinginhuur.setFase(aanbestedinginhuur.getFase());
                }
                if (aanbestedinginhuur.getHoogstetarief() != null) {
                    existingAanbestedinginhuur.setHoogstetarief(aanbestedinginhuur.getHoogstetarief());
                }
                if (aanbestedinginhuur.getLaagstetarief() != null) {
                    existingAanbestedinginhuur.setLaagstetarief(aanbestedinginhuur.getLaagstetarief());
                }
                if (aanbestedinginhuur.getOmschrijving() != null) {
                    existingAanbestedinginhuur.setOmschrijving(aanbestedinginhuur.getOmschrijving());
                }
                if (aanbestedinginhuur.getPerceel() != null) {
                    existingAanbestedinginhuur.setPerceel(aanbestedinginhuur.getPerceel());
                }
                if (aanbestedinginhuur.getProcedure() != null) {
                    existingAanbestedinginhuur.setProcedure(aanbestedinginhuur.getProcedure());
                }
                if (aanbestedinginhuur.getProjectnaam() != null) {
                    existingAanbestedinginhuur.setProjectnaam(aanbestedinginhuur.getProjectnaam());
                }
                if (aanbestedinginhuur.getProjectreferentie() != null) {
                    existingAanbestedinginhuur.setProjectreferentie(aanbestedinginhuur.getProjectreferentie());
                }
                if (aanbestedinginhuur.getPublicatie() != null) {
                    existingAanbestedinginhuur.setPublicatie(aanbestedinginhuur.getPublicatie());
                }
                if (aanbestedinginhuur.getReferentie() != null) {
                    existingAanbestedinginhuur.setReferentie(aanbestedinginhuur.getReferentie());
                }
                if (aanbestedinginhuur.getStatus() != null) {
                    existingAanbestedinginhuur.setStatus(aanbestedinginhuur.getStatus());
                }
                if (aanbestedinginhuur.getTitel() != null) {
                    existingAanbestedinginhuur.setTitel(aanbestedinginhuur.getTitel());
                }
                if (aanbestedinginhuur.getType() != null) {
                    existingAanbestedinginhuur.setType(aanbestedinginhuur.getType());
                }

                return existingAanbestedinginhuur;
            })
            .map(aanbestedinginhuurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aanbestedinginhuur.getId().toString())
        );
    }

    /**
     * {@code GET  /aanbestedinginhuurs} : get all the aanbestedinginhuurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aanbestedinginhuurs in body.
     */
    @GetMapping("")
    public List<Aanbestedinginhuur> getAllAanbestedinginhuurs() {
        log.debug("REST request to get all Aanbestedinginhuurs");
        return aanbestedinginhuurRepository.findAll();
    }

    /**
     * {@code GET  /aanbestedinginhuurs/:id} : get the "id" aanbestedinginhuur.
     *
     * @param id the id of the aanbestedinginhuur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aanbestedinginhuur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aanbestedinginhuur> getAanbestedinginhuur(@PathVariable("id") Long id) {
        log.debug("REST request to get Aanbestedinginhuur : {}", id);
        Optional<Aanbestedinginhuur> aanbestedinginhuur = aanbestedinginhuurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aanbestedinginhuur);
    }

    /**
     * {@code DELETE  /aanbestedinginhuurs/:id} : delete the "id" aanbestedinginhuur.
     *
     * @param id the id of the aanbestedinginhuur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAanbestedinginhuur(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aanbestedinginhuur : {}", id);
        aanbestedinginhuurRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
