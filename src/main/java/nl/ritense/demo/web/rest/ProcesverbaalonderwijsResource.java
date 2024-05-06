package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Procesverbaalonderwijs;
import nl.ritense.demo.repository.ProcesverbaalonderwijsRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Procesverbaalonderwijs}.
 */
@RestController
@RequestMapping("/api/procesverbaalonderwijs")
@Transactional
public class ProcesverbaalonderwijsResource {

    private final Logger log = LoggerFactory.getLogger(ProcesverbaalonderwijsResource.class);

    private static final String ENTITY_NAME = "procesverbaalonderwijs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcesverbaalonderwijsRepository procesverbaalonderwijsRepository;

    public ProcesverbaalonderwijsResource(ProcesverbaalonderwijsRepository procesverbaalonderwijsRepository) {
        this.procesverbaalonderwijsRepository = procesverbaalonderwijsRepository;
    }

    /**
     * {@code POST  /procesverbaalonderwijs} : Create a new procesverbaalonderwijs.
     *
     * @param procesverbaalonderwijs the procesverbaalonderwijs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procesverbaalonderwijs, or with status {@code 400 (Bad Request)} if the procesverbaalonderwijs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Procesverbaalonderwijs> createProcesverbaalonderwijs(@RequestBody Procesverbaalonderwijs procesverbaalonderwijs)
        throws URISyntaxException {
        log.debug("REST request to save Procesverbaalonderwijs : {}", procesverbaalonderwijs);
        if (procesverbaalonderwijs.getId() != null) {
            throw new BadRequestAlertException("A new procesverbaalonderwijs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        procesverbaalonderwijs = procesverbaalonderwijsRepository.save(procesverbaalonderwijs);
        return ResponseEntity.created(new URI("/api/procesverbaalonderwijs/" + procesverbaalonderwijs.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, procesverbaalonderwijs.getId().toString()))
            .body(procesverbaalonderwijs);
    }

    /**
     * {@code PUT  /procesverbaalonderwijs/:id} : Updates an existing procesverbaalonderwijs.
     *
     * @param id the id of the procesverbaalonderwijs to save.
     * @param procesverbaalonderwijs the procesverbaalonderwijs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procesverbaalonderwijs,
     * or with status {@code 400 (Bad Request)} if the procesverbaalonderwijs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procesverbaalonderwijs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Procesverbaalonderwijs> updateProcesverbaalonderwijs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Procesverbaalonderwijs procesverbaalonderwijs
    ) throws URISyntaxException {
        log.debug("REST request to update Procesverbaalonderwijs : {}, {}", id, procesverbaalonderwijs);
        if (procesverbaalonderwijs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procesverbaalonderwijs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procesverbaalonderwijsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        procesverbaalonderwijs = procesverbaalonderwijsRepository.save(procesverbaalonderwijs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procesverbaalonderwijs.getId().toString()))
            .body(procesverbaalonderwijs);
    }

    /**
     * {@code PATCH  /procesverbaalonderwijs/:id} : Partial updates given fields of an existing procesverbaalonderwijs, field will ignore if it is null
     *
     * @param id the id of the procesverbaalonderwijs to save.
     * @param procesverbaalonderwijs the procesverbaalonderwijs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procesverbaalonderwijs,
     * or with status {@code 400 (Bad Request)} if the procesverbaalonderwijs is not valid,
     * or with status {@code 404 (Not Found)} if the procesverbaalonderwijs is not found,
     * or with status {@code 500 (Internal Server Error)} if the procesverbaalonderwijs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Procesverbaalonderwijs> partialUpdateProcesverbaalonderwijs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Procesverbaalonderwijs procesverbaalonderwijs
    ) throws URISyntaxException {
        log.debug("REST request to partial update Procesverbaalonderwijs partially : {}, {}", id, procesverbaalonderwijs);
        if (procesverbaalonderwijs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procesverbaalonderwijs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procesverbaalonderwijsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Procesverbaalonderwijs> result = procesverbaalonderwijsRepository
            .findById(procesverbaalonderwijs.getId())
            .map(existingProcesverbaalonderwijs -> {
                if (procesverbaalonderwijs.getDatumafgehandeld() != null) {
                    existingProcesverbaalonderwijs.setDatumafgehandeld(procesverbaalonderwijs.getDatumafgehandeld());
                }
                if (procesverbaalonderwijs.getDatumeindeproeftijd() != null) {
                    existingProcesverbaalonderwijs.setDatumeindeproeftijd(procesverbaalonderwijs.getDatumeindeproeftijd());
                }
                if (procesverbaalonderwijs.getDatumingelicht() != null) {
                    existingProcesverbaalonderwijs.setDatumingelicht(procesverbaalonderwijs.getDatumingelicht());
                }
                if (procesverbaalonderwijs.getDatumuitspraak() != null) {
                    existingProcesverbaalonderwijs.setDatumuitspraak(procesverbaalonderwijs.getDatumuitspraak());
                }
                if (procesverbaalonderwijs.getDatumzitting() != null) {
                    existingProcesverbaalonderwijs.setDatumzitting(procesverbaalonderwijs.getDatumzitting());
                }
                if (procesverbaalonderwijs.getGeldboete() != null) {
                    existingProcesverbaalonderwijs.setGeldboete(procesverbaalonderwijs.getGeldboete());
                }
                if (procesverbaalonderwijs.getGeldboetevoorwaardelijk() != null) {
                    existingProcesverbaalonderwijs.setGeldboetevoorwaardelijk(procesverbaalonderwijs.getGeldboetevoorwaardelijk());
                }
                if (procesverbaalonderwijs.getOpmerkingen() != null) {
                    existingProcesverbaalonderwijs.setOpmerkingen(procesverbaalonderwijs.getOpmerkingen());
                }
                if (procesverbaalonderwijs.getProeftijd() != null) {
                    existingProcesverbaalonderwijs.setProeftijd(procesverbaalonderwijs.getProeftijd());
                }
                if (procesverbaalonderwijs.getReden() != null) {
                    existingProcesverbaalonderwijs.setReden(procesverbaalonderwijs.getReden());
                }
                if (procesverbaalonderwijs.getSanctiesoort() != null) {
                    existingProcesverbaalonderwijs.setSanctiesoort(procesverbaalonderwijs.getSanctiesoort());
                }
                if (procesverbaalonderwijs.getUitspraak() != null) {
                    existingProcesverbaalonderwijs.setUitspraak(procesverbaalonderwijs.getUitspraak());
                }
                if (procesverbaalonderwijs.getVerzuimsoort() != null) {
                    existingProcesverbaalonderwijs.setVerzuimsoort(procesverbaalonderwijs.getVerzuimsoort());
                }

                return existingProcesverbaalonderwijs;
            })
            .map(procesverbaalonderwijsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procesverbaalonderwijs.getId().toString())
        );
    }

    /**
     * {@code GET  /procesverbaalonderwijs} : get all the procesverbaalonderwijs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procesverbaalonderwijs in body.
     */
    @GetMapping("")
    public List<Procesverbaalonderwijs> getAllProcesverbaalonderwijs() {
        log.debug("REST request to get all Procesverbaalonderwijs");
        return procesverbaalonderwijsRepository.findAll();
    }

    /**
     * {@code GET  /procesverbaalonderwijs/:id} : get the "id" procesverbaalonderwijs.
     *
     * @param id the id of the procesverbaalonderwijs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procesverbaalonderwijs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Procesverbaalonderwijs> getProcesverbaalonderwijs(@PathVariable("id") Long id) {
        log.debug("REST request to get Procesverbaalonderwijs : {}", id);
        Optional<Procesverbaalonderwijs> procesverbaalonderwijs = procesverbaalonderwijsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(procesverbaalonderwijs);
    }

    /**
     * {@code DELETE  /procesverbaalonderwijs/:id} : delete the "id" procesverbaalonderwijs.
     *
     * @param id the id of the procesverbaalonderwijs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcesverbaalonderwijs(@PathVariable("id") Long id) {
        log.debug("REST request to delete Procesverbaalonderwijs : {}", id);
        procesverbaalonderwijsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
