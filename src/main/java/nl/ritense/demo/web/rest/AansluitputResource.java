package nl.ritense.demo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import nl.ritense.demo.domain.Aansluitput;
import nl.ritense.demo.repository.AansluitputRepository;
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
 * REST controller for managing {@link nl.ritense.demo.domain.Aansluitput}.
 */
@RestController
@RequestMapping("/api/aansluitputs")
@Transactional
public class AansluitputResource {

    private final Logger log = LoggerFactory.getLogger(AansluitputResource.class);

    private static final String ENTITY_NAME = "aansluitput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AansluitputRepository aansluitputRepository;

    public AansluitputResource(AansluitputRepository aansluitputRepository) {
        this.aansluitputRepository = aansluitputRepository;
    }

    /**
     * {@code POST  /aansluitputs} : Create a new aansluitput.
     *
     * @param aansluitput the aansluitput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aansluitput, or with status {@code 400 (Bad Request)} if the aansluitput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Aansluitput> createAansluitput(@RequestBody Aansluitput aansluitput) throws URISyntaxException {
        log.debug("REST request to save Aansluitput : {}", aansluitput);
        if (aansluitput.getId() != null) {
            throw new BadRequestAlertException("A new aansluitput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aansluitput = aansluitputRepository.save(aansluitput);
        return ResponseEntity.created(new URI("/api/aansluitputs/" + aansluitput.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, aansluitput.getId().toString()))
            .body(aansluitput);
    }

    /**
     * {@code PUT  /aansluitputs/:id} : Updates an existing aansluitput.
     *
     * @param id the id of the aansluitput to save.
     * @param aansluitput the aansluitput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aansluitput,
     * or with status {@code 400 (Bad Request)} if the aansluitput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aansluitput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aansluitput> updateAansluitput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aansluitput aansluitput
    ) throws URISyntaxException {
        log.debug("REST request to update Aansluitput : {}, {}", id, aansluitput);
        if (aansluitput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aansluitput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aansluitputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aansluitput = aansluitputRepository.save(aansluitput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aansluitput.getId().toString()))
            .body(aansluitput);
    }

    /**
     * {@code PATCH  /aansluitputs/:id} : Partial updates given fields of an existing aansluitput, field will ignore if it is null
     *
     * @param id the id of the aansluitput to save.
     * @param aansluitput the aansluitput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aansluitput,
     * or with status {@code 400 (Bad Request)} if the aansluitput is not valid,
     * or with status {@code 404 (Not Found)} if the aansluitput is not found,
     * or with status {@code 500 (Internal Server Error)} if the aansluitput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aansluitput> partialUpdateAansluitput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aansluitput aansluitput
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aansluitput partially : {}, {}", id, aansluitput);
        if (aansluitput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aansluitput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aansluitputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aansluitput> result = aansluitputRepository
            .findById(aansluitput.getId())
            .map(existingAansluitput -> {
                if (aansluitput.getAansluitpunt() != null) {
                    existingAansluitput.setAansluitpunt(aansluitput.getAansluitpunt());
                }
                if (aansluitput.getRisicogebied() != null) {
                    existingAansluitput.setRisicogebied(aansluitput.getRisicogebied());
                }
                if (aansluitput.getType() != null) {
                    existingAansluitput.setType(aansluitput.getType());
                }

                return existingAansluitput;
            })
            .map(aansluitputRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aansluitput.getId().toString())
        );
    }

    /**
     * {@code GET  /aansluitputs} : get all the aansluitputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aansluitputs in body.
     */
    @GetMapping("")
    public List<Aansluitput> getAllAansluitputs() {
        log.debug("REST request to get all Aansluitputs");
        return aansluitputRepository.findAll();
    }

    /**
     * {@code GET  /aansluitputs/:id} : get the "id" aansluitput.
     *
     * @param id the id of the aansluitput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aansluitput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aansluitput> getAansluitput(@PathVariable("id") Long id) {
        log.debug("REST request to get Aansluitput : {}", id);
        Optional<Aansluitput> aansluitput = aansluitputRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aansluitput);
    }

    /**
     * {@code DELETE  /aansluitputs/:id} : delete the "id" aansluitput.
     *
     * @param id the id of the aansluitput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAansluitput(@PathVariable("id") Long id) {
        log.debug("REST request to delete Aansluitput : {}", id);
        aansluitputRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
