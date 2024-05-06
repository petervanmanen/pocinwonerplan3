package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Rioolput;
import nl.ritense.demo.repository.RioolputRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Rioolput}.
 */
@RestController
@RequestMapping("/api/rioolputs")
@Transactional
public class RioolputResource {

    private final Logger log = LoggerFactory.getLogger(RioolputResource.class);

    private static final String ENTITY_NAME = "rioolput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RioolputRepository rioolputRepository;

    public RioolputResource(RioolputRepository rioolputRepository) {
        this.rioolputRepository = rioolputRepository;
    }

    /**
     * {@code POST  /rioolputs} : Create a new rioolput.
     *
     * @param rioolput the rioolput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rioolput, or with status {@code 400 (Bad Request)} if the rioolput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Rioolput> createRioolput(@RequestBody Rioolput rioolput) throws URISyntaxException {
        log.debug("REST request to save Rioolput : {}", rioolput);
        if (rioolput.getId() != null) {
            throw new BadRequestAlertException("A new rioolput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rioolput = rioolputRepository.save(rioolput);
        return ResponseEntity.created(new URI("/api/rioolputs/" + rioolput.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rioolput.getId().toString()))
            .body(rioolput);
    }

    /**
     * {@code PUT  /rioolputs/:id} : Updates an existing rioolput.
     *
     * @param id the id of the rioolput to save.
     * @param rioolput the rioolput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rioolput,
     * or with status {@code 400 (Bad Request)} if the rioolput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rioolput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rioolput> updateRioolput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rioolput rioolput
    ) throws URISyntaxException {
        log.debug("REST request to update Rioolput : {}, {}", id, rioolput);
        if (rioolput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rioolput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rioolputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rioolput = rioolputRepository.save(rioolput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rioolput.getId().toString()))
            .body(rioolput);
    }

    /**
     * {@code PATCH  /rioolputs/:id} : Partial updates given fields of an existing rioolput, field will ignore if it is null
     *
     * @param id the id of the rioolput to save.
     * @param rioolput the rioolput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rioolput,
     * or with status {@code 400 (Bad Request)} if the rioolput is not valid,
     * or with status {@code 404 (Not Found)} if the rioolput is not found,
     * or with status {@code 500 (Internal Server Error)} if the rioolput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rioolput> partialUpdateRioolput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rioolput rioolput
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rioolput partially : {}, {}", id, rioolput);
        if (rioolput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rioolput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rioolputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rioolput> result = rioolputRepository
            .findById(rioolput.getId())
            .map(existingRioolput -> {
                if (rioolput.getAantalbedrijven() != null) {
                    existingRioolput.setAantalbedrijven(rioolput.getAantalbedrijven());
                }
                if (rioolput.getAantalrecreatie() != null) {
                    existingRioolput.setAantalrecreatie(rioolput.getAantalrecreatie());
                }
                if (rioolput.getAantalwoningen() != null) {
                    existingRioolput.setAantalwoningen(rioolput.getAantalwoningen());
                }
                if (rioolput.getAfvoerendoppervlak() != null) {
                    existingRioolput.setAfvoerendoppervlak(rioolput.getAfvoerendoppervlak());
                }
                if (rioolput.getBergendoppervlak() != null) {
                    existingRioolput.setBergendoppervlak(rioolput.getBergendoppervlak());
                }
                if (rioolput.getRioolputconstructieonderdeel() != null) {
                    existingRioolput.setRioolputconstructieonderdeel(rioolput.getRioolputconstructieonderdeel());
                }
                if (rioolput.getRioolputrioolleiding() != null) {
                    existingRioolput.setRioolputrioolleiding(rioolput.getRioolputrioolleiding());
                }
                if (rioolput.getRisicogebied() != null) {
                    existingRioolput.setRisicogebied(rioolput.getRisicogebied());
                }
                if (rioolput.getToegangbreedte() != null) {
                    existingRioolput.setToegangbreedte(rioolput.getToegangbreedte());
                }
                if (rioolput.getToeganglengte() != null) {
                    existingRioolput.setToeganglengte(rioolput.getToeganglengte());
                }
                if (rioolput.getType() != null) {
                    existingRioolput.setType(rioolput.getType());
                }
                if (rioolput.getTypeplus() != null) {
                    existingRioolput.setTypeplus(rioolput.getTypeplus());
                }

                return existingRioolput;
            })
            .map(rioolputRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rioolput.getId().toString())
        );
    }

    /**
     * {@code GET  /rioolputs} : get all the rioolputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rioolputs in body.
     */
    @GetMapping("")
    public List<Rioolput> getAllRioolputs() {
        log.debug("REST request to get all Rioolputs");
        return rioolputRepository.findAll();
    }

    /**
     * {@code GET  /rioolputs/:id} : get the "id" rioolput.
     *
     * @param id the id of the rioolput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rioolput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rioolput> getRioolput(@PathVariable("id") Long id) {
        log.debug("REST request to get Rioolput : {}", id);
        Optional<Rioolput> rioolput = rioolputRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rioolput);
    }

    /**
     * {@code DELETE  /rioolputs/:id} : delete the "id" rioolput.
     *
     * @param id the id of the rioolput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRioolput(@PathVariable("id") Long id) {
        log.debug("REST request to delete Rioolput : {}", id);
        rioolputRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
